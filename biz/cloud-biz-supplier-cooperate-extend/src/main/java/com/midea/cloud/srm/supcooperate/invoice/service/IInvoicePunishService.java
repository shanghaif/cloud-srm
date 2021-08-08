package com.midea.cloud.srm.supcooperate.invoice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.suppliercooperate.invoice.dto.InvoicePunishQueryDTO;
import com.midea.cloud.srm.model.suppliercooperate.invoice.entity.InvoicePunish;

import java.util.List;

/**
*  <pre>
 *  扣罚&派利明细 服务类
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-22 17:39:56
 *  修改内容:
 * </pre>
*/
public interface IInvoicePunishService extends IService<InvoicePunish> {

    PageInfo listPageByParam(InvoicePunishQueryDTO invoicePunishQueryDTO);

    void bachDeleteInvoicePunishes(List<Long> invoicePunishIds);
}
