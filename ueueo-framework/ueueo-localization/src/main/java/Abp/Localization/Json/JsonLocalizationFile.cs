using System.Collections.Generic;

namespace Volo.Abp.Localization.Json;

public class JsonLocalizationFile
{
    /**
     * Culture name; eg : en , en-us, zh-CN
    */
    public String Culture;// { get; set; }

    public Dictionary<String, String> Texts;// { get; set; }

    public JsonLocalizationFile()
    {
        Texts = new Dictionary<String, String>();
    }
}
