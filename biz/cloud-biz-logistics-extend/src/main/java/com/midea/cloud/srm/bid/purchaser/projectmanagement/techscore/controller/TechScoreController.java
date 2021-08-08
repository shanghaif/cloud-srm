package com.midea.cloud.srm.bid.purchaser.projectmanagement.techscore.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.techscore.service.ITechScoreHeadService;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.techscore.service.ITechScoreLineService;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.techscore.entity.TechScoreHead;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.techscore.param.TechScoreQueryParam;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.techscore.param.TechScoreSaveHeadParam;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.techscore.vo.TechScoreHeadVO;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.techscore.vo.TechScoreLineVO;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * 技术评分
 * </pre>
 *
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020年3月19日 下午6:45:30
 *  修改内容:
 *          </pre>
 */
@RestController
@RequestMapping("/techScore")
public class TechScoreController extends BaseController {

    @Autowired
    private ITechScoreHeadService iTechScoreHeadService;

    @Autowired
    private ITechScoreLineService iTechScoreLineService;

    /**
     * 技术评分分页列表
     */
    @GetMapping("/listPage")
    public PageInfo<TechScoreHeadVO> listPage(TechScoreQueryParam queryParam) {
        queryParam.setOperateUserId(AppUserUtil.getLoginAppUser().getUserId());
        return iTechScoreHeadService.listPage(queryParam);
    }

    /**
     * 技术评分行列表
     */
    @GetMapping("/queryTechScoreLineList")
    public List<TechScoreLineVO> queryTechScoreLineList(@RequestParam Long bidVendorId) {
        return iTechScoreLineService.queryTechScoreLineList(bidVendorId);
    }

    @GetMapping("/queryTechScoreInfo")
    public Map<String, Object> queryTechScoreInfo(@RequestParam Long bidVendorId) {
        List<TechScoreLineVO> techScoreLineVOS = iTechScoreLineService.queryTechScoreLineList(bidVendorId);
        TechScoreHead one = iTechScoreHeadService.getOne(Wrappers.lambdaQuery(TechScoreHead.class)
                .select(TechScoreHead::getTechComments)
                .eq(TechScoreHead::getBidVendorId, bidVendorId).last("limit 1")
        );
        String techComments = one == null ? "" : one.getTechComments();
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("techScoreLineVOS", techScoreLineVOS);
        resultMap.put("techComments", techComments);
        return resultMap;
    }

    /**
     * 保存or提交
     */
    @PostMapping("/save")
    public void save(@RequestBody TechScoreSaveHeadParam param) {
        param.setOperateUserId(AppUserUtil.getLoginAppUser().getUserId());
        param.setOperateUsername(AppUserUtil.getUserName());
        iTechScoreHeadService.saveOrUpdateTechScore(param);
    }


    /**
     * 代理 - 技术评分分页列表（需要前端提供operateUserId）
     */
    @GetMapping("/proxyListPage")
    public PageInfo<TechScoreHeadVO> proxyListPage(TechScoreQueryParam queryParam) {
        return iTechScoreHeadService.listPage(queryParam);
    }

    /**
     * 代理 - 保存or提交（需要前端提供operateUserId）
     */
    @PostMapping("/proxySave")
    public void proxySave(@RequestBody TechScoreSaveHeadParam param) {
        param.setProxyOperateUserId(AppUserUtil.getLoginAppUser().getUserId());
        param.setProxyOperateUsername(AppUserUtil.getUserName());
        iTechScoreHeadService.saveOrUpdateTechScore(param);
    }
}
