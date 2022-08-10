package com.ueueo.datamodel;

/**
 * This interface is used to make an entity active/passive.
 *
 * @author Lee
 * @date 2022-07-13 17:05
 */
public interface IPassivable {

    boolean isActive();

    void setActive(boolean active);
}
