package com.xmd.dto.page;

import com.xmd.utils.BeanCpyUtils;

import java.util.Map;

/**
 * @Description
 * @Author lixiao
 * @Date 2021/10/22 上午10:09
 */
public class PageConvert<C extends  PageDto> {

    /**
     * PageDto 转换成 Map
     * @param c
     * @return
     */
    public Map<String,Object> pageDto2Map(C c){
        if(c == null){
            return null;
        }
        Map<String, Object> map = BeanCpyUtils.beanToMap(c);
        Long currentPage = c.getCurrentPage();
        if(currentPage == null){
            map.put("currentPage",0L);
        }else {
            Long pageSize = c.getPageSize();
            Long size = (currentPage -1) * pageSize;
            map.put("currentPage",size);
        }


        return map;
    }
}
