package com.midea.cloud.srm.base.quotaorder.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.common.utils.StringUtil;
import com.midea.cloud.srm.base.quotaorder.mapper.QuotaHeadMapper;
import com.midea.cloud.srm.base.quotaorder.service.IQuotaHeadService;
import com.midea.cloud.srm.base.quotaorder.service.IQuotaLineService;
import com.midea.cloud.srm.feign.inq.InqClient;
import com.midea.cloud.srm.model.base.quotaorder.QuotaHead;
import com.midea.cloud.srm.model.base.quotaorder.QuotaLine;
import com.midea.cloud.srm.model.base.quotaorder.dto.QuotaHeadDto;
import com.midea.cloud.srm.model.base.quotaorder.dto.QuotaLineDto;
import com.midea.cloud.srm.model.base.quotaorder.dto.QuotaParamDto;
import com.midea.cloud.srm.model.inq.price.dto.PriceLibraryParamDto;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
*  <pre>
 *  配额比例头表 服务实现类
 * </pre>
*
* @author wangpr@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-12-07 17:00:12
 *  修改内容:
 * </pre>
*/
@Service
public class QuotaHeadServiceImpl extends ServiceImpl<QuotaHeadMapper, QuotaHead> implements IQuotaHeadService {
    @Resource
    private IQuotaLineService iQuotaLineService;
    @Resource
    private InqClient inqClient;

    @Override
    public PageInfo<QuotaHeadDto> listPage(QuotaHeadDto quotaHeadDto) {
        PageUtil.startPage(quotaHeadDto.getPageNum(),quotaHeadDto.getPageSize());
        List<QuotaHeadDto> quotaHeadDtos = this.baseMapper.listPage(quotaHeadDto);
        return new PageInfo<>(quotaHeadDtos);
    }

    @Override
    public QuotaHead get(Long quotaHeadId) {
        Assert.notNull(quotaHeadId, "参数:quotaHeadId,不能为空");
        QuotaHead quotaHead = this.getById(quotaHeadId);
        Assert.notNull(quotaHead,"找不到配额详情,quotaHeadId="+quotaHeadId);
        List<QuotaLine> quotaLines = iQuotaLineService.list(new QueryWrapper<>(new QuotaLine().setQuotaHeadId(quotaHeadId)));
        quotaHead.setQuotaLineList(quotaLines);
        return quotaHead;
    }

    @Override
    public Long addOrUpdate(QuotaHead quotaHead) {
        // 校验数据
        checkQuotaHead(quotaHead);
        Long quotaHeadId = quotaHead.getQuotaHeadId();
        if(StringUtil.isEmpty(quotaHeadId)){
            quotaHead.setQuotaHeadId(IdGenrator.generate());
            this.save(quotaHead);
        }else {
            this.updateById(quotaHead);
        }
        // 保存行
        iQuotaLineService.remove(new QueryWrapper<>(new QuotaLine().setQuotaHeadId(quotaHeadId)));
        List<QuotaLine> quotaLineList = quotaHead.getQuotaLineList();
        if(CollectionUtils.isNotEmpty(quotaLineList)){
            quotaLineList.forEach(quotaLine -> {
                quotaLine.setQuotaHeadId(quotaHead.getQuotaHeadId());
                quotaLine.setQuotaLineId(IdGenrator.generate());
            });
            iQuotaLineService.saveBatch(quotaLineList);
        }
        return quotaHead.getQuotaHeadId();
    }

