package com.midea.cloud.srm.po.logisticsOrder.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.constants.SequenceCodeConstant;
import com.midea.cloud.common.enums.UserType;
import com.midea.cloud.common.enums.bid.projectmanagement.evaluation.SelectionStatusEnum;
import com.midea.cloud.common.enums.logistics.LogisticsOrderStatus;
import com.midea.cloud.common.enums.logistics.LogisticsOrderTmsStatus;
import com.midea.cloud.common.enums.logistics.OrderSourceFrom;
import com.midea.cloud.common.enums.logistics.TransportModeEnum;
import com.midea.cloud.common.enums.logistics.pr.requirement.LogisticsApplyProcessStatus;
import com.midea.cloud.common.enums.pm.ps.BusinessType;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.BeanCopyUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.bid.service.ILgtBidingService;
import com.midea.cloud.srm.feign.api.ApiClient;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.logistics.template.service.ILogisticsTemplateLineService;
import com.midea.cloud.srm.model.api.interfacelog.dto.InterfaceLogDTO;
import com.midea.cloud.srm.model.base.soap.erp.dto.SoapResponse;
import com.midea.cloud.srm.model.logistics.bid.entity.*;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidinitiating.enums.SourceFrom;
import com.midea.cloud.srm.model.logistics.bid.vo.LgtBidInfoVO;
import com.midea.cloud.srm.model.logistics.bid.vo.LgtVendorQuotedInfoVO;
import com.midea.cloud.srm.model.logistics.bid.vo.LgtVendorVO;
import com.midea.cloud.srm.model.logistics.po.order.dto.LogisticsOrderDTO;
import com.midea.cloud.srm.model.logistics.po.order.entity.*;
import com.midea.cloud.srm.model.logistics.po.order.vo.LogisticsOrderVO;
import com.midea.cloud.srm.model.logistics.pr.requirement.entity.LogisticsRequirementHead;
import com.midea.cloud.srm.model.logistics.soap.order.entity.*;
import com.midea.cloud.srm.model.logistics.soap.tms.request.LogisticsOrderRequest;
import com.midea.cloud.srm.model.logistics.template.entity.LogisticsTemplateLine;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.po.logisticsOrder.mapper.LogisticsOrderHeadMapper;
import com.midea.cloud.srm.po.logisticsOrder.service.*;
import com.midea.cloud.srm.po.logisticsOrder.soap.service.ITmsLogisticsOrderWsService;
import com.midea.cloud.srm.po.logisticsOrder.soap.service.LogisticsContractRatePtt;
import com.midea.cloud.srm.pr.logisticsRequirement.service.IRequirementHeadService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

/**
 *  <pre>
 *  物流采购订单头表 服务实现类
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 *  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-12-05 18:50:05
 *  修改内容:
 * </pre>
 */
@Service(value = "LogisticsOrderHeadServiceImpl")
@Slf4j
public class OrderHeadServiceImpl extends ServiceImpl<LogisticsOrderHeadMapper, OrderHead> implements IOrderHeadService {


    @Autowired
    private BaseClient baseClient;

    @Autowired
    private IOrderLineService iOrderLineService;

    @Autowired
    private IOrderFileService iOrderFileService;

    @Autowired
    private IOrderLineContractService iOrderLineContractService;

    @Autowired
    private IOrderLineShipService iOrderLineShipService;

    @Autowired
    private ILogisticsTemplateLineService iLogisticsTemplateLineService;

    @Autowired
    private IRequirementHeadService requirementHeadService;

    @Autowired
    private ILgtBidingService lgtBidingService;

    @Autowired
    private ApiClient apiClient;

    @Value("${SOAP_URL.LGT_ORDER_URL}")
    private String orderToTmsUrl;

    @Autowired
    private ThreadPoolExecutor requestExecutors;

    @Autowired
    private IOrderLineFeeService iOrderLineFeeService;

    @Autowired
    private LogisticsOrderHeadMapper orderHeadMapper;

    @Autowired
    private IOrderRejectRecordService orderRejectRecordService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long addOrder(LogisticsOrderDTO orderDTO) {
        log.info("保存物流采购订单,参数：{}", orderDTO);
        OrderHead orderHead = orderDTO.getOrderHead();
        List<OrderLine> orderLineList = orderDTO.getOrderLineList();
        List<OrderLineContract> orderLineContractList = orderDTO.getOrderLineContractList();
        List<OrderLineShip> orderLineShipList = orderDTO.getOrderLineShipList();
        List<OrderFile> orderFileList = orderDTO.getOrderFileList();

        //获取登录人信息
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        //部门编码
        String purchaseDepartmentCode = loginAppUser.getCeeaDeptId();
        //部门名称
        String purchaseDepartmentName = loginAppUser.getDepartment();

        long id = IdGenrator.generate();
        String status = orderHead.getOrderStatus();
        if(Objects.isNull(status)){
            status = LogisticsOrderStatus.DRAFT.getValue();
        }

        orderHead.setOrderHeadId(id)
                .setOrderHeadNum(baseClient.seqGen(SequenceCodeConstant.SEQ_LOGISTICS_ORDER))
                .setTmsStatus(LogisticsOrderTmsStatus.NOT_SYNC.getValue())
                .setApplyDepartmentCode(purchaseDepartmentCode)
                .setApplyDepartmentName(purchaseDepartmentName)
                .setOrderStatus(status);
        if(StringUtils.isBlank(orderHead.getOrderSourceFrom())){
            orderHead.setOrderSourceFrom(OrderSourceFrom.MANUAL.getItemValue());
        }
        this.save(orderHead);
        // 保存采购订单行列表
        iOrderLineService.addOrderLineBatch(orderHead, orderLineList);
        // 保存采购订单附件表
        iOrderFileService.addOrderFileBatch(orderHead, orderFileList);
        // 保存采购订单合同列表
        iOrderLineContractService.addOrderContractBatch(orderHead, orderLineContractList);
        // 保存订单船期列表
        iOrderLineShipService.addOrderShipBatch(orderHead, orderLineShipList);
        // 回写【采购订单号】【处理状态】至采购申请上
        if (!SourceFrom.MANUAL.name().equals(orderHead.getSourceFrom())) {
            requirementHeadService.updateById(new LogisticsRequirementHead()
                    .setRequirementHeadId(Long.parseLong(orderHead.getRequirementHeadId()))
                    .setApplyProcessStatus(LogisticsApplyProcessStatus.FINISHED.getValue())
            );
        }
        return id;
    }

