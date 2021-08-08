package com.midea.cloud.srm.bid.suppliercooperate.projectlist.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.enums.ImportStatus;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.enums.bid.projectmanagement.signupmanagement.SignUpStatus;
import com.midea.cloud.common.enums.bid.projectmanagement.signupmanagement.VendorFileType;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.listener.AnalysisEventListenerImpl;
import com.midea.cloud.common.utils.*;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service.IBidVendorService;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service.IBidingService;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.techproposal.service.IOrderHeadService;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.techproposal.service.IOrderLineService;
import com.midea.cloud.srm.bid.suppliercooperate.projectlist.mapper.BidSignUpMapper;
import com.midea.cloud.srm.bid.suppliercooperate.projectlist.service.IBidSignUpService;
import com.midea.cloud.srm.bid.suppliercooperate.projectlist.service.IBidVendorFileService;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.bid.BidOrderLineFormulaPriceDetailClient;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.model.base.dict.dto.DictItemDTO;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseTax;
import com.midea.cloud.srm.model.base.region.dto.AreaDTO;
import com.midea.cloud.srm.model.base.region.dto.AreaPramDTO;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.entity.BidBidingCurrency;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.entity.BidVendor;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.entity.Biding;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.businessproposal.entity.SignUp;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.techproposal.entity.OrderLine;
import com.midea.cloud.srm.model.bid.suppliercooperate.entity.BidVendorFile;
import com.midea.cloud.srm.model.bid.suppliercooperate.vo.*;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
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
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
*  <pre>
 *  供应商报名记录表 服务实现类
 * </pre>
*
* @author zhuomb1@midea.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:tanjl11@meicloud.com
 *  修改日期: 2020-09-11 13:54:22
 *  修改内容:
 * </pre>
*/
@Service
@Slf4j
public class BidSignUpServiceImpl extends ServiceImpl<BidSignUpMapper, SignUp> implements IBidSignUpService {
    @Autowired
    private IBidVendorFileService vendorFileService;
    @Autowired
    private IBidVendorService bidVendorService;
    @Autowired
    private IBidingService iBidingService;
    @Autowired
    private IOrderHeadService iOrderHeadService;
    @Autowired
    private BaseClient baseClient;
    @Resource
    private BidOrderLineFormulaPriceDetailClient bidOrderLineFormulaPriceDetailClient;
    @Resource
    private IOrderLineService orderLineService;
    @Resource
    private FileCenterClient fileCenterClient;

    @Override
    @Transactional
    public Map<String, Object> importExcel(MultipartFile file, Fileupload fileupload, Long bidingId) throws Exception {
        Assert.notNull(bidingId,"缺少参数:bidingId");
        // 文件校验
        EasyExcelUtil.checkParam(file,fileupload);
        List<BidOrderLineImportVO> bidOrderLineImportVOS = readData(file);
        Map<String, Object> result = ImportStatus.importSuccess();
        AtomicBoolean flag = new AtomicBoolean(false);
        List<OrderLine> orderLines = new ArrayList<>();
        // 检查数据
        checkData(bidingId, bidOrderLineImportVOS, flag, orderLines);
        if(flag.get()){
            // 有报错
            fileupload.setFileSourceName("报价行导入报错");
            Fileupload fileupload1 = EasyExcelUtil.uploadErrorFile(fileCenterClient, fileupload,
                    bidOrderLineImportVOS, BidOrderLineImportVO.class, file.getName(), file.getOriginalFilename(), file.getContentType());
            return ImportStatus.importError(fileupload1.getFileuploadId(),fileupload1.getSceneFileSourceName());
        }else {
            if(CollectionUtils.isNotEmpty(orderLines)){
                orderLineService.updateBatchById(orderLines);
            }
        }
        return result;
    }

