package com.xmd.tree;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author lixiao
 * @Date 2021/4/25 下午2:29
 */
public class TreeBuilder<V extends Node> {
    List<V> nodes = new ArrayList<>();
    public String buildTree(List<V> nodes) throws JsonProcessingException {
        TreeBuilder<V> treeBuilder = new TreeBuilder<V>(nodes);
        return treeBuilder.buildJSONTree();
    }

    public List<V> buildTreeList(List<V> nodes) throws JsonProcessingException {
        TreeBuilder<V> treeBuilder = new TreeBuilder<V>(nodes);
        List<V> vs = treeBuilder.buildTree();
        return vs;

    }
    public TreeBuilder() {
    }
    public TreeBuilder(List<V> nodes) {
        super();
        this.nodes = nodes;
    }
    // 构建JSON树形结构
    public String buildJSONTree() throws JsonProcessingException {
        List<V> nodeTree = buildTree();
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(nodeTree);
    }
    // 构建树形结构
    public List<V> buildTree() {
        List<V> treeNodes = new ArrayList<>();
        List<V> rootNodes = getRootNodes();
        for (V rootNode : rootNodes) {
            buildChildNodes(rootNode);
            treeNodes.add(rootNode);
        }

        if(treeNodes != null && treeNodes.size() != 0){
            return treeNodes.stream().filter(f-> (StringUtils.isBlank(f.getParentId()) || f.getParentId().equals("0") || f.getParentId().equals("-1"))).collect(Collectors.toList());
        }else {
            return treeNodes;
        }
    }
    // 递归子节点
    public void buildChildNodes(V node) {
        List<V> children = getChildNodes(node);
        if (!children.isEmpty()) {
            for (V child : children) {
                buildChildNodes(child);
            }
            node.setChildren(children);
        }
    }
    // 获取父节点下所有的子节点
    public List<V> getChildNodes(V pnode) {
        List<V> childNodes = new ArrayList<>();
        for (V n : nodes) {
            if (pnode.getId().equals(n.getParentId())) {
                childNodes.add(n);
            }
        }
        return childNodes;
    }
    // 判断是否为根节点
    public boolean rootNode(V node) {
        boolean isRootNode = true;
        for (V n : nodes) {
            if (node.getParentId().equals(n.getId())) {
                isRootNode = false;
                break;
            }
        }
        return isRootNode;
    }
    // 获取集合中所有的根节点
    public List<V> getRootNodes() {
        List<V> rootNodes = new ArrayList<>();
        for (V n : nodes) {
            if (rootNode(n)) {
                rootNodes.add(n);
            }
        }
        return rootNodes;
    }
}