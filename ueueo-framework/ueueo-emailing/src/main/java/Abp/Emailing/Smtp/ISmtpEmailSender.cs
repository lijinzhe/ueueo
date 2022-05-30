using System.Net.Mail;
using System.Threading.Tasks;

namespace Volo.Abp.Emailing.Smtp;

/**
 * Used to send emails over SMTP.
*/
public interface ISmtpEmailSender : IEmailSender
{
    /**
     * Creates and configures new <see cref="SmtpClient"/> object to send emails.
    *
     * <returns>
     * An <see cref="SmtpClient"/> object that is ready to send emails.
     * </returns>
     */
    Task<SmtpClient> BuildClientAsync();
}
