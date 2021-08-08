package com.midea.cloud.srm.supcooperate.order.deliverynoticeExport.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import com.midea.cloud.srm.model.suppliercooperate.order.deliverynoticeExport.entity.DeliveryNotice;

/**
* <pre>
 *  导出 服务类
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
public interface Add_DeliveryNoticeService extends IService<DeliveryNotice>{

    /*
     批量删除
     */
    void batchDeleted(List<Long> ids) throws IOException;
    /*
     导出excel内容数据
     param ： 传入参数
     */
    void exportExcel(DeliveryNotice excelParam, HttpServletResponse response)throws IOException;
    /*
   分页查询
    */
    PageInfo<DeliveryNotice> listPage(DeliveryNotice deliveryNotice);


}
