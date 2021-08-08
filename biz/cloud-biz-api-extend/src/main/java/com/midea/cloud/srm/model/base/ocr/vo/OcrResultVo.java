package com.midea.cloud.srm.model.base.ocr.vo;

import lombok.Data;

import java.util.Map;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author zhuwl7@midea.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-4-2 14:46
 *  修改内容:
 * </pre>
 */
@Data
public class OcrResultVo {
    /**
     * 类型
     */
    private String type;

    /**
     * 住所/经营场所/主要经营场所/营业场所
     */
    private String biz_license_address;

    /**
     * 名称
     */
    private String biz_license_company_name;

    /**
     *统一社会信用代码
     */
    private String biz_license_credit_code;

    /**
     *营业期限
     */
    private String biz_license_operating_period;

    /**
     *法定代表人/负责人/经营者/经营者姓名
     */
    private String biz_license_owner_name;

    /**
     *注册资本
     */
    private String biz_license_reg_capital;

    /**
     * 经营范围
     */
    private String biz_license_scope;

    /**
     *证照编号
     */
    private String biz_license_serial_number;

    /**
     *成立日期/注册日期
     */
    private String biz_license_start_time;

    /**
     * 识别耗时
     */
    private String time_cost;



}
