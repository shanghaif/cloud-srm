package com.midea.cloud.srm.cm.accept.service.impl;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.coyote.Response;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.annotation.AuthData;
import com.midea.cloud.common.constants.SequenceCodeConstant;
import com.midea.cloud.common.enums.UserType;
import com.midea.cloud.common.enums.bpm.TempIdToModuleEnum;
import com.midea.cloud.common.enums.contract.AcceptanceStatus;
import com.midea.cloud.common.enums.rbac.MenuEnum;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.JsonUtil;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.cm.accept.mapper.AcceptOrderMapper;
import com.midea.cloud.srm.cm.accept.service.IAcceptDetailService;
import com.midea.cloud.srm.cm.accept.service.IAcceptOrderService;
import com.midea.cloud.srm.cm.accept.service.IDetailLineService;
import com.midea.cloud.srm.cm.accept.service.IToolEqpService;
import com.midea.cloud.srm.cm.accept.soap.assetsreimbursement.BusinessFormInfoService;
import com.midea.cloud.srm.cm.accept.soap.assetsreimbursement.ReceivableTypeInfoService;
import com.midea.cloud.srm.cm.accept.workflow.AcceptFlow;
import com.midea.cloud.srm.cm.contract.service.IContractHeadService;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.feign.rbac.RbacClient;
import com.midea.cloud.srm.feign.supcooperate.SupcooperateClient;
import com.midea.cloud.srm.feign.workflow.FlowClient;
import com.midea.cloud.srm.model.base.organization.entity.Organization;
import com.midea.cloud.srm.model.base.soap.DataSourceEnum;
import com.midea.cloud.srm.model.cm.accept.dto.AcceptDTO;
import com.midea.cloud.srm.model.cm.accept.dto.AcceptDetailDTO;
import com.midea.cloud.srm.model.cm.accept.dto.AcceptNumVO;
import com.midea.cloud.srm.model.cm.accept.dto.AcceptOrderDTO;
import com.midea.cloud.srm.model.cm.accept.entity.AcceptDetail;
import com.midea.cloud.srm.model.cm.accept.entity.AcceptOrder;
import com.midea.cloud.srm.model.cm.accept.entity.ToolEqp;
import com.midea.cloud.srm.model.cm.accept.soap.BusinessFormRequest;
import com.midea.cloud.srm.model.cm.accept.soap.BusinessFormResponse;
import com.midea.cloud.srm.model.cm.contract.entity.ContractHead;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.rbac.user.entity.User;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.OrderDetailDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.OrderRequestDTO;
import com.midea.cloud.srm.model.workflow.entity.SrmFlowBusWorkflow;

/**
 * <pre>
 * 功能名称
 * </pre>
 *
 * @author zhi1772778785@163.com
 * @version 1.00.00
 *
 * <pre>
 * 修改记录
 * 修改后版本:
 * 修改人:
 * 修改日期: 2020/8/7/007 16:08
 * 修改内容:
 * </pre>
 */
@Service
public class AcceptOrderServiceImpl extends ServiceImpl<AcceptOrderMapper, AcceptOrder> implements IAcceptOrderService {
    @Value("${ReceivableTypeInfo.showAcceptOrderUrl}")
    private String showAcceptOrderUrl;

    @Autowired
    IAcceptDetailService iAcceptDetailService;

    @Autowired
    BaseClient baseClient;

    @Autowired
    IContractHeadService iContractHeadService;

    @Autowired
    RbacClient rbacClient;

    @Autowired
    IToolEqpService iToolEqpService;

    @Autowired
    FileCenterClient fileCenterClient;

    @Autowired
    IDetailLineService iDetailLineService;
    @Autowired
    AcceptOrderMapper acceptOrderMapper;
    @Autowired
    private SupcooperateClient supcooperateClient;

    @Autowired
    private AcceptFlow acceptFlow;
    
    @Resource
    private FlowClient flowClient;

    /**
     * 暂存接口
     *
     * @param acceptDTO
     */
    @Override
    @Transactional
    public Long buyerSaveTemporary(AcceptDTO acceptDTO) {
        AcceptOrder acceptOrder = acceptDTO.getAcceptOrder();
        List<AcceptDetailDTO> acceptDetails = acceptDTO.getAcceptDetails();
        List<ToolEqp> toolEqp = acceptDTO.getToolEqp();
        List<Fileupload> assetFile = acceptDTO.getAssetFile();
        List<Fileupload> techFile = acceptDTO.getTechFile();

        //acceptOrder.setAcceptStatus(AcceptanceStatus.DRAFT.name());
         return saveOrUpdateAcceptDTO(acceptOrder, acceptDetails, toolEqp, techFile, assetFile);
    }

