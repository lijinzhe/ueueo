using System;
using System.Threading.Tasks;
using JetBrains.Annotations;
using Microsoft.Extensions.DependencyInjection;
using Volo.Abp.Modularity;

namespace Volo.Abp;

public static class AbpApplicationFactory
{
    public  static IAbpApplicationWithInternalServiceProvider> CreateAsync<TStartupModule>(
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

    public  static IAbpApplicationWithInternalServiceProvider> CreateAsync(
        @NonNull Type startupModuleType,
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

    public  static IAbpApplicationWithExternalServiceProvider> CreateAsync<TStartupModule>(
        @NonNull IServiceCollection services,
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

    public  static IAbpApplicationWithExternalServiceProvider> CreateAsync(
        @NonNull Type startupModuleType,
        @NonNull IServiceCollection services,
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
        @NonNull Type startupModuleType,
        @Nullable Action<AbpApplicationCreationOptions> optionsAction = null)
    {
        return new AbpApplicationWithInternalServiceProvider(startupModuleType, optionsAction);
    }

    public static IAbpApplicationWithExternalServiceProvider Create<TStartupModule>(
        @NonNull IServiceCollection services,
        @Nullable Action<AbpApplicationCreationOptions> optionsAction = null)
        //where TStartupModule : IAbpModule
    {
        return Create(typeof(TStartupModule), services, optionsAction);
    }

    public static IAbpApplicationWithExternalServiceProvider Create(
        @NonNull Type startupModuleType,
        @NonNull IServiceCollection services,
        @Nullable Action<AbpApplicationCreationOptions> optionsAction = null)
    {
        return new AbpApplicationWithExternalServiceProvider(startupModuleType, services, optionsAction);
    }
}
