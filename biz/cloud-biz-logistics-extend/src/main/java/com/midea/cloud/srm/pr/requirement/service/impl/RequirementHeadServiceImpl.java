package com.midea.cloud.srm.pr.requirement.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.contract.ContractClient;
import com.midea.cloud.srm.feign.inq.InqClient;
import com.midea.cloud.srm.feign.rbac.RbacClient;
import com.midea.cloud.srm.feign.supcooperate.SupcooperateClient;
import com.midea.cloud.srm.feign.supplier.SupplierClient;
import com.midea.cloud.srm.model.pm.pr.requirement.dto.PurchaseRequirementDTO;
import com.midea.cloud.srm.model.pm.pr.requirement.dto.RequirementHeadQueryDTO;
import com.midea.cloud.srm.model.pm.pr.requirement.dto.RequirementManageDTO;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.RequirementHead;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.RequirementLine;
import com.midea.cloud.srm.model.pm.pr.requirement.vo.RequirementLineVO;
import com.midea.cloud.srm.model.pm.ps.http.FSSCResult;
import com.midea.cloud.srm.po.order.service.IOrderService;
import com.midea.cloud.srm.pr.division.service.IDivisionCategoryService;
import com.midea.cloud.srm.pr.documents.service.ISubsequentDocumentsService;
import com.midea.cloud.srm.pr.requirement.mapper.RequirementHeadMapper;
import com.midea.cloud.srm.pr.requirement.mapper.RequirementLineMapper;
import com.midea.cloud.srm.pr.requirement.service.IRequirementAttachService;
import com.midea.cloud.srm.pr.requirement.service.IRequirementHeadService;
import com.midea.cloud.srm.pr.requirement.service.IRequirementLineService;
import com.midea.cloud.srm.pr.shopcart.service.IShopCartService;
import com.midea.cloud.srm.pr.workflow.PurchaseRequirementFlow;
import com.midea.cloud.srm.ps.http.fssc.service.IFSSCReqService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadPoolExecutor;


/**
 * <pre>
 *  采购需求头表 服务实现类
 * </pre>
 *
 * @author fengdc3@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-12 18:46:40
 *  修改内容:
 * </pre>
 */
@Slf4j
@Service
public class RequirementHeadServiceImpl extends ServiceImpl<RequirementHeadMapper, RequirementHead> implements IRequirementHeadService {

