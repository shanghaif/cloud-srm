package com.midea.cloud.srm.supcooperate.plan.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.UserType;
import com.midea.cloud.common.enums.order.OrderForecastStatus;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.ExcelUtil;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.suppliercooperate.excel.ErrorCell;
import com.midea.cloud.srm.model.suppliercooperate.orderforecast.entry.OrderForecast;
import com.midea.cloud.srm.supcooperate.plan.service.IOrderForecastService;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.WriteException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *  三月滚动预测表 前端控制器
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/4/14 20:02
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/plan/orderForecast")
public class OrderForecastController extends BaseController {
    private final static int HEAD_LINE_NUM = 1;//excel导入文件标题所占行数

    @Autowired
    private IOrderForecastService iOrderForecastService;
    @Autowired
    private FileCenterClient fileCenterClient;

    /**
     * 分页查询
     * @param
     * @return
     */
    @PostMapping("/listPage")
    public PageInfo<OrderForecast> listPage(@RequestBody OrderForecast orderForecast) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        if(!StringUtils.equals(UserType.VENDOR.name(), loginAppUser.getUserType())
                &&!StringUtils.equals(UserType.BUYER.name(), loginAppUser.getUserType())){
            Assert.isTrue(false, "用户类型不存在");
        }
        if(StringUtils.isNotBlank(orderForecast.getPlanMonth())){
            orderForecast.setPlanMonth(orderForecast.getPlanMonth().replace("-",""));
        }
        String materialName = orderForecast.getMaterialName();
        String materialCode = orderForecast.getMaterialCode();
        orderForecast.setMaterialName(null);
        orderForecast.setMaterialCode(null);
        QueryWrapper<OrderForecast> queryWrapper = new QueryWrapper<OrderForecast>(orderForecast);
        if(StringUtils.equals(UserType.VENDOR.name(), loginAppUser.getUserType())){
            queryWrapper.eq("VENDOR_ID",loginAppUser.getCompanyId());
            queryWrapper.ne("STATUS", OrderForecastStatus.CREATE.name());
        }
        queryWrapper.like(StringUtils.isNotBlank(materialName),"MATERIAL_NAME",materialName);
        queryWrapper.like(StringUtils.isNotBlank(materialCode),"MATERIAL_CODE",materialCode);

