package com.midea.cloud.srm.pr.vendordistdesc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.enums.base.QuotaManagementType;
import com.midea.cloud.common.enums.pm.pr.requirement.IfDistributionVendor;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.DateUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.StringUtil;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.model.base.organization.entity.OrganizationUser;
import com.midea.cloud.srm.model.base.quotaorder.QuotaHead;
import com.midea.cloud.srm.model.base.quotaorder.QuotaLine;
import com.midea.cloud.srm.model.base.quotaorder.dto.QuotaHeadParamDto;
import com.midea.cloud.srm.model.base.quotaorder.dto.QuotaParamDto;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.RequirementLine;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.VendorDistDesc;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.pr.requirement.service.IRequirementLineService;
import com.midea.cloud.srm.pr.vendordistdesc.mapper.VendorDistDescMapper;
import com.midea.cloud.srm.pr.vendordistdesc.service.IVendorDistDescService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
*  <pre>
 *  供应商分配明细表 服务实现类
 * </pre>
*
* @author wangpr@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-12-07 17:01:39
 *  修改内容:
 * </pre>
*/
@Service
public class VendorDistDescServiceImpl extends ServiceImpl<VendorDistDescMapper, VendorDistDesc> implements IVendorDistDescService {
    @Resource
    private IRequirementLineService iRequirementLineService;
    @Resource
    private BaseClient baseClient;

    @Override
    @Transactional
    public void assignSupplier(List<Long> requirementLineIds) {
        // 采购申请行
        Map<RequirementLine, QuotaHead> requirementLineQuotaHeadMap = new HashMap<>();
        if(CollectionUtils.isNotEmpty(requirementLineIds)){
            // 数据不符合规则会报错
            /**
             * a)	采购需求明细行中的【是否已分配供应商】字段，必须为“否”或“分配失败”或“分配中”；
             * b)	采购需求明细行【剩余可下单数量】=【需求数量】；
             * c)	采购需求明细行的物料+业务组织必须在【配额数据管理】功能中已经维护了配额数据，且该配额数据在有效期范围内；
             * d)	配额数据中的供应商必须在价格库中具备有效价格。
             */
            List<RequirementLine> requirementLines = iRequirementLineService.listByIds(requirementLineIds);
            Assert.isTrue(CollectionUtils.isNotEmpty(requirementLines),"没找到选中的需求行数据");
            Map<String, QuotaHead> quotaHeadMap = getQuotaHeadMap(requirementLines);

            requirementLines.forEach(requirementLine -> {
                // 检查是否有不符合的信息
                StringBuffer errorMsg = checkRequirementLine(quotaHeadMap, requirementLine);
                if(errorMsg.length() > 0){
                    requirementLine.setErrorMsg(errorMsg.toString());
                    requirementLine.setIfDistributionVendor(IfDistributionVendor.ALLOCATING_FAILED.name());
                    if(errorMsg.toString().contains("【剩余可下单数量】必须等于【需求数量】")){
                        requirementLine.setIfDistributionVendor(IfDistributionVendor.Y.name());
                    }
                }else {
                    requirementLineQuotaHeadMap.put(requirementLine,quotaHeadMap.get(String.valueOf(requirementLine.getOrgId())+requirementLine.getMaterialId()));
                    requirementLine.setIfDistributionVendor(IfDistributionVendor.ALLOCATING.name());
                    requirementLine.setErrorMsg("");
                }
            });
            iRequirementLineService.updateBatchById(requirementLines);
        }else {
            // 数据不符合规则不处理
            LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
            // 获取用户有权限的组织
            List<OrganizationUser> organizationUsers = loginAppUser.getOrganizationUsers();
            if (CollectionUtils.isNotEmpty(organizationUsers)) {
                // 初始化数据
                initData(organizationUsers);
                /**
                 * 查找要处理的数据
                 * a)	采购需求明细行中的【是否已分配供应商】字段，必须为“否”或“分配失败”；
                 * b)	采购需求明细行【剩余可下单数量】=【需求数量】；
                 * c)	采购需求明细行的物料+业务组织必须在【配额数据管理】功能中已经维护了配额数据，且该配额数据在有效期范围内；
                 * d)	配额数据中的供应商必须在价格库中具备有效价格。
                 */
                // 查找【是否已分配供应商】字段，必须为“否”或“分配失败”；【剩余可下单数量】=【需求数量】；
                List<Long> orgId  = organizationUsers.stream().map(OrganizationUser::getOrganizationId).collect(Collectors.toList());
                // 获取用户有权限操作的需求行
                List<RequirementLine> requirementLines = null;
                if (CollectionUtils.isNotEmpty(orgId)) {
                    requirementLines = this.baseMapper.queryRequirementLine(orgId);
                }
                // 要更新的数据
                List<RequirementLine> requirementLinesUpdate = new ArrayList<>();
                if(CollectionUtils.isNotEmpty(requirementLines)){
                    requirementLines.forEach(requirementLine -> {
                        Assert.notNull(requirementLine.getRequirementDate(),requirementLine.getRequirementHeadNum()+"存在需求日期为空的数据;");
                        QuotaHead quotaHead = baseClient.getQuotaHeadByOrgIdItemIdDate(QuotaParamDto.builder().
                                orgId(requirementLine.getOrgId()).
                                itemId(requirementLine.getMaterialId()).
                                requirementDate(DateUtil.localDateToDate(requirementLine.getRequirementDate())).build());
                        if(null != quotaHead){
                            requirementLine.setErrorMsg(null);
                            requirementLine.setIfDistributionVendor(IfDistributionVendor.ALLOCATING.name());
                            requirementLineQuotaHeadMap.put(requirementLine,quotaHead);
                            requirementLinesUpdate.add(requirementLine);
                        }
                    });
                }
                if(CollectionUtils.isNotEmpty(requirementLinesUpdate)){
                    // 更新状态分批处理中
                    iRequirementLineService.updateBatchById(requirementLinesUpdate);
                }
            }
        }

        // 异步处理
        CompletableFuture.runAsync(() -> {
            // 开始分配供应商
            asynchronousAssignSupplier(requirementLineQuotaHeadMap);
        });
    }

