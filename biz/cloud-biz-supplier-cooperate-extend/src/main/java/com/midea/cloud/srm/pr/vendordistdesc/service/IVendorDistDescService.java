package com.midea.cloud.srm.pr.vendordistdesc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.VendorDistDesc;

import java.util.List;

/**
*  <pre>
 *  供应商分配明细表 服务类
 * </pre>
*
* @author wangpr@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-12-07 17:01:39
 *  修改内容:
 * </pre>
*/
public interface IVendorDistDescService extends IService<VendorDistDesc> {
    /**
     * 分配供应商
     * @param requirementLineIds
     */
    void assignSupplier(List<Long> requirementLineIds);

}
