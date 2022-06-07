using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.DependencyInjection.Extensions;
using Volo.Abp.Domain;
using Volo.Abp.Domain.Repositories.MongoDB;
using Volo.Abp.Modularity;
using Volo.Abp.MongoDB.DependencyInjection;
using Volo.Abp.Uow.MongoDB;
using Volo.Abp.MongoDB.DistributedEvents;

namespace Volo.Abp.MongoDB;

[DependsOn(typeof(AbpDddDomainModule))]
public class AbpMongoDbModule : AbpModule
{
    @Override
    public void PreConfigureServices(ServiceConfigurationContext context)
    {
        context.Services.AddConventionalRegistrar(new AbpMongoDbConventionalRegistrar());
    }

    @Override
    public void ConfigureServices(ServiceConfigurationContext context)
    {
        context.Services.TryAddTransient(
            typeof(IMongoDbContextProvider<>),
            typeof(UnitOfWorkMongoDbContextProvider<>)
        );

        context.Services.TryAddTransient(
            typeof(IMongoDbRepositoryFilterer<>),
            typeof(MongoDbRepositoryFilterer<>)
        );

        context.Services.TryAddTransient(
            typeof(IMongoDbRepositoryFilterer<,>),
            typeof(MongoDbRepositoryFilterer<,>)
        );

        context.Services.AddTransient(
            typeof(IMongoDbContextEventOutbox<>),
            typeof(MongoDbContextEventOutbox<>)
        );

        context.Services.AddTransient(
            typeof(IMongoDbContextEventInbox<>),
            typeof(MongoDbContextEventInbox<>)
        );
    }
}
