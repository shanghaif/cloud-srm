package com.midea.cloud.srm.base.businesstype.service;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.base.businesstype.entity.BussinessType;
import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.base.businesstype.entity.BussinessType;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
*  <pre>
 *  业务类型配置表 服务类
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-10-21 14:57:34
 *  修改内容:
 * </pre>
*/
public interface IBussinessTypeService extends IService<BussinessType> {
    /**
     * 导入模板下载
     * @param response
     * @throws IOException
     */
    void importModelDownload(HttpServletResponse response) throws IOException;

    Long saveOrUpdateBussinessType(BussinessType bussinessType);

    PageInfo<BussinessType> listPageByParam(BussinessType bussinessType);
    /**
     * 文件导入
     * @param file
     * @param fileupload
     * @return
     * @throws Exception
     */
    Map<String,Object> importExcel(@RequestParam("file") MultipartFile file, Fileupload fileupload) throws Exception;
}
