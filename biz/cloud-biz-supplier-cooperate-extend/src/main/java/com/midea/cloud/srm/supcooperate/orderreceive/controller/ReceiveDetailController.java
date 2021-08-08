package com.midea.cloud.srm.supcooperate.orderreceive.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.suppliercooperate.orderreceive.dto.ReceiveDetailDTO;

import com.midea.cloud.srm.supcooperate.orderreceive.service.ReceiveDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pm/receive/detail")
public class ReceiveDetailController extends BaseController {
    @Autowired
    ReceiveDetailService receiveDetailService;

    @PostMapping("/listPage")
    public PageInfo<ReceiveDetailDTO> findAll(@RequestBody ReceiveDetailDTO receiveDetailDTO){

        return receiveDetailService.listPage(receiveDetailDTO);
    }
}
