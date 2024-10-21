package org.god.ibatis.core;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * JDBC事务管理器
 * 目前只对这个类进行实现
 */
public class JDBCTransaction implements Transaction {
    /**
     * 数据源属性
     */
    private DataSource dataSource;

    /**
     * 自动提交标志
     */
    private boolean autoCommit;

    @Override
    public Connection getConnection() {
        return connection;
    }

    /**
     * 连接对象
     */
    private Connection connection;

    /**
     * 创建事务管理器对象
     *
     * @param dataSource
     * @param autoCommit
     */
    public JDBCTransaction(DataSource dataSource, boolean autoCommit) {
        this.dataSource = dataSource;
        this.autoCommit = autoCommit;
    }

    @Override
    public void commit() {
        try {
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void rollback() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void openConnection() {
        if (connection == null) {
            try {
                connection = dataSource.getConnection();
                // 开启事务
                connection.setAutoCommit(autoCommit);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
