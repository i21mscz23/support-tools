package com.mscz.utils;

import com.google.common.io.ByteStreams;

import java.io.*;

/**
 * @Description
 * @Author lixiao
 * @Date 2021/6/30 下午9:50
 */
public class IoUtils {

    /**
     * 文件转换成byte[]
     * @param file
     * @return
     * @throws IOException
     */
    public static byte[] file2Byte(File file) throws IOException {
        byte[] bytesArray = new byte[(int) file.length()];

        FileInputStream fis = new FileInputStream(file);
        fis.read(bytesArray);
        fis.close();

        return bytesArray;
    }

    /**
     * InputStream 转 byte[]
     * @param is
     * @return
     * @throws IOException
     */
    public static byte[] is2Byte(InputStream is) throws IOException {
        byte[] targetArray = ByteStreams.toByteArray(is);

        return targetArray;
    }

    /**
     * byte[]转is
     * @param bytes
     * @return
     */
    public static InputStream byte2Is(byte[] bytes){

        InputStream is = new ByteArrayInputStream(bytes);
        return is;
    }
}
