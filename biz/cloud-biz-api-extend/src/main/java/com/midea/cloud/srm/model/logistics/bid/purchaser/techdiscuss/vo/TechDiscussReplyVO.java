package com.midea.cloud.srm.model.logistics.bid.purchaser.techdiscuss.vo;

import com.midea.cloud.srm.model.logistics.bid.purchaser.techdiscuss.entity.TechDiscussProjInfo;
import com.midea.cloud.srm.model.logistics.bid.suppliercooperate.vo.BidVendorFileVO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author fengdc3@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-3-12 11:21
 *  修改内容:
 * </pre>
 */
@Data
public class TechDiscussReplyVO extends TechDiscussProjInfo {

    private static final long serialVersionUID = 1L;

    private Long replyId;

    /**
     * 回复单编码
     */
    private String replyCode;
    /**
     * 供应商编码
     */
    private String supplierCode;

    /**
     * 供应商名称
     */
    private String supplierName;
    /**
     * 联系人
     */
    private String contactName;

    /**
     * 电话(移动电话)
     */
    private String telephone;

    /**
     * 邮箱
     */
    private String email;

    /** 技术交流项目状态 */
    private String projectStatus;

    /**
     * 税率
     */
    private BigDecimal tax;

    /** 附件保存的时候，在附件表的附件类型字段 */
    private String vendorFileType ;

    /** 技术交流回复--技术交流附件 */
    private List<BidVendorFileVO> files;
}