    public void asynchronousAssignSupplier(Map<RequirementLine, QuotaHead> requirementLineQuotaHeadMap) {

        if(!requirementLineQuotaHeadMap.isEmpty()){
            Map<String, BigDecimal> temporaryAllocationMap = new HashMap<>();
            Map<String, BigDecimal> sumMap = new HashMap<>();
            requirementLineQuotaHeadMap.forEach((requirementLine, quotaHead) -> {
                // 配额管理类型
                String quotaManagementType = quotaHead.getQuotaManagementType();
                // 配额供应商明细
                List<QuotaLine> quotaLineList = quotaHead.getQuotaLineList();
                // 分配供应商明细
                List<VendorDistDesc> vendorDistDescs = new ArrayList<>();
                if(QuotaManagementType.QUOTA_ACHIEVEMENT_RATE.name().equals(quotaManagementType)){ // 配额达成率
                    /**
                     * 比较该需求中的【剩余可下单数量】是否大于物料配额档案中的【最大分配量】：
                     * 名词解释：	【临时已分配量】为在本次分配计算任务中，该组织+物料+供应商已经临时获得的，存入供应商分配明细页中的【本次分配数量】，
                     * 从而避免多个申请重复分配同一个供应商。
                     * 【供应商已分配量】为物料配额表中各个供应商的【已分配量】数值   quotaAchievementRate
                     */
                    if(requirementLine.getOrderQuantity().compareTo(quotaHead.getMaxAllocation()) <= 0){
                        /**
                         * a)若【剩余可下单数量】≤【最大分配量】，则比较该物料配额数据下所有供应商分配比重，
                         * 将本次待分配数量全部分配给计算比重最小的供应商（若最小比重一样，则选择配额比最大的供应商），
                         * 即该需求的剩余下单数量只分配给一个供应商。比重计算公式为：比重=【（供应商已分配量+配额基数+临时已分配量）/配额比】；
                         */
                        vendorDistDescs.addAll(vendorDistDescsAdd(sumMap,temporaryAllocationMap, requirementLine, quotaHead, quotaLineList,null));
                    }else {
                        /**
                         * b)若【剩余可下单数量】>【最大分配量】，则先将需求数量按【最大分配量】对【剩余可下单数量】进行分配明细拆分，
                         * 直至每一笔分配明细的【待分配数量】≤【最大分配量】。拆分后的每一笔待分配数量按“【本次待分配量】≤【最大分配量】”
                         * （即a情况处理）的情况进行处理，每笔分配给单独供应商。
                         */
                        List<VendorDistDesc> vendorDistDescTemps = new ArrayList<>();
                        // 尾数
                        double mantissa  = requirementLine.getOrderQuantity().doubleValue() % quotaHead.getMaxAllocation().doubleValue();
                        boolean flag = mantissa > 0;
                        int count = (int) Math.floor(requirementLine.getOrderQuantity().doubleValue() / quotaHead.getMaxAllocation().doubleValue());
                        count = flag ? count+1 : count;
                        for(int j = 0; j < count; j++){
                            if(j == count - 1 && flag){
                                vendorDistDescTemps.addAll(vendorDistDescsAdd(sumMap,temporaryAllocationMap, requirementLine, quotaHead, quotaLineList,BigDecimal.valueOf(mantissa)));
                            }else {
                                vendorDistDescTemps.addAll(vendorDistDescsAdd(sumMap,temporaryAllocationMap, requirementLine, quotaHead, quotaLineList,quotaHead.getMaxAllocation()));
                            }
                        }
                        // 分组合并
                        if (CollectionUtils.isNotEmpty(vendorDistDescTemps)) {
                            Map<Long, List<VendorDistDesc>> collect = vendorDistDescTemps.stream().collect(Collectors.groupingBy(VendorDistDesc::getCompanyId));
                            collect.forEach((companyId, vendorDists) -> {
                                // 合并数量
                                BigDecimal decimal = vendorDists.stream().map(VendorDistDesc::getPlanAmount).reduce(BigDecimal::add).get();
                                VendorDistDesc vendorDistDesc = vendorDists.get(0);
                                vendorDistDesc.setPlanAmount(decimal);
                                vendorDistDescs.add(vendorDistDesc);
                            });
                        }
                    }
                }else if(QuotaManagementType.FIXED_RATIO.name().equals(quotaManagementType)){ // 固定比例
                    /**
                     * 比较该需求中的【剩余可下单数量】是否小于物料配额档案中的【最小拆单量】：fixedRatio
                     * a)	若【剩余可下单数量】<【最小拆单量】，则使用“配额达成率”的分配算法。
                     * b)	若【剩余可下单数量】≥【最小拆单量】，则每个供应商分配固定比例的数量，分配数量=【剩余可下单数量】*【供应商配额比例】/100
                     */
                    if(requirementLine.getOrderQuantity().compareTo(quotaHead.getMiniSplit()) < 0){
                        /**
                         * 【剩余可下单数量】<【最小拆单量】，则使用“配额达成率”的分配算法。
                         */
                        vendorDistDescs.addAll(vendorDistDescsAdd(sumMap,temporaryAllocationMap, requirementLine, quotaHead, quotaLineList,null));
                    }else {
                        /**
                         * 剩余可下单数量】≥【最小拆单量】，则每个供应商分配固定比例的数量，分配数量=【剩余可下单数量】*【供应商配额比例】/100
                         */
                        fixedRatioExecution(sumMap,requirementLine, quotaHead, vendorDistDescs,temporaryAllocationMap);
                    }
                }else { // 综合比例
                    /**
                     * 比较该需求中的【剩余可下单数量】是否小于物料配额档案中的【最小拆单量】： comprehensiveRatio
                     * a)	若【剩余可下单数量】<【最小拆单量】，则使用“配额达成率”的分配算法。
                     * b)	若【剩余可下单数量】≥【最小拆单量】，则每个供应商分配一定比例的数量：
                     * i.	首先计算需分配量，需分配数量=（【已分配物料总数量】+【临时已分配总量】+【剩余可下单数量】）*【供应商配额比例】-【供应商临时分配量】-【供应商已分配量】。
                     * ii.	然后确定本次分配数量，分配数量应按照【需分配数量】从大到小，将需求中的【剩余可下单数量】依次分配，直至分配结束为止
                     */
                    if(requirementLine.getOrderQuantity().compareTo(quotaHead.getMiniSplit()) < 0){
                        /**
                         * 【剩余可下单数量】<【最小拆单量】 则使用“配额达成率”的分配算法。
                         */
                        vendorDistDescs.addAll(vendorDistDescsAdd(sumMap,temporaryAllocationMap, requirementLine, quotaHead, quotaLineList,null));
                    }else {
                        /**
                         * 若【剩余可下单数量】≥【最小拆单量】，则每个供应商分配一定比例的数量：
                         * * 1.	首先计算需分配量，需分配数量=（【已分配物料总数量】+【临时已分配总量】+【剩余可下单数量】）*【供应商配额比例】-【供应商临时分配量】-【供应商已分配量】。
                         * * 2. 然后确定本次分配数量，分配数量应按照【需分配数量】从大到小，将需求中的【剩余可下单数量】依次分配，直至分配结束为止
                         * 【已分配物料总数量】即该物料已经分配的总数量；
                         * 【临时已分配总量】为在本次分配计算任务中，该该物料已经临时获得的，存入供应商分配明细页中的【本次分配数量】；
                         * 【供应商临时分配量】为在本次分配计算任务中，该该物料+供应商已经临时获得的，存入供应商分配明细页中的【本次分配数量】
                         * 【已分配量】为物料配额表中各供应商的【已分配量】数值
                         */
                        comprehensiveRatioExecution(temporaryAllocationMap, sumMap, requirementLine, quotaHead, vendorDistDescs);
                    }
                }
                // 保存供应商,更新状态
                saveVendorAndUpdateStatus(requirementLine, vendorDistDescs);
            });
        }
    }

