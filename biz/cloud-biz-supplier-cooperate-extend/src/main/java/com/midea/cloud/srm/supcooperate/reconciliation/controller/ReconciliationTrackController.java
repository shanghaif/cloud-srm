package com.midea.cloud.srm.supcooperate.reconciliation.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.UserType;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.ExcelUtil;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.suppliercooperate.excel.ErrorCell;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.dto.ExcelReconciliationTrackReqDTO;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.dto.ReconciliationTrackDTO;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.dto.ReconciliationTrackRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.entry.ReconciliationInvoice;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.entry.ReconciliationPayment;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.entry.ReconciliationTrack;
import com.midea.cloud.srm.supcooperate.reconciliation.service.IReconciliationTrackService;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *  对账单跟踪 前端控制器
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/4/1 20:06
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/reconciliation/reconciliationTrack")
public class ReconciliationTrackController extends BaseController {

    private final static int HEAD_LINE_NUM = 2;//excel导入文件标题所占行数
    @Autowired
    private FileCenterClient fileCenterClient;

    @Autowired
    private IReconciliationTrackService iReconciliationTrackService;
    /**
     * 分页查询
     * @param
     * @return
     */
    @PostMapping("/listPage")
    public PageInfo<ReconciliationTrackDTO> listPage(@RequestBody ReconciliationTrackRequestDTO requestDTO) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        if(!StringUtils.equals(UserType.VENDOR.name(), loginAppUser.getUserType())
                &&!StringUtils.equals(UserType.BUYER.name(), loginAppUser.getUserType())){
            Assert.isTrue(false, "用户类型不存在");
        }
        if(StringUtils.equals(UserType.VENDOR.name(), loginAppUser.getUserType())) {
            requestDTO.setVendorId(loginAppUser.getCompanyId());
        }

