package com.midea.cloud.srm.base.material.utils;

import com.midea.cloud.component.context.i18n.LocaleHandler;

import java.util.LinkedHashMap;

/**
 * <pre>
 *  供应商改善单表导出工具
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
public class ExportUtils {
    /**
     * 中文导出标题
     */
    public static final LinkedHashMap<String,String> materialItemTitles_CH;
    /**
     * 日文导出标题
     */
    public static final LinkedHashMap<String,String> materialItemTitles_JP;
    /**
     * 英文
     */
    public static final LinkedHashMap<String,String> materialItemTitles_EN;

    static {
        materialItemTitles_CH = new LinkedHashMap<>();
        materialItemTitles_JP = new LinkedHashMap<>();
        materialItemTitles_EN = new LinkedHashMap<>();

        materialItemTitles_CH.put("materialName","物料名称");
        materialItemTitles_CH.put("materialCode","物料编码");
        materialItemTitles_CH.put("specification","规格与型号");
        materialItemTitles_CH.put("unitName","单位");
        materialItemTitles_CH.put("categoryFullName","品类");
        materialItemTitles_CH.put("status","是否启用");
        materialItemTitles_CH.put("lastUpdateDate","更新时间");
        materialItemTitles_CH.put("lastUpdatedBy","更新人");

        materialItemTitles_JP.put("materialName","素材名");
        materialItemTitles_JP.put("materialCode","材料コーディング");
        materialItemTitles_JP.put("specification","仕様とモデル");
        materialItemTitles_JP.put("unitName","単位");
        materialItemTitles_JP.put("categoryFullName","カテゴリー");
        materialItemTitles_JP.put("status","有効にするかどうか");
        materialItemTitles_JP.put("lastUpdateDate","更新時間");
        materialItemTitles_JP.put("lastUpdatedBy","アップデーター");

        materialItemTitles_EN.put("materialName","Material name");
        materialItemTitles_EN.put("materialCode","Material coding");
        materialItemTitles_EN.put("specification","Specifications and models");
        materialItemTitles_EN.put("unitName","unit");
        materialItemTitles_EN.put("categoryFullName","Category");
        materialItemTitles_EN.put("status","Whether to enable");
        materialItemTitles_EN.put("lastUpdateDate","Update time");
        materialItemTitles_EN.put("lastUpdatedBy","updater");

    }

    /**
     * 根据当前环境语言返回标题
     * @return
     */
    public static LinkedHashMap<String,String> materialItemTieles(){
        String localeKey = LocaleHandler.getLocaleKey();
        switch (localeKey){
            case "en_US":
                return materialItemTitles_EN;
            case "ja_JP":
                return materialItemTitles_JP;
            default:
                return materialItemTitles_CH;
        }
    }
}
