package com.zw.mybatis.mapper;

import com.zw.mybatis.pojo.Car;
import org.apache.ibatis.annotations.MapKey;

import java.util.List;
import java.util.Map;

public interface CarMapper {
    Car selectById(Long id);

    Car selectByBrandLike(String brand);

    Map<String, Object> selectByIdRetMap(Long id);

    List<Map<String, Object>> selectByBrand(String brand);

    @MapKey("car_num")
    Map<String, Map<String, Object>> selectAllRetMap();

    List<Car> selectAllResultMap();
}
