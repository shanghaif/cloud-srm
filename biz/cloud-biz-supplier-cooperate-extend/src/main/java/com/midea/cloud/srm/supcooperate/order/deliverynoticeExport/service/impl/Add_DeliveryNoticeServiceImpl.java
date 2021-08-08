package com.midea.cloud.srm.supcooperate.order.deliverynoticeExport.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;


import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.suppliercooperate.order.deliverynoticeExport.dto.ExcelDeliveryNoticeDto;
import com.midea.cloud.srm.model.suppliercooperate.order.deliverynoticeExport.entity.DeliveryNotice;

import com.midea.cloud.srm.supcooperate.order.deliverynoticeExport.mapper.Add_DeliveryNoticeMapper;
import com.midea.cloud.srm.supcooperate.order.deliverynoticeExport.service.Add_DeliveryNoticeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;

import com.midea.cloud.common.utils.BeanCopyUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import com.alibaba.excel.EasyExcel;
import com.midea.cloud.common.utils.EasyExcelUtil;

import java.io.IOException;
/**
* <pre>
 *  导出 服务实现类
 * </pre>
*
* @author yancj9@meicloud.com
* @version 1.00.00
*
* <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Feb 5, 2021 10:54:16 AM
 *  修改内容:
 * </pre>
*/
@Service
public class Add_DeliveryNoticeServiceImpl extends ServiceImpl<Add_DeliveryNoticeMapper, DeliveryNotice> implements Add_DeliveryNoticeService {
    @Transactional
    public void batchUpdate(List<DeliveryNotice> deliveryNoticeList) {
        this.saveOrUpdateBatch(deliveryNoticeList);
    }


    @Override
    @Transactional
    public void batchDeleted(List<Long> ids) {
        this.removeByIds(ids);
    }
    @Override
    public void exportExcel(DeliveryNotice excelParam, HttpServletResponse response) throws IOException {
        // 查询数据
        List<DeliveryNotice> deliveryNotices = getDeliveryNotices(excelParam);
        List<ExcelDeliveryNoticeDto> excelDeliveryNoticeDto = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(deliveryNotices)){
            deliveryNotices.forEach(deliveryNotice -> {
                ExcelDeliveryNoticeDto deliveryNoticeDto = new ExcelDeliveryNoticeDto();
                BeanCopyUtil.copyProperties(deliveryNoticeDto,deliveryNotice);
                excelDeliveryNoticeDto.add(deliveryNoticeDto);
            });
        }
        // 获取输出流
        OutputStream outputStream = EasyExcelUtil.getServletOutputStream(response, "导出文件名");
        EasyExcel.write(outputStream).head(ExcelDeliveryNoticeDto.class).sheet(0).sheetName("sheetName").doWrite(excelDeliveryNoticeDto);

    }
    @Override
    public PageInfo<DeliveryNotice> listPage(DeliveryNotice deliveryNotice) {
        PageUtil.startPage(deliveryNotice.getPageNum(), deliveryNotice.getPageSize());
        List<DeliveryNotice> deliveryNotices = getDeliveryNotices(deliveryNotice);
        return new PageInfo<>(deliveryNotices);
    }

    public List<DeliveryNotice> getDeliveryNotices(DeliveryNotice deliveryNotice) {
        QueryWrapper<DeliveryNotice> wrapper = new QueryWrapper<>();
  //      wrapper.eq("NOTICE_ID",deliveryNotice.getNoticeId()); // 精准匹配
//        wrapper.like("TITLE",deliveryNotice.getTitle());      // 模糊匹配
//        wrapper.and(queryWrapper ->
//                queryWrapper.ge("CREATION_DATE",deliveryNotice.getStartDate()).
//                        le("CREATION_DATE",deliveryNotice.getEndDate())); // 时间范围查询
        return this.list(wrapper);
    }
}
