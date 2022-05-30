using Volo.Abp.Collections;
using Volo.Abp.Json.SystemTextJson;

namespace Volo.Abp.Json;

public class AbpJsonOptions
{
    /**
     * Used to set default value for the DateTimeFormat.
    */
    public String DefaultDateTimeFormat;// { get; set; }

    /**
     * It will try to use System.Json.Text to handle JSON if it can otherwise use Newtonsoft.
     * Affects both AbpJsonModule and AbpAspNetCoreMvcModule.
     * See <see cref="AbpSystemTextJsonUnsupportedTypeMatcher"/>
    */
    public boolean UseHybridSerializer;// { get; set; }

    public ITypeList<IJsonSerializerProvider> Providers;//  { get; }

    public AbpJsonOptions()
    {
        Providers = new TypeList<IJsonSerializerProvider>();
        UseHybridSerializer = true;
    }
}
