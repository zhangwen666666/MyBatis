package com.zw.javassist;

import com.zw.bank.dao.AccountDao;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.Arrays;

public class JavassistTest {
    @Test
    public void testGenerateFirstClass() throws Exception {
        // 获取类池，这个类池就是用来生成class的
        ClassPool pool = ClassPool.getDefault();
        // 制造类 (需要告诉javassist类名是啥)
        CtClass ctClass = pool.makeClass("com.zw.bank.dao.impl.AccountDaoImpl");
        // 制造方法
        String methodCode = "public void insert(){System.out.println(123);}";
        CtMethod ctMethod = CtMethod.make(methodCode, ctClass);
        // 将方法添加到类中
        ctClass.addMethod(ctMethod);
        // 在内存中生成class
        ctClass.toClass();

        // 类加载
        Class<?> clazz = Class.forName("com.zw.bank.dao.impl.AccountDaoImpl");
        // 创建对象调用insert方法
        Object obj = clazz.newInstance();
        Method insert = clazz.getDeclaredMethod("insert");
        insert.invoke(obj);
    }

    @Test
    public void testGenerateImpl() throws Exception {
        // 获取类名
        ClassPool pool = ClassPool.getDefault();
        // 制造类
        CtClass ctClass = pool.makeClass("com.zw.bank.dao.impl.AccountDaoImpl");
        // 制造接口
        CtClass ctInterface = pool.makeInterface("com.zw.bank.dao.AccountDao");
        // 添加接口到类中 （相当于AccountDaoImpl implements AccountDao）
        ctClass.addInterface(ctInterface);
        // 实现接口中的方法
        // 先制造方法
        String methodCode = "public void delete(){System.out.println(\"hello delete\");}";
        CtMethod ctMethod = CtMethod.make(methodCode, ctClass);
        // 将方法添加到类中
        ctClass.addMethod(ctMethod);

        // 在内存中生成类
        Class<?> clazz = ctClass.toClass();
        AccountDao accountDao = (AccountDao) clazz.newInstance();
        accountDao.delete();
    }


    @Test
    public void testGenerateAccountDaoImpl() throws Exception{
        // 获取类池
        ClassPool pool = ClassPool.getDefault();
        // 制造类
        CtClass ctClass = pool.makeClass("com.zw.bank.dao.impl.AccountDaoImpl");
        // 制造接口
        CtClass ctInterface = pool.makeInterface("com.zw.bank.dao.AccountDao");
        // 添加接口到类中
        ctClass.addInterface(ctInterface);
        // 实现接口中的方法
        Method[] methods = AccountDao.class.getDeclaredMethods();// 获取接口中的所有方法
        Arrays.stream(methods).forEach(method -> {
            // method是接口中的抽象方法，现在需要将其实现
            try {
                // 拼串来拼出方法的代码
                StringBuilder methodCode = new StringBuilder();
                methodCode.append("public "); // 修饰符列表
                methodCode.append(method.getReturnType().getName());// 方法返回值类型
                methodCode.append(" ");
                methodCode.append(method.getName());// 方法名
                methodCode.append("(");
                // 拼接参数
                Class<?>[] parameterTypes = method.getParameterTypes();
                for (int i = 0; i < parameterTypes.length; i++) {
                    Class<?> parameterType = parameterTypes[i];
                    methodCode.append(parameterType.getName());// 拼接参数类型
                    methodCode.append(" ");
                    methodCode.append("arg" + i);// 参数名（可以随意了，但是不能重复）
                    // 如果不是最后一个形参，需要追加逗号
                    if (i != parameterTypes.length - 1) {
                        methodCode.append(",");
                    }
                }
                methodCode.append("){System.out.println(1111);");
                // 判断方法是否需要返回值,添加return语句
                String returnTypeName = method.getReturnType().getSimpleName();
                switch (returnTypeName) {
                    case "void" -> {
                    }
                    case "int" -> {
                        methodCode.append("return 1;");
                    }
                    case "String" -> {
                        methodCode.append("return \"hello\";");
                    }
                }
                methodCode.append("}");
                System.out.println(methodCode);
                CtMethod ctMethod = CtMethod.make(methodCode.toString(), ctClass);
                ctClass.addMethod(ctMethod);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        // 在内存中生成class，并且加载到JVM当中
        Class<?> clazz = ctClass.toClass();
        // 创建对象
        AccountDao accountDao = (AccountDao) clazz.newInstance();
        // 调用方法
        accountDao.delete();
        int count = accountDao.insert("111");
        int count1 = accountDao.update("111",3.14);
        String s = accountDao.selectByActno("111");
        System.out.println(count);
        System.out.println(count1);
        System.out.println(s);
    }
}
