using System.Threading.Tasks;

namespace Volo.Abp.Emailing;

/**
 * Defines configurations used while sending emails.
*/
public interface IEmailSenderConfiguration
{
    Task<String> GetDefaultFromAddressAsync();

    Task<String> GetDefaultFromDisplayNameAsync();
}
