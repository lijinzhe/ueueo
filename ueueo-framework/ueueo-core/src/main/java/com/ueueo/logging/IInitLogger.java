package com.ueueo.logging;

import java.util.List;

/**
 * A generic interface for logging where the category name is derived from the specified
 * TCategoryName type name. Generally used to enable activation of a named Microsoft.Extensions.Logging.ILogger
 * from dependency injection.
 *
 * @param <TCategoryName> The type whose name is used for the logger category name.
 */
public interface IInitLogger<TCategoryName> extends ILogger {
    List<AbpInitLogEntry> getEntries();
}
