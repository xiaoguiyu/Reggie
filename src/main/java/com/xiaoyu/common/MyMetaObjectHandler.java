package com.xiaoyu.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author 小鱼
 * @version 1.0
 * @date 2022/10/6 15:35
 * 自定义元数据对象处理器
 */

@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {

    /**
     * 插入数据时自动填充公共字段
     * @param metaObject 执行sql语句前传递的元数据
     */
    @Override
    public void insertFill(MetaObject metaObject) {

        log.info("自动填充公共字段[insert]");
        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("createUser", BaseContext.getCurrentId());
        metaObject.setValue("updateUser", BaseContext.getCurrentId());
    }


    /**
     * 更新数据时, 自动填充公共字段
     * @param metaObject 执行sql语句前传递的元数据
     */
    @Override
    public void updateFill(MetaObject metaObject) {

        log.info("自动填充公共字段[update]");
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("updateUser", BaseContext.getCurrentId());
    }
}
