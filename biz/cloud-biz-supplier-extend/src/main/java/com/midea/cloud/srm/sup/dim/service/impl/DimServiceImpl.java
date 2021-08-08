package com.midea.cloud.srm.sup.dim.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.srm.model.supplier.dim.entity.Dim;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.srm.sup.dim.mapper.DimMapper;
import com.midea.cloud.srm.sup.dim.service.IDimService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
*  <pre>
 *  维度表 服务实现类
 * </pre>
*
* @author zhuwl7@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-02-29 15:17:35
 *  修改内容:
 * </pre>
*/
@Service
public class DimServiceImpl extends ServiceImpl<DimMapper, Dim> implements IDimService {

    @Autowired
    private DimMapper dimMapper;

    @Override
    public void definitionDim(List<Dim> dimList) {
        if (!CollectionUtils.isEmpty(dimList)) {
            for (Dim dim : dimList) {
                List<Dim> existsDim = dimMapper.selectExists(dim);
                if(!existsDim.isEmpty()){
                    throw new BaseException("财务信息和银行信息的管控维度配置影响后续数据规则," +
                            "配置后不能更改!");
                }
                dim.setLastUpdateDate(new Date());
                dimMapper.updateById(dim);
            }
        }
    }

    @Override
    public void updateBasic(List<Dim> dimList) {
        if (!CollectionUtils.isEmpty(dimList)) {
            for (Dim dim : dimList) {
                dim.setLastUpdateDate(new Date());
                dim.setLastUpdatedBy("lang");
                dimMapper.updateById(dim);
            }
        }
    }

    @Override
    public Dim queryByParam(String dimCode) {
        Dim dim = new Dim();
        dim.setDimCode(dimCode);
        QueryWrapper<Dim> wrapper = new QueryWrapper<Dim>(dim);
       return StringUtils.isNoneBlank(dimCode)?this.getOne(wrapper):null;
    }
}
