package com.zw.threadlocal;

import java.util.HashMap;
import java.util.Map;

public class MyThreadLocal<T> {
    /**
     * 所有需要和当前线程绑定的数据都要放到这个容器中
     */
    private Map<Thread, T> map = new HashMap<>();

    public void set(T object){
        map.put(Thread.currentThread(), object);
    }

    public T get(){
        return map.get(Thread.currentThread());
    }

    public void remove(){
        map.remove(Thread.currentThread());
    }
}
