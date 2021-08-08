package com.midea.cloud.srm.sup.info.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfoDetail;
import com.midea.cloud.srm.sup.info.mapper.CompanyInfoDetailMapper;
import com.midea.cloud.srm.sup.info.service.ICompanyInfoDetailService;
import org.springframework.stereotype.Service;

/**
*  <pre>
 *  基本信息从表 服务实现类
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-04 09:47:45
 *  修改内容:
 * </pre>
*/
@Service
public class CompanyInfoDetailServiceImpl extends ServiceImpl<CompanyInfoDetailMapper, CompanyInfoDetail> implements ICompanyInfoDetailService {

    @Override
    public CompanyInfoDetail getByCompanyId(Long companyId) {
        QueryWrapper<CompanyInfoDetail> queryWrapper = new QueryWrapper<>(new CompanyInfoDetail().setCompanyId(companyId));
        return companyId != null ? this.getById(companyId) : null;
    }
}
