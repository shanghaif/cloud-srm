package com.midea.cloud.srm.supcooperate.orderreceive.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.midea.cloud.srm.model.suppliercooperate.orderreceive.dto.ReceiveDetailDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReceiveDetailMapper extends BaseMapper<ReceiveDetailDTO> {
    List<ReceiveDetailDTO> getReceiveDetail(@Param("ew")QueryWrapper<ReceiveDetailDTO> ew);
    ReceiveDetailDTO getOne(@Param("ew")QueryWrapper<ReceiveDetailDTO> ew);
}