    /**
     * 批量提交物流采购订单
     * @param orderDTOList
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public List<Long> batchSubmitOrder(List<LogisticsOrderDTO> orderDTOList){
        log.info("批量提交物流采购订单,参数：{}", orderDTOList);
        //校验
        for(LogisticsOrderDTO orderDTO : orderDTOList){
            OrderHead orderHead = orderDTO.getOrderHead();
            List<OrderLine> orderLineList = orderDTO.getOrderLineList();
            checkIfSubmit(orderHead, orderLineList);
        }

        //提交物流采购订单
        List<Long> result = new LinkedList<>();
        for(LogisticsOrderDTO orderDTO : orderDTOList){
            Long id = submit(orderDTO);
            result.add(id);
        }
        return result;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long modifyOrder(LogisticsOrderDTO orderDTO) {
        OrderHead orderHead = orderDTO.getOrderHead();
        List<OrderLine> orderLineList = orderDTO.getOrderLineList();
        List<OrderLineContract> orderLineContractList = orderDTO.getOrderLineContractList();
        List<OrderFile> orderFileList = orderDTO.getOrderFileList();
        List<OrderLineShip> orderLineShipList = orderDTO.getOrderLineShipList();

        OrderHead head = this.getById(orderHead.getOrderHeadId());
        this.updateById(orderHead);
        // 更新采购订单行列表
        iOrderLineService.updateBatch(orderHead, orderLineList);
        // 更新采购订单合同列表
        iOrderLineContractService.updateBatch(orderHead, orderLineContractList);
        // 更新采购订单附件列表
        iOrderFileService.updateOrderAttachBatch(orderHead, orderFileList);
        //更新采购订单船期列表
        iOrderLineShipService.updateOrderLineShipBatch(orderHead,orderLineShipList);
        return head.getOrderHeadId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByHeadId(Long orderHeadId) {
        log.info("删除物流采购订单，参数：{}",orderHeadId);
        OrderHead orderHead = this.getById(orderHeadId);
        if(Objects.isNull(orderHead)){
            throw new BaseException(String.format("未找到物流采购订单，单据id：%s",orderHead));
        }
        //拟定状态下才可删除
        if (LogisticsOrderStatus.DRAFT.getValue().equals(orderHead.getOrderStatus())) {
            this.removeById(orderHeadId);
            List<OrderLine> rls = iOrderLineService.list(new QueryWrapper<>(new OrderLine().setOrderHeadId(orderHeadId)));
            iOrderLineService.removeBatch(orderHeadId);
            iOrderLineContractService.remove(new QueryWrapper<>(new OrderLineContract().setOrderHeadId(orderHeadId)));
            iOrderFileService.remove(new QueryWrapper<>(new OrderFile().setOrderHeadId(orderHeadId)));
        } else {
            throw new BaseException(LocaleHandler.getLocaleMsg("只有拟定状态下才可以删除"));
        }
    }

    @Override
    public PageInfo<OrderHead> listPage(OrderHead orderHead) {
        PageUtil.startPage(orderHead.getPageNum(), orderHead.getPageSize());
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        if(UserType.VENDOR.name().equals(loginAppUser.getUserType())){
            //供应商断登录，设置权限
            orderHead.setLoginCompanyId(loginAppUser.getCompanyId());
        }
        return new PageInfo<OrderHead>(orderHeadMapper.list(orderHead));
    }


    @Override
    public LogisticsOrderVO getByHeadId(Long orderHeadId) {
        Assert.notNull(orderHeadId, "采购订单头ID不能为空");
        LogisticsOrderVO vo = new LogisticsOrderVO();
        vo.setOrderHead(this.getById(orderHeadId));
        //物流订单行
        List<OrderLine> orderLineList = iOrderLineService.listBatch(orderHeadId);
        vo.setOrderLineList(orderLineList);
        //物流订单文件
        List<OrderFile> orderFileList = iOrderFileService.list(new QueryWrapper<>(new OrderFile()
                .setOrderHeadId(orderHeadId)));
        vo.setOrderFileList(orderFileList);
        //物流订单合同行
        List<OrderLineContract> orderLineContractList = iOrderLineContractService.list(new QueryWrapper<>(new OrderLineContract()
                .setOrderHeadId(orderHeadId)));
        vo.setOrderLineContractList(orderLineContractList);
        //物流订单船期
        List<OrderLineShip> orderLineShipList = iOrderLineShipService.list(new QueryWrapper<>(new OrderLineShip().setOrderHeadId(orderHeadId)));
        vo.setOrderLineShipList(orderLineShipList);
        //物流订单拒绝记录
        List<OrderRejectRecord> orderRejectRecordList = orderRejectRecordService.list(new QueryWrapper<>(new OrderRejectRecord().setOrderHeadId(orderHeadId)));
        vo.setOrderRejectRecordList(orderRejectRecordList);
        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long submitApproval(LogisticsOrderDTO orderDTO) {
        log.info("提交物流采购订单，参数：{}",orderDTO.toString());
        OrderHead orderHead = orderDTO.getOrderHead();
        List<OrderLine> orderLineList = orderDTO.getOrderLineList();
        //校验
        checkIfSubmit(orderHead,orderLineList);
        //提交物流采购订单
        Long id = submit(orderDTO);
        return id;

    }

    /**
     * 提交物流采购订单
     * @param orderDTO
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Long submit(LogisticsOrderDTO orderDTO){
        OrderHead orderHead = orderDTO.getOrderHead();

        String status = null;

        //是否已经维护合同
        String ifContractMaintain = null;
        //是否需要供方确认
        String ifNeedVendorComfirm = null;

        List<OrderLineContract> orderLineContractList = orderDTO.getOrderLineContractList();
        if(CollectionUtils.isEmpty(orderLineContractList)){
            ifContractMaintain = "N";
        }else{
            ifContractMaintain = "Y";
        }
        ifNeedVendorComfirm = orderHead.getIfNeedVendorComfirm();

        if("N".equals(ifContractMaintain)){
            status = LogisticsOrderStatus.WAITING_CONFIRM.getValue();
        }else {
            if("Y".equals(ifNeedVendorComfirm)){
                status = LogisticsOrderStatus.WAITING_VENDOR_CONFIRM.getValue();
            }else{
                status = LogisticsOrderStatus.COMPLETED.getValue();
            }
        }

        orderHead.setOrderStatus(status);
        if (orderHead.getOrderHeadId() == null) {
            //增加
            this.addOrder(orderDTO);
        } else {
            //修改
            this.modifyOrder(orderDTO);
        }

        //更新采购申请【需求池分配状态】
        if(SourceFrom.PURCHASE_REQUEST.name().equals(orderHead.getSourceFrom())){
            if(LogisticsOrderStatus.COMPLETED.getValue().equals(status)){
                LogisticsRequirementHead requirementHead = requirementHeadService.getById(orderHead.getRequirementHeadId());
                requirementHeadService.updateById(requirementHead.setApplyProcessStatus(LogisticsApplyProcessStatus.FINISHED.getValue()));
            }
        }

        //推送Tms
        if(LogisticsOrderStatus.COMPLETED.getValue().equals(status)){
            requestExecutors.submit(new Thread(() -> {
                syncTmsLongi(orderHead.getOrderHeadId());
            }));
        }
        return orderHead.getOrderHeadId();
    }


    /**
     * 校验项
     * 1.必填项校验
     * (1)采购申请头必填校验
     *   申请模板id
     *   业务模式
     *   运输方式
     *   业务类型
     *   单位编码
     *   项目总量
     *   价格有效开始日期
     *   价格有效结束日期
     *   是否需要供方确认
     *   采购申请id
     * (2)采购申请行必填校验
     * 2.采购申请的校验
     * (1)采购申请不可为空
     * (2)该采购申请是否创建过订单
     * 3.其他校验
     * (1)业务类型为项目类时,校验服务项目名称必填
     * @param orderHead
     * @param orderLineList
     */
    public void checkIfSubmit(OrderHead orderHead,List<OrderLine> orderLineList){
        Assert.notNull(orderHead, LocaleHandler.getLocaleMsg("采购订单头不能为空"));
        Assert.notEmpty(orderLineList, LocaleHandler.getLocaleMsg("请至少添加一行行程明细信息"));
        String errorMsg = "%s为带*的字段，要求必填，请维护字段值";
        //检查物流采购订单头，必填字段
        Assert.notNull(orderHead.getTemplateHeadId(), LocaleHandler.getLocaleMsg(String.format(errorMsg,"申请模板")));
        Assert.hasText(orderHead.getBusinessModeCode(), LocaleHandler.getLocaleMsg(String.format(errorMsg,"业务模式")));
        Assert.hasText(orderHead.getTransportModeCode(), LocaleHandler.getLocaleMsg(String.format(errorMsg,"运输方式")));
        Assert.hasText(orderHead.getBusinessType(), LocaleHandler.getLocaleMsg(String.format(errorMsg,"业务类型不能为空")));
        Assert.hasText(orderHead.getUnit(), LocaleHandler.getLocaleMsg(String.format(errorMsg,"单位")));
        Assert.notNull(orderHead.getProjectTotal(), LocaleHandler.getLocaleMsg(String.format(errorMsg,"项目总量")));
        Assert.notNull(orderHead.getPriceStartDate(), LocaleHandler.getLocaleMsg(String.format(errorMsg,"价格有效开始日期")));
        Assert.notNull(orderHead.getPriceEndDate(), LocaleHandler.getLocaleMsg(String.format(errorMsg,"价格有效结束日期")));
        Assert.hasText(orderHead.getIfNeedVendorComfirm(), LocaleHandler.getLocaleMsg(String.format(errorMsg,"是否需要供方确认")));
//        Assert.hasText(orderHead.getNumberComment(),"数量说明必填");
        //采购申请转招标的校验
        if(SourceFrom.PURCHASE_REQUEST.name().equals(orderHead.getSourceFrom())){
            Assert.notNull(orderHead.getRequirementHeadId(), LocaleHandler.getLocaleMsg("采购申请id必传"));
            //必填校验
            LogisticsRequirementHead requirementHead = requirementHeadService.getById(orderHead.getRequirementHeadId());
            if(Objects.isNull(requirementHead)){
                throw new BaseException(LocaleHandler.getLocaleMsg("查询不到该采购申请,requirementHeadId = [%s]",orderHead.getRequirementHeadId()));
            }

            //校验采购申请是否已经被使用
            checkIfRequirementUsed(requirementHead);
        }

        //ii.	若业务类型为项目类时，校验服务项目名称必填
        if(BusinessType.BUSINESS_TYPE_40.getValue().equals(orderHead.getBusinessType()) && StringUtils.isBlank(orderHead.getServiceProjectCode())){
            throw new BaseException(LocaleHandler.getLocaleMsg("若业务类型为项目类时，校验服务项目编码必填"));
        }

        //检查行，必填
        checkLineByTemplate(orderHead,orderLineList);

    }

    /**
     * 校验采购申请是否已经使用
     * @param requirementHead
     */
    private void checkIfRequirementUsed(LogisticsRequirementHead requirementHead){
        Long requirementHeadId = requirementHead.getRequirementHeadId();
        QueryWrapper<OrderHead> orderHeadQueryWrapper = new QueryWrapper<>();
        orderHeadQueryWrapper.eq("REQUIREMENT_HEAD_ID",requirementHeadId);
        int count = this.count(orderHeadQueryWrapper);
        if(count > 0){
            throw new BaseException(LocaleHandler.getLocaleMsg(String.format("requirementHeadNum=[%s]申请已经创建过订单,不允许重复创建",requirementHead.getRequirementHeadNum())));
        }
    }


