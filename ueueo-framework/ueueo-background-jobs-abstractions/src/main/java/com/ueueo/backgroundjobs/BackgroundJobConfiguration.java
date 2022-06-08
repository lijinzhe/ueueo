package com.ueueo.backgroundjobs;

public class BackgroundJobConfiguration {
    private Class<?> argsType;

    private Class<?> jobType;

    private String jobName;

    public BackgroundJobConfiguration(Class<?> jobType) {
        this.jobType = jobType;
        this.argsType = BackgroundJobArgsHelper.getJobArgsType(jobType);
        if (this.argsType != null) {
            BackgroundJobNameAttribute attribute = this.argsType.getAnnotation(BackgroundJobNameAttribute.class);
            this.jobName = attribute != null ? attribute.name() : "";
        }
    }

    public Class<?> getArgsType() {
        return argsType;
    }

    public Class<?> getJobType() {
        return jobType;
    }

    public String getJobName() {
        return jobName;
    }
}
