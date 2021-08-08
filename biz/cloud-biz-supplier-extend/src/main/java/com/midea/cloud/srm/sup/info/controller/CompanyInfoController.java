package com.midea.cloud.srm.sup.info.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.ApproveStatusType;
import com.midea.cloud.common.enums.SupplierDataSourceType;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.ObjectUtil;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.supcooperate.SupcooperateClient;
import com.midea.cloud.srm.model.base.organization.entity.Organization;
import com.midea.cloud.srm.model.base.questionairesurvey.dto.VendorDTO;
import com.midea.cloud.srm.model.base.questionairesurvey.entity.SurveyScopeVendor;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.supplier.info.dto.*;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import com.midea.cloud.srm.sup.info.service.IAttachFileService;
import com.midea.cloud.srm.sup.info.service.ICompanyInfoService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <pre>
 *  基本信息 前端控制器
 * </pre>
 *
 * @author zhuwl7@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-02 14:29:48
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/info/companyInfo")
public class CompanyInfoController extends BaseController {

    @Autowired
    private ICompanyInfoService iCompanyInfoService;

    @Autowired
    private IAttachFileService iattachFileService;

    @Resource
    private SupcooperateClient supcooperateClient;

    /**Base模块Feign*/
    @Resource
    private BaseClient baseClient;

    /**
     * 获取
     *
     * @param companyId
     */
    @GetMapping("/get")
    public CompanyInfo get(Long companyId) {
        Assert.notNull(companyId, "id不能为空");
        return iCompanyInfoService.getById(companyId);
    }

    /**
     * 批量获取
     *
     * @param companyIds
     */
    @GetMapping("/getByIds")
    public List<CompanyInfo> getByIds(@RequestParam("companyIds") List<Long> companyIds) {
        if (!CollectionUtils.isEmpty(companyIds)) {
            QueryWrapper<CompanyInfo> queryCompanyInfoWrapper = new QueryWrapper<CompanyInfo>();
            queryCompanyInfoWrapper.in("COMPANY_ID", companyIds);
            return iCompanyInfoService.list(queryCompanyInfoWrapper);
        }else {
            return null;
        }
    }

    /**
     * 新增
     *
     * @param companyInfo
     */
    @PostMapping("/add")
    public void add(CompanyInfo companyInfo) {
        Long id = IdGenrator.generate();
        companyInfo.setCompanyId(id);
        iCompanyInfoService.save(companyInfo);
    }

