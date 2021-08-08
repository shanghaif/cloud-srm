package com.midea.cloud.srm.supcooperate.order.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.midea.cloud.common.enums.order.DeliveryNoteStatus;
import com.midea.cloud.srm.model.suppliercooperate.deliverynote.dto.*;
import com.midea.cloud.srm.model.suppliercooperate.deliverynote.entry.DeliveryNote;
import com.midea.cloud.srm.supcooperate.order.service.IDeliveryNoteService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.UserType;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.ExcelUtil;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.suppliercooperate.deliverynote.entry.DeliveryNoteDetail;
import com.midea.cloud.srm.model.suppliercooperate.excel.ErrorCell;
import com.midea.cloud.srm.supcooperate.order.service.IDeliveryNoteDetailService;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 * <pre>
 *  送货单明细表 前端控制器
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/27 14:54
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/order/deliveryNoteDetail")
public class DeliveryNoteDetailController extends BaseController {

    private final static int HEAD_LINE_NUM = 1;//excel导入文件标题所占行数
    @Autowired
    private FileCenterClient fileCenterClient;

    @Autowired
    private IDeliveryNoteService iDeliveryNoteService;

    @Autowired
    private IDeliveryNoteDetailService iDeliveryNoteDetailService;

    /**
     * 根据主键查询送货单明细
     * @param ids
     * @return
     */
    @PostMapping("/queryDeliveryNoteDetailByIds")
    public List<DeliveryNoteDetail> queryDeliveryNoteDetailByIds(@RequestBody List<Long> ids) {
        return iDeliveryNoteDetailService.listByIds(ids);
    }

    /**
     * 采购商分页查询送货单明细
     * @param deliveryNoteRequestDTO 送货数据请求传输对象
     * @return
     */
    @PostMapping("/deliveryNoteDetailPage")
    public PageInfo<DeliveryNoteDetailDTO> deliveryNoteDetailPage(@RequestBody DeliveryNoteRequestDTO deliveryNoteRequestDTO) {
        PageUtil.startPage(deliveryNoteRequestDTO.getPageNum(), 1000);
        return new PageInfo<DeliveryNoteDetailDTO>(iDeliveryNoteDetailService.listDeliveryNoteDetail(deliveryNoteRequestDTO));
    }

    /**
     * 编辑送货单明细
     * @param deliveryNoteRequestDTO 送货数据请求传输对象
     * @return
     */
    @PostMapping("/deliveryNoteEditPage")
    public PageInfo<DeliveryNoteDetailDTO> deliveryNoteEditPage(@RequestBody DeliveryNoteRequestDTO deliveryNoteRequestDTO) {
        PageUtil.startPage(deliveryNoteRequestDTO.getPageNum(), deliveryNoteRequestDTO.getPageSize());
        return new PageInfo<DeliveryNoteDetailDTO>(iDeliveryNoteDetailService.listPage(deliveryNoteRequestDTO));
    }