    /**
     * 1、	每一条配额数据的【业务实体+物料编码】应保持有效期内唯一，即：同一个业务组织下的物料，可以有多条记录，但记录之间的有效期不能重叠；
     * 2、	明细行中的供应商不能重复；
     * 3、	【物料】【供应商】【业务实体】【配额管理类型】不能为空；
     * 4、	保存时应判定明细表中的供应商是否存在有效价格，即价格有效期大于等于当前日期的价格（需匹配价格表）；
     * 5、	【配额比】之和，必须等于100。
     */
    public void checkQuotaHead(QuotaHead quotaHead){
        // 检查头信息
        Assert.notNull(quotaHead.getOrganizationId(),"业务实体不能为空");
        Assert.notNull(quotaHead.getMaterialId(),"物料编码不能为空");
        Assert.notNull(quotaHead.getStartDate(),"有效期起不能为空");
        Assert.notNull(quotaHead.getEndDate(),"有效期止不能为空");
        Assert.notNull(quotaHead.getQuotaManagementType(),"配额管理类型不能为空");
        Assert.isTrue(quotaHead.getStartDate().compareTo(quotaHead.getEndDate()) <=0,"生效日期不能大于失效日期");
        LocalDate now = LocalDate.now();
        /**
         * 1. 有效期不能为空
         * 2. 如果是更新的数据, 检查当前数据有效是否生效, 否则继续执行, 是则检查数据库是否有重复数据
         */
        if(StringUtil.isEmpty(quotaHead.getQuotaHeadId())){
            List<QuotaHead> quotaHeads = this.list(Wrappers.lambdaQuery(QuotaHead.class).
                    eq(QuotaHead::getOrganizationId, quotaHead.getOrganizationId()).
                    eq(QuotaHead::getMaterialId, quotaHead.getMaterialId()).
                    orderByDesc(QuotaHead::getEndDate));
            if(CollectionUtils.isNotEmpty(quotaHeads)){
                LocalDate endDate = quotaHeads.get(0).getEndDate();
                Assert.isTrue(quotaHead.getStartDate().compareTo(endDate) > 0,"生效日期必须大于历史数据的最大失效日期");
            }
        }else {
            // 情况1, 全等于
            List<QuotaHead> quotaHeads1 = this.list(Wrappers.lambdaQuery(QuotaHead.class).
                    eq(QuotaHead::getOrganizationId, quotaHead.getOrganizationId()).
                    eq(QuotaHead::getMaterialId, quotaHead.getMaterialId()).
                    eq(QuotaHead::getStartDate,quotaHead.getStartDate()).
                    eq(QuotaHead::getEndDate,quotaHead.getEndDate()).
                    ne(QuotaHead::getQuotaHeadId,quotaHead.getQuotaHeadId()));
            Assert.isTrue(CollectionUtils.isEmpty(quotaHeads1),"有效期存在交集");
            // 情况2,外包含
            List<QuotaHead> quotaHeads2 = this.list(Wrappers.lambdaQuery(QuotaHead.class).
                    eq(QuotaHead::getOrganizationId, quotaHead.getOrganizationId()).
                    eq(QuotaHead::getMaterialId, quotaHead.getMaterialId()).
                    lt(QuotaHead::getStartDate,quotaHead.getStartDate()).
                    gt(QuotaHead::getEndDate,quotaHead.getEndDate()).
                    ne(QuotaHead::getQuotaHeadId,quotaHead.getQuotaHeadId()));
            Assert.isTrue(CollectionUtils.isEmpty(quotaHeads2),"有效期存在交集");
            // 情况3,内包含
            List<QuotaHead> quotaHeads3 = this.list(Wrappers.lambdaQuery(QuotaHead.class).
                    eq(QuotaHead::getOrganizationId, quotaHead.getOrganizationId()).
                    eq(QuotaHead::getMaterialId, quotaHead.getMaterialId()).
                    lt(QuotaHead::getEndDate,quotaHead.getEndDate()).
                    gt(QuotaHead::getStartDate,quotaHead.getStartDate()).
                    ne(QuotaHead::getQuotaHeadId,quotaHead.getQuotaHeadId()));
            Assert.isTrue(CollectionUtils.isEmpty(quotaHeads3),"有效期存在交集");
            // 情况4, 左包含
            List<QuotaHead> quotaHeads4 = this.list(Wrappers.lambdaQuery(QuotaHead.class).
                    eq(QuotaHead::getOrganizationId, quotaHead.getOrganizationId()).
                    eq(QuotaHead::getMaterialId, quotaHead.getMaterialId()).
                    lt(QuotaHead::getStartDate,quotaHead.getStartDate()).
                    lt(QuotaHead::getEndDate,quotaHead.getEndDate()).
                    gt(QuotaHead::getEndDate,quotaHead.getStartDate()).
                    ne(QuotaHead::getQuotaHeadId,quotaHead.getQuotaHeadId()));
            Assert.isTrue(CollectionUtils.isEmpty(quotaHeads4),"有效期存在交集");
            // 情况5, 右包含
            List<QuotaHead> quotaHeads5 = this.list(Wrappers.lambdaQuery(QuotaHead.class).
                    eq(QuotaHead::getOrganizationId, quotaHead.getOrganizationId()).
                    eq(QuotaHead::getMaterialId, quotaHead.getMaterialId()).
                    gt(QuotaHead::getStartDate,quotaHead.getStartDate()).
                    lt(QuotaHead::getStartDate,quotaHead.getEndDate()).
                    gt(QuotaHead::getEndDate,quotaHead.getEndDate()).
                    ne(QuotaHead::getQuotaHeadId,quotaHead.getQuotaHeadId()));
            Assert.isTrue(CollectionUtils.isEmpty(quotaHeads5),"有效期存在交集");
        }


        // 行信息
        List<QuotaLine> quotaLineList = quotaHead.getQuotaLineList();
        if(CollectionUtils.isNotEmpty(quotaLineList)){
            HashSet<String> hashSet = new HashSet<>();
            AtomicReference<BigDecimal> sum = new AtomicReference<>(BigDecimal.ZERO);
            //明细行中的供应商不能重复；
            quotaLineList.forEach(quotaLine -> {
                BigDecimal allocatedAmount = quotaLine.getAllocatedAmount();
                quotaLine.setAllocatedAmount(null != allocatedAmount?allocatedAmount:BigDecimal.ZERO);
                Assert.notNull(quotaLine.getCompanyName(),"配额明细行供应商不能为空");
                Assert.isTrue(hashSet.add(quotaLine.getCompanyName()),"配额明细行供应商不能重复");
                // 保存时应判定明细表中的供应商是否存在有效价格，即价格有效期大于等于当前日期的价格（需匹配价格表）；
                boolean flag = inqClient.queryValidByVendorItem(new PriceLibraryParamDto().setVendorId(quotaLine.getCompanyId()).setItemId(quotaHead.getMaterialId()).setOrgId(quotaHead.getOrganizationId()));
                Assert.isTrue(flag,quotaLine.getCompanyName()+"不存在该物料有效价格");
                if(null != quotaLine.getQuota()){
                    sum.set(sum.get().add(quotaLine.getQuota()));
                }
            });
            Assert.isTrue(sum.get().compareTo(BigDecimal.valueOf(100)) == 0,"配额比】之和，必须等于100");
        }
    }

