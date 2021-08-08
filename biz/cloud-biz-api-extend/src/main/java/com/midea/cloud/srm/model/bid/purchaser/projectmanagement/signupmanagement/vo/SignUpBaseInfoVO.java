package com.midea.cloud.srm.model.bid.purchaser.projectmanagement.signupmanagement.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * <pre>
 * 报名管理-供应商基本信息 视图对象
 * </pre>
 *
 * @author fengdc3@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-4-7 17:36:03
 *  修改内容:
 * </pre>
 */
@Data
public class SignUpBaseInfoVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String companyName;//企业名称

    private String companyType;//企业性质

    private LocalDate companyCreationDate;//企业成立日期

    private String overseasRelationName;//境外关系名称

    private String legalPerson;//法定代表人

    private String registeredCapital;//注册资金

    private String companyProvince;//营业地址（省份/州）

    private String companyCity;//营业地址（城市）

    private String address;//营业地址

    private String businessScope;//经营范围

    private LocalDate businessStartDate;//营业日期开始

    private LocalDate businessEndDate;//营业日期结束

}