        queryWrapper.orderByDesc("PLAN_MONTH","MATERIAL_CODE","CREATION_DATE");
        return new PageInfo<OrderForecast>(iOrderForecastService.list(queryWrapper));
    }

    /**
     * 采购商批量发布计划
     * @param ids 计划ids
     */
    @PostMapping("/publishBatch")
    public void publishBatch(@RequestBody List<Long> ids) {
        iOrderForecastService.publishBatch(ids);
    }

    /**
     * 供应商批量确认计划
     * @param ids 计划ids
     */
    @PostMapping("/comfirmBatch")
    public void comfirmBatch(@RequestBody List<Long> ids) {
        iOrderForecastService.comfirmBatch(ids);
    }


    /**
     * excel导入三月滚动预测
     *
     * @param file
     * @throws Exception
     */
    @PostMapping("/saveByExcel")
    public void saveByExcel(@RequestParam("file") MultipartFile file, Fileupload fileupload) throws Exception {
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

        Workbook rwb = Workbook.getWorkbook(file.getInputStream());

        List<ErrorCell> errorCells = new ArrayList();
        List<OrderForecast> list = checkExcelData(rwb,errorCells);

        if (errorCells.size() == 0) {
            errorCells = iOrderForecastService.saveBatchByExcel(list);
        }
        if (errorCells.size() > 0) {
            ExcelUtil.uploadErrorFile(HEAD_LINE_NUM,fileCenterClient, rwb, errorCells, fileupload,
                    file.getName(), file.getOriginalFilename(), file.getContentType());
        }
    }

    /**
     * 检验excel文件数据
     * @param rwb
     * @return execl三月滚动预测导入接收对象列表
     */
    private List checkExcelData(Workbook rwb, List<ErrorCell> errorCells)throws WriteException {
        List list = new ArrayList();
        for(int sheetIndex=0;sheetIndex<rwb.getNumberOfSheets();sheetIndex++){
            Sheet sheet = rwb.getSheet(sheetIndex);
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
                    checkCell(sheetIndex,cell,list,errorCells);
                }
            }
        }
        return list;
    }

    /**
     * 校验单元格数据及格式
     * @param sheetIndex sheet下标
     * @param cell 单元格
     * @param list 三月滚动预测接收对象列表
     */
    private void checkCell(int sheetIndex, Cell cell, List<OrderForecast> list,List<ErrorCell> errorCells){
        OrderForecast eord = null;
        if(list.size()!=0){
            eord = list.get(list.size()-1);
        }
        switch (cell.getColumn()){
            case 0://供应商
                ExcelUtil.addErrorCell(StringUtils.isBlank(cell.getContents()), errorCells,
                        new ErrorCell(sheetIndex, cell.getColumn(), cell.getRow(), "供应商不能为空"));
                OrderForecast newEord = new OrderForecast();
                newEord.setVendorName(cell.getContents());
                newEord.addErrorCell("vendorName", new ErrorCell(sheetIndex, cell.getColumn(), cell.getRow()));
                list.add(newEord);
                break;
            case 1://采购组织
                ExcelUtil.addErrorCell(StringUtils.isBlank(cell.getContents()), errorCells,
                        new ErrorCell(sheetIndex, cell.getColumn(), cell.getRow(), "采购组织不能为空"));
                eord.setOrganizationName(cell.getContents());
                eord.addErrorCell("organizationName", new ErrorCell(sheetIndex, cell.getColumn(), cell.getRow()));
                break;
            case 2://计划月
                if(StringUtils.isBlank(cell.getContents())) {
                    ExcelUtil.addErrorCell(true, errorCells,
                            new ErrorCell(sheetIndex, cell.getColumn(), cell.getRow(), "计划月不能为空"));
                }else if(cell.getContents().length()!=6) {
                    ExcelUtil.addErrorCell(true, errorCells,
                            new ErrorCell(sheetIndex, cell.getColumn(), cell.getRow(), "计划月格式错误"));
                }else{
                    try{
                        Integer.valueOf(cell.getContents());
                        Integer year = Integer.valueOf(cell.getContents().substring(0,4));
                        Integer month = Integer.valueOf(cell.getContents().substring(4,6));
                        if(year<2020||(month<=0||month>12)){
                            ExcelUtil.addErrorCell(true, errorCells,
                                    new ErrorCell(sheetIndex, cell.getColumn(), cell.getRow(), "计划月格式错误"));
                        }
                    }catch(Exception e){
                        ExcelUtil.addErrorCell(true, errorCells,
                                new ErrorCell(sheetIndex, cell.getColumn(), cell.getRow(), "计划月格式错误"));
                    }
                    eord.setPlanMonth(cell.getContents());
                    eord.addErrorCell("planMonth", new ErrorCell(sheetIndex, cell.getColumn(), cell.getRow()));
                }

                break;
            case 3://版本号
                ExcelUtil.addErrorCell(StringUtils.isBlank(cell.getContents()), errorCells,
                        new ErrorCell(sheetIndex, cell.getColumn(), cell.getRow(), "版本号不能为空"));
                eord.setPlanVersion(cell.getContents());
                eord.addErrorCell("planVersion", new ErrorCell(sheetIndex, cell.getColumn(), cell.getRow()));
                break;
            case 4://物料编码
                ExcelUtil.addErrorCell(StringUtils.isBlank(cell.getContents()), errorCells,
                        new ErrorCell(sheetIndex, cell.getColumn(), cell.getRow(), "物料编码不能为空"));
                eord.setMaterialCode(cell.getContents());
                eord.addErrorCell("materialCode", new ErrorCell(sheetIndex, cell.getColumn(), cell.getRow()));
                break;
            case 5://物料名称
                ExcelUtil.addErrorCell(StringUtils.isBlank(cell.getContents()), errorCells,
                        new ErrorCell(sheetIndex, cell.getColumn(), cell.getRow(), "物料名称不能为空"));
                eord.setMaterialName(cell.getContents());
                eord.addErrorCell("materialName", new ErrorCell(sheetIndex, cell.getColumn(), cell.getRow()));
                break;
            case 6://第一月预测
                if(StringUtils.isBlank(cell.getContents())){
                    ExcelUtil.addErrorCell(true, errorCells,
                            new ErrorCell(sheetIndex, cell.getColumn(), cell.getRow(), "第一月预测不能为空"));
                }else{
                    if(ExcelUtil.checkPositiveInt(cell.getContents())){
                        eord.setFirstMonth(new BigDecimal(cell.getContents()));
                        eord.addErrorCell("firstMonth", new ErrorCell(sheetIndex, cell.getColumn(), cell.getRow()));
                    }else{
                        ExcelUtil.addErrorCell(true, errorCells,
                                new ErrorCell(sheetIndex, cell.getColumn(), cell.getRow(), "第一月预测格式错误"));
                    }
                }
                break;
            case 7://第二月预测
                if(StringUtils.isNotBlank(cell.getContents())){
                    if(ExcelUtil.checkPositiveInt(cell.getContents())){
                        eord.setSecondMonth(new BigDecimal(cell.getContents()));
                        eord.addErrorCell("secondMonth", new ErrorCell(sheetIndex, cell.getColumn(), cell.getRow()));
                    }else{
                        ExcelUtil.addErrorCell(true, errorCells,
                                new ErrorCell(sheetIndex, cell.getColumn(), cell.getRow(), "第二月预测格式错误"));
                    }
                }
                break;
            case 8://第三月预测
                if(StringUtils.isNotBlank(cell.getContents())){
                    if(ExcelUtil.checkPositiveInt(cell.getContents())){
                        eord.setThreeMonth(new BigDecimal(cell.getContents()));
                        eord.addErrorCell("threeMonth", new ErrorCell(sheetIndex, cell.getColumn(), cell.getRow()));
                    }else{
                        ExcelUtil.addErrorCell(true, errorCells,
                                new ErrorCell(sheetIndex, cell.getColumn(), cell.getRow(), "第三月预测格式错误"));
                    }
                }
                break;
            case 9://单位
                ExcelUtil.addErrorCell(StringUtils.isBlank(cell.getContents()), errorCells,
                        new ErrorCell(sheetIndex, cell.getColumn(), cell.getRow(), "单位不能为空"));
                eord.setUnit(cell.getContents());
                eord.addErrorCell("unit", new ErrorCell(sheetIndex, cell.getColumn(), cell.getRow()));
                eord.addErrorCell("lineErrorContents", new ErrorCell(sheetIndex, cell.getColumn()+1, cell.getRow()));
                break;
        }
    }
}
