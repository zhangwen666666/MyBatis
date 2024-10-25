package com.zw.mybatis.mapper;

import com.zw.mybatis.pojo.Student;

public interface StudentMapper {
    Student selectById(Integer sid);
    Student selectByIdAssociation(Integer sid);

    /**
     * 分步查询，根据学生的sid查询学生的信息
     * @param sid
     * @return
     */
    Student selectByIdStep1(Integer sid);

    Student selectByCid(Integer cid);
}
