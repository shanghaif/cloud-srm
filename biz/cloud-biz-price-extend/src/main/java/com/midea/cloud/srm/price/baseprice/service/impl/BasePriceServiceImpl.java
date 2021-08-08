package com.midea.cloud.srm.price.baseprice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.enums.price.costelement.CostElementStatus;
import com.midea.cloud.common.enums.price.costelement.DisplayCategory;
import com.midea.cloud.common.enums.price.costelement.ElementType;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.listener.AnalysisEventListenerImpl;
import com.midea.cloud.common.utils.*;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseCurrency;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.price.baseprice.dto.BasePriceDto;
import com.midea.cloud.srm.model.price.baseprice.entity.BasePrice;
import com.midea.cloud.srm.price.baseprice.mapper.BasePriceMapper;
import com.midea.cloud.srm.price.baseprice.service.IBasePriceService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
*  <pre>
 *  基价表 服务实现类
 * </pre>
*
* @author wangpr@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-07-25 14:16:13
 *  修改内容:
 * </pre>
*/
@Service
public class BasePriceServiceImpl extends ServiceImpl<BasePriceMapper, BasePrice> implements IBasePriceService {
    @Resource
    private BaseClient baseClient;
    @Resource
    private FileCenterClient fileCenterClient;

    @Override
    public PageInfo<BasePrice> listPage(BasePrice basePrice) {
        PageUtil.startPage(basePrice.getPageNum(),basePrice.getPageSize());
        List<BasePrice> basePriceList = this.getBasePrices(basePrice);
        return new PageInfo<>(basePriceList);
    }

