using System.Collections.Generic;

namespace Volo.Abp.Http.Client;

public class RemoteServiceConfiguration : Dictionary<String, String>
{
    /**
     * Base Url.
    */
    public String BaseUrl {
        get => this.GetOrDefault(nameof(BaseUrl));
        set => this[nameof(BaseUrl)] = value;
    }

    /**
     * Version.
    */
    public String Version {
        get => this.GetOrDefault(nameof(Version));
        set => this[nameof(Version)] = value;
    }

    public RemoteServiceConfiguration()
    {

    }

    public RemoteServiceConfiguration(String baseUrl, String version = null)
    {
        this[nameof(BaseUrl)] = baseUrl;
        this[nameof(Version)] = version;
    }
}
