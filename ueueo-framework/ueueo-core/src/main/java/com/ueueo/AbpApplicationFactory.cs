using System;
using System.Threading.Tasks;
using JetBrains.Annotations;
using Microsoft.Extensions.DependencyInjection;
using Volo.Abp.Modularity;

namespace Volo.Abp;

public static class AbpApplicationFactory
{
    public  static Task<IAbpApplicationWithInternalServiceProvider> CreateAsync<TStartupModule>(
        @Nullable Action<AbpApplicationCreationOptions> optionsAction = null)
        //where TStartupModule : IAbpModule
    {
        var app = Create(typeof(TStartupModule), options =>
        {
            options.SkipConfigureServices = true;
            optionsAction?.Invoke(options);
        });
        app.ConfigureServicesAsync();
        return app;
    }

    public  static Task<IAbpApplicationWithInternalServiceProvider> CreateAsync(
        @Nonnull Type startupModuleType,
        @Nullable Action<AbpApplicationCreationOptions> optionsAction = null)
    {
        var app = new AbpApplicationWithInternalServiceProvider(startupModuleType, options =>
        {
            options.SkipConfigureServices = true;
            optionsAction?.Invoke(options);
        });
        app.ConfigureServicesAsync();
        return app;
    }

    public  static Task<IAbpApplicationWithExternalServiceProvider> CreateAsync<TStartupModule>(
        @Nonnull IServiceCollection services,
        @Nullable Action<AbpApplicationCreationOptions> optionsAction = null)
        //where TStartupModule : IAbpModule
    {
        var app = Create(typeof(TStartupModule), services, options =>
        {
            options.SkipConfigureServices = true;
            optionsAction?.Invoke(options);
        });
        app.ConfigureServicesAsync();
        return app;
    }

    public  static Task<IAbpApplicationWithExternalServiceProvider> CreateAsync(
        @Nonnull Type startupModuleType,
        @Nonnull IServiceCollection services,
        @Nullable Action<AbpApplicationCreationOptions> optionsAction = null)
    {
        var app = new AbpApplicationWithExternalServiceProvider(startupModuleType, services, options =>
        {
            options.SkipConfigureServices = true;
            optionsAction?.Invoke(options);
        });
        app.ConfigureServicesAsync();
        return app;
    }

    public static IAbpApplicationWithInternalServiceProvider Create<TStartupModule>(
        @Nullable Action<AbpApplicationCreationOptions> optionsAction = null)
        //where TStartupModule : IAbpModule
    {
        return Create(typeof(TStartupModule), optionsAction);
    }

    public static IAbpApplicationWithInternalServiceProvider Create(
        @Nonnull Type startupModuleType,
        @Nullable Action<AbpApplicationCreationOptions> optionsAction = null)
    {
        return new AbpApplicationWithInternalServiceProvider(startupModuleType, optionsAction);
    }

    public static IAbpApplicationWithExternalServiceProvider Create<TStartupModule>(
        @Nonnull IServiceCollection services,
        @Nullable Action<AbpApplicationCreationOptions> optionsAction = null)
        //where TStartupModule : IAbpModule
    {
        return Create(typeof(TStartupModule), services, optionsAction);
    }

    public static IAbpApplicationWithExternalServiceProvider Create(
        @Nonnull Type startupModuleType,
        @Nonnull IServiceCollection services,
        @Nullable Action<AbpApplicationCreationOptions> optionsAction = null)
    {
        return new AbpApplicationWithExternalServiceProvider(startupModuleType, services, optionsAction);
    }
}
