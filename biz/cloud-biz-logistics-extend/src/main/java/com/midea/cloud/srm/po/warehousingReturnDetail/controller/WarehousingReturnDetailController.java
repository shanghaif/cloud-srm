package com.midea.cloud.srm.po.warehousingReturnDetail.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.feign.supcooperate.SupcooperateClient;
import com.midea.cloud.srm.feign.supplier.SupplierClient;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.common.ExportExcelParam;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.WarehousingReturnDetailRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.WarehousingReturnDetail;
import com.midea.cloud.srm.po.warehousingReturnDetail.service.IWarehousingReturnDetailService;
import com.midea.cloud.srm.po.warehousingReturnDetail.utils.ExportUtils;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

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
 *  修改日期: 2020/8/20 14:07
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/po/warehousingReturnDetail")
public class WarehousingReturnDetailController extends BaseController {

    public static void main(String[] args) {
        boolean b = false;
        String s = "空气特气设备，空气干燥机？“稍等”；";
        String tmp = s;
        tmp = tmp.replaceAll("，", ",");
        tmp = tmp.replaceAll("。", ".");
        tmp = tmp.replaceAll("？", "?");
        tmp = tmp.replaceAll("；", ";");
        tmp = tmp.replaceAll("’", "'");
        tmp = tmp.replaceAll("：", ":");
        tmp = tmp.replaceAll("“", "\"");
        tmp = tmp.replaceAll("”", "\"");
        tmp = tmp.replaceAll("（", "(");
        tmp = tmp.replaceAll("）", ")");
        tmp = tmp.replaceAll("！", "!");

        System.out.println(tmp);
    }
    @Autowired
    private SupcooperateClient supcooperateClient;

    @Autowired
    private IWarehousingReturnDetailService warehousingReturnDetailService;

    @PostMapping("/listPage")
    public PageInfo<WarehousingReturnDetail> listPage(@RequestBody WarehousingReturnDetailRequestDTO warehousingReturnDetailRequestDTO){
        return supcooperateClient.warehousingReturnDetailListPage(warehousingReturnDetailRequestDTO);
    }

    @PostMapping("/list")
    public List<WarehousingReturnDetail> list(WarehousingReturnDetailRequestDTO warehousingReturnDetailRequestDTO){
        return warehousingReturnDetailService.list(warehousingReturnDetailRequestDTO);
    }

    @RequestMapping("/exportExcelTitle")
    public Map<String, String> exportExcelTitle(){
        return ExportUtils.getWareHousingReturnDetailTitles();
    }

    @RequestMapping("/exportExcel")
    public void exportExcel(@RequestBody ExportExcelParam<WarehousingReturnDetail> excelParam, HttpServletResponse response) throws IOException {
        warehousingReturnDetailService.exportStart(excelParam,response);
    }

}
