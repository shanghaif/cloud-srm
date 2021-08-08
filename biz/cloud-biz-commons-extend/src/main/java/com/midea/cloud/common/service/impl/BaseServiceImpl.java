package com.midea.cloud.common.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.service.BaseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.utils.ClassUtils;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.entry.WarehouseReceipt;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.function.BiConsumer;

@Slf4j
public abstract class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements BaseService<T> {

    protected final Class<T> clazz;

    public BaseServiceImpl() {
        clazz = (Class<T>) ClassUtils.getSuperClassGenericType(getClass(), 1);
    }

    @Override
    public int updateByIds(List<Long> idList, T value) {
        if (CollectionUtils.isEmpty(idList)) {
            return 0;
        }

        return this.getBaseMapper().update(value, Wrappers.lambdaQuery(clazz).in(getKeyWrapper(), idList));
    }

    public <R> int updateColumnByIds(List<Long> idList, BiConsumer<T, R> updateColumn, R values) {
        if (CollectionUtils.isEmpty(idList)) {
            return 0;
        }

        T instance = getInstance(clazz);
        updateColumn.accept(instance, values);

        return this.getBaseMapper().update(instance, Wrappers.lambdaQuery(clazz).in(getKeyWrapper(), idList));
    }

    public static <T> T getInstance(Class<T> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            log.error("实例化失败", e);
            throw new BaseException(LocaleHandler.getLocaleMsg("实例化失败"));
        }
    }
}
