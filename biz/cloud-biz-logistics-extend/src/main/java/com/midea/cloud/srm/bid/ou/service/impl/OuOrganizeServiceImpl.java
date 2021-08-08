package com.midea.cloud.srm.bid.ou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.bid.ou.mapper.OuOrganizeMapper;
import com.midea.cloud.srm.bid.ou.service.IOuOrganizeService;
import com.midea.cloud.srm.model.logistics.bid.ou.entity.OuOrganize;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <pre>
 *  ou组基础信息表 服务实现类
 * </pre>
 *
 * @author yourname@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-25 13:53:09
 *  修改内容:
 * </pre>
 */
@Service
public class OuOrganizeServiceImpl extends ServiceImpl<OuOrganizeMapper, OuOrganize> implements IOuOrganizeService {

    @Override
    public PageInfo<OuOrganize> getOuListPage(OuOrganize ouOrganize) {
        PageUtil.startPage(ouOrganize.getPageNum(), ouOrganize.getPageSize());
        QueryWrapper<OuOrganize> wrapper = new QueryWrapper<OuOrganize>(ouOrganize);
        //业务实体id条件查询
        wrapper.eq(ouOrganize.getOrgId()!=null,"ORG_ID",ouOrganize.getOrgId());
        //业务实体名称模糊查询
        wrapper.like(StringUtils.isNotEmpty(ouOrganize.getOrgName()),"ORG_NAME",ouOrganize.getOrgName());
        //ou组编号模糊查询
        wrapper.like(StringUtils.isNotEmpty(ouOrganize.getOuNumber()),"OU_NUMBER",ouOrganize.getOuNumber());
        //ou组名称模糊查询
        wrapper.like(StringUtils.isNotEmpty(ouOrganize.getOuName()),"OU_NAME",ouOrganize.getOuName());
        return new PageInfo<OuOrganize>(this.list(wrapper));
    }
}
