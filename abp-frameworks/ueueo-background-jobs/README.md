# Default Background Job Manager

> Default background manager (see below) does not support multiple processes execute the same job queue. So, if you have multiple running instance of your application and you are using the default background job manager, you should only enable one application instance process the job queue.

ABP framework includes a simple `IBackgroundJobManager` implementation that;

- Works as **FIFO** in a **single thread**.
- **Retries** job execution until the job **successfully runs** or **timeouts**. Default timeout is 2 days for a job. Logs all exceptions.
- **Deletes** a job from the store (database) when it's successfully executed. If it's timed out, it sets it as **abandoned** and leaves it in the database.
- **Increasingly waits between retries** for a job. It waits 1 minute for the first retry, 2 minutes for the second retry, 4 minutes for the third retry and so on.
- **Polls** the store for jobs in fixed intervals. It queries jobs, ordering by priority (asc) and then by try count (asc).

> `Volo.Abp.BackgroundJobs` nuget package contains the default background job manager and it is installed to the startup templates by default.

### Configuration

Use `AbpBackgroundJobWorkerOptions` in your [module class](Module-Development-Basics.md) to configure the default background job manager. The example below changes the timeout duration for background jobs:

````csharp
[DependsOn(typeof(AbpBackgroundJobsModule))]
public class MyModule : AbpModule
{
    public override void ConfigureServices(ServiceConfigurationContext context)
    {
        Configure<AbpBackgroundJobWorkerOptions>(options =>
        {
            options.DefaultTimeout = 864000; //10 days (as seconds)
        });
    }
}
````

### Data Store

The default background job manager needs a data store to save and read jobs. It defines `IBackgroundJobStore` as an abstraction. So, you can replace the implementation if you want.

Background Jobs module implements `IBackgroundJobStore` using various data access providers. See its own [documentation](Modules/Background-Jobs.md).

> Background Jobs module is already installed to the startup templates by default and it works based on your ORM/data access choice.

## See Also
* [Background Workers](Background-Workers.md)
