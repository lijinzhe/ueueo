package com.ueueo;

/**
 * Used to standardize soft deleting entities.
 * Soft-delete entities are not actually deleted,
 * marked as IsDeleted = true in the database,
 * but can not be retrieved to the application normally.
 *
 * @author Lee
 * @date 2022-05-14 16:10
 */
public interface ISoftDelete {
    /**
     * Used to mark an Entity as 'Deleted'.
     *
     * @return
     */
    boolean isDeleted();

    void setDeleted(boolean isDeleted);

}
