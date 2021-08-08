package com.midea.cloud.srm.base.orgcompany.controller;

import com.midea.cloud.common.utils.EasyExcelUtil;
import com.midea.cloud.srm.base.orgcompany.service.ICompanyHeadService;
import com.midea.cloud.srm.model.base.orgcompany.entity.CompanyHead;
import com.midea.cloud.srm.model.cm.contract.entity.ContractPartner;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
*  <pre>
 *  组织-公司头表 前端控制器
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
@RestController
@RequestMapping("/orgcompany/company-head")
public class CompanyHeadController extends BaseController {

    @Autowired
    private ICompanyHeadService iCompanyHeadService;

    /**
    * 获取
    * @param companyId
    */
    @GetMapping("/get")
    public CompanyHead get(String companyId,String isActive,String isDefault,String isMain) {
        return iCompanyHeadService.get(companyId, isActive, isDefault, isMain);
    }

    /**
    * 新增
    * @param companyHead
    */
    @PostMapping("/add")
    public Long add(@RequestBody CompanyHead companyHead) {
        return iCompanyHeadService.addOrUpdate(companyHead);
    }
    
    /**
    * 删除
    * @param orgCompanyHeadId
    */
    @GetMapping("/delete")
    public void delete(Long orgCompanyHeadId) {
        Assert.notNull(orgCompanyHeadId, "orgCompanyHeadId不能为空");
        iCompanyHeadService.removeById(orgCompanyHeadId);
    }

    /**
    * 修改
    * @param companyHead
    */
    @PostMapping("/modify")
    public Long modify(@RequestBody CompanyHead companyHead) {
        Assert.notNull(companyHead,"参数不能为空");
        Assert.notNull(companyHead.getOrgCompanyHeadId(), "参数缺少: orgCompanyHeadId");
        return iCompanyHeadService.addOrUpdate(companyHead);
    }

    /**
     * 根于业务实体查找合作伙伴信息
     * @param ouId
     * @return
     */
    @GetMapping("/queryContractPartnerByOuId")
    public ContractPartner queryContractPartnerByOuId(@RequestParam("ouId") Long ouId){
        return iCompanyHeadService.queryContractPartnerByOuId(ouId);
    }

    /**
     * 根于业务实体查找合作伙伴信息
     * @param ouIdList
     * @return
     */
    @PostMapping("/queryContractPartnerByOuIdList")
    public List<ContractPartner> queryContractPartnerByOuIdList(@RequestBody List<Long> ouIdList){
        List<ContractPartner> contractPartners = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(ouIdList)){
            ouIdList.forEach(ouId->{
                ContractPartner contractPartner = iCompanyHeadService.queryContractPartnerByOuId(ouId);
                contractPartners.add(contractPartner);
            });
        }
        return contractPartners;
    }

    /**
     * 公司信息导入
     * @param response
     * @throws IOException
     */
    @RequestMapping("/importModelDownload")
    public void importModelDownload(HttpServletResponse response) throws IOException {
        InputStream inputStream = this.getClass().getResourceAsStream("/excel-model/公司信息导入模板.xlsx");
        OutputStream outputStream = null;
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            outputStream = EasyExcelUtil.getServletOutputStream(response, "公司信息导入模板");
            workbook.write(outputStream);
        } finally {
            if (null != outputStream) {
                outputStream.flush();
                outputStream.close();
            }
        }
    }

    /**
     * 导入文件
     * @param file
     */
    @RequestMapping("/importExcel")
    public Map<String,Object> importExcel(@RequestParam("file") MultipartFile file, Fileupload fileupload) throws Exception {
        return iCompanyHeadService.importExcel(file, fileupload);
    }
 
}