    /**
     * 分页条件查询
     *
     * @param acceptOrderDTO
     * @return
     */
    @Override
    public PageInfo<AcceptOrder> listPageByParm(AcceptOrderDTO acceptOrderDTO) {
        PageUtil.startPage(acceptOrderDTO.getPageNum(), acceptOrderDTO.getPageSize());
        AcceptOrder acceptOrder = new AcceptOrder();
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        //用户信息验证
        /*if (UserType.VENDOR.name().equals(loginAppUser.getUserType()) && loginAppUser.getCompanyId() != null) {
            acceptOrder.setVendorId(loginAppUser.getCompanyId());
        }
        if (StringUtils.isNotBlank(acceptOrderDTO.getAcceptStatus())) {
            acceptOrder.setAcceptStatus(acceptOrderDTO.getAcceptStatus());
        }*/
        //当验收类型不存在的时候默认展示验收单申请
        List<String> acceptTypeList = new ArrayList<>();
        QueryWrapper<AcceptOrder> queryWrapper = new QueryWrapper<>(acceptOrder);
        //如果是供应商，就是供应商协同验收单，只能看到自己的
        if (UserType.VENDOR.name().equals(loginAppUser.getUserType())) {
            // 供应商id为空时直接返回空
            if(ObjectUtils.isEmpty(loginAppUser.getCompanyId())){
                return new PageInfo<>();
            }
            queryWrapper.eq("VENDOR_ID", loginAppUser.getUserId());
        }
        //验收单号查询条件
        queryWrapper.like(StringUtils.isNotBlank(acceptOrderDTO.getContractNo()), "ACCEPT_NUMBER", acceptOrderDTO.getAcceptNumber());
        //业务实体查询条件
        if (acceptOrderDTO.getCeeaOrgId() != null) {
            queryWrapper.eq("CEEA_ORG_ID", acceptOrderDTO.getCeeaOrgId());
        }
        //验收状态
        queryWrapper.eq(StringUtils.isNotEmpty(acceptOrderDTO.getCeeaAcceptType()), "ACCEPT_STATUS", acceptOrderDTO.getCeeaAcceptType());
        if (StringUtils.isEmpty(acceptOrderDTO.getCeeaAcceptType())) {
            acceptTypeList = Arrays.asList(AcceptanceStatus.getString());
            if (StringUtils.isNotEmpty(acceptOrderDTO.getRoofScheme())) {
                queryWrapper.in("ACCEPT_STATUS", acceptTypeList);
            } else {
                queryWrapper.notIn("ACCEPT_STATUS", acceptTypeList);
            }
        }
        //供应商编号查询条件
        queryWrapper.like(StringUtils.isNotBlank(acceptOrderDTO.getVendorCode()), "VENDOR_CODE", acceptOrderDTO.getVendorCode());
        //合同编号查询条件
        queryWrapper.like(StringUtils.isNotBlank(acceptOrderDTO.getContractNo()), "CONTRACT_NO", acceptOrderDTO.getContractNo());
        //验收日期时间段查询条件
        queryWrapper.between(acceptOrderDTO.getStartAcceptDate() != null
                && acceptOrderDTO.getEndAcceptDate() != null, "ACCEPT_DATE", acceptOrderDTO.getStartAcceptDate(), acceptOrderDTO.getEndAcceptDate());
        if (acceptOrderDTO.getStartAcceptDate() == null && acceptOrderDTO.getEndAcceptDate() != null) {
            queryWrapper.le("ACCEPT_DATE", acceptOrderDTO.getEndAcceptDate());
        }
        if (acceptOrderDTO.getStartAcceptDate() != null && acceptOrderDTO.getEndAcceptDate() == null) {
            queryWrapper.ge("ACCEPT_DATE", acceptOrderDTO.getStartAcceptDate());
        }
        //实际完成日期时间段查询条件
        queryWrapper.between(acceptOrderDTO.getStartOverDate() != null
                && acceptOrderDTO.getEndOverDate() != null, "OVER_DATE", acceptOrderDTO.getStartOverDate(), acceptOrderDTO.getEndOverDate());
        if (acceptOrderDTO.getStartOverDate() == null && acceptOrderDTO.getEndOverDate() != null) {
            queryWrapper.le("OVER_DATE", acceptOrderDTO.getEndOverDate());
        }
        if (acceptOrderDTO.getStartOverDate() != null && acceptOrderDTO.getEndOverDate() == null) {
            queryWrapper.ge("OVER_DATE", acceptOrderDTO.getStartOverDate());
        }
        //供应商名称查询条件
        if (UserType.BUYER.name().equals(loginAppUser.getUserType())) {
            queryWrapper.like(StringUtils.isNotBlank(acceptOrderDTO.getVendorName()), "VENDOR_NAME", acceptOrderDTO.getVendorName());
        }
        queryWrapper.orderByDesc("LAST_UPDATE_DATE");
        return new PageInfo<>(this.list(queryWrapper));
    }