    public void comprehensiveRatioExecution(Map<String, BigDecimal> temporaryAllocationMap, Map<String, BigDecimal> sumMap, RequirementLine requirementLine, QuotaHead quotaHead, List<VendorDistDesc> vendorDistDescs) {
        double orderQuantity = requirementLine.getOrderQuantity().doubleValue(); // 剩余可下单数量
        List<QuotaLine> quotaLines = quotaHead.getQuotaLineList();
        if(CollectionUtils.isNotEmpty(quotaLines)){
            // 该物料已分配总量
            double num1 = quotaLines.stream().
                    map(quotaLine -> null != quotaLine.getAllocatedAmount()?quotaLine.getAllocatedAmount():BigDecimal.ZERO).
                    reduce(BigDecimal::add).get().doubleValue();
            BigDecimal num2Temp = sumMap.get(String.valueOf(quotaHead.getOrganizationId()) + quotaHead.getMaterialId());
            // 临时已分配总量
            double num2 = null != num2Temp?num2Temp.doubleValue():0;
            quotaLines.forEach(quotaLine -> {
                // 计算需分配量=（【已分配物料总数量】+【临时已分配总量】+【剩余可下单数量】）*【供应商配额比例】-【供应商临时分配量】-【供应商已分配量】。
                String key = String.valueOf(quotaHead.getOrganizationId()) + quotaHead.getMaterialId() + quotaLine.getCompanyId();
                BigDecimal tempNum = temporaryAllocationMap.get(key);
                // 供应商临时分配量
                double num3 = null != tempNum?tempNum.doubleValue():0;
                // 供应商已分配量
                BigDecimal allocatedAmount = quotaLine.getAllocatedAmount();
                double num4 = null != allocatedAmount?allocatedAmount.doubleValue():0;
                // 供应商配额比例
                double num5 = quotaLine.getQuota().doubleValue()/100;
                // 分配量
                double num6 = ((num1+num2+orderQuantity)*num5) - num3 - num4;
                quotaLine.setAllocation(num6);
            });
            quotaLines.sort((o1, o2) -> (int)(o2.getAllocation()-o1.getAllocation()));
            // 可下单数量
            double num = orderQuantity;
            for(QuotaLine quotaLine:quotaLines) {
                VendorDistDesc vendorDistDesc = VendorDistDesc.builder()
                        .vendorDistDescId(IdGenrator.generate())
                        .requirementHeadId(requirementLine.getRequirementHeadId())
                        .requirementLineId(requirementLine.getRequirementLineId())
                        .companyId(quotaLine.getCompanyId())
                        .companyCode(quotaLine.getCompanyCode())
                        .companyName(quotaLine.getCompanyName())
                        .quota(quotaLine.getQuota())
                        .actualAmount(BigDecimal.ZERO)
                        .quotaHeadId(quotaLine.getQuotaHeadId())
                        .quotaLineId(quotaLine.getQuotaLineId())
                        .build();
                // 需分配量
                double allocation = quotaLine.getAllocation();
                if(num >= allocation){
                    vendorDistDesc.setPlanAmount(BigDecimal.valueOf(allocation));

                    String key = String.valueOf(quotaHead.getOrganizationId()) + quotaHead.getMaterialId() + quotaLine.getCompanyId();
                    // 获取临时累计值
                    BigDecimal tempNum = temporaryAllocationMap.get(key);
                    temporaryAllocationMap.put(key,null != tempNum?tempNum.add(vendorDistDesc.getPlanAmount()):vendorDistDesc.getPlanAmount());

                    key = String.valueOf(quotaHead.getOrganizationId()) + quotaHead.getMaterialId();
                    BigDecimal tempSumNum = sumMap.get(key);
                    sumMap.put(key,null != tempSumNum?tempSumNum.add(vendorDistDesc.getPlanAmount()):vendorDistDesc.getPlanAmount());

                    num = num-allocation;

                    vendorDistDescs.add(vendorDistDesc);
                }else {
                    vendorDistDesc.setPlanAmount(BigDecimal.valueOf(num));
                    String key = String.valueOf(quotaHead.getOrganizationId()) + quotaHead.getMaterialId() + quotaLine.getCompanyId();
                    // 获取临时累计值
                    BigDecimal tempNum = temporaryAllocationMap.get(key);
                    temporaryAllocationMap.put(key,null != tempNum?tempNum.add(vendorDistDesc.getPlanAmount()):vendorDistDesc.getPlanAmount());

                    key = String.valueOf(quotaHead.getOrganizationId()) + quotaHead.getMaterialId();
                    BigDecimal tempSumNum = sumMap.get(key);
                    sumMap.put(key,null != tempSumNum?tempSumNum.add(vendorDistDesc.getPlanAmount()):vendorDistDesc.getPlanAmount());

                    vendorDistDescs.add(vendorDistDesc);
                    break;
                }
            }
        }
    }

