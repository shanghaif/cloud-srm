package com.midea.cloud.srm.supcooperate.orderreceive.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.event.Order;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.ImportStatus;
import com.midea.cloud.common.enums.pm.po.ReceivedStatusEnum;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.*;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.suppliercooperate.orderreceive.dto.ExcelOrderReceiveDto;
import com.midea.cloud.srm.model.suppliercooperate.orderreceive.dto.OrderReceiveDTO;
import com.midea.cloud.srm.model.suppliercooperate.orderreceive.dto.ReceiveDetailDTO;
import com.midea.cloud.srm.model.suppliercooperate.orderreceive.entity.OrderReceive;

import com.midea.cloud.srm.supcooperate.orderreceive.mapper.OrderReceiveMapper;
import com.midea.cloud.srm.supcooperate.orderreceive.mapper.ReceiveDetailMapper;
import com.midea.cloud.srm.supcooperate.orderreceive.service.OrderReceiveService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
* <pre>
 * 采购端-收货明细
 * </pre>
*
* @author yancj9@meicloud.com
* @version 1.00.00
*
* <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Jan 30, 2021 10:31:54 AM
 *  修改内容:
 * </pre>
*/
@Service
public class OrderReceiveServiceImpl extends ServiceImpl<OrderReceiveMapper, OrderReceive> implements OrderReceiveService {
    @Resource
    private FileCenterClient fileCenterClient;
    @Autowired
    ReceiveDetailMapper receiveDetailMapper;

    @Transactional
    public void batchUpdate(List<OrderReceive> orderReceiveList) {
        this.saveOrUpdateBatch(orderReceiveList);
    }
    /**
     * 批量 新建/更新
     * @param orderReceiveList
     * @throws IOException
     */
    @Override
    @Transactional
    public void batchSaveOrUpdate(List<OrderReceive> orderReceiveList) throws Exception {

        for(OrderReceive orderReceive :orderReceiveList){
            //新建，拟定状态
            if(orderReceive.getWarehousingReturnDetailId() == null){
                //设置id和拟定状态
                Long id = IdGenrator.generate();
                orderReceive.setReceiveStatus(ReceivedStatusEnum.PROTOCOL.getValue());
                orderReceive.setWarehousingReturnDetailId(id);
                //检查是否有可创建的送货单的收货明细
                checkRecordIsEmpty(orderReceive);
                //设置默认收货数量
                setDefaultReceiveNum(orderReceive);
            }
            //已经是拟定，变更为确定状态
            else {
                 OrderReceive orderReceiveGet =this.getById(orderReceive.getWarehousingReturnDetailId());
                 if(orderReceiveGet.getReceiveStatus().equals(ReceivedStatusEnum.PROTOCOL.getValue())) {
                    orderReceive.setReceiveStatus(ReceivedStatusEnum.CONFIRM.getValue());
                    orderReceive.setReceiveDate(new Date());
                    BeanCopyUtil.copyProperties(orderReceiveGet,orderReceive,true);
                    BeanCopyUtil.copyProperties(orderReceive,orderReceiveGet,true);
                    setDefaultReceiveNum(orderReceive);
                 }
            }
        }
        if(!CollectionUtils.isEmpty(orderReceiveList)) {
            batchUpdate(orderReceiveList);
        }
    }

    /**
     * 导入批量保存
     * @param orderReceiveList
     * @throws Exception
     */
    @Override
    public void batchSaveForImport(List<OrderReceive> orderReceiveList) throws Exception {
        for(OrderReceive orderReceive :orderReceiveList){
            //新建，拟定状态
            if(orderReceive.getWarehousingReturnDetailId() == null){
                //设置id和拟定状态
                Long id = IdGenrator.generate();
                orderReceive.setReceiveStatus(ReceivedStatusEnum.PROTOCOL.getValue());
                orderReceive.setWarehousingReturnDetailId(id);
            }
        }
        if(!CollectionUtils.isEmpty(orderReceiveList)) {
            batchUpdate(orderReceiveList);
        }
    }

