package com.midea.cloud.srm.bargaining.suppliercooperate.projectlist.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.base.work.dto.WorkCount;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.entity.Biding;
import com.midea.cloud.srm.model.bargaining.suppliercooperate.vo.SupplierBidingVO;

import java.util.List;

/**
*  <pre>
 *  招标基础信息表 服务类
 * </pre>
*
* @author zhuomb1@midea.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-31 11:44:36
 *  修改内容:
 * </pre>
*/
public interface ISupplierBidingService extends IService<Biding> {
    /**
     * 查找数据：招标项目列表-供应商端
     * @param supplierBidingVO
     * @return
     */
    List<SupplierBidingVO> getSupplierBiding(SupplierBidingVO supplierBidingVO);


    WorkCount countCreate(SupplierBidingVO supplierBidingVO);
}
