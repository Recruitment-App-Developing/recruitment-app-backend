package com.ducthong.TopCV.utility;

import com.ducthong.TopCV.constant.TimeFormatConstant;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeUtil {
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern(TimeFormatConstant.DATETIME_FORMAT);

    public static LocalDateTime getDateTimeNow(){
        return LocalDateTime.now();
    }

    public static LocalDateTime convertToDateTime(String timeStr) {
        return LocalDateTime.parse(timeStr, DATETIME_FORMATTER);
    }

    public static String toStringDateTime(LocalDateTime dateTime) {
        if (dateTime == null) return null;
        return dateTime.format(DATETIME_FORMATTER);
    }

    public static int getHoursDifference(LocalDateTime startTime, LocalDateTime endTime) {
        Duration duration = Duration.between(startTime, endTime);
        System.out.println(duration.toMinutes());
        Double hours = duration.toMinutes() / 60.0;
        return (int) Math.round(hours);
    }
}
