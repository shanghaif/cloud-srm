package com.midea.cloud.srm.logistics.baseprice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.ImportStatus;
import com.midea.cloud.common.enums.logistics.LogisticsStatus;
import com.midea.cloud.common.utils.*;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.logistics.baseprice.mapper.BasePriceMapper;
import com.midea.cloud.srm.logistics.baseprice.service.BasePriceService;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.logistics.baseprice.dto.ExcelBasePriceDto;
import com.midea.cloud.srm.model.logistics.baseprice.entity.BasePrice;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
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

/**
* <pre>
 *  物流招标基础价格 服务实现类
 * </pre>
*
* @author wangpr@meicloud.com
* @version 1.00.00
*
* <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Jan 26, 2021 4:32:59 PM
 *  修改内容:
 * </pre>
*/
@Service
public class BasePriceServiceImpl extends ServiceImpl<BasePriceMapper, BasePrice> implements BasePriceService {
    @Resource
    private FileCenterClient fileCenterClient;

    @Override
    @Transactional
    public void effect(List<Long> basePriceIds) {
        /**
         * 	前台校验必填：港口、费用项、价格
         * 	后台校验唯一性：业务模式+运输方式+行政区域+港口+LEG+费用项+计费方式+计费单位
         */
        String nickname = AppUserUtil.getLoginAppUser().getNickname();
        List<BasePrice> basePrices = this.listByIds(basePriceIds);
        if(CollectionUtils.isNotEmpty(basePrices)){
            basePrices.forEach(basePrice -> {
                basePrice.setLastUpdatedByName(nickname);

                // 非空校验
                Assert.notNull(basePrice.getPortCode(),"存在港口为空,不能生效!");
                Assert.notNull(basePrice.getExpenseItem(),"存在费项为空,不能生效!");
                Assert.notNull(basePrice.getPrice(),"存在价格为空,不能生效!");

                /**
                 * 校验生效唯一 业务模式+运输方式+行政区域+港口+LEG+费用项+计费方式+计费单位
                 */
                int count = this.count(Wrappers.lambdaQuery(BasePrice.class).
                        eq(BasePrice::getBusinessModeCode, basePrice.getBusinessModeCode()).
                        eq(BasePrice::getTransportModeCode, basePrice.getTransportModeCode()).
                        eq(BasePrice::getRegionCode, basePrice.getRegionCode()).
                        eq(BasePrice::getPortCode, basePrice.getPortCode()).
                        eq(BasePrice::getLeg, basePrice.getLeg()).
                        eq(BasePrice::getExpenseItem, basePrice.getExpenseItem()).
                        eq(BasePrice::getChargeMethod, basePrice.getChargeMethod()).
                        eq(BasePrice::getChargeUnit, basePrice.getChargeUnit()).
                        eq(BasePrice::getStatus, LogisticsStatus.EFFECTIVE.getValue()).
                        ne(BasePrice::getBasePriceId, basePrice.getBasePriceId())
                );
                Assert.isTrue(0 == count, "存在重复生效数据");
                basePrice.setStatus(LogisticsStatus.EFFECTIVE.getValue());
            });
            this.updateBatchById(basePrices);
        }
    }

    @Override
    @Transactional
    public void invalid(List<Long> basePriceIds) {
        String nickname = AppUserUtil.getLoginAppUser().getNickname();
        List<BasePrice> basePrices = this.listByIds(basePriceIds);
        if(CollectionUtils.isNotEmpty(basePrices)){
            basePrices.forEach(basePrice -> {
                basePrice.setLastUpdatedByName(nickname);
                basePrice.setStatus(LogisticsStatus.INEFFECTIVE.getValue());
            });
            this.updateBatchById(basePrices);
        }
    }

    @Override
    @Transactional
    public void batchAdd(List<BasePrice> basePriceList) {
        //设置主键id
        String nickname = AppUserUtil.getLoginAppUser().getNickname();
        for(BasePrice basePrice :basePriceList){
            Long id = IdGenrator.generate();
            basePrice.setCreatedByName(nickname);
            basePrice.setLastUpdatedByName(nickname);
            basePrice.setBasePriceId(id);
        }
        this.saveBatch(basePriceList);
    }

    @Override
    @Transactional
    public void batchUpdate(List<BasePrice> basePriceList) {
        String nickname = AppUserUtil.getLoginAppUser().getNickname();
        if(CollectionUtils.isNotEmpty(basePriceList)){
            basePriceList.forEach(basePrice -> {
                basePrice.setLastUpdatedByName(nickname);
            });
        }
        this.saveOrUpdateBatch(basePriceList);
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
        EasyExcel.write(outputStream).head(ExcelBasePriceDto.class).sheet(0).sheetName("sheetName").doWrite(Arrays.asList(new ExcelBasePriceDto()));
    }

