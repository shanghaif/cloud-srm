package com.midea.cloud.srm.base.ou.service.impl;


import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.constants.SequenceCodeConstant;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.BeanCopyUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.base.dict.mapper.DictItemMapper;
import com.midea.cloud.srm.base.dict.mapper.DictMapper;
import com.midea.cloud.srm.base.organization.mapper.OrganizationMapper;
import com.midea.cloud.srm.base.ou.mapper.BaseOuGroupMapper;
import com.midea.cloud.srm.base.ou.service.IBaseOuDetailService;
import com.midea.cloud.srm.base.ou.service.IBaseOuGroupService;
import com.midea.cloud.srm.base.seq.service.ISeqDefinitionService;
import com.midea.cloud.srm.model.base.dict.entity.Dict;
import com.midea.cloud.srm.model.base.dict.entity.DictItem;
import com.midea.cloud.srm.model.base.formula.enums.StuffStatus;
import com.midea.cloud.srm.model.base.organization.entity.Organization;
import com.midea.cloud.srm.model.base.ou.dto.create.BaseOuGroupCreateDTO;
import com.midea.cloud.srm.model.base.ou.dto.query.BaseOuGroupQueryDTO;
import com.midea.cloud.srm.model.base.ou.dto.update.BaseOuDetailUpdateDTO;
import com.midea.cloud.srm.model.base.ou.dto.update.BaseOuGroupUpdateDTO;
import com.midea.cloud.srm.model.base.ou.entity.BaseOuDetail;
import com.midea.cloud.srm.model.base.ou.entity.BaseOuGroup;
import com.midea.cloud.srm.model.base.ou.vo.BaseOuBuInfoVO;
import com.midea.cloud.srm.model.base.ou.vo.BaseOuDetailVO;
import com.midea.cloud.srm.model.base.ou.vo.BaseOuGroupDetailVO;
import com.midea.cloud.srm.model.log.biz.holder.BizDocumentInfoHolder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <pre>
 *  ou组信息表 服务实现类
 * </pre>
 *
 * @author tanjl11@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-04 19:53:34
 *  修改内容:
 * </pre>
 */
@Service
@AllArgsConstructor
@Slf4j
public class BaseOuGroupServiceImpl extends ServiceImpl<BaseOuGroupMapper, BaseOuGroup> implements IBaseOuGroupService {
    private final IBaseOuDetailService detailService;
    private final ISeqDefinitionService iSeqDefinitionService;
    private final BaseOuGroupMapper ouGroupMapper;
    private final OrganizationMapper organizationMapper;
    private final DictMapper dictMapper;
    private final DictItemMapper dictItemMapper;

    /**
     * 创建ou组，ou组对应oulist
     *
     * @param dto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseOuGroupDetailVO createOuGroup(BaseOuGroupCreateDTO dto) {
        String ouGroupName = dto.getOuGroupName();
        BaseOuGroup group = new BaseOuGroup();
        long groupId = IdGenrator.generate();
        group.setOuGroupId(groupId);
        group.setOuGroupName(ouGroupName);
        group.setGroupStatus(StuffStatus.DRAFT.getStatus());
        group.setOuGroupCode(iSeqDefinitionService.genSequencesNumBase(SequenceCodeConstant.SEQ_BID_OU_OU_CODE));
        List<BaseOuDetail> details = dto.getDetails().stream().map(e -> {
            BaseOuDetail detail = BeanCopyUtil.copyProperties(e, BaseOuDetail::new);
            detail.setOuGroupId(groupId);
            detail.setOuDetailId(IdGenrator.generate());
            if (StringUtils.isBlank(detail.getOuCode()) || StringUtils.isBlank(detail.getOuName())) {
                Organization organization = organizationMapper.selectById(e.getOuId());
                detail.setOuCode(organization.getOrganizationCode());
                detail.setOuName(organization.getOrganizationName());
            }
            return detail;
        }).collect(Collectors.toList());
        save(group);
        detailService.saveBatch(details);
        return transferVO(group, details);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteOuGroupById(Long id) {
        int count = count(Wrappers.lambdaQuery(BaseOuGroup.class).eq(BaseOuGroup::getOuGroupId, id));
        if (count == 0) {
            return false;
        }
        //删除组
        removeById(id);
        //删除详情
        detailService.remove(Wrappers.lambdaQuery(BaseOuDetail.class).eq(BaseOuDetail::getOuGroupId, id));
        return true;
    }

    /**
     * 查询组信息
     *
     * @param dto
     * @return
     */
    @Override
    public PageInfo<BaseOuGroupDetailVO> queryOuDetailByPage(BaseOuGroupQueryDTO dto) {
        PageUtil.startPage(dto.getPageNum(), dto.getPageSize());
        String username = BizDocumentInfoHolder.get().getUsername();
        List<BaseOuGroupDetailVO> detailVOS = ouGroupMapper.queryBaseOuGroupDetailByPage(dto, username);
        return new PageInfo<>(detailVOS);
    }

