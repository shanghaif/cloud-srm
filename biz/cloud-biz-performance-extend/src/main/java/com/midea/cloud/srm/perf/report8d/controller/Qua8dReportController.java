package com.midea.cloud.srm.perf.report8d.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.perf.report8d.dto.Qua8dReportDTO;
import com.midea.cloud.srm.model.perf.report8d.entity.Qua8dReport;
import com.midea.cloud.srm.perf.report8d.service.Qua8dProblemService;
import com.midea.cloud.srm.perf.report8d.service.Qua8dReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;


/**
* <pre>
 *  8D报告 控制层
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
@RestController
@RequestMapping("/report8D")
public class Qua8dReportController extends BaseController {

    @Autowired
    private Qua8dReportService qua8dReportService;

    @Autowired
    private Qua8dProblemService qua8dProblemService;


    /**
     * 分页查询+条件查询
     * @param qua8dReport
     * @return
     */
    @PostMapping("/listPage")
    public PageInfo<Qua8dReportDTO> listPage(@RequestBody Qua8dReport qua8dReport) {
        return qua8dReportService.listPage(qua8dReport);
    }


    /**
    * 获取8D报告
    * @param reportId
    */
    @GetMapping("/getReportById")
    public Qua8dReport getReportById(Long reportId) {
        Assert.notNull(reportId, "reportId不能为空");
        return qua8dReportService.getReportById(reportId);
    }

    /**
    * 修改8D报告
    * @param qua8dReport
    */
    @PostMapping("/modify")
    public Long modify(@RequestBody Qua8dReport qua8dReport) {
        return  qua8dReportService.modify(qua8dReport);
    }

    
    /**
    * 删除
    * @param reportId
    */
    @GetMapping("/delete")
    public void delete(Long reportId) {
        Assert.notNull(reportId, "reportId不能为空");
        qua8dReportService.removeById(reportId);
    }

    /**
     * 导入文件内容
     * @param file
     */
    @RequestMapping("/importExcel")
    public Map<String,Object> importExcel(@RequestParam("file") MultipartFile file, Fileupload fileupload) throws Exception {
        return qua8dReportService.importExcel(file,fileupload);
    }

    /**
     * 导出文件
     * @param excelParam
     * @param response
     * @throws Exception
     */
    @RequestMapping("/exportExcel")
    public void exportExcel(@RequestBody Qua8dReport excelParam, HttpServletResponse response) throws Exception {
        qua8dReportService.exportExcel(excelParam,response);
    }

}
