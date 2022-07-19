package com.ueueo.datamodels.management;

import com.ueueo.datamodels.definition.DataModelDefinition;
import com.ueueo.datamodels.meta.DataModelMeta;
import org.springframework.lang.NonNull;

/**
 * @author Lee
 * @date 2022-07-14 17:41
 */
public interface IDataModelManagementProvider {
    String getName();

    DataModelMeta getOrNull(@NonNull DataModelDefinition model, String providerKey);

    void set(@NonNull DataModelDefinition model, @NonNull DataModelMeta meta, String providerKey);

    void clear(@NonNull DataModelDefinition model, String providerKey);
}