    @Override
    public List<BaseOuGroupDetailVO> queryOuDetailByDto(BaseOuGroupQueryDTO dto) {
        String username = BizDocumentInfoHolder.get().getUsername();
        return ouGroupMapper.queryBaseOuGroupDetailByPage(dto, username);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseOuGroupDetailVO updateOuGroup(BaseOuGroupUpdateDTO dto) {
        //保存时候行可能会出现新增，修改，删除
        List<BaseOuDetailUpdateDTO> dtos = dto.getDetails();
        String ouGroupName = dto.getOuGroupName();
        //修改组名
        Long ouGroupId = dto.getOuGroupId();
        update(Wrappers.lambdaUpdate(BaseOuGroup.class).set(Objects.nonNull(ouGroupName), BaseOuGroup::getOuGroupName, ouGroupName).eq(BaseOuGroup::getOuGroupId, ouGroupId));
        //新增->删除->修改
        List<BaseOuDetail> insertList = new ArrayList<>(dtos.size());
        for (int i = dtos.size() - 1; i >= 0; i--) {
            BaseOuDetailUpdateDTO current = dtos.get(i);
            if (Objects.isNull(current.getOuDetailId())) {
                BaseOuDetail insert = BeanCopyUtil.copyProperties(current, BaseOuDetail::new);
                insert.setOuDetailId(IdGenrator.generate());
                insert.setOuGroupId(ouGroupId);
                insertList.add(insert);
                dtos.remove(i);
            }
        }
        //数据库已有的,要在新增前插入
        List<Long> shouldRemoveIds = detailService.list(Wrappers.lambdaQuery(BaseOuDetail.class)
                .select(BaseOuDetail::getOuDetailId)
                .eq(BaseOuDetail::getOuGroupId, ouGroupId))
                .stream().map(BaseOuDetail::getOuDetailId).collect(Collectors.toList());

        if (CollectionUtils.isNotEmpty(insertList)) {
            detailService.saveBatch(insertList);
        }

        for (int i = dtos.size() - 1; i >= 0; i--) {
            Long currentId = dtos.get(i).getOuDetailId();
            boolean find = false;
            for (int j = shouldRemoveIds.size() - 1; j >= 0; j--) {
                if (shouldRemoveIds.get(j).equals(currentId)) {
                    find = true;
                    //把应该修改的去掉
                    shouldRemoveIds.remove(j);
                    break;
                }
            }
            //把应该删掉去掉
            if (!find) {
                dtos.remove(i);
            }
        }
        if (CollectionUtils.isNotEmpty(shouldRemoveIds)) {
            detailService.removeByIds(shouldRemoveIds);
        }
        //剩下的就是修改
        List<BaseOuDetail> update = dtos.stream().map(e -> BeanCopyUtil.copyProperties(e, BaseOuDetail::new)).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(update)) {
            detailService.updateBatchById(update);
        }
        return queryOuGroupById(ouGroupId);
    }

    /**
     * 获取详情
     *
     * @param id
     * @return
     */
    @Override
    public BaseOuGroupDetailVO queryOuGroupById(Long id) {
        BaseOuGroup group = getOne(Wrappers.lambdaQuery(BaseOuGroup.class).eq(BaseOuGroup::getOuGroupId, id));
        List<BaseOuDetail> list = detailService.list(Wrappers.lambdaQuery(BaseOuDetail.class).eq(BaseOuDetail::getOuGroupId, id));
        return transferVO(group, list);
    }

