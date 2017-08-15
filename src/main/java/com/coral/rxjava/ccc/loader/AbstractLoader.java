package com.coral.rxjava.ccc.loader;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by ccc on 2017/8/15.
 */
public class AbstractLoader {

    private List<ILoader> children = Lists.newArrayList();

    public void setChildren(List<ILoader> children) {
        this.children = children;
    }

    public List<ILoader> getChildren() {
        return children;
    }

    public void addChild(ILoader child) {
        this.children.add(child);
    }

}