    /**
     * 供应商分页条件查询
     *
     * @param acceptOrderDTO
     * @return
     */
    @Override
    public List<AcceptOrder> listByParm(AcceptOrderDTO acceptOrderDTO) {
        AcceptOrder acceptOrder = new AcceptOrder();
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();

        if (ObjectUtils.isEmpty(loginAppUser)){
            return new ArrayList<>();
        }
        //用户信息验证
        if (UserType.VENDOR.name().equals(loginAppUser.getUserType())) {
            acceptOrder.setVendorId(loginAppUser.getCompanyId());
        }
        if (StringUtils.isNotBlank(acceptOrderDTO.getAcceptStatus())) {
            acceptOrder.setAcceptStatus(acceptOrderDTO.getAcceptStatus());
        }
        //当验收类型不存在的时候默认展示验收单申请
        List<String> acceptTypeList = new ArrayList<>();
        QueryWrapper<AcceptOrderDTO> wrapper = new QueryWrapper<>();
        //验收单号查询条件
        wrapper.like(StringUtils.isNotBlank(acceptOrderDTO.getContractNo()), "ACCEPT_NUMBER", acceptOrderDTO.getAcceptNumber());
        //业务实体查询条件
        wrapper.eq(acceptOrderDTO.getCeeaOrgId() != null,"CEEA_ORG_ID", acceptOrderDTO.getCeeaOrgId());
        //验收状态
        wrapper.eq(StringUtils.isNotEmpty(acceptOrderDTO.getAcceptStatus()), "ACCEPT_STATUS", acceptOrderDTO.getAcceptStatus());
        if (StringUtils.isEmpty(acceptOrderDTO.getAcceptStatus())) {
            //获取供方验收合同的所有状态
            acceptTypeList = Arrays.asList(AcceptanceStatus.getString());
            if (StringUtils.isNotEmpty(acceptOrderDTO.getRoofScheme())) {
                wrapper.notIn("ACCEPT_STATUS", acceptTypeList);
            } else {
                wrapper.in("ACCEPT_STATUS", acceptTypeList);
            }
        }
        //供应商查看的是申请时间，采购商看的是提交时间
        String dateVendor = "a.ACCEPT_DATE";
        if (UserType.VENDOR.name().equals(loginAppUser.getUserType())) {
            //供应商筛选
            //如果是供应商，就是供应商协同验收单，只能看到自己的
            wrapper.eq("VENDOR_ID", loginAppUser.getCompanyId());
            //wrapper.eq(loginAppUser.getUserId() != null, "", loginAppUser.getUserId());
            dateVendor = "a.CEEA_APPLICATION_DATE";
        }
        //资产类别
        wrapper.eq(StringUtils.isNotEmpty(acceptOrderDTO.getCeeaAssetType()), "CEEA_ASSET_TYPE", acceptOrderDTO.getCeeaAssetType());
        //验收申请单号条件查询
        wrapper.like(StringUtils.isNotEmpty(acceptOrderDTO.getCeeaAcceptApplicationNum()), "a.CEEA_ACCEPT_APPLICATION_NUM", acceptOrderDTO.getCeeaAcceptApplicationNum());
        //申请时间
        //验收日期时间段查询条件
        wrapper.ge(acceptOrderDTO.getStartAcceptDate() != null, dateVendor, acceptOrderDTO.getStartAcceptDate());
        wrapper.le(acceptOrderDTO.getEndAcceptDate() != null, dateVendor, acceptOrderDTO.getEndAcceptDate());
        //验收单号查询条件
        wrapper.like(StringUtils.isNotEmpty(acceptOrderDTO.getAcceptNumber()), "ACCEPT_NUMBER", acceptOrderDTO.getAcceptNumber());
        //业务实体查询条件
        wrapper.in(CollectionUtils.isNotEmpty(acceptOrderDTO.getOrgIdList()), "CEEA_ORG_ID", acceptOrderDTO.getOrgIdList());
        //采购订单号模糊查询
        wrapper.like(StringUtils.isNotEmpty(acceptOrderDTO.getOrderNumber()), "b.ORDER_NUMBER", acceptOrderDTO.getOrderNumber());
        //供应商编号查询条件
        wrapper.like(StringUtils.isNotEmpty(acceptOrderDTO.getVendorCode()), "VENDOR_CODE", acceptOrderDTO.getVendorCode());
        //合同编号查询条件
        wrapper.like(StringUtils.isNotEmpty(acceptOrderDTO.getContractNo()), "CONTRACT_NO", acceptOrderDTO.getContractNo());
        //实际完成日期时间段查询条件
        wrapper.between(acceptOrderDTO.getStartOverDate() != null
                && acceptOrderDTO.getEndOverDate() != null, "OVER_DATE", acceptOrderDTO.getStartOverDate(), acceptOrderDTO.getEndOverDate());
        if (acceptOrderDTO.getStartOverDate() == null && acceptOrderDTO.getEndOverDate() != null) {
            wrapper.le("OVER_DATE", acceptOrderDTO.getEndOverDate());
        }
        if (acceptOrderDTO.getStartOverDate() != null && acceptOrderDTO.getEndOverDate() == null) {
            wrapper.ge("OVER_DATE", acceptOrderDTO.getStartOverDate());
        }
        //供应商名称查询条件
        if (UserType.BUYER.name().equals(loginAppUser.getUserType())) {
            wrapper.like(StringUtils.isNotBlank(acceptOrderDTO.getVendorName()), "VENDOR_NAME", acceptOrderDTO.getVendorName());
        }
        wrapper.groupBy("ACCEPT_ORDER_ID");
        wrapper.orderByDesc("LAST_UPDATE_DATE");

        List<AcceptOrder> result = new ArrayList<>();
        //验收单申请列表 or 验收单列表
        if("SUBMIT".equals(acceptOrderDTO.getAcceptStatus())){
            result = listInspectionBillForApply(wrapper);
        }else{
            result = listInspectionBill(wrapper);
        }
        return result;
    }

