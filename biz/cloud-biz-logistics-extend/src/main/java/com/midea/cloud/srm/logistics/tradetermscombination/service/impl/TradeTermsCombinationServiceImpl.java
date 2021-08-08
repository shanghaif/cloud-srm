package com.midea.cloud.srm.logistics.tradetermscombination.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.ImportStatus;
import com.midea.cloud.common.enums.logistics.LogisticsStatus;
import com.midea.cloud.common.utils.EasyExcelUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.logistics.tradetermscombination.mapper.TradeTermsCombinationMapper;
import com.midea.cloud.srm.logistics.tradetermscombination.service.TradeTermsCombinationService;
import com.midea.cloud.srm.model.base.dict.dto.DictItemDTO;
import com.midea.cloud.srm.model.base.dict.entity.DictItem;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.logistics.tradetermscombination.dto.ExcelTradeTermsCombinationDto;
import com.midea.cloud.srm.model.logistics.tradetermscombination.dto.TradeTermsCombinationDto;
import com.midea.cloud.srm.model.logistics.tradetermscombination.dto.legExpenseItemDto;
import com.midea.cloud.srm.model.logistics.tradetermscombination.entity.TradeTermsCombination;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
* <pre>
 *  贸易术语组合 服务实现类
 * </pre>
*
* @author yancj9@meicloud.com
* @version 1.00.00
*
* <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Mar 3, 2021 11:52:27 AM
 *  修改内容:
 * </pre>
*/
@Service
public class TradeTermsCombinationServiceImpl extends ServiceImpl<TradeTermsCombinationMapper, TradeTermsCombination> implements TradeTermsCombinationService {
    @Resource
    private FileCenterClient fileCenterClient;
    @Resource
    private BaseClient baseClient;
    @Transactional
    public void batchUpdate(List<TradeTermsCombination> tradeTermsCombinationList) {
        this.saveOrUpdateBatch(tradeTermsCombinationList);
    }

    @Override
    public List<TradeTermsCombinationDto> queryTradeTermsCombinationDto() {
        List<TradeTermsCombinationDto> combinationDtos = new ArrayList<>();
        List<TradeTermsCombination> combinationList = this.list(Wrappers.lambdaQuery(TradeTermsCombination.class).
                eq(TradeTermsCombination::getLogisticsStatus, LogisticsStatus.EFFECTIVE.getValue()));
        if(CollectionUtils.isNotEmpty(combinationList)){
            Map<String, List<TradeTermsCombination>> map = combinationList.stream().
                    collect(Collectors.groupingBy(TradeTermsCombination::getCombinationName));
            map.forEach((combinationName, tradeTermsCombinations) -> {
                TradeTermsCombination tradeTermsCombination = tradeTermsCombinations.get(0);
                TradeTermsCombinationDto combinationDto = new TradeTermsCombinationDto().
                        setCombinationName(combinationName).
                        setTradeTerm(tradeTermsCombination.getTradeTermsName()).
                        setImportExportMethod(tradeTermsCombination.getImportExportName());
                List<legExpenseItemDto> expenseItemDtos = tradeTermsCombinations.stream().map(tradeTermsCombination1 ->
                        new legExpenseItemDto().
                                setLeg(tradeTermsCombination1.getLegCode()).
                                setExpenseItem(tradeTermsCombination1.getFeeName())
                ).collect(Collectors.toList());
                combinationDto.setLegExpenseItemDtos(expenseItemDtos);
                combinationDtos.add(combinationDto);
            });
        }
        return combinationDtos;
    }

    @Override
    @Transactional
    public void batchSaveOrUpdate(List<TradeTermsCombination> tradeTermsCombinationList) throws IOException {
        for(TradeTermsCombination tradeTermsCombination : tradeTermsCombinationList){
            if(checkIsRepeat(tradeTermsCombination)){
                String IMP_EXP =tradeTermsCombination.getImportExportName().equals("IMP")? "进口":"出口";
                String err = String.format("已经存在贸易术语为%s,进出口项为%s,LEG编码为%s,费用项为%s的贸易术语组合",
                        tradeTermsCombination.getTradeTermsName(),IMP_EXP,tradeTermsCombination.getLegCode(),tradeTermsCombination.getFeeName());
                Assert.isTrue(false,err);
            }
            if(tradeTermsCombination.getTradeTermsCombinationId() == null){
                Long id = IdGenrator.generate();
                tradeTermsCombination.setTradeTermsCombinationId(id);
            }
            //保存组合名称
            if(null!=tradeTermsCombination.getTradeTermsName() && null!=tradeTermsCombination.getImportExportName()){
                String IMP_EXP =tradeTermsCombination.getImportExportName().equals("IMP")? "进口":"出口";
                tradeTermsCombination.setCombinationName(tradeTermsCombination.getTradeTermsName()+"+"+IMP_EXP);
            }
        }
        if(!CollectionUtils.isEmpty(tradeTermsCombinationList)) {
            batchUpdate(tradeTermsCombinationList);
        }
    }

