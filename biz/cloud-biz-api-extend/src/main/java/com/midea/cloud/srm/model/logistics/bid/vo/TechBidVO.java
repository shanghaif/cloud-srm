package com.midea.cloud.srm.model.logistics.bid.vo;

import com.midea.cloud.srm.model.logistics.bid.entity.LgtBidShipPeriod;
import com.midea.cloud.srm.model.logistics.bid.entity.LgtVendorFile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 *  技术标管理详情
 * </pre>
 *
 * @author chenwj92@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人: chenwj92
 *  修改日期: 2021/1/12 19:11
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TechBidVO implements Serializable {
    private static final long serialVersionUID = 1L;
    //供应商船期明细表
    private List<LgtBidShipPeriod> bidShipPeriodList;
    //供方必须上传附件信息
    private List<LgtVendorFile> lgtVendorFiles;
    // 技术评选结论
    private String technoSelection;
}
