//package com.ueueo.logging;
//
//import lombok.AllArgsConstructor;
//import org.slf4j.event.Level;
//
//import java.util.function.BiFunction;
//
///**
// * @author Lee
// * @date 2022-05-14 11:38
// */
//@AllArgsConstructor
//public class AbpInitLogEntry {
//
//    private Level logLevel;
//    private EventId eventId;
//    private Object state;
//    private Exception exception;
//    private BiFunction<Object, Exception, String> formatter;
//
//    public String message(Object state, Exception exception) {
//        return formatter.apply(state, exception);
//    }
//}
