package com.study.aviator;

import com.googlecode.aviator.AviatorEvaluator;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiaodong on 2017/6/22.
 * test main
 */
public class Main {

    public static void main(String[] args) {
        AviatorEvaluator.addFunction(new MyAviatorFunction());
        System.out.println(AviatorEvaluator.execute("add(1, 2)"));

        System.out.println(AviatorUtil.startWith("a_video_a"));

        Map<String,Object> map = new HashMap<>();
        map.put("name", "world");
        System.out.println(AviatorEvaluator.execute("'hello ' + name", map));
        System.out.println(AviatorEvaluator.execute("$name==world1", map));
    }
}
