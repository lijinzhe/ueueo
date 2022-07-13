using System.Threading.Tasks;

namespace Volo.Abp.Ldap;

public interface ILdapSettingProvider
{
    public String> GetServerHostAsync();

    public int> GetServerPortAsync();

    public String> GetBaseDcAsync();

    public String> GetDomainAsync();

    public String> GetUserNameAsync();

    public String> GetPasswordAsync();
}
