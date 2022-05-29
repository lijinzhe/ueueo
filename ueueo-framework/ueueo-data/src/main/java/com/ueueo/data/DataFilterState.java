package com.ueueo.data;

import lombok.Data;

/**
 * @author Lee
 * @date 2022-05-29 14:48
 */
@Data
public class DataFilterState {
    private boolean isEnabled;

    public DataFilterState(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    @Override
    public DataFilterState clone() {
        return new DataFilterState(isEnabled);
    }
}
