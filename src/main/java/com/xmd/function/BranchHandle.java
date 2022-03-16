package com.xmd.function;

/**
 * @Description
 * @Author lixiao
 * @Date 2022/3/16 上午10:13
 */
@FunctionalInterface
public interface BranchHandle {


    /**
     * 分支操作
     *
     * @param trueHandle 为true时要进行的操作
     * @param falseHandle 为false时要进行的操作
     * @return void
     **/
    void trueOrFalseHandle(Runnable trueHandle, Runnable falseHandle);
}
