package com.midea.cloud.srm.base.noticetest.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.base.noticetest.entity.NoticeTest;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
*  <pre>
 *  功能代码生成测试 服务类
 * </pre>
*
* linsb@meicloud.com
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
public interface NoticeTestService extends IService<NoticeTest>{
    /*
     批量增加
     */
    void batchAdd(List<NoticeTest> noticeTestList) throws IOException;

    /*
     批量更新
     */
    void batchUpdate(List<NoticeTest> noticeTestList) throws IOException;

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
    void exportExcel(NoticeTest excelParam, HttpServletResponse response)throws IOException;
   /*
   分页查询
    */
    PageInfo<NoticeTest> listPage(NoticeTest noticeTest);
}