    /**
     * 删除
     *
     * @param id
     */
    @PostMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iCompanyInfoService.removeById(id);
    }

    /**
     * 删除供应商相关信息(页面无调用)
     * (删除与供应商相关的所有表的数据, !!!谨慎调用)
     */
    @GetMapping("/deleteCompanyInfoAllData")
    public void deleteCompanyInfoAllData(Long companyId) {
        Optional.ofNullable(companyId).orElseThrow(() -> new BaseException(LocaleHandler.getLocaleMsg("传入的供应商id不能为空.")));
        iCompanyInfoService.deleteCompanyInfoAllData(companyId);
    }


    /**
     * 废弃订单
     * @param companyId
     */
    @GetMapping("/abandon")
    public void abandon(Long companyId) {
        Assert.notNull(companyId,"废弃订单id不能为空");
        iCompanyInfoService.abandon(companyId);
    }


    /**
     * 修改
     *
     * @param companyInfo
     */
    @PostMapping("/modify")
    public void modify(@RequestBody CompanyInfo companyInfo) {
        iCompanyInfoService.updateById(companyInfo);
    }

    /**
     * 分页查询
     *
     * @param companyInfo
     * @return
     */
    @PostMapping("/listPage")
    public PageInfo<CompanyInfo> listPage(CompanyInfo companyInfo) {
        PageUtil.startPage(companyInfo.getPageNum(), companyInfo.getPageSize());
        QueryWrapper<CompanyInfo> wrapper = new QueryWrapper<CompanyInfo>(companyInfo);
        return new PageInfo<CompanyInfo>(iCompanyInfoService.list(wrapper));
    }

    /**
     * 查询所有
     *
     * @return
     */
    @PostMapping("/CompanyInfoListAll")
    public List<CompanyInfo> CompanyInfoListAll() {
        QueryWrapper<CompanyInfo> wrapper = new QueryWrapper<>();
        wrapper.select("COMPANY_ID,STATUS,COMPANY_CODE,ERP_VENDOR_ID,ERP_VENDOR_CODE,COMPANY_NAME,COMPANY_EN_NAME\n" +
                ",IS_BACKLIST,VENDOR_CLASSIFICATION,IF_LONG_PERIOD,BACKLIST_UPDATED_BY\n" +
                ",DATA_SOURCES,CEEA_COMPANY_INTRO,CEEA_SUP_BUSINESS_TYPE,EMAIL,NICKNAME,IF_NEW_COMPANY");
        return iCompanyInfoService.list(wrapper);
    }
    /**
     * 查询所有
     *
     * @return
     */
    @PostMapping("/listAll")
    public List<CompanyInfo> listAll() {
        return iCompanyInfoService.list();
    }

    /**
     * 保存或更新（注册暂存）
     */
    @PostMapping("/saveOrUpdateInfo")
    public Long saveOrUpdateInfo(@RequestBody InfoDTO infoDTO) {
        iCompanyInfoService.commonCheck(infoDTO, ApproveStatusType.DRAFT.getValue());
        return iCompanyInfoService.saveOrUpdateInfo(infoDTO, ApproveStatusType.DRAFT.getValue(),
                SupplierDataSourceType.ONESELF_REGISTER.getValue());
    }

    /**
     * 保存或更新(注册提交)
     */
    @PostMapping("/submitInfo")
    public Long submitInfo(@RequestBody InfoDTO infoDTO) {
        iCompanyInfoService.commonCheck(infoDTO, ApproveStatusType.SUBMITTED.getValue());
        ObjectUtil.validate(infoDTO);
        return iCompanyInfoService.saveOrUpdateInfo(infoDTO, ApproveStatusType.SUBMITTED.getValue()
                , SupplierDataSourceType.ONESELF_REGISTER.getValue());
    }

    /**
     * 获取Info
     */
    @PostMapping("/getInfoByParam")
    public InfoDTO getInfoByParam(Long companyId) {
        return iCompanyInfoService.getInfoByParam(companyId);
    }


    /**
     * 保存或更新(绿色通道暂存)
     */
    @PostMapping("/saveCompanyGreenChannel")
    public Long saveCompanyGreenChannel(@RequestBody InfoDTO infoDTO) {
        return iCompanyInfoService.saveCompanyGreenChannel(infoDTO);
    }

    /**
     * 保存或更新(绿色通道提交)
     */
    @PostMapping("/companyGreenChannelSubmit")
    public Long companyGreenChannelSubmit(@RequestBody InfoDTO infoDTO) {
        ObjectUtil.validate(infoDTO);
        return iCompanyInfoService.companyGreenChannelSubmit(infoDTO);
    }

    /**
     * 绿色通道审批
     * @modified xiexh12@meicloud.com
     */
    @PostMapping("/companyGreenChannelApprove")
    public Long companyGreenChannelApprove(@RequestBody InfoDTO infoDTO) {
        return iCompanyInfoService.companyGreenChannelApprove(infoDTO);
    }

    /**
     * Description 绿色通道-删除用户信息表的
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.11.13
     * @throws
     **/
    @PostMapping("/companyGreenChannelDelete")
    public void companyGreenChannelDelete(@RequestBody List<Long> companyIds) {
        iCompanyInfoService.companyGreenChannelDelete(companyIds, true);
    }

    /**
     * Description 供应商清单-驳回
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.11.13
     * @throws
     **/
    @PostMapping("/companyGreenChannelDeleteNotDelUser")
    public void companyGreenChannelDeleteNotDelUser(@RequestBody List<Long> companyIds) {
        iCompanyInfoService.companyGreenChannelDelete(companyIds, false);
    }

    /**
     * 分页查询
     *
     * @param companyRequestDTO
     * @return
     */
    @PostMapping("/listPageByDTO")
    public PageInfo<CompanyInfo> listPageByDTO(@RequestBody CompanyRequestDTO companyRequestDTO) {
        return iCompanyInfoService.listPageByDTO(companyRequestDTO);
    }

    /**
     * 驳回单据并且记录日志
     *
     * @param rejectRequestDTO
     */
    @PostMapping("/rejectCompanyInfo")
    public void rejectCompanyInfo(@RequestBody RejectRequestDTO rejectRequestDTO) {
        iCompanyInfoService.rejectCompanyInfo(rejectRequestDTO);
    }

    /**
     * 检测公司名是否重复
     *
     * @param checkRequestDTO
     */
    @PostMapping("/checkSameLcCode")
    public void checkSameLcCode(@RequestBody CheckRequestDTO checkRequestDTO) {
        if (checkRequestDTO != null && StringUtils.isNoneBlank(checkRequestDTO.getLcCode())) {
            iCompanyInfoService.checkSameLcCode(checkRequestDTO.getLcCode(), checkRequestDTO.getCompanyId());
        }
    }

    /**
     * 检测证件号是否重复
     *
     * @param checkRequestDTO
     */
    @PostMapping("/checkSameCompanyName")
    public void checkSameCompanyName(@RequestBody CheckRequestDTO checkRequestDTO) {
        if (checkRequestDTO != null && StringUtils.isNoneBlank(checkRequestDTO.getCompanyName())) {
            iCompanyInfoService.checkSameCompanyName(checkRequestDTO.getCompanyName(), checkRequestDTO.getCompanyId());
        }
    }

    /**
     * 根据公司ID查询组织与品类服务状态DTO
     *
     * @param companyId
     * @return
     */
    @GetMapping("/listOrgCateServiceStatusByCompanyId")
    public List<OrgCateServiceStatusDTO> listOrgCateServiceStatusByCompanyId(Long companyId) {
        return iCompanyInfoService.listOrgCateServiceStatusByCompanyId(companyId);
    }

    /**
     * 根据公司ID查询合作ou和合作品类(ceea) add by chensl26
     *
     * @param companyId
     * @return
     */
    @GetMapping("/listOrgAndCategoryByCompanyId")
    public InfoDTO listOrgAndCategoryByCompanyId(@RequestParam Long companyId) {
        return iCompanyInfoService.listOrgAndCategoryByCompanyId(companyId);
    }

    /**
     * 根据编码获取组织与品类服务状态
     *
     * @param orgId
     * @param categoryId
     * @return
     */
    @GetMapping("/getOrgCateServiceStatusById")
    public OrgCateServiceStatusDTO getOrgCateServiceStatusById(Long orgId, Long categoryId, Long vendorId) {
        return iCompanyInfoService.getOrgCateServiceStatusById(orgId, categoryId, vendorId);
    }

    /**
     * 根据合作组织ID和关键字分页查询供应商基本信息
     *
     * @param companyRequestDTO
     * @return
     */
    @PostMapping("/listPageByOrgIdAndKeyWord")
    public List<CompanyInfo> listPageByOrgCodeAndKeyWord(@RequestBody CompanyRequestDTO companyRequestDTO) {
        return iCompanyInfoService.listPageByOrgCodeAndKeyWord(companyRequestDTO);
    }

    /**
     * 根据合作组织ID和供应商查询基本信息
     *
     * @param companyRequestDTO
     * @return
     */
    @PostMapping("/queryVendorByNameAndOrgId")
    public CompanyInfo queryVendorByNameAndOrgId(@RequestBody CompanyRequestDTO companyRequestDTO) {
        return iCompanyInfoService.queryVendorByNameAndOrgId(companyRequestDTO);
    }

    /**
     * 根据条件获取
     *
     * @param companyInfo
     */
    @PostMapping("/getByParam")
    public CompanyInfo getByParam(@RequestBody CompanyInfo companyInfo) {
        return iCompanyInfoService.getOne(new QueryWrapper<>(companyInfo));
    }

    /**
     * 根据条件获取数量
     *
     * @param companyInfo
     */
    @PostMapping("/getCountByParam")
    public int getCountByParam(@RequestBody CompanyInfo companyInfo) {
        return iCompanyInfoService.count(new QueryWrapper<>(companyInfo));
    }

    /**
     * 根据条件批量获取公司信息
     */
    @PostMapping("/getComponyByNameList")
    List<CompanyInfo> getComponyByNameList(@RequestBody List<String> companyNameList) {
        return iCompanyInfoService.getComponyByNameList(companyNameList);
    }

    /**
     * 根据条件批量获取公司信息
     */
    @PostMapping("/getComponyByCodeList")
    Map<String,CompanyInfo> getComponyByCodeList(@RequestBody List<String> companyCodeList) {
        Map<String,CompanyInfo> companyInfoMap = new HashMap<>();
        if(CollectionUtils.isNotEmpty(companyCodeList)){
            List<CompanyInfo> companyInfoList = iCompanyInfoService.list(new QueryWrapper<CompanyInfo>().in("COMPANY_CODE", companyCodeList));
            if(CollectionUtils.isNotEmpty(companyInfoList)){
                companyInfoMap = companyInfoList.stream().collect(Collectors.toMap(CompanyInfo::getCompanyCode, v -> v, (k1, k2) -> k1));
            }
        }
        return companyInfoMap;
    }

