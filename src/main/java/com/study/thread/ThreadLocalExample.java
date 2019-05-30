package com.study.thread;

import org.apache.commons.lang.StringUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by xiaodong36 on 2018/4/13.
 * ThreadLocal简单使用
 */
public class ThreadLocalExample {

    private ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) {
        //第一步： 验证ThreadLocal
        ExecutorService client = Executors.newFixedThreadPool(2);
        ThreadLocalExample example = new ThreadLocalExample();
        client.execute(() -> System.out.println(example.getLocalString(Thread.currentThread().getName())));
        client.execute(() -> System.out.println(example.getLocalString(Thread.currentThread().getName())));
        client.execute(() -> System.out.println(example.getLocalString(Thread.currentThread().getName())));
        client.execute(() -> System.out.println(example.getLocalString(Thread.currentThread().getName())));
        client.execute(() -> System.out.println(example.getLocalString(Thread.currentThread().getName())));
        client.execute(() -> System.out.println(example.getLocalString(Thread.currentThread().getName())));
        client.execute(() -> System.out.println(example.getLocalString(Thread.currentThread().getName())));
        client.execute(() -> System.out.println(example.getLocalString(Thread.currentThread().getName())));
    }

    public String getLocalString(String threadName) {
        String localStr = threadLocal.get();
        if (StringUtils.isBlank(localStr)) {
            threadLocal.set(threadName);
            localStr = threadName;
            System.out.println("***************set thread local var");
        }
        return localStr;
    }
}
