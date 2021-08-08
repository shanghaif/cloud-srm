package com.midea.cloud.srm.base.datatransfer.service;

import com.alibaba.excel.util.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.StringUtil;
import com.midea.cloud.dynamicds.bind.CheckModuleHolder;
import com.midea.cloud.dynamicds.enums.Module;
import com.midea.cloud.srm.base.datatransfer.entity.*;
import com.midea.cloud.srm.base.datatransfer.mapper.DataTransferBaseOuGroupBidTempMapper;
import com.midea.cloud.srm.base.datatransfer.mapper.DataTransferBaseOuGroupBrgTempMapper;
import com.midea.cloud.srm.base.datatransfer.mapper.DataTransferBidOrderLine1Mapper;
import com.midea.cloud.srm.base.datatransfer.mapper.DataTransferBrgOrderLine1Mapper;
import com.midea.cloud.srm.base.organization.mapper.OrganizationMapper;
import com.midea.cloud.srm.base.ou.service.IBaseOuDetailService;
import com.midea.cloud.srm.base.ou.service.IBaseOuGroupService;
import com.midea.cloud.srm.model.base.formula.enums.StuffStatus;
import com.midea.cloud.srm.model.base.organization.entity.Organization;
import com.midea.cloud.srm.model.base.ou.entity.BaseOuDetail;
import com.midea.cloud.srm.model.base.ou.entity.BaseOuGroup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author tanjl11
 * @date 2020/10/31 10:55
 */
@Component
@Slf4j
public class DataTransferOuGroupTransfer {

    @Autowired
    private DataTransferBaseOuGroupBidTempMapper bidTempMapper;
    @Autowired
    private DataTransferBaseOuGroupBrgTempMapper brgTempMapper;
    @Autowired
    private DataTransferBidRequirement1Service bidRequirement1Service;
    @Autowired
    private DataTransferBrgRequirementLine1Service brgRequirementLine1Service;
    @Autowired
    private DataTransferBidOrderLine1Mapper bidOrderLine1Mapper;
    @Autowired
    private DataTransferBrgOrderLine1Mapper brgOrderLine1Mapper;
    @Autowired
    private IBaseOuGroupService baseOuGroupService;
    @Autowired
    private IBaseOuDetailService detailService;
    @Autowired
    private OrganizationMapper organizationMapper;

