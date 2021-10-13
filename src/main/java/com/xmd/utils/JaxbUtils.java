package com.xmd.utils;

import cn.hutool.core.util.StrUtil;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description
 * @Author lixiao
 * @Date 2021/6/30 下午9:57
 */
public class JaxbUtils {

    /**
     * jaxb协议对象转换为xml字符串
     * @param pojo
     * @param classes
     * @return
     */
    public static String pojo2XmlStr(Object pojo,Class...classes ) throws JAXBException {
        StringWriter sw = new StringWriter();
        JAXBContext jaxbContext = JAXBContext.newInstance(classes);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);

        marshaller.marshal(pojo, sw);
        String result = sw.toString();
        return JaxbUtils.replaceXsi(result);

    }



    public static <T> T xmlString2Pojo(String str,Class<T>  classes) throws JAXBException {
        StringReader reader = null;
        T unmarshal = null;
        if(StrUtil.isNotBlank(str)) {
            JAXBContext jaxbContext = JAXBContext.newInstance(classes);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            reader = new StringReader(str);
            unmarshal = (T) unmarshaller.unmarshal(reader);
        }

        return unmarshal;
    }

    /**
     * 通过正则以什么开头，以什么结尾去除 <Info xsi:type="suIDDTO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"> 节点中的msi的信息
     * @param content
     * @return
     */
    public static String replaceXsi(String content){
        String regEx = " xsi:type=.+?xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(content);
        return matcher.replaceAll("").trim();
    }
}
