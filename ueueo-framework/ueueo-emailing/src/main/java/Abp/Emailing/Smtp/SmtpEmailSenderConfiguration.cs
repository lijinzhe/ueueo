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

    public Task<String> GetHostAsync()
    {
        return GetNotEmptySettingValueAsync(EmailSettingNames.Smtp.Host);
    }

    public  Task<int> GetPortAsync()
    {
        return (GetNotEmptySettingValueAsync(EmailSettingNames.Smtp.Port)).To<int>();
    }

    public Task<String> GetUserNameAsync()
    {
        return GetNotEmptySettingValueAsync(EmailSettingNames.Smtp.UserName);
    }

    public Task<String> GetPasswordAsync()
    {
        return GetNotEmptySettingValueAsync(EmailSettingNames.Smtp.Password);
    }

    public Task<String> GetDomainAsync()
    {
        return SettingProvider.GetOrNullAsync(EmailSettingNames.Smtp.Domain);
    }

    public  Task<bool> GetEnableSslAsync()
    {
        return (GetNotEmptySettingValueAsync(EmailSettingNames.Smtp.EnableSsl)).To<bool>();
    }

    public  Task<bool> GetUseDefaultCredentialsAsync()
    {
        return (GetNotEmptySettingValueAsync(EmailSettingNames.Smtp.UseDefaultCredentials)).To<bool>();
    }
}
