package com.midea.cloud.srm.base.serviceconfig.service.impl;

import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.DateChangeUtil;
import com.midea.cloud.srm.base.configguide.service.IConfigGuideService;
import com.midea.cloud.srm.model.base.configguide.entity.ConfigGuide;
import com.midea.cloud.srm.model.base.serviceconfig.entity.ServiceConfig;
import com.midea.cloud.srm.base.serviceconfig.mapper.BaseServiceConfigMapper;
import com.midea.cloud.srm.base.serviceconfig.service.IBaseServiceConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
*  <pre>
 *  产品与服务以及供方管理配置 服务实现类
 * </pre>
*
* @author zhuwl7@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-02-21 19:02:27
 *  修改内容:
 * </pre>
*/
@Service
public class BaseServiceConfigServiceImpl extends ServiceImpl<BaseServiceConfigMapper, ServiceConfig> implements IBaseServiceConfigService {

    @Autowired
    private IConfigGuideService iConfigGuideService;

    @Override
    @Transactional
    public void saveForOne(ServiceConfig entity) {
        List<ServiceConfig> existlist = this.list();
        if(!CollectionUtils.isEmpty(existlist)){
            ServiceConfig existServiceConfig = existlist.get(0);
            if(StringUtils.isNoneBlank(existServiceConfig.getServiceLevel())){
                throw  new BaseException("配置保存之后不可修改!");
            }
            if(StringUtils.isBlank(entity.getServiceLevel())){
                throw  new BaseException("必须启用一项");
            }
            existServiceConfig.setServiceLevel(entity.getServiceLevel());
            existServiceConfig.setActiveDate(DateChangeUtil.asLocalDate(new Date()));
            this.updateById(existServiceConfig);

            //配置导引
            iConfigGuideService.saveOrUpdateConfigGuide(new ConfigGuide().setLevelConfig(YesOrNo.YES.getValue()));
        }



    }
}
