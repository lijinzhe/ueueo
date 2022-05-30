using System.Threading.Tasks;

namespace Volo.Abp.Ldap;

public interface ILdapSettingProvider
{
    public Task<String> GetServerHostAsync();

    public Task<int> GetServerPortAsync();

    public Task<String> GetBaseDcAsync();

    public Task<String> GetDomainAsync();

    public Task<String> GetUserNameAsync();

    public Task<String> GetPasswordAsync();
}
