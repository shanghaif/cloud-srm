package com.midea.cloud.srm.logistics.expense.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.enums.ImportStatus;
import com.midea.cloud.common.enums.logistics.LogisticsStatus;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.listener.AnalysisEventListenerImpl;
import com.midea.cloud.common.utils.BeanCopyUtil;
import com.midea.cloud.common.utils.EasyExcelUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.StringUtil;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.bid.service.ILgtBidingService;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.logistics.expense.mapper.ExpenseItemMapper;
import com.midea.cloud.srm.logistics.expense.service.IExpenseItemService;
import com.midea.cloud.srm.model.base.dict.dto.DictItemDTO;
import com.midea.cloud.srm.model.base.dict.dto.DictItemQueryDTO;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.logistics.bid.entity.LgtBiding;
import com.midea.cloud.srm.model.logistics.expense.dto.ChargeCodeDto;
import com.midea.cloud.srm.model.logistics.expense.dto.ExpenseItemImportDTO;
import com.midea.cloud.srm.model.logistics.expense.entity.ExpenseItem;
import com.midea.cloud.srm.model.logistics.pr.requirement.entity.LogisticsRequirementHead;
import com.midea.cloud.srm.pr.logisticsRequirement.service.IRequirementHeadService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <pre>
 *  费用项定义表 服务实现类
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-11-26 13:33:44
 *  修改内容:
 * </pre>
 */
@Service
@Slf4j
public class ExpenseItemServiceImpl extends ServiceImpl<ExpenseItemMapper, ExpenseItem> implements IExpenseItemService {

    // 标题行数组
    private static final List<String> fixedTitle;

    static {
        fixedTitle = new ArrayList<>();
        fixedTitle.addAll(Arrays.asList("业务模式", "运输方式", "leg", "费用项"));
    }

    @Autowired
    private BaseClient baseClient;

    @Autowired
    private FileCenterClient fileCenterClient;

    // 费项字典编码
    public static final String CHARGE_NAME = "CHARGE_NAME";
    // LEG字典编码
    public static final String LEG = "LEG";

    @Resource
    private ILgtBidingService iLgtBidingService;
    @Autowired
    private IRequirementHeadService iRequirementHeadService;

    /**
     * 查找leg的层级
     * @Param legName leg的名称组
     * @return leg编码 - (费项名称-费项编码)
     */
    @Override
    public Map<String, Map<String, String>> queryLegChargeMap(List<String> legName,Long bidingId) {
        Map<String, Map<String, String>> map = new HashMap<>();
        if (CollectionUtils.isNotEmpty(legName)) {
            // 查询LEG的字典值
            List<DictItemDTO> division = baseClient.listAllByDictCode(LEG);
            if(CollectionUtils.isNotEmpty(division)){
                division = division.stream().filter(dictItemDTO -> legName.contains(dictItemDTO.getDictItemName())).collect(Collectors.toList());
                if(CollectionUtils.isNotEmpty(division)){
                    List<String> legCodes = division.stream().map(DictItemDTO::getDictItemCode).collect(Collectors.toList());
                    legCodes.forEach(code -> {
                        List<ChargeCodeDto> codeDtos = queryChargeCodeDtoByLeg(code,bidingId,null);
                        if(CollectionUtils.isNotEmpty(codeDtos)){
                            Map<String, String> collect = codeDtos.stream().collect(Collectors.toMap(ChargeCodeDto::getChargeCode,ChargeCodeDto::getChargeName, (k1, k2) -> k1));
                            map.put(code,collect);
                        }
                    });
                }
            }
        }
        return map;
    }