    @Autowired
    private BaseClient baseClient;
    @Resource
    private RbacClient rbacClient;
    @Autowired
    private InqClient inqClient;
    @Autowired
    private IRequirementLineService iRequirementLineService;
    @Resource
    private RequirementLineMapper requirementLineMapper;
    @Autowired
    private IRequirementAttachService iRequirementAttachService;
    @Autowired
    private IDivisionCategoryService iDivisionCategoryService;
    @Autowired
    private SupplierClient supplierClient;
    @Autowired
    private IFSSCReqService iFSSCReqService;
    @Autowired
    private IOrderService orderService;
    @Autowired
    private IShopCartService iShopCartService;
    @Autowired
    private ContractClient contractClient;
    @Autowired
    private SupcooperateClient supcooperateClient;
    @Autowired
    private ISubsequentDocumentsService iSubsequentDocumentsService;
    @Resource
    private PurchaseRequirementFlow purchaseRequirementFlow;
//    private final ThreadPoolExecutor ioThreadPool;
//    private final ForkJoinPool calculateThreadPool;

//    public RequirementHeadServiceImpl() {
//        int cpuCount = Runtime.getRuntime().availableProcessors();
//        ioThreadPool = new ThreadPoolExecutor(cpuCount * 2 + 1, cpuCount * 2 + 1,
//                0, TimeUnit.SECONDS, new LinkedBlockingQueue(),
//                new NamedThreadFactory("需求池-http-sender", true), new ThreadPoolExecutor.CallerRunsPolicy());
//        calculateThreadPool = new ForkJoinPool(cpuCount + 1);
//    }
//
//    @Override
//    @AuthData(module = MenuEnum.PURCHASE_APPLICATION)
//    public PageInfo<RequirementHead> listPage(RequirementHeadQueryDTO requirementHeadQueryDTO) {
//        PageUtil.startPage(requirementHeadQueryDTO.getPageNum(), requirementHeadQueryDTO.getPageSize());
//        RequirementHead headQuery = new RequirementHead();
//        headQuery.setCeeaPurchaseType(StringUtils.isNotBlank(requirementHeadQueryDTO.getCeeaPurchaseType()) ? requirementHeadQueryDTO.getCeeaPurchaseType() : null);
//        headQuery.setCeeaPrType(StringUtils.isNotBlank(requirementHeadQueryDTO.getCeeaPrType()) ? requirementHeadQueryDTO.getCeeaPrType() : null);
//        headQuery.setCeeaDepartmentId(StringUtils.isNotBlank(requirementHeadQueryDTO.getCeeaDepartmentId()) ? requirementHeadQueryDTO.getCeeaDepartmentId() : null);
//        headQuery.setCategoryId(requirementHeadQueryDTO.getCategoryId());
//        QueryWrapper<RequirementHead> wrapper = new QueryWrapper<>(headQuery);
//        wrapper.like(StringUtils.isNoneBlank(requirementHeadQueryDTO.getRequirementHeadNum()),
//                "REQUIREMENT_HEAD_NUM", requirementHeadQueryDTO.getRequirementHeadNum());
//        wrapper.like(StringUtils.isNoneBlank(requirementHeadQueryDTO.getCreatedFullName()),
//                "CREATED_FULL_NAME", requirementHeadQueryDTO.getCreatedFullName());
//        wrapper.like(StringUtils.isNotBlank(requirementHeadQueryDTO.getCeeaProjectNum()),
//                "CEEA_PROJECT_NUM", requirementHeadQueryDTO.getCeeaProjectNum());
//        wrapper.like(StringUtils.isNotBlank(requirementHeadQueryDTO.getCeeaProjectName()),
//                "CEEA_PROJECT_NAME", requirementHeadQueryDTO.getCeeaProjectName());
//        wrapper.like(StringUtils.isNotBlank(requirementHeadQueryDTO.getCeeaProjectUserNickname()),
//                "CEEA_PROJECT_USER_NICKNAME", requirementHeadQueryDTO.getCeeaProjectUserNickname());
//        wrapper.eq(StringUtils.isNoneBlank(requirementHeadQueryDTO.getAuditStatus()),
//                "AUDIT_STATUS", requirementHeadQueryDTO.getAuditStatus());
//        wrapper.like(StringUtils.isNotBlank(requirementHeadQueryDTO.getEsRequirementHeadNum()), "ES_REQUIREMENT_HEAD_NUM", requirementHeadQueryDTO.getEsRequirementHeadNum());
//        wrapper.in(CollectionUtils.isNotEmpty(requirementHeadQueryDTO.getOrgIds()), "ORG_ID", requirementHeadQueryDTO.getOrgIds());
//        wrapper.in(CollectionUtils.isNotEmpty(requirementHeadQueryDTO.getCeeaDepartmentIds()), "CEEA_DEPARTMENT_ID", requirementHeadQueryDTO.getCeeaDepartmentIds());
//        wrapper.in(CollectionUtils.isNotEmpty(requirementHeadQueryDTO.getCreatedIds()), "CREATED_ID", requirementHeadQueryDTO.getCreatedIds());
//        wrapper.ge(requirementHeadQueryDTO.getStartApplyDate() != null, "APPLY_DATE", requirementHeadQueryDTO.getStartApplyDate());
//        wrapper.le(requirementHeadQueryDTO.getEndApplyDate() != null, "APPLY_DATE", requirementHeadQueryDTO.getEndApplyDate());
////        wrapper.eq(StringUtils.isNoneBlank(requirementHead.getHandleStatus()),
////                "HANDLE_STATUS", requirementHead.getHandleStatus());
//        //采购需求申请/审批页不显示合并需求行生成的采购需求
////        wrapper.eq("CREATE_TYPE", RequirementCreateType.CREATE_NEW.value);
//        wrapper.orderByDesc("LAST_UPDATE_DATE");
//        return new PageInfo<>(this.list(wrapper));
//    }
//
//
//    @Override
//    @Transactional
//    public void deleteByHeadId(Long requirementHeadId) {
//        Assert.notNull(requirementHeadId, "采购需求(申请)头ID不能为空");
//        RequirementHead requirementHead = this.getById(requirementHeadId);
//        //拟定状态下才可删除
//        if (requirementHead != null && RequirementApproveStatus.DRAFT.getValue().equals(requirementHead.getAuditStatus())) {
//            this.removeById(requirementHeadId);
//            List<RequirementLine> rls = iRequirementLineService.list(new QueryWrapper<>(new RequirementLine().setRequirementHeadId(requirementHeadId)));
//            iRequirementLineService.remove(new QueryWrapper<>(new RequirementLine().setRequirementHeadId(requirementHeadId)));
//            iRequirementAttachService.remove(new QueryWrapper<>(new RequirementAttach().setRequirementHeadId(requirementHeadId)));
//            // 更新购物车行状态为“已提交”，lihaiping，2020-09-27
//            if (CollectionUtils.isNotEmpty(rls)) {
//                List<Long> shopCartIds = new ArrayList<>();
//                for (RequirementLine rl : rls) {
//                    if (rl.getShopCartId() != null) {
//                        shopCartIds.add(rl.getShopCartId());
//                    }
//                }
//                if (shopCartIds.size() > 0) {
//                    ShopCart sc = new ShopCart().setStatus(ShopCartStatus.SUBMITTED.getCode()).setRequirementHeadNum("");
//                    UpdateWrapper<ShopCart> uw = new UpdateWrapper<>();
//                    uw.in("SHOP_CART_ID", shopCartIds);
//                    iShopCartService.update(sc, uw);
//                }
//            }
//
//        } else {
//            throw new BaseException(LocaleHandler.getLocaleMsg("只有拟定状态下才可以删除"));
//        }
//
//        //查询采购需求头不为空且审批状态为已废弃才能删除
////        if(requirementHead != null && RequirementApproveStatus.ABANDONED.getValue().equals(requirementHead.getAuditStatus())){
////            this.removeById(requirementHeadId);
////            iRequirementLineService.remove(new QueryWrapper<>(new RequirementLine().setRequirementHeadId(requirementHeadId)));
////        }else {
////            throw new BaseException(LocaleHandler.getLocaleMsg("未废弃不能删除"));
////        }
//    }
//
//    @Override
//    @Transactional
//    public Long addPurchaseRequirement(PurchaseRequirementDTO purchaseRequirementDTO, String auditStatus) {
//        RequirementHead requirementHead = purchaseRequirementDTO.getRequirementHead();
//        List<RequirementLine> requirementLineList = purchaseRequirementDTO.getRequirementLineList();
//        List<RequirementAttach> requirementAttaches = purchaseRequirementDTO.getRequirementAttaches();
//
//        /**
//         * 检查物料是否可用于采购申请
//         */
//        checkItem(purchaseRequirementDTO, requirementLineList);
//        //校验必填项
////        checkRequiredParam(requirementHead, requirementLineList, requirementAttaches);
//
//
//        if (CategoryEnum.BIG_CATEGORY_SERVER.getCategoryCode().equals(requirementHead.getCategoryCode())) {
//            Assert.notNull(requirementHead.getCeeaBusinessSmallCode(), "物料大类为服务类时业务小类不能为空");
//        }
//        //保存采购需求头
//        Long id = IdGenrator.generate();
//        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
//        requirementHead.setRequirementHeadId(id).setCreatedFullName(loginAppUser.getNickname())
//                .setRequirementHeadNum(baseClient.seqGen(SequenceCodeConstant.SEQ_PMP_PR_APPLY_NUM))
//                .setApplyDate(DateChangeUtil.asLocalDate(new Date()))
//                .setAuditStatus(auditStatus);
////                setHandleStatus(RequirementHeadHandleStatus.EDIT.getValue())
////                .setCreateType(RequirementCreateType.CREATE_NEW.value);
//        this.save(requirementHead);
//        //保存采购需求行列表
//        iRequirementLineService.addRequirementLineBatch(loginAppUser, requirementHead, requirementLineList);
//        //保存采购需求附件表 (ceea)
//        iRequirementAttachService.addRequirementAttachBatch(requirementHead, requirementAttaches);
//        return id;
//    }
//
//
//    @Transactional
//    public Long addPurchaseRequirementMrp(PurchaseRequirementDTO purchaseRequirementDTO, String auditStatus) {
//        RequirementHead requirementHead = purchaseRequirementDTO.getRequirementHead();
//        List<RequirementLine> requirementLineList = purchaseRequirementDTO.getRequirementLineList();
//        List<RequirementAttach> requirementAttaches = purchaseRequirementDTO.getRequirementAttaches();
//        //校验必填项
////        checkRequiredParam(requirementHead, requirementLineList, requirementAttaches);
//
//
//        if (CategoryEnum.BIG_CATEGORY_SERVER.getCategoryCode().equals(requirementHead.getCategoryCode())) {
//            Assert.notNull(requirementHead.getCeeaBusinessSmallCode(), "物料大类为服务类时业务小类不能为空");
//        }
//        //保存采购需求头
//        Long id = IdGenrator.generate();
//        requirementHead.setRequirementHeadId(id)
//                .setApplyDate(DateChangeUtil.asLocalDate(new Date()))
//                .setAuditStatus(auditStatus);
////                setHandleStatus(RequirementHeadHandleStatus.EDIT.getValue())
////                .setCreateType(RequirementCreateType.CREATE_NEW.value);
//        this.save(requirementHead);
//        //保存采购需求行列表
//        iRequirementLineService.addRequirementLineBatch(null, requirementHead, requirementLineList);
//        //保存采购需求附件表 (ceea)
//        iRequirementAttachService.addRequirementAttachBatch(requirementHead, requirementAttaches);
//        return id;
//    }
//
//    private void checkRequiredParam(RequirementHead requirementHead, List<RequirementLine> requirementLineList, List<RequirementAttach> requirementAttaches) {
//        Assert.notNull(requirementHead, LocaleHandler.getLocaleMsg("采购需求头不能为空"));
//        Assert.notEmpty(requirementLineList, LocaleHandler.getLocaleMsg("采购需求行不能为空"));
//        Assert.notNull(requirementHead.getOrgId(), LocaleHandler.getLocaleMsg("业务实体不能为空"));
//        Assert.hasText(requirementHead.getCeeaPurchaseType(), "采购类型不能为空");
//        Assert.notNull(requirementHead.getCategoryId(), "物料大类不能为空");
//        for (RequirementLine requirementLine : requirementLineList) {
//            Assert.notNull(requirementLine, LocaleHandler.getLocaleMsg("采购需求行不能为空"));
//            Assert.isTrue(requirementLine.getOrganizationId() != null, "库存组织不能为空");
//            Assert.isTrue(requirementLine.getCategoryId() != null, "物料小类不能为空");
//            Assert.isTrue(StringUtils.isNotBlank(requirementLine.getMaterialCode()), "物料编码不能为空");
//            Assert.isTrue(StringUtils.isNotBlank(requirementLine.getMaterialName()), "物料名称不能为空");
//            Assert.isTrue((requirementLine.getRequirementQuantity() != null && requirementLine.getRequirementQuantity().compareTo(BigDecimal.ZERO) >= 0), "需求数量不能为空且大于等于0");
//
//            if (CategoryEnum.BIG_CATEGORY_SERVER.getCategoryCode().equals(requirementHead.getCategoryCode()) || YesOrNo.YES.getValue().equals(requirementLine.getCeeaIfDirectory())) {
//                Assert.notNull(requirementLine.getNotaxPrice(), "预算单价为空");
//            }
//            //判断物料单位不能为空 add by chenwt24@meicloud.com    2020-10-07
//            Assert.hasText(requirementLine.getUnit(), "物料单位不能为空");
//            Assert.hasText(requirementLine.getUnitCode(), "物料单位不能为空");
//
////            Assert.isTrue(StringUtils.isNotBlank(requirementLine.getApplyReason()), "申请原因不能为空");
//        }
//
//        if (RequirementApproveStatus.REJECTED.getValue().equals(requirementHead.getAuditStatus())) {
//            Assert.hasText(requirementHead.getCeeaDrafterOpinion(), LocaleHandler.getLocaleMsg("已驳回状态下,起草人意见不能为空"));
//        }
//
//        if (PurchaseType.URGENT.getValue().equals(requirementHead.getCeeaPurchaseType())) {
//            Assert.hasText(requirementHead.getCeeaUrgencyExplain(), "采购类型为紧急采购,紧急情况说明不能为空");
//        }
//
//        if (PurchaseType.APPOINT.getValue().equals(requirementHead.getCeeaPurchaseType())) {
//            Assert.hasText(requirementHead.getCeeaAppointReason(), "采购类型为指定采购,指定原因不能为空");
//        }
//        if (CollectionUtils.isNotEmpty(requirementAttaches)) {
//            for (RequirementAttach requirementAttach : requirementAttaches) {
//                if (requirementAttach == null) continue;
//                Assert.notNull(requirementAttach.getFileuploadId(), LocaleHandler.getLocaleMsg("存在尚未上传的附件,请检查!"));
//            }
//        }
//    }
//
//    @Override
//    public PurchaseRequirementDTO getByHeadId(Long requirementHeadId) {
//        Assert.notNull(requirementHeadId, "采购需求头ID不能为空");
//        PurchaseRequirementDTO vo = new PurchaseRequirementDTO();
//        vo.setRequirementHead(this.getById(requirementHeadId));
//        List<RequirementLine> requirementLineList = iRequirementLineService.list(new QueryWrapper<>(new RequirementLine().
//                setRequirementHeadId(requirementHeadId)));
//        vo.setRequirementLineList(requirementLineList);
//        List<RequirementAttach> requirementAttaches = iRequirementAttachService.list(new QueryWrapper<>(new RequirementAttach()
//                .setRequirementHeadId(requirementHeadId)));
//        vo.setRequirementAttaches(requirementAttaches);
//        return vo;
//    }
//
//    @Override
//    @Transactional
//    public Long modifyPurchaseRequirement(PurchaseRequirementDTO purchaseRequirementDTO) {
//        RequirementHead requirementHead = purchaseRequirementDTO.getRequirementHead();
//        List<RequirementLine> requirementLineList = purchaseRequirementDTO.getRequirementLineList();
//        List<RequirementAttach> requirementAttaches = purchaseRequirementDTO.getRequirementAttaches();
//
//        /**
//         * 检查物料是否可用于采购申请
//         */
//        checkItem(purchaseRequirementDTO, requirementLineList);
//        //校验必填信息
////        checkRequiredParam(requirementHead, requirementLineList, requirementAttaches);
//
//        RequirementHead head = this.getById(requirementHead.getRequirementHeadId());
//        if (RequirementApproveStatus.APPROVED.getValue().equals(head.getAuditStatus())
//                && RequirementApproveStatus.SUBMITTED.getValue().equals(head.getAuditStatus())) {
//            throw new BaseException(LocaleHandler.getLocaleMsg("只有拟定或已驳回状态才可以编辑"));
//        }
//        this.updateById(requirementHead);
//        iRequirementLineService.updateBatch(requirementHead, requirementLineList);
//        //编辑采购需求附件表 (ceea)
//        iRequirementAttachService.updateRequirementAttachBatch(requirementHead, requirementAttaches);
//        return head.getRequirementHeadId();
//    }
//
//    public void checkItem(PurchaseRequirementDTO purchaseRequirementDTO, List<RequirementLine> requirementLineList) {
//        List<String> itemCodes = new ArrayList<>();
//        if (CollectionUtils.isNotEmpty(requirementLineList)) {
//            requirementLineList.forEach(requirementLine -> {
//                if (StringUtil.notEmpty(requirementLine.getMaterialCode())) {
//                    itemCodes.add(requirementLine.getMaterialCode());
//                }
//            });
//        }
//        ItemCodeUserPurchaseDto purchaseDto = new ItemCodeUserPurchaseDto();
//        purchaseDto.setOrgId(purchaseRequirementDTO.getRequirementHead().getOrgId());
//        purchaseDto.setInvId(purchaseRequirementDTO.getRequirementHead().getOrganizationId());
//        purchaseDto.setItemCodes(itemCodes);
//        // 物料编码-采购标识
//        Map<String, String> idUserPurchase = baseClient.queryItemIdUserPurchase(purchaseDto);
//        if (CollectionUtils.isNotEmpty(requirementLineList)) {
//            requirementLineList.forEach(requirementLine -> {
//                String materialCode = requirementLine.getMaterialCode();
//                if (StringUtil.notEmpty(materialCode)) {
//                    Assert.isTrue(YesOrNo.YES.getValue().equals(idUserPurchase.get(materialCode)), "物料:" + materialCode + ",不能用于采购申请");
//                }
//            });
//        }
//    }
//
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public BaseResult<String> bachRequirement(List<RequirementHead> requirementHeadList) {
//        Assert.notNull(requirementHeadList, ResultCode.MISSING_SERVLET_REQUEST_PART.getMessage());
//        BaseResult<String> result = BaseResult.build(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage());
//        try {
//            getBaseMapper().bachRequirementHead(requirementHeadList);
//        } catch (Exception e) {
//            log.error("根据ID批量修改采购需求头信息时报错：", e);
//            throw new BaseException(ResultCode.OPERATION_FAILED.getMessage());
//        }
//        return result;
//    }
//
//    @Override
//    @Transactional
//    public void updateApprovelStatus(Long requirementHeadId, String auditStatus) {
//        // 更新头表
//        this.updateById(new RequirementHead().setRequirementHeadId(requirementHeadId).setAuditStatus(auditStatus));
//    }
//
//    @Override
//    @Transactional
//    public void updateApproved1(Long requirementHeadId, String auditStatus) {
//        // 更新行数据
//        RequirementLine requirementLine = new RequirementLine();
//        requirementLine.setRequirementHeadId(requirementHeadId);
//        QueryWrapper<RequirementLine> queryWrapper = new QueryWrapper<>(requirementLine);
//        List<RequirementLine> requirementLineList = iRequirementLineService.list(queryWrapper);
//        if (CollectionUtils.isNotEmpty(requirementLineList)) {
//            requirementLineList.forEach(requirement -> {
//                VendorAndEffectivePriceVO andEffectivePrice = iRequirementLineService.getVendorAndEffectivePrice(requirement);
//                RequirementLine line = new RequirementLine();
//                line.setRequirementLineId(requirement.getRequirementLineId());
//                line.setHaveSupplier(andEffectivePrice.getHaveSupplier());
//                line.setHaveEffectivePrice(andEffectivePrice.getHaveEffectivePrice());
//
//                String categoryName = requirement.getCategoryName();
//                if (StringUtil.notEmpty(categoryName)) {
//                    // 初始化一个采购员
//                    List<CategoryDv> categoryDvs = baseClient.queryUserByCategoryId(new CategoryDv().setCategoryName(categoryName));
//                    if (CollectionUtils.isNotEmpty(categoryDvs) && categoryDvs.size() == 1) {
//                        CategoryDv categoryDv = categoryDvs.get(0);
//                        //初始化采购员后, 状态改为已分配
//                        line.setBuyerId(categoryDv.getUserId());
//                        line.setBuyerName(categoryDv.getFullName());
//                        line.setBuyer(categoryDv.getUserName());
//                        line.setApplyStatus(RequirementApplyStatus.ASSIGNED.getValue());
//                    }
//                }
//                if (StringUtil.isEmpty(line.getBuyerId())) {
//                    line.setApplyStatus(RequirementApplyStatus.UNASSIGNED.getValue());
//                }
//                iRequirementLineService.updateById(line);
//            });
//        }
//
//        // 更新头状态
//        // 查询总行数
//        HashMap<String, Object> param = new HashMap<>();
//        param.put("requirementHeadId", requirementHeadId);
//        Long sunNum = requirementLineMapper.queryLineNum(param);
//        // 查询未处理行数
//        param.put("applyStatus", RequirementApplyStatus.UNASSIGNED.getValue());
//        Long num = requirementLineMapper.queryLineNum(param);
//        RequirementHead requirementHead = new RequirementHead();
//        requirementHead.setAuditStatus(auditStatus);
//        requirementHead.setRequirementHeadId(requirementHeadId);
//        if (num == sunNum) {
//            // 拟定
//            this.updateById(requirementHead.setHandleStatus(RequirementHeadHandleStatus.EDIT.getValue()));
//        } else if (num == 0) {
//            // 已全部
//            this.updateById(requirementHead.setHandleStatus(RequirementHeadHandleStatus.ALL.getValue()));
//        } else {
//            // 部分
//            this.updateById(requirementHead.setHandleStatus(RequirementHeadHandleStatus.PARTIAL.getValue()));
//        }
//    }
//
//    @Override
//    @Transactional
//    public void updateApproved(Long requirementHeadId, String auditStatus) {
//        RequirementHead byId = this.getById(requirementHeadId);
//        List<RequirementLine> requirementLineList = iRequirementLineService.list(new QueryWrapper<>(new RequirementLine().setRequirementHeadId(requirementHeadId)));
//        if (byId != null && !RequirementApproveStatus.SUBMITTED.getValue().equals(byId.getAuditStatus())) {
//            throw new BaseException("单据非已提交状态,不可审批,请检查!");
//        }
//        //自动根据品类分工规则进行分配
//        //PS:使用spring的代理类调用，事务生效
//        SpringContextHolder.getBean(this.getClass()).assignByDivisionCategory(byId, requirementLineList);
//        // 更新头表
//        this.updateById(new RequirementHead().setRequirementHeadId(requirementHeadId)
//                .setAuditStatus(auditStatus));
////                .setHandleStatus(RequirementHeadHandleStatus.EDIT.getValue()));
//
//        SpringContextHolder.getBean(this.getClass()).transferOrdersNew(byId,requirementLineList);
//    }
//
//    /**
//     * 1.筛选出目录化的，有供应商的，有供应商组织品类关系的采购申请 转订单
//     *      （1）供应商组织品类关系过滤方法：.根据供应商id，物料小类id，业务实体id，查询供应商组织品类关系，过滤没有供应商组织关系的申请行
//     *      （2）根据物料id + 物料描述 + 库存组织id + 供应商id + 有效期 + 是否上架为是 + 价格类型为标准
//     * 2.根据物料id + 物料描述  + 库存组织id + 供应商id + 有效期 + 是否上架为是 + 价格类型为标准 查询是否有有效价格。
//     * 3.获取有效价格赋值
//     * 4.相同业务实体、相同库存组织、相同供应商、相同币种、相同税率、相同付款条款、相同项目可汇总合并成同一个订单
//     * PS：订单的创建人为采购申请的创建人，采购员为创建人对应的erp采购员。
//     * @param requirementHead
//     * @param requirementLineList
//     */
//    @Transactional
//    public void transferOrdersNew(RequirementHead requirementHead, List<RequirementLine> requirementLineList){
//        //获取外围系统数据 PriceLibrary OrgCategory
//        List<OrgCategory> orgCategoryParams = new LinkedList<>();
//        List<PriceLibrary> priceLibraryParams = new LinkedList<>();
//        requirementLineList.forEach(r -> {
//            orgCategoryParams.add(new OrgCategory()
//                    .setOrgId(r.getOrgId())
//                    .setCategoryId(r.getCategoryId())
//            );
//            priceLibraryParams.add(new PriceLibrary()
//                    .setItemId(r.getMaterialId())
//                    .setItemDesc(r.getMaterialName())
//                    .setCeeaOrganizationId(r.getOrganizationId())
//                    .setVendorId(r.getVendorId())
//            );
//        });
//        List<OrgCategory> orgCategoryList = supplierClient.listOrgCategory(orgCategoryParams);
//        List<PriceLibrary> priceLibraryList = inqClient.listPriceLibraryForTransferOrders(priceLibraryParams);
//        User user = rbacClient.getUserByIdAnon(requirementHead.getCreatedId());
//        List<PoAgent> poAgentList = null;
//        if(!Objects.isNull(user) && StringUtils.isNotBlank(user.getCeeaEmpNo())){
//            poAgentList = rbacClient.listPoAgentByParamAnon(new PoAgent().setAgentNumber(user.getCeeaEmpNo()));
//        }
//
//        //判断申请创建人有没有采购员身份，如果没有则过滤
//        if(CollectionUtils.isEmpty(poAgentList)){
//            log.info("目录化转订单找不到采购员，申请单号为：" + requirementHead.getRequirementHeadNum());
//            return;
//        }
//
//        //过滤没有供应商的采购申请
//        requirementLineList = requirementLineList.stream().filter(r -> !Objects.isNull(r.getVendorId())).collect(Collectors.toList());
//        if (CollectionUtils.isEmpty(requirementLineList)) {
//            log.info("目录化转订单找不到供应商，申请单号为：" + requirementHead.getRequirementHeadNum());
//            return;
//        }
//
//        //过滤没有供应商组织品类关系的采购申请
//        List<RequirementLine> requirementLineList2 = new LinkedList<RequirementLine>();
//        Map<String, List<OrgCategory>> orgCategoryMap = getOrgCategoryMap(orgCategoryList);
//        requirementLineList.forEach(r -> {
//            //业务实体id + 供应商id + 物料小类id 作为 key
//            String key = new StringBuffer().append(r.getOrgId())
//                    .append(r.getVendorId())
//                    .append(r.getCategoryId()).toString();
//            if (Objects.isNull(orgCategoryMap.get(key))) {
//                return;
//            } else {
//                List<OrgCategory> categoryList = orgCategoryMap.get(key);
//                if (!CollectionUtils.isEmpty(categoryList)) {
//                    requirementLineList2.add(r);
//                }
//            }
//        });
//        if (CollectionUtils.isEmpty(requirementLineList2)) {
//            log.info("目录化转订单找不到供应商组织品类关系，申请单号为：" + requirementHead.getRequirementHeadNum());
//            return;
//        }
//
//        //过滤非目录化的采购申请
//        List<RequirementLine> requirementLineList3 = new LinkedList<RequirementLine>();
//        if (CollectionUtils.isEmpty(priceLibraryList)) {
//            log.info("目录化转订单找不到有效价格，申请单号为：" + requirementHead.getRequirementHeadNum());
//            return;
//        }
//        Map<String, PriceLibrary> priceLibraryMap = getPriceLibraryMap(priceLibraryList);
//        requirementLineList2.forEach(r -> {
//            String key = new StringBuffer().append(r.getMaterialId())
//                    .append(r.getMaterialName())
//                    .append(r.getOrganizationId())
//                    .append(r.getVendorId()).toString();
//            PriceLibrary priceLibrary = priceLibraryMap.get(key);
//            if(!Objects.isNull(priceLibrary)){
//                r.setPriceLibrary(priceLibrary);
//                requirementLineList3.add(r);
//            }
//        });
//        if (CollectionUtils.isEmpty(requirementLineList3)) {
//            log.info("目录化转订单找不到有效价格，申请单号为：" + requirementHead.getRequirementHeadNum());
//            return;
//        }
//
//        //申请转订单
//        requirementsToOrders(requirementHead,requirementLineList3,user,poAgentList);
//    }
//
//    /**
//     * 相同的业务实体 + 库存组织 + 供应商 + 币种 + 税率 + 相同付款条款 + 相同项目 汇总为同一个订单
//     * 付款条款判断是否相等
//     *
//     * @param requirementHead
//     * @param requirementLineList
//     */
//    private void requirementsToOrders(RequirementHead requirementHead,List<RequirementLine> requirementLineList,User user,List<PoAgent> poAgentList){
//        //分组
//        Map<String, List<RequirementLine>> map = groupRequirementLine(requirementLineList);
//        //根据分组生成订单
//        for(Map.Entry<String,List<RequirementLine>> entry : map.entrySet()){
//            OrderSaveRequestDTO orderSaveRequestDTO = buildOrderForTransferNew(requirementHead,entry.getValue(),user,poAgentList);
//            Long orderId = orderService.save(orderSaveRequestDTO);
//            //回写采购订单号
//            List<RequirementLine> rList = entry.getValue();
//            Order order = supcooperateClient.getOrderById(orderId);
//            List<RequirementLine> updateRequirementLineList = new ArrayList<>();
//            for (RequirementLine item : rList) {
//                RequirementLine requirementLine = new RequirementLine().setRequirementLineId(item.getRequirementLineId()).setOrderNumber(order.getOrderNumber());
//                updateRequirementLineList.add(requirementLine);
//            }
//            iRequirementLineService.updateBatchById(updateRequirementLineList);
//        }
//    }
//
//    /**
//     * 根据库存组织id,供应商id,币种编码,税率编码,相同付款条款 汇总为同一个订单,
//     *
//     * @param requirementLineList
//     * @return
//     */
//    private Map<String, List<RequirementLine>> groupRequirementLine(List<RequirementLine> requirementLineList) {
//        //获取付款条款唯一值
//        requirementLineList.forEach(r -> {
//            List<PriceLibraryPaymentTerm> priceLibraryPaymentTermList = r.getPriceLibraryPaymentTermList();
//            String paymentTermId = null;
//            if(CollectionUtils.isNotEmpty(priceLibraryPaymentTermList)){
//                paymentTermId = getPaymentTermId(priceLibraryPaymentTermList);
//            }
//            r.setPaymentTermId(paymentTermId);
//        });
//
//        //库存组织id,供应商id,币种编码,税率编码,付款条款 作为唯一key值
//        Map<String, List<RequirementLine>> map = new HashMap<String, List<RequirementLine>>();
//        requirementLineList.forEach(r -> {
//            PriceLibrary priceLibrary = r.getPriceLibrary();
//            String key = new StringBuffer()
//                    .append(r.getOrganizationId())
//                    .append(r.getVendorId())
//                    .append(priceLibrary.getCurrencyCode())
//                    .append(priceLibrary.getTaxKey())
//                    .append(r.getPaymentTermId()).toString();
//            if (Objects.isNull(map.get(key))) {
//                map.put(key, new LinkedList<RequirementLine>() {{
//                    add(r);
//                }});
//            } else {
//                map.get(key).add(r);
//            }
//        });
//        return map;
//
//    }
//
//    /**
//     * 付款条件 + 付款账期 + 付款方式 唯一key值
//     *
//     * @param priceLibraryPaymentTermList
//     * @return
//     */
//    private String getPaymentTermId(List<PriceLibraryPaymentTerm> priceLibraryPaymentTermList) {
//        Comparator<PriceLibraryPaymentTerm> comparator = new Comparator<PriceLibraryPaymentTerm>() {
//            @Override
//            public int compare(PriceLibraryPaymentTerm o1, PriceLibraryPaymentTerm o2) {
//                return getPaymentTermId(o1).compareTo(getPaymentTermId(o2));
//            }
//        };
//        priceLibraryPaymentTermList.sort(comparator);
//        StringBuffer id = new StringBuffer();
//        priceLibraryPaymentTermList.forEach(e -> {
//            String paymentTermId = getPaymentTermId(e);
//            id.append(paymentTermId);
//        });
//        return id.toString();
//    }
//
//    private String getPaymentTermId(PriceLibraryPaymentTerm priceLibraryPaymentTerm) {
//        return new StringBuffer().append(priceLibraryPaymentTerm.getPaymentWay())
//                .append(priceLibraryPaymentTerm.getPaymentDay())
//                .append(priceLibraryPaymentTerm.getPaymentWay()).toString();
//    }
//
//
//
//    /**
//     * 物料id + 物料描述  + 库存组织id + 供应商id 作为唯一key值
//     *
//     * @param priceLibraryList
//     * @return
//     */
//    private Map<String, PriceLibrary> getPriceLibraryMap(List<PriceLibrary> priceLibraryList) {
//        HashMap<String, PriceLibrary> priceLibraryHashMap = new HashMap<>();
//        for (int i = 0; i < priceLibraryList.size(); i++) {
//            PriceLibrary value = priceLibraryList.get(i);
//            String key = new StringBuffer().append(value.getItemId())
//                    .append(value.getItemDesc())
//                    .append(value.getCeeaOrganizationId())
//                    .append(value.getVendorId()).toString();
//            priceLibraryHashMap.put(key, value);
//        }
//        return priceLibraryHashMap;
//    }
//
//    /**
//     * 业务实体id + 供应商id + 物料小类id + 品类状态 作为唯一key
//     *
//     * @return
//     */
//    private Map<String, List<OrgCategory>> getOrgCategoryMap(List<OrgCategory> orgCategoryList) {
//        List<String> effectiveList = new LinkedList<String>() {{
//            add(CategoryStatus.VERIFY.name());
//            add(CategoryStatus.YELLOW.name());
//            add(CategoryStatus.ONE_TIME.name());
//            add(CategoryStatus.REGISTERED.name());
//            add(CategoryStatus.GREEN.name());
//        }};
//        HashMap<String, List<OrgCategory>> stringOrgCategoryHashMap = new HashMap<>();
//        for (int i = 0; i < orgCategoryList.size(); i++) {
//            OrgCategory value = orgCategoryList.get(i);
//            if (!effectiveList.contains(value.getServiceStatus())) {
//                continue;
//            }
//            String key = new StringBuffer().append(value.getOrgId())
//                    .append(value.getCompanyId())
//                    .append(value.getCategoryId()).toString();
//            if (Objects.isNull(stringOrgCategoryHashMap.get(key))) {
//                stringOrgCategoryHashMap.put(key, new LinkedList<OrgCategory>() {{
//                    add(value);
//                }});
//            } else {
//                stringOrgCategoryHashMap.get(key).add(value);
//            }
//        }
//        return stringOrgCategoryHashMap;
//    }
//
//    @Transactional
//    public OrderSaveRequestDTO buildOrderForTransferNew(RequirementHead requirementHead,List<RequirementLine> requirementLineList,User user,List<PoAgent> poAgentList){
//        //获取外围系统数据 dictItemDTO(订单条目标识) userPermissionDTO(采购员名称,工号) ou(业务实体) materialMaxCategoryVoList(物料大类)
//        List<DictItemDTO> dictItemDTOList = baseClient.listAllByParam(new DictItemDTO().setDictCode("ORDER_TYPE"));
//        Map<String, DictItemDTO> dictItemDTOMap = dictItemDTOList.stream().collect(Collectors.toMap(e -> e.getDictItemCode(), e -> e));
//
//        Organization organization = baseClient.getOrganization(new Organization().setOrganizationId(requirementLineList.get(0).getOrgId()));
//
//        List<Long> materialIds = requirementLineList.stream().map(item -> item.getMaterialId()).collect(Collectors.toList());
//        List<MaterialMaxCategoryVO> materialMaxCategoryVOList = baseClient.queryCategoryMaxCodeByMaterialIds(materialIds);
//        Map<Long, MaterialMaxCategoryVO> materialMaxCategoryVOMap = materialMaxCategoryVOList.stream().collect(Collectors.toMap(e -> e.getMaterialId(), e -> e));
//
//        //获取付款条款
//        List<PriceLibraryPaymentTerm> priceLibraryPaymentTermList = requirementLineList.get(0).getPriceLibrary().getPaymentTerms();
//
//        //获取订单类型的条目标识
//        DictItemDTO dictItemDTO = dictItemDTOMap.get(requirementHead.getCeeaPurchaseType());
//        String ceeaOrderTypeIdentification = dictItemDTO.getDictItemMark();
//        if (StringUtils.isBlank(ceeaOrderTypeIdentification)) {
//            throw new BaseException("条目标识为空");
//        }
//
//        //获取采购员名称，采购员工号，采购员id
//        PoAgent poAgent = poAgentList.get(0);
//        Long userId = poAgent.getAgentId();
//        String nickName = poAgent.getAgentName();
//        String ceeaEmpNo = poAgent.getAgentNumber();
//
//        //设置OrderDetail
//        List<OrderDetail> orderDetailList = new LinkedList<>();
//        for (int i = 0; i < requirementLineList.size(); i++) {
//
//            OrderDetail orderDetail = new OrderDetail();
//            RequirementLine r = requirementLineList.get(i);
//            MaterialMaxCategoryVO materialMaxCategoryVO = materialMaxCategoryVOMap.get(r.getMaterialId());
//            String bigCategoryCode = null;
//            if (!Objects.isNull(materialMaxCategoryVO)) {
//                bigCategoryCode = materialMaxCategoryVO.getCategoryCode();
//            }
//            orderDetail.setLineNum(i + 1)   //订单行号
//                    .setCeeaPlanReceiveDate(Date.from(r.getRequirementDate().atStartOfDay(ZoneId.systemDefault()).toInstant()))
//                    .setCeeaPromiseReceiveDate(new Date())  // todo 承诺到货日期 = 当前日期 + 供货周期
//                    .setCeeaUnitNoTaxPrice(r.getPriceLibrary().getNotaxPrice())  //不含税单价
//                    .setCeeaUnitTaxPrice(r.getPriceLibrary().getTaxPrice())   //含税单价
//                    .setCurrencyId(r.getPriceLibrary().getCurrencyId())    //币种id
//                    .setCurrencyCode(r.getPriceLibrary().getCurrencyCode())   //币种编码
//                    .setCurrencyName(r.getPriceLibrary().getCurrencyName())  //币种名称
//                    .setCeeaContractNo(r.getPriceLibrary().getContractCode())  //合同编号 todo
//                    .setCeeaBusinessSmall(r.getCeeaBusinessSmall())//业务小类
//                    .setCeeaBusinessSmallCode(r.getCeeaBusinessSmallCode())//业务小类编码
//                    .setOrderDetailId(IdGenrator.generate())
//                    .setCategoryId(r.getCategoryId()) //采购分类ID(物料小类id)
//                    .setCategoryName(r.getCategoryName()) //采购分类全名(物料小类名称)
//                    .setMaterialId(r.getMaterialId())  //物料ID
//                    .setMaterialCode(r.getMaterialCode())  //物料编码
//                    .setMaterialName(r.getMaterialName())  //物料名称
//                    .setReceiptPlace(r.getCeeaDeliveryPlace())  //收货地点
//                    .setUnit(r.getUnitCode())  //单位
//                    .setUnitCode(r.getUnitCode())
//                    .setOrderNum(r.getOrderQuantity())  //订单数量(本次下单数量)
//                    .setRequirementDate(r.getRequirementDate()) //需求日期
//                    .setRequirementQuantity(r.getRequirementQuantity()) //需求数量(本次下单数量)
//                    .setCurrency(r.getCurrency()) //币种
//                    .setComments(r.getComments())  //备注
//                    .setTenantId(0L)
//                    .setVersion(0L)
//                    .setReceiveSum(BigDecimal.ZERO) //订单累计收货量
//                    .setCeeaRequirementLineId(r.getRequirementLineId()) //采购申请物料行主键id
//                    .setCeeaRequirementHeadNum(r.getRequirementHeadNum())  //采购申请编号(longi)
//                    .setCeeaRowNum(StringUtil.StringValue(i + 1))  //
//                    .setCeeaOrganizationId(r.getOrganizationId()) //库存组织ID
//                    .setCeeaOrganizationName(r.getOrganizationName())  //库存组织名称
//                    .setCeeaOrganizationCode(r.getOrganizationCode()) //库存组织编码
//                    .setCeeaFirstApprovedNum(BigDecimal.ZERO) //第一次审批通过数量(原订单数量)
//                    .setCeeaApprovedNum(BigDecimal.ZERO)  //审批通过数量
//                    .setCeeaTaxRate(new BigDecimal(r.getPriceLibrary().getTaxRate()))  //税率
//                    .setCeeaTaxKey(r.getPriceLibrary().getTaxKey())  //税码
//                    .setCeeaIfRequirement("Y") //物料行是否未采购需求
//                    .setBigCategoryCode(bigCategoryCode); //物料大类编码
//
//            if (Objects.isNull(orderDetail.getOrderNum())) {
//                orderDetail.setOrderNum(BigDecimal.ZERO);
//            }
//            if (Objects.isNull(orderDetail.getCeeaUnitTaxPrice())) {
//                orderDetail.setCeeaUnitTaxPrice(BigDecimal.ZERO);
//            }
//            if (Objects.isNull(orderDetail.getCeeaUnitNoTaxPrice())) {
//                orderDetail.setCeeaUnitNoTaxPrice(new BigDecimal(0));
//            }
//            BigDecimal ceeaTaxRate = orderDetail.getCeeaTaxRate().divide(new BigDecimal(100), 4, BigDecimal.ROUND_HALF_UP).add(new BigDecimal(1));
//            orderDetail.setCeeaUnitNoTaxPrice(orderDetail.getCeeaUnitTaxPrice().divide(ceeaTaxRate, 2, BigDecimal.ROUND_HALF_UP));
//
//            BigDecimal ceeaAmountIncludingTax = orderDetail.getOrderNum().multiply(orderDetail.getCeeaUnitTaxPrice());
//            BigDecimal ceeaAmountExcludingTax = orderDetail.getOrderNum().multiply(orderDetail.getCeeaUnitNoTaxPrice());
//            BigDecimal ceeaTaxAmount = ceeaAmountExcludingTax.subtract(ceeaAmountIncludingTax);
//            orderDetail.setCeeaAmountIncludingTax(ceeaAmountIncludingTax)  //含税金额
//                    .setCeeaAmountExcludingTax(ceeaAmountExcludingTax)  //不含税金额
//                    .setCeeaTaxAmount(ceeaTaxAmount);  //税额
//
//            orderDetailList.add(orderDetail);
//        }
//
//        //设置Order
//        RequirementLine requirementLine = requirementLineList.get(0);
//        BigDecimal ceeaTotalNum = BigDecimal.ZERO; //合计数量
//        BigDecimal ceeaTaxAmount = BigDecimal.ZERO;  //合计金额含税
//        BigDecimal ceeaNoTaxAmount = BigDecimal.ZERO;  //合计金额不含税
//        for (OrderDetail orderDetail : orderDetailList) {
//            ceeaTotalNum = ceeaNoTaxAmount.add(orderDetail.getOrderNum());
//            ceeaTaxAmount = ceeaTaxAmount.add(orderDetail.getCeeaAmountExcludingTax());
//            ceeaNoTaxAmount = ceeaNoTaxAmount.add(orderDetail.getCeeaAmountExcludingTax());
//        }
//        Order order = new Order();
//        order.setVendorId(requirementLine.getVendorId())
//                .setVendorName(requirementLine.getVendorName())
//                .setVendorCode(requirementLine.getVendorCode())
//                .setCeeaIfSupplierConfirm("Y")
//                .setCeeaIfConSignment("N")
//                .setCeeaIfPowerStationBusiness("N")
//                .setCeeaOrgId(requirementLine.getOrgId())  //业务实体ID
//                .setCeeaOrgCode(requirementLine.getOrgCode())  //业务实体编码
//                .setCeeaOrgName(requirementLine.getOrgName())  //业务实体名称
//                .setCeeaDepartmentId(requirementHead.getCeeaDepartmentId())  //部门ID
//                .setCeeaDepartmentCode(requirementHead.getCeeaDepartmentCode())  //部门编码
//                .setCeeaDepartmentName(requirementHead.getCeeaDepartmentName())  //部门名称
//                .setCeeaOrderTypeIdentification(ceeaOrderTypeIdentification)
//                .setOrderType(requirementHead.getCeeaPurchaseType())
//                .setCeeaReceiveOrderAddress(Objects.isNull(organization) ? null : organization.getCeeaReceivingLocation())//收单地址
//                .setCreatedId(requirementLine.getCreatedId())  //创建人id
//                .setCreatedBy(requirementLine.getCreatedBy())  //创建人
//                .setCreationDate(new Date())  //创建时间
//                .setCeeaSaveId(requirementLine.getCreatedId())  //保存人id
//                .setCeeaSaveBy(requirementLine.getCreatedBy())  //保存人名称
//                .setCeeaSaveDate(new Date())  //保存时间
//                .setCeeaEmpUseId(userId)    //采购员id
//                .setCeeaEmpUsername(nickName)   //采购员名称
//                .setCeeaEmpNo(ceeaEmpNo)    //采购员工号
//                .setCeeaReceiveAddress(Objects.isNull(organization) ? null : organization.getOrganizationSite()) //收货地点
//                .setIfSample("N") //是否样品小批量订单
//                .setSourceSystem(SourceSystemEnum.DEMAND.getValue()) //订单来源
//                .setTenantId(0L)
//                .setVersion(0L)
//                .setCeeaPurchaseOrderDate(new Date())
//                .setOrderStatus(PurchaseOrderEnum.DRAFT.getValue())
//                .setCeeaPurchaseOrderDate(new Date())
//                .setCeeaTotalNum(ceeaTotalNum)
//                .setCeeaTaxAmount(ceeaTaxAmount)
//                .setCeeaNoTaxAmount(ceeaNoTaxAmount);
//
//        //获取付款条款
//        List<OrderPaymentProvision> orderPaymentProvisionList = new LinkedList<>();
//        priceLibraryPaymentTermList.forEach(priceLibraryPaymentTerm -> {
//            OrderPaymentProvision orderPaymentProvision = new OrderPaymentProvision()
//                    .setPaymentPeriod(StringUtil.StringValue(priceLibraryPaymentTerm.getPaymentDay()))
//                    .setPaymentWay(priceLibraryPaymentTerm.getPaymentWay())
//                    .setPaymentTerm(priceLibraryPaymentTerm.getPaymentTerm());
//            orderPaymentProvisionList.add(orderPaymentProvision);
//        });
//
//        return new OrderSaveRequestDTO()
//                .setOrder(order)
//                .setDetailList(orderDetailList)
//                .setAttachList(Collections.EMPTY_LIST)
//                .setPaymentProvisionList(orderPaymentProvisionList);
//    }
//
//
//
//
//    /**
//     * author:cwj
//     * date:2020-10-20
//     * 如果采购申请物料行为目录化，则直接转订单
//     * 判断是否有供应商，没有则报错
//     * 根据供应商id,物料小类id,业务实体id 查询供应商组织品类关系
//     * 有组织品类关系
//     * 根据 物料编码，物料名称，业务实体id，库存组织id，供应商id, 查询是否有有效价格，如果有赋值
//     * 如果没有 根据 物料编码，物料名称，供应商id，业务实体id，库存组织id 查询有效合同(查询到多个合同)，如果有赋值
//     * 如果没有价格则报错
//     * 相同业务实体、相同库存组织、相同供应商、相同币种、相同税率、相同付款条款、相同项目可汇总合并成同一个订单
//     * PS：订单的创建人为：采购申请的创建人
//     * <p>
//     * -------------------
//     * 2020-10-20 取价逻辑修改为：
//     * （1）目录化要动态判断（物料编码+物料描述+业务实体+库存组织+供应商+价格类型（标准）在价格库中查找，是否有当前时间在有效期内，且是否上架为是的行）
//     * （2）取价 只去价格库取，如果无价格，略过（不通过合同取价）
//     */
//    @Transactional(rollbackFor = Exception.class)
//    public void tranferOrders(RequirementHead requirementHead, List<RequirementLine> requirementLineList) {
//
//        /*获取需求行的所有物料id*/
//        List<Long> materialIds = requirementLineList.stream().map(item -> item.getMaterialId()).collect(Collectors.toList());
//
//        /*查询所有的物料（优化方案：通过收集物料id去优化）*/
//        List<MaterialItem> materialItemAll = baseClient.listAllMaterialItemForTranferOrder(new MaterialQueryDTO().setMaterialIds(materialIds));
//        /*查询所有有效价格（优化方案：通过收集物料id优化）*/
//        List<PriceLibrary> priceLibraryAll = inqClient.listAllEffective(new PriceLibrary().setItemIds(materialIds));
//        /*查询所有供应商品类组织关系（通过业务实体id优化）*/
//        List<OrgCategory> orgCategoryList = supplierClient.getOrgCategoryByOrgCategory(new OrgCategory().setOrgId(requirementHead.getOrgId()));
//        /*查询出所有有效合同（通过收集物料id去优化）*/
//        List<Long> orgIds = requirementLineList.stream().map(item -> item.getOrgId()).collect(Collectors.toList());
//        List<ContractVo> contractVoList = contractClient.listAllEffectiveCM(new ContractItemDto().setMaterialIds(orgIds));
//        /*判断是否为目录化*/
//        List<RequirementLineVO> catalogRequirementLineList = new ArrayList<>();
//        //合同的合作伙伴
//        List<Long> controlHeadIds = contractVoList.stream().map(c -> c.getContractHeadId()).collect(Collectors.toList());
//        List<ContractPartner> contractPartnerList = contractClient.listAllEffectiveCP(controlHeadIds);
//
//        for (RequirementLine requirementLine : requirementLineList) {
//            boolean isCatalog = false;
//            boolean havePrice = false;
//            RequirementLineVO requirementLineVO = new RequirementLineVO();
//            BeanUtils.copyProperties(requirementLine, requirementLineVO);
//
//            for (MaterialItem materialItem : materialItemAll) {
//                if (Objects.equals(materialItem.getMaterialId(), requirementLine.getMaterialId()) &&
//                        "Y".equals(materialItem.getCeeaIfCatalogMaterial())
//                ) {
//                    isCatalog = true;
//                    break;
//                }
//            }
//            if (isCatalog) {
//                for (PriceLibrary priceLibrary : priceLibraryAll) {
//                    //价格库和行表地点比对
//                    String priceLibraryPlace = StringUtils.defaultString(priceLibrary.getCeeaArrivalPlace());
//                    String rePriceLibraryPlace = StringUtils.defaultString(requirementLine.getCeeaDeliveryPlace());
//                    boolean ceeaArrivalPlaceFlag = Objects.equals(priceLibraryPlace, rePriceLibraryPlace);
//
//                    if (null != priceLibrary && StringUtils.isNotBlank(requirementLine.getMaterialName()) && requirementLine.getMaterialName().equals(priceLibrary.getItemDesc()) &&
//                            StringUtils.isNotBlank(requirementLine.getMaterialCode()) && requirementLine.getMaterialCode().equals(priceLibrary.getItemCode()) &&
//                            requirementLine.getOrgId() != null && !ObjectUtils.notEqual(requirementLine.getOrgId(), priceLibrary.getCeeaOrgId()) &&
//                            requirementLine.getOrganizationId() != null && Objects.equals(requirementLine.getOrganizationId(), priceLibrary.getCeeaOrganizationId()) &&
//                            requirementLine.getVendorId() != null && Objects.equals(requirementLine.getVendorId(), priceLibrary.getVendorId()) &&
//                            StringUtils.isNotBlank(priceLibrary.getPriceType()) && "STANDARD".equals(priceLibrary.getPriceType()) &&
//                            ceeaArrivalPlaceFlag
//                    ) {
//                        if (priceLibrary.getNotaxPrice() == null ||
//                                priceLibrary.getTaxPrice() == null ||
//                                StringUtils.isBlank(priceLibrary.getTaxKey()) ||
//                                StringUtils.isBlank(priceLibrary.getTaxRate()) ||
//                                priceLibrary.getCurrencyId() == null ||
//                                StringUtils.isBlank(priceLibrary.getCurrencyCode()) ||
//                                StringUtils.isBlank(priceLibrary.getCurrencyName())
//                        ) {
//                            log.info("requirementHeadNum = " + requirementLine.getRequirementHeadNum() + "  rowNum:" + requirementLine.getRowNum() + " 价格库某些字段为空:" + JsonUtil.entityToJsonStr(priceLibrary));
//                        } else {
//                            havePrice = true;
//                            /*设置价格库*/
//                            requirementLineVO.setPriceLibrary(priceLibrary);
//                            /*设置付款条款*/
//                            List<PriceLibraryPaymentTerm> priceLibraryPaymentTermList = inqClient.listPriceLibraryPaymentTerm(new PriceLibraryPaymentTerm().setPriceLibraryId(priceLibrary.getPriceLibraryId()));
//                            List<OrderPaymentProvision> orderPaymentProvisionList = new ArrayList<>();
//                            priceLibraryPaymentTermList.forEach(priceLibraryPaymentTerm -> {
//                                OrderPaymentProvision orderPaymentProvision = new OrderPaymentProvision()
//                                        .setPaymentPeriod(StringUtil.StringValue(priceLibraryPaymentTerm.getPaymentDay()))
//                                        .setPaymentWay(priceLibraryPaymentTerm.getPaymentWay())
//                                        .setPaymentTerm(priceLibraryPaymentTerm.getPaymentTerm());
//                                orderPaymentProvisionList.add(orderPaymentProvision);
//                            });
//                            requirementLineVO.setOrderPaymentProvisionList(orderPaymentProvisionList);
//                            /*关联框架合同*/
////                            for (ContractVo contractVo : contractVoList) {
////                                if (null != contractVo && contractVo.getBuId() != null && !ObjectUtils.notEqual(contractVo.getBuId(), requirementLine.getOrgId())
////                                        && contractVo.getInvId() != null && Objects.equals(contractVo.getInvId(), requirementLine.getOrganizationId())
////                                        && contractVo.getHeadVendorId() != null && Objects.equals(contractVo.getHeadVendorId(), requirementLine.getVendorId())
////                                        && StringUtils.isNotBlank(contractVo.getIsFrameworkAgreement()) && contractVo.getIsFrameworkAgreement().equals("Y")
////                                ) {
////                                    /*判断合同是否某些字段为空，为空则跳过该数据*/
////                                    if (StringUtils.isNotBlank(contractVo.getContractCode())) {
////                                        requirementLineVO.setContractCode(contractVo.getContractCode());
////                                        break;
////                                    } else {
////                                        log.info("requirementHeadNum = " + requirementLineVO.getRequirementHeadNum() + "  rowNum:" + requirementLineVO.getRowNum() + " 合同的合同编号为空:" + JsonUtil.entityToJsonStr(contractVo));
////                                    }
////                                }
////                            }
//
//                            if (!StringUtil.isEmpty(priceLibrary.getContractCode())) {
//                                requirementLineVO.setContractCode(priceLibrary.getContractCode());
//                            } else {
//                                //添加比对合作伙伴 逻辑2020年11月10日
//                                for (ContractVo contractVo : contractVoList) {
//                                    boolean hitContractFlag =
////                                          null != contractVo &&
////                                          contractVo.getBuId() != null &&
////                                          !ObjectUtils.notEqual(contractVo.getBuId(), item.getOrgId())&&
//                                            contractVo.getHeadVendorId() != null &&
////                                          contractVo.getInvId() != null &&
////                                          Objects.equals(contractVo.getInvId(), item.getOrganizationId()) &&
//                                                    Objects.equals(contractVo.getHeadVendorId(), priceLibrary.getVendorId()) &&
//                                                    StringUtils.isNotBlank(contractVo.getIsFrameworkAgreement()) &&
//                                                    StringUtils.isNotBlank(contractVo.getContractCode()) &&
//                                                    contractVo.getIsFrameworkAgreement().equals("Y");
//                                    log.info("requirementHeadNum = " + requirementLineVO.getRequirementHeadNum() + "  rowNum:" + requirementLineVO.getRowNum() + " 合同的合同编号为空:" + JsonUtil.entityToJsonStr(contractVo));
//                                    boolean findContractNum = false;
//                                    for (ContractPartner contractPartner : contractPartnerList) {
//                                        if (!contractPartner.getContractHeadId().equals(contractVo.getContractHeadId())) {
//                                            continue;
//                                        }
//                                        if (findContractNum) {
//                                            break;
//                                        }
//
//                                        Long contractPartnerOUId = contractPartner.getOuId();
//                                        //todo
//                                        if (hitContractFlag && Objects.equals(contractVo.getBuId(), contractPartnerOUId)) {
//                                            /*判断合同是否某些字段为空，为空则跳过该数据*/
//                                            requirementLineVO.setContractCode(contractVo.getContractCode());
//                                            List<ContractVo> list = requirementLineVO.getContractVoList();
//                                            if (null == list || list.isEmpty()) {
//                                                list = new ArrayList<>();
//                                            }
//                                            list.add(contractVo);
//                                            requirementLineVO.setContractVoList(list);
//                                            findContractNum = true;
//                                            break;
//                                        }
//                                    }
//                                }
//                            }
//                            catalogRequirementLineList.add(requirementLineVO);
//                        }
//                    }
//                }
//            }
//            if (isCatalog && havePrice) {
//                continue;
//            } else {
//                log.info("采购申请号为:[" + requirementLine.getRequirementHeadNum() + "],行号为:[" + requirementLine.getRowNum() + "],为非目录化物料,无法目录化转订单");
//            }
//        }
//
//        if (CollectionUtils.isNotEmpty(catalogRequirementLineList)) {
//            /*判断是否有供应商*/
//            for (int i = 0; i < catalogRequirementLineList.size(); i++) {
//                RequirementLineVO requirementLineVO = catalogRequirementLineList.get(i);
//                if (Objects.isNull(requirementLineVO.getVendorId())) {
//                    throw new BaseException(LocaleHandler.getLocaleMsg("采购申请号为:[" + requirementLineVO.getRequirementHeadNum() + "],行号为:[" + requirementLineVO.getRowNum() + "],无指定供应商,无法目录化转订单"));
//                }
//            }
//            /*根据 供应商id,物料小类id,业务实体id 查询供应商组织品类关系 - 供应商组织品类关系判断方法【认证中，绿牌，黄牌，一次性】*/
//            List<RequirementLineVO> requirementLineVOList = new ArrayList<>();
//            for (int i = 0; i < catalogRequirementLineList.size(); i++) {
//                RequirementLineVO requirementLineVO = catalogRequirementLineList.get(i);
//                OrgCategory orgCategoryItem = null;
//                for (OrgCategory orgCategory : orgCategoryList) {
//                    if (null != orgCategory
//                            && null != requirementLineVO.getOrgId() && Objects.equals(orgCategory.getOrgId(), requirementLineVO.getOrgId())
//                            && null != requirementLineVO.getCategoryId() && Objects.equals(orgCategory.getCategoryId(), requirementLineVO.getCategoryId())
//                            && null != requirementLineVO.getVendorId() && Objects.equals(orgCategory.getCompanyId(), requirementLineVO.getVendorId())
//                            && null != orgCategory.getServiceStatus()
//                            && (orgCategory.getServiceStatus().equals(CategoryStatus.VERIFY.name()) || orgCategory.getServiceStatus().equals(CategoryStatus.ONE_TIME.name()) || orgCategory.getServiceStatus().equals(CategoryStatus.GREEN.name()) || orgCategory.getServiceStatus().equals(CategoryStatus.YELLOW.name()))
//                    ) {
//                        orgCategoryItem = orgCategory;
//                        requirementLineVOList.add(requirementLineVO);
//                        break;
//                    }
//                }
//                if (orgCategoryItem == null) {
//                    log.info("采购申请编号：[" + requirementLineVO.getRequirementHeadNum() + "]; 采购申请行号：[" + requirementLineVO.getRowNum() + "] 无有效供应商组织品类关系，请检查。");
//                    continue;
//                }
//            }
//
//            List<List<RequirementLineVO>> lists = new ArrayList<>();
//            /*相同业务实体、相同库存组织、相同供应商、相同币种、相同税率、相同付款条款、相同项目可汇总合并成同一个订单*/
//            requirementLineVOList.forEach(item -> {
//                boolean add = true;
//                for (int i = 0; i < lists.size(); i++) {
//                    List<RequirementLineVO> list = lists.get(i);
//                    if (list.size() > 0) {
//                        RequirementLineVO requirementLineVO = list.get(0);
//                        if (ifCommonGroup2(requirementLineVO, item)) {
//                            list.add(item);
//                            add = false;
//                            break;
//                        }
//                    }
//                }
//                if (add) {
//                    List<RequirementLineVO> list = new ArrayList<>();
//                    list.add(item);
//                    lists.add(list);
//                }
//            });
//            /*创建订单*/
//            for (List<RequirementLineVO> list : lists) {
//                OrderSaveRequestDTO orderSaveRequestDTO = buildOrderForTranfer(list, requirementHead);
//                Long orderId = orderService.save(orderSaveRequestDTO);
//                /*回写采购订单单号*/
//                Order order = supcooperateClient.getOrderById(orderId);
//                List<RequirementLine> updateRequirementLineList = new ArrayList<>();
//                for (RequirementLineVO requirementLineVO : list) {
//                    RequirementLine requirementLine = new RequirementLine().setRequirementLineId(requirementLineVO.getRequirementLineId()).setOrderNumber(order.getOrderNumber());
//                    updateRequirementLineList.add(requirementLine);
//                }
//                iRequirementLineService.updateBatchById(updateRequirementLineList);
//            }
//
//        }
//    }
//
//    /**
//     * 目录化订单分组规则：
//     * 业务实体id + 库存组织id + 供应商id + 币种编码 + 税率编码 + 相同付款条款 + 相同项目编号
//     *
//     * 非目录化订单分组规则：
//     * 业务实体id + 库存组织id + 供应商id + 币种编码 + 税率编码 + 相同付款条款 + 相同项目编号 + 相同采购履行人id
//     *
//     * @param requirementManageDTOS
//     */
//    @Override
//    public void submitPurchaseOrderNew(List<RequirementManageDTO> requirementManageDTOS) {
//        List<String> numList = requirementManageDTOS.stream().map(item -> item.getRequirementHeadNum()).collect(Collectors.toList());
//        List<Integer> rowNumList = requirementManageDTOS.stream().map(item -> item.getRowNum()).collect(Collectors.toList());
//        log.info("采购申请转订单提交开始，采购申请编号为：[" + JsonUtil.arrayToJsonStr(numList) + "]，行号为：[" + JsonUtil.arrayToJsonStr(rowNumList) + "]");
//
//        //检验数据是否为空
//        checkIfNull(requirementManageDTOS);
//
//        //检验是否满足提交条件
//        checkIfSubmit(requirementManageDTOS);
//
//        //根据是否目录化进行分组
//        List<RequirementManageDTO> categoryList = new LinkedList<RequirementManageDTO>();
//        List<RequirementManageDTO> notCategoryList = new LinkedList<RequirementManageDTO>();
//        for(int i=0;i<requirementManageDTOS.size();i++){
//            RequirementManageDTO r = requirementManageDTOS.get(i);
//            if("Y".equals(r.getCeeaIfDirectory())){
//                categoryList.add(r);
//            }else{
//                notCategoryList.add(r);
//            }
//        }
//
//        //处理目录化的数据  业务实体id + 库存组织id + 供应商id + 币种编码 + 税率编码 + 相同付款条款 + 相同项目编号
//        Map<String,List<RequirementManageDTO>> categoryMap = new HashMap<>();
//        if(CollectionUtils.isNotEmpty(categoryList)){
//            categoryMap = groupCategoryMap(categoryList);
//        }
//
//        //处理非目录化的数据   业务实体id + 库存组织id + 供应商id + 币种编码 + 税率编码 + 相同付款条款 + 相同项目编号 + 相同采购履行人id
//        Map<String,List<RequirementManageDTO>> notCategoryMap = new HashMap<>();
//        if(CollectionUtils.isNotEmpty(notCategoryList)){
//            notCategoryMap = groupNotCategoryMap(notCategoryList);
//        }
//
//        //目录化申请转订单
//        for(Map.Entry<String,List<RequirementManageDTO>> entry : categoryMap.entrySet()){
//            List<RequirementManageDTO> requirementManageDTOList = entry.getValue();
//            if(CollectionUtils.isNotEmpty(requirementManageDTOList)){
//                OrderSaveRequestDTO orderSaveRequestDTO = buildOrder(requirementManageDTOList);
//                Long orderId = orderService.save(orderSaveRequestDTO);
//                /*回写采购订单单号*/
//                Order order = supcooperateClient.getOrderById(orderId);
//                List<RequirementLine> requirementLineList = new ArrayList<>();
//                for (RequirementManageDTO requirementManageDTO : requirementManageDTOList) {
//                    RequirementLine requirementLine = new RequirementLine()
//                            .setRequirementLineId(requirementManageDTO.getRequirementLineId())
//                            .setIfCreateFollowForm(YesOrNo.YES.getValue())
//                            .setOrderNumber(order.getOrderNumber());
//                    requirementLineList.add(requirementLine);
//                }
//                iRequirementLineService.updateBatchById(requirementLineList);
//            }
//        }
//
//        //非目录化申请转订单
//        for(Map.Entry<String,List<RequirementManageDTO>> entry : notCategoryMap.entrySet()){
//            List<RequirementManageDTO> requirementManageDTOList = entry.getValue();
//            if(CollectionUtils.isNotEmpty(requirementManageDTOList)){
//                OrderSaveRequestDTO orderSaveRequestDTO = buildOrder(requirementManageDTOList);
//                Long orderId = orderService.save(orderSaveRequestDTO);
//                /*回写采购订单单号*/
//                Order order = supcooperateClient.getOrderById(orderId);
//                List<RequirementLine> requirementLineList = new ArrayList<>();
//                for (RequirementManageDTO requirementManageDTO : requirementManageDTOList) {
//                    RequirementLine requirementLine = new RequirementLine()
//                            .setRequirementLineId(requirementManageDTO.getRequirementLineId())
//                            .setIfCreateFollowForm(YesOrNo.YES.getValue())
//                            .setOrderNumber(order.getOrderNumber());
//                    requirementLineList.add(requirementLine);
//                }
//                iRequirementLineService.updateBatchById(requirementLineList);
//            }
//        }
//        log.info("采购申请转订单提交结束，采购申请编号为：[" + JsonUtil.arrayToJsonStr(numList) + "]，行号为：[" + JsonUtil.arrayToJsonStr(rowNumList) + "]");
//
//
//    }
//
//    /**
//     * 分组目录化的数据：业务实体id + 库存组织id + 供应商id + 币种编码 + 税率编码 + 相同付款条款 + 相同项目编号
//     * @param requirementManageDTOList
//     * @return
//     */
//    private Map<String,List<RequirementManageDTO>> groupCategoryMap(List<RequirementManageDTO> requirementManageDTOList){
//        //获取付款条款唯一值
//        requirementManageDTOList.forEach(r -> {
//            List<OrderPaymentProvision> orderPaymentProvisionList = r.getOrderPaymentProvisionList();
//            String orderPaymentTermId = null;
//            if(CollectionUtils.isNotEmpty(orderPaymentProvisionList)){
//                orderPaymentTermId = getOrderPaymentTermId(orderPaymentProvisionList);
//            }
//            r.setPaymentTermId(orderPaymentTermId);
//        });
//
//        //业务实体id + 库存组织id + 供应商id + 币种编码 + 税率编码 + 相同付款条款 + 相同项目编号 作为唯一key值
//        Map<String,List<RequirementManageDTO>> map = new HashMap<>();
//        for(int i=0;i<requirementManageDTOList.size();i++){
//            RequirementManageDTO r = requirementManageDTOList.get(i);
//            String key = new StringBuffer()
//                    .append(r.getOrgId())
//                    .append(r.getOrganizationId())
//                    .append(r.getVendorId())
//                    .append(r.getCurrencyCode())
//                    .append(r.getTaxKey())
//                    .append(r.getPaymentTermId())
//                    .append(r.getCeeaProjectNum()).toString();
//            if(Objects.isNull(map.get(key))){
//                map.put(key,new LinkedList<RequirementManageDTO>(){{
//                    add(r);
//                }});
//            }else{
//                map.get(key).add(r);
//            }
//        }
//        return map;
//    }
//
//    /**
//     * 业务实体id + 库存组织id + 供应商id + 币种编码 + 税率编码 + 相同付款条款 + 相同项目编号 + 相同采购履行人id
//     * @param requirementManageDTOList
//     * @return
//     */
//    private Map<String,List<RequirementManageDTO>> groupNotCategoryMap(List<RequirementManageDTO> requirementManageDTOList){
//        //获取付款条款唯一值
//        requirementManageDTOList.forEach(r -> {
//            List<OrderPaymentProvision> orderPaymentProvisionList = r.getOrderPaymentProvisionList();
//            String orderPaymentTermId = null;
//            if(CollectionUtils.isNotEmpty(orderPaymentProvisionList)){
//                orderPaymentTermId = getOrderPaymentTermId(orderPaymentProvisionList);
//            }
//            r.setPaymentTermId(orderPaymentTermId);
//        });
//
//
//        //业务实体id + 库存组织id + 供应商id + 币种编码 + 税率编码 + 相同付款条款 + 相同项目编号 + 相同采购履行人id 作为唯一key值
//        Map<String,List<RequirementManageDTO>> map = new HashMap<>();
//        for(int i=0;i<requirementManageDTOList.size();i++){
//            RequirementManageDTO r = requirementManageDTOList.get(i);
//            String key = new StringBuffer()
//                    .append(r.getOrgId())
//                    .append(r.getOrganizationId())
//                    .append(r.getVendorId())
//                    .append(r.getCurrencyCode())
//                    .append(r.getTaxKey())
//                    .append(r.getPaymentTermId())
//                    .append(r.getCeeaProjectNum())
//                    .append(r.getCeeaPerformUserId()).toString();
//            if(Objects.isNull(map.get(key))){
//                map.put(key,new LinkedList<RequirementManageDTO>(){{
//                    add(r);
//                }});
//            }else{
//                map.get(key).add(r);
//            }
//        }
//        return map;
//    }
//
//    private String getOrderPaymentTermId(List<OrderPaymentProvision> orderPaymentProvisionList){
//        Comparator<OrderPaymentProvision> comparator = new Comparator<OrderPaymentProvision>() {
//            @Override
//            public int compare(OrderPaymentProvision o1, OrderPaymentProvision o2) {
//                return getOrderPaymentTermId(o1).compareTo(getOrderPaymentTermId(o2));
//            }
//        };
//        orderPaymentProvisionList.sort(comparator);
//        StringBuffer id = new StringBuffer();
//        orderPaymentProvisionList.forEach(e -> {
//            String orderPaymentTermId = getOrderPaymentTermId(e);
//            id.append(orderPaymentTermId);
//        });
//        return id.toString();
//    }
//
//    private String getOrderPaymentTermId(OrderPaymentProvision orderPaymentProvision){
//        return new StringBuffer().append(orderPaymentProvision.getPaymentTerm())
//                .append(orderPaymentProvision.getPaymentPeriod())
//                .append(orderPaymentProvision.getPaymentWay()).toString();
//    }
//
//
//    @Transactional(rollbackFor = Exception.class)
//    public OrderSaveRequestDTO buildOrderForTranfer(List<RequirementLineVO> requirementLineVOList, RequirementHead requirementHead) {
//        Date date = new Date();
//        Order order = new Order();
//        List<OrderDetail> orderDetailList = new ArrayList<>();
//        List<OrderAttach> orderAttachList = new ArrayList<>();
//        List<OrderPaymentProvision> orderPaymentProvisionList = new ArrayList<>();
//        /*获取付款条款*/
//        if (CollectionUtils.isNotEmpty(requirementLineVOList)) {
//            RequirementLineVO requirementLineVO = requirementLineVOList.get(0);
//            orderPaymentProvisionList = requirementLineVO.getOrderPaymentProvisionList();
//        }
//        /*获取订单类型的条目标识*/
//        String ceeaOrderTypeIdentification = null;
//        List<DictItemDTO> dictItemDTOList = baseClient.listAllByParam(new DictItemDTO().setDictCode("ORDER_TYPE"));
//        for (DictItemDTO dictItemDTO : dictItemDTOList) {
//            if (requirementHead.getCeeaPurchaseType().equals(dictItemDTO.getDictItemCode())) {
//                ceeaOrderTypeIdentification = dictItemDTO.getDictItemMark();
//            }
//        }
//        if (StringUtils.isBlank(ceeaOrderTypeIdentification)) {
//            throw new BaseException(LocaleHandler.getLocaleMsg("条目标识为空"));
//        }
//        /*获取采购员名称，采购员工号*/
//        UserPermissionDTO userPermissionDTO = rbacClient.getByBuyer(requirementHead.getCreatedId());
//
//
//        for (int i = 0; i < requirementLineVOList.size(); i++) {
//            RequirementLineVO item = requirementLineVOList.get(i);
//            Organization organization = baseClient.getOrganization(new Organization().setOrganizationId(item.getOrgId()));
//            /*获取合同编号*/
//            order.setVendorId(item.getVendorId())
//                    .setVendorName(item.getVendorName())
//                    .setVendorCode(item.getVendorCode())
//                    .setCeeaIfSupplierConfirm("Y")  //是否供应商确认
//                    .setCeeaIfConSignment("N")  //是否寄售
//                    .setCeeaIfPowerStationBusiness("N") //是否电站业务
//                    .setCeeaOrgId(item.getOrgId())  //业务实体ID
//                    .setCeeaOrgCode(item.getOrgCode())  //业务实体编码
//                    .setCeeaOrgName(item.getOrgName())  //业务实体名称
//                    .setCeeaDepartmentId(requirementHead.getCeeaDepartmentId())  //部门ID
//                    .setCeeaDepartmentCode(null)  //部门编码(需求无部门编码)
//                    .setCeeaDepartmentName(requirementHead.getCeeaDepartmentName())  //部门名称
//                    .setCeeaOrderTypeIdentification(ceeaOrderTypeIdentification)  //条目标识
//                    .setOrderType(requirementHead.getCeeaPurchaseType())
//                    .setCeeaReceiveOrderAddress(organization.getCeeaReceivingLocation())//收单地址
//                    .setCeeaSaveId(item.getCreatedId())  //保存人id
//                    .setCeeaSaveBy(item.getCreatedBy())  //保存人名称
//                    .setCeeaSaveDate(date)  //保存时间
//                    .setCeeaEmpUseId(userPermissionDTO.getUser().getUserId())    //采购员id
//                    .setCeeaEmpUsername(userPermissionDTO.getUser().getNickname())   //采购员名称
//                    .setCeeaEmpNo(userPermissionDTO.getUser().getCeeaEmpNo())    //采购员工号
//                    .setCreatedBy(item.getCreatedBy())
//                    .setCreatedId(item.getCreatedId())
//                    .setCreationDate(date)
//                    .setCeeaReceiveAddress(organization.getOrganizationSite()) //收货地点
//                    .setIfSample("N") //是否样品小批量订单
//                    .setSourceSystem(SourceSystemEnum.DEMAND.getValue()); //订单来源
//
//            OrderDetail orderDetail = new OrderDetail()
//                    .setLineNum(i + 1)   //订单行号
//                    .setCeeaPlanReceiveDate(Date.from(item.getRequirementDate().atStartOfDay(ZoneId.systemDefault()).toInstant()))
//                    .setCeeaPromiseReceiveDate(new Date())  // todo 承诺到货日期 = 当前日期 + 供货周期
//                    .setCeeaUnitNoTaxPrice(item.getPriceLibrary().getNotaxPrice())  //不含税单价
//                    .setCeeaUnitTaxPrice(item.getPriceLibrary().getTaxPrice())   //含税单价
//                    .setCurrencyId(item.getPriceLibrary().getCurrencyId())    //币种id
//                    .setCurrencyCode(item.getPriceLibrary().getCurrencyCode())   //币种编码
//                    .setCurrencyName(item.getPriceLibrary().getCurrencyName())  //币种名称
//                    .setCeeaContractNo(item.getContractCode())  //合同编号 todo
//                    .setCeeaBusinessSmall(item.getCeeaBusinessSmall())//业务小类
//                    .setCeeaBusinessSmallCode(item.getCeeaBusinessSmallCode())//业务小类编码
//                    .setOrderDetailId(IdGenrator.generate())
//                    .setCategoryId(item.getCategoryId()) //采购分类ID(物料小类id)
//                    .setCategoryName(item.getCategoryName()) //采购分类全名(物料小类名称)
//                    .setMaterialId(item.getMaterialId())  //物料ID
//                    .setMaterialCode(item.getMaterialCode())  //物料编码
//                    .setMaterialName(item.getMaterialName())  //物料名称
//                    .setReceiptPlace(item.getCeeaDeliveryPlace())  //收货地点
//                    .setUnit(item.getUnitCode())  //单位
//                    .setUnitCode(item.getUnitCode())
//                    .setOrderNum(item.getOrderQuantity())  //订单数量(本次下单数量)
//                    .setRequirementDate(item.getRequirementDate()) //需求日期
//                    .setRequirementQuantity(item.getRequirementQuantity()) //需求数量(本次下单数量)
//                    .setCurrency(item.getCurrency()) //币种
//                    .setComments(item.getComments())  //备注
//                    .setTenantId(0L)
//                    .setVersion(0L)
//                    .setReceiveSum(new BigDecimal(0)) //订单累计收货量
//                    .setCeeaRequirementLineId(item.getRequirementLineId()) //采购申请物料行主键id
//                    .setCeeaRequirementHeadNum(item.getRequirementHeadNum())  //采购申请编号(longi)
//                    .setCeeaRowNum(StringUtil.StringValue(i + 1))  //
//                    .setCeeaOrganizationId(item.getOrganizationId()) //库存组织ID
//                    .setCeeaOrganizationName(item.getOrganizationName())  //库存组织名称
//                    .setCeeaOrganizationCode(item.getOrganizationCode()) //库存组织编码
//                    .setCeeaFirstApprovedNum(new BigDecimal(0)) //第一次审批通过数量(原订单数量)
//                    .setCeeaApprovedNum(new BigDecimal(0))  //审批通过数量
//                    .setCeeaTaxRate(new BigDecimal(item.getPriceLibrary().getTaxRate()))  //税率
//                    .setCeeaTaxKey(item.getPriceLibrary().getTaxKey())  //税码
//                    .setCeeaIfRequirement("Y"); //物料行是否未采购需求
//            if (orderDetail.getOrderNum() == null) {
//                orderDetail.setOrderNum(new BigDecimal(0));
//            }
//            if (orderDetail.getCeeaUnitTaxPrice() == null) {
//                orderDetail.setCeeaUnitTaxPrice(new BigDecimal(0));
//            }
//            if (orderDetail.getCeeaUnitNoTaxPrice() == null) {
//                orderDetail.setCeeaUnitNoTaxPrice(new BigDecimal(0));
//            }
//            BigDecimal ceeaTaxRate = orderDetail.getCeeaTaxRate().divide(new BigDecimal(100), 4, BigDecimal.ROUND_HALF_UP).add(new BigDecimal(1));
//            orderDetail.setCeeaUnitNoTaxPrice(orderDetail.getCeeaUnitTaxPrice().divide(ceeaTaxRate, 2, BigDecimal.ROUND_HALF_UP));
//
//            BigDecimal ceeaAmountIncludingTax = orderDetail.getOrderNum().multiply(orderDetail.getCeeaUnitTaxPrice());
//            BigDecimal ceeaAmountExcludingTax = orderDetail.getOrderNum().multiply(orderDetail.getCeeaUnitNoTaxPrice());
//            BigDecimal ceeaTaxAmount = ceeaAmountExcludingTax.subtract(ceeaAmountIncludingTax);
//            orderDetail.setCeeaAmountIncludingTax(ceeaAmountIncludingTax)  //含税金额
//                    .setCeeaAmountExcludingTax(ceeaAmountExcludingTax)  //不含税金额
//                    .setCeeaTaxAmount(ceeaTaxAmount);  //税额
//
//            orderDetailList.add(orderDetail);
//        }
//        /*计算合计数量，合计金额含税，合计金额不含税*/
//        BigDecimal ceeaTotalNum = BigDecimal.ZERO;
//        BigDecimal ceeaTaxAmount = BigDecimal.ZERO;
//        BigDecimal ceeaNoTaxAmount = BigDecimal.ZERO;
//        for (OrderDetail orderDetail : orderDetailList) {
//            ceeaTotalNum = ceeaNoTaxAmount.add(orderDetail.getOrderNum());
//            ceeaTaxAmount = ceeaTaxAmount.add(orderDetail.getCeeaAmountExcludingTax());
//            ceeaNoTaxAmount = ceeaNoTaxAmount.add(orderDetail.getCeeaAmountExcludingTax());
//        }
//
//        order.setTenantId(0L)
//                .setVersion(0L)
//                .setOrderStatus(PurchaseOrderEnum.DRAFT.getValue())
//                .setCeeaPurchaseOrderDate(date)
//                .setCeeaTotalNum(ceeaTotalNum)
//                .setCeeaTaxAmount(ceeaTaxAmount)
//                .setCeeaNoTaxAmount(ceeaNoTaxAmount);
//
//        /*防止无订单明细的订单生成*/
//        if (CollectionUtils.isEmpty(orderDetailList)) {
//            throw new BaseException(LocaleHandler.getLocaleMsg("请检查本次下单数量"));
//        }
//
//        /*获取物料大类*/
//        List<Long> materialIds = orderDetailList.stream().map(item -> item.getMaterialId()).collect(Collectors.toList());
//        List<MaterialMaxCategoryVO> materialMaxCategoryVOList = baseClient.queryCategoryMaxCodeByMaterialIds(materialIds);
//        Map<Long, MaterialMaxCategoryVO> map = materialMaxCategoryVOList.stream().collect(Collectors.toMap(e -> e.getMaterialId(), e -> e));
//        for (int i = 0; i < orderDetailList.size(); i++) {
//            OrderDetail orderDetail = orderDetailList.get(i);
//            MaterialMaxCategoryVO materialMaxCategoryVO = map.get(orderDetail.getMaterialId());
//            if (materialMaxCategoryVO != null) {
//                orderDetail.setBigCategoryCode(materialMaxCategoryVO.getCategoryCode());
//            }
//        }
//
//        return new OrderSaveRequestDTO()
//                .setOrder(order)
//                .setAttachList(orderAttachList)
//                .setDetailList(orderDetailList)
//                .setPaymentProvisionList(orderPaymentProvisionList);
//
//    }
//
//    /**
//     * 相同业务实体、相同库存组织、相同供应商、相同币种、相同税率、相同付款条款、相同项目(先不管) 可汇总合并
//     *
//     * @param requirementLineVO1
//     * @param requirementLineVO2
//     * @return
//     */
//    private boolean ifCommonGroup2(RequirementLineVO requirementLineVO1, RequirementLineVO requirementLineVO2) {
//        /*业务实体*/
//        if (requirementLineVO1.getOrgId().longValue() != requirementLineVO2.getOrgId().longValue()) {
//            return false;
//        }
//        /*库存组织*/
//        if (requirementLineVO1.getOrganizationId().longValue() != requirementLineVO2.getOrganizationId().longValue()) {
//            return false;
//        }
//        /*供应商*/
//        if (!Objects.equals(requirementLineVO1.getVendorId(), requirementLineVO2.getVendorId())) {
//            return false;
//        }
//        /*相同币种*/
//        if (!Objects.equals(requirementLineVO1.getCurrencyId(), requirementLineVO2.getCurrencyId())) {
//            return false;
//        }
//        /*相同税率*/ //TODO
//        if (Optional.ofNullable(requirementLineVO1.getTaxRate()).orElse(new BigDecimal(BigInteger.ZERO)).compareTo(Optional.ofNullable(requirementLineVO2.getTaxRate()).orElse(new BigDecimal(BigInteger.ZERO))) != 0) {
//            return false;
//        }
//        /*相同付款条款*/
//        return checkIfCommonPaymentTerm(requirementLineVO1, requirementLineVO2) != false;
//    }
//
//    private boolean checkIfCommonPaymentTerm(RequirementLineVO requirementLineVO1, RequirementLineVO requirementLineVO2) {
//        List<OrderPaymentProvision> orderPaymentProvisionList1 = requirementLineVO1.getOrderPaymentProvisionList();
//        List<OrderPaymentProvision> orderPaymentProvisionList2 = requirementLineVO2.getOrderPaymentProvisionList();
//        if (orderPaymentProvisionList1.size() != orderPaymentProvisionList2.size()) {
//            return false;
//        }
//        if (!orderPaymentProvisionList1.containsAll(orderPaymentProvisionList2)) {
//            return false;
//        }
//        return orderPaymentProvisionList2.containsAll(orderPaymentProvisionList1);
//    }
//
//    @Override
//    @Transactional
//    public void reject(Long requirementHeadId, String rejectReason) {
//        this.updateById(new RequirementHead().setRequirementHeadId(requirementHeadId).
//                setAuditStatus(RequirementApproveStatus.REJECTED.getValue()).setApproveOpinion(rejectReason));
//    }
//
//    /**
//     * <pre>
//     *  审批撤回
//     * </pre>
//     *
//     * @author chenwt24@meicloud.com
//     * @version 1.00.00
//     *
//     * <pre>
//     *  修改记录
//     *  修改后版本:
//     *  修改人:
//     *  修改日期: 2020-10-10
//     *  修改内容:
//     * </pre>
//     */
//    @Override
//    @Transactional
//    public void withdraw(Long requirementHeadId, String rejectReason) {
//        this.updateById(new RequirementHead().setRequirementHeadId(requirementHeadId).
//                setAuditStatus(RequirementApproveStatus.WITHDRAW.getValue()).setApproveOpinion(rejectReason));
//    }
//
//    @Override
//    @Transactional
//    public void abandon(Long requirementHeadId) {
//        PurchaseRequirementDTO purchaseRequirementDTO = this.getByHeadId(requirementHeadId);
//        RequirementHead requirementHead = purchaseRequirementDTO.getRequirementHead();
//        //RequirementHead requirementHead = this.getById(requirementHeadId);
//        if (requirementHead == null) {
//            throw new BaseException(LocaleHandler.getLocaleMsg("审批状态为'已审批'的采购需求申请不可进行废弃"));
//        }
//        if (RequirementApproveStatus.APPROVED.getValue().equals(requirementHead.getAuditStatus())) {
//            throw new BaseException(LocaleHandler.getLocaleMsg("审批状态为'已审批'的采购需求申请不可进行废弃"));
//        }
//        this.updateApprovelStatus(requirementHeadId, RequirementApproveStatus.ABANDONED.getValue());
//
//        SrmFlowBusWorkflow srmworkflowForm = baseClient.getSrmFlowBusWorkflow(requirementHeadId);
//        if (srmworkflowForm != null) {
//            //接入废弃审批流
//            try {
//                purchaseRequirementDTO.setProcessType("N");
//                purchaseRequirementFlow.submitPurchaseRequirementConfFlow(purchaseRequirementDTO);
//            } catch (Exception e) {
//                Assert.isTrue(e == null, e.getMessage());
//            }
//        }
//
//    }
//
//    @Override
//    @Transactional
//    public FSSCResult submitApproval(PurchaseRequirementDTO purchaseRequirementDTO, String auditStatus) {
////        ToDo 预算校验先不管
////        for (RequirementLine line : requirementLineList) {
//////            //若该物料不存在未税单价,不需校验预算和总金额
//////            if (line.getNotaxPrice() == null) {
//////                continue;
//////            }
//////            //若该物料存在未税单价,校验预算和总金额数值必须大于0
//////            if (line.getBudget().compareTo(BigDecimal.ZERO) < 0 || line.getTotalAmount().compareTo(BigDecimal.ZERO) < 0) {
//////                throw new BaseException(LocaleHandler.getLocaleMsg("预算和总金额数值必须大于0,请检查"));
//////            }
//////            //总金额必须小于等于预算
//////            if(line.getBudget().compareTo(line.getTotalAmount()) < 0) {
//////                throw new BaseException(LocaleHandler.getLocaleMsg("总金额必须小于等于预算,请检查"));
//////            }
//////        }
//        long submitStartTime = System.currentTimeMillis();
//        log.info("------------------开始提交申请------------------------" + new Date());
//        RequirementHead requirementHead = purchaseRequirementDTO.getRequirementHead();
//        List<RequirementLine> requirementLineList = purchaseRequirementDTO.getRequirementLineList();
//        List<RequirementAttach> requirementAttaches = purchaseRequirementDTO.getRequirementAttaches();
//        //特殊品类跳出预算冻结
//        List<DictItemDTO> dictItemDTOS = baseClient.listAllByDictCode("SPECIAL_CATEGORY");
//        List<String> collect = new ArrayList<>();
//        Boolean flag = false;
//        if (CollectionUtils.isNotEmpty(dictItemDTOS)) {
//            collect = dictItemDTOS.stream().map(dictItemDTO -> (dictItemDTO.getDictItemCode())).collect(Collectors.toList());
//            Set<Long> set = requirementLineList.stream().map(requirementLine -> (requirementLine.getCategoryId())).collect(Collectors.toSet());
//            flag = set.size() == 1 && collect.contains(StringUtil.StringValue(set.iterator().next()));
//        }
//
//        //校验必需参数
//        checkRequiredParam(requirementHead, requirementLineList, requirementAttaches);
//
////        //自动根据品类分工规则进行分配
////        assignByDivisionCategory(purchaseRequirementDTO, requirementHead, requirementLineList);
//
//        /* Begin by chenwt24@meicloud.com   2020-09-26 */
//        //TODO 提交审批流程
////        String formId = null;
////        try {
////            formId = purchaseRequirementFlow.submitPurchaseRequirementConfFlow(purchaseRequirementDTO);
//
////            //判断是否启用工作流.
////            FormResultDTO formResultDTO = new FormResultDTO();
////            formResultDTO.setFormId(requirementHead.getRequirementHeadId());
////            UpdateWrapper<RequirementHead> updateWrapper = new UpdateWrapper<>(
////                    new RequirementHead().setRequirementHeadId(requirementHead.getRequirementHeadId()));
////            //拟定状态不进入工作流
////            if (!ApproveStatusType.DRAFT.getValue().equals(requirementHead.getHandleStatus())) {
////                Long menuId = purchaseRequirementDTO.getMenuId();
////                Permission menu = null;
////                if (menuId != null) {
////                    menu = rbacClient.getMenu(menuId);
////                }
////                if (menu != null) {
////                    //1.启用:进入工作流,
//////                    formResultDTO.setEnableWorkFlow(menu.getEnableWorkFlow());
//////                    formResultDTO.setFunctionId(menu.getFunctionId());
//////                    if (YesOrNo.YES.getValue().equals(menu.getEnableWorkFlow())) {
//////                        updateApproveStatus(updateWrapper, ApproveStatusType.DRAFT.getValue());
//////                        return formResultDTO;
//////                    }
////                    if (YesOrNo.YES.getValue().equals(menu.getEnableWorkFlow())) {
////                        formId = purchaseRequirementFlow.submitPurchaseRequirementConfFlow(purchaseRequirementDTO);
////                    }
////                }
////                //2.禁用:
////                updateVendorMainData(orgCateJournals, financeJournals, bankJournals);
////                //改变状态为已审批
////                updateApproveStatus(updateWrapper, ApproveStatusType.APPROVED.getValue());
////            }
//
////        } catch (Exception e) {
////            throw new BaseException(e.getMessage());
////        }
////        if(StringUtils.isEmpty(formId)){
////            throw new BaseException(LocaleHandler.getLocaleMsg("提交OA审批失败"));
////
////        }
//
//        /* End by chenwt24@meicloud.com     2020-09-26 */
//
//        if (requirementHead.getRequirementHeadId() == null) {
//            this.addPurchaseRequirement(purchaseRequirementDTO, auditStatus);
//        } else {
//            this.modifyPurchaseRequirement(purchaseRequirementDTO);
//            requirementHead.setAuditStatus(RequirementApproveStatus.SUBMITTED.getValue());
//            this.updateById(requirementHead);
//        }
//        long applyFreezeStartTime = System.currentTimeMillis();
//        log.info("-------------------------开始预算冻结-----------------------" + new Date());
//        //预算冻结
//        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
//        FSSCResult fsscResult = new FSSCResult();
//        //特殊物料小类需要跳过预算冻结
//        BgtCheckReqParamDto bgtCheckReqParamDto = new BgtCheckReqParamDto();
//        if (CategoryEnum.BIG_CATEGORY_SERVER.getCategoryCode().equals(requirementHead.getCategoryCode()) && !flag) {
//            List<Line> lineList = new ArrayList<>();
//            for (RequirementLine requirementLine : requirementLineList) {
//                if (requirementLine == null) continue;
//                convertRequirementLine(loginAppUser, requirementHead, lineList, requirementLine, StringUtil.StringValue(requirementLine.getTotalAmount()));
//            }
//            setBgtCheckReqParamDto(requirementHead, loginAppUser, bgtCheckReqParamDto, lineList);
//            fsscResult = iFSSCReqService.applyFreeze(bgtCheckReqParamDto);
//            log.info("请求返回参数:===============================================>" + JsonUtil.entityToJsonStr(fsscResult));
//            if (FSSCResponseCode.ERROR.getCode().equals(fsscResult.getCode())) {
//                throw new BaseException(fsscResult.getMsg());
//            }
//            //如果是WARNING的话,需要手动回滚事务,且返回对应结果给前端进行页面渲染
//            if (FSSCResponseCode.WARN.getCode().equals(fsscResult.getCode())) {
//                //手动回滚事务
//                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//                return fsscResult;
//            }
//        }
//        long applyFreezeEndTime = System.currentTimeMillis() - applyFreezeStartTime;
//        log.info("------------------------------结束预算冻结,开始进入工作流------------------------------耗时:" + applyFreezeEndTime);
//        //工作流提交,假如工作流提交失败,费控冻结预算成功,则需释放预算.
//        String formId = null;
//        try {
//            formId = purchaseRequirementFlow.submitPurchaseRequirementConfFlow(purchaseRequirementDTO);
//        } catch (Exception e) {
//            log.error("工作流提交异常:========================>", e);
//            //释放预算
//            fsscResult = iFSSCReqService.applyRelease(bgtCheckReqParamDto);
//            log.info("请求返回参数:===============================================>" + JsonUtil.entityToJsonStr(fsscResult));
//            if (FSSCResponseCode.ERROR.getCode().equals(fsscResult.getCode())) {
//                throw new BaseException(fsscResult.getMsg());
//            }
//            throw new BaseException(e.getMessage());
//        }
//        if (StringUtils.isEmpty(formId)) {
//            log.error("---------------------提交OA审批失败------------------------------");
//            //释放预算
//            fsscResult = iFSSCReqService.applyRelease(bgtCheckReqParamDto);
//            log.info("请求返回参数:===============================================>" + JsonUtil.entityToJsonStr(fsscResult));
//            if (FSSCResponseCode.ERROR.getCode().equals(fsscResult.getCode())) {
//                throw new BaseException(fsscResult.getMsg());
//            }
//            throw new BaseException(LocaleHandler.getLocaleMsg("提交OA审批失败"));
//        }
//        log.info("----------------------------结束提交采购申请--------------------耗时:" + (System.currentTimeMillis() - submitStartTime));
//        return fsscResult;
//    }
//
//
//    @Override
//    @Transactional
//    public BaseResult approvalMrp(PurchaseRequirementDTO purchaseRequirementDTO, String auditStatus) {
//        RequirementHead requirementHead = purchaseRequirementDTO.getRequirementHead();
//        List<RequirementLine> requirementLineList = purchaseRequirementDTO.getRequirementLineList();
//        List<RequirementAttach> requirementAttaches = purchaseRequirementDTO.getRequirementAttaches();
//        //校验必需参数
//        checkRequiredParam(requirementHead, requirementLineList, requirementAttaches);
//        if (null != purchaseRequirementDTO.getRequirementHead().getRequirementHeadNum()) {
//            QueryWrapper<RequirementHead> wrapper = new QueryWrapper<>();
//            wrapper.eq("REQUIREMENT_HEAD_NUM", purchaseRequirementDTO.getRequirementHead().getRequirementHeadNum());
//            RequirementHead temp = this.baseMapper.selectOne(wrapper);
//            if (null != temp) {
//                throw new BaseException("单号已存在！");
//            }
//        }
//
//        this.addPurchaseRequirementMrp(purchaseRequirementDTO, auditStatus);
//
//        PurchaseRequirementApsDTO dto = new PurchaseRequirementApsDTO();
//        BeanUtils.copyProperties(requirementHead, dto);
//        dto.setRequirementLineList(requirementLineList);
//
//        return BaseResult.build(ResultCode.SUCCESS, dto);
//    }
//
//    private void setBgtCheckReqParamDto(RequirementHead requirementHead, LoginAppUser loginAppUser, BgtCheckReqParamDto bgtCheckReqParamDto, List<Line> lineList) {
//        bgtCheckReqParamDto.setPriKey(requirementHead.getRequirementHeadId().toString());
//        bgtCheckReqParamDto.setSourceSystem(DataSourceEnum.NSRM_SYS.getKey());
//        bgtCheckReqParamDto.setGroupCode("LGi");
//        bgtCheckReqParamDto.setTemplateCode("Budget2020");
//        bgtCheckReqParamDto.setDocumentNum(requirementHead.getRequirementHeadNum());
//        bgtCheckReqParamDto.setBudgetUseStatus(BudgetUseStatus.F.name());
//        bgtCheckReqParamDto.setDocumentCreateBy(StringUtil.StringValue(loginAppUser.getCeeaEmpNo()));
//        bgtCheckReqParamDto.setLineList(lineList);
//        bgtCheckReqParamDto.setTransferTime(DateUtils.format(new Date(), DateUtils.DATE_FORMAT_19));
//        bgtCheckReqParamDto.setIgnore(requirementHead.getBudgetIgnore());
//    }
//
//    private void convertRequirementLine(LoginAppUser loginAppUser, RequirementHead requirementHead, List<Line> lineList, RequirementLine requirementLine, String documentLineAmount) {
//        Line line = new Line();
//        line.setLineId(requirementLine.getRequirementLineId().toString());
//        line.setDocumentLineNum(requirementHead.getRequirementHeadId().toString() + requirementLine.getRequirementLineId().toString());
//        line.setTemplateCode("Budget2020");
//        line.setDocumentLineAmount(documentLineAmount);
//        line.setSegment29(requirementHead.getCeeaDepartmentId());
//        line.setSegment30(requirementHead.getCeeaBusinessSmallCode());
//        line.setIgnore(requirementHead.getBudgetIgnore());
//        lineList.add(line);
//    }
//
//    @Transactional(rollbackFor = Exception.class)
//    public void assignByDivisionCategory(RequirementHead requirementHead, List<RequirementLine> requirementLineList) {
//        for (RequirementLine requirementLine : requirementLineList) {
//            List<DivisionCategory> divisionCategories = iDivisionCategoryService.list(new QueryWrapper<>(new DivisionCategory()
//                    .setOrgId(requirementHead.getOrgId())
//                    .setOrganizationId(requirementHead.getOrganizationId())
//                    .setCategoryId(requirementLine.getCategoryId())
//                    .setIfMainPerson(YesOrNo.YES.getValue())));
//            if (CollectionUtils.isNotEmpty(divisionCategories)) {
//                for (DivisionCategory divisionCategory : divisionCategories) {
//                    if (divisionCategory == null) continue;
//                    //设置采购策略负责人
//                    if (DUTY.Purchase_Strategy.name().equals(divisionCategory.getDuty())) {
//                        requirementLine.setCeeaStrategyUserId(divisionCategory.getPersonInChargeUserId())
//                                .setCeeaStrategyUserName(divisionCategory.getPersonInChargeUsername())
//                                .setCeeaStrategyUserNickname(divisionCategory.getPersonInChargeNickname());
//                    }
//                    //设置采购履行负责人
//                    if (DUTY.Carry_Out.name().equals(divisionCategory.getDuty())) {
//                        requirementLine.setCeeaPerformUserId(divisionCategory.getPersonInChargeUserId())
//                                .setCeeaPerformUserName(divisionCategory.getPersonInChargeUsername())
//                                .setCeeaPerformUserNickname(divisionCategory.getPersonInChargeNickname());
//                    }
//                }
//            }
//            //采购履行和采购策略都有负责人,则改变行状态为已分配
//            if (StringUtils.isNotEmpty(requirementLine.getCeeaStrategyUserName()) && StringUtils.isNotEmpty(requirementLine.getCeeaPerformUserName())) {
//                requirementLine.setApplyStatus(RequirementApplyStatus.ASSIGNED.getValue());
//            }
//            iRequirementLineService.updateById(requirementLine);
////            if (divisionCategory != null) {
////                requirementLine.setCeeaStrategyUserNickname(divisionCategory.getStrategyUserNickname())
////                        .setCeeaStrategyUserName(divisionCategory.getStrategyUserName())
////                        .setCeeaStrategyUserId(divisionCategory.getStrategyUserId())
////                        .setCeeaPerformUserNickname(divisionCategory.getPerformUserNickname())
////                        .setCeeaPerformUserName(divisionCategory.getPerformUserName())
////                        .setCeeaPerformUserId(divisionCategory.getPerformUserId())
////                        .setCeeaSupUserNickname(divisionCategory.getSupUserNickname())
////                        .setCeeaSupUserName(divisionCategory.getSupUserName())
////                        .setCeeaSupUserId(divisionCategory.getSupUserId())
////                        .setApplyStatus(RequirementApplyStatus.ASSIGNED.getValue());
////                iRequirementLineService.updateById(requirementLine);
////            }
//        }
//    }
//
//    @Override
//    public PageInfo<RequirementManageDTO> listPageByParam(RequirementManageDTO requirementManageDTO) {
//
//        log.info("---------------开始需求池查询-----------------------");
//        PageUtil.startPage(requirementManageDTO.getPageNum(), requirementManageDTO.getPageSize());
//        requirementManageDTO.setOrgIds(CollectionUtils.isEmpty(requirementManageDTO.getOrgIds()) ? null : requirementManageDTO.getOrgIds());
//        requirementManageDTO.setOrganizationIds(CollectionUtils.isEmpty(requirementManageDTO.getOrganizationIds()) ? null : requirementManageDTO.getOrganizationIds());
//        requirementManageDTO.setCeeaDepartmentIds(CollectionUtils.isEmpty(requirementManageDTO.getCeeaDepartmentIds()) ? null : requirementManageDTO.getCeeaDepartmentIds());
//        requirementManageDTO.setCreatedIds(CollectionUtils.isEmpty(requirementManageDTO.getCreatedIds()) ? null : requirementManageDTO.getCreatedIds());
//        requirementManageDTO.setMaterialIds(CollectionUtils.isEmpty(requirementManageDTO.getMaterialIds()) ? null : requirementManageDTO.getMaterialIds());
//        requirementManageDTO.setCategoryIds(CollectionUtils.isEmpty(requirementManageDTO.getCategoryIds()) ? null : requirementManageDTO.getCategoryIds());
//        requirementManageDTO.setRequirementHeadNums(CollectionUtils.isEmpty(requirementManageDTO.getRequirementHeadNums()) ? null : requirementManageDTO.getRequirementHeadNums());
//        requirementManageDTO.setRequirementHeadNum(StringUtils.isNotBlank(requirementManageDTO.getRequirementHeadNum()) ? requirementManageDTO.getRequirementHeadNum() : null);
//        requirementManageDTO.setMaterialCodes(CollectionUtils.isNotEmpty(requirementManageDTO.getMaterialCodes()) ? requirementManageDTO.getMaterialCodes() : null);
//        long startTime = System.currentTimeMillis();//开始时间
//        List<RequirementManageDTO> requirementManageDTOS = filterAuthData(requirementManageDTO); // 20201029 优化查询 by alan
//        long start1 = System.currentTimeMillis();
//        System.out.println("需求池查询id耗时" + (start1 - startTime));
//        PageInfo<RequirementManageDTO> pageInfo = new PageInfo<>(requirementManageDTOS); // 20201030 分页信息保存 by alan\
//        if (requirementManageDTOS.size() == 0) {
//            pageInfo.setList(requirementManageDTOS);
//            return pageInfo;
//        }
//        requirementManageDTOS = this.baseMapper.listPageByIds(requirementManageDTOS); // 20201029 优化查询 by alan
//        System.out.println("需求池数据查询:" + (System.currentTimeMillis() - start1));
//        //遍历需求，后后续单据中已有询价、招标、竞价，不可创建寻源单
//        Set<Long> requirementLineIds = new HashSet<>();
//        requirementManageDTOS.forEach(dto -> {
//            requirementLineIds.add(dto.getRequirementLineId());
//        });
//
//        //后续单号 TENDER/ENQUIRY/BIDDING 时候不允许创建寻源单据
//        if (CollectionUtils.isNotEmpty(requirementLineIds)) {
//            List<SubsequentDocuments> subsequentDocumentList = iSubsequentDocumentsService
//                    .list(new QueryWrapper<>(new SubsequentDocuments())
//                            .in("REQUIREMENT_LINE_ID", requirementLineIds));
//            requirementManageDTOS.forEach(dto -> {
//                dto.setCanCreateSourcing(YesOrNo.YES.getValue());
//                Iterator<SubsequentDocuments> subsequentIterator = subsequentDocumentList.iterator();
//                while (subsequentIterator.hasNext()) {
//                    SubsequentDocuments subsequentDocuments = subsequentIterator.next();
//                    boolean notAllayCreateSourcing = RelatedDocumentsEnum.TENDER.getName().equals(subsequentDocuments.getIsubsequentDocumentssType()) ||
//                            RelatedDocumentsEnum.ENQUIRY.getName().equals(subsequentDocuments.getIsubsequentDocumentssType()) ||
//                            RelatedDocumentsEnum.BIDDING.getName().equals(subsequentDocuments.getIsubsequentDocumentssType());
//                    boolean isCurrentDto = Objects.equals(dto.getRequirementLineId(), subsequentDocuments.getRequirementLineId());
//                    if (isCurrentDto && notAllayCreateSourcing) {
//                        dto.setCanCreateSourcing(YesOrNo.NO.getValue());
//                    }
//                }
//            });
//        }
//
//        //检验是否有有效价格和是否有货源供应商
//        checkHavePriceAndHaveSupplier(requirementManageDTOS);
//        long endTime = System.currentTimeMillis() / 1000 - startTime;
//        log.info("---------------------结束需求池查询--------------耗时:" + endTime + "s");
//        pageInfo.setList(requirementManageDTOS);
//        return pageInfo;
//    }
//
//    /**
//     * 过滤数据权限
//     *
//     * @param param
//     * @return
//     */
//    @AuthData(module = MenuEnum.DEMAND_POOL_MANAGEMENT)
//    private List<RequirementManageDTO> filterAuthData(RequirementManageDTO param) {
//        return this.baseMapper.listPageByParamNew(param);
////        return this.baseMapper.listPageByParam(param);
//    }
//
//    @AuthData(module = MenuEnum.DEMAND_POOL_MANAGEMENT)
//    private Long getCountByAuthData(RequirementManageDTO requirementManageDTO) {
//        return getBaseMapper().listPageByParamNewCount(requirementManageDTO);
//    }
//
//    /**
//     * 【过滤掉剩余可下单数量为0的采购申请】
//     * 【有指定的供应商的
//     * 根据 供应商id,物料小类id,业务实体id 查询供应商组织品类关系
//     * 有组织品类关系
//     * 根据 物料编码，物料名称，业务实体id，库存组织id，供应商id, 查询是否有有效价格，如果有赋值
//     * 如果没有 根据 物料编码，物料名称，供应商id，业务实体id，库存组织id 查询有效合同(查询到多个合同)，如果有赋值
//     * 如果没有 忽略该采购申请
//     * * 【有指定的供应商的】
//     * * 根据 供应商id,物料小类id,业务实体id 查询供应商组织品类关系
//     * *   有组织品类关系
//     * *      根据 物料编码，物料名称，业务实体id，库存组织id，供应商id, 查询是否有有效价格，如果有赋值
//     * *      如果没有 根据 物料编码，物料名称，供应商id，业务实体id，库存组织id 查询有效合同(查询到多个合同)，如果有赋值
//     * *      如果没有 忽略该采购申请
//     * 【无指定的供应商的】
//     * 根据物料小类id,业务实体id 查询供应商组织品类关系
//     * 判断是否有供应商组织品类关系，如果有
//     * 根据 物料编码，物料名称，业务实体id，库存组织id 查询是否有有效价格，如果有【拆单】并赋值
//     * 如果没有 根据 物料编码，物料名称，业务实体id，库存组织id 查询有效合同，如果有，根据供应商进行【分组】。
//     * 根据分组拆单并赋值
//     * 如果没有  忽略该采购申请
//     * PS：订单的创建人为：订单的创建人为采购履行人
//     *
//     * @param requirementManageDTOS
//     * @return
//     */
//    @Override
//    public List<RequirementManageDTO> createPurchaseOrder(List<RequirementManageDTO> requirementManageDTOS) {
//        checkBeforeCreatePurchaseOrder(requirementManageDTOS);
//        /*获取当前需求行的所有物料id*/
//        List<Long> itemIds = requirementManageDTOS.stream().map(item -> item.getMaterialId()).collect(Collectors.toList());
//        /*获取当前的所有业务实体id*/
//        List<Long> orgIds = requirementManageDTOS.stream().map(item -> item.getOrgId()).collect(Collectors.toList());
//
//        /*查询所有有效价格(优化方案：通过收集物料id优化)*/
//        List<PriceLibrary> priceLibraryAll = inqClient.listAllEffective(new PriceLibrary().setItemIds(itemIds));
//        /*查询所有供应商品类组织关系(根据业务实体ids 过滤)*/
//        List<OrgCategory> orgCategoryList = supplierClient.getOrgCategoryForCheck(new OrgCategoryQueryDTO().setOrgIds(orgIds));
//        /*查询出所有有效合同（通过收集物料id去优化）*/
//        List<ContractVo> contractVoList = contractClient.listAllEffectiveCM(new ContractItemDto().setMaterialIds(orgIds));
//        //合同的合作伙伴
//        List<Long> controlHeadIds = contractVoList.stream().map(c -> c.getContractHeadId()).collect(Collectors.toList());
//        List<ContractPartner> contractPartnerList = contractClient.listAllEffectiveCP(controlHeadIds);
//
//        String ifHasError = "Y";
//        StringBuffer errorString = new StringBuffer();
//        List<RequirementManageDTO> result = new ArrayList<>();
//        for (RequirementManageDTO item : requirementManageDTOS) {
//            /*过滤掉剩余可下单数量为0的行*/
//            if (item.getOrderQuantity() == null || item.getOrderQuantity().compareTo(BigDecimal.ZERO) == 0) {
//                continue;
//            }
//
//            if (item.getVendorId() == null) {
//                /*无指定供应商*/
//                /*根据物料小类id,业务实体id查询是否有供应商组织品类关系 - 供应商组织品类关系判断方法【认证中，绿牌，黄牌，一次性】*/
//                OrgCategory orgCategoryItem = null;
//                for (OrgCategory orgCategory : orgCategoryList) {
//                    if (null != orgCategory
//                            && null != item.getOrgId() && Objects.equals(orgCategory.getOrgId(), item.getOrgId())
//                            && null != item.getCategoryId() && Objects.equals(orgCategory.getCategoryId(), item.getCategoryId())
//                            && null != orgCategory.getServiceStatus()
//                            && (orgCategory.getServiceStatus().equals(CategoryStatus.VERIFY.name()) || orgCategory.getServiceStatus().equals(CategoryStatus.ONE_TIME.name()) || orgCategory.getServiceStatus().equals(CategoryStatus.GREEN.name()) || orgCategory.getServiceStatus().equals(CategoryStatus.YELLOW.name()))
//                    ) {
//                        orgCategoryItem = orgCategory;
//                        break;
//                    }
//                }
//                if (orgCategoryItem == null) {
//                    ifHasError = "N";
//                    errorString.append("采购申请编号：[" + item.getRequirementHeadNum() + "]; 采购申请行号：[" + item.getRowNum() + "] 无有效供应商组织品类关系，请检查。 \n");
//                    log.info("采购申请行：" + JsonUtil.entityToJsonStr(item) + " 无供应商组织品类关系");
//                    continue;
//                }
//                log.info("materialCode = " + item.getMaterialCode() + "  materialName = " + item.getMaterialName() + "  vendorName = " + item.getVendorName() + "的供应商组织品类关系为：" + JsonUtil.entityToJsonStr(orgCategoryItem));
//                /*根据物料编码，物料名称，业务实体id，库存组织id，交货地点 查询是否有有效价格*/
//                List<PriceLibrary> priceLibraryList = new ArrayList<>();
//                for (PriceLibrary priceLibrary : priceLibraryAll) {
//                    if (null != priceLibrary && StringUtils.isNotBlank(item.getMaterialName()) && item.getMaterialName().equals(priceLibrary.getItemDesc()) &&
//                            StringUtils.isNotBlank(item.getMaterialCode()) && item.getMaterialCode().equals(priceLibrary.getItemCode()) &&
//                            item.getOrgId() != null && !ObjectUtils.notEqual(item.getOrgId(), priceLibrary.getCeeaOrgId()) &&
//                            item.getOrganizationId() != null && Objects.equals(item.getOrganizationId(), priceLibrary.getCeeaOrganizationId()) &&
//                            StringUtils.isNotBlank(priceLibrary.getPriceType()) && "STANDARD".equals(priceLibrary.getPriceType()) &&
//                            Objects.equals(priceLibrary.getCeeaArrivalPlace(), item.getCeeaDeliveryPlace())
//
//                    ) {
//                        /*判断价格库是否某些字段为空，为空则跳过该数据*/
//                        if (priceLibrary.getNotaxPrice() == null ||
//                                priceLibrary.getTaxPrice() == null ||
//                                StringUtils.isBlank(priceLibrary.getTaxKey()) ||
//                                StringUtils.isBlank(priceLibrary.getTaxRate()) ||
//                                priceLibrary.getCurrencyId() == null ||
//                                StringUtils.isBlank(priceLibrary.getCurrencyCode()) ||
//                                StringUtils.isBlank(priceLibrary.getCurrencyName())
//                        ) {
//                            log.info("requirementHeadNum = " + item.getRequirementHeadNum() + "  rowNum:" + item.getRowNum() + " 价格库某些字段为空:" + JsonUtil.entityToJsonStr(priceLibrary));
//                        } else {
//                            RequirementManageDTO requirementManageDTO = new RequirementManageDTO();
//                            BeanUtils.copyProperties(item, requirementManageDTO);
//                            if (priceLibrary.getCeeaQuotaProportion() == null) {
//                                priceLibrary.setCeeaQuotaProportion(BigDecimal.ZERO);
//                            }
//                            BigDecimal quotaProportion = priceLibrary.getCeeaQuotaProportion().divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP);
//                            requirementManageDTO.setThisOrderQuantity(quotaProportion.multiply(item.getRequirementQuantity()));
//                            requirementManageDTO.setQuotaProportion(priceLibrary.getCeeaQuotaProportion());
//                            requirementManageDTO.setVendorId(priceLibrary.getVendorId());
//                            requirementManageDTO.setVendorName(priceLibrary.getVendorName());
//                            requirementManageDTO.setVendorCode(priceLibrary.getVendorCode());
//                            requirementManageDTO.setCurrencyId(priceLibrary.getCurrencyId());
//                            requirementManageDTO.setCurrencyCode(priceLibrary.getCurrencyCode());
//                            requirementManageDTO.setCurrencyName(priceLibrary.getCurrencyName());
//                            requirementManageDTO.setTaxRate(new BigDecimal(priceLibrary.getTaxRate()));
//                            requirementManageDTO.setTaxKey(priceLibrary.getTaxKey());
//                            requirementManageDTO.setTaxPrice(priceLibrary.getTaxPrice());
//                            requirementManageDTO.setNotaxPrice(priceLibrary.getNotaxPrice());
//                            requirementManageDTO.setMinOrderQuantity(priceLibrary.getMinOrderQuantity());
//                            priceLibraryList.add(priceLibrary);
//                            /*设置付款条款 - 根据价格库id查询付款条款，设置付款条款*/
//                            List<PriceLibraryPaymentTerm> priceLibraryPaymentTermList = inqClient.listPriceLibraryPaymentTerm(new PriceLibraryPaymentTerm().setPriceLibraryId(priceLibrary.getPriceLibraryId()));
//                            List<OrderPaymentProvision> orderPaymentProvisionList = new ArrayList<>();
//                            priceLibraryPaymentTermList.forEach(priceLibraryPaymentTerm -> {
//                                OrderPaymentProvision orderPaymentProvision = new OrderPaymentProvision()
//                                        .setPaymentPeriod(StringUtil.StringValue(priceLibraryPaymentTerm.getPaymentDay()))
//                                        .setPaymentWay(priceLibraryPaymentTerm.getPaymentWay())
//                                        .setPaymentTerm(priceLibraryPaymentTerm.getPaymentTerm());
//                                orderPaymentProvisionList.add(orderPaymentProvision);
//                            });
//                            requirementManageDTO.setOrderPaymentProvisionList(orderPaymentProvisionList);
//                            /*关联框架合同 业务实体id，库存组织id，供应商id，合同为框架协议*/
//                            /**
//                             * 取【框架协议】逻辑
//                             * 在价格库取框架协议。
//                             * 如果为空：
//                             * 根据以下条件去查询获取：
//                             *
//                             * （1）通过 合同合作伙伴的业务实体 = 采购申请的业务实体
//                             * （2）合同头的供应商 = 价格库的供应商
//                             * （3）合同为框架协议
//                             */
//                            if (!StringUtil.isEmpty(priceLibrary.getContractCode())) {
//                                requirementManageDTO.setContractNum(priceLibrary.getContractCode());
//                            } else {
//                                //添加比对合作伙伴 逻辑2020年11月10日
//                                for (ContractVo contractVo : contractVoList) {
//                                    boolean hitContractFlag =
////                                          null != contractVo &&
////                                          contractVo.getBuId() != null &&
////                                          !ObjectUtils.notEqual(contractVo.getBuId(), item.getOrgId())&&
//                                            contractVo.getHeadVendorId() != null &&
////                                          contractVo.getInvId() != null &&
////                                          Objects.equals(contractVo.getInvId(), item.getOrganizationId()) &&
//                                                    Objects.equals(contractVo.getHeadVendorId(), priceLibrary.getVendorId()) &&
//                                                    StringUtils.isNotBlank(contractVo.getIsFrameworkAgreement()) &&
//                                                    StringUtils.isNotBlank(contractVo.getContractCode()) &&
//                                                    contractVo.getIsFrameworkAgreement().equals("Y");
//                                    log.info("requirementHeadNum = " + item.getRequirementHeadNum() + "  rowNum:" + item.getRowNum() + " 合同信息为" + JsonUtil.entityToJsonStr(contractVo));
//
//                                    boolean findContractNum = false;
//                                    for (ContractPartner contractPartner : contractPartnerList) {
//                                        if (!contractPartner.getContractHeadId().equals(contractVo.getContractHeadId())) {
//                                            continue;
//                                        }
//                                        if (findContractNum) {
//                                            break;
//                                        }
//
//                                        Long contractPartnerOUId = contractPartner.getOuId();
//                                        //todo
//                                        if (hitContractFlag && Objects.equals(contractVo.getBuId(), contractPartnerOUId)) {
//                                            /*判断合同是否某些字段为空，为空则跳过该数据*/
//                                            requirementManageDTO.setContractNum(contractVo.getContractCode());
//                                            List<ContractVo> list = requirementManageDTO.getContractVoList();
//                                            if (null == list || list.isEmpty()) {
//                                                list = new ArrayList<>();
//                                            }
//                                            list.add(contractVo);
//                                            requirementManageDTO.setContractVoList(list);
//                                            findContractNum = true;
//                                            break;
//                                        }
//                                    }
//                                }
//                            }
//                            result.add(requirementManageDTO);
//                        }
//                    }
//                }
//                if (CollectionUtils.isEmpty(priceLibraryList)) {
//                    /*无有效价格，获取合同*/
//                    log.info("requirementHeadNum = " + item.getRequirementHeadNum() + "  rowNum:" + item.getRowNum() + "  materialCode:" + item.getMaterialCode() + " materialName:" + item.getMaterialName() + " 无有效价格");
//                    List<List<ContractVo>> contractVoLists = new ArrayList<>();
//                    for (ContractVo contractVo : contractVoList) {
//                        if (null != contractVo && contractVo.getBuId() != null && !ObjectUtils.notEqual(contractVo.getBuId(), item.getOrgId())
//                                && contractVo.getMaterialCode() != null && contractVo.getMaterialCode().equals(item.getMaterialCode())
//                                && contractVo.getMaterialName() != null && contractVo.getMaterialName().equals(item.getMaterialName())
//                                && contractVo.getInvId() != null && Objects.equals(contractVo.getInvId(), item.getOrganizationId())
//                                && StringUtils.isNotBlank(contractVo.getIsFrameworkAgreement()) && contractVo.getIsFrameworkAgreement().equals("N")
//                        ) {
//                            /*判断合同是否某些字段为空，为空则跳过该数据*/
//                            if (contractVo.getTaxedPrice() == null ||
//                                    StringUtils.isBlank(contractVo.getTaxKey()) ||
//                                    contractVo.getTaxRate() == null ||
//                                    contractVo.getCurrencyId() == null ||
//                                    StringUtils.isBlank(contractVo.getCurrencyCode()) ||
//                                    StringUtils.isBlank(contractVo.getCurrencyName()) ||
//                                    contractVo.getHeadVendorId() == null
//                            ) {
//                                log.info("requirementHeadNum = " + item.getRequirementHeadNum() + "  rowNum:" + item.getRowNum() + " 合同某些字段为空:" + JsonUtil.entityToJsonStr(contractVo));
//                            } else {
//                                /*计算未税单价*/
//                                BigDecimal taxRate = contractVo.getTaxRate().divide(new BigDecimal(100), 8, BigDecimal.ROUND_HALF_UP);
//                                BigDecimal untaxedPrice = contractVo.getTaxedPrice().divide((new BigDecimal(1).add(taxRate)), 2, BigDecimal.ROUND_HALF_UP);
//                                contractVo.setUntaxedPrice(untaxedPrice);
//
//                                /*将合同进行分组*/
//                                boolean addFlag = true;
//                                for (List<ContractVo> contractVos : contractVoLists) {
//                                    if (CollectionUtils.isNotEmpty(contractVos)) {
//                                        ContractVo firstContractVo = contractVos.get(0);
//                                        if (Objects.equals(firstContractVo.getHeadVendorId(), contractVo.getHeadVendorId())) {
//                                            contractVos.add(contractVo);
//                                            addFlag = false;
//                                            break;
//                                        }
//                                    }
//                                }
//                                if (addFlag) {
//                                    List<ContractVo> contractVos = new ArrayList<>();
//                                    contractVos.add(contractVo);
//                                    contractVoLists.add(contractVos);
//                                }
//                            }
//                        }
//                    }
//
//
//                    /*根据合同分组赋值*/
//                    if (CollectionUtils.isNotEmpty(contractVoLists)) {
//                        for (int i = 0; i < contractVoLists.size(); i++) {
//                            List<ContractVo> contractVos = contractVoLists.get(i);
//                            /*默认把第一个合同赋值给采购申请行*/
//                            ContractVo contractVo = contractVos.get(0);
//                            RequirementManageDTO requirementManageDTO = new RequirementManageDTO();
//                            BeanUtils.copyProperties(item, requirementManageDTO);
//                            requirementManageDTO.setVendorId(contractVo.getHeadVendorId());
//                            requirementManageDTO.setVendorName(contractVo.getHeadVendorName());
//                            requirementManageDTO.setVendorCode(contractVo.getHeadVendorCode());
//                            requirementManageDTO.setCurrencyId(contractVo.getCurrencyId());
//                            requirementManageDTO.setCurrencyCode(contractVo.getCurrencyCode());
//                            requirementManageDTO.setCurrencyName(contractVo.getCurrencyName());
//                            requirementManageDTO.setNotaxPrice(contractVo.getUntaxedPrice());
//                            requirementManageDTO.setTaxPrice(contractVo.getTaxedPrice());
//                            requirementManageDTO.setTaxKey(contractVo.getTaxKey());
//                            requirementManageDTO.setTaxRate(contractVo.getTaxRate());
//                            requirementManageDTO.setContractNum(contractVo.getContractCode());
//                            /*设置付款条款 - 根据合同id查询付款条款，设置付款条款*/
//                            List<PayPlan> payPlanList = contractClient.list(new PayPlan().setContractHeadId(contractVo.getContractHeadId()));
//                            List<OrderPaymentProvision> orderPaymentProvisionList = new ArrayList<>();
//                            for (PayPlan payPlan : payPlanList) {
//                                OrderPaymentProvision orderPaymentProvision = new OrderPaymentProvision()
//                                        .setPaymentPeriod(StringUtil.StringValue(payPlan.getDateNum()))
//                                        .setPaymentWay(payPlan.getPayMethod())
//                                        .setPaymentTerm(payPlan.getPayExplain());
//                                orderPaymentProvisionList.add(orderPaymentProvision);
//                            }
//                            requirementManageDTO.setOrderPaymentProvisionList(orderPaymentProvisionList);
//                            requirementManageDTO.setContractVoList(contractVos);
//                            result.add(requirementManageDTO);
//                        }
//
//                    } else {
//                        ifHasError = "N";
//                        errorString.append("采购申请编号：[" + item.getRequirementHeadNum() + "]; 采购申请行号：[" + item.getRowNum() + "] 无有效价格库价格，无有效合同，请检查。 \n");
//                        log.info("requirementHeadNum = " + item.getRequirementHeadNum() + "  rowNum:" + item.getRowNum() + "  materialCode:" + item.getMaterialCode() + " materialName:" + item.getMaterialName() + " 无有效合同");
//                    }
//                }
//
//            } else {
//                /**
//                 * 【有指定的供应商的】
//                 * 根据 供应商id,物料小类id,业务实体id 查询供应商组织品类关系
//                 *   有组织品类关系
//                 *      根据 物料编码，物料名称，业务实体id，库存组织id，供应商id, 查询是否有有效价格，如果有赋值
//                 *      如果没有 根据 物料编码，物料名称，供应商id，业务实体id，库存组织id 查询有效合同(查询到多个合同)，如果有赋值
//                 *      如果没有 忽略该采购申请
//                 *
//                 * 根据价格库获取付款条款,
//                 * 根据合同编号获取付款条款
//                 **/
//                /*根据 供应商id,物料小类id,业务实体id 查询供应商组织品类关系 - 供应商组织品类关系判断方法【认证中，绿牌，黄牌，一次性】*/
//                OrgCategory orgCategoryItem = null;
//                for (OrgCategory orgCategory : orgCategoryList) {
//                    if (null != orgCategory
//                            && null != item.getOrgId() && Objects.equals(orgCategory.getOrgId(), item.getOrgId())
//                            && null != item.getCategoryId() && Objects.equals(orgCategory.getCategoryId(), item.getCategoryId())
//                            && null != item.getVendorId() && Objects.equals(orgCategory.getCompanyId(), item.getVendorId())
//                            && null != orgCategory.getServiceStatus()
//                            && (orgCategory.getServiceStatus().equals(CategoryStatus.VERIFY.name()) || orgCategory.getServiceStatus().equals(CategoryStatus.ONE_TIME.name()) || orgCategory.getServiceStatus().equals(CategoryStatus.GREEN.name()) || orgCategory.getServiceStatus().equals(CategoryStatus.YELLOW.name()))
//                    ) {
//                        orgCategoryItem = orgCategory;
//                        break;
//                    }
//                }
//                if (orgCategoryItem == null) {
//                    ifHasError = "N";
//                    errorString.append("采购申请编号：[" + item.getRequirementHeadNum() + "]; 采购申请行号：[" + item.getRowNum() + "] 无有效供应商组织品类关系，请检查。 \n");
//                    log.info("采购申请行：" + JsonUtil.entityToJsonStr(item) + " 无供应商组织品类关系");
//                    continue;
//                }
//                log.info("materialCode = " + item.getMaterialCode() + "  materialName = " + item.getMaterialName() + "  vendorName = " + item.getVendorName() + "的供应商组织品类关系为：" + JsonUtil.entityToJsonStr(orgCategoryItem));
//                /*根据 物料编码，物料名称，业务实体id，供应商id 查询是否有有效价格，如果有赋值*/
//                PriceLibrary priceEntity = null;
//                for (PriceLibrary priceLibrary : priceLibraryAll) {
//                    if (null != priceLibrary && StringUtils.isNotBlank(item.getMaterialName()) && item.getMaterialName().equals(priceLibrary.getItemDesc()) &&
//                            StringUtils.isNotBlank(item.getMaterialCode()) && item.getMaterialCode().equals(priceLibrary.getItemCode()) &&
//                            item.getOrgId() != null && !ObjectUtils.notEqual(item.getOrgId(), priceLibrary.getCeeaOrgId()) &&
//                            item.getVendorId() != null && Objects.equals(item.getVendorId(), priceLibrary.getVendorId()) &&
//                            item.getOrganizationId() != null && Objects.equals(item.getOrganizationId(), priceLibrary.getCeeaOrganizationId()) &&
//                            StringUtils.isNotBlank(priceLibrary.getPriceType()) && "STANDARD".equals(priceLibrary.getPriceType()) &&
//                            Objects.equals(priceLibrary.getCeeaArrivalPlace(), item.getCeeaDeliveryPlace())
//                    ) {
//                        /*判断价格库是否某些字段为空，为空则跳过该数据*/
//                        if (priceLibrary.getNotaxPrice() == null ||
//                                priceLibrary.getTaxPrice() == null ||
//                                StringUtils.isBlank(priceLibrary.getTaxKey()) ||
//                                StringUtils.isBlank(priceLibrary.getTaxRate()) ||
//                                priceLibrary.getCurrencyId() == null ||
//                                StringUtils.isBlank(priceLibrary.getCurrencyCode()) ||
//                                StringUtils.isBlank(priceLibrary.getCurrencyName())
//                        ) {
//                            log.info("requirementHeadNum = " + item.getRequirementHeadNum() + "  rowNum:" + item.getRowNum() + " 价格库某些字段为空:" + JsonUtil.entityToJsonStr(priceLibrary));
//                        } else {
//                            RequirementManageDTO requirementManageDTO = new RequirementManageDTO();
//                            BeanUtils.copyProperties(item, requirementManageDTO);
//                            if (priceLibrary.getCeeaQuotaProportion() == null) {
//                                priceLibrary.setCeeaQuotaProportion(BigDecimal.ZERO);
//                            }
//                            requirementManageDTO.setThisOrderQuantity(BigDecimal.ZERO);
//                            requirementManageDTO.setQuotaProportion(BigDecimal.ZERO);
//                            requirementManageDTO.setVendorId(priceLibrary.getVendorId());
//                            requirementManageDTO.setVendorName(priceLibrary.getVendorName());
//                            requirementManageDTO.setVendorCode(priceLibrary.getVendorCode());
//                            requirementManageDTO.setCurrencyId(priceLibrary.getCurrencyId());
//                            requirementManageDTO.setCurrencyCode(priceLibrary.getCurrencyCode());
//                            requirementManageDTO.setCurrencyName(priceLibrary.getCurrencyName());
//                            requirementManageDTO.setTaxRate(new BigDecimal(priceLibrary.getTaxRate()));
//                            requirementManageDTO.setTaxKey(priceLibrary.getTaxKey());
//                            requirementManageDTO.setTaxPrice(priceLibrary.getTaxPrice());
//                            requirementManageDTO.setNotaxPrice(priceLibrary.getNotaxPrice());
//                            requirementManageDTO.setMinOrderQuantity(priceLibrary.getMinOrderQuantity());
//                            priceEntity = priceLibrary;
//                            /*设置付款条款 - 获取价格库 - 付款条款*/
//                            List<OrderPaymentProvision> orderPaymentProvisionList = new ArrayList<>();
//                            List<PriceLibraryPaymentTerm> priceLibraryPaymentTermList = inqClient.listPriceLibraryPaymentTerm(new PriceLibraryPaymentTerm().setPriceLibraryId(priceLibrary.getPriceLibraryId()));
//                            for (PriceLibraryPaymentTerm priceLibraryPaymentTerm : priceLibraryPaymentTermList) {
//                                OrderPaymentProvision orderPaymentProvision = new OrderPaymentProvision()
//                                        .setPaymentTerm(priceLibraryPaymentTerm.getPaymentTerm())
//                                        .setPaymentWay(priceLibraryPaymentTerm.getPaymentWay())
//                                        .setPaymentPeriod(StringUtil.StringValue(priceLibraryPaymentTerm.getPaymentDay()));
//                                orderPaymentProvisionList.add(orderPaymentProvision);
//                            }
//                            requirementManageDTO.setOrderPaymentProvisionList(orderPaymentProvisionList);
//                            /*关联框架合同 业务实体id，库存组织id，供应商id，合同为框架协议*/
//
////
////                            for (ContractVo contractVo : contractVoList) {
////                                if (null != contractVo && contractVo.getBuId() != null && !ObjectUtils.notEqual(contractVo.getBuId(), item.getOrgId())
////                                        && contractVo.getInvId() != null && Objects.equals(contractVo.getInvId(), item.getOrganizationId())
////                                        && contractVo.getHeadVendorId() != null && Objects.equals(contractVo.getHeadVendorId(), priceLibrary.getVendorId())
////                                        && StringUtils.isNotBlank(contractVo.getIsFrameworkAgreement()) && contractVo.getIsFrameworkAgreement().equals("Y")
////                                ) {
////                                    /*判断合同是否某些字段为空，为空则跳过该数据*/
////                                    if (StringUtils.isNotBlank(contractVo.getContractCode())) {
////                                        requirementManageDTO.setContractNum(contractVo.getContractCode());
////                                        break;
////                                    } else {
////                                        log.info("requirementHeadNum = " + item.getRequirementHeadNum() + "  rowNum:" + item.getRowNum() + " 合同的合同编号为空:" + JsonUtil.entityToJsonStr(contractVo));
////                                    }
////                                }
////                            }
//                            if (!StringUtil.isEmpty(priceLibrary.getContractCode())) {
//                                requirementManageDTO.setContractNum(priceLibrary.getContractCode());
//                            } else {
//                                //添加比对合作伙伴 逻辑2020年11月10日
//                                for (ContractVo contractVo : contractVoList) {
//                                    boolean hitContractFlag =
////                                          null != contractVo &&
////                                          contractVo.getBuId() != null &&
////                                          !ObjectUtils.notEqual(contractVo.getBuId(), item.getOrgId())&&
//                                            contractVo.getHeadVendorId() != null &&
////                                          contractVo.getInvId() != null &&
////                                          Objects.equals(contractVo.getInvId(), item.getOrganizationId()) &&
//                                                    Objects.equals(contractVo.getHeadVendorId(), priceLibrary.getVendorId()) &&
//                                                    StringUtils.isNotBlank(contractVo.getIsFrameworkAgreement()) &&
//                                                    StringUtils.isNotBlank(contractVo.getContractCode()) &&
//                                                    contractVo.getIsFrameworkAgreement().equals("Y");
//                                    log.info("requirementHeadNum = " + item.getRequirementHeadNum() + "  rowNum:" + item.getRowNum() + " 合同信息为" + JsonUtil.entityToJsonStr(contractVo));
//
//                                    boolean findContractNum = false;
//                                    for (ContractPartner contractPartner : contractPartnerList) {
//                                        if (!contractPartner.getContractHeadId().equals(contractVo.getContractHeadId())) {
//                                            continue;
//                                        }
//                                        if (findContractNum) {
//                                            break;
//                                        }
//
//                                        Long contractPartnerOUId = contractPartner.getOuId();
//                                        //todo
//                                        if (hitContractFlag && Objects.equals(contractVo.getBuId(), contractPartnerOUId)) {
//                                            /*判断合同是否某些字段为空，为空则跳过该数据*/
//                                            requirementManageDTO.setContractNum(contractVo.getContractCode());
//                                            requirementManageDTO.getContractVoList().add(contractVo);
//                                            findContractNum = true;
//                                            break;
//                                        }
//                                    }
//                                }
//                            }
//                            result.add(requirementManageDTO);
//                        }
//                    }
//                }
//                if (priceEntity == null) {
//                    /*无有效价格，获取合同*/
//                    //如果没有 根据 物料编码，物料名称，供应商id，业务实体，查询有效合同，如果有赋值
//                    log.info("requirementHeadNum = " + item.getRequirementHeadNum() + "  rowNum:" + item.getRowNum() + "  materialCode:" + item.getMaterialCode() + " materialName:" + item.getMaterialName() + " 无有效价格");
//                    List<ContractVo> effectiveContractVoList = new ArrayList<>();
//                    for (ContractVo contractVo : contractVoList) {
//                        if (null != contractVo && contractVo.getBuId() != null && !ObjectUtils.notEqual(contractVo.getBuId(), item.getOrgId())
//                                && contractVo.getMaterialCode() != null && contractVo.getMaterialCode().equals(item.getMaterialCode())
//                                && contractVo.getMaterialName() != null && contractVo.getMaterialName().equals(item.getMaterialName())
//                                && contractVo.getHeadVendorId() != null && Objects.equals(contractVo.getHeadVendorId(), item.getVendorId())
//                                && contractVo.getInvId() != null && Objects.equals(contractVo.getInvId(), item.getOrganizationId())
//                                && StringUtils.isNotBlank(contractVo.getIsFrameworkAgreement()) && contractVo.getIsFrameworkAgreement().equals("N")
//                        ) {
//                            /*判断合同是否某些字段为空，为空则跳过该数据*/
//                            if (contractVo.getTaxedPrice() == null ||
//                                    StringUtils.isBlank(contractVo.getTaxKey()) ||
//                                    contractVo.getTaxRate() == null ||
//                                    contractVo.getCurrencyId() == null ||
//                                    StringUtils.isBlank(contractVo.getCurrencyCode()) ||
//                                    StringUtils.isBlank(contractVo.getCurrencyName())
//                            ) {
//                                log.info("requirementHeadNum = " + item.getRequirementHeadNum() + "  rowNum:" + item.getRowNum() + " 合同某些字段为空:" + JsonUtil.entityToJsonStr(contractVo));
//                            } else {
//                                /*计算未税单价*/
//                                BigDecimal taxRate = contractVo.getTaxRate().divide(new BigDecimal(100), 8, BigDecimal.ROUND_HALF_UP);
//                                BigDecimal untaxedPrice = contractVo.getTaxedPrice().divide((new BigDecimal(1).add(taxRate)), 2, BigDecimal.ROUND_HALF_UP);
//                                contractVo.setUntaxedPrice(untaxedPrice);
//                                effectiveContractVoList.add(contractVo);
//                            }
//                        }
//                    }
//                    /*采购申请行设置合同信息*/
//                    if (CollectionUtils.isNotEmpty(effectiveContractVoList)) {
//                        ContractVo contractVo = effectiveContractVoList.get(0);
//                        RequirementManageDTO requirementManageDTO = new RequirementManageDTO();
//                        BeanUtils.copyProperties(item, requirementManageDTO);
//                        requirementManageDTO.setVendorId(contractVo.getHeadVendorId());
//                        requirementManageDTO.setVendorName(contractVo.getHeadVendorName());
//                        requirementManageDTO.setVendorCode(contractVo.getHeadVendorCode());
//                        requirementManageDTO.setCurrencyId(contractVo.getCurrencyId());
//                        requirementManageDTO.setCurrencyCode(contractVo.getCurrencyCode());
//                        requirementManageDTO.setCurrencyName(contractVo.getCurrencyName());
//                        requirementManageDTO.setNotaxPrice(contractVo.getUntaxedPrice());
//                        requirementManageDTO.setTaxPrice(contractVo.getTaxedPrice());
//                        requirementManageDTO.setTaxKey(contractVo.getTaxKey());
//                        requirementManageDTO.setTaxRate(contractVo.getTaxRate());
//                        requirementManageDTO.setContractNum(contractVo.getContractCode());
//                        /*设置付款条款 - 获取合同计划*/
//                        List<PayPlan> payPlanList = contractClient.list(new PayPlan().setContractHeadId(contractVo.getContractHeadId()));
//                        List<OrderPaymentProvision> orderPaymentProvisionList = new ArrayList<>();
//                        for (PayPlan payPlan : payPlanList) {
//                            OrderPaymentProvision orderPaymentProvision = new OrderPaymentProvision()
//                                    .setPaymentPeriod(StringUtil.StringValue(payPlan.getDateNum()))  //付款账期
//                                    .setPaymentWay(payPlan.getPayMethod())  //付款方式
//                                    .setPaymentTerm(payPlan.getPayExplain());  //付款条件
//                            orderPaymentProvisionList.add(orderPaymentProvision);
//                        }
//                        requirementManageDTO.setOrderPaymentProvisionList(orderPaymentProvisionList);
//                        result.add(requirementManageDTO);
//                    } else {
//                        ifHasError = "N";
//                        errorString.append("采购申请编号：[" + item.getRequirementHeadNum() + "]; 采购申请行号：[" + item.getRowNum() + "] 无有效价格库价格，无有效合同，请检查。 \n");
//                        log.info("requirementHeadNum = " + item.getRequirementHeadNum() + "  rowNum:" + item.getRowNum() + "  materialCode:" + item.getMaterialCode() + " materialName:" + item.getMaterialName() + " 无有效合同");
//                    }
//                }
//            }
//        }
//        if ("N".equals(ifHasError)) {
//            throw new BaseException(LocaleHandler.getLocaleMsg(errorString.toString()));
//        }
//        return result;
//    }
//
//
//    /**
//     * 提交生成采购订单
//     *
//     * @param requirementManageDTOS
//     */
//    @Override
//    @Transactional
//    public void submitPurchaseOrder(List<RequirementManageDTO> requirementManageDTOS) {
//        /*校验数据是否为空*/
//        checkIfNull(requirementManageDTOS);
//        /*检验是否满足提交条件*/
//        checkIfSubmit(requirementManageDTOS);
//
//        /*获取所有的物料的物料大类编码 todo delete*/
//        /*List<Long> materialIds = requirementManageDTOS.stream().map(item -> item.getMaterialId()).collect(Collectors.toList());
//        List<MaterialMaxCategoryVO> materialMaxCategoryVOList = baseClient.queryCategoryMaxCodeByMaterialIds(materialIds);
//        Map<Long,String> map = materialMaxCategoryVOList.stream().collect(Collectors.toMap(MaterialMaxCategoryVO::getMaterialId,MaterialMaxCategoryVO::getCategoryCode));*/
//
//        /* 相同业务实体、相同库存组织、相同供应商、相同采购履行、相同币种、相同税率、相同付款条款、相同项目可汇总合并 */
//        List<List<RequirementManageDTO>> lists = new ArrayList<>();
//        requirementManageDTOS.forEach(item -> {
//            /*设置所有物料大类编码 todo delete*/
//            /*item.setBigCategoryCode(map.get(item.getMaterialId()));*/
//
//            boolean add = true;
//            for (int i = 0; i < lists.size(); i++) {
//                List<RequirementManageDTO> list = lists.get(i);
//                if (list.size() > 0) {
//                    RequirementManageDTO requirementManageDTO = list.get(0);
//                    if (ifCommonGroup(requirementManageDTO, item)) {
//                        list.add(item);
//                        add = false;
//                        break;
//                    }
//                }
//            }
//            if (add) {
//                List<RequirementManageDTO> list = new ArrayList<>();
//                list.add(item);
//                lists.add(list);
//            }
//        });
//        log.info("lists.size:" + lists.size());
//        log.info("lists:" + lists);
//
//        /*创建订单*/
//        for (List<RequirementManageDTO> requirementManageDTOList : lists) {
//            OrderSaveRequestDTO orderSaveRequestDTO = buildOrder(requirementManageDTOList);
//            Long orderId = orderService.save(orderSaveRequestDTO);
//            /*回写采购订单单号*/
//            Order order = supcooperateClient.getOrderById(orderId);
//            List<RequirementLine> requirementLineList = new ArrayList<>();
//            for (RequirementManageDTO requirementManageDTO : requirementManageDTOList) {
//                RequirementLine requirementLine = new RequirementLine()
//                        .setRequirementLineId(requirementManageDTO.getRequirementLineId())
//                        .setIfCreateFollowForm(YesOrNo.YES.getValue())
//                        .setOrderNumber(order.getOrderNumber());
//                requirementLineList.add(requirementLine);
//            }
//            iRequirementLineService.updateBatchById(requirementLineList);
//        }
//    }
//
//    private void checkIfSubmit(List<RequirementManageDTO> requirementManageDTOS) {
//        for (RequirementManageDTO requirementManageDTO : requirementManageDTOS) {
//            if (requirementManageDTO.getMinOrderQuantity() != null && requirementManageDTO.getMinOrderQuantity().compareTo(requirementManageDTO.getThisOrderQuantity()) == 1) {
//                throw new BaseException(LocaleHandler.getLocaleMsg("requirementLineId = " + requirementManageDTO.getRequirementLineId() + "  【本次下单数量】:" + requirementManageDTO.getThisOrderQuantity() + "不可小于【最小起订数量】：" + requirementManageDTO.getMinOrderQuantity()));
//            }
//
//            /**
//             * 1、PR的采购类型为研发采购，只能转订单类型为研发采购的订单；
//             * 2、PR的采购类型为紧急采购，转订单时，订单类型可为常规采购、紧急采购、便捷订单、寄售订单
//             * 3、PR的采购类型为常规采购，转订单时，订单类型可为常规采购、便捷订单、寄售订单、零价格
//             * 4、PR的采购类型为指定采购，转订单时，订单类型可为常规采购、便捷订单、寄售订单、零价格
//             * 5、PR的采购类型为废旧物资处置，不可转采购订单；
//             */
//            if (OrderTypeEnum.DEVELOP.getValue().equals(requirementManageDTO.getCeeaPurchaseType()) &&
//                    !OrderTypeEnum.DEVELOP.getValue().equals(requirementManageDTO.getOrderType())
//            ) {
//                throw new BaseException(LocaleHandler.getLocaleMsg("PR的采购类型为研发采购，只能转订单类型为研发采购的订单"));
//            }
//            if (OrderTypeEnum.URGENT.getValue().equals(requirementManageDTO.getCeeaPurchaseType()) &&
//                    (!OrderTypeEnum.NORMAL.getValue().equals(requirementManageDTO.getOrderType()) &&
//                            !OrderTypeEnum.URGENT.getValue().equals(requirementManageDTO.getOrderType()) &&
//                            !OrderTypeEnum.CONVENIENT.getValue().equals(requirementManageDTO.getOrderType()) &&
//                            !OrderTypeEnum.CONSIGNMENT.getValue().equals(requirementManageDTO.getOrderType())
//                    )
//            ) {
//                throw new BaseException(LocaleHandler.getLocaleMsg("PR的采购类型为紧急采购，转订单时，订单类型可为常规采购、紧急采购、便捷订单、寄售订单"));
//            }
//            if (OrderTypeEnum.NORMAL.getValue().equals(requirementManageDTO.getCeeaPurchaseType()) &&
//                    (!OrderTypeEnum.NORMAL.getValue().equals(requirementManageDTO.getOrderType()) &&
//                            !OrderTypeEnum.CONVENIENT.getValue().equals(requirementManageDTO.getOrderType()) &&
//                            !OrderTypeEnum.CONSIGNMENT.getValue().equals(requirementManageDTO.getOrderType()) &&
//                            !OrderTypeEnum.ZERO_PRICE.getValue().equals(requirementManageDTO.getOrderType())
//                    )
//            ) {
//                throw new BaseException(LocaleHandler.getLocaleMsg("PR的采购类型为常规采购，转订单时，订单类型可为常规采购、便捷订单、寄售订单、零价格"));
//            }
//            if (OrderTypeEnum.APPOINT.getValue().equals(requirementManageDTO.getCeeaPurchaseType()) &&
//                    (!OrderTypeEnum.NORMAL.getValue().equals(requirementManageDTO.getOrderType()) &&
//                            !OrderTypeEnum.CONVENIENT.getValue().equals(requirementManageDTO.getOrderType()) &&
//                            !OrderTypeEnum.CONSIGNMENT.getValue().equals(requirementManageDTO.getOrderType()) &&
//                            !OrderTypeEnum.ZERO_PRICE.getValue().equals(requirementManageDTO.getOrderType())
//                    )
//            ) {
//                throw new BaseException(LocaleHandler.getLocaleMsg("PR的采购类型为指定采购，转订单时，订单类型可为常规采购、便捷订单、寄售订单、零价格"));
//
//            }
//            if (OrderTypeEnum.OUT.getValue().equals(requirementManageDTO.getCeeaPurchaseType())) {
//                throw new BaseException(LocaleHandler.getLocaleMsg("PR的采购类型为废旧物资处置，不可转采购订单"));
//            }
//        }
//
//
//    }
//
//    private void checkIfNull(List<RequirementManageDTO> requirementManageDTOS) {
//        for (RequirementManageDTO requirementManageDTO : requirementManageDTOS) {
//            if (Objects.isNull(requirementManageDTO.getOrgId())) {
//                throw new BaseException(LocaleHandler.getLocaleMsg("申请编号为[" + requirementManageDTO.getRequirementHeadNum() + "] 申请行号为[" + requirementManageDTO.getRowNum() + "]  业务实体不可为空"));
//            }
//            if (Objects.isNull(requirementManageDTO.getOrganizationId())) {
//                throw new BaseException(LocaleHandler.getLocaleMsg("申请编号为[" + requirementManageDTO.getRequirementHeadNum() + "] 申请行号为[" + requirementManageDTO.getRowNum() + "]  库存组织不可为空"));
//            }
//            if (StringUtils.isBlank(requirementManageDTO.getCeeaPurchaseType())) {
//                throw new BaseException(LocaleHandler.getLocaleMsg("申请编号为[" + requirementManageDTO.getRequirementHeadNum() + "] 申请行号为[" + requirementManageDTO.getRowNum() + "]  采购类型不可为空"));
//            }
//            if (Objects.isNull(requirementManageDTO.getCeeaPerformUserId()) && !"Y".equals(requirementManageDTO.getCeeaIfDirectory())) {
//                throw new BaseException(LocaleHandler.getLocaleMsg("申请编号为[" + requirementManageDTO.getRequirementHeadNum() + "] 申请行号为[" + requirementManageDTO.getRowNum() + "]  采购履行人id不可为空"));
//            }
//            if (StringUtils.isBlank(requirementManageDTO.getCurrencyCode())) {
//                throw new BaseException(LocaleHandler.getLocaleMsg("申请编号为[" + requirementManageDTO.getRequirementHeadNum() + "] 申请行号为[" + requirementManageDTO.getRowNum() + "]  币种编码不可为空"));
//            }
//            if (StringUtils.isBlank(requirementManageDTO.getTaxKey())) {
//                throw new BaseException(LocaleHandler.getLocaleMsg("申请编号为[" + requirementManageDTO.getRequirementHeadNum() + "] 申请行号为[" + requirementManageDTO.getRowNum() + "]  税率编码不可为空"));
//            }
//            if (Objects.isNull(requirementManageDTO.getThisOrderQuantity())) {
//                throw new BaseException(LocaleHandler.getLocaleMsg("申请编号为[" + requirementManageDTO.getRequirementHeadNum() + "] 申请行号为[" + requirementManageDTO.getRowNum() + "]  本次下单数量不可为空"));
//            }
//            if (Objects.isNull(requirementManageDTO.getVendorId())) {
//                throw new BaseException(LocaleHandler.getLocaleMsg("申请编号为[" + requirementManageDTO.getRequirementHeadNum() + "] 申请行号为[" + requirementManageDTO.getRowNum() + "]  供应商id不可为空"));
//            }
//        }
//    }
//
//    /**
//     * 相同业务实体、相同库存组织、相同供应商、相同采购履行、相同币种、相同税率、相同付款条款、相同项目(先不管) 可汇总合并
//     */
//    private boolean ifCommonGroup(RequirementManageDTO requirementManageDTO1, RequirementManageDTO requirementManageDTO2) {
//        /*业务实体*/
//        if (requirementManageDTO1.getOrgId().longValue() != requirementManageDTO2.getOrgId().longValue()) {
//            return false;
//        }
//        /*库存组织*/
//        if (requirementManageDTO1.getOrganizationId().longValue() != requirementManageDTO2.getOrganizationId().longValue()) {
//            return false;
//        }
//        /*供应商*/
//        if (!Objects.equals(requirementManageDTO1.getVendorId(), requirementManageDTO2.getVendorId())) {
//            return false;
//        }
//        /*采购履行*/
//        if (requirementManageDTO1.getCeeaPerformUserId().longValue() != requirementManageDTO2.getCeeaPerformUserId().longValue()) {
//            return false;
//        }
//        /*相同币种*/
//        if (!Objects.equals(requirementManageDTO1.getCurrencyId(), requirementManageDTO2.getCurrencyId())) {
//            return false;
//        }
//        /*相同税率*/
//        if (requirementManageDTO1.getTaxRate().compareTo(requirementManageDTO2.getTaxRate()) != 0) {
//            return false;
//        }
//        /*相同付款条款*/
//        return checkIfCommonPaymentTerm(requirementManageDTO1, requirementManageDTO2) != false;
//    }
//
//    private boolean checkIfCommonPaymentTerm(RequirementManageDTO requirementManageDTO1, RequirementManageDTO requirementManageDTO2) {
//        List<OrderPaymentProvision> orderPaymentProvisionList1 = requirementManageDTO1.getOrderPaymentProvisionList();
//        List<OrderPaymentProvision> orderPaymentProvisionList2 = requirementManageDTO2.getOrderPaymentProvisionList();
//        if (orderPaymentProvisionList1.size() != orderPaymentProvisionList2.size()) {
//            return false;
//        }
//        if (!orderPaymentProvisionList1.containsAll(orderPaymentProvisionList2)) {
//            return false;
//        }
//        return orderPaymentProvisionList2.containsAll(orderPaymentProvisionList1);
//    }
//
//    /*public static void main(String[] args) {
//        OrderPaymentProvision orderPaymentProvision1 = new OrderPaymentProvision();
//        orderPaymentProvision1.setPaymentPeriod("NET45")
//                .setPaymentTerm("7982657224572928")
//                .setPaymentWay("LONGI_BILL");
//
//        OrderPaymentProvision orderPaymentProvision2 = new OrderPaymentProvision();
//        orderPaymentProvision2.setPaymentPeriod("NET75")
//                .setPaymentTerm("7982658108850176")
//                .setPaymentWay("LONGI_BILL");
//
//        OrderPaymentProvision orderPaymentProvision3 = new OrderPaymentProvision();
//        orderPaymentProvision3.setPaymentPeriod("NET45")
//                .setPaymentTerm("7982657224572928")
//                .setPaymentWay("LONGI_BILL");
//
//        OrderPaymentProvision orderPaymentProvision4 = new OrderPaymentProvision();
//        orderPaymentProvision4.setPaymentPeriod("NET75")
//                .setPaymentTerm("7982658108850176")
//                .setPaymentWay("LONGI_BILL");
//
//        List<OrderPaymentProvision> orderPaymentProvisionList1 = new ArrayList<>();
//        List<OrderPaymentProvision> orderPaymentProvisionList2 = new ArrayList<>();
//        orderPaymentProvisionList1.add(orderPaymentProvision1);
//        orderPaymentProvisionList1.add(orderPaymentProvision2);
//        orderPaymentProvisionList2.add(orderPaymentProvision3);
//        orderPaymentProvisionList2.add(orderPaymentProvision4);
//
//        *//*if(orderPaymentProvision1.equals(orderPaymentProvision3)){
//            System.out.println(true);
//        }else{
//            System.out.println(false);
//        }*//*
//        System.out.println("---");
//        System.out.println(testCheck(orderPaymentProvisionList1,orderPaymentProvisionList2));
//        System.out.println("---");
//    }
//
//    public static boolean testCheck(List<OrderPaymentProvision> orderPaymentProvisions1,List<OrderPaymentProvision> orderPaymentProvisions2){
//        if(orderPaymentProvisions1.size() != orderPaymentProvisions2.size()){
//            return false;
//        }
//        if(!orderPaymentProvisions1.containsAll(orderPaymentProvisions2)){
//            return false;
//        }
//        if(!orderPaymentProvisions2.containsAll(orderPaymentProvisions1)){
//            return false;
//        }
//        return true;
//    }*/
//
//    @Override
//    public RequirementHead getRequirementHeadByParam(RequirementHeadQueryDTO requirementHeadQueryDTO) {
//        RequirementHead requirementHead = new RequirementHead();
//        BeanUtils.copyProperties(requirementHeadQueryDTO, requirementHead);
//        return this.getOne(new QueryWrapper<>(requirementHead));
//    }
//
//    @Override
//    @Transactional
//    public RequirementLineVO createSourceForm(List<RequirementManageDTO> requirementManageDTOS, int sourcingType) {
//        checkBeforeCreateSourceForm(requirementManageDTOS);
//        RequirementLineVO requirementLineVO = new RequirementLineVO();
//        //创建招标
//        if (SourcingType.BIDDING.getItemValue() == sourcingType) {
//            List<RequirementLineVO> requirementLineVOS = getRequirementLines(requirementManageDTOS);
//            GenerateSourceFormParameter parameter = GenerateSourceFormParameter.create(requirementLineVOS);
//            //插入数据
//            GenerateSourceFormResult result = SpringContextHolder.getBean(SourceFormClient.class).generateForm(parameter);
//            for (RequirementManageDTO bidRequirementLine : requirementManageDTOS) {
//                //添加后续单据记录
//                SubsequentDocuments subsequentDocuments = new SubsequentDocuments();
//                subsequentDocuments.setSubsequentDocumentsId(IdGenrator.generate());
//                subsequentDocuments.setSubsequentDocumentsNumber(result.getSourceForm().getBidding().getBidingNum());
//                subsequentDocuments.setRequirementLineId(bidRequirementLine.getRequirementLineId());
//                subsequentDocuments.setIsubsequentDocumentssType(RelatedDocumentsEnum.TENDER.getName());
//                subsequentDocuments.setFollowFormId(result.getSourceForm().getBidding().getBidingId());
//                iSubsequentDocumentsService.save(subsequentDocuments);
//            }
//            System.out.println("生成后的单据======================================>" + JsonUtil.entityToJsonStr(result));
//            log.info("生成后的单据======================================>" + JsonUtil.entityToJsonStr(result));
//            SourceForm sourceForm = result.getSourceForm();
//            Biding bidding = sourceForm.getBidding();
//            //返回前端后续ID和后续编号
//            Long bidingId = bidding.getBidingId();
//            String bidingNum = bidding.getBidingNum();
//            return requirementLineVO.setFollowFormId(bidingId).setFollowFormCode(bidingNum);
//        }
//        //创建询比价
//        if (SourcingType.BARGAINING.getItemValue() == sourcingType) {
//            List<RequirementLineVO> requirementLineVOS = getRequirementLines(requirementManageDTOS);
//            com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.vo.GenerateSourceFormParameter parameter = com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.vo.GenerateSourceFormParameter.create(requirementLineVOS);
//            com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.vo.GenerateSourceFormResult result = SpringContextHolder.getBean(com.midea.cloud.srm.feign.bargaining.SourceFormClient.class).generateForm(parameter);
//            for (RequirementManageDTO bidRequirementLine : requirementManageDTOS) {
//                //添加后续单据记录
//                SubsequentDocuments subsequentDocuments = new SubsequentDocuments();
//                subsequentDocuments.setSubsequentDocumentsId(IdGenrator.generate());
//                subsequentDocuments.setSubsequentDocumentsNumber(result.getSourceForm().getBidding().getBidingNum());
//                subsequentDocuments.setRequirementLineId(bidRequirementLine.getRequirementLineId());
//                subsequentDocuments.setIsubsequentDocumentssType(RelatedDocumentsEnum.ENQUIRY.getName());
//                subsequentDocuments.setFollowFormId(result.getSourceForm().getBidding().getBidingId());
//                iSubsequentDocumentsService.save(subsequentDocuments);
//            }
//            System.out.println("生成后的单据======================================>" + JsonUtil.entityToJsonStr(result));
//            log.info("生成后的单据======================================>" + JsonUtil.entityToJsonStr(result));
//            com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.vo.SourceForm sourceForm = result.getSourceForm();
//            Long bidingId = sourceForm.getBidding().getBidingId();
//            String bidingNum = sourceForm.getBidding().getBidingNum();
//            return requirementLineVO.setFollowFormId(bidingId).setFollowFormCode(bidingNum);
//        }
//        return requirementLineVO;
//    }
//
//    private List<RequirementLineVO> getRequirementLines(List<RequirementManageDTO> requirementManageDTOS) {
//        List<RequirementLineVO> requirementLineVOS = new ArrayList<>();
//        for (RequirementManageDTO requirementManageDTO : requirementManageDTOS) {
//            if (requirementManageDTO == null) continue;
//            RequirementLineVO requirementLineVO = new RequirementLineVO();
//            BeanUtils.copyProperties(requirementManageDTO, requirementLineVO);
//            requirementLineVO.setPurchaseType(requirementManageDTO.getCeeaPurchaseType());
//            // 需求日期
//            requirementLineVO.setRequirementDate(requirementManageDTO.getRequirementDate());
//            // 预计总金额
//            requirementLineVO.setCeeaTotalBudget(requirementManageDTO.getCeeaTotalBudget());
//            requirementLineVOS.add(requirementLineVO);
//            //创建单据成功回写创建后续单据标识
//            iRequirementLineService.updateById(new RequirementLine()
//                    .setRequirementLineId(requirementManageDTO.getRequirementLineId())
//                    .setIfCreateFollowForm(YesOrNo.YES.getValue()));
//        }
//        return requirementLineVOS;
//    }
//
//    private void checkBeforeCreateSourceForm(List<RequirementManageDTO> requirementManageDTOS) {
//        //ToDo
//        Assert.notEmpty(requirementManageDTOS, LocaleHandler.getLocaleMsg("请勾选需要创建寻源单据的申请行"));
//        for (RequirementManageDTO requirementManageDTO : requirementManageDTOS) {
//            if (requirementManageDTO == null) continue;
//            Assert.notNull(requirementManageDTO.getCeeaStrategyUserId(), LocaleHandler.getLocaleMsg("缺少策略负责人,无法创建寻源单"));
//            if (!RequirementApplyStatus.ASSIGNED.getValue().equals(requirementManageDTO.getApplyStatus())) {
//                throw new BaseException(LocaleHandler.getLocaleMsg("存在申请行状态不为已分配的行,无法生成寻源单据"));
//            }
//        }
//    }
//
//    public OrderSaveRequestDTO buildOrder(List<RequirementManageDTO> requirementManageDTOList) {
//        Date date = new Date();
//        Order order = new Order();
//        List<OrderDetail> orderDetailList = new ArrayList<>();
//        List<OrderAttach> orderAttachList = new ArrayList<>();
//        List<OrderPaymentProvision> orderPaymentProvisionList = new ArrayList<>();
//        /*获取付款条款*/
//        if (CollectionUtils.isNotEmpty(requirementManageDTOList)) {
//            RequirementManageDTO requirementManageDTO = requirementManageDTOList.get(0);
//            orderPaymentProvisionList = requirementManageDTO.getOrderPaymentProvisionList();
//        }
//
//        for (int i = 0; i < requirementManageDTOList.size(); i++) {
//            RequirementManageDTO item = requirementManageDTOList.get(i);
//            /*如果下单数为0直接过滤*/
//            if (item.getThisOrderQuantity() == null || item.getThisOrderQuantity().compareTo(BigDecimal.ZERO) <= 0) {
//                continue;
//            }
//
//            Organization organization = baseClient.getOrganization(new Organization().setOrganizationId(item.getOrgId()));
//            order.setVendorId(item.getVendorId())
//                    .setVendorName(item.getVendorName())
//                    .setVendorCode(item.getVendorCode())
//                    .setCeeaIfSupplierConfirm(item.getCeeaIfSupplierConfirm())  //是否供应商确认
//                    .setCeeaIfConSignment(item.getCeeaIfConSignment())  //是否寄售
//                    .setCeeaIfPowerStationBusiness(item.getCeeaIfPowerStationBusiness()) //是否电站业务
//                    .setCeeaOrgId(item.getOrgId())  //业务实体ID
//                    .setCeeaOrgCode(item.getOrgCode())  //业务实体编码
//                    .setCeeaOrgName(item.getOrgName())  //业务实体名称
//                    .setCeeaDepartmentId(item.getCeeaDepartmentId())  //部门ID
//                    .setCeeaDepartmentCode(item.getCeeaDepartmentCode())  //部门编码
//                    .setCeeaDepartmentName(item.getCeeaDepartmentName())  //部门名称
//                    .setCeeaOrderTypeIdentification(item.getCeeaOrderTypeIdentification())  //条目标识
//                    .setOrderType(item.getOrderType())
//                    .setCeeaReceiveOrderAddress(organization.getCeeaReceivingLocation())//收单地址
//                    .setCeeaSaveId(item.getCeeaPerformUserId())  //保存人id
//                    .setCeeaSaveBy(item.getCeeaPerformUserNickname())  //保存人名称
//                    .setCeeaSaveDate(date)  //保存时间
//                    .setCeeaEmpUseId(item.getCeeaPerformUserId())    //采购员id
//                    .setCeeaEmpUsername(item.getCeeaPerformUserNickname())   //采购员名称
//                    .setCeeaEmpNo(item.getCeeaPerformUserName())    //采购员工号
//                    .setCreatedBy(item.getCeeaPerformUserNickname())
//                    .setCreatedId(item.getCeeaPerformUserId())
//                    .setCreationDate(date)
//                    .setCeeaReceiveAddress(organization.getOrganizationSite()) //收货地点
//                    .setIfSample("N") //是否批量小订单
//                    .setSourceSystem(SourceSystemEnum.DEMAND.getValue()); //订单来源
//
//            OrderDetail orderDetail = new OrderDetail()
//                    .setLineNum(i + 1)   //订单行号
//                    .setCeeaPlanReceiveDate(Date.from(item.getRequirementDate().atStartOfDay(ZoneId.systemDefault()).toInstant()))
//                    .setCeeaPromiseReceiveDate(new Date())  // todo 承诺到货日期 = 当前日期 + 供货周期
//                    .setCeeaUnitNoTaxPrice(item.getNotaxPrice())  //不含税单价
//                    .setCeeaUnitTaxPrice(item.getTaxPrice())   //含税单价
//                    .setCurrencyId(item.getCurrencyId())    //币种id
//                    .setCurrencyCode(item.getCurrencyCode())   //币种编码
//                    .setCurrencyName(item.getCurrencyName())  //币种名称
//                    .setCeeaContractNo(item.getContractNum())  //合同编号
//                    .setCeeaBusinessSmall(item.getCeeaBusinessSmall())//业务小类
//                    .setCeeaBusinessSmallCode(item.getCeeaBusinessSmallCode())//业务小类编码
//                    .setOrderDetailId(IdGenrator.generate())
//                    .setCategoryId(item.getCategoryId()) //采购分类ID(物料小类id)
//                    .setCategoryName(item.getCategoryName()) //采购分类全名(物料小类名称)
//                    .setMaterialId(item.getMaterialId())  //物料ID
//                    .setMaterialCode(item.getMaterialCode())  //物料编码
//                    .setMaterialName(item.getMaterialName())  //物料名称
//                    .setReceiptPlace(item.getCeeaDeliveryPlace())  //收货地点
//                    .setUnit(item.getUnitCode())  //单位
//                    .setUnitCode(item.getUnitCode())
//                    .setOrderNum(item.getThisOrderQuantity())  //订单数量(本次下单数量)
//                    .setRequirementDate(item.getRequirementDate()) //需求日期
//                    .setRequirementQuantity(item.getThisOrderQuantity()) //需求数量(本次下单数量)
//                    .setCurrency(item.getCurrency()) //币种
//                    .setComments(item.getComments())  //备注
//                    .setTenantId(0L)
//                    .setVersion(0L)
//                    .setReceiveSum(new BigDecimal(0)) //订单累计收货量
//                    .setCeeaRequirementLineId(item.getRequirementLineId()) //采购申请物料行主键id
//                    .setCeeaRequirementHeadNum(item.getRequirementHeadNum())  //采购申请编号(longi)
//                    .setCeeaRowNum(StringUtil.StringValue(i + 1))  //
//                    .setCeeaOrganizationId(item.getOrganizationId()) //库存组织ID
//                    .setCeeaOrganizationName(item.getOrganizationName())  //库存组织名称
//                    .setCeeaOrganizationCode(item.getOrganizationCode()) //库存组织编码
//                    .setCeeaFirstApprovedNum(new BigDecimal(0)) //第一次审批通过数量(原订单数量)
//                    .setCeeaApprovedNum(new BigDecimal(0))  //审批通过数量
//                    .setCeeaTaxRate(item.getTaxRate())  //税率
//                    .setCeeaTaxKey(item.getTaxKey())  //税码
//                    .setCeeaIfRequirement("Y"); //物料行是否未采购需求
//            if (orderDetail.getOrderNum() == null) {
//                orderDetail.setOrderNum(new BigDecimal(0));
//            }
//            if (orderDetail.getCeeaUnitTaxPrice() == null) {
//                orderDetail.setCeeaUnitTaxPrice(new BigDecimal(0));
//            }
//            if (orderDetail.getCeeaUnitNoTaxPrice() == null) {
//                orderDetail.setCeeaUnitNoTaxPrice(new BigDecimal(0));
//            }
//            BigDecimal ceeaTaxRate = orderDetail.getCeeaTaxRate().divide(new BigDecimal(100), 4, BigDecimal.ROUND_HALF_UP).add(new BigDecimal(1));
//            orderDetail.setCeeaUnitNoTaxPrice(orderDetail.getCeeaUnitTaxPrice().divide(ceeaTaxRate, 2, BigDecimal.ROUND_HALF_UP));
//
//            BigDecimal ceeaAmountIncludingTax = orderDetail.getOrderNum().multiply(orderDetail.getCeeaUnitTaxPrice());
//            BigDecimal ceeaAmountExcludingTax = orderDetail.getOrderNum().multiply(orderDetail.getCeeaUnitNoTaxPrice());
//            BigDecimal ceeaTaxAmount = ceeaAmountExcludingTax.subtract(ceeaAmountIncludingTax);
//            orderDetail.setCeeaAmountIncludingTax(ceeaAmountIncludingTax)  //含税金额
//                    .setCeeaAmountExcludingTax(ceeaAmountExcludingTax)  //不含税金额
//                    .setCeeaTaxAmount(ceeaTaxAmount);  //税额
//
//            orderDetailList.add(orderDetail);
//        }
//        /*计算合计数量，合计金额含税，合计金额不含税*/
//        BigDecimal ceeaTotalNum = BigDecimal.ZERO;
//        BigDecimal ceeaTaxAmount = BigDecimal.ZERO;
//        BigDecimal ceeaNoTaxAmount = BigDecimal.ZERO;
//        for (OrderDetail orderDetail : orderDetailList) {
//            ceeaTotalNum = ceeaTotalNum.add(orderDetail.getOrderNum());
//            ceeaTaxAmount = ceeaTaxAmount.add(orderDetail.getCeeaAmountExcludingTax());
//            ceeaNoTaxAmount = ceeaNoTaxAmount.add(orderDetail.getCeeaAmountExcludingTax());
//        }
//
//        order.setTenantId(0L)
//                .setVersion(0L)
//                .setOrderStatus(PurchaseOrderEnum.DRAFT.getValue())
//                .setCeeaPurchaseOrderDate(date)
//                .setCeeaTotalNum(ceeaTotalNum)
//                .setCeeaTaxAmount(ceeaTaxAmount)
//                .setCeeaNoTaxAmount(ceeaNoTaxAmount);
//
//        /*防止无订单明细的订单生成*/
//        if (CollectionUtils.isEmpty(orderDetailList)) {
//            throw new BaseException(LocaleHandler.getLocaleMsg("请检查本次下单数量"));
//        }
//
//        /*设置物料大类编码*/
//        List<Long> materialIds = orderDetailList.stream().map(item -> item.getMaterialId()).collect(Collectors.toList());
//        List<MaterialMaxCategoryVO> materialMaxCategoryVOList = baseClient.queryCategoryMaxCodeByMaterialIds(materialIds);
//        Map<Long, MaterialMaxCategoryVO> map = materialMaxCategoryVOList.stream().collect(Collectors.toMap(e -> e.getMaterialId(), e -> e));
//        for (int i = 0; i < orderDetailList.size(); i++) {
//            OrderDetail orderDetail = orderDetailList.get(i);
//            MaterialMaxCategoryVO materialMaxCategoryVO = map.get(orderDetail.getMaterialId());
//            if (materialMaxCategoryVO != null) {
//                orderDetail.setBigCategoryCode(materialMaxCategoryVO.getCategoryCode());
//            }
//        }
//
//        return new OrderSaveRequestDTO()
//                .setOrder(order)
//                .setAttachList(orderAttachList)
//                .setDetailList(orderDetailList)
//                .setPaymentProvisionList(orderPaymentProvisionList);
//    }
//
//    public Integer findIndexInList(List<List<RequirementManageDTO>> requirementManageDTOLists, RequirementManageDTO requirementManageDTO) {
//        Integer index = -1;
//        for (int i = 0; i < requirementManageDTOLists.size(); i++) {
//            List<RequirementManageDTO> itemList = requirementManageDTOLists.get(i);
//            RequirementManageDTO item = itemList.get(0);
//            if (ifCommonOrder(item, requirementManageDTO)) {
//                index = i;
//            }
//        }
//        return index;
//    }
//
//    /**
//     * todo 相同付款条款(付款条款还未定义)
//     * 相同业务实体，相同库存组织，相同供应商，相同采购履行，相同币种，相同税率，相同付款条款，相同项目 为相同订单
//     *
//     * @param requirementManageDTO1
//     * @param requirementManageDTO2
//     * @return
//     */
//    public boolean ifCommonOrder(RequirementManageDTO requirementManageDTO1, RequirementManageDTO requirementManageDTO2) {
//        if (requirementManageDTO1.getOrgId() != requirementManageDTO2.getOrgId()) {
//            return false;
//        }
//        if (requirementManageDTO1.getOrganizationId() != requirementManageDTO2.getOrganizationId()) {
//            return false;
//        }
//        if (requirementManageDTO1.getVendorId() != requirementManageDTO2.getVendorId()) {
//            return false;
//        }
//        if (requirementManageDTO1.getCeeaPerformUserId() != requirementManageDTO2.getCeeaPerformUserId()) {
//            return false;
//        }
//        if (requirementManageDTO1.getCurrency() != requirementManageDTO2.getCurrency()) {
//            return false;
//        }
//        if (requirementManageDTO1.getTaxRate().compareTo(requirementManageDTO2.getTaxRate()) != 1) {
//            return false;
//        }
//        return requirementManageDTO1.getCeeaProjectNum().equals(requirementManageDTO2.getCeeaProjectNum());
//    }
//
//
//    /**
//     * 校验供应商非空，有效价格非空，采购履行人非空
//     * 相同业务实体，相同库存组织，相同采购类型，相同采购履行，相同币种，相同税率，申请状态为已分配
//     *
//     * @param requirementManageDTOS
//     */
//    private void checkBeforeCreatePurchaseOrder(List<RequirementManageDTO> requirementManageDTOS) {
//        Assert.notEmpty(requirementManageDTOS, LocaleHandler.getLocaleMsg("请勾选需要创建采购订单的申请行"));
//        for (RequirementManageDTO requirementManageDTO : requirementManageDTOS) {
//            if (requirementManageDTO == null) continue;
//            if (!RequirementApplyStatus.ASSIGNED.getValue().equals(requirementManageDTO.getApplyStatus())) {
//                throw new BaseException(LocaleHandler.getLocaleMsg("存在申请行状态不为已分配的行,无法生成寻源单据"));
//            }
//            //检验“货源供应商”字段必须为“有”，检验“有效价格”字段必须为“有”
//            if (RequirementSourcingSupplier.NOT.value.equals(requirementManageDTO.getHaveSupplier())
//                    || RequirementEffectivePrice.NOT.value.equals(requirementManageDTO.getHaveEffectivePrice())) {
//                throw new BaseException(LocaleHandler.getLocaleMsg("采购申请不满足创建采购订单条件"));
//            }
//            /*校验是否存在实时价格*/
//            /*checkIfHasPrice(requirementManageDTO);*/
//            /*校验实时供应商*/
//            boolean haveSupplier = supplierClient.haveSupplier(new OrgCategory()
//                    .setCategoryId(requirementManageDTO.getCategoryId())
//                    .setOrgId(requirementManageDTO.getOrgId()));
//            if (!haveSupplier) {
//                throw new BaseException(LocaleHandler.getLocaleMsg("该物料不存在供应商,请刷新页面"));
//            }
//
//            /*判断该需求的状态是否可以创建订单*/
//            if (!requirementManageDTO.getApplyStatus().equals(RequirementApplyStatus.ASSIGNED.getValue())) {
//                Assert.notNull(null, LocaleHandler.getLocaleMsg("只有状态为已分配的需求才能创建订单"));
//            }
//            /*判断是否拥有采购履行人*/
//            Assert.notNull(requirementManageDTO.getCeeaPerformUserId() == null, LocaleHandler.getLocaleMsg("必须已经有采购履行人才可进行创建"));
//        }
//    }
//
//    private void checkIfHasPrice(RequirementManageDTO requirementManageDTO) {
//        String ifHasPrice = inqClient.ifHasPrice(new PriceLibrary()
//                .setCeeaOrgId(requirementManageDTO.getOrgId())
//                .setCeeaOrganizationId(null)
//                .setItemCode(requirementManageDTO.getMaterialCode())
//                .setItemDesc(requirementManageDTO.getMaterialName()));
//        ContractItemDto contractItemDto = new ContractItemDto();
//        contractItemDto.setOuId(requirementManageDTO.getOrgId());
//        contractItemDto.setInvId(null);
//        contractItemDto.setItemCode(requirementManageDTO.getMaterialCode());
//        contractItemDto.setItemName(requirementManageDTO.getMaterialName());
//        /*List<ContractMaterial> contractMaterialList = contractClient.queryContractPrice(contractItemDto);*/
//        if ("N".equals(ifHasPrice)) {
//            throw new BaseException(LocaleHandler.getLocaleMsg("[" + requirementManageDTO.getRequirementLineId() + "]该物料不存在有效价格,请刷新页面"));
//        }
//    }
//
//
//    private void checkHavePriceAndHaveSupplier(List<RequirementManageDTO> requirementManageDTOS) {
//        //ToDo 临时应急处理
//        List<DictItemDTO> dictItemDTOS = baseClient.listAllByDictCode("FORMULA_MATERIAL");
//
//        //空集合不处理
//        if (CollectionUtils.isEmpty(requirementManageDTOS)) {
//            return;
//        }
//        //封装结果集参数
//        List<OrgCategory> orgCategorieParam = new ArrayList<>();
//        List<PriceLibrary> priceLibraryParams = new ArrayList<>();
//        requirementManageDTOS.forEach(r -> {
//            orgCategorieParam.add(new OrgCategory()
//                    .setCategoryId(r.getCategoryId())
//                    .setOrgId(r.getOrgId()));
//
//            priceLibraryParams.add(new PriceLibrary()
//                    .setCeeaOrgId(r.getOrgId())
//                    .setCeeaOrganizationId(r.getOrganizationId())
//                    .setItemCode(r.getMaterialCode())
//                    .setItemDesc(r.getMaterialName()));
//        });
//        System.out.println("================计算时间==============");
//        Long d1 = System.currentTimeMillis();
//        //获取组织品类关系 TODO 这里查询结果集数量多会很慢，需要业务优化(多加参数)
//        List<OrgCategory> dbOrgCategoryList = supplierClient.listOrgCategory(orgCategorieParam);
//        System.out.println("==============获取组织品类关系时间花费:" + (System.currentTimeMillis() - d1) + "ms========");
//        //获取价格库记录
//        List<PriceLibrary> dbPriceLibraryList = inqClient.listPriceLibrary(priceLibraryParams);
//        Long d2 = System.currentTimeMillis();
//        System.out.println("=============获取品类和价格库时间花费" + (d2 - d1) + "ms/=================");
//        List<RequirementLine> updateRequirementLines = new ArrayList<>();
//        //对返回结果集进行二次处理 判断 是否有货源供应商、是否有效价格
//        for (RequirementManageDTO manageDTO : requirementManageDTOS) {
//            if (manageDTO == null) continue;
//            RequirementLine requirementLine = new RequirementLine();
//            AtomicBoolean haveSupplier = new AtomicBoolean(false);
//            dbOrgCategoryList.stream().forEach(dbOrgCategory -> {
//                boolean greenSupplier = dbOrgCategory.getOrgId().equals(manageDTO.getOrgId()) &&
//                        dbOrgCategory.getCategoryId().equals(manageDTO.getCategoryId()) &&
//                        !CategoryStatus.RED.name().equals(dbOrgCategory.getServiceStatus());
//                if (greenSupplier) {
//                    haveSupplier.set(true);
//                }
//            });
//            //有货源供应商 才会有 有效价格
//            if (haveSupplier.get()) {
//                manageDTO.setHaveSupplier(RequirementSourcingSupplier.HAVE.value);
//                requirementLine.setRequirementLineId(manageDTO.getRequirementLineId())
//                        .setHaveSupplier(RequirementSourcingSupplier.HAVE.value);
//
//                boolean priceLibraryNotExist = dbPriceLibraryList.stream().filter(p -> {
//                    boolean exist = Objects.nonNull(p.getCeeaOrgId()) &&
//                            Objects.nonNull(p.getCeeaOrganizationId()) &&
//                            Objects.nonNull(p.getItemCode()) &&
//                            Objects.nonNull(p.getItemDesc()) &&
//                            p.getCeeaOrgId().equals(manageDTO.getOrgId()) &&
//                            p.getCeeaOrganizationId().equals(manageDTO.getOrganizationId()) &&
//                            p.getItemCode().equals(manageDTO.getMaterialCode()) &&
//                            p.getItemDesc().equals(manageDTO.getMaterialName());
//                    if (Objects.nonNull(manageDTO.getVendorId())) {
//                        exist = manageDTO.getVendorId().equals(p.getVendorId());
//                    }
//                    return exist;
//                }).collect(Collectors.toList()).isEmpty();
//
//                //假如价格库没有有效价格,则查询合同是否存在有效价格
//                if (priceLibraryNotExist) {
//                    ContractMaterial contractMaterial = new ContractMaterial();
//                    contractMaterial.setBuId(manageDTO.getOrgId())
//                            .setInvId(manageDTO.getOrganizationId())
//                            .setMaterialCode(manageDTO.getMaterialCode())
//                            .setMaterialName(manageDTO.getMaterialName());
//                    if (manageDTO.getVendorId() != null) {
//                        contractMaterial.setVendorId(manageDTO.getVendorId());
//                    }
//                    List<ContractVo> contractVos = contractClient.listEffectiveContractByParam(contractMaterial);
//                    manageDTO.setHaveEffectivePrice(contractVos.isEmpty() ? YesOrNo.NO.getValue() : YesOrNo.YES.getValue());
//                    requirementLine.setHaveEffectivePrice(contractVos.isEmpty() ? YesOrNo.NO.getValue() : YesOrNo.YES.getValue());
//                } else {
//                    manageDTO.setHaveEffectivePrice(YesOrNo.YES.getValue());
//                    requirementLine.setHaveEffectivePrice(YesOrNo.YES.getValue());
//                }
//                //没有货源供应商，就没有 有效价格
//            } else {
//                manageDTO.setHaveSupplier(RequirementSourcingSupplier.NOT.value);
//                requirementLine.setHaveSupplier(RequirementSourcingSupplier.NOT.value) //没有货源供应商
//                        .setHaveEffectivePrice(YesOrNo.NO.getValue());         //没有有效价格
//            }
//            /*临时处理*/
//            String materialCode = manageDTO.getMaterialCode();
//            String materialName = manageDTO.getMaterialName();
//            for (DictItemDTO dictItemDTO : dictItemDTOS) {
//                if (dictItemDTO.getDictItemCode().equals(materialCode)) {
//                    manageDTO.setHaveEffectivePrice(YesOrNo.NO.getValue());
//                    requirementLine.setHaveEffectivePrice(YesOrNo.NO.getValue());
//                }
//            }
//            /*临时处理*/
//            updateRequirementLines.add(requirementLine);
//        }
//
//        Long d3 = System.currentTimeMillis();
//        System.out.println("==================对比，以及查询合同信息花费时间:(" + (d3 - d2) + ")");
//        //异步保存
//        CompletableFuture.runAsync(() ->
//                updateRequirementLineSupplierAndEffectivePrice(updateRequirementLines, requirementManageDTOS));
//
//        System.out.println("==================异步保存花费时间:(" + (System.currentTimeMillis() - d3));
//
//    }
//
//    /**
//     * 更新需求池行表信息
//     *
//     * @param updateRequirementLines
//     */
//    public void updateRequirementLineSupplierAndEffectivePrice(List<RequirementLine> updateRequirementLines,
//                                                               List<RequirementManageDTO> requirementManageDTOS) {
//        //比对，只更新不同的记录
//        List<RequirementLine> needUpdateRequirementLine = new ArrayList<>();
//        for (RequirementLine updateRecord : updateRequirementLines) {
//            for (RequirementManageDTO dto : requirementManageDTOS) {
//                boolean curFlag = dto.getRequirementLineId().equals(updateRecord.getRequirementLineId());
//                boolean needUpdate = !dto.getHaveSupplier().equals(updateRecord.getHaveSupplier()) ||
//                        !dto.getHaveEffectivePrice().equals(updateRecord.getHaveEffectivePrice());
//                if (curFlag && needUpdate) {
//                    needUpdateRequirementLine.add(updateRecord);
//                }
//            }
//        }
//        iRequirementLineService.updateBatchById(needUpdateRequirementLine);
//    }
//
//    private void checkHaveSupplier(RequirementManageDTO manageDTO) {
//        boolean haveSupplier = supplierClient.haveSupplier(new OrgCategory()
//                .setCategoryId(manageDTO.getCategoryId())
//                .setOrgId(manageDTO.getOrgId()));
//        if (haveSupplier) {
//            manageDTO.setHaveSupplier(RequirementSourcingSupplier.HAVE.value);
//            iRequirementLineService.updateById(new RequirementLine()
//                    .setRequirementLineId(manageDTO.getRequirementLineId())
//                    .setHaveSupplier(RequirementSourcingSupplier.HAVE.value));
//            String havePrice = inqClient.ifHasPrice(new PriceLibrary()
//                    .setCeeaOrgId(manageDTO.getOrgId())
//                    .setCeeaOrganizationId(manageDTO.getOrganizationId())
//                    .setItemCode(manageDTO.getMaterialCode())
//                    .setItemDesc(manageDTO.getMaterialName()));
//            if (YesOrNo.YES.getValue().equals(havePrice)) {
//                manageDTO.setHaveEffectivePrice(YesOrNo.YES.getValue());
//                iRequirementLineService.updateById(new RequirementLine()
//                        .setRequirementLineId(manageDTO.getRequirementLineId())
//                        .setHaveEffectivePrice(YesOrNo.YES.getValue()));
//            } else {
//                manageDTO.setHaveEffectivePrice(YesOrNo.NO.getValue());
//                iRequirementLineService.updateById(new RequirementLine()
//                        .setRequirementLineId(manageDTO.getRequirementLineId())
//                        .setHaveEffectivePrice(YesOrNo.NO.getValue()));
//            }
//        } else {
//            manageDTO.setHaveSupplier(RequirementSourcingSupplier.NOT.value);
//            iRequirementLineService.updateById(new RequirementLine()
//                    .setRequirementLineId(manageDTO.getRequirementLineId())
//                    .setHaveSupplier(RequirementSourcingSupplier.NOT.value));
//            //设置有效价格为无
//            manageDTO.setHaveEffectivePrice(YesOrNo.NO.getValue());
//            iRequirementLineService.updateById(new RequirementLine()
//                    .setRequirementLineId(manageDTO.getRequirementLineId())
//                    .setHaveEffectivePrice(YesOrNo.NO.getValue()));
//        }
//    }
//
//
//    @Override
//    public PageInfo<RequirementManageDTO> listPageByParamNew(RequirementManageDTO requirementManageDTO) {
//        /**
//         * "ifCreateFollowForm": "N" 是否创建后续单据
//         * 	"requirementHeadNum": "test", 申请编号
//         * 	"orgIds": [7665554795921408], 业务实体s
//         * 	"organizationIds": [7699180544983040],库存组织s
//         * 	"materialCode": "M000626",物料编码
//         * 	"categoryIds": 7983382732865536, 小类id
//         * 	"ceeaStrategyUserNickname": "12", 策略负责
//         * 	"ceeaDepartmentName": "12", 申请部门
//         * 	"requirementSource": "12", 需求来源
//         * 	"endApplyDate": "2020-11-20", 申请结束日期
//         * 	"startApplyDate": "2020-11-28", 申请开始日期
//         * 	"ceeaPerformUserNickname": "12", 采购履行
//         * 	"createdFullName": "12",  申请人
//         * 	"haveSupplier": "Y", 是否货源供应商
//         * 	"esRequirementHeadNum": "21", OA申请编号
//         * 	"followFormCode": "12", 后续单据编号
//         * 	"enable": "Y" 是否有剩余下单数量
//         * 	"haveEffectivePrice": "Y", 是否有效价格
//         * 	"dmandLineRequest": "B", 需求人
//         * 	"applyStatus": "MERGED" 申请状态
//         * 	CeeaIfDirectory 是否目录化
//         */
//        Long size = getCountByAuthData(requirementManageDTO);
//        List<RequirementManageDTO> requirementManageDTOS = filterAuthData(requirementManageDTO);
//        PageInfo<RequirementManageDTO> pageInfo = new PageInfo<>(requirementManageDTOS);
//        if (requirementManageDTOS.size() == 0) {
//            pageInfo.setList(requirementManageDTOS);
//            return pageInfo;
//        }
//        Set<Long> requirementLineIds = requirementManageDTOS.stream().map(RequirementManageDTO::getRequirementLineId).collect(Collectors.toSet());
//        requirementManageDTOS = this.baseMapper.listPageByIdsNew(requirementLineIds);
//        final List<RequirementManageDTO> point = requirementManageDTOS;
//        //遍历需求，后后续单据中已有询价、招标、竞价，不可创建寻源单
//        //后续单号 TENDER/ENQUIRY/BIDDING 时候不允许创建寻源单据
//        if (CollectionUtils.isNotEmpty(requirementLineIds)) {
//            Map<Long, List<SubsequentDocuments>> subMap = iSubsequentDocumentsService.list(Wrappers.lambdaQuery(SubsequentDocuments.class)
//                    .in(SubsequentDocuments::getRequirementLineId, requirementLineIds)
//            ).stream().collect(Collectors.groupingBy(SubsequentDocuments::getRequirementLineId));
//            calculateThreadPool.submit(() -> point.parallelStream().forEach(dto -> {
//                boolean canCreate = true;
//                List<SubsequentDocuments> temp = subMap.get(dto.getRequirementLineId());
//                if (!org.springframework.util.CollectionUtils.isEmpty(temp)) {
//                    for (SubsequentDocuments e : temp) {
//                        String type = e.getIsubsequentDocumentssType();
//                        boolean notAllayCreateSourcing = Objects.equals(RelatedDocumentsEnum.ENQUIRY.getName(), type)
//                                || Objects.equals(RelatedDocumentsEnum.BIDDING.getName(), type)
//                                || Objects.equals(RelatedDocumentsEnum.TENDER.getName(), type);
//                        if (notAllayCreateSourcing) {
//                            canCreate = false;
//                            break;
//                        }
//                    }
//                }
//                dto.setCanCreateSourcing(canCreate ? "Y" : "N");
//            })).join();
//        }
//        //检验是否有有效价格和是否有货源供应商
//        boolean change = false;
//        try {
//            change = checkHavePriceAndHaveSupplierNew(requirementManageDTOS);
//        } catch (Exception e) {
//            log.error("检验是否有有效价格和是否有货源供应商报错", e);
//            throw new BaseException(e.getMessage());
//        }
//        if (change) {
//            for (int i = requirementManageDTOS.size() - 1; i >= 0; i--) {
//                RequirementManageDTO current = requirementManageDTOS.get(i);
//                boolean supplierEquals = true;
//                if (StringUtils.isNotBlank(requirementManageDTO.getHaveSupplier())) {
//                    supplierEquals = Objects.equals(requirementManageDTO.getHaveSupplier(), current.getHaveSupplier());
//                }
//                boolean effectEquals = true;
//                if (StringUtils.isNotBlank(requirementManageDTO.getHaveEffectivePrice())) {
//                    effectEquals = Objects.equals(requirementManageDTO.getHaveEffectivePrice(), current.getHaveEffectivePrice());
//                }
//                if (!effectEquals || !supplierEquals) {
//                    requirementManageDTOS.remove(i);
//                }
//            }
//            if (requirementManageDTO.getPageNum() == 1) {
//                requirementManageDTO.setPageNum(0);
//            }
//            size = getCountByAuthData(requirementManageDTO);
//            if (Objects.isNull(size)) {
//                size = 0L;
//            }
//        }
//        pageInfo.setList(requirementManageDTOS);
//        pageInfo.setTotal(size);
//        return pageInfo;
//    }
//
//    private Boolean checkHavePriceAndHaveSupplierNew(List<RequirementManageDTO> requirementManageDTOS) throws Exception {
//        //空集合不处理
//        if (CollectionUtils.isEmpty(requirementManageDTOS)) {
//            return false;
//        }
//        //封装结果集参数
//        List<OrgCategory> orgCategoryParam = new LinkedList<>();
//        List<PriceLibrary> priceLibraryParams = new LinkedList<>();
//        requirementManageDTOS.forEach(r -> {
//            orgCategoryParam.add(new OrgCategory()
//                    .setCategoryId(r.getCategoryId())
//                    .setOrgId(r.getOrgId()));
//
//            priceLibraryParams.add(new PriceLibrary()
//                    .setCeeaOrgCode(r.getOrgCode())
//                    .setCeeaOrganizationCode(r.getOrganizationCode())
//                    .setItemCode(r.getMaterialCode())
//                    .setItemDesc(r.getMaterialName()));
//        });
//        CountDownLatch countDownLatch = new CountDownLatch(2);
//        //获取组织品类关系 外围3'
//        Map<String, Map<String, List<OrgCategory>>> orgCatMap = CompletableFuture.supplyAsync(() -> supplierClient.listOrgCategory(orgCategoryParam), ioThreadPool)
//                .thenApplyAsync(e -> {
//                    Map<String, Map<String, List<OrgCategory>>> result = e.stream().collect(Collectors.groupingBy(OrgCategory::getOrgCode, Collectors.groupingBy(OrgCategory::getCategoryCode)));
//                    countDownLatch.countDown();
//                    return result;
//                }, calculateThreadPool).get();
//        //获取价格库记录 目前只有库存组织、业务实体、物料编码、物料名称
//        Map<String, Map<String, List<PriceLibrary>>> invItemMap = CompletableFuture.supplyAsync(() -> inqClient.listPriceLibrary(priceLibraryParams), ioThreadPool)
//                .thenApplyAsync(e -> {
//                    Map<String, Map<String, List<PriceLibrary>>> result = e.stream().collect(Collectors.groupingBy(PriceLibrary::getCeeaOrganizationCode, Collectors.groupingBy(PriceLibrary::getItemCode)));
//                    countDownLatch.countDown();
//                    return result;
//                }, calculateThreadPool).get();
//      /*  //外围1
//        Set<String> formulaMaterialCode = CompletableFuture.supplyAsync(() -> {
//
//            Set<String> result = baseClient.listFormulaMaterialCode("FORMULA_MATERIAL");
//            countDownLatch.countDown();
//            return result;
//        }, ioThreadPool).get();*/
//
//        Queue<RequirementLine> checkUpdateList = new ConcurrentLinkedQueue<>();
//        Set<String> invCodes = new ConcurrentHashSet<>();
//        Set<String> materialCodes = new ConcurrentHashSet<>();
//        Map<Long, RequirementManageDTO> requirementMap = requirementManageDTOS.stream().collect(Collectors.toMap(RequirementManageDTO::getRequirementLineId, Function.identity()));
//        //对返回结果集进行二次处理 判断 是否有货源供应商、是否有效价格
//        countDownLatch.await();
//
//        calculateThreadPool.submit(() -> requirementManageDTOS.parallelStream().filter(Objects::nonNull).forEach(manageDTO -> {
//            RequirementLine requirementLine = new RequirementLine();
//            //设置需求行id和供应商id、物料id
//            requirementLine.setRequirementLineId(manageDTO.getRequirementLineId());
//            requirementLine.setVendorId(manageDTO.getVendorId());
//            requirementLine.setMaterialName(manageDTO.getMaterialName());
//            requirementLine.setMaterialId(manageDTO.getMaterialId());
//            requirementLine.setOrganizationId(manageDTO.getOrganizationId());
//            requirementLine.setOrganizationCode(manageDTO.getOrganizationCode());
//            requirementLine.setOrgCode(manageDTO.getOrgCode());
//            requirementLine.setOrgId(manageDTO.getOrgId());
//            requirementLine.setMaterialCode(manageDTO.getMaterialCode());
//            boolean green = false;
//            Map<String, List<OrgCategory>> catMap = orgCatMap.get(manageDTO.getOrgCode());
//            //判断是否有有效价格
//            if (!org.springframework.util.CollectionUtils.isEmpty(catMap)) {
//                List<OrgCategory> categories = catMap.get(manageDTO.getCategoryCode());
//                if (!org.springframework.util.CollectionUtils.isEmpty(categories)) {
//                    green = categories.stream().anyMatch(e -> !Objects.equals(CategoryStatus.RED.name(), e.getServiceStatus()) && !Objects.equals(CategoryStatus.BLACK.name(), e.getServiceStatus()));
//                }
//            }
//            //1.对应的组织、服务类不能为空
//            if (green) {
//                requirementLine.setHaveSupplier(RequirementSourcingSupplier.HAVE.value);
//                //获取到某个库存下面的价格
//                Map<String, List<PriceLibrary>> itemMap = invItemMap.get(manageDTO.getOrganizationCode());
//
//                if (!org.springframework.util.CollectionUtils.isEmpty(itemMap)) {
//                    //获取物料编码集合
//                    List<PriceLibrary> priceLibraries = itemMap.get(manageDTO.getMaterialCode());
//                    if (!org.springframework.util.CollectionUtils.isEmpty(priceLibraries)) {
//                        boolean find = false;
//                        for (PriceLibrary priceLibrary : priceLibraries) {
//                            ///去掉物料名称和到货地点的校验
//                            //如果物料名称不为空
//                            boolean nameEquals = true;
//                            if (StringUtils.isNotBlank(manageDTO.getMaterialName())) {
//                                nameEquals = Objects.equals(priceLibrary.getItemDesc(), manageDTO.getMaterialName());
//                            }
//                            //如果供应商id不为空，校验
//                            boolean vendorEquals = true;
//                            if (StringUtils.isNotBlank(manageDTO.getVendorCode())) {
//                                vendorEquals = Objects.equals(priceLibrary.getVendorCode(), manageDTO.getVendorCode());
//                            }
//                            if (vendorEquals && nameEquals) {
//                                find = true;
//                                break;
//                            }
//                        }
//                        //如果没有，加入到合同查询
//                        if (!find) {
//                            invCodes.add(manageDTO.getOrganizationCode());
//                            materialCodes.add(manageDTO.getMaterialCode());
//                            //这里不赋值
//                        } else {
//                            //有，设置有效价格
//                            requirementLine.setHaveEffectivePrice(YesOrNo.YES.getValue());
//                        }
//                    }
//                } else {
//                    invCodes.add(manageDTO.getOrganizationCode());
//                    materialCodes.add(manageDTO.getMaterialCode());
//                }
//            } else {
//                requirementLine.setHaveSupplier(RequirementSourcingSupplier.NOT.value) //没有货源供应商
//                        .setHaveEffectivePrice(YesOrNo.NO.getValue());         //没有有效价格
//            }
//            checkUpdateList.add(requirementLine);
//        })).join();
//        if (!org.springframework.util.CollectionUtils.isEmpty(invCodes) && !org.springframework.util.CollectionUtils.isEmpty(materialCodes)) {
//            Map<String, Object> param = new HashMap<>();
//            //价格库没有，取非协议框架合同
//            param.put("invCodes", invCodes);
//            param.put("materialCodes", materialCodes);
//            param.put("frame", "N");
//            Map<String, Map<String, List<ContractVo>>> contractMap = contractClient.listEffectiveContractByInvCodesAndMaterialCodes(param);
//            //合同里面要校验供应商id
//            calculateThreadPool.submit(() -> checkUpdateList.parallelStream().forEach(requirementLine -> {
//                if (Objects.isNull(requirementLine.getHaveEffectivePrice())) {
//                    Map<String, List<ContractVo>> materialMap = contractMap.get(requirementLine.getOrganizationCode());
//                    if (!org.springframework.util.CollectionUtils.isEmpty(materialMap)) {
//                        //通过库存，物料
//                        List<ContractVo> contractVos = materialMap.get(requirementLine.getMaterialCode());
//                        //获取同一个库存、物料下的所有合同并进行并对其赋值
//                        if (org.springframework.util.CollectionUtils.isEmpty(contractVos)) {
//                            requirementLine.setHaveEffectivePrice("N");
//                        } else {
////                            String materialName = requirementLine.getMaterialName();
//                            Long vendorId = requirementLine.getVendorId();
//                            //要找到名字相同的，供应商相同的一个，如果有
//                            boolean hasEffectPriceFromContract = contractVos.stream().anyMatch(e -> {
//
//                                boolean vendorIdEquals = true;
//                                //如果vendorId不为空
//                                if (Objects.nonNull(vendorId)) {
//                                    vendorIdEquals = Objects.equals(vendorId, e.getVendorId());
//                                }
//                                return vendorIdEquals;
//                            });
//                            requirementLine.setHaveEffectivePrice(hasEffectPriceFromContract ? "Y" : "N");
//                        }
//                    } else {
//                        requirementLine.setHaveEffectivePrice("N");
//                    }
//                }
//            })).join();
//        }
//        return updateRequirementLineSupplierAndEffectivePriceNew(checkUpdateList, requirementMap);
//    }
//
//
//    public boolean updateRequirementLineSupplierAndEffectivePriceNew(Collection<RequirementLine> updateRequirementLines,
//                                                                     Map<Long, RequirementManageDTO> requirementMap) {
//        //比对，只更新不同的记录
//        List<RequirementLine> updateList = updateRequirementLines.stream().filter(e -> {
//            RequirementManageDTO requirementManageDTOS = requirementMap.get(e.getRequirementLineId());
//            boolean notChange = Objects.equals(requirementManageDTOS.getHaveSupplier(), e.getHaveSupplier())
//                    && Objects.equals(requirementManageDTOS.getHaveEffectivePrice(), e.getHaveEffectivePrice());
//            if (!notChange) {
//                requirementManageDTOS.setHaveSupplier(e.getHaveSupplier())
//                        .setHaveEffectivePrice(e.getHaveEffectivePrice());
//            }
//            return !notChange;
//        }).map(e -> new RequirementLine().setRequirementLineId(e.getRequirementLineId())
//                .setHaveEffectivePrice(e.getHaveEffectivePrice()).setHaveSupplier(e.getHaveSupplier())
//        ).collect(Collectors.toList());
//        boolean change = false;
//        if (!org.springframework.util.CollectionUtils.isEmpty(updateList)) {
//            iRequirementLineService.updateBatchById(updateList);
//            change = true;
//        }
//        return change;
//    }
//
//    @Override
//    public Collection<RequirementManageDTO> createPurchaseOrderNew(List<RequirementManageDTO> requirementManageDTOS) {
//        //外围系统信息：PriceLibrary，OrgCategory，ContractVo
//        //校验前端传来的信息
//        //获取外围系统数据
//        //封装参数
//        List<OrgCategory> orgCategoryParam = new LinkedList<>();
//        List<PriceLibrary> priceLibraryParams = new LinkedList<>();
//        Set<String> invCodes = new HashSet<>();
//        Set<String> materialCodes = new HashSet<>();
//        Set<String> orgCodes = new HashSet<>();
//        requirementManageDTOS.forEach(r -> {
//            invCodes.add(r.getOrganizationCode());
//            materialCodes.add(r.getMaterialCode());
//            orgCodes.add(r.getOrgCode());
//            orgCategoryParam.add(new OrgCategory()
//                    .setOrgId(r.getOrgId())
//                    .setCategoryId(r.getCategoryId()));
//            priceLibraryParams.add(new PriceLibrary()
//                    .setCeeaOrgCode(r.getOrgCode())
//                    .setCeeaOrganizationCode(r.getOrganizationCode())
//                    .setItemCode(r.getMaterialCode())
//                    .setItemDesc(r.getMaterialName()));
//        });
//        List<RequirementManageDTO> shouldCheckDirect = checkBeforeCreatePurchaseOrderNewAndReturnDirectory(requirementManageDTOS);
//        Map<String, Object> paramMap = new HashMap<>();
//        paramMap.put("invCodes", invCodes);
//        paramMap.put("materialCodes", materialCodes);
//        paramMap.put("frame", shouldCheckDirect.isEmpty() ? "N" : "");
//        CountDownLatch countDownLatch = new CountDownLatch(4);
//        //组织品类关系
//        Map<String, Map<String, List<OrgCategory>>> orgCatMapT = null;
//        //框架协议合同列表，按供应商、业务实体
//        Map<String, Map<String, List<ContractVo>>> orgSupplierMapT = null;
//        //价格库，按库存组织和物料编码查询
//        Map<String, Map<String, List<PriceLibrary>>> invItemMapT = null;
//        //按库存和物料
//        Map<String, Map<String, List<ContractVo>>> contractInvAndMaterialMapT = null;
//
//        try {
//            orgCatMapT = CompletableFuture.supplyAsync(() -> supplierClient.listOrgCategory(orgCategoryParam), ioThreadPool)
//                    .thenApplyAsync(e -> {
//                        Map<String, Map<String, List<OrgCategory>>> result = new HashMap<>();
//                        if (!org.springframework.util.CollectionUtils.isEmpty(e)) {
//                            result = e.stream().collect(Collectors.groupingBy(OrgCategory::getOrgCode, Collectors.groupingBy(OrgCategory::getCategoryCode)));
//                        }
//                        countDownLatch.countDown();
//                        return result;
//                    }, calculateThreadPool).get();
//            //获取价格库记录 目前只有库存组织、业务实体、物料编码、物料名称
//            invItemMapT = CompletableFuture.supplyAsync(() -> inqClient.listPriceLibraryWithPaymentTerm(priceLibraryParams), ioThreadPool)
//                    .thenApplyAsync(e -> {
//                        Map<String, Map<String, List<PriceLibrary>>> result = new HashMap<>();
//                        if (!org.springframework.util.CollectionUtils.isEmpty(e)) {
//                            result = e.stream().collect(Collectors.groupingBy(PriceLibrary::getCeeaOrganizationCode, Collectors.groupingBy(PriceLibrary::getItemCode)));
//                        }
//                        countDownLatch.countDown();
//                        return result;
//                    }, calculateThreadPool).get();
//            //获取库存组织和物料的合同，非协议框架
//            contractInvAndMaterialMapT = CompletableFuture.supplyAsync(() -> {
//                Map<String, Map<String, List<ContractVo>>> result = contractClient.listEffectiveContractByInvCodeAndMaterialCodeWithPartner(paramMap);
//                countDownLatch.countDown();
//                return result;
//            }, calculateThreadPool).get();
//            orgSupplierMapT = CompletableFuture.supplyAsync(() -> contractClient.listEffectiveContractByOrgCodesWithPartner(orgCodes), ioThreadPool)
//                    .thenApplyAsync(e -> {
//                        Map<String, Map<String, List<ContractVo>>> result = new HashMap<>();
//                        if (!org.springframework.util.CollectionUtils.isEmpty(e)) {
//                            result = e.stream().collect(Collectors.groupingBy(ContractVo::getBuCode, Collectors.groupingBy(ContractVo::getHeadVendorCode)));
//                        }
//                        countDownLatch.countDown();
//                        return result;
//                    }).get();
//            countDownLatch.await();
//        } catch (Exception e) {
//            log.error("获取接口信息失败", e);
//            throw new BaseException("获取接口信息失败:" + e.getLocalizedMessage());
//        }
//        Map<String, Map<String, List<OrgCategory>>> orgCatMap = orgCatMapT;
//        Map<String, Map<String, List<ContractVo>>> orgSupplierMap = orgSupplierMapT;
//        Map<String, Map<String, List<PriceLibrary>>> invItemMap = invItemMapT;
//        Map<String, Map<String, List<ContractVo>>> contractInvAndMaterialMap = contractInvAndMaterialMapT;
//        //获取框架协议合同，根据ou、是框架协议
//        List<RequirementManageDTO> result = new LinkedList<>();
//        //目录化
//        /**
//         * 目录化物料申请转订单：
//         //         * 1、勾选的行，如果是否目录化为是，直接按“需求行中的物料ID+物料名称+库存组织+供应商+有效期+是否上架为是”，在价格库中可以找到记录，均可需求转订单；
//         * 2、提交订单时，需校验行中是否有对应的合同；
//         * 目录化行关联合同逻辑：按“需求行中的物料ID+物料名称+库存组织+供应商”找到合同；需过滤掉已失效的合同；需过滤掉合同在有效期内，单价格有效期过了的合同；
//         */
//        if (!org.springframework.util.CollectionUtils.isEmpty(shouldCheckDirect)) {
//            for (RequirementManageDTO item : shouldCheckDirect) {
//                Map<String, List<PriceLibrary>> itemMap = invItemMap.get(item.getOrganizationCode());
//                if (Objects.isNull(itemMap)) {
//                    throw new BaseException(String.format("目录化申请行号[%s]在价格目录找不到库存组织[%s]的有效价格", item.getRowNum(), item.getOrganizationName()));
//                }
//                List<PriceLibrary> priceLibraries = itemMap.get(item.getMaterialCode());
//                if (Objects.isNull(priceLibraries)) {
//                    throw new BaseException(String.format("目录化申请行号[%s]在价格目录找不到库存组织[%s]在物料[%s]有效价格", item.getRowNum(), item.getOrganizationName(), item.getMaterialName()));
//                }
//                List<PriceLibrary> effectPriceList = priceLibraries.stream().filter(e -> {
//                    boolean vendorEqual = true;
//                    if (Objects.nonNull(item.getVendorCode())) {
//                        vendorEqual = Objects.equals(item.getVendorCode(), e.getVendorCode());
//                    }
//                    boolean nameEquals = true;
//                    if (StringUtils.isNotBlank(item.getMaterialName())) {
//                        nameEquals = Objects.equals(e.getItemDesc(), item.getMaterialName());
//                    }
//                    return vendorEqual && Objects.equals(e.getCeeaIfUse(), "Y") && nameEquals;
//                }).collect(Collectors.toList());
//                if (org.springframework.util.CollectionUtils.isEmpty(effectPriceList)) {
//                    throw new BaseException(String.format("目录化申请行号[%s]在价格目录下找不到库存组织[%s]在物料[%s]下的有效价格", item.getRowNum(), item.getOrganizationName(), item.getMaterialName()));
//                }
//                //关联合同
//                Function<PriceLibrary, String> getPlace = s -> Objects.isNull(s.getCeeaArrivalPlace()) ? "" : s.getCeeaArrivalPlace();
//                //先根据vendorCode分组，再根据地点分组
//                Map<String, Map<String, List<PriceLibrary>>> vendorPlaceMap = effectPriceList.stream().collect(Collectors.groupingBy(PriceLibrary::getVendorCode
//                        , Collectors.groupingBy(getPlace)
//                ));
//                for (Map.Entry<String, Map<String, List<PriceLibrary>>> placeMap : vendorPlaceMap.entrySet()) {
//                    for (Map.Entry<String, List<PriceLibrary>> vendorMap : placeMap.getValue().entrySet()) {
//                        for (PriceLibrary effect : vendorMap.getValue()) {
//                            if (StringUtils.isBlank(effect.getContractCode())) {
//                                throw new BaseException(String.format("目录化申请行号[%s]在库存组织[%s]在物料[%s]下的有效价格没有维护合同", item.getRowNum(), item.getOrganizationName(), item.getMaterialName()));
//                            }
//                            ContractVo contractVo = contractInvAndMaterialMap.get(item.getOrganizationCode()).get(item.getMaterialCode())
//                                    .stream().filter(e -> {
//                                        if (e.getContractCode().contains(":")) {
//                                            int i = e.getContractCode().lastIndexOf(":");
//                                            e.setContractCode(e.getContractCode().substring(0, i));
//                                        }
//                                        return Objects.equals(e.getContractCode(), effect.getContractCode());
//                                    }).findAny().orElseThrow(() -> new BaseException(String.format("目录化申请行号[%s]在库存组织[%s]在物料[%s]下的有效价格的合同[%s]-未到生效时间或已失效"
//                                            , item.getRowNum(), item.getOrganizationName(), item.getMaterialName(), effect.getContractCode())));
//                            RequirementManageDTO requirementManageDTO = assignValueFromEffectPrice(item, effect);
//                            contractVo.setTaxedPrice(requirementManageDTO.getTaxPrice());
//                            contractVo.setTaxRate(requirementManageDTO.getTaxRate());
//                            contractVo.setTaxKey(requirementManageDTO.getTaxKey());
//                            contractVo.setUntaxedPrice(requirementManageDTO.getNotaxPrice());
//                            contractVo.setCurrencyCode(requirementManageDTO.getCurrencyCode());
//                            contractVo.setCurrencyName(requirementManageDTO.getCurrencyName());
//                            requirementManageDTO.setContractVoList(Collections.singletonList(contractVo));
//                            requirementManageDTO.setFromPrice("Y");
//                            requirementManageDTO.setCeeaPriceSourceId(effect.getPriceLibraryId());
//                            requirementManageDTO.setCeeaPriceSourceType(PriceSourceTypeEnum.PRICE_LIBRARY.getValue());
//                            requirementManageDTO.setContractNum(contractVo.getContractCode());
//                            result.add(requirementManageDTO);
//                        }
//                    }
//                }
//            }
//        }
//        if (org.springframework.util.CollectionUtils.isEmpty(requirementManageDTOS)) {
//            return result;
//        }
//        /**
//         * 1、取价格目录价格（框架协议）：到货地点、物料编码、库存组织、价格类型、有效期；（如果有到货地点，需将有到货地点和没有到货地点的行都显示出来）
//         * a、如果有多个供应商，需求行显示多行，对应不同的供应商；合同分别显示与供应商签订的框架协议；
//         * b、查合同的条件：需求行中的“业务实体+有效期+是否框架协议为是”，其中业务实体，需在合同伙伴中公司对应的业务实体内时，才可取对应的合同；
//         *    注意：如果有多个合同，默认显示最新合同，可以选择其他合同，更改合同时不更改价格等；需过滤掉过期的合同；
//         *
//         * 2、取合同价格(非框架协议）：库存组织、物料编码、合同有效期、价格有效期、非框架协议；
//         * a、如果有多个供应商，显示多行，对应不同的供应商；合同分别显示与供应商签订的合同；
//         * b、如果有多个物料描述，选择不同的合同行时，需显示不同合同行中的物料描述；生成订单时也形式合同中的物料描述；
//         *    注意：如果有多个合同，默认显示最新合同，可以选择其他合同，选择其他合同时，未税单价、含税单价、税率、币种、物料描述需要从选择的合同中带出；
//         */
//        //非目录化
//        requirementManageDTOS.stream()
//                .filter(item -> Objects.nonNull(item.getOrderQuantity()) && item.getOrderQuantity().compareTo(BigDecimal.ZERO) > 0)
//                .forEach(item -> {
//                    //有供应商id
//                    boolean hasSupplier = false;
//                    Map<String, List<OrgCategory>> categoryMap = orgCatMap.get(item.getOrgCode());
//                    if (Objects.nonNull(categoryMap)) {
//                        List<OrgCategory> categories = categoryMap.get(item.getCategoryCode());
//                        hasSupplier = categories.stream().anyMatch(e -> {
//                                    boolean vendorEquals = true;
//                                    if (StringUtils.isNotBlank(item.getVendorCode())) {
//                                        vendorEquals = Objects.equals(item.getVendorCode(), e.getCompanyCode());
//                                    }
//                                    boolean effectVendor = Objects.equals(e.getServiceStatus(), CategoryStatus.YELLOW.name())
//                                            || Objects.equals(e.getServiceStatus(), CategoryStatus.GREEN.name())
//                                            || Objects.equals(e.getServiceStatus(), CategoryStatus.ONE_TIME.name())
//                                            || Objects.equals(e.getServiceStatus(), CategoryStatus.VERIFY.name());
//                                    return effectVendor && vendorEquals;
//                                }
//                        );
//                    }
//                    if (!hasSupplier) {
//                        throw new BaseException(String.format("申请行号[%s]需要-有效供应商才方可生成采购订单", item.getRowNum()));
//                    }
//                    Map<String, List<PriceLibrary>> priceLibraryMap = invItemMap.get(item.getOrganizationCode());
//                    //物料名称、交货地点
//                    List<PriceLibrary> effectPriceList = new LinkedList<>();
//                    boolean hasEffectPrice = false;
//                    if (Objects.nonNull(priceLibraryMap)) {
//                        List<PriceLibrary> priceLibraries = priceLibraryMap.get(item.getMaterialCode());
//                        if (!org.springframework.util.CollectionUtils.isEmpty(priceLibraries)) {
//                            for (PriceLibrary priceLibrary : priceLibraries) {
//                                ///去掉物料名称和到货地点校验
//                                //如果物料名称不为空
//                                boolean nameEquals = true;
//                                if (StringUtils.isNotBlank(item.getMaterialName())) {
//                                    nameEquals = Objects.equals(priceLibrary.getItemDesc(), item.getMaterialName());
//                                }
//                                //如果供应商id不为空，校验
//                                boolean vendorEquals = true;
//                                if (StringUtils.isNotBlank(item.getVendorCode())) {
//                                    vendorEquals = Objects.equals(priceLibrary.getVendorCode(), item.getVendorCode());
//                                }
//                                if (vendorEquals && nameEquals) {
//                                    //找到有效的价格
//                                    if (Objects.nonNull(priceLibrary.getNotaxPrice()) &&
//                                            Objects.nonNull(priceLibrary.getTaxPrice())
//                                            && StringUtils.isNotBlank(priceLibrary.getTaxKey())
//                                            && StringUtils.isNotBlank(priceLibrary.getTaxRate())
//                                            && StringUtils.isNotBlank(priceLibrary.getCurrencyCode())) {
//                                        effectPriceList.add(priceLibrary);
//                                    }
//                                }
//                            }
//                        }
//                        //如果有效价格,这里已经确保了获取的是无供应商和有供应商的情况
//                        if (!org.springframework.util.CollectionUtils.isEmpty(effectPriceList)) {
//                            hasEffectPrice = true;
//                            //根据供应商分组
//                            Function<PriceLibrary, String> getPlace = s -> Objects.isNull(s.getCeeaArrivalPlace()) ? "" : s.getCeeaArrivalPlace();
//                            //先根据vendorCode分组，再根据地点分组
//                            Map<String, Map<String, List<PriceLibrary>>> vendorPlaceMap = effectPriceList.stream().collect(Collectors.groupingBy(PriceLibrary::getVendorCode
//                                    , Collectors.groupingBy(getPlace)
//                            ));
//                            for (Map.Entry<String, Map<String, List<PriceLibrary>>> placeMap : vendorPlaceMap.entrySet()) {
//                                for (Map.Entry<String, List<PriceLibrary>> place : placeMap.getValue().entrySet()) {
//                                    for (PriceLibrary effect : place.getValue()) {
//                                        RequirementManageDTO requirementManageDTO = assignValueFromEffectPrice(item, effect);
//                                        //获取供应商和对应的协议框架合同
//                                        if (Objects.isNull(orgSupplierMap)) {
//                                            throw new BaseException(String.format("申请行号[%s]存在有效价格，但对应的业务实体-[%s]无框架协议合同,无法生成采购订单", item.getRowNum(), item.getOrgName()));
//                                        }
//                                        Map<String, List<ContractVo>> vendorMap = orgSupplierMap.get(requirementManageDTO.getOrgCode());
//                                        if (Objects.isNull(vendorMap)) {
//                                            throw new BaseException(String.format("申请行号[%s]存在有效价格，但对应的业务实体-[%s]无框架协议合同,无法生成采购订单", item.getRowNum(), item.getOrgName()));
//                                        }
//                                        List<ContractVo> contractVos = vendorMap.get(effect.getVendorCode());
//                                        if (!org.springframework.util.CollectionUtils.isEmpty(contractVos)) {
//                                            //赋值
//                                            List<ContractVo> partnerContract = contractVos.stream().filter(e -> Objects.equals(e.getBuCode(), item.getOrgCode())
//                                            ).collect(Collectors.toList());
//                                            if (!org.springframework.util.CollectionUtils.isEmpty(partnerContract)) {
//                                                for (ContractVo contractVo : partnerContract) {
//                                                    contractVo.setTaxedPrice(requirementManageDTO.getTaxPrice());
//                                                    contractVo.setTaxRate(requirementManageDTO.getTaxRate());
//                                                    contractVo.setTaxKey(requirementManageDTO.getTaxKey());
//                                                    contractVo.setUntaxedPrice(requirementManageDTO.getNotaxPrice());
//                                                    contractVo.setCurrencyCode(requirementManageDTO.getCurrencyCode());
//                                                    contractVo.setCurrencyName(requirementManageDTO.getCurrencyName());
//                                                }
//                                                List<ContractVo> newContract = getContractVosCopyAndSort(partnerContract);
//                                                requirementManageDTO.setContractVoList(newContract);
//                                                requirementManageDTO.setContractNum(newContract.get(contractVos.size() - 1).getContractCode());
//                                            }
//                                        }
//                                        requirementManageDTO.setFromPrice("Y");
//                                        requirementManageDTO.setCeeaPriceSourceId(effect.getPriceLibraryId());
//                                        requirementManageDTO.setCeeaPriceSourceType(PriceSourceTypeEnum.PRICE_LIBRARY.getValue());
//                                        result.add(requirementManageDTO);
//                                    }
//                                }
//                            }
//                        } else {
//                            hasEffectPrice = false;
//                        }
//                    }
//                    //没有有效价格处理
//                    if (!hasEffectPrice) {
//                        //如果没有有效价格，找非框架协议的合同
//                        Map<String, List<ContractVo>> materialMap = contractInvAndMaterialMap.get(item.getOrganizationCode());
//                        if (Objects.nonNull(materialMap)) {
//                            List<ContractVo> contractVos = materialMap.get(item.getMaterialCode());
//                            if (org.springframework.util.CollectionUtils.isEmpty(contractVos)) {
//                                throw new BaseException(String.format("申请行号[%s]无有效价格、无可用合同", item.getRowNum()));
//                            }
//                            //根据物料编码、库存组织、非框架协议、合同有效期、价格有效期
//                            List<ContractVo> notPriceContractVO = contractVos.stream().filter(e -> Objects.equals(e.getIsFrameworkAgreement(), "N")
//                                    && Objects.nonNull(e.getTaxedPrice())
//                                    && StringUtils.isNotBlank(e.getTaxKey())
//                                    && Objects.nonNull(e.getTaxRate())
//                                    && Objects.equals(e.getMaterialCode(), item.getMaterialCode())
//                                    && item.getVendorCode() == null || Objects.equals(e.getVendorCode(), item.getVendorCode())
//                                    && StringUtils.isNotBlank(e.getCurrencyCode())
//                            ).peek(e -> {
//                                BigDecimal taxRate = e.getTaxRate().divide(new BigDecimal(100), 8, BigDecimal.ROUND_HALF_UP);
//                                BigDecimal untaxedPrice = e.getTaxedPrice().divide((new BigDecimal(1).add(taxRate)), 2, BigDecimal.ROUND_HALF_UP);
//                                e.setUntaxedPrice(untaxedPrice);
//                            }).collect(Collectors.toList());
//                            if (org.springframework.util.CollectionUtils.isEmpty(notPriceContractVO)) {
//                                throw new BaseException(String.format("申请行号[%s]无有效价格、无可用合同", item.getRowNum()));
//                            }
//                            //根据供应商获取
//                            Map<String, List<ContractVo>> contractMap = notPriceContractVO.stream().collect(Collectors.groupingBy(ContractVo::getHeadVendorCode));
//                            for (Map.Entry<String, List<ContractVo>> vendorMap : contractMap.entrySet()) {
//                                List<ContractVo> partnerContract = vendorMap.getValue();
//                                RequirementManageDTO requirementManageDTO = BeanCopyUtil.copyProperties(item, RequirementManageDTO::new);
//                                List<ContractVo> newContract = getContractVosCopyAndSort(partnerContract);
//                                ContractVo contractVo = newContract.get(partnerContract.size() - 1);
//                                requirementManageDTO.setVendorId(contractVo.getHeadVendorId());
//                                requirementManageDTO.setVendorName(contractVo.getHeadVendorName());
//                                requirementManageDTO.setVendorCode(contractVo.getHeadVendorCode());
//                                requirementManageDTO.setMaterialName(contractVo.getMaterialName());
//                                requirementManageDTO.setCurrencyId(contractVo.getCurrencyId());
//                                requirementManageDTO.setCurrencyCode(contractVo.getCurrencyCode());
//                                requirementManageDTO.setCurrencyName(contractVo.getCurrencyName());
//                                requirementManageDTO.setNotaxPrice(contractVo.getUntaxedPrice());
//                                requirementManageDTO.setTaxPrice(contractVo.getTaxedPrice());
//                                requirementManageDTO.setTaxKey(contractVo.getTaxKey());
//                                requirementManageDTO.setTaxRate(contractVo.getTaxRate());
//                                requirementManageDTO.setContractNum(contractVo.getContractCode());
//                                requirementManageDTO.setContractVoList(partnerContract);
//                                requirementManageDTO.setFromPrice("N");
//                                requirementManageDTO.setCeeaPriceSourceId(contractVo.getContractMaterialId());
//                                requirementManageDTO.setCeeaPriceSourceType(PriceSourceTypeEnum.CONTRACT.getValue());
//                                List<OrderPaymentProvision> paymentProvisions = contractVo.getPayPlanList().stream().map(e -> {
//                                    OrderPaymentProvision orderPaymentProvision = new OrderPaymentProvision()
//                                            .setPaymentPeriod(StringUtil.StringValue(e.getDateNum()))  //付款账期
//                                            .setPaymentWay(e.getPayMethod())  //付款方式
//                                            .setPaymentTerm(e.getPayExplain());  //付款条件
//                                    return orderPaymentProvision;
//                                }).collect(Collectors.toList());
//                                requirementManageDTO.setOrderPaymentProvisionList(paymentProvisions);
//                                requirementManageDTO.setContractVoList(newContract);
//                                result.add(requirementManageDTO);
//                            }
//                        }
//                    }
//                });
//        return result;
//    }
//
//    private RequirementManageDTO assignValueFromEffectPrice(RequirementManageDTO item, PriceLibrary effect) {
//        if (Objects.isNull(effect.getCeeaQuotaProportion())) {
//            effect.setCeeaQuotaProportion(BigDecimal.ZERO);
//        }
//        RequirementManageDTO requirementManageDTO = BeanCopyUtil.copyProperties(item, RequirementManageDTO::new);
//        BigDecimal quotaProportion = effect.getCeeaQuotaProportion().divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP);
//        effect.setCeeaQuotaProportion(quotaProportion);
//        requirementManageDTO.setThisOrderQuantity(quotaProportion.multiply(item.getRequirementQuantity()))
//                .setQuotaProportion(effect.getCeeaQuotaProportion())
//                .setVendorId(effect.getVendorId())
//                .setVendorName(effect.getVendorName())
//                .setVendorCode(effect.getVendorCode())
//                .setCurrencyId(effect.getCurrencyId())
//                .setCurrencyCode(effect.getCurrencyCode())
//                .setCurrencyName(effect.getCurrencyName())
//                .setTaxRate(new BigDecimal(effect.getTaxRate()))
//                .setTaxKey(effect.getTaxKey())
//                .setTaxPrice(effect.getTaxPrice())
//                .setNotaxPrice(effect.getNotaxPrice())
//                .setMinOrderQuantity(effect.getMinOrderQuantity());
//        requirementManageDTO.setTaxRate(new BigDecimal(effect.getTaxRate()));
//        List<OrderPaymentProvision> paymentProvisions = effect.getPaymentTerms().stream().map(e -> {
//            OrderPaymentProvision orderPaymentProvision = new OrderPaymentProvision()
//                    .setPaymentPeriod(StringUtil.StringValue(e.getPaymentDay()))  //付款账期
//                    .setPaymentWay(e.getPaymentWay())  //付款方式
//                    .setPaymentTerm(e.getPaymentTerm());  //付款条件
//            return orderPaymentProvision;
//        }).collect(Collectors.toList());
//        requirementManageDTO.setOrderPaymentProvisionList(paymentProvisions);
//        return requirementManageDTO;
//    }
//
//
//    private List<RequirementManageDTO> checkBeforeCreatePurchaseOrderNewAndReturnDirectory(List<RequirementManageDTO> requirementManageDTOS) {
//        if (org.springframework.util.CollectionUtils.isEmpty(requirementManageDTOS)) {
//            throw new BaseException("所选申请行不能为空");
//        }
//        List<RequirementManageDTO> directList = new LinkedList<>();
//        for (int i = requirementManageDTOS.size() - 1; i >= 0; i--) {
//            RequirementManageDTO requirementManageDTO = requirementManageDTOS.get(i);
//            if (Objects.equals(requirementManageDTO.getCeeaIfDirectory(), "Y")) {
//                RequirementManageDTO remove = requirementManageDTOS.remove(i);
//                directList.add(remove);
//                continue;
//            }
//            if (!Objects.equals(requirementManageDTO.getApplyStatus(), RequirementApplyStatus.ASSIGNED.getValue())) {
//                throw new BaseException(String.format("申请行号[%s]状态为[已分配]才可以生成采购订单", requirementManageDTO.getRowNum()));
//            }
//            if (Objects.equals(requirementManageDTO.getHaveEffectivePrice(), YesOrNo.NO.getValue())) {
//                throw new BaseException(String.format("申请行号[%s]需要-有效价格方可生成采购订单", requirementManageDTO.getRowNum()));
//            }
//            if (Objects.equals(requirementManageDTO.getHaveSupplier(), YesOrNo.NO.getValue())) {
//                throw new BaseException(String.format("申请行号[%s]需要-有效供应商才方可生成采购订单", requirementManageDTO.getRowNum()));
//            }
//            if (Objects.isNull(requirementManageDTO.getCeeaPerformUserId())) {
//                throw new BaseException(String.format("申请行号[%s]需要-采购履行人方可生成采购订单", requirementManageDTO.getRowNum()));
//            }
//        }
//        return directList;
//    }
//
//
//    private List<ContractVo> getContractVosCopyAndSort(List<ContractVo> partnerContract) {
//        Map<String, List<ContractVo>> collect = partnerContract.stream().collect(Collectors.groupingBy(ContractVo::getContractCode));
//        for (Map.Entry<String, List<ContractVo>> entry : collect.entrySet()) {
//            if (entry.getValue().size() > 1) {
//                for (int i = 1; i < entry.getValue().size(); i++) {
//                    ContractVo contractVo = entry.getValue().get(i);
//                    contractVo.setContractCode(contractVo.getContractCode() + i);
//                }
//            }
//        }
//        List<ContractVo> newContract = partnerContract.stream().map(e -> {
//            ContractVo temp = BeanCopyUtil.copyProperties(e, ContractVo::new);
//            List<PayPlan> list = e.getPayPlanList().stream().map(p -> BeanCopyUtil.copyProperties(p, PayPlan::new)).collect(Collectors.toList());
//            temp.setPayPlanList(list);
//            return temp;
//        }).collect(Collectors.toList());
//        newContract.sort(Comparator.comparing(ContractVo::getCreationDate));
//        return newContract;
//    }

    @Override
    public PageInfo<RequirementHead> listPage(RequirementHeadQueryDTO requirementHeadQueryDTO) {
        return null;
    }

    @Override
    public void deleteByHeadId(Long requirementHeadId) {

    }

    @Override
    public Long addPurchaseRequirement(PurchaseRequirementDTO purchaseRequirementDTO, String auditStatus) {
        return null;
    }

    @Override
    public PurchaseRequirementDTO getByHeadId(Long requirementHeadId) {
        return null;
    }

    @Override
    public Long modifyPurchaseRequirement(PurchaseRequirementDTO purchaseRequirementDTO) {
        return null;
    }

    @Override
    public BaseResult<String> bachRequirement(List<RequirementHead> requirementHeadList) {
        return null;
    }

    @Override
    public void updateApprovelStatus(Long requirementHeadId, String auditStatus) {

    }

    @Override
    public void updateApproved(Long requirementHeadId, String auditStatus) {

    }

    @Override
    public void updateApproved1(Long requirementHeadId, String auditStatus) {

    }

    @Override
    public void reject(Long requirementHeadId, String rejectReason) {

    }

    @Override
    public void withdraw(Long requirementHeadId, String rejectReason) {

    }

    @Override
    public void abandon(Long requirementHeadId) {

    }

    @Override
    public FSSCResult submitApproval(PurchaseRequirementDTO purchaseRequirementDTO, String auditStatus) {
        return null;
    }

    @Override
    public PageInfo<RequirementManageDTO> listPageByParam(RequirementManageDTO requirementManageDTO) {
        return null;
    }

    @Override
    public List<RequirementManageDTO> createPurchaseOrder(List<RequirementManageDTO> requirementManageDTOS) {
        return null;
    }

    @Override
    public Collection<RequirementManageDTO> createPurchaseOrderNew(List<RequirementManageDTO> requirementManageDTOS) {
        return null;
    }

    @Override
    public void submitPurchaseOrder(List<RequirementManageDTO> requirementManageDTOS) {

    }

    @Override
    public RequirementHead getRequirementHeadByParam(RequirementHeadQueryDTO requirementHeadQueryDTO) {
        return null;
    }

    @Override
    public RequirementLineVO createSourceForm(List<RequirementManageDTO> requirementManageDTOS, int sourcingType) {
        return null;
    }

    @Override
    public BaseResult approvalMrp(PurchaseRequirementDTO purchaseRequirementDTO, String auditStatus) {
        return null;
    }

    @Override
    public void assignByDivisionCategory(RequirementHead requirementHead, List<RequirementLine> requirementLineList) {

    }

    @Override
    public PageInfo<RequirementManageDTO> listPageByParamNew(RequirementManageDTO requirementManageDTO) {
        return null;
    }

    @Override
    public void tranferOrders(RequirementHead requirementHead, List<RequirementLine> requirementLineList) {

    }

    @Override
    public void submitPurchaseOrderNew(List<RequirementManageDTO> requirementManageDTOS) {

    }
}
