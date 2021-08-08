package com.midea.cloud.srm.model.bid.suppliercooperate.vo;

import lombok.Data;

/**
 * <pre>
 *  报名表和供方必须上传附件配置表相关字段信息
 * </pre>
 *
 * @author zhuomb1@midea.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/4/6 14:46
 *  修改内容:
 * </pre>
 */
@Data
public class BidVendorFileVO {

    /** 主键ID */
    private Long vendorFileId;
    /** 关联业务表单ID */
    private Long businessId;
    /** 招标ID */
    private Long bidingId;
    /** 供应商ID */
    private Long vendorId;
    /** 供方必须上传附件配置表ID(scc_bid_file_config) */
    private Long requireId;
    /** 附件类型 报名:Enroll,投标:Biding */
    private String fileType;

    /** 附件名称 */
    private String fileName;
    /** 附件大小 */
    private String fileSize;
    /** 文档中心ID */
    private String docId;
    /** 备注 */
    private String comments;
    /** 企业编码 */
    private String companyCode;
    /** 组织编码 */
    private String organizationCode;
    /** 供方必须上传附件配置表"附件名称说明(资料要求)"(scc_bid_file_config) */
    private String reqFileName;
}