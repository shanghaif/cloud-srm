package com.midea.cloud.srm.supcooperate.order.service.impl;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.UserType;
import com.midea.cloud.common.enums.pm.po.CeeaWarehousingReturnDetailEnum;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.common.utils.*;
import com.midea.cloud.common.utils.DateUtil;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.component.handler.AutoMetaObjContext;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.pm.PmClient;
import com.midea.cloud.srm.feign.supcooperate.SupcooperateClient;
import com.midea.cloud.srm.feign.supplier.SupplierClient;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseTax;
import com.midea.cloud.srm.model.base.soap.erp.dto.SoapResponse;
import com.midea.cloud.srm.model.base.soap.erp.suppliercooperate.dto.WarehousingReturnDetailEntity;
import com.midea.cloud.srm.model.common.ExportExcelParam;
import com.midea.cloud.srm.model.pm.pr.requirement.dto.PurchaseRequirementDTO;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.RequirementHead;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.RequirementLine;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.suppliercooperate.deliverynote.entry.DeliveryNoteWms;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.WarehousingReturnDetailFileDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.WarehousingReturnDetailRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.Order;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.OrderDetail;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.WarehousingReturnDetail;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.WarehousingReturnDetailErp;
import com.midea.cloud.srm.supcooperate.order.mapper.WarehousingReturnDetailMapper;
import com.midea.cloud.srm.supcooperate.order.service.IOrderDetailService;
import com.midea.cloud.srm.supcooperate.order.service.IOrderService;
import com.midea.cloud.srm.supcooperate.order.service.IWarehousingReturnDetailErpService;
import com.midea.cloud.srm.supcooperate.order.service.IWarehousingReturnDetailService;
import com.midea.cloud.srm.supcooperate.reconciliation.service.IWarehouseReceiptService;
import net.sf.cglib.core.Local;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.Assert;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author chenwj92@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人: chenwj92
 *  修改日期: 2020/8/20 14:16
 *  修改内容:
 * </pre>
 */
@Service
public class WarehousingReturnDetailServiceImpl extends ServiceImpl<WarehousingReturnDetailMapper, WarehousingReturnDetail> implements IWarehousingReturnDetailService {
    @Autowired
    WarehousingReturnDetailMapper warehousingReturnDetailMapper;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IOrderDetailService orderDetailService;

    @Autowired
    private IWarehousingReturnDetailErpService warehousingReturnDetailErpService;

    @Autowired
    private PmClient pmClient;

    @Autowired
    private BaseClient baseClient;

    @Override
    public List<WarehousingReturnDetail> listPage(WarehousingReturnDetailRequestDTO warehousingReturnDetailRequestDTO) {
        return warehousingReturnDetailMapper.findListCopy(warehousingReturnDetailRequestDTO);
    }

    @Override
    public List<WarehousingReturnDetail> list(WarehousingReturnDetailRequestDTO warehousingReturnDetailRequestDTO) {
        return warehousingReturnDetailMapper.findList(warehousingReturnDetailRequestDTO);
    }

