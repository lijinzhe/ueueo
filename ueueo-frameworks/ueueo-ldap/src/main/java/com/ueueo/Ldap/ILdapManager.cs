using System.Threading.Tasks;

namespace Volo.Abp.Ldap;

public interface ILdapManager
{
    Boolean>  AuthenticateAsync(String username, String password);
}
