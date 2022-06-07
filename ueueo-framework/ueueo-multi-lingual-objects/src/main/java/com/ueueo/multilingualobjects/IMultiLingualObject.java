package com.ueueo.multilingualobjects;

import java.util.List;

public interface IMultiLingualObject<TTranslation> extends IObjectTranslation {
    List<TTranslation> getTranslations();

    void setTranslations(List<TTranslation> translations);
}
