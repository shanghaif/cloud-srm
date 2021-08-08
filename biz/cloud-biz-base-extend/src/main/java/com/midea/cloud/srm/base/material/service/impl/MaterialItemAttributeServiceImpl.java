package com.midea.cloud.srm.base.material.service.impl;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.constants.SequenceCodeConstant;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.common.utils.BeanCopyUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.LogUtil;
import com.midea.cloud.srm.base.formula.service.IMaterialAttributeRelateService;
import com.midea.cloud.srm.base.material.mapper.MaterialItemAttributeMapper;
import com.midea.cloud.srm.base.material.mapper.MaterialItemMapper;
import com.midea.cloud.srm.base.material.service.IMaterialItemAttributeService;
import com.midea.cloud.srm.base.seq.service.ISeqDefinitionService;
import com.midea.cloud.srm.model.base.material.MaterialItem;
import com.midea.cloud.srm.model.base.material.MaterialItemAttribute;
import com.midea.cloud.srm.model.base.material.MaterialItemAttributeRelate;
import com.midea.cloud.srm.model.base.material.dto.MaterialItemAttributeRelateCreateDto;
import com.midea.cloud.srm.model.base.material.dto.MaterialItemAttributeRelateUpdateDto;
import com.midea.cloud.srm.model.base.material.vo.MaterialItemAttributeRelateVO;
import com.midea.cloud.srm.model.base.material.vo.MaterialItemAttributeVO;
import com.midea.cloud.srm.model.base.quicksearch.param.JsonParam;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <pre>
 *  物料主数据属性表 服务实现类
 * </pre>
 *
 * @author tanjl11@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-26 16:28:22
 *  修改内容:
 * </pre>
 */
@Service
@AllArgsConstructor
public class MaterialItemAttributeServiceImpl extends ServiceImpl<MaterialItemAttributeMapper, MaterialItemAttribute> implements IMaterialItemAttributeService {

    private final ISeqDefinitionService iSeqDefinitionService;

    private final IMaterialAttributeRelateService relateService;

    private final MaterialItemAttributeMapper materialItemAttributeMapper;

    private final MaterialItemMapper materialItemMapper;

    /**
     * 创建基本材料属性
     *
     * @param name
     * @return
     */
    @Override
    public MaterialItemAttributeVO createMaterialItemAttribute(String name) {
        //判断该要素名字是否重复了
        int count = count(Wrappers.lambdaQuery(MaterialItemAttribute.class)
                .eq(MaterialItemAttribute::getAttributeName, name));
        if (count > 0) {
            log.error(LogUtil.getCurrentLogInfo(ResultCode.MATERIAL_ATTRIBUTE_NAME_EXISTS));
            throw new BaseException(ResultCode.MATERIAL_ATTRIBUTE_NAME_EXISTS);
        }
        String itemCode = iSeqDefinitionService.genSequencesNumBase(SequenceCodeConstant.SEQ_BASE_MATERIAL_ATTRIBUTE_CODE);
        MaterialItemAttribute attribute = new MaterialItemAttribute();
        attribute.setAttributeCode(itemCode);
        attribute.setAttributeName(name);
        attribute.setMaterialAttributeId(IdGenrator.generate());
        save(attribute);
        MaterialItemAttributeVO vo = BeanCopyUtil.copyProperties(attribute, MaterialItemAttributeVO::new);
        return vo;
    }

    /**
     * 分页查询
     *
     * @param param
     * @return
     */
    @Override
    public Map<String, Object> listPageByParam(JsonParam param) {
        Map<String, Object> result = new HashMap<>();
        List<MaterialItemAttribute> materialItemAttributes = this.list();
        result.put("data", materialItemAttributes);
        Integer totalCount = materialItemAttributes.size();
        result.put("totalCount", totalCount);
        return result;
    }