    /**
     * 只同步接收、退货至供应商的数据
     * 2020-11-02修改逻辑
     * 如果是寄售订单，则根据erp_order_number去查询订单，否则通过order_number去查询订单
     *
     * @param warehousingReturnDetailEntityList
     * @param instId
     * @param requestTime
     * @return
     */
    @Override
    @Transactional
    public SoapResponse acceptErpData(List<WarehousingReturnDetailEntity> warehousingReturnDetailEntityList, String instId, String requestTime) {
        List<WarehousingReturnDetail> warehousingReturnDetailAddOrUpdates = new ArrayList<>();
        List<WarehousingReturnDetailErp> warehousingReturnDetailErpAdds = new ArrayList<>();
        String returnStatus = "";
        String returnMsg = "";
        try{
            for(WarehousingReturnDetailEntity item:warehousingReturnDetailEntityList){
                checkWarehousingReturnDetailEntity(item);
                /*如果校验到txn_id重复，则为重复推送*/
                QueryWrapper<WarehousingReturnDetailErp> repeatWrapper = new QueryWrapper<>();
                repeatWrapper.eq("TXN_ID",item.getTxnId());
                List<WarehousingReturnDetailErp> warehousingReturnDetailErpList = warehousingReturnDetailErpService.list(repeatWrapper);
                if(!CollectionUtils.isEmpty(warehousingReturnDetailErpList)){
                    insertRepeat(item);
                    continue;
                }
                /*如果检验到类型不是【接收】【退货至供应商】,则直接插入*/
                if(item.getTxnType().equals("RECEIVE") ||
                        item.getTxnType().equals("RECEIVE_STANDARD") ||
                        item.getTxnType().equals("RETURN TO VENDOR") ||
                        item.getTxnType().equals("RETRUN_TO_VENDOR")
                ){
                    QueryWrapper<Order> orderWrapper = new QueryWrapper();
                    if("CONSIGNMENT".equals(item.getConsignType())){
                        /*寄售订单逻辑*/
                        orderWrapper.eq("ERP_ORDER_NUMBER",item.getPoNumber());
                    }else{
                        /*非寄售订单逻辑*/
                        orderWrapper.eq("ORDER_NUMBER",item.getPoNumber());
                    }
                    List<Order> orderList = orderService.list(orderWrapper);
                    if(CollectionUtils.isEmpty(orderList)){
                        /*当订单匹配不上，直接插入*/
                        insertError(item);
                        continue;
                        /*throw new BaseException(LocaleHandler.getLocaleMsg("查询不到订单号[poNumber]为：" + item.getPoNumber()));*/
                    }
                    QueryWrapper<OrderDetail> orderDetailQueryWrapper = new QueryWrapper<>();
                    orderDetailQueryWrapper.eq("ORDER_ID",orderList.get(0).getOrderId());
                    orderDetailQueryWrapper.eq("LINE_NUM",item.getPoLineNum());
                    List<OrderDetail> orderDetailList = orderDetailService.list(orderDetailQueryWrapper);
                    if(CollectionUtils.isEmpty(orderDetailList)){
                        insertError(item);
                        continue;
                        /*throw new BaseException(LocaleHandler.getLocaleMsg("查询不到订单号[poNumber]为：" + item.getPoNumber() + ",订单行号[poLineNum]为：" + item.getPoLineNum() + "的单据"));*/
                    }
                    OrderDetail orderDetail = orderDetailList.get(0);
                    Order order = orderList.get(0);
                    String requirementHeadNum = null;
                    Integer rowNum = null;
                    String projectNum = null;
                    String projectName = null;
                    if("Y".equals(orderDetail.getCeeaIfRequirement())){
                        RequirementLine requirementLine = pmClient.getRequirementLineByIdForAnon(orderDetail.getCeeaRequirementLineId());
                        if(Objects.nonNull(requirementLine)){
                            PurchaseRequirementDTO purchaseRequirementDTO = pmClient.getPurchaseRequirementDTOByHeadIdForAnon(requirementLine.getRequirementHeadId());
                            RequirementHead requirementHead = purchaseRequirementDTO.getRequirementHead();
                            if(Objects.nonNull(requirementHead)){
                                requirementHeadNum = requirementLine.getRequirementHeadNum();
                                rowNum = requirementLine.getRowNum();
                                projectNum = requirementHead.getCeeaProjectNum();
                                projectName = requirementHead.getCeeaProjectName();

                            }
                        }
                    }

                    Date date = new Date();
                    //srm订单编号
                    String srmOrderNumber = order.getOrderNumber();
                    //采购结算使用订单编号
                    String settlementOrderNumber = StringUtils.isNotBlank(order.getEprOrderNumber()) ? order.getEprOrderNumber() : order.getOrderNumber();

                    WarehousingReturnDetail warehousingReturnDetail = new WarehousingReturnDetail();
                    warehousingReturnDetail.setReceiveOrderNo(item.getReceiveNum())  //接收单号
                            .setReceiveOrderLineNo(Integer.parseInt(item.getReceiveLineNum()))  //接收行号
                            .setTxnId(Long.parseLong(item.getTxnId()))
                            .setErpOrgId(Long.parseLong(item.getOperationUnitId()))  //erp业务实体id
                            .setErpOrganizationId(Long.parseLong(item.getOrganizationId()))  //erp库存组织id
                            .setErpOrganizationCode(item.getOrganizationCode())  //erp库存组织编码
                            .setErpVendorId(Long.parseLong(item.getVendorId()))  //erp供应商id
                            .setErpVendorCode(item.getVendorNumber())   //erp供应商编码
                            .setOrgId(order.getCeeaOrgId())  //业务实体id
                            .setOrgName(item.getOperationUnit())       //业务实体名称
                            .setOrganizationId(orderDetail.getCeeaOrganizationId()) //库存组织id
                            .setOrganizationCode(orderDetail.getCeeaOrganizationCode())  //库存组织编码
                            .setOrganizationName(orderDetail.getCeeaOrganizationName()) //库存组织名称
                            .setVendorId(order.getVendorId())  //供应商id
                            .setVendorCode(order.getVendorCode())   //供应商编码
                            .setItemCode(item.getItemNumber())      //物料编码
                            .setItemName(item.getItemDescr())    //物料名称
                            .setUnit(item.getUnitOfMeasure())   //单位名称
                            .setUnitCode(item.getUnitOfMeasure()) //单位编码名称
                            .setOrderNumber(item.getPoNumber())  //采购订单号
                            .setSrmOrderNumber(srmOrderNumber) //srm订单编号
                            .setSettlementOrderNumber(settlementOrderNumber)  //采购结算使用订单编号
                            .setLineNum(Integer.parseInt(item.getPoLineNum()))  //订单行号
                            .setAsnNumber(item.getAsnNumber())
                            .setAsnLineNum(item.getAsnLineNum())
                            .setCreatedBy("erp推送")
                            .setCreationDate(date)
                            .setUnitPriceExcludingTax(new BigDecimal(item.getPrice())) //单价不含税
                            .setPoLineId(Long.parseLong(item.getPoLineId()))  //采购订单行id
                            .setShipLineId(Long.parseLong(item.getShipLineId()))    //发运行id
                            .setShipLineNum(Long.parseLong(item.getShipLineNum()))   //发运行编号
                            /*order数据*/
                            .setOrgCode(order.getCeeaOrgCode())   //库存组织编码
                            .setVendorName(order.getVendorName())  //供应商名称
                            /*orderDetail数据*/
                            .setCurrencyId(orderDetail.getCurrencyId())  //币种id
                            .setCurrencyCode(orderDetail.getCurrencyCode())   //币种编码
                            .setCurrencyName(orderDetail.getCurrencyName())   //币种名称
                            .setOrganizationName(orderDetail.getCeeaOrganizationName())  //库存组织名称
                            .setCategoryId(orderDetail.getCategoryId())  //物料小类id
                            .setCategoryName(orderDetail.getCategoryName())  //物料小类名称
                            .setCategoryCode(orderDetail.getCategoryCode()) //物料小类编码
                            .setItemId(orderDetail.getMaterialId())  //物料id
                            .setContractNo(orderDetail.getCeeaContractNo())  //采购合同号
                            .setUnitPriceContainingTax(orderDetail.getCeeaUnitTaxPrice()) //含税单价
                            .setTaxRate(orderDetail.getCeeaTaxRate())  //税率
                            .setTaxKey(orderDetail.getCeeaTaxKey())  //税码
                            .setCreatedBy(order.getCeeaEmpUsername())   //创建人为采购员名称
                            .setCreatedId(order.getCeeaEmpUseId())
                            .setCreationDate(new Date())
                            /*requirement数据*/
                            .setRequirementHeadNum(requirementHeadNum)  //采购申请单号
                            .setRowNum(rowNum)   //申请行号
                            .setProjectNum(projectNum)  //项目编码
                            .setProjectName(projectName);  //项目名称
                    /*todo 物料小类编码*/
                    /*todo 任务编号 任务名称 */
                    /*todo 单价(含税)*/
                    /*赋值warehousingReturnDetailErp(属性名称相同，类型不同 BeanUtils.copyProperties()不起效果)*/
                    WarehousingReturnDetailErp warehousingReturnDetailErp = new WarehousingReturnDetailErp();
                    BeanUtils.copyProperties(item,warehousingReturnDetailErp);
                    warehousingReturnDetailErp.setWarehousingReturnDetailErpId(IdGenrator.generate());
                    Date txnDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(item.getTxnDate());
                    Date exchangeDate = null;
                    if(StringUtils.isNotBlank(item.getExchangeDate())){
                        exchangeDate = new SimpleDateFormat("yyyy-MM-dd").parse(item.getExchangeDate());
                    }

                    warehousingReturnDetailErp.setTxnId(Long.parseLong(item.getTxnId()))
                            .setTxnDate(txnDate)
                            .setQuantity(StringUtils.isBlank(item.getQuantity()) ? null : new BigDecimal(item.getQuantity()))
                            .setParentTxnId(StringUtils.isBlank(item.getParentTxnId()) ? null : Long.parseLong(item.getParentTxnId()))
                            .setPoHeaderId(StringUtils.isBlank(item.getPoHeaderId()) ? null : Long.parseLong(item.getPoHeaderId()))
                            .setPoLineId(StringUtils.isBlank(item.getPoLineId()) ? null : Long.parseLong(item.getPoLineId()))
                            .setShipLineId(StringUtils.isBlank(item.getShipLineId()) ? null : Long.parseLong(item.getShipLineId()))
                            .setReleaseLineId(StringUtils.isBlank(item.getReleaseLineId()) ? null : Long.parseLong(item.getReleaseLineId()))
                            .setDistId(StringUtils.isBlank(item.getDistId()) ? null : Long.parseLong(item.getDistId()))
                            .setPoLineNum(StringUtils.isBlank(item.getPoLineNum()) ? null : Long.parseLong(item.getPoLineNum()))
                            .setShipLineNum(StringUtils.isBlank(item.getShipLineNum()) ? null : Long.parseLong(item.getShipLineNum()))
                            .setPrice(StringUtils.isBlank(item.getPrice()) ? null : new BigDecimal(item.getPrice()))
                            .setExchangeDate(exchangeDate)
                            .setExchangeRate(StringUtils.isBlank(item.getExchangeRate()) ? null : new BigDecimal(item.getExchangeRate()))
                            .setVendorId(StringUtils.isBlank(item.getVendorId()) ? null : Long.parseLong(item.getVendorId()))
                            .setVendorSiteId(StringUtils.isBlank(item.getVendorSiteId()) ? null : Long.parseLong(item.getVendorSiteId()))
                            .setOrganizationId(StringUtils.isBlank(item.getOrganizationId()) ? null : Long.parseLong(item.getOrganizationId()))
                            .setAmount(StringUtils.isBlank(item.getAmount()) ? null : new BigDecimal(item.getAmount()))
                            .setOperationUnitId(StringUtils.isBlank(item.getOperationUnitId()) ? null : Long.parseLong(item.getOperationUnitId()));

                    /*20201013版本：不对数据做逻辑处理，直接插入*/
                    if(item.getTxnType().equals("RECEIVE") || item.getTxnType().equals("RECEIVE_STANDARD")){
                        /*接收*/
                        warehousingReturnDetail.setWarehousingReturnDetailId(IdGenrator.generate())
                                .setType(CeeaWarehousingReturnDetailEnum.RECEIVE.getValue())
                                .setReceiveNum(new BigDecimal(item.getQuantity()))
                                .setDealNum(1)
                                .setParentTxnId(Long.parseLong(item.getTxnId()))
                                .setEnable("Y")
                                .setReceiveDate(txnDate);
                        warehousingReturnDetailErp.setInsertSequence(1)
                                .setIfHandle("Y")
                                .setWarehousingReturnDetailId(warehousingReturnDetail.getWarehousingReturnDetailId());
                        /*设置未开票数量*/
                        warehousingReturnDetail.setNotInvoiceQuantity(warehousingReturnDetail.getReceiveNum());//设置未开票数量等于接收数量(后面开票业务需要用到)
                        /*回写订单明细接收数量*/
                        if(orderDetail.getReceivedQuantity() == null){
                            orderDetail.setReceivedQuantity(BigDecimal.ZERO);
                        }
                        orderDetailService.updateById(new OrderDetail()
                                .setOrderDetailId(orderDetail.getOrderDetailId())
                                .setReceivedQuantity(orderDetail.getReceivedQuantity().add(new BigDecimal(item.getQuantity())))
                        );
                    }else if(item.getTxnType().equals("RETURN TO VENDOR") || item.getTxnType().equals("RETRUN_TO_VENDOR")){
                        /*退货至供应商*/
                        warehousingReturnDetail.setWarehousingReturnDetailId(IdGenrator.generate())
                                .setType(CeeaWarehousingReturnDetailEnum.RETURN.getValue())
                                .setReturnToSupplierNum(new BigDecimal(item.getQuantity()))
                                .setReceiveNum(BigDecimal.ZERO.subtract(new BigDecimal(item.getQuantity())))
                                .setDealNum(1)
                                .setReturnToSupplierDate(txnDate);
                        warehousingReturnDetailErp.setInsertSequence(1)
                                .setWarehousingReturnDetailId(warehousingReturnDetail.getWarehousingReturnDetailId());
                        /*设置未开票数量*/
                        warehousingReturnDetail.setNotInvoiceQuantity(warehousingReturnDetail.getReceiveNum());//设置未开票数量等于接收数量(后面开票业务需要用到)

                        /*递归查询获取 退货类型的 parentTxnId*/
                        WarehousingReturnDetailErp erpEntity = new WarehousingReturnDetailErp();
                        BeanUtils.copyProperties(warehousingReturnDetailErp,erpEntity);

                        while (erpEntity != null && !"RECEIVE".equals(erpEntity.getTxnType()) && !"RECEIVE_STANDARD".equals(erpEntity.getTxnType())){
                            if(Objects.isNull(erpEntity.getParentTxnId())){
                                erpEntity = null;
                            }else{
                                WarehousingReturnDetailErp param = new WarehousingReturnDetailErp()
                                        .setTxnId(erpEntity.getParentTxnId());
                                List<WarehousingReturnDetailErp> list = warehousingReturnDetailErpService.list(new QueryWrapper<>(param));
                                if(CollectionUtils.isNotEmpty(list)){
                                    erpEntity = list.get(0);
                                }else{
                                    erpEntity = null;
                                }

                            }
                        }
                        if(erpEntity == null){
                            /*没有检测到parentTxnId */
                            warehousingReturnDetailErp.setIfHandle("N");
                            warehousingReturnDetail.setEnable("N");
                        }else{
                            warehousingReturnDetailErp.setIfHandle("Y");
                            warehousingReturnDetail.setParentTxnId(erpEntity.getTxnId())
                                    .setEnable("Y");
                        }
                        /*回写订单接收明细*/
                        if(Objects.isNull(orderDetail.getReceivedQuantity())){
                            orderDetail.setReceivedQuantity(BigDecimal.ZERO);
                        }
                        orderDetailService.updateById(new OrderDetail()
                                .setOrderDetailId(orderDetail.getOrderDetailId())
                                .setReceivedQuantity(orderDetail.getReceivedQuantity().add(warehousingReturnDetail.getReceiveNum()))
                        );

                    }else{
                        Assert.notNull(null,LocaleHandler.getLocaleMsg("事务处理类型错误：TxnType = " + item.getTxnType()));
                    }
                    warehousingReturnDetailAddOrUpdates.add(warehousingReturnDetail);
                    warehousingReturnDetailErpAdds.add(warehousingReturnDetailErp);
                }else{
                    insertRepeat(item);
                    continue;
                }

            }
            warehousingReturnDetailErpService.saveBatch(warehousingReturnDetailErpAdds);
            LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
            if(Objects.isNull(loginAppUser)){
                AutoMetaObjContext.noOp(AutoMetaObjContext.MODE.FOREVERY);
            }
            this.saveOrUpdateBatch(warehousingReturnDetailAddOrUpdates);

            returnStatus = "S";
            returnMsg = "接收成功";
        }catch (Exception e){
            returnStatus = "E";
            returnMsg = e.getMessage();
            log.error("操作失败",e);
            /*强制手动回滚事务*/
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        } finally {
            //手动释放AutoMetaObjContext 避免下次其他地方找不到用户信息
            AutoMetaObjContext.manullyRemove();
            return returnResponse(returnStatus, returnMsg,requestTime,instId);
        }
    }

