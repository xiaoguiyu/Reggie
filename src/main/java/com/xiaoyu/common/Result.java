package com.xiaoyu.common;

import lombok.Data;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 小鱼
 * @version 1.0
 * @date 2022/9/25 23:16
 * 处理后台请求结果
 */

@Data
@SuppressWarnings("all")
public class Result<T> {

    private Integer code; //编码：1成功，0和其它数字为失败

    private String msg; //错误信息

    private T data; //数据

    private Map map = new HashMap(); //动态数据

    public static <T> Result<T> success(T object) {
        Result<T> res = new Result<T>();
        res.data = object;
        res.code = 1;
        return res;
    }

    public static <T> Result<T> error(String msg) {
        Result res = new Result();
        res.msg = msg;
        res.code = 0;
        return res;
    }

    public Result<T> add(String key, Object value) {
        this.map.put(key, value);
        return this;
    }

}