    /**
     * 删除物料主属性
     * 注意会把关联表的数据也删掉
     *
     * @param id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteMaterialItemAttributeById(Long id) {
        int count = count(Wrappers.lambdaQuery(MaterialItemAttribute.class).eq(MaterialItemAttribute::getMaterialAttributeId, id));
        if (count == 0) {
            return false;
        }
        removeById(id);
        relateService.remove(Wrappers.lambdaQuery(MaterialItemAttributeRelate.class)
                .eq(MaterialItemAttributeRelate::getMaterialAttributeId, id));
        return true;
    }

    /**
     * 根据物料id查询关联属性表
     */
    @Override
    public List<MaterialItemAttributeRelateVO> getMaterialAttributeRelateByMaterialId(Long materalId, boolean keyFuture) {

        List<MaterialItemAttributeRelate> list = relateService.list(
                Wrappers.lambdaQuery(MaterialItemAttributeRelate.class)
                        .select(MaterialItemAttributeRelate::getRelateId, MaterialItemAttributeRelate::getMaterialAttributeId
                                , MaterialItemAttributeRelate::getAttributeCode, MaterialItemAttributeRelate::getAttributeName, MaterialItemAttributeRelate::getMaterialItemId
                                , MaterialItemAttributeRelate::getAttributeValue, MaterialItemAttributeRelate::getKeyFeature
                        )
                        .eq(MaterialItemAttributeRelate::getMaterialItemId, materalId));
        if (CollectionUtils.isEmpty(list)) {
            return new LinkedList<>();
        }
        Stream<MaterialItemAttributeRelateVO> stream = list.stream().map(e -> BeanCopyUtil.copyProperties(e, MaterialItemAttributeRelateVO::new));
        return keyFuture ? stream.filter(e -> e.getKeyFeature().equals(YesOrNo.YES.getValue())).collect(Collectors.toList()) : stream.collect(Collectors.toList());
    }

