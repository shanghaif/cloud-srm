package com.midea.cloud.srm.supcooperate.material.service;

import com.midea.cloud.srm.model.suppliercooperate.material.entity.CeeaMaterialDetail;
import com.baomidou.mybatisplus.extension.service.IService;

/**
*  <pre>
 *  物料计划明细表 服务类
 * </pre>
*
* @author yourname@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-21 23:38:17
 *  修改内容:
 * </pre>
*/
public interface IMaterialDetailService extends IService<CeeaMaterialDetail> {
    void updateBycount(CeeaMaterialDetail materialDetail);
}
