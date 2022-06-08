package com.ueueo.blobstoring;

public interface IBlobContainerFactory {
    /**
     * Gets a named container.
     *
     * <param name="name">The name of the container</param>
     * <returns>
     * The container object.
     * </returns>
     */
    IBlobContainer create(String name);
}
