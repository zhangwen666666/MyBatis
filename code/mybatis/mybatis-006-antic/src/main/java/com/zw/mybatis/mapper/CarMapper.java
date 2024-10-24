package com.zw.mybatis.mapper;

import com.zw.mybatis.pojo.Car;

import java.util.List;

public interface CarMapper {
    /**
     * 根据汽车类型获取汽车信息
     * @param CarType
     * @return
     */
    List<Car> selectByCarType(String CarType);

    /**
     * 查询所有的汽车信息，根据ascOrDesc来升序或降序排列
     * @param ascOrDesc
     * @return
     */
    List<Car> selectAllByAscOrDesc(String ascOrDesc);

    Car selectById(Long id);
}
