package com.mscz.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * @Description
 * @Author lixiao
 * @Date 2021/6/29 下午11:00
 */
public class TimeUtils {

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
}
