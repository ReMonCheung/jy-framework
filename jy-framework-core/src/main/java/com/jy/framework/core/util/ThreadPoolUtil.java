package com.jy.framework.core.util;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池工具类
 * @author zhanglj
 *
 */
public class ThreadPoolUtil {
	
	private static int corePoolSize = 30;
	private static int maximumPoolSize = 50;
	private static int keepAliveTime = 3;
	private static int queueSize = 150;
	private static ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, 
			TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(queueSize), new ThreadPoolExecutor.CallerRunsPolicy());;
	
	private ThreadPoolUtil(){}
	
	public static ThreadPoolExecutor getTaskExecutor(){ 
		return executor; 
	}
	/**
	 * 添加一个任务到线程池
	 * @param runnable
	 */
	public static void  execute(Runnable runnable) {
		getTaskExecutor().execute(runnable);
	}
	
	public static <T> Future<T> submit(Callable<T> task) {
		return getTaskExecutor().submit(task);
	}
	/**
	 * 关闭线程池
	 */
	public static void shutdown() {
		if (executor != null) {
			executor.shutdown();
		}
	}
}