    @Override
    public List<ChargeCodeDto> queryChargeCodeDtoByLeg(String legCode,Long bidingId,Long requirementHeadId) {
        Assert.isTrue(null != bidingId || null != requirementHeadId,"缺少参数: 单据头ID");
        List<ChargeCodeDto> ChargeCodeDtos = new ArrayList<>();
        // 查找单据信息, 根据运输方式和业务模式过滤
        // 运输方式
        String transportModeCode = "";
        // 业务模式
        String businessModeCode = "";
        if(null != bidingId){
            LgtBiding lgtBiding = iLgtBidingService.getById(bidingId);
            Assert.notNull(lgtBiding,"找不到物流招标头信息,bidingId="+bidingId);
            transportModeCode = lgtBiding.getTransportModeCode();
            businessModeCode = lgtBiding.getBusinessModeCode();
        }
        if(null != requirementHeadId){
            LogisticsRequirementHead requirementHead = iRequirementHeadService.getById(requirementHeadId);
            Assert.notNull(requirementHead,"找不到物流招标头信息,requirementHeadId="+requirementHeadId);
            transportModeCode = requirementHead.getTransportModeCode();
            businessModeCode = requirementHead.getBusinessModeCode();
        }

        if (StringUtil.notEmpty(legCode)) {
            List<ExpenseItem> itemList = this.list(Wrappers.lambdaQuery(ExpenseItem.class).
                    eq(ExpenseItem::getLegCode, legCode).
                    eq(ExpenseItem::getBusinessModeCode,businessModeCode).
                    eq(ExpenseItem::getTransportModeCode,transportModeCode).
                    eq(ExpenseItem::getStatus, LogisticsStatus.EFFECTIVE.getValue()));
            if(CollectionUtils.isNotEmpty(itemList)){
                ChargeCodeDtos = itemList.stream().map(expenseItem -> {
                    ChargeCodeDto chargeCodeDto = new ChargeCodeDto();
                    BeanCopyUtil.copyProperties(chargeCodeDto, expenseItem);
                    return chargeCodeDto;
                }).collect(Collectors.toList());
            }
        }else {
            // 查询全部的费项
            List<DictItemDTO> division = baseClient.listAllByDictCode(CHARGE_NAME);
            if(CollectionUtils.isNotEmpty(division)){
                ChargeCodeDtos = division.stream().map(dictItemDTO -> {
                    return new ChargeCodeDto().
                            setChargeCode(dictItemDTO.getDictItemCode()).
                            setChargeName(dictItemDTO.getDictItemName());
                }).collect(Collectors.toList());
            }
        }
        return ChargeCodeDtos;
    }

    @Override
    public List<ChargeCodeDto> queryChargeCodeDtoBy(String legCode, String transportModeCode, String businessModeCode) {
        List<ChargeCodeDto> ChargeCodeDtos = new ArrayList<>();
        List<ExpenseItem> itemList =new ArrayList<>();
        if (StringUtil.notEmpty(legCode)) {
            itemList= this.list(Wrappers.lambdaQuery(ExpenseItem.class).
                    eq(ExpenseItem::getLegCode, legCode).
                    eq(ExpenseItem::getBusinessModeCode, businessModeCode).
                    eq(ExpenseItem::getTransportModeCode, transportModeCode).
                    eq(ExpenseItem::getStatus, LogisticsStatus.EFFECTIVE.getValue()));
        } else {
            itemList = this.list(Wrappers.lambdaQuery(ExpenseItem.class).
                    eq(ExpenseItem::getBusinessModeCode, businessModeCode).
                    eq(ExpenseItem::getTransportModeCode, transportModeCode).
                    eq(ExpenseItem::getStatus, LogisticsStatus.EFFECTIVE.getValue()).
                    and(wrapper->wrapper.isNull(ExpenseItem::getLegCode).or().eq(ExpenseItem::getLegCode," "))
            );
        }
        if (CollectionUtils.isNotEmpty(itemList)) {
            ChargeCodeDtos = itemList.stream().map(expenseItem -> {
                ChargeCodeDto chargeCodeDto = new ChargeCodeDto();
                BeanCopyUtil.copyProperties(chargeCodeDto, expenseItem);
                return chargeCodeDto;
            }).collect(Collectors.toList());
        }
        return ChargeCodeDtos;
    }

    /**
     * 条件查询
     *
     * @param expenseItem
     * @return
     */
    @Override
    public List<ExpenseItem> listByParam(ExpenseItem expenseItem) {
        List<ExpenseItem> expenseItemList = this.list(Wrappers.lambdaQuery(ExpenseItem.class)
                .like(StringUtils.isNotEmpty(expenseItem.getChargeCode()), ExpenseItem::getChargeCode, expenseItem.getChargeCode())
                .like(StringUtils.isNotEmpty(expenseItem.getChargeName()), ExpenseItem::getChargeName, expenseItem.getChargeName())
                .eq(StringUtils.isNotEmpty(expenseItem.getBusinessModeCode()), ExpenseItem::getBusinessModeCode, expenseItem.getBusinessModeCode())
                .eq(StringUtils.isNotEmpty(expenseItem.getTransportModeCode()), ExpenseItem::getTransportModeCode, expenseItem.getTransportModeCode())
                .eq(StringUtils.isNotEmpty(expenseItem.getLegCode()), ExpenseItem::getLegCode, expenseItem.getLegCode())
                .eq(StringUtils.isNotEmpty(expenseItem.getStatus()), ExpenseItem::getStatus, expenseItem.getStatus())
                .like(StringUtils.isNotBlank(expenseItem.getTemplateName()), ExpenseItem::getTemplateName, expenseItem.getTemplateName())
                .orderByDesc(ExpenseItem::getLastUpdateDate)
        );
        return expenseItemList;
    }

