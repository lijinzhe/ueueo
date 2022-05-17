//package com.ueueo.logging;
//
//import org.slf4j.event.Level;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.function.BiFunction;
//
///**
// * @author Lee
// * @date 2022-05-14 13:23
// */
//public class DefaultInitLogger<T> implements IInitLogger<T> {
//
//    private final List<AbpInitLogEntry> entries;
//
//    public DefaultInitLogger() {
//        this.entries = new ArrayList<>();
//    }
//
//    @Override
//    public List<AbpInitLogEntry> getEntries() {
//        return entries;
//    }
//
//    @Override
//    public boolean getIsEnabled(Level logLevel) {
//        return logLevel !=null;
//    }
//
//    @Override
//    public <TState> void log(LogLevel logLevel, EventId eventId, TState state, Exception exception, BiFunction<TState, Exception, String> formatter) {
//        this.entries.add(new AbpInitLogEntry(logLevel, eventId, state, exception, (BiFunction<Object, Exception, String>) formatter));
//    }
//}
