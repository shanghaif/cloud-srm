package com.midea.cloud.srm.pr.division.utils;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.midea.cloud.component.context.i18n.LocaleHandler;

import javax.validation.constraints.NotEmpty;
import java.util.LinkedHashMap;

/**
 * @author tanjl11
 * @date 2020/10/12 14:27
 */
public class ExportUtils {
    /**
     * 中文导出标题
     */
    public static final LinkedHashMap<String, String> categoryDvTitles_CH;
    /**
     * 日文导出标题
     */
    public static final LinkedHashMap<String, String> categoryDvTitles_JP;
    /**
     * 英文
     */
    public static final LinkedHashMap<String, String> categoryDvTitles_EN;

    static {
        categoryDvTitles_CH = new LinkedHashMap<>();
        categoryDvTitles_JP = new LinkedHashMap<>();
        categoryDvTitles_EN = new LinkedHashMap<>();

        categoryDvTitles_CH.put("orgName", "业务实体");
        categoryDvTitles_CH.put("organizationName", "库存组织");
        categoryDvTitles_CH.put("categoryName", "物料小类名");
        categoryDvTitles_CH.put("categoryCode", "物料小类编码");
        categoryDvTitles_CH.put("ifMainPerson", "是否为主要负责人");
        categoryDvTitles_CH.put("duty", "职责");
        categoryDvTitles_CH.put("personInChargeNickname", "负责人名称");
        categoryDvTitles_CH.put("erpNum", "负责人工号");
        categoryDvTitles_CH.put("startDate", "生效日期");
        categoryDvTitles_CH.put("endDate", "失效日期");
        categoryDvTitles_CH.put("lastUpdatedBy", "更新人");
        categoryDvTitles_CH.put("lastUpdateDate", "更新时间");


    }

    /**
     * 根据当前环境语言返回标题
     *
     * @return
     */
    public static LinkedHashMap<String, String> getCategoryDvTitles() {
        String localeKey = LocaleHandler.getLocaleKey();
        switch (localeKey) {
            case "en_US":
                return categoryDvTitles_EN;
            case "ja_JP":
                return categoryDvTitles_JP;
            default:
                return categoryDvTitles_CH;
        }
    }
}
