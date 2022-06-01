package com.ueueo.multilingualobjects;

import java.util.Collection;

public interface IMultiLingualObject<TTranslation> extends IObjectTranslation {
    Collection<TTranslation> getTranslations();

    void setTranslations(Collection<TTranslation> translations);
}
