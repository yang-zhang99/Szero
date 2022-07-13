package com.ttdo.core.redis;

import org.springframework.util.CollectionUtils;

import java.util.ArrayDeque;
import java.util.Deque;

public class RedisDatabaseThreadLocal {

    private static final ThreadLocal<Deque<Integer>> THREAD_DB = new ThreadLocal();

    private RedisDatabaseThreadLocal() {
    }

    public static void set(int db) {
        Deque<Integer> deque = (Deque)THREAD_DB.get();
        if (deque == null) {
            deque = new ArrayDeque();
        }

        ((Deque)deque).addFirst(db);
        THREAD_DB.set(deque);
    }

    public static Integer get() {
        Deque<Integer> deque = (Deque)THREAD_DB.get();
        return CollectionUtils.isEmpty(deque) ? null : (Integer)deque.getFirst();
    }

    public static void clear() {
        Deque<Integer> deque = (Deque)THREAD_DB.get();
        if (deque != null && deque.size() > 1) {
            deque.removeFirst();
            THREAD_DB.set(deque);
        } else {
            THREAD_DB.remove();
        }
    }
}
