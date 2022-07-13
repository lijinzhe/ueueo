package com.ueueo.backgroundjobs;

/**
 * @author Lee
 * @date 2022-05-29 18:15
 */
public interface IBackgroundJobExecuter {
    void execute(JobExecutionContext context);
}
