package com.xiaoyu.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 菜品口味关系表
 */
@TableName(value ="dish_flavor")
@Data
public class DishFlavor implements Serializable {
    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 菜品
     */
    private Long dishId;

    /**
     * 口味名称
     */
    private String name;

    /**
     * 口味数据list
     */
    private String value;

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

    /**
     * 是否删除
     */
    //@TableLogic // 逻辑删除字段
    private Integer isDeleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}