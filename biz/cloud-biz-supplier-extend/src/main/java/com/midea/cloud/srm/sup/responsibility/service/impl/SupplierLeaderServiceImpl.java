package com.midea.cloud.srm.sup.responsibility.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.midea.cloud.common.enums.ImportStatus;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.listener.AnalysisEventListenerImpl;
import com.midea.cloud.common.utils.EasyExcelUtil;
import com.midea.cloud.common.utils.ExcelUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.StringUtil;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.feign.rbac.RbacClient;
import com.midea.cloud.srm.model.base.formula.dto.MaterialAttrFormulaDTO;
import com.midea.cloud.srm.model.base.material.MaterialItemAttribute;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.dto.BidRequirementLineDto;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.rbac.user.entity.User;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import com.midea.cloud.srm.model.supplier.responsibility.dto.SupplierLeaderDTO;
import com.midea.cloud.srm.model.supplier.responsibility.entity.SupplierLeader;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.srm.sup.info.service.ICompanyInfoService;
import com.midea.cloud.srm.sup.responsibility.mapper.SupplierLeaderMapper;
import com.midea.cloud.srm.sup.responsibility.service.ISupplierLeaderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.ehcache.core.internal.util.CollectionUtil;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.sql.BatchUpdateException;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * <pre>
 * supplier leader维护表 服务实现类
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-19 14:45:21
 *  修改内容:
 * </pre>
 */
@Service
@Slf4j
public class SupplierLeaderServiceImpl extends ServiceImpl<SupplierLeaderMapper, SupplierLeader> implements ISupplierLeaderService {

    @Resource
    private ICompanyInfoService iCompanyInfoService;

    @Resource
    private RbacClient rbacClient;

    @Resource
    private FileCenterClient fileCenterClient;

    //标题行数组
    private static final List<String> fixedTitle;

