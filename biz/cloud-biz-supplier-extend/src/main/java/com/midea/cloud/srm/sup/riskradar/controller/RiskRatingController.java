package com.midea.cloud.srm.sup.riskradar.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.supplier.riskradar.dto.RiskRadarDto;
import com.midea.cloud.srm.model.supplier.riskradar.entity.RiskProblemLog;
import com.midea.cloud.srm.sup.riskradar.mapper.RiskProblemLogMapper;
import com.midea.cloud.srm.sup.riskradar.service.IRiskRatingService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 *  <pre>
 *  风险评级表 前端控制器
 * </pre>
 *
 * @author chensl26@meicloud.com
 * @version 1.00.00
 *
 *  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-05 14:14:17
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/risk-rating")
public class RiskRatingController extends BaseController {

    @Autowired
    private IRiskRatingService iRiskRatingService;
    @Resource
    private RiskProblemLogMapper riskProblemLogMapper;

    /**
     * 获取风险雷达数据
     * @param vendorId
     */
    @GetMapping("/getRiskRadarDto")
    public RiskRadarDto getRiskRadarDto(@Param("vendorId") Long vendorId) {
        Assert.notNull(vendorId, "vendorId不能为空");
        return iRiskRatingService.getRiskRadarDto(vendorId);
    }

    /**
     * 获取风险雷达数据
     * @param riskProblemLog
     */
    @PostMapping("/saveRiskProblemLog")
    public void saveRiskProblemLog(@RequestBody RiskProblemLog riskProblemLog) {
        Long id = IdGenrator.generate();
        riskProblemLog.setProblemId(id);
        riskProblemLogMapper.insert(riskProblemLog);
    }

    /**
     * 获取风险雷达数据
     * @param vendorId
     */
    @GetMapping("/getRiskProblemLogList")
    public List<RiskProblemLog> getRiskProblemLogList(@RequestParam("vendorId") Long vendorId) {
        Objects.nonNull(vendorId);
        return riskProblemLogMapper.selectList(Wrappers.lambdaQuery(RiskProblemLog.class).eq(RiskProblemLog::getVendorId,vendorId));
    }

}