    @Override
    public Map<String, QuotaHead> queryQuotaHeadByOrgIdItemId(QuotaParamDto quotaParamDto) {
        Map<String, QuotaHead> result = new HashMap<>();
        List<Long> itemId = quotaParamDto.getItemIds();
        List<Long> orgId = quotaParamDto.getOrgIds();
        List<QuotaHead> quotaHeads = this.list(Wrappers.lambdaQuery(QuotaHead.class).
                in(QuotaHead::getOrganizationId, orgId).
                in(QuotaHead::getMaterialId, itemId).
                ge(QuotaHead::getEndDate,LocalDate.now()).
                le(QuotaHead::getStartDate,LocalDate.now()));
        if(CollectionUtils.isNotEmpty(quotaHeads)){
            quotaHeads.forEach(quotaHead -> {
                List<QuotaLine> list = iQuotaLineService.list(new QueryWrapper<>(new QuotaLine().setQuotaHeadId(quotaHead.getQuotaHeadId())));
                quotaHead.setQuotaLineList(list);
            });

            result = quotaHeads.stream().collect(Collectors.toMap(
                    k -> String.valueOf(k.getOrganizationId()) + String.valueOf(k.getMaterialId()),
                    Function.identity(), (k1, k2) -> k1));
        }
        return result;
    }

