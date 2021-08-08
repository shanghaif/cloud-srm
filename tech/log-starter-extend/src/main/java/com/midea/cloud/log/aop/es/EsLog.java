package com.midea.cloud.log.aop.es;

import org.apache.log4j.Logger;
import java.time.LocalDateTime;


/**
 * @author tanjl11
 * @date 2020/12/18 16:30
 */
public class EsLog {
    public static ThreadLocal<StringBuilder> holder = ThreadLocal.withInitial(StringBuilder::new);
    static String line = System.getProperty("line.separator");
    static Long DEFAULT_SIZE=5000L;
    public static void clear() {
        holder.remove();
    }

    public static StringBuilder get() {
        return holder.get();
    }

    public static void info(Logger logger, Object object, Throwable throwable) {
        logger.info(object, throwable);
        log(object,"info");
    }

    public static void error(Logger logger,Object object, Throwable throwable){
        logger.error(object,throwable);
        log(object,"error");
    }
    public static void error(Logger logger,Object object){
        logger.error(object);
        log(object,"error");
    }
    public static void warn(Logger logger,Object object){
        logger.warn(object);
        log(object,"warn");
    }
    public static void info(Logger logger, Object object) {
        logger.info(object);
        log(object,"info");
    }

    private static void log(Object object,String level) {
        StringBuilder builder = holder.get();
        if (builder.length() > DEFAULT_SIZE) {
            return;
        }
        builder
                .append(LocalDateTime.now())
                .append("===")
                .append(level)
                .append("===")
                .append(object.toString())
                .append(line);
    }

}
