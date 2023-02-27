package com.xiaoyu.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 套餐
 */
@TableName(value ="setmeal")
@Data
public class Setmeal implements Serializable {
    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 菜品分类id
     */
    private Long categoryId;

    /**
     * 套餐名称
     */
    private String name;

    /**
     * 套餐价格
     */
    private BigDecimal price;

    /**
     * 状态 0:停用 1:启用
     */
    private Integer status;

    /**
     * 编码
     */
    private String code;

    /**
     * 描述信息
     */
    private String description;

    /**
     * 图片
     */
    private String image;

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
    private Integer isDeleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}