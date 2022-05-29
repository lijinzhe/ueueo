package com.ueueo.exceptionhandling;

/**
 * @author Lee
 * @date 2022-05-29 12:46
 */
public class NullExceptionNotifier implements IExceptionNotifier {
    public static final NullExceptionNotifier INSTANCE = new NullExceptionNotifier();

    private NullExceptionNotifier() {
    }

    @Override
    public void notify(ExceptionNotificationContext context) {

    }
}
