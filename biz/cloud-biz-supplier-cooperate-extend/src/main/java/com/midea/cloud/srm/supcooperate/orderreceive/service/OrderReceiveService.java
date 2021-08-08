package com.midea.cloud.srm.supcooperate.orderreceive.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.suppliercooperate.orderreceive.dto.OrderReceiveDTO;
import com.midea.cloud.srm.model.suppliercooperate.orderreceive.entity.OrderReceive;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
* <pre>
 *  xx 服务类
 * </pre>
*
* @author yancj9@meicloud.com
* @version 1.00.00
*
* <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Jan 30, 2021 10:31:54 AM
 *  修改内容:
 * </pre>
*/
public interface OrderReceiveService extends IService<OrderReceive>{
    /*
    批量更新或者批量新增
    */
     void batchSaveOrUpdate(List<OrderReceive> orderReceiveList) throws Exception;
    /*
     *导入的批量保存
     */
     void batchSaveForImport(List<OrderReceive> orderReceiveList) throws Exception;
     /*
     批量删除
     */
    void batchDeleted(List<Long> ids) throws IOException;
    /*
    导出excel模板文件
    */
    public void exportExcelTemplate(HttpServletResponse response) throws IOException;
    /*
    导入excel 内容
     */
    Map<String,Object> importExcel(MultipartFile file, Fileupload fileupload) throws Exception;
    /*
     导出excel内容数据
     param ： 传入参数
     */
    void exportExcel(OrderReceiveDTO excelParam, HttpServletResponse response)throws IOException;
    /*
   分页查询
    */
    PageInfo<OrderReceive> listPage(OrderReceiveDTO orderReceiveDTO);
}
