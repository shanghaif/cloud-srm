package com.midea.cloud.srm.cm.contract.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.googlecode.aviator.AviatorEvaluator;
import com.midea.cloud.common.enums.ImportStatus;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.enums.contract.CalculatingSigns;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.listener.AnalysisEventListenerImpl;
import com.midea.cloud.common.utils.*;
import com.midea.cloud.srm.cm.contract.mapper.LevelMaintainMapper;
import com.midea.cloud.srm.cm.contract.service.ILevelMaintainService;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.model.base.dict.dto.DictItemDTO;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseCategory;
import com.midea.cloud.srm.model.cm.contract.dto.LevelMaintainExportDto;
import com.midea.cloud.srm.model.cm.contract.dto.LevelMaintainImportDto;
import com.midea.cloud.srm.model.cm.contract.dto.LevelMaintainModelImportDto;
import com.midea.cloud.srm.model.cm.contract.entity.LevelMaintain;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
*  <pre>
 *  合同定级维护表 服务实现类
 * </pre>
*
* @author yourname@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-10-06 10:59:25
 *  修改内容:
 * </pre>
*/
@Service
public class LevelMaintainServiceImpl extends ServiceImpl<LevelMaintainMapper, LevelMaintain> implements ILevelMaintainService {
    @Resource
    private BaseClient baseClient;
    @Resource
    private FileCenterClient fileCenterClient;

    private static final Map<String,String> calculatingSigns;
    static {
        calculatingSigns = new HashMap<>();
        calculatingSigns.put("MORE_THAN",">");
        calculatingSigns.put("MORE_THAN_EQUAL",">=");
        calculatingSigns.put("LESS_THAN","<");
        calculatingSigns.put("LESS_THAN_EQUAL","<=");
        calculatingSigns.put("EQUAL","==");
    }

    /**
     * 新增
     * @param levelMaintain
     */
    @Override
    public Long add(LevelMaintain levelMaintain) {
        // 检查参数
        checkParam(levelMaintain);
        levelMaintain.setLevelMaintainId(IdGenrator.generate());
        this.save(levelMaintain);
        return levelMaintain.getLevelMaintainId();
    }

    private void checkParam(LevelMaintain levelMaintain) {
        Assert.notNull(levelMaintain.getCategoryId(),"物料小类不能为空");
        Assert.notNull(levelMaintain.getAmount(),"合同定级金额不能为空");
        Assert.notNull(levelMaintain.getOperational(),"运算关系不能为空");
        Assert.notNull(levelMaintain.getLevel(),"合同定级不能为空");
        if (StringUtil.isEmpty(levelMaintain.getLevelMaintainId())) {
            List<LevelMaintain> levelMaintains = this.list(new QueryWrapper<>(new LevelMaintain().setCategoryId(levelMaintain.getCategoryId()).
                    setAmount(levelMaintain.getAmount()).setOperational(levelMaintain.getOperational())));
            Assert.isTrue(CollectionUtils.isEmpty(levelMaintains),"物料小类+金额+运算关系{}已存在");
        }else {
            List<LevelMaintain> levelMaintains = this.list(new QueryWrapper<>(new LevelMaintain().setCategoryId(levelMaintain.getCategoryId()).
                    setAmount(levelMaintain.getAmount()).setOperational(levelMaintain.getOperational())));
            Assert.isTrue(CollectionUtils.isEmpty(levelMaintains) || levelMaintains.get(0).getLevelMaintainId().compareTo(levelMaintain.getLevelMaintainId()) == 0,"物料小类+金额+运算关系{}已存在");
        }
        if(StringUtil.isEmpty(levelMaintain.getStartData())){
            levelMaintain.setStartData(LocalDate.now());
        }
        if(StringUtil.notEmpty(levelMaintain.getStruct())){
            String struct = levelMaintain.getStruct();
            PurchaseCategory purchaseCategoryByParm = baseClient.getPurchaseCategoryByParm(new PurchaseCategory().setStruct(struct));
            if(null != purchaseCategoryByParm){
                levelMaintain.setCategoryFullName(purchaseCategoryByParm.getCategoryFullName());
            }
        }
        // 判断区间是否重复
//        if (StringUtil.notEmpty(levelMaintain.getStruct())) {
//            List<LevelMaintain> levelMaintains = this.list(new QueryWrapper<>(new LevelMaintain().setStruct(levelMaintain.getStruct())));
//            boolean intervalRepeat = isIntervalRepeat(levelMaintain, levelMaintains);
//            Assert.isTrue(!intervalRepeat,"同一品类存在条件区间重叠");
//        }


        // 转换公式
        StringBuffer formula = new StringBuffer("${value}");
        formula.append(" ").append(calculatingSigns.get(levelMaintain.getOperational()));
        formula.append(" ").append(levelMaintain.getAmount());
        levelMaintain.setFormula(formula.toString());
    }

