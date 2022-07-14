package com.ueueo.threading;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

/**
 * 批处理队列
 *
 * @author Lee
 * @date 2019-02-20 11:37
 */
public class BatchQueue<T> {
    private final int batchSize;
    private final Consumer<List<T>> consumer;
    private final int timeoutMillis;

    private AtomicBoolean isLooping = new AtomicBoolean(false);
    private BlockingQueue<T> queue = new LinkedBlockingQueue<>();

    private AtomicLong startTime = new AtomicLong(System.currentTimeMillis());

    private ExecutorService executorService;

    public BatchQueue(int batchSize, int timeoutMillis, Consumer<List<T>> consumer) {
        this.batchSize = batchSize;
        this.timeoutMillis = timeoutMillis;
        this.consumer = consumer;
        this.executorService = new ThreadPoolExecutor(4, 8,
                0L, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(1000),
                new BatchQueueThreadFactory(),
                new ThreadPoolExecutor.DiscardPolicy());
    }

    public BatchQueue(int batchSize, Consumer<List<T>> consumer) {
        this(batchSize, 1000, consumer);
    }

    public boolean add(T t) {
        boolean result = queue.add(t);
        if (!isLooping.get() && result) {
            isLooping.set(true);
            startLoop();
        }
        return result;
    }

    public void completeAll() {
        while (!queue.isEmpty()) {
            drainToConsume();
        }
    }

    private void startLoop() {
        executorService.execute(() -> {
            startTime = new AtomicLong(System.currentTimeMillis());
            while (true) {
                long last = System.currentTimeMillis() - startTime.get();
                if (queue.size() >= batchSize || (!queue.isEmpty() && last > timeoutMillis)) {
                    drainToConsume();
                } else if (queue.isEmpty()) {
                    isLooping.set(false);
                    break;
                }
            }
        });
    }

    private void drainToConsume() {
        List<T> drained = new ArrayList<>();
        int num = queue.drainTo(drained, batchSize);
        if (num > 0) {
            consumer.accept(drained);
            startTime.set(System.currentTimeMillis());
        }
    }

    static class BatchQueueThreadFactory implements ThreadFactory {
        private static final AtomicInteger POOL_NUMBER = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        BatchQueueThreadFactory() {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() :
                    Thread.currentThread().getThreadGroup();
            namePrefix = "batch-queue-pool-" +
                    POOL_NUMBER.getAndIncrement() +
                    "-thread-";
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r,
                    namePrefix + threadNumber.getAndIncrement(),
                    0);
            if (t.isDaemon()) {
                t.setDaemon(false);
            }
            if (t.getPriority() != Thread.NORM_PRIORITY) {
                t.setPriority(Thread.NORM_PRIORITY);
            }
            return t;
        }
    }
}
