package com.midea.cloud.srm.inq.inquiry.controller;

import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.srm.inq.inquiry.service.VendorNService;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.inq.inquiry.dto.VendorNQueryResponseDTO;
import com.midea.cloud.srm.model.inq.inquiry.dto.VendorNSaveDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
*  <pre>
 *  供应商N值 前端控制器
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-04-07 14:32:42
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/inquiry/vendor/n")
public class VendorNController extends BaseController {

    @Autowired
    private VendorNService vendorNService;

    @GetMapping
    public List<VendorNQueryResponseDTO> queryVendorN(Long inquiryId) {
        Assert.notNull(inquiryId, "询价单id不能为空");
        return vendorNService.queryVendorN(inquiryId);
    }

    @PostMapping
    public void saveVendorN(@RequestBody List<VendorNSaveDTO> request) {
        if (CollectionUtils.isEmpty(request)) {
            throw new BaseException("新增数据不能为空");
        }
        vendorNService.saveVendorN(request);
    }

//    @PostMapping
//    public void updateVendorN(@RequestBody List<VendorNUpdateDTO> request) {
//        if (CollectionUtils.isEmpty(request)) {
//            throw new BaseException("修改数据不能为空");
//        }
//        vendorNService.updateVendorN(request);
//    }
}