    /**
     * 保存前非空校验
     *
     * @param expenseItem
     */
    @Override
    public void checkNotEmptyBeforeSave(ExpenseItem expenseItem) {
        if (StringUtils.isEmpty(expenseItem.getBusinessModeCode()))
            throw new BaseException(LocaleHandler.getLocaleMsg("业务模式不能为空, 请选择/输入后重试."));
        if (StringUtils.isEmpty(expenseItem.getTransportModeCode()))
            throw new BaseException(LocaleHandler.getLocaleMsg("运输模式不能为空, 请选择/输入后重试."));
        if (StringUtils.isEmpty(expenseItem.getChargeCode()))
            throw new BaseException(LocaleHandler.getLocaleMsg("费用项不能为空, 请选择/输入后重试."));
    }

    /**
     * 删除前校验是否都是拟定状态
     *
     * @param expenseItemId
     */
    @Override
    public void checkExpenseItemsByIdBeforeDelete(Long expenseItemId) {
        ExpenseItem expenseItem = this.getById(expenseItemId);
        if (!Objects.equals(expenseItem.getStatus(), LogisticsStatus.DRAFT.getValue())) {
            // 获取业务模式字典条目码对应的字典条目名称
            String businessModeDictItemName = getDictItemNameByDictCodeAndDictCode("BUSINESS_MODE", expenseItem.getBusinessModeCode());
            // 获取运输方式字典条目码对应的字典条目名称
            String transportModeDictItemName = getDictItemNameByDictCodeAndDictCode("TRANSPORT_MODE", expenseItem.getTransportModeCode());
            // 获取LEG字典条目码对应的字典条目名称
            String legDictItemName = getDictItemNameByDictCodeAndDictCode("LEG", expenseItem.getLegCode());
            // 获取费用项字典条目码对应的字典条目名称
            String chargeNameDictItemName = getDictItemNameByDictCodeAndDictCode("CHARGE_NAME", expenseItem.getChargeCode());
            StringBuffer sb = new StringBuffer();
            sb.append("您选择的业务模式为:[").append(businessModeDictItemName).append("], 运输方式为:[").append(transportModeDictItemName)
                    .append("], LEG为:[").append(legDictItemName).append("], 费用项为:[").append(chargeNameDictItemName).append("]的数据, 不是拟定状态, 不能删除.");
            throw new BaseException(sb.toString());
        }
    }

    /**
     * 生效前校验是否都是拟定状态
     *
     * @param expenseItemId
     */
    @Override
    public void checkExpenseItemsByIdsBeforeEffective(Long expenseItemId) {
        ExpenseItem expenseItem = this.getById(expenseItemId);
        if (!(Objects.equals(expenseItem.getStatus(), LogisticsStatus.DRAFT.getValue()) || Objects.equals(expenseItem.getStatus(), LogisticsStatus.INEFFECTIVE.getValue()))) {
            // 获取业务模式字典条目码对应的字典条目名称
            String businessModeDictItemName = getDictItemNameByDictCodeAndDictCode("BUSINESS_MODE", expenseItem.getBusinessModeCode());
            // 获取运输方式字典条目码对应的字典条目名称
            String transportModeDictItemName = getDictItemNameByDictCodeAndDictCode("TRANSPORT_MODE", expenseItem.getTransportModeCode());
            // 获取LEG字典条目码对应的字典条目名称
            String legDictItemName = getDictItemNameByDictCodeAndDictCode("LEG", expenseItem.getLegCode());
            // 获取费用项字典条目码对应的字典条目名称
            String chargeNameDictItemName = getDictItemNameByDictCodeAndDictCode("CHARGE_NAME", expenseItem.getChargeCode());
            StringBuffer sb = new StringBuffer();
            sb.append("您选择的业务模式为:[").append(businessModeDictItemName).append("], 运输方式为:[").append(transportModeDictItemName)
                    .append("], LEG为:[").append(legDictItemName).append("], 费用项为:[").append(chargeNameDictItemName).append("]的数据, 不是拟定或失效状态, 不能进行生效操作.");
            throw new BaseException(sb.toString());
        }
    }

