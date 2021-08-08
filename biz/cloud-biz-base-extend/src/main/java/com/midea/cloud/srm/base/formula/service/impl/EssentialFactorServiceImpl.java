package com.midea.cloud.srm.base.formula.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.common.utils.BeanCopyUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.LogUtil;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.base.formula.mapper.BaseMaterialMapper;
import com.midea.cloud.srm.base.formula.mapper.EssentialFactorMapper;
import com.midea.cloud.srm.base.formula.service.IEssentialFactorService;
import com.midea.cloud.srm.base.material.mapper.MaterialItemAttributeMapper;
import com.midea.cloud.srm.model.base.formula.dto.create.EssentialFactorCreateDto;
import com.midea.cloud.srm.model.base.formula.dto.query.EssentialFactorQueryDto;
import com.midea.cloud.srm.model.base.formula.dto.update.EssentialFactorUpdateDto;
import com.midea.cloud.srm.model.base.formula.entity.BaseMaterial;
import com.midea.cloud.srm.model.base.formula.entity.EssentialFactor;
import com.midea.cloud.srm.model.base.formula.enums.EssentialFactorFromType;
import com.midea.cloud.srm.model.base.formula.enums.StuffStatus;
import com.midea.cloud.srm.model.base.formula.vo.EssentialFactorVO;
import com.midea.cloud.srm.model.base.material.MaterialItemAttribute;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <pre>
 *  公式要素表 服务实现类
 * </pre>
 *
 * @author tanjl11@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-26 16:27:13
 *  修改内容:
 * </pre>
 */
@Service
@AllArgsConstructor
@Slf4j
public class EssentialFactorServiceImpl extends ServiceImpl<EssentialFactorMapper, EssentialFactor> implements IEssentialFactorService {
    private final BaseMaterialMapper baseMaterialMapper;
    private final MaterialItemAttributeMapper materialItemAttributeMapper;

    /**
     * 新建要素
     *
     * @param dto
     * @return
     */
    @Override
    public EssentialFactorVO createEssentialFactor(EssentialFactorCreateDto dto) {
        //判断名字是否已经重复，公式要用
        int count = count(Wrappers.lambdaQuery(EssentialFactor.class).eq(EssentialFactor::getEssentialFactorName, dto.getEssentialFactorName()));
        if (count > 0) {
            throw new BaseException("要素名字已存在");
        }
        //判断来源
        String factorFrom = dto.getEssentialFactorFrom();
        EssentialFactor factor = BeanCopyUtil.copyProperties(dto, EssentialFactor::new);
        //如果为基价，则需要查询基材，并把基材id设置进去
        if (EssentialFactorFromType.BASE_MATERIAL_PRICE.getCode().equals(factorFrom)) {
            String baseMaterialCode = dto.getBaseMaterialCode();
            BaseMaterial material = getMaterialIdFromCode(baseMaterialCode);
            factor.setBaseMaterialId(material.getBaseMaterialId());
            factor.setBaseMaterialName(material.getBaseMaterialName());
        }
        //如果来源于物料主数据,则判断该物料主数据编号是否存在
        if (EssentialFactorFromType.MATERIAL_MAIN_DATA.getCode().equals(factorFrom)) {
            String materialAttributeName = dto.getMaterialAttributeName();
            Long attributeId = getMaterialAttributeIdFromName(materialAttributeName);
            factor.setMaterialAttributeId(attributeId);
        }
        factor.setEssentialFactorId(IdGenrator.generate());
        factor.setEssentialFactorStatus(StuffStatus.DRAFT.getStatus());
        save(factor);
        EssentialFactorVO vo = BeanCopyUtil.copyProperties(factor, EssentialFactorVO::new);
        return vo;
    }