    public void fixedRatioExecution(Map<String, BigDecimal> sumMap, RequirementLine requirementLine, QuotaHead quotaHead, List<VendorDistDesc> vendorDistDescs, Map<String, BigDecimal> temporaryAllocationMap) {
        double orderQuantity = requirementLine.getOrderQuantity().doubleValue();
        List<QuotaLine> quotaLines = quotaHead.getQuotaLineList();
        if(CollectionUtils.isNotEmpty(quotaLines)){
            quotaLines.forEach(quotaLine -> {
                VendorDistDesc vendorDistDesc = VendorDistDesc.builder()
                        .vendorDistDescId(IdGenrator.generate())
                        .requirementHeadId(requirementLine.getRequirementHeadId())
                        .requirementLineId(requirementLine.getRequirementLineId())
                        .companyId(quotaLine.getCompanyId())
                        .companyCode(quotaLine.getCompanyCode())
                        .companyName(quotaLine.getCompanyName())
                        .quota(quotaLine.getQuota())
                        .quotaHeadId(quotaLine.getQuotaHeadId())
                        .quotaLineId(quotaLine.getQuotaLineId())
                        .build();
                // 分配数量
                double num = quotaLine.getQuota().doubleValue() / 100 * orderQuantity;
                vendorDistDesc.setPlanAmount(BigDecimal.valueOf(num));
                vendorDistDesc.setActualAmount(BigDecimal.ZERO);
                vendorDistDescs.add(vendorDistDesc);


                String key = String.valueOf(quotaHead.getOrganizationId()) + quotaHead.getMaterialId() + quotaLine.getCompanyId();
                // 获取临时累计值
                BigDecimal tempNum = temporaryAllocationMap.get(key);
                temporaryAllocationMap.put(key, null != tempNum? tempNum.add(BigDecimal.valueOf(num)):BigDecimal.valueOf(num));

                key = String.valueOf(quotaHead.getOrganizationId()) + quotaHead.getMaterialId();
                BigDecimal tempSumNum = sumMap.get(key);
                sumMap.put(key,null != tempSumNum?tempSumNum.add(BigDecimal.valueOf(num)):BigDecimal.valueOf(num));
            });
        }
    }

