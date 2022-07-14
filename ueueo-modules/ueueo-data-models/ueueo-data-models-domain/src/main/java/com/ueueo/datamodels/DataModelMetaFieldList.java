package com.ueueo.datamodels;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class DataModelMetaFieldList extends ArrayList<DataModelMetaField> {
    public DataModelMetaFieldList() {

    }

    public DataModelMetaFieldList(int capacity) {
        super(capacity);

    }

    public DataModelMetaFieldList(List<DataModelMetaField> collection) {
        super(collection);

    }

    public void normalize() {
        removeEmptyItems();
        order();
    }

    private void removeEmptyItems() {
        removeIf(item -> StringUtils.isBlank(item.getType()));
    }

    private void order() {
        sort((o1, o2) -> o2.getOrder() - o1.getOrder());
    }
}
