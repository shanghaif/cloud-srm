package com.midea.cloud.srm.base.formula.service.impl;

import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.component.context.container.SpringContextHolder;
import com.midea.cloud.srm.base.formula.service.IEssentialFactorCalculateService;
import com.midea.cloud.srm.base.formula.service.IEssentialFactorValueService;
import com.midea.cloud.srm.model.base.formula.enums.EssentialFactorFromType;
import org.springframework.stereotype.Service;

/**
 * Implement of {@link IEssentialFactorCalculateService}.
 *
 * @author zixuan.yan@meicloud.com
 */
@Service
public class IEssentialFactorCalculateServiceImpl implements IEssentialFactorCalculateService {

    @Override
    public IEssentialFactorValueService select(EssentialFactorFromType valueSourceType) {
        return SpringContextHolder.getBean(selectValueService(valueSourceType));
    }

    protected Class<? extends IEssentialFactorValueService> selectValueService(EssentialFactorFromType valueSourceType) {
        switch (valueSourceType) {
            case MATERIAL_MAIN_DATA:
                return IEssentialFactorValueForMaterialItemExtServiceImpl.class;
            case BASE_MATERIAL_PRICE:
                return IEssentialFactorValueForBaseMaterialPriceServiceImpl.class;
            case SUPPLIER_QUOTED_PRICE:
                return IEssentialFactorValueForVendorBiddingServiceImpl.class;
            default:
                throw new BaseException("Could not find correct value service with type [" + valueSourceType + "]");
        }
    }
}