    @Override
    public Map<Long, List<MaterialItemAttributeRelateVO>> getMaterialAttributeRelateByMaterialIds(List<Long> materialIds) {
        if (CollectionUtils.isEmpty(materialIds)) {
            return Collections.emptyMap();
        }
        List<MaterialItemAttributeRelateVO> list = relateService.list(
                Wrappers.lambdaQuery(MaterialItemAttributeRelate.class)
                        .select(MaterialItemAttributeRelate::getRelateId, MaterialItemAttributeRelate::getMaterialAttributeId
                                , MaterialItemAttributeRelate::getAttributeCode, MaterialItemAttributeRelate::getAttributeName, MaterialItemAttributeRelate::getMaterialItemId
                                , MaterialItemAttributeRelate::getAttributeValue, MaterialItemAttributeRelate::getKeyFeature
                        )
                        .eq(MaterialItemAttributeRelate::getKeyFeature, YesOrNo.YES.getValue())
                        .in(MaterialItemAttributeRelate::getMaterialItemId, materialIds))
                .stream().map(e -> BeanCopyUtil.copyProperties(e, MaterialItemAttributeRelateVO::new))
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyMap();
        }
        return list.stream().collect(Collectors.groupingBy(MaterialItemAttributeRelateVO::getMaterialItemId));
    }

    @Override
    public MaterialItemAttributeRelateVO createMaterialItemAttributeRelate(MaterialItemAttributeRelateCreateDto dto) {
        MaterialItemAttribute attribute = checkBeforeHandleAttributeRelate(dto.getMaterialItemId(), dto.getMaterialAttributeId());
        MaterialItemAttributeRelate relate = BeanCopyUtil.copyProperties(dto, MaterialItemAttributeRelate::new);
        relate.setAttributeName(attribute.getAttributeName());
        relate.setAttributeCode(attribute.getAttributeCode());
        relate.setRelateId(IdGenrator.generate());
        relateService.save(relate);
        MaterialItemAttributeRelateVO vo = BeanCopyUtil.copyProperties(relate, MaterialItemAttributeRelateVO::new);
        return vo;
    }

    private MaterialItemAttribute checkBeforeHandleAttributeRelate(Long materialId, Long attributeId) {
        MaterialItemAttribute attribute = materialItemAttributeMapper.selectOne(Wrappers.lambdaQuery(MaterialItemAttribute.class)
                .select(MaterialItemAttribute::getMaterialAttributeId, MaterialItemAttribute::getAttributeCode, MaterialItemAttribute::getAttributeName)
                .eq(MaterialItemAttribute::getMaterialAttributeId, attributeId));
        if (Objects.isNull(attribute)) {
            log.error(LogUtil.getCurrentLogInfo(ResultCode.MATERIAL_ATTRIBUTE_EMPTY));
            throw new BaseException(ResultCode.MATERIAL_ATTRIBUTE_EMPTY);
        }
        Integer count = materialItemMapper.selectCount(Wrappers.lambdaQuery(MaterialItem.class).eq(MaterialItem::getMaterialId, materialId));
        if (count == 0) {
            log.error(LogUtil.getCurrentLogInfo(ResultCode.MATERIAL_ITEM_NOT_EXISTS));
            throw new BaseException(ResultCode.MATERIAL_ITEM_NOT_EXISTS);
        }
        return attribute;
    }

    @Override
    public MaterialItemAttributeRelateVO updateMaterialItemAttributeRelate(MaterialItemAttributeRelateUpdateDto dto) {
        return updateAttributeByDtoAndReturnVO(dto);
    }

    /**
     * 新增物料-属性关系
     *
     * @param createDtoList
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<MaterialItemAttributeRelateVO> batchCreateMaterialItemAttributeRelate(List<MaterialItemAttributeRelateCreateDto> createDtoList) {
        Map<Long, List<MaterialItemAttributeRelateCreateDto>> map = createDtoList.stream().collect(Collectors.groupingBy(MaterialItemAttributeRelateCreateDto::getMaterialItemId));
        //以传来的数据为准
        HashSet<Long> attributeIds = createDtoList.stream().map(MaterialItemAttributeRelateCreateDto::getMaterialAttributeId).collect(Collectors.toCollection(HashSet::new));
        //把id对应得属性查出来，这里只需要查id、name、code
        List<MaterialItemAttribute> materialItemAttributes = materialItemAttributeMapper.selectList(Wrappers.lambdaQuery(MaterialItemAttribute.class)
                .select(MaterialItemAttribute::getMaterialAttributeId, MaterialItemAttribute::getAttributeCode, MaterialItemAttribute::getAttributeName)
                .in(MaterialItemAttribute::getMaterialAttributeId, attributeIds)
        );
        if (CollectionUtils.isEmpty(materialItemAttributes)) {
            log.error(LogUtil.getCurrentLogInfo(ResultCode.MATERIAL_ATTRIBUTE_EMPTY));
            throw new BaseException(ResultCode.MATERIAL_ATTRIBUTE_EMPTY);
        }
        List<Long> deleteIds = new LinkedList<>();
        map.forEach((k, v) -> {
            //根据物料id查找出数据库有的
            List<MaterialItemAttributeRelate> relates = relateService.list(Wrappers.lambdaQuery(MaterialItemAttributeRelate.class)
                    .eq(MaterialItemAttributeRelate::getMaterialItemId, k));
            for (MaterialItemAttributeRelate relate : relates) {
                boolean find = false;
                for (MaterialItemAttributeRelateCreateDto materialItemAttributeRelateCreateDto : v) {
                    if (Objects.equals(relate.getRelateId(), materialItemAttributeRelateCreateDto.getRelateId())) {
                        find = true;
                        break;
                    }
                }
                if (!find) {
                    deleteIds.add(relate.getRelateId());
                }
            }
        });

        List<MaterialItemAttributeRelate> addList = new LinkedList<>();
        List<MaterialItemAttributeRelate> updateList = new LinkedList<>();
        List<MaterialItemAttributeRelateVO> relateVOS = new ArrayList<>();
        createDtoList.forEach(e -> {
            MaterialItemAttributeRelate relate = BeanCopyUtil.copyProperties(e, MaterialItemAttributeRelate::new);
            if (Objects.isNull(e.getRelateId())) {
                relate.setRelateId(IdGenrator.generate());
            } else {
                relate.setRelateId(e.getRelateId());
            }
            checkBeforeHandleAttributeRelate(e.getMaterialItemId(), e.getMaterialAttributeId());
            //把上面的属性对应的name和code复制给关联行数据，因为前端传过来的name和code不一定可信
            for (MaterialItemAttribute attribute : materialItemAttributes) {
                if (relate.getMaterialAttributeId().equals(attribute.getMaterialAttributeId())) {
                    relate.setAttributeName(attribute.getAttributeName());
                    relate.setAttributeCode(attribute.getAttributeCode());
                    MaterialItemAttributeRelateVO vo = BeanCopyUtil.copyProperties(relate, MaterialItemAttributeRelateVO::new);
                    relateVOS.add(vo);
                    break;
                }
            }
            if (Objects.isNull(e.getRelateId())) {
                addList.add(relate);
            } else {
                updateList.add(relate);
            }
        });
        //批量插入
        if (!CollectionUtils.isEmpty(addList)) {
            relateService.saveBatch(addList);
        }
        //批量更新
        if (!CollectionUtils.isEmpty(updateList)) {
            relateService.updateBatchById(updateList);
        }
        //批量删除
        if (!CollectionUtils.isEmpty(deleteIds)) {
            relateService.removeByIds(deleteIds);
        }
        return relateVOS;
    }

    @Override
    @Deprecated
    @Transactional(rollbackFor = Exception.class)
    public List<MaterialItemAttributeRelateVO> batchUpdateMaterialItemAttributeRelate(List<MaterialItemAttributeRelateUpdateDto> updateDtos) {
        List<MaterialItemAttributeRelateVO> voList = new ArrayList<>();
        for (MaterialItemAttributeRelateUpdateDto updateDto : updateDtos) {
            MaterialItemAttributeRelateVO temp = updateAttributeByDtoAndReturnVO(updateDto);
            voList.add(temp);
        }
        return voList;
    }


    /**
     * 根据dto更新并返回vo对象
     *
     * @param updateDto
     * @return
     */
    private MaterialItemAttributeRelateVO updateAttributeByDtoAndReturnVO(MaterialItemAttributeRelateUpdateDto updateDto) {
        MaterialItemAttributeRelate relate = relateService.getById(updateDto.getRelateId());
        MaterialItemAttributeRelateVO temp = new MaterialItemAttributeRelateVO();
        //判断物料主属性id是否变了
        Boolean updateStatus = whetherUpdate(updateDto, relate);
        //如果不变，跳过更新,直接返回vo对象
        if (!updateStatus) {
            BeanUtils.copyProperties(relate, temp);
            return temp;
        }
        checkBeforeHandleAttributeRelate(relate.getMaterialItemId(), relate.getMaterialAttributeId());
        //构建返回参数
        BeanUtils.copyProperties(updateDto, temp);
        //构建修改参数
        LambdaUpdateWrapper<MaterialItemAttributeRelate> updateWrapper = Wrappers
                .lambdaUpdate(MaterialItemAttributeRelate.class)
                .set(Objects.nonNull(updateDto.getKeyFeature()), MaterialItemAttributeRelate::getKeyFeature, updateDto.getKeyFeature())
                .set(Objects.nonNull(updateDto.getAttributeValue()), MaterialItemAttributeRelate::getAttributeValue, updateDto.getAttributeValue())
                .eq(MaterialItemAttributeRelate::getRelateId, updateDto.getRelateId());
        relateService.update(updateWrapper);
        return temp;
    }


    /**
     * 返回false代表无需更新
     * 返回true代表需要更新，非主属性id改变
     *
     * @param param
     * @param source
     * @return
     */
    private Boolean whetherUpdate(MaterialItemAttributeRelateUpdateDto param, MaterialItemAttributeRelate source) {
        if (Objects.nonNull(param.getKeyFeature()) && !Objects.equals(param.getKeyFeature(), source.getKeyFeature())) {
            return true;
        }
        if (Objects.nonNull(param.getAttributeValue()) && !Objects.equals(param.getAttributeValue(), source.getAttributeValue())) {
            return true;
        }
        return false;
    }

}