    public void transferBidOuGroup() {
        //1、ou组分组并保存到业务表里面
        List<DataTransferBaseOuGroupBidTemp> baseOuGroupBidTemps = bidTempMapper.selectList(Wrappers.lambdaQuery(DataTransferBaseOuGroupBidTemp.class));
        Map<String, List<DataTransferBaseOuGroupBidTemp>> map = baseOuGroupBidTemps.stream().collect(Collectors.groupingBy(DataTransferBaseOuGroupBidTemp::getBidNum));
        Set<String> erpOrgNames = baseOuGroupBidTemps.stream().filter(e -> StringUtil.notEmpty(e.getOrgErpName())).map(DataTransferBaseOuGroupBidTemp::getOrgErpName).map(String::trim).collect(Collectors.toSet());
        Set<String> erpInvNames = baseOuGroupBidTemps.stream().filter(e -> StringUtil.notEmpty(e.getOrgErpName())).map(DataTransferBaseOuGroupBidTemp::getInvErpName).map(e -> e.replace('（', '(').replace('）', ')').trim()).collect(Collectors.toSet());
        //构造ou组的名称
        Map<String, Organization> nameOrgMap = organizationMapper.selectList(Wrappers.lambdaQuery(Organization.class)
                .in(Organization::getOrganizationName, erpOrgNames)
        ).stream().collect(Collectors.toMap(e -> e.getOrganizationName().trim(), Function.identity()));
        Set<String> orgSet = nameOrgMap.keySet();
        for (String erpOrgName : erpOrgNames) {
            if (!orgSet.contains(erpOrgName)) {
                log.warn(String.format("转OU组找不到%s的业务组织信息", erpOrgName));
            }
        }
        //根据inv分组
        Map<String, Organization> nameInvMap = organizationMapper.selectList(Wrappers.lambdaQuery(Organization.class)
                .in(Organization::getOrganizationName, erpInvNames)
        ).stream().map(e -> e.setOrganizationName(e.getOrganizationName().replace('（', '(').replace('）', ')').trim())).collect(Collectors.toMap(Organization::getOrganizationName, Function.identity()));
        Set<String> invSet = nameInvMap.keySet();
        for (String erpInvName : erpInvNames) {
            if (!invSet.contains(erpInvName)) {
                log.warn(String.format("转OU组找不到%s的库存组织信息", erpInvName));
            }
        }
        List<BaseOuDetail> details = new LinkedList<>();
        List<BaseOuGroup> groups = new LinkedList<>();
        for (Map.Entry<String, List<DataTransferBaseOuGroupBidTemp>> codeMap : map.entrySet()) {
            String key = codeMap.getKey();
            BaseOuGroup group = new BaseOuGroup();
            long groupId = IdGenrator.generate();
            group.setGroupStatus(StuffStatus.ACTIVE.getStatus())
                    .setOuGroupId(groupId)
                    .setOuGroupCode(key)
                    .setOuGroupName(String.format("H:%s的ou组", key));

            boolean add = true;
            List<BaseOuDetail> temp = new LinkedList<>();
            for (DataTransferBaseOuGroupBidTemp baseOuGroupBidTemp : codeMap.getValue()) {
                BaseOuDetail detail = new BaseOuDetail();
                Organization org = nameOrgMap.get(baseOuGroupBidTemp.getOrgErpName().replace('（', '(').replace('）', ')').trim());
                String invErpName = baseOuGroupBidTemp.getInvErpName().replace('（', '(').replace('）', ')').trim();
                Organization inv = nameInvMap.get(invErpName);
                if (Objects.isNull(inv) || Objects.isNull(org)) {
                    add = false;
                    continue;
                }
                detail.setOuName(org.getOrganizationName())
                        .setOuCode(org.getOrganizationCode())
                        .setBuId(org.getDivisionId())
                        .setBuName(org.getDivision())
                        .setInvId(inv.getOrganizationId())
                        .setInvCode(inv.getOrganizationCode())
                        .setInvName(inv.getOrganizationName())
                        .setOuGroupId(groupId)
                        .setOuId(org.getOrganizationId())
                        .setOuDetailId(IdGenrator.generate())
                ;
                temp.add(detail);
            }
            //只加入现在系统有的ou组id
            if (add) {
                groups.add(group);
                details.addAll(temp);
            }
        }
        List<Long> ouIds = baseOuGroupService.list(Wrappers.lambdaQuery(BaseOuGroup.class)
                .select(BaseOuGroup::getOuGroupId)
                .in(BaseOuGroup::getOuGroupCode, map.keySet())
        ).stream().map(BaseOuGroup::getOuGroupId).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(ouIds)) {
            detailService.remove(Wrappers.lambdaQuery(BaseOuDetail.class)
                    .in(BaseOuDetail::getOuGroupId, ouIds)
            );
        }
        if (!CollectionUtils.isEmpty(ouIds)) {
            baseOuGroupService.removeByIds(ouIds);
        }
        baseOuGroupService.saveBatch(groups);
        detailService.saveBatch(details);

