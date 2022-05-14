package com.ueueo.localization;

import lombok.Data;

/**
 * TODO ABP
 * A locale specific string.
 *
 * @author Lee
 * @date 2021-09-13 21:31
 */
@Data
public class LocalizedString {
    /**
     * The name of the string in the resource it was loaded from.
     */
    private String name;
    /**
     * The actual string.
     */
    private String value;
    /**
     * Whether the string was not found in a resource.
     * If true, an alternate string value was used.
     */
    private boolean resourceNotFound;
    /**
     * The location which was searched for a localization value.
     */
    private String searchedLocation;

    public LocalizedString(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public LocalizedString(String name, String value, boolean resourceNotFound) {
        this.name = name;
        this.value = value;
        this.resourceNotFound = resourceNotFound;
    }

    public LocalizedString(String name, String value, boolean resourceNotFound, String searchedLocation) {
        this.name = name;
        this.value = value;
        this.resourceNotFound = resourceNotFound;
        this.searchedLocation = searchedLocation;
    }
}
