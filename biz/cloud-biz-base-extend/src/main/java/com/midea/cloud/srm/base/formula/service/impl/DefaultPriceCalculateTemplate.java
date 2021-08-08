package com.midea.cloud.srm.base.formula.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.BeanCopyUtil;
import com.midea.cloud.common.utils.DateUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.StringUtil;
import com.midea.cloud.srm.base.formula.mapper.BaseMaterialPriceMapper;
import com.midea.cloud.srm.base.formula.service.BasePriceSplitCalculateTemplate;
import com.midea.cloud.srm.model.base.formula.dto.calculate.PriceSpiltDto;
import com.midea.cloud.srm.model.base.formula.dto.create.BaseMaterialPriceCreateDto;
import com.midea.cloud.srm.model.base.formula.entity.BaseMaterialPrice;
import com.midea.cloud.srm.model.base.formula.enums.CalculateOpType;
import com.midea.cloud.srm.model.base.formula.enums.StuffStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author tanjl11@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/31 14:18
 *  修改内容:
 * </pre>
 */
@Component(value = "default")
@AllArgsConstructor
public class DefaultPriceCalculateTemplate extends BasePriceSplitCalculateTemplate<BaseMaterialPriceCreateDto, BaseMaterialPrice> {

    private final BaseMaterialPriceMapper baseMaterialPriceMapper;

    /**
     * 获取基材编码+价格类型重复的列表
     *
     * @param dto
     * @return
     */
    @Override
    protected List<BaseMaterialPrice> getDuplicateElement(BaseMaterialPriceCreateDto dto) {
        String baseMaterialCode = dto.getBaseMaterialCode();
        String baseMaterialPriceType = dto.getBaseMaterialPriceType();
        String priceFrom = dto.getPriceFrom();
        //查询出已重复的类
        List<BaseMaterialPrice> duplicateList = baseMaterialPriceMapper.selectList(Wrappers.lambdaQuery(BaseMaterialPrice.class).select(BaseMaterialPrice::getBaseMaterialPriceId,
                BaseMaterialPrice::getActiveDateFrom, BaseMaterialPrice::getActiveDateTo, BaseMaterialPrice::getBaseMaterialPrice
                , BaseMaterialPrice::getPriceFrom
                , BaseMaterialPrice::getBaseMaterialName)
                .eq(Objects.nonNull(baseMaterialCode), BaseMaterialPrice::getBaseMaterialCode, baseMaterialCode)
                .eq(Objects.nonNull(baseMaterialPriceType), BaseMaterialPrice::getBaseMaterialPriceType, baseMaterialPriceType)
                .eq(!StringUtil.isEmpty(priceFrom), BaseMaterialPrice::getPriceFrom, priceFrom)
                .eq(BaseMaterialPrice::getBaseMaterialPriceStatus, StuffStatus.ACTIVE.getStatus())
                .orderByAsc(BaseMaterialPrice::getCreationDate));
        return duplicateList;
    }

    /**
     * 把实体转换成拆分列表
     *
     * @param entityList
     * @param dto
     * @return
     */
    @Override
    protected List<? extends PriceSpiltDto> transformEntityToPriceEntity(List<BaseMaterialPrice> entityList, BaseMaterialPriceCreateDto dto) {
        /**
         * 构造价格拆分列表
         */
        List<PriceSpiltDto> priceSpiltDtos = new ArrayList<>(entityList.size() + 1);
        if (!CollectionUtils.isEmpty(entityList)) {
            for (int i = 0; i < entityList.size(); i++) {
                PriceSpiltDto current = new PriceSpiltDto();
                BaseMaterialPrice baseMaterialPrice = entityList.get(i);
                current.setBizId(baseMaterialPrice.getBaseMaterialPriceId());
                current.setFrom(baseMaterialPrice.getActiveDateFrom());
                current.setTo(baseMaterialPrice.getActiveDateTo());
                current.setPriority(i);
                current.setPriceFrom(baseMaterialPrice.getPriceFrom());
                current.setPrice(baseMaterialPrice.getBaseMaterialPrice());
                current.setName(baseMaterialPrice.getBaseMaterialName());
                priceSpiltDtos.add(current);
            }
        }
        return priceSpiltDtos;
    }

