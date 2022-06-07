using System;
using System.Collections.Generic;
using System.Collections.Immutable;

namespace Volo.Abp.BackgroundJobs;

public class AbpBackgroundJobOptions
{
    private readonly Dictionary<Type, BackgroundJobConfiguration> _jobConfigurationsByArgsType;
    private readonly Dictionary<String, BackgroundJobConfiguration> _jobConfigurationsByName;

    /**
     * Default: true.
    */
    public boolean IsJobExecutionEnabled;// { get; set; } = true;

    public AbpBackgroundJobOptions()
    {
        _jobConfigurationsByArgsType = new Dictionary<Type, BackgroundJobConfiguration>();
        _jobConfigurationsByName = new Dictionary<String, BackgroundJobConfiguration>();
    }

    public BackgroundJobConfiguration GetJob<TArgs>()
    {
        return GetJob(typeof(TArgs));
    }

    public BackgroundJobConfiguration GetJob(Type argsType)
    {
        var jobConfiguration = _jobConfigurationsByArgsType.GetOrDefault(argsType);

        if (jobConfiguration == null)
        {
            throw new AbpException("Undefined background job for the job args type: " + argsType.AssemblyQualifiedName);
        }

        return jobConfiguration;
    }

    public BackgroundJobConfiguration GetJob(String name)
    {
        var jobConfiguration = _jobConfigurationsByName.GetOrDefault(name);

        if (jobConfiguration == null)
        {
            throw new AbpException("Undefined background job for the job name: " + name);
        }

        return jobConfiguration;
    }

    public IReadOnlyList<BackgroundJobConfiguration> GetJobs()
    {
        return _jobConfigurationsByArgsType.Values.ToImmutableList();
    }

    public void AddJob<TJob>()
    {
        AddJob(typeof(TJob));
    }

    public void AddJob(Type jobType)
    {
        AddJob(new BackgroundJobConfiguration(jobType));
    }

    public void AddJob(BackgroundJobConfiguration jobConfiguration)
    {
        _jobConfigurationsByArgsType[jobConfiguration.ArgsType] = jobConfiguration;
        _jobConfigurationsByName[jobConfiguration.JobName] = jobConfiguration;
    }
}
