package com.midea.cloud.common.enums.bargaining.projectmanagement.projectpublish;

/**
 * <pre>
 * 招标项目状态 字典码:BIDDING_PRO_STATUS
 * </pre>
 *
 * @author fengdc3@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-04-03 14:53:20
 *  修改内容:
 * </pre>
 */
public enum BiddingProjectStatus {

    DRAW_UP("拟定", "DRAW_UP"),
    UNPUBLISHED("未发布", "UNPUBLISHED"),
    ACCEPT_SIGNUP("接受报名中", "ACCEPT_SIGNUP"),
    ACCEPT_BID("接受投标中", "ACCEPT_BID"),
    TENDER_ENDING("投标截止 ", "TENDER_ENDING"),
    TECHNICAL_EVALUATION("技术评标", "TECHNICAL_EVALUATION"),
    BUSINESS_EVALUATION("商务评标", "BUSINESS_EVALUATION"),
    PUBLICITY_OF_RESULT("结果已公示", "PUBLICITY_OF_RESULT"),
    PRICED("已定价", "PRICED"),
    PROJECT_END("已结项", "PROJECT_END");

    private String name;
    private String value;

    private BiddingProjectStatus(String name, String value) {
        this.name = name;
        this.value = value;
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

    /**
     * 通过指定value值获取枚举
     *
     * @param value
     * @return
     */
    public static BiddingProjectStatus get(String value) {
        for (BiddingProjectStatus o : BiddingProjectStatus.values()) {
            if (o.value.equals(value)) {
                return o;
            }
        }
        return null;
    }

    /**
     * 枚举值列表是否包含指定code
     *
     * @param code
     * @return true or false
     */
    public static boolean isContain(String code) {
        return (get(code) != null);
    }

}
