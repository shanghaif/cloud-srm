package com.midea.cloud.srm.ps.advance.service;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.srm.model.pm.ps.advance.dto.AdvanceApplyQueryDTO;
import com.midea.cloud.srm.model.pm.ps.advance.dto.AdvanceApplySaveDTO;
import com.midea.cloud.srm.model.pm.ps.advance.entity.AdvanceApplyHead;
import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.pm.ps.http.FSSCResult;
import com.midea.cloud.srm.model.suppliercooperate.invoice.dto.OnlineInvoiceSaveDTO;
import com.midea.cloud.srm.model.suppliercooperate.invoice.dto.OnlineInvoiceVo;
import com.midea.cloud.srm.model.suppliercooperate.invoice.entity.OnlineInvoiceAdvance;

import java.util.List;

/**
*  <pre>
 *  预付款申请头表 服务类
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-20 13:40:24
 *  修改内容:
 * </pre>
*/
public interface IAdvanceApplyHeadService extends IService<AdvanceApplyHead> {

    /**
     * 暂存
     *
     * @param advanceApplySaveDTO
     */
    Long saveTemporary(AdvanceApplySaveDTO advanceApplySaveDTO, String advanceApplyStatus);

    /**
     * 提交
     * @param advanceApplySaveDTO
     */
    FSSCResult submit(AdvanceApplySaveDTO advanceApplySaveDTO, String advanceApplyStatus);

    /**
     * 获取预付款申请详情
     * @param advanceApplyHeadId
     * @return
     */
    AdvanceApplySaveDTO getAdvanceApplySaveDTO(Long advanceApplyHeadId);

    /**
     * 条件分页查询
     * @param advanceApplyQueryDTO
     * @return
     */
    PageInfo<AdvanceApplyHead> listPageByParam(AdvanceApplyQueryDTO advanceApplyQueryDTO);

    /**
     * 根据预付款申请头ID删除
     * @param advanceApplyHeadId
     */
    void deleteByAdvanceApplyHeadId(Long advanceApplyHeadId);

    void setQuote(OnlineInvoiceAdvance onlineInvoiceAdvance, String ifQuote);

    OnlineInvoiceSaveDTO advanceReturn(List<AdvanceApplyHead> advanceApplyHeads);

    FSSCResult abandon(Long advanceApplyHeadId);

}
