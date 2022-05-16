package com.ueueo.core.threading;

import org.springframework.util.Assert;

import java.util.Locale;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Lee
 * @date 2022-05-16 16:44
 */
public class AsyncTaskExecutor {
    private final ExecutorService executorService;

    public AsyncTaskExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime,
                             TimeUnit unit, int workQueueCapacity, String threadFactoryNameFormat) {
        this.executorService = new ThreadPoolExecutor(
                corePoolSize, maximumPoolSize, keepAliveTime, unit,
                new LinkedBlockingDeque<>(workQueueCapacity),
                new ThreadFactoryBuilder().setNameFormat(threadFactoryNameFormat).build(),
                new ThreadPoolExecutor.AbortPolicy()
        );
    }

    public Future<?> submit(Runnable task) {
        return executorService.submit(task);
    }

    public <T> Future<T> submit(Callable<T> task) {
        return executorService.submit(task);
    }

    public final static class ThreadFactoryBuilder {
        private String nameFormat = null;
        private Boolean daemon = null;
        private Integer priority = null;
        private Thread.UncaughtExceptionHandler uncaughtExceptionHandler = null;
        private ThreadFactory backingThreadFactory = null;

        public ThreadFactoryBuilder() {
        }

        public ThreadFactoryBuilder setNameFormat(String nameFormat) {
            this.nameFormat = nameFormat;
            return this;
        }

        public ThreadFactoryBuilder setDaemon(boolean daemon) {
            this.daemon = daemon;
            return this;
        }

        public ThreadFactoryBuilder setPriority(int priority) {
            Assert.isTrue(priority >= 1, String.format("Thread priority (%s) must be >= %s", priority, 1));
            Assert.isTrue(priority <= 10, String.format("Thread priority (%s) must be <= %s", priority, 10));
            this.priority = priority;
            return this;
        }

        public ThreadFactoryBuilder setUncaughtExceptionHandler(Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
            Assert.notNull(uncaughtExceptionHandler, "uncaughtExceptionHandler must not null.");
            this.uncaughtExceptionHandler = uncaughtExceptionHandler;
            return this;
        }

        public ThreadFactoryBuilder setThreadFactory(ThreadFactory backingThreadFactory) {
            Assert.notNull(uncaughtExceptionHandler, "backingThreadFactory must not null.");
            this.backingThreadFactory = backingThreadFactory;
            return this;
        }

        public ThreadFactory build() {
            return doBuild(this);
        }

        private static ThreadFactory doBuild(ThreadFactoryBuilder builder) {
            final String nameFormat = builder.nameFormat;
            final Boolean daemon = builder.daemon;
            final Integer priority = builder.priority;
            final Thread.UncaughtExceptionHandler uncaughtExceptionHandler = builder.uncaughtExceptionHandler;
            final ThreadFactory backingThreadFactory = builder.backingThreadFactory != null ? builder.backingThreadFactory : Executors.defaultThreadFactory();
            final AtomicLong count = nameFormat != null ? new AtomicLong(0L) : null;
            return new ThreadFactory() {

                @Override
                public Thread newThread(Runnable runnable) {
                    Thread thread = backingThreadFactory.newThread(runnable);
                    if (nameFormat != null) {
                        thread.setName(ThreadFactoryBuilder.format(nameFormat, count.getAndIncrement()));
                    }

                    if (daemon != null) {
                        thread.setDaemon(daemon);
                    }

                    if (priority != null) {
                        thread.setPriority(priority);
                    }

                    if (uncaughtExceptionHandler != null) {
                        thread.setUncaughtExceptionHandler(uncaughtExceptionHandler);
                    }

                    return thread;
                }
            };
        }

        private static String format(String format, Object... args) {
            return String.format(Locale.ROOT, format, args);
        }
    }
}
