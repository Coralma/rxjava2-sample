package com.coral.rxjava.ccc.loader;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;

/**
 * Created by ccc on 2017/8/14.
 */
public class LoadMain {

    public static void main(String[] args) {
        ILoader loader1 = new ClaimData1Loader();
        ILoader loader2 = new ClaimData2Loader();
        ILoader loader3 = new ClaimData3Loader();
        ILoader loader4 = new ClaimData4Loader();
        ILoader loader5 = new ClaimData5Loader();

        loader1.addChild(loader3);
        loader2.addChild(loader4);
        loader3.addChild(loader5);
        loader4.addChild(loader5);

        List<ILoader> loaders = Lists.newArrayList(loader1,loader2,loader3,loader4,loader5);
    }


    public static void printDependencyTree(List<ILoader> loaders) {

    }
}