    @AuthData(module = MenuEnum.INSPECTION_APPLY_BILL)
    private List<AcceptOrder> listInspectionBillForApply(QueryWrapper<AcceptOrderDTO> wrapper ){
        return acceptOrderMapper.listByParm(wrapper);
    }

    @AuthData(module = MenuEnum.INSPECTION_BILL_LIST)
    private List<AcceptOrder> listInspectionBill(QueryWrapper<AcceptOrderDTO> wrapper ){
        return acceptOrderMapper.listByParm(wrapper);
    }

    /**
     * 获取单个验收单的数据
     *
     * @param acceptOrderId
     * @return
     */
    @Override
    public AcceptDTO getAcceptDTO(Long acceptOrderId) {
        AcceptOrder acceptOrder = this.getById(acceptOrderId);
        if (null==acceptOrder){
            return null;
        }
        AcceptDTO acceptDTO = new AcceptDTO().setAcceptOrder(acceptOrder);
        //获取验收单明细列表
        QueryWrapper<AcceptDetail> acceptDetailQueryWrapper = new QueryWrapper<>(new AcceptDetail().setAcceptOrderId(acceptOrderId));
        acceptDetailQueryWrapper.groupBy("CEEA_ORDER_DETAIL_ID");
        List<AcceptDetail> acceptDetails = iAcceptDetailService.list(acceptDetailQueryWrapper);
        if (CollectionUtils.isNotEmpty(acceptDetails)) {
            Map<Long, AcceptDetail> acceptDetailMap = acceptDetails.stream().collect(Collectors.toMap(AcceptDetail::getOrderDetailId, Function.identity()));
            List<Long> collect = acceptDetails.stream().map(AcceptDetail::getOrderDetailId).collect(Collectors.toList());
            //获取对应的采购明细
            List<OrderDetailDTO> OrderDetailDTOList = supcooperateClient.OrderDetailListCopy(new OrderRequestDTO().setOrderDetailIdList(collect).setAssetType(acceptOrder.getCeeaAssetType()).setViewType("Y"));
            Map<Long, OrderDetailDTO> orderDetailDTOMap = OrderDetailDTOList.stream().collect(Collectors.toMap(OrderDetailDTO::getOrderDetailId, Function.identity()));
            ArrayList<AcceptDetailDTO> acceptDetailDTOS = new ArrayList<>();
            for (Long ODid : orderDetailDTOMap.keySet()) {
                AcceptDetail acceptDetail = acceptDetailMap.get(ODid);
                OrderDetailDTO orderDetailDTO = orderDetailDTOMap.get(ODid);
                acceptDetailDTOS.add(this.mergeAcceptDetailDTO(acceptDetail, orderDetailDTO));
            }
            acceptDTO.setAcceptDetails(acceptDetailDTOS);
        }

        //获取附带工具设备列表信息
        if (acceptOrder.getCeeaToolEquipment() != null && acceptOrder.getCeeaToolEquipment() != 0) {
            List<ToolEqp> toolEqpList = iToolEqpService.list(new QueryWrapper<>(new ToolEqp().setAcceptOrderId(acceptOrderId)));
            acceptDTO.setToolEqp(toolEqpList);
        }
        //获取附随技术文件的文件列表
        if (acceptOrder.getCeeaTechnicalDocuments() != null && acceptOrder.getCeeaTechnicalDocuments() != 0) {
            PageInfo<Fileupload> acceptTECH = fileCenterClient.listPage(new Fileupload().setBusinessId(acceptOrderId).setFileFunction("acceptTECH"), "");
            acceptDTO.setTechFile(acceptTECH.getList());
        }
        //获取附件信息列表
            PageInfo<Fileupload> acceptASSET = fileCenterClient.listPage(new Fileupload().setBusinessId(acceptOrderId).setFileFunction("acceptASSET"), "");
        if (acceptASSET!=null){
            acceptDTO.setAssetFile(acceptASSET.getList());
        }
        return acceptDTO;
    }

