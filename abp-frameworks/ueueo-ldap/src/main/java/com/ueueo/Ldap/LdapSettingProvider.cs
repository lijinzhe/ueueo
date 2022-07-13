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

    public  String> GetServerHostAsync()
    {
        return SettingProvider.GetOrNullAsync(LdapSettingNames.ServerHost);
    }

    public  int> GetServerPortAsync()
    {
        return (SettingProvider.GetOrNullAsync(LdapSettingNames.ServerPort))?.To<int>() ?? default;
    }

    public  String> GetBaseDcAsync()
    {
        return SettingProvider.GetOrNullAsync(LdapSettingNames.BaseDc);
    }

    public  String> GetDomainAsync()
    {
        return SettingProvider.GetOrNullAsync(LdapSettingNames.Domain);
    }

    public  String> GetUserNameAsync()
    {
        return SettingProvider.GetOrNullAsync(LdapSettingNames.UserName);
    }

    public  String> GetPasswordAsync()
    {
        return SettingProvider.GetOrNullAsync(LdapSettingNames.Password);
    }
}
