package com.xmd.utils;

import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.google.common.collect.Sets;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.jayway.jsonpath.JsonPath.using;

public class JsonUtils {

    private static final String FILTER_NAME = "fieldFilter";

    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 对象 转 map
     * @param object
     * @return
     */
    public static Map<String,Object> objectToMap(Object object){
//        Map<String,Object> map = new ObjectMapper().convertValue(object, new TypeReference<Map<String,Object>>(){});

        Map<String,Object> map = JSONUtil.parse(object).toBean(HashMap.class);
        return map;
    }





    /**
     * 字符串 转 集合
     * @param jsonData
     * @param beanType
     * @param <T>
     * @return
     */
    public static <T> List<T> jsonToList(String jsonData, Class<T> beanType) {
        JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, beanType);
        try {
            List<T> list = MAPPER.readValue(jsonData, javaType);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * 字符串 转 对象
     * @param json
     * @param beanType
     * @param <T>
     * @return
     */
    public static <T> T jsonToPojo(String json, Class<T> beanType) {
        try {
            T t = MAPPER.readValue(json, beanType);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 对象 转 字符串
     * @param data
     * @return
     */
    public static String objectToJson(Object data) {
        try {
            String string = MAPPER.writeValueAsString(data);
            return string;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 对象转字符串，排除指定属性
     * @param object
     * @param excludeProperties 过滤掉的属性
     * @return
     */
    public static String objectToJsonFilter(Object object, String... excludeProperties) {
        addFilterForMapper(MAPPER, object.getClass(), excludeProperties);
        try {
            return MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * 添加过滤的字段
     */
    private static void addFilterForMapper(ObjectMapper mapper, Class filterClass, String... excludeProperties) {
        if(excludeProperties == null){
            return;
        }

        SimpleBeanPropertyFilter fieldFilter = SimpleBeanPropertyFilter.serializeAllExcept(excludeProperties);
        SimpleFilterProvider filterProvider = new SimpleFilterProvider().addFilter(FILTER_NAME, fieldFilter);
        mapper.setFilterProvider(filterProvider).addMixIn(filterClass, FieldFilterMixIn.class);
    }

    @JsonFilter(FILTER_NAME)
    interface FieldFilterMixIn{
    }



}
