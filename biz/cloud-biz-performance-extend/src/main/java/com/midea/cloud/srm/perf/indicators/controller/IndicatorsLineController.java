package com.midea.cloud.srm.perf.indicators.controller;

import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.perf.inditors.entity.IndicatorsLine;
import com.midea.cloud.srm.perf.indicators.service.IIndicatorsLineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *  <pre>
 *  指标库行表 前端控制器
 * </pre>
 *
 * @author wuwl18@meiCloud.com
 * @version 1.00.00
 *
 *  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-26 11:35:37
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/perf/indicatorsLine")
@Slf4j
public class IndicatorsLineController extends BaseController {

    @Autowired
    private IIndicatorsLineService iIndicatorsLineService;

    /**
     * Description 根据ID删除指标行信息
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.05.27
     * @throws
     **/
    @PostMapping("/deleteIndicatorsLine")
    public BaseResult<String> deleteIndicatorsLine(@RequestBody IndicatorsLine indicatorsLine) {
        if(null == indicatorsLine){
            Assert.isTrue(false, "id不能为空");
        }
        return iIndicatorsLineService.deleteIndicatorsLine(indicatorsLine.getIndicatorLineId());
    }

}
