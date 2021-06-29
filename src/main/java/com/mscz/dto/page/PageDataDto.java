package com.mscz.dto.page;

/**
 * @Description 分页数据出参
 * @Author lixiao
 * @Date 2021/6/29 下午2:54
 */
public class PageDataDto<T> {

    private T dataList;

    private Long totalCount;

    public static <T> PageDataDto build(T t,Long count){
        PageDataDto dto = new PageDataDto<>();
        dto.setDataList(t);
        dto.setTotalCount(count);

        return dto;
    }
    public static <T> PageDataDto empty(){
        PageDataDto dto = new PageDataDto<>();
        dto.setTotalCount(0L);

        return dto;
    }

    public T getDataList() {
        return dataList;
    }

    public void setDataList(T dataList) {
        this.dataList = dataList;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }
}
