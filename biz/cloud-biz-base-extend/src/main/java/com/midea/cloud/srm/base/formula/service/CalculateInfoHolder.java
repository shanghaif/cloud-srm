package com.midea.cloud.srm.base.formula.service;

import java.util.Map;

/**
 * @author tanjl11
 * @date ???
 */
public class CalculateInfoHolder {
    public static ThreadLocal<Map<String, String>> holder = new ThreadLocal<>();

    public CalculateInfoHolder() {
    }

    public static void clear() {
        holder.remove();
    }

    public static void set(Map<String, String> temp) {
        holder.set(temp);
    }

    public static Map<String, String> get() {
        return holder.get();
    }
}