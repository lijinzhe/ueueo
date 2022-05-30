using System;
using System.Threading.Tasks;
using Volo.Abp.DependencyInjection;
using Volo.Abp.Settings;

namespace Volo.Abp.Ldap;

public class LdapSettingProvider : ILdapSettingProvider, ITransientDependency
{
    protected ISettingProvider SettingProvider;//  { get; }

    public LdapSettingProvider(ISettingProvider settingProvider)
    {
        SettingProvider = settingProvider;
    }

    public  Task<String> GetServerHostAsync()
    {
        return SettingProvider.GetOrNullAsync(LdapSettingNames.ServerHost);
    }

    public  Task<int> GetServerPortAsync()
    {
        return (SettingProvider.GetOrNullAsync(LdapSettingNames.ServerPort))?.To<int>() ?? default;
    }

    public  Task<String> GetBaseDcAsync()
    {
        return SettingProvider.GetOrNullAsync(LdapSettingNames.BaseDc);
    }

    public  Task<String> GetDomainAsync()
    {
        return SettingProvider.GetOrNullAsync(LdapSettingNames.Domain);
    }

    public  Task<String> GetUserNameAsync()
    {
        return SettingProvider.GetOrNullAsync(LdapSettingNames.UserName);
    }

    public  Task<String> GetPasswordAsync()
    {
        return SettingProvider.GetOrNullAsync(LdapSettingNames.Password);
    }
}