    @Override
    public void export(WarehousingReturnDetailRequestDTO warehousingReturnDetailRequestDTO, HttpServletResponse response) {
        //构建表格
        /*Workbook workbook = createWorkbookModel();*/
        //获取数据
    }

    /*private Workbook createWorkbookModel(){
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

    }*/

    private void insertRepeat(WarehousingReturnDetailEntity item) throws Exception{
        WarehousingReturnDetailErp warehousingReturnDetailErp = new WarehousingReturnDetailErp();
        BeanUtils.copyProperties(item,warehousingReturnDetailErp);
        warehousingReturnDetailErp.setWarehousingReturnDetailErpId(IdGenrator.generate());
        Date txnDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(item.getTxnDate());
        Date exchangeDate = null;
        if(StringUtils.isNotBlank(item.getExchangeDate())){
            exchangeDate = new SimpleDateFormat("yyyy-MM-dd").parse(item.getExchangeDate());
        }
        warehousingReturnDetailErp.setTxnId(Long.parseLong(item.getTxnId()))
                .setTxnDate(txnDate)
                .setQuantity(StringUtils.isBlank(item.getQuantity()) ? null : new BigDecimal(item.getQuantity()))
                .setParentTxnId(StringUtils.isBlank(item.getParentTxnId()) ? null : Long.parseLong(item.getParentTxnId()))
                .setPoHeaderId(StringUtils.isBlank(item.getPoHeaderId()) ? null : Long.parseLong(item.getPoHeaderId()))
                .setPoLineId(StringUtils.isBlank(item.getPoLineId()) ? null : Long.parseLong(item.getPoLineId()))
                .setShipLineId(StringUtils.isBlank(item.getShipLineId()) ? null : Long.parseLong(item.getShipLineId()))
                .setReleaseLineId(StringUtils.isBlank(item.getReleaseLineId()) ? null : Long.parseLong(item.getReleaseLineId()))
                .setDistId(StringUtils.isBlank(item.getDistId()) ? null : Long.parseLong(item.getDistId()))
                .setPoLineNum(StringUtils.isBlank(item.getPoLineNum()) ? null : Long.parseLong(item.getPoLineNum()))
                .setShipLineNum(StringUtils.isBlank(item.getShipLineNum()) ? null : Long.parseLong(item.getShipLineNum()))
                .setPrice(StringUtils.isBlank(item.getPrice()) ? null : new BigDecimal(item.getPrice()))
                .setExchangeDate(exchangeDate)
                .setExchangeRate(StringUtils.isBlank(item.getExchangeRate()) ? null : new BigDecimal(item.getExchangeRate()))
                .setVendorId(StringUtils.isBlank(item.getVendorId()) ? null : Long.parseLong(item.getVendorId()))
                .setVendorSiteId(StringUtils.isBlank(item.getVendorSiteId()) ? null : Long.parseLong(item.getVendorSiteId()))
                .setOrganizationId(StringUtils.isBlank(item.getOrganizationId()) ? null : Long.parseLong(item.getOrganizationId()))
                .setAmount(StringUtils.isBlank(item.getAmount()) ? null : new BigDecimal(item.getAmount()))
                .setOperationUnitId(StringUtils.isBlank(item.getOperationUnitId()) ? null : Long.parseLong(item.getOperationUnitId()))
                .setIfHandle("Y")
                .setInsertSequence(-1);
        warehousingReturnDetailErpService.save(warehousingReturnDetailErp);

    }

