package com.midea.cloud.srm.base.clarify.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.ObjectUtil;
import com.midea.cloud.srm.base.clarify.service.IClarifyService;
import com.midea.cloud.srm.feign.bargaining.BargainSourcingClarifyClient;
import com.midea.cloud.srm.feign.base.IGetSourcingRelateInfo;
import com.midea.cloud.srm.feign.bid.BidClient;
import com.midea.cloud.srm.feign.bid.BidSourcingClarifyClient;
import com.midea.cloud.srm.model.base.clarify.dto.SourcingClarifyDto;
import com.midea.cloud.srm.model.base.clarify.dto.SourcingClarifyQueryDto;
import com.midea.cloud.srm.model.base.clarify.dto.SourcingInfoQueryDto;
import com.midea.cloud.srm.model.base.clarify.entity.SourcingClarify;
import com.midea.cloud.srm.model.base.clarify.enums.ClarifySourcingType;
import com.midea.cloud.srm.model.base.clarify.enums.ClarifyStatus;
import com.midea.cloud.srm.model.base.clarify.vo.SourcingClarifyDetailVO;
import com.midea.cloud.srm.model.base.clarify.vo.SourcingClarifyReplyDetailVO;
import com.midea.cloud.srm.model.base.clarify.vo.SourcingClarifyVO;
import com.midea.cloud.srm.model.base.clarify.vo.SourcingInfoVO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author tanjl11
 * @date 2020/10/07 13:51
 */
@RestController
@RequestMapping("/clarify/buyer")
@AllArgsConstructor
public class ClarifyBuyerController {

    private final IClarifyService clarifyService;

    private final BidSourcingClarifyClient bidClient;

    private final BargainSourcingClarifyClient bargainClient;

    /**
     * 查看公告列表
     *
     * @param queryDto
     * @return
     */
    @PostMapping("/queryClarifyList")
    public PageInfo<SourcingClarifyVO> listSourcingClarifyByPage(@RequestBody SourcingClarifyQueryDto queryDto) {
        return clarifyService.listSourcingClarifyByPage(queryDto);
    }

    /**
     * 查看公告详情
     */
    @PostMapping("/queryReplyDetailsByClarifyId")
    public SourcingClarifyVO queryReplyDetailsByClarifyId(@RequestParam("clarifyId") Long clarifyId) {
        return clarifyService.queryClarifyDetailById(clarifyId);
    }

    /**
     * 查看供应商回复详情
     */
    @PostMapping("/queryReplyDetailByReplyId")
    public SourcingClarifyReplyDetailVO queryReplyDetailByReplyId(@RequestParam("replyId") Long clarifyReplyId) {
        return clarifyService.queryReplyDetailByReplyId(clarifyReplyId);
    }

    /**
     * 发布澄清公告
     *
     * @return
     */
    @PostMapping("/publish")
    public SourcingClarifyDetailVO publish(@RequestBody SourcingClarifyDto dto) {
        ObjectUtil.validate(dto);
        return clarifyService.publishSourcingClarify(dto);
    }

    /**
     * 发布澄清公告列表
     */
    @GetMapping("/publishById")
    public Boolean publishById(@RequestParam("clarifyId") Long clarifyId) {
        return clarifyService.publishSourcingClarifyById(clarifyId);
    }

    /**
     * 暂存澄清公告
     */
    @PostMapping("/saveTemporary")
    public SourcingClarifyDetailVO saveTemporary(@RequestBody SourcingClarifyDto dto) {
        return clarifyService.saveTemporarySourcingClarify(dto, ClarifyStatus.UNPUBLISH);
    }

    /**
     * 删除
     */
    @GetMapping("/deleteById")
    public Boolean deleteSourcingClarifyById(@RequestParam("clarifyId") Long sourcingClarifyId) {
        return clarifyService.deleteSourcingClarifyById(sourcingClarifyId);
    }

    @PostMapping("/getSourcingInfo")
    public PageInfo<SourcingInfoVO> getSourcingInfo(@RequestBody SourcingInfoQueryDto queryDto) {
        if (StringUtils.isEmpty(queryDto.getSourcingType())) {
            throw new BaseException("寻源类型不能为空");
        }
        IGetSourcingRelateInfo querySourcingInfo = null;

        switch (queryDto.getSourcingType()) {
            case "RFQ":
                querySourcingInfo = bargainClient;
                break;
            case "TENDER":
                querySourcingInfo= bidClient;
                break;
            default:
                throw new BaseException("暂不支持的寻源方式");
        }
        return querySourcingInfo.querySourcingInfo(queryDto);

    }

    @GetMapping("/getSourcingType")
    public List<Map<String, Object>> getSourcingType() {
        List<Map<String, Object>> list = new ArrayList<>();
        for (ClarifySourcingType value : ClarifySourcingType.values()) {
            Map<String, Object> map = new HashMap();
            map.put("label", value.getItemName());
            map.put("value", value.getItemValue());
            list.add(map);
        }
        return list;
    }
}
