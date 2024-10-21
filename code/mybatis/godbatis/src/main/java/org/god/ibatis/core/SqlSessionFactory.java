package org.god.ibatis.core;

import java.util.Map;

/**
 * 一个数据库对应一个SqlSessionFactory对象
 * 通过SqlSessionFactory可以获取SqlSession对象
 * 一个SqlSession对象可以开启多个SqlSession会话
 */
public class SqlSessionFactory {
    /**
     * 事务管理器属性
     * 事务管理器是可以灵活切换的，因此应该是可以灵活切换的
     */
    private Transaction transaction;


    // 存放sql语句的Map集合
    private Map<String, MappedStatement> mappedStatements;

    public SqlSessionFactory(Transaction transaction, Map<String, MappedStatement> mappedStatements) {
        this.transaction = transaction;
        this.mappedStatements = mappedStatements;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public Map<String, MappedStatement> getMappedStatements() {
        return mappedStatements;
    }

    public void setMappedStatements(Map<String, MappedStatement> mappedStatements) {
        this.mappedStatements = mappedStatements;
    }

    public SqlSessionFactory() {
    }


    /**
     * 获取sql会话对象
     * @return
     */
    public SqlSession openSession(){
        // 开启会话的前提就是开启连接
        transaction.openConnection();
        // 创建SqlSession对象
        // sqlSession对象要执行sql语句，需要用到connection对象，需要存放sql语句的map集合
        SqlSession sqlSession = new SqlSession(this);
        return sqlSession;
    }
}
