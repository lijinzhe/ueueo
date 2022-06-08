package com.ueueo.blobstoring;

import com.ueueo.threading.CancellationToken;

import java.io.InputStream;

public interface IBlobContainer {
    /**
     * Saves a blob <see cref="Stream"/> to the container.
     *
     * <param name="name">The name of the blob</param>
     * <param name="stream">A stream for the blob</param>
     * <param name="overrideExisting">
     * Set <code>true</code> to override if there is already a blob in the container with the given name.
     * If set to <code>false</code> (default), throws exception if there is already a blob in the container with the given name.
     * </param>
     * <param name="cancellationToken">Cancellation token</param>
     */
    void save(
            String name,
            InputStream stream,
            boolean overrideExisting,
            CancellationToken cancellationToken
    );

    /**
     * Deletes a blob from the container.
     *
     * <param name="name">The name of the blob</param>
     * <param name="cancellationToken">Cancellation token</param>
     * <returns>
     * Returns true if actually deleted the blob.
     * Returns false if the blob with the given <paramref name="name"/> was not exists.
     * </returns>
     */
    Boolean delete(
            String name,
            CancellationToken cancellationToken
    );

    /**
     * Checks if a blob does exists in the container.
     *
     * <param name="name">The name of the blob</param>
     * <param name="cancellationToken">Cancellation token</param>
     */
    Boolean exists(
            String name,
            CancellationToken cancellationToken
    );

    /**
     * Gets a blob from the container.
     * It actually gets a <see cref="Stream"/> to read the blob data.
     * It throws exception if there is no blob with the given <paramref name="name"/>.
     * Use <see cref="GetOrNullAsync"/> if you want to get <code>null</code> if there is no blob with the given <paramref name="name"/>.
     *
     * <param name="name">The name of the blob</param>
     * <param name="cancellationToken">Cancellation token</param>
     * <returns>
     * A <see cref="Stream"/> to read the blob data.
     * </returns>
     */
    InputStream get(
            String name,
            CancellationToken cancellationToken
    );

}
