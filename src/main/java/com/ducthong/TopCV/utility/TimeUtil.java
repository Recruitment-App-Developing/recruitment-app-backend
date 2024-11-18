package com.ducthong.TopCV.utility;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

import com.ducthong.TopCV.constant.TimeFormatConstant;
import com.ducthong.TopCV.exceptions.AppException;

public class TimeUtil {
    private static final DateTimeFormatter DATETIME_FORMATTER =
            DateTimeFormatter.ofPattern(TimeFormatConstant.DATETIME_FORMAT);

    public static LocalDateTime getDateTimeNow() {
        return LocalDateTime.now();
    }

    public static LocalDateTime convertToDateTime(String timeStr) {
        return LocalDateTime.parse(timeStr, DATETIME_FORMATTER);
    }
    public static LocalDateTime convertToDate(String timeStr) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TimeFormatConstant.DATE_FORMAT);
            return LocalDate.parse(timeStr, formatter).atStartOfDay();
        } catch (Exception e) {
            throw new AppException("Thời gian không hợp lệ");
        }
    }
    public static LocalDateTime monthYearToDate(String timeStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TimeFormatConstant.MONTH_YEAR);
        YearMonth yearMonth = YearMonth.parse(timeStr, formatter);
        LocalDateTime dateTime = yearMonth.atDay(1).atStartOfDay();
        return dateTime;
    }

    public static String toMonthYear(LocalDateTime dateTime) {
        if (dateTime == null) return null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TimeFormatConstant.MONTH_YEAR);
        return  dateTime.format(formatter);
    }

    public static String toStringDateTime(LocalDateTime dateTime) {
        if (dateTime == null) return null;
        return dateTime.format(DATETIME_FORMATTER);
    }
    public static String toStringDate(LocalDateTime date) {
        if (date == null) return null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TimeFormatConstant.DATE_FORMAT);
        return  date.format(formatter).toString();
    }
    public static String toStringFullDateTime(LocalDateTime dateTime) {
        if (dateTime == null) return null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TimeFormatConstant.FULL_DATETIME_FORMAT);
        return  dateTime.format(formatter).toString();
    }

    public static int getHoursDifference(LocalDateTime startTime, LocalDateTime endTime) {
        Duration duration = Duration.between(startTime, endTime);
        System.out.println(duration.toMinutes());
        Double hours = duration.toMinutes() / 60.0;
        return (int) Math.round(hours);
    }
}
