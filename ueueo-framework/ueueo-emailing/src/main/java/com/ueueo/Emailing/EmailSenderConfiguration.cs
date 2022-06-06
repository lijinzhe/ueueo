using System;
using System.Threading.Tasks;
using Volo.Abp.Settings;

namespace Volo.Abp.Emailing;

/**
 * Base implementation of <see cref="IEmailSenderConfiguration"/> that reads settings
 * from <see cref="ISettingProvider"/>.
*/
public abstract class EmailSenderConfiguration : IEmailSenderConfiguration
{
    protected ISettingProvider SettingProvider;//  { get; }

    /**
     * Creates a new <see cref="EmailSenderConfiguration"/>.
    */
    protected EmailSenderConfiguration(ISettingProvider settingProvider)
    {
        SettingProvider = settingProvider;
    }

    public String> GetDefaultFromAddressAsync()
    {
        return GetNotEmptySettingValueAsync(EmailSettingNames.DefaultFromAddress);
    }

    public String> GetDefaultFromDisplayNameAsync()
    {
        return GetNotEmptySettingValueAsync(EmailSettingNames.DefaultFromDisplayName);
    }

    /**
     * Gets a setting value by checking. Throws <see cref="AbpException"/> if it's null or empty.
    *
     * <param name="name">Name of the setting</param>
     * <returns>Value of the setting</returns>
     */
    protected  String> GetNotEmptySettingValueAsync(String name)
    {
        var value = SettingProvider.GetOrNullAsync(name);

        if (value.IsNullOrEmpty())
        {
            throw new AbpException($"Setting value for '{name}' is null or empty!");
        }

        return value;
    }
}