    private void checkData(Long bidingId, List<BidOrderLineImportVO> bidOrderLineImportVOS, AtomicBoolean flag, List<OrderLine> orderLines) {
        Set<String> set = bidOrderLineImportVOS.stream().map(BidOrderLineImportVO::getOrderLineId).filter(StringUtils::isNotEmpty).map(String::trim).collect(Collectors.toSet());
        List<OrderLine> result = orderLineService.list(Wrappers.lambdaQuery(OrderLine.class).in(OrderLine::getOrderLineId, set));
        if(!Objects.equals(result.size(),bidOrderLineImportVOS.size())){
            flag.set(false);
        }
        Map<Long, OrderLine> orderLineMap = result.stream().collect(Collectors.toMap(OrderLine::getOrderLineId, Function.identity()));
        Set<Long> idSet=orderLineMap.keySet();
        Map<String, String> importDicValueKey = getImportDicValueKey(bidingId);
        if(CollectionUtils.isNotEmpty(bidOrderLineImportVOS)){
            Map<String, Map<String, String>> valueKey = getValueKey();
            bidOrderLineImportVOS.forEach(bidOrderLineImportVO -> {
                OrderLine orderLine = new OrderLine();
                StringBuffer errorMsg = new StringBuffer();
                // 唯一标识
                String orderLineId = bidOrderLineImportVO.getOrderLineId();
                if(StringUtils.isNotEmpty(orderLineId)){
                    orderLineId = orderLineId.trim();
                    long id = Long.parseLong(orderLineId);
                    if(idSet.contains(id)){
                        orderLine = orderLineMap.get(id);
                    }else {
                        flag.set(true);
                        errorMsg.append("数据库找不着行内容, 请勿修改行唯一标识; ");
                    }
                }else {
                    flag.set(true);
                    errorMsg.append("行唯一标识不能为空; ");
                }

                // 运输方式
                String transportType = bidOrderLineImportVO.getTransportType();
                if(StringUtil.notEmpty(transportType)){
                    transportType = transportType.trim();
                    if(StringUtil.notEmpty(valueKey.get("TRANSF_TYPE").get(transportType))){
                        orderLine.setTransportType(valueKey.get("TRANSF_TYPE").get(transportType));
                    }else {
                        flag.set(true);
                        errorMsg.append("运输方式字典值不存在; ");
                    }
                }
//                // 供货周期
//                String leadTime = bidOrderLineImportVO.getLeadTime();
//                if(StringUtil.notEmpty(leadTime)){
//                    leadTime = leadTime.trim();
//                    if(StringUtil.isDigit(leadTime)){
//                        orderLine.setLeadTime(Integer.parseInt(leadTime));
//                    }else {
//                        flag.set(true);
//                        errorMsg.append("供货周期必须是整数; ");
//                    }
//                }
                // 报价币种
                String currencyType = bidOrderLineImportVO.getCurrencyType();
                if(StringUtil.notEmpty(currencyType)){
                    currencyType = currencyType.trim();
                    if(StringUtil.notEmpty(importDicValueKey.get(currencyType))){
                        orderLine.setCurrencyType(importDicValueKey.get(currencyType));
                    }else {
                        flag.set(true);
                        errorMsg.append("币种字典值不存在或没该币种权限; ");
                    }
                }
                // MQO
                String mqo = bidOrderLineImportVO.getMQO();
                if(StringUtil.notEmpty(mqo)){
                    mqo = mqo.trim();
                    orderLine.setMQO(mqo);
                }
                // 含税报价
                String price = bidOrderLineImportVO.getPrice();
                if(StringUtil.notEmpty(price)){
                    price = price.trim();
                    if(StringUtil.isDigit(price)){
                        orderLine.setPrice(new BigDecimal(price));
                    }else {
                        flag.set(true);
                        errorMsg.append("含税报价数字格式非法; ");
                    }
                }
                // 税率
                String taxRate = bidOrderLineImportVO.getTaxRate();
                if(StringUtil.notEmpty(taxRate)){
                    taxRate = StringUtil.subZeroAndDot(taxRate.trim());
                    if(StringUtil.notEmpty(importDicValueKey.get(taxRate))){
                        orderLine.setTaxRate(new BigDecimal(taxRate));
                        orderLine.setTaxKey(importDicValueKey.get(taxRate));
                    }else {
                        flag.set(true);
                        errorMsg.append("税率值不存在; ");
                    }
                }
                //质保期
                String warrantyPeriod = bidOrderLineImportVO.getWarrantyPeriod();
                if(StringUtils.isNotEmpty(warrantyPeriod)){
                    warrantyPeriod = warrantyPeriod.trim();
                    try {
                        orderLine.setWarrantyPeriod(Integer.parseInt(warrantyPeriod));
                    }catch (Exception e){
                        flag.set(true);
                        errorMsg.append("质保期数字格式非法; ");
                    }
                }
                // 承诺交期
                String deliverDate = bidOrderLineImportVO.getDeliverDate();
                if(StringUtil.notEmpty(deliverDate)){
                    deliverDate = deliverDate.trim();
                    try {
                        Date date = DateUtil.parseDate(deliverDate);
                        orderLine.setDeliverDate(date);
                    } catch (ParseException e) {
                        flag.set(true);
                        errorMsg.append("承诺交期格式非法; ");
                    }
                }
                // 备注
                String commnets = bidOrderLineImportVO.getCommnets();
                if(StringUtil.notEmpty(commnets)){
                    commnets = commnets.trim();
                    orderLine.setComments(commnets);
                }

                if(errorMsg.length() > 0){
                    bidOrderLineImportVO.setErrorMsg(errorMsg.toString());
                }else {
                    bidOrderLineImportVO.setErrorMsg(null);
                }

                orderLines.add(orderLine);

            });
        }
    }

