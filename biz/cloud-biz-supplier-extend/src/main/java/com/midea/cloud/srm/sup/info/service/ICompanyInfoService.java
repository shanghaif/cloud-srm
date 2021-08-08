package com.midea.cloud.srm.sup.info.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.base.questionairesurvey.dto.VendorDTO;
import com.midea.cloud.srm.model.base.questionairesurvey.entity.SurveyScopeVendor;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.rbac.user.entity.User;
import com.midea.cloud.srm.model.supplier.change.dto.ChangeInfoDTO;
import com.midea.cloud.srm.model.supplier.info.dto.*;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import com.midea.cloud.srm.model.supplier.info.entity.ManagementAttach;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
*  <pre>
 *  基本信息 服务类
 * </pre>
*
* @author zhuwl7@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-02 14:29:48
 *  修改内容:
 * </pre>
*/
public interface ICompanyInfoService extends IService<CompanyInfo> {

    Long saveOrUpdateInfo(InfoDTO infoDTO, String orderStatus, String dataSource);

    Long saveOrUpdateCompanyInfo(CompanyInfo companyInfo);

    InfoDTO getInfoByParam(Long companyId);

    void startUpOrBlockUpReminder(Long id,String isReminder);

    void updateUpReminder(ManagementAttach managementAttach);
    /**
     * 废弃订单
     * @param companyId
     */
    void abandon(Long companyId);

    CompanyInfo getByCompanyId(Long companyId);

    Long saveCompanyGreenChannel(InfoDTO infoDTO);

    PageInfo<CompanyInfo> listPageByDTO(CompanyRequestDTO companyRequestDTO);

    List<CompanyInfo> listByDTO(CompanyRequestDTO companyRequestDTO);

    Long companyGreenChannelSubmit(InfoDTO infoDTO);

    Long companyGreenChannelApprove(InfoDTO infoDTO);

    /**
     * Description
     * @Param companyIds 条供应商ID isDelUser 是否删除用户信息(是-true，否false)
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.11.13
     * @throws
     **/
    void companyGreenChannelDelete(List<Long> companyIds, boolean isDelUser);

    void rejectCompanyInfo(RejectRequestDTO rejectRequestDTO);

    void checkSameCompanyName(String companyName, Long companyId);

    void checkSameLcCode(String lcCode, Long companyId);

    List<OrgCateServiceStatusDTO> listOrgCateServiceStatusByCompanyId(Long companyId);

    OrgCateServiceStatusDTO getOrgCateServiceStatusById(Long orgId, Long categoryId, Long vendorId);

    void saveOrUpdateInfoChange(ChangeInfoDTO infoDTO);

    /**
     * 推送供应商基础信息到Erp
     * @param companyId
     */
    void sendVendorToErp(Long companyId);

    /**
     * 推送供应商银行信息到Erp
     * @param companyId
     * @param erpVendorId
     */
    void sendVendorBank(Long companyId, Long erpVendorId);

    /**
     * 推送供应商地点信息到Erp
     * @param companyId
     * @param erpVendorId
     */
    void sendVendorSite(Long companyId, Long erpVendorId);

    /**
     * 推送供应商联系人信息到Erp
     * @param companyId
     * @param erpVendorId
     */
    void sendVendorContact(Long companyId, Long erpVendorId);

    /**
     * 根据合作组织ID和关键字分页查询供应商基本信息
     * @param companyRequestDTO
     * @return
     */
    List<CompanyInfo> listPageByOrgCodeAndKeyWord(CompanyRequestDTO companyRequestDTO);

    /**
     * 根据组织id和供应商查找
     * @param companyRequestDTO
     * @return
     */
    CompanyInfo queryVendorByNameAndOrgId(CompanyRequestDTO companyRequestDTO);

    void commonCheck(InfoDTO infoDTO, String orderStatus);

    List<CompanyInfo> getComponyByNameList(List<String> companyNameList);

    /**
     * 导入供应商
     * @param file
     * @param fileupload
     * @return
     */
    Map<String,Object> importExcel(MultipartFile file, Fileupload fileupload) throws Exception;

    /**
     * 根据公司ID查询合作ou和合作品类(ceea) add by chensl26
     *
     * @param companyId
     * @return
     */
    InfoDTO listOrgAndCategoryByCompanyId(Long companyId);

    /**
     * 获取供应商画像
     * @param vendorId
     * @return
     */
    VendorImageDto getVendorImage(Long vendorId) throws ParseException;

    void testCompany(String erpOrgId);

    PageInfo<CompanyInfo> listPageByOrgId(CompanyRequestDTO companyRequestDTO);

    void importModelDownload(HttpServletResponse response) throws IOException;

    Map<String, Object> importDatasExcel(MultipartFile file) throws IOException;

    /**
     * 获取所有供应商(价格库导入使用)
     * @return
     */
    List<CompanyInfo> listAllForImport();

    /**
     * 获取供应商主账号及子账号
     * @Param companyId
     * @modified xiexh12@meicloud.com 2020/09/10
     */
    /*List<User> getVendorAccountsByCompanyId(Long companyId);*/

    /**
     * 删除供应商相关信息(页面无调用)
     * (删除与供应商相关的所有表的数据, !!!谨慎调用)
     * @param companyId
     */
    void deleteCompanyInfoAllData(Long companyId);

    /**
     * 获取所有的供应商(根据名称)
     * @param companyInfoNameList
     * @return
     */
    List<CompanyInfo> listVendorByNameBatch(List<String> companyInfoNameList);

    /**
     * 根据dto获取供应商信息
     */
    List<CompanyInfo> listCompanyInfosByVendorDTO(VendorDTO vendorDTO);
    /**
     *
     */
    List<VendorDTO> listCompanyInfosByStringList(List<String> list);

    /**
     * 获取供应商列表通过dto
     * @param vendorDTO
     * @return
     */
    CompanyInfo getCompanyInfo(VendorDTO vendorDTO);
}
