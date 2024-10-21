package com.zw.mybatis.test;

import com.zw.mybatis.pojo.Car;
import com.zw.mybatis.util.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarMapperTest {

    @Test
    public void testDelete() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        int count = sqlSession.delete("deleteById", 17);
        System.out.println(count);
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void testInsertByPojo() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        Car car = new Car(null, "333", "比亚迪秦", 10.0, "2008-10-11", "新能源");
        int count = sqlSession.insert("insertCar", car);
        System.out.println(count);
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void testInsertCar() {
        SqlSession sqlSession = SqlSessionUtil.openSession();

        // 这个对象我们先试用Map集合进行数据的封装
/*        Map<String,Object> map = new HashMap<>();
        map.put("k1", "1111");
        map.put("k2", "比亚迪汉");
        map.put("k3", 10.0);
        map.put("k4", "2020-11-11");
        map.put("k5", "电车");*/

        Map<String, Object> map = new HashMap<>();
        map.put("car_num", "2222");
        map.put("brand", "比亚迪汉");
        map.put("guide_price", 10.0);
        map.put("produce_time", "2020-11-11");
        map.put("car_type", "电车");

        // 执行SQL语句
        // 第一个参数：sqlId，从CarMapper.xml文件中复制
        // 第二个参数：封装数据的对象
        int count = sqlSession.insert("insertCar", map);
        System.out.println(count);
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void testSelectById() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        // 根据id查询，执行SQL语句
        // mybatis底层执行select语句之后，一定会返回一个结果集对象：ResultSet
        Object car = sqlSession.selectOne("selectCarById", 1);
        System.out.println(car);
        sqlSession.close();
    }

    @Test
    public void testSelectAll() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        List<Object> cars = sqlSession.selectList("selectAll");
        cars.forEach(System.out::println);
        sqlSession.close();
    }

    @Test
    public void testNamespace(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        List<Object> cars = sqlSession.selectList("selectAll");
        cars.forEach(System.out::println);
        sqlSession.close();
    }
}
