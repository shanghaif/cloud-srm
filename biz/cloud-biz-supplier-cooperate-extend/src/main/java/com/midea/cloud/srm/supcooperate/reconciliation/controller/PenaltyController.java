package com.midea.cloud.srm.supcooperate.reconciliation.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.entry.Penalty;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.entry.PenaltyRequestDTO;
import com.midea.cloud.srm.supcooperate.reconciliation.service.IPenaltyService;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <pre>
 *  罚扣款单表 前端控制器
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/4/7 15:08
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/reconciliation/penalty")
public class PenaltyController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PenaltyController.class);

    private final static int HEAD_LINE_NUM = 1;//excel导入文件标题所占行数
    @Autowired
    private FileCenterClient fileCenterClient;

    @Autowired
    private IPenaltyService iPenaltyService;

    public static void main(String[] args) {

        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        try{
            Date d = new Date(f.parse("2010-01-01").getTime()+24*3600*1000);
            System.out.println(f.format(d));
        }catch (Exception e){
            LOGGER.error("日期格式错误：" + e.toString());
        }
    }
    /**
     * 分页查询
     * @param
     * @return
     */
    @PostMapping("/listPage")
    public PageInfo<Penalty> listPage(@RequestBody PenaltyRequestDTO requestDTO) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        if(!StringUtils.equals(UserType.VENDOR.name(), loginAppUser.getUserType())
                &&!StringUtils.equals(UserType.BUYER.name(), loginAppUser.getUserType())){
            Assert.isTrue(false, "用户类型不存在");
        }
        String penaltyNumber = requestDTO.getPenaltyNumber();
        String invoiceNumber = requestDTO.getInvoiceNumber();
        requestDTO.setPenaltyNumber(null);
        requestDTO.setInvoiceNumber(null);
        QueryWrapper<Penalty> queryWrapper = new QueryWrapper<>(requestDTO);
        queryWrapper.ge(!StringUtils.isEmpty(requestDTO.getStartPenaltyTime()),"PENALTY_TIME",requestDTO.getStartPenaltyTime());
        if(StringUtils.isNotBlank(requestDTO.getEndPenaltyTime())){
            SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
            try{
                Date d = new Date(f.parse(requestDTO.getEndPenaltyTime()).getTime()+24*3600*1000);
                queryWrapper.lt("PENALTY_TIME", f.format(d));
            }catch (Exception e){
                LOGGER.error("日期格式错误：" + e.toString());
            }
        }

        if(StringUtils.equals(UserType.VENDOR.name(),loginAppUser.getUserType())){
            queryWrapper.eq("VENDOR_ID",loginAppUser.getCompanyId());
        }
        queryWrapper.like(StringUtils.isNotBlank(penaltyNumber),"PENALTY_NUMBER",penaltyNumber);
        queryWrapper.like(StringUtils.isNotBlank(invoiceNumber),"INVOICE_NUMBER",invoiceNumber);

        queryWrapper.orderByDesc("CREATION_DATE");

        PageUtil.startPage(requestDTO.getPageNum(), requestDTO.getPageSize());
        return new PageInfo(iPenaltyService.list(queryWrapper));
    }

    /**
     * excel导入罚扣款
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

            List<Penalty> list = checkExcelData(rwb);

            List<ErrorCell> errorCells = ExcelUtil.getErrorCells(list);
            if (errorCells.size() > 0) {
                ExcelUtil.uploadErrorFile(HEAD_LINE_NUM,fileCenterClient, rwb, errorCells, fileupload,
                        file.getName(), file.getOriginalFilename(), file.getContentType());
                return;
            }

            iPenaltyService.saveBatchByExcel(list);

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
     * 检验excel文件数据
     * @param workbook
     * @return execl罚扣款导入接收对象列表
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
     * @param list 罚扣款接收对象列表
     */
    private void checkCell(int sheetIndex, Cell cell, List<Penalty> list){
        Penalty eord = null;
        if(list.size()!=0){
            eord = list.get(list.size()-1);
        }
        switch (cell.getColumn()){
            case 0://供应商名称
                Penalty newEord = new Penalty();
                newEord.addErrorCell("vendorName","供应商",cell,sheetIndex,true);

                list.add(newEord);
                break;
            case 1://采购组织
                eord.addErrorCell("organizationName","采购组织",cell,sheetIndex,true);
                break;
            case 2://罚扣款类型
                eord.addErrorCell("penaltyType","罚扣款类型",cell,sheetIndex,true);
                break;
            case 3://罚扣款描述
                eord.addErrorCell("penaltyCommons","罚扣款描述",cell,sheetIndex,true);
                break;
            case 4://罚扣款日期
                eord.addErrorCell("penaltyTime","罚扣款日期",cell,sheetIndex,true);
                break;
            case 5://币种
                eord.addErrorCell("rfqSettlementCurrency","币种",cell,sheetIndex,true);
                break;
            case 6://税控发票号
                eord.addErrorCell("invoiceNumber","税控发票号",cell,sheetIndex,false);
                break;
            case 7://罚扣金额
                eord.addErrorCell("penaltyAmount","罚扣金额",cell,sheetIndex,true);
                eord.addErrorCell("lineErrorContents", new ErrorCell(sheetIndex, cell.getColumn()+1, cell.getRow()));
                break;
        }
    }
}
