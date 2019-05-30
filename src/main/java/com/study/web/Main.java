package com.study.web;

import java.io.IOException;

/**
 * Created by xiaodong on 2017/6/29.
 * 测试
 */
public class Main {

    public static void main(String[] args) throws IOException {
        String result = WebUtils.doPost("http://www.baidu.com", null);
        System.out.println(result);
    }
}
