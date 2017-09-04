package com.coral.rxjava.ccc.loader;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Random;

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

    public void randomSleep() {
        try {
            Thread.sleep(randomNumber(100, 300));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int randomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }
}
