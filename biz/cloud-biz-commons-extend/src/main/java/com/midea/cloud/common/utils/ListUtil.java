package com.midea.cloud.common.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * <pre>
 *
 * </pre>
 *
 * @author wangpr@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/19
 *  修改内容:
 * </pre>
 */
public class ListUtil {


    /**
     * 对象集合根据条件去重
     * @param tList 集合
     * @param keyExtractor 指定去重的条件
     * @param <T>
     * @return
     */
    public static <T> List<T> listDeduplication(List<T> tList, Function<? super T, ?> keyExtractor){
        if (null != tList) {
            return tList.stream().filter(distinctByVariable(u -> keyExtractor.apply(u))).collect(Collectors.toList());
        }else {
            return null;
        }
    }

    /**
     * putIfAbsent() 方法是
     *      如果 key对应的value值不存在, key value 添加到 map 中,并返回 null
     *      如果 key对应的value值已存在, key value 不再添加到 map 中, 并返回原 value
     *
     * 故 newKey(这里的newKey对应user对象中的name的值), 如果(newKey, Boolean.TRUE) 在map中已存在,
     * putIfAbsent(newKey, Boolean.TRUE) 会返回 Boolean.TRUE (Boolean.TRUE 被final修饰,故其地址值唯一, 可用作比较)
     * 然后判断是否等于 null, 返回false, filter接收到结果为false的Predicate并将该值过滤掉
     * @param keyExtractor
     * @param <T>
     * @return
     */
    public static <T> Predicate<T> distinctByVariable(Function<? super T, ?> keyExtractor) {
        HashMap<Object, Boolean> map = new HashMap<>();
        return t->map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    /**
     * 测试, 根据用户id和名字去重
     */
    @Test
    public void testListDeduplication(){
        @Data
        @AllArgsConstructor
        class User{
            private int userId;
            private String userName;
        }
        ArrayList<User> users = new ArrayList<>();
        users.add(new User(1,"小王"));
        users.add(new User(1,"小王"));
        users.add(new User(2,"小王"));
        users.add(new User(2,"小王"));

        // 去重
        List<User> users1 = listDeduplication(users, u -> {
            return u.getUserId() + u.getUserName();
        });
        // 打印检查
        users1.forEach(u->{
            System.out.println(u.getUserId()+"-"+u.getUserName());
        });
    }
}