    @Override
    public void updateStatus(List<Long> tradeTermsCombinationIds, String status) {
        List<TradeTermsCombination> tradeTermsCombinationIdList = new ArrayList<>();
        tradeTermsCombinationIds.forEach(tradeTermsCombinationId -> {
            TradeTermsCombination tradeTermsCombination = this.getById(tradeTermsCombinationId);
            tradeTermsCombination.setLogisticsStatus(status);
            tradeTermsCombinationIdList.add(tradeTermsCombination);
        });
        this.updateBatchById(tradeTermsCombinationIdList);
    }

    /**
     * 判断是否存在同样的贸易术语组合协议
     * @return
     */
    public boolean checkIsRepeat(TradeTermsCombination tradeTermsCombination){
        QueryWrapper<TradeTermsCombination> wrapper =new QueryWrapper<>();
        wrapper.eq(null != tradeTermsCombination.getTradeTermsName(),"TRADE_TERMS_NAME",tradeTermsCombination.getTradeTermsName());
        wrapper.eq(null != tradeTermsCombination.getImportExportName(),"IMPORT_EXPORT_NAME",tradeTermsCombination.getImportExportName());
        wrapper.eq(null != tradeTermsCombination.getFeeName(),"FEE_NAME",tradeTermsCombination.getFeeName());
        wrapper.eq(null != tradeTermsCombination.getLegCode(),"LEG_CODE",tradeTermsCombination.getLegCode());
        TradeTermsCombination isTrue = this.getOne(wrapper);
        return isTrue == null? false:true;
    }

