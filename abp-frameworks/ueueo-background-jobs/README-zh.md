# 默认后台作业管理器

> 默认后台管理器(见下文)不支持多进程执行相同的作业队列. 所以, 如果你的应用程序中有多个正在运行的实现,并且使用的是默认的后台管理器, 你应该只在一个应用程序实例进程中启用作业队列.

ABP framework 包含一个简单的 `IBackgroundJobManager` 实现;

- 在**单线程**中**FIFO(先入先出)**.
- **重试**作业执行直到作业**执行成功**或**超时**. 默认作业超时时间是2天. 记录所有异常 .
- 作业执行成功时从存储中(数据库)**删除**作业. 如果超时, 作业会在数据库中被设置为**abandoned**.
- 作业的**重试等待时间会越来越长**. 作业第一次重试等待1分钟, 第二次重试等待2分钟, 第三次重试等待4分钟,以此类推.
- 以固定的时间间隔轮询存储中的作业. 查询作业, 按优先级排序(asc)然后按尝试次数排序(asc).

> `Volo.Abp.BackgroundJobs` nuget package 包含默认的后台作业管理器并且在默认在启动模板中已经安装.

## 配置

在你的[模块类](Module-Development-Basics.md)中使用 `AbpBackgroundJobWorkerOptions` 配置默认作业管理器.
示例中更改后台作业的的超时时间:

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

## 数据存储

默认的后台作业管理器需要数据存储用来保存和读取作业. 它将 `IBackgroundJobStore` 定义为抽象的. 所以, 如果你想要的话你可以替换它的实现.

后台作业模块使用各种数据访问提供程序实现 `IBackgroundJobStore`. 参阅 [后台工作模块文档](Modules/Background-Jobs.md).

> 默认情况下,后台作业模块已经安装到启动模板中,它基于你的ORM/数据访问选项.

## 另请参阅
* [后台工作者](Background-Workers.md)
