package com.xmd.function;

/**
 * @Description
 * @Author lixiao
 * @Date 2022/3/16 上午10:07
 */
@FunctionalInterface
public interface ThrowExceptionFunction {

    /**
     * 抛出异常信息
     *
     * @param message 异常信息
     * @return void
     **/
    void throwMessage(String message);
}
