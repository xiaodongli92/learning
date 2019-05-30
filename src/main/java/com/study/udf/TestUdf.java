package com.study.udf;

import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.hive.ql.exec.UDF;

/**
 * Created by xiaodong on 2017/6/22.
 * udf
 * add /path/to/***.jar
 * create temporary function test as 'package.name.TestUdf';
 *
 * select test(column) from table ;
 */
public class TestUdf extends UDF {

    public String evaluate(String str) {
        if (StringUtils.isBlank(str)) {
            return "";
        }
        return str.toLowerCase();
    }
}
