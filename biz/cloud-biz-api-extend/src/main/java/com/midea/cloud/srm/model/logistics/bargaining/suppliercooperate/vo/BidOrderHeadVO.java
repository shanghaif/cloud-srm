package com.midea.cloud.srm.model.logistics.bargaining.suppliercooperate.vo;

import com.midea.cloud.srm.model.logistics.bargaining.purchaser.projectmanagement.bidinitiating.entity.BidFileConfig;
import com.midea.cloud.srm.model.logistics.bargaining.purchaser.projectmanagement.techproposal.entity.OrderHeadFile;
import lombok.Data;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * <pre>
 *  投标--供应商端--页面详情
 * </pre>
 *
 * @author zhuomb1@midea.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/4/3 10:41
 *  修改内容:
 * </pre>
 */
@Data
public class BidOrderHeadVO extends SupplierBidingVO {


    /** 投标界面投标单号 */
    private String bidOrderNum;
    /** 文件路径 */
    private String filePath;

    /** 判断是否满足投标返回的信息 */
    private String message;

    /** 投标提交时间 */
    private Date submitTime;
    /** 撤回时间 */
    private Date withDrawTime;
    /** 撤回原因 */
    private String withDrawReason;
    /** 作废时间 */
    private Date rejectTime;
    /** 作废原因 */
    private String rejectReason;

    /** 企业编码 */
    private String companyCode;
    /** 组织编码 */
    private String organizationCode;

    /** 对应页面的商务信息 */
    @Valid
    private List<BidOrderLineVO> orderLines;

    /** 对应页面的技术信息 */
    private List<BidVendorFileVO> files;
    /**
     * 需要上传的配置文件
     */
    private List<BidFileConfig> configFiles;
    /**
     * 报价头文件
     */
    private List<OrderHeadFile> orderHeadFiles;
    /** 文件上传ID */
//    List<Long> fileUploadIds;


    /* ===================== 定制化字段 ===================== */

    private String isProxyBidding;	// 是否代理报价（Y / N）
    private String pricingType;     // 报价类型
    private String isSeaFoodFormula;
}

