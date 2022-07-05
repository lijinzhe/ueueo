package com.ueueo.backgroundjobs;

import com.ueueo.exception.BaseException;
import com.ueueo.exceptionhandling.ExceptionNotificationContext;
import com.ueueo.exceptionhandling.IExceptionNotifier;
import org.springframework.beans.BeansException;

import java.lang.reflect.Method;

public class BackgroundJobExecuter implements IBackgroundJobExecuter {

    protected AbpBackgroundJobOptions Options;//  { get; }

    public BackgroundJobExecuter(AbpBackgroundJobOptions options) {
        Options = options;

    }

    @Override
    public void execute(JobExecutionContext context) {
        Object job = null;
        try {
            job = context.getBeanFactory().getBean(context.getJobType());
        } catch (BeansException e) {
            throw new BaseException("The job type is not registered to DI: " + context.getJobType());
        }
        Method jobExecuteMethod = null;
        try {
            jobExecuteMethod = context.getJobType().getMethod("execute");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        if (jobExecuteMethod == null) {
            throw new BaseException("Given job type does not implement IBackgroundJob or IAsyncBackgroundJob. " +
                    "The job type was: " + context.getJobType().getName());
        }

        try {
            jobExecuteMethod.invoke(job, new Object[]{context.getJobArgs()});
        } catch (Exception ex) {
            ex.printStackTrace();
            IExceptionNotifier exceptionNotifier = context.getBeanFactory().getBean(IExceptionNotifier.class);
            exceptionNotifier.notify(new ExceptionNotificationContext(ex, null, false));

            throw new BackgroundJobExecutionException("A background job execution is failed. See inner exception for details.", ex) {{
                setJobType(context.getJobType().getName());
                setJobArgs(context.getJobArgs());
            }};
        }
    }
}
