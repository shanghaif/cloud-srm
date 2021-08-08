package com.midea.cloud.common.utils;

import com.midea.cloud.srm.model.common.ExportExcelParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * <pre>
 *
 * </pre>
 *
 * @author ex_wangpr@partner.midea.com
 * @version 2.00.00
 * <pre>
 * 修改记录:      修改人:      修改日期:       修改内容:
 * </pre>
 */
public interface ExportExcel<T> {
    /**
     * 查询
     *
     * @param param
     * @return
     */
    List<List<Object>> queryExportData(ExportExcelParam<T> param);

    /**
     * 获取多语言列名
     *
     * @param param
     * @return
     */
    List<String> getMultilingualHeader(ExportExcelParam<T> param);

    /**
     * 开始导出
     * @param param
     * @param response
     */
    void exportStart(ExportExcelParam<T> param, HttpServletResponse response)throws IOException;

//    @Override
//    public void exportStart(ExportExcelParam<VendorAssesForm> vendorAssesFormDto, HttpServletResponse response) throws IOException {
//        // 获取导出的数据
//        List<List<Object>> dataList = queryExportData(vendorAssesFormDto);
//        // 标题
//        List<String> head = getMultilingualHeader(vendorAssesFormDto);
//        // 文件名
//        String fileName = vendorAssesFormDto.getFileName();
//        // 开始导出
//        EasyExcelUtil.exportStart(response, dataList, head, fileName);
//    }
}
