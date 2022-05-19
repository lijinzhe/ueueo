package com.ueueo.uow;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Lee
 * @date 2022-05-19 11:24
 */
public final class EventOrderGenerator {

    private static final AtomicLong LastOrder = new AtomicLong();

    public static long getNext() {
        return LastOrder.incrementAndGet();
    }
}
