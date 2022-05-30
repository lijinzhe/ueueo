using System;
using System.Threading.Tasks;
using LdapForNet;
using LdapForNet.Native;
using Microsoft.Extensions.Logging;
using Microsoft.Extensions.Logging.Abstractions;
using Volo.Abp.DependencyInjection;

namespace Volo.Abp.Ldap;

public class LdapManager : ILdapManager, ITransientDependency
{
    public ILogger<LdapManager> Logger;// { get; set; }
    protected ILdapSettingProvider LdapSettingProvider;//  { get; }

    public LdapManager(ILdapSettingProvider ldapSettingProvider)
    {
        LdapSettingProvider = ldapSettingProvider;
        Logger = NullLogger<LdapManager>.Instance;
    }

    public    Task<bool> AuthenticateAsync(String username, String password)
    {
        try
        {
            using (var conn = CreateLdapConnectionAsync())
            {
                AuthenticateLdapConnectionAsync(conn, username, password);
                return true;
            }
        }
        catch (Exception ex)
        {
            Logger.LogException(ex);
            return false;
        }
    }

    protected    Task<ILdapConnection> CreateLdapConnectionAsync()
    {
        var ldapConnection = new LdapConnection();
        ConfigureLdapConnectionAsync(ldapConnection);
        ConnectAsync(ldapConnection);
        return ldapConnection;
    }

    protected void ConfigureLdapConnectionAsync(ILdapConnection ldapConnection)
    {
        return Task.CompletedTask;
    }

    protected   void ConnectAsync(ILdapConnection ldapConnection)
    {
        ldapConnection.Connect(LdapSettingProvider.GetServerHostAsync(), LdapSettingProvider.GetServerPortAsync());
    }

    protected   void AuthenticateLdapConnectionAsync(ILdapConnection connection, String username, String password)
    {
        connection.BindAsync(Native.LdapAuthType.Simple, new LdapCredential()
        {
            UserName = username,
            Password = password
        });
    }
}