    /**
     * 失效前校验是否都是生效状态
     *
     * @param expenseItemId
     */
    @Override
    public void checkExpenseItemsByIdsBeforeInEffective(Long expenseItemId) {
        ExpenseItem expenseItem = this.getById(expenseItemId);
        if (!Objects.equals(expenseItem.getStatus(), LogisticsStatus.EFFECTIVE.getValue())) {
            // 获取业务模式字典条目码对应的字典条目名称
            String businessModeDictItemName = getDictItemNameByDictCodeAndDictCode("BUSINESS_MODE", expenseItem.getBusinessModeCode());
            // 获取运输方式字典条目码对应的字典条目名称
            String transportModeDictItemName = getDictItemNameByDictCodeAndDictCode("TRANSPORT_MODE", expenseItem.getTransportModeCode());
            // 获取LEG字典条目码对应的字典条目名称
            String legDictItemName = getDictItemNameByDictCodeAndDictCode("LEG", expenseItem.getLegCode());
            // 获取费用项字典条目码对应的字典条目名称
            String chargeNameDictItemName = getDictItemNameByDictCodeAndDictCode("CHARGE_NAME", expenseItem.getChargeCode());
            StringBuffer sb = new StringBuffer();
            sb.append("您选择的业务模式为:[").append(businessModeDictItemName).append("], 运输方式为:[").append(transportModeDictItemName)
                    .append("], LEG为:[").append(legDictItemName).append("], 费用项为:[").append(chargeNameDictItemName).append("]的数据, 不是生效状态, 不能进行失效操作.");
            throw new BaseException(sb.toString());
        }
    }

    /**
     * 根据字典编码和字典条目编码获取字典条目名称
     */
    public String getDictItemNameByDictCodeAndDictCode(String dictCode, String dictItemCode) {
        List<DictItemDTO> dictItems = baseClient.listAllByParam(
                new DictItemDTO().setDictCode(dictCode).setDictItemCode(dictItemCode)
        );
        DictItemDTO dictItemDTO = CollectionUtils.isNotEmpty(dictItems) ? dictItems.get(0) : null;
        String dictItemName = StringUtils.isNotEmpty(dictItemDTO.getDictItemName()) ? dictItemDTO.getDictItemName() : "";
        return dictItemName;
    }

    /**
     * 根据字典名称和字典条目编码获取字典条目编码
     */
    public DictItemDTO getDictItemCodeByDictNameAndDictCode(String dictCode, String dictItemName) {
        List<DictItemDTO> dictItems = baseClient.listAllByParam(
                new DictItemDTO().setDictCode(dictCode).setDictItemName(dictItemName)
        );
        DictItemDTO dictItemDTO = CollectionUtils.isNotEmpty(dictItems) ? dictItems.get(0) : null;
        return dictItemDTO;
    }

    /**
     * 保存/更新费用项
     *
     * @param expenseItem
     */
    @Override
    public void saveExpenseItem(ExpenseItem expenseItem) {
        if (null == expenseItem.getExpenseItemId()) {
            expenseItem.setExpenseItemId(IdGenrator.generate()).setStatus(LogisticsStatus.DRAFT.getValue());
            this.save(expenseItem);
        } else {
            this.updateById(expenseItem);
        }
    }

    /**
     * 保存费用项定义 批量
     *
     * @param expenseItems
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveExpenseItems(List<ExpenseItem> expenseItems) {
        // 批量保存前校验, 校验前端传入的列表是否有重复的数据
        checkRepeatExpenseItems(expenseItems);
        // 批量保存前校验, 校验要保存的数据和数据库中数据是否重复
        checkDbRepeatExpenseItems(expenseItems);

        // 区分出哪些是要新增的, 哪些是要更新的
        List<ExpenseItem> saveList = new ArrayList<>();
        List<ExpenseItem> updateList = new ArrayList<>();
        expenseItems.forEach(expenseItem -> {
            if (null == expenseItem.getExpenseItemId()) {
                expenseItem.setExpenseItemId(IdGenrator.generate()).setStatus(LogisticsStatus.DRAFT.getValue());
                saveList.add(expenseItem);
            } else
                updateList.add(expenseItem);
        });
        if (CollectionUtils.isNotEmpty(updateList))
            this.updateBatchById(updateList);
        if (CollectionUtils.isNotEmpty(saveList))
            this.saveBatch(saveList);
    }

    /**
     * 校验前端传入的数据是否存在重复
     *
     * @param expenseItems
     */
    public void checkRepeatExpenseItems(List<ExpenseItem> expenseItems) {
        Map<String, List<ExpenseItem>> collect = expenseItems.stream().collect(Collectors.groupingBy(x -> getExpenseItemKey(x)));
        expenseItems.forEach(expenseItem -> {
            String key = getExpenseItemKey(expenseItem);
            if (collect.containsKey(key) && collect.get(key).size() > 1) {
                throw new BaseException(LocaleHandler.getLocaleMsg("需要保存的数据存在重复, 请检查后重试"));
            }
        });
    }