    /**
     * 2020-11-10有部分入库退货匹配不上，直接插入
     * @param item
     * @throws ParseException
     */
    public void insertError(WarehousingReturnDetailEntity item) throws ParseException {
        WarehousingReturnDetailErp warehousingReturnDetailErp = new WarehousingReturnDetailErp();
        BeanUtils.copyProperties(item,warehousingReturnDetailErp);
        warehousingReturnDetailErp.setWarehousingReturnDetailErpId(IdGenrator.generate());
        Date txnDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(item.getTxnDate());
        Date exchangeDate = null;
        if(StringUtils.isNotBlank(item.getExchangeDate())){
            exchangeDate = new SimpleDateFormat("yyyy-MM-dd").parse(item.getExchangeDate());
        }
        warehousingReturnDetailErp.setTxnId(Long.parseLong(item.getTxnId()))
                .setTxnDate(txnDate)
                .setQuantity(StringUtils.isBlank(item.getQuantity()) ? null : new BigDecimal(item.getQuantity()))
                .setParentTxnId(StringUtils.isBlank(item.getParentTxnId()) ? null : Long.parseLong(item.getParentTxnId()))
                .setPoHeaderId(StringUtils.isBlank(item.getPoHeaderId()) ? null : Long.parseLong(item.getPoHeaderId()))
                .setPoLineId(StringUtils.isBlank(item.getPoLineId()) ? null : Long.parseLong(item.getPoLineId()))
                .setShipLineId(StringUtils.isBlank(item.getShipLineId()) ? null : Long.parseLong(item.getShipLineId()))
                .setReleaseLineId(StringUtils.isBlank(item.getReleaseLineId()) ? null : Long.parseLong(item.getReleaseLineId()))
                .setDistId(StringUtils.isBlank(item.getDistId()) ? null : Long.parseLong(item.getDistId()))
                .setPoLineNum(StringUtils.isBlank(item.getPoLineNum()) ? null : Long.parseLong(item.getPoLineNum()))
                .setShipLineNum(StringUtils.isBlank(item.getShipLineNum()) ? null : Long.parseLong(item.getShipLineNum()))
                .setPrice(StringUtils.isBlank(item.getPrice()) ? null : new BigDecimal(item.getPrice()))
                .setExchangeDate(exchangeDate)
                .setExchangeRate(StringUtils.isBlank(item.getExchangeRate()) ? null : new BigDecimal(item.getExchangeRate()))
                .setVendorId(StringUtils.isBlank(item.getVendorId()) ? null : Long.parseLong(item.getVendorId()))
                .setVendorSiteId(StringUtils.isBlank(item.getVendorSiteId()) ? null : Long.parseLong(item.getVendorSiteId()))
                .setOrganizationId(StringUtils.isBlank(item.getOrganizationId()) ? null : Long.parseLong(item.getOrganizationId()))
                .setAmount(StringUtils.isBlank(item.getAmount()) ? null : new BigDecimal(item.getAmount()))
                .setOperationUnitId(StringUtils.isBlank(item.getOperationUnitId()) ? null : Long.parseLong(item.getOperationUnitId()))
                .setIfHandle("Y")
                .setInsertSequence(-1);
        warehousingReturnDetailErpService.save(warehousingReturnDetailErp);
    }

