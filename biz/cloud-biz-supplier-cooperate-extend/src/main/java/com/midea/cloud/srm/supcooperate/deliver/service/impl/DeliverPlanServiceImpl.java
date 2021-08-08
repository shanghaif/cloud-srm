package com.midea.cloud.srm.supcooperate.deliver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.annotation.AuthData;
import com.midea.cloud.common.constants.SequenceCodeConstant;
import com.midea.cloud.common.enums.UserType;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.enums.pm.pr.requirement.RequirementApproveStatus;
import com.midea.cloud.common.enums.rbac.MenuEnum;
import com.midea.cloud.common.enums.supcooperate.DeliverPlanLineStatus;
import com.midea.cloud.common.enums.supcooperate.DeliverPlanStatus;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.DateUtil;
import com.midea.cloud.common.utils.*;
import com.midea.cloud.srm.feign.api.ApiClient;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.feign.supplier.SupplierClient;
import com.midea.cloud.srm.model.base.material.MaterialItem;
import com.midea.cloud.srm.model.base.organization.entity.Organization;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseCategory;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import com.midea.cloud.srm.model.supplier.info.entity.OrgCategory;
import com.midea.cloud.srm.model.suppliercooperate.deliver.dto.DeliverPlanDTO;
import com.midea.cloud.srm.model.suppliercooperate.deliver.entity.DeliverPlan;
import com.midea.cloud.srm.model.suppliercooperate.deliver.entity.DeliverPlanDetail;
import com.midea.cloud.srm.supcooperate.deliver.mapper.DeliverPlanMapper;
import com.midea.cloud.srm.supcooperate.deliver.service.IDeliverPlanDetailService;
import com.midea.cloud.srm.supcooperate.deliver.service.IDeliverPlanService;
import com.midea.cloud.srm.supcooperate.job.DeliverPlanJob;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.BindException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <pre>
 *  到货计划维护表 服务实现类
 * </pre>
 *
 * @author zhi1772778785@163.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-27 14:42:31
 *  修改内容:
 * </pre>
 */
@Service
@Slf4j
public class DeliverPlanServiceImpl extends ServiceImpl<DeliverPlanMapper, DeliverPlan> implements IDeliverPlanService {
    @Resource
    DeliverPlanJob deliverPlanJob;

    @Autowired
    IDeliverPlanDetailService iDeliverPlanDetailService;

    @Resource
    private BaseClient baseClient;

    @Resource
    private SupplierClient supplierClient;

    @Resource
    private FileCenterClient fileCenterClient;

    @Resource
    private  DeliverPlanMapper deliverPlanMapper;
    @Autowired
    private ApiClient apiClient;

    private static final List<String> fixedTitle;

    private static final List<String> fixedLineTitle;

    private final ThreadPoolExecutor ioThreadPool;

    private final ForkJoinPool calculateThreadPool;


    //创建需求池
    public DeliverPlanServiceImpl() {
        int cpuCount = Runtime.getRuntime().availableProcessors();
        ioThreadPool = new ThreadPoolExecutor(cpuCount * 2 + 1, cpuCount * 2 + 1,
                0, TimeUnit.SECONDS, new LinkedBlockingQueue(),
                new NamedThreadFactory("到货计划明细-http-sender", true), new ThreadPoolExecutor.CallerRunsPolicy());
        calculateThreadPool = new ForkJoinPool(cpuCount + 1);
    }

    static {
        fixedTitle = new ArrayList<>();
        fixedTitle.addAll(Arrays.asList("*业务实体", "*库存组织", "*交货地点", "*供应商名称", "*物料编码", "*项目"));
        fixedLineTitle = new ArrayList<>();
        fixedLineTitle.addAll(Arrays.asList("*到货计划号","*项目"));
    }

    /**
     * 分页查询
     *
     * @param deliverPlanDTO
     * @return
     */
    @Override
    public PageInfo<DeliverPlan> getdeliverPlanListPage(DeliverPlanDTO deliverPlanDTO) {
        PageUtil.startPage(deliverPlanDTO.getPageNum(), deliverPlanDTO.getPageSize());
        List<DeliverPlan> deliverPlans = getDeliverPlans(deliverPlanDTO);
        return new PageInfo<>(deliverPlans);
    }

    @AuthData(module = {MenuEnum.VENDOR_DELIVER_PLAN  , MenuEnum.SUPPLIER_SIGN})
    private List<DeliverPlan> getDeliverPlans(DeliverPlanDTO deliverPlanDTO) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        if (ObjectUtils.isEmpty(loginAppUser)){
            return new ArrayList<>();
        }
        QueryWrapper<DeliverPlan> wrapper = new QueryWrapper<>();
        //业务实体多选条件查询
        wrapper.in(CollectionUtils.isNotEmpty(deliverPlanDTO.getOrgIds()), "ORG_ID", deliverPlanDTO.getOrgIds());
        //库存组织多条件查询
        wrapper.in(CollectionUtils.isNotEmpty(deliverPlanDTO.getOrganizationIds()), "ORGANIZATION_ID", deliverPlanDTO.getOrganizationIds());
        //交货地点模糊查询
        wrapper.like(StringUtils.isNotEmpty(deliverPlanDTO.getDeliveryAddress()), "DELIVERY_ADDRESS", deliverPlanDTO.getDeliveryAddress());
        LoginAppUser user = AppUserUtil.getLoginAppUser();
        if (user.getUserType() != null && user.getUserType().equals("VENDOR")) {
            Assert.notNull(user.getCompanyId(),"当前用户没有绑定供应商信息，获取到货计划失败。");
                //供应商ID查询
            wrapper.eq("VENDOR_ID", user.getCompanyId());
            wrapper.eq("DELIVER_PLAN_STATUS", DeliverPlanStatus.APPROVAL.toString());
        }else {
            //到货计划状态条件查询
            wrapper.eq(StringUtils.isNotEmpty(deliverPlanDTO.getDeliverPlanStatus()), "DELIVER_PLAN_STATUS", deliverPlanDTO.getDeliverPlanStatus());

        }
        //供应商编号查询
        wrapper.eq(StringUtils.isNotEmpty(deliverPlanDTO.getVendorCode()), "VENDOR_CODE", deliverPlanDTO.getVendorCode());
        //供应商名称查询
        wrapper.eq(StringUtils.isNotEmpty(deliverPlanDTO.getVendorName()), "VENDOR_NAME", deliverPlanDTO.getVendorName());
        //物料编号查询模糊查询
        wrapper.like(StringUtils.isNotEmpty(deliverPlanDTO.getMaterialCode()), "MATERIAL_CODE", deliverPlanDTO.getMaterialCode());
        //物料名称查询模糊查询
        wrapper.like(StringUtils.isNotEmpty(deliverPlanDTO.getMaterialName()), "MATERIAL_NAME", deliverPlanDTO.getMaterialName());
        //物料小类多条件查询
        wrapper.in(CollectionUtils.isNotEmpty(deliverPlanDTO.getCategoryIds()), "CATEGORY_ID", deliverPlanDTO.getCategoryIds());
        //判断是不是供应商，供应商只能看到已发布的
/*        if (loginAppUser != null) {
            String userType = loginAppUser.getUserType();
            wrapper.eq(StringUtils.isNotEmpty(userType) && userType.equals("VENDOR"), "DELIVER_PLAN_STATUS", DeliverPlanStatus.APPROVAL.toString());
        } else {
            //到货计划状态条件查询
            wrapper.eq(StringUtils.isNotEmpty(deliverPlanDTO.getDeliverPlanLineStatus()), "DELIVER_PLAN_STATUS", deliverPlanDTO.getDeliverPlanLineStatus());
        }*/
        //月度计划条件查询
        wrapper.eq(StringUtils.isNotEmpty(deliverPlanDTO.getMonthlySchDate()), "MONTHLY_SCH_DATE", deliverPlanDTO.getMonthlySchDate());
        //到货计划号模糊查询
        wrapper.like(StringUtils.isNotEmpty(deliverPlanDTO.getDeliverPlanNum()), "DELIVER_PLAN_NUM", deliverPlanDTO.getDeliverPlanNum());
        //版本号查询
        wrapper.eq(deliverPlanDTO.getVersion() != null, "VERSION", deliverPlanDTO.getVersion());

