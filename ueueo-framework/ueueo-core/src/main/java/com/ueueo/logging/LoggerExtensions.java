package com.ueueo.logging;

import org.slf4j.Logger;
import org.slf4j.event.Level;

/**
 * @author Lee
 * @date 2022-05-25 22:37
 */
public class LoggerExtensions {

    public static void logWithLevel(Logger logger, Level level, String msg) {
        switch (level) {
            case DEBUG:
                logger.debug(msg);
                break;
            case INFO:
                logger.info(msg);
                break;
            case TRACE:
                logger.trace(msg);
                break;
            case WARN:
                logger.warn(msg);
                break;
            case ERROR:
                logger.error(msg);
                break;
            default:
                break;
        }
    }

    public static void logWithLevel(Logger logger, Level level, String format, Object arg) {
        switch (level) {
            case DEBUG:
                logger.debug(format, arg);
                break;
            case INFO:
                logger.info(format, arg);
                break;
            case TRACE:
                logger.trace(format, arg);
                break;
            case WARN:
                logger.warn(format, arg);
                break;
            case ERROR:
                logger.error(format, arg);
                break;
            default:
                break;
        }
    }

    public static void logWithLevel(Logger logger, Level level, String format, Object arg1, Object arg2) {
        switch (level) {
            case DEBUG:
                logger.debug(format, arg1, arg2);
                break;
            case INFO:
                logger.info(format, arg1, arg2);
                break;
            case TRACE:
                logger.trace(format, arg1, arg2);
                break;
            case WARN:
                logger.warn(format, arg1, arg2);
                break;
            case ERROR:
                logger.error(format, arg1, arg2);
                break;
            default:
                break;
        }
    }

    public static void logWithLevel(Logger logger, Level level, String format, Object... arguments) {
        switch (level) {
            case DEBUG:
                logger.debug(format, arguments);
                break;
            case INFO:
                logger.info(format, arguments);
                break;
            case TRACE:
                logger.trace(format, arguments);
                break;
            case WARN:
                logger.warn(format, arguments);
                break;
            case ERROR:
                logger.error(format, arguments);
                break;
            default:
                break;
        }
    }

    public static void logWithLevel(Logger logger, Level level, String msg, Throwable t) {
        switch (level) {
            case DEBUG:
                logger.debug(msg, t);
                break;
            case INFO:
                logger.info(msg, t);
                break;
            case TRACE:
                logger.trace(msg, t);
                break;
            case WARN:
                logger.warn(msg, t);
                break;
            case ERROR:
                logger.error(msg, t);
                break;
            default:
                break;
        }
    }
}
