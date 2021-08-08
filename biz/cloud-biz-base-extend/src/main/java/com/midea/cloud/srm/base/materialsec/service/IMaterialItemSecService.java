package com.midea.cloud.srm.base.materialsec.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.base.material.MaterialItemSec;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
*  <pre>
 *  物料附表维护 服务类
 * </pre>
*
* @author yourname@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-10-27 14:39:23
 *  修改内容:
 * </pre>
*/
public interface IMaterialItemSecService extends IService<MaterialItemSec> {
    /**
     * 下载导入模板
     * @param response
     */
    void importModelDownload(HttpServletResponse response) throws IOException;

    /**
     * 导入文件
     * @param file
     * @param fileupload
     * @return
     * @throws Exception
     */
    Map<String,Object> importExcel(MultipartFile file, Fileupload fileupload) throws Exception;
}