    public List<BasePrice> getBasePrices(BasePrice basePrice) {
        QueryWrapper<BasePrice> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtil.notEmpty(basePrice.getCombinationCode()),"COMBINATION_CODE",basePrice.getCombinationCode());
        queryWrapper.eq(StringUtil.notEmpty(basePrice.getElementType()),"ELEMENT_TYPE",basePrice.getElementType());
        queryWrapper.like(StringUtil.notEmpty(basePrice.getElementCode()),"ELEMENT_CODE",basePrice.getElementCode());
        queryWrapper.like(StringUtil.notEmpty(basePrice.getElementName()),"ELEMENT_NAME",basePrice.getElementName());
        queryWrapper.eq(StringUtil.notEmpty(basePrice.getStatus()),"STATUS",basePrice.getStatus());
        queryWrapper.eq(StringUtil.notEmpty(basePrice.getCreatedBy()),"CREATED_BY",basePrice.getCreatedBy());
        queryWrapper.eq(StringUtil.notEmpty(basePrice.getCreationDate()),"CREATION_DATE",basePrice.getCreationDate());
        queryWrapper.ge(StringUtil.notEmpty(basePrice.getStartDate()),"START_DATE",basePrice.getStartDate());
        queryWrapper.le(StringUtil.notEmpty(basePrice.getEndDate()),"END_DATE",basePrice.getEndDate());
        queryWrapper.ne("DISPLAY_CATEGORY", DisplayCategory.SUB_TABLE.getKey());
        queryWrapper.orderByDesc("LAST_UPDATE_DATE");
        return this.list(queryWrapper);
    }

    @Override
    public Long add(BasePrice basePrice) {
        return null;
    }

    @Override
    public void takeEffect(Long basePriceId) {
        BasePrice basePrice = this.getById(basePriceId);
        if(null != basePrice){
            // 检查有效期是否大于当前时间
            if(StringUtil.notEmpty(basePrice.getEndDate())){
                LocalDate endDate = basePrice.getEndDate();
                Assert.isTrue(endDate.isAfter(LocalDate.now()),"有效日期必须大于当前时间");
            }
            basePrice.setStatus(CostElementStatus.VALID.getKey());
            this.updateById(basePrice);
        }
    }

    @Override
    public void failure(Long basePriceId) {
        BasePrice basePrice = this.getById(basePriceId);
        if(null != basePrice){
            // 检查有效期是否小于当前时间
            if(StringUtil.notEmpty(basePrice.getEndDate())){
                LocalDate endDate = basePrice.getEndDate();
                if(endDate.isAfter(LocalDate.now())){
                    LocalDate localDate = LocalDate.now().minusDays(1);
                    basePrice.setEndDate(localDate);
                }
            }
            basePrice.setStatus(CostElementStatus.INVALID.getKey());
            this.updateById(basePrice);
        }
    }

    /**
     * 根据成本要素编码和属性值查询
     * 有效的
     * @param basePrice
     * @return
     */
    @Override
    public BasePrice queryBy(BasePrice basePrice) {
        Assert.notNull(basePrice.getPriceVersion(),"缺少参数: PriceVersion");
        Assert.notNull(basePrice.getElementCode(),"缺少参数: ElementCode");
        Assert.notNull(basePrice.getAttributeValueCombination(),"缺少参数: AttributeValueCombination");
        String attributeValueCombination = basePrice.getAttributeValueCombination();
        // 获取属性列表
        List<String> bracketsList = StringUtil.getBracketsList(attributeValueCombination);
        BasePrice basePrice1 = null;
        basePrice.setAttributeValueCombination(null);
        QueryWrapper<BasePrice> queryWrapper = new QueryWrapper<>(basePrice);
        queryWrapper.eq("STATUS",CostElementStatus.VALID.getKey());
        queryWrapper.and(query->query.ge("END_DATE",LocalDate.now()).or().isNull("END_DATE"));
        if(CollectionUtils.isNotEmpty(bracketsList)){
            bracketsList.forEach(s -> {
                queryWrapper.like("ATTRIBUTE_VALUE_COMBINATION",s);
            });
        }
        queryWrapper.orderByDesc("BASE_PRICE_ID");
        List<BasePrice> basePrices = this.list(queryWrapper);
        if (CollectionUtils.isNotEmpty(basePrices)){
            basePrice1 = basePrices.get(0);
        }
        return basePrice1;
    }

    @Override
    public void importModelDownload(BasePrice basePrice, HttpServletResponse response) throws IOException {
        List<BasePrice> basePrices = getBasePrices(basePrice);
        if(CollectionUtils.isNotEmpty(basePrices)){
            // 获取字典值
            HashMap<String, String> dicMap = new HashMap<>();
            // 币种
            List<PurchaseCurrency> purchaseCurrencies = baseClient.listCurrencyAll();
            if (CollectionUtils.isNotEmpty(purchaseCurrencies)) {
                purchaseCurrencies.forEach(purchaseCurrency -> {
                    dicMap.put(purchaseCurrency.getCurrencyCode(),purchaseCurrency.getCurrencyName());
                });
            }
            dicMap.put(ElementType.MATERIAL.getKey(),ElementType.MATERIAL.getValue());
            dicMap.put(ElementType.CRAFT.getKey(),ElementType.CRAFT.getValue());
            dicMap.put(ElementType.FEE.getKey(),ElementType.FEE.getValue());

            ArrayList<BasePriceDto> basePriceDtos = new ArrayList<>();
            for (BasePrice base : basePrices){
                BasePriceDto basePriceDto = new BasePriceDto();
                BeanCopyUtil.copyProperties(basePriceDto,base);
                // 基价
                basePriceDto.setBasePrice(StringUtil.subZeroAndDot(base.getBasePrice().toString()));
                // 生效日期
                LocalDate startDate = base.getStartDate();
                if (null != startDate) {
                    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    basePriceDto.setStartDate(startDate.format(dateTimeFormatter));
                }
                // 失效日期
                LocalDate endDate = base.getEndDate();
                if (null != endDate) {
                    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    basePriceDto.setEndDate(endDate.format(dateTimeFormatter));
                }
                // 币种
                String clearCurrency = base.getClearCurrency();
                if(StringUtil.notEmpty(clearCurrency)){
                    basePriceDto.setClearCurrency(dicMap.get(clearCurrency));
                }
                // 要素类型
                String elementType = base.getElementType();
                if(StringUtil.notEmpty(elementType)){
                    basePriceDto.setElementType(dicMap.get(elementType));
                }
                basePriceDtos.add(basePriceDto);
            }
            String fileName = "基价导入模板";
            ServletOutputStream outputStream = EasyExcelUtil.getServletOutputStream(response, fileName);
            EasyExcelUtil.writeExcelWithModel(outputStream,fileName,basePriceDtos,BasePriceDto.class);
        }else {
            throw new BaseException("没有找到可导入的基价数据");
        }
    }

    @Override
    public Map<String, Object> importExcel(MultipartFile file, Fileupload fileupload) {
        // 文件校验
        EasyExcelUtil.checkParam(file,fileupload);
        // 是否有错误标识
        boolean flag = false;
        // 结果
        Map<String, Object> result = new HashMap<>();
        result.put("status", YesOrNo.YES.getValue());
        result.put("message","success");
        // 读取数据
        List<BasePriceDto> basePriceDtos = this.readData(file);
        if(CollectionUtils.isNotEmpty(basePriceDtos)){
            // 获取字典值
            HashMap<String, String> dicMap = new HashMap<>();
            // 币种
            List<PurchaseCurrency> purchaseCurrencies = baseClient.listCurrencyAll();
            if (CollectionUtils.isNotEmpty(purchaseCurrencies)) {
                purchaseCurrencies.forEach(purchaseCurrency -> {
                    dicMap.put(purchaseCurrency.getCurrencyName(), purchaseCurrency.getCurrencyCode());
                });
            }

            List<BasePrice> basePrices = new ArrayList<>();
            // 检查数据
            this.checkData(basePriceDtos, dicMap, basePrices);

            if(flag){
                // 有报错
                fileupload.setFileSourceName("基价导入报错");
                Fileupload fileupload1 = EasyExcelUtil.uploadErrorFile(fileCenterClient, fileupload,
                        basePriceDtos, BasePriceDto.class, file.getName(), file.getOriginalFilename(), file.getContentType());
                result.put("status", YesOrNo.NO.getValue());
                result.put("message","error");
                result.put("fileuploadId",fileupload1.getFileuploadId());
                result.put("fileName",fileupload1.getFileSourceName());
            }else {
                if(CollectionUtils.isNotEmpty(basePrices)){
                    this.updateBatchById(basePrices);
                }
            }
        }
        return result;
    }

    public void checkData(List<BasePriceDto> basePriceDtos, HashMap<String, String> dicMap, List<BasePrice> basePrices) {
        boolean flag;
        for(BasePriceDto basePriceDto : basePriceDtos){
            StringBuffer errorMsg = new StringBuffer();
            BasePrice basePrice = new BasePrice();
            HashSet<String> hashSet = new HashSet<>();

            // 检验组合编码, 不能有重复, 并且要存在
            String combinationCode = basePriceDto.getCombinationCode();
            if (StringUtil.notEmpty(combinationCode)) {
                if (hashSet.add(combinationCode)) {
                    BasePrice base = this.getOne(new QueryWrapper<>(new BasePrice().setCombinationCode(combinationCode.trim())));
                    if(null == base){
                        flag = true;
                        errorMsg.append("该组合编码不存在 ;");
                    }else {
                        basePrice = base;
                    }
                }else {
                    flag = true;
                    errorMsg.append("该组合编码存在重复 ;");
                }
            }else {
                flag = true;
                errorMsg.append("组合编码不能为空 ;");
            }

            // 检验基价
            String price = basePriceDto.getBasePrice();
            if(StringUtil.notEmpty(price)){
                if(StringUtil.isDigit(price.trim())){
                    basePrice.setBasePrice(new BigDecimal(price.trim()));
                }else {
                    flag = true;
                    errorMsg.append("基价非数字 ;");
                }
            }

            // 检验币种
            String clearCurrency = basePriceDto.getClearCurrency();
            if(StringUtil.notEmpty(clearCurrency)){
                if (dicMap.containsKey(clearCurrency.trim())){
                    String value = dicMap.get(clearCurrency.trim());
                    basePrice.setClearCurrency(value);
                }else {
                    flag = true;
                    errorMsg.append("该币种字典值不存在 ;");
                }
            }

            // 检验生效日期
            String startDate = basePriceDto.getStartDate();
            if(StringUtil.notEmpty(startDate)){
                try {
                    Date date = DateUtil.parseDate(startDate.trim());
                    LocalDate localDate = DateUtil.dateToLocalDate(date);
                    basePrice.setStartDate(localDate);
                } catch (Exception e) {
                    flag = true;
                    errorMsg.append("生效日期格式无法解析 ;");
                }
            }

            // 检验失效日期
            String endDate = basePriceDto.getEndDate();
            if(StringUtil.notEmpty(endDate)){
                try {
                    Date date = DateUtil.parseDate(endDate.trim());
                    LocalDate localDate = DateUtil.dateToLocalDate(date);
                    basePrice.setEndDate(localDate);
                } catch (Exception e) {
                    flag = true;
                    errorMsg.append("失效日期格式无法解析 ;");
                }
            }

            if(errorMsg.length() > 1){
                basePriceDto.setErrorMsg(errorMsg.toString());
            }else {
                basePriceDto.setErrorMsg(null);
            }
            basePrices.add(basePrice);
        }
    }

    public List<BasePriceDto> readData(MultipartFile file) {
        List<BasePriceDto> basePriceDtos;
        try {
            // 获取输入流
            InputStream inputStream = file.getInputStream();
            // 数据收集器
            AnalysisEventListenerImpl<BasePriceDto> listener = new AnalysisEventListenerImpl<>();
            ExcelReader excelReader = EasyExcel.read(inputStream,listener).build();
            // 第一个sheet读取类型
            ReadSheet readSheet = EasyExcel.readSheet(0).head(BasePriceDto.class).build();
            // 开始读取第一个sheet
            excelReader.read(readSheet);
            basePriceDtos = listener.getDatas();
        } catch (IOException e) {
            throw new BaseException("excel解析出错");
        }
        return basePriceDtos;
    }
}
