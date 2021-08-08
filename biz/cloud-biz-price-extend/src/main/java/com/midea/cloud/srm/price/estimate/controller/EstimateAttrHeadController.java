package com.midea.cloud.srm.price.estimate.controller;

import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.price.estimate.entity.EstimateAttrHead;
import com.midea.cloud.srm.price.estimate.service.IEstimateAttrHeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
*  <pre>
 *  价格估算属性头表 前端控制器
 * </pre>
*
* @author wangpr@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-07-25 14:36:39
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/estimate-attr-head")
public class EstimateAttrHeadController extends BaseController {

    @Autowired
    private IEstimateAttrHeadService iEstimateAttrHeadService;

    /**
    * 获取
    * @param priceModelHeadId
    */
    @GetMapping("/get")
    public List<EstimateAttrHead> get(Long priceModelHeadId) {
        return iEstimateAttrHeadService.get(priceModelHeadId);
    }
 
}
