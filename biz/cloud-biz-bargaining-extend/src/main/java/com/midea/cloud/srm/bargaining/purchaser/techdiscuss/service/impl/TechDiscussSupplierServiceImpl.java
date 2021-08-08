package com.midea.cloud.srm.bargaining.purchaser.techdiscuss.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.midea.cloud.srm.bargaining.purchaser.techdiscuss.mapper.TechDiscussSupplierMapper;
import com.midea.cloud.srm.bargaining.purchaser.techdiscuss.service.ITechDiscussSupplierService;
import com.midea.cloud.srm.model.bargaining.purchaser.techdiscuss.entity.TechDiscussSupplier;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <pre>
 *  技术交流项目供应商表 服务实现类
 * </pre>
 *
 * @author fengdc3@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-12 11:09:20
 *  修改内容:
 * </pre>
 */
@Service
public class TechDiscussSupplierServiceImpl extends ServiceImpl<TechDiscussSupplierMapper, TechDiscussSupplier> implements ITechDiscussSupplierService {

    @Override
    @Transactional
    public void removeByProjId(Long id) {
        this.remove(new QueryWrapper<>(new TechDiscussSupplier().setProjId(id)));
    }
}
