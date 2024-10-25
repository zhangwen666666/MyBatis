package com.zw.mybatis.mapper;

import com.zw.mybatis.pojo.Clazz;

public interface ClazzMapper {
    Clazz selectByIdStep2(Integer cid);
    Clazz selectByIdCollection(Integer cid);
    Clazz selectByIdStep(Integer cid);
}
