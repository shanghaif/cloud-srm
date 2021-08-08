package com.midea.cloud.srm.perf.scoring.utils;

import com.midea.cloud.component.context.i18n.LocaleHandler;

import java.util.LinkedHashMap;

/**
 * <pre>
 * 综合绩效得分主表(绩效考核综合查询)表导出工具
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/6/23
 *  修改内容:
 * </pre>
 */
public class OverallScoreExportUtils {
    /**
     * 中文导出标题
     */
    public static final LinkedHashMap<String,String> OverallScoreTitles_CH;
    /**
     * 日文导出标题
     */
    public static final LinkedHashMap<String,String> OverallScoreTitles_JP;
    /**
     * 英文
     */
    public static final LinkedHashMap<String,String> OverallScoreTitles_EN;

    static {
        OverallScoreTitles_CH = new LinkedHashMap<>();
        OverallScoreTitles_JP = new LinkedHashMap<>();
        OverallScoreTitles_EN = new LinkedHashMap<>();

        OverallScoreTitles_CH.put("projectName","项目名称");
//        OverallScoreTitles_CH.put("evaluationPeriod","评价期间");
        OverallScoreTitles_CH.put("perStartMonth","绩效开始月份");
        OverallScoreTitles_CH.put("perEndMonth","绩效结束月份");
        OverallScoreTitles_CH.put("templateName","模板名称");
        OverallScoreTitles_CH.put("categoryNames","模板品类");
        OverallScoreTitles_CH.put("organizationName","采购组织");
        OverallScoreTitles_CH.put("companyName","供应商");
        OverallScoreTitles_CH.put("scoreAttribute1","品质");
        OverallScoreTitles_CH.put("scoreAttribute2","成本");
        OverallScoreTitles_CH.put("scoreAttribute3","交付");
        OverallScoreTitles_CH.put("scoreAttribute4","服务");
        OverallScoreTitles_CH.put("scoreAttribute5","技术");
        OverallScoreTitles_CH.put("score","综合得分");
        OverallScoreTitles_CH.put("rank","绩效排名");
        OverallScoreTitles_CH.put("levelName","绩效等级");
        OverallScoreTitles_CH.put("vendorFeedbackComments","供应商反馈说明");

        OverallScoreTitles_EN.put("projectName","project Name");
//        OverallScoreTitles_EN.put("evaluationPeriod","Evaluation Period");
        OverallScoreTitles_EN.put("perStartMonth","Performance Start Month");
        OverallScoreTitles_EN.put("perEndMonth","Performance End Month");
        OverallScoreTitles_EN.put("templateName","Template Name");
        OverallScoreTitles_EN.put("categoryNames","Template Categories");
        OverallScoreTitles_EN.put("organizationName","Procurement Organization");
        OverallScoreTitles_EN.put("companyName","Supplier");
        OverallScoreTitles_EN.put("scoreAttribute1","Quality");
        OverallScoreTitles_EN.put("scoreAttribute2","Cost");
        OverallScoreTitles_EN.put("scoreAttribute3","Delivery");
        OverallScoreTitles_EN.put("scoreAttribute4","Service");
        OverallScoreTitles_EN.put("scoreAttribute5","Technology");
        OverallScoreTitles_EN.put("score","Overall Score");
        OverallScoreTitles_EN.put("rank","Performance Ranking");
        OverallScoreTitles_EN.put("levelName","Performance Level");
        OverallScoreTitles_EN.put("vendorFeedbackComments","Vendor Feedback Comments");

        OverallScoreTitles_JP.put("projectName","プロジェクト名");
//        OverallScoreTitles_JP.put("evaluationPeriod","評価期間");
        OverallScoreTitles_JP.put("perStartMonth","公演開始月");
        OverallScoreTitles_JP.put("perEndMonth","パフォーマンス終了月");
        OverallScoreTitles_JP.put("templateName","テンプレート名");
        OverallScoreTitles_JP.put("categoryNames","テンプレート品目");
        OverallScoreTitles_JP.put("organizationName","購買組織");
        OverallScoreTitles_JP.put("companyName","サプライヤー");
        OverallScoreTitles_JP.put("scoreAttribute1","品質");
        OverallScoreTitles_JP.put("scoreAttribute2","費用");
        OverallScoreTitles_JP.put("scoreAttribute3","届ける");
        OverallScoreTitles_JP.put("scoreAttribute4","サービス");
        OverallScoreTitles_JP.put("scoreAttribute5","技術");
        OverallScoreTitles_JP.put("score","総合評価");
        OverallScoreTitles_JP.put("rank","パフォーマンスランキング");
        OverallScoreTitles_JP.put("levelName","パフォーマンスレベル");
        OverallScoreTitles_JP.put("vendorFeedbackComments","サプライヤーからのフィードバック");
    }

    /**
     * 根据当前环境语言返回标题
     * @return
     */
    public static LinkedHashMap<String,String> getOverallScoreTitles(){
        String localeKey = LocaleHandler.getLocaleKey();
        switch (localeKey){
            case "en_US":
                return OverallScoreTitles_EN;
            case "ja_JP":
                return OverallScoreTitles_JP;
            default:
                return OverallScoreTitles_CH;
        }
    }

}