    /**
     * 判断区间是否重复
     * @return
     */
    public boolean isIntervalRepeat(LevelMaintain levelMaintain,List<LevelMaintain> levelMaintains){
        AtomicBoolean flag = new AtomicBoolean(false);
        // 运算公式
        BigDecimal amount = levelMaintain.getAmount();
        String operational = levelMaintain.getOperational();
        if (CollectionUtils.isNotEmpty(levelMaintains)) {
            // 等于
            if(CalculatingSigns.EQUAL.getCode().equals(operational)){
                    levelMaintains.forEach(levelMaintain1 -> {
                        // 运算公式
                        String formula1 = levelMaintain1.getFormula();
                        String replace = StringUtils.replace(formula1, "${value}", String.valueOf(amount.doubleValue()));
                        boolean flag1 = (boolean) AviatorEvaluator.execute(replace);
                        flag.set(flag1);
                    });
            } else if (CalculatingSigns.LESS_THAN.getCode().equals(operational) ||
                    CalculatingSigns.LESS_THAN_EQUAL.getCode().equals(operational)){
                    for(LevelMaintain levelMaintain1 : levelMaintains){
                        // 运算公式
                        String operational1 = levelMaintain1.getOperational();
                        BigDecimal amount1 = levelMaintain1.getAmount();
                        // 小于 或 小于等于
                        if(CalculatingSigns.LESS_THAN.getCode().equals(operational1) ||
                                CalculatingSigns.LESS_THAN_EQUAL.getCode().equals(operational1)){
                            // <  <=
                            flag.set(true);
                            break;
                        }
                        // 等于
                        else if(CalculatingSigns.EQUAL.getCode().equals(operational1)){
                            // ==
                            if(CalculatingSigns.LESS_THAN.getCode().equals(operational) &&
                                    amount1.compareTo(amount) < 0){
                                flag.set(true);
                                break;
                            }else if (CalculatingSigns.LESS_THAN_EQUAL.getCode().equals(operational) &&
                                    amount1.compareTo(amount) <= 0){
                                flag.set(true);
                                break;
                            }
                        } else {
                            if(CalculatingSigns.LESS_THAN.getCode().equals(operational)){
                                // <
                                if(amount1.compareTo(amount) < 0){
                                    flag.set(true);
                                    break;
                                }
                            }else if(CalculatingSigns.LESS_THAN_EQUAL.getCode().equals(operational)){
                                // <=
                                if(amount1.compareTo(amount) <= 0){
                                    // >
                                    flag.set(true);
                                    break;
                                }
                            }

                        }

                    }
            } else { // >   >=
                for(LevelMaintain levelMaintain1 : levelMaintains){
                    // 运算公式
                    String operational1 = levelMaintain1.getOperational();
                    BigDecimal amount1 = levelMaintain1.getAmount();
                    if(CalculatingSigns.MORE_THAN.getCode().equals(operational1) ||
                            CalculatingSigns.MORE_THAN_EQUAL.getCode().equals(operational1)){
                        // >  >=
                        flag.set(true);
                        break;
                    }else if(CalculatingSigns.EQUAL.getCode().equals(operational1)){
                        // ==
                        if(CalculatingSigns.MORE_THAN.getCode().equals(operational) &&
                                amount1.compareTo(amount) > 0){
                            flag.set(true);
                            break;
                        }else if (CalculatingSigns.MORE_THAN_EQUAL.getCode().equals(operational) &&
                                amount1.compareTo(amount) >= 0){
                            flag.set(true);
                            break;
                        }
                    }else {
                        // <  或 <=               < 30
                        if(CalculatingSigns.MORE_THAN.getCode().equals(operational)){
                            // >
                            if(amount1.compareTo(amount) > 0){
                                flag.set(true);
                                break;
                            }
                        }else if(CalculatingSigns.MORE_THAN_EQUAL.getCode().equals(operational)){
                            // <=
                            if(amount1.compareTo(amount) >= 0){
                                // >
                                flag.set(true);
                                break;
                            }
                        }

                    }

                }

            }
        }
        return flag.get();
    }

