package com.midea.cloud.srm.perf.scoring.utils;

import com.midea.cloud.component.context.i18n.LocaleHandler;

import java.util.LinkedHashMap;

/**
 * <pre>
 *  评分人绩效评分 ScoreManScoringV1 导出工具
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021/01/28 19:35
 *  修改内容:
 * </pre>
 */
public class ScoreManScoringV1ExportUtils {

    /**
     * 中文导出标题
     */
    public static final LinkedHashMap<String, String> ScoreManScoringV1Titles_CH;

    /**
     * 英文
     */
    public static final LinkedHashMap<String, String> ScoreManScoringV1Titles_EN;


    static {

        ScoreManScoringV1Titles_CH = new LinkedHashMap<>();
        ScoreManScoringV1Titles_EN = new LinkedHashMap<>();

        ScoreManScoringV1Titles_CH.put("projectName", "项目名称");
        ScoreManScoringV1Titles_CH.put("companyName", "供应商名称");
        ScoreManScoringV1Titles_CH.put("indicatorDimensionType", "指标维度");
        ScoreManScoringV1Titles_CH.put("indicatorName", "指标名称");
        ScoreManScoringV1Titles_CH.put("categoryNames", "模型品类");
        ScoreManScoringV1Titles_CH.put("indicatorLogic", "打分逻辑");
        ScoreManScoringV1Titles_CH.put("pefScore", "指标评分值（百分制打分）");
        ScoreManScoringV1Titles_CH.put("score", "绩效得分");
        ScoreManScoringV1Titles_CH.put("ifEndScored", "项目已计算得分");
        ScoreManScoringV1Titles_CH.put("perStartMonth", "绩效开始月份");
        ScoreManScoringV1Titles_CH.put("perEndMonth", "绩效结束月份");
        ScoreManScoringV1Titles_CH.put("organizationName", "采购组织");
        ScoreManScoringV1Titles_CH.put("scoreNickName", "评分人");
        ScoreManScoringV1Titles_CH.put("comments", "打分说明");


        ScoreManScoringV1Titles_EN.put("projectName", "projectName");
        ScoreManScoringV1Titles_EN.put("companyName", "companyName");
        ScoreManScoringV1Titles_EN.put("indicatorDimensionType", "indicatorDimensionType");
        ScoreManScoringV1Titles_EN.put("indicatorName", "indicatorName");
        ScoreManScoringV1Titles_EN.put("categoryNames", "categoryNames");
        ScoreManScoringV1Titles_EN.put("indicatorLogic", "indicatorLogic");
        ScoreManScoringV1Titles_EN.put("pefScore", "pefScore");
        ScoreManScoringV1Titles_EN.put("score", "score");
        ScoreManScoringV1Titles_EN.put("ifEndScored", "ifEndScored");
        ScoreManScoringV1Titles_EN.put("perStartMonth", "perStartMonth");
        ScoreManScoringV1Titles_EN.put("perEndMonth", "perEndMonth");
        ScoreManScoringV1Titles_EN.put("organizationName", "organizationName");
        ScoreManScoringV1Titles_EN.put("scoreNickName", "scoreNickName");
        ScoreManScoringV1Titles_EN.put("comments", "comments");

    }


    /**
     * 根据当前环境语言的标题
     * @return
     */
    public static LinkedHashMap<String,String> getScoreManScoringV1Titles(){
        String localeKey = LocaleHandler.getLocaleKey();
        switch (localeKey){
            case "en_US":
                return ScoreManScoringV1Titles_EN;
            default:
                return ScoreManScoringV1Titles_CH;
        }
    }

}