    /**
     * 检查配额数据中的供应商必须在价格库中具备有效价格。
     * @param quotaHeadId
     * @return
     */
    @Override
    public String checkVendorIfValidPrice(Long quotaHeadId,Date requirementDate) {
        StringBuffer errorMsg = new StringBuffer();
        QuotaHead quotaHead = this.getById(quotaHeadId);
        List<QuotaLine> quotaLines = iQuotaLineService.list(Wrappers.lambdaQuery(QuotaLine.class).eq(QuotaLine::getQuotaHeadId, quotaHeadId));
        if(CollectionUtils.isNotEmpty(quotaLines)){
            quotaLines.forEach(quotaLine -> {
                boolean flag = inqClient.queryValidByVendorItemDate(new PriceLibraryParamDto().
                        setVendorId(quotaLine.getCompanyId()).
                        setItemId(quotaHead.getMaterialId()).
                        setOrgId(quotaHead.getOrganizationId()).
                        setRequirementDate(requirementDate));
                if(!flag){
                    errorMsg.append("配额数据:"+
                            quotaHead.getOrganizationId()+"+"+
                            quotaHead.getMaterialName()+"+"+
                            quotaLine.getCompanyName()+
                            ",需求日期内不存在有效价格;\n");
                }
            });
        }else {
            errorMsg.append("配额:"+quotaHead.getOrganizationName()+"+"+quotaHead.getMaterialName()+",没有配额明细");
        }
        return errorMsg.toString();
    }

    /**
     * 根据物料ID和组织ID查找有效的配额
     * 配额里的供应商都有有效价格
     * @param quotaParamDto
     * @return
     */
    @Override
    public Map<String, QuotaHead> queryQuotaHeadByOrgIdItemIdAndPriceValid(QuotaParamDto quotaParamDto) {
        Map<String, QuotaHead> result = new HashMap<>();
        List<QuotaHead> heads = new ArrayList<>();
        List<QuotaHead> quotaHeads = this.list(Wrappers.lambdaQuery(QuotaHead.class).
                in(QuotaHead::getOrganizationId, quotaParamDto.getOrgIds()).
                in(QuotaHead::getMaterialId, quotaParamDto.getItemIds()).
                ge(QuotaHead::getEndDate,LocalDate.now()).
                le(QuotaHead::getStartDate,LocalDate.now()));
        if(CollectionUtils.isNotEmpty(quotaHeads)){
            // 检查配额里的供应商是否都存在有效价格
            quotaHeads.forEach(quotaHead -> {
                List<QuotaLine> quotaLines = iQuotaLineService.list(Wrappers.lambdaQuery(QuotaLine.class).eq(QuotaLine::getQuotaHeadId, quotaHead.getQuotaHeadId()));
                if (CollectionUtils.isNotEmpty(quotaLines)) {
                    quotaHead.setQuotaLineList(quotaLines);
                    if(CollectionUtils.isNotEmpty(quotaLines)){
                        List<Long> vendorIds = quotaLines.stream().map(QuotaLine::getCompanyId).collect(Collectors.toList());
                        boolean flag = inqClient.queryValidByBatchVendorItem(
                                QuotaParamDto.builder().
                                        itemId(quotaHead.getMaterialId()).
                                        orgId(quotaHead.getOrganizationId()).
                                        vendorIds(vendorIds).
                                        build()
                        );
                        if(flag){
                            heads.add(quotaHead);
                        }
                    }
                }
            });
        }
        if(CollectionUtils.isNotEmpty(heads)){
            result = heads.stream().collect(Collectors.toMap(k->String.valueOf(k.getOrganizationId())+k.getMaterialId(),Function.identity(),(k1,k2)->k1));
        }
        return result;
    }

