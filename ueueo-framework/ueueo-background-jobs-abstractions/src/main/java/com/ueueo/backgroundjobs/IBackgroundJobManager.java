package com.ueueo.backgroundjobs;

import com.ueueo.ID;
import com.ueueo.dynamicproxy.ProxyHelper;

import java.util.concurrent.TimeUnit;

/**
 * Defines interface of a job manager.
 *
 * @author Lee
 * @date 2022-05-29 18:15
 */
public interface IBackgroundJobManager {
    /**
     * Enqueues a job to be executed.
     *
     * @param args          Job arguments.
     * @param priority      Job priority. default Normal
     * @param delay         Job delay (wait duration before first try).
     * @param delayTimeUnit 时间单位
     *
     * @return
     */
    ID enqueue(String jobName, Object args, BackgroundJobPriority priority, Integer delay, TimeUnit delayTimeUnit);

    class Extensions {
        /**
         * Checks if background job system has a real implementation.
         * It returns false if the current implementation is <see cref="NullBackgroundJobManager"/>.
         *
         * <param name="backgroundJobManager"></param>
         * <returns></returns>
         */
        public static boolean isAvailable(IBackgroundJobManager backgroundJobManager) {
            return !(ProxyHelper.unProxy(backgroundJobManager) instanceof NullBackgroundJobManager);
        }
    }
}
