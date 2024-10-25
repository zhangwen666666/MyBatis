package com.zw.mybatis.test;

import com.zw.mybatis.mapper.CarMapper;
import com.zw.mybatis.pojo.Car;
import com.zw.mybatis.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class CarMapperTest {
    @Test
    public void testSelectByMultiCondition(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        List<Car> cars = mapper.selectByMultiCondition("丰田霸道",null,null);
        cars.forEach(System.out::println);
        sqlSession.close();
    }

    @Test
    public void testSelectWithChoose(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        List<Car> cars = mapper.selectWithChoose("",null,"");
        cars.forEach(System.out::println);
        sqlSession.close();
    }
}

