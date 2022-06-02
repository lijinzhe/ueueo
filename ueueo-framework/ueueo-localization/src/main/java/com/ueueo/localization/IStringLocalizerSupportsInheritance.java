package com.ueueo.localization;

import java.util.List;

public interface IStringLocalizerSupportsInheritance {
    List<LocalizedString> getAllStrings(boolean includeBaseLocalizers);
}
