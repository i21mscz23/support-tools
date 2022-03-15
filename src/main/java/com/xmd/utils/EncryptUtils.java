package com.xmd.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.Key;
import java.util.Base64;

/**
 * @Description 加解密处理
 * @Author lixiao
 * @Date 2022/3/10 下午2:37
 */
public class EncryptUtils {

    // 加解密统一使用的编码方式
    private final static String ENCODING_UTF8 = "utf-8";


    /**
     * （1）3DES密钥的长度必须是8的倍数，可取24位或32位；
     * （2）加密结果的byte数组转换为字符串，一般采用两种方式：Base64处理或十六进制处理。
     */
    /**
     * 3DES加密
     * 模式： CBC
     * 填充：PKCS5Padding
     *
     * @param key 密钥
     * @param lv 偏移量
     * @param data 加密数据
     * @return
     * @throws Exception
     */
    public static String encrypt3DESCBC(String key,String lv,String data)throws Exception{
        DESedeKeySpec desKeySpec = new DESedeKeySpec(key.getBytes(ENCODING_UTF8));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);

        // --加密向量
        IvParameterSpec iv = new IvParameterSpec(lv.getBytes(ENCODING_UTF8));

        // --通过Chipher执行加密得到的是一个byte的数组，Cipher.getInstance("DES")就是采用ECB模式，cipher.init(Cipher.ENCRYPT_MODE, secretKey)即可
        Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
        byte[] b = cipher.doFinal(data.getBytes("UTF-8"));

        // --通过base64，将加密数组转换成字符串
        return Base64.getEncoder().encodeToString(b);

    }

    /**
     * 3DES解密
     * 模式： CBC
     * 填充：PKCS5Padding
     *
     * @param key 密钥
     * @param lv 偏移量
     * @param data 加密数据
     * @return
     * @throws Exception
     */
    public static String decrypt3DESCBC(String key,String lv,String data) throws Exception {
        // --通过base64，将字符串转成byte数组
        byte[] bytesrc = Base64.getDecoder().decode(data);

        // --解密的key
        DESedeKeySpec dks = new DESedeKeySpec(key.getBytes(ENCODING_UTF8));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
        Key securekey = keyFactory.generateSecret(dks);

        // --Chipher对象解密
        final Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        IvParameterSpec ips = new IvParameterSpec(lv.getBytes(ENCODING_UTF8));
        cipher.init(Cipher.DECRYPT_MODE, securekey,ips);
        final byte[] retByte = cipher.doFinal(bytesrc);

        return new String(retByte);
    }




}
