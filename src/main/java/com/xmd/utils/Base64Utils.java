package com.xmd.utils;

import java.util.Base64;

public class Base64Utils {


    /**
     * Base64 获取图片字符串
     * @param bytes
     * @return
     */
    public static String getImgStr(byte[] bytes){
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * Base64 转图片
     * @param imgStr
     * @return
     */
    public static byte[] getByte(String imgStr){
        return Base64.getDecoder().decode(imgStr);
    }

}