        if (StringUtils.isNotEmpty(deliverPlanDTO.getDeliverPlanLineStatus())) {
            //计划行状态查询
            wrapper.gt("(SELECT count(b.DELIVER_PLAN_DETAIL_ID) FROM ceea_sc_deliver_plan_detail b \n" +
                    "WHERE DELIVER_PLAN_ID=b.DELIVER_PLAN_ID and b.DELIVER_PLAN_STATUS='"+deliverPlanDTO.getDeliverPlanLineStatus()+"')","0");
        }
        wrapper.orderByDesc("LAST_UPDATE_DATE");
        return this.list(wrapper);
    }
    /**
     * 单个查询
     *
     * @param id
     * @return
     */
    @Override
    public DeliverPlanDTO getDeliverPlan(Long id) {
        DeliverPlanDTO deliverPlanDTO = new DeliverPlanDTO();
        deliverPlanDTO.setDeliverPlan(this.getById(id));
        QueryWrapper<DeliverPlanDetail> wrapper = new QueryWrapper<>();
        wrapper.eq("DELIVER_PLAN_ID", id);
        wrapper.orderByAsc("SCH_MONTHLY_DATE");
        List<DeliverPlanDetail> list = iDeliverPlanDetailService.list(wrapper);
        deliverPlanDTO.setDeliverPlanDetailList(list);
        return deliverPlanDTO;
    }

    /**
     * 保存到货计划
     *
     * @param deliverPlanDTO
     */
    @Transactional
    @Override
    public void modifyDeliverPlan(DeliverPlanDTO deliverPlanDTO) {
        DeliverPlan deliverPlan = deliverPlanDTO.getDeliverPlan();
        Assert.notNull(deliverPlan, "到货计划不能为空");
        List<DeliverPlanDetail> deliverPlanDetailList = deliverPlanDTO.getDeliverPlanDetailList();
        Assert.notEmpty(deliverPlanDetailList, "请勾选需要保存计划");
        //获取所有允许超计划发货的小类id
        PurchaseCategory purchaseCategory = baseClient.MinByIfBeyondDeliver(deliverPlan.getCategoryId());
        BigDecimal i = new BigDecimal(0);
        ArrayList<Long> longs = new ArrayList<>();
        boolean falg=false;
        //如果需求==承诺供应数量则自动改变行状态为已确认
        for (DeliverPlanDetail deliverPlanDetail : deliverPlanDetailList) {
            //判断是否是已锁定的数据
            Assert.isTrue(deliverPlanDetail.getDeliverPlanLock() != "1", "请勾选未锁定的到货计划详情进行保存。");
            //需求数量
            BigDecimal bigDecimal1 = deliverPlanDetail.getRequirementQuantity();
            //承诺供应数量
            BigDecimal bigDecimal2 = deliverPlanDetail.getQuantityPromised();
            if (purchaseCategory==null){
                Assert.isTrue(bigDecimal2.compareTo(bigDecimal1) != 1,"非允许超计划发货物料承诺供应数量必须小于或等于需求数量，请修改后保存。");
            }
            //确认数量
            if (bigDecimal1.compareTo(bigDecimal2) == 0) {
                deliverPlanDetail.setDeliverPlanStatus(DeliverPlanLineStatus.COMFIRM.toString());
                falg=true;
            } else {
                deliverPlanDetail.setDeliverPlanStatus(DeliverPlanLineStatus.UNCOMFIRMED.toString());
            }
            //累计需求数量算出到货计划总数量
            i = i.add(bigDecimal1);
            //记录需要保存的数据id
            longs.add(deliverPlanDetail.getDeliverPlanDetailId());
            iDeliverPlanDetailService.updateById(deliverPlanDetail);
        }
        QueryWrapper<DeliverPlanDetail> wrapper = new QueryWrapper<>();
        wrapper.select("SUM(REQUIREMENT_QUANTITY)AS REQUIREMENT_QUANTITY");
        wrapper.eq("DELIVER_PLAN_ID", deliverPlan.getDeliverPlanId());
        wrapper.notIn("DELIVER_PLAN_DETAIL_ID", longs);
        List<DeliverPlanDetail> list = iDeliverPlanDetailService.list(wrapper);
        if (CollectionUtils.isEmpty(list)) {
            i = i.add(list.get(0).getRequirementQuantity());
        }
        deliverPlan.setSchTotalQuantity(i);
        this.updateById(deliverPlan);
        //发送请求给mrp
        getAffirmByMrp(falg);
    }

    @Override
    public void importModelDownload(String monthlySchDate, HttpServletResponse response) throws IOException, ParseException {
        Assert.notNull(monthlySchDate, "缺少计划月度参数: monthlySchDate");
        // 构建表格
        Workbook workbook = crateWorkbookModel(monthlySchDate);
        // 获取输出流
        ServletOutputStream outputStream = EasyExcelUtil.getServletOutputStream(response, "物料计划导入模板");
        // 导出
        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
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
                String msg = "项目分别填\"需求\"或\"供应\"，分别对应输入需求数量及承诺供应数量";
                LoginAppUser user = AppUserUtil.getLoginAppUser();
                if (null != user && UserType.VENDOR.name().equals(user.getUserType())) {
                    msg = "供应商，项目只能填\"供应\"，分别对应输入承诺供应数量";
                }
                EasyExcelUtil.setCellStyle(workbook, cell1, sheet, msg, fixedTitle.get(i));
            }
            cellIndex++;
        }

        // 设置时间动态列
        Date date = DateUtil.parseDate(monthlySchDate);
        LocalDate localDate = DateUtil.dateToLocalDate(date);
        List<String> dayBetween = DateUtil.getDayBetween(localDate, "yyyy-MM-dd");
        for (int i = 0; i < dayBetween.size(); i++) {
            Cell cell1 = row.createCell(cellIndex);
            cell1.setCellValue(dayBetween.get(i));
            cell1.setCellStyle(cellStyle);
            cellIndex++;
        }
        return workbook;
    }

    @Override
    @Transactional
    public Map<String, Object> importExcel(MultipartFile file, Fileupload fileupload) throws Exception {
        // 检查上传文件
        EasyExcelUtil.checkParam(file, fileupload);
        // 获取文件输入流
        InputStream inputStream = file.getInputStream();
        // 导入文件是否有错误信息标志
        AtomicBoolean errorFlag = new AtomicBoolean(false);
        // 导入的数据
        List<DeliverPlan> deliverPlans = new ArrayList<>();
        // 每行报错信息
        List<String> errorList = new ArrayList<>();
        // 返回信息
        Map<String, Object> result = new HashMap<>();
        result.put("status", YesOrNo.YES.getValue());
        result.put("message", "success");
        // 获取导入数据
        Workbook workbook = this.getImportData(inputStream, deliverPlans, errorList, errorFlag);
        if (errorFlag.get()) {
            // 有报错,上传报错文件
            this.uploadErrorFile(file, fileupload, errorList, result, workbook);
        } else {
            if (CollectionUtils.isNotEmpty(deliverPlans)) {
                HashSet<Long> deliverPlanIdUpdate = new HashSet<>();
                HashSet<Long> deliverPlanIdAdd = new HashSet<>();
                deliverPlans.forEach(deliverPlan -> {
                    // 校验是否存在 业务实体+库存组织+到货地点+供应商+物料编码
                    List<DeliverPlan> deliverPlanList = this.list(new QueryWrapper<>(new DeliverPlan().setOrgId(deliverPlan.getOrgId()).
                            setOrganizationId(deliverPlan.getOrganizationId()).setDeliveryAddress(deliverPlan.getDeliveryAddress()).
                            setVendorId(deliverPlan.getVendorId()).setMaterialId(deliverPlan.getMaterialId())));
                    if(CollectionUtils.isNotEmpty(deliverPlanList)){
                        // 更新
                        DeliverPlan deliverPlan1 = deliverPlanList.get(0);
                        // 头id
                        Long deliverPlanId = deliverPlan1.getDeliverPlanId();
                        String projectFlag = deliverPlan.getProjectFlag();
                        deliverPlanIdUpdate.add(deliverPlanId);
                        this.updateById(deliverPlan1);

                        List<DeliverPlanDetail> deliverPlanDetails = deliverPlan.getDeliverPlanDetails();
                        deliverPlanDetails.forEach(deliverPlanDetail -> {
                            // 需求
                            List<DeliverPlanDetail> planDetails = iDeliverPlanDetailService.list(new QueryWrapper<>(new DeliverPlanDetail().setDeliverPlanId(deliverPlanId).setSchMonthlyDate(deliverPlanDetail.getSchMonthlyDate())));
                            if(CollectionUtils.isNotEmpty(planDetails)){
                                // 更新
                                DeliverPlanDetail planDetail = planDetails.get(0);
                                // 判断是否锁定
                                if (!"1".equals(planDetail.getDeliverPlanLock())) {
                                    if ("0".equals(projectFlag)) {
                                        planDetail.setRequirementQuantity(deliverPlanDetail.getRequirementQuantity());
                                    }else {
                                        planDetail.setQuantityPromised(deliverPlanDetail.getQuantityPromised());
                                    }
                                    // 状态判断
                                    if(planDetail.getQuantityPromised().compareTo(BigDecimal.ZERO) != 0 && planDetail.getRequirementQuantity().compareTo(BigDecimal.ZERO) != 0
                                            && planDetail.getRequirementQuantity().compareTo(planDetail.getQuantityPromised()) == 0){
                                        planDetail.setDeliverPlanStatus(DeliverPlanLineStatus.COMFIRM.name());
                                    }
                                    iDeliverPlanDetailService.updateById(planDetail);
                                }
                            }else {
                                // 新增
                                deliverPlanDetail.setDeliverPlanDetailId(IdGenrator.generate());
                                deliverPlanDetail.setDeliverPlanId(deliverPlanId);
                                deliverPlanDetail.setDeliverPlanStatus(DeliverPlanLineStatus.UNCOMFIRMED.name());
                                iDeliverPlanDetailService.save(deliverPlanDetail);
                            }
                        });
                    }else {
                        // 新增
                        LocalDate localDate = deliverPlan.getDeliverPlanDetails().get(0).getSchMonthlyDate();
                        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM");
                        // 计划月度
                        String monthlySchDate = localDate.format(dateTimeFormatter);
                        deliverPlan.setMonthlySchDate(monthlySchDate);
                        Long deliverPlanId = IdGenrator.generate();
                        deliverPlanIdAdd.add(deliverPlanId);
                        deliverPlan.setDeliverPlanId(deliverPlanId);
                        // 到货计划号
                        deliverPlan.setDeliverPlanNum(baseClient.seqGen(SequenceCodeConstant.SEQ_CEEA_DELIVER_PLANNUM_CODE));
                        deliverPlan.setDeliverPlanStatus("DRAFT");
                        deliverPlan.setVersion(0L);
                        this.save(deliverPlan);
                        // 保存行表
                        List<DeliverPlanDetail> deliverPlanDetails = deliverPlan.getDeliverPlanDetails();
                        deliverPlanDetails.forEach(deliverPlanDetail -> {
                            deliverPlanDetail.setDeliverPlanId(deliverPlanId);
                            deliverPlanDetail.setDeliverPlanDetailId(IdGenrator.generate());
                            deliverPlanDetail.setDeliverPlanLock("2");
                            deliverPlanDetail.setDeliverPlanStatus(DeliverPlanLineStatus.UNCOMFIRMED.name());
                            iDeliverPlanDetailService.save(deliverPlanDetail);
                        });
                    }
                });
                // 更新版本
                if(CollectionUtils.isNotEmpty(deliverPlanIdUpdate)){
                    deliverPlanIdUpdate.forEach(id->{
                        this.baseMapper.updateSchTotalQuantity(id);
                    });
                    QueryWrapper<DeliverPlan> queryWrapper = new QueryWrapper<>();
                    queryWrapper.in("DELIVER_PLAN_ID",deliverPlanIdUpdate);
                    List<DeliverPlan> list = this.list(queryWrapper);
                    if(CollectionUtils.isNotEmpty(list)){
                        list.forEach(deliverPlan -> {
                            deliverPlan.setVersion(deliverPlan.getVersion() + 1);
                        });
                        this.updateBatchById(list);
                    }
                }
                if(CollectionUtils.isNotEmpty(deliverPlanIdAdd)){
                    deliverPlanIdUpdate.forEach(id->{
                        this.baseMapper.updateSchTotalQuantity(id);
                    });
                    QueryWrapper<DeliverPlan> queryWrapper = new QueryWrapper<>();
                    queryWrapper.in("DELIVER_PLAN_ID",deliverPlanIdAdd);
                    List<DeliverPlan> list = this.list(queryWrapper);
                    if(CollectionUtils.isNotEmpty(list)){
                        list.forEach(deliverPlan -> {
                            deliverPlan.setVersion(0L);
                        });
                        this.updateBatchById(list);
                    }
                }
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
        for (int i = 1; i <= totalRows; i++) {
            Cell cell = sheet.getRow(i).createCell(totalCells);
            cell.setCellValue(errorList.get(i - 1));
        }
        Fileupload fileupload1 = ExcelUtil.uploadErrorFile(fileCenterClient, fileupload, workbook, file);
        result.put("status", YesOrNo.NO.getValue());
        result.put("message", "error");
        result.put("fileuploadId", fileupload1.getFileuploadId());
        result.put("fileName", fileupload1.getFileSourceName());
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
     *
     * @param inputStream
     * @param deliverPlans
     * @param errorList
     * @throws IOException
     * @throws ParseException
     */
    private Workbook getImportData(InputStream inputStream, List<DeliverPlan> deliverPlans, List<String> errorList, AtomicBoolean errorFlag) throws IOException, ParseException {
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
        Map<String, Map<LocalDate,BigDecimal>> deliverPlanMap = new HashMap<>();


        // 遍历内容行获取数据,从2行开始,也就是行的下标为1
        for (int r = 1; r <= totalRows; r++) {
            log.info("第"+r+"次循环");
            Row row = sheet.getRow(r);
            if (null == row) {
                // 过滤空行,空行一下内容全部上移一行
                sheet.shiftRows(r + 1, totalRows, -1);
                r--;
                totalRows--;
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
                totalRows--;
                continue;

            }
// <------------------------------------一下正式开始获取表格内容-------------------------------------------->
            // 收集每行错误信息
            StringBuffer errorMsg = new StringBuffer();
            // 开始遍历行的单元格值
            DeliverPlan deliverPlan = new DeliverPlan();
            List<DeliverPlanDetail> deliverPlanDetails = new ArrayList<>();
            StringBuffer uniKey = new StringBuffer();
            StringBuffer uniKeyQuery = new StringBuffer();

            // 是否允许超计划发货
            String ceeaIfBeyondDeliver = "N";

            boolean lineErrorFlag = true;

            // 获取业务实体
            Cell cell0 = row.getCell(0);
            String orgName = ExcelUtil.getCellValue(cell0);
            if (StringUtil.notEmpty(orgName)) {
                orgName = orgName.trim();
                uniKey.append(orgName);
                uniKeyQuery.append(orgName);
                Organization organization = baseClient.getOrganizationByParam(new Organization().setOrganizationName(orgName));
                if (null != organization && StringUtil.notEmpty(organization.getOrganizationId())) {
                    deliverPlan.setOrgId(organization.getOrganizationId());
                    deliverPlan.setOrgCode(organization.getOrganizationCode());
                    deliverPlan.setOrgName(organization.getOrganizationCode());
                } else {
                    errorFlag.set(true);
                    lineErrorFlag= false;
                    errorMsg.append("业务实体不存在; ");
                }
            } else {
                errorFlag.set(true);
                lineErrorFlag= false;
                errorMsg.append("业务实体不能为空; ");
            }

            // 检查库存组织
            Cell cell1 = row.getCell(1);
            String organizationName = ExcelUtil.getCellValue(cell1);
            if (StringUtil.notEmpty(organizationName)) {
                organizationName = organizationName.trim();
                uniKey.append(organizationName);
                uniKeyQuery.append(organizationName);
                Organization organization = baseClient.getOrganizationByParam(new Organization().setOrganizationName(organizationName));
                if (null != organization && StringUtil.notEmpty(organization.getOrganizationId())) {
                    if (StringUtil.notEmpty(organization.getParentOrganizationIds())) {
                        if (StringUtil.notEmpty(deliverPlan.getOrgId()) && organization.getParentOrganizationIds().contains(deliverPlan.getOrgId().toString())) {
                            deliverPlan.setOrganizationId(organization.getOrganizationId());
                            deliverPlan.setOrganizationName(organization.getOrganizationName());
                            deliverPlan.setOrganizationCode(organization.getOrganizationCode());
                        } else {
                            errorFlag.set(true);
                            lineErrorFlag= false;
                            errorMsg.append("库存组织与业务实体不是父子关系; ");
                        }
                    } else {
                        errorFlag.set(true);
                        lineErrorFlag= false;
                        errorMsg.append("库存组织没有父节点; ");
                    }
                } else {
                    errorFlag.set(true);
                    lineErrorFlag= false;
                    errorMsg.append("库存组织不存在; ");
                }
            } else {
                errorFlag.set(true);
                lineErrorFlag= false;
                errorMsg.append("库存组织不能为空; ");
            }

            // 交货地点
            Cell cell2 = row.getCell(2);
            String deliveryAddress = ExcelUtil.getCellValue(cell2);
            if (StringUtil.notEmpty(deliveryAddress)) {
                deliveryAddress = deliveryAddress.trim();
                uniKey.append(deliveryAddress);
                uniKeyQuery.append(deliveryAddress);
                deliverPlan.setDeliveryAddress(deliveryAddress);
            } else {
                errorFlag.set(true);
                lineErrorFlag= false;
                errorMsg.append("交货地点不能为空; ");
            }

            // 供应商名称
            Cell cell3 = row.getCell(3);
            String vendorName = ExcelUtil.getCellValue(cell3);
            if (StringUtil.notEmpty(vendorName)) {
                vendorName = vendorName.trim();
                uniKey.append(vendorName);
                uniKeyQuery.append(vendorName);
                CompanyInfo companyInfo = supplierClient.getCompanyInfoByParam(new CompanyInfo().setCompanyName(vendorName));
                if (null != companyInfo && StringUtil.notEmpty(companyInfo.getCompanyId())) {
                    if (StringUtil.notEmpty(companyInfo.getCompanyCode())) {
                        deliverPlan.setVendorId(companyInfo.getCompanyId());
                        deliverPlan.setVendorCode(companyInfo.getCompanyCode());
                        deliverPlan.setVendorName(companyInfo.getCompanyName());
                    }else {
                        errorFlag.set(true);
                        lineErrorFlag= false;
                        errorMsg.append("供应商编码不存在; ");
                    }
                } else {
                    errorFlag.set(true);
                    lineErrorFlag= false;
                    errorMsg.append("供应商不存在; ");
                }
            } else {
                errorFlag.set(true);
                lineErrorFlag= false;
                errorMsg.append("供应商名称不能为空; ");
            }


            PurchaseCategory purchaseCategorycopy=null;
            // 物料编码
            Cell cell4 = row.getCell(4);
            String materialCode = ExcelUtil.getCellValue(cell4);
            if (StringUtil.notEmpty(materialCode)) {
                materialCode = materialCode.trim();
                uniKey.append(materialCode);
                uniKeyQuery.append(materialCode);
                List<MaterialItem> materialItems = baseClient.listMaterialByParam(new MaterialItem().setMaterialCode(materialCode));
                if (CollectionUtils.isNotEmpty(materialItems)) {
                    MaterialItem materialItem = materialItems.get(0);
                    deliverPlan.setMaterialId(materialItem.getMaterialId());
                    deliverPlan.setMaterialCode(materialItem.getMaterialCode());
                    deliverPlan.setMaterialName(materialItem.getMaterialName());
                    if (materialItem.getStruct() == null){
                        errorFlag.set(true);
                        lineErrorFlag= false;
                        errorMsg.append("物料小类不存在; ");
                    }else {
                        //判断是否是到货计划物料
                        PurchaseCategory purchaseCategory = baseClient.checkByIfDeliverPlan(materialItem.getStruct());
                        if (purchaseCategory==null){
                            errorFlag.set(true);
                            lineErrorFlag= false;
                            errorMsg.append("该物料的采购分类在采购分类维护里不存在; ");
                        }else {
                            deliverPlan.setCategoryId(purchaseCategory.getCategoryId());
                            deliverPlan.setCategoryName(purchaseCategory.getCategoryName());
                            deliverPlan.setUnit(materialItem.getUnit());
                            deliverPlan.setStruct(purchaseCategory.getStruct());
                            ceeaIfBeyondDeliver = StringUtil.notEmpty(purchaseCategory.getCeeaIfBeyondDeliver()) ? purchaseCategory.getCeeaIfBeyondDeliver() : "N";
                        }
                    }
                } else {
                    errorFlag.set(true);
                    lineErrorFlag= false;
                    errorMsg.append("物料编码不存在; ");
                }
            } else {
                errorFlag.set(true);
                lineErrorFlag= false;
                errorMsg.append("物料编码不能为空; ");
            }

            /**
             * 校验 供应商 + 业务实体 + 品类  查询  校验品类状态是否绿牌
             */
            if(StringUtil.notEmpty(deliverPlan.getVendorId())
                    && StringUtil.notEmpty(deliverPlan.getOrgId())
                    && StringUtil.notEmpty(deliverPlan.getStruct())){
                List<OrgCategory> orgCategoryList = supplierClient.getOrgCategoryByOrgCategory(new OrgCategory().
                        setCompanyId(deliverPlan.getVendorId()).
                        setCategoryFullId(deliverPlan.getStruct()).
                        setOrgId(deliverPlan.getOrgId()));
                if(CollectionUtils.isNotEmpty(orgCategoryList)){
                    String serviceStatus = orgCategoryList.get(0).getServiceStatus();
                    /**
                     * 供应商组织品类状态为 GREEN、VERIFY、ONE_TIME、YELLOW 可以创建到货计划
                     */
                    if(!"GREEN".equals(serviceStatus) &&
                       !"VERIFY".equals(serviceStatus) &&
                       !"ONE_TIME".equals(serviceStatus) &&
                       !"YELLOW".equals(serviceStatus)){
                        errorFlag.set(true);
                        lineErrorFlag= false;
                        errorMsg.append("该供应商组织品类状态不是绿牌; ");
                    }
                }else {
                    errorFlag.set(true);
                    lineErrorFlag= false;
                    errorMsg.append("供应商+业务实体+物料品类:不存在合作关系; ");
                }

            }
            String userType = AppUserUtil.getLoginAppUser().getUserType();
            // 项目 : 需求-需求数量  供应-承诺供应数量
            Cell cell5 = row.getCell(5);
            String project = ExcelUtil.getCellValue(cell5);
            if (StringUtil.notEmpty(project)) {
                project = project.trim();
                uniKey.append(project);
                if ("BUYER".equals(userType)) {
                    if ("需求".equals(project) || "供应".equals(project)) {
                        if ("供应".equals(project)) {
                            deliverPlan.setProjectFlag("1");
                        } else {
                            deliverPlan.setProjectFlag("0");
                        }
                    } else {
                        errorFlag.set(true);
                        lineErrorFlag= false;
                        errorMsg.append("采购商，项目只能填\"需求\"或\"供应\"; ");
                    }
                } else if ("VENDOR".equals(userType)) {
                    if ("供应".equals(project)) {
                        deliverPlan.setProjectFlag("1");
                    } else {
                        errorFlag.set(true);
                        lineErrorFlag= false;
                        errorMsg.append("供应商:项目只能填\"供应\"; ");
                    }
                }
            } else {
                errorFlag.set(true);
                lineErrorFlag= false;
                errorMsg.append("项目不能为空; ");
            }

            if (!hashSet.add(uniKey.toString())) {
                errorFlag.set(true);
                lineErrorFlag= false;
                errorMsg.append("唯一条件组合存在重复; ");
            }else {
                // 遍历获取需求数量
                for (int c = 6; c < totalCells; c++) {
                    Cell cell = row.getCell(c);
                    String cellValue = ExcelUtil.getCellValue(cell);
                    if (StringUtil.notEmpty(cellValue)) {
                        cellValue = cellValue.trim();
                    }else {
                        cellValue = "0";
                    }
                    if (StringUtil.isDigit(cellValue)) {
                        DeliverPlanDetail deliverPlanDetail = new DeliverPlanDetail();
                        Date date = DateUtil.parseDate(head.get(c));
                        deliverPlanDetail.setSchMonthlyDate(DateUtil.dateToLocalDate(date));
                        if ("0".equals(deliverPlan.getProjectFlag())) {
                            deliverPlanDetail.setRequirementQuantity(new BigDecimal(cellValue));
                        } else {
                            deliverPlanDetail.setQuantityPromised(new BigDecimal(cellValue));
                        }
                        deliverPlanDetails.add(deliverPlanDetail);
                    } else {
                        errorFlag.set(true);
                        errorMsg.append(cellValue + "非数字; ");
                    }
                }
                // 业务实体+库存组织+到货地点+供应商+物料编码
                if(lineErrorFlag && YesOrNo.NO.getValue().equals(ceeaIfBeyondDeliver)){
                    if("1".equals(deliverPlan.getProjectFlag())){
                        if ("BUYER".equals(userType)) {
                            // 供应
                            uniKeyQuery.append("需求");
                            Map<LocalDate, BigDecimal> decimalMap = deliverPlanMap.get(uniKeyQuery.toString());
                            if(null != decimalMap && !decimalMap.isEmpty()){
                                if(CollectionUtils.isNotEmpty(deliverPlanDetails)){
                                    for(DeliverPlanDetail deliverPlanDetail : deliverPlanDetails){
                                        LocalDate schMonthlyDate = deliverPlanDetail.getSchMonthlyDate();
                                        BigDecimal quantityPromised = deliverPlanDetail.getQuantityPromised();
                                        BigDecimal decimal = null != decimalMap.get(schMonthlyDate) ? decimalMap.get(schMonthlyDate) : BigDecimal.ZERO;
                                        if(quantityPromised.compareTo(decimal) > 0){
                                            errorFlag.set(true);
                                            errorMsg.append("供应数量不可大于需求数量; ");
                                            break;
                                        }
                                    }
                                }
                            }else {
                                // 查数据库有没有
                                // 校验是否存在 业务实体+库存组织+到货地点+供应商+物料编码
                                List<DeliverPlan> deliverPlanList = this.list(new QueryWrapper<>(new DeliverPlan().setOrgId(deliverPlan.getOrgId()).
                                        setOrganizationId(deliverPlan.getOrganizationId()).setDeliveryAddress(deliverPlan.getDeliveryAddress()).
                                        setVendorId(deliverPlan.getVendorId()).setMaterialId(deliverPlan.getMaterialId())));
                                if(CollectionUtils.isNotEmpty(deliverPlanList)){
                                    Long deliverPlanId = deliverPlanList.get(0).getDeliverPlanId();
                                    List<DeliverPlanDetail> deliverPlanDetailList = iDeliverPlanDetailService.list(new QueryWrapper<>(new DeliverPlanDetail().setDeliverPlanId(deliverPlanId)));
                                    if(CollectionUtils.isNotEmpty(deliverPlanDetailList)){
                                        Map<LocalDate, BigDecimal> bigDecimalMap = deliverPlanDetailList.stream().collect(Collectors.toMap(DeliverPlanDetail::getSchMonthlyDate, DeliverPlanDetail::getRequirementQuantity));
                                        if(CollectionUtils.isNotEmpty(deliverPlanDetails)){
                                            for(DeliverPlanDetail deliverPlanDetail : deliverPlanDetails){
                                                LocalDate schMonthlyDate = deliverPlanDetail.getSchMonthlyDate();
                                                BigDecimal quantityPromised = deliverPlanDetail.getQuantityPromised();
                                                BigDecimal decimal = null != bigDecimalMap.get(schMonthlyDate) ? bigDecimalMap.get(schMonthlyDate) : BigDecimal.ZERO;
                                                if(quantityPromised.compareTo(decimal) > 0){
                                                    errorFlag.set(true);
                                                    errorMsg.append("供应数量不可大于需求数量; ");
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }else {
                                    errorFlag.set(true);
                                    errorMsg.append("该项目为新增,同一个项目\"需求\"要写在\"供应\"的上面,不然无法判断供应是否大于需求; ");
                                }
                            }
                        }else {
                            // 校验是否存在 业务实体+库存组织+到货地点+供应商+物料编码
                            List<DeliverPlan> deliverPlanList = this.list(new QueryWrapper<>(new DeliverPlan().setOrgId(deliverPlan.getOrgId()).
                                    setOrganizationId(deliverPlan.getOrganizationId()).setDeliveryAddress(deliverPlan.getDeliveryAddress()).
                                    setVendorId(deliverPlan.getVendorId()).setMaterialId(deliverPlan.getMaterialId())));
                            if(CollectionUtils.isNotEmpty(deliverPlanList)){
                                Long deliverPlanId = deliverPlanList.get(0).getDeliverPlanId();
                                List<DeliverPlanDetail> deliverPlanDetailList = iDeliverPlanDetailService.list(new QueryWrapper<>(new DeliverPlanDetail().setDeliverPlanId(deliverPlanId)));
                                if(CollectionUtils.isNotEmpty(deliverPlanDetailList)){
                                    Map<LocalDate, BigDecimal> bigDecimalMap = deliverPlanDetailList.stream().collect(Collectors.toMap(DeliverPlanDetail::getSchMonthlyDate, DeliverPlanDetail::getRequirementQuantity));
                                    if(CollectionUtils.isNotEmpty(deliverPlanDetails)){
                                        for(DeliverPlanDetail deliverPlanDetail : deliverPlanDetails){
                                            LocalDate schMonthlyDate = deliverPlanDetail.getSchMonthlyDate();
                                            BigDecimal quantityPromised = deliverPlanDetail.getQuantityPromised();
                                            BigDecimal decimal = null != bigDecimalMap.get(schMonthlyDate) ? bigDecimalMap.get(schMonthlyDate) : BigDecimal.ZERO;
                                            if(quantityPromised.compareTo(decimal) > 0){
                                                errorFlag.set(true);
                                                errorMsg.append("供应数量不可大于需求数量; ");
                                                break;
                                            }
                                        }
                                    }
                                }
                            }else {
                                errorFlag.set(true);
                                errorMsg.append("该项目不存在,不能导入; ");
                            }
                        }
                    }else {
                        // 需求
                        if (CollectionUtils.isNotEmpty(deliverPlanDetails)){
                            Map<LocalDate, BigDecimal> map = new HashMap<>();
                            deliverPlanDetails.forEach(deliverPlanDetail -> {
                                map.put(deliverPlanDetail.getSchMonthlyDate(),deliverPlanDetail.getRequirementQuantity());
                            });
                            deliverPlanMap.put(uniKey.toString(),map);
                        }
                    }
                }
            }

            deliverPlan.setDeliverPlanDetails(deliverPlanDetails);
            deliverPlans.add(deliverPlan);

            errorList.add(errorMsg.toString());
        }
        return workbook;
    }

    @Override
    public void importLineModelDownload(Long deliverPlanId, HttpServletResponse response) throws Exception {
        Assert.notNull(deliverPlanId, "缺少到货计划ID: deliverPlanId");
        // 构建表格
        Workbook workbook = crateWorkbookLineModel(deliverPlanId);
        // 获取输出流
        ServletOutputStream outputStream = EasyExcelUtil.getServletOutputStream(response, "物料计划导入模板");
        // 导出
        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }

    /**
     * 构建导入模板
     */
    public Workbook crateWorkbookLineModel(Long deliverPlanId) throws ParseException {
        DeliverPlan deliverPlan = this.getById(deliverPlanId);
        Assert.notNull(deliverPlan,"找不到到货计划,Id:"+deliverPlanId);
        String monthlySchDate = deliverPlan.getMonthlySchDate();
        Assert.notNull(monthlySchDate,"请先维护计划月度");
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

        boolean isVendor = AppUserUtil.userIsVendor();

        // 设置固定标题列
        for (int i = 0; i < fixedLineTitle.size(); i++) {
            XSSFCell cell1 = row.createCell(cellIndex);
            if (i != fixedLineTitle.size() - 1) {
                cell1.setCellValue(fixedLineTitle.get(i));
                cell1.setCellStyle(cellStyle);
            } else {
                String msg = "1、项目分别填\"需求\"或\"供应\"，分别对应输入需求数量及承诺供应数量";
                if(isVendor){
                    msg = "1、项目只能填\"供应\"，对应输入承诺供应数量";
                }
                EasyExcelUtil.setCellStyle(workbook, cell1, sheet, msg, fixedLineTitle.get(i));
            }
            cellIndex++;
        }

        // 设置时间动态列
        Date date = DateUtil.parseDate(monthlySchDate);
        LocalDate localDate = DateUtil.dateToLocalDate(date);
        List<String> dayBetween = DateUtil.getDayBetween(localDate, "yyyy-MM-dd");
        for (int i = 0; i < dayBetween.size(); i++) {
            Cell cell1 = row.createCell(cellIndex);
            cell1.setCellValue(dayBetween.get(i));
            cell1.setCellStyle(cellStyle);
            cellIndex++;
        }
        return workbook;
    }

    @Override
    public Map<String, Object> importLineExcel(MultipartFile file, Long deliverPlanId, Fileupload fileupload) throws Exception {
        Assert.notNull(deliverPlanId,"缺少参数");
        // 校验id
        DeliverPlan deliverPlan = this.getById(deliverPlanId);
        Assert.notNull(deliverPlan,"找不到该到货计划:"+deliverPlanId);
        //校验是否是超计划物料
        // 到货单号
        String deliverPlanNum = deliverPlan.getDeliverPlanNum();
        Assert.notNull(deliverPlanNum,"到货单号为空");
        // 检查上传文件
        EasyExcelUtil.checkParam(file, fileupload);
        // 获取文件输入流
        InputStream inputStream = file.getInputStream();
        // 导入文件是否有错误信息标志
        AtomicBoolean errorFlag = new AtomicBoolean(false);
        // 导入的数据
        Map<String,List<DeliverPlanDetail>> deliverPlanDetailTyppeMap = new HashMap<>();
        // 每行报错信息
        List<String> errorList = new ArrayList<>();
        // 返回信息
        Map<String, Object> result = new HashMap<>();
        result.put("status", YesOrNo.YES.getValue());
        result.put("message", "success");
        // 获取导入数据
        Workbook workbook = this.getImportLineData(deliverPlan,inputStream, errorList, errorFlag,deliverPlanDetailTyppeMap);
        if (errorFlag.get()) {
            // 有报错,上传报错文件
            this.uploadErrorFile(file, fileupload, errorList, result, workbook);
        }else {
            List<DeliverPlanDetail> deliverPlanDetails0 = deliverPlanDetailTyppeMap.get("0");
            List<DeliverPlanDetail> deliverPlanDetails1 = deliverPlanDetailTyppeMap.get("1");
            HashMap<LocalDate, DeliverPlanDetail> planDetailHashMap = new HashMap<>();
            if(CollectionUtils.isNotEmpty(deliverPlanDetails0)){
                deliverPlanDetails0.forEach(deliverPlanDetail -> {
                    LocalDate schMonthlyDate = deliverPlanDetail.getSchMonthlyDate();
                    planDetailHashMap.put(schMonthlyDate,deliverPlanDetail);
                });
            }
            if(CollectionUtils.isNotEmpty(deliverPlanDetails1)){
                deliverPlanDetails1.forEach(deliverPlanDetail -> {
                    LocalDate schMonthlyDate = deliverPlanDetail.getSchMonthlyDate();
                    DeliverPlanDetail planDetail = planDetailHashMap.get(schMonthlyDate);
                    if(null != planDetail){
                        planDetail.setQuantityPromised(deliverPlanDetail.getQuantityPromised());
                        planDetailHashMap.put(schMonthlyDate,planDetail);
                    }
                });
            }
            ArrayList<DeliverPlanDetail> deliverPlanDetails = new ArrayList<>();
            if(!planDetailHashMap.isEmpty()){
                planDetailHashMap.forEach((localDate, deliverPlanDetail) -> {
                    deliverPlanDetails.add(deliverPlanDetail);
                });
            }

            if(CollectionUtils.isNotEmpty(deliverPlanDetails)){
                deliverPlanDetails.forEach(deliverPlanDetail -> {
                    /**
                     * 先查询是否存在, 存在则更新, 不存在则新增
                     * 查询 头id + 日期
                     */
                    DeliverPlanDetail planDetail = iDeliverPlanDetailService.getOne(new QueryWrapper<>(new DeliverPlanDetail().
                            setDeliverPlanId(deliverPlanId).setSchMonthlyDate(deliverPlanDetail.getSchMonthlyDate())));
                    if(null != planDetail){
                        // 更新
                        // 判断是否锁定
                        if (!"1".equals(planDetail.getDeliverPlanLock())) {
                            if(StringUtil.notEmpty(deliverPlanDetail.getRequirementQuantity())){
                                planDetail.setRequirementQuantity(deliverPlanDetail.getRequirementQuantity());
                            }
                            if(StringUtil.notEmpty(deliverPlanDetail.getQuantityPromised())){
                                planDetail.setQuantityPromised(deliverPlanDetail.getQuantityPromised());
                            }
                            // 状态判断
                            if(planDetail.getQuantityPromised().compareTo(BigDecimal.ZERO) != 0 && planDetail.getRequirementQuantity().compareTo(BigDecimal.ZERO) != 0
                                    && planDetail.getRequirementQuantity().compareTo(planDetail.getQuantityPromised()) == 0){
                                planDetail.setDeliverPlanStatus(DeliverPlanLineStatus.COMFIRM.name());
                            }
                            iDeliverPlanDetailService.updateById(planDetail);
                        }
                    }else {
                        // 新增
                        deliverPlanDetail.setDeliverPlanId(deliverPlanId);
                        deliverPlanDetail.setDeliverPlanDetailId(IdGenrator.generate());
                        deliverPlanDetail.setDeliverPlanLock("2");
                        deliverPlanDetail.setDeliverPlanStatus(DeliverPlanLineStatus.UNCOMFIRMED.name());
                        iDeliverPlanDetailService.save(deliverPlanDetail);
                    }
                });
            }
        }
        this.baseMapper.updateSchTotalQuantity(deliverPlanId);
        return result;
    }

    /**
     * 获取数据并校验
     * @param inputStream
     * @param errorList
     * @param errorFlag
     * @return
     * @throws IOException
     * @throws ParseException
     */
    private Workbook getImportLineData(DeliverPlan deliverPlan,InputStream inputStream, List<String> errorList, AtomicBoolean errorFlag,Map<String,List<DeliverPlanDetail>> deliverPlanDetailTyppeMap) throws IOException, ParseException {
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

        // 是否供应商标识
        boolean isVendor = AppUserUtil.userIsVendor();

        String ceeaIfBeyondDeliver = YesOrNo.NO.getValue();

        HashSet<String> hashSet = new HashSet<>();

        List<MaterialItem> materialItems = baseClient.listMaterialByParam(new MaterialItem().setMaterialId(deliverPlan.getMaterialId()));
        // 检查物料信息
        StringBuffer errorHead = new StringBuffer();
        if(CollectionUtils.isNotEmpty(materialItems)){
            String struct = materialItems.get(0).getStruct();
            if (StringUtil.notEmpty(struct)) {
                PurchaseCategory purchaseCategory = baseClient.checkByIfDeliverPlan(struct);
                if(null != purchaseCategory){
                    ceeaIfBeyondDeliver = StringUtil.notEmpty(purchaseCategory.getCeeaIfBeyondDeliver()) ? purchaseCategory.getCeeaIfBeyondDeliver() : "N";
                }else {
                    errorHead.append("该单据物料品类在品类维护里不存在; ");
                }
            }else {
                errorHead.append("该单据物料的品类没维护; ");
            }
        }else {
            errorHead.append("找不到该单据物料; ");
        }

        int sum = 0;
        // 遍历内容行获取数据,从2行开始,也就是行的下标为1
        for (int r = 1; r <= totalRows; r++) {
            log.info("第"+r+"次循环");
            if (errorHead.length() < 1) {
                // 类型标记, 0-需求 1-供应
                String type = "0";

                Row row = sheet.getRow(r);
                if (null == row) {
                    // 过滤空行,空行一下内容全部上移一行
                    sheet.shiftRows(r + 1, totalRows, -1);
                    r--;
                    totalRows--;
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
                    totalRows--;
                    continue;

                }
// <------------------------------------一下正式开始获取表格内容-------------------------------------------->
                // 收集每行错误信息
                StringBuffer errorMsg = new StringBuffer();
                // 送货单号
                Cell cell0 = row.getCell(0);
                String deliverCode = ExcelUtil.getCellValue(cell0);
                if(StringUtil.notEmpty(deliverCode)){
                    deliverCode = deliverCode.trim();
                    if(!deliverPlan.getDeliverPlanNum().equals(deliverCode)){
                        errorFlag.set(true);
                        errorMsg.append("到货计划号不是当前到货计划号; ");
                    }
                }else {
                    errorFlag.set(true);
                    errorMsg.append("到货计划号不能为空; ");
                }

                // 项目 : 需求-需求数量  供应-承诺供应数量
                Cell cell1 = row.getCell(1);
                String project = ExcelUtil.getCellValue(cell1);
                if (StringUtil.notEmpty(project)) {
                    project = project.trim();
                    if(!hashSet.add(project)){
                        errorFlag.set(true);
                        errorMsg.append("单个到货计划单不能存在重复项目; ");
                    }
                    if (!isVendor) {
                        if ("需求".equals(project) || "供应".equals(project)) {
                            if ("供应".equals(project)) {
                                type = "1";
                            }
                        } else {
                            errorFlag.set(true);
                            errorMsg.append("项目只能填: \"需求\"或\"供应\"; ");
                        }
                    }else {
                        if ("供应".equals(project)) {
                            type = "1";
                        } else {
                            errorFlag.set(true);
                            errorMsg.append("项目只能填: \"供应\"; ");
                        }
                    }
                } else {
                    errorFlag.set(true);
                    errorMsg.append("项目不能为空; ");
                }

                sum +=1;

                // 遍历获取需求数量
                ArrayList<DeliverPlanDetail> planDetailArrayList = new ArrayList<>();
                for (int c = 2; c < totalCells; c++) {
                    Cell cell = row.getCell(c);
                    String cellValue = ExcelUtil.getCellValue(cell);
                    if (StringUtil.notEmpty(cellValue)) {
                        cellValue = cellValue.trim();
                    }else {
                        cellValue = "0";
                    }
                    if (StringUtil.isDigit(cellValue)) {
                        // 计划日期
                        String dateStr = head.get(c);
                        dateStr = dateStr.trim();
                        Date date = DateUtil.parseDate(dateStr);
                        LocalDate localDate = DateUtil.dateToLocalDate(date);
                        DeliverPlanDetail deliverPlanDetail = new DeliverPlanDetail();;
                        deliverPlanDetail.setSchMonthlyDate(localDate);
                        if ("0".equals(type)) {
                            // 需求
                            deliverPlanDetail.setRequirementQuantity(new BigDecimal(cellValue));
                            planDetailArrayList.add(deliverPlanDetail);
                        } else {
                            // 供应
                            deliverPlanDetail.setQuantityPromised(new BigDecimal(cellValue));
                            planDetailArrayList.add(deliverPlanDetail);
                        }
                    } else {
                        errorFlag.set(true);
                        errorMsg.append(cellValue + "非数字; ");
                    }
                }
                if(CollectionUtils.isNotEmpty(planDetailArrayList)){
                    deliverPlanDetailTyppeMap.put(type,planDetailArrayList);
                }

                if(!errorFlag.get() && YesOrNo.NO.getValue().equals(ceeaIfBeyondDeliver)){
                    if(isVendor){
                        // 供应商
                        List<DeliverPlanDetail> deliverPlanDetails = deliverPlanDetailTyppeMap.get("1");
                        // 查数据库需求数量
                        List<DeliverPlanDetail> deliverPlanDetailList = iDeliverPlanDetailService.list(new QueryWrapper<>(new DeliverPlanDetail().setDeliverPlanId(deliverPlan.getDeliverPlanId())));
                        if(CollectionUtils.isNotEmpty(deliverPlanDetailList)){
                            HashMap<LocalDate, DeliverPlanDetail> deliverPlanDetailMap = new HashMap<>();
                            deliverPlanDetailList.forEach(deliverPlanDetail -> {
                                LocalDate schMonthlyDate = deliverPlanDetail.getSchMonthlyDate();
                                deliverPlanDetailMap.put(schMonthlyDate,deliverPlanDetail);
                            });
                            if(CollectionUtils.isNotEmpty(deliverPlanDetails)){
                                for(DeliverPlanDetail deliverPlanDetail : deliverPlanDetails){
                                    LocalDate schMonthlyDate = deliverPlanDetail.getSchMonthlyDate();
                                    DeliverPlanDetail deliverPlanDetail1 = deliverPlanDetailMap.get(schMonthlyDate);
                                    if(null != deliverPlanDetail1){
                                        BigDecimal requirementQuantity = deliverPlanDetail1.getRequirementQuantity();
                                        if(deliverPlanDetail.getQuantityPromised().compareTo(requirementQuantity) > 0){
                                            errorFlag.set(true);
                                            errorMsg.append("供应数量不可大于需求数量");
                                            break;
                                        }
                                    }else {
                                        errorFlag.set(true);
                                        errorMsg.append("供应数量不可大于需求数量");
                                        break;
                                    }
                                }
                            }
                        }else {
                            errorFlag.set(true);
                            errorMsg.append("该到货计划没有需求数据量不能导入");
                        }
                    }else {
                        // 采购商
                        if("1".equals(type)){
                            // 供应
                            /**
                             * 判断当前是否第一次循环,如果是第一次循环,在判断是否还有需求行, 有的话就报错
                             *
                             */
                            if(1 == sum){
                                // 当前第一次寻源, 在判断是否有需求行
                                if(totalRows == 1){
                                    // 只有供应
                                    List<DeliverPlanDetail> deliverPlanDetails = deliverPlanDetailTyppeMap.get("1");
                                    // 查数据库需求数量
                                    List<DeliverPlanDetail> deliverPlanDetailList = iDeliverPlanDetailService.list(new QueryWrapper<>(new DeliverPlanDetail().setDeliverPlanId(deliverPlan.getDeliverPlanId())));
                                    if(CollectionUtils.isNotEmpty(deliverPlanDetailList)){
                                        HashMap<LocalDate, DeliverPlanDetail> deliverPlanDetailMap = new HashMap<>();
                                        deliverPlanDetailList.forEach(deliverPlanDetail -> {
                                            LocalDate schMonthlyDate = deliverPlanDetail.getSchMonthlyDate();
                                            deliverPlanDetailMap.put(schMonthlyDate,deliverPlanDetail);
                                        });
                                        if(CollectionUtils.isNotEmpty(deliverPlanDetails)){
                                            for(DeliverPlanDetail deliverPlanDetail : deliverPlanDetails){
                                                LocalDate schMonthlyDate = deliverPlanDetail.getSchMonthlyDate();
                                                DeliverPlanDetail deliverPlanDetail1 = deliverPlanDetailMap.get(schMonthlyDate);
                                                if(null != deliverPlanDetail1){
                                                    BigDecimal requirementQuantity = deliverPlanDetail1.getRequirementQuantity();
                                                    if(deliverPlanDetail.getQuantityPromised().compareTo(requirementQuantity) > 0){
                                                        errorFlag.set(true);
                                                        errorMsg.append("供应数量不可大于需求数量");
                                                        break;
                                                    }
                                                }else {
                                                    errorFlag.set(true);
                                                    errorMsg.append("供应数量不可大于需求数量");
                                                    break;
                                                }
                                            }
                                        }
                                    }else {
                                        errorFlag.set(true);
                                        errorMsg.append("该到货计划没有需求数据量不能导入");
                                    }
                                }else if(totalRows == 2){
                                    // 还有需求行
                                    // 需求
                                    errorFlag.set(true);
                                    errorMsg.append("同一个项目导入,需求要写在供应的前面,以便于判断是否超计划; ");
                                }
                            }else if(sum == 2){
                                List<DeliverPlanDetail> deliverPlanDetails0 = deliverPlanDetailTyppeMap.get("0");
                                List<DeliverPlanDetail> deliverPlanDetails1 = deliverPlanDetailTyppeMap.get("1");
                                if(CollectionUtils.isNotEmpty(deliverPlanDetails0)){
                                    HashMap<LocalDate, DeliverPlanDetail> deliverPlanDetailMap = new HashMap<>();
                                    deliverPlanDetails0.forEach(deliverPlanDetail -> {
                                        LocalDate schMonthlyDate = deliverPlanDetail.getSchMonthlyDate();
                                        deliverPlanDetailMap.put(schMonthlyDate,deliverPlanDetail);
                                    });
                                    if(CollectionUtils.isNotEmpty(deliverPlanDetails1)){
                                        for(DeliverPlanDetail deliverPlanDetail : deliverPlanDetails1){
                                            LocalDate schMonthlyDate = deliverPlanDetail.getSchMonthlyDate();
                                            DeliverPlanDetail deliverPlanDetail1 = deliverPlanDetailMap.get(schMonthlyDate);
                                            if(null != deliverPlanDetail1){
                                                BigDecimal requirementQuantity = deliverPlanDetail1.getRequirementQuantity();
                                                if(deliverPlanDetail.getQuantityPromised().compareTo(requirementQuantity) > 0){
                                                    errorFlag.set(true);
                                                    errorMsg.append("供应数量不可大于需求数量");
                                                    break;
                                                }
                                            }else {
                                                errorFlag.set(true);
                                                errorMsg.append("供应数量不可大于需求数量");
                                                break;
                                            }
                                        }
                                    }
                                }else {
                                    errorFlag.set(true);
                                    errorMsg.append("该到货计划没有需求数据量不能导入");
                                }
                            }
                        }
                    }
                }
                // 汇总错误信息
                errorList.add(errorMsg.toString());
            }else {
                // 汇总错误信息
                errorFlag.set(true);
                errorList.add(errorHead.toString());
            }
        }
        return workbook;
    }

    @Override
    public void export(DeliverPlanDTO deliverPlanDTO, HttpServletResponse response) throws Exception {
        if(StringUtil.isEmpty(deliverPlanDTO.getMonthlySchDate())){
            throw new BaseException("请选择计划月度");
        }
        String monthlySchDate = deliverPlanDTO.getMonthlySchDate();
        Workbook workbook = crateWorkbookModel(monthlySchDate);
        List<DeliverPlan> deliverPlans = getDeliverPlans(deliverPlanDTO);
        if(CollectionUtils.isNotEmpty(deliverPlans)){
            deliverPlans.forEach(deliverPlan -> {
                // 需求
                HashMap<String, String> demandNumMap = new HashMap<>();
                // 供应
                HashMap<String, String> supplyNumMap = new HashMap<>();
                List<DeliverPlanDetail> deliverPlanDetails = iDeliverPlanDetailService.list(new QueryWrapper<>(new DeliverPlanDetail().setDeliverPlanId(deliverPlan.getDeliverPlanId())));
                if(CollectionUtils.isNotEmpty(deliverPlanDetails)){
                    deliverPlanDetails.forEach(deliverPlanDetail -> {
                        // 日期
                        LocalDate schMonthlyDate = deliverPlanDetail.getSchMonthlyDate();
                        String schMonthly = DateUtil.localDateToStr(schMonthlyDate);
                        // 供应
                        BigDecimal quantityPromised = deliverPlanDetail.getQuantityPromised();
                        if(StringUtil.isEmpty(quantityPromised)){
                            supplyNumMap.put(schMonthly,"0");
                        }else if (quantityPromised.compareTo(BigDecimal.ZERO) == 0){
                            supplyNumMap.put(schMonthly,"0");
                        }else {
                            supplyNumMap.put(schMonthly,StringUtil.subZeroAndDot(String.valueOf(quantityPromised.doubleValue())));
                        }
                        // 需求
                        BigDecimal requirementQuantity = deliverPlanDetail.getRequirementQuantity();
                        if(StringUtil.isEmpty(requirementQuantity)){
                            demandNumMap.put(schMonthly,"0");
                        }else if (requirementQuantity.compareTo(BigDecimal.ZERO) == 0){
                            demandNumMap.put(schMonthly,"0");
                        }else {
                            demandNumMap.put(schMonthly,StringUtil.subZeroAndDot(String.valueOf(requirementQuantity.doubleValue())));
                        }
                    });
                }
                deliverPlan.setDemandNumMap(demandNumMap);
                deliverPlan.setSupplyNumMap(supplyNumMap);
            });

            // 获取指定工作表
            Sheet sheet = workbook.getSheetAt(0);
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

            // 行的下标
            int rowIndex = 1;
            for(int i=0; i < deliverPlans.size();i++){
                DeliverPlan deliverPlan = deliverPlans.get(i);
                // 设置需求内容
                setContenx(deliverPlan.getDemandNumMap(),sheet, totalCells, head, rowIndex, deliverPlan,"需求");
                rowIndex += 1;
                // 设置供应内容
                setContenx(deliverPlan.getSupplyNumMap(),sheet, totalCells, head, rowIndex, deliverPlan,"供应");
                rowIndex += 1;
            }
        }

        ServletOutputStream outputStream = EasyExcelUtil.getServletOutputStream(response, "供货计划导出");
        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }

    private void setContenx(Map<String, String> map,Sheet sheet, int totalCells, List<String> head, int rowIndex, DeliverPlan deliverPlan,String project) {
        Row row = sheet.createRow(rowIndex);
        // "*业务实体", "*库存组织", "*交货地点", "*供应商名称", "*物料编码", "*项目"
        Cell cell0 = row.createCell(0);
        cell0.setCellValue(deliverPlan.getOrgName());
        Cell cell1 = row.createCell(1);
        cell1.setCellValue(deliverPlan.getOrganizationName());
        Cell cell2 = row.createCell(2);
        cell2.setCellValue(deliverPlan.getDeliveryAddress());
        Cell cell3 = row.createCell(3);
        cell3.setCellValue(deliverPlan.getVendorName());
        Cell cell4 = row.createCell(4);
        cell4.setCellValue(deliverPlan.getMaterialCode());
        Cell cell5 = row.createCell(5);
        cell5.setCellValue(project);
        for(int j = 6;j < totalCells;j++){
            String title = head.get(j);
            Cell cell = row.createCell(j);
            if(StringUtil.notEmpty(map.get(title))){
                cell.setCellValue(map.get(title));
            }else {
                cell.setCellValue("0");
            }
        }
    }

    @Override
    public void exportLine(Long deliverPlanId, HttpServletResponse response) throws Exception {
        Assert.notNull(deliverPlanId,"缺少参数:"+deliverPlanId);
        // 计划月度
        Workbook workbook = crateWorkbookLineModel(deliverPlanId);
        DeliverPlan deliverPlan = this.getById(deliverPlanId);
        // 需求
        HashMap<String, String> demandNumMap = new HashMap<>();
        // 供应
        HashMap<String, String> supplyNumMap = new HashMap<>();
        List<DeliverPlanDetail> deliverPlanDetails = iDeliverPlanDetailService.list(new QueryWrapper<>(new DeliverPlanDetail().setDeliverPlanId(deliverPlanId)));
        if(CollectionUtils.isNotEmpty(deliverPlanDetails)){
            deliverPlanDetails.forEach(deliverPlanDetail -> {
                // 日期
                LocalDate schMonthlyDate = deliverPlanDetail.getSchMonthlyDate();
                String schMonthly = DateUtil.localDateToStr(schMonthlyDate);
                // 供应
                BigDecimal quantityPromised = deliverPlanDetail.getQuantityPromised();
                if(StringUtil.isEmpty(quantityPromised)){
                    supplyNumMap.put(schMonthly,"0");
                }else if (quantityPromised.compareTo(BigDecimal.ZERO) == 0){
                    supplyNumMap.put(schMonthly,"0");
                }else {
                    supplyNumMap.put(schMonthly,StringUtil.subZeroAndDot(String.valueOf(quantityPromised.doubleValue())));
                }
                // 需求
                BigDecimal requirementQuantity = deliverPlanDetail.getRequirementQuantity();
                if(StringUtil.isEmpty(requirementQuantity)){
                    demandNumMap.put(schMonthly,"0");
                }else if (requirementQuantity.compareTo(BigDecimal.ZERO) == 0){
                    demandNumMap.put(schMonthly,"0");
                }else {
                    demandNumMap.put(schMonthly,StringUtil.subZeroAndDot(String.valueOf(requirementQuantity.doubleValue())));
                }
            });
        }

        // 获取指定工作表
        Sheet sheet = workbook.getSheetAt(0);
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

        // 行的下标
        int rowIndex = 1;
        for(int i=0; i < 1;i++){
            // 设置需求内容
            setContentLine(deliverPlan, demandNumMap, sheet, totalCells, head, rowIndex,"需求");
            rowIndex += 1;

            // 设置供应内容
            setContentLine(deliverPlan, supplyNumMap, sheet, totalCells, head, rowIndex,"供应");
            rowIndex += 1;
        }

        ServletOutputStream outputStream = EasyExcelUtil.getServletOutputStream(response, "供货计划详情导出");
        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();

    }

    @Override
    public void exportLineCopy(Long deliverPlanId, HttpServletResponse response) throws Exception {
        Assert.notNull(deliverPlanId,"缺少参数:"+deliverPlanId);
        // 计划月度
        Workbook workbook = crateWorkbookLineModel(deliverPlanId);
        DeliverPlan deliverPlan = this.getById(deliverPlanId);
        // 供应
        HashMap<String, String> supplyNumMap = new HashMap<>();
        List<DeliverPlanDetail> deliverPlanDetails = iDeliverPlanDetailService.list(new QueryWrapper<>(new DeliverPlanDetail().setDeliverPlanId(deliverPlanId)));
        if(CollectionUtils.isNotEmpty(deliverPlanDetails)){
            deliverPlanDetails.forEach(deliverPlanDetail -> {
                // 日期
                LocalDate schMonthlyDate = deliverPlanDetail.getSchMonthlyDate();
                String schMonthly = DateUtil.localDateToStr(schMonthlyDate);
                // 供应
                BigDecimal quantityPromised = deliverPlanDetail.getQuantityPromised();
                if(StringUtil.isEmpty(quantityPromised)){
                    supplyNumMap.put(schMonthly,"0");
                }else if (quantityPromised.compareTo(BigDecimal.ZERO) == 0){
                    supplyNumMap.put(schMonthly,"0");
                }else {
                    supplyNumMap.put(schMonthly,StringUtil.subZeroAndDot(String.valueOf(quantityPromised.doubleValue())));
                }
            });
        }

        // 获取指定工作表
        Sheet sheet = workbook.getSheetAt(0);
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

        // 行的下标
        int rowIndex = 1;
        for(int i=0; i < 1;i++){
            // 设置供应内容
            setContentLine(deliverPlan, supplyNumMap, sheet, totalCells, head, rowIndex,"供应");
            rowIndex += 1;
        }

        ServletOutputStream outputStream = EasyExcelUtil.getServletOutputStream(response, "供货计划详情导出");
        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();

    }


    private void setContentLine(DeliverPlan deliverPlan, HashMap<String, String> demandNumMap, Sheet sheet, int totalCells, List<String> head, int rowIndex,String project) {
        Row row = sheet.createRow(rowIndex);
        // *到货计划号","*项目
        Cell cell0 = row.createCell(0);
        cell0.setCellValue(deliverPlan.getDeliverPlanNum());
        Cell cell1 = row.createCell(1);
        cell1.setCellValue(project);

        for(int j = 2;j < totalCells;j++){
            String title = head.get(j);
            Cell cell = row.createCell(j);
            if(StringUtil.notEmpty(demandNumMap.get(title))){
                cell.setCellValue(demandNumMap.get(title));
            }else {
                cell.setCellValue("0");
            }
        }
    }

@Override
    public List<DeliverPlanDetail> getDeliverPlanList(DeliverPlanDTO deliverPlanDTO) {
/*    QueryWrapper<DeliverPlanDTO> wrapper = new QueryWrapper<>();
        //状态
        wrapper.ne(StringUtils.isNotEmpty(deliverPlanDTO.getDeliverPlanStatus()), "a.DELIVER_PLAN_STATUS", DeliverPlanStatus.DRAFT.toString());
        //物料小类多条件查询
        //wrapper.in(CollectionUtils.isNotEmpty(deliverPlanDTO.getCategoryIds()), "a.CATEGORY_ID", deliverPlanDTO.getCategoryIds());
        //到货计划物料小类筛选
        //wrapper.in("(a.CATEGORY_ID,b.SCH_MONTHLY_DATE)",deliverPlanDTO.getDeliverPlanVOS());
        //获取已确认的到货计划
        wrapper.eq("b.DELIVER_PLAN_STATUS",DeliverPlanLineStatus.COMFIRM.toString());
        //获取到货计划行未锁定
        wrapper.ne("b.DELIVER_PLAN_LOCK","1");
        wrapper.orderByDesc("b.SCH_MONTHLY_DATE");*/
        return deliverPlanMapper.getDeliverPlanList(deliverPlanDTO);
    }


    /**
     *送货预示计划（创建到货计划）
     * @param deliverPlanDTO
     */
    @Override
    @Transactional
    public DeliverPlanDTO getDeliverPlanMRPList(DeliverPlanDTO deliverPlanDTO) throws Exception{
        //根据：库存组织、供应商、地点、物料、月度计划判断唯一性
        //库存组织-->推过来的是库存组织erpID--->转换srm库存组织
        String organizationCode = deliverPlanDTO.getOrganizationCode();
        Organization organization = getOrganization(organizationCode);

        Organization orgids = getOrgids(Long.valueOf(organization.getParentOrganizationIds()));

        //供应商ERPcode---->SRM供应商code
        String vendorCode = deliverPlanDTO.getVendorCode();
        CompanyInfo erpCodes = getErpCodes(vendorCode);
        //地点
        String deliveryAddress = deliverPlanDTO.getDeliveryAddress();
        //物料code
        String materialCode = deliverPlanDTO.getMaterialCode();
        MaterialItem materialItem = getMaterialItemMap(materialCode);
        //物料小类
        Long categoryId = materialItem.getCategoryId();
        PurchaseCategory purchaseCategory = baseClient.MinByIfDeliverPlan(categoryId);
        Assert.notNull(purchaseCategory,"该物料不是执行到货计划物料，请确认后重试");
        Assert.notNull(purchaseCategory.getCategoryId(),"该物料不是执行到货计划物料，请确认后重试");

        //获取到货计划明细行
        List<DeliverPlanDetail> deliverPlanDetailList = deliverPlanDTO.getDeliverPlanDetailList();
        //按年月分组---》 2020-09 和2020-10分组
        Map<String, List<DeliverPlanDetail>> DeliverPlanDetailMap = deliverPlanDetailList.stream().collect(Collectors.groupingBy(o -> ""+o.getSchMonthlyDate().getYear()+"-"+String.format("%02d",o.getSchMonthlyDate().getMonthValue())));
        log.info("=========================开始处理数据===============================================");
        ArrayList<String> numberList = new ArrayList<>();

        //按年月分组
        for (String dataString:DeliverPlanDetailMap.keySet()){
            //获取对应行
            List<DeliverPlanDetail> deliverPlanDetails = DeliverPlanDetailMap.get(dataString);
            //尝试获取存在的到货计划进行修改
            DeliverPlanDTO deliverPlanDTOCopy = new DeliverPlanDTO();
            deliverPlanDTOCopy.setOrganizationId(organization.getOrganizationId())
                    .setOrganizationName(organization.getOrganizationName())
                    .setOrganizationCode(organization.getOrganizationCode())
                    .setVendorId(erpCodes.getCompanyId())
                    .setVendorCode(erpCodes.getCompanyCode())
                    .setVendorName(erpCodes.getCompanyName())
                    .setMaterialId(materialItem.getMaterialId())
                    .setMaterialCode(materialItem.getMaterialCode())
                    .setMaterialName(materialItem.getMaterialName())
                    .setCategoryId(purchaseCategory.getCategoryId())
                    .setCategoryCode(purchaseCategory.getCategoryCode())
                    .setCategoryName(purchaseCategory.getCategoryName())
                    .setDeliveryAddress(deliveryAddress)
                    .setOrgId(orgids.getOrganizationId())
                    .setOrgName(orgids.getOrganizationName())
                    .setOrgCode(orgids.getOrganizationCode())
                    .setMonthlySchDate(dataString);
            DeliverPlanDTO deliverPlanCopy = getDeliverPlanCopy(deliverPlanDTOCopy);
            //组装DTO准备往业务表插
            numberList.add(packageDeliverPlanDTO(deliverPlanDTOCopy, deliverPlanCopy, deliverPlanDetails));
        }
        deliverPlanDTO.setDeliverPlanNum(numberList.get(0));
        return deliverPlanDTO;
    }
    /**
     *到货计划(送货通知)
     * @param deliverPlanDTO
     */
    @Override
    public DeliverPlanDTO getDeliverPlanMessageMRP(DeliverPlanDTO deliverPlanDTO)throws Exception{
        //获取到货计划号
        String deliverPlanNum = deliverPlanDTO.getDeliverPlanNum();
        Assert.isTrue(StringUtils.isNotEmpty(deliverPlanNum),"srm到货计划单号为空");
        DeliverPlanDTO deliverPlanDTO1 = new DeliverPlanDTO();
        deliverPlanDTO1.setDeliverPlanNum(deliverPlanNum);
        List<DeliverPlan> deliverPlanListCopy = getDeliverPlanListCopy(deliverPlanDTO1);
        Assert.isTrue(CollectionUtils.isNotEmpty(deliverPlanListCopy),"srm找不到对应的到货计划单，单号为："+deliverPlanNum);
        //获取srm的到货计划单
        Long deliverPlanId = deliverPlanListCopy.get(0).getDeliverPlanId();
        DeliverPlanDTO deliverPlan = getDeliverPlan(deliverPlanId);
        //获取srm的到货计划明细行
        List<DeliverPlanDetail> srmDeliverPlanDetailList = deliverPlan.getDeliverPlanDetailList();
        Map<LocalDate, DeliverPlanDetail> srmDeliverPlanDetailMap = srmDeliverPlanDetailList.stream().collect(Collectors.toMap(DeliverPlanDetail::getSchMonthlyDate, Function.identity()));

        srmDeliverPlanDetailList=new ArrayList<>();

        //BRP获取到货计划明细行
        List<DeliverPlanDetail> brpDeliverPlanDetailList = deliverPlanDTO.getDeliverPlanDetailList();
        //获取需要锁定的啊行
        for (DeliverPlanDetail x:brpDeliverPlanDetailList){
            DeliverPlanDetail deliverPlanDetail = srmDeliverPlanDetailMap.get(x.getSchMonthlyDate());
            if (null!=deliverPlanDetail){
                srmDeliverPlanDetailList.add(deliverPlanDetail);
            }
        }
        deliverPlanJob.getDeliverPlanLock(srmDeliverPlanDetailList,deliverPlanId);
        return deliverPlanDTO;
    }

    public Organization getOrganization(String OrganizationCode)throws Exception{
        ArrayList<String> longs = new ArrayList<>();
        longs.add(OrganizationCode);
        List<Organization> organizations = baseClient.listByErpOrgId(longs);
        if (CollectionUtils.isEmpty(organizations)){
            Assert.isTrue(false,"库存组织："+OrganizationCode+"。找不到");
        }
        return organizations.get(0);
    }

    public CompanyInfo  getErpCodes(String erpCode) {
        ArrayList<String> longs = new ArrayList<>();
        longs.add(erpCode);
        List<CompanyInfo> erpCodes = supplierClient.getErpCodes(longs);
        if (CollectionUtils.isEmpty(erpCodes)){
            Assert.isTrue(false,"供应商："+erpCode+"。找不到");
        }
        return erpCodes.get(0);
    }
    public MaterialItem getMaterialItemMap(String itemCode){
        ArrayList<String> longs = new ArrayList<>();
        longs.add(itemCode);
        /* 获取传入的采购申请报文的所有行物料编码 */
        Map<String, MaterialItem> materialItemMap = baseClient.listMaterialItemsByCodes(longs);
        if(materialItemMap.isEmpty()){
            Assert.isTrue(false,"物料："+itemCode+"。找不到");
        }
        for (String s:materialItemMap.keySet()){
            return  materialItemMap.get(s);
        }
        return null;
    }

    public DeliverPlanDTO getDeliverPlanCopy(DeliverPlanDTO deliverPlanDTO){
        List<DeliverPlan> deliverPlanListCopy = getDeliverPlanListCopy(deliverPlanDTO);
        if (CollectionUtils.isNotEmpty(deliverPlanListCopy)){
            return getDeliverPlan(deliverPlanListCopy.get(0).getDeliverPlanId());
        }else {
           return null;
        }


    }
    public List<DeliverPlan> getDeliverPlanListCopy(DeliverPlanDTO deliverPlanDTO) {
        QueryWrapper<DeliverPlan> wrapper = new QueryWrapper<>();
        //库存组织多条件查询
        wrapper.eq(null!=deliverPlanDTO.getOrganizationId(), "ORGANIZATION_ID", deliverPlanDTO.getOrganizationId());
        //交货地点模糊查询
        wrapper.eq(StringUtils.isNotEmpty(deliverPlanDTO.getDeliveryAddress()), "DELIVERY_ADDRESS", deliverPlanDTO.getDeliveryAddress());
        //供应商ID查询
        wrapper.eq(null!=deliverPlanDTO.getVendorId(),"VENDOR_ID", deliverPlanDTO.getVendorId());
        //供应商编号查询
        wrapper.eq(StringUtils.isNotEmpty(deliverPlanDTO.getVendorCode()), "VENDOR_CODE", deliverPlanDTO.getVendorCode());
        //物料编号查询模糊查询
        wrapper.eq(StringUtils.isNotEmpty(deliverPlanDTO.getMaterialCode()), "MATERIAL_CODE", deliverPlanDTO.getMaterialCode());
        //月度计划条件查询
        wrapper.eq(StringUtils.isNotEmpty(deliverPlanDTO.getMonthlySchDate()), "MONTHLY_SCH_DATE", deliverPlanDTO.getMonthlySchDate());
        //到货计划号模糊查询
        wrapper.like(StringUtils.isNotEmpty(deliverPlanDTO.getDeliverPlanNum()), "DELIVER_PLAN_NUM", deliverPlanDTO.getDeliverPlanNum());
        wrapper.orderByDesc("LAST_UPDATE_DATE");
        return this.list(wrapper);
    }

    /**
     *
     * @param deliverPlanDTOCopy 组装的对象
     * @param deliverPlanDTOs 存在的
     * @param deliverPlanDetails 到货计划明细行
     * @return
     */
    @Transactional
    public String packageDeliverPlanDTO(DeliverPlanDTO deliverPlanDTOCopy,DeliverPlanDTO deliverPlanDTOs,List<DeliverPlanDetail> deliverPlanDetails){
        String number=null;
        //不存在直接用组装的对象，设置id，code
        if (null==deliverPlanDTOs){
            number = baseClient.seqGenForAnon(SequenceCodeConstant.SEQ_CEEA_DELIVER_PLANNUM_CODE);
            deliverPlanDTOCopy.setDeliverPlanId(IdGenrator.generate());
            deliverPlanDTOCopy.setDeliverPlanNum(number)
                    .setDeliverPlanStatus("APPROVAL")
                    .setVersion(0L);
            packagedeliverPlanDetailDTO(deliverPlanDTOCopy,deliverPlanDetails);
            this.save(deliverPlanDTOCopy);
        }else {
            packagedeliverPlanDetailDTO(deliverPlanDTOs,deliverPlanDetails);
            number=deliverPlanDTOs.getDeliverPlan().getDeliverPlanNum();
        }

        return number;
    }
    //处理行
    @Transactional
    public void packagedeliverPlanDetailDTO(DeliverPlanDTO deliverPlanDTOs,List<DeliverPlanDetail> deliverPlanDetails){
        //新增对象行
        Map<LocalDate, DeliverPlanDetail>  saveDeliverPlanDetailMap=new HashMap<>();
        //更新对象行
        Map<LocalDate, DeliverPlanDetail>  updateDeliverPlanDetailMap=new HashMap<>();

        List<DeliverPlanDetail> deliverPlanDetailList = deliverPlanDTOs.getDeliverPlanDetailList();
        Map<LocalDate, DeliverPlanDetail> DeliverPlanDetailMap=new HashMap<>();
        if (CollectionUtils.isNotEmpty(deliverPlanDetailList)) {
            DeliverPlanDetailMap = deliverPlanDetailList.stream().collect(Collectors.toMap(DeliverPlanDetail::getSchMonthlyDate, Function.identity()));
        }
            for (DeliverPlanDetail deliverPlanDetail:deliverPlanDetails){
                LocalDate schMonthlyDate = deliverPlanDetail.getSchMonthlyDate();
                DeliverPlanDetail planDetail = DeliverPlanDetailMap.get(schMonthlyDate);
                if(null != planDetail){
                    // 更新
                    // 判断是否锁定
                    if (!"1".equals(planDetail.getDeliverPlanLock())) {
                        if(StringUtil.notEmpty(deliverPlanDetail.getRequirementQuantity())){
                            planDetail.setRequirementQuantity(deliverPlanDetail.getRequirementQuantity());
                        }
                        if(StringUtil.notEmpty(deliverPlanDetail.getQuantityPromised())){
                            planDetail.setQuantityPromised(deliverPlanDetail.getQuantityPromised());
                        }
                        // 状态判断
                        if(planDetail.getQuantityPromised().compareTo(BigDecimal.ZERO) != 0 && planDetail.getRequirementQuantity().compareTo(BigDecimal.ZERO) != 0
                                && planDetail.getRequirementQuantity().compareTo(planDetail.getQuantityPromised()) == 0){
                            planDetail.setDeliverPlanStatus(DeliverPlanLineStatus.COMFIRM.name());
                        }
                        updateDeliverPlanDetailMap.put(schMonthlyDate,planDetail);
                    }
                }else {
                    // 新增
                    deliverPlanDetail.setDeliverPlanId(deliverPlanDTOs.getDeliverPlanId());
                    deliverPlanDetail.setDeliverPlanDetailId(IdGenrator.generate());
                    deliverPlanDetail.setDeliverPlanLock("2");
                    deliverPlanDetail.setDeliverPlanStatus(DeliverPlanLineStatus.UNCOMFIRMED.name());
                    saveDeliverPlanDetailMap.put(schMonthlyDate,deliverPlanDetail);
                }
            }
        iDeliverPlanDetailService.saveBatch(saveDeliverPlanDetailMap.values());
        iDeliverPlanDetailService.updateBatchById(updateDeliverPlanDetailMap.values());
    }
    public Organization getOrgids(Long OrgId)throws Exception{
        ArrayList<Long> longs = new ArrayList<>();
        longs.add(OrgId);
        List<Organization> organizations = baseClient.getOrganizationsByIds(longs);
        if (CollectionUtils.isEmpty(organizations)){
            Assert.isTrue(false,"库存组织："+OrgId+"。找不到");
        }
        return organizations.get(0);
    }
    //发送请求给mrp
    @Override
    public void getAffirmByMrp(Boolean falg){
        if (falg){
            //异步处理
            CompletableFuture.runAsync(() -> {
                try {
                    apiClient.DeliverPlanJob();
                }catch (Exception e){
                    log.error("推送mrp失败，失败原因："+e.getMessage());
                }
            }, ioThreadPool);
        }
    }
}
