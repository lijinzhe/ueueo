package com.ueueo.backgroundjobs;

public class BackgroundJobConfiguration {

    private Class<?> jobType;

    private String jobName;

    public BackgroundJobConfiguration(Class<?> jobType) {
        this.jobType = jobType;
        BackgroundJobNameAttribute attribute = this.jobType.getAnnotation(BackgroundJobNameAttribute.class);
        this.jobName = attribute != null ? attribute.name() : this.jobType.getName();
    }

    public Class<?> getJobType() {
        return jobType;
    }

    public String getJobName() {
        return jobName;
    }
}