    @Override
    @Transactional
    public Map<String, Object> importExcel(MultipartFile file, Fileupload fileupload) throws Exception {
        // 检查参数
        EasyExcelUtil.checkParam(file, fileupload);
        // 读取数据
        List<ExcelBasePriceDto> basePriceDtos = EasyExcelUtil.readExcelWithModel(file, ExcelBasePriceDto.class);
        // 检查导入数据是否正确
        AtomicBoolean errorFlag = new AtomicBoolean(true);
        // 导入数据校验
        List<BasePrice> basePrices = chackImportParam(basePriceDtos, errorFlag);

        if(errorFlag.get()){
            // 保存
            batchAdd(basePrices);
        }else {
            // 有错误,上传错误文件
            Fileupload errorFileupload = EasyExcelUtil.uploadErrorFile(fileCenterClient, fileupload, basePrices, BasePrice.class, file);
            return ImportStatus.importError(errorFileupload.getFileuploadId(),errorFileupload.getFileSourceName());
        }
        return ImportStatus.importSuccess();
    }

    /**
     * 检查导入参数
     * @param excelBasePriceDto
     * @param errorFlag
     * @return 业务实体集合
     */
    public List<BasePrice> chackImportParam(List<ExcelBasePriceDto> excelBasePriceDto, AtomicBoolean errorFlag){
        /**
         * 省略校验过程,数据有错误则把  errorFlag.set(false);
         */
        List<BasePrice> basePrices = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(excelBasePriceDto)){
            excelBasePriceDto.forEach(basePriceDto -> {
                BasePrice basePrice = new BasePrice();
                StringBuffer errorMsg = new StringBuffer();
                BeanCopyUtil.copyProperties(basePrice,basePriceDto);
                // 检查示例: noticeId 非空
//                Long noticeId = basePriceDto.getNoticeId();
//                if(ObjectUtils.isEmpty(noticeId)){
//                    errorMsg.append("noticeId不能为空;");
//                    errorFlag.set(false);
//                }else {
//                    basePrice.setNoticeId(noticeId);
//                }
//
//                // .......
//                if(errorMsg.length() > 0){
//                    basePriceDto.setErrorMsg(errorMsg.toString());
//                }else {
//                    basePrices.add(basePrice);
//                }
                basePrices.add(basePrice);
            });
        }
        return basePrices;
    }

    @Override
    public void exportExcel(BasePrice excelParam, HttpServletResponse response) throws IOException {
        // 查询数据
        List<BasePrice> basePrices = getBasePrices(excelParam);
        List<ExcelBasePriceDto> excelBasePriceDto = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(basePrices)){
            basePrices.forEach(basePrice -> {
                ExcelBasePriceDto basePriceDto = new ExcelBasePriceDto();
                BeanCopyUtil.copyProperties(basePriceDto,basePrice);
                excelBasePriceDto.add(basePriceDto);
            });
        }
        // 获取输出流
        OutputStream outputStream = EasyExcelUtil.getServletOutputStream(response, "导出文件名");
        EasyExcel.write(outputStream).head(ExcelBasePriceDto.class).sheet(0).sheetName("sheetName").doWrite(excelBasePriceDto);

    }

    @Override
    public PageInfo<BasePrice> listPage(BasePrice basePrice) {
        PageUtil.startPage(basePrice.getPageNum(), basePrice.getPageSize());
        List<BasePrice> basePrices = getBasePrices(basePrice);
        return new PageInfo<>(basePrices);
    }

    public List<BasePrice> getBasePrices(BasePrice basePrice) {
        return this.list(Wrappers.lambdaQuery(BasePrice.class).
                like(!ObjectUtils.isEmpty(basePrice.getRegionName()),BasePrice::getRegionName,basePrice.getRegionName()).
                like(!ObjectUtils.isEmpty(basePrice.getPortNameZhs()),BasePrice::getPortNameZhs,basePrice.getPortNameZhs()).
                eq(!ObjectUtils.isEmpty(basePrice.getExpenseItem()),BasePrice::getExpenseItem,basePrice.getExpenseItem()).
                eq(!ObjectUtils.isEmpty(basePrice.getBusinessModeCode()),BasePrice::getBusinessModeCode,basePrice.getBusinessModeCode()).
                eq(!ObjectUtils.isEmpty(basePrice.getTransportModeCode()),BasePrice::getTransportModeCode,basePrice.getTransportModeCode()).
                eq(!ObjectUtils.isEmpty(basePrice.getLeg()),BasePrice::getLeg,basePrice.getLeg()).
                eq(!ObjectUtils.isEmpty(basePrice.getStatus()),BasePrice::getStatus,basePrice.getStatus())
        );
    }
}