    /**
     * <pre>
     *  //根据申请模板，校验行上面的字段
     * </pre>
     *
     * @author chenwt24@meicloud.com
     * @version 1.00.00
     *
     * <pre>
     *  修改记录
     *  修改后版本:
     *  修改人:
     *  修改日期: 2020-11-30
     *  修改内容:
     * </pre>
     */
    public void checkLineByTemplate(OrderHead requirementHead,List<OrderLine> requirementLineList){
        String templateName = requirementHead.getTemplateName();
        Assert.notNull(templateName,"物流申请模板名称为空!");

        Long templateHeadId = requirementHead.getTemplateHeadId();
        List<LogisticsTemplateLine> lines = iLogisticsTemplateLineService.list(Wrappers.lambdaQuery(LogisticsTemplateLine.class)
                .eq(LogisticsTemplateLine::getHeadId, templateHeadId));

        List<LogisticsTemplateLine> notEmptyList = lines.parallelStream().filter(x -> Objects.equals(x.getApplyNotEmptyFlag(), "Y")).collect(Collectors.toList());

        for (int i = 0; i < notEmptyList.size(); i++) {
            LogisticsTemplateLine logisticsTemplateLine = notEmptyList.get(i);
            for (int j = 0; j < requirementLineList.size(); j++) {
                Object o = null;
                try {
                    o = doCheck(logisticsTemplateLine.getFieldCode(), requirementLineList.get(j));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                if(Objects.isNull(o)){
                    throw new BaseException(String.format("模板：%S,[%S]不能为空",templateName,logisticsTemplateLine.getFieldName()));
                }
            }
        }
    }

    public Object doCheck(String fieldCode,OrderLine line) throws IllegalAccessException {

        Field[] declaredFields = OrderLine.class.getDeclaredFields();
        for (Field s:declaredFields){
            boolean annotationPresent = s.isAnnotationPresent(TableField.class);
            if(annotationPresent && StringUtils.isNotBlank(s.getName())){
                TableField annotation = s.getAnnotation(TableField.class);
                String value = annotation.value();
                s.setAccessible(true);
                if(Objects.equals(fieldCode,value)){
                    return s.get(line);
                }
            }
        }

        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateApproved(Long orderHeadId, String auditStatus) {
        // 更新头表
        this.updateById(new OrderHead().setOrderHeadId(orderHeadId)
                .setOrderStatus(auditStatus));

        //TODO:完成：则单据变成完成状态，并将订单同步到TMS

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void submitVendorConfirm(Long orderHeadId) {
        //i.	完成状态的单据可以提交供应商确认
        //ii.	拟定状态的单据可以提交供应商确认
        //iii.	拒绝状态的单据可以提交供应商确认
        //iv.	单据状态是：完成，同步状态是：成功的单据不能再次进行提交供应商确认
//        Objects.requireNonNull(orderHeadId);
        OrderHead byId = this.getById(orderHeadId);
        if(Objects.isNull(byId)){
            throw new BaseException(String.format("未找到物流采购订单，订单id：%s",orderHeadId));
        }
//        if(Objects.equals(byId.getOrderStatus(),LogisticsOrderStatus.COMPLETED.getValue()) && Objects.equals(byId.getTmsStatus(), LogisticsOrderTmsStatus.SUCCESS_SYNC)){
//            throw new BaseException(String.format("单据状态是：完成，同步状态是：成功的单据不能再次进行提交供应商确认"));
//        }

        // 更新头表
        this.updateById(new OrderHead().setOrderHeadId(orderHeadId)
                .setOrderStatus(LogisticsOrderStatus.COMPLETED.getValue()));

        //更新采购申请【需求池分配状态】
        LogisticsRequirementHead requirementHead = requirementHeadService.getById(byId.getRequirementHeadId());
        requirementHeadService.updateById(requirementHead.setApplyProcessStatus(LogisticsApplyProcessStatus.FINISHED.getValue()));
        //推送tms
        requestExecutors.submit(new Thread(() -> {
            syncTmsLongi(orderHeadId);
        }));
//        syncTms(orderHeadId);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void refuse(OrderHead orderHead) {
        Long orderHeadId = orderHead.getOrderHeadId();
        String rejectReason = orderHead.getRejectReason();
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        //校验是否可拒绝
        checkIfRefuse(orderHead);
        // 更新头表
        this.updateById(new OrderHead().setOrderHeadId(orderHeadId)
                .setIfRejected("Y")
                .setOrderStatus(LogisticsOrderStatus.DRAFT.getValue()));
        //插入拒绝行表
        orderRejectRecordService.save(new OrderRejectRecord()
                .setOrderRejectRecordId(IdGenrator.generate())
                .setOrderHeadId(orderHeadId)
                .setRejectReason(rejectReason)
                .setRejectNickname(loginAppUser.getNickname())
                .setRejectUsername(loginAppUser.getUsername())
        );
    }

    /**
     * 校验是否可拒绝
     * @param orderHead
     */
    private void checkIfRefuse(OrderHead orderHead){
        Long orderHeadId = orderHead.getOrderHeadId();

        //校验
        if(Objects.isNull(orderHead.getOrderHeadId())){
            throw new BaseException(LocaleHandler.getLocaleMsg("请传递必传参数"));
        }
        if(StringUtils.isBlank(orderHead.getRejectReason())){
            throw new BaseException(LocaleHandler.getLocaleMsg("请添加拒绝原因"));
        }
        OrderHead oh = this.getById(orderHeadId);
        if(Objects.isNull(oh)){
            throw new BaseException(LocaleHandler.getLocaleMsg("查询不到物流订单,请刷新页面"));
        }
        if(!LogisticsOrderStatus.WAITING_VENDOR_CONFIRM.getValue().equals(oh.getOrderStatus())){
            throw new BaseException(LocaleHandler.getLocaleMsg(String.format("物流订单[%s]状态为[%s],不可拒绝",oh.getOrderHeadNum(),oh.getOrderStatus())));
        }
    }

    /**
     * 暂存
     * @param orderDTO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long temporarySave(LogisticsOrderDTO orderDTO) {
        //校验是否可暂存
        checkIfTemporarySave(orderDTO);

        Long orderHeadId = null;
        OrderHead orderHead = orderDTO.getOrderHead();
        if(Objects.isNull(orderHead.getOrderHeadId())){
            //保存
            orderHeadId = addOrder(orderDTO);
        }else{
            //修改
            orderHeadId = modifyOrder(orderDTO);
        }
        return orderHeadId;
    }

    /**
     * 采购订单同步tms
     * @param orderHeadId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void syncTms(Long orderHeadId) {

        LogisticsOrderVO logisticsOrderVO = this.getByHeadId(orderHeadId);

        //校验是否可同步
        checkIfSync(logisticsOrderVO);

        //请求实体
        LogisticsOrderRequest logisticsOrderRequest = null;
        //返回实体
        SoapResponse response = null;
        //报错信息
        Map<String, String> errorMsg = null;
        try {
            logisticsOrderRequest = buildRequest(logisticsOrderVO);
            log.info("请求的报文是：" + logisticsOrderRequest);
//            orderToTmsUrl = "http://10.16.87.99:8842/registerService/TmsLogisticsOrderWsServiceImpl?wsdl";
            //创建主账号接口地址
            String address = orderToTmsUrl;
            //代理工厂
            JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
            jaxWsProxyFactoryBean.setAddress(address);
            //设置接口类型
            jaxWsProxyFactoryBean.setServiceClass(ITmsLogisticsOrderWsService.class);
            //创建一个代理接口实现
            ITmsLogisticsOrderWsService service = (ITmsLogisticsOrderWsService) jaxWsProxyFactoryBean.create();
            //推送tms
            response = service.execute(logisticsOrderRequest);
        } catch (Exception e) {
            // 堆栈错误信息
            String stackTrace = Arrays.toString(e.getStackTrace());
            // 错误信息
            String message = e.getMessage();
            errorMsg = new HashMap<>();
            errorMsg.put("message", e.getClass().getName() + ": " + message);
            errorMsg.put("stackTrace", stackTrace);
            log.error(e.getMessage());
            throw e;

        } finally {
            try {
                //保存接口日志
                saveInterfaceLog(logisticsOrderRequest, response, errorMsg, logisticsOrderVO);
                //物流订单推送tms,状态回写
                updateAfterTmsPush(response,orderHeadId);
            } catch (Exception e){
                log.error(e.getMessage());
            }

        }
    }

    /**
     * 采购订单同步tms(隆基项目)
     * @param orderHeadId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void syncTmsLongi(Long orderHeadId){
        LogisticsOrderVO logisticsOrderVO = this.getByHeadId(orderHeadId);
        Assert.notNull(logisticsOrderVO,"找不到订单数据!!!");
        /**
         * 陆运、铁运, 不推船期信息
         */
        String transportModeCode = logisticsOrderVO.getOrderHead().getTransportModeCode();
        if(TransportModeEnum.LAND_TRANSPORT.name().equals(transportModeCode) ||
                TransportModeEnum.RAILWAY_TRANSPORT.name().equals(transportModeCode)){
            logisticsOrderVO.setOrderLineShipList(null);
        }

        //校验是否可同步
        checkIfSync(logisticsOrderVO);

        //请求实体
        GetSrmTariffInfo srmTariffInfo = null;
        //返回实体
        GetSrmTariffInfoResponse responseBody = null;
        //报错信息
        Map<String, String> errorMsg = null;
        try {
            srmTariffInfo = buildRequestLongi(logisticsOrderVO);
            log.info("请求的报文是：" + srmTariffInfo);
//            orderToTmsUrl = "http://soatest.longi.com:8011/TMSSB/Srm/LogisticsContractRate/ProxyServices/TmsAcceptLogisticsContractRateSoapProxy?wsdl";
            //创建主账号接口地址
            String address = orderToTmsUrl;
            //代理工厂
            JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
            jaxWsProxyFactoryBean.setAddress(address);
            //设置接口类型
            jaxWsProxyFactoryBean.setServiceClass(LogisticsContractRatePtt.class);
            //创建一个代理接口实现
            LogisticsContractRatePtt logisticsContractRatePtt = (LogisticsContractRatePtt) jaxWsProxyFactoryBean.create();
            //推送tms
            responseBody = logisticsContractRatePtt.logisticsContractRate(srmTariffInfo);

        } catch (Exception e) {
            // 堆栈错误信息
            String stackTrace = Arrays.toString(e.getStackTrace());
            // 错误信息
            String message = e.getMessage();
            errorMsg = new HashMap<>();
            errorMsg.put("message", e.getClass().getName() + ": " + message);
            errorMsg.put("stackTrace", stackTrace);
            log.error(e.getMessage());
            throw e;
        } finally {
            try {
                //保存接口日志
                saveInterfaceLogLongi(srmTariffInfo, responseBody, errorMsg, logisticsOrderVO);
                //物流订单推送tms,状态回写
                updateAfterTmsPushLongi(responseBody,orderHeadId);
            } catch (Exception e){
                log.error(e.getMessage());
            }
        }
    }

    /**
     * 构建采购订单对接tms实体类
     * @param logisticsOrderVO
     * @return
     */
    private GetSrmTariffInfo buildRequestLongi(LogisticsOrderVO logisticsOrderVO){
        OrderHead orderHead = logisticsOrderVO.getOrderHead();
        List<OrderLine> orderLineList = logisticsOrderVO.getOrderLineList();
        List<OrderLineShip> orderLineShipList = logisticsOrderVO.getOrderLineShipList();
        List<OrderLineContract> orderLineContractList = logisticsOrderVO.getOrderLineContractList();

        //获取其他模块的数据  LogisticsRequirementHead(如果是手工创建的招标单,则没有采购申请id)
        //合同类型
        String contractType = null;
        if(Objects.nonNull(orderHead.getRequirementHeadId())){
            LogisticsRequirementHead logisticsRequirementHead = requirementHeadService.getById(orderHead.getRequirementHeadId());
            if(Objects.isNull(logisticsRequirementHead)){
                throw new BaseException(LocaleHandler.getLocaleMsg(String.format("orderHeadId=[%s],获取不到采购申请,requirementHeadId=[%s]",orderHead.getOrderHeadId(),orderHead.getRequirementHeadId())));
            }
            contractType = logisticsRequirementHead.getContractType();
        }

        String settlementMethod = null;
        if(!CollectionUtils.isEmpty(orderLineContractList)){
            settlementMethod = orderLineContractList.stream().map(item -> item.getPaymentMethod()).collect(Collectors.joining("/"));
        }

        //设置采购订单头
        Customerinfo customerinfo = new Customerinfo();
        customerinfo.setOrderHeadNum(orderHead.getOrderHeadNum());
        customerinfo.setVendorCode(orderHead.getVendorCode());
        customerinfo.setPriceStartDate(String.valueOf(orderHead.getPriceStartDate()));
        customerinfo.setPriceEndDate(String.valueOf(orderHead.getPriceEndDate()));
        customerinfo.setSettlementMethod(settlementMethod); //结算方式
        customerinfo.setContractType(contractType);   //合同类型
        customerinfo.setBusinessModeCode(orderHead.getBusinessModeCode());
        customerinfo.setTransportModeCode(orderHead.getTransportModeCode());
        customerinfo.setStatus("Y"); //有效性
        customerinfo.setSourceSystem("SRM");

        //设置行程明细
        List<Road> roadList = new LinkedList<>();
        if(!CollectionUtils.isEmpty(orderLineList)){
            for(OrderLine orderLine : orderLineList){
                Road road = new Road();

                road.setOrderHeadNum(orderLine.getOrderHeadNum());
                road.setRowLineNum(String.valueOf(orderLine.getRowNum()));
                road.setFromCountry(orderLine.getFromCountry());
                road.setFromProvince(orderLine.getFromProvince());
                road.setFromCity(orderLine.getFromCity());
                road.setFromCounty(orderLine.getFromCounty());
                road.setFromPlace(orderLine.getFromPlace());
                road.setToCountry(orderLine.getToCountry());
                road.setToProvinceCode(orderLine.getToProvinceCode());
                road.setToCountyCode(orderLine.getToCountyCode());
                road.setToPlace(orderLine.getToPlace());
                road.setVendorOrderNum("1"); //供方顺序
                road.setTransportDistance(orderLine.getTransportDistance());
                road.setTransportModeCode(orderHead.getTransportModeCode()); //运输方式
                road.setServiceProjectCode(orderHead.getServiceProjectCode());  //项目
                road.setStatus("Y");
                road.setRealTime(null); //运输周期
                road.setPriceStartDate(String.valueOf(orderHead.getPriceStartDate())); //生效期
                road.setPriceEndDate(String.valueOf(orderHead.getPriceEndDate())); //失效期
                road.setWholeArk(orderLine.getWholeArk());
                road.setFromPort(orderLine.getFromPortCode());
                road.setToPort(orderLine.getToPortCode());
                road.setUnProjectFlag("N"); //非项目可使用
                // todo 还有一个进出口方式

                //设置费用项
                List<Rate> rateList = new LinkedList<>();
                List<OrderLineFee> orderLineFeeList = orderLine.getOrderLineFeeList();
                if(!CollectionUtils.isEmpty(orderLineFeeList)){
                    for(OrderLineFee orderLineFee : orderLineFeeList){
                        Rate rate = new Rate();
                        rate.setOrderHeadNum(orderLineFee.getOrderHeadNum());
                        rate.setRowLineNum(String.valueOf(orderLine.getRowNum()));
                        rate.setRowNum(String.valueOf(orderLineFee.getRowNum()));
                        rate.setExpenseItem(orderLineFee.getExpenseItem());
                        rate.setChargeMethod(orderLineFee.getChargeMethod());
                        rate.setChargeUnit(orderLineFee.getChargeUnit());
                        rate.setMinCost(orderLineFee.getMinCost());
                        rate.setMaxCost(orderLineFee.getMaxCost());
                        rate.setExpense(String.valueOf(orderLineFee.getExpense()));
                        rate.setCurrency(orderLineFee.getCurrency());
                        rate.setIfBack(orderLine.getIfBack()); //是否往返
                        rate.setLeg(orderLineFee.getLeg());
                        rate.setTaxRate(String.valueOf(orderHead.getTaxRate()));
                        rateList.add(rate);
                    }
                }
                road.setRate(rateList);
                roadList.add(road);
            }
        }

        //设置船期信息
        List<Boat> boatList = new LinkedList<>();
        if(!CollectionUtils.isEmpty(orderLineShipList)){
            for(OrderLineShip orderLineShip : orderLineShipList){
                Boat boat = new Boat();
                boat.setOrderHeadNum(orderLineShip.getOrderHeadNum());
                boat.setRowNum(String.valueOf(orderLineShip.getRowNum()));
                boat.setFromPort(orderLineShip.getFromPortCode());
                boat.setToPort(orderLineShip.getToPortCode());
                boat.setWholeArk(orderLineShip.getWholeArk());
                boat.setMon(String.valueOf(orderLineShip.getMon()));
                boat.setTue(String.valueOf(orderLineShip.getTue()));
                boat.setWed(String.valueOf(orderLineShip.getWed()));
                boat.setThu(String.valueOf(orderLineShip.getThu()));
                boat.setFri(String.valueOf(orderLineShip.getFri()));
                boat.setSat(String.valueOf(orderLineShip.getSat()));
                boat.setSun(String.valueOf(orderLineShip.getSun()));
                boat.setTransitTime(String.valueOf(orderLineShip.getTransitTime()));
                boat.setCompany(orderLineShip.getShipCompanyName());
                boat.setTransferPort(orderLineShip.getTransferPort());
                boatList.add(boat);
            }
        }

        customerinfo.setRoad(roadList);
        customerinfo.setBoat(boatList);

        GetSrmTariffInfo srmTariffInfo = new GetSrmTariffInfo();
        srmTariffInfo.setEsbInfo(new EsbInfo());
        srmTariffInfo.setHead(new Head());
        srmTariffInfo.setRequestInfo(new LinkedList<Customerinfo>(){{
            add(customerinfo);
        }});
        return srmTariffInfo;
    }

    /**
     * 保存接口日志
     * @param srmTariffInfo
     * @param response
     * @param errorMsg
     * @param logisticsOrderVO
     */
    private void saveInterfaceLogLongi(GetSrmTariffInfo srmTariffInfo,GetSrmTariffInfoResponse response,Map<String, String> errorMsg,LogisticsOrderVO logisticsOrderVO){
        List<Customerinfo> requestInfo = srmTariffInfo.getRequestInfo();
        InterfaceLogDTO interfaceLogDTO = new InterfaceLogDTO();
        if(!CollectionUtils.isEmpty(requestInfo)){
            try {
                interfaceLogDTO.setServiceInfo(JSON.toJSONString(requestInfo));
            } catch (Exception e) {
                log.error(String.format("记录采购订单推送TMS接口日志报错，单号为[%s]",logisticsOrderVO.getOrderHead().getOrderHeadNum()));
            }
        }
        interfaceLogDTO.setCreationDateBegin(new Date());
        interfaceLogDTO.setServiceName("采购订单推送ERP接口开始");
        interfaceLogDTO.setServiceType("WEBSERVICE"); //请求方式
        interfaceLogDTO.setType("SEND"); // 发送方式
        interfaceLogDTO.setBillType("物流采购订单"); // 单据类型
        interfaceLogDTO.setBillId(logisticsOrderVO.getOrderHead().getOrderHeadNum());
        interfaceLogDTO.setTargetSys("TMS");
        interfaceLogDTO.setReturnInfo(JSON.toJSONString(response));
        interfaceLogDTO.setStatus("SUCCESS");

        if(Objects.nonNull(errorMsg)){
            interfaceLogDTO.setErrorInfo(JSON.toJSONString(errorMsg));
            interfaceLogDTO.setStatus("FAIL");
        }

        if(Objects.isNull(response) ||
                Objects.isNull(response.getGetSrmTariffInfo()) ||
                Objects.isNull(response.getGetSrmTariffInfo().getEsbInfo()) ||
                !"S".equals(response.getGetSrmTariffInfo().getEsbInfo().getReturnStatus())
        ){
            if(Objects.nonNull(response) &&
                    Objects.nonNull(response.getGetSrmTariffInfo()) &&
                    Objects.nonNull(response.getGetSrmTariffInfo().getEsbInfo()) &&
                    StringUtils.isNotBlank(response.getGetSrmTariffInfo().getEsbInfo().getReturnMsg())
            ){
                interfaceLogDTO.setErrorInfo(response.getGetSrmTariffInfo().getEsbInfo().getReturnMsg());
            }
            interfaceLogDTO.setStatus("FAIL");
        }
        interfaceLogDTO.setCreationDateEnd(new Date());
        interfaceLogDTO.setFinishDate(new Date());
        try {
            apiClient.createInterfaceLogForAnon(interfaceLogDTO);
        } catch (Exception e){
            log.error("报错<物流采购订单推送TMS>日志报错{}" + e);
        }

    }

    /**
     * 回写物流采购订单
     * @param response
     * @param orderHeadId
     */
    private void updateAfterTmsPushLongi(GetSrmTariffInfoResponse response,Long orderHeadId){
        String tmsInfo = null;
        if(Objects.nonNull(response) &&
                Objects.nonNull(response.getGetSrmTariffInfo()) &&
                Objects.nonNull(response.getGetSrmTariffInfo().getEsbInfo())
        ){
            tmsInfo = response.getGetSrmTariffInfo().getEsbInfo().getReturnMsg();
        }
        if (Objects.isNull(response) ||
                Objects.isNull(response.getGetSrmTariffInfo()) ||
                Objects.isNull(response.getGetSrmTariffInfo().getEsbInfo()) ||
                !"S".equals(response.getGetSrmTariffInfo().getEsbInfo().getReturnStatus())
        ) {
            this.updateById(new OrderHead()
                    .setOrderHeadId(orderHeadId)
                    .setTmsInfo(tmsInfo) //todo 暂且设置为状态
                    .setTmsStatus(LogisticsOrderTmsStatus.FAIL_SYNC.getValue()));
        } else {
            this.updateById(new OrderHead()
                    .setOrderHeadId(orderHeadId)
                    .setTmsInfo(tmsInfo) //todo 暂且设置为状态
                    .setTmsStatus(LogisticsOrderTmsStatus.SUCCESS_SYNC.getValue()));
        }
    }



    /**
     * 招标单转物流采购订单
     * @param bidingId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<OrderHead> bidingToOrders(Long bidingId) {
        //校验参数
        if(Objects.isNull(bidingId)){
            throw new BaseException(LocaleHandler.getLocaleMsg("参数必传[bidingId]"));
        }
        //查询招标详情
        LgtBidInfoVO lgtBidInfoVO = lgtBidingService.detail(bidingId);
        //招标单 -> 物流采购订单
        List<LogisticsOrderDTO> orderDTOList = buildOrders(lgtBidInfoVO);
        //保存订单 todo 有优化的空间,批量插入多个物流采购订单
        batchSubmitOrder(orderDTOList);

        return orderDTOList.stream().map(item -> item.getOrderHead()).collect(Collectors.toList());
    }

    /**
     * 批量作废订单
     * @param ids
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchCancel(List<Long> ids) {
        //校验参数
        if(CollectionUtils.isEmpty(ids)){
            throw new BaseException(LocaleHandler.getLocaleMsg("缺失必要的请求参数[ids]"));
        }
        List<OrderHead> orderHeadList = this.listByIds(ids);
        if(CollectionUtils.isEmpty(orderHeadList)){
            throw new BaseException(LocaleHandler.getLocaleMsg("查找不到订单,请刷新"));
        }
        //【订单状态为已完成】【同步状态为失败】的订单可作废
        for(OrderHead orderHead : orderHeadList){
            boolean cancelFlag = LogisticsOrderStatus.COMPLETED.getValue().equals(orderHead.getOrderStatus()) &&
                    LogisticsOrderTmsStatus.FAIL_SYNC.getValue().equals(orderHead.getTmsStatus());
            if(!cancelFlag){
                throw new BaseException(LocaleHandler.getLocaleMsg(String.format("订单号[%s]不可作废,请检查",orderHead.getOrderHeadNum())));
            }
        }

        //批量修改订单
        for(OrderHead orderHead : orderHeadList){
            orderHead.setOrderStatus(LogisticsOrderStatus.CANCEL.getValue());
        }
        this.updateBatchById(orderHeadList);
    }

    /**
     * 批量删除订单
     * @param ids
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(List<Long> ids) {
        //校验参数
        if(CollectionUtils.isEmpty(ids)){
            throw new BaseException(LocaleHandler.getLocaleMsg("缺失必要的请求参数[ids]"));
        }
        List<OrderHead> orderHeadList = this.listByIds(ids);
        if(CollectionUtils.isEmpty(orderHeadList)){
            throw new BaseException(LocaleHandler.getLocaleMsg("查找不到订单,请刷新"));
        }
        //拟定状态的订单可删除
        for(OrderHead orderHead : orderHeadList){
            boolean deleteFlag = LogisticsOrderStatus.DRAFT.getValue().equals(orderHead.getOrderStatus());
            if(!deleteFlag){
                throw new BaseException(LocaleHandler.getLocaleMsg(String.format("订单号[%s]不可删除,请检查",orderHead.getOrderHeadNum())));
            }
        }
        //批量删除订单
        deleteByHeadIds(ids);
    }

    /**
     * 根据id批量删除订单
     * @param ids
     */
    public void deleteByHeadIds(List<Long> ids){
        //批量删除订单头
        this.removeByIds(ids);
        //批量删除订单行
        QueryWrapper<OrderLine> orderLineWrapper = new QueryWrapper<>();
        orderLineWrapper.in("ORDER_HEAD_ID",ids);
        iOrderLineService.remove(orderLineWrapper);
        //批量删除合同
        QueryWrapper<OrderLineContract> orderLineContractWrapper = new QueryWrapper<>();
        orderLineContractWrapper.in("ORDER_HEAD_ID",ids);
        iOrderLineContractService.remove(orderLineContractWrapper);
        //批量删除船期
        QueryWrapper<OrderLineShip> orderLineShipWrapper = new QueryWrapper<>();
        orderLineShipWrapper.in("ORDER_HEAD_ID",ids);
        iOrderLineShipService.remove(orderLineShipWrapper);
        //批量删除费用项
        QueryWrapper<OrderLineFee> orderLineFeeWrapper = new QueryWrapper<>();
        orderLineFeeWrapper.in("ORDER_HEAD_ID",ids);
        iOrderLineFeeService.remove(orderLineFeeWrapper);
        //批量删除文件
        QueryWrapper<OrderFile> orderFileWrapper = new QueryWrapper<>();
        orderFileWrapper.in("ORDER_HEAD_ID",ids);
        iOrderFileService.remove(orderFileWrapper);
    }

    /**
     * 获取招标单当前中标供应商的信息
     * @param lgtBidInfoVO
     * @return
     */
    private List<LogisticsOrderDTO> buildOrders(LgtBidInfoVO lgtBidInfoVO){
        List<LogisticsOrderDTO> result = new LinkedList<>();
        //招标单基础信息
        LgtBiding biding = lgtBidInfoVO.getBiding();
        //投标结果
        List<LgtVendorQuotedSum> vendorQuotedSumList = lgtBidInfoVO.getVendorQuotedSumList();
        //获取中标的投标结果
        List<LgtVendorQuotedSum> vendorQuotedSums = vendorQuotedSumList.stream().filter(item -> SelectionStatusEnum.WIN.getValue().equals(item.getBidResult()))
                .collect(Collectors.toList());

        List<Long> quotedHeadIds = vendorQuotedSumList.stream()
                .filter(item -> SelectionStatusEnum.WIN.getValue().equals(item.getBidResult()))
                .map(item -> item.getQuotedHeadId())
                .collect(Collectors.toList());
        //获取中标的供应商报价信息
        List<LgtVendorQuotedInfoVO> vendorQuotedInfoList = lgtBidInfoVO.getVendorQuotedInfoList().stream().filter(item -> quotedHeadIds.contains(item.getVendorQuotedHead().getQuotedHeadId())).collect(Collectors.toList());
        //根据供应商编码分组 获取供应商报价信息
        Map<String,LgtVendorQuotedInfoVO> map = vendorQuotedInfoList.stream().collect(Collectors.toMap(item -> item.getVendorQuotedHead().getVendorCode(),item -> item));

        //获取邀请供应商信息
        List<LgtVendorVO> vendorList = lgtBidInfoVO.getVendorList();
        //根据供应商编码分组 获取付款信息
        Map<String,LgtVendorVO> map2 = vendorList.stream().collect(Collectors.toMap(item -> item.getBidVendor().getVendorCode(),item -> item));

        for(Map.Entry<String,LgtVendorQuotedInfoVO> entry : map.entrySet()){
            //供应商编码
            String vendorCode = entry.getKey();
            //供应商报价信息
            LgtVendorQuotedInfoVO vendorQuotedInfoVO = entry.getValue();
            //去除供应商未中标的报价行
            removeNotQuoted(vendorQuotedInfoVO, vendorQuotedSums);
            //供应商付款条款
            LgtVendorVO vendorVO = map2.get(vendorCode);
            result.add(buildOrder(biding,vendorQuotedInfoVO,vendorVO));
        }
        return result;
    }

    /**
     * 移除供应商未中标的报价行
     * @param vendorQuotedInfoVO 招标单详情
     * @param vendorQuotedSums 中标的投标结果
     */
    private void removeNotQuoted(LgtVendorQuotedInfoVO vendorQuotedInfoVO, List<LgtVendorQuotedSum> vendorQuotedSums){
        //【投标结果】根据【供应商id + 始发地 + 目的地 】 匹配 【投标明细信息】
        List<String> lineKeyList = vendorQuotedSums
                .stream()
                .map(vendorQuotedSum -> new StringBuffer()
                        .append(vendorQuotedSum.getVendorId())
                        .append(vendorQuotedSum.getStartAddress())
                        .append(vendorQuotedSum.getEndAddress())
                        .toString())
                .distinct()
                .collect(Collectors.toList());

        List<LgtVendorQuotedLine> vendorQuotedLineList = vendorQuotedInfoVO.getVendorQuotedLineList();
        //过滤中标
        List<LgtVendorQuotedLine> vendorQuotedLineListFilter = vendorQuotedLineList
                .stream()
                .filter(item -> lineKeyList.contains(
                        new StringBuffer()
                                .append(item.getVendorId())
                                .append(item.getStartAddress())
                                .append(item.getEndAddress())
                                .toString())
                ).collect(Collectors.toList());
        vendorQuotedInfoVO.setVendorQuotedLineList(vendorQuotedLineListFilter);
    }

    /**
     * 招标单 -> 物流采购订单
     * @param biding 招标基础信息
     * @param vendorQuotedInfoVO 供应商报价信息
     * @param vendorVO 供应商信息(包括供应商基础信息,付款信息)
     * @return
     */
    private LogisticsOrderDTO buildOrder(LgtBiding biding, LgtVendorQuotedInfoVO vendorQuotedInfoVO, LgtVendorVO vendorVO){
        //物流采购订单头
        OrderHead orderHead = new OrderHead();
        orderHead.setSourceFrom(biding.getSourceFrom())
                .setVendorId(vendorVO.getBidVendor().getVendorId())
                .setVendorCode(vendorVO.getBidVendor().getVendorCode())
                .setVendorName(vendorVO.getBidVendor().getVendorName())
                .setTemplateHeadId(biding.getTemplateHeadId())
                .setTemplateCode(biding.getTemplateCode())
                .setTemplateName(biding.getTemplateName())
                .setBusinessModeCode(biding.getBusinessModeCode())
                .setTransportModeCode(biding.getTransportModeCode())
                .setBusinessType(biding.getBusinessType())
                .setServiceProjectName(biding.getServiceProjectName())
                .setServiceProjectCode(biding.getServiceProjectCode())
                .setIfNeedVendorComfirm(biding.getIfNeedVendorComfirm())
//                .setIfNeedShepDate()        //是否需要船期
                .setIfVendorSubmitShipDate(biding.getIfVendorSubmitShipDate())
                .setOrderTitle(biding.getBidingName())             //订单主题
//                .setBiddingSequence()         //中标顺序
                .setPriceStartDate(biding.getPriceTimeStart())
                .setPriceEndDate(biding.getPriceTimeEnd())
//                .setCeeaApplyUserName()   //采购员工号
//                .setCeeaApplyUserNickname()  //采购员用户名
//                .setCeeaApplyUserId()   //采购员id
//                .setOrderDate()      //订单日期
                .setApplyDepartmentId(String.valueOf(biding.getApplyDepartmentId()))  //部门id
                .setApplyDepartmentCode(biding.getApplyDepartmentCode())  //部门编码
                .setApplyDepartmentName(biding.getApplyDepartmentName())  //部门名称
                .setApplyCode(biding.getApplyCode())     //申请人工号
                .setApplyBy(biding.getApplyBy())        //申请人用户名
                .setApplyId(biding.getApplyId())       //申请人id
                .setUnit(biding.getUnitCode())          //单位
                .setProjectTotal(biding.getProjectTotal())      //项目总量
                .setTaxId(biding.getTaxId())            //税率id
                .setTaxRate(biding.getTaxCode())          //税率值
                .setTaxCode(biding.getTaxKey())           //税率编码
                .setRequirementHeadNum(biding.getRequirementHeadNum())   //采购需求编号
                .setRequirementHeadId(String.valueOf(biding.getRequirementHeadId()))   //采购申请id
                .setComments(biding.getComments())          //备注
//                .setDrafterOpinion()    //起草人意见
                .setPhone(biding.getPhone())             //联系电话
                .setTmsStatus(LogisticsOrderTmsStatus.NOT_SYNC.getValue())
                .setOrderSourceFrom(OrderSourceFrom.LOGISTICS_BID.getItemValue());

        //订单行
        List<OrderLine> orderLineList = new LinkedList<>();
        List<LgtVendorQuotedLine> vendorQuotedLineList = vendorQuotedInfoVO.getVendorQuotedLineList();
        //以【起始地+目的地】分组
        Map<String,List<LgtVendorQuotedLine>> map = groupVendorQuotedLine(vendorQuotedLineList);

        for(Map.Entry<String,List<LgtVendorQuotedLine>> entry : map.entrySet()){
            List<LgtVendorQuotedLine> value = entry.getValue();
            LgtVendorQuotedLine vendorQuotedLine = value.get(0);

            //设置订单行的信息
            OrderLine orderLine = new OrderLine();
            BeanUtils.copyProperties(vendorQuotedLine,orderLine);
            orderLine.setSingleKmCost(vendorQuotedLine.getSingleKmCost())
                    .setSingleDragCost(vendorQuotedLine.getSingleDragCost())
                    .setImportExportMethod(vendorQuotedLine.getImportExportMethod())
                    .setLeg(vendorQuotedLine.getLeg())
                    .setBeyondBoxCost(vendorQuotedLine.getBeyondBoxCost())
                    .setBeyondStorageCost(vendorQuotedLine.getBeyondStorageCost())
                    .setFreeBoxPeriod(vendorQuotedLine.getFreeBoxPeriod())
                    .setFreeStoragePeriod(vendorQuotedLine.getFreeStoragePeriod())
                    .setShipDateFrequency(vendorQuotedLine.getShipDateFrequency())
                    .setWholeArk(vendorQuotedLine.getWholeArk())
                    .setTradeTerm(vendorQuotedLine.getTradeTerm())
                    .setSpecifiedVendor(vendorQuotedLine.getSpecifiedVendor())
                    .setExpense(vendorQuotedLine.getExpense())
                    .setChargeUnit(vendorQuotedLine.getChargeUnit())
                    .setChargeMethod(vendorQuotedLine.getChargeMethod())
                    .setExpenseItem(vendorQuotedLine.getExpenseItem())
                    .setFreeStayPeriod(vendorQuotedLine.getFreeStayPeriod())
                    .setShipDate(vendorQuotedLine.getShipDate())
                    .setFullRealTime(vendorQuotedLine.getFullRealTime())
                    .setReserveTime(vendorQuotedLine.getReserveTime())
                    .setToPort(vendorQuotedLine.getToPort())
                    .setFromPort(vendorQuotedLine.getFromPort())
                    .setRealTime(vendorQuotedLine.getRealTime())
                    .setComments(vendorQuotedLine.getComments())
                    .setTransportDistance(vendorQuotedLine.getTransportDistance())
                    .setCurrency(vendorQuotedLine.getCurrency())
                    .setMaxCost(vendorQuotedLine.getMaxCost())
                    .setMinCost(vendorQuotedLine.getMinCost())
                    .setIfBack(vendorQuotedLine.getIfBack())
                    .setToCounty(vendorQuotedLine.getToCounty())
                    .setToCity(vendorQuotedLine.getToCity())
                    .setToProvince(vendorQuotedLine.getToProvince())
                    .setToPlace(vendorQuotedLine.getToPlace())
                    .setFromCity(vendorQuotedLine.getFromCity())
                    .setFromProvince(vendorQuotedLine.getFromProvince())
                    .setFromPlace(vendorQuotedLine.getFromPlace())
                    .setFromCountry(vendorQuotedLine.getFromCountry())
                    .setToCountry(vendorQuotedLine.getToCountry())
                    .setToCountyCode(vendorQuotedLine.getToCountyCode())
                    .setToCityCode(vendorQuotedLine.getToCityCode())
                    .setToProvinceCode(vendorQuotedLine.getToProvinceCode())
                    .setToPlaceCode(vendorQuotedLine.getToPlaceCode())
                    .setFromCountryCode(vendorQuotedLine.getFromCountryCode())
                    .setFromCityCode(vendorQuotedLine.getFromCityCode())
                    .setFromProvinceCode(vendorQuotedLine.getFromProvinceCode())
                    .setFromPlaceCode(vendorQuotedLine.getFromPlaceCode())
                    .setFromCountryCode(vendorQuotedLine.getFromCountryCode())
                    .setToCountryCode(vendorQuotedLine.getToCountryCode())
                    .setLogisticsCategoryId(vendorQuotedLine.getLogisticsCategoryId())
                    .setLegName(vendorQuotedLine.getLegName())
                    .setChargeUnitName(vendorQuotedLine.getChargeUnitName())
                    .setChargeMethodName(vendorQuotedLine.getChargeMethodName())
                    .setExpenseItemName(vendorQuotedLine.getExpenseItemName())
                    .setLogisticsCategoryCode(vendorQuotedLine.getLogisticsCategoryCode())
                    .setLogisticsCategoryName(vendorQuotedLine.getLogisticsCategoryName());

            //设置费用行的信息
            List<OrderLineFee> orderLineFeeList = new LinkedList<>();
            for(LgtVendorQuotedLine v : value){
                OrderLineFee orderLineFee = new OrderLineFee()
                        .setLeg(v.getLeg())
                        .setExpenseItem(v.getExpenseItem())
                        .setChargeMethod(v.getChargeMethod())
                        .setChargeUnit(v.getChargeUnit())
                        .setMaxCost(v.getMaxCost())
                        .setMinCost(v.getMinCost())
                        .setExpense(v.getExpense())
                        .setCurrency(v.getCurrency());
                orderLineFeeList.add(orderLineFee);
            }
            orderLine.setOrderLineFeeList(orderLineFeeList);
            orderLineList.add(orderLine);
        }

        //船期信息
        List<OrderLineShip> orderLineShipList = new LinkedList<>();
        List<LgtBidShipPeriod> bidShipPeriodList = vendorQuotedInfoVO.getBidShipPeriodList();
        for(LgtBidShipPeriod bidShipPeriod : bidShipPeriodList){
            OrderLineShip orderLineShip = new OrderLineShip();
            BeanCopyUtil.copyProperties(orderLineShip,bidShipPeriod);
            orderLineShip.setShipId(bidShipPeriod.getShipId())
                    .setFromPortId(bidShipPeriod.getFromPortId())
                    .setFromPortCode(bidShipPeriod.getFromPortCode())
                    .setFromPort(bidShipPeriod.getFromPort())
                    .setToPortId(bidShipPeriod.getToPortId())
                    .setToPortCode(bidShipPeriod.getToPortCode())
                    .setToPort(bidShipPeriod.getToPort())
                    .setWholeArk(bidShipPeriod.getWholeArk())
                    .setMon(bidShipPeriod.getMon())
                    .setTue(bidShipPeriod.getTue())
                    .setWed(bidShipPeriod.getWed())
                    .setThu(bidShipPeriod.getThu())
                    .setFri(bidShipPeriod.getFri())
                    .setSat(bidShipPeriod.getSat())
                    .setSun(bidShipPeriod.getSun())
                    .setTransitTime(bidShipPeriod.getTransitTime())
                    .setShipCompanyName(bidShipPeriod.getShipCompanyName())
                    .setTransferPort(bidShipPeriod.getTransferPort());
            orderLineShipList.add(orderLineShip);
        }

        //合同信息
        List<OrderLineContract> orderLineContractList = new LinkedList<>();
        List<LgtPayPlan> payPlanList = vendorVO.getPayPlanList();
        for(LgtPayPlan payPlan : payPlanList){
            OrderLineContract orderLineContract = new OrderLineContract()
                    .setContractId(payPlan.getContractHeadId())
                    .setContractName(payPlan.getContractName())
                    .setContractCode(payPlan.getContractCode())
                    .setPaymentMethod(payPlan.getPayMethod())
                    .setPaymentStage(payPlan.getPayExplain())
                    .setAccountDate(payPlan.getDateNum());
            orderLineContractList.add(orderLineContract);
        }

        return new LogisticsOrderDTO()
                .setOrderHead(orderHead)
                .setOrderLineContractList(orderLineContractList)
                .setOrderLineList(orderLineList)
                .setOrderLineShipList(orderLineShipList);

    }

    /**
     * 以【起运地+目的地】为key值进行分组
     * @param vendorQuotedLineList
     * @return
     */
    private Map<String,List<LgtVendorQuotedLine>> groupVendorQuotedLine(List<LgtVendorQuotedLine> vendorQuotedLineList){
        Map<String,List<LgtVendorQuotedLine>> map = new HashMap<>();
        for(LgtVendorQuotedLine vendorQuotedLine : vendorQuotedLineList){
            String key = new StringBuffer()
                    .append(vendorQuotedLine.getStartAddress())
                    .append(vendorQuotedLine.getEndAddress())
                    .toString();
            if(CollectionUtils.isEmpty(map.get(key))){
                map.put(key,new LinkedList<LgtVendorQuotedLine>(){{
                    add(vendorQuotedLine);
                }});
            }else{
                map.get(key).add(vendorQuotedLine);
            }
        }
        return map;
    }


    /**
     * 根据供应商编码分组 供应商报价信息
     * @param vendorQuotedInfoList
     * @return
     */
    private Map<String,List<LgtVendorQuotedInfoVO>> groupVendorQuotedByVendorCode(List<LgtVendorQuotedInfoVO> vendorQuotedInfoList){
        Map<String,List<LgtVendorQuotedInfoVO>> map = new HashMap<>();
        for(LgtVendorQuotedInfoVO lgtVendorQuotedInfoVO : vendorQuotedInfoList){
            String key = lgtVendorQuotedInfoVO.getVendorQuotedHead().getVendorCode();
            if(CollectionUtils.isEmpty(map.get(key))){
                map.put(key,new LinkedList<LgtVendorQuotedInfoVO>(){{
                    add(lgtVendorQuotedInfoVO);
                }});
            }else{
                map.get(key).add(lgtVendorQuotedInfoVO);
            }
        }
        return map;
    }


    /**
     * 物流订单推送TMS 状态回写
     * @param response
     * @param orderHeadId
     */
    private void updateAfterTmsPush(SoapResponse response, Long orderHeadId){
        String tmsInfo = null;
        if(Objects.nonNull(response)){
            tmsInfo = response.getSuccess();
        }
        if (Objects.isNull(response) ||
                Objects.isNull(response.getSuccess()) ||
                !"S".equals(response.getSuccess())
        ) {
            this.updateById(new OrderHead()
                    .setOrderHeadId(orderHeadId)
                    .setTmsInfo(tmsInfo) //todo 暂且设置为状态
                    .setTmsStatus(LogisticsOrderTmsStatus.FAIL_SYNC.getValue()));
        } else {
            this.updateById(new OrderHead()
                    .setOrderHeadId(orderHeadId)
                    .setTmsInfo(tmsInfo) //todo 暂且设置为状态
                    .setTmsStatus(LogisticsOrderTmsStatus.SUCCESS_SYNC.getValue()));
        }
    }



    /**
     * 保存推送日志
     * @param logisticsOrderRequest
     * @param response
     * @param errorMsg
     * @param logisticsOrderVO
     */
    private void saveInterfaceLog(LogisticsOrderRequest logisticsOrderRequest, SoapResponse response, Map<String,String> errorMsg, LogisticsOrderVO logisticsOrderVO){
        InterfaceLogDTO interfaceLogDTO = new InterfaceLogDTO();
        if(Objects.nonNull(logisticsOrderRequest)){
            try {
                interfaceLogDTO.setServiceInfo(JSON.toJSONString(logisticsOrderRequest));
            } catch (Exception e) {
                log.error(String.format("记录采购订单推送TMS接口日志报错，单号为[%s]",logisticsOrderVO.getOrderHead().getOrderHeadNum()));
            }
        }
        interfaceLogDTO.setCreationDateBegin(new Date());
        interfaceLogDTO.setServiceName("采购订单推送ERP接口开始");
        interfaceLogDTO.setServiceType("WEBSERVICE"); //请求方式
        interfaceLogDTO.setType("SEND"); // 发送方式
        interfaceLogDTO.setBillType("物流采购订单"); // 单据类型
        interfaceLogDTO.setBillId(logisticsOrderVO.getOrderHead().getOrderHeadNum());
        interfaceLogDTO.setTargetSys("TMS");
        interfaceLogDTO.setReturnInfo(JSON.toJSONString(response));
        interfaceLogDTO.setStatus("SUCCESS");

        if(Objects.nonNull(errorMsg)){
            interfaceLogDTO.setErrorInfo(JSON.toJSONString(errorMsg));
            interfaceLogDTO.setStatus("FAIL");
        }

        if(Objects.isNull(response) ||
                Objects.isNull(response.getSuccess()) ||
                !"S".equals(response.getSuccess())
        ){
            if(Objects.nonNull(response) &&
                    Objects.nonNull(response.getResponse()) &&
                    Objects.nonNull(response.getResponse().getResultInfo()) &&
                    StringUtils.isNotBlank(response.getResponse().getResultInfo().getRESPONSEMESSAGE())
            ){
                interfaceLogDTO.setErrorInfo(response.getResponse().getResultInfo().getRESPONSEMESSAGE());
            }
            interfaceLogDTO.setStatus("FAIL");
        }
        interfaceLogDTO.setCreationDateEnd(new Date());
        interfaceLogDTO.setFinishDate(new Date());
        try {
            apiClient.createInterfaceLog(interfaceLogDTO);
        } catch (Exception e){
            log.error("报错<物流采购订单推送TMS>日志报错{}" + e);
        }
    }

    private void checkIfSync(LogisticsOrderVO logisticsOrderVO){
        OrderHead orderHead = logisticsOrderVO.getOrderHead();
        if(!LogisticsOrderStatus.COMPLETED.getValue().equals(orderHead.getOrderStatus())){
            throw new BaseException(LocaleHandler.getLocaleMsg("订单状态为[完成]才可同步TMS"));
        }
        if(LogisticsOrderTmsStatus.SUCCESS_SYNC.getValue().equals(orderHead.getTmsStatus())){
            throw new BaseException(LocaleHandler.getLocaleMsg("订单已同步成功,请勿重新同步"));
        }
    }

    /**
     * 构建采购订单对接tms实体类
     * @param logisticsOrderVO
     * @return
     */
    private LogisticsOrderRequest buildRequest(LogisticsOrderVO logisticsOrderVO){
        OrderHead orderHead = logisticsOrderVO.getOrderHead();
        List<OrderLine> orderLineList = logisticsOrderVO.getOrderLineList();
        List<OrderLineShip> orderLineShipList = logisticsOrderVO.getOrderLineShipList();
        List<OrderLineContract> orderLineContractList = logisticsOrderVO.getOrderLineContractList();

        //获取其他模块的数据 LogisticsRequirementHead
        LogisticsRequirementHead logisticsRequirementHead = requirementHeadService.getById(orderHead.getRequirementHeadId());
        if(Objects.isNull(logisticsRequirementHead)){
            throw new BaseException(LocaleHandler.getLocaleMsg(String.format("orderHeadId=[%s],获取不到采购申请,requirementHeadId=[%s]",orderHead.getOrderHeadId(),orderHead.getRequirementHeadId())));
        }
        String settlementMethod = null;
        if(!CollectionUtils.isEmpty(orderLineContractList)){
            settlementMethod = orderLineContractList.stream().map(item -> item.getPaymentMethod()).collect(Collectors.joining("/"));
        }

        //设置采购订单头
        LogisticsOrderRequest.RequestInfo.LogisticsOrders.OrderHead tmsOrderHead = new LogisticsOrderRequest.RequestInfo.LogisticsOrders.OrderHead();
        tmsOrderHead.setOrderHeadNum(orderHead.getOrderHeadNum());
        tmsOrderHead.setVendorCode(orderHead.getVendorCode());
        tmsOrderHead.setPriceStartDate(String.valueOf(orderHead.getPriceStartDate()));
        tmsOrderHead.setPriceEndDate(String.valueOf(orderHead.getPriceEndDate()));
        tmsOrderHead.setSettlementMethod(settlementMethod);// 结算方式
        tmsOrderHead.setContractType(logisticsRequirementHead.getContractType());  //合同类型
        tmsOrderHead.setBusinessModeCode(orderHead.getBusinessModeCode());
        tmsOrderHead.setTransportModeCode(orderHead.getTransportModeCode());
        tmsOrderHead.setStatus("Y"); //有效性
        tmsOrderHead.setSourceSystem("SRM");

        //设置行程明细行
        List<LogisticsOrderRequest.RequestInfo.LogisticsOrders.OrderLine> tmsOrderLineList = new LinkedList<>();
        if(!CollectionUtils.isEmpty(orderLineList)){
            for(OrderLine orderLine : orderLineList){
                LogisticsOrderRequest.RequestInfo.LogisticsOrders.OrderLine tmsOrderLine = new LogisticsOrderRequest.RequestInfo.LogisticsOrders.OrderLine();
                tmsOrderLine.setOrderHeadNum(orderLine.getOrderHeadNum());
                tmsOrderLine.setRowLineNum(String.valueOf(orderLine.getRowNum()));
                tmsOrderLine.setFromCountry(orderLine.getFromCountry());
                tmsOrderLine.setFromProvince(orderLine.getFromProvince());
                tmsOrderLine.setFromCity(orderLine.getFromCity());
                tmsOrderLine.setFromCounty(orderLine.getFromCounty());
                tmsOrderLine.setFromPlace(orderLine.getFromPlace());
                tmsOrderLine.setToCountry(orderLine.getToCountry());
                tmsOrderLine.setToProvinceCode(orderLine.getToProvinceCode());
                tmsOrderLine.setToCountyCode(orderLine.getToCountyCode());
                tmsOrderLine.setToPlace(orderLine.getToPlace());
                tmsOrderLine.setVendorOrderNum("1"); //供方顺序
                tmsOrderLine.setTransportDistance(orderLine.getTransportDistance());
                tmsOrderLine.setTransportModeCode(orderHead.getTransportModeCode()); //运输方式
                tmsOrderLine.setServiceProjectCode(orderHead.getServiceProjectCode());  //项目
                tmsOrderLine.setStatus("Y");
                tmsOrderLine.setRealTime(null); //运输周期
                tmsOrderLine.setPriceStartDate(String.valueOf(orderHead.getPriceStartDate())); //生效期
                tmsOrderLine.setPriceEndDate(String.valueOf(orderHead.getPriceEndDate())); //失效期
                tmsOrderLine.setWholeArk(orderLine.getWholeArk());
                tmsOrderLine.setFromPort(orderLine.getFromPort());
                tmsOrderLine.setToPort(orderLine.getToPort());
                tmsOrderLine.setUnProjectFlag("N"); //非项目可使用
                tmsOrderLine.setImportExportMethod(orderLine.getImportExportMethod());
                tmsOrderLineList.add(tmsOrderLine);
            }
        }


        //设置船期信息
        List<LogisticsOrderRequest.RequestInfo.LogisticsOrders.OrderLineShip> tmsOrderLineShipList = new LinkedList<>();
        if(!CollectionUtils.isEmpty(orderLineShipList)){
            for(OrderLineShip orderLineShip : orderLineShipList){
                LogisticsOrderRequest.RequestInfo.LogisticsOrders.OrderLineShip tmsOrderLineShip = new LogisticsOrderRequest.RequestInfo.LogisticsOrders.OrderLineShip();
                tmsOrderLineShip.setOrderHeadNum(orderLineShip.getOrderHeadNum());
                tmsOrderLineShip.setRowNum(String.valueOf(orderLineShip.getRowNum()));
                tmsOrderLineShip.setFromPort(orderLineShip.getFromPortCode());
                tmsOrderLineShip.setToPort(orderLineShip.getToPortCode());
                tmsOrderLineShip.setWholeArk(orderLineShip.getWholeArk());
                tmsOrderLineShip.setMon(String.valueOf(orderLineShip.getMon()));
                tmsOrderLineShip.setTue(String.valueOf(orderLineShip.getTue()));
                tmsOrderLineShip.setWed(String.valueOf(orderLineShip.getWed()));
                tmsOrderLineShip.setThu(String.valueOf(orderLineShip.getThu()));
                tmsOrderLineShip.setFri(String.valueOf(orderLineShip.getFri()));
                tmsOrderLineShip.setSat(String.valueOf(orderLineShip.getSat()));
                tmsOrderLineShip.setSun(String.valueOf(orderLineShip.getSun()));
                tmsOrderLineShip.setTransitTime(String.valueOf(orderLineShip.getTransitTime()));
                tmsOrderLineShip.setCompany(orderLineShip.getShipCompanyName());
                tmsOrderLineShip.setTransferPort(orderLineShip.getTransferPort());
                tmsOrderLineShipList.add(tmsOrderLineShip);
            }
        }

        //设置费用项
        List<LogisticsOrderRequest.RequestInfo.LogisticsOrders.OrderLineFee> tmsOrderLineFeeList = new LinkedList<>();
        if(!CollectionUtils.isEmpty(orderLineList)){
            for(OrderLine orderLine : orderLineList){
                List<OrderLineFee> orderLineFeeList = orderLine.getOrderLineFeeList();
                if(!CollectionUtils.isEmpty(orderLineFeeList)){
                    for(OrderLineFee orderLineFee : orderLineFeeList){
                        LogisticsOrderRequest.RequestInfo.LogisticsOrders.OrderLineFee tmsOrderLineFee = new LogisticsOrderRequest.RequestInfo.LogisticsOrders.OrderLineFee();
                        tmsOrderLineFee.setOrderHeadNum(orderLineFee.getOrderHeadNum());
                        tmsOrderLineFee.setRowLineNum(String.valueOf(orderLine.getRowNum()));
                        tmsOrderLineFee.setRowNum(String.valueOf(orderLineFee.getRowNum()));
                        tmsOrderLineFee.setExpenseItem(orderLineFee.getExpenseItem());
                        tmsOrderLineFee.setChargeMethod(orderLineFee.getChargeMethod());
                        tmsOrderLineFee.setChargeUnit(orderLineFee.getChargeUnit());
                        tmsOrderLineFee.setMinCost(orderLineFee.getMinCost());
                        tmsOrderLineFee.setMaxCost(orderLineFee.getMaxCost());
                        tmsOrderLineFee.setExpense(String.valueOf(orderLineFee.getExpense()));
                        tmsOrderLineFee.setCurrency(orderLineFee.getCurrency());
                        tmsOrderLineFee.setIfBack(orderLine.getIfBack()); //是否往返
                        tmsOrderLineFee.setLeg(orderLineFee.getLeg());
                        tmsOrderLineFee.setTaxRate(String.valueOf(orderHead.getTaxRate()));
                        tmsOrderLineFeeList.add(tmsOrderLineFee);
                    }
                }
            }
        }

        //组合实体类
        LogisticsOrderRequest request = new LogisticsOrderRequest();
        LogisticsOrderRequest.RequestInfo requestInfo = new LogisticsOrderRequest.RequestInfo();
        LogisticsOrderRequest.RequestInfo.LogisticsOrders logisticsOrders = new LogisticsOrderRequest.RequestInfo.LogisticsOrders();
        logisticsOrders.setOrderHead(tmsOrderHead);
        logisticsOrders.setOrderLine(tmsOrderLineList);
        logisticsOrders.setOrderLineFee(tmsOrderLineFeeList);
        logisticsOrders.setOrderLineShip(tmsOrderLineShipList);
        List<LogisticsOrderRequest.RequestInfo.LogisticsOrders> logisticsOrdersList = new LinkedList<LogisticsOrderRequest.RequestInfo.LogisticsOrders>(){{
            add(logisticsOrders);
        }};
        requestInfo.setLogisticsOrders(logisticsOrdersList);
        request.setRequestInfo(requestInfo);
        return request;
    }


    /**
     * 检验是否可以暂存
     * @param orderDTO
     */
    private void checkIfTemporarySave(LogisticsOrderDTO orderDTO){

        Long orderHeadId = orderDTO.getOrderHead().getOrderHeadId();
        if(Objects.nonNull(orderHeadId)){
            OrderHead orderHead = this.getById(orderHeadId);
            if(Objects.isNull(orderHead)){
                throw new BaseException(LocaleHandler.getLocaleMsg(String.format("查询不到该采购订单,订单id[%s]",orderHead.getOrderHeadId())));
            }

            if(Objects.nonNull(orderHead) && !LogisticsOrderStatus.DRAFT.getValue().equals(orderHead.getOrderStatus())){
                throw new BaseException(LocaleHandler.getLocaleMsg("非拟定状态的订单不可暂存"));
            }

        }

        OrderHead orderHead = orderDTO.getOrderHead();
        if (!SourceFrom.MANUAL.name().equals(orderHead.getOrderSourceFrom())) {
            //必填校验
            if(Objects.isNull(orderHead.getRequirementHeadId())){
                throw new BaseException(LocaleHandler.getLocaleMsg("[requirementHeadId]必传"));
            }
            LogisticsRequirementHead requirementHead = requirementHeadService.getById(orderHead.getRequirementHeadId());
            if(Objects.isNull(requirementHead)){
                throw new BaseException(LocaleHandler.getLocaleMsg("查询不到该采购申请,requirementHeadId = [%s]",orderHead.getRequirementHeadId()));
            }
            //校验采购申请是否已经被使用
            checkIfRequirementUsed(requirementHead);
        }

    }
}