    static {
        fixedTitle = new ArrayList<>();
        fixedTitle.addAll(Arrays.asList("供应商SRM编号", "供应商名称", "负责人工号", "负责人姓名"));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addSupplierLeader(List<SupplierLeader> supplierLeaderList) {
        Assert.isTrue(!supplierLeaderList.isEmpty(), "categoryResponsibilityList为空！");
        List<SupplierLeader> saveList = new ArrayList<>();
        List<SupplierLeader> updateList = new ArrayList<>();
        //开始保存传入的小类负责人列表
        //并校验一个小类一个业务实体对应一个负责人
        try {
            if (!saveList.isEmpty()) {
                this.saveBatch(supplierLeaderList);
            }
            if (!updateList.isEmpty()) {
                this.updateBatchById(updateList);
            }
        } catch (DuplicateKeyException e) {
            e.printStackTrace();
            Throwable cause = e.getCause();
            if (cause instanceof BatchUpdateException) {
                String errMsg = ((BatchUpdateException) cause).getMessage();
                if (StringUtils.isNotBlank(errMsg) && errMsg.indexOf("CATEGORY_ORG_INDEX") != -1) {
                    throw new BaseException("存在一个小类一个业务实体对应多个负责人，请重新编辑！");
                }
            }
        }
    }

    /**
     * 下载supplier leader导入模板
     *
     * @param response
     * @throws Exception
     */
    @Override
    public void importSupplierLeaderModelDownload(HttpServletResponse response) throws Exception {
        //构建表格
        Workbook workbook = crateWorkbookModel();
        //获取输出流
        ServletOutputStream outputStream = EasyExcelUtil.getServletOutputStream(response, "供应商supplier leader导入模板");
        //导出
        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> importExcel(MultipartFile file, Fileupload fileupload) throws Exception {
        // 文件校验
        EasyExcelUtil.checkParam(file, fileupload);
        // 读取数据
        List<SupplierLeaderDTO> supplierLeaderDTOs = readData(file);
        // 是否有报错标识
        AtomicBoolean errorFlag = new AtomicBoolean(false);
        // 获取数据
        List<SupplierLeader> supplierLeaderList = getImportData(supplierLeaderDTOs, errorFlag);
        if (errorFlag.get()) {
            //报错
            fileupload.setFileSourceName("供应商supplier leader导入报错");
            Fileupload fileupload1 = EasyExcelUtil.uploadErrorFile(fileCenterClient, fileupload,
                    supplierLeaderDTOs, SupplierLeaderDTO.class, file.getName(), file.getOriginalFilename(), file.getContentType());
            return ImportStatus.importError(fileupload1.getFileuploadId(), fileupload1.getFileSourceName());
        } else {
            //保存或更新supplier leader表
            saveOrUpdateSupplierLeaders(supplierLeaderList);
        }
        return ImportStatus.importSuccess();
    }

    /**
     * 构建导入模板
     */
    public Workbook crateWorkbookModel() throws ParseException {
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

        //第一行 创建标题行
        XSSFRow row0 = sheet.createRow(0);

        // 当前单元格下标
        int cellIndex = 0;

        // 设置固定标题列
        for (int i = 0; i < fixedTitle.size(); i++) {
            XSSFCell cell1 = row0.createCell(cellIndex);
            cell1.setCellValue(fixedTitle.get(i));
            cell1.setCellStyle(cellStyle);
            cellIndex++;
        }
        return workbook;
    }

    /**
     * 保存或更新
     *
     * @param supplierLeaderList
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdateSupplierLeaders(List<SupplierLeader> supplierLeaderList) {
        Map<String, SupplierLeader> supplierLeaderMap = this.list().stream().collect(Collectors.toMap(x -> x.getCompanyCode() + "-" + x.getResponsibilityCode(), part -> part));
        List<SupplierLeader> updateList = new ArrayList<>();
        List<SupplierLeader> saveList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(supplierLeaderList)) {
            for (SupplierLeader supplierLeader : supplierLeaderList) {
                if (Objects.nonNull(supplierLeader)
                        && StringUtils.isNotEmpty(supplierLeader.getCompanyCode())
                        && StringUtils.isNotEmpty(supplierLeader.getResponsibilityCode())) {
                    String companyCodeResponsibilityCode = supplierLeader.getCompanyCode() + "-" + supplierLeader.getResponsibilityCode();
                    if (supplierLeaderMap.containsKey(companyCodeResponsibilityCode)) {
                        Long id = supplierLeaderMap.get(companyCodeResponsibilityCode).getSupplierLeaderId();
                        supplierLeader.setSupplierLeaderId(id);
                        updateList.add(supplierLeader);
                    } else {
                        supplierLeader.setSupplierLeaderId(IdGenrator.generate());
                        saveList.add(supplierLeader);
                    }
                }
            }
            if (CollectionUtils.isNotEmpty(saveList)) {
                log.info("将excel文件供应商supplier leader数据导入到数据库...保存操作");
                this.saveBatch(saveList);
            }
            if (CollectionUtils.isNotEmpty(updateList)) {
                log.info("将excel文件供应商supplier leader数据导入到数据库...更新操作");
                this.updateBatchById(updateList);
            }
        }
    }

    /**
     * 获取导入的数据, 并对数据进行校验
     */
    private List<SupplierLeader> getImportData(List<SupplierLeaderDTO> supplierLeaderDTOs, AtomicBoolean errorFlag) throws IOException, ParseException {
        List<SupplierLeader> supplierLeaderList = new ArrayList<>();
        /**
         * 1. 根据供应商Srm编号去重
         * 2. 根据供应商Srm编号查询对应的供应商
         * 3. 根据负责人工号（员工工号）查询对应的员工
         */
        Map<String, List<CompanyInfo>> companyCodeMap = new HashMap<>();
        Map<String, List<CompanyInfo>> companyNameMap = new HashMap<>();
        Map<String, List<User>> responsibilityCodeMap = new HashMap<>();
        Map<String, List<User>> responsibilityNameMap = new HashMap<>();

        if (CollectionUtils.isNotEmpty(supplierLeaderDTOs)) {
            //供应商编号
            List<String> companyCodeList = new ArrayList<>();
            //供应商名称
            List<String> companyNameList = new ArrayList<>();
            //负责人工号
            List<String> responsibilityCodeList = new ArrayList<>();
            List<String> responsibilityNameList = new ArrayList<>();

            supplierLeaderDTOs.forEach(supplierLeaderDTO -> {
                String companyCode = supplierLeaderDTO.getCompanyCode();
                String companyName = supplierLeaderDTO.getCompanyName();
                String responsibilityCode = supplierLeaderDTO.getResponsibilityCode();
                String responsibilityName = supplierLeaderDTO.getResponsibilityName();
                Optional.ofNullable(companyCode).ifPresent(s -> companyCodeList.add(s.trim()));
                Optional.ofNullable(companyName).ifPresent(s -> companyNameList.add(s.trim()));
                Optional.ofNullable(responsibilityCode).ifPresent(s -> responsibilityCodeList.add(s.trim()));
                Optional.ofNullable(responsibilityName).ifPresent(s -> responsibilityNameList.add(s.trim()));
            });

            if (CollectionUtils.isNotEmpty(companyCodeList)) {
                List<CompanyInfo> companyCodes = iCompanyInfoService.list(Wrappers.lambdaQuery(CompanyInfo.class)
                        .in(CompanyInfo::getCompanyCode, companyCodeList.stream().distinct().collect(Collectors.toList())));
                if (CollectionUtils.isNotEmpty(companyCodes)) {
                    companyCodeMap = companyCodes.stream().collect(Collectors.groupingBy(CompanyInfo::getCompanyCode));
                }
            }

            if (CollectionUtils.isNotEmpty(companyNameList)) {
                List<CompanyInfo> companyNames = iCompanyInfoService.list(Wrappers.lambdaQuery(CompanyInfo.class)
                        .in(CompanyInfo::getCompanyName, companyNameList.stream().distinct().collect(Collectors.toList())));
                if (CollectionUtils.isNotEmpty(companyNames)) {
                    companyNameMap = companyNames.stream().collect(Collectors.groupingBy(CompanyInfo::getCompanyName));
                }

            }

            if (CollectionUtils.isNotEmpty(responsibilityCodeList)) {
                List<User> userCodes = rbacClient.listUsersByUsersParamCode(responsibilityCodeList);
                if (CollectionUtils.isNotEmpty(userCodes)) {
                    responsibilityCodeMap = userCodes.stream().collect(Collectors.groupingBy(User::getCeeaEmpNo));
                }
            }

            if (CollectionUtils.isNotEmpty(responsibilityNameList)) {
                List<User> userNickNames = rbacClient.listUsersByUsersParamNickName(responsibilityNameList);
                if ((CollectionUtils.isNotEmpty(userNickNames))) {
                    responsibilityNameMap = userNickNames.stream().collect(Collectors.groupingBy(User::getNickname));
                }
            }

        }

        //校验数据 是否在数据库存在
        if (CollectionUtils.isNotEmpty(supplierLeaderDTOs)) {

            for (SupplierLeaderDTO supplierLeaderDTO : supplierLeaderDTOs) {
                SupplierLeader supplierLeader = new SupplierLeader();
                StringBuffer errorMsg = new StringBuffer();

                //检查供应商编码 是否在数据库存在
                String companyCode = supplierLeaderDTO.getCompanyCode();
                if (StringUtil.notEmpty(companyCode)) {
                    companyCode = companyCode.trim();
                    if (null != companyCodeMap.get(companyCode)) {
                        CompanyInfo companyInfo = companyCodeMap.get(companyCode).get(0);
                        supplierLeader.setCompanyId(companyInfo.getCompanyId());
                        supplierLeader.setCompanyCode(companyInfo.getCompanyCode());
                        supplierLeader.setCompanyName(companyInfo.getCompanyName());
                    } else {
                        errorFlag.set(true);
                        errorMsg.append("供应商编码[").append(companyCode).append("]在srm系统不存在；");
                    }
                } else {
                    errorFlag.set(true);
                    errorMsg.append("供应商SRM编号不能为空；");
                }

                //检查供应商名称 是否在数据库存在 并且和供应商编码对应上
                String companyName = supplierLeaderDTO.getCompanyName();
                if (StringUtil.notEmpty(companyName)) {
                    companyName = companyName.trim();
                    if (Objects.nonNull(companyNameMap.get(companyName))) {
                        CompanyInfo companyInfo = companyNameMap.get(companyName).get(0);
                        if (StringUtil.notEmpty(supplierLeader.getCompanyCode())
                                && StringUtil.notEmpty(companyInfo.getCompanyName())
                                && !Objects.equals(supplierLeader.getCompanyName(), companyInfo.getCompanyName())) {
                            errorFlag.set(true);
                            errorMsg.append("excel模板中的数据，供应商编码[").append(companyCode).append("]和供应商名称[")
                                    .append(companyName).append("]不对应");
                        }
                    } else {
                        errorFlag.set(true);
                        errorMsg.append("供应商名称[").append(companyName).append("]在srm系统不存在；");
                    }
                }

                //检查负责人工号
                String responsibilityCode = supplierLeaderDTO.getResponsibilityCode();
                if (StringUtil.notEmpty(responsibilityCode)) {
                    responsibilityCode = responsibilityCode.trim();
                    if (Objects.nonNull(responsibilityCodeMap.get(responsibilityCode))) {
                        User user = responsibilityCodeMap.get(responsibilityCode).get(0);
                        supplierLeader.setResponsibilityId(user.getUserId());
                        supplierLeader.setResponsibilityCode(user.getCeeaEmpNo());
                        supplierLeader.setResponsibilityUsername(user.getUsername());
                        supplierLeader.setResponsibilityName(user.getNickname());
                    } else {
                        errorFlag.set(true);
                        errorMsg.append("负责人工号[").append(responsibilityCode).append("]在srm系统不存在；");
                    }
                } else {
                    errorFlag.set(true);
                    errorMsg.append("负责人工号不能为空；");
                }

                //检查负责人姓名
                String responsibilityName = supplierLeaderDTO.getResponsibilityName();
                if (StringUtil.notEmpty(responsibilityName)) {
                    responsibilityName = responsibilityName.trim();
                    if (Objects.nonNull(responsibilityNameMap.get(responsibilityName))) {
                        User user = responsibilityNameMap.get(responsibilityName).get(0);
                        if (StringUtil.notEmpty(supplierLeader.getResponsibilityCode())
                                && StringUtil.notEmpty(user.getNickname())
                                && !Objects.equals(supplierLeader.getResponsibilityName(), user.getNickname())) {
                            errorFlag.set(true);
                            errorMsg.append("excel模板中的数据，负责人工号[").append(responsibilityCode).append("]和负责人姓名[")
                                    .append(responsibilityName).append("]不对应");
                        }
                    } else {
                        errorFlag.set(true);
                        errorMsg.append("负责人姓名[").append(responsibilityName).append("]在srm系统不存在；");
                    }
                }

                if (errorMsg.length() > 0) {
                    supplierLeaderDTO.setErrorMsg(errorMsg.toString());
                } else {
                    supplierLeaderDTO.setErrorMsg(null);
                }
                supplierLeaderList.add(supplierLeader);
            }
        }
        return supplierLeaderList;
    }

    /**
     * 读取excel文件的数据
     *
     * @param file
     * @return
     */
    private List<SupplierLeaderDTO> readData(MultipartFile file) {
        List<SupplierLeaderDTO> supplierLeaderDTOS = null;
        try {
            // 获取输入流
            InputStream inputStream = file.getInputStream();
            // 数据收集器
            AnalysisEventListenerImpl<SupplierLeaderDTO> listener = new AnalysisEventListenerImpl<>();
            ExcelReader excelReader = EasyExcel.read(inputStream, listener).build();
            // 第一个sheet读取类型
            ReadSheet readSheet = EasyExcel.readSheet(0).head(SupplierLeaderDTO.class).build();
            // 开始读取第一个sheet
            excelReader.read(readSheet);
            supplierLeaderDTOS = listener.getDatas();
        } catch (IOException e) {
            throw new BaseException("excel解析出错");
        }
        return supplierLeaderDTOS;
    }

    /**
     * 保存或更新
     *
     * @param companyId
     * @param responsibilityId
     */
    @Override
    public void saveOrUpdateSupplierLeader(Long companyId, Long responsibilityId) {
        List<SupplierLeader> supplierLeaders = this.list(Wrappers.lambdaQuery(SupplierLeader.class)
                .eq(SupplierLeader::getCompanyId, companyId)
                .eq(SupplierLeader::getResponsibilityId, responsibilityId));
        CompanyInfo companyInfo = iCompanyInfoService.getById(companyId);
        User user = rbacClient.getUserByParmForAnon(new User().setUserId(responsibilityId));
        if (CollectionUtils.isEmpty(supplierLeaders)) {
            SupplierLeader supplierLeader = new SupplierLeader().setSupplierLeaderId(IdGenrator.generate());
            supplierLeader.setCompanyId(companyId)
                    .setCompanyCode(companyInfo.getCompanyCode())
                    .setCompanyName(companyInfo.getCompanyName());
            supplierLeader.setResponsibilityId(responsibilityId)
                    .setResponsibilityCode(user.getCeeaEmpNo())
                    .setResponsibilityName(user.getNickname())
                    .setResponsibilityUsername(user.getUsername());
            this.save(supplierLeader);
        } else {
            SupplierLeader supplierLeader = supplierLeaders.get(0);
            supplierLeader.setCompanyId(companyId)
                    .setCompanyCode(companyInfo.getCompanyCode())
                    .setCompanyName(companyInfo.getCompanyName());
            supplierLeader.setResponsibilityId(responsibilityId)
                    .setResponsibilityCode(user.getCeeaEmpNo())
                    .setResponsibilityName(user.getNickname())
                    .setResponsibilityUsername(user.getUsername());
            this.updateById(supplierLeader);
        }
    }

    /**
     * 分页条件查询
     *
     * @param supplierLeader
     * @return
     */
    @Override
    public List<SupplierLeader> listPageByParam(SupplierLeader supplierLeader) {
        List<SupplierLeader> supplierLeaders = this.list(Wrappers.lambdaQuery(SupplierLeader.class)
                .like(StringUtils.isNotEmpty(supplierLeader.getCompanyCode()), SupplierLeader::getCompanyCode, supplierLeader.getCompanyCode())
                .like(StringUtils.isNotEmpty(supplierLeader.getCompanyName()), SupplierLeader::getCompanyName, supplierLeader.getCompanyName())
                .eq(null != supplierLeader.getResponsibilityId(), SupplierLeader::getResponsibilityId, supplierLeader.getResponsibilityId())
                .orderByDesc(SupplierLeader::getLastUpdateDate));
        return supplierLeaders;
    }
}
