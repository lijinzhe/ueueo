package com.ueueo.backgroundjobs;

import com.ueueo.exception.BaseException;

import java.util.HashMap;
import java.util.Map;

public class AbpBackgroundJobOptions {
//    private Map<Class<?>, BackgroundJobConfiguration> jobConfigurationsByArgsType;
    private Map<String, BackgroundJobConfiguration> jobConfigurationsByName;

    /**
     * Default: true.
     */
    private boolean isJobExecutionEnabled = true;

    public AbpBackgroundJobOptions() {
//        jobConfigurationsByArgsType = new HashMap<>();
        jobConfigurationsByName = new HashMap<>();
    }

//    public BackgroundJobConfiguration getJob(Class<?> argsType) {
//        BackgroundJobConfiguration jobConfiguration = jobConfigurationsByArgsType.get(argsType);
//
//        if (jobConfiguration == null) {
//            throw new AbpException("Undefined background job for the job args type: " + argsType.getName());
//        }
//
//        return jobConfiguration;
//    }

    public BackgroundJobConfiguration getJob(String name) {
        BackgroundJobConfiguration jobConfiguration = jobConfigurationsByName.get(name);

        if (jobConfiguration == null) {
            throw new BaseException("Undefined background job for the job name: " + name);
        }

        return jobConfiguration;
    }

//    public List<BackgroundJobConfiguration> getJobs() {
//        return new ArrayList<>(jobConfigurationsByArgsType.values());
//    }

    public void addJob(Class<?> jobType) {
        addJob(new BackgroundJobConfiguration(jobType));
    }

    public void addJob(BackgroundJobConfiguration jobConfiguration) {
//        jobConfigurationsByArgsType.put(jobConfiguration.getArgsType(), jobConfiguration);
        jobConfigurationsByName.put(jobConfiguration.getJobName(), jobConfiguration);
    }

    public boolean isJobExecutionEnabled() {
        return isJobExecutionEnabled;
    }

    public void setJobExecutionEnabled(boolean jobExecutionEnabled) {
        isJobExecutionEnabled = jobExecutionEnabled;
    }
}