    /**
     * 分页查询送货单明细
     * @param deliveryNoteRequestDTO 送货数据请求传输对象
     * @return
     */
    @PostMapping("/listPage")
    public PageInfo<DeliveryNoteDetailDTO> listPage(@RequestBody DeliveryNoteRequestDTO deliveryNoteRequestDTO) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        if(!StringUtils.equals(UserType.VENDOR.name(), loginAppUser.getUserType())
                &&!StringUtils.equals(UserType.BUYER.name(), loginAppUser.getUserType())){
            Assert.isTrue(false, "用户类型不存在");
        }
        if(StringUtils.equals(UserType.VENDOR.name(), loginAppUser.getUserType())){
            deliveryNoteRequestDTO.setVendorId(loginAppUser.getCompanyId());
        }
        PageUtil.startPage(deliveryNoteRequestDTO.getPageNum(), deliveryNoteRequestDTO.getPageSize());
        return new PageInfo<DeliveryNoteDetailDTO>(iDeliveryNoteDetailService.listPage(deliveryNoteRequestDTO));
    }

    /**
     * 供应商确认发货 送货单状态变成已发货，同时回写采购订单已送货数量
     * @param param
     */
    @PostMapping("/confirmDelivery")
    public void confirmDelivery(@RequestBody DeliveryNoteSaveRequestDTO param) {
        LoginAppUser user=AppUserUtil.getLoginAppUser();
        Assert.isTrue(StringUtils.equals(user.getUserType(), UserType.VENDOR.name()), "送货单是由供应商确认发货");

        DeliveryNote deliveryNote = param.getDeliveryNote();
        Long deliveryNoteId = deliveryNote.getDeliveryNoteId();
        deliveryNote = iDeliveryNoteService.getById(deliveryNoteId);
        Assert.notNull(deliveryNoteId, "送货单ID不能为空");
        Assert.isTrue(StringUtils.equals(DeliveryNoteStatus.CREATE.name(),
                deliveryNote.getDeliveryNoteStatus()), "送货单只有拟定状态才能确认发货");
        List<DeliveryNoteDetailDTO> detailList = param.getDetailList();
        for (DeliveryNoteDetail deliveryNoteDetail: detailList) {
            Long deliveryNoteDetailId = deliveryNoteDetail.getDeliveryNoteDetailId();
            String batchNum = deliveryNoteDetail.getCeeaBatchNum();
            BigDecimal deliveryQuantity = deliveryNoteDetail.getDeliveryQuantity();
            if(batchNum!=null && !deliveryQuantity.equals(null)) {
                iDeliveryNoteDetailService.confirmDeliveryNoteDetailStatus(deliveryNoteDetailId, batchNum, deliveryQuantity);
            }
        }
        iDeliveryNoteService.confirmDeliveryNoteStatus(deliveryNoteId);
    }

    /**
     * 供应商取消发货 送货单状态变成待取消，待取消状态不可编辑
     * @param param
     */
    @PostMapping("/cancelDelivery")
    public void cancelDelivery(@RequestBody DeliveryNoteSaveRequestDTO param) {
        LoginAppUser user=AppUserUtil.getLoginAppUser();
        Assert.isTrue(StringUtils.equals(user.getUserType(), UserType.VENDOR.name()), "送货单是由供应商取消发货");

        DeliveryNote deliveryNote = param.getDeliveryNote();
        Long deliveryNoteId = deliveryNote.getDeliveryNoteId();
        deliveryNote = iDeliveryNoteService.getById(deliveryNoteId);
        Assert.notNull(deliveryNoteId, "送货单ID不能为空");
        Assert.isTrue(StringUtils.equals(DeliveryNoteStatus.DELIVERED.name(),
                deliveryNote.getDeliveryNoteStatus()), "只有已发货才能取消发货");
        iDeliveryNoteService.cancelDeliveryNoteStatus(deliveryNoteId);
    }
    /**
     * 供应商删除送货单 只有拟定状态可以删除
     * @param param
     */
    @PostMapping("/deleteDeliveryNote")
    public void deleteDeliveryNote(@RequestBody DeliveryNoteSaveRequestDTO param) {
        DeliveryNote deliveryNote = param.getDeliveryNote();
        Long deliveryNoteId = deliveryNote.getDeliveryNoteId();
        deliveryNote = iDeliveryNoteService.getById(deliveryNoteId);
        Assert.notNull(deliveryNoteId, "送货单不存在");
        Assert.isTrue(StringUtils.equals(DeliveryNoteStatus.CREATE.name(),
                deliveryNote.getDeliveryNoteStatus()), "只有拟定状态才能删除");
        List<DeliveryNoteDetailDTO> detaildtoList = param.getDetailList();
        for (DeliveryNoteDetail deliveryNoteDetail: detaildtoList) {
            Long deliveryNoteDetailId = deliveryNoteDetail.getDeliveryNoteDetailId();
            Assert.notNull(deliveryNoteDetailId, "送货单明细不存在");
            iDeliveryNoteDetailService.getBaseMapper().deleteById(deliveryNoteDetailId);
        }
        iDeliveryNoteService.getBaseMapper().deleteById(deliveryNoteId);
    }

    /**
     * 分页查询收货明细
     * @param receiveRequestDTO 送货数据请求传输对象
     * @return
     */
    @PostMapping("/receiveListPage")
    public PageInfo<DeliveryNoteDetailReceiveDTO> receiveListPage(@RequestBody ReceiveRequestDTO receiveRequestDTO) {
//        Assert.notNull(deliveryNoteRequestDTO.getDeliveryNoteId(),"送货单ID不能为空");
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        if(!StringUtils.equals(UserType.VENDOR.name(), loginAppUser.getUserType())
                &&!StringUtils.equals(UserType.BUYER.name(), loginAppUser.getUserType())){
            Assert.isTrue(false, "用户类型不存在");
        }
        if(StringUtils.equals(UserType.VENDOR.name(), loginAppUser.getUserType())){
            receiveRequestDTO.setVendorId(loginAppUser.getCompanyId());
        }

        PageUtil.startPage(receiveRequestDTO.getPageNum(), receiveRequestDTO.getPageSize());
        return new PageInfo<DeliveryNoteDetailReceiveDTO>(iDeliveryNoteDetailService.receiveListPage(receiveRequestDTO));
    }

    /**
     * excel导入收货明细数量
     *
     * @param file
     * @param fileupload
     * @throws Exception
     */
    @PostMapping("/updateByExcel")
    public void updateByExcel(@RequestParam("file") MultipartFile file, Fileupload fileupload) {
        Assert.notNull(fileupload,"文件参数不能为空");
        Assert.notNull(fileupload.getSourceType(),"来源类型不能为空");
        Assert.notNull(fileupload.getUploadType(),"上传介质类型不能为空");
        Assert.notNull(fileupload.getFileModular(),"文件所属模块不能为空");
        Assert.notNull(fileupload.getFileFunction(),"文件所属功能不能为空");
        Assert.notNull(fileupload.getFileType(),"文件所属类型不能为空");
        Assert.notNull(file, "文件上传失败");

        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        Assert.isTrue(
                (org.apache.commons.lang3.StringUtils.equals("xls",suffix.toLowerCase())|| org.apache.commons.lang3.StringUtils.equals("xlsx",suffix.toLowerCase())),
                "请上传excel文件");
        Assert.isTrue(new Double(147294)/(1024*1024)<1,"文件大小不能超过1M");

        Workbook rwb = null;
        try {
            rwb = Workbook.getWorkbook(file.getInputStream());

            List<DeliveryNoteDetail> list = checkExcelData(rwb);

            List<ErrorCell> errorCells = ExcelUtil.getErrorCells(list);
            if (errorCells.size() > 0) {
                ExcelUtil.uploadErrorFile(HEAD_LINE_NUM,fileCenterClient, rwb, errorCells, fileupload,
                        file.getName(), file.getOriginalFilename(), file.getContentType());
                return;
            }

            iDeliveryNoteDetailService.updateBatchByExcel(list);

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
        }
    }

    /**
     * 检验excel文件数据
     * @param workbook
     * @return execl订单导入接收对象列表
     */
    private List checkExcelData(Workbook workbook){
        List list = new ArrayList();
        for(int sheetIndex=0;sheetIndex<workbook.getNumberOfSheets();sheetIndex++){
            Sheet sheet = workbook.getSheet(sheetIndex);
            //行数(表头的目录不需要，从1开始)
            for (int i = HEAD_LINE_NUM; i < sheet.getRows(); i++) {
                boolean isBlack = true;
                for (int c = 0; c < sheet.getColumns(); c++) {
                    if (org.apache.commons.lang3.StringUtils.isNotBlank(sheet.getCell(c, i).getContents())) {
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
     * @param list 订单接收对象列表
     */
    private void checkCell(int sheetIndex, Cell cell, List<DeliveryNoteDetail> list){
        DeliveryNoteDetail eord = null;
        if(list.size()!=0){
            eord = list.get(list.size()-1);
        }
        switch (cell.getColumn()){
            case 0://ID
                DeliveryNoteDetail newEord = new DeliveryNoteDetail();
                newEord.addErrorCell("deliveryNoteDetailId","ID",cell,sheetIndex,true);
                list.add(newEord);
                break;
            case 8://实退数量
                eord.addErrorCell("actualReturnedNum","实退数量",cell,sheetIndex,false);
                break;
            case 9://补货数量
                eord.addErrorCell("replenishNum","补货数量",cell,sheetIndex,false);
                break;
            case 10://扣款数量
                eord.addErrorCell("deductionNum","扣款数量",cell,sheetIndex,false);
                break;
            case 12://收货数量
                eord.addErrorCell("receivedNum","收货数量",cell,sheetIndex,false);
                break;
            case 13://不良数量
                eord.addErrorCell("badNum","不良数量",cell,sheetIndex,false);
                break;
            case 14://不良原因
                if(StringUtils.isNotBlank(cell.getContents())){
                    eord.setBadReason(cell.getContents());
                }
                break;
            case 15://差异数量
                eord.addErrorCell("differenceNum","差异数量",cell,sheetIndex,false);
                eord.addErrorCell("lineErrorContents", new ErrorCell(sheetIndex, cell.getColumn()+1, cell.getRow()));
                break;
        }
    }

    /**
     * 创建入库单-查询送货单明细
     * @param deliveryNoteRequestDTO
     * @return
     */
    @PostMapping("/listInWarehouseReceipt")
    public PageInfo<DeliveryNoteDetailDTO> listInWarehouseReceipt(@RequestBody DeliveryNoteRequestDTO deliveryNoteRequestDTO){
        return iDeliveryNoteDetailService.listInWarehouseReceipt(deliveryNoteRequestDTO);
    }

    /**
     * 创建退货单-查询送货单明细
     * @param deliveryNoteRequestDTO
     * @return
     */
    @PostMapping("/listInReturnOrder")
    PageInfo<DeliveryNoteDetailDTO> listInReturnOrder(@RequestBody DeliveryNoteRequestDTO deliveryNoteRequestDTO){
        return iDeliveryNoteDetailService.listInReturnOrder(deliveryNoteRequestDTO);
    }
}