    public List<VendorDistDesc> vendorDistDescsAdd(Map<String, BigDecimal> sumMap, Map<String, BigDecimal> temporaryAllocationMap, RequirementLine requirementLine, QuotaHead quotaHead, List<QuotaLine> quotaLineList, BigDecimal mantissa) {
        List<VendorDistDesc> vendorDistDescs = new ArrayList<>();
        BigDecimal orderQuantity = null != mantissa ? mantissa : requirementLine.getOrderQuantity();
        // 计算比重
        quotaLineList.forEach(quotaLine -> {
            /**
             * 比重=【（供应商已分配量+配额基数+临时已分配量）/配额比】；
             */
            BigDecimal tempNum = temporaryAllocationMap.get(String.valueOf(quotaHead.getOrganizationId()) + quotaHead.getMaterialId() + quotaLine.getCompanyId());
            // 临时已分配量
            double num1 = null != tempNum?tempNum.doubleValue():0;
            BigDecimal allocatedAmount = quotaLine.getAllocatedAmount();
            // 供应商已分配量
            double num2 = null != allocatedAmount?allocatedAmount.doubleValue():0;
            BigDecimal baseNum = quotaLine.getBaseNum();
            double num3 = null != baseNum?baseNum.doubleValue():0;
            // 比重
            double num4 = (num2 + num3 + num1)/ quotaLine.getQuota().doubleValue();
            quotaLine.setProportion(BigDecimal.valueOf(num4));
            quotaLine.setDistributionTemp(BigDecimal.valueOf(num1));
        });
        // 根据比重进行排序
        quotaLineList.sort((o1, o2) -> o1.getProportion().compareTo(o2.getProportion()));
        for(int i = 0;i < quotaLineList.size();i++){
            QuotaLine quotaLine = quotaLineList.get(i);
            VendorDistDesc vendorDistDesc = VendorDistDesc.builder()
                    .vendorDistDescId(IdGenrator.generate())
                    .requirementHeadId(requirementLine.getRequirementHeadId())
                    .requirementLineId(requirementLine.getRequirementLineId())
                    .companyId(quotaLine.getCompanyId())
                    .companyCode(quotaLine.getCompanyCode())
                    .companyName(quotaLine.getCompanyName())
                    .quota(quotaLine.getQuota())
                    .quotaHeadId(quotaLine.getQuotaHeadId())
                    .quotaLineId(quotaLine.getQuotaLineId())
                    .build();
            if(0 == i){
                vendorDistDesc.setPlanAmount(orderQuantity);
                vendorDistDesc.setActualAmount(BigDecimal.ZERO);
                String key = String.valueOf(quotaHead.getOrganizationId()) + quotaHead.getMaterialId() + quotaLine.getCompanyId();
                // 获取临时累计值
                BigDecimal tempNum = temporaryAllocationMap.get(key);
                temporaryAllocationMap.put(key,null != tempNum?tempNum.add(orderQuantity):orderQuantity);

                key = String.valueOf(quotaHead.getOrganizationId()) + quotaHead.getMaterialId();
                BigDecimal tempSumNum = sumMap.get(key);
                sumMap.put(key,null != tempSumNum?tempSumNum.add(orderQuantity):orderQuantity);
            }else {
                vendorDistDesc.setPlanAmount(BigDecimal.ZERO);
                vendorDistDesc.setActualAmount(BigDecimal.ZERO);
            }
            vendorDistDescs.add(vendorDistDesc);
        }
        return vendorDistDescs;
    }

