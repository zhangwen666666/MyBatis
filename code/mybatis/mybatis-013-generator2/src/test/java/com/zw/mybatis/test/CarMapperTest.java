package com.zw.mybatis.test;

import com.zw.mybatis.mapper.CarMapper;
import com.zw.mybatis.pojo.Car;
import com.zw.mybatis.pojo.CarExample;
import com.zw.mybatis.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

public class CarMapperTest {
    @Test
    public void testSelect(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        // 查询一个
        Car car = mapper.selectByPrimaryKey(2L);
        // System.out.println(car);
        // 查询所有
        List<Car> cars = mapper.selectByExample(null);
        // cars.forEach(System.out::println);
        // 按照条件查询
        // 1. 封装条件，通过CarExample对象来封装查询条件
        CarExample carExample = new CarExample();
        // 2. 调用carExample.createCriteria()来创建查询条件
        carExample.createCriteria().andBrandLike("%丰田%").andGuidePriceGreaterThan(new BigDecimal(20));
        // 继续添加or条件
        carExample.or().andCarTypeEqualTo("电车");
        // 执行查询
        List<Car> carList = mapper.selectByExample(carExample);
        carList.forEach(System.out::println);
        sqlSession.close();
    }
}
