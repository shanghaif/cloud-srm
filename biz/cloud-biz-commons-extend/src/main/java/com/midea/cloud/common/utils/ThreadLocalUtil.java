package com.midea.cloud.common.utils;

import com.midea.cloud.srm.model.rbac.user.entity.User;

import java.util.UUID;

/**
 * <pre>
 *  当前线程变量工具类
 * </pre>
 *
 * @author wangpr@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-27 09:24:20
 *  修改内容:
 * </pre>
 */
public class ThreadLocalUtil<T> {
    private static ThreadLocal<String> threadLocalOnlyFlag = ThreadLocal.withInitial(() -> UUID.randomUUID().toString());

    private static ThreadLocal tThreadLocal = new ThreadLocal();
    /**
     * 获取当前线程唯一标识
     * @return
     */
    public static String getThreadOnlyFlag(){
        return threadLocalOnlyFlag.get();
    }

    /**
     * 设置当前线程变量
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> T get(Class<T> tClass){
        return (T)tThreadLocal.get();
    }

    /**
     * 获取当前线程变量
     * @param t
     * @param <T>
     */
    public static <T> void set(T t){
        tThreadLocal.set(t);
    }

    /**
     * 清除线程变量
     */
    public static void clear() {
        tThreadLocal.remove();
    }

    /**
     * 测试
     * @param args
     */
    public static void main(String[] args) {
        new Thread(() -> {
            System.out.println("线程一:唯一key=>"+getThreadOnlyFlag());
            ThreadLocalUtil.set(new User().setUsername("username1"));
            System.out.println("线程一:变量=>"+get(User.class).getUsername());
        }).start();

        new Thread(() -> {
            System.out.println("线程二:唯一key=>"+getThreadOnlyFlag());
            ThreadLocalUtil.set(new User().setUsername("username2"));
            System.out.println("线程二:变量=>"+get(User.class).getUsername());
        }).start();

        new Thread(() -> {
            System.out.println("线程三:唯一key=>"+getThreadOnlyFlag());
            ThreadLocalUtil.set(new User().setUsername("username3"));
            System.out.println("线程三:变量=>"+get(User.class).getUsername());
        }).start();

        new Thread(() -> {
            System.out.println("线程四:唯一key=>"+getThreadOnlyFlag());
            ThreadLocalUtil.set(new User().setUsername("username4"));
            System.out.println("线程四:变量=>"+get(User.class).getUsername());
        }).start();
    }
}
