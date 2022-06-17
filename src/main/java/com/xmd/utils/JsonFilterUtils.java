package com.xmd.utils;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

public class JsonFilterUtils {

    public static String serialize(Object object, String... excludeProperties) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        addFilterForMapper(mapper, object.getClass(), excludeProperties);
        return mapper.writeValueAsString(object);
    }

    /**
     * 添加过滤的字段
     */
    private static void addFilterForMapper(ObjectMapper mapper, Class filterClass, String... excludeProperties) {
        SimpleBeanPropertyFilter fieldFilter = SimpleBeanPropertyFilter.serializeAllExcept(excludeProperties);
        SimpleFilterProvider filterProvider = new SimpleFilterProvider().addFilter("fieldFilter", fieldFilter);
        mapper.setFilterProvider(filterProvider).addMixIn(filterClass, FieldFilterMixIn.class);
    }

    /**
     * 定义一个类或接口
     */
    @JsonFilter("fieldFilter")
    interface FieldFilterMixIn{
    }


}
