package com.midea.cloud.srm.base.work.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.base.work.entity.CeeaBaseWorkSort;

import java.util.List;

public interface ICeeaBaseWorkSortService extends IService<CeeaBaseWorkSort> {
    /****
     * 保存我的任务列表数据排序信息
     * @param list
     * @throws Exception
     */
    void saveWorkCountSort(List<CeeaBaseWorkSort> list) throws Exception;
}
