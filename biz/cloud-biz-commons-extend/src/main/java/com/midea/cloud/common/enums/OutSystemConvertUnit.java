package com.midea.cloud.common.enums;

/**
 * <pre>
 *   对接外部系统的单位转换
 * </pre>
 *
 * @author chensl26@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/11/22 15:26
 *  修改内容:
 * </pre>
 */
public enum OutSystemConvertUnit {

    FSSC_KG("KG", "千克"),
    FSSC_PCS("PCS", "个"),
    FSSC_M2("M2", "平方米"),
    FSSC_KM("KM", "千米"),
    FSSC_ML("ML", "毫升"),
    FSSC_KL("KL", "克拉"),
    FSSC_M3("M3", "立方米"),
    FSSC_MM("MM", "毫米"),
    FSSC_HR("HR", "Hour"),
    FSSC_RM("RM", "林吉特"),
    FSSC_GJ("GJ", "公斤");

    private String nsrmUnit;

    private String outSystemUnit;

    OutSystemConvertUnit(String nsrmUnit, String outSystemUnit) {
        this.nsrmUnit = nsrmUnit;
        this.outSystemUnit = outSystemUnit;
    }

    public String getNsrmUnit() {
        return nsrmUnit;
    }

    public void setNsrmUnit(String nsrmUnit) {
        this.nsrmUnit = nsrmUnit;
    }

    public String getOutSystemUnit() {
        return outSystemUnit;
    }

    public void setOutSystemUnit(String outSystemUnit) {
        this.outSystemUnit = outSystemUnit;
    }

    public static String automatch(String nsrmUnit){
        for(OutSystemConvertUnit thisEnum : values()){
            if(thisEnum.getNsrmUnit().equals(nsrmUnit)){
                return thisEnum.getOutSystemUnit();
            }
        }
        return "";
    }


}
