package com.ueueo.bunding;

/**
 * @author Lee
 * @date 2022-05-29 11:29
 */
public interface IBundleContributor {
    void addScripts(BundleContext context);

    void addStyles(BundleContext context);
}