    /**
     * 修改要素定义
     *
     * @param dto
     * @return
     */
    @Override
    public EssentialFactorVO updateEssentialFactor(EssentialFactorUpdateDto dto) {
        EssentialFactor factor = getOne(Wrappers.lambdaQuery(EssentialFactor.class)
                .select(EssentialFactor::getEssentialFactorStatus, EssentialFactor::getEssentialFactorId,
                        EssentialFactor::getCreatedBy, EssentialFactor::getCreationDate
                )
                .eq(EssentialFactor::getEssentialFactorId, dto.getEssentialFactorId()));
        //如果状态不为新建，则无法修改
//        String status = factor.getEssentialFactorStatus();
//        if (!StuffStatus.DRAFT.getStatus().equals(status)) {
//            String message = String.format("当前要素状态为%s,%s", StuffStatus.getValueByStatus(status), ResultCode.STATUS_ERROR.getMessage());
//            log.error(message);
//            throw new BaseException(message);
//        }
        //判断来源
        String factorFrom = dto.getEssentialFactorFrom();
        BeanUtils.copyProperties(dto, factor);
        //如果为基价，则需要查询基材，并把基材id设置进去
        if (EssentialFactorFromType.BASE_MATERIAL_PRICE.getCode().equals(factorFrom)) {
            String baseMaterialCode = dto.getBaseMaterialCode();
            BaseMaterial material = getMaterialIdFromCode(baseMaterialCode);
            factor.setBaseMaterialId(material.getBaseMaterialId());
            factor.setBaseMaterialName(material.getBaseMaterialName());
        }
        //如果来源于物料主数据,则判断该物料主数据编号是否存在
        if (EssentialFactorFromType.MATERIAL_MAIN_DATA.getCode().equals(factorFrom)) {
            String materialAttributeName = dto.getMaterialAttributeName();
            Long attributeId = getMaterialAttributeIdFromName(materialAttributeName);
            factor.setMaterialAttributeId(attributeId);
        }
        updateById(factor);
        EssentialFactorVO vo = new EssentialFactorVO();
        BeanUtils.copyProperties(factor, vo);
        return vo;
    }

    /**
     * 根据id删除要素
     *
     * @param essentialId
     * @return
     */
    @Override
    public Boolean deleteEssentialFactorById(Long essentialId) {
        //TODO 看是否需要删除关联的公式id
        EssentialFactor factor = getOne(Wrappers.lambdaQuery(EssentialFactor.class)
                .eq(EssentialFactor::getBaseMaterialId, essentialId));
        if (!Objects.isNull(factor)) {
            throw new BaseException("公式已存在不能删除");
        }
        removeById(essentialId);
        return true;
    }

    /**
     * 根据查询条件分页返回编码
     *
     * @param dto
     * @return
     */
    @Override
    public PageInfo<EssentialFactorVO> queryEssentialFactorByPage(EssentialFactorQueryDto dto) {
        PageUtil.startPage(dto.getPageNum(), dto.getPageSize());
        String desc = dto.getEssentialFactorDesc();
        String from = dto.getEssentialFactorFrom();
        String status = dto.getEssentialFactorStatus();
        String name = dto.getEssentialFactorName();
        List<EssentialFactor> list = list(Wrappers.lambdaQuery(EssentialFactor.class)
                .select(EssentialFactor::getEssentialFactorId, EssentialFactor::getEssentialFactorName,
                        EssentialFactor::getEssentialFactorDesc, EssentialFactor::getEssentialFactorFrom,
                        EssentialFactor::getEssentialFactorStatus, EssentialFactor::getBaseMaterialId,
                        EssentialFactor::getBaseMaterialCode, EssentialFactor::getMaterialAttributeId,
                        EssentialFactor::getMaterialAttributeName, EssentialFactor::getPriceType
                )
                .eq(Objects.nonNull(desc), EssentialFactor::getEssentialFactorDesc, desc)
                .eq(Objects.nonNull(from), EssentialFactor::getEssentialFactorFrom, dto.getEssentialFactorFrom())
                .eq(Objects.nonNull(status), EssentialFactor::getEssentialFactorStatus, status)
                .eq(Objects.nonNull(name), EssentialFactor::getEssentialFactorName, name));
        List<EssentialFactorVO> collect = list.stream().map(e -> BeanCopyUtil.copyProperties(e, EssentialFactorVO::new)).collect(Collectors.toList());
        return new PageInfo<>(collect);
    }