    /**
     * 校验要保存的数据和数据库中的数据是否存在重复
     *
     * @param expenseItems
     */
    public void checkDbRepeatExpenseItems(List<ExpenseItem> expenseItems) {
        expenseItems = expenseItems.stream().filter(x->null == x.getExpenseItemId()).collect(Collectors.toList());
        for (ExpenseItem expenseItem : expenseItems) {
            List<ExpenseItem> expenseItemList = new ArrayList<>();
            if(StringUtils.isNotEmpty(expenseItem.getLegCode())) {
                expenseItemList =this.list(Wrappers.lambdaQuery(ExpenseItem.class)
                        .eq(ExpenseItem::getBusinessModeCode, expenseItem.getBusinessModeCode())
                        .eq(ExpenseItem::getTransportModeCode, expenseItem.getTransportModeCode())
                        .eq(ExpenseItem::getLegCode, expenseItem.getLegCode())
                        .eq(ExpenseItem::getChargeCode, expenseItem.getChargeCode()));
            } else {
                expenseItemList =this.list(Wrappers.lambdaQuery(ExpenseItem.class)
                        .eq(ExpenseItem::getBusinessModeCode, expenseItem.getBusinessModeCode())
                        .eq(ExpenseItem::getTransportModeCode, expenseItem.getTransportModeCode())
                        .and(wrapper->wrapper.isNull(ExpenseItem::getLegCode).or().eq(ExpenseItem::getLegCode," "))
                        .eq(ExpenseItem::getChargeCode, expenseItem.getChargeCode()));
            }
            if (CollectionUtils.isNotEmpty(expenseItemList)) {
                // 获取业务模式字典条目码对应的字典条目名称
                String businessModeDictItemName = getDictItemNameByDictCodeAndDictCode("BUSINESS_MODE", expenseItem.getBusinessModeCode());
                // 获取运输方式字典条目码对应的字典条目名称
                String transportModeDictItemName = getDictItemNameByDictCodeAndDictCode("TRANSPORT_MODE", expenseItem.getTransportModeCode());
                // 获取费用项字典条目码对应的字典条目名称
                String chargeNameDictItemName = getDictItemNameByDictCodeAndDictCode("CHARGE_NAME", expenseItem.getChargeCode());
                StringBuffer sb = new StringBuffer();
                sb.append("您选择的业务模式为:[").append(businessModeDictItemName).append("], 运输方式为:[").append(transportModeDictItemName)
                        .append("], 费用项为:[").append(chargeNameDictItemName).append("]");
                if (StringUtils.isNotEmpty(expenseItem.getLegCode())) {
                    // 获取LEG字典条目码对应的字典条目名称
                    String legDictItemName = getDictItemNameByDictCodeAndDictCode("LEG", expenseItem.getLegCode());
                    sb.append(", LEG为:[").append(legDictItemName).append("]");
                }
                sb.append("的数据, 在数据库已存在.");
                throw new BaseException(LocaleHandler.getLocaleMsg(sb.toString()));
            }
        }
    }

    /**
     * 更新费用项定义状态
     *
     * @param expenseItemId
     * @param status
     */
    @Override
    public void updateExpenseItemStatus(Long expenseItemId, String status) {
        ExpenseItem expenseItem = this.getById(expenseItemId);
        expenseItem.setStatus(status);
        this.updateById(expenseItem);
    }

    /**
     * 批量更新费用项定义状态
     *
     * @param expenseItemIds
     * @param status
     */
    @Override
    public void updateExpenseItemsStatus(List<Long> expenseItemIds, String status) {
        List<ExpenseItem> expenseItemList = new ArrayList<>();
        expenseItemIds.forEach(expenseItemId -> {
            ExpenseItem expenseItem = this.getById(expenseItemId);
            expenseItem.setStatus(status);
            expenseItemList.add(expenseItem);
        });
        this.updateBatchById(expenseItemList);
    }

