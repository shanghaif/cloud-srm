package com.midea.cloud.srm.base.formula.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.constants.SequenceCodeConstant;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.common.utils.*;
import com.midea.cloud.srm.base.formula.mapper.BaseMaterialMapper;
import com.midea.cloud.srm.base.formula.service.IBaseMaterialService;
import com.midea.cloud.srm.base.seq.service.ISeqDefinitionService;
import com.midea.cloud.srm.model.base.formula.dto.create.BaseMaterialCreateDto;
import com.midea.cloud.srm.model.base.formula.dto.query.BaseMaterialQueryDto;
import com.midea.cloud.srm.model.base.formula.dto.update.BaseMaterialUpdateDto;
import com.midea.cloud.srm.model.base.formula.entity.BaseMaterial;
import com.midea.cloud.srm.model.base.formula.enums.StuffStatus;
import com.midea.cloud.srm.model.base.formula.vo.BaseMaterialVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <pre>
 *  基本材料表 服务实现类
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
@Slf4j
public class BaseMaterialServiceImpl extends ServiceImpl<BaseMaterialMapper, BaseMaterial> implements IBaseMaterialService {
    @Autowired
    private ISeqDefinitionService iSeqDefinitionService;

    @Override
    public BaseMaterialVO createBaseMaterial(BaseMaterialCreateDto dto) {
        ObjectUtil.setStrFieldNullIfEmpty(dto);
        String code = iSeqDefinitionService.genSequencesNumBase(SequenceCodeConstant.SEQ_BASE_BASE_MATERIAL_CODE);
        BaseMaterial material = BeanCopyUtil.copyProperties(dto, BaseMaterial::new);
        material.setBaseMaterialId(IdGenrator.generate());
        material.setBaseMaterialCode(code);
        material.setBaseMaterialStatus(StuffStatus.DRAFT.getStatus());
        save(material);
        BaseMaterialVO vo = BeanCopyUtil.copyProperties(material, BaseMaterialVO::new);
        return vo;
    }

    @Override
    public BaseMaterialVO updateBaseMaterial(BaseMaterialUpdateDto dto) {
        Long baseMaterialId = dto.getBaseMaterialId();
        ObjectUtil.setStrFieldNullIfEmpty(dto);
        BaseMaterial material = queryBaseMaterialInfoById(baseMaterialId, true);
        if (Objects.isNull(material)) {
            log.error(LogUtil.getCurrentLogInfo(ResultCode.BASE_MATERIAL_NOT_EXISTS));
            throw new BaseException(ResultCode.BASE_MATERIAL_NOT_EXISTS);
        }
        BeanUtils.copyProperties(dto, material);
        updateById(material);
        BaseMaterialVO vo = BeanCopyUtil.copyProperties(material, BaseMaterialVO::new);
        return vo;
    }

    @Override
    public Boolean deleteBaseMaterialById(Long materialId) {
        BaseMaterial material = queryBaseMaterialInfoById(materialId, false);
        if (Objects.isNull(material) || Objects.isNull(material.getBaseMaterialId())) {
            return false;
        }
        if (StuffStatus.DRAFT.getStatus().equals(material.getBaseMaterialStatus())) {
            removeById(materialId);
            return true;
        }
        return false;
    }

    @Override
    public PageInfo<BaseMaterial> queryBaseMaterialByPage(BaseMaterialQueryDto dto) {
        PageUtil.startPage(dto.getPageNum(), dto.getPageSize());
        //查询条件
        String baseMaterialName = dto.getBaseMaterialName();
        String baseMaterialCalculateType = dto.getBaseMaterialCalculateType();
        String baseMaterialStatus = dto.getBaseMaterialStatus();
        String baseMaterialUnit = dto.getBaseMaterialUnit();
        String seaFoodPrice = dto.getSeaFoodPrice();
        String baseMaterialType = dto.getBaseMaterialType();
        LambdaQueryWrapper<BaseMaterial> queryWrapper = Wrappers.lambdaQuery(BaseMaterial.class)
                .select(BaseMaterial::getBaseMaterialId, BaseMaterial::getBaseMaterialCode,
                        BaseMaterial::getBaseMaterialName, BaseMaterial::getBaseMaterialType,
                        BaseMaterial::getBaseMaterialStatus, BaseMaterial::getSeaFoodPrice,
                        BaseMaterial::getBaseMaterialUnit, BaseMaterial::getBaseMaterialCalculateType,
                        BaseMaterial::getCreatedBy, BaseMaterial::getCreationDate
                )
                .likeRight(Objects.nonNull(baseMaterialName), BaseMaterial::getBaseMaterialName, baseMaterialName)
                .eq(Objects.nonNull(baseMaterialCalculateType), BaseMaterial::getBaseMaterialCalculateType, baseMaterialCalculateType)
                .eq(Objects.nonNull(baseMaterialStatus), BaseMaterial::getBaseMaterialStatus, baseMaterialStatus)
                .eq(Objects.nonNull(baseMaterialUnit), BaseMaterial::getBaseMaterialUnit, baseMaterialUnit)
                .eq(Objects.nonNull(seaFoodPrice), BaseMaterial::getSeaFoodPrice, seaFoodPrice)
                .eq(Objects.nonNull(baseMaterialType), BaseMaterial::getBaseMaterialType, baseMaterialType);
        return new PageInfo<>(list(queryWrapper));
    }

    @Override
    public Boolean updateStatus(StuffStatus status, Long id) {
        return update(Wrappers.lambdaUpdate(BaseMaterial.class)
                .set(BaseMaterial::getBaseMaterialStatus, status.getStatus())
                .eq(BaseMaterial::getBaseMaterialId, id));
    }

    /**
     * 用于修改、删除前的校验
     *
     * @param materialId
     * @param extractColumns
     * @return
     */
    private BaseMaterial queryBaseMaterialInfoById(Long materialId, boolean extractColumns) {
        LambdaQueryWrapper<BaseMaterial> queryWrapper = Wrappers.lambdaQuery(BaseMaterial.class)
                .select(BaseMaterial::getBaseMaterialId, BaseMaterial::getBaseMaterialStatus)
                .eq(BaseMaterial::getBaseMaterialId, materialId);
        if (extractColumns) {
            queryWrapper.select(BaseMaterial::getBaseMaterialCode,
                    BaseMaterial::getBaseMaterialName, BaseMaterial::getBaseMaterialType,
                    BaseMaterial::getSeaFoodPrice, BaseMaterial::getBaseMaterialUnit, BaseMaterial::getBaseMaterialCalculateType,
                    BaseMaterial::getCreationDate, BaseMaterial::getCreatedBy
            );
        }
        BaseMaterial material = getOne(queryWrapper);
        return material;
    }


}
