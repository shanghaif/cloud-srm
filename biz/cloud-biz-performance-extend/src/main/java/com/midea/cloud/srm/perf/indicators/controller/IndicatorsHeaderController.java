package com.midea.cloud.srm.perf.indicators.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.perf.inditors.dto.IndicatorsDTO;
import com.midea.cloud.srm.model.perf.inditors.dto.IndicatorsHeaderDTO;
import com.midea.cloud.srm.model.perf.inditors.entity.IndicatorsHeader;
import com.midea.cloud.srm.perf.indicators.service.IIndicatorsHeaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

/**
 *  <pre>
 *  指标库头表 前端控制器
 * </pre>
 *
 * @author wuwl18@meiCloud.com
 * @version 1.00.00
 *
 *  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-26 11:35:36
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/perf/indicatorsHeader")
public class IndicatorsHeaderController extends BaseController {

    @Autowired
    private IIndicatorsHeaderService iIndicatorsHeaderService;

    /**
     * Description 分页查询指标库头表信息
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.05.26
     **/
    @PostMapping("/findIndicatorsHeaderPage")
    public PageInfo<IndicatorsHeader> listIndicatorsHeaderPage(@RequestBody IndicatorsHeader indicatorsHeader) {
        PageUtil.startPage(indicatorsHeader.getPageNum(), indicatorsHeader.getPageSize());
        QueryWrapper<IndicatorsHeader> wrapper = new QueryWrapper<IndicatorsHeader>(indicatorsHeader);
        wrapper.orderByDesc("LAST_UPDATE_DATE");
        return new PageInfo<IndicatorsHeader>(iIndicatorsHeaderService.list(wrapper));
    }

    /**
     * Description 根据ID获取指标库投头和行表信息
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.05.26
     **/
    @GetMapping("/getIndicatorsHeaderById")
    public IndicatorsHeaderDTO getIndicatorsHeaderById(Long indicatorHeadId) {
        Assert.notNull(indicatorHeadId, "id不能为空");
        IndicatorsHeaderDTO indicatorsHeaderDTO = new IndicatorsHeaderDTO();
        indicatorsHeaderDTO.setIndicatorHeadId(indicatorHeadId);
        return iIndicatorsHeaderService.findIndicationHeadAndLineList(indicatorsHeaderDTO);
    }

    /**
     * Description 根据指标头ID删除指标头和行信息
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.05.26
     * @throws com.midea.cloud.common.exception.BaseException
     **/
    @PostMapping("/delIndicationHeaderAndLine")
    @PreAuthorize("hasAuthority('pef:indicators:delete')")
    public BaseResult<String> delIndicationHeaderAndLine(@RequestBody IndicatorsHeader indicatorsHeader) {
        Assert.notNull(indicatorsHeader, ResultCode.MISSING_SERVLET_REQUEST_PARAMETER.getMessage());
        return iIndicatorsHeaderService.delIndicationHeaderAndLine(indicatorsHeader.getIndicatorHeadId());
    }

    /**
     * Description 禁用/启动指标头和行信息
     * @param indicatorsHeader 指标头对象(indicatorHeadId 指标头ID,enableFlag 禁用/启动)
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.05.27
     * @throws com.midea.cloud.common.exception.BaseException
     **/
    @PostMapping("/updateIndicatorHeadAndLineEnable")
    @PreAuthorize("hasAuthority('pef:indicators:enable')")
    public BaseResult<String> updateIndicatorHeadAndLineEnable(@RequestBody IndicatorsHeader indicatorsHeader) {
        Assert.notNull(indicatorsHeader, ResultCode.MISSING_SERVLET_REQUEST_PARAMETER.getMessage());
        return iIndicatorsHeaderService.updateIndicatorHeadAndLineEnable(indicatorsHeader.getIndicatorHeadId(), indicatorsHeader.getEnableFlag());
    }

    /**
     * Description 新增指标头和行信息
     * @Param indicatorsDTO
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.05.27
     * @throws com.midea.cloud.common.exception.BaseException
     **/
    @PostMapping("/saveIndicatorHeadAndLine")
    @PreAuthorize("hasAuthority('pef:indicators:add')")
    public BaseResult<String> saveIndicatorHeadAndLine(@RequestBody IndicatorsDTO indicatorsDTO) {
        return iIndicatorsHeaderService.saveOrUpdateIndicatorHeadAndLine(indicatorsDTO);
    }

    /**
     * Description 修改指标头和行信息
     * @Param indicatorsDTO
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.05.27
     * @throws com.midea.cloud.common.exception.BaseException
     **/
    @PostMapping("/updateIndicatorHeadAndLine")
    @PreAuthorize("hasAuthority('pef:indicators:add')")
    public BaseResult<String> updateIndicatorHeadAndLine(@RequestBody IndicatorsDTO indicatorsDTO) {
        return iIndicatorsHeaderService.saveOrUpdateIndicatorHeadAndLine(indicatorsDTO);
    }

}