    public static void main(String[] args) {
        NumberFormat nf1 = NumberFormat.getInstance(new Locale("zh", "CN"));
        // 不采取四舍五入策略
        nf1.setRoundingMode(RoundingMode.DOWN);
        double bigDecimal = BigDecimal.valueOf(35).doubleValue();
        double bigDecima2 = BigDecimal.valueOf(7).doubleValue();
        double num = bigDecimal/bigDecima2;
        System.out.println(num);
        System.out.println((int) Math.floor(BigDecimal.valueOf(35).doubleValue()/BigDecimal.valueOf(6).doubleValue()));
        System.out.println(bigDecimal%bigDecima2);
        System.out.println(bigDecimal%bigDecima2 == 0);

        double num1 = 23.4567;
        System.out.println((int)num1);
        double num2 = 23.7567;
        System.out.println((int)num2);
    }

    // 保存供应商,更新状态
    @Transactional
    public void saveVendorAndUpdateStatus(RequirementLine requirementLine, List<VendorDistDesc> vendorDistDescs) {
        if (CollectionUtils.isNotEmpty(vendorDistDescs)) {
            this.saveBatch(vendorDistDescs);
        }
        // 更新状态
        requirementLine.setIfDistributionVendor(IfDistributionVendor.Y.name());
        iRequirementLineService.updateById(requirementLine);
    }

