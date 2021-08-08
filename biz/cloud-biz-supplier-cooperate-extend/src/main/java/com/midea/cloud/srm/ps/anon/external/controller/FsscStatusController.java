package com.midea.cloud.srm.ps.anon.external.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.midea.cloud.common.utils.JsonUtil;
import com.midea.cloud.component.filter.HttpServletHolder;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.pm.ps.anon.external.FsscStatus;
import com.midea.cloud.srm.ps.anon.external.service.IFsscStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
*  <pre>
 *  FSSC状态反馈表 前端控制器
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-02 08:47:44
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/ps-anon/external/fsscStatus")
public class FsscStatusController extends BaseController {

    @Autowired
    private IFsscStatusService iFsscStatusService;

    @PostMapping("/receiveData")
    public void receiveData(@RequestBody FsscStatus fsscStatus) throws IOException {
        HttpServletResponse response = HttpServletHolder.getResponse();
        Map<String, Object> resultMap = iFsscStatusService.receiveData(fsscStatus);
        response.setHeader("Context-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        writer.write(JsonUtil.entityToJsonStr(resultMap));
        response.flushBuffer();
        writer.close();
    }

    @GetMapping("/download")
    public void download(@RequestParam("fileuploadId") String fileuploadId) throws IOException {
        HttpServletResponse response = HttpServletHolder.getResponse();
        Map<String, Object> resultMap = iFsscStatusService.download(fileuploadId);
        response.setHeader("Context-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        writer.write(JsonUtil.entityToJsonStr(resultMap));
        response.flushBuffer();
        writer.close();
    }

}
