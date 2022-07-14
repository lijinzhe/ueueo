package com.ueueo.datamodel;

/**
 * 树形结构
 *
 * @author Lee
 * @date 2022-07-13 17:38
 */
public interface ITree {
    /**
     * 上级
     *
     * @return
     */
    String getParent();

    void setParent(String parent);

    /**
     * 层级
     *
     * @return
     */
    int getLevel();

    void setLevel(int level);

    /**
     * 排序
     *
     * @return
     */
    float getSort();

    void setSort(float level);

    /**
     * 路径
     *
     * @return
     */
    String getPath();

    /**
     * 是否叶子节点
     *
     * @return
     */
    boolean isLeaf();
}
