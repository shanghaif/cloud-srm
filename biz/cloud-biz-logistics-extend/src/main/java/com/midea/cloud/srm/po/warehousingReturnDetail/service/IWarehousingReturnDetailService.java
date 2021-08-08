package com.midea.cloud.srm.po.warehousingReturnDetail.service;

import com.midea.cloud.common.utils.ExportExcel;
import com.midea.cloud.srm.model.common.ExportExcelParam;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.WarehousingReturnDetailRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.WarehousingReturnDetail;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
 *  修改日期: 2020/8/20 14:08
 *  修改内容:
 * </pre>
 */
public interface IWarehousingReturnDetailService extends ExportExcel<WarehousingReturnDetail> {
    void exportStart(ExportExcelParam<WarehousingReturnDetail> excelParam, HttpServletResponse response) throws IOException;

    List<WarehousingReturnDetail> list(WarehousingReturnDetailRequestDTO warehousingReturnDetailRequestDTO);
}
