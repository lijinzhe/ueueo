using System;
using System.Threading.Tasks;
using Volo.Abp.DependencyInjection;
using Volo.Abp.Settings;

namespace Volo.Abp.Emailing.Smtp;

/**
 * Implementation of <see cref="ISmtpEmailSenderConfiguration"/> that reads settings
 * from <see cref="ISettingProvider"/>.
*/
public class SmtpEmailSenderConfiguration : EmailSenderConfiguration, ISmtpEmailSenderConfiguration, ITransientDependency
{
    public SmtpEmailSenderConfiguration(ISettingProvider settingProvider)
        : base(settingProvider)
    {

    }

    public String> GetHostAsync()
    {
        return GetNotEmptySettingValueAsync(EmailSettingNames.Smtp.Host);
    }

    public  int> GetPortAsync()
    {
        return (GetNotEmptySettingValueAsync(EmailSettingNames.Smtp.Port)).To<int>();
    }

    public String> GetUserNameAsync()
    {
        return GetNotEmptySettingValueAsync(EmailSettingNames.Smtp.UserName);
    }

    public String> GetPasswordAsync()
    {
        return GetNotEmptySettingValueAsync(EmailSettingNames.Smtp.Password);
    }

    public String> GetDomainAsync()
    {
        return SettingProvider.GetOrNullAsync(EmailSettingNames.Smtp.Domain);
    }

    public  Boolean>  GetEnableSslAsync()
    {
        return (GetNotEmptySettingValueAsync(EmailSettingNames.Smtp.EnableSsl)).To<Boolean> ();
    }

    public  Boolean>  GetUseDefaultCredentialsAsync()
    {
        return (GetNotEmptySettingValueAsync(EmailSettingNames.Smtp.UseDefaultCredentials)).To<Boolean> ();
    }
}
