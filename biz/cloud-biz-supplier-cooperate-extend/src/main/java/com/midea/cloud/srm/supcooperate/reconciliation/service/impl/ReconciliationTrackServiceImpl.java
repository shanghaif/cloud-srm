package com.midea.cloud.srm.supcooperate.reconciliation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.enums.OrgStatus;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.DateUtil;
import com.midea.cloud.common.utils.ExcelUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.supplier.SupplierClient;
import com.midea.cloud.srm.model.base.dict.dto.DictItemDTO;
import com.midea.cloud.srm.model.base.organization.entity.OrganizationUser;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseCurrency;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import com.midea.cloud.srm.model.supplier.info.entity.OrgInfo;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.Order;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.ReturnOrder;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.dto.ExcelReconciliationTrackReqDTO;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.dto.ReconciliationTrackDTO;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.dto.ReconciliationTrackRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.entry.*;
import com.midea.cloud.srm.supcooperate.order.service.IOrderService;
import com.midea.cloud.srm.supcooperate.order.service.IReturnOrderService;
import com.midea.cloud.srm.supcooperate.reconciliation.mapper.ReconciliationInvoiceMapper;
import com.midea.cloud.srm.supcooperate.reconciliation.mapper.ReconciliationPaymentMapper;
import com.midea.cloud.srm.supcooperate.reconciliation.mapper.ReconciliationTrackMapper;
import com.midea.cloud.srm.supcooperate.reconciliation.service.IReconciliationInvoiceService;
import com.midea.cloud.srm.supcooperate.reconciliation.service.IReconciliationPaymentService;
import com.midea.cloud.srm.supcooperate.reconciliation.service.IReconciliationTrackService;
import com.midea.cloud.srm.supcooperate.reconciliation.service.IWarehouseReceiptService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.List;

/**
 * <pre>
 *  对账单跟踪付款表 服务实现类
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/4/1 20:24
 *  修改内容:
 * </pre>
 */
@Service
public class ReconciliationTrackServiceImpl extends ServiceImpl<ReconciliationTrackMapper, ReconciliationTrack> implements IReconciliationTrackService {
    @Autowired
    private IReconciliationInvoiceService iReconciliationInvoiceService;
    @Autowired
    private IReconciliationPaymentService iReconciliationPaymentService;
    @Autowired
    private ReconciliationInvoiceMapper reconciliationInvoiceMapper;
    @Autowired
    private ReconciliationPaymentMapper reconciliationPaymentMapper;
    @Autowired
    private IOrderService iOrderService;
    @Autowired
    private IReturnOrderService iReturnOrderService;
    @Autowired
    private IWarehouseReceiptService iWarehouseReceiptService;

    @Autowired
    private ReconciliationTrackMapper reconciliationTrackMapper;

    @Autowired
    private BaseClient baseClient;
    @Autowired
    private SupplierClient supplierClient;

    @Transactional
    @Override
    public void saveBatchByExcel(List<ExcelReconciliationTrackReqDTO> list) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        List<OrganizationUser> organizations = loginAppUser.getOrganizationUsers();
        List<DictItemDTO> billTypeDictItems = baseClient.listAllByDictCode("RECONCILIATION_TRACK_BILL_TYPE");
        List<PurchaseCurrency> purchaseCurrencys = baseClient.listAllPurchaseCurrency();

