package com.midea.cloud.srm.cm.template.controller;

import com.midea.cloud.srm.cm.template.service.ITemplLineService;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

/**
*  <pre>
 *  合同模板行表 前端控制器
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-19 08:58:55
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/template/templLine")
public class TemplLineController extends BaseController {

    @Autowired
    private ITemplLineService iTemplLineService;

    /**
    * 删除
    * @param templLineId
    */
    @GetMapping("/deleteTempline")
    public void delete(Long templLineId) {
        Assert.notNull(templLineId, "templLineId不能为空");
        iTemplLineService.removeById(templLineId);
    }

}