    @Override
    public Long modify(LevelMaintain levelMaintain) {
        checkParam(levelMaintain);
        this.updateById(levelMaintain);
        return levelMaintain.getLevelMaintainId();
    }

    @Override
    public PageInfo<LevelMaintain> listPage(LevelMaintain levelMaintain) {
        PageUtil.startPage(levelMaintain.getPageNum(), levelMaintain.getPageSize());
        List<LevelMaintain> levelMaintains = getLevelMaintains(levelMaintain);
        return new PageInfo<>(levelMaintains);
    }

    private List<LevelMaintain> getLevelMaintains(LevelMaintain levelMaintain) {
        QueryWrapper<LevelMaintain> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtil.notEmpty(levelMaintain.getStruct()),"STRUCT", levelMaintain.getStruct());
        wrapper.eq(StringUtil.notEmpty(levelMaintain.getLevel()),"LEVEL", levelMaintain.getLevel());

        if(YesOrNo.YES.getValue().equals(levelMaintain.getIsValid())){
            wrapper.and(queryWrapper ->queryWrapper.isNull("END_DATA").or().ge("END_DATA",LocalDate.now()));
        }else if (YesOrNo.NO.getValue().equals(levelMaintain.getIsValid())){
            wrapper.isNotNull("END_DATA");
            wrapper.lt("END_DATA",LocalDate.now());
        }
        wrapper.orderByDesc("LAST_UPDATE_DATE");
        List<LevelMaintain> levelMaintains = this.list(wrapper);

