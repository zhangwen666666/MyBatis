package org.god.ibatis.core;

import java.lang.reflect.Method;
import java.sql.*;

/**
 * 专门负责执行SQL语句的会话对象
 */
public class SqlSession {
    private SqlSessionFactory factory;

    public SqlSession() {
    }

    public SqlSession(SqlSessionFactory factory) {
        this.factory = factory;
    }

    /**
     * 执行insert方法，向数据库中插入数据
     *
     * @param sqlId sql语句的id
     * @param pojo  插入的数据
     * @return 影响的记录条数
     */
    public int insert(String sqlId, Object pojo) {
        int count = 0;
        try {
            // JDBC代码完成插入操作
            Connection connection = factory.getTransaction().getConnection();
            String godBatisSql = factory.getMappedStatements().get(sqlId).getSql();
            String sql = godBatisSql.replaceAll("#\\{[0-9a-zA-z_&]*}", "?");
            PreparedStatement ps = connection.prepareStatement(sql);
            // 给问号传值
            int fromIndex = 0;
            int index = 1; // 表示第几个问号
            while (true) {
                int jingIndex = godBatisSql.indexOf("#", fromIndex);
                if (jingIndex < 0) {
                    break;
                }
                int youKuoHaoIndex = godBatisSql.indexOf("}", fromIndex);
                String propertyName = godBatisSql.substring(jingIndex + 2, youKuoHaoIndex).trim();
                // System.out.println(propertyName);
                fromIndex = youKuoHaoIndex + 1;
                // System.out.println(index);
                // 有属性名怎么获取属性值呢？
                String getMethodName = "get" + propertyName.toUpperCase().charAt(0) + propertyName.substring(1);
                Method getMethod = pojo.getClass().getDeclaredMethod(getMethodName);
                Object propertyValue = getMethod.invoke(pojo);
                ps.setString(index, propertyValue.toString());
                index++;
            }
            count = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * 执行查询语句，返回一个对象
     *
     * @param sqlId
     * @param param
     * @return
     */
    public Object selectOne(String sqlId, Object param) {
        Object obj = null;
        try {
            Connection connection = factory.getTransaction().getConnection();
            String godbatisSql = factory.getMappedStatements().get(sqlId).getSql();
            String sql = godbatisSql.replaceAll("#\\{[0-9a-zA-Z_&]*}", "?");
            PreparedStatement ps = connection.prepareStatement(sql);
            // 占位符传值
            ps.setString(1, param.toString());
            ResultSet resultSet = ps.executeQuery();
            String resultType = factory.getMappedStatements().get(sqlId).getResultType();
            if (resultSet.next()) {
                Class<?> resultTypeClass = Class.forName(resultType);
                obj = resultTypeClass.newInstance();
                // 给User类的id，name，age属性赋值
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();
                for (int i = 0; i < columnCount; i++) {
                    String propertyName = metaData.getColumnName(i + 1);
                    String setMethodName = "set" + propertyName.toUpperCase().charAt(0) + propertyName.substring(1);
                    Method setMethod = resultTypeClass.getDeclaredMethod(setMethodName, String.class);
                    setMethod.invoke(obj, resultSet.getString(propertyName));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    public void commit() {
        factory.getTransaction().commit();
    }

    public void rollback() {
        factory.getTransaction().rollback();
    }

    public void close() {
        factory.getTransaction().close();
    }

    /*public static void main(String[] args) {
        String sql = "insert into t_user values(#{id},#{name},#{age})";
        int fromIndex = 0;
        int index = 1; //
        while (true) {
            int jingIndex = sql.indexOf("#", fromIndex);
            if (jingIndex < 0) {
                break;
            }
            System.out.println(index);
            index++;
            int youKuoHaoIndex = sql.indexOf("}", fromIndex);
            String propertyName = sql.substring(jingIndex + 2, youKuoHaoIndex).trim();
            System.out.println(propertyName);
            fromIndex = youKuoHaoIndex + 1;
        }
    }*/
}
