using System.Threading.Tasks;
using JetBrains.Annotations;

namespace Volo.Abp.Http.Client;

public interface IRemoteServiceConfigurationProvider
{
    [ItemNotNull]
    RemoteServiceConfiguration> GetConfigurationOrDefaultAsync(String name);

    [ItemCanBeNull]
    RemoteServiceConfiguration> GetConfigurationOrDefaultOrNullAsync(String name);
}
