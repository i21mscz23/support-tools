package com.mscz.utils;



import com.tuyang.beanutils.config.BeanCopyConfig;
import com.tuyang.beanutils.internal.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author lixiao
 * @Date 2021/6/29 下午2:57
 */
public class BeanCopyUtils {

    private static final int BEAN_COPY_LOGLEVEL_INFO = Logger.LogLevelInfo;

    private static final int BEAN_COPY_LOGLEVEL_DEBUG = Logger.LogLevelDebug;

    public static <T> T copyBean(Object sourceObject, Class<T> targetClass) {
        BeanCopyConfig beanCopyConfig = new BeanCopyConfig();
        beanCopyConfig.setLogLevel(BEAN_COPY_LOGLEVEL_DEBUG);
        BeanCopyConfig.setBeanCopyConfig(beanCopyConfig);
        return  com.tuyang.beanutils.BeanCopyUtils.copyBean(sourceObject,targetClass);
    }


    public static <S, T> List<T> copyList(List<S> sourceList, Class<T> targetClass){
        BeanCopyConfig beanCopyConfig = new BeanCopyConfig();
        beanCopyConfig.setLogLevel(BEAN_COPY_LOGLEVEL_DEBUG);
        BeanCopyConfig.setBeanCopyConfig(beanCopyConfig);
        return com.tuyang.beanutils.BeanCopyUtils.copyList(sourceList,targetClass);
    }


}
