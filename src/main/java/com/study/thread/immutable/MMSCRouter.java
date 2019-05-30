package com.study.thread.immutable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 彩信中心路由规则管理器
 */
public final class MMSCRouter {

    //用volatile修饰,保证多线程环境下变量的可见性
    private static volatile MMSCRouter instance = new MMSCRouter();

    //维护手机号码前缀到彩信中心之间的映射关系
    private final Map<String, MMSCInfo> routeMap;

    public MMSCRouter() {
        this.routeMap = retrieveRouteMapFromDB();
    }

    private static Map<String, MMSCInfo> retrieveRouteMapFromDB() {
        Map<String, MMSCInfo> map = new HashMap<>();
        //todo 从数据库中加载数据到内存中
        return map;
    }

    public static MMSCRouter getInstance() {
        return instance;
    }

    /**
     * 根据手机信息获取对应的彩信中心信息
     * @param msisdnPrefix
     * @return 彩信中心信息
     */
    public MMSCInfo getMMSC(String msisdnPrefix) {
        return routeMap.get(msisdnPrefix);
    }

    public static void setInstance(MMSCRouter newInstance) {
        instance = newInstance;
    }

    /**
     * 做防御性复制
     * @return 只读的Map
     */
    public Map<String, MMSCInfo> getRouteMap() {
        return Collections.unmodifiableMap(deepCopy(routeMap));
    }

    private static Map<String, MMSCInfo> deepCopy(Map<String, MMSCInfo> map) {
        Map<String, MMSCInfo> result = new HashMap<>();
        for (Map.Entry<String, MMSCInfo> entry:map.entrySet()) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
}
