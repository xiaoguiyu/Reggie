package com.xiaoyu.pojo.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author 小鱼
 * @version 1.0
 * @date 2022/9/25 23:16
 * 员工实体类
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 用户名(唯一)
     */
    private String username;

    /**
     * 姓名
     */
    private String name;

    private String password;

    private String phone;

    private String sex;

    /**
     * 身份证号
     */
    private String idNumber;

    private Integer status;

    @TableField(fill = FieldFill.INSERT)  // 公共字段
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE) // 插入和更新数据时都会自动填充数据
    private LocalDateTime updateTime;

    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;

}
