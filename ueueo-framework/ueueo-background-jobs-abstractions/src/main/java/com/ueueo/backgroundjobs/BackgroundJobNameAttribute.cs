using System;
using System.Linq;
using JetBrains.Annotations;

namespace Volo.Abp.BackgroundJobs;

public class BackgroundJobNameAttribute : Attribute, IBackgroundJobNameProvider
{
    public String Name;//  { get; }

    public BackgroundJobNameAttribute(@NonNull String name)
    {
        Name = Check.NotNullOrWhiteSpace(name, nameof(name));
    }

    public static String GetName<TJobArgs>()
    {
        return GetName(typeof(TJobArgs));
    }

    public static String GetName(@NonNull Type jobArgsType)
    {
        Objects.requireNonNull(jobArgsType, nameof(jobArgsType));

        return jobArgsType
                   .GetCustomAttributes(true)
                   .OfType<IBackgroundJobNameProvider>()
                   .FirstOrDefault()
                   ?.Name
               ?? jobArgsType.FullName;
    }
}
