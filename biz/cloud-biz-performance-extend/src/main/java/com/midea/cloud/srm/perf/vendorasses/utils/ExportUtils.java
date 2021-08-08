package com.midea.cloud.srm.perf.vendorasses.utils;

import com.midea.cloud.component.context.i18n.LocaleHandler;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <pre>
 *  供应商考核单表导出工具
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
    public static final LinkedHashMap<String,String> VendorAssesFormTitles_CH;
    /**
     * 日文导出标题
     */
    public static final LinkedHashMap<String,String> VendorAssesFormTitles_JP;
    /**
     * 英文
     */
    public static final LinkedHashMap<String,String> VendorAssesFormTitles_EN;
    /**
     * 字典
     */
    public static final Map<String,String> keyValue;

    static {
        VendorAssesFormTitles_CH = new LinkedHashMap<>();
        VendorAssesFormTitles_JP = new LinkedHashMap<>();
        VendorAssesFormTitles_EN = new LinkedHashMap<>();
        keyValue = new HashMap<>();

        VendorAssesFormTitles_CH.put("assessmentNo","考核单号");
        VendorAssesFormTitles_CH.put("assessmentDate","考核日期");
        VendorAssesFormTitles_CH.put("respFullName","考核人");
        VendorAssesFormTitles_CH.put("indicatorDimension","指标维度");
        VendorAssesFormTitles_CH.put("indicatorName","指标名称");
        VendorAssesFormTitles_CH.put("organizationName","采购组织");
        VendorAssesFormTitles_CH.put("assessmentPenalty","建议考核金额");
        VendorAssesFormTitles_CH.put("actualAssessmentAmountY","实际考核金额");
        VendorAssesFormTitles_CH.put("currencyName","币种");
        VendorAssesFormTitles_CH.put("vendorName","供应商");
        VendorAssesFormTitles_CH.put("categoryName","采购分类");
        VendorAssesFormTitles_CH.put("status","考核单状态");

        VendorAssesFormTitles_JP.put("assessmentNo","評価番号");
        VendorAssesFormTitles_JP.put("assessmentDate","評価日");
        VendorAssesFormTitles_JP.put("respFullName","審査官");
        VendorAssesFormTitles_JP.put("indicatorDimension","メトリックディメンション");
        VendorAssesFormTitles_JP.put("indicatorName","インジケーター名");
        VendorAssesFormTitles_JP.put("organizationName","購買組織");
        VendorAssesFormTitles_JP.put("assessmentPenalty","推奨査定額");
        VendorAssesFormTitles_JP.put("actualAssessmentAmountY","実際の査定額");
        VendorAssesFormTitles_JP.put("currencyName","通貨");
        VendorAssesFormTitles_JP.put("vendorName","サプライヤー");
        VendorAssesFormTitles_JP.put("categoryName","購入分類");
        VendorAssesFormTitles_JP.put("status","チェックリストのステータス");

        VendorAssesFormTitles_EN.put("assessmentNo","Assessment number");
        VendorAssesFormTitles_EN.put("assessmentDate","Assessment date");
        VendorAssesFormTitles_EN.put("respFullName","examiner");
        VendorAssesFormTitles_EN.put("indicatorDimension","Indicator dimension");
        VendorAssesFormTitles_EN.put("indicatorName","Indicator name");
        VendorAssesFormTitles_EN.put("organizationName","Purchasing organization");
        VendorAssesFormTitles_EN.put("assessmentPenalty","Recommended assessment amount");
        VendorAssesFormTitles_EN.put("actualAssessmentAmountY","Actual assessment amount");
        VendorAssesFormTitles_EN.put("currencyName","Currency");
        VendorAssesFormTitles_EN.put("vendorName","supplier");
        VendorAssesFormTitles_EN.put("categoryName","Purchasing classification");
        VendorAssesFormTitles_EN.put("status","Checklist status");

        keyValue.put("1","品质");
        keyValue.put("2","服务");
        keyValue.put("3","交付");
        keyValue.put("4","成本");
        keyValue.put("5","技术");
        keyValue.put("DRAFT","拟定");
        keyValue.put("IN_FEEDBACK","反馈中");
        keyValue.put("WITHDRAWN","已撤回");
        keyValue.put("OBSOLETE","已废弃");
        keyValue.put("ASSESSED","已考核");

    }

    /**
     * 根据当前环境语言返回标题
     * @return
     */
    public static LinkedHashMap<String,String> getVendorAssesFormTitles(){
        String localeKey = LocaleHandler.getLocaleKey();
        switch (localeKey){
            case "en_US":
                return VendorAssesFormTitles_EN;
            case "ja_JP":
                return VendorAssesFormTitles_JP;
            default:
                return VendorAssesFormTitles_CH;
        }
    }
}