        for(ExcelReconciliationTrackReqDTO eord:list){
            CompanyInfo companyInfo1 = new CompanyInfo();
            companyInfo1.setCompanyName(eord.getReconciliationTrack().getVendorName());
            companyInfo1.setStatus("APPROVED");
            CompanyInfo companyInfo = supplierClient.getCompanyInfoByParam(companyInfo1);
            if(companyInfo==null){
                eord.getReconciliationTrack().getErrorCell("vendorName").setComment("不存在名称为：'"+eord.getReconciliationTrack().getVendorName()+"'的已批准供应商");
            }else{
                eord.getReconciliationTrack().setVendorId(companyInfo.getCompanyId());
                eord.getReconciliationTrack().setVendorCode(companyInfo.getCompanyCode());
            }

            OrganizationUser organization = getOrganization(organizations,eord.getReconciliationTrack().getOrganizationName());
            if(organization==null){
                eord.getReconciliationTrack().getErrorCell("organizationName").setComment("不存在名称为：'"
                        +eord.getReconciliationTrack().getOrganizationName()+"'的有效组织");
            }else{
                eord.getReconciliationTrack().setOrganizationId(organization.getOrganizationId());
            }


            if(organization!=null&&companyInfo!=null){
                //检验这个供应商有没有这个合作组织
                OrgInfo orgInfo = supplierClient.getOrgInfoByOrgIdAndCompanyId(organization.getOrganizationId(),companyInfo.getCompanyId());
                if(!checkOrgEffective(orgInfo)){
                    eord.getReconciliationTrack().getErrorCell("organizationName").setComment("不存在名称为：'"+eord.getReconciliationTrack().getOrganizationName()+"'的有效组织");
                }
            }
            String rfq = getCurrencyCode(purchaseCurrencys,eord.getReconciliationTrack().getRfqSettlementCurrency());
            if(StringUtils.isBlank(rfq)){
                eord.getReconciliationTrack().getErrorCell("rfqSettlementCurrency").setComment("不存在名称为：'"+eord.getReconciliationTrack().getRfqSettlementCurrency()+"'的币种");
            }else{
                eord.getReconciliationTrack().setRfqSettlementCurrency(rfq);
            }

            String billTypeValue = eord.getReconciliationTrack().getBillType();
            String billType = getDictItemCode(billTypeDictItems,eord.getReconciliationTrack().getBillType());
            if(StringUtils.isBlank(billType)){
                eord.getReconciliationTrack().getErrorCell("billType").setComment("不存在名称为：'"+eord.getReconciliationTrack().getBillType()+"'的单据类型");
            }else{
                eord.getReconciliationTrack().setBillType(billType);
            }

            List<ReconciliationTrack> rts = this.checkExist(eord.getReconciliationTrack());
            if(!CollectionUtils.isEmpty(rts)){
                eord.getReconciliationTrack().getErrorCell("lineErrorContents").setComment("已存在供应商：'"+eord.getReconciliationTrack().getVendorName()+"'" +
                        ",单据类型:'"+billTypeValue+"'" +
                        ",单据编号:'"+eord.getReconciliationTrack().getBillCode()+"'" +
                        ",业务日期:'"+ DateFormatUtils.format(eord.getReconciliationTrack().getBusinessDate(),"yyyy-MM-dd") +"'的数据");
            }

            QueryWrapper<Order> wrapperOrder = new QueryWrapper<>();
            wrapperOrder.eq("ORDER_NUMBER",eord.getReconciliationTrack().getOrderNumber());
            Order order = iOrderService.getOne(wrapperOrder);
            if(order==null){
                eord.getReconciliationTrack().getErrorCell("orderNumber").setComment("订单号:"+eord.getReconciliationTrack().getOrderNumber()+"不存在");
            }else{
                eord.getReconciliationTrack().setOrderId(order.getOrderId());
            }

            QueryWrapper<WarehouseReceipt> wrapperWarehouseReceipt = new QueryWrapper<>();
            wrapperWarehouseReceipt.eq("WAREHOUSE_RECEIPT_NUMBER",eord.getReconciliationTrack().getWarehouseReceiptNumber());
            WarehouseReceipt warehouseReceipt = iWarehouseReceiptService.getOne(wrapperWarehouseReceipt);

            if(warehouseReceipt==null){
                eord.getReconciliationTrack().getErrorCell("warehouseReceiptNumber").setComment("入库单编号:"+eord.getReconciliationTrack().getWarehouseReceiptNumber()+"不存在");
            }else {
                eord.getReconciliationTrack().setWarehouseReceiptId(warehouseReceipt.getWarehouseReceiptId());
            }

            if(StringUtils.isNotBlank(eord.getReconciliationTrack().getReturnOrderNumber())){
                QueryWrapper<ReturnOrder> wrapperReturnOrder = new QueryWrapper<>();
                wrapperReturnOrder.eq("RETURN_ORDER_NUMBER",eord.getReconciliationTrack().getReturnOrderNumber());
                ReturnOrder returnOrder = iReturnOrderService.getOne(wrapperReturnOrder);

                if(returnOrder == null){
                    eord.getReconciliationTrack().getErrorCell("returnOrderNumber").setComment("退货单编号:"+eord.getReconciliationTrack().getReturnOrderNumber()+"不存在");
                }else {
                    eord.getReconciliationTrack().setReturnOrderId(returnOrder.getReturnOrderId());
                }
            }

            for(ReconciliationInvoice reconciliationInvoice:eord.getReconciliationInvoices()) {
                ReconciliationInvoice chexkReconciliationInvoice = reconciliationInvoiceMapper.selectOne(new QueryWrapper<ReconciliationInvoice>().eq("INVOICE_NUMBER",reconciliationInvoice.getInvoiceNumber()));
                if(chexkReconciliationInvoice!=null){
                    reconciliationInvoice.getErrorCell("invoiceNumber").setComment("发票单号:" + reconciliationInvoice.getInvoiceNumber() + "已存在");
                }

                reconciliationInvoice.setInvoiceId(IdGenrator.generate());
            }
            for(ReconciliationPayment reconciliationPayment:eord.getReconciliationPayments()) {
                ReconciliationPayment chexkReconciliationPayment = reconciliationPaymentMapper.selectOne(new QueryWrapper<ReconciliationPayment>().eq("PAYMENT_NOTE_NUMBER",reconciliationPayment.getPaymentNoteNumber()));
                if(chexkReconciliationPayment!=null){
                    reconciliationPayment.getErrorCell("paymentNoteNumber").setComment("付款单编号:" + reconciliationPayment.getPaymentNoteNumber() + "已存在");
                }
                reconciliationPayment.setPaymentId(IdGenrator.generate());
            }
        }
        if(ExcelUtil.getErrorCells(list).size()>0){
            return;
        }
        for(ExcelReconciliationTrackReqDTO eord:list){
            try{
                this.saveOrUpdate(eord.getReconciliationTrack(),eord.getReconciliationInvoices(),eord.getReconciliationPayments());
            }catch(Exception e){
                e.printStackTrace();
                eord.getReconciliationTrack().getErrorCell("lineErrorContents").setLineErrorContents("该行上传失败，请重新处理后再导入");
            }
        }
    }

    /**
     * 检验合作组织的有效性
     * @return
     */
    private static boolean checkOrgEffective(OrgInfo orgInfo)  {
        if(orgInfo == null||orgInfo.getStartDate()==null||!StringUtils.equals(orgInfo.getServiceStatus(), OrgStatus.EFFECTIVE.name())){
            return false;
        }
        LocalDate now = LocalDate.now();
        if(orgInfo.getStartDate().isAfter(now)){
            return false;
        }
        if(orgInfo.getEndDate()!=null&&(now.isAfter(orgInfo.getEndDate())||now.isEqual(orgInfo.getEndDate()))){
            return false;
        }
        return true;
    }

    /**
     * 检查数据是否重复
     * @param reconciliationTrack
     * @return
     */
    private List<ReconciliationTrack> checkExist(ReconciliationTrack reconciliationTrack) {
        ReconciliationTrack query = new ReconciliationTrack();
        query.setVendorCode(reconciliationTrack.getVendorCode());
        query.setBillType(reconciliationTrack.getBillType());
        query.setBillCode(reconciliationTrack.getBillCode());
        QueryWrapper<ReconciliationTrack> wrapper = new QueryWrapper<ReconciliationTrack>(query);
        String businessDateStr = DateUtil.parseDateToStr(reconciliationTrack.getBusinessDate(),DateUtil.YYYY_MM_DD);
        wrapper.ge("BUSINESS_DATE",businessDateStr);
        wrapper.le("BUSINESS_DATE",businessDateStr);
        return this.list(wrapper);
    }

    @Override
    public List<ReconciliationTrackDTO> listPage(ReconciliationTrackRequestDTO requestDTO) {
//        Map<String,BigDecimal> map = reconciliationTrackMapper.sum(requestDTO);
//        ReconciliationTrackDTO reconciliationTrackDTO = new ReconciliationTrackDTO();
//        ReconciliationInvoice reconciliationInvoice = new ReconciliationInvoice();
//
//        reconciliationInvoice.setInvoiceAmount(new BigDecimal(10));
//        reconciliationTrackDTO.setReconciliationInvoices(new ArrayList<>());
//        reconciliationTrackDTO.getReconciliationInvoices().add(reconciliationInvoice);
//
//        ReconciliationPayment reconciliationPayment = new ReconciliationPayment();
//        reconciliationPayment.setPaymentAmount(new BigDecimal(20));
//        reconciliationTrackDTO.setReconciliationPayments(new ArrayList<>());
//        reconciliationTrackDTO.getReconciliationPayments().add(reconciliationPayment);
//
//        PageUtil.startPage(requestDTO.getPageNum(), requestDTO.getPageSize()==null?null:requestDTO.getPageSize()-1);
//        List list = reconciliationTrackMapper.findList(requestDTO);
//        list.add(reconciliationTrackDTO);
        return reconciliationTrackMapper.findList(requestDTO);
    }

    @Override
    @Transactional
    public void saveOrUpdate(ReconciliationTrack reconciliationTrack, List<ReconciliationInvoice> reconciliationInvoices,
                             List<ReconciliationPayment> reconciliationPayments) {
        //开始保存或新增订单明细
        if (reconciliationTrack.getReconciliationTrackId() == null) {
            reconciliationTrack.setReconciliationTrackId(IdGenrator.generate());
        } else {
            QueryWrapper<ReconciliationInvoice> queryWrapperRI = new QueryWrapper<>();
            queryWrapperRI.eq("RECONCILIATION_TRACK_ID", reconciliationTrack.getReconciliationTrackId());
            reconciliationInvoiceMapper.delete(queryWrapperRI);

            QueryWrapper<ReconciliationPayment> queryWrapperRP = new QueryWrapper<>();
            queryWrapperRP.eq("RECONCILIATION_TRACK_ID", reconciliationTrack.getReconciliationTrackId());
            reconciliationPaymentMapper.delete(queryWrapperRP);

            reconciliationTrackMapper.updateById(reconciliationTrack);
        }
        this.saveOrUpdate(reconciliationTrack);

        //开始保存或新增订单明细
        reconciliationInvoices.forEach(item -> {
            item.setInvoiceId(IdGenrator.generate());
            item.setReconciliationTrackId(reconciliationTrack.getReconciliationTrackId());
        });
        iReconciliationInvoiceService.saveBatch(reconciliationInvoices);

        //开始保存或新增订单明细
        reconciliationPayments.forEach(item -> {
            item.setPaymentId(IdGenrator.generate());
            item.setReconciliationTrackId(reconciliationTrack.getReconciliationTrackId());
        });
        iReconciliationPaymentService.saveBatch(reconciliationPayments);
    }


    /**
     * 通过字典类型名称查询字典类型编号
     * @param dictItems
     * @param dictItemName
     * @return
     */
    private String getDictItemCode(List<DictItemDTO> dictItems, String dictItemName) {
        for(DictItemDTO dictItemDTO:dictItems){
            if(StringUtils.equals(dictItemName,dictItemDTO.getDictItemName())){
                return dictItemDTO.getDictItemCode();
            }
        }
        return null;
    }

    /**
     * 通过组织名称获取组织ID
     * @param organizations
     * @param organizationName
     * @return
     */
    private OrganizationUser getOrganization(List<OrganizationUser> organizations, String organizationName) {
        for(OrganizationUser organizationUser:organizations){
            if(StringUtils.equals(organizationName,organizationUser.getOrganizationName())){
                return organizationUser;
            }
        }
        return null;
    }

    /**
     * 通过币种名称查询币种的编码
     * @param purchaseCurrencys
     * @param currencyName
     * @return
     */
    private String getCurrencyCode(List<PurchaseCurrency> purchaseCurrencys, String currencyName) {
        for(PurchaseCurrency item:purchaseCurrencys){
            if(StringUtils.equals(currencyName,item.getCurrencyName())){
                return item.getCurrencyCode();
            }
        }
        return null;
    }
}
