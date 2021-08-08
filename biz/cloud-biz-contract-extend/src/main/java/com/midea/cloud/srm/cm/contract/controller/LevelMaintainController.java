package com.midea.cloud.srm.cm.contract.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.cm.contract.service.ILevelMaintainService;
import com.midea.cloud.srm.model.cm.contract.entity.LevelMaintain;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
*  <pre>
 *  合同定级维护表 前端控制器
 * </pre>
*
* @author yourname@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-10-06 10:59:25
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/contract/level-maintain")
public class LevelMaintainController extends BaseController {

    @Autowired
    private ILevelMaintainService iLevelMaintainService;

    /**
    * 获取
    * @param levelMaintainId
    */
    @GetMapping("/get")
    public LevelMaintain get(Long levelMaintainId) {
        Assert.notNull(levelMaintainId, "levelMaintainId不能为空");
        return iLevelMaintainService.getById(levelMaintainId);
    }

    /**
    * 新增
    * @param levelMaintain
    */
    @PostMapping("/add")
    public Long add(@RequestBody LevelMaintain levelMaintain) {
        return iLevelMaintainService.add(levelMaintain);
    }
    
    /**
    * 删除
    * @param levelMaintainId
    */
    @GetMapping("/delete")
    public void delete(Long levelMaintainId) {
        Assert.notNull(levelMaintainId, "levelMaintainId不能为空");
        iLevelMaintainService.removeById(levelMaintainId);
    }

    /**
    * 修改
    * @param levelMaintain
    */
    @PostMapping("/modify")
    public Long modify(@RequestBody LevelMaintain levelMaintain) {
        return iLevelMaintainService.modify(levelMaintain);
    }

    /**
    * 分页查询
    * @param levelMaintain
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<LevelMaintain> listPage(@RequestBody LevelMaintain levelMaintain) {
        return iLevelMaintainService.listPage(levelMaintain);
    }

    /**
     * 导入文件模板下载
     * @return
     */
    @RequestMapping("/importModelDownload")
    public void importModelDownload(HttpServletResponse response) throws IOException {
        iLevelMaintainService.importModelDownload(response);
    }

    /**
     * 导入文件
     * @param file
     */
    @RequestMapping("/importExcel")
    public Map<String,Object> importExcel(@RequestParam("file") MultipartFile file, Fileupload fileupload) throws Exception {
        return iLevelMaintainService.importExcel(file, fileupload);
    }

    /**
     * 导出文件
     * @param levelMaintain
     * @param response
     * @throws Exception
     */
    @RequestMapping("/exportExcel")
    public void exportExcel(@RequestBody LevelMaintain levelMaintain, HttpServletResponse response) throws Exception {
        iLevelMaintainService.exportExcel(levelMaintain, response);
    }

}
