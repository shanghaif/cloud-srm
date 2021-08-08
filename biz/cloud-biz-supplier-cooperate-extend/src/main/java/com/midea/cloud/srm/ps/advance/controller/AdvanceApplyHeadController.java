package com.midea.cloud.srm.ps.advance.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.pm.ps.AdvanceApplyStatus;
import com.midea.cloud.common.enums.pm.ps.FSSCResponseCode;
import com.midea.cloud.common.enums.pm.ps.FsscResponseType;
import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.pm.ps.advance.dto.AdvanceApplyQueryDTO;
import com.midea.cloud.srm.model.pm.ps.advance.dto.AdvanceApplySaveDTO;
import com.midea.cloud.srm.model.pm.ps.advance.entity.AdvanceApplyHead;
import com.midea.cloud.srm.model.pm.ps.http.FSSCResult;
import com.midea.cloud.srm.model.suppliercooperate.invoice.dto.OnlineInvoiceSaveDTO;
import com.midea.cloud.srm.model.suppliercooperate.invoice.dto.OnlineInvoiceVo;
import com.midea.cloud.srm.model.suppliercooperate.invoice.entity.OnlineInvoiceAdvance;
import com.midea.cloud.srm.ps.advance.service.IAdvanceApplyHeadService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *  <pre>
 *  预付款申请头表 前端控制器
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
@RestController
@RequestMapping("/ps/advanceApplyHead")
public class AdvanceApplyHeadController extends BaseController {

    @Autowired
    private IAdvanceApplyHeadService iAdvanceApplyHeadService;

    /**
     * 条件分页查询
     * @param advanceApplyQueryDTO
     * @return
     */
    @PostMapping("/listPageByParam")
    public PageInfo<AdvanceApplyHead> listPageByParam(@RequestBody AdvanceApplyQueryDTO advanceApplyQueryDTO) {
        return iAdvanceApplyHeadService.listPageByParam(advanceApplyQueryDTO);
    }

    /**
     * 暂存
     * @param advanceApplySaveDTO
     */
    @PostMapping("/saveTemporary")
    public Long saveTemporary(@RequestBody AdvanceApplySaveDTO advanceApplySaveDTO) {
        return iAdvanceApplyHeadService.saveTemporary(advanceApplySaveDTO, AdvanceApplyStatus.DRAFT.getValue());
    }

    /**
     * 提交
     * @param advanceApplySaveDTO
     */
//    @PostMapping("/submit")
//    public BaseResult submit(@RequestBody AdvanceApplySaveDTO advanceApplySaveDTO) {
//        FSSCResult fsscResult = iAdvanceApplyHeadService.submit(advanceApplySaveDTO, AdvanceApplyStatus.UNDER_APPROVAL.getValue());
//        BaseResult baseResult = BaseResult.buildSuccess();
////        if (FSSCResponseCode.WARN.getCode().equals(fsscResult.getCode())) {
////            baseResult.setCode(fsscResult.getCode());
////            baseResult.setMessage(fsscResult.getMsg());
////            baseResult.setData(fsscResult.getType());
////        }
//        return baseResult;
//    }


    /**
     * 提交
     * @param advanceApplySaveDTO
     */
    @PostMapping("/submit")
    public BaseResult submit(@RequestBody AdvanceApplySaveDTO advanceApplySaveDTO) {
        Long advanceApplyId = iAdvanceApplyHeadService.submit(advanceApplySaveDTO, AdvanceApplyStatus.UNDER_APPROVAL.getValue());
        BaseResult baseResult = BaseResult.buildSuccess();
        baseResult.setData(advanceApplyId);
//        if (FSSCResponseCode.WARN.getCode().equals(fsscResult.getCode())) {
//            baseResult.setCode(fsscResult.getCode());
//            baseResult.setMessage(fsscResult.getMsg());
//            baseResult.setData(fsscResult.getType());
//        }
        return baseResult;
    }

    /**
     * 获取预付款申请详情
     * @param advanceApplyHeadId
     * @return
     */
    @GetMapping("/getAdvanceApplySaveDTO")
    public AdvanceApplySaveDTO getAdvanceApplySaveDTO(@RequestParam Long advanceApplyHeadId) {
        return iAdvanceApplyHeadService.getAdvanceApplySaveDTO(advanceApplyHeadId);
    }

    /**
     * 根据预付款申请头ID删除
     * @param advanceApplyHeadId
     */
    @GetMapping("/deleteByAdvanceApplyHeadId")
    public void deleteByAdvanceApplyHeadId(@RequestParam Long advanceApplyHeadId) {
        iAdvanceApplyHeadService.deleteByAdvanceApplyHeadId(advanceApplyHeadId);
    }

    /**
     * 预付款退回
     * @param advanceApplyHeads
     * @return
     */
    @PostMapping("/advanceReturn")
    public OnlineInvoiceSaveDTO advanceReturn (@RequestBody List<AdvanceApplyHead> advanceApplyHeads) {
        OnlineInvoiceSaveDTO onlineInvoiceSaveDTO = iAdvanceApplyHeadService.advanceReturn(advanceApplyHeads);
        return onlineInvoiceSaveDTO;
    }

    /**
     * 作废
     * @param advanceApplyHeadId
     * @return
     */
    @GetMapping("/abandon")
    public BaseResult abandon(@RequestParam Long advanceApplyHeadId) {
        FSSCResult fsscResult = iAdvanceApplyHeadService.abandon(advanceApplyHeadId);
        BaseResult baseResult = BaseResult.buildSuccess();
        if (StringUtils.isNotBlank(fsscResult.getCode()) && StringUtils.isNotBlank(fsscResult.getMsg())) {
            baseResult.setCode(fsscResult.getCode());
            baseResult.setMessage(fsscResult.getMsg());
        }
        return baseResult;
    }

//    /**
//     * 设置网上开票是否引用标记(弃用)
//     * @param onlineInvoiceAdvance
//     * @param ifQuote
//     */
//    @PostMapping("/setQuote")
//    public void setQuote(@RequestBody OnlineInvoiceAdvance onlineInvoiceAdvance, @RequestParam("ifQuote") String ifQuote) {
//        iAdvanceApplyHeadService.setQuote(onlineInvoiceAdvance, ifQuote);
//    }




}
