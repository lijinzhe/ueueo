using Microsoft.Extensions.Logging;

namespace Volo.Abp.ObjectExtending.Modularity;

public class ExtensionPropertyLookupConfiguration
{
    public String Url;// { get; set; }

    /**
     * Default value: "items".
    */
    public String ResultListPropertyName;// { get; set; } = "items";

    /**
     * Default value: "text".
    */
    public String DisplayPropertyName;// { get; set; } = "text";

    /**
     * Default value: "id".
    */
    public String ValuePropertyName;// { get; set; } = "id";

    /**
     * Default value: "filter".
    */
    public String FilterParamName;// { get; set; } = "filter";
}
