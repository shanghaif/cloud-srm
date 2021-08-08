package com.midea.cloud.srm.cm.contract.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.cm.contract.entity.LevelMaintain;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
*  <pre>
 *  合同定级维护表 服务类
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
public interface ILevelMaintainService extends IService<LevelMaintain> {
    /**
     * 新增定级维护
     * @param levelMaintain
     */
    Long add(LevelMaintain levelMaintain);

    /**
     * 修改
     * @param levelMaintain
     */
    Long modify(LevelMaintain levelMaintain);

    /**
     * 分页查询
     * @param levelMaintain
     * @return
     */
    PageInfo<LevelMaintain> listPage(LevelMaintain levelMaintain);

    /**
     * 导入模板下载
     * @param response
     * @throws IOException
     */
    void importModelDownload(HttpServletResponse response) throws IOException;

    /**
     * 导入文件数据
     * @param file
     * @param fileupload
     * @return
     * @throws Exception
     */
    Map<String,Object> importExcel(MultipartFile file, Fileupload fileupload) throws Exception;

    /**
     * 导出文件
     * @param levelMaintain
     * @param response
     * @throws Exception
     */
    void exportExcel(LevelMaintain levelMaintain, HttpServletResponse response) throws Exception;
}
