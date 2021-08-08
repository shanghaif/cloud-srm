package com.midea.cloud.srm.perf.vendorimprove.utils;

import com.midea.cloud.component.context.i18n.LocaleHandler;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

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
    public static final LinkedHashMap<String,String> VendorImproveFormTitles_CH;
    /**
     * 日文导出标题
     */
    public static final LinkedHashMap<String,String> VendorImproveFormTitles_JP;
    /**
     * 英文
     */
    public static final LinkedHashMap<String,String> VendorImproveFormTitles_EN;
    /**
     * 字典
     */
    public static final Map<String,String> keyValue;

    static {
        VendorImproveFormTitles_CH = new LinkedHashMap<>();
        VendorImproveFormTitles_JP = new LinkedHashMap<>();
        VendorImproveFormTitles_EN = new LinkedHashMap<>();
        keyValue = new HashMap<>();

        VendorImproveFormTitles_CH.put("improveNo","改善单号");
        VendorImproveFormTitles_CH.put("vendorName","供应商");
        VendorImproveFormTitles_CH.put("organizationName","采购组织");
        VendorImproveFormTitles_CH.put("categoryName","采购分类");
        VendorImproveFormTitles_CH.put("improveTitle","改善主题");
        VendorImproveFormTitles_CH.put("improveProject","改善项目");
        VendorImproveFormTitles_CH.put("respFullName","责任跟进人");
        VendorImproveFormTitles_CH.put("improveDateStart","改善开始日期");
        VendorImproveFormTitles_CH.put("improveDateEnd","改善结束日期");
        VendorImproveFormTitles_CH.put("createdBy","创建人");
        VendorImproveFormTitles_CH.put("creationDate","创建时间");
        VendorImproveFormTitles_CH.put("status","改善状态");

        VendorImproveFormTitles_JP.put("improveNo","ノート番号を改善する");
        VendorImproveFormTitles_JP.put("vendorName","サプライヤー");
        VendorImproveFormTitles_JP.put("organizationName","購買組織");
        VendorImproveFormTitles_JP.put("categoryName","購入分類");
        VendorImproveFormTitles_JP.put("improveTitle","テーマを改善する");
        VendorImproveFormTitles_JP.put("improveProject","改善プロジェクト");
        VendorImproveFormTitles_JP.put("respFullName","責任あるフォロワー");
        VendorImproveFormTitles_JP.put("improveDateStart","開始日を改善する");
        VendorImproveFormTitles_JP.put("improveDateEnd","終了日を改善する");
        VendorImproveFormTitles_JP.put("createdBy","創設者");
        VendorImproveFormTitles_JP.put("creationDate","作成時間");
        VendorImproveFormTitles_JP.put("status","状態を改善する");

        VendorImproveFormTitles_EN.put("improveNo","Improve note number");
        VendorImproveFormTitles_EN.put("vendorName","supplier");
        VendorImproveFormTitles_EN.put("organizationName","Purchasing organization");
        VendorImproveFormTitles_EN.put("categoryName","Purchasing classification");
        VendorImproveFormTitles_EN.put("improveTitle","Improve the theme");
        VendorImproveFormTitles_EN.put("improveProject","Improvement project");
        VendorImproveFormTitles_EN.put("respFullName","Responsible follower");
        VendorImproveFormTitles_EN.put("improveDateStart","Improve start date");
        VendorImproveFormTitles_EN.put("improveDateEnd","Improve end date");
        VendorImproveFormTitles_EN.put("createdBy","founder");
        VendorImproveFormTitles_EN.put("creationDate","Creation time");
        VendorImproveFormTitles_EN.put("status","Improve state");

        keyValue.put("DRAFT","拟定");
        keyValue.put("IMPROVING","改善中");
        keyValue.put("UNDER_EVALUATION","评价中");
        keyValue.put("EVALUATED","已评价");

    }

    /**
     * 根据当前环境语言返回标题
     * @return
     */
    public static LinkedHashMap<String,String> getVendorImproveFormTitles(){
        String localeKey = LocaleHandler.getLocaleKey();
        switch (localeKey){
            case "en_US":
                return VendorImproveFormTitles_EN;
            case "ja_JP":
                return VendorImproveFormTitles_JP;
            default:
                return VendorImproveFormTitles_CH;
        }
    }
}