        List<BaseOuGroup> updateNameList = new LinkedList<>();
        CheckModuleHolder.checkout(Module.BID);
        //回写招标缓存行的id,记录没有对应的ou组的行
        //1.选择2020之后未创建合同的数据，中标标志为Y或P
        List<DataTransferBidOrderLine1> bidOrderLine1s = bidOrderLine1Mapper.selectList(Wrappers.lambdaQuery(DataTransferBidOrderLine1.class)
                .eq(DataTransferBidOrderLine1::getCreateContractFlag, "N")
                .nested(e -> e.eq(DataTransferBidOrderLine1::getSuggestedFlag, "Y").or().eq(DataTransferBidOrderLine1::getSuggestedFlag, "P"))
                .ge(DataTransferBidOrderLine1::getCreationDate, "2020")
        );
        Set<Long> requirementIds = bidOrderLine1s.stream().map(DataTransferBidOrderLine1::getRequirementLineId).collect(Collectors.toSet());
        List<DataTransferBidRequirementLine1> bidRequirementLine1s = bidRequirement1Service.listByIds(requirementIds);
        Map<String, List<DataTransferBidRequirementLine1>> numberMap = bidRequirementLine1s.stream().collect(Collectors.groupingBy(DataTransferBidRequirementLine1::getRequirementNumber));
        //设置ou组信息
        List<DataTransferBidRequirementLine1> param = new LinkedList<>();
        for (Map.Entry<String, List<DataTransferBidRequirementLine1>> entry : numberMap.entrySet()) {
            String key = entry.getKey();
            List<DataTransferBidRequirementLine1> value = entry.getValue();
            for (BaseOuGroup ou : groups) {
                if (Objects.equals(ou.getOuGroupCode(), key)) {
                    for (DataTransferBidRequirementLine1 bidRequirementLine1 : value) {
                        DataTransferBidRequirementLine1 temp = new DataTransferBidRequirementLine1();
                        temp.setRequirementLineId(bidRequirementLine1.getRequirementLineId());
                        temp.setCeeaOuId(ou.getOuGroupId())
                                .setCeeaOuName(ou.getOuGroupName())
                                .setCeeaOuNumber(ou.getOuGroupCode());
                        temp.setCeeaIsBaseOu("N").setOrgName("");
                        param.add(temp);
                        ou.setOuGroupName(bidRequirementLine1.getCeeaOuName());
                    }
                    updateNameList.add(ou);
                }
            }
        }
        //回写ouId
        if (!CollectionUtils.isEmpty(param)) {
            bidRequirement1Service.updateBatchById(param);
        }
        CheckModuleHolder.release();
        CheckModuleHolder.checkout(Module.BASE);
        if (!CollectionUtils.isEmpty(updateNameList)) {
            baseOuGroupService.updateBatchById(updateNameList);
        }
        CheckModuleHolder.release();
    }


    public void transferBrgOuGroup() {
        //ou组分组
        List<DataTransferBaseOuGroupBrgTemp> baseOuGroupBrgTemps = brgTempMapper.selectList(Wrappers.lambdaQuery(DataTransferBaseOuGroupBrgTemp.class));
        Map<String, List<DataTransferBaseOuGroupBrgTemp>> map = baseOuGroupBrgTemps.stream().collect(Collectors.groupingBy(DataTransferBaseOuGroupBrgTemp::getBrgNum));
        Set<String> erpOrgNames = baseOuGroupBrgTemps.stream().filter(e -> StringUtil.notEmpty(e.getOrgErpName())).map(DataTransferBaseOuGroupBrgTemp::getOrgErpName).map(String::trim).collect(Collectors.toSet());
        Set<String> erpInvNames = baseOuGroupBrgTemps.stream().filter(e -> StringUtil.notEmpty(e.getOrgErpName())).map(DataTransferBaseOuGroupBrgTemp::getInvErpName).map(e -> e.replace('（', '(').replace('）', ')').trim()).collect(Collectors.toSet());
        //构造ou组的名称
        Map<String, Organization> nameOrgMap = organizationMapper.selectList(Wrappers.lambdaQuery(Organization.class)
                .in(Organization::getOrganizationName, erpOrgNames)
        ).stream().map(e -> e.setOrganizationName(e.getOrganizationName().replace('（', '(').replace('）', ')').trim())).collect(Collectors.toMap(Organization::getOrganizationName, Function.identity()));
        Set<String> orgSet = nameOrgMap.keySet();
        for (String erpOrgName : erpOrgNames) {
            if (!orgSet.contains(erpOrgName)) {
                log.warn(String.format("转OU组找不到%s的业务组织信息", erpOrgName));
            }
        }
        Map<String, Organization> nameInvMap = organizationMapper.selectList(Wrappers.lambdaQuery(Organization.class)
                .in(Organization::getOrganizationName, erpInvNames)
        ).stream().map(e -> e.setOrganizationName(e.getOrganizationName().replace('（', '(').replace('）', ')').trim())).collect(Collectors.toMap(Organization::getOrganizationName, Function.identity()));
        Set<String> invSet = nameInvMap.keySet();
        for (String erpInvName : erpInvNames) {
            if (!invSet.contains(erpInvName)) {
                log.warn(String.format("转OU组找不到%s的库存组织信息", erpInvName));
            }
        }
        List<BaseOuDetail> details = new LinkedList<>();
        List<BaseOuGroup> groups = new LinkedList<>();
        for (Map.Entry<String, List<DataTransferBaseOuGroupBrgTemp>> codeMap : map.entrySet()) {
            String key = codeMap.getKey();
            BaseOuGroup group = new BaseOuGroup();
            long groupId = IdGenrator.generate();
            group.setGroupStatus(StuffStatus.ACTIVE.getStatus())
                    .setOuGroupId(groupId)
                    .setOuGroupCode(key)
                    .setOuGroupName(String.format("H:%s的ou组", key));
            boolean add = true;
            List<BaseOuDetail> temp = new LinkedList<>();
            for (DataTransferBaseOuGroupBrgTemp baseOuGroupBrgTemp : codeMap.getValue()) {
                BaseOuDetail detail = new BaseOuDetail();
                Organization org = nameOrgMap.get(baseOuGroupBrgTemp.getOrgErpName().replace('（', '(').replace('）', ')').trim());
                String invErpName = baseOuGroupBrgTemp.getInvErpName().replace('（', '(').replace('）', ')').trim();
                Organization inv = nameInvMap.get(invErpName);
                if (Objects.isNull(org) || Objects.isNull(inv)) {
                    add = false;
                    continue;
                }
                detail.setOuName(org.getOrganizationName())
                        .setOuCode(org.getOrganizationCode())
                        .setBuId(org.getDivisionId())
                        .setBuName(org.getDivision())
                        .setInvId(inv.getOrganizationId())
                        .setInvCode(inv.getOrganizationCode())
                        .setInvName(inv.getOrganizationName())
                        .setOuGroupId(groupId)
                        .setOuId(org.getOrganizationId())
                        .setOuDetailId(IdGenrator.generate());
                temp.add(detail);
            }
            if (add) {
                groups.add(group);
                details.addAll(temp);
            }

        }
        List<Long> ouIds = baseOuGroupService.list(Wrappers.lambdaQuery(BaseOuGroup.class)
                .select(BaseOuGroup::getOuGroupId)
                .in(BaseOuGroup::getOuGroupCode, map.keySet())
        ).stream().map(BaseOuGroup::getOuGroupId).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(ouIds)) {
            detailService.remove(Wrappers.lambdaQuery(BaseOuDetail.class)
                    .in(BaseOuDetail::getOuGroupId, ouIds)
            );
        }
        if (!CollectionUtils.isEmpty(ouIds)) {
            baseOuGroupService.removeByIds(ouIds);
        }
        baseOuGroupService.saveBatch(groups);
        detailService.saveBatch(details);

        //回写招标缓存行的id,记录没有对应的ou组的行
        //1.选择2020之后未创建合同的数据，中标标志为Y或P
        List<BaseOuGroup> updateNameList = new LinkedList<>();
        CheckModuleHolder.checkout(Module.BARGAIN);
        List<DataTransferBrgOrderLine1> bidOrderLine1s = brgOrderLine1Mapper.selectList(Wrappers.lambdaQuery(DataTransferBrgOrderLine1.class)
                .eq(DataTransferBrgOrderLine1::getCreateContractFlag, "N")
                .nested(e -> e.eq(DataTransferBrgOrderLine1::getSuggestedFlag, "Y").or().eq(DataTransferBrgOrderLine1::getSuggestedFlag, "P"))
                .ge(DataTransferBrgOrderLine1::getCreationDate, "2020")
        );
        Set<Long> requirementIds = bidOrderLine1s.stream().map(DataTransferBrgOrderLine1::getRequirementLineId).collect(Collectors.toSet());
        List<DataTransferBrgRequirementLine1> bidRequirementLine1s = brgRequirementLine1Service.listByIds(requirementIds);
        Map<String, List<DataTransferBrgRequirementLine1>> numberMap = bidRequirementLine1s.stream().collect(Collectors.groupingBy(DataTransferBrgRequirementLine1::getRequirementNumber));
        //设置ou组信息
        List<DataTransferBrgRequirementLine1> param = new LinkedList<>();
        for (Map.Entry<String, List<DataTransferBrgRequirementLine1>> entry : numberMap.entrySet()) {
            String key = entry.getKey();
            List<DataTransferBrgRequirementLine1> value = entry.getValue();
            for (BaseOuGroup ou : groups) {
                if (Objects.equals(ou.getOuGroupCode(), key)) {
                    for (DataTransferBrgRequirementLine1 bidRequirementLine1 : value) {
                        DataTransferBrgRequirementLine1 temp = new DataTransferBrgRequirementLine1();
                        temp.setRequirementLineId(bidRequirementLine1.getRequirementLineId());
                        temp.setCeeaOuId(ou.getOuGroupId())
                                .setCeeaOuName(ou.getOuGroupName())
                                .setCeeaOuNumber(ou.getOuGroupCode())
                                .setCeeaIsBaseOu("N")
                                .setCeeaOuName("");
                        param.add(temp);
                        ou.setOuGroupName(bidRequirementLine1.getCeeaOuName());
                    }
                    updateNameList.add(ou);
                }
            }
        }
        //回写ouId
        if (!CollectionUtils.isEmpty(param)) {
            brgRequirementLine1Service.updateBatchById(param);
        }
        CheckModuleHolder.release();
        CheckModuleHolder.checkout(Module.BASE);
        if (!CollectionUtils.isEmpty(updateNameList)) {
            baseOuGroupService.updateBatchById(updateNameList);
        }
        CheckModuleHolder.release();
    }
}
