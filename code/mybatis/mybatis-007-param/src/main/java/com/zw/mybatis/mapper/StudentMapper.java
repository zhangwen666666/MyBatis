package com.zw.mybatis.mapper;

import com.zw.mybatis.pojo.Student;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface StudentMapper {
    /**
     * 当接口中方法的参数只有一个，并且是简单类型
     * 根据id查询、name查询、birth查询、sex查询
     */
    Student selectById(Long id);
    List<Student> selectByName(String name);
    List<Student> selectByBirth(Date birth);
    List<Student> selectBySex(Character sex);

    /**
     * 根据名字和性别查询Student信息
     * @param name 名字
     * @param sex 性别
     * @return
     */
    List<Student> selectByNameAndSex(@Param("name") String name, @Param("sex") Character sex);
}
