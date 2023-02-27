package com.xiaoyu.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @author 小鱼
 * @version 1.0
 * @date 2022/10/2 11:32
 * 全局的异常处理
 */

@ControllerAdvice(annotations = {RestController.class, Controller.class})
@RestController
@Slf4j
public class GlobalExceptionHandler {


    /**
     * 处理sql异常
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)  //捕获sql异常
    public Result<String> exceptionHandler(SQLIntegrityConstraintViolationException e) {

        log.error(e.getMessage());  // 输出信息

        if (e.getMessage().contains("Duplicate entry")) {

            String[] str = e.getMessage().split(" ");
            String msg = str[2] + "已存在, 添加用户失败!";
            return Result.error(msg);
        }
        return Result.error("未知的错误");
    }


    /**
     * 处理自定义的异常
     * @param e 自定义的异常
     * @return 返回异常的具体消息
     */
    @ExceptionHandler(CustomException.class)
    public Result<String> customExceptionHandler(CustomException e) {

        log.error(e.getMessage());  // 输出信息
        return Result.error(e.getMessage());
    }


}
