package com.midea.cloud.srm.bid.purchaser.projectmanagement.informationsupplement.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.evaluation.entity.VendorPerformance;

import java.util.List;

/**
 * <pre>
 *  供应商绩效 服务类
 * </pre>
 *
 * @author fengdc3@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-31 20:55:27
 *  修改内容:
 * </pre>
 */
public interface IBidVendorPerformanceService extends IService<VendorPerformance> {

    PageInfo<VendorPerformance> listPage(VendorPerformance VendorPerformance);

    void addBatch(List<VendorPerformance> VendorPerformanceList);

    void updateBatch(List<VendorPerformance> VendorPerformanceList);
}
