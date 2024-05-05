package com.tencent.wxcloudrun.utils;

import java.util.UUID;

public class UserIdGenerator {
    public static long generateUserId() {
        UUID uuid = UUID.randomUUID();
        return uuid.getMostSignificantBits() & Long.MAX_VALUE;
    }
}