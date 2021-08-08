package com.midea.cloud.srm.supcooperate.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.srm.model.base.soap.erp.dto.SoapResponse;
import com.midea.cloud.srm.model.base.soap.erp.suppliercooperate.dto.WarehousingReturnDetailEntity;
import com.midea.cloud.srm.model.common.ExportExcelParam;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.WarehousingReturnDetailRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.WarehousingReturnDetail;
import org.springframework.web.bind.annotation.RequestBody;

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
 *  修改日期: 2020/8/20 14:15
 *  修改内容:
 * </pre>
 */
public interface IWarehousingReturnDetailService extends IService<WarehousingReturnDetail> {
    List<WarehousingReturnDetail> listPage(WarehousingReturnDetailRequestDTO warehousingReturnDetailRequestDTO);

    List<WarehousingReturnDetail> list(WarehousingReturnDetailRequestDTO warehousingReturnDetailRequestDTO);

    public SoapResponse acceptErpData(List<WarehousingReturnDetailEntity> warehousingReturnDetailEntityList, String instId, String requestTime);

    void export(WarehousingReturnDetailRequestDTO warehousingReturnDetailRequestDTO, HttpServletResponse response);

    PageInfo<WarehousingReturnDetail> warehousingReturnlistPageByParam(WarehousingReturnDetailRequestDTO warehousingReturnDetailRequestDTO);

    void exportStart(WarehousingReturnDetailRequestDTO warehousingReturnDetail,HttpServletResponse response) throws IOException;

}
