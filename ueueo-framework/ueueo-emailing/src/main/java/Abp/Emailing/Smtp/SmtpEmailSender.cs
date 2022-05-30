using System;
using System.Net;
using System.Net.Mail;
using System.Threading.Tasks;
using Volo.Abp.BackgroundJobs;
using Volo.Abp.DependencyInjection;

namespace Volo.Abp.Emailing.Smtp;

/**
 * Used to send emails over SMTP.
*/
public class SmtpEmailSender : EmailSenderBase, ISmtpEmailSender, ITransientDependency
{
    protected ISmtpEmailSenderConfiguration SmtpConfiguration;//  { get; }

    /**
     * Creates a new <see cref="SmtpEmailSender"/>.
    */
    public SmtpEmailSender(
        ISmtpEmailSenderConfiguration smtpConfiguration,
        IBackgroundJobManager backgroundJobManager)
        : base(smtpConfiguration, backgroundJobManager)
    {
        SmtpConfiguration = smtpConfiguration;
    }

    public  Task<SmtpClient> BuildClientAsync()
    {
        var host = SmtpConfiguration.GetHostAsync();
        var port = SmtpConfiguration.GetPortAsync();

        var smtpClient = new SmtpClient(host, port);

        try
        {
            if (SmtpConfiguration.GetEnableSslAsync())
            {
                smtpClient.EnableSsl = true;
            }

            if (SmtpConfiguration.GetUseDefaultCredentialsAsync())
            {
                smtpClient.UseDefaultCredentials = true;
            }
            else
            {
                smtpClient.UseDefaultCredentials = false;

                var userName = SmtpConfiguration.GetUserNameAsync();
                if (!userName.IsNullOrEmpty())
                {
                    var password = SmtpConfiguration.GetPasswordAsync();
                    var domain = SmtpConfiguration.GetDomainAsync();
                    smtpClient.Credentials = !domain.IsNullOrEmpty()
                        ? new NetworkCredential(userName, password, domain)
                        : new NetworkCredential(userName, password);
                }
            }

            return smtpClient;
        }
        catch
        {
            smtpClient.Dispose();
            throw;
        }
    }

    protected override void SendEmailAsync(MailMessage mail)
    {
        using (var smtpClient = BuildClientAsync())
        {
            smtpClient.SendMailAsync(mail);
        }
    }
}
