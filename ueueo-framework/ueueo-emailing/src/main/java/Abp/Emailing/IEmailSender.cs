using System.Net.Mail;
using System.Threading.Tasks;

namespace Volo.Abp.Emailing;

/**
 * This service can be used simply sending emails.
*/
public interface IEmailSender
{
    /**
     * Sends an email.
    */
    void SendAsync(
        String to,
        String subject,
        String body,
        boolean isBodyHtml = true
    );

    /**
     * Sends an email.
    */
    void SendAsync(
        String from,
        String to,
        String subject,
        String body,
        boolean isBodyHtml = true
    );

    /**
     * Sends an email.
    *
     * <param name="mail">Mail to be sent</param>
     * <param name="normalize">
     * Should normalize email?
     * If true, it sets sender address/name if it's not set before and makes mail encoding UTF-8.
     * </param>
     */
    void SendAsync(
        MailMessage mail,
        boolean normalize = true
    );

    /**
     * Adds an email to queue to send via background jobs.
    */
    void QueueAsync(
        String to,
        String subject,
        String body,
        boolean isBodyHtml = true
    );

    /**
     * Adds an email to queue to send via background jobs.
    */
    void QueueAsync(
        String from,
        String to,
        String subject,
        String body,
        boolean isBodyHtml = true
    );

    //TODO: Add other Queue methods too. Problem: MailMessage is not serializable so can not be used in background jobs.
}
