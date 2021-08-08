package com.midea.cloud.srm.cm.condfactor.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.common.utils.StringUtil;
import com.midea.cloud.srm.cm.condfactor.mapper.CondFactorMapper;
import com.midea.cloud.srm.model.cm.condfactor.entity.CondFactor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import com.midea.cloud.srm.cm.condfactor.service.ICondFactorService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
*  <pre>
 *  条件因素表 服务实现类
 * </pre>
*
* @author wangpr@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-11 11:15:50
 *  修改内容:
 * </pre>
*/
@Service
public class CondFactorServiceImpl extends ServiceImpl<CondFactorMapper, CondFactor> implements ICondFactorService {
    @Override
    public PageInfo<CondFactor> listPage(CondFactor condFactor) {
        // 设置不分页
        PageUtil.startPage(condFactor.getPageNum(), condFactor.getPageSize());
        QueryWrapper<CondFactor> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtil.notEmpty(condFactor.getCondFactor()),"COND_FACTOR",condFactor.getCondFactor());
        wrapper.like(StringUtil.notEmpty(condFactor.getMenuName()),"MENU_NAME",condFactor.getMenuName());
        wrapper.like(StringUtil.notEmpty(condFactor.getSystemField()),"SYSTEM_FIELD",condFactor.getSystemField());
        wrapper.orderByDesc("COND_FACTOR_ID");
        return new PageInfo<>(this.list(wrapper));
    }

    @Override
    public List<CondFactor> getByIdList(String idList) {
        List<CondFactor> condFactors = new ArrayList<>();
        Assert.notNull(idList, "idList不能为空");
        if (idList.contains(",")){
            List<String> idTemp = Arrays.asList(idList.split(","));
            ArrayList<Long> ids = new ArrayList<>();
            if(CollectionUtils.isNotEmpty(idTemp)){
                idTemp.forEach(id->ids.add(Long.parseLong(id)));
            }
            condFactors = this.listByIds(ids);
        }else {
            condFactors.add(this.getById(Long.parseLong(idList)));
        }
        return condFactors;
    }

    @Override
    public Long add(CondFactor condFactor) {
        long id = IdGenrator.generate();
        condFactor.setCondFactorId(id);
        this.save(condFactor);
        return id;
    }

    @Override
    public List<CondFactor> queryAll() {
        QueryWrapper<CondFactor> queryWrapper = new QueryWrapper<>();
        queryWrapper.isNull("END_DATE").or().ge("END_DATE", LocalDate.now());
        return this.list(queryWrapper);
    }
}
