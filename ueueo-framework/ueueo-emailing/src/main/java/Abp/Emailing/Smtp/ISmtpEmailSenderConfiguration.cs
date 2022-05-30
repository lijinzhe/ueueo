using System.Threading.Tasks;

namespace Volo.Abp.Emailing.Smtp;

/**
 * Defines configurations to used by SmtpClient object.
*/
public interface ISmtpEmailSenderConfiguration : IEmailSenderConfiguration
{
    /**
     * SMTP Host name/IP.
    */
    Task<String> GetHostAsync();

    /**
     * SMTP Port.
    */
    Task<int> GetPortAsync();

    /**
     * User name to login to SMTP server.
    */
    Task<String> GetUserNameAsync();

    /**
     * Password to login to SMTP server.
    */
    Task<String> GetPasswordAsync();

    /**
     * Domain name to login to SMTP server.
    */
    Task<String> GetDomainAsync();

    /**
     * Is SSL enabled?
    */
    Task<bool> GetEnableSslAsync();

    /**
     * Use default credentials?
    */
    Task<bool> GetUseDefaultCredentialsAsync();
}
