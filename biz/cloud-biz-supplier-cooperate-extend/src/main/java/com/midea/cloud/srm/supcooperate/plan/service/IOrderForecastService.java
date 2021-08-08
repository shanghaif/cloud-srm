package com.midea.cloud.srm.supcooperate.plan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.suppliercooperate.excel.ErrorCell;
import com.midea.cloud.srm.model.suppliercooperate.orderforecast.entry.OrderForecast;

import java.util.List;

/**
 * <pre>
 *  三月滚动预测表 服务类
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/4/2 21:15
 *  修改内容:
 * </pre>
 */
public interface IOrderForecastService extends IService<OrderForecast> {

    List<ErrorCell> saveBatchByExcel(List<OrderForecast> list);

    void publishBatch(List<Long> ids);

    void comfirmBatch(List<Long> ids);
}
