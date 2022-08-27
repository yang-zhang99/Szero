package com.me.redis;

import org.springframework.util.CollectionUtils;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Redis Database ThreadLocal
 */
public class RedisDatabaseThreadLocal {

    /**
     * 当前线程池
     */
    private static final ThreadLocal<Deque<Integer>> THREAD_DB = new ThreadLocal();

    private RedisDatabaseThreadLocal() {
    }

    public static void set(int db) {
        Deque<Integer> deque = THREAD_DB.get();
        if (deque == null) {
            deque = new ArrayDeque<>();
        }
        deque.addFirst(db);
        THREAD_DB.set(deque);
    }

    public static Integer get() {
        Deque<Integer> deque = THREAD_DB.get();
        return CollectionUtils.isEmpty(deque) ? null : deque.getFirst();
    }

    public static void clear() {
        Deque<Integer> deque = THREAD_DB.get();
        if (deque != null && deque.size() > 1) {
            deque.removeFirst();
            THREAD_DB.set(deque);
        } else {
            THREAD_DB.remove();
        }
    }
}
