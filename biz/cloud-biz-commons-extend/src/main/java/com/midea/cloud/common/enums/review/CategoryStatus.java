package com.midea.cloud.common.enums.review;

/**
 * <pre>
 *  功能名称描述:  品类状态  参考字典码:CATEGORY_STATUS
 * </pre>
 *
 * @author chensl26@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-3-24 14:39
 *  修改内容:
 * </pre>
 */
public enum CategoryStatus {

    APPLICATION,        //申请中
    QUALIFIED,          //合格
    SUSPEND,            //暂停
    TERMINATION,        //终止

    //ceea
    VERIFY,//认证中
    YELLOW,//黄牌
    RED,//红牌
    ONE_TIME,//一次性
    REGISTERED,//注册
    BLACK,//黑牌
    GREEN;//绿牌
}
