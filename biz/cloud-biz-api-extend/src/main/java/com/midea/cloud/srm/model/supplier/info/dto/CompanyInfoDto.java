package com.midea.cloud.srm.model.supplier.info.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

import java.io.Serializable;

/**
 * <pre>
 * 供应商基本信息(供应商导入)
 * </pre>
 *
 * @author wangpr@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/7/2
 *  修改内容:
 * </pre>
 */
@Data
@ColumnWidth(20)
public class CompanyInfoDto implements Serializable {

    private static final long serialVersionUID = 4729179030348326821L;
    /**
     * 用户名
     */
    @ExcelProperty( value = "用户名",index = 0)
    private String username;

    /**
     * 企业名称
     */
    @ExcelProperty( value = "企业名称",index = 1)
    private String companyName;

    /**
     * 是否黑名单R
     */
    @ExcelProperty( value = "是否黑名单(Y/N)",index = 2)
    private String isBacklist;

    /**
     * 黑名单更新时间
     */
    @ExcelProperty( value = "黑名单更新时间",index = 3)
    private String backlistUpdatedDate;

    /**
     * 黑名单更新人
     */
    @ExcelProperty( value = "黑名单更新人",index = 4)
    private String backlistUpdatedBy;

    /**
     * 境内外关系
     */
    @ExcelProperty( value = "境内外关系",index = 5)
    private String overseasRelation;

    /**
     * 企业性质
     */
    @ExcelProperty( value = "企业性质",index = 6)
    private String companyType;

    /**
     * 注册资本(万元)
     */
    @ExcelProperty( value = "注册资本(万元)",index = 7)
    private String registeredCapital;

    /**
     * 注册资金币种
     */
    @ExcelProperty( value = "注册资金币种",index = 8)
    private String registCurrency;

    /**
     * 企业成立日期
     */
    @ExcelProperty( value = "成立日期",index = 9)
    private String companyCreationDate;

    /**
     * 企业简称
     */
    @ExcelProperty( value = "企业简称",index = 10)
    private String companyShortName;

    /**
     * DUNS编号
     */
    @ExcelProperty( value = "DUNS编号",index = 11)
    private String dunsCode;

    /**
     * 统一社会信用代码
     */
    @ExcelProperty( value = "统一社会信用代码",index = 12)
    private String lcCode;

    /**
     * 法定代表人
     */
    @ExcelProperty( value = "法定代表人",index = 13)
    private String legalPerson;

    /**
     * 登记机关
     */
    @ExcelProperty( value = "登记机关",index = 14)
    private String registrationAuthority;

    /**
     * 营业日期从
     */
    @ExcelProperty( value = "营业日期从",index = 15)
    private String businessStartDate;

    /**
     * 营业日期结束
     */
    @ExcelProperty( value = "营业日期至",index = 16)
    private String businessEndDate;

    /**
     * 营业地址（国家/地区）
     */
    @ExcelProperty( value = "营业地址（国家/地区）",index = 17)
    private String companyCountry;

    /**
     * 营业地址（省份/州）
     */
    @ExcelProperty( value = "省份/州",index = 18)
    private String companyProvince;

    /**
     * 营业地址（城市）
     */
    @ExcelProperty( value = "城市",index = 19)
    private String companyCity;

    /**
     * 详细地址
     */
    @ExcelProperty( value = "详细地址",index = 20)
    private String companyAddress;

    /**
     * 营业范围
     */
    @ExcelProperty( value = "营业范围",index = 21)
    private String businessScope;

    /**
     * 营业状态
     */
    @ExcelProperty( value = "营业状态",index = 22)
    private String companyStatus;

    /**
     * 准入日期
     */
    @ExcelProperty( value = "准入日期",index = 23)
    private String approvingDate;

    /**
     * 可供品类(,)
     */
    @ExcelProperty( value = "可供品类",index = 24)
    private String categoryRel;

    /**
     * 错误提示信息
     */
    @ExcelProperty( value = "错误提示信息",index = 25)
    private String errorMsg;
}
