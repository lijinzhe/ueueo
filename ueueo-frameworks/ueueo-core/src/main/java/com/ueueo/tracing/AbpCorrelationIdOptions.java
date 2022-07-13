package com.ueueo.tracing;

/**
 * @author Lee
 * @date 2022-05-23 21:09
 */
public class AbpCorrelationIdOptions {
    public static String HttpHeaderName = "X-Correlation-Id";

    public static boolean SetResponseHeader = true;
}
