package com.xmd.utils;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description
 * @Author lixiao
 * @Date 2021/7/30 下午2:50
 */
public class RegexUtils {

    /**
     * 判断是否包含中文
     * @param str
     * @return
     */
    public static boolean isContainChinese(String str) {

        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    /**
     * 删除特殊字符
     * @param str
     * @return
     */
    public static String deleteSpecialCharacter(String str){
        String regEx="[`_~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    /**
     * 查询所有特殊符号的位置
     * @param str
     * @return
     */
    public static List<Integer> queryFirstSpecialCharacterInedex(String str){
        String regEx="[`_~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher slashMatcher = Pattern.compile(regEx).matcher(str);
        List<Integer> list = new ArrayList<>();
        while(slashMatcher.find()) {
            list.add(slashMatcher.start());
        }
        return list;
    }






    /**
     * 判断是否是整数
     * @param str
     * @return
     */
    public static boolean isInt(String str){
        return Pattern.compile("^-?[1-9]\\d*$").matcher(str).find();
    }

    /**
     * 判断是否是小数
     * @param str
     * @return
     */
    public static boolean isDouble(String str){
        return Pattern.compile("^-?([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0)$").matcher(str).find();
    }

    /**
     * 获取字符串中的所有数字
     * @param str
     * @return
     */
    public static String getNumber(String str){
        String regEx="[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    /**
     * 查询字符串中长度从2-4的数字（过长的会截去到4）
     * @param str
     * @return
     */
    public static List<String> queryNumberGroup(String str){
        List<String> list = new ArrayList<>();

        if(StringUtils.isBlank(str)){
            return list;
        }

        Pattern p = Pattern.compile("(\\d{2,4})");
        Matcher m = p.matcher(str);

        while(m.find()){
            list.add(m.group(1));
        }

        return list;
    }

    public static List<String> getTimes(String str){
        List<String> list = new ArrayList<>();
        if(StringUtils.isNotBlank(str)){
            String regex = "([0-9]{4}.[0-9]{2})";

            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(str);

            while(m.find()){
                list.add(m.group());
            }
        }


        return list;
    }

    /**
     * 只为数字
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        String regEx = "[0-9]{4}";
        Pattern pat = Pattern.compile(regEx);
        Matcher mat = pat.matcher(str);
        if (mat.find()) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * 查询符号第n次出现的位置
     * @param str 被匹配的字符
     * @param time 第n次
     * @param regex 符号
     * @return
     */
    public static int querySite(String str,int time,String regex){
        Pattern pattern = Pattern.compile(regex);
        Matcher findMatcher = pattern.matcher(str);
        int number = 0;
        while(findMatcher.find()) {
            number++;
            if(number == time){
                break;
            }
        }
        int i = findMatcher.start();
        return i;
    }

    /**
     *
     * @param timeStr
     * @return
     */
    public static LocalDateTime handerTime(String timeStr){
        LocalDateTime time = null;

        if(StringUtils.isNotBlank(timeStr)){
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(timeStr);

            //yyyy年MM月dd日
            if(timeStr.matches("\\d{4}年\\d{1,2}月\\d{1,2}日")){
                String month = StringUtils.substringBetween(timeStr, "年", "月");
                if(month.length() == 1){
                    stringBuilder.insert(5,"0");
                }
                String day = StringUtils.substringBetween(timeStr, "月", "日");
                if(day.length() == 1){
                    stringBuilder.insert(8,"0");
                }

                System.out.println(stringBuilder.toString());

                time = TimeUtils.convertToLocalDateTime(stringBuilder.toString(), "yyyy年MM月dd日");
            }else {
                String symbol = null;

                if (timeStr.matches("\\d{4}-\\d{1,2}-\\d{1,2}")) {
                    //yyyy-MM-dd
                    symbol = "-";
                }else if (timeStr.matches("\\d{4}/\\d{1,2}/\\d{1,2}")) {
                    //yyyy/MM/dd
                    symbol = "/";
                }

                int i = querySite(timeStr, 2, symbol);
                if(i == 6){
                    stringBuilder.insert(5,"0");
                }
                String dayValue = StringUtils.substringAfterLast(timeStr, symbol);
                if(StringUtils.length(dayValue) == 1){
                    stringBuilder.insert(8,"0");
                }
                System.out.println(stringBuilder.toString());
                time = TimeUtils.convertToLocalDateTime(stringBuilder.toString(), "yyyy"+symbol+"MM"+symbol+"dd");
            }
        }

        return time;
    }








}
