package com.midea.cloud.common.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import static com.baomidou.mybatisplus.extension.service.IService.DEFAULT_BATCH_SIZE;

/**
 * @author tanjl11
 * @date 2020/11/04 13:49
 * @param <T> excel的入参
 * @param <R> 具体操作的实体
 */
public class AnalysisEventListenerBatch<T, R> extends AnalysisEventListener<T> {
        private final int size;
        private final Function<Collection<T>, Collection<R>> function;
        //不用扩容
        private final List<T> tempData = new LinkedList<>();
        private final IService<R> service;

        /**
         * 注意不能超过默认的saveBatch数量，不然出现插入数据比导入数据多的情况,原因未知
         * @param size     单次处理的量
         * @param function 入参为excel的参数，中间可以做校验，把失败的收集,只返回给客户端失败的行，详情可以看品类分工导入
         * @param service
         */
        public AnalysisEventListenerBatch(int size, Function<Collection<T>, Collection<R>> function, IService<R> service) {
            if(size>DEFAULT_BATCH_SIZE){
                size=DEFAULT_BATCH_SIZE;
            }
            this.size = size;
            this.function = function;
            this.service = service;
        }

        @Override
        public void invoke(T o, AnalysisContext analysisContext) {
            tempData.add(o);
            if (tempData.size() >= size) {
                handle();
            }
        }

        @Override
        public void doAfterAllAnalysed(AnalysisContext analysisContext) {
            if (!CollectionUtils.isEmpty(tempData)) {
                handle();
            }
        }

        private void handle() {
            Collection<R> saveList = function.apply(tempData);
            if (!CollectionUtils.isEmpty(saveList)) {
                service.saveBatch(saveList);
            }
            //防止内存溢出
            saveList.clear();
            tempData.clear();
        }
    }