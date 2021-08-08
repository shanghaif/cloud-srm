package com.midea.cloud.srm.pr.erp.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.Requisition;
import com.midea.cloud.srm.pr.erp.service.IErpService;
import com.midea.cloud.srm.pr.requirement.service.IRequisitionService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/28 14:52
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/erp/ErpController")
public class ErpController {
    @Autowired
    public IErpService iErpService;
    @Autowired
    private IRequisitionService iRequisitionService;
    @GetMapping("/getResetError")
    public  void  getResetError( String number)throws Exception{
        Assert.isTrue(StringUtils.isNotEmpty(number),"需要处理的ERP单号为空");
        QueryWrapper<Requisition> wrapper = new QueryWrapper<>();
        wrapper.eq("REQUEST_NUMBER",number);
        List<Requisition> list = iRequisitionService.list(wrapper);
        Assert.isTrue(CollectionUtils.isNotEmpty(list),"需要处理的ERP单号找不到");
        iErpService.getResetError(list.get(0).getRequestHeaderId());
    }
}
