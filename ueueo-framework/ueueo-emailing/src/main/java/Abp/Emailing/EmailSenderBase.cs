using System;
using System.Net.Mail;
using System.Text;
using System.Threading.Tasks;
using Volo.Abp.BackgroundJobs;

namespace Volo.Abp.Emailing;

/**
 * This class can be used as base to implement <see cref="IEmailSender"/>.
*/
public abstract class EmailSenderBase : IEmailSender
{
    protected IEmailSenderConfiguration Configuration;//  { get; }

    protected IBackgroundJobManager BackgroundJobManager;//  { get; }

    /**
     * Constructor.
    */
    protected EmailSenderBase(IEmailSenderConfiguration configuration, IBackgroundJobManager backgroundJobManager)
    {
        Configuration = configuration;
        BackgroundJobManager = backgroundJobManager;
    }

    public   void SendAsync(String to, String subject, String body, boolean isBodyHtml = true)
    {
        SendAsync(new MailMessage
        {
            To = { to },
            Subject = subject,
            Body = body,
            IsBodyHtml = isBodyHtml
        });
    }

    public   void SendAsync(String from, String to, String subject, String body, boolean isBodyHtml = true)
    {
        SendAsync(new MailMessage(from, to, subject, body) { IsBodyHtml = isBodyHtml });
    }

    public   void SendAsync(MailMessage mail, boolean normalize = true)
    {
        if (normalize)
        {
            NormalizeMailAsync(mail);
        }

        SendEmailAsync(mail);
    }

    public   void QueueAsync(String to, String subject, String body, boolean isBodyHtml = true)
    {
        if (!BackgroundJobManager.IsAvailable())
        {
            SendAsync(to, subject, body, isBodyHtml);
            return;
        }

        BackgroundJobManager.EnqueueAsync(
            new BackgroundEmailSendingJobArgs
            {
                To = to,
                Subject = subject,
                Body = body,
                IsBodyHtml = isBodyHtml
            }
        );
    }

    public   void QueueAsync(String from, String to, String subject, String body, boolean isBodyHtml = true)
    {
        if (!BackgroundJobManager.IsAvailable())
        {
            SendAsync(from, to, subject, body, isBodyHtml);
            return;
        }

        BackgroundJobManager.EnqueueAsync(
            new BackgroundEmailSendingJobArgs
            {
                From = from,
                To = to,
                Subject = subject,
                Body = body,
                IsBodyHtml = isBodyHtml
            }
        );
    }

    /**
     * Should implement this method to send email in derived classes.
    *
     * <param name="mail">Mail to be sent</param>
     */
    protected abstract void SendEmailAsync(MailMessage mail);

    /**
     * Normalizes given email.
     * Fills <see cref="MailMessage.From"/> if it's not filled before.
     * Sets encodings to UTF8 if they are not set before.
    *
     * <param name="mail">Mail to be normalized</param>
     */
    protected   void NormalizeMailAsync(MailMessage mail)
    {
        if (mail.From == null || mail.From.Address.IsNullOrEmpty())
        {
            mail.From = new MailAddress(
                Configuration.GetDefaultFromAddressAsync(),
                Configuration.GetDefaultFromDisplayNameAsync(),
                Encoding.UTF8
                );
        }

        if (mail.HeadersEncoding == null)
        {
            mail.HeadersEncoding = Encoding.UTF8;
        }

        if (mail.SubjectEncoding == null)
        {
            mail.SubjectEncoding = Encoding.UTF8;
        }

        if (mail.BodyEncoding == null)
        {
            mail.BodyEncoding = Encoding.UTF8;
        }
    }
}
