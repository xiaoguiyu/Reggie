package com.xiaoyu.pojo.dto;


import com.xiaoyu.pojo.entity.Setmeal;
import com.xiaoyu.pojo.entity.SetmealDish;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.List;

/**
 * @author 小鱼
 * @version 1.0
 * @date 2022/10/13 22:17
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
