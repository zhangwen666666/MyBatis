package org.god.ibatis.utils;

import java.io.InputStream;

/**
 * godBatis框架提供的工具类
 * 这个工具类专门完成类路径中资源的加载
 */
public class Resources{
    // 工具类的方法都是建议私有化的。
    private Resources() {}

    /**
     * 从类路径中加载资源
     * @param resource 放在类路径中的资源文件
     * @return 指向资源文件的输入流
     */
    public static InputStream getResourceAsStream(String resource){
        return ClassLoader.getSystemClassLoader().getResourceAsStream(resource);
    }
}
