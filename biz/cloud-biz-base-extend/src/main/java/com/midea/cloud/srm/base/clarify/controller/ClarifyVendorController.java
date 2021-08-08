package com.midea.cloud.srm.base.clarify.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.ObjectUtil;
import com.midea.cloud.srm.base.clarify.service.IClarifyService;
import com.midea.cloud.srm.model.base.clarify.dto.SourcingClarifyQueryDto;
import com.midea.cloud.srm.model.base.clarify.dto.SourcingClarifyReplyDto;
import com.midea.cloud.srm.model.base.clarify.enums.ClarifySourcingType;
import com.midea.cloud.srm.model.base.clarify.enums.ClarifyStatus;
import com.midea.cloud.srm.model.base.clarify.vo.SourcingClarifyDetailVO;
import com.midea.cloud.srm.model.base.clarify.vo.SourcingClarifyReplyDetailVO;
import com.midea.cloud.srm.model.base.clarify.vo.SourcingClarifyVO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tanjl11
 * @date 2020/10/07 13:53
 * 供应商可用功能
 */
@RestController
@RequestMapping("/clarify/vendor")
@AllArgsConstructor
public class ClarifyVendorController {

    private final IClarifyService clarifyService;

    /**
     * 查看公告列表
     *
     * @param queryDto
     * @return
     */
    @PostMapping("/queryReplyList")
    public PageInfo<SourcingClarifyVO> listSourcingClarifyByPage(@RequestBody SourcingClarifyQueryDto queryDto) {
        return clarifyService.listSourcingClarifyByPage(queryDto);
    }

    /**
     * 查看公告详情
     *
     * @param clarifyId
     * @return
     */
    @GetMapping("/queryReplyDetailById")
    public SourcingClarifyReplyDetailVO queryReplyDetailById(@RequestParam("clarifyId") Long clarifyId) {
        return clarifyService.queryReplyDetailById(clarifyId);
    }

    /**
     * 回复公告
     */
    @PostMapping("/publishReply")
    public SourcingClarifyReplyDetailVO publishReply(@RequestBody SourcingClarifyReplyDto replyDto) {
        ObjectUtil.validate(replyDto);
        return clarifyService.replySourcingClarify(replyDto);
    }

    /**
     * 保存回复
     */
    @PostMapping("/saveReply")
    public SourcingClarifyReplyDetailVO saveReply(@RequestBody SourcingClarifyReplyDto replyDto) {
        return clarifyService.saveTemporarySourcingClarifyReply(replyDto, ClarifyStatus.WAITRESPONSE);
    }

    /**
     * 获取寻源类型
     * @return
     */
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
