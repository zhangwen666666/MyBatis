package com.zw.mybatis.test;

import com.zw.mybatis.mapper.StudentMapper;
import com.zw.mybatis.pojo.Student;
import com.zw.mybatis.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class StudentMapperTest {
    @Test
    public void testSelectById(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        Student student = mapper.selectById(1L);
        System.out.println(student);
        sqlSession.close();
    }

    @Test
    public void testSelectByName(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        List<Student> students = mapper.selectByName("张三");
        students.forEach(System.out::println);
        sqlSession.close();
    }

    @Test
    public void testSelectByBirth() throws Exception{
        SqlSession sqlSession = SqlSessionUtil.openSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        SimpleDateFormat slf =new SimpleDateFormat("yyyy-MM-dd");
        Date birth = slf.parse("1980-10-11");
        List<Student> students = mapper.selectByBirth(birth);
        students.forEach(System.out::println);
        sqlSession.close();
    }

    @Test
    public void testSelectBySex(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        List<Student> students = mapper.selectBySex('男');
        students.forEach(System.out::println);
        sqlSession.close();
    }

    @Test
    public void testSelectByNameAndSex(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        List<Student> students = mapper.selectByNameAndSex("张三", '男');
        students.forEach(System.out::println);
        sqlSession.close();
    }
}
