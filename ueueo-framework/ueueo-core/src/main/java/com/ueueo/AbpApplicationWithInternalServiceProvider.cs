using System;
using System.Threading.Tasks;
using JetBrains.Annotations;
using Microsoft.Extensions.DependencyInjection;

namespace Volo.Abp;

internal class AbpApplicationWithInternalServiceProvider : AbpApplicationBase, IAbpApplicationWithInternalServiceProvider
{
    public IServiceScope ServiceScope ;// { get; private set; }

    public AbpApplicationWithInternalServiceProvider(
        @Nonnull Type startupModuleType,
        @Nullable Action<AbpApplicationCreationOptions> optionsAction
        ) : this(
        startupModuleType,
        new ServiceCollection(),
        optionsAction)
    {

    }

    private AbpApplicationWithInternalServiceProvider(
        @Nonnull Type startupModuleType,
        @Nonnull IServiceCollection services,
        @Nullable Action<AbpApplicationCreationOptions> optionsAction
        ) : base(
            startupModuleType,
            services,
            optionsAction)
    {
        Services.AddSingleton<IAbpApplicationWithInternalServiceProvider>(this);
    }

    public IServiceProvider CreateServiceProvider()
    {
        if (ServiceProvider != null)
        {
            return ServiceProvider;
        }

        ServiceScope = Services.BuildServiceProviderFromFactory().CreateScope();
        SetServiceProvider(ServiceScope.ServiceProvider);

        return ServiceProvider;
    }

    public void InitializeAsync()
    {
        CreateServiceProvider();
        InitializeModulesAsync();
    }

    public void Initialize()
    {
        CreateServiceProvider();
        InitializeModules();
    }

    @Override
    public void Dispose()
    {
       super.Dispose();
        ServiceScope.Dispose();
    }
}
