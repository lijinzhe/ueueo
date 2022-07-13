package com.ueueo.data.objectextending.modularity;

import lombok.Data;

/**
 * @author Lee
 * @date 2022-05-23 14:25
 */
@Data
public class ExtensionPropertyLookupConfiguration {
    private String url;

    /** Default value: "items". */
    public String ResultListPropertyName = "items";

    /** Default value: "text". */
    public String DisplayPropertyName = "text";

    /** Default value: "id". */
    public String ValuePropertyName = "id";

    /** Default value: "filter". */
    public String FilterParamName = "filter";
}
