package com.midea.cloud.srm.cm.accept.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.common.utils.StringUtil;
import com.midea.cloud.srm.model.cm.accept.dto.DetailLineDTO;
import com.midea.cloud.srm.model.cm.accept.entity.DetailLine;
import com.midea.cloud.srm.cm.accept.mapper.DetailLineMapper;
import com.midea.cloud.srm.cm.accept.service.IDetailLineService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.logstash.logback.encoder.org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
*  <pre>
 *  合同验收行 服务实现类
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-11 19:45:28
 *  修改内容:
 * </pre>
*/
@Service
public class DetailLineServiceImpl extends ServiceImpl<DetailLineMapper, DetailLine> implements IDetailLineService {

    @Override
    public PageInfo<DetailLine> listPageByParm(DetailLineDTO detailLine) {
        PageUtil.startPage(detailLine.getPageNum(), detailLine.getPageSize());
        QueryWrapper<DetailLine> wrapper= new QueryWrapper<>();
        wrapper.select("*,WAREHOUSE_RECEIPT_QUANTITY-ACCEPT_QUANTITY as remainingQuantity");
        //采购订单号模糊查询
        wrapper.like(StringUtils.isNotBlank(detailLine.getOrderNumber()),"ORDER_NUMBER",detailLine.getOrderNumber());
        //业务实体条件查询
        wrapper.eq(detailLine.getOrgId()!=null,"ORG_ID",detailLine.getOrgId());
        //库存组织条件查询
        wrapper.eq(detailLine.getOrganizationId()!=null,"ORGANIZATION_ID",detailLine.getOrganizationId());

        //物料编码或描述条件查询
        if (StringUtils.isNotBlank(detailLine.getItemLongDesc())){
            wrapper.eq("ITEM_LONG_DESC",detailLine.getItemLongDesc()).or().like("ITEM_CODE",detailLine.getItemLongDesc());
        }
        //物料小类模糊查询
        wrapper.like(StringUtils.isNotBlank(detailLine.getSmallClassMat()),"SMALL_CLASS_MAT",detailLine.getSmallClassMat());

        //订单出厂日期模糊查询
        if (detailLine.getEndProductionDate()!=null&&detailLine.getStartProductionDate()!=null){
            wrapper.ge("PRODUCTION_DATE",detailLine.getStartProductionDate());
            wrapper.le("PRODUCTION_DATE",detailLine.getEndProductionDate());
        }
        if (detailLine.getEndProductionDate()==null&&detailLine.getStartProductionDate()!=null||detailLine.getStartProductionDate()==null&&detailLine.getEndProductionDate()!=null){
            wrapper.ge(detailLine.getStartProductionDate()!=null,"PRODUCTION_DATE",detailLine.getStartProductionDate());
            wrapper.le(detailLine.getEndProductionDate()!=null,"PRODUCTION_DATE",detailLine.getEndProductionDate());
        }
        //采购申请号模糊查询
        wrapper.like(StringUtils.isNotBlank(detailLine.getRequirementHeadNum()),"REQUIREMENT_HEAD_NUM",detailLine.getRequirementHeadNum());
        //合同编号模糊查询
        wrapper.like(StringUtils.isNotBlank(detailLine.getContractNo()),"CONTRACT_NO",detailLine.getContractNo());
        //采购员条件查询
        wrapper.like(StringUtils.isNotBlank(detailLine.getBuyer()),"BUYER",detailLine.getBuyer());
        //验收申请号
        wrapper.like(StringUtils.isNotBlank(detailLine.getAcceptApplicationNum()),"ACCEPT_APPLICATION_NUM",detailLine.getAcceptApplicationNum());
        return new PageInfo<DetailLine>(list(wrapper));
    }
}
