package com.midea.cloud.srm.perf.scoring.constants;

/**
 * <pre>
 *  绩效供应商反馈状态
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021/2/5 9:41
 *  修改内容:
 * </pre>
 */
public enum VendorFeedbackStatus {

    NOT_CONFIRMED("未确认","NOT_CONFIRMED"),
    CONFIRMED("已确认","CONFIRMED");


    private String name;
    private String value;

    private VendorFeedbackStatus(String name, String value){
        this.name = name;
        this.value = value;
    }

    /**根据value获取枚举值**/
    private static VendorFeedbackStatus get(String value){
        for(VendorFeedbackStatus vendorFeedbackStatus: VendorFeedbackStatus.values()){
            if(vendorFeedbackStatus.value.equals(value)){
                return vendorFeedbackStatus;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