//    /**
//     * 供应商导入模板下载
//     * @return
//     */
//    @PreAuthorize("hasAuthority('sup:companyInfo:importVendor')")
//    @RequestMapping("/importModelDownload")
//    public void importModelDownload(HttpServletResponse response) throws IOException {
//        InputStream inputStream = this.getClass().getResourceAsStream("/excel-model/供应商导入模板.xlsx");
//        OutputStream outputStream = null;
//        try {
//            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
//            outputStream = EasyExcelUtil.getServletOutputStream(response, "供应商导入模板");
//            workbook.write(outputStream);
//        } finally {
//            if (null != outputStream) {
//                outputStream.flush();
//                outputStream.close();
//            }
//        }
//    }

    /**
     * 供应商导入模板下载  modify by chensl26
     * @return
     */
    @RequestMapping("/importModelDownload")
    public void importModelDownload(HttpServletResponse response) throws IOException {
        iCompanyInfoService.importModelDownload(response);
    }


//    /**
//     * 导入文件
//     * @param file
//     */
//    @RequestMapping("/importExcel")
//    @PreAuthorize("hasAuthority('sup:companyInfo:importVendor')")
//    public Map<String,Object> importExcel(@RequestParam("file") MultipartFile file, Fileupload fileupload) throws Exception {
//        return iCompanyInfoService.importExcel(file,fileupload);
//    }

    /**
     * 导入文件
     * @param file  add by chensl26
     */
    @RequestMapping("/importDatasExcel")
    public Map<String,Object> importDatasExcel(@RequestParam("file") MultipartFile file) throws Exception {
        return iCompanyInfoService.importDatasExcel(file);
    }



    /**
     * 获取供应商画像
     * @param vendorId 供应商ID
     */
    @GetMapping("/getVendorImage")
    public VendorImageDto getVendorImage(@RequestParam("vendorId") Long vendorId) throws ParseException {
        return iCompanyInfoService.getVendorImage(vendorId);
    }


    /**
     * 获取近三年采购金额汇总 维度: 供应商+采购分类
     * @param vendorId
     * @param categoryId
     * @return
     */
    @GetMapping("/aggregateAmount")
    List<PurchaseAmountDto> aggregateAmount(@RequestParam("vendorId") Long vendorId, @RequestParam("categoryId") Long categoryId) throws ParseException{
        return supcooperateClient.aggregateAmount(vendorId, categoryId);
    }

    /**
     * Description 根据组织设置Id获取组织设置信息
     * @Param organizationId 组织设置Id
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.09.20
     **/
    @GetMapping("/getOrganizationById")
    public Organization getOrganizationById(Long organizationId){
        return baseClient.get(organizationId);
    }

    /**
     * 根据业务实体ID分页查询供应商信息
     * @param companyRequestDTO
     * @return
     */
    @PostMapping("/listPageByOrgId")
    public PageInfo<CompanyInfo> listPageByOrgId(@RequestBody CompanyRequestDTO companyRequestDTO) {
        return iCompanyInfoService.listPageByOrgId(companyRequestDTO);
    }

    @GetMapping("/testCompany")
    public void testCompany(@RequestParam String erpOrgId) {
        iCompanyInfoService.testCompany(erpOrgId);

    }

    @GetMapping("/listAllForImport")
    List<CompanyInfo> listAllForImport(){
        return iCompanyInfoService.listAllForImport();
    }

    /**
     * 获取所有的供应商(根据名称)
     * @param companyInfoNameList
     * @return
     */
    @PostMapping("/listVendorByNameBatch")
    List<CompanyInfo> listVendorByNameBatch(@RequestBody List<String> companyInfoNameList){
        return iCompanyInfoService.listVendorByNameBatch(companyInfoNameList);
    }

    /**
     * 获取供应商通过dto
     */
    @PostMapping("/listCompanyInfosByVendorDTO")
    PageInfo<CompanyInfo> listCompanyInfosByVendorDTO(@RequestBody VendorDTO vendorDTO) {
        List<CompanyInfo> companyInfoList = iCompanyInfoService.listCompanyInfosByVendorDTO(vendorDTO);
        return new PageInfo<>(companyInfoList);
    }
    /**
     * 获取供应商通过list
     */
    @PostMapping("/listCompanyInfosByStringList")
    List<VendorDTO> listCompanyInfosByStringList(@RequestParam("list") List<String> list) {
        return iCompanyInfoService.listCompanyInfosByStringList(list);
    }
    /**
     * 获取供应商信息通过dto
     */
    @PostMapping("/getCompanyInfo")
    CompanyInfo getCompanyInfo(@RequestBody VendorDTO vendorDTO) {
        return iCompanyInfoService.getCompanyInfo(vendorDTO);
    }
}
