package com.zw.mybatis.test;

import com.zw.mybatis.mapper.ClazzMapper;
import com.zw.mybatis.pojo.Clazz;
import com.zw.mybatis.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

public class ClazzMapperTest {
    @Test
    public void testSelectByIdCollection(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        ClazzMapper mapper = sqlSession.getMapper(ClazzMapper.class);
        Clazz clazz = mapper.selectByIdCollection(1000);
        System.out.println(clazz);
        sqlSession.close();
    }

    @Test
    public void testSelectByIdStep(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        ClazzMapper mapper = sqlSession.getMapper(ClazzMapper.class);
        Clazz clazz = mapper.selectByIdStep(1000);
        System.out.println(clazz.getCname());
        System.out.println(clazz);
        sqlSession.close();
    }
}