    @Override
    public QuotaHead getQuotaHeadByOrgIdItemIdDate(QuotaParamDto quotaParamDto) {
        Long orgId = quotaParamDto.getOrgId();
        Long itemId = quotaParamDto.getItemId();
        Date requirementDate = quotaParamDto.getRequirementDate();
        QuotaHead quotaHead = null;
        AtomicBoolean flag = new AtomicBoolean(false);
        if(StringUtil.notEmpty(orgId) && StringUtil.notEmpty(itemId) && null != requirementDate){
            // 查找有效的对应配额
            List<QuotaHead> quotaHeads = this.list(Wrappers.lambdaQuery(QuotaHead.class).
                    in(QuotaHead::getOrganizationId, orgId).
                    in(QuotaHead::getMaterialId, itemId).
                    ge(QuotaHead::getEndDate,LocalDate.now()).
                    le(QuotaHead::getStartDate,LocalDate.now()));
            if(CollectionUtils.isNotEmpty(quotaHeads)){
                quotaHead = quotaHeads.get(0);
                List<QuotaLine> quotaLines = iQuotaLineService.list(Wrappers.lambdaQuery(QuotaLine.class).eq(QuotaLine::getQuotaHeadId, quotaHead.getQuotaHeadId()));
                quotaHead.setQuotaLineList(quotaLines);
                if(CollectionUtils.isNotEmpty(quotaLines)){
                    for(QuotaLine quotaLine:quotaLines) {
                        // 检查供应商在指定日期里是否存在有效价格
                        if(!inqClient.queryValidByVendorItemDate(new PriceLibraryParamDto().setVendorId(quotaLine.getCompanyId()).
                                setItemId(itemId).setOrgId(orgId).setRequirementDate(requirementDate))){
                            flag.set(true);
                            break;
                        }
                    }
                }
            }
        }
        if(flag.get()){
            quotaHead = null;
        }
        return quotaHead;
    }

    @Override
    @Transactional
    public void updateQuotaLineByQuotaLineDtos(List<QuotaLineDto> quotaLineDtos) {
        if(CollectionUtils.isNotEmpty(quotaLineDtos)){
            quotaLineDtos.forEach(quotaLineDto -> {
                Long quotaLineId = quotaLineDto.getQuotaLineId();
                BigDecimal amount = quotaLineDto.getAmount();
                QuotaLine quotaLine = iQuotaLineService.getById(quotaLineId);
                if (null != quotaLine) {
                    BigDecimal allocatedAmount = quotaLine.getAllocatedAmount();
                    quotaLine.setAllocatedAmount(null != allocatedAmount?allocatedAmount.add(amount):amount);
                    iQuotaLineService.updateById(quotaLine);
                }
            });
        }
    }

    @Override
    public void rollbackQuotaLineByQuotaLineDtos(List<QuotaLineDto> quotaLineDtos) {
        if(CollectionUtils.isNotEmpty(quotaLineDtos)){
            quotaLineDtos.forEach(quotaLineDto -> {
                Long quotaLineId = quotaLineDto.getQuotaLineId();
                // 需扣减数量
                BigDecimal amount = quotaLineDto.getAmount();
                QuotaLine quotaLine = iQuotaLineService.getById(quotaLineId);
                if (null != quotaLine) {
                    BigDecimal allocatedAmount = quotaLine.getAllocatedAmount();
                    allocatedAmount = null != allocatedAmount?allocatedAmount.subtract(amount):BigDecimal.ZERO;
                    quotaLine.setAllocatedAmount(allocatedAmount.compareTo(BigDecimal.ZERO) >= 0 ? allocatedAmount:BigDecimal.ZERO);
                    iQuotaLineService.updateById(quotaLine);
                }
            });
        }
    }
}
