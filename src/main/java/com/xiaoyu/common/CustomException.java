package com.xiaoyu.common;

/**
 * @author 小鱼
 * @version 1.0
 * @date 2022/10/7 20:07
 * 自定义的业务异常类
 */

public class CustomException extends RuntimeException{

    public CustomException(String msg) {
        super(msg);
    }

}
