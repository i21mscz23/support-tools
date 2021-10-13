package com.xmd.utils;

import org.apache.commons.lang3.StringUtils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * @Description
 * @Author lixiao
 * @Date 2021/6/29 下午11:00
 */
public class TimeUtils {

    public static final String LOCALDATETIME_YYDD = "yyyy.MM[[.dd][ HH][:mm][:ss]]";

    /**
     * Date->LocalDateTime
     * @param date
     * @return
     */
    public static LocalDateTime convertToLocalDateTime(Date date){
        if(date == null){
            return null;
        }

        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();

        LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
        return localDateTime;
    }

    /**
     * LocalDateTime->Date
     * @param localDateTime
     * @return
     */
    public static Date convertToDate(LocalDateTime localDateTime){
        if(localDateTime == null){
            return null;
        }
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);

        Date date = Date.from(zdt.toInstant());
        return date;
    }




    /**
     * 字符串传转LocalDateTime(对字符串有严格要求。如月日必须是2位数)
     * @param date
     * @param timeFormatter
     * @return
     */
    public static LocalDateTime convertToLocalDateTime(String date,String timeFormatter){
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern(timeFormatter)
                .parseDefaulting(ChronoField.MONTH_OF_YEAR, 1)
                .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
                .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                .parseDefaulting(ChronoField.MILLI_OF_SECOND, 0)
                .toFormatter();
        LocalDateTime localDateTime = LocalDateTime.parse(date, formatter);
        return localDateTime;
    }


    /**
     * 距离当前时间多少年
     * @param time
     * @return
     */
    public static Long distanceTime(LocalDateTime time){
        LocalDateTime now = LocalDateTime.now();

        LocalDateTime from = LocalDateTime.from(time);
        long until = from.until(now, ChronoUnit.YEARS);
        return until;
    }

    /**
     * 转换成成指定格式字符串
     * @param time
     * @param pattern
     * @return
     */
    public static String getTimeString(LocalDateTime time,String pattern){
        if(time == null){
            return null;
        }

        return DateTimeFormatter.ofPattern(pattern).format(time);
    }


    /**
     * 时间戳(毫秒)转换成LocalDateTime
     * @param time
     * @return
     */
    public static LocalDateTime getLocalDateTime(Long time){
        LocalDateTime dateTime = LocalDateTime.ofEpochSecond(time / 1000,0, ZoneOffset.ofHours(8));
        return dateTime;
    }







}
