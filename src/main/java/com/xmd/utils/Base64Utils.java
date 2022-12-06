package com.xmd.utils;

import com.google.common.base.Joiner;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Sets;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;

import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

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

    /**
     * 字符串加密
     * @param content
     * @return
     */
    public static String encode(String content){
        return Base64Utils.getImgStr(content.getBytes());
    }





}
