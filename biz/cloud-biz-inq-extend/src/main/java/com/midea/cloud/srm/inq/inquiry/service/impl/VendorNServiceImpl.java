package com.midea.cloud.srm.inq.inquiry.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.inq.inquiry.mapper.VendorNMapper;
import com.midea.cloud.srm.inq.inquiry.service.VendorNService;
import com.midea.cloud.srm.inq.inquiry.service.impl.convertor.VendorNQueryConvertor;
import com.midea.cloud.srm.model.inq.inquiry.dto.VendorNQueryResponseDTO;
import com.midea.cloud.srm.model.inq.inquiry.dto.VendorNSaveDTO;
import com.midea.cloud.srm.model.inq.inquiry.dto.VendorNUpdateDTO;
import com.midea.cloud.srm.model.inq.inquiry.entity.VendorN;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
*  <pre>
 *  供应商N值 服务实现类
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
@Service
public class VendorNServiceImpl extends ServiceImpl<VendorNMapper, VendorN> implements VendorNService {

    @Override
    public List<VendorNQueryResponseDTO> queryVendorN(Long inquiryId) {

        return VendorNQueryConvertor.convert(this.baseMapper.queryVendorN(inquiryId));
    }

    @Override
    public void saveVendorN(List<VendorNSaveDTO> request) {

        /*添加数据前校验*/
        checkBeforeAdd(request);

        List<VendorN> saveList = new ArrayList<>();
        request.forEach(vendorNSaveDTO -> {
            VendorN entity = new VendorN();
            if (vendorNSaveDTO.getVendorNId() == null) {
                BeanUtils.copyProperties(vendorNSaveDTO, entity);
                entity.setVendorNId(IdGenrator.generate());
            }else {
                entity.setVendorNId(vendorNSaveDTO.getVendorNId());
                entity.setPaymentTerm(vendorNSaveDTO.getPaymentTerm());
            }
            saveList.add(entity);
        });
        saveOrUpdateBatch(saveList);
    }

    /**
     * 新增前数据校验
     */
    private void checkBeforeAdd(List<VendorNSaveDTO> request) {
        request.forEach(vendorNSaveDTO -> {
            if (vendorNSaveDTO.getVendorNId() == null) {
                /*新增数据校验*/
                if (vendorNSaveDTO.getOrganizationId() == null) {
                    throw new BaseException("组织id不能为空");
                }
                if (vendorNSaveDTO.getVendorId() == null) {
                    throw new BaseException("供应商id不能为空");
                }
                if (StringUtils.isBlank(vendorNSaveDTO.getVendorCode())) {
                    throw new BaseException("供应商编码不能为空");
                }
                if (StringUtils.isBlank(vendorNSaveDTO.getVendorName())) {
                    throw new BaseException("供应商名称不能为空");
                }
                if (vendorNSaveDTO.getPaymentTerm() == null) {
                    throw new BaseException("N值不能为空");
                }
            }else {
                /*修改数据校验*/
                if (vendorNSaveDTO.getVendorNId() == null) {
                    throw new BaseException("供应商N值主键不能为空");
                }
                if (vendorNSaveDTO.getPaymentTerm() == null) {
                    throw new BaseException("供应商N值不能为空");
                }
            }

        });
    }

    @Override
    public void updateVendorN(List<VendorNUpdateDTO> request) {
        /*修改数据前校验*/
        checkBeforeUpdate(request);

        List<VendorN> updateList = new ArrayList<>();
        request.forEach(vendorNUpdateDTO -> {
            VendorN entity = new VendorN();
            BeanUtils.copyProperties(vendorNUpdateDTO, entity);
            updateList.add(entity);
        });
        updateBatchById(updateList);
    }

    @Override
    public List<VendorN> queryByInquiryId(Long inquiryId) {
        QueryWrapper<VendorN> wrapper = new QueryWrapper<>();
        wrapper.eq("INQUIRY_ID", inquiryId);
        return list(wrapper);
    }

    /**
     * 修改数据前校验
     */
    private void checkBeforeUpdate(List<VendorNUpdateDTO> request) {
        request.forEach(vendorNUpdateDTO -> {
            if (vendorNUpdateDTO.getVendorNId() == null) {
                throw new BaseException("供应商N值主键不能为空");
            }
            if (vendorNUpdateDTO.getPaymentTerm() == null) {
                throw new BaseException("供应商N值不能为空");
            }
        });
    }
}
