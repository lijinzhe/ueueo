using System;
using System.Collections.Generic;
using Volo.Abp.DependencyInjection;

namespace Volo.Abp.BackgroundWorkers.Quartz;

public class AbpQuartzConventionalRegistrar : DefaultConventionalRegistrar
{
    protected override boolean IsConventionalRegistrationDisabled(Type type)
    {
        return !typeof(IQuartzBackgroundWorker).IsAssignableFrom(type) ||super.IsConventionalRegistrationDisabled(type);
    }

    protected override List<Type> GetExposedServiceTypes(Type type)
    {
        return new List<Type>()
            {
                typeof(IQuartzBackgroundWorker)
            };
    }
}