    @Override
    protected List<PriceSpiltDto> spiltDtos(List<? extends PriceSpiltDto> dtos, BaseMaterialPriceCreateDto dto) {
        //代表当前只有一个价格，不用后续处理,如果是覆盖了，则报错
        if (CollectionUtils.isEmpty(dtos)) {
            return null;
        }
        if (dtos.size() == 1) {
            PriceSpiltDto priceSpiltDto = dtos.get(0);
            Date activeDateFrom = dto.getActiveDateFrom();
            Date activeDateTo = dto.getActiveDateTo();
            if (priceSpiltDto.getFrom().equals(activeDateFrom)) {
                throw new BaseException(String.format("不能和来源为%s的%s的起始日期重复", priceSpiltDto.getPriceFrom(), priceSpiltDto.getName()));
            }
            if (priceSpiltDto.getTo().equals(activeDateTo)) {
                throw new BaseException(String.format("不能和来源为%s的%s的结束日期重复", priceSpiltDto.getPriceFrom(), priceSpiltDto.getName()));
            }
        }
        //查找出重复区间的dtos
        List<PriceSpiltDto> duplicateDateDtos = new ArrayList<>(dtos.size());
        //对之前的重复的元素进行遍历查找，查找出区间重合的数据
        for (int i = 0; i < dtos.size(); i++) {
            PriceSpiltDto e = dtos.get(i);
            if (isInRange(dto, e)) {
                duplicateDateDtos.add(e);
            }
        }
        if (duplicateDateDtos.size() > 1) {
            throw new BaseException("当前不支持2个或以上的日期重复区间的价格拆分");
        }
        if (CollectionUtils.isEmpty(duplicateDateDtos)) {
            return null;
        }
        //存放分割后的结果
        List<PriceSpiltDto> result = new ArrayList<>();

        PriceSpiltDto priceSpiltDto = duplicateDateDtos.get(0);

        Date advanceFrom = priceSpiltDto.getFrom();
        Date advanceTo = priceSpiltDto.getTo();
        Date currentFrom = dto.getActiveDateFrom();
        Date currentTo = dto.getActiveDateTo();
        //场景2 中间交叉 6.1-6.30、6.15-6.20===>6.1-6.14,6.15-6.20,6.21-6.30
        if (advanceFrom.compareTo(currentFrom) <= 0 && advanceTo.compareTo(currentTo) >= 0) {
            PriceSpiltDto tail = new PriceSpiltDto();
            tail.setFrom(DateUtil.getDate(currentTo, 1, Calendar.DAY_OF_MONTH));
            tail.setTo(advanceTo);
            tail.setOpType(CalculateOpType.SPILT.getOpType());
            tail.setBizId(priceSpiltDto.getBizId());
            tail.setPrice(priceSpiltDto.getPrice());
            priceSpiltDto.setTo(DateUtil.getDate(currentFrom, -1, Calendar.DAY_OF_MONTH));
            priceSpiltDto.setOpType(CalculateOpType.UPDATE.getOpType());
            result.add(tail);
            result.add(priceSpiltDto);
        }
        //场景一 前交叉 6.1-6.30、6.15-7.30===>6.1-6.14、6.16-7.30
        else if (advanceFrom.compareTo(currentFrom) <= 0) {
            priceSpiltDto.setTo(DateUtil.getDate(currentFrom, -1, Calendar.DAY_OF_MONTH));
            result.add(priceSpiltDto);
            priceSpiltDto.setOpType(CalculateOpType.UPDATE.getOpType());
            return result;
        }

        return result;
    }

    /**
     * 这里没有删除的场景
     *
     * @param afterSpilt
     * @param dto
     * @return
     */
    @Override
    protected List<? extends PriceSpiltDto> removeSplitDtos(List<? extends PriceSpiltDto> afterSpilt, BaseMaterialPriceCreateDto dto) {
        return afterSpilt;
    }

    /**
     * 处理日期变更的情况
     *
     * @param afterDelete
     * @param dto
     * @return
     */
    @Override
    protected List<? extends PriceSpiltDto> updateSplitDtos(List<? extends PriceSpiltDto> afterDelete, BaseMaterialPriceCreateDto dto) {
        if (CollectionUtils.isEmpty(afterDelete)) {
            return null;
        }
        for (int i = afterDelete.size() - 1; i >= 0; i--) {
            PriceSpiltDto priceSpiltDto = afterDelete.get(i);
            if (CalculateOpType.UPDATE.getOpType().equals(priceSpiltDto.getOpType())) {
                updateCondition(priceSpiltDto);
                afterDelete.remove(i);
            }
        }
        return afterDelete;
    }

    /**
     * 处理因分裂要新增的情况
     *
     * @param afterUpdate
     * @param dto
     */
    @Override
    protected void insertSplitDtos(List<? extends PriceSpiltDto> afterUpdate, BaseMaterialPriceCreateDto dto) {
        if (CollectionUtils.isEmpty(afterUpdate)) {
            return;
        }
        for (int i = afterUpdate.size() - 1; i >= 0; i--) {
            PriceSpiltDto priceSpiltDto = afterUpdate.get(i);
            if (CalculateOpType.SPILT.getOpType().equals(priceSpiltDto.getOpType())) {
                spiltCondition(priceSpiltDto);
            }
        }
    }


    /**
     * 判断是否有交叉
     *
     * @param dto
     * @param other
     * @return
     */
    private boolean isInRange(BaseMaterialPriceCreateDto dto, PriceSpiltDto other) {
        if (!Objects.equals(dto.getPriceFrom(), other.getPriceFrom())) {
            return false;
        }
        //如果结束时间小于对比的开始时间，没有交集
        if (dto.getActiveDateTo().compareTo(other.getFrom()) < 0) {
            return false;
        }
        //如果开始时间大于对比的结束时间，没有交集
        if (dto.getActiveDateFrom().compareTo(other.getTo()) > 0) {
            return false;
        }
        return true;
    }

    /**
     * 场景一
     *
     * @param dto
     */
    private void updateCondition(PriceSpiltDto dto) {
        LambdaUpdateWrapper<BaseMaterialPrice> updateWrapper = Wrappers.lambdaUpdate(BaseMaterialPrice.class)
                .set(BaseMaterialPrice::getActiveDateFrom, dto.getFrom())
                .set(BaseMaterialPrice::getActiveDateTo, dto.getTo())
                .set(BaseMaterialPrice::getBaseMaterialPrice, dto.getPrice())
                .eq(BaseMaterialPrice::getBaseMaterialPriceId, dto.getBizId());
        baseMaterialPriceMapper.update(null, updateWrapper);
    }

    /**
     * 场景二的切分
     *
     * @param dto
     */
    private void spiltCondition(PriceSpiltDto dto) {
        BaseMaterialPrice price = baseMaterialPriceMapper.selectById(dto.getBizId());
        BaseMaterialPrice other = BeanCopyUtil.copyProperties(price, BaseMaterialPrice::new);
        other.setActiveDateFrom(dto.getFrom());
        other.setActiveDateTo(dto.getTo());
        other.setBaseMaterialPriceId(IdGenrator.generate());
        baseMaterialPriceMapper.insert(other);
    }

}
