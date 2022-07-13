package com.ueueo.eventbus.local;

import com.ueueo.eventbus.IEventHandler;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class AbpLocalEventBusOptions {
    private final List<? extends Class<? extends IEventHandler>> handlers;

    public AbpLocalEventBusOptions() {
        handlers = new ArrayList<>();
    }
}