    @Override
    public PageInfo<WarehousingReturnDetail> warehousingReturnlistPageByParam(WarehousingReturnDetailRequestDTO warehousingReturnDetailRequestDTO) {

        PageUtil.startPage(warehousingReturnDetailRequestDTO.getPageNum(), warehousingReturnDetailRequestDTO.getPageSize());
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        if (UserType.VENDOR.name().equals(loginAppUser.getUserType())) {
            //防止没注册供应商查看到其它全部供应商的信息
            if (Objects.isNull(loginAppUser.getCompanyId())) {
                return new PageInfo<>(new ArrayList<>());
            }
            warehousingReturnDetailRequestDTO.setVendorId(loginAppUser.getCompanyId());
        }
        /**
         * 由于基线产品系统没有对接ERP,暂且放开供应商地点校验
         */
        warehousingReturnDetailRequestDTO.setCeeaCostTypeCode(null);
        List<WarehousingReturnDetail> warehousingReturnDetails = this.baseMapper.findInvoiceNoticeList(warehousingReturnDetailRequestDTO);
        for (WarehousingReturnDetail warehousingReturnDetail : warehousingReturnDetails) {
            if (warehousingReturnDetail == null) continue;
            //计算含税金额,不含税金额,税额
            BigDecimal receiveNum = warehousingReturnDetail.getReceiveNum() == null ? BigDecimal.ZERO : warehousingReturnDetail.getReceiveNum();//接收数量
            BigDecimal unitPriceExcludingTax = warehousingReturnDetail.getUnitPriceExcludingTax() == null ? BigDecimal.ZERO : warehousingReturnDetail.getUnitPriceExcludingTax();//单价(不含税)
            BigDecimal unitPriceContainingTax = warehousingReturnDetail.getUnitPriceContainingTax() == null ? BigDecimal.ZERO : warehousingReturnDetail.getUnitPriceContainingTax();//单价(含税)
            warehousingReturnDetail.setNoTaxAmount(warehousingReturnDetail.getReceiveNum().multiply(warehousingReturnDetail.getUnitPriceExcludingTax()));
            warehousingReturnDetail.setTaxAmount(warehousingReturnDetail.getReceiveNum().multiply(warehousingReturnDetail.getUnitPriceContainingTax()));
            warehousingReturnDetail.setTax((warehousingReturnDetail.getUnitPriceContainingTax().subtract(warehousingReturnDetail.getUnitPriceExcludingTax())).multiply(warehousingReturnDetail.getReceiveNum()));
            //映射入退库明细中的采购结算使用订单号
            warehousingReturnDetail.setOrderNumber(warehousingReturnDetail.getSettlementOrderNumber());
        }
        return new PageInfo<>(warehousingReturnDetails);
    }
    @Override
    public void exportStart(WarehousingReturnDetailRequestDTO warehousingReturnDetail,HttpServletResponse response) throws IOException {
        // 标题
        WarehousingReturnDetailFileDTO warehousingReturnDetailFileDTO = new WarehousingReturnDetailFileDTO();
        Field[] declaredFields = warehousingReturnDetailFileDTO.getClass().getDeclaredFields();
        ArrayList<String> head = new ArrayList<>();
        ArrayList<String> headName = new ArrayList<>();
        for (Field field : declaredFields) {
            head.add(field.getName());
            ExcelProperty annotation = field.getAnnotation(ExcelProperty.class);
            if (null != annotation) {
                headName.add(annotation.value()[0]);
            }
        }
        /*获取导出的数据*/
        List<List<Object>> dataList = this.queryExportData(head,warehousingReturnDetail);
        /*文件名*/
        String fileName = "入库退货明细";
        /*开始导出*/
        EasyExcelUtil.exportStart(response,dataList,headName,fileName);
    }


