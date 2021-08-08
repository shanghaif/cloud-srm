package com.midea.cloud.srm.ps.advance.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.pm.ps.advance.entity.AdvanceApplyLine;
import com.midea.cloud.srm.ps.advance.service.IAdvanceApplyLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
*  <pre>
 *  预付款申请行表 前端控制器
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-20 13:41:48
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/ps/advanceApplyLine")
public class AdvanceApplyLineController extends BaseController {

    @Autowired
    private IAdvanceApplyLineService iAdvanceApplyLineService;

    @PostMapping("/deleteBatch")
    public void deleteBatch(@RequestBody List<Long> advanceApplyLineIds) {
        iAdvanceApplyLineService.removeByIds(advanceApplyLineIds);
    }

}
