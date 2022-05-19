package com.ueueo.uow;

/**
 * @author Lee
 * @date 2022-05-19 20:36
 */
public enum IsolationLevel {
    Unspecified(-1),
    Chaos(16),
    ReadUncommitted(256),
    ReadCommitted(4096),
    RepeatableRead(65536),
    Serializable(1048576),
    Snapshot(16777216),
    ;

    private int intLevel;

    IsolationLevel(int intLevel) {
        this.intLevel = intLevel;
    }

    public int getIntLevel() {
        return intLevel;
    }
}
