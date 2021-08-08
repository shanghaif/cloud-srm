package com.midea.cloud.srm.supcooperate.material.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.annotation.AuthData;
import com.midea.cloud.common.constants.SequenceCodeConstant;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.enums.rbac.MenuEnum;
import com.midea.cloud.common.enums.supcooperate.MaterialItemType;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.DateUtil;
import com.midea.cloud.common.utils.*;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.feign.supplier.SupplierClient;
import com.midea.cloud.srm.model.base.material.MaterialItem;
import com.midea.cloud.srm.model.base.organization.entity.Organization;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.suppliercooperate.material.dto.CeeaMaterialItemDTO;
import com.midea.cloud.srm.model.suppliercooperate.material.entity.CeeaMaterialDetail;
import com.midea.cloud.srm.model.suppliercooperate.material.entity.CeeaMaterialItem;
import com.midea.cloud.srm.supcooperate.material.mapper.MaterialItemMapper;
import com.midea.cloud.srm.supcooperate.material.service.IMaterialDetailService;
import com.midea.cloud.srm.supcooperate.material.service.IMaterialItemService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * <pre>
 *  物料计划维护表 服务实现类
 * </pre>
 *
 * @author yourname@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-21 23:38:18
 *  修改内容:
 * </pre>
 */
@Service
@Slf4j
public class MaterialItemServiceImpl extends ServiceImpl<MaterialItemMapper, CeeaMaterialItem> implements IMaterialItemService {
    @Autowired
    private IMaterialDetailService iMaterialDetailService;

    @Resource
    private BaseClient baseClient;

    @Resource
    private FileCenterClient fileCenterClient;

    @Resource
    private SupplierClient supplierClient;


    private static final List<String> fixedTitle;

    private static final List<String> fixedTitleDetail;

    static {
        fixedTitle = new ArrayList<>();
        fixedTitle.addAll(Arrays.asList("*业务实体", "*库存组织", "*交货地点", "*供应商名称", "*物料编码", "*项目"));
        fixedTitleDetail = new ArrayList<>();
        fixedTitleDetail.addAll(Arrays.asList("*物料计划号"));
    }

    /**
     * 分页条件查询
     *
     * @param materialItemDTO
     * @return
     */
    @Override
    public PageInfo<CeeaMaterialItem> getMaterialItemList(CeeaMaterialItemDTO materialItemDTO) {
        PageUtil.startPage(materialItemDTO.getPageNum(), materialItemDTO.getPageSize());
        List<CeeaMaterialItem> ceeaMaterialItems = getCeeaMaterialItems(materialItemDTO);
        return new PageInfo<>(ceeaMaterialItems);
    }

