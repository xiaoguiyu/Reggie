package com.xiaoyu.pojo.dto;

import com.xiaoyu.pojo.entity.Dish;
import com.xiaoyu.pojo.entity.DishFlavor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaoyu
 * @date 2022/10/10 21:15
 * dish dto包含了dish和dishFlavor 中的属性
 */


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
