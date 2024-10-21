package org.god.ibatis.core;

import java.sql.Connection;

/**
 * 事务管理器接口
 * 所有的事务管理器都应该遵循该规范
 */
public interface Transaction {
    void commit();
    void rollback();
    void close();

    /**
     * 开启数据库连接
     */
    void openConnection();

    Connection getConnection();
}
