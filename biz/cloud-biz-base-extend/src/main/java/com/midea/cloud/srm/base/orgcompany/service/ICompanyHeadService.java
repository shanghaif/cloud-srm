package com.midea.cloud.srm.base.orgcompany.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.base.orgcompany.entity.CompanyHead;
import com.midea.cloud.srm.model.cm.contract.entity.ContractPartner;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
*  <pre>
 *  组织-公司头表 服务类
 * </pre>
*
* @author yourname@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-10-14 14:32:08
 *  修改内容:
 * </pre>
*/
public interface ICompanyHeadService extends IService<CompanyHead> {

    /**
     * 新增
     * @param companyHead
     * @return
     */
    Long addOrUpdate(CompanyHead companyHead);

    /**
     * 获取详情
     * @param companyId
     * @return
     */
    CompanyHead get(String companyId,String isActive,String isDefault,String isMain);

    /**
     * 根据业务实体Id
     * @param ouId
     * @return
     */
    ContractPartner queryContractPartnerByOuId(Long ouId);

    /**
     * 导入文件
     * @param file
     * @param fileupload
     * @return
     * @throws Exception
     */
    Map<String,Object> importExcel(@RequestParam("file") MultipartFile file, Fileupload fileupload) throws Exception;
}
