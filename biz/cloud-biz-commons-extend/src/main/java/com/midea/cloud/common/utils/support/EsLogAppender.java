package com.midea.cloud.common.utils.support;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author tanjl11
 * @date 2020/12/19 0:13
 */
public class EsLogAppender extends AppenderBase<ILoggingEvent> {
    private static final AtomicBoolean sync = new AtomicBoolean(false);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static ThreadLocal<StringBuilder> holder;
    static Long DEFAULT_SIZE = 5000L;

    public static StringBuilder get() {
        return holder.get();
    }

    public static void clear() {
        holder.remove();
    }

    public static synchronized void init() {
        boolean b = sync.compareAndSet(false, true);
        if (b) {
            holder = ThreadLocal.withInitial(StringBuilder::new);
        }
    }

    @Override
    protected void append(ILoggingEvent loggingEvent) {
        if (!sync.get()) {
            return;
        }
        StringBuilder builder = holder.get();
        if (builder.length() > DEFAULT_SIZE) {
            return;
        }
        builder.append("[")
                .append(formatter.format(LocalDateTime.now()))
                .append("] ")
                .append("[")
                .append(loggingEvent.getLevel()).append("] ")
                .append("[").append(Thread.currentThread().getName())
                .append("] ")
                .append("-- ")
                .append(loggingEvent.getFormattedMessage())
                .append("<br/>");
    }
}
