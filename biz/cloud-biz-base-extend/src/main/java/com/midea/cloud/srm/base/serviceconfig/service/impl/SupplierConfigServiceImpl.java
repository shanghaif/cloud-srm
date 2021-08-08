package com.midea.cloud.srm.base.serviceconfig.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.DateChangeUtil;
import com.midea.cloud.srm.base.serviceconfig.mapper.SupplierConfigMapper;
import com.midea.cloud.srm.base.serviceconfig.service.ISupplierConfigService;
import com.midea.cloud.srm.model.base.serviceconfig.entity.SupplierConfig;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
*  <pre>
 *   服务实现类
 * </pre>
*
* @author chensl26@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-02-26 16:18:14
 *  修改内容:
 * </pre>
*/
@Service
public class SupplierConfigServiceImpl extends ServiceImpl<SupplierConfigMapper, SupplierConfig> implements ISupplierConfigService {

    @Override
    public void saveForOne(SupplierConfig supplierConfig) {
        List<SupplierConfig> existlist = this.list();
        if(!CollectionUtils.isEmpty(existlist)){
            SupplierConfig existSupplierConfig = existlist.get(0);
            if(StringUtils.isNotBlank(existSupplierConfig.getEnableCategory()) || StringUtils.isNotBlank(existSupplierConfig.getEnableOrg())){
                throw new BaseException("配置保存之后不可修改!");
            }
            if(StringUtils.isBlank(supplierConfig.getEnableCategory()) && StringUtils.isBlank(supplierConfig.getEnableOrg())){
                throw  new BaseException("必须启用一项");
            }
            existSupplierConfig.setActiveDate(DateChangeUtil.asLocalDate(new Date()));
            existSupplierConfig.setEnableCategory(supplierConfig.getEnableCategory());
            existSupplierConfig.setEnableOrg(supplierConfig.getEnableOrg());
            this.updateById(existSupplierConfig);
        }

    }
}
