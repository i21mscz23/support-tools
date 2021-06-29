package com.mscz.dto.page;

/**
 * @Description 分页入参
 * @Author lixiao
 * @Date 2021/6/29 下午2:53
 */
public class PageDto {


    private Long currentPage = 1L;

    private Long pageSize = 10L;

    private String condition;


    public Long getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Long currentPage) {
        this.currentPage = currentPage;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}
