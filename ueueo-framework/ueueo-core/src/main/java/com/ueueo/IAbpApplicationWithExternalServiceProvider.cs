using System;
using System.Threading.Tasks;
using JetBrains.Annotations;

namespace Volo.Abp;

public interface IAbpApplicationWithExternalServiceProvider : IAbpApplication
{
    /**
     * Sets the service provider, but not initializes the modules.
    */
    void SetServiceProvider(@Nonnull IServiceProvider serviceProvider);

    /**
     * Sets the service provider and initializes all the modules.
     * If <see cref="SetServiceProvider"/> was called before, the same
     * <see cref="serviceProvider"/> instance should be passed to this method.
    */
    void InitializeAsync(@Nonnull IServiceProvider serviceProvider);

    /**
     * Sets the service provider and initializes all the modules.
     * If <see cref="SetServiceProvider"/> was called before, the same
     * <see cref="serviceProvider"/> instance should be passed to this method.
    */
    void Initialize(@Nonnull IServiceProvider serviceProvider);
}
