package com.midea.cloud.srm.cm.template.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.contract.PayTypeStatus;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.common.utils.StringUtil;
import com.midea.cloud.srm.cm.template.mapper.PayTypeMapper;
import com.midea.cloud.srm.cm.template.service.IPayTypeService;
import com.midea.cloud.srm.model.cm.template.dto.PayTypeDTO;
import com.midea.cloud.srm.model.cm.template.entity.PayType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
*  <pre>
 *  合同付款类型 服务实现类
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-13 16:49:04
 *  修改内容:
 * </pre>
*/
@Service
public class PayTypeServiceImpl extends ServiceImpl<PayTypeMapper, PayType> implements IPayTypeService {

    @Override
    public List<PayType> listAll() {
        return this.baseMapper.listAll();
    }

    @Override
    public PageInfo<PayTypeDTO> listPageByParm(PayType payType) {
        PageUtil.startPage(payType.getPageNum(), payType.getPageSize());

        PayType payTypeQuery = new PayType().setStartDate(payType.getStartDate()).setEndDate(payType.getEndDate());

        if (StringUtils.isNotBlank(payType.getPayDateRequired())) {
            payTypeQuery.setPayDateRequired(payType.getPayDateRequired());
        }

        if (StringUtils.isNotBlank(payType.getPayTypeStatus())) {
            payTypeQuery.setPayTypeStatus(payType.getPayTypeStatus());
        }

        QueryWrapper<PayType> queryWrapper = new QueryWrapper<>(payTypeQuery);

        queryWrapper.like(StringUtils.isNotBlank(payType.getPayType()), "PAY_TYPE", payType.getPayType());
        queryWrapper.like(StringUtils.isNotBlank(payType.getPayExplain()), "PAY_EXPLAIN", payType.getPayExplain());
        queryWrapper.like(StringUtils.isNotBlank(payType.getLogicalExplain()), "LOGICAL_EXPLAIN", payType.getLogicalExplain());
        queryWrapper.orderByDesc("CREATION_DATE");

        List<PayType> payTypesEntities = this.list(queryWrapper);
        List<PayTypeDTO> payTypeDTOs = new ArrayList<>();
        payTypesEntities.forEach(payTypeEntity -> {
            PayTypeDTO resultPayTypeDTO = new PayTypeDTO();
            BeanUtils.copyProperties(payTypeEntity, resultPayTypeDTO);
            payTypeDTOs.add(resultPayTypeDTO);
        });
        return new PageInfo<>(payTypeDTOs);
    }

    @Override
    @Transactional
    public void savePayTypeDTO(PayTypeDTO payTypeDTO) {
        long id = IdGenrator.generate();
        payTypeDTO.setPayTypeId(id);
        payTypeDTO.setPayTypeStatus(PayTypeStatus.EFFECTIVE.name());
        payTypeDTO.setStartDate(LocalDate.now());
        PayType payType = new PayType();
        BeanUtils.copyProperties(payTypeDTO, payType);
        this.save(payType);
    }

    @Override
    @Transactional
    public void updatePayTypeDTO(PayTypeDTO payTypeDTO) {
        Assert.notNull(payTypeDTO.getPayTypeId(), "payTypeId不能为空");
        PayType payType = new PayType();
        BeanUtils.copyProperties(payTypeDTO, payType);
        this.updateById(payType);
    }

    @Override
    public void effective(Long payTypeId) {
        Assert.notNull(payTypeId, "payTypeId不能为空");
        PayType payType = this.getById(payTypeId);
        if (payType != null && !PayTypeStatus.EFFECTIVE.name().equals(payType.getPayTypeStatus())) {
            this.updateById(payType.setStartDate(LocalDate.now())
                    .setPayTypeStatus(PayTypeStatus.EFFECTIVE.name()));
        }
    }

    @Override
    public void invalid(Long payTypeId) {
        Assert.notNull(payTypeId, "payTypeId不能为空");
        PayType payType = this.getById(payTypeId);
        if (payType != null && !PayTypeStatus.INVALID.name().equals(payType.getPayTypeStatus())) {
            this.updateById(payType.setEndDate(LocalDate.now())
                    .setPayTypeStatus(PayTypeStatus.INVALID.name()));
        }
    }

    @Override
    public Long paymentTermsAdd(PayType payType) {
        long id = IdGenrator.generate();
        payType.setPayTypeId(id);
        this.save(payType);
        return id;
    }

    @Override
    public Long paymentTermsUpdate(PayType payType) {
        Assert.notNull(payType.getPayTypeId(), "payTypeId不能为空");
        this.updateById(payType);
        return payType.getPayTypeId();
    }

    @Override
    public PageInfo<PayType> paymentTermsPage(PayType payType) {
        PageUtil.startPage(payType.getPageNum(), payType.getPageSize());
        QueryWrapper<PayType> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtil.notEmpty(payType.getPayExplain()),"PAY_EXPLAIN",payType.getPayExplain());
        queryWrapper.like(StringUtil.notEmpty(payType.getCondFactorId()),"COND_FACTOR_ID",payType.getCondFactorId());
        queryWrapper.like(StringUtil.notEmpty(payType.getCondFactor()),"COND_FACTOR",payType.getCondFactor());
        queryWrapper.orderByDesc("PAY_TYPE_ID");
        return new PageInfo<>(this.list(queryWrapper));
    }

    @Override
    public List<PayType> getActivationPaymentTerms() {
        QueryWrapper<PayType> queryWrapper = new QueryWrapper<>();
        queryWrapper.isNull("END_DATE").or().ge("END_DATE", LocalDate.now());
        return this.list(queryWrapper);
    }
}
