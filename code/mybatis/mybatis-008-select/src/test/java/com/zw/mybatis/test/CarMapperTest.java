package com.zw.mybatis.test;

import com.zw.mybatis.mapper.CarMapper;
import com.zw.mybatis.pojo.Car;
import com.zw.mybatis.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

public class CarMapperTest {

    @Test
    public void testSelectById(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        Car car = mapper.selectById(2L);
        System.out.println(car);
        sqlSession.close();
    }

    @Test
    public void testSelectByBrandLike(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        Car car = mapper.selectByBrandLike("奔驰");
        System.out.println(car);
        sqlSession.close();
    }

    @Test
    public void testSelectByIdRetMap(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        Map<String, Object> map = mapper.selectByIdRetMap(2L);
        Set<Map.Entry<String, Object>> entries = map.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            String key = entry.getKey();
            Object value = entry.getValue();
            System.out.println(key + "=" + value);
        }
        sqlSession.close();
    }

    @Test
    public void testSelectByBrand(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        List<Map<String, Object>> mapList = mapper.selectByBrand("比亚迪汉");
        mapList.forEach(map -> {
            Set<Map.Entry<String, Object>> entries = map.entrySet();
            for (Map.Entry<String, Object> entry : entries) {
                String key = entry.getKey();
                Object value = entry.getValue();
                System.out.println(key + "=" + value);
            }
        });
        sqlSession.close();
        System.out.println("========================");
        System.out.println(mapList);
    }

    @Test
    public void testSelectAllRetMap(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        Map<String, Map<String, Object>> map = mapper.selectAllRetMap();
        map.forEach((key, value) -> {
            System.out.println(key+"="+value);
        });
        sqlSession.close();
        System.out.println("===============");
        System.out.println(map);
    }
}