    private Map<String,Map<String,String>> getValueKey(){
        HashMap<String, Map<String, String>> hashMap = new HashMap<>();

        // 查询字典
        hashMap.put("trade_clause",setMapValueKey("trade_clause")); // 贸易条款
        hashMap.put("TRANSF_TYPE",setMapValueKey("TRANSF_TYPE")); // 运输方式
        hashMap.put("PURCHASE_TYPE",setMapValueKey("PURCHASE_TYPE")); // 采购类型

        return hashMap;
    }

    /**
     * 查询字典集合
     *
     * @param dicCode
     */
    public Map<String, String> setMapValueKey(String dicCode) {
        List<DictItemDTO> dictItemDTOS = baseClient.listAllByDictCode(dicCode);
        HashMap<String, String> map = new HashMap<>();
        if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(dictItemDTOS)) {
            dictItemDTOS.forEach(dictItemDTO -> {
                map.put(dictItemDTO.getDictItemName(),dictItemDTO.getDictItemCode());
            });
        }
        return map;
    }

    private List<BidOrderLineImportVO> readData(MultipartFile file) {
        List<BidOrderLineImportVO> bidOrderLineImportVOS;
        try {
            // 获取输入流
            InputStream inputStream = file.getInputStream();
            // 数据收集器
            AnalysisEventListenerImpl<BidOrderLineImportVO> listener = new AnalysisEventListenerImpl<>();
            ExcelReader excelReader = EasyExcel.read(inputStream,listener).build();
            // 第一个sheet读取类型
            ReadSheet readSheet = EasyExcel.readSheet(0).head(BidOrderLineImportVO.class).build();
            // 开始读取第一个sheet
            excelReader.read(readSheet);
            bidOrderLineImportVOS = listener.getDatas();
        } catch (IOException e) {
            throw new BaseException("excel解析出错");
        }
        return bidOrderLineImportVOS;
    }

    @Override
    public void importModelDownload(BidOrderHeadVO bidOrderHeadVO, HttpServletResponse response) throws Exception {
        Assert.notNull(bidOrderHeadVO.getBidingId(),"缺少参数:bidingId");
        Assert.notNull(bidOrderHeadVO.getOrderHeadId(),"缺少参数:orderHeadId");
        Assert.notNull(bidOrderHeadVO.getBidVendorId(),"缺少参数:bidVendorId");
        // 查找行数据
        List<BidOrderLineVO> orderLineVOS = iOrderHeadService.getOrderLineVOS(bidOrderHeadVO.getBidingId(), bidOrderHeadVO.getOrderHeadId(), bidOrderHeadVO.getBidVendorId());
        Map<String, String> dicMap = getImportDic(bidOrderHeadVO.getBidingId());
        if(CollectionUtils.isNotEmpty(orderLineVOS)){
            ArrayList<BidOrderLineExportVO> bidOrderLineExportVOS = new ArrayList<>();
            Map<String, Map<String, String>> dicMap1 = getDicMap();
            orderLineVOS.forEach(bidOrderLineVO -> {
                BidOrderLineExportVO bidOrderLineExportVO = new BidOrderLineExportVO();
                BeanCopyUtil.copyProperties(bidOrderLineExportVO,bidOrderLineVO);
                // 唯一标识
                bidOrderLineExportVO.setOrderLineId(String.valueOf(bidOrderLineVO.getOrderLineId()));
                // 交货地点
                if(StringUtil.notEmpty(bidOrderLineVO.getDeliveryPlace())){
                    List<String> strings = JSON.parseArray(bidOrderLineVO.getDeliveryPlace(), String.class);
                    // 省份
                    String province = dicMap.get(strings.get(0));
                    // 市
                    AreaPramDTO areaPramDTO = new AreaPramDTO();
                    areaPramDTO.setParentId(Long.parseLong(strings.get(0)));
                    areaPramDTO.setQueryType("city");
                    List<AreaDTO> areaDTOS = baseClient.queryRegionById(areaPramDTO);
                    String city = areaDTOS.get(0).getCity();
                    bidOrderLineExportVO.setDeliveryPlace(province+"/"+city);
                }
                // 是否为基准OU
                if(YesOrNo.YES.getValue().equals(bidOrderLineVO.getBaseOu())){
                    bidOrderLineExportVO.setBaseOu(YesOrNo.YES.getName());
                }else {
                    bidOrderLineExportVO.setBaseOu(YesOrNo.NO.getName());
                }
                // 贸易条款
                if(StringUtil.notEmpty(bidOrderLineVO.getTradeTerm()) && StringUtil.notEmpty(dicMap1.get("trade_clause").get(bidOrderLineVO.getTradeTerm()))){
                    bidOrderLineExportVO.setTradeTerm(dicMap1.get("trade_clause").get(bidOrderLineVO.getTradeTerm()));
                }
                // 运输方式
                if(StringUtil.notEmpty(bidOrderLineVO.getTransportType()) && StringUtil.notEmpty(dicMap1.get("TRANSF_TYPE").get(bidOrderLineVO.getTransportType()))){
                    bidOrderLineExportVO.setTransportType(dicMap1.get("TRANSF_TYPE").get(bidOrderLineVO.getTransportType()));
                }
                // 采购类型
                if(StringUtil.notEmpty(bidOrderLineVO.getPurchaseType()) && StringUtil.notEmpty(dicMap1.get("PURCHASE_TYPE").get(bidOrderLineVO.getPurchaseType()))){
                    bidOrderLineExportVO.setPurchaseType(dicMap1.get("PURCHASE_TYPE").get(bidOrderLineVO.getPurchaseType()));
                }
                // 报价币种
                if(StringUtil.notEmpty(bidOrderLineVO.getCurrencyType()) && StringUtil.notEmpty(dicMap.get(bidOrderLineVO.getCurrencyType()))){
                    bidOrderLineExportVO.setCurrencyType(dicMap.get(bidOrderLineVO.getCurrencyType()));
                }
                // 税率
//                if(StringUtil.notEmpty(bidOrderLineVO.getTaxRate()) && StringUtil.notEmpty(dicMap.get(bidOrderLineVO.getTaxRate().toString()))){
//                    bidOrderLineExportVO.setTaxKey(dicMap.get(bidOrderLineVO.getTaxRate().toString()) + "/" + bidOrderLineVO.getTaxRate().doubleValue());
//                }
                bidOrderLineExportVOS.add(bidOrderLineExportVO);
            });
            String fileName = "报价行导入模板";
            ServletOutputStream outputStream = EasyExcelUtil.getServletOutputStream(response, fileName);
            EasyExcelUtil.writeExcelWithModel(outputStream,fileName,bidOrderLineExportVOS, BidOrderLineExportVO.class);
        }
    }

    private Map<String,Map<String,String>> getDicMap(){
        HashMap<String, Map<String, String>> hashMap = new HashMap<>();

        // 查询字典
        hashMap.put("trade_clause",setMapKeyValue("trade_clause")); // 贸易条款
        hashMap.put("TRANSF_TYPE",setMapKeyValue("TRANSF_TYPE")); // 运输方式
        hashMap.put("PURCHASE_TYPE",setMapKeyValue("PURCHASE_TYPE")); // 采购类型

        return hashMap;
    }

    /**
     * 查询字典集合
     *
     * @param dicCode
     */
    public Map<String, String> setMapKeyValue(String dicCode) {
        List<DictItemDTO> dictItemDTOS = baseClient.listAllByDictCode(dicCode);
        HashMap<String, String> map = new HashMap<>();
        if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(dictItemDTOS)) {
            dictItemDTOS.forEach(dictItemDTO -> {
                map.put(dictItemDTO.getDictItemCode(),dictItemDTO.getDictItemName());
            });
        }
        return map;
    }

    public Map<String, String> getImportDicValueKey(Long bidingId) {
        HashMap<String, String> dicMap = new HashMap<>();
        // 所有的省份
        AreaPramDTO areaPramDTO = new AreaPramDTO();
        areaPramDTO.setQueryType("province");
        List<AreaDTO> regions = baseClient.queryRegionById(areaPramDTO);
        if (CollectionUtils.isNotEmpty(regions)) {
            regions.forEach(region -> {
                dicMap.put(region.getProvince(),region.getProvinceId().toString());
            });
        }

        // 报价币种
        List<BidBidingCurrency> bidBidingCurrencies = bidOrderLineFormulaPriceDetailClient.getCurrencyByBidId(bidingId);
        if(CollectionUtils.isNotEmpty(bidBidingCurrencies)){
            bidBidingCurrencies.forEach(bidBidingCurrency -> {
                dicMap.put(bidBidingCurrency.getCurrencyName(),bidBidingCurrency.getCurrencyCode());
            });
        }

        // 税率
        List<PurchaseTax> purchaseTaxes = baseClient.listTaxAll();
        if(com.baomidou.mybatisplus.core.toolkit.CollectionUtils.isNotEmpty(purchaseTaxes)){
            purchaseTaxes.forEach(purchaseTax -> {
                if (StringUtil.notEmpty(purchaseTax.getTaxCode())) {
                    dicMap.put(StringUtil.subZeroAndDot(purchaseTax.getTaxCode().toString()),String.valueOf(purchaseTax.getTaxKey()));
                }
            });
        }

        // 所有单位 purchase/purchaseUnit/listAll
//        List<PurchaseUnit> purchaseUnits = baseClient.listAllEnablePurchaseUnit();
//        if (CollectionUtils.isNotEmpty(purchaseUnits)) {
//            purchaseUnits.forEach(purchaseUnit -> {
//                dicMap.put(purchaseUnit.getUnitName(), purchaseUnit.getUnitCode());
//            });
//        }
        return dicMap;
    }

    public Map<String, String> getImportDic(Long bidingId) {
        HashMap<String, String> dicMap = new HashMap<>();
        // 所有的省份
        AreaPramDTO areaPramDTO = new AreaPramDTO();
        areaPramDTO.setQueryType("province");
        List<AreaDTO> regions = baseClient.queryRegionById(areaPramDTO);
        if (CollectionUtils.isNotEmpty(regions)) {
            regions.forEach(region -> {
                dicMap.put(region.getProvinceId().toString(),region.getProvince());
            });
        }

        // 报价币种
        List<BidBidingCurrency> bidBidingCurrencies = bidOrderLineFormulaPriceDetailClient.getCurrencyByBidId(bidingId);
        if(CollectionUtils.isNotEmpty(bidBidingCurrencies)){
            bidBidingCurrencies.forEach(bidBidingCurrency -> {
                dicMap.put(bidBidingCurrency.getCurrencyCode(),bidBidingCurrency.getCurrencyName());
            });
        }

        // 税率
        List<PurchaseTax> purchaseTaxes = baseClient.listTaxAll();
        if(com.baomidou.mybatisplus.core.toolkit.CollectionUtils.isNotEmpty(purchaseTaxes)){
            purchaseTaxes.forEach(purchaseTax -> {
                if (StringUtil.notEmpty(purchaseTax.getTaxCode())) {
                    dicMap.put(String.valueOf(purchaseTax.getTaxCode()),purchaseTax.getTaxKey());
                }
            });
        }

        // 所有单位 purchase/purchaseUnit/listAll
//        List<PurchaseUnit> purchaseUnits = baseClient.listAllEnablePurchaseUnit();
//        if (CollectionUtils.isNotEmpty(purchaseUnits)) {
//            purchaseUnits.forEach(purchaseUnit -> {
//                dicMap.put(purchaseUnit.getUnitName(), purchaseUnit.getUnitCode());
//            });
//        }
        return dicMap;
    }



    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long saveSignUpInfo(BidSignUpVO bidSignUpVO) {
        //1 保存到bid_sign_up表
        SignUp signUp = getById(bidSignUpVO.getSignUpId());
        if(null != signUp){
            bidSignUpVO.setSignUpId(signUp.getSignUpId());
            //更新报名信息
            this.updateById(signUp);
        }else{
            signUp = new SignUp();
            signUp.setBidingId(bidSignUpVO.getBidingId());
            signUp.setVendorId(bidSignUpVO.getVendorId());
            signUp.setSignUpStatus(bidSignUpVO.getSignUpStatus());
            //新增
            Long id = IdGenrator.generate();
            signUp.setSignUpId(id);
            this.save(signUp);
            bidSignUpVO.setSignUpId(id);
        }

        //2 当招标范围是公开招标，在报名的时候，根据招标单ID"BIDING_ID"和"vendor_id"查找“scc_bid_vendor”表是否有数据，如果有则更新，如果没有则新增一条记录
        // 如果报名成功，则要根据招标单ID"BIDING_ID"和"vendor_id"更新供应商表scc_bid_vendor的"报名标识"为"Y"
        Long bidVendorId = bidVendorService.saveBidVendorWhenSignUp(bidSignUpVO);

        //3 保存文件上传信息表
        List<BidVendorFileVO> vendorFileVOS = bidSignUpVO.getVendorFileVOs();
        String fileType = VendorFileType.ENROLL.getValue();
        vendorFileVOS.forEach(vendorFileVO -> {
            vendorFileVO.setFileType(fileType);
            vendorFileVO.setBusinessId(bidSignUpVO.getSignUpId());
            vendorFileVO.setBidingId(bidSignUpVO.getBidingId());
            vendorFileVO.setVendorId(bidSignUpVO.getVendorId());});
        if(vendorFileVOS == null ||vendorFileVOS.size() == 0) {
            vendorFileService.remove(
                    Wrappers.lambdaQuery(BidVendorFile.class)
                            .eq(BidVendorFile::getBidingId,signUp.getBidingId())
            );
        }
        boolean vendorFileflag = vendorFileService.saveBatchVendorFilesByBusinessIdAndFileType(vendorFileVOS);

        return bidSignUpVO.getSignUpId();

    }

    /**
     * 判断是否已经报名
     * @param bidingId
     * @param vendorId
     * @return
     */
    @Override
    public SignUp alreadySignUpByBidingAndVendorId(long bidingId, long vendorId) {
        QueryWrapper wrapper = new QueryWrapper(new SignUp().setBidingId(bidingId).setVendorId(vendorId));
        //报名状态不是驳回状态
        wrapper.notIn("SIGN_UP_STATUS",SignUpStatus.REJECTED.getValue());
        SignUp signUp = this.getOne(wrapper);
        return signUp;
    }

    @Override
    public BidSignUpVO getBidSignUpVO(BidSignUpVO signUpVO) {
        SignUp signUp = alreadySignUpByBidingAndVendorId(signUpVO.getBidingId(),signUpVO.getVendorId());
        boolean notSignUp = (null == signUp);
        if(notSignUp){
            signUp = new SignUp();
            BeanCopyUtil.copyProperties(signUp,signUpVO);
        }
        BeanCopyUtil.copyProperties(signUpVO,signUp);
        return signUpVO;
    }

    @Override
    public boolean judgeSignUpCondition(BidSignUpVO signUpVO) {
        Assert.notNull(signUpVO.getBidingId(),"招标单ID不能为空");
        List<SignUp> list = this.list(new QueryWrapper<>(new SignUp().setVendorId(signUpVO.getVendorId()).setBidingId(signUpVO.getBidingId()).setSignUpStatus(SignUpStatus.SIGNUPED.getValue())));
        if (list.size() > 0) {
            throw new BaseException(LocaleHandler.getLocaleMsg("已经报名，不能重复报名！"));
        }
        Biding biding = iBidingService.getById(signUpVO.getBidingId());
        int compareTo = new Date().compareTo(biding.getEnrollEndDatetime());
        if(compareTo == 1){
            throw new BaseException(LocaleHandler.getLocaleMsg("已经截止报名！"));
        }
        return true;
    }
}
