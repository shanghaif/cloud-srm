package com.midea.cloud.srm.model.pm.ps.advance.dto;

import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.pm.ps.advance.entity.AdvanceApplyHead;
import com.midea.cloud.srm.model.pm.ps.advance.entity.AdvanceApplyLine;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.List;

/**
 * <pre>
 *  功能名称   预付款申请查询DTO
 * </pre>
 *
 * @author chensl26@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/20 13:59
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
public class AdvanceApplyQueryDTO extends AdvanceApplyHead{

    private List<Long> orgIds;//业务实体id集

    private List<Long> payAgentOrgIds;//代付方业务实体id集

    private LocalDate applyStartDate;//开始日期

    private LocalDate applyEndDate;//结束日期

    private String vendorParam;//供应商编码或供应商名称

    private String ifOnlineInvoice;//是否网上开票请求
}
