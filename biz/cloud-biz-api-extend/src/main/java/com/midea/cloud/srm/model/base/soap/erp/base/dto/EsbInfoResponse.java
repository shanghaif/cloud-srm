
package com.midea.cloud.srm.model.base.soap.erp.base.dto;


import lombok.Data;

/**
 * <pre>
 *  隆基总线返回值标注格式
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/24
 *  修改内容:
 * </pre>
 */
@Data
public class EsbInfoResponse {

    /**总线传值，原值返回*/
    private String instId;
    /**判断下若总线传值为空，则取系统时间，类似“2019-08-02 15:11:24:928”；若不为空，原值返回*/
    private String requestTime;
    /**固定值S或E,分别代表成功和失败 */
    private String returnStatus;
    /**自定义值 */
    private String returnCode;
    /** 成功或失败错误汉化说明返回*/
    private String returnMsg;
    /**取系统时间，精确到毫秒类似“2019-08-02 15:11:24:928”*/
    private String responseTime;
    /**备用字段1，暂时无值*/
    private String attr1;
    /**备用字段2，暂时无值*/
    private String attr2;
    /**备用字段3，暂时无值*/
    private String attr3;
}

