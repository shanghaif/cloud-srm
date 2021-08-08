package com.midea.cloud.srm.base.noticetest.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.base.noticetest.service.NoticeTestService;
import com.midea.cloud.srm.model.base.noticetest.entity.NoticeTest;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
*  <pre>
 *  功能代码生成测试 前端控制器
 * </pre>
*
* @author linsb@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021-1-17 20:36:12
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/base/noticetest")
public class NoticeTestController extends BaseController {

    @Autowired
    private NoticeTestService noticeTestService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public NoticeTest get(Long id) {
        Assert.notNull(id, "id不能为空");
        return noticeTestService.getById(id);
    }

    /**
    * 新增
    * @param noticeTest
    */
    @PostMapping("/add")
    public void add(@RequestBody NoticeTest noticeTest) {
        Long id = IdGenrator.generate();
        noticeTest.setNoticeId(id);
        noticeTestService.save(noticeTest);
    }

    /**
     * 批量新增
     * @param noticeTestList
     */
    @PostMapping("/batchAdd")
    public void batchAdd(@RequestBody List<NoticeTest> noticeTestList) throws IOException{
        noticeTestService.batchAdd(noticeTestList);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        noticeTestService.removeById(id);
    }
    /**
     * 批量删除
     * @param ids
     */
    @GetMapping("/bathDelete")
    public void bathDelete(@RequestBody List<Long> ids) throws IOException{
        noticeTestService.batchDeleted(ids);
    }
    /**
    * 修改
    * @param noticeTest
    */
    @PostMapping("/modify")
    public void modify(@RequestBody NoticeTest noticeTest) {
        noticeTestService.updateById(noticeTest);
    }


    /**
     * 批量修改
     * @param noticeTestList
     */
    @PostMapping("/batchModify")
    public void batchModify(@RequestBody List<NoticeTest> noticeTestList) throws IOException{
        noticeTestService.batchUpdate(noticeTestList);
    }
    /**
    * 分页查询
    * @param noticeTest
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<NoticeTest> listPage(@RequestBody NoticeTest noticeTest) {
        return noticeTestService.listPage(noticeTest);
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<NoticeTest> listAll() {
        return noticeTestService.list();
    }

    /**
     * 导入文件模板下载
     * @return
     */
    @RequestMapping("/exportExcelTemplate")
    public void exportExcelTemplate(HttpServletResponse response) throws IOException {
        noticeTestService.exportExcelTemplate(response);
    }

    /**
     * 导入文件内容
     * @param file
     */
    @RequestMapping("/importExcel")
    public Map<String,Object> importExcel(@RequestParam("file") MultipartFile file, Fileupload fileupload) throws Exception {
        return noticeTestService.importExcel(file,fileupload);
    }

    /**
     * 导出文件
     * @param excelParam
     * @param response
     * @throws Exception
     */
    @RequestMapping("/exportExcel")
    public void exportExcel(@RequestBody NoticeTest excelParam, HttpServletResponse response) throws Exception {
        noticeTestService.exportExcel(excelParam,response);
    }
}
