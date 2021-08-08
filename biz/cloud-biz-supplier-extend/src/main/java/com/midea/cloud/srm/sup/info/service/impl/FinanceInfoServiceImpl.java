package com.midea.cloud.srm.sup.info.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.model.supplier.info.entity.FinanceInfo;
import com.midea.cloud.srm.sup.dim.service.IDimFieldContextService;
import com.midea.cloud.srm.sup.info.mapper.FinanceInfoMapper;
import com.midea.cloud.srm.sup.info.service.IFinanceInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
*  <pre>
 *  财务信息 服务实现类
 * </pre>
*
* @author zhuwl7@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-02 16:06:05
 *  修改内容:
 * </pre>
*/
@Service
public class FinanceInfoServiceImpl extends ServiceImpl<FinanceInfoMapper, FinanceInfo> implements IFinanceInfoService {
    @Autowired
    private IDimFieldContextService dimFieldContextService;

    @Autowired
    private FinanceInfoMapper financeInfoMapper;

    @Override
    @Transactional
    public void saveOrUpdateFinace(FinanceInfo financeInfo, Long companyId) {
        financeInfo.setCompanyId(companyId);
        if(financeInfo.getFinanceInfoId() != null){
            financeInfo.setLastUpdateDate(new Date());
        }else{
            financeInfo.setCreationDate(new Date());
            Long id = IdGenrator.generate();
            financeInfo.setFinanceInfoId(id);
        }
        this.saveOrUpdate(financeInfo);
        if(null != financeInfo.getDimFieldContexts() && !CollectionUtils.isEmpty(financeInfo.getDimFieldContexts())){
            dimFieldContextService.saveOrUpdateList(financeInfo.getDimFieldContexts(),financeInfo.getFinanceInfoId(),companyId);
        }
    }

    @Override
    public List<FinanceInfo> getByCompanyId(Long companyId) {
        QueryWrapper<FinanceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("COMPANY_ID",companyId);
        List<FinanceInfo> financeInfos = companyId != null?this.list(queryWrapper):null;
//        ceea,隆基不需要
//        if(!CollectionUtils.isEmpty(financeInfos)) {
//            for(FinanceInfo financeInfo:financeInfos){
//                Map<String,Object> dimFieldContexts = dimFieldContextService.findByOrderId(financeInfo.getFinanceInfoId());
//                financeInfo.setDimFieldContexts(dimFieldContexts);
//            }
//
//        }
        return financeInfos;
    }

    @Override
    public void removeByCompanyId(Long companyId) {
        QueryWrapper<FinanceInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("COMPANY_ID",companyId);
        this.remove(wrapper);
    }

    @Override
    public FinanceInfo getByCompanyIdAndOrgId(Long companyId, Long orgId) {
        QueryWrapper<FinanceInfo> queryWrapper = new QueryWrapper<>(new FinanceInfo()
                .setCompanyId(companyId).setOrgId(orgId));
        List<FinanceInfo> financeInfos = financeInfoMapper.selectList(queryWrapper);
        if (!CollectionUtils.isEmpty(financeInfos)) {
            return financeInfos.get(0);
        }
        return null;
    }
}
