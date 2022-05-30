using Microsoft.Extensions.DependencyInjection;
using Volo.Abp.Modularity;

namespace Volo.Abp.Uow;

public class AbpUnitOfWorkModule : AbpModule
{
    @Override
    public void PreConfigureServices(ServiceConfigurationContext context)
    {
        context.Services.OnRegistred(UnitOfWorkInterceptorRegistrar.RegisterIfNeeded);
    }
}
