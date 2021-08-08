package com.midea.cloud.component.i18n;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Encoder;

import java.io.*;

/**
 * <pre>
 * 多语言配置文件加载
 * </pre>`
 *
 * @author huanghb14@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021/03/15 17:12
 *  修改内容:
 * </pre>
 */
@Slf4j
@Component
public class I18nProp {

    public static OrderedProperties zh_props;

    public static OrderedProperties ja_props;

    public static OrderedProperties en_props;

    public static String zh_base64File;

    public static String ja_base64File;

    public static String en_base64File;

    {
        loadProps();
        loadPropFiles();
    }

    private synchronized static void loadProps() {
        log.info("开始加载国际化OrderedProperties文件内容");
        zh_props = new OrderedProperties();
        ja_props = new OrderedProperties();
        en_props = new OrderedProperties();
        InputStream zh_in = null;
        InputStream ja_in = null;
        InputStream en_in = null;
        try {
            zh_in = I18nProp.class.getClassLoader().getResourceAsStream("i18n/messages_zh_CN.properties");
            ja_in = I18nProp.class.getClassLoader().getResourceAsStream("i18n/messages_ja_JP.properties");
            en_in = I18nProp.class.getClassLoader().getResourceAsStream("i18n/messages_en_US.properties");
            if (zh_in != null) {
                zh_props.load(new InputStreamReader(zh_in, "UTF-8"));
            }
            if (ja_in != null) {
                ja_props.load(new InputStreamReader(ja_in, "UTF-8"));
            }
            if (en_in != null) {
                en_props.load(new InputStreamReader(en_in, "UTF-8"));
            }
        } catch (FileNotFoundException e) {
            log.error("国际化文件未找到");
        } catch (IOException e) {
            log.error("读取国际化文件异常");
        } finally {
            try {
                if (null != zh_in) {
                    zh_in.close();
                }
                if (null != ja_in) {
                    ja_in.close();
                }
                if (null != en_in) {
                    en_in.close();
                }
            } catch (IOException e) {
                log.error("国际化文件流关闭出现异常");
            }
        }
        log.info("加载国际化文件内容完成...........");
    }

    private synchronized static void loadPropFiles() {
        InputStream zh_in = null;
        InputStream ja_in = null;
        InputStream en_in = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        BASE64Encoder encoder = new BASE64Encoder();
        try {
            zh_in = I18nProp.class.getClassLoader().getResourceAsStream("i18n/messages_zh_CN.properties");
            ja_in = I18nProp.class.getClassLoader().getResourceAsStream("i18n/messages_ja_JP.properties");
            en_in = I18nProp.class.getClassLoader().getResourceAsStream("i18n/messages_en_US.properties");
            if (zh_in != null) {
                byteArrayOutputStream = new ByteArrayOutputStream();
                encoder.encode(zh_in, byteArrayOutputStream);
                zh_base64File = byteArrayOutputStream.toString("UTF-8");
            }
            if (ja_in != null) {
                byteArrayOutputStream = new ByteArrayOutputStream();
                encoder.encode(ja_in, byteArrayOutputStream);
                ja_base64File = byteArrayOutputStream.toString("UTF-8");
            }
            if (en_in != null) {
                byteArrayOutputStream = new ByteArrayOutputStream();
                encoder.encode(en_in, byteArrayOutputStream);
                en_base64File = byteArrayOutputStream.toString("UTF-8");
            }
        } catch (FileNotFoundException e) {
            log.error("国际化文件未找到");
        } catch (IOException e) {
            log.error("读取国际化文件异常");
        } finally {
            try {
                if (null != zh_in) {
                    zh_in.close();
                }
                if (null != ja_in) {
                    ja_in.close();
                }
                if (null != en_in) {
                    en_in.close();
                }
            } catch (IOException e) {
                log.error("国际化文件流关闭出现异常");
            }
        }
        log.info("读取国际化文件内容完成...........");
    }

}
