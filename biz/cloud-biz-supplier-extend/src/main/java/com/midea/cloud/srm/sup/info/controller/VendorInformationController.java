package com.midea.cloud.srm.sup.info.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.sup.ExpiredCertificateType;
import com.midea.cloud.srm.model.supplier.info.dto.*;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import com.midea.cloud.srm.model.supplier.info.entity.ManagementAttach;
import com.midea.cloud.srm.sup.info.service.ICompanyInfoService;
import com.midea.cloud.srm.sup.info.service.IManagementAttachService;
import com.midea.cloud.srm.sup.info.service.IVendorInformationService;
import com.midea.cloud.srm.sup.reminderrecord.service.IReminderRecordService;
import com.midea.cloud.srm.supauth.entry.service.IFileRecordService;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/9 19:57
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/info/vendorInformation")
public class VendorInformationController {

    @Autowired
    private ICompanyInfoService iCompanyInfoService;

    @Autowired
    private IVendorInformationService iVendorInformationService;

    @Autowired
    private IManagementAttachService iManagementAttachService;

    @Autowired
    private IFileRecordService iFileRecordService;

    @Autowired
    private IReminderRecordService iReminderRecordService;

    /**
     * 分页查询 供应商信息
     * @modifiedBy xiexh12@meicloud.com
     * @param companyRequestDTO
     */
    @PostMapping("/listPageByDTO")
    public PageInfo<CompanyInfo> listPageByDTO(@RequestBody CompanyRequestDTO companyRequestDTO) {
        return iVendorInformationService.listByDTO(companyRequestDTO);
    }
    /**
     * 获取Info
     * @modifiedBy xiexh12@meicloud.com
     * @param companyId
     */
    @GetMapping("/getInformationByParam")
    public InfoDTO getInformationByParam(@RequestParam("companyId") Long companyId) {
        return iCompanyInfoService.getInfoByParam(companyId);
    }

    /**
     * 编辑供应商档案（只有是否单一供方、是否战略供应商可以编辑）
     * @modifiedBy xiexh12@meicloud.com
     * @param infoDTO
     */
    @PostMapping("/updateInformation")
    public void saveOrUpdateInformation(@RequestBody InfoDTO infoDTO) {
        iVendorInformationService.saveOrUpdateInformation(infoDTO);
    }

    /**
     * 删除供应商
     * @modifiedBy xiexh12@meicloud.com
     * @param companyId
     */
    @GetMapping("/deleteInformation")
    public void deleteVendorInformation(@RequestParam("companyId") Long companyId) {
        Assert.notNull(companyId, "供应商Id不能为空！");
        CompanyInfo companyInfo = iCompanyInfoService.getById(companyId);
        Assert.notNull(companyInfo, "找不到对应的供应商！");
        String status = companyInfo.getStatus();
        Assert.isTrue(status.equals("SUBMITTED"), "只有已提交状态才能删除！");
        iVendorInformationService.deleteVendorInformation(companyId);
    }

    /**
     * 供应商审核通过（前置状态为已提交 SUBMITTED 的可以进行审核）
     * @modifiedBy xiexh12@meicloud.com
     * @param companyId
     */
    @GetMapping("/vendorInformationApprove")
    public void vendorInformationApprove(@RequestParam("companyId") Long companyId) {
        Assert.notNull(companyId, "供应商Id不能为空！");
        iVendorInformationService.vendorInformationApprove(companyId);
    }

    /**
     * 证件到期提醒查询 分页
     * @modifiedBy xiexh12@meicloud.com 2020/09/12
     * @param
     */
    @PostMapping("/listManagementAttachPageByDTO")
    public PageInfo<ManagementAttach> listManagementAttachPageByDTO(@RequestBody ManagementAttachRequestDTO managementAttachRequestDTO){
        return iVendorInformationService.listManagementAttachPageByDTO(managementAttachRequestDTO);
    }
    /**
     * 停用 证件
     */
    @PostMapping("/blockUpOrStartUpReminder")
    public void blockUpOrStartUpReminder(@RequestBody ManagementAttach managementAttach) {

        if(managementAttach.getFormType().equals(ExpiredCertificateType.COMPANY_INFO.getValue())) {
            iCompanyInfoService.startUpOrBlockUpReminder(managementAttach.getManagementAttachId(), managementAttach.getIsUseReminder());
        } else if(managementAttach.getFormType().equals(ExpiredCertificateType.MANAGEMENT_ATTACH.getValue())) {
            iManagementAttachService.startUpOrBlockUpReminder(managementAttach.getManagementAttachId(), managementAttach.getIsUseReminder());
        } else {
            iFileRecordService.blockUpOrStartUpReminder(managementAttach.getFileRecordId(), managementAttach.getIsUseReminder());
        }
    }
    /**
     * 变更 证件
     */
    @PostMapping("modify")
    public void updateData(@RequestBody ManagementAttach managementAttach) {

        //保存变更历史
        iReminderRecordService.saveReminderRecord(managementAttach);
        //更新变更
        if(managementAttach.getFormType().equals(ExpiredCertificateType.COMPANY_INFO.getValue())) {
            iCompanyInfoService.updateUpReminder(managementAttach);
        } else if(managementAttach.getFormType().equals(ExpiredCertificateType.MANAGEMENT_ATTACH.getValue())) {
            iManagementAttachService.updateUpReminder(managementAttach);
        } else {
            iFileRecordService.updateUpReminder(managementAttach);
        }
    }
    /**
     * 证件到期提醒查询 不分页
     *
     * @param
     */
    @PostMapping("/listAllManagementAttachPageByDTO")
    public List<ManagementAttach> listAllManagementAttachPageByDTO(@RequestBody ManagementAttach managementAttach){
        return iVendorInformationService.listAllManagementAttachByDTO(managementAttach);
    }

    /**
     * 供应商清单驳回接口
     */
    @GetMapping("/rejectInformation")
    public void rejectVendorInformation(Long companyId){
        Assert.notNull(companyId, "该供应商id不存在.");
        iVendorInformationService.rejectVendorInformation(companyId);
    }
}