        // 查找品类全路径
        if(CollectionUtils.isNotEmpty(levelMaintains)){
            List<Long> ids = new ArrayList<>();
            levelMaintains.forEach(levelMaintain1 -> {
                Long categoryId = levelMaintain1.getCategoryId();
                ids.add(categoryId);
            });
            Map<String, String> idMap = baseClient.queryCategoryFullNameByLevelIds(ids);
            levelMaintains.forEach(levelMaintain1 -> {
                Long categoryId = levelMaintain1.getCategoryId();
                String s = idMap.get(String.valueOf(categoryId));
                levelMaintain1.setCategoryFullName(s);
            });

        }
        return levelMaintains;
    }

    @Override
    public void importModelDownload(HttpServletResponse response) throws IOException {
        String fileName = "合同定级维护导入模板";
        ArrayList<LevelMaintainModelImportDto> levelMaintainImportDtos = new ArrayList<>();
        ServletOutputStream outputStream = EasyExcelUtil.getServletOutputStream(response, fileName);
        EasyExcelUtil.writeExcelWithModel(outputStream,fileName,levelMaintainImportDtos,LevelMaintainModelImportDto.class);
    }

    @Override
    @Transactional
    public Map<String, Object> importExcel(MultipartFile file, Fileupload fileupload) throws Exception {
        // 文件校验
        EasyExcelUtil.checkParam(file,fileupload);
        // 读取数据
        List<LevelMaintainImportDto> levelMaintainImportDtos = readData(file);
        List<LevelMaintain> levelMaintains = new ArrayList<>();
        AtomicBoolean errorFlag = new AtomicBoolean(false);
        checkData(errorFlag,levelMaintainImportDtos, levelMaintains);
        if(errorFlag.get()){
            // 有报错
            fileupload.setFileSourceName("合同定级维护导入报错");
            Fileupload fileupload1 = EasyExcelUtil.uploadErrorFile(fileCenterClient, fileupload,
                    levelMaintainImportDtos, LevelMaintainImportDto.class, file.getName(), file.getOriginalFilename(), file.getContentType());
            return ImportStatus.importError(fileupload1.getFileuploadId(),fileupload1.getFileSourceName());
        }else {
            if(CollectionUtils.isNotEmpty(levelMaintains)){
                levelMaintains.forEach(levelMaintain -> {
                    // 检查是否存在
                    LevelMaintain maintain = this.getOne(new QueryWrapper<>(new LevelMaintain().setCategoryId(levelMaintain.getCategoryId()).setAmount(levelMaintain.getAmount()).setOperational(levelMaintain.getOperational())));
                    if(null != maintain){
                        // 更新
                        maintain.setLevel(levelMaintain.getLevel()).setStartData(levelMaintain.getStartData()).
                                setEndData(levelMaintain.getEndData());
                        this.updateById(maintain);
                    }else {
                        // 新增
                        add(levelMaintain);
                    }
                });
            }
        }
        return ImportStatus.importSuccess();
    }

    private void checkData(AtomicBoolean errorFlag,List<LevelMaintainImportDto> levelMaintainImportDtos, List<LevelMaintain> levelMaintains) {
        if(CollectionUtils.isNotEmpty(levelMaintainImportDtos)){
            Map<String, String> dicValueKey = setMapValueKey();
            Set<String> onlyCode = new HashSet<>();
            // 导入数据
            HashMap<String, List<LevelMaintain>> levelMaintainImportMap = new HashMap<>();
            // 数据库数据
            HashMap<String, List<LevelMaintain>> levelMaintainDateMap = new HashMap<>();

            // 查找采购分类数据
            List<String> categoryNameList = new ArrayList<>();
            for(LevelMaintainImportDto levelMaintainImportDto : levelMaintainImportDtos){
                String categoryName = levelMaintainImportDto.getCategoryName();
                if(StringUtil.notEmpty(categoryName)){
                    categoryName = categoryName.trim();
                    categoryNameList.add(categoryName);
                }
            }
            Map<String, PurchaseCategory> purchaseCategoryMap = baseClient.queryPurchaseCategoryByLevelNames(categoryNameList);

            // 数据库的合同级别
            Map<String, List<LevelMaintain>> levelMaintainMap = new HashMap<>();
            if (CollectionUtils.isNotEmpty(categoryNameList)) {
                categoryNameList = categoryNameList.stream().distinct().collect(Collectors.toList());
                List<LevelMaintain> levelMaintainList = this.list(new QueryWrapper<LevelMaintain>().in("CATEGORY_NAME", categoryNameList));
                levelMaintainMap = levelMaintainList.stream().filter(levelMaintain -> StringUtil.notEmpty(levelMaintain.getStruct())).collect(Collectors.groupingBy(LevelMaintain::getStruct));
            }


            for(LevelMaintainImportDto levelMaintainImportDto : levelMaintainImportDtos) {
                StringBuffer errorMsg = new StringBuffer();
                LevelMaintain levelMaintain = new LevelMaintain();
                StringBuffer code = new StringBuffer();
                boolean flag = true;
                // 物料小类名称
                String categoryName = levelMaintainImportDto.getCategoryName();
                if(StringUtil.notEmpty(categoryName)){
                    categoryName = categoryName.trim();
                    code.append(categoryName);
                    PurchaseCategory purchaseCategory = purchaseCategoryMap.get(categoryName);
                    if(null != purchaseCategory){
                        levelMaintain.setCategoryId(purchaseCategory.getCategoryId());
                        levelMaintain.setCategoryCode(purchaseCategory.getCategoryCode());
                        levelMaintain.setCategoryName(purchaseCategory.getCategoryName());
                        levelMaintain.setStruct(purchaseCategory.getStruct());
                        levelMaintain.setCategoryFullName(purchaseCategory.getCategoryFullName());
                    }else {
                        flag = false;
                        errorMsg.append("小类名称数据库不存在; ");
                        errorFlag.set(true);
                    }
                }else {
                    flag = false;
                    errorMsg.append("小类名称不能为空; ");
                    errorFlag.set(true);
                }

                // 合同定级限制金额（万元）
                String amount = levelMaintainImportDto.getAmount();
                if(StringUtil.notEmpty(amount)){
                    amount = amount.trim();
                    code.append(amount);
                    if(StringUtil.isDigit(amount)){
                        levelMaintain.setAmount(new BigDecimal(amount));
                    }else {
                        flag = false;
                        errorMsg.append("合同定级限制金额（万元）格式非法; ");
                        errorFlag.set(true);
                    }
                }else{
                    flag = false;
                    errorMsg.append("合同定级限制金额（万元）不能为空; ");
                    errorFlag.set(true);
                }

                // 运算关系
                String operational = levelMaintainImportDto.getOperational();
                if(StringUtil.notEmpty(operational)){
                    operational = operational.trim();
                    code.append(operational);
                    if(StringUtil.notEmpty(dicValueKey.get(operational))){
                        levelMaintain.setOperational(dicValueKey.get(operational));
                    }else {
                        flag = false;
                        errorMsg.append("运算关系字典值不存在; ");
                        errorFlag.set(true);
                    }
                }else {
                    flag = false;
                    errorMsg.append("运算关系不能为空; ");
                    errorFlag.set(true);
                }
                /**
                 * 但品类/运算符/金额正缺失校验方位区间是否重复
                 * 1. 每个校验, 将检查当前品类在导入的数据里是否存在
                 * 2. 在校验数据库里的是否存在
                 */
                if (flag){
                    StringBuffer formula = new StringBuffer("${value}");
                    formula.append(" ").append(calculatingSigns.get(levelMaintain.getOperational()));
                    formula.append(" ").append(levelMaintain.getAmount());
                    levelMaintain.setFormula(formula.toString());
                    // 先检查导入数据有没有
//                    if(CollectionUtils.isNotEmpty(levelMaintainImportMap.get(levelMaintain.getStruct()))){
//                        List<LevelMaintain> maintainList = levelMaintainImportMap.get(levelMaintain.getStruct());
//                        boolean intervalRepeat = isIntervalRepeat(levelMaintain, maintainList);
//                        if(intervalRepeat){
//                            errorFlag.set(true);
//                            errorMsg.append("与导入数据比较同一品类条件范围存在区间重复; ");
//                        }
//                        maintainList.add(levelMaintain);
//                        levelMaintainImportMap.put(levelMaintain.getStruct(),maintainList);
//                    }else {
//                        ArrayList<LevelMaintain> maintains = new ArrayList<>();
//                        maintains.add(levelMaintain);
//                        levelMaintainImportMap.put(levelMaintain.getStruct(),maintains);
//                    }
                    // 检查数据库数据是否存在区间重复
//                    if(CollectionUtils.isNotEmpty(levelMaintainMap.get(levelMaintain.getStruct()))){
//                        List<LevelMaintain> maintainList = levelMaintainMap.get(levelMaintain.getStruct());
//                        boolean intervalRepeat = isIntervalRepeat(levelMaintain, maintainList);
//                        if(intervalRepeat){
//                            errorFlag.set(true);
//                            errorMsg.append("与数据库数据比较同一品类条件范围存在区间重复; ");
//                        }
//                        maintainList.add(levelMaintain);
//                    }
                }

                // 合同级别
                String level = levelMaintainImportDto.getLevel();
                if(StringUtil.notEmpty(level)){
                    level = level.trim();
                    if(StringUtil.notEmpty(dicValueKey.get(level))){
                        levelMaintain.setLevel(dicValueKey.get(level));
                    }else {
                        errorMsg.append("合同级别字典值不存在; ");
                        errorFlag.set(true);
                    }
                }else {
                    errorMsg.append("合同级别不能为空; ");
                    errorFlag.set(true);
                }

                // 生效日期
                String startData = levelMaintainImportDto.getStartData();
                if(StringUtil.notEmpty(startData)){
                    startData = startData.trim();
                    try {
                        Date date = DateUtil.parseDate(startData);
                        LocalDate localDate = DateUtil.dateToLocalDate(date);
                        levelMaintain.setStartData(localDate);
                    } catch (Exception e) {
                        errorMsg.append("生效日期格式非法; ");
                        errorFlag.set(true);
                    }
                }else {
                    levelMaintain.setStartData(LocalDate.now());
                }

                // 失效日期
                String endData = levelMaintainImportDto.getEndData();
                if(StringUtil.notEmpty(endData)){
                    endData = endData.trim();
                    try {
                        Date date = DateUtil.parseDate(endData);
                        LocalDate localDate = DateUtil.dateToLocalDate(date);
                        levelMaintain.setEndData(localDate);
                    } catch (Exception e) {
                        errorMsg.append("失效日期格式非法; ");
                        errorFlag.set(true);
                    }
                }

                if(!onlyCode.add(code.toString())){
                    errorMsg.append("物料小类+金额+运算关系存在重复; ");
                    errorFlag.set(true);
                }

                if(errorMsg.length() > 0){
                    levelMaintainImportDto.setErrorMsg(errorMsg.toString());
                }else {
                    levelMaintainImportDto.setErrorMsg(null);
                }

                levelMaintains.add(levelMaintain);
            }
        }
    }

    /**
     * 查询字典集合
     */
    public Map<String, String> setMapValueKey() {
        List<DictItemDTO> dictItemDTOS = baseClient.listByDictCode(Arrays.asList("OPERATOR","CONTARCT_LEVEL"));
        Map<String, String> map = new HashMap<>();
        if (CollectionUtils.isNotEmpty(dictItemDTOS)) {
            dictItemDTOS.forEach(dictItemDTO -> {
                map.put(dictItemDTO.getDictItemName().trim(),dictItemDTO.getDictItemCode().trim());
            });
        }
        return map;
    }



    private List<LevelMaintainImportDto> readData(MultipartFile file) {
        List<LevelMaintainImportDto> levelMaintainImportDtos = new ArrayList<>();
        try {
            // 获取输入流
            InputStream inputStream = file.getInputStream();
            // 数据收集器
            AnalysisEventListenerImpl<LevelMaintainImportDto> listener = new AnalysisEventListenerImpl<>();
            ExcelReader excelReader = EasyExcel.read(inputStream,listener).build();
            // 第一个sheet读取类型
            ReadSheet readSheet = EasyExcel.readSheet(0).head(LevelMaintainImportDto.class).build();
            // 开始读取第一个sheet
            excelReader.read(readSheet);
            levelMaintainImportDtos = listener.getDatas();
        } catch (IOException e) {
            throw new BaseException("excel解析出错");
        }
        return levelMaintainImportDtos;
    }

    @Override
    public void exportExcel(LevelMaintain levelMaintain, HttpServletResponse response) throws Exception {
        levelMaintain.setPageNum(null);
        levelMaintain.setPageSize(null);
        List<LevelMaintain> levelMaintains = getLevelMaintains(levelMaintain);
        List<LevelMaintainExportDto> levelMaintainExportDtos = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(levelMaintains)){
            Map<String, String> dicKeyValue = setMapKeyValue();
            levelMaintains.forEach(levelMaintain1 -> {
                LevelMaintainExportDto levelMaintainExportDto = new LevelMaintainExportDto();
                BeanCopyUtil.copyProperties(levelMaintainExportDto,levelMaintain1);
                // 运算关系,合同级别
                levelMaintainExportDto.setOperational(dicKeyValue.get(levelMaintain1.getOperational()));
                levelMaintainExportDto.setLevel(dicKeyValue.get(levelMaintain1.getLevel()));
                if(StringUtil.notEmpty(levelMaintain1.getStartData())){
                    String dateToStr = DateUtil.localDateToStr(levelMaintain1.getStartData());
                    levelMaintainExportDto.setStartData(dateToStr);
                }
                if(StringUtil.notEmpty(levelMaintain1.getEndData())){
                    String dateToStr = DateUtil.localDateToStr(levelMaintain1.getEndData());
                    levelMaintainExportDto.setEndData(dateToStr);
                }
                levelMaintainExportDtos.add(levelMaintainExportDto);
            });
            String fileName = "合同定级维护导出";
            ServletOutputStream outputStream = EasyExcelUtil.getServletOutputStream(response, fileName);
            EasyExcelUtil.writeExcelWithModel(outputStream,fileName,levelMaintainExportDtos,LevelMaintainExportDto.class);
        }
    }

    /**
     * 查询字典集合
     */
    public Map<String, String> setMapKeyValue() {
        List<DictItemDTO> dictItemDTOS = baseClient.listByDictCode(Arrays.asList("OPERATOR","CONTARCT_LEVEL"));
        Map<String, String> map = new HashMap<>();
        if (CollectionUtils.isNotEmpty(dictItemDTOS)) {
            dictItemDTOS.forEach(dictItemDTO -> {
                map.put(dictItemDTO.getDictItemCode().trim(),dictItemDTO.getDictItemName().trim());
            });
        }
        return map;
    }
}
