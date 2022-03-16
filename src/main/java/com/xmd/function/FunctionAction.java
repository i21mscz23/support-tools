package com.xmd.function;

/**
 * @Description
 * @Author lixiao
 * @Date 2022/3/16 上午11:20
 */
public class FunctionAction {

    public static void main(String[] args) {

        //1
//        isTrue(false).throwMessage("异常");

        //2
//        String name = Thread.currentThread().getName();
//        System.out.println(name);
//
//        isTureOrFalse(false).trueOrFalseHandle( () -> {
//            System.out.println("true");
//            String name1 = Thread.currentThread().getName();
//            System.out.println(name1);
//        },() ->{
//            System.out.println("false");
//            String name1 = Thread.currentThread().getName();
//            System.out.println(name1);
//        });

        //3
//        isBlankOrNoBlank("").presentOrElseHandle(System.out::println,() -> System.out.println("空字符串"));
    }
}
