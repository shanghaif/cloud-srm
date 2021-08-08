package com.midea.cloud.srm.base.material.service;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.base.material.entity.CategoryBusiness;
import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
*  <pre>
 *  物料小类业务小类维护表 服务类
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-26 20:04:01
 *  修改内容:
 * </pre>
*/
public interface ICategoryBusinessService extends IService<CategoryBusiness> {

    /**
     * 条件查询
     * @param categoryBusiness
     * @return
     */
    PageInfo<CategoryBusiness> listByParam(CategoryBusiness categoryBusiness);

    /**
     * Description 下载导入模板
     * @param response
     * @Author fansb3@meicloud.com
     * @Date 2020/9/30
     **/
    void importModelDownload(HttpServletResponse response) throws Exception;

    /**
     * Description 导入Excel文件并返回数据
     * @param file
     * @param fileupload
     * @Author fansb3@meicloud.com
     * @Date 2020/9/30
     **/
    Map<String,Object> importExcel(MultipartFile file, Fileupload fileupload) throws Exception;
}