    @AuthData(module = MenuEnum.MATERIAL_PLAN)
    private List<CeeaMaterialItem> getCeeaMaterialItems(CeeaMaterialItemDTO materialItemDTO) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        QueryWrapper<CeeaMaterialItem> wrapper = new QueryWrapper<>();
        //业务实体多选条件查询
        wrapper.in(CollectionUtils.isNotEmpty(materialItemDTO.getOrgIds()), "ORG_ID", materialItemDTO.getOrgIds());
        //库存组织多条件查询
        wrapper.in(CollectionUtils.isNotEmpty(materialItemDTO.getOrganizationIds()), "ORGANIZATION_ID", materialItemDTO.getOrganizationIds());
        //交货地点模糊查询
        wrapper.like(StringUtils.isNotEmpty(materialItemDTO.getOrganizationSite()), "ORGANIZATION_SITE", materialItemDTO.getOrganizationSite());
        //物料计划号模糊查询
        wrapper.like(StringUtils.isNotEmpty(materialItemDTO.getMaterialSchNum()), "MATERIAL_SCH_NUM", materialItemDTO.getMaterialSchNum());
        //物料编号查询模糊查询
        wrapper.like(StringUtils.isNotEmpty(materialItemDTO.getMaterialCode()), "MATERIAL_CODE", materialItemDTO.getMaterialCode());
        //物料名称查询模糊查询
        wrapper.like(StringUtils.isNotEmpty(materialItemDTO.getMaterialName()), "MATERIAL_NAME", materialItemDTO.getMaterialName());
        //物料小类多条件查询
        wrapper.in(CollectionUtils.isNotEmpty(materialItemDTO.getCategoryIds()), "CATEGORY_ID", materialItemDTO.getCategoryIds());
        if (loginAppUser!=null){
            String userType = loginAppUser.getUserType();
            wrapper.eq(StringUtils.isNotEmpty(userType)&&userType.equals("VENDOR"), "SCH_TYPE", MaterialItemType.get("ISSUED"));
        }else {
            //计划状态条件查询
            wrapper.eq(StringUtils.isNotEmpty(materialItemDTO.getSchType()), "SCH_TYPE", materialItemDTO.getSchType());
        }
        //月度计划条件查询
        wrapper.eq(StringUtils.isNotEmpty(materialItemDTO.getMonthlySchDate()), "MONTHLY_SCH_DATE", materialItemDTO.getMonthlySchDate());
        return this.list(wrapper);
    }

    /**
     * 查询物料计划详情及明细列表
     *
     * @param id
     * @return
     */
    @Override
    public CeeaMaterialItemDTO getMaterialItemDetail(Long id) {
        CeeaMaterialItemDTO materialItemDTO = new CeeaMaterialItemDTO();
        CeeaMaterialItem byId = this.getById(id);
        materialItemDTO.setMaterialItem(byId);
        QueryWrapper<CeeaMaterialDetail> wrapper = new QueryWrapper<>();
        wrapper.eq("MATERIAL_ITEM_ID", id);
        List<CeeaMaterialDetail> list = iMaterialDetailService.list(wrapper);
        if (CollectionUtils.isNotEmpty(list)) {
            materialItemDTO.setMaterialDetailList(iMaterialDetailService.list(wrapper));
        }
        return materialItemDTO;
    }



    @Override
    public void importModelDownload(String monthlySchDate,HttpServletResponse response) throws IOException, ParseException {
        Assert.notNull(monthlySchDate,"缺少计划月度参数: monthlySchDate");
        // 构建表格
        Workbook workbook = crateWorkbookModel(monthlySchDate);
        // 获取输出流
        ServletOutputStream outputStream = EasyExcelUtil.getServletOutputStream(response, "物料计划导入模板");
        // 导出
        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();

    }

    @Override
    public void importModelDetailDownload(String materialItemId, HttpServletResponse response) throws IOException, ParseException {
        Assert.notNull(materialItemId, "缺少参数；");
        // 构建表格
        Workbook workbook = crateWorkbookModelDetail(materialItemId);
        // 获取输出流
        ServletOutputStream outputStream = EasyExcelUtil.getServletOutputStream(response, "物料计划详情导入模板");
        // 导出
        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }

    public Workbook crateWorkbookModelDetail(String materialItemId) throws ParseException {
        // 创建工作簿
        XSSFWorkbook workbook = new XSSFWorkbook();
        // 创建工作表:自定义导入
        XSSFSheet sheet = workbook.createSheet("sheet");
        // 创建单元格样式
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        // 设置水平对齐方式:中间对齐
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        // 设置垂直对齐方式:中间对齐
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // 设置字体样式
        Font font = workbook.createFont();
        font.setBold(true);
        cellStyle.setFont(font);

        cellStyle.setBorderBottom(BorderStyle.THIN); //下边框
        cellStyle.setBorderLeft(BorderStyle.THIN);//左边框
        cellStyle.setBorderTop(BorderStyle.THIN);//上边框
        cellStyle.setBorderRight(BorderStyle.THIN);//右边框

        // 创建标题行
        XSSFRow row = sheet.createRow(0);

        // 当前单元格下标
        int cellIndex = 0;

        // 设置固定标题列
        for (int i = 0; i < fixedTitleDetail.size(); i++) {
            XSSFCell cell1 = row.createCell(cellIndex);
                cell1.setCellValue(fixedTitleDetail.get(i));
                cell1.setCellStyle(cellStyle);
                cellIndex++;
        }

        CeeaMaterialItem ceeaMaterialItem = this.getById(materialItemId);
        if(ceeaMaterialItem !=null && StringUtils.isNotEmpty(ceeaMaterialItem.getMonthlySchDate())) {
            Date date = DateUtil.parseDate(ceeaMaterialItem.getMonthlySchDate());
            LocalDate localDate = DateUtil.dateToLocalDate(date);
            List<String> dayBetween = DateUtil.getDayBetween(localDate, "yyyy-MM-dd");
            for (int i = 0; i < dayBetween.size(); i++) {
                XSSFCell cell1 = row.createCell(cellIndex);
                cell1.setCellValue(dayBetween.get(i));
                cell1.setCellStyle(cellStyle);
                cellIndex++;
            }
        }else {
            throw new BaseException("请先维护计划月度；");
        }
        return workbook;
    }

    /**
     * 构建导入模板
     */
    public Workbook crateWorkbookModel(String monthlySchDate) throws ParseException {
        // 创建工作簿
        XSSFWorkbook workbook = new XSSFWorkbook();
        // 创建工作表:自定义导入
        XSSFSheet sheet = workbook.createSheet("sheet");
        // 创建单元格样式
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        // 设置水平对齐方式:中间对齐
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        // 设置垂直对齐方式:中间对齐
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // 设置字体样式
        Font font = workbook.createFont();
        font.setBold(true);
        cellStyle.setFont(font);

        cellStyle.setBorderBottom(BorderStyle.THIN); //下边框
        cellStyle.setBorderLeft(BorderStyle.THIN);//左边框
        cellStyle.setBorderTop(BorderStyle.THIN);//上边框
        cellStyle.setBorderRight(BorderStyle.THIN);//右边框

        // 创建标题行
        XSSFRow row = sheet.createRow(0);

        // 当前单元格下标
        int cellIndex = 0;

        // 设置固定标题列
        for (int i = 0; i < fixedTitle.size(); i++) {
            XSSFCell cell1 = row.createCell(cellIndex);
            if (i != fixedTitle.size() - 1) {
                cell1.setCellValue(fixedTitle.get(i));
                cell1.setCellStyle(cellStyle);
            } else {
                String msg = "1、项目分别填\"需求，分别对应输入需求数量";
                EasyExcelUtil.setCellStyle(workbook, cell1, sheet, msg, fixedTitle.get(i));
            }
            cellIndex++;
        }


        // 设置时间动态列
        Date date = DateUtil.parseDate(monthlySchDate);
        LocalDate localDate = DateUtil.dateToLocalDate(date);
        List<String> dayBetween = DateUtil.getDayBetween(localDate, "yyyy-MM-dd");
        for(int i = 0;i < dayBetween.size();i++){
            XSSFCell cell1 = row.createCell(cellIndex);
            cell1.setCellValue(dayBetween.get(i));
            cell1.setCellStyle(cellStyle);
            cellIndex ++;
        }
        return workbook;
    }

    @Override
    @Transactional
    public Map<String, Object> importExcel(MultipartFile file, Fileupload fileupload,HttpServletResponse response) throws IOException, ParseException {
        // 检查上传文件
        EasyExcelUtil.checkParam(file,fileupload);
        // 获取文件输入流
        InputStream inputStream = file.getInputStream();
        // 导入文件是否有错误信息标志
        AtomicBoolean errorFlag = new AtomicBoolean(false);
        // 导入的数据
        List<CeeaMaterialItem> ceeaMaterialItems = new ArrayList<>();
        // 每行报错信息
        List<String> errorList = new ArrayList<>();
        // 返回信息
        Map<String, Object> result = new HashMap<>();
        result.put("status", YesOrNo.YES.getValue());
        result.put("message","success");
        // 获取导入数据
        Workbook workbook = this.getImportData(inputStream, ceeaMaterialItems, errorList, errorFlag);
        if(errorFlag.get()){
            // 有报错,上传报错文件
            this.uploadErrorFile(file, fileupload, errorList, result, workbook);
        }else {
            if(CollectionUtils.isNotEmpty(ceeaMaterialItems)){
                HashSet<Long> ids = new HashSet<>();
                ceeaMaterialItems.forEach(ceeaMaterialItem -> {
                    List<CeeaMaterialDetail> ceeaMaterialDetails = ceeaMaterialItem.getCeeaMaterialDetails();
                    Date schMonthlyDate = ceeaMaterialDetails.get(0).getSchMonthlyDate();
                    String monthlySchDate = DateUtil.format(schMonthlyDate, "yyyy-MM");
                    ceeaMaterialItem.setMonthlySchDate(monthlySchDate);
                    // 检查是否存在: 计划月度 + 业务实体 + 库存组织 + 交货地点 + 物料小类 + 物料编码 + 供应商编码
                    List<CeeaMaterialItem> materialItems = this.list(new QueryWrapper<>(new CeeaMaterialItem().setMonthlySchDate(monthlySchDate).setOrgId(ceeaMaterialItem.getOrgId()).
                            setOrganizationId(ceeaMaterialItem.getOrganizationId()).setOrganizationSite(ceeaMaterialItem.getOrganizationSite()).
                            setCategoryId(ceeaMaterialItem.getCategoryId()).setMaterialId(ceeaMaterialItem.getMaterialId()).setVendorName(ceeaMaterialItem.getVendorName())));
                    if(CollectionUtils.isNotEmpty(materialItems)){
                        // 更新操作
                        CeeaMaterialItem ceeaMaterialItem1 = materialItems.get(0);
                        ids.add(ceeaMaterialItem1.getMaterialItemId());
                        ceeaMaterialDetails.forEach(ceeaMaterialDetail -> {
                            // 检查行是否存在, 存在则更新 : materialItemId + 日期
                            List<CeeaMaterialDetail> ceeaMaterialDetailList = iMaterialDetailService.list(new QueryWrapper<>(new CeeaMaterialDetail().
                                    setMaterialItemId(ceeaMaterialItem1.getMaterialItemId()).setSchMonthlyDate(ceeaMaterialDetail.getSchMonthlyDate())));
                            if(CollectionUtils.isNotEmpty(ceeaMaterialDetailList)){
                                CeeaMaterialDetail ceeaMaterialDetail1 = ceeaMaterialDetailList.get(0);
                                ceeaMaterialDetail1.setRequirementQuantity(ceeaMaterialDetail.getRequirementQuantity());
                                iMaterialDetailService.updateById(ceeaMaterialDetail1);
                            }else {
                                ceeaMaterialDetail.setMaterialItemId(ceeaMaterialItem1.getMaterialItemId());
                                ceeaMaterialDetail.setMaterialSchNum(ceeaMaterialItem1.getMaterialSchNum());
                                iMaterialDetailService.save(ceeaMaterialDetail);
                            }
                        });
                    }else {
                        // 新增
                        Long materialItemId = IdGenrator.generate();
                        ids.add(materialItemId);
                        ceeaMaterialItem.setMaterialItemId(materialItemId);
                        String materialSchNum = baseClient.seqGen(SequenceCodeConstant.SEQ_CEEA_MATERIAL_ITEM_CODE);
                        ceeaMaterialItem.setMaterialSchNum(materialSchNum);
                        ceeaMaterialItem.setSchType(MaterialItemType.HASNEW.getName());
                        this.save(ceeaMaterialItem);
                        // 保存行表
                        ceeaMaterialDetails.forEach(ceeaMaterialDetail -> {
                            ceeaMaterialDetail.setId(IdGenrator.generate());
                            ceeaMaterialDetail.setMaterialItemId(materialItemId);
                            ceeaMaterialDetail.setMaterialSchNum(materialSchNum);
                        });
                        iMaterialDetailService.saveBatch(ceeaMaterialDetails);
                    }
                });
                ids.forEach(id->{
                    this.baseMapper.updateSchTotalQuantity(id);
                });
            }
        }
        return result;
    }

    @Override
    public Map<String, Object> importDetailExcel(String materialItemId,MultipartFile file, Fileupload fileupload, HttpServletResponse response) throws IOException, ParseException {
        // 检查上传文件
        // EasyExcelUtil.checkParam(file,fileupload);
        // 获取文件输入流
        InputStream inputStream = file.getInputStream();
        // 导入文件是否有错误信息标志
        AtomicBoolean errorFlag = new AtomicBoolean(false);
        // 导入的数据
        List<CeeaMaterialItem> ceeaMaterialItems = new ArrayList<>();
        // 每行报错信息
        List<String> errorList = new ArrayList<>();
        // 返回信息
        Map<String, Object> result = new HashMap<>();
        result.put("status", YesOrNo.YES.getValue());
        result.put("message","success");

        // 获取导入数据
        Workbook workbook = this.getImportDetailData(materialItemId,inputStream, ceeaMaterialItems, errorList, errorFlag);

        if(errorFlag.get()){
            // 有报错,上传报错文件
            this.uploadErrorFile(file, fileupload, errorList, result, workbook);
        }else {
            if(CollectionUtils.isNotEmpty(ceeaMaterialItems)){
                ceeaMaterialItems.forEach(ceeaMaterialItem->{
                    BigDecimal total = new BigDecimal(0);
                    List<CeeaMaterialDetail> ceeaMaterialDetails = ceeaMaterialItem.getCeeaMaterialDetails();
                        for (CeeaMaterialDetail ceeaMaterialDetail:ceeaMaterialDetails) {
                            QueryWrapper<CeeaMaterialDetail> wrapper = new QueryWrapper<>();
                            wrapper.eq("MATERIAL_ITEM_ID", ceeaMaterialDetail.getMaterialItemId());
                            wrapper.eq("MATERIAL_SCH_NUM", ceeaMaterialDetail.getMaterialSchNum());
                            wrapper.eq("SCH_MONTHLY_DATE", ceeaMaterialDetail.getSchMonthlyDate());
                            CeeaMaterialDetail materialDetail = iMaterialDetailService.getOne(wrapper);
                            // 更新
                            if (materialDetail != null) {
                                materialDetail.setRequirementQuantity(ceeaMaterialDetail.getRequirementQuantity());
                                iMaterialDetailService.updateById(materialDetail);
                            } else { // 新增
                                ceeaMaterialDetail.setMaterialItemId(ceeaMaterialItem.getMaterialItemId());
                                ceeaMaterialDetail.setId(IdGenrator.generate());
                                iMaterialDetailService.save(ceeaMaterialDetail);
                            }
                            total = total.add(ceeaMaterialDetail.getRequirementQuantity());
                        }
                         UpdateWrapper<CeeaMaterialItem> updateWrapper =new UpdateWrapper<>();
                         updateWrapper.eq("MATERIAL_ITEM_ID",ceeaMaterialItem.getMaterialItemId());
                         updateWrapper.eq("MATERIAL_SCH_NUM",ceeaMaterialItem.getMaterialSchNum());
                         updateWrapper.set("SCH_TOTAL_QUANTITY",total);
                         this.update(updateWrapper);
                    });
            }
        }
        return result;
    }

    private void uploadErrorFile(MultipartFile file, Fileupload fileupload, List<String> errorList, Map<String, Object> result, Workbook workbook) {
        // 获取指定工作表
        Sheet sheet = workbook.getSheetAt(0);

        // 获取最后一行的行号(0开始)
        int totalRows = sheet.getLastRowNum();
        // 获取每行的列数, 获取内容行的格数
        int totalCells = sheet.getRow(0).getLastCellNum();
        sheet.setColumnWidth(totalCells, sheet.getColumnWidth(totalCells) * 17 / 5);

        // 设置"错误信息"标题
        this.setErrorTitle(workbook, sheet, totalCells);
        for(int i =1;i<=totalRows;i++){
            Cell cell = sheet.getRow(i).createCell(totalCells);
            cell.setCellValue(errorList.get(i-1));
        }
        Fileupload fileupload1 = ExcelUtil.uploadErrorFile(fileCenterClient, fileupload, workbook, file);
        result.put("status", YesOrNo.NO.getValue());
        result.put("message","error");
        result.put("fileuploadId",fileupload1.getFileuploadId());
        result.put("fileName",fileupload1.getFileSourceName());
    }

    private void setErrorTitle(Workbook workbook, Sheet sheet, int totalCells) {
        Row row0 = sheet.getRow(0);
        // 创建单元格样式
        CellStyle cellStyle = workbook.createCellStyle();
        // 设置水平对齐方式:中间对齐
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        // 设置垂直对齐方式:中间对齐
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // 设置字体样式
        Font font = workbook.createFont();
        font.setBold(true);
        cellStyle.setFont(font);
        cellStyle.setBorderBottom(BorderStyle.THIN); //下边框
        cellStyle.setBorderLeft(BorderStyle.THIN);//左边框
        cellStyle.setBorderTop(BorderStyle.THIN);//上边框
        cellStyle.setBorderRight(BorderStyle.THIN);//右边框
        Cell cell1 = row0.createCell(totalCells);
        cell1.setCellValue("错误信息");
        cell1.setCellStyle(cellStyle);
    }

    /**
     * 获取导入的数据, 并对数据进行校验
     * @param inputStream
     * @param ceeaMaterialItems
     * @param errorList
     * @throws IOException
     * @throws ParseException
     */
    private Workbook getImportData(InputStream inputStream, List<CeeaMaterialItem> ceeaMaterialItems, List<String> errorList,AtomicBoolean errorFlag) throws IOException, ParseException {
        Workbook workbook = new XSSFWorkbook(inputStream);
        // 获取指定工作表
        Sheet sheet = workbook.getSheetAt(0);
        // 获取最后一行的行号(0开始)
        int totalRows = sheet.getLastRowNum();
        // 获取每行的列数, 获取内容行的格数
        int totalCells = sheet.getRow(0).getLastCellNum();
        // 获取标题行数据
        List<String> head = new ArrayList<>();
        // 获取首行
        Row headRow = sheet.getRow(0);
        // 遍历每个单元格的值
        for (int i = 0; i < totalCells; i++) {
            Cell cell = headRow.getCell(i);
            head.add(ExcelUtil.getCellValue(cell));
        }
        HashSet<String> hashSet = new HashSet<>();
        // 遍历内容行获取数据,从2行开始,也就是行的下标为1
        for (int r = 1; r <= totalRows; r++) {
            log.info("第"+r+"次循环");
            Row row = sheet.getRow(r);
            if (null == row) {
                // 过滤空行,空行一下内容全部上移一行
                sheet.shiftRows(r + 1, totalRows, -1);
                r--;
                totalRows --;
                continue;
            }

            // 过滤空行, 当前行每个单元格的值都为空时, 当前行一下行全部往上移一行
            int count = 0;
            for (int i = 0; i < totalCells; i++) {
                // 获取当前单元格
                Cell cell = row.getCell(i);
                // 调用方法获取数值
                String cellValue = ExcelUtil.getCellValue(cell);
                if (null == cellValue || "".equals(cellValue)) {
                    count++;
                }
            }
            if (count == totalCells) {
                if (r + 1 > totalRows) {
                    break;
                }
                sheet.shiftRows(r + 1, totalRows, -1);
                r--;
                totalRows --;
                continue;

            }
// <------------------------------------一下正式开始获取表格内容-------------------------------------------->
            // 收集每行错误信息
            StringBuffer errorMsg = new StringBuffer();
            // 开始遍历行的单元格值
            CeeaMaterialItem ceeaMaterialItem = new CeeaMaterialItem();
            List<CeeaMaterialDetail> ceeaMaterialDetails = new ArrayList<>();
            StringBuffer only = new StringBuffer();

            // 获取业务实体
            Cell cell0 = row.getCell(0);
            String orgName = ExcelUtil.getCellValue(cell0);
            if(StringUtil.notEmpty(orgName)){
                orgName = orgName.trim();
                only.append(orgName);
                Organization organization = baseClient.getOrganizationByParam(new Organization().setOrganizationName(orgName));
                if(null != organization && StringUtil.notEmpty(organization.getOrganizationId())){
                    ceeaMaterialItem.setOrgId(organization.getOrganizationId());
                    ceeaMaterialItem.setOrgCode(organization.getOrganizationCode());
                    ceeaMaterialItem.setOrgName(organization.getOrganizationCode());
                }else {
                    errorFlag.set(true);
                    errorMsg.append("业务实体不存在; ");
                }
            }else {
                errorFlag.set(true);
                errorMsg.append("业务实体不能为空; ");
            }

            // 检查库存组织
            Cell cell1 = row.getCell(1);
            String organizationName = ExcelUtil.getCellValue(cell1);
            if(StringUtil.notEmpty(organizationName)){
                organizationName = organizationName.trim();
                only.append(organizationName);
                Organization organization = baseClient.getOrganizationByParam(new Organization().setOrganizationName(organizationName));
                if(null != organization && StringUtil.notEmpty(organization.getOrganizationId())){
                    if(StringUtil.notEmpty(organization.getParentOrganizationIds())){
                        if(StringUtil.notEmpty(ceeaMaterialItem.getOrgId()) && organization.getParentOrganizationIds().contains(ceeaMaterialItem.getOrgId().toString())){
                            ceeaMaterialItem.setOrganizationId(organization.getOrganizationId());
                            ceeaMaterialItem.setOrganizationName(organization.getOrganizationName());
                            ceeaMaterialItem.setOrganizationCode(organization.getOrganizationCode());
                        }else {
                            errorFlag.set(true);
                            errorMsg.append("库存组织与业务实体不知层级关系; ");
                        }
                    }else {
                        errorFlag.set(true);
                        errorMsg.append("库存组织没有父节点; ");
                    }
                }else {
                    errorFlag.set(true);
                    errorMsg.append("库存组织不存在; ");
                }
            }else {
                errorFlag.set(true);
                errorMsg.append("库存组织不能为空; ");
            }

            // 交货地点
            Cell cell2 = row.getCell(2);
            String organizationSite = ExcelUtil.getCellValue(cell2);
            if(StringUtil.notEmpty(organizationSite)){
                organizationSite = organizationSite.trim();
                only.append(organizationSite);
                ceeaMaterialItem.setOrganizationSite(organizationSite);
            }else {
                errorFlag.set(true);
                errorMsg.append("交货地点不能为空; ");
            }

            // 供应商
            Cell cell3 = row.getCell(3);
            String vendorName = ExcelUtil.getCellValue(cell3);
            if (StringUtil.notEmpty(vendorName)) {
                vendorName = vendorName.trim();
                only.append(vendorName);
                ceeaMaterialItem.setVendorName(vendorName);
            } else {
                errorFlag.set(true);
                errorMsg.append("供应商名称不能为空; ");
            }

            // 物料编码
            Cell cell4 = row.getCell(4);
            String materialCode = ExcelUtil.getCellValue(cell4);
            if(StringUtil.notEmpty(materialCode)){
                materialCode = materialCode.trim();
                only.append(materialCode);
                List<MaterialItem> materialItems = baseClient.listMaterialByParam(new MaterialItem().setMaterialCode(materialCode));
                if(CollectionUtils.isNotEmpty(materialItems)){
                    MaterialItem materialItem = materialItems.get(0);
                    ceeaMaterialItem.setMaterialId(materialItem.getMaterialId());
                    ceeaMaterialItem.setMaterialCode(materialItem.getMaterialCode());
                    ceeaMaterialItem.setMaterialName(materialItem.getMaterialName());
                    ceeaMaterialItem.setCategoryId(materialItem.getCategoryId());
                    ceeaMaterialItem.setCategoryName(materialItem.getCategoryName());
                    ceeaMaterialItem.setUnit(materialItem.getUnit());
                }else {
                    errorFlag.set(true);
                    errorMsg.append("物料编码不存在; ");
                }
            }else {
                errorFlag.set(true);
                errorMsg.append("物料编码不能为空; ");
            }

            // 项目
            Cell cell5 = row.getCell(5);
            String project = ExcelUtil.getCellValue(cell5);
            if(StringUtil.notEmpty(project)){
                project = project.trim();
                if(!"需求".equals(project)) {
                    errorFlag.set(true);
                    errorMsg.append("项目只能填\"需求\"; ");
                }
            }else {
                errorFlag.set(true);
                errorMsg.append("项目不能为空; ");
            }

            if(only.length() > 0 && !hashSet.add(only.toString())){
                errorFlag.set(true);
                errorMsg.append("业务实体+库存组织+交货地点+物料编码+供应商名称,存在重复; ");
            }

            // 遍历获取需求数量
            for (int c = 6; c < totalCells; c++) {
                Cell cell = row.getCell(c);
                String cellValue = ExcelUtil.getCellValue(cell);
                if(StringUtil.notEmpty(cellValue)){
                    cellValue = cellValue.trim();
                }else {
                    cellValue = "0";
                }
                if(StringUtil.isDigit(cellValue)){
                    CeeaMaterialDetail ceeaMaterialDetail = new CeeaMaterialDetail();
                    Date date = DateUtil.parseDate(head.get(c));
                    ceeaMaterialDetail.setSchMonthlyDate(date);
                    ceeaMaterialDetail.setRequirementQuantity(new BigDecimal(cellValue));
                    ceeaMaterialDetails.add(ceeaMaterialDetail);
                }else {
                    errorFlag.set(true);
                    errorMsg.append(cellValue+"非数字; ");
                }
            }

            ceeaMaterialItem.setCeeaMaterialDetails(ceeaMaterialDetails);
            ceeaMaterialItems.add(ceeaMaterialItem);
            errorList.add(errorMsg.toString());
        }
        return workbook;
    }


    /**
     * 获取物料计划详情导入的数据, 并对数据进行校验
     * @param inputStream
     * @param ceeaMaterialItems
     * @param errorList
     * @throws IOException
     * @throws ParseException
     */
    private Workbook getImportDetailData(String materialItemId,InputStream inputStream, List<CeeaMaterialItem> ceeaMaterialItems, List<String> errorList,AtomicBoolean errorFlag) throws IOException, ParseException {
        Workbook workbook = new XSSFWorkbook(inputStream);
        // 获取指定工作表
        Sheet sheet = workbook.getSheetAt(0);
        // 获取最后一行的行号(0开始)
        int totalRows = sheet.getLastRowNum();
        // 获取每行的列数, 获取内容行的格数
        int totalCells = sheet.getRow(0).getLastCellNum();
        // 获取标题行数据
        List<String> head = new ArrayList<>();
        // 获取首行
        Row headRow = sheet.getRow(0);
        // 遍历每个单元格的值
        for (int i = 0; i < totalCells; i++) {
            Cell cell = headRow.getCell(i);
            head.add(ExcelUtil.getCellValue(cell));
        }
        HashSet<String> hashSet = new HashSet<>();
        // 遍历内容行获取数据,从2行开始,也就是行的下标为1
        for (int r = 1; r <= totalRows; r++) {
            log.info("第"+r+"次循环");
            Row row = sheet.getRow(r);
            if (null == row) {
                // 过滤空行,空行一下内容全部上移一行
                sheet.shiftRows(r + 1, totalRows, -1);
                r--;
                totalRows --;
                continue;
            }

            // 过滤空行, 当前行每个单元格的值都为空时, 当前行一下行全部往上移一行
            int count = 0;
            for (int i = 0; i < totalCells; i++) {
                // 获取当前单元格
                Cell cell = row.getCell(i);
                // 调用方法获取数值
                String cellValue = ExcelUtil.getCellValue(cell);
                if (null == cellValue || "".equals(cellValue)) {
                    count++;
                }
            }
            if (count == totalCells) {
                if (r + 1 > totalRows) {
                    break;
                }
                sheet.shiftRows(r + 1, totalRows, -1);
                r--;
                totalRows --;
                continue;

            }
// <------------------------------------一下正式开始获取表格内容-------------------------------------------->
            // 收集每行错误信息
            StringBuffer errorMsg = new StringBuffer();
            // 开始遍历行的单元格值
            CeeaMaterialItem ceeaMaterialItem = new CeeaMaterialItem();
            List<CeeaMaterialDetail> ceeaMaterialDetails = new ArrayList<>();
            StringBuffer only = new StringBuffer();

            // 物料计划号
            Cell cell0 = row.getCell(0);
            String materialSchNum = ExcelUtil.getCellValue(cell0);
            if(StringUtil.notEmpty(materialSchNum)){
                materialSchNum = materialSchNum.trim();
                only.append(materialSchNum);
                QueryWrapper<CeeaMaterialItem> wrapper = new QueryWrapper<>();
                wrapper.eq("MATERIAL_ITEM_ID",materialItemId);
                wrapper.eq("MATERIAL_SCH_NUM",materialSchNum);
                CeeaMaterialItem materialItem = this.getOne(wrapper);
                if(null != materialItem){
                    ceeaMaterialItem.setMaterialSchNum(materialSchNum);
                    ceeaMaterialItem.setMaterialItemId(materialItem.getMaterialItemId());
                }else {
                    errorFlag.set(true);
                    errorMsg.append("物料计划号不存在; ");
                }
            }else {
                errorFlag.set(true);
                errorMsg.append("物料计划号不能为空; ");
            }

            if(only.length() > 0 && !hashSet.add(only.toString())){
                errorFlag.set(true);
                errorMsg.append("物料计划号存在重复；");
            }

            // 遍历获取需求数量
            for (int c = 1; c < totalCells; c++) {
                Cell cell = row.getCell(c);
                String cellValue = ExcelUtil.getCellValue(cell);
                if(StringUtil.notEmpty(cellValue)){
                    cellValue = cellValue.trim();
                }else {
                    cellValue = "0";
                }
                if(StringUtil.isDigit(cellValue)){
                    CeeaMaterialDetail ceeaMaterialDetail = new CeeaMaterialDetail();
                    Date date = DateUtil.parseDate(head.get(c));
                    ceeaMaterialDetail.setSchMonthlyDate(date);
                    ceeaMaterialDetail.setRequirementQuantity(new BigDecimal(cellValue));
                    if(StringUtils.isNotEmpty(ceeaMaterialItem.getMaterialSchNum())){
                        ceeaMaterialDetail.setMaterialSchNum(ceeaMaterialItem.getMaterialSchNum());
                        ceeaMaterialDetail.setMaterialItemId(ceeaMaterialItem.getMaterialItemId());
                    }
                    ceeaMaterialDetails.add(ceeaMaterialDetail);
                }else {
                    errorFlag.set(true);
                    errorMsg.append(cellValue+"非数字; ");
                }
            }
            ceeaMaterialItem.setCeeaMaterialDetails(ceeaMaterialDetails);
            ceeaMaterialItems.add(ceeaMaterialItem);
            errorList.add(errorMsg.toString());
        }
        return workbook;
    }

    @Override
    public void export(CeeaMaterialItemDTO materialItemDTO, HttpServletResponse response) throws ParseException, IOException {
        String monthlySchDate = materialItemDTO.getMonthlySchDate();
        Assert.notNull(materialItemDTO,"缺少必选参数:计划月度");
        // 构建表格
        Workbook workbook = crateWorkbookModel(monthlySchDate);
        // 获取数据
        List<CeeaMaterialItem> ceeaMaterialItems = getCeeaMaterialItems(materialItemDTO);
        if(CollectionUtils.isNotEmpty(ceeaMaterialItems)){
            ceeaMaterialItems.forEach(ceeaMaterialItem -> {
                HashMap<String, String> ceeaMaterialDetailMap = new HashMap<>();
                List<CeeaMaterialDetail> ceeaMaterialDetails = iMaterialDetailService.list(new QueryWrapper<>(new CeeaMaterialDetail().setMaterialItemId(ceeaMaterialItem.getMaterialItemId())));
                if(CollectionUtils.isNotEmpty(ceeaMaterialDetails)){
                    ceeaMaterialDetails.forEach(ceeaMaterialDetail -> {
                        String schMonthlyDate = DateUtil.format(ceeaMaterialDetail.getSchMonthlyDate(), "yyyy-MM-dd");
                        BigDecimal requirementQuantity = ceeaMaterialDetail.getRequirementQuantity();
                        if(StringUtil.notEmpty(requirementQuantity)){
                            ceeaMaterialDetailMap.put(schMonthlyDate,StringUtil.subZeroAndDot(String.valueOf(requirementQuantity.doubleValue())));
                        }else {
                            ceeaMaterialDetailMap.put(schMonthlyDate,"0");
                        }
                    });
                }
                ceeaMaterialItem.setCeeaMaterialDetailMap(ceeaMaterialDetailMap);
            });

            // 获取指定工作表
            Sheet sheet = workbook.getSheetAt(0);
            // 获取每行的列数, 获取内容行的格数
            int totalCells = sheet.getRow(0).getLastCellNum();
            // 获取标题行数据
            List<String> heads = new ArrayList<>();
            // 获取首行
            Row headRow = sheet.getRow(0);
            // 遍历每个单元格的值
            for (int i = 0; i < totalCells; i++) {
                Cell cell = headRow.getCell(i);
                heads.add(ExcelUtil.getCellValue(cell));
            }

            int rowIndex = 1;
            for (int i = 0;i < ceeaMaterialItems.size();i++){
                CeeaMaterialItem ceeaMaterialItem = ceeaMaterialItems.get(i);
                Map<String, String> ceeaMaterialDetailMap = ceeaMaterialItem.getCeeaMaterialDetailMap();
                Row row = sheet.createRow(rowIndex);
                // *业务实体","*库存组织","*交货地点","*物料编码
                Cell cell0 = row.createCell(0);
                cell0.setCellValue(ceeaMaterialItem.getOrgName());
                Cell cell1 = row.createCell(1);
                cell1.setCellValue(ceeaMaterialItem.getOrganizationName());
                Cell cell2 = row.createCell(2);
                cell2.setCellValue(ceeaMaterialItem.getOrganizationSite());
                Cell cell3 = row.createCell(3);
                cell3.setCellValue(ceeaMaterialItem.getVendorName());
                Cell cell4 = row.createCell(4);
                cell4.setCellValue(ceeaMaterialItem.getMaterialCode());
                Cell cell5 = row.createCell(5);
                cell5.setCellValue("需求");

                // 需求数量
                for(int j=6;j<totalCells;j++){
                    Cell cell = row.createCell(j);
                    String title = heads.get(j);
                    String s = ceeaMaterialDetailMap.get(title);
                    if(StringUtil.notEmpty(s)){
                        cell.setCellValue(s);
                    }else {
                        cell.setCellValue("0");
                    }
                }
                rowIndex ++;
            }
        }
        ServletOutputStream outputStream = EasyExcelUtil.getServletOutputStream(response, "物料计划导出");
        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }

    @Override
    public void exportDetail(String materialItemId, HttpServletResponse response) throws ParseException, IOException {
        Assert.notNull(materialItemId,"缺少必选参数:materialItemId;");
        Workbook workbook = crateWorkbookModelDetail(materialItemId);
        HashMap<String, String> ceeaMaterialDetailMap = new HashMap<>();
        if(StringUtil.isDigit(materialItemId)){
            List<CeeaMaterialDetail> ceeaMaterialDetails = iMaterialDetailService.list(new QueryWrapper<>(new CeeaMaterialDetail().setMaterialItemId(Long.parseLong(materialItemId))));
            if(CollectionUtils.isNotEmpty(ceeaMaterialDetails)){
                ceeaMaterialDetails.forEach(ceeaMaterialDetail -> {
                    String schMonthlyDate = DateUtil.format(ceeaMaterialDetail.getSchMonthlyDate(), "yyyy-MM-dd");
                    BigDecimal requirementQuantity = ceeaMaterialDetail.getRequirementQuantity();
                    if(StringUtil.notEmpty(requirementQuantity)){
                        ceeaMaterialDetailMap.put(schMonthlyDate,StringUtil.subZeroAndDot(String.valueOf(requirementQuantity.doubleValue())));
                    }else {
                        ceeaMaterialDetailMap.put(schMonthlyDate,"0");
                    }
                });
                // 获取指定工作表
                Sheet sheet = workbook.getSheetAt(0);
                // 获取每行的列数, 获取内容行的格数
                int totalCells = sheet.getRow(0).getLastCellNum();
                // 获取标题行数据
                List<String> heads = new ArrayList<>();
                // 获取首行
                Row headRow = sheet.getRow(0);
                // 遍历每个单元格的值
                for (int i = 0; i < totalCells; i++) {
                    Cell cell = headRow.getCell(i);
                    heads.add(ExcelUtil.getCellValue(cell));
                }
                Row row = sheet.createRow(1);
                Cell cell0 = row.createCell(0);
                cell0.setCellValue(ceeaMaterialDetails.get(0).getMaterialSchNum());
                for(int j=1;j<totalCells;j++){
                    Cell cell = row.createCell(j);
                    String title = heads.get(j);
                    String s = ceeaMaterialDetailMap.get(title);
                    if(StringUtil.notEmpty(s)){
                        cell.setCellValue(s);
                    }else {
                        cell.setCellValue("0");
                    }
                }
                ServletOutputStream outputStream = EasyExcelUtil.getServletOutputStream(response, "物料计划详情导出");
                workbook.write(outputStream);
                outputStream.flush();
                outputStream.close();
            }else {
                ServletOutputStream outputStream = EasyExcelUtil.getServletOutputStream(response, "物料计划详情导出");
                workbook.write(outputStream);
                outputStream.flush();
                outputStream.close();
            }
        }else {
            throw new BaseException("materialItemId格式非法；");
        }
    }
}
