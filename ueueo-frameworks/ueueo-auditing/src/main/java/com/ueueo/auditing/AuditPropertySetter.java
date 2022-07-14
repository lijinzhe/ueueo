package com.ueueo.auditing;

import com.ueueo.multitenancy.ICurrentTenant;
import com.ueueo.multitenancy.IMultiTenant;
import com.ueueo.users.ICurrentUser;
import lombok.Getter;

import java.util.Date;

/**
 * @author Lee
 * @date 2022-05-26 14:28
 */
@Getter
public class AuditPropertySetter implements IAuditPropertySetter {

    protected ICurrentUser currentUser;
    protected ICurrentTenant currentTenant;

    public AuditPropertySetter(ICurrentUser currentUser, ICurrentTenant currentTenant) {
        this.currentUser = currentUser;
        this.currentTenant = currentTenant;
    }

    @Override
    public void setCreationProperties(Object targetObject) {
        setCreationTime(targetObject);
        setCreatorId(targetObject);
    }

    @Override
    public void setModificationProperties(Object targetObject) {
        setLastModificationTime(targetObject);
        setLastModifierId(targetObject);
    }

    @Override
    public void setDeletionProperties(Object targetObject) {
        setDeletionTime(targetObject);
        setDeleterId(targetObject);
    }

    protected void setCreationTime(Object targetObject) {
        if (targetObject instanceof IHasCreationTime) {
            if (((IHasCreationTime) targetObject).getCreationTime() == null) {
                ((IHasCreationTime) targetObject).setCreationTime(new Date());
            }
        }
    }

    protected void setCreatorId(Object targetObject) {
        if (currentUser == null || currentUser.getId() == null) {
            return;
        }
        if (targetObject instanceof IMultiTenant) {
            if (!currentUser.getTenantId().equals(((IMultiTenant) targetObject).getTenantId())) {
                return;
            }
        }
        if (targetObject instanceof ICreationAuditedObject) {
            if (((ICreationAuditedObject) targetObject).getCreatorId() != null) {
                return;
            }
            ((ICreationAuditedObject) targetObject).setCreatorId(currentUser.getId());
        }
    }

    protected void setLastModificationTime(Object targetObject) {
        if (targetObject instanceof IHasModificationTime) {
            ((IHasModificationTime) targetObject).setLastModificationTime(new Date());
        }
    }

    protected void setLastModifierId(Object targetObject) {
        if (!(targetObject instanceof IModificationAuditedObject)) {
            return;
        }

        if (currentUser.getId() == null) {
            ((IModificationAuditedObject) targetObject).setLastModifierId(null);
            return;
        }

        if (targetObject instanceof IMultiTenant) {
            if (!((IMultiTenant) targetObject).getTenantId().equals(currentUser.getTenantId())) {
                ((IModificationAuditedObject) targetObject).setLastModifierId(null);
                return;
            }
        }

        /* TODO: The code below is from old ABP, not implemented yet
        if (tenantId.HasValue && MultiTenancyHelper.IsHostEntity(entity))
        {
            //Tenant user modified a host entity
            modificationAuditedObject.LastModifierId = null;
            return;
        }
         */
        ((IModificationAuditedObject) targetObject).setLastModifierId(currentUser.getId());
    }

    protected void setDeletionTime(Object targetObject) {
        if (targetObject instanceof IHasDeletionTime) {
            if (((IHasDeletionTime) targetObject).getDeletionTime() == null) {
                ((IHasDeletionTime) targetObject).setDeletionTime(new Date());
            }
        }
    }

    protected void setDeleterId(Object targetObject) {
        if (!(targetObject instanceof IDeletionAuditedObject)) {
            return;
        }

        if (((IDeletionAuditedObject) targetObject).getDeleterId() != null) {
            return;
        }

        if (currentUser.getId() == null) {

            ((IDeletionAuditedObject) targetObject).setDeleterId(null);
            return;
        }

        if (targetObject instanceof IMultiTenant) {
            if (!((IMultiTenant) targetObject).getTenantId().equals(currentUser.getTenantId())) {
                ((IDeletionAuditedObject) targetObject).setDeleterId(null);
                return;
            }
        }
        ((IDeletionAuditedObject) targetObject).setDeleterId(currentUser.getId());
    }
}
