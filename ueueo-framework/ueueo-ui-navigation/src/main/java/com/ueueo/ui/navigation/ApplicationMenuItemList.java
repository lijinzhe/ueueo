package com.ueueo.ui.navigation;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ApplicationMenuItemList extends ArrayList<ApplicationMenuItem> {
    public ApplicationMenuItemList() {

    }

    public ApplicationMenuItemList(int capacity) {
        super(capacity);

    }

    public ApplicationMenuItemList(List<ApplicationMenuItem> collection) {
        super(collection);

    }

    public void normalize() {
        removeEmptyItems();
        order();
    }

    private void removeEmptyItems() {
        removeIf(item -> item.isLeaf() && StringUtils.isBlank(item.url));
    }

    private void order() {
        sort((o1, o2) -> o2.getOrder() - o1.getOrder());
    }
}