        PageUtil.startPage(requestDTO.getPageNum(), requestDTO.getPageSize());
        return new PageInfo(iReconciliationTrackService.listPage(requestDTO));
    }

    /**
     * excel导入对账单跟踪
     *
     * @param file
     * @throws Exception
     */
    @PostMapping("/saveByExcel")
    public void saveByExcel(@RequestParam("file") MultipartFile file, Fileupload fileupload) {
        Assert.notNull(fileupload,"文件参数不能为空");
        Assert.notNull(fileupload.getSourceType(),"来源类型不能为空");
        Assert.notNull(fileupload.getUploadType(),"上传介质类型不能为空");
        Assert.notNull(fileupload.getFileModular(),"文件所属模块不能为空");
        Assert.notNull(fileupload.getFileFunction(),"文件所属功能不能为空");
        Assert.notNull(fileupload.getFileType(),"文件所属类型不能为空");
        Assert.notNull(file, "文件上传失败");

        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        Assert.isTrue(
                (StringUtils.equals("xls",suffix.toLowerCase())||StringUtils.equals("xlsx",suffix.toLowerCase())),
                "请上传excel文件");
        Assert.isTrue(new Double(147294)/(1024*1024)<1,"文件大小不能超过1M");

        Workbook rwb = null;
        try {
            rwb = Workbook.getWorkbook(file.getInputStream());

            List<ExcelReconciliationTrackReqDTO> list = checkExcelData(rwb);

            List<ErrorCell> errorCells = ExcelUtil.getErrorCells(list);
            if (errorCells.size() > 0) {
                ExcelUtil.uploadErrorFile(HEAD_LINE_NUM,fileCenterClient, rwb, errorCells, fileupload,
                        file.getName(), file.getOriginalFilename(), file.getContentType());
                return;
            }
            checkRepeatBillCode(list);
            checkInvoiceNumber(list);
            checkPaymentNoteNumber(list);
            errorCells = ExcelUtil.getErrorCells(list);
            if (errorCells.size() > 0) {
                ExcelUtil.uploadErrorFile(HEAD_LINE_NUM,fileCenterClient, rwb, errorCells, fileupload,
                        file.getName(), file.getOriginalFilename(), file.getContentType());
                return;
            }

            iReconciliationTrackService.saveBatchByExcel(list);

            errorCells = ExcelUtil.getErrorCells(list);
            if (errorCells.size() > 0) {
                ExcelUtil.uploadErrorFile(HEAD_LINE_NUM,fileCenterClient, rwb, errorCells, fileupload,
                        file.getName(), file.getOriginalFilename(), file.getContentType());
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        } finally {
            if (rwb != null) {
                rwb.close();
            }
        }

    }

    /**
     * 检查用户导入的单据编码是否重复
     */
    private void checkRepeatBillCode(List<ExcelReconciliationTrackReqDTO> list){
        Map<String,Integer> map = new HashMap();
        for(ExcelReconciliationTrackReqDTO item:list){
            String key = new StringBuffer("供应商:").append(item.getReconciliationTrack().getVendorName()).append(",")
                    .append("单据类型:").append(item.getReconciliationTrack().getBillType()).append(",")
                    .append("单据编号:").append(item.getReconciliationTrack().getBillCode()).append(",")
                    .append("业务日期:").append(DateFormatUtils.format(item.getReconciliationTrack().getBusinessDate(),"yyyy/MM/dd")).toString();
            if(map.containsKey(key)){
                item.getReconciliationTrack().getErrorCell("lineErrorContents").setComment(key+"重复");
            }else{
                map.put(key,1);
            }
        }
    }

    /**
     * 检查用户导入的发票单号是否重复
     */
    private void checkInvoiceNumber(List<ExcelReconciliationTrackReqDTO> list){
        Map<String,Integer> map = new HashMap();
        for(ExcelReconciliationTrackReqDTO item:list){
            for(ReconciliationInvoice item1:item.getReconciliationInvoices()){
                String invoiceNumber = item1.getInvoiceNumber();
                if(map.containsKey(invoiceNumber)){
                    item1.getErrorCell("invoiceNumber").setComment("发票单号重复");
                }else{
                    map.put(invoiceNumber,1);
                }
            }
        }
    }
    /**
     * 检查用户导入的付款单编号是否重复
     */
    private void checkPaymentNoteNumber(List<ExcelReconciliationTrackReqDTO> list){
        Map<String,Integer> map = new HashMap();
        for(ExcelReconciliationTrackReqDTO item:list){
            for(ReconciliationPayment item1:item.getReconciliationPayments()){
                String paymentNoteNumber = item1.getPaymentNoteNumber();
                if(map.containsKey(paymentNoteNumber)){
                    item1.getErrorCell("paymentNoteNumber").setComment("付款单编号重复");
                }else{
                    map.put(paymentNoteNumber,1);
                }
            }
        }
    }

    /**
     * 检验excel文件数据
     * @param workbook
     * @return execl对账单跟踪导入接收对象列表
     */
    private List checkExcelData(Workbook workbook){
        List list = new ArrayList();
        for(int sheetIndex=0;sheetIndex<workbook.getNumberOfSheets();sheetIndex++){
            Sheet sheet = workbook.getSheet(sheetIndex);
            //行数(表头的目录不需要，从1开始)
            for (int i = HEAD_LINE_NUM; i < sheet.getRows(); i++) {
                boolean isBlack = true;
                for (int c = 0; c < sheet.getColumns(); c++) {
                    if (StringUtils.isNotBlank(sheet.getCell(c, i).getContents())) {
                        isBlack = false;
                        break;
                    }
                }
                if (isBlack) {
                    break;
                }
                Cell cell = null;
                //列数
                for (int j = 0; j < sheet.getColumns(); j++) {
                    //获取第i行，第j列的值
                    cell = sheet.getCell(j, i);
                    checkCell(sheetIndex,cell,list);
                }
            }
        }
        return list;
    }

    /**
     * 校验单元格数据及格式
     * @param sheetIndex sheet下标
     * @param cell 单元格
     * @param list 对账单跟踪接收对象列表
     */
    private void checkCell(int sheetIndex, Cell cell, List<ExcelReconciliationTrackReqDTO> list){
        ExcelReconciliationTrackReqDTO eord = null;
        if(list.size()!=0){
            eord = list.get(list.size()-1);
        }
        switch (cell.getColumn()){
            case 0://供应商名称
                if(list.size()==0&&StringUtils.isBlank(cell.getContents())){

                    ExcelReconciliationTrackReqDTO newEord = new ExcelReconciliationTrackReqDTO();
                    newEord.setReconciliationTrack(new ReconciliationTrack());
                    list.add(newEord);

                    newEord.getReconciliationTrack().addErrorCell(StringUtils.isBlank(cell.getContents()),"vendorName",
                            new ErrorCell(sheetIndex, cell.getColumn(), cell.getRow(), "供应商不能为空"));
                }else if(list.size()==0){
                    ExcelReconciliationTrackReqDTO newEord = new ExcelReconciliationTrackReqDTO();
                    newEord.setReconciliationTrack(new ReconciliationTrack());

                    newEord.getReconciliationTrack().setVendorName(cell.getContents());
                    newEord.getReconciliationTrack().addErrorCell("vendorName", new ErrorCell(sheetIndex, cell.getColumn(), cell.getRow()));
                    list.add(newEord);
                } else if(list.size()!=0&&StringUtils.isNotBlank(cell.getContents())){
                    //当原来接收对象的供应商名称不为空， 供应商单元格有新的供应商名称，则开始用新接收对象接收对账单跟踪数据
                    ExcelReconciliationTrackReqDTO newEord = new ExcelReconciliationTrackReqDTO();
                    newEord.setReconciliationTrack(new ReconciliationTrack());
                    newEord.getReconciliationTrack().setVendorName(cell.getContents());
                    newEord.getReconciliationTrack().addErrorCell("vendorName", new ErrorCell(sheetIndex, cell.getColumn(), cell.getRow()));
                    list.add(newEord);
                }
                break;
            case 1://采购组织名称
                if(StringUtils.isBlank(eord.getReconciliationTrack().getOrganizationName())){
                    if(eord.getReconciliationInvoices()==null&&eord.getReconciliationPayments()==null){
                        eord.getReconciliationTrack().addErrorCell("organizationName","采购组织",cell,sheetIndex,true);
                        eord.getReconciliationTrack().setOrganizationName(cell.getContents());
                    }
                }
                break;
            case 2://单据类型
                if(StringUtils.isBlank(eord.getReconciliationTrack().getBillType())){
                    if(eord.getReconciliationInvoices()==null&&eord.getReconciliationPayments()==null){
                        eord.getReconciliationTrack().addErrorCell("billType","单据类型",cell,sheetIndex,true);
                        eord.getReconciliationTrack().setBillType(cell.getContents());
                    }
                }
                break;
            case 3://单据编码
                if(StringUtils.isBlank(eord.getReconciliationTrack().getBillCode())){
                    if(eord.getReconciliationInvoices()==null&&eord.getReconciliationPayments()==null){
                        eord.getReconciliationTrack().addErrorCell("billCode","单据编码",cell,sheetIndex,true);
                    }
                }
                break;
            case 4://业务日期
                if(eord.getReconciliationTrack().getBusinessDate()==null) {
                    if(eord.getReconciliationInvoices()==null&&eord.getReconciliationPayments()==null){
                        eord.getReconciliationTrack().addErrorCell("businessDate","业务日期",cell,sheetIndex,true);
                    }
                }
                break;
            case 5://币别
                if(StringUtils.isBlank(eord.getReconciliationTrack().getRfqSettlementCurrency())){
                    if(eord.getReconciliationInvoices()==null&&eord.getReconciliationPayments()==null){
                        eord.getReconciliationTrack().addErrorCell("rfqSettlementCurrency","币别",cell,sheetIndex,true);
                    }
                }
                break;
            case 6://价税合计
                if(eord.getReconciliationTrack().getPriceTaxSum()==null){
                    if(eord.getReconciliationInvoices()==null&&eord.getReconciliationPayments()==null){
                        eord.getReconciliationTrack().addErrorCell("priceTaxSum","价税合计",cell,sheetIndex,true);
                    }
                }
                break;
            case 7://订单编号
                if(StringUtils.isBlank(eord.getReconciliationTrack().getOrderNumber())){
                    if(eord.getReconciliationInvoices()==null&&eord.getReconciliationPayments()==null){
                        eord.getReconciliationTrack().addErrorCell("orderNumber","订单编号",cell,sheetIndex,true);
                    }
                }
                break;
            case 8://入库单编号
                if(StringUtils.isBlank(eord.getReconciliationTrack().getWarehouseReceiptNumber())){
                    if(eord.getReconciliationInvoices()==null&&eord.getReconciliationPayments()==null){
                        eord.getReconciliationTrack().addErrorCell("warehouseReceiptNumber","入库单编号",cell,sheetIndex,true);
                    }
                }
                break;
            case 9://退货单编号
                if(StringUtils.isBlank(eord.getReconciliationTrack().getReturnOrderNumber())){
                    if(eord.getReconciliationInvoices()==null&&eord.getReconciliationPayments()==null){
                        eord.getReconciliationTrack().addErrorCell("returnOrderNumber","退货单编号",cell,sheetIndex,false);
                    }
                }
                break;
            case 10://发票单据号
                if(StringUtils.isNotBlank(cell.getContents())){
                    if(eord.getReconciliationInvoices()==null){
                        eord.setReconciliationInvoices(new ArrayList<>());
                    }

                    ReconciliationInvoice reconciliationInvoice = new ReconciliationInvoice();
                    reconciliationInvoice.addErrorCell("invoiceNumber","发票单据号",cell,sheetIndex,true);

                    eord.getReconciliationInvoices().add(reconciliationInvoice);

                }
                break;
            case 11://发票数量
                //判断前面的发票单据号是否为空，为空：这里不为空，报错；不为空，这里为空，报错
                if(eord.getReconciliationInvoices()!=null
                        &&(StringUtils.isNotBlank(eord.getReconciliationInvoices().get(eord.getReconciliationInvoices().size()-1).getInvoiceNumber())
                        &&eord.getReconciliationInvoices().get(eord.getReconciliationInvoices().size()-1).getInvoiceNum()==null)){
                    //同一行前面有发票单据号
                    eord.getReconciliationInvoices().get(eord.getReconciliationInvoices().size()-1)
                            .addErrorCell("invoiceNum","发票数量",cell,sheetIndex,true);
                }else if(eord.getReconciliationInvoices()==null
                        ||(StringUtils.isNotBlank(eord.getReconciliationInvoices().get(eord.getReconciliationInvoices().size()-1).getInvoiceNumber()))
                        &&eord.getReconciliationInvoices().get(eord.getReconciliationInvoices().size()-1).getInvoiceNum()!=null){
                    //前面没有付款单号，但后边又有付款金额
                    if(StringUtils.isNotBlank(cell.getContents())){
                        eord.getReconciliationInvoices().get(eord.getReconciliationInvoices().size()-1)
                                .addErrorCell("invoiceNum",new ErrorCell(sheetIndex,cell.getColumn(),cell.getRow(),"门票数量缺失发票单据号"));
                    }
                }
                break;
            case 12://发票金额
                //判断前面的发票单据号是否为空，为空：这里不为空，报错；不为空，这里为空，报错
                if(eord.getReconciliationInvoices()!=null
                        &&(StringUtils.isNotBlank(eord.getReconciliationInvoices().get(eord.getReconciliationInvoices().size()-1).getInvoiceNumber())
                        &&eord.getReconciliationInvoices().get(eord.getReconciliationInvoices().size()-1).getInvoiceAmount()==null)){
                    //同一行前面有发票单据号
                    eord.getReconciliationInvoices().get(eord.getReconciliationInvoices().size()-1)
                            .addErrorCell("invoiceAmount","发票金额",cell,sheetIndex,true);
                }else if(eord.getReconciliationInvoices()==null
                        ||(StringUtils.isNotBlank(eord.getReconciliationInvoices().get(eord.getReconciliationInvoices().size()-1).getInvoiceNumber()))
                        &&eord.getReconciliationInvoices().get(eord.getReconciliationInvoices().size()-1).getInvoiceAmount()!=null){
                    //前面没有付款单号，但后边又有付款金额
                    if(StringUtils.isNotBlank(cell.getContents())){
                        eord.getReconciliationInvoices().get(eord.getReconciliationInvoices().size()-1)
                                .addErrorCell("invoiceAmount",new ErrorCell(sheetIndex,cell.getColumn(),cell.getRow(),"发票金额缺失发票单据号"));
                    }
                }
                break;

            case 13://付款单编号
                if(StringUtils.isNotBlank(cell.getContents())){
                    if(eord.getReconciliationPayments()==null){
                        eord.setReconciliationPayments(new ArrayList<>());
                    }

                    ReconciliationPayment reconciliationPayment = new ReconciliationPayment();
                    eord.getReconciliationPayments().add(reconciliationPayment);

                    eord.getReconciliationPayments().get(eord.getReconciliationPayments().size()-1)
                            .addErrorCell("paymentNoteNumber","付款单编号",cell,sheetIndex,false);
                }
                break;
            case 14://付款金额
                //判断前面的付款编号是否为空，为空：这里不为空，报错；不为空，这里为空，报错
                if(eord.getReconciliationPayments()!=null
                        &&(StringUtils.isNotBlank(eord.getReconciliationPayments().get(eord.getReconciliationPayments().size()-1).getPaymentNoteNumber())
                        &&eord.getReconciliationPayments().get(eord.getReconciliationPayments().size()-1).getPaymentAmount()==null)){
                    //同一行前面有付款单号
                    eord.getReconciliationPayments().get(eord.getReconciliationPayments().size()-1)
                            .addErrorCell("paymentAmount","付款金额",cell,sheetIndex,true);
                }else if(eord.getReconciliationPayments()==null
                        ||(StringUtils.isNotBlank(eord.getReconciliationPayments().get(eord.getReconciliationPayments().size()-1).getPaymentNoteNumber()))
                        &&eord.getReconciliationPayments().get(eord.getReconciliationPayments().size()-1).getPaymentAmount()!=null){
                    //前面没有付款单号，但后边又有付款金额
                    if(StringUtils.isNotBlank(cell.getContents())){
                        eord.getReconciliationPayments().get(eord.getReconciliationPayments().size()-1)
                                .addErrorCell("paymentAmount",new ErrorCell(sheetIndex,cell.getColumn(),cell.getRow(),"付款金额缺失付款单编号"));
                    }
                }
                if(eord.getReconciliationTrack().getErrorCell("lineErrorContents")==null){
                    eord.getReconciliationTrack().addErrorCell("lineErrorContents", new ErrorCell(sheetIndex, cell.getColumn()+1, cell.getRow()));
                }
                eord.getReconciliationPayments().get(eord.getReconciliationPayments().size()-1).addErrorCell("lineErrorContents", new ErrorCell(sheetIndex, cell.getColumn()+1, cell.getRow()));
                eord.getReconciliationInvoices().get(eord.getReconciliationInvoices().size()-1).addErrorCell("lineErrorContents", new ErrorCell(sheetIndex, cell.getColumn()+1, cell.getRow()));
                break;
        }
    }
}
