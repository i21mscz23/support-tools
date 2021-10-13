package com.xmd.tree;

import java.util.List;

/**
 * @Description
 * @Author lixiao
 * @Date 2021/4/25 下午2:25
 */
public abstract class Node {

    private String id;
    private String parentId;
    private String name;
    protected List<?> children;


    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<?> getChildren() {
        return children;
    }
    public abstract void setChildren(List<?> children);

}
