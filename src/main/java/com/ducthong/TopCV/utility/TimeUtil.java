package com.ducthong.TopCV.utility;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.ducthong.TopCV.constant.TimeFormatConstant;
import com.ducthong.TopCV.exceptions.AppException;
import lombok.extern.log4j.Log4j2;

@Log4j2
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
            log.error("Thời gian không hợp lệ");
            return null;
        }
    }

    public static LocalDateTime convertStringToDoB(String timeStr) {
        if (StringUtils.isNullOrEmpty(timeStr)) return null;
        List<DateTimeFormatter> formatters = List.of(
                DateTimeFormatter.ofPattern("dd-MM-yyyy"),
                DateTimeFormatter.ofPattern("dd/MM/yyyy")
        );
        for (DateTimeFormatter formatter : formatters) {
            try {
                return LocalDate.parse(timeStr, formatter).atStartOfDay();
            } catch (Exception ignored) {
            }
        }
        log.error("[ERROR_COMMON] Error convert string to Date of birth: {}", timeStr);
        return null;
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
        return dateTime.format(formatter);
    }

    public static String toStringDateTime(LocalDateTime dateTime) {
        if (dateTime == null) return null;
        return dateTime.format(DATETIME_FORMATTER);
    }

    public static String toStringDate(LocalDateTime date) {
        if (date == null) return null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TimeFormatConstant.DATE_FORMAT);
        return date.format(formatter).toString();
    }

    public static String toStringFullDateTime(LocalDateTime dateTime) {
        if (dateTime == null) return null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TimeFormatConstant.FULL_DATETIME_FORMAT);
        return dateTime.format(formatter).toString();
    }

    public static int getHoursDifferenceNow(LocalDateTime applyTime) {
        if (applyTime == null) return 0;
        LocalDateTime now = LocalDateTime.now();
        if (applyTime.isBefore(now)) return 0;

        Duration duration = Duration.between(applyTime, now);
        return (int) duration.toHours();
    }

    public static int getHoursDifferenceUpdate(LocalDateTime updateTime) {
        if (updateTime == null) return 0;
        LocalDateTime now = LocalDateTime.now();

        Duration duration = Duration.between(updateTime, now);
        return (int) duration.toHours();
    }
}
