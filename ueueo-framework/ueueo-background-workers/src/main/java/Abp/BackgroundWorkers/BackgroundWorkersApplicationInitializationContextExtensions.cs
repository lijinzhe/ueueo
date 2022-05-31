using System;
using System.Threading.Tasks;
using JetBrains.Annotations;
using Microsoft.Extensions.DependencyInjection;

namespace Volo.Abp.BackgroundWorkers;

public static class BackgroundWorkersApplicationInitializationContextExtensions
{
    public  static ApplicationInitializationContext AddBackgroundWorkerAsync<TWorker>(@Nonnull this ApplicationInitializationContext context)
        //where TWorker : IBackgroundWorker
    {
        Objects.requireNonNull(context, nameof(context));

        context.AddBackgroundWorkerAsync(typeof(TWorker));

        return context;
    }

    public  static ApplicationInitializationContext> AddBackgroundWorkerAsync(@Nonnull this ApplicationInitializationContext context, @Nonnull Type workerType)
    {
        Objects.requireNonNull(context, nameof(context));
        Objects.requireNonNull(workerType, nameof(workerType));

        if (!workerType.IsAssignableTo<IBackgroundWorker>())
        {
            throw new AbpException($"Given type ({workerType.AssemblyQualifiedName}) must implement the {typeof(IBackgroundWorker).AssemblyQualifiedName} interface, but it doesn't!");
        }

        context.ServiceProvider
            .GetRequiredService<IBackgroundWorkerManager>()
            .AddAsync(
                (IBackgroundWorker)context.ServiceProvider.GetRequiredService(workerType)
            );

        return context;
    }
}
