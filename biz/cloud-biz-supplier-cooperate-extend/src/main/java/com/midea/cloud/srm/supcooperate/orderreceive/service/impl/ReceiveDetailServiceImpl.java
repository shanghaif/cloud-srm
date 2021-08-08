package com.midea.cloud.srm.supcooperate.orderreceive.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.suppliercooperate.orderreceive.dto.ReceiveDetailDTO;

import com.midea.cloud.srm.supcooperate.orderreceive.mapper.ReceiveDetailMapper;
import com.midea.cloud.srm.supcooperate.orderreceive.service.ReceiveDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReceiveDetailServiceImpl extends ServiceImpl<ReceiveDetailMapper, ReceiveDetailDTO>  implements ReceiveDetailService {
    @Autowired
    ReceiveDetailMapper receiveDetailMapper;

    /**
     * 条件查询
     * @param receiveDetailDTO
     * @return
     */
    public List<ReceiveDetailDTO> getReceiveDetails(ReceiveDetailDTO receiveDetailDTO){
        QueryWrapper<ReceiveDetailDTO> ew =new QueryWrapper<>();
        //实体列表范围查询
        ew.eq(receiveDetailDTO.getOrgId()!=null,"ORG_ID",receiveDetailDTO.getOrgId());
        ew.eq("od.CREATED_BY", AppUserUtil.getLoginAppUser().getUsername());
        //模糊查询
        ew.like(receiveDetailDTO.getVendorName()!=null,"VENDOR_NAME",receiveDetailDTO.getVendorName());
        ew.like(receiveDetailDTO.getOrderNumber()!=null,"ORDER_NUMBER",receiveDetailDTO.getOrderNumber());
        ew.like(receiveDetailDTO.getDeliveryNumber()!=null,"DELIVERY_NUMBER",receiveDetailDTO.getDeliveryNumber());
        ew.like(receiveDetailDTO.getMaterialName()!=null,"MATERIAL_NAME",receiveDetailDTO.getMaterialName());
        ew.like(receiveDetailDTO.getMaterialCode()!=null,"MATERIAL_CODE",receiveDetailDTO.getMaterialCode());
        return receiveDetailMapper.getReceiveDetail(ew);
    }

    /**
     * 查询结果分页
     * @param receiveDetailDTO
     * @return
     */
    public PageInfo<ReceiveDetailDTO> listPage(ReceiveDetailDTO receiveDetailDTO){
        PageUtil.startPage(receiveDetailDTO.getPageNum(), receiveDetailDTO.getPageSize());
        List<ReceiveDetailDTO> receiveDetailDTOs = getReceiveDetails(receiveDetailDTO);
        return new PageInfo<>(receiveDetailDTOs);
    }
}
