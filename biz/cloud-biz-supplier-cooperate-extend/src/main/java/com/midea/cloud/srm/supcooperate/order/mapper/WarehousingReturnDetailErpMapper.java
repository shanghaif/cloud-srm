package com.midea.cloud.srm.supcooperate.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.WarehousingReturnDetailErp;

import java.util.List;

/**
 * <p>
 * 入库退货明细erp表 Mapper 接口
 * </p>
 *
 * @author chenwj92@meiCloud.com
 * @since 2020-08-25
 */
public interface WarehousingReturnDetailErpMapper extends BaseMapper<WarehousingReturnDetailErp> {
    public int countWarehousingReturnDetailErp();

    public List<WarehousingReturnDetailErp> listWarehousingReturnDetailErp(Integer page);
}
