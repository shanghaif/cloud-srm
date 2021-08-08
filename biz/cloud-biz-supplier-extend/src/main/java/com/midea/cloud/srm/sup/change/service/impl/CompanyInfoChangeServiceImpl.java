package com.midea.cloud.srm.sup.change.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.model.supplier.change.entity.*;
import com.midea.cloud.srm.sup.change.mapper.CompanyInfoChangeMapper;
import com.midea.cloud.srm.sup.change.service.*;
import com.midea.cloud.srm.sup.info.service.ICompanyInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

/**
 *  <pre>
 *  公司基本信息 服务实现类
 * </pre>
 *
 * @author zhuwl7@meicloud.com
 * @version 1.00.00
 *
 *  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-11 11:01:27
 *  修改内容:
 * </pre>
 */
@Service
public class CompanyInfoChangeServiceImpl extends ServiceImpl<CompanyInfoChangeMapper, CompanyInfoChange> implements ICompanyInfoChangeService {
    @Autowired
    private IDimFieldContextChangeService iDimFieldContextChangeService;
    @Autowired
    private ICompanyInfoService iCompanyInfoService;
    @Autowired
    private ICategoryRelChangeService iCategoryRelChangeService;

    @Override
    public CompanyInfoChange saveOrUpdateCompany(CompanyInfoChange companyInfoChange,Long companyId,Long changeId) {
        CompanyInfoChange result = new CompanyInfoChange();
        companyInfoChange.setChangeId(changeId);
        companyInfoChange.setCompanyId(companyId);
        if(companyInfoChange.getCompanyChangeId() != null){
            result=  this.modifyChange(companyInfoChange);
        }else{
            result=  this.addChange(companyInfoChange);
        }
//        if(!CollectionUtils.isEmpty(companyInfoChange.getDimFieldContexts())){
//            iDimFieldContextChangeService.saveOrUpdateList(companyInfoChange.getDimFieldContexts(),
//                    companyInfoChange.getChangeId(),
//                    companyInfoChange.getCompanyId(),
//                    companyInfoChange.getCompanyId(),
//                    companyInfoChange.getChangeId()
//            );
//        }
        iCategoryRelChangeService.saveOrUpdateList(companyInfoChange.getCategoryRelChanges(),
                companyId,
                changeId);
        return  result;
    }

    @Override
    public void removeByChangeId(Long changeId) {
        CompanyInfoChange companyInfoChange= new CompanyInfoChange();
        companyInfoChange.setChangeId(changeId);
        this.remove(new QueryWrapper<>(companyInfoChange));
        iCategoryRelChangeService.removeByChangeId(changeId);
    }

    @Override
    public CompanyInfoChange addChange(CompanyInfoChange companyInfoChange) {
        Long id = IdGenrator.generate();
        companyInfoChange.setCompanyChangeId(id);
        this.save(companyInfoChange);
        return companyInfoChange;
    }

    @Override
    public CompanyInfoChange modifyChange(CompanyInfoChange companyInfoChange) {
        if(companyInfoChange.getChangeId() != null){
            this.updateById(companyInfoChange);
            return companyInfoChange;
        }
        return null;
    }



    @Override
    public CompanyInfoChange getByChangeId(Long chanageId) {
        CompanyInfoChange queryChage = new CompanyInfoChange();
        queryChage.setChangeId(chanageId);
        CompanyInfoChange infoChange =  chanageId != null?this.getOne(new QueryWrapper<>(queryChage)):null;
//        if(infoChange != null){
//            Map<String,Object> dimFieldContexts = iDimFieldContextChangeService.findByOrderId(infoChange.getChangeId());
//            infoChange.setDimFieldContexts(dimFieldContexts);
//        }
        List<CategoryRelChange> categoryRelChanges = iCategoryRelChangeService.queryByChangeId(chanageId);
        if (CollectionUtils.isNotEmpty(categoryRelChanges)){
            infoChange.setCategoryRelChanges(categoryRelChanges);
        }
        return infoChange;
    }

}
