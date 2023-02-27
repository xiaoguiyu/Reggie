package com.xiaoyu.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 菜品及套餐分类 实体类
 */
@TableName(value ="category")
@Data
public class Category implements Serializable {

    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 类型   1 菜品分类 2 套餐分类
     */
    private Integer type;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 顺序
     */
    private Integer sort;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)  // 公共字段
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE) // 插入和更新数据时都会自动填充数据
    private LocalDateTime updateTime;

    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    /**
     * 修改人
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;

    @TableField(exist = false)  // 当前属性不是数据库的字段，但在项目中必须使用。
    private static final long serialVersionUID = 1L;
}