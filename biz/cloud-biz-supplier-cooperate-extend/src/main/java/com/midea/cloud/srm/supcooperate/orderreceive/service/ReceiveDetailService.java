package com.midea.cloud.srm.supcooperate.orderreceive.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.suppliercooperate.orderreceive.dto.ReceiveDetailDTO;

import java.util.List;

public interface ReceiveDetailService extends IService<ReceiveDetailDTO> {
    /*
   分页查询
    */
    PageInfo<ReceiveDetailDTO> listPage(ReceiveDetailDTO receiveDetailDTO);
}
