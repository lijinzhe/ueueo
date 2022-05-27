package com.ueueo.auditing;

import com.ueueo.IDisposable;
import com.ueueo.threading.IAmbientScopeProvider;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * TODO Description Of This JAVA Class.
 *
 * @author Lee
 * @date 2022-05-26 16:39
 */
@Slf4j
@Getter
public class AuditingManager implements IAuditingManager {

    private final String AmbientContextKey = "Volo.Abp.Auditing.IAuditLogScope";

    protected AbpAuditingOptions options;

    private final IAuditingHelper auditingHelper;
    private final IAuditingStore auditingStore;

    private final IAmbientScopeProvider<IAuditLogScope> ambientScopeProvider;
    private final IAuditLogScope current;

    public AuditingManager(
            IAmbientScopeProvider<IAuditLogScope> ambientScopeProvider,
            IAuditingHelper auditingHelper,
            IAuditingStore auditingStore,
            AbpAuditingOptions options) {
        this.options = options;

        this.ambientScopeProvider = ambientScopeProvider;
        this.auditingHelper = auditingHelper;
        this.auditingStore = auditingStore;

        this.current = ambientScopeProvider.getValue(AmbientContextKey);
    }

    @Override
    public IAuditLogSaveHandle beginScope() {
        IDisposable ambientScope = ambientScopeProvider.beginScope(
                AmbientContextKey,
                new AuditLogScope(auditingHelper.createAuditLogInfo())
        );

        Assert.notNull(current, "Current != null");

        return new DisposableSaveHandle(this, ambientScope, current.getLog(), StopWatch.createStarted());
    }

    protected void executePostContributors(AuditLogInfo auditLogInfo) {

        AuditLogContributionContext context = new AuditLogContributionContext(auditLogInfo);

        for (AuditLogContributor contributor : options.getContributors()) {
            try {
                contributor.postContribute(context);
            } catch (Exception ex) {
                log.warn(ex.getMessage(), ex);
            }
        }

    }

    protected void beforeSave(DisposableSaveHandle saveHandle) {
        saveHandle.stopWatch.stop();
        saveHandle.auditLog.setExecutionDuration(saveHandle.stopWatch.getTime());
        executePostContributors(saveHandle.auditLog);
        mergeEntityChanges(saveHandle.auditLog);
    }

    protected void mergeEntityChanges(AuditLogInfo auditLog) {
        Map<String, List<EntityChangeInfo>> changeGroups = auditLog.getEntityChanges()
                .stream().filter(e -> Objects.equals(e.getChangeType(), EntityChangeType.Updated))
                .collect(Collectors.toMap(changeInfo -> changeInfo.getEntityTypeFullName() + ":" + changeInfo.getEntityId(),
                        Collections::singletonList, ListUtils::union));

        for (List<EntityChangeInfo> changeGroup : changeGroups.values()) {
            if (changeGroup.size() <= 1) {
                continue;
            }

            EntityChangeInfo firstEntityChange = changeGroup.get(0);

            for (EntityChangeInfo entityChangeInfo : changeGroup) {
                if (entityChangeInfo == firstEntityChange) {
                    continue;
                }

                firstEntityChange.merge(entityChangeInfo);

                auditLog.getEntityChanges().remove(entityChangeInfo);
            }
        }
    }

    protected void save(DisposableSaveHandle saveHandle) {
        beforeSave(saveHandle);

        auditingStore.save(saveHandle.auditLog);
    }

    protected static class DisposableSaveHandle implements IAuditLogSaveHandle {
        private final AuditLogInfo auditLog;
        private final StopWatch stopWatch;

        private final AuditingManager auditingManager;
        private final IDisposable scope;

        public DisposableSaveHandle(
                AuditingManager auditingManager,
                IDisposable scope,
                AuditLogInfo auditLog,
                StopWatch stopWatch) {
            this.auditingManager = auditingManager;
            this.scope = scope;
            this.auditLog = auditLog;
            this.stopWatch = stopWatch;
        }

        @Override
        public void save() {
            auditingManager.save(this);
        }

        @Override
        public void dispose() {
            scope.dispose();
        }
    }
}
