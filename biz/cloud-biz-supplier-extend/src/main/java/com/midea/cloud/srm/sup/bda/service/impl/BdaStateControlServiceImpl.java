package com.midea.cloud.srm.sup.bda.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.model.base.bda.entity.BdaStateControl;
import com.midea.cloud.srm.model.base.configguide.entity.ConfigGuide;
import com.midea.cloud.srm.sup.bda.mapper.BdaStateControlMapper;
import com.midea.cloud.srm.sup.bda.service.IBdaStateControlService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
*  <pre>
 *  业务状态控制 服务实现类
 * </pre>
*
* @author zhuwl7@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-05 10:17:03
 *  修改内容:
 * </pre>
*/
@Service
public class BdaStateControlServiceImpl extends ServiceImpl<BdaStateControlMapper, BdaStateControl> implements IBdaStateControlService {
    @Autowired
    private BaseClient baseClient;

    @Override
    public PageInfo<BdaStateControl> listPageByParam(BdaStateControl bdaStateControl) {
        PageUtil.startPage(bdaStateControl.getPageNum(), bdaStateControl.getPageSize());
        QueryWrapper<BdaStateControl> wrapper = new QueryWrapper<BdaStateControl>(bdaStateControl);
        if(StringUtils.isNoneBlank(bdaStateControl.getBusinessType())) {
            wrapper.eq("BUSINESS_TYPE",bdaStateControl.getBusinessType());
        }
        if(StringUtils.isNoneBlank(bdaStateControl.getOrgStatus())){
            wrapper.eq("ORG_STATUS",bdaStateControl.getOrgStatus());
        }
        if(StringUtils.isNoneBlank(bdaStateControl.getIsAllow())){
            wrapper.eq("IS_ALLOW",bdaStateControl.getIsAllow());
        }
        if(StringUtils.isNoneBlank(bdaStateControl.getCategoryStatus())){
            wrapper.eq("CATEGORY_STATUS",bdaStateControl.getCategoryStatus());
        }
        return new PageInfo<BdaStateControl>(this.list(wrapper));
    }

    @Override
    @Transactional
    public void saveOrUpdateBda(BdaStateControl bdaStateControl) {
        if(bdaStateControl.getStateControlId() != null){
            bdaStateControl.setLastUpdateDate(new Date());
        }else{
            bdaStateControl.setCreationDate(new Date());
            Long id = IdGenrator.generate();
            bdaStateControl.setStateControlId(id);
        }
        this.saveOrUpdate(bdaStateControl);
        baseClient.saveOrUpdateConfigGuide(new ConfigGuide().setStateControlConfig(YesOrNo.YES.getValue()));
    }
}
