package com.midea.cloud.srm.base.dept.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.base.dept.entity.Companies;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
*  <pre>
 *   服务类
 * </pre>
*
* @author yourname@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-10-07 13:59:21
 *  修改内容:
 * </pre>
*/
public interface ICompaniesService extends IService<Companies> {
    /**
     * 导入公司基本信息数据
     *
     * @param file
     */
    Map<String,Object> importExcelByBasics(MultipartFile file, Fileupload fileupload) throws Exception;

    /**
     * 下载公司基本信息数据导入模板
     * @return
     */
    void imporDownloadByBasics(HttpServletResponse response) throws Exception;
}
