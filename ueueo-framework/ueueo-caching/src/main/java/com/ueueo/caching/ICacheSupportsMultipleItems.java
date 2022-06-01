package com.ueueo.caching;

import com.ueueo.KeyValuePair;

import java.util.Collection;

public interface ICacheSupportsMultipleItems {
    byte[][] getMany(Collection<String> keys);

    void setMany(Collection<KeyValuePair<String, byte[]>> items, DistributedCacheEntryOptions options);

    void refreshMany(Collection<String> keys);

    void removeMany(Collection<String> keys);

}