    /**
     * 下载费用项定义导入模板
     * @param response
     * @throws Exception
     */
    @Override
    public void importSupplierLeaderModelDownload(HttpServletResponse response) throws Exception {
        //构建表格
        Workbook workbook = crateWorkbookModel();
        //获取输出流
        ServletOutputStream outputStream = EasyExcelUtil.getServletOutputStream(response, "费用项导入模板");
        //导出
        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
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
     * excel导入数据到数据库
     * @param file
     * @param fileupload
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> importExcel(MultipartFile file, Fileupload fileupload) throws Exception {
        // 文件校验
        EasyExcelUtil.checkParam(file, fileupload);
        // 读取数据
        List<ExpenseItemImportDTO> expenseItemDTOs = readData(file);
        // 是否有报错标识
        AtomicBoolean errorFlag = new AtomicBoolean(false);
        // 获取数据
        List<ExpenseItem> expenseItemList = getImportData(expenseItemDTOs, errorFlag);
        if (errorFlag.get()) {
            //报错
            fileupload.setFileSourceName("费用项定义导入报错");
            Fileupload fileupload1 = EasyExcelUtil.uploadErrorFile(fileCenterClient, fileupload,
                    expenseItemDTOs, ExpenseItemImportDTO.class, file.getName(), file.getOriginalFilename(), file.getContentType());
            return ImportStatus.importError(fileupload1.getFileuploadId(), fileupload1.getFileSourceName());
        } else {
            //保存或更新supplier leader表
            saveOrUpdateExpenseItems(expenseItemList);
        }
        return ImportStatus.importSuccess();
    }

    /**
     * 读取excel文件的数据
     *
     * @param file
     * @return
     */
    private List<ExpenseItemImportDTO> readData(MultipartFile file) {
        List<ExpenseItemImportDTO> expenseItemDTOS = null;
        try {
            // 获取输入流
            InputStream inputStream = file.getInputStream();
            // 数据收集器
            AnalysisEventListenerImpl<ExpenseItemImportDTO> listener = new AnalysisEventListenerImpl<>();
            ExcelReader excelReader = EasyExcel.read(inputStream, listener).build();
            // 第一个sheet读取类型
            ReadSheet readSheet = EasyExcel.readSheet(0).head(ExpenseItemImportDTO.class).build();
            // 开始读取第一个sheet
            excelReader.read(readSheet);
            expenseItemDTOS = listener.getDatas();
        } catch (IOException e) {
            throw new BaseException("excel解析出错");
        }
        return expenseItemDTOS;
    }

    /**
     * 获取导入的数据, 并对数据进行校验
     */
    private List<ExpenseItem> getImportData(List<ExpenseItemImportDTO> expenseItemDTOS, AtomicBoolean errorFlag) throws IOException, ParseException {
        List<ExpenseItem> expenseItemList = new ArrayList<>();
        /**
         * 1. 根据供应商Srm编号去重
         * 2. 根据供应商Srm编号查询对应的供应商
         * 3. 根据负责人工号（员工工号）查询对应的员工
         */
        Map<String, List<DictItemDTO>> businessModeMap = new HashMap<>();
        Map<String, List<DictItemDTO>> transportModeMap = new HashMap<>();
        Map<String, List<DictItemDTO>> legMap = new HashMap<>();
        Map<String, List<DictItemDTO>> chargeMap = new HashMap<>();

        if (CollectionUtils.isNotEmpty(expenseItemDTOS)) {
            // 业务模式名称
            List<String> businessModeNameList = new ArrayList<>();
            // 运输方式名称
            List<String> transportModeNameList = new ArrayList<>();
            // LEG
            List<String> legNameList = new ArrayList<>();
            // 费用项名称
            List<String> chargeNameList = new ArrayList<>();

            expenseItemDTOS.forEach(x -> {
                String businessModeName = x.getBusinessModeName();
                String transportModeName = x.getTransportModeName();
                String legName = x.getLegName();
                String chargeName = x.getChargeName();
                Optional.ofNullable(businessModeName).ifPresent(s -> businessModeNameList.add(s.trim()));
                Optional.ofNullable(transportModeName).ifPresent(s -> transportModeNameList.add(s.trim()));
                Optional.ofNullable(legName).ifPresent(s -> legNameList.add(s.trim()));
                Optional.ofNullable(chargeName).ifPresent(s -> chargeNameList.add(s.trim()));
            });

            if (CollectionUtils.isNotEmpty(businessModeNameList)) {
                DictItemQueryDTO dto = new DictItemQueryDTO();
                dto.setDictCode("BUSINESS_MODE");
                dto.setDictItemNames(businessModeNameList);
                List<DictItemDTO> businessModeCodes = baseClient.getDictItemsByDictCodeAndDictItemNames(dto);
                if (CollectionUtils.isNotEmpty(businessModeCodes)) {
                    businessModeMap = businessModeCodes.stream().collect(Collectors.groupingBy(DictItemDTO::getDictItemName));
                }
            }

            if (CollectionUtils.isNotEmpty(transportModeNameList)) {
                DictItemQueryDTO dto = new DictItemQueryDTO();
                dto.setDictCode("TRANSPORT_MODE");
                dto.setDictItemNames(transportModeNameList);
                List<DictItemDTO> transportModeCodes = baseClient.getDictItemsByDictCodeAndDictItemNames(dto);
                if (CollectionUtils.isNotEmpty(transportModeCodes)) {
                    transportModeMap = transportModeCodes.stream().collect(Collectors.groupingBy(DictItemDTO::getDictItemName));
                }

            }

            if (CollectionUtils.isNotEmpty(legNameList)) {
                DictItemQueryDTO dto = new DictItemQueryDTO();
                dto.setDictCode("LEG");
                dto.setDictItemNames(legNameList);
                List<DictItemDTO> lrgCodes = baseClient.getDictItemsByDictCodeAndDictItemNames(dto);
                if (CollectionUtils.isNotEmpty(lrgCodes)) {
                    legMap = lrgCodes.stream().collect(Collectors.groupingBy(DictItemDTO::getDictItemName));
                }

            }

            if (CollectionUtils.isNotEmpty(chargeNameList)) {
                DictItemQueryDTO dto = new DictItemQueryDTO();
                dto.setDictCode("CHARGE_NAME");
                dto.setDictItemNames(chargeNameList);
                List<DictItemDTO> ChargeCodes = baseClient.getDictItemsByDictCodeAndDictItemNames(dto);
                if (CollectionUtils.isNotEmpty(ChargeCodes)) {
                    chargeMap = ChargeCodes.stream().collect(Collectors.groupingBy(DictItemDTO::getDictItemName));
                }

            }

        }

        //校验数据 是否在数据库存在
        if (CollectionUtils.isNotEmpty(expenseItemDTOS)) {

            for (ExpenseItemImportDTO expenseItemImportDTO : expenseItemDTOS) {
                ExpenseItem expenseItem = new ExpenseItem();
                StringBuffer errorMsg = new StringBuffer();

                // 检查业务模式名称 是否在字典存在
                String businessModeName = expenseItemImportDTO.getBusinessModeName();
                if (StringUtil.notEmpty(businessModeName)) {
                    businessModeName = businessModeName.trim();
                    if (null != businessModeMap && null != businessModeMap.get(businessModeName)) {
                        DictItemDTO businessModeDictItemDTO = businessModeMap.get(businessModeName).get(0);
                        expenseItem.setBusinessModeCode(businessModeDictItemDTO.getDictItemCode());
                    } else {
                        errorFlag.set(true);
                        errorMsg.append("业务模式名称[").append(businessModeName).append("]在srm系统字典中不存在, 请先添加字典条目或在导入excel中录入与字典对应的的业务模式名称；");
                    }
                } else {
                    errorFlag.set(true);
                    errorMsg.append("业务模式名称不能为空；");
                }

                // 检查运输模式 是否在字典存在
                String transportModeName = expenseItemImportDTO.getTransportModeName();
                if (StringUtil.notEmpty(transportModeName)) {
                    transportModeName = transportModeName.trim();
                    if (null != transportModeMap.get(transportModeName)) {
                        DictItemDTO transportModeDictItemDTO = transportModeMap.get(transportModeName).get(0);
                        expenseItem.setTransportModeCode(transportModeDictItemDTO.getDictItemCode());
                    } else {
                        errorFlag.set(true);
                        errorMsg.append("运输方式名称[").append(businessModeName).append("]在srm系统字典中不存在, 请先添加字典条目或在导入excel中录入与字典对应的的运输方式名称；");
                    }
                } else {
                    errorFlag.set(true);
                    errorMsg.append("运输方式名称不能为空；");
                }

                //检查LEG 是否在字典中存在
                String legName = expenseItemImportDTO.getLegName();
                if (StringUtil.notEmpty(legName)) {
                    legName = legName.trim();
                    if (null != legMap.get(legName)) {
                        DictItemDTO legDictItemDTO = legMap.get(legName).get(0);
                        expenseItem.setLegCode(legDictItemDTO.getDictItemCode());
                    } else {
                        errorFlag.set(true);
                        errorMsg.append("LEG[").append(legName).append("]在srm系统字典中不存在, 请先添加字典条目或在导入excel中录入与字典对应的的LEG名称；");
                    }
                }

                // 检查费项 是否在字典中存在
                String chargeName = expenseItemImportDTO.getChargeName();
                if (StringUtil.notEmpty(chargeName)) {
                    chargeName = chargeName.trim();
                    if (null != chargeMap.get(chargeName)) {
                        DictItemDTO chargeDictItemDTO = chargeMap.get(chargeName).get(0);
                        expenseItem.setChargeCode(chargeDictItemDTO.getDictItemCode()).setChargeName(chargeDictItemDTO.getDictItemName());
                    } else {
                        errorFlag.set(true);
                        errorMsg.append("费用项[").append(chargeName).append("]在srm系统字典中不存在, 请先添加字典条目或在导入excel中录入与字典对应的的费用项名称；");
                    }
                }

                if (errorMsg.length() > 0) {
                    expenseItemImportDTO.setErrorMsg(errorMsg.toString());
                } else {
                    expenseItemImportDTO.setErrorMsg(null);
                }
                expenseItemList.add(expenseItem);
            }
        }
        return expenseItemList;
    }

    /**
     * 保存或更新
     *
     * @param expenseItems
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdateExpenseItems(List<ExpenseItem> expenseItems) {
        List<ExpenseItem> expenseItemList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(expenseItems)) {
            QueryWrapper<ExpenseItem> wrapper = new QueryWrapper<>();
            List<String> businessModes = expenseItems.stream().map(ExpenseItem::getBusinessModeCode).distinct().collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(businessModes)) {
                wrapper.in("BUSINESS_MODE_CODE", businessModes);
            }
            List<String> transportModes = expenseItems.stream().map(ExpenseItem::getTransportModeCode).distinct().collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(transportModes)) {
                wrapper.in("TRANSPORT_MODE_CODE", transportModes);
            }
            List<String> chargeCodes = expenseItems.stream().map(ExpenseItem::getChargeCode).distinct().collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(chargeCodes)) {
                wrapper.in("CHARGE_CODE", chargeCodes);
            }
            List<String> legCodes = expenseItems.stream().map(ExpenseItem::getLegCode).filter(StringUtils::isNotEmpty).distinct().collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(legCodes)) {
                wrapper.in("LEG_CODE", legCodes);
            }
            boolean allEmpty = CollectionUtils.isEmpty(businessModes) && CollectionUtils.isEmpty(transportModes)
                    && CollectionUtils.isEmpty(chargeCodes) && CollectionUtils.isEmpty(legCodes);
            if (allEmpty) {
                expenseItemList = Collections.EMPTY_LIST;
            } else {
                expenseItemList = this.list(wrapper);
            }
        }
        Map<String, ExpenseItem> expenseItemMap = expenseItemList.stream().collect(Collectors.toMap(x -> getExpenseItemKey(x) , Function.identity(), (o1, o2) -> o2));
        List<ExpenseItem> updateList = new ArrayList<>();
        List<ExpenseItem> saveList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(expenseItems)) {
            for (ExpenseItem expenseItem : expenseItems) {
                if (Objects.nonNull(expenseItem)) {
                    String key = getExpenseItemKey(expenseItem);
                    if (expenseItemMap.containsKey(key)) {
                        Long id = expenseItemMap.get(key).getExpenseItemId();
                        expenseItem.setExpenseItemId(id);
                        expenseItem.setStatus(LogisticsStatus.DRAFT.getValue());
                        updateList.add(expenseItem);
                    } else {
                        expenseItem.setExpenseItemId(IdGenrator.generate());
                        expenseItem.setStatus(LogisticsStatus.DRAFT.getValue());
                        saveList.add(expenseItem);
                    }
                }
            }
            if (CollectionUtils.isNotEmpty(saveList)) {
                log.info("将excel文件费用项定义数据导入到数据库...保存操作");
                this.saveBatch(saveList);
            }
            if (CollectionUtils.isNotEmpty(updateList)) {
                log.info("将excel文件费用项定义数据导入到数据库...更新操作");
                this.updateBatchById(updateList);
            }
        }
    }

    /**
     * 根据几个字段 唯一标识一条费用项数据
     */
    public String getExpenseItemKey(ExpenseItem expenseItem) {
        StringBuffer sb = new StringBuffer();
        sb.append(expenseItem.getBusinessModeCode()).append("-")
                .append(expenseItem.getTransportModeCode()).append("-")
                .append(expenseItem.getChargeCode());
        if (StringUtils.isNotEmpty(expenseItem.getLegCode())) {
            sb.append("-").append(expenseItem.getLegCode());
        }
        return sb.toString();
    }
}
