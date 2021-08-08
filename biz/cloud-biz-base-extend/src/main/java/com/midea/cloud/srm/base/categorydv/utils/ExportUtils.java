package com.midea.cloud.srm.base.categorydv.utils;

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
    public static final LinkedHashMap<String,String> categoryDvTitles_CH;
    /**
     * 日文导出标题
     */
    public static final LinkedHashMap<String,String> categoryDvTitles_JP;
    /**
     * 英文
     */
    public static final LinkedHashMap<String,String> categoryDvTitles_EN;

    static {
        categoryDvTitles_CH = new LinkedHashMap<>();
        categoryDvTitles_JP = new LinkedHashMap<>();
        categoryDvTitles_EN = new LinkedHashMap<>();

        categoryDvTitles_CH.put("categoryFullName","品类");
        categoryDvTitles_CH.put("orgName","组织名称");
        categoryDvTitles_CH.put("fullName","员工名称");
        categoryDvTitles_CH.put("userName","员工账号");
        categoryDvTitles_CH.put("startDate","生效日期");
        categoryDvTitles_CH.put("endDate","失效日期");
        categoryDvTitles_CH.put("lastUpdateDate","更新时间");
        categoryDvTitles_CH.put("lastUpdatedBy","更新人");

        categoryDvTitles_JP.put("categoryFullName","カテゴリー");
        categoryDvTitles_JP.put("orgName","協会の名前");
        categoryDvTitles_JP.put("fullName","従業員名");
        categoryDvTitles_JP.put("userName","従業員アカウント");
        categoryDvTitles_JP.put("startDate","発効日");
        categoryDvTitles_JP.put("endDate","賞味期限");
        categoryDvTitles_JP.put("lastUpdateDate","更新時間");
        categoryDvTitles_JP.put("lastUpdatedBy","アップデーター");

        categoryDvTitles_EN.put("categoryFullName","Category");
        categoryDvTitles_EN.put("orgName","name of association");
        categoryDvTitles_EN.put("fullName","Employee name");
        categoryDvTitles_EN.put("userName","Employee account");
        categoryDvTitles_EN.put("startDate","effective date");
        categoryDvTitles_EN.put("endDate","Expiration date");
        categoryDvTitles_EN.put("lastUpdateDate","Update time");
        categoryDvTitles_EN.put("lastUpdatedBy","updater");


    }

    /**
     * 根据当前环境语言返回标题
     * @return
     */
    public static LinkedHashMap<String,String> getCategoryDvTitles(){
        String localeKey = LocaleHandler.getLocaleKey();
        switch (localeKey){
            case "en_US":
                return categoryDvTitles_EN;
            case "ja_JP":
                return categoryDvTitles_JP;
            default:
                return categoryDvTitles_CH;
        }
    }
}
