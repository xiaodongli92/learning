package com.study.aviator;

import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorDouble;
import com.googlecode.aviator.runtime.type.AviatorObject;

import java.util.Map;

/**
 * Created by xiaodong on 2017/6/22.
 * 自定义function
 */
public class MyAviatorFunction extends AbstractFunction {

    @Override
    public AviatorObject call(Map<String, Object> env, AviatorObject arg1, AviatorObject arg2) {
        Number one = FunctionUtils.getNumberValue(arg1, env);
        Number another = FunctionUtils.getNumberValue(arg2, env);
        return new AviatorDouble(one.doubleValue() + another.doubleValue());
    }

    @Override
    public String getName() {
        return "add";
    }
}
