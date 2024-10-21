package org.god.ibatis.core;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.god.ibatis.utils.Resources;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SqlSessionFactoryBuilder构建器对象
 * 通过SqlSessionFactoryBuilder的build方法来解析godbatis-config.xml文件
 * 然后创建sqlSessionFactory对象
 */
public class SqlSessionFactoryBuilder {
    /**
     * 无参构造
     */
    public SqlSessionFactoryBuilder() {
    }

    /**
     * 解析godbatis-config.xml文件，构建sqlSessionFactory对象
     *
     * @param in 指向godbatis-config.xml的输入流
     * @return sqlSessionFactory对象
     */
    public SqlSessionFactory build(InputStream in) {
        SqlSessionFactory factory = null;
        // 解析文件
        try {
            SAXReader reader = new SAXReader();
            Document document = reader.read(in);
            Element environments = (Element) document.selectSingleNode("/configuration/environments");
            String defaultId = environments.attributeValue("default");
            Element environment = (Element) document.selectSingleNode("/configuration/environments/environment[@id='" + defaultId + "']");
            Element transactionElt = environment.element("transactionManager");
            Element dataSourceElt = environment.element("dataSource");
            List<String> sqlMapperXMLPathList = new ArrayList<>();
            List<Node> nodes = document.selectNodes("//mapper");
            nodes.forEach(node -> {
                Element mapper = (Element) node;
                String resource = mapper.attributeValue("resource");
                sqlMapperXMLPathList.add(resource);
            });

            // 获取数据源
            DataSource dataSource = getDataSource(dataSourceElt);


            // 获取事务管理器
            Transaction transaction = getTransaction(transactionElt, dataSource);

            // 获取mappedStatements
            Map<String, MappedStatement> mappedStatements = getMappedStatements(sqlMapperXMLPathList);
            // 解析完成之后，构建SqlSessionFactory对象
            factory = new SqlSessionFactory(transaction, mappedStatements);

        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return factory;
    }


    /**
     * 解析所有的SqlMapper.xml文件，构建Map集合
     *
     * @param sqlMapperXMLPathList
     * @return
     */
    private Map<String, MappedStatement> getMappedStatements(List<String> sqlMapperXMLPathList) {
        Map<String, MappedStatement> mappedStatements = new HashMap<>();
        sqlMapperXMLPathList.forEach(path -> {
            try {
                SAXReader reader = new SAXReader();
                Document document = reader.read(Resources.getResourceAsStream(path));
                Element mapper = document.getRootElement();
                String namespace = mapper.attributeValue("namespace");
                List<Element> elements = mapper.elements();
                elements.forEach(element -> {
                    String id = element.attributeValue("id");
                    String sqlId = namespace + "." + id;
                    String resultType = element.attributeValue("resultType");
                    String sql = element.getText().trim();
                    MappedStatement mappedStatement = new MappedStatement(sql, resultType);
                    mappedStatements.put(sqlId,mappedStatement);
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return mappedStatements;
    }


    /**
     * 获取事务管理器
     *
     * @param transactionElt 事务管理器标签元素
     * @param dataSource     数据源对象
     * @return
     */
    private Transaction getTransaction(Element transactionElt, DataSource dataSource) {
        Transaction transaction = null;
        String type = transactionElt.attributeValue("type").trim().toUpperCase();
        if (Const.JDBC_TRANSACTION.equals(type)) {
            transaction = new JDBCTransaction(dataSource, false);
        } else if (Const.MANAGED_TRANSACTION.equals(type)) {
            transaction = new ManagedTransaction();
        }
        return transaction;
    }


    /**
     * 获取数据源对象
     *
     * @param dataSourceElt
     * @return
     */
    private DataSource getDataSource(Element dataSourceElt) {
        Map<String, String> map = new HashMap<>();
        // 获取所有的子节点
        List<Element> propertyElts = dataSourceElt.elements("property");
        propertyElts.forEach(propertyElt -> {
            String name = propertyElt.attributeValue("name");
            String value = propertyElt.attributeValue("value");
            map.put(name, value);
        });

        DataSource dataSource = null;
        String type = dataSourceElt.attributeValue("type").trim().toUpperCase();
        switch (type) {
            case Const.UN_POOLED_DATASOURCE -> {
                dataSource = new UnPooledDataSource(map.get("driver"), map.get("url"), map.get("username"), map.get("password"));
            }
            case Const.POOLED_DATASOURCE -> {
                dataSource = new PooledDataSource();
            }
            case Const.JDNI_DATASOURCE -> {
                dataSource = new JNDIDataSource();
            }
        }
        return dataSource;
    }
}