    // 获取导出的数据
    public List<List<Object>> queryExportData(List<String> param,WarehousingReturnDetailRequestDTO warehousingReturnDetail) {
        //查新需要导出的数据
        /*QueryWrapper<WarehousingReturnDetail> wrapper = new QueryWrapper<>();
        wrapper.select("if(TYPE='RECEIVE','采购接收','采购退货')TYPE,RECEIVE_ORDER_NO,RECEIVE_ORDER_LINE_NO,ORG_NAME\n" +
                ",if(TYPE='RECEIVE',IFNULL(RECEIVE_DATE,WAREHOUSING_DATE),IFNULL(RETURN_TO_RECEIVING_DATE,RETURN_TO_SUPPLIER_DATE))RECEIVE_DATE\n" +
                ",ORGANIZATION_NAME,VENDOR_CODE,VENDOR_NAME,CATEGORY_NAME,ITEM_CODE,ITEM_NAME,UNIT,RECEIVE_NUM,REQUIREMENT_HEAD_NUM\n" +
                ",ROW_NUM,ORDER_NUMBER,LINE_NUM,CONTRACT_NO,CREATED_BY,CREATION_DATE");
        List<WarehousingReturnDetail> WarehousingReturnDetailList = this.list(wrapper);*/

        List<WarehousingReturnDetail> WarehousingReturnDetailList = warehousingReturnDetailMapper.findListCopy(warehousingReturnDetail);

        List<List<Object>> dataList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(WarehousingReturnDetailList)) {
            List<Map<String, Object>> mapList = BeanMapUtils.objectsToMaps(WarehousingReturnDetailList);
            List<String> titleList = param;
            if (CollectionUtils.isNotEmpty(titleList)) {
                for (Map<String, Object> map : mapList) {
                    ArrayList<Object> objects = new ArrayList<>();
                    for (String key : titleList) {
                        objects.add(map.get(key));
                    }
                    dataList.add(objects);
                }
            }
        }
        return dataList;
    }

    public void checkWarehousingReturnDetailEntity(WarehousingReturnDetailEntity warehousingReturnDetailEntity){
        Assert.hasText(warehousingReturnDetailEntity.getTxnType(), LocaleHandler.getLocaleMsg("事务处理类型代码不可为空"));
        Assert.hasText(warehousingReturnDetailEntity.getTxnDate(), LocaleHandler.getLocaleMsg("事务处理时间不可为空"));
        Assert.notNull(warehousingReturnDetailEntity.getQuantity(),LocaleHandler.getLocaleMsg("数量不可为空"));
        Assert.hasText(warehousingReturnDetailEntity.getReceiveNum(),LocaleHandler.getLocaleMsg("接收号不可为空"));
        Assert.hasText(warehousingReturnDetailEntity.getReceiveLineNum(),LocaleHandler.getLocaleMsg("接收行号不可为空"));
        Assert.hasText(warehousingReturnDetailEntity.getPoNumber(),LocaleHandler.getLocaleMsg("采购订单编号不可为空"));
        Assert.hasText(warehousingReturnDetailEntity.getPoLineNum(), LocaleHandler.getLocaleMsg("采购订单行号不可为空"));
    }

    public SoapResponse returnResponse(String returnStatus, String returnMsg, String requestTime, String instId){
        SoapResponse returnResponse = new SoapResponse();
        Date nowDate = new Date();
        returnResponse.setSuccess("true");
        SoapResponse.RESPONSE responseValue = new SoapResponse.RESPONSE();
        SoapResponse.RESPONSE.EsbInfo esbInfoValue = new SoapResponse.RESPONSE.EsbInfo();
        esbInfoValue.setReturnStatus(returnStatus);
        esbInfoValue.setReturnMsg(returnMsg);
        esbInfoValue.setResponseTime(requestTime);
        if(StringUtils.isBlank(requestTime)){
            requestTime = DateUtil.parseDateToStr(nowDate, "yyyy-MM-dd HH:mm:ss:SSS");
        }
        esbInfoValue.setInstId(instId);
        esbInfoValue.setRequestTime(requestTime);
        esbInfoValue.setAttr1("");
        esbInfoValue.setAttr2("");
        esbInfoValue.setAttr3("");
        responseValue.setEsbInfo(esbInfoValue);
        returnResponse.setResponse(responseValue);
        return returnResponse;
    }
}
