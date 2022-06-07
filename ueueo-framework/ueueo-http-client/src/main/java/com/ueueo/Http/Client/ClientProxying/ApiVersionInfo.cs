using System;

namespace Volo.Abp.Http.Client.ClientProxying;

public class ApiVersionInfo  //TODO: Rename to not conflict with api versioning apis
{
    public String BindingSource;//  { get; }
    public String Version;//  { get; }

    public ApiVersionInfo(String bindingSource, String version)
    {
        BindingSource = bindingSource;
        Version = version;
    }

    public boolean ShouldSendInQueryString()
    {
        //TODO: Constant! TODO: Other sources!
        return !BindingSource.IsIn("Path");
    }
}