    /**
     * 检查送货单和物料有没有对应的记录
     * @param orderReceive
     * @return
     */
    public boolean checkRecordIsEmpty(OrderReceive orderReceive) {
        //重新查询赋值
        QueryWrapper<ReceiveDetailDTO> wrapper =new QueryWrapper<>();
        //应该还要再加一个采购订单号唯一
        wrapper.eq("DELIVERY_NUMBER",orderReceive.getDeliveryNumber());
        wrapper.eq("MATERIAL_CODE",orderReceive.getMaterialCode());
        //复制属性
        ReceiveDetailDTO orderReceiveDto = receiveDetailMapper.getOne(wrapper);
        if( null != orderReceiveDto) {
            BeanCopyUtil.copyProperties(orderReceive,orderReceiveDto);
            return  true;
        }
        else {
            return false;
        }
//                else {
//                    throw new BaseException
//                            (LocaleHandler.getLocaleMsg
//                                    ("送货单号为"+orderReceive.getDeliveryNumber()
//                                             +"\n物料编码为"+orderReceive.getMaterialCode()
//                                            +"的送货单不存在对应的可创建的记录")
//                            );
//                }
    }
    /**
     * 收货数量检验与设置默认值
     * @param orderReceive
     */
    public void setDefaultReceiveNum(OrderReceive orderReceive) {
        //判断用户提交的数量是否超限
        if(orderReceive.getReceivedNum() !=null && orderReceive.getReceivedNum()>=0){
            Long diff =orderReceive.getDeliveryQuantity()-orderReceive.getReceiveSum();
            if(orderReceive.getReceivedNum()>diff){
                throw new BaseException(LocaleHandler.getLocaleMsg("送货单的收货数量超限,请检查"));
            }
            else{
                return;
            }
        }
        //如果为新建的拟定状态，设置默认收货数量
        if(orderReceive.getDeliveryQuantity()!=null && orderReceive.getReceiveSum()!=null){
            Long diff =orderReceive.getDeliveryQuantity()-orderReceive.getReceiveSum();
            if(diff>0) {
                orderReceive.setReceivedNum(diff);
            }
            else{
                throw new BaseException(LocaleHandler.getLocaleMsg("送货单的收货数量已达上限,请检查"));
            }
        }
        else{
            throw new BaseException(LocaleHandler.getLocaleMsg("该订单累计收货数量/送货数量存在空值,请检查"));
        }
    }

    /**
     * 批量删除
     * @param ids
     */
    @Override
    @Transactional
    public void batchDeleted(List<Long> ids) {
        this.removeByIds(ids);
    }

    /**
     * 导入模板
     * @param response
     * @throws IOException
     */
    @Override
    public void exportExcelTemplate(HttpServletResponse response) throws IOException {
        // 获取输出流
        OutputStream outputStream = EasyExcelUtil.getServletOutputStream(response,"模板下载");
        EasyExcel.write(outputStream).head(ExcelOrderReceiveDto.class).sheet(0).sheetName("sheetName").doWrite(Arrays.asList(new ExcelOrderReceiveDto()));
    }

