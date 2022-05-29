package com.ueueo.backgroundjobs;

/**
 * Priority of a background job.
 *
 * @author Lee
 * @date 2022-05-29 18:18
 */
public enum BackgroundJobPriority {
    /*
     * Low.
     */
    Low(5),

    /*
     * Below normal.
     */
    BelowNormal(10),

    /*
     * Normal (default).
     */
    Normal(15),

    /*
     * Above normal.
     */
    AboveNormal(20),

    /*
     * High.
     */
    High(25);

    private int intValue;

    BackgroundJobPriority(int intValue) {
        this.intValue = intValue;
    }

    public int getIntValue() {
        return intValue;
    }
}