    public AcceptDetailDTO mergeAcceptDetailDTO(AcceptDetail acceptDetail, OrderDetailDTO orderDetailDTO) {
        AcceptDetailDTO acceptDetailDTO = new AcceptDetailDTO();
        BeanUtils.copyProperties(acceptDetail, acceptDetailDTO);
        BeanUtils.copyProperties(orderDetailDTO, acceptDetailDTO);
        acceptDetailDTO.setWaitAcceptQuantity(orderDetailDTO.getWaitAcceptQuantity());
        return acceptDetailDTO;
    }


    /**
     * 提交->
     * 1.回写订单明细的已验收数量
     *
     * @param acceptDTO
     */
    @Override
    @Transactional
    public void buyerSubmit(AcceptDTO acceptDTO) {
        AcceptOrder acceptOrder = acceptDTO.getAcceptOrder();
        List<AcceptDetailDTO> acceptDetails = acceptDTO.getAcceptDetails();
        List<ToolEqp> toolEqp = acceptDTO.getToolEqp();
        List<Fileupload> assetFile = acceptDTO.getAssetFile();
        List<Fileupload> techFile = acceptDTO.getTechFile();

        saveOrUpdateAcceptDTO(acceptOrder, acceptDetails, toolEqp, techFile, assetFile);


        /* Begin by chenwt24@meicloud.com   2020-10-16 */

        String formId = null;
        try {
            formId = String.valueOf(acceptDTO.getAcceptOrder().getAcceptOrderId());
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override
                public void afterCommit() {
                	flowClient.submitFlow(acceptDTO.getAcceptOrder().getAcceptOrderId(), TempIdToModuleEnum.ACCEPT.getValue(), "throughWorkflowService");
                }
            });
        } catch (Exception e) {
            throw new BaseException(e.getMessage());
        }
        if(StringUtils.isEmpty(formId)){
            throw new BaseException(LocaleHandler.getLocaleMsg("提交OA审批失败"));

        }

        /* End by chenwt24@meicloud.com     2020-10-16 */
        //提交
        this.buyerSubmit(acceptOrder.getAcceptOrderId());
    }

    /**
     * 采购商验收申请提交
     *
     * @param acceptOrderId
     */
    @Override
    @Transactional
    public void buyerSubmit(Long acceptOrderId) {
        AcceptOrder acceptOrder = this.getById(acceptOrderId);
        //修改状态
        acceptOrder.setAcceptStatus(AcceptanceStatus.UNDER_REVIEW.name());
        this.updateById(acceptOrder);
        //获取需要回写的验收明细
        QueryWrapper<AcceptDetail> acceptDetailQueryWrapper = new QueryWrapper<>(new AcceptDetail().setAcceptOrderId(acceptOrderId));
        acceptDetailQueryWrapper.groupBy("CEEA_ORDER_DETAIL_ID");
        List<AcceptDetail> acceptDetailList = iAcceptDetailService.list(acceptDetailQueryWrapper);
        Map<Long, BigDecimal> collect = acceptDetailList.stream().collect(Collectors.toMap(AcceptDetail::getOrderDetailId, AcceptDetail::getAcceptQuantity));
        Map<Long, BigDecimal> collects = acceptDetailList.stream().filter(a -> a.getCeeaDamageQuantity() != null).collect(Collectors.toMap(AcceptDetail::getOrderDetailId, AcceptDetail::getCeeaDamageQuantity));
        List<AcceptNumVO> acceptNumVOList = new ArrayList<>();
        for (Long id : collect.keySet()) {
            acceptNumVOList.add(new AcceptNumVO().setOrderDetailId(id).setCeeaDamageQuantity(Optional.ofNullable(collects.get(id)).orElse(new BigDecimal(0))).setAcceptQuantity(collect.get(id)));
        }
        //回写订单明细的验收单数量
        supcooperateClient.acceptBuyerSubmit(acceptNumVOList);
    }

    /**
     * 撤回取消
     *
     * @param acceptOrderId
     */
    @Override
    public void buyerWithdraw(Long acceptOrderId) {
        AcceptOrder acceptOrder = this.getById(acceptOrderId);
        //修改状态
        acceptOrder.setAcceptStatus(AcceptanceStatus.WITHDRAW.name());
        this.updateById(acceptOrder);
        //获取需要回写的验收明细
        QueryWrapper<AcceptDetail> acceptDetailQueryWrapper = new QueryWrapper<>(new AcceptDetail().setAcceptOrderId(acceptOrderId));
        acceptDetailQueryWrapper.groupBy("CEEA_ORDER_DETAIL_ID");
        List<AcceptDetail> acceptDetailList = iAcceptDetailService.list(acceptDetailQueryWrapper);
        Map<Long, BigDecimal> collect = acceptDetailList.stream().collect(Collectors.toMap(AcceptDetail::getOrderDetailId, AcceptDetail::getAcceptQuantity));
        Map<Long, BigDecimal> collects = acceptDetailList.stream().filter(a -> a.getCeeaDamageQuantity() != null).collect(Collectors.toMap(AcceptDetail::getOrderDetailId, AcceptDetail::getCeeaDamageQuantity));
        List<AcceptNumVO> acceptNumVOList = new ArrayList<>();
        for (Long id : collect.keySet()) {
            acceptNumVOList.add(new AcceptNumVO().setOrderDetailId(id).setCeeaDamageQuantity(Optional.ofNullable(collects.get(id)).orElse(new BigDecimal(0)).negate()).setAcceptQuantity(collect.get(id).negate()));
        }
        //回写订单明细的验收单数量
        supcooperateClient.acceptBuyerSubmit(acceptNumVOList);
    }

    @Override
    @Transactional
    public void deleteAcceptDTO(Long acceptOrderId) {
        AcceptOrder byId = this.getById(acceptOrderId);
        Assert.notNull(byId,"需要删除的对象不存在。");
        String acceptStatus = byId.getAcceptStatus();
        if(AcceptanceStatus.DRAFT.toString().equals(acceptStatus)){
            this.removeById(acceptOrderId);
            iAcceptDetailService.remove(new QueryWrapper<>(new AcceptDetail()
                    .setAcceptOrderId(acceptOrderId)));
        }else {
            Assert.isTrue(false,"删除的对象状态应该为拟定状态");
        }

    }



    /**
     * 废弃订单
     * @param acceptOrderId
     */
    @GetMapping("/abandon")
    public void abandon(Long acceptOrderId) {
        AcceptDTO acceptDTO = this.getAcceptDTO(acceptOrderId);
        AcceptOrder acceptOrder = acceptDTO.getAcceptOrder();
        Assert.notNull(ObjectUtils.isEmpty(acceptOrder),"找不到废弃的订单");
        String acceptStatus = acceptOrder.getAcceptStatus();
        Assert.isTrue(AcceptanceStatus.WITHDRAW.toString().equals(acceptStatus)||AcceptanceStatus.REJECTED.toString().equals(acceptStatus),"只有已驳回，已撤回的订单才可以撤回。");
        acceptOrder.setAcceptStatus(AcceptanceStatus.ABANDONED.toString());
        this.updateById(acceptOrder);
        SrmFlowBusWorkflow srmworkflowForm = baseClient.getSrmFlowBusWorkflow(acceptOrderId);
        if (srmworkflowForm!=null) {
            try {
                acceptDTO.setProcessType("N");
                acceptFlow.submitAcceptFlow(acceptDTO);
            }catch (Exception e){
                Assert.isTrue(false, "废弃同步审批流失败。");
            }
        }
    }

    /**
     * 审核通过/contract/contractHead/listPageByParam
     *
     * @param acceptOrderDTO
     */
    @Override
    @Transactional
    public void vendorPass(AcceptOrderDTO acceptOrderDTO) {
        acceptOrderDTO.setAcceptStatus(AcceptanceStatus.APPROVED.name()).setOverDate(LocalDate.now());
        updateAcceptOrderDTO(acceptOrderDTO);
        //kuangzm 屏蔽验收单ERP接口调用
//        AcceptOrder byId = this.getById(acceptOrderDTO.getAcceptOrderId());
//        BeanUtils.copyProperties(byId, acceptOrderDTO);
//        //获取erp业务实体id----》ou编码
//        Organization organization = baseClient.get(acceptOrderDTO.getCeeaOrgId());
//        Assert.isTrue(StringUtils.isNotEmpty(organization.getCeeaBusinessCode()), "找不到系统对接的ou编码，请检查业务实体信息后再提交。");
//        acceptOrderDTO.setCeeaOrgCode(organization.getCeeaBusinessCode());
//        this.getSubmit(acceptOrderDTO);
    }

    /**
     * 验收单驳回
     * @param acceptOrderDTO
     */
    @Override
    @Transactional
    public void vendorReject(AcceptOrderDTO acceptOrderDTO) {
        //获取需要回写的验收明细
        Assert.hasText(acceptOrderDTO.getRejectReason(), LocaleHandler.getLocaleMsg("驳回原因不能为空"));
        Long acceptOrderId = acceptOrderDTO.getAcceptOrderId();
        AcceptOrder acceptOrder = this.getById(acceptOrderId);
        //修改状态
        acceptOrder.setAcceptStatus(AcceptanceStatus.REJECTED.name());
        acceptOrder.setRejectReason(acceptOrderDTO.getRejectReason());
        acceptOrder.setCeeaDraftsmanOpinion(acceptOrderDTO.getCeeaDraftsmanOpinion());
        this.updateById(acceptOrder);

        //获取需要回写的验收明细
        QueryWrapper<AcceptDetail> acceptDetailQueryWrapper = new QueryWrapper<>(new AcceptDetail().setAcceptOrderId(acceptOrderId));
        acceptDetailQueryWrapper.groupBy("CEEA_ORDER_DETAIL_ID");
        List<AcceptDetail> acceptDetailList = iAcceptDetailService.list(acceptDetailQueryWrapper);
        Map<Long, BigDecimal> collect = acceptDetailList.stream().collect(Collectors.toMap(AcceptDetail::getOrderDetailId, AcceptDetail::getAcceptQuantity));
        Map<Long, BigDecimal> collects = acceptDetailList.stream().filter(a -> a.getCeeaDamageQuantity() != null).collect(Collectors.toMap(AcceptDetail::getOrderDetailId, AcceptDetail::getCeeaDamageQuantity));
        List<AcceptNumVO> acceptNumVOList = new ArrayList<>();
        for (Long id : collect.keySet()) {
            acceptNumVOList.add(new AcceptNumVO().setOrderDetailId(id).setCeeaDamageQuantity(Optional.ofNullable(collects.get(id)).orElse(new BigDecimal(0)).negate()).setAcceptQuantity(collect.get(id).negate()));
        }
        //回写订单明细的验收单数量
        supcooperateClient.acceptBuyerSubmit(acceptNumVOList);
    }

    private void updateAcceptOrderDTO(AcceptOrderDTO acceptOrderDTO) {
        AcceptOrder acceptOrder = new AcceptOrder();
        BeanUtils.copyProperties(acceptOrderDTO, acceptOrder);
        this.updateById(acceptOrder);
    }

    @Override   //TODO
    public PageInfo<User> listOrgUserByContract(Long contractHeadId) {
        ContractHead contractHead = iContractHeadService.getById(contractHeadId);
        if (contractHead != null) {
        }
        return null;
    }

    private void updateAcceptDTO(AcceptOrder acceptOrder, List<AcceptDetail> acceptDetails) {
        if (acceptOrder != null) {
            if (acceptOrder.getAcceptOrderId() != null) {
                this.updateById(acceptOrder);
                if (CollectionUtils.isNotEmpty(acceptDetails)) {
                    acceptDetails.forEach(acceptDetail -> {
                        if (acceptDetail.getAcceptDetailId() != null) {
                            iAcceptDetailService.updateById(acceptDetail);
                        }
                    });
                }
            }
        }
    }

    /**
     * 验收单新增修改具体方法
     *
     * @param acceptOrder
     * @param acceptDetails
     * @param toolEqps
     * @param techFile
     * @param assetFile
     */
    @Transactional
    public Long saveOrUpdateAcceptDTO(AcceptOrder acceptOrder, List<AcceptDetailDTO> acceptDetails, List<ToolEqp> toolEqps, List<Fileupload> techFile, List<Fileupload> assetFile) {
        Long acceptOrderId = null;
        if (acceptOrder != null) {
            if (acceptOrder.getAcceptOrderId() == null) {
                LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
                long id = IdGenrator.generate();
                //回写id给前端
                acceptOrderId=id;
                acceptOrder.setCeeaEmpNo(loginAppUser.getCeeaEmpNo());
                acceptOrder.setAcceptOrderId(id).setAcceptNumber(baseClient.seqGen(SequenceCodeConstant.SEQ_CONTRACT_ACCEPT_NUMBER));
                //申请单号
                acceptOrder.setCeeaAcceptApplicationNum(baseClient.seqGen(SequenceCodeConstant.SEQ_CONTRACT_ACCEPT_APPLICTION_CODE));
                this.save(acceptOrder);
            } else {
                acceptOrderId=acceptOrder.getAcceptOrderId();
                this.updateById(acceptOrder);
            }
            //添加保存绑定文件信息
            if (acceptOrder.getCeeaTechnicalDocuments() != null && acceptOrder.getCeeaTechnicalDocuments() != 0 && !CollectionUtils.isEmpty(techFile)) {
                fileCenterClient.bindingFileupload(techFile, acceptOrder.getAcceptOrderId());
            }
            if (CollectionUtils.isNotEmpty(assetFile)) {
                fileCenterClient.bindingFileupload(assetFile, acceptOrder.getAcceptOrderId());
            }

            //添加验收明细
            if (CollectionUtils.isNotEmpty(acceptDetails)) {
                acceptDetails.forEach(acceptDetail -> {
                    if (acceptDetail.getAcceptDetailId() == null) {
                        acceptDetail.setAcceptDetailId(IdGenrator.generate())
                                .setAcceptOrderId(acceptOrder.getAcceptOrderId());
                        iAcceptDetailService.save(acceptDetail);
                    } else {
                        iAcceptDetailService.updateById(acceptDetail);
                    }
                });
                Assert.notNull(acceptOrderId,"保存时获取id失败。");
                //删除处理当前列表里的数据
                List<Long> orderList = acceptDetails.stream().map(AcceptDetail::getAcceptDetailId).collect(Collectors.toList());
                QueryWrapper<AcceptDetail> acceptDetailQueryWrapper = new QueryWrapper<>();
                acceptDetailQueryWrapper.eq("ACCEPT_ORDER_ID",acceptOrderId);
                acceptDetailQueryWrapper.notIn("ACCEPT_DETAIL_ID",orderList);
                iAcceptDetailService.remove(acceptDetailQueryWrapper);
            }
            //添加附带工具
            if (CollectionUtils.isNotEmpty(toolEqps)) {
                toolEqps.forEach(toolEqp -> {
                    if (toolEqp.getToolEqpId() == null) {
                        toolEqp.setToolEqpId(IdGenrator.generate())
                                .setAcceptOrderId(acceptOrder.getAcceptOrderId());
                        iToolEqpService.save(toolEqp);
                    } else {
                        iToolEqpService.updateById(toolEqp);
                    }
                });
            }
        }
        Assert.notNull(acceptOrderId,"保存时获取id失败。");
        return acceptOrderId;
    }

    public void getSubmit(AcceptOrderDTO acceptOrderDTO) {
        Response response = new Response();
        try {
            ReceivableTypeInfoService locator = new ReceivableTypeInfoService();
            BusinessFormInfoService service = locator.getBusinessFormInfoServiceImplPort();
            BusinessFormRequest request = new BusinessFormRequest();

            BusinessFormRequest.BUSINESSFORMS.BUSINESSFORM businessform = new BusinessFormRequest.BUSINESSFORMS.BUSINESSFORM();
            LocalDate localDate = acceptOrderDTO.getCreationDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            //保存对应的值
            businessform.setSourceSystem(DataSourceEnum.NSRM_SYS.getKey());
            businessform.setOaNo(acceptOrderDTO.getAcceptNumber());
            businessform.setCreatedate(localDate.toString());
            Assert.notNull(acceptOrderDTO.getCeeaEmpNo(), "员工工号不能为空！请稍后重试");
            businessform.setEmployeeId(acceptOrderDTO.getCeeaEmpNo());
            //传OU编码
            businessform.setLeCode(acceptOrderDTO.getCeeaOrgCode());
            businessform.setOperationTypeName("337");
            businessform.setPaperAccessories("N");
            businessform.setBoeAbstract(acceptOrderDTO.getRemark());
            businessform.setLinkAddress(showAcceptOrderUrl+acceptOrderDTO.getAcceptOrderId());

            BusinessFormRequest.BUSINESSFORMS businessforms = new BusinessFormRequest.BUSINESSFORMS();
            List<BusinessFormRequest.BUSINESSFORMS.BUSINESSFORM> businessform1 = businessforms.getBUSINESSFORM();
            businessform1.add(businessform);

            request.setBUSINESSFORMS(businessforms);
            BusinessFormResponse form = service.form(request);
            if (null != form.getRESPONSEBODY()) {
                log.debug("-----------------------------------连接成功----------------------------------------");
                log.debug("成功标识：" + form.getRESPONSEBODY().getFsscStatus().toString());
                log.debug("错误信息：" + form.getRESPONSEBODY().getFsscMessage());
                log.debug(businessform.toString());
                Assert.isTrue(String.valueOf(form.getRESPONSEBODY().getFsscStatus()).equals("Y"),  form.getRESPONSEBODY().getFsscMessage());
                log.debug("成功调用：response: " + JsonUtil.entityToJsonStr(response));
            }
        } catch (Exception ex) {
            log.error("调用ERP接口实现Service时报错: ", ex);
            throw new BaseException(ResultCode.RPC_ERROR.getMessage());
        }
    }
}
