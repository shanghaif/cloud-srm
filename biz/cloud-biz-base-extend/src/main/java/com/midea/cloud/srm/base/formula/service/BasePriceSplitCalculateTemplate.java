package com.midea.cloud.srm.base.formula.service;

import com.midea.cloud.srm.model.base.formula.dto.calculate.PriceSpiltDto;

import java.util.List;

/**
 * <pre>
 *  价格拆分处理模板类，防止以后有其他实现
 * </pre>
 *
 * @author tanjl11@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/31 13:54
 *  修改内容:
 * </pre>
 */
public abstract class BasePriceSplitCalculateTemplate<D, E> {

    public void handle(D dto) {
        List<E> duplicateElement = getDuplicateElement(dto);
        List<? extends PriceSpiltDto> priceSpiltDtos = transformEntityToPriceEntity(duplicateElement,dto);
        List<? extends PriceSpiltDto> afterSplit = spiltDtos(priceSpiltDtos,dto);
        List<? extends PriceSpiltDto> afterDelete = removeSplitDtos(afterSplit,dto);
        List<? extends PriceSpiltDto> afterUpdate = updateSplitDtos(afterDelete,dto);
        insertSplitDtos(afterUpdate,dto);
    }

    /**
     * 通过入参获取重复的价格实体
     *
     * @param dto
     * @return
     */
    protected abstract List<E> getDuplicateElement(D dto);

    /**
     * 实体转换为待处理区间参数
     *
     * @param entityList
     * @return
     */
    protected abstract List<? extends PriceSpiltDto> transformEntityToPriceEntity(List<E> entityList,D dto);

    /**
     * 处理区间参数
     *
     * @param dtos
     * @return
     */
    protected abstract List<PriceSpiltDto> spiltDtos(List<? extends PriceSpiltDto> dtos,D dto);

    /**
     * 返回分割处理后的
     * @param afterSpilt
     * @return
     */
    protected abstract List<? extends PriceSpiltDto> removeSplitDtos(List<? extends PriceSpiltDto> afterSpilt,D dto);

    /**
     * 返回修改后的
     * @param afterDelete
     * @return
     */
    protected abstract List<? extends PriceSpiltDto> updateSplitDtos(List<? extends PriceSpiltDto> afterDelete,D dto);

    /**
     * 处理因分割多出来的
     * @param afterUpdate
     * @param dto
     */
    protected abstract void insertSplitDtos(List<? extends PriceSpiltDto> afterUpdate,D dto);
}