    @Override
    public BaseOuGroupDetailVO queryGroupInfoById(Long id) {
        if (Objects.isNull(id)) {
            return new BaseOuGroupDetailVO();
        }
        return BeanCopyUtil.copyProperties(getById(id), BaseOuGroupDetailVO::new);
    }

    @Override
    public List<BaseOuGroupDetailVO> queryGroupInfoByIds(Collection<Long> idsList) {
        if (CollectionUtils.isEmpty(idsList)) {
            return Collections.EMPTY_LIST;
        }
        return listByIds(idsList).stream().map(e -> BeanCopyUtil.copyProperties(e, BaseOuGroupDetailVO::new)).collect(Collectors.toList());
    }

    @Override
    public List<BaseOuGroupDetailVO> queryGroupInfoDetailByIds(Collection<Long> idList) {
        if (CollectionUtils.isEmpty(idList)) {
            return Collections.EMPTY_LIST;
        }
        List<BaseOuGroupDetailVO> result = new ArrayList<>(idList.size());
        List<BaseOuGroup> baseOuGroups = listByIds(idList);
        Map<Long, List<BaseOuDetail>> listMap = detailService.list(Wrappers.lambdaQuery(BaseOuDetail.class).in(BaseOuDetail::getOuGroupId, idList)).stream()
                .collect(Collectors.groupingBy(BaseOuDetail::getOuGroupId));
        for (BaseOuGroup baseOuGroup : baseOuGroups) {
            List<BaseOuDetail> baseOuDetails = listMap.get(baseOuGroup.getOuGroupId());
            result.add(transferVO(baseOuGroup, baseOuDetails));
        }
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public BaseOuBuInfoVO queryBuInfoByOrgId(Long orgId) {
        Organization organization = organizationMapper.selectById(orgId);
        if (!Objects.equals(organization.getOrganizationTypeCode(), "OU")) {
            throw new BaseException("该组织不是业务实体");
        }
        if (Objects.equals(organization.getEnabled(), YesOrNo.NO.getValue())) {
            throw new BaseException("该组织状态为禁用");
        }
        //把对应的值写进org表
        if (StringUtils.isEmpty(organization.getDivision()) &&
                !StringUtils.isEmpty(organization.getDivisionId())
        ) {
            Dict division = dictMapper.selectOne(Wrappers.lambdaQuery(Dict.class)
                    .eq(Dict::getDictCode, "DIVISION"));
            if (Objects.isNull(division)) {
                throw new BaseException("事业部对应的字典DIVISION不存在");
            }
            Long dictId = division.getDictId();
            DictItem dictItem = dictItemMapper.selectOne(Wrappers.lambdaQuery(DictItem.class)
                    .eq(DictItem::getDictId, dictId)
                    .eq(DictItem::getDictItemCode, organization.getDivisionId()).last("limit 1"));
            organization.setDivision(dictItem.getDictItemName());
            //修改
            organizationMapper.update(null, Wrappers.lambdaUpdate(Organization.class)
                    .set(Organization::getDivision, organization.getDivision())
                    .eq(Organization::getOrganizationId, organization.getOrganizationId())
            );
        }
        BaseOuBuInfoVO result = BaseOuBuInfoVO.builder()
                .division(organization.getDivision())
                .divisionId(organization.getDivisionId())
                .organizationName(organization.getOrganizationName())
                .organizationId(orgId)
                .build();
        return result;
    }

    private BaseOuGroupDetailVO transferVO(BaseOuGroup group, List<BaseOuDetail> details) {
        BaseOuGroupDetailVO detailGroupVO = BeanCopyUtil.copyProperties(group, BaseOuGroupDetailVO::new);
        List<BaseOuDetailVO> detailVOS = details.stream().map(e -> BeanCopyUtil.copyProperties(e, BaseOuDetailVO::new)).collect(Collectors.toList());
        detailGroupVO.setDetails(detailVOS);
        return detailGroupVO;
    }

    @Override
    public BaseOuDetail queryBaseOuDetailByCode(String ouGroupCode) {
        return this.baseMapper.queryBaseOuDetailByCode(ouGroupCode);
    }
}
