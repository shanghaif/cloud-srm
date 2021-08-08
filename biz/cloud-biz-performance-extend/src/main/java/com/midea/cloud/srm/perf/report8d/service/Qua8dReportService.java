package com.midea.cloud.srm.perf.report8d.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.perf.report8d.dto.Qua8dReportDTO;
import com.midea.cloud.srm.model.perf.report8d.entity.Qua8dReport;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;


/**
* <pre>
 *  8D报告 服务类
 * </pre>
*
* @author chenjw90@meicloud.com
* @version 1.00.00
*
* <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Jan 31, 2021 5:47:57 PM
 *  修改内容:
 * </pre>
*/
public interface Qua8dReportService extends IService<Qua8dReport>{
    /*
   分页查询
    */
    PageInfo<Qua8dReportDTO> listPage(Qua8dReport qua8dReport);

    /*
   获取8D报告详情
     */
    Qua8dReport getReportById(Long reportId);

    /*
   修改8D报告
     */
    Long modify(Qua8dReport qua8dReport);

    /*
   导入excel 内容
     */
    Map<String,Object> importExcel(MultipartFile file, Fileupload fileupload) throws Exception;

    /*
   批量更新或者批量新增
     */
    void batchSaveOrUpdate(List<Qua8dReport> qua8dReportList) throws IOException;

    /*
   内容
     */
    void exportExcel(@RequestBody Qua8dReport excelParam, HttpServletResponse response) throws IOException;

}
