package com.midea.cloud.srm.perf.supplierenotice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.perf.supplierenotice.dto.QuaSupplierEnoticeDTO;
import com.midea.cloud.srm.model.perf.supplierenotice.entity.QuaSupplierEnotice;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
* <pre>
 *  21 服务类
 * </pre>
*
* @author wengzc@media.com
* @version 1.00.00
*
* <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Feb 1, 2021 5:12:43 PM
 *  修改内容:
 * </pre>
*/
public interface QuaSupplierEnoticeService extends IService<QuaSupplierEnotice>{
    /*
    批量更新或者批量新增
    */
     void batchSaveOrUpdate(List<QuaSupplierEnotice> quaSupplierEnoticeList) throws IOException;

    /*
    导入excel 内容
     */
    Map<String,Object> importExcel(MultipartFile file, Fileupload fileupload) throws Exception;

    /*
     导出excel内容数据
     param ： 传入参数
     */
    void exportExcel(QuaSupplierEnotice excelParam, HttpServletResponse response)throws IOException;

    /*
   分页查询
    */
    PageInfo<QuaSupplierEnoticeDTO> listPage(QuaSupplierEnotice quaSupplierEnotice);

    /*
    新增
    */
    void add(QuaSupplierEnotice quaSupplierEnotice);

}