    /**
     * 导入
     * @param file
     * @param fileupload
     * @return
     * @throws Exception
     */
    @Override
    @Transactional
    public Map<String, Object> importExcel(MultipartFile file, Fileupload fileupload) throws Exception {
        // 检查参数
        EasyExcelUtil.checkParam(file, fileupload);
        // 读取数据
        List<ExcelOrderReceiveDto> orderReceiveDtos = EasyExcelUtil.readExcelWithModel(file, ExcelOrderReceiveDto.class);
        // 检查导入数据是否正确
        AtomicBoolean errorFlag = new AtomicBoolean(true);
        // 导入数据校验
        List<OrderReceive> orderReceives = chackImportParam(orderReceiveDtos, errorFlag);

        if(errorFlag.get()){
            // 保存或者更新
            batchSaveForImport(orderReceives);
        }else {
            // 有错误,上传错误文件
            Fileupload errorFileupload = EasyExcelUtil.uploadErrorFile(fileCenterClient, fileupload, orderReceiveDtos, ExcelOrderReceiveDto.class, file);
            return ImportStatus.importError(errorFileupload.getFileuploadId(),errorFileupload.getFileSourceName());
        }
        return ImportStatus.importSuccess();
    }
    /**
     * 检查导入参数
     * @param excelOrderReceiveDto
     * @param errorFlag
     * @return 业务实体集合
     */
    public List<OrderReceive> chackImportParam(List<ExcelOrderReceiveDto> excelOrderReceiveDto, AtomicBoolean errorFlag){
        /**
         * 省略校验过程,数据有错误则把  errorFlag.set(false);
         */
        List<OrderReceive> orderReceives = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(excelOrderReceiveDto)){
            excelOrderReceiveDto.forEach(orderReceiveDto -> {
                OrderReceive orderReceive = new OrderReceive();
                StringBuffer errorMsg = new StringBuffer();
                BeanCopyUtil.copyProperties(orderReceive,orderReceiveDto);
//                // 检查示例: 采购订单号 非空
//                String orderNumber = orderReceiveDto.getOrderNumber();
//                if(ObjectUtils.isEmpty(orderNumber)){
//                    errorMsg.append("采购订单号不能为空");
//                    errorFlag.set(false);
//                }else {
//                    orderReceive.setOrderNumber(orderNumber);
//                }
                // 检查示例: 送货单号 非空
                String deliverNumber = orderReceiveDto.getDeliveryNumber();
                if(ObjectUtils.isEmpty(deliverNumber)){
                    errorMsg.append("送货单号不能为空  ");
                    errorFlag.set(false);
                }else {
                    orderReceive.setDeliveryNumber(deliverNumber);
                }
                // 检查示例: 物料编码 非空
                String materailCode = orderReceiveDto.getMaterialCode();
                if(ObjectUtils.isEmpty(materailCode)){
                    errorMsg.append("物料编码不能为空  ");
                    errorFlag.set(false);
                }else {
                    orderReceive.setMaterialCode(materailCode);
                }
                // 判断对应的单号是否可以创建记录
                if(deliverNumber != null && materailCode != null) {
                    if(!checkRecordIsEmpty(orderReceive)){
                        errorMsg.append(
                                "该送货单号和对应的物料编码不存在对应的可创建的收货明细记录");
                        errorFlag.set(false);
                    }
                    else {
                        //可以创建的前提下，检验收货数量是否超限
                        Long diff =orderReceive.getDeliveryQuantity()-orderReceive.getReceiveSum();
                        if(orderReceive.getReceivedNum()>diff){
                            errorMsg.append("送货单的收货数量超限,请检查");
                            errorFlag.set(false);
                        }
                    }
                }
                if(errorMsg.length() > 0){
                    orderReceiveDto.setErrorMsg(errorMsg.toString());
                }else {
                    orderReceives.add(orderReceive);
                }
            });
        }
        return orderReceives;
    }
    /**
     * 文件导出
     * @param excelParam
     * @param response
     * @throws IOException
     */
    @Override
    public void exportExcel(OrderReceiveDTO excelParam, HttpServletResponse response) throws IOException {
        // 查询数据
        List<OrderReceive> orderReceives = getOrderReceives(excelParam);
        List<ExcelOrderReceiveDto> orderReceiveDto = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(orderReceives)){
            orderReceives.forEach(orderReceive -> {
                ExcelOrderReceiveDto excelOrderReceiveDto = new ExcelOrderReceiveDto();
                BeanCopyUtil.copyProperties(excelOrderReceiveDto,orderReceive);
                orderReceiveDto.add(excelOrderReceiveDto);
            });
        }
        // 获取输出流
        OutputStream outputStream = EasyExcelUtil.getServletOutputStream(response, "导出文件名");
        EasyExcel.write(outputStream).head(ExcelOrderReceiveDto.class).sheet(0).sheetName("sheetName").doWrite(orderReceiveDto);
    }
    /**
     * 查询结果分页
     * @param orderReceiveDTO
     * @return
     */
    @Override
    public PageInfo<OrderReceive> listPage(OrderReceiveDTO orderReceiveDTO) {
        PageUtil.startPage(orderReceiveDTO.getPageNum(), orderReceiveDTO.getPageSize());
        List<OrderReceive> orderReceives = getOrderReceives(orderReceiveDTO);
        return new PageInfo<>(orderReceives);
    }
    /**
     * 查询全部/条件查询
     * @param orderReceiveDTO
     * @return
     */
    public List<OrderReceive> getOrderReceives(OrderReceiveDTO orderReceiveDTO) {
        QueryWrapper<OrderReceive> wrapper = new QueryWrapper<>();
        //精确匹配
        wrapper.eq(orderReceiveDTO.getReceiveStatus()!=null,"RECEIVE_STATUS",orderReceiveDTO.getReceiveStatus());
        wrapper.eq(orderReceiveDTO.getOrgId()!=null,"ORG_ID",orderReceiveDTO.getOrgId());
        wrapper.eq(orderReceiveDTO.getCreatedId()!=null,"CREATED_ID",orderReceiveDTO.getCreatedId());
        //模糊匹配
        wrapper.like(orderReceiveDTO.getVendorName()!=null,"VENDOR_NAME",orderReceiveDTO.getVendorName());
        wrapper.like(orderReceiveDTO.getOrderNumber()!=null,"ORDER_NUMBER",orderReceiveDTO.getOrderNumber());
        wrapper.like(orderReceiveDTO.getMaterialCode()!=null,"MATERIAL_CODE",orderReceiveDTO.getMaterialCode());
        wrapper.like(orderReceiveDTO.getMaterialName()!=null,"MATERIAL_NAME",orderReceiveDTO.getMaterialName());
        wrapper.like(orderReceiveDTO.getDeliveryNumber()!=null,"DELIVERY_NUMBER",orderReceiveDTO.getDeliveryNumber());
        //日期范围
        wrapper.ge(orderReceiveDTO.getStartDate()!=null,"RECEIVE_DATE",orderReceiveDTO.getStartDate());
        wrapper.le(orderReceiveDTO.getEndDate()!=null,"RECEIVE_DATE",orderReceiveDTO.getEndDate());
        //实体列表匹配
//        wrapper.in(orderReceiveDTO.getOrgIds()!=null&&orderReceiveDTO.getOrgIds().size()>0,"ORG_ID",orderReceiveDTO.getOrgIds());
        //排序
        wrapper.orderByDesc("LAST_UPDATE_DATE");
        return this.list(wrapper);
    }

}
