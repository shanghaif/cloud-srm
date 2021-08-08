package com.midea.cloud.srm.sup.responsibility.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.supplier.responsibility.entity.SupplierLeader;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
*  <pre>
 * supplier leader维护表 服务类
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-19 14:45:21
 *  修改内容:
 * </pre>
*/
public interface ISupplierLeaderService extends IService<SupplierLeader> {

    /**
     * 添加
     * @param supplierLeaderList
     */
    void addSupplierLeader(List<SupplierLeader> supplierLeaderList);

    /**
     * 下载supplier leader导入模板
     * @param response
     * @throws Exception
     */
    void importSupplierLeaderModelDownload(HttpServletResponse response) throws Exception;

    /**
     * 文件导入
     * @param file
     * @param fileupload
     * @return
     * @throws Exception
     */
    Map<String,Object> importExcel(MultipartFile file, Fileupload fileupload) throws Exception;

    /**
     * 保存或更新供应商supplier leader
     * @param companyId
     * @param responsibilityId
     */
    void saveOrUpdateSupplierLeader(Long companyId, Long responsibilityId);

    /**
     * 分页条件查询
     */
    List<SupplierLeader> listPageByParam(SupplierLeader supplierLeader);

}
