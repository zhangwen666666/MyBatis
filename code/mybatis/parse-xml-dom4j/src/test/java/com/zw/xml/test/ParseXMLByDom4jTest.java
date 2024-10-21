package com.zw.xml.test;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

public class ParseXMLByDom4jTest {
    @Test
    public void testParseMyBatisConfigXML() throws Exception {
        SAXReader reader = new SAXReader();
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("mybatis-config.xml");
        Document document = reader.read(in);
        // System.out.println(read);
        Element root = document.getRootElement();
        // System.out.println(root.getName());
        String xpath = "/configuration/environments";
        Element environments = (Element) root.selectSingleNode(xpath);
        String defaultEnvironmentId = environments.attributeValue("default");
        System.out.println(defaultEnvironmentId);

        xpath = "/configuration/environments/environment[@id='" + defaultEnvironmentId + "']";
        // System.out.println(xpath);
        Element environment = (Element) document.selectSingleNode(xpath);

        // 获取environment节点下的节点
        Element transactionManager = environment.element("transactionManager");
        String transactionType = transactionManager.attributeValue("type");
        System.out.println(transactionType);

        // 获取dataSource节点
        Element dataSource = environment.element("dataSource");
        String dataSourceType = dataSource.attributeValue("type");
        System.out.println(dataSourceType);

        // 获取dataSource节点下的所有子节点
        List<Element> propertyEles = dataSource.elements();
        propertyEles.forEach(propertyEle -> {
            String name = propertyEle.attributeValue("name");
            String value = propertyEle.attributeValue("value");
            System.out.println(name + "=" + value);
        });

        // 获取所有的mapper标签

        xpath = "//mapper";
        List<Node> mappers = document.selectNodes(xpath);
        mappers.forEach(mapper -> {
            Element mapperElt = (Element) mapper;
            String resource = mapperElt.attributeValue("resource");
            System.out.println(resource);
        });
    }

    @Test
    public void testParseSqlMapperXML() throws Exception {
        SAXReader reader = new SAXReader();
        InputStream in = ClassLoader.getSystemResourceAsStream("CarMapper.xml");
        Document document = reader.read(in);
        Element mapper = document.getRootElement();
        // System.out.println(rootElement.getName());
        String namespace = mapper.attributeValue("namespace");
        System.out.println(namespace);
        List<Element> elements = mapper.elements();
        elements.forEach(element -> {
            System.out.println("id=" + element.attributeValue("id"));
            System.out.println("resultType=" + element.attributeValue("resultType"));
            System.out.println(element.getName());
            String sql = element.getTextTrim();
            sql = sql.replaceAll("#\\{[0-9A-Za-z_$]*}", "?");
            System.out.println("标签中的SQL语句："+ sql);
        });
    }
}
