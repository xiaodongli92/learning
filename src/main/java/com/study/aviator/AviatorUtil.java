package com.study.aviator;

import com.googlecode.aviator.AviatorEvaluator;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiaodong on 2017/6/22.
 * util
 */
public class AviatorUtil {

    private AviatorUtil() {}

    public static boolean startWith(String s) {
        return (Boolean) AviatorEvaluator.execute("string.startsWith('"+s+"','video_')");
    }
}
