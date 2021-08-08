package com.midea.cloud.srm.supcooperate.reconciliation.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.UserType;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.ExcelUtil;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.suppliercooperate.excel.ErrorCell;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.dto.AccountsPayableRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.entry.AccountsPayable;
import com.midea.cloud.srm.supcooperate.reconciliation.service.IAccountsPayableService;
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
 *  应付款明细表 前端控制器
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/4/2 21:07
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/reconciliation/accountsPayable")
public class AccountsPayableController extends BaseController {

    private final static int HEAD_LINE_NUM = 1;//excel导入文件标题所占行数
    @Autowired
    private FileCenterClient fileCenterClient;

    @Autowired
    IAccountsPayableService iAccountsPayableService;

    /**
     * 分页查询
     * @param
     * @return
     */
    @PostMapping("/listPage")
    public PageInfo<AccountsPayable> listPage(@RequestBody AccountsPayableRequestDTO requestDTO) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        if(!StringUtils.equals(UserType.VENDOR.name(), loginAppUser.getUserType())
                &&!StringUtils.equals(UserType.BUYER.name(), loginAppUser.getUserType())){
            Assert.isTrue(false, "用户类型不存在");
        }
        if(StringUtils.equals(UserType.VENDOR.name(), loginAppUser.getUserType())){
            requestDTO.setVendorId(loginAppUser.getCompanyId());
        }
        AccountsPayable accountsPayable = iAccountsPayableService.getSum(requestDTO);
        PageInfo<AccountsPayable> pageInfo = new PageInfo<AccountsPayable>(iAccountsPayableService.listPage(requestDTO));
        if(accountsPayable!=null){
            pageInfo.getList().add(accountsPayable);
        }
        return pageInfo;
    }

    /**
     * excel导入应付款明细
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

            List<AccountsPayable> list = checkExcelData(rwb);

            List<ErrorCell> errorCells = ExcelUtil.getErrorCells(list);
            if (errorCells.size() > 0) {
                ExcelUtil.uploadErrorFile(HEAD_LINE_NUM,fileCenterClient, rwb, errorCells, fileupload,
                        file.getName(), file.getOriginalFilename(), file.getContentType());
                return;
            }
            checkRepeatBillCode(list);
            errorCells = ExcelUtil.getErrorCells(list);
            if (errorCells.size() > 0) {
                ExcelUtil.uploadErrorFile(HEAD_LINE_NUM,fileCenterClient, rwb, errorCells, fileupload,
                        file.getName(), file.getOriginalFilename(), file.getContentType());
                return;
            }

            iAccountsPayableService.saveBatchByExcel(list);
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
    private void checkRepeatBillCode(List<AccountsPayable> list){
        Map<String,Integer> map = new HashMap();
        for(AccountsPayable item:list){
            String key = new StringBuffer("供应商:").append(item.getVendorName()).append(",")
                    .append("单据类型:").append(item.getBillType()).append(",")
                    .append("单据编号:").append(item.getBillCode()).append(",")
                    .append("业务日期:").append(DateFormatUtils.format(item.getBusinessDate(),"yyyy/MM/dd")).toString();
            if(map.containsKey(key)){
                item.getErrorCell("lineErrorContents").setComment(key+"重复");
            }else{
                map.put(key,1);
            }
        }
    }

    /**
     * 检验excel文件数据
     * @param workbook
     * @return execl应付款明细导入接收对象列表
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
     * @param list 应付款明细接收对象列表
     */
    private void checkCell(int sheetIndex, Cell cell, List<AccountsPayable> list){
        AccountsPayable eord = null;
        if(list.size()!=0){
            eord = list.get(list.size()-1);
        }
        switch (cell.getColumn()){
            case 0://供应商名称
                AccountsPayable newEord = new AccountsPayable();
                newEord.setLineNum(cell.getRow());
                newEord.addErrorCell("vendorName","供应商",cell,sheetIndex,true);

                list.add(newEord);
                break;
            case 1://采购组织名称
                eord.addErrorCell("organizationName","采购组织",cell,sheetIndex,true);
                break;
            case 2://业务描述
                eord.setBusinessDescription(cell.getContents());
                break;
            case 3://单据类型
                eord.addErrorCell("billType","单据类型",cell,sheetIndex,true);
                break;
            case 4://单据编号
                eord.addErrorCell("billCode","单据编号",cell,sheetIndex,true);
                break;
            case 5://业务日期
                eord.addErrorCell("businessDate","业务日期",cell,sheetIndex,true);
                break;
            case 6://币别
                eord.addErrorCell("rfqSettlementCurrency","币别",cell,sheetIndex,true);
                break;
            case 7://初期余额
                eord.addErrorCell("initialBalance","初期余额",cell,sheetIndex,true);
                break;
            case 8://本期应付
                eord.addErrorCell("currentShouldPay","本期应付",cell,sheetIndex,true);
                break;
            case 9://本期付款
                if(StringUtils.isNotBlank(cell.getContents())){
                    eord.addErrorCell("currentPay","本期付款",cell,sheetIndex,false);
                }
                break;
            case 10://本期冲销额
                eord.addErrorCell("chargeAgainst","本期冲销额",cell,sheetIndex,true);
                break;
            case 11://期末余额
                eord.addErrorCell("endingBalance","期末余额",cell,sheetIndex,true);

                if(eord.getEndingBalance()!=null&&eord.getInitialBalance()!=null&&eord.getCurrentShouldPay()!=null&&eord.getChargeAgainst()!=null){
                    if(eord.getEndingBalance().compareTo(eord.getInitialBalance()
                            .add(eord.getCurrentShouldPay()).subtract(eord.getChargeAgainst()))!=0){
                        eord.addErrorCell("endingBalance",new ErrorCell(sheetIndex,cell.getColumn(),cell.getRow(),"期末余额!=期初余额+本期应付-本期冲销"));
                    }
                }
                eord.addErrorCell("lineErrorContents", new ErrorCell(sheetIndex, cell.getColumn()+1, cell.getRow()));
                break;
        }
    }
}
