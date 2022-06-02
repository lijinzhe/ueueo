package com.ueueo.multilingualobjects;

public interface IMultiLingualObjectManager {

    <TTranslation extends IObjectTranslation> TTranslation getTranslation(
            IMultiLingualObject<TTranslation> multiLingual,
            String culture
    );
}
