package com.mscz.utils;

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


}