    @Override
    public Boolean updateStatus(StuffStatus status, Long id) {
        return update(Wrappers.lambdaUpdate(EssentialFactor.class)
                .set(EssentialFactor::getEssentialFactorStatus, status.getStatus())
                .eq(EssentialFactor::getEssentialFactorId, id));
    }

    @Override
    public List<EssentialFactor> listByEssentialFactorName(List<String> essentialFactorNameList) {
        if(CollectionUtils.isEmpty(essentialFactorNameList)){
            return Collections.EMPTY_LIST;
        }
        QueryWrapper<EssentialFactor> wrapper = new QueryWrapper<>();
        wrapper.in("ESSENTIAL_FACTOR_NAME",essentialFactorNameList);
        return this.list(wrapper);
    }

    @Override
    public EssentialFactorVO queryEssentialFactorById(Long essentialFactorId) {
        EssentialFactor factor = getOne(Wrappers.lambdaQuery(EssentialFactor.class)
                .select(EssentialFactor::getEssentialFactorId, EssentialFactor::getEssentialFactorName,
                        EssentialFactor::getEssentialFactorDesc, EssentialFactor::getEssentialFactorFrom,
                        EssentialFactor::getEssentialFactorStatus, EssentialFactor::getBaseMaterialId,
                        EssentialFactor::getBaseMaterialCode, EssentialFactor::getMaterialAttributeId,
                        EssentialFactor::getMaterialAttributeName, EssentialFactor::getPriceType
                )
                .eq(Objects.nonNull(essentialFactorId), EssentialFactor::getEssentialFactorId, essentialFactorId));
        EssentialFactorVO essentialFactorVO = new EssentialFactorVO();
        BeanCopyUtil.copyProperties(essentialFactorVO,factor);
        return essentialFactorVO;
    }


    /**
     * 根据code判断是否有该基材，没有的话报错
     *
     * @param baseMaterialCode
     * @return
     */
    private BaseMaterial getMaterialIdFromCode(String baseMaterialCode) {
        BaseMaterial baseMaterial = baseMaterialMapper.selectOne(Wrappers.lambdaQuery(BaseMaterial.class)
                .select(BaseMaterial::getBaseMaterialId, BaseMaterial::getBaseMaterialName)
                .eq(BaseMaterial::getBaseMaterialCode, baseMaterialCode));
        //判断该基材编码是否存在
        Long baseMaterialId = baseMaterial.getBaseMaterialId();
        if (Objects.isNull(baseMaterial) || Objects.isNull(baseMaterialId)) {
            log.error(LogUtil.getCurrentLogInfo(ResultCode.BASE_MATERIAL_NOT_EXISTS));
            throw new BaseException(ResultCode.BASE_MATERIAL_NOT_EXISTS);
        }
        return baseMaterial;
    }

    /**
     * 根据主属性名查看是否有该主属性，没有的话报错
     *
     * @param materialAttributeName
     * @return
     */
    private Long getMaterialAttributeIdFromName(String materialAttributeName) {
        MaterialItemAttribute attribute = materialItemAttributeMapper.selectOne(Wrappers.lambdaQuery(MaterialItemAttribute.class)
                .select(MaterialItemAttribute::getMaterialAttributeId)
                .eq(MaterialItemAttribute::getAttributeName, materialAttributeName));
        if (Objects.isNull(attribute) || Objects.isNull(attribute.getMaterialAttributeId())) {
            log.error(LogUtil.getCurrentLogInfo(ResultCode.MATERIAL_ATTRIBUTE_EMPTY));
            throw new BaseException(ResultCode.MATERIAL_ATTRIBUTE_EMPTY);
        }
        return attribute.getMaterialAttributeId();
    }



}
