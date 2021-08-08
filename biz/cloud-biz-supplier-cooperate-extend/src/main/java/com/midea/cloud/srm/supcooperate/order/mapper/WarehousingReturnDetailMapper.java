package com.midea.cloud.srm.supcooperate.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.WarehousingReturnDetailRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.WarehousingReturnDetail;

import java.util.List;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author chenwj92@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人: chenwj92
 *  修改日期: 2020/8/20 14:17
 *  修改内容:
 * </pre>
 */
public interface WarehousingReturnDetailMapper extends BaseMapper<WarehousingReturnDetail> {
    List<WarehousingReturnDetail> findList(WarehousingReturnDetailRequestDTO warehousingReturnDetailRequestDTO);

    List<WarehousingReturnDetail> findListCopy(WarehousingReturnDetailRequestDTO warehousingReturnDetailRequestDTO);

    List<WarehousingReturnDetail> findInvoiceNoticeList(WarehousingReturnDetailRequestDTO warehousingReturnDetailRequestDTO);
}
