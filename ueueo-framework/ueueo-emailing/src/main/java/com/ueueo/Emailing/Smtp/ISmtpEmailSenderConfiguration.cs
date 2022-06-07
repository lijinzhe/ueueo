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
    String> GetHostAsync();

    /**
     * SMTP Port.
    */
    int> GetPortAsync();

    /**
     * User name to login to SMTP server.
    */
    String> GetUserNameAsync();

    /**
     * Password to login to SMTP server.
    */
    String> GetPasswordAsync();

    /**
     * Domain name to login to SMTP server.
    */
    String> GetDomainAsync();

    /**
     * Is SSL enabled?
    */
    Boolean>  GetEnableSslAsync();

    /**
     * Use default credentials?
    */
    Boolean>  GetUseDefaultCredentialsAsync();
}
