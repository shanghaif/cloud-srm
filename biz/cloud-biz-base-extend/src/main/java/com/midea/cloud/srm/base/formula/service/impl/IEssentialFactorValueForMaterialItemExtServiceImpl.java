package com.midea.cloud.srm.base.formula.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.srm.base.formula.service.IEssentialFactorCalculateService;
import com.midea.cloud.srm.base.formula.service.IEssentialFactorValueService;
import com.midea.cloud.srm.base.material.mapper.MaterialItemAttributeRelateMapper;
import com.midea.cloud.srm.model.base.formula.vo.EssentialFactorValueParameter;
import com.midea.cloud.srm.model.base.material.MaterialItemAttributeRelate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * Implement of {@link IEssentialFactorCalculateService}.
 *
 * @author zixuan.yan@meicloud.com
 */
@Service
public class IEssentialFactorValueForMaterialItemExtServiceImpl implements IEssentialFactorValueService {

    @Resource
    private MaterialItemAttributeRelateMapper   materialItemAttributeRelDao;


    @Override
    public String value(EssentialFactorValueParameter parameter) {
        return Optional.ofNullable(
                materialItemAttributeRelDao.selectOne(
                        Wrappers.lambdaQuery(MaterialItemAttributeRelate.class)
                                .eq(MaterialItemAttributeRelate::getMaterialAttributeId, parameter.getEssentialFactor().getMaterialAttributeId())
                                .eq(MaterialItemAttributeRelate::getMaterialItemId, parameter.getMaterialItem().getMaterialId())
                ))
                .map(MaterialItemAttributeRelate::getAttributeValue)
                .orElseThrow(() -> new BaseException(
                        "物料扩展属性获取失败 | 物料 [" + parameter.getMaterialItem().getMaterialCode()
                                + "]，扩展属性 [" + parameter.getEssentialFactor().getMaterialAttributeName() + "]"));
    }
}
