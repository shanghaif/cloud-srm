package com.midea.cloud.srm.base.material.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.base.material.mapper.MaterialOrgMapper;
import com.midea.cloud.srm.base.material.service.IMaterialOrgService;
import com.midea.cloud.srm.model.base.material.MaterialOrg;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
*  <pre>
 *  物料与组织关系表 服务实现类
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-07-25 11:14:25
 *  修改内容:
 * </pre>
*/
@Service
public class MaterialOrgServiceImpl extends ServiceImpl<MaterialOrgMapper, MaterialOrg> implements IMaterialOrgService {

    @Override
    public PageInfo<MaterialOrg> findMateriaOrgPageList(MaterialOrg materialOrg) {
        PageUtil.startPage(materialOrg.getPageNum(), materialOrg.getPageSize());
        List<MaterialOrg> materialOrgList = new ArrayList<>();
        try{
            materialOrgList = getBaseMapper().selectList(new QueryWrapper<>(materialOrg));
        }catch (Exception e){
            log.error("操作失败",e);
            throw new BaseException("分页获取物料与组织关系信息时报错: ");
        }
        return new PageInfo<>(materialOrgList);
    }
}
