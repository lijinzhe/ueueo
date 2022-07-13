using System;
using System.Threading.Tasks;
using Volo.Abp.BackgroundJobs;
using Volo.Abp.DependencyInjection;

namespace Volo.Abp.Emailing;

public class BackgroundEmailSendingJob : AsyncBackgroundJob<BackgroundEmailSendingJobArgs>, ITransientDependency
{
    protected IEmailSender EmailSender;//  { get; }

    public BackgroundEmailSendingJob(IEmailSender emailSender)
    {
        EmailSender = emailSender;
    }

    @Override
    public void ExecuteAsync(BackgroundEmailSendingJobArgs args)
    {
        if (args.From.IsNullOrWhiteSpace())
        {
            EmailSender.SendAsync(args.To, args.Subject, args.Body, args.IsBodyHtml);
        }
        else
        {
            EmailSender.SendAsync(args.From, args.To, args.Subject, args.Body, args.IsBodyHtml);
        }
    }
}