    @Override
    @Transactional
    public void batchDeleted(List<Long> ids) {
        this.removeByIds(ids);
    }
    @Override
    public void exportExcelTemplate(HttpServletResponse response) throws IOException {
        // 获取输出流
        OutputStream outputStream = EasyExcelUtil.getServletOutputStream(response,"模板下载");
        EasyExcel.write(outputStream).head(ExcelTradeTermsCombinationDto.class).sheet(0).sheetName("sheetName").doWrite(Arrays.asList(new ExcelTradeTermsCombinationDto()));
    }
    @Override
    @Transactional
    public Map<String, Object> importExcel(MultipartFile file, Fileupload fileupload) throws Exception {
        // 检查参数
        EasyExcelUtil.checkParam(file, fileupload);
        // 读取数据
        List<ExcelTradeTermsCombinationDto> tradeTermsCombinationDtos = EasyExcelUtil.readExcelWithModel(file, ExcelTradeTermsCombinationDto.class);
        // 检查导入数据是否正确
        AtomicBoolean errorFlag = new AtomicBoolean(true);
        // 导入数据校验
        List<TradeTermsCombination> tradeTermsCombinations = chackImportParam(tradeTermsCombinationDtos, errorFlag);

        if(errorFlag.get()){
            // 保存或者更新
            batchUpdate(tradeTermsCombinations);
        }else {
            // 有错误,上传错误文件
            Fileupload errorFileupload = EasyExcelUtil.uploadErrorFile(fileCenterClient, fileupload, tradeTermsCombinationDtos, ExcelTradeTermsCombinationDto.class, file);
            return ImportStatus.importError(errorFileupload.getFileuploadId(),errorFileupload.getFileSourceName());
        }
        return ImportStatus.importSuccess();
    }
    /**
     * 检查导入参数
     * @param excelTradeTermsCombinationDto
     * @param errorFlag
     * @return 业务实体集合
     */
    public List<TradeTermsCombination> chackImportParam(List<ExcelTradeTermsCombinationDto> excelTradeTermsCombinationDto, AtomicBoolean errorFlag){
        /**
         * 省略校验过程,数据有错误则把  errorFlag.set(false);
         */
        List<TradeTermsCombination> tradeTermsCombinations = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(excelTradeTermsCombinationDto)){
            excelTradeTermsCombinationDto.forEach(tradeTermsCombinationDto -> {
                TradeTermsCombination tradeTermsCombination = new TradeTermsCombination();
                StringBuffer errorMsg = new StringBuffer();
//                BeanCopyUtil.copyProperties(tradeTermsCombination,tradeTermsCombinationDto);
                // 检查 贸易组合检验
                String tradeTermsName = tradeTermsCombinationDto.getTradeTermsName();
                if(ObjectUtils.isEmpty(tradeTermsName)){
                    errorMsg.append("贸易术语名称不能为空 ");
                    errorFlag.set(false);
                }else {
                    //判断数据库的字典是否有用户写的值
                    String str =checkIsInDataBase(tradeTermsName,new DictItemDTO().setDictId(8261981955227648L));
                    if(null != str) {
                        tradeTermsCombination.setTradeTermsName(str);
                    }
                    else {
                        errorMsg.append(String.format("贸易术语_%s_不存在字典中 ",tradeTermsName));
                        errorFlag.set(false);
                    }

                }
                String importExportName = tradeTermsCombinationDto.getImportExportName();
                if(ObjectUtils.isEmpty(importExportName)){
                    errorMsg.append("进出口名称不能为空 ");
                    errorFlag.set(false);
                }else {
                    //判断数据库的字典是否有用户写的值
                    String str =checkIsInDataBase(importExportName,new DictItemDTO().setDictId(8300074288087040L));
                    if(null != str) {
                        tradeTermsCombination.setImportExportName(str);
                    }
                    else {
                        errorMsg.append(String.format("进出口_%s_不存在字典中 ",importExportName));
                        errorFlag.set(false);
                    }
                }
                String legCode = tradeTermsCombinationDto.getLegCode();
                if(ObjectUtils.isEmpty(legCode)){
                    errorMsg.append("LEG编码不能为空 ");
                    errorFlag.set(false);
                }else {
                    //判断数据库的字典是否有用户写的值
                    String str =checkIsInDataBase(legCode,new DictItemDTO().setDictId(8262081429307392L));
                    if(null != str) {
                        tradeTermsCombination.setLegCode(str);
                    }
                    else {
                        errorMsg.append(String.format("LEG编码_%s_不存在字典中 ",legCode));
                        errorFlag.set(false);
                    }
                }
                String feeName = tradeTermsCombinationDto.getFeeName();
                if(ObjectUtils.isEmpty(feeName)){
                    errorMsg.append("费用项不能为空 ");
                    errorFlag.set(false);
                }else {
                    //判断数据库的字典是否有用户写的值
                    String str =checkIsInDataBase(feeName,new DictItemDTO().setDictId(8261827255664640L));
                    if(null != str) {
                        tradeTermsCombination.setFeeName(str);
                    }
                    else {
                        errorMsg.append(String.format("费用项_%s_不存在字典中 ",feeName));
                        errorFlag.set(false);
                    }
                }
                //检查数据库是否存在同样的贸易组合
                if(errorMsg.length() == 0){
                    if(checkIsRepeat(tradeTermsCombination)){
                        String err = "已经存在同样的贸易术语组合";
                        errorMsg.append(err);
                        errorFlag.set(false);
                        tradeTermsCombinationDto.setErrorMsg(errorMsg.toString());
                    }
                    else {
                        //不存在就新增
                        tradeTermsCombination.setCombinationName(tradeTermsName+"+"+importExportName);
                        Long id = IdGenrator.generate();
                        tradeTermsCombination.setTradeTermsCombinationId(id);
                        tradeTermsCombination.setLogisticsStatus(LogisticsStatus.DRAFT.getValue());
                        tradeTermsCombinations.add(tradeTermsCombination);
                    }

                }else {
                    tradeTermsCombinationDto.setErrorMsg(errorMsg.toString());
                }
            });
        }
        return tradeTermsCombinations;
    }
    @Override
    public PageInfo<TradeTermsCombination> listPage(TradeTermsCombination tradeTermsCombination) {
        PageUtil.startPage(tradeTermsCombination.getPageNum(), tradeTermsCombination.getPageSize());
        List<TradeTermsCombination> tradeTermsCombinations = getTradeTermsCombinations(tradeTermsCombination);
        return new PageInfo<>(tradeTermsCombinations);
    }
    //判断导入的数据是否存在数据库的字典里面
    public String checkIsInDataBase(String str,DictItemDTO dictItemDTO) {
//        dictItemDTO.setDictId(8261827255664640L);
        List<DictItem> list =new ArrayList<>();
        list =baseClient.listDictItemsByParam(dictItemDTO);
        for(DictItem dictItem : list) {
            if(dictItem.getDictItemName().equals(str) || dictItem.getDictItemCode().equals(str)) {
                return dictItem.getDictItemCode();
            }
        }
        return null;
    }
    public List<TradeTermsCombination> getTradeTermsCombinations(TradeTermsCombination tradeTermsCombination) {
        QueryWrapper<TradeTermsCombination> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("LAST_UPDATE_DATE");
        wrapper.like(tradeTermsCombination.getCombinationName()!=null,"COMBINATION_NAME",tradeTermsCombination.getCombinationName());
        wrapper.eq(tradeTermsCombination.getTradeTermsName()!=null,"TRADE_TERMS_NAME",tradeTermsCombination.getTradeTermsName());
        wrapper.eq(tradeTermsCombination.getImportExportName()!=null,"IMPORT_EXPORT_NAME",tradeTermsCombination.getImportExportName());
        wrapper.eq(tradeTermsCombination.getLegCode()!=null,"LEG_CODE",tradeTermsCombination.getLegCode());
        wrapper.eq(tradeTermsCombination.getFeeName()!=null,"FEE_NAME",tradeTermsCombination.getFeeName());
        wrapper.eq(tradeTermsCombination.getLogisticsStatus()!=null,"LOGISTICS_STATUS",tradeTermsCombination.getLogisticsStatus());
        return this.list(wrapper);
    }
}
