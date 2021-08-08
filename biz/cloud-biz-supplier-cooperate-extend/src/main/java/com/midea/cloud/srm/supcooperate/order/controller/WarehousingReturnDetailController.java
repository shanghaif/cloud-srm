package com.midea.cloud.srm.supcooperate.order.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.common.utils.StringUtil;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.supplier.SupplierClient;
import com.midea.cloud.srm.model.base.organization.entity.Organization;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.common.ExportExcelParam;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.OrderRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.WarehousingReturnDetailRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.WarehousingReturnDetail;
import com.midea.cloud.srm.supcooperate.order.service.IWarehousingReturnDetailService;
import com.midea.cloud.srm.supcooperate.reconciliation.service.IWarehouseReceiptService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author chenwj92@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人: chenwj92
 *  修改日期: 2020/8/20 14:14
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/order/warehousingReturnDetail")
public class WarehousingReturnDetailController extends BaseController {
    @Autowired
    private IWarehousingReturnDetailService warehousingReturnDetailService;

    @PostMapping("/listPage")
    public PageInfo<WarehousingReturnDetail> listPage(@RequestBody WarehousingReturnDetailRequestDTO warehousingReturnDetailRequestDTO){
        PageUtil.startPage(warehousingReturnDetailRequestDTO.getPageNum(),warehousingReturnDetailRequestDTO.getPageSize());
        return new PageInfo<WarehousingReturnDetail>(warehousingReturnDetailService.listPage(warehousingReturnDetailRequestDTO));
    }

    @PostMapping("/list")
    public List<WarehousingReturnDetail> list(@RequestBody WarehousingReturnDetailRequestDTO warehousingReturnDetailRequestDTO){
        return warehousingReturnDetailService.list(warehousingReturnDetailRequestDTO);
    }

    @RequestMapping("/export")
    public void export(@RequestBody WarehousingReturnDetailRequestDTO warehousingReturnDetailRequestDTO, HttpServletResponse response){
        warehousingReturnDetailService.export(warehousingReturnDetailRequestDTO,response);

    }

    /**
     * 导出
     * @param response
     * @throws IOException
     */
    @PostMapping("/exportExcel")
    public void exportExcel(@RequestBody WarehousingReturnDetailRequestDTO warehousingReturnDetail,HttpServletResponse response) throws IOException {
        warehousingReturnDetail= ObjectUtils.isEmpty(warehousingReturnDetail)?new WarehousingReturnDetailRequestDTO():warehousingReturnDetail;
        warehousingReturnDetailService.exportStart(warehousingReturnDetail,response);
    }

    /*@Autowired
    private BaseClient baseClient;
    @Autowired
    private SupplierClient supplierClient;

    @GetMapping("/testUpdateData")
    @Transactional
    public void updateData(){
        *//* 新增erpOrgId,erpOrganizationId,erpOrganizationCode,erpVendorId,erpVendorCode 修改orgId,organizationId,organizationCode,vendorId,vendorCode*//*
        List<CompanyInfo> companyInfos = supplierClient.listAllCompanyInfo();
        List<Organization> organizations = baseClient.listAllOrganization();

        List<WarehousingReturnDetail> updates = new ArrayList<>();
        List<WarehousingReturnDetail> warehousingReturnDetailList = warehousingReturnDetailService.list(new QueryWrapper<>());
        int index = 0;
        for(WarehousingReturnDetail warehousingReturnDetail:warehousingReturnDetailList){
            *//*根据erp_org_id查询出 org 赋值 org_id ,修改原值*//*
            boolean flag = false;
            WarehousingReturnDetail item = new WarehousingReturnDetail();
            item.setWarehousingReturnDetailId(warehousingReturnDetail.getWarehousingReturnDetailId());
            for(Organization organization:organizations){
                if(StringUtils.isNotBlank(organization.getErpOrgId()) && Long.parseLong(organization.getErpOrgId()) == warehousingReturnDetail.getOrgId()){
                    item.setErpOrgId(warehousingReturnDetail.getOrgId())
                            .setOrgId(organization.getOrganizationId());
                    flag = true;
                    break;
                }
            }
            for(Organization organization:organizations){
                if(StringUtils.isNotBlank(organization.getErpOrgId()) && Long.parseLong(organization.getErpOrgId()) == warehousingReturnDetail.getOrganizationId()){
                    item.setErpOrganizationId(warehousingReturnDetail.getOrganizationId())
                            .setErpOrganizationCode(warehousingReturnDetail.getOrganizationCode())
                            .setOrganizationId(organization.getOrganizationId())
                            .setOrganizationCode(organization.getOrganizationCode());
                    flag = true;
                    break;

                }
            }
            for(CompanyInfo companyInfo:companyInfos){
                if(Objects.equals(warehousingReturnDetail.getVendorId(),new Long(147))){
                    if(companyInfo.getCompanyName().equals("西安汉信自动识别技术有限公司")){
                        if(companyInfo.getErpVendorId() != null && Objects.equals(warehousingReturnDetail.getVendorId(),companyInfo.getErpVendorId())){
                            item.setVendorId(companyInfo.getCompanyId())
                                    .setVendorCode(companyInfo.getCompanyCode())
                                    .setErpVendorId(warehousingReturnDetail.getVendorId())
                                    .setErpVendorCode(warehousingReturnDetail.getVendorCode());
                            flag = true;
                            break;
                        }
                    }
                }


            }
            if(flag){
                index++;
                warehousingReturnDetailService.updateById(item);
            }

        }
        System.out.println(index);
    }*/
}
