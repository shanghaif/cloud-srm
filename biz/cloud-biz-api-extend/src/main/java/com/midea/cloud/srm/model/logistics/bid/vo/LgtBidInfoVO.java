package com.midea.cloud.srm.model.logistics.bid.vo;

import com.midea.cloud.srm.model.logistics.bid.entity.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author chenwj92@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人: chenwj92
 *  修改日期: 2021/1/12 14:09
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LgtBidInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;
    //招标项目基础信息
    private LgtBiding biding;
    //招标项目附件信息
    private List<LgtBidFile> bidfileList;
    //供方必须上传附件信息
    private List<LgtFileConfig> bidFileConfigList;
    //需求明细表
    private List<LgtBidRequirementLine> bidRequirementLineList;
    //供应商报价信息 船期信息 附件信息
    private List<LgtVendorQuotedInfoVO> vendorQuotedInfoList;
    //投标结果
    private List<LgtVendorQuotedSum> vendorQuotedSumList;
    //邀请供应商的信息
    private List<LgtVendorVO> vendorList;

}
