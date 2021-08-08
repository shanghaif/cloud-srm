package com.midea.cloud.srm.cm.model.controller;

import com.midea.cloud.srm.cm.model.service.IModelLineService;
import com.midea.cloud.srm.model.cm.model.entity.ModelLine;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
*  <pre>
 *  合同行表 前端控制器
 * </pre>
*
* @author wangpr@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-06-24 09:24:06
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/modelLine")
public class ModelLineController extends BaseController {

    @Autowired
    private IModelLineService iModelLineService;

    /**
     * 根据模板id获取元素
     * @param modelHeadId
     */
    @GetMapping("/getModelLine")
    public List<ModelLine> getModelLine(Long modelHeadId) {
        return iModelLineService.getModelLine(modelHeadId);
    }

}
