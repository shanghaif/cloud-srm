package com.midea.cloud.srm.model.base.ocr.vo;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

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
 *  修改日期: 2020-4-3 15:10
 *  修改内容:
 * </pre>
 */
@Data
public class CompanyResultVo {
    /**
     * 公司名称
     */
    private String companyName;
    /**
     * 法定代表人
     */
    private String legalPerson;
    /**
     * 社会统一信用
     */
    private String lcCode;
    /**
     * 注册资本
     */
    private String registeredCapital;
    /**
     * 注册币种
     */
    private String registCurrency;
    /**
     * 公司地址
     */
    private String companyAddress;
    /**
     * 公司创建日期
     */
    private Date companyCreationDate;
    /**
     * 营业范围
     */
    private String businessScope;
    /**
     * 注册机关
     */
    private String registrationAuthority;
    /**
     * 营业日期开始
     */
    private Date businessStartDate;
    /**
     * 营业日期结束
     */
    private Date businessEndDate;
}
