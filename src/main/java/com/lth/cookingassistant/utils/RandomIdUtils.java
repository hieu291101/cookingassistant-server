package com.lth.cookingassistant.utils;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class RandomIdUtils {
    private static final AtomicLong atomicCounter = new AtomicLong();

    public static Long createId() {
        Long currentCounter = atomicCounter.getAndIncrement();
        return currentCounter;
    }

    public static String createUUID() {
        String uuid = UUID.randomUUID().toString();
        return uuid;
    }
}