    public void initData(List<OrganizationUser> organizationUsers) {
        if (CollectionUtils.isNotEmpty(organizationUsers)) {
            /**
             * （1）	需将该用户组织权限内所有【是否已分配供应商】为“是”，
             * 且【剩余可下单数量】=【需求数量】的采购申请行数据中的供应商分配数据清空，
             * 并将【是否已分配供应商】变更为“否”（即代表该用户权限内所有还没下单的采购申请分配数据，先清空，后重算写入的方式）；
             */
            List<Long> orgId  = organizationUsers.stream().map(OrganizationUser::getOrganizationId).collect(Collectors.toList());
            // 获取要更新的行id
            List<Long> ids = this.baseMapper.queryRequirementLinebyOrgs(orgId);
            iRequirementLineService.update(Wrappers.lambdaUpdate(RequirementLine.class).
                    set(RequirementLine::getIfDistributionVendor, IfDistributionVendor.N.name()).
                    in(RequirementLine::getRequirementLineId,ids));
            // 清楚行分配供应商数据
            this.remove(Wrappers.lambdaQuery(VendorDistDesc.class).in(VendorDistDesc::getRequirementLineId,ids));
        }
    }

    public StringBuffer checkRequirementLine(Map<String, QuotaHead> quotaHeadMap, RequirementLine requirementLine) {
        /**
         *  需将该用户组织权限内所有【是否已分配供应商】为“是”，且【剩余可下单数量】=【需求数量】的采购申请行数据中的供应商分配数据清空，
         *  并将【是否已分配供应商】变更为“否”（即代表该用户权限内所有还没下单的采购申请分配数据，先清空，后重算写入的方式）；
         */
        if(IfDistributionVendor.Y.name().equals(requirementLine.getIfDistributionVendor()) &&
                (null != requirementLine.getRequirementQuantity() &&
                        null != requirementLine.getOrderQuantity() &&
                        requirementLine.getRequirementQuantity().equals(requirementLine.getOrderQuantity()))
        ){
            requirementLine.setIfDistributionVendor(IfDistributionVendor.N.name());
            this.remove(new QueryWrapper<>(new VendorDistDesc().setRequirementLineId(requirementLine.getRequirementLineId())));
        }

        StringBuffer errorMsg = new StringBuffer();

        // 采购需求明细行中的【是否已分配供应商】字段，必须为“否”或“分配失败”或“分配中”；
        String ifDistributionVendor = requirementLine.getIfDistributionVendor();
        if(IfDistributionVendor.Y.name().equals(ifDistributionVendor) ||
                IfDistributionVendor.ALLOCATING.name().equals(ifDistributionVendor)){
            errorMsg.append("【是否已分配供应商】需为“否”或“分配失败”;\n");
        }
        // 采购需求明细行【剩余可下单数量】=【需求数量】；
        if(null == requirementLine.getRequirementQuantity() ||
                null == requirementLine.getOrderQuantity() ||
                !requirementLine.getRequirementQuantity().equals(requirementLine.getOrderQuantity())){
            errorMsg.append("【剩余可下单数量】必须等于【需求数量】;\n");
        }
        // 采购需求明细行的物料+业务组织必须在【配额数据管理】功能中已经维护了配额数据，且该配额数据在有效期范围内；
        String key = String.valueOf(requirementLine.getOrgId())+String.valueOf(requirementLine.getMaterialId());
        QuotaHead quotaHead = quotaHeadMap.get(key);
        if(null == quotaHead){
            errorMsg.append(String.valueOf(requirementLine.getOrgName())+"+"+String.valueOf(requirementLine.getMaterialName())+
                    ",需在【配额数据管理】功能中维护配额数据;\n");
        }else {
            if (null != requirementLine.getRequirementDate()) {
                // 配额数据中的供应商必须在价格库中具备有效价格。
                String msg = baseClient.checkVendorIfValidPrice(new QuotaHeadParamDto().setQuotaHeadId(quotaHead.getQuotaHeadId()).setRequirementDate(DateUtil.localDateToDate(requirementLine.getRequirementDate())));
                if(StringUtil.notEmpty(msg)){
                    errorMsg.append(msg);
                }
            }else {
                errorMsg.append("需求日期不能为空;\n");
            }
        }
        return errorMsg;
    }

    public Map<String, QuotaHead> getQuotaHeadMap(List<RequirementLine> requirementLines) {
        Map<String, QuotaHead> quotaHeadMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(requirementLines)) {
            List<Long> orgIds = new ArrayList<>();
            List<Long> itemIds = new ArrayList<>();

            requirementLines.forEach(requirementLine -> {
                orgIds.add(requirementLine.getOrgId());
                itemIds.add(requirementLine.getMaterialId());
            });
            quotaHeadMap = baseClient.queryQuotaHeadByOrgIdItemId(QuotaParamDto.builder().orgIds(orgIds).itemIds(itemIds).build());
        }
        return quotaHeadMap;
    }
}
