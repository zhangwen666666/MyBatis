package org.god.ibatis.core;

import java.util.Objects;

/**
 * 普通的Java类，POJO，封装了一个SQL标签。
 * 一个MappedStatement对象封装了一个SQL标签
 */
public class MappedStatement {
    private String sql;
    private String resultType;

    @Override
    public String toString() {
        return "MappedStatement{" +
                "sql='" + sql + '\'' +
                ", resultType='" + resultType + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        MappedStatement that = (MappedStatement) object;
        return Objects.equals(sql, that.sql) && Objects.equals(resultType, that.resultType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sql, resultType);
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public MappedStatement(String sql, String resultType) {
        this.sql = sql;
        this.resultType = resultType;
    }

    public MappedStatement() {
    }
}
