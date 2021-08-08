package com.midea.cloud.srm.po.warehousingReturnDetail.service.impl;

import com.midea.cloud.common.utils.*;
import com.midea.cloud.srm.model.common.ExportExcelParam;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.WarehousingReturnDetailRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.WarehousingReturnDetail;
import com.midea.cloud.srm.po.warehousingReturnDetail.service.IWarehousingReturnDetailService;
import com.midea.cloud.srm.po.warehousingReturnDetail.utils.ExportUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
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
 *  修改日期: 2020/8/20 14:08
 *  修改内容:
 * </pre>
 */
@Service
public class WarehousingReturnDetailServiceImpl implements IWarehousingReturnDetailService {

    @Resource
    private com.midea.cloud.srm.supcooperate.order.service.IWarehousingReturnDetailService scIWarehousingReturnDetailService;

    @Override
    public List<List<Object>> queryExportData(ExportExcelParam<WarehousingReturnDetail> param) {
        WarehousingReturnDetail queryParam = param.getQueryParam();
        /*检查是否要分页导出*/
        boolean flag = StringUtil.notEmpty(queryParam.getPageSize()) && StringUtil.notEmpty(queryParam.getPageNum());
        if (flag) {
            // 设置分页
            PageUtil.startPage(queryParam.getPageNum(), queryParam.getPageSize());
        }
        WarehousingReturnDetailRequestDTO dto = new WarehousingReturnDetailRequestDTO();
        BeanCopyUtil.copyProperties(dto,queryParam);
        /*查询数据*/
        List<WarehousingReturnDetail> warehousingReturnDetailList = list(dto);
        List<List<Object>> dataList = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(warehousingReturnDetailList)){
            List<Map<String, Object>> mapList = BeanMapUtils.objectsToMaps(warehousingReturnDetailList);
            ArrayList<String> titleList = param.getTitleList();
            if (CollectionUtils.isNotEmpty(titleList)) {
                for(Map<String, Object> map : mapList){
                    ArrayList<Object> objects = new ArrayList<>();
                    for(String key : titleList){
                        objects.add(map.get(key));
                    }
                    dataList.add(objects);
                }
            }
        }
        return dataList;
    }

    @Override
    public List<String> getMultilingualHeader(ExportExcelParam<WarehousingReturnDetail> param) {
        LinkedHashMap<String,String> warehousingReturnDetailTitles = ExportUtils.getWareHousingReturnDetailTitles();
        return param.getMultilingualHeader(param,warehousingReturnDetailTitles);
    }

    @Override
    public void exportStart(ExportExcelParam<WarehousingReturnDetail> excelParam, HttpServletResponse response) throws IOException {
        /*获取导出的数据*/
        List<List<Object>> dataList = queryExportData(excelParam);
        /*标题*/
        List<String> head = getMultilingualHeader(excelParam);
        /*文件名*/
        String fileName = excelParam.getFileName();
        /*开始导出*/
        EasyExcelUtil.exportStart(response,dataList,head,fileName);

    }

    @Override
    public List<WarehousingReturnDetail> list(WarehousingReturnDetailRequestDTO warehousingReturnDetailRequestDTO) {
        return scIWarehousingReturnDetailService.list(warehousingReturnDetailRequestDTO);
    }
}
