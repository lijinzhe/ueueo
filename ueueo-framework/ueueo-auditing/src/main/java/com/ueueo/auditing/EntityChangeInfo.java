package com.ueueo.auditing;

import com.ueueo.ID;
import com.ueueo.data.objectextending.ExtraPropertyDictionary;
import com.ueueo.data.objectextending.IHasExtraProperties;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Lee
 * @date 2022-05-26 11:04
 */
@Data
public class EntityChangeInfo implements IHasExtraProperties {

    private Date changeTime;

    private EntityChangeType changeType;

    /**
     * TenantId of the related entity.
     * This is not the TenantId of the audit log entry.
     * There can be multiple tenant data changes in a single audit log entry.
     */
    private ID entityTenantId;

    private String entityId;

    private String entityTypeFullName;

    private List<EntityPropertyChangeInfo> propertyChanges;

    private ExtraPropertyDictionary extraProperties;

    //TODO: Try to remove since it breaks serializability
    public Object entityEntry;

    public EntityChangeInfo() {
        extraProperties = new ExtraPropertyDictionary();
    }

    public void merge(EntityChangeInfo changeInfo) {
        for (EntityPropertyChangeInfo propertyChange : changeInfo.propertyChanges) {
            EntityPropertyChangeInfo existingChange = propertyChanges.stream()
                    .filter(p -> Objects.equals(p.getPropertyName(), propertyChange.getPropertyName()))
                    .findFirst().orElse(null);
            if (existingChange == null) {
                propertyChanges.add(propertyChange);
            } else {
                existingChange.setNewValue(propertyChange.getNewValue());
            }
        }

        for (Map.Entry<String, Object> extraProperty : changeInfo.extraProperties.entrySet()) {
            String key = extraProperty.getKey();
            if (extraProperties.containsKey(key)) {
                key = InternalUtils.addCounter(key);
            }
            extraProperties.put(key, extraProperty.getValue());
        }
    }
}
