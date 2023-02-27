package com.xiaoyu.common;

/**
 * @author 小鱼
 * @version 1.0
 * @date 2022/10/6 16:00
 * 基于ThreadLocal 的工具类
 */

public class BaseContext {

    private static final ThreadLocal<Long> threadLocal = new ThreadLocal<>();


    /**
     * 保存当前登录用户的id
     * @param id 传递的id
     */
    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }

    /**
     * 获取当前登录用户的id
     * @return id
     */
    public static Long getCurrentId() {
        return threadLocal.get();
    }

}
