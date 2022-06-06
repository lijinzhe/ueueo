using System.Threading.Tasks;

namespace Volo.Abp.Emailing;

/**
 * Defines configurations used while sending emails.
*/
public interface IEmailSenderConfiguration
{
    String> GetDefaultFromAddressAsync();

    String> GetDefaultFromDisplayNameAsync();
}
