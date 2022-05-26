package com.ueueo.auditing;

/**
 * @author Lee
 * @date 2022-05-26 11:32
 */
public interface IAuditPropertySetter {
    void setCreationProperties(Object targetObject);

    void setModificationProperties(Object targetObject);

    void setDeletionProperties(Object targetObject);
}
