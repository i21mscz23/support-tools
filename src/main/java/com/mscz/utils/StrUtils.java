package com.mscz.utils;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description
 * @Author lixiao
 * @Date 2021/9/1 上午11:17
 */
public class StrUtils {

    /**
     * 根据字符串获推算出为哪一年
     * @param number 数字字符串
     * @return
     */
    public static Integer getYear(String number){


        if (StringUtils.length(number) == 2) {
            String nowYear = String.valueOf(LocalDate.now().getYear());
            String beforeAppend = nowYear.substring(0, 2);
            String afterAppend = number.substring(0, 2);

            String year = beforeAppend + afterAppend;
            if(Integer.parseInt(year) > Integer.parseInt(nowYear)){
                year = Integer.parseInt(beforeAppend) - 1 + afterAppend;
            }
            return Integer.parseInt(year);
        }

        if(StringUtils.length(number) == 4){
            return Integer.parseInt(number);
        }

        return null;
    }




}
