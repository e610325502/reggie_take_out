package com.itheima.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.itheima.reggie.entity.SetmealDish;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SetmealDishMapper extends BaseMapper<SetmealDish> {
}
