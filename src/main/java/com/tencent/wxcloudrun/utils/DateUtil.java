package com.tencent.wxcloudrun.utils;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class DateUtil {
    public static LocalDateTime dateToTime(String time) {
        return LocalDateTime.parse(time, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public static String TimeToPeriodName(String timeStr) {
        LocalTime time = LocalTime.parse(timeStr);
        int hours = time.getHour();
        if (hours > 18) {
            return "晚上";
        }
        if (hours > 12) {
            return "下午";
        }
        return "上午";
    }
}
