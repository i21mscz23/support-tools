package com.xmd.function;

/**
 * @Description
 * @Author lixiao
 * @Date 2022/3/16 上午10:08
 */
public class FunctionUtils {


    public static ThrowExceptionFunction isTrue(boolean b){
        return (msg) -> {
            if(b) {
                throw new RuntimeException(msg);
            }
        };
    }

    public static BranchHandle isTureOrFalse(boolean b){

        return (trueHandle, falseHandle) -> {
            if (b){
                trueHandle.run();
            } else {
                falseHandle.run();
            }
        };
    }

    /**
     * 参数为true或false时，分别进行不同的操作
     *
     * @param str
     * @return
     **/
    public static PresentOrElseHandler<?> isBlankOrNoBlank(String str){

        return (consumer, runnable) -> {
            if (str == null || str.length() == 0){
                runnable.run();
            } else {
                consumer.accept(str);
            }
        };
    }


}
