package com.midea.cloud.common.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.entry.WarehouseReceipt;

import java.util.List;
import java.util.function.BiConsumer;

public interface BaseService<T>  extends IService<T> {

    SFunction<T, Long> getKeyWrapper();

    default boolean exist(Wrapper<T> queryWrapper) {
        return count(queryWrapper) > 0;
    }

    int updateByIds(List<Long> idList, T value);

    <R> int updateColumnByIds(List<Long> idList, BiConsumer<T, R> updateColumn, R values);
}
