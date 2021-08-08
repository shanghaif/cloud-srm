package com.midea.cloud.srm.pr.vendordistdesc.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.VendorDistDesc;
import com.midea.cloud.srm.pr.vendordistdesc.service.IVendorDistDescService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <pre>
 *
 * </pre>
 *
 * @author wangpr@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-27 09:24:20
 *  修改内容:
 * </pre>
 */
@RestController
@Slf4j
@RequestMapping("/vendorDistDescController")
public class VendorDistDescController {

    @Resource
    private IVendorDistDescService iVendorDistDescService;

    /**
     * 获取供应商分配明细页
     * @param requirementLineId
     * @return
     */
    @GetMapping("/getVendorDistDescById")
    public List<VendorDistDesc> getVendorDistDescById(Long requirementLineId){
        Assert.notNull(requirementLineId,"缺少参数:requirementLineId");
        return iVendorDistDescService.list(new QueryWrapper<>(new VendorDistDesc().setRequirementLineId(requirementLineId)));
    }

    /**
     * 分配供应商
     * @param requirementLineIds
     */
    @PostMapping("/assignSupplier")
    public void assignSupplier(@RequestBody List<Long> requirementLineIds){
        iVendorDistDescService.assignSupplier(requirementLineIds);
    }
}
