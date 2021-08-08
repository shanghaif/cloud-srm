package com.midea.cloud.srm.sup.change.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.constants.DimConstant;
import com.midea.cloud.common.constants.SequenceCodeConstant;
import com.midea.cloud.common.enums.*;
import com.midea.cloud.common.enums.flow.CbpmFormTemplateIdEnum;
import com.midea.cloud.common.enums.sup.InfoChangeStatus;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.*;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.feign.flow.WorkFlowFeign;
import com.midea.cloud.srm.feign.rbac.RbacClient;
import com.midea.cloud.srm.model.common.FormResultDTO;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.flow.process.dto.CbpmRquestParamDTO;
import com.midea.cloud.srm.model.rbac.permission.entity.Permission;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.supplier.change.dto.ChangeFlowResultDTO;
import com.midea.cloud.srm.model.supplier.change.dto.ChangeInfoDTO;
import com.midea.cloud.srm.model.supplier.change.dto.ChangeRequestDTO;
import com.midea.cloud.srm.model.supplier.change.dto.InfoChangeDTO;
import com.midea.cloud.srm.model.supplier.change.entity.*;
import com.midea.cloud.srm.model.supplier.dim.entity.Dim;
import com.midea.cloud.srm.model.supplier.info.entity.*;
import com.midea.cloud.srm.model.workflow.entity.SrmFlowBusWorkflow;
import com.midea.cloud.srm.sup.change.mapper.InfoChangeMapper;
import com.midea.cloud.srm.sup.change.service.*;
import com.midea.cloud.srm.sup.change.workflow.VendorInfoChangeFlow;
import com.midea.cloud.srm.sup.dim.service.IDimService;
import com.midea.cloud.srm.sup.info.service.*;
import com.midea.cloud.srm.sup.statuslog.service.ICompanyStatusLogService;
import feign.FeignException;
import net.sf.cglib.core.Local;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.reflections.util.Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <pre>
 *  公司信息变更表 服务实现类
 * </pre>
 *
 * @author chensl26@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-30 19:57:36
 *  修改内容:
 * </pre>
 */
@Service
public class InfoChangeServiceImpl extends ServiceImpl<InfoChangeMapper, InfoChange> implements IInfoChangeService {

    @Autowired
    private BaseClient baseClient;

    @Autowired
    private FileCenterClient fileCenterClient;

    @Autowired
    private InfoChangeMapper infoChangeMapper;

    @Autowired
    private IDimService iDimService;

    @Autowired
    private IDimFieldContextChangeService iDimFieldContextChangeService;

    @Autowired
    private IContactInfoChangeService iContactInfoChangeService;

    @Autowired
    private IOtherInfoChangeService iOtherInfoChangeService;

    @Autowired
    private IBankInfoChangeService iBankInfoChangeService;

    @Autowired
    private ISiteInfoChangeService iSiteInfoChangeService;

    @Autowired
    private IBusinessInfoChangeService iBusinessInfoChangeService;

    @Autowired
    private IHonorInfoChangeService iHonorInfoChangeService;

    @Autowired
    private IHolderInfoChangeService iHolderInfoChangeService;

    @Autowired
    private IFinanceInfoChangeService iFinanceInfoChangeService;

    @Autowired
    private IOrgCategoryChangeService iOrgCategoryChangeService;

    @Autowired
    private IOrgInfoChangeService iOrgInfoChangeService;

    @Autowired
    private IOperationInfoChangeService iOperationInfoChangeService;

    @Autowired
    private ICompanyInfoChangeService iCompanyInfoChangeService;

    @Autowired
    private ICompanyInfoService iCompanyInfoService;

    @Autowired
    private ManagementAttachChangeService managementAttachChangeService;

    @Autowired
    private IFileuploadChangeService iFileuploadChangeService;

    @Autowired
    private ICompanyStatusLogService iCompanyStatusLogService;

    @Autowired
    private RbacClient rbacClient;

    @Autowired
    private WorkFlowFeign workFlowFeign;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private IManagementAttachService iManagementAttachService;

    @Autowired
    private IManagementInfoService iManagementInfoService;

    /**
     * 供应商银行信息Service
     */
    @Resource
    private IBankInfoService iBankInfoService;

    /**
     * 供应商联系人信息Service
     */
    @Resource
    private IContactInfoService iContactInfoService;

    /**
     * 供应商地点信息Service
     */
    @Resource
    private ISiteInfoService iSiteInfoService;

    private final static String defaultUsername = "admin";

    @Autowired
    private VendorInfoChangeFlow vendorInfoChangeFlow;

    public final ThreadPoolExecutor submitExector;

    public InfoChangeServiceImpl() {
        int cpuCount = Runtime.getRuntime().availableProcessors();
        submitExector = new ThreadPoolExecutor(cpuCount * 2, cpuCount * 2,
                0, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100),
                new NamedThreadFactory("ERP-message-sender", true),
                new ThreadPoolExecutor.CallerRunsPolicy());
    }

    @Override
    public InfoChange addInfoChange(InfoChange infoChange) {
        Long id = IdGenrator.generate();
        infoChange.setChangeId(id);
        infoChange.setChangeApplyNo(baseClient.seqGen(SequenceCodeConstant.SEQ_SUP_COMPANY_CHANGE_NUM));
        this.save(infoChange);
        return infoChange;
    }

    @Override
    public InfoChange updateInfoChange(InfoChange infoChange) {
        Assert.notNull(infoChange.getChangeId(), LocaleHandler.getLocaleMsg("id不能为空"));
        this.updateById(infoChange);
        return infoChange;
    }

    @Override
    public List<InfoChangeDTO> listPageByParam(ChangeRequestDTO changeRequestDTO) {
        PageUtil.startPage(changeRequestDTO.getPageNum(), changeRequestDTO.getPageSize());
        return infoChangeMapper.listPageByParam(changeRequestDTO);
    }

    @Override
    public PageInfo<InfoChangeDTO> listPageByParamPage(ChangeRequestDTO changeRequestDTO) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        if (loginAppUser != null && UserType.VENDOR.name().equals(loginAppUser.getUserType())) {
            if (loginAppUser.getCompanyId() != null) {
                changeRequestDTO.setCompanyId(loginAppUser.getCompanyId());
            } else {
                return new PageInfo<>(new ArrayList<>());
            }
        }
        PageUtil.startPage(changeRequestDTO.getPageNum(), changeRequestDTO.getPageSize());
        return new PageInfo<InfoChangeDTO>(this.listPageByParam(changeRequestDTO));
    }

    private ChangeFlowResultDTO checkChangeFlow(String orderType, String userType) {
        Assert.notNull(orderType, LocaleHandler.getLocaleMsg("不能传空值"));
        Assert.notNull(userType, LocaleHandler.getLocaleMsg("不能传空值"));
        ChangeFlowResultDTO changeFlowResultDTO = new ChangeFlowResultDTO();
        Dim companyDim = iDimService.queryByParam(orderType);
        //区分供应商以及采购商的情况
        if (companyDim != null) {
            if (StringUtils.isNotEmpty(userType) && UserType.VENDOR.name().equals(userType)) {
                changeFlowResultDTO.setIsAllowChange(YesOrNo.YES.getValue().equals(companyDim.getIsSupply()));
            } else {
                changeFlowResultDTO.setIsAllowChange(YesOrNo.YES.getValue().equals(companyDim.getIsBuyer()));
            }
            changeFlowResultDTO.setIsFlow(YesOrNo.YES.getValue().equals(companyDim.getIsFlow()));
        } else {
            changeFlowResultDTO.setIsAllowChange(false);
            changeFlowResultDTO.setIsFlow(false);
        }
        return changeFlowResultDTO;
    }

    /**
     * 供应商信息变更 审批
     * 审批通过后推送供应商数据到Erp
     *
     * @param changeInfo
     * @param orderStatus
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public FormResultDTO saveOrUpdateChange(ChangeInfoDTO changeInfo, String orderStatus) {
        FormResultDTO formResultDTO = new FormResultDTO();
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        InfoChange infoInfoChange = changeInfo.getInfoChange();
        String userType = "";
        if (loginAppUser != null) {
            userType = loginAppUser.getUserType();
            //Assert.notNull(loginAppUser, "获取人员信息失败");
            // 如果是供应商登录进行信息变更，设置companyId为自己的companyId, 并且状态变为供方已提交
            if (userType.equals(UserType.VENDOR.name())) {
                infoInfoChange.setCompanyId(loginAppUser.getCompanyId());
            }
        }

        infoInfoChange.setChangeStatus(orderStatus)
                .setChangeApplyDate(new Date());
        InfoChange infoChange = this.saveOrUpdateInfoChange(infoInfoChange);

        Long changeId = infoChange.getChangeId();

        // 供应商基础信息校验
        Long companyId = infoInfoChange.getCompanyId();

        // 采购商提交时校验 判断供应商是否存在供应商编码
        if (orderStatus.equals(InfoChangeStatus.SUBMITTED.getValue())) {

            Assert.notNull(companyId, "进行信息变更的供应商Id为空！");
            String vendorCode = iCompanyInfoService.getByCompanyId(companyId).getCompanyCode();
            Assert.notNull(vendorCode, "进行信息变更的供应商编码为空！");

            // 供应商地点信息校验 相同的业务实体和地点编码不能重复
            orgSiteDuplicateCheck(changeInfo.getSiteInfoChanges());

            // 供应商银行信息校验 有且仅有一个主账户，主账户必须启用
            bankInfoCheck(changeInfo.getBankInfoChanges());

            // 供应商联系人信息校验, 联系人姓名不能为空
            contactInfoCheck(changeInfo.getContactInfoChanges());

            //联系人、银行、地点至少变更一条信息（提交前校验）
            //Assert.isTrue(CollectionUtils.isNotEmpty(changeInfo.getContactInfoChanges()), "请至少变更一条联系人信息！");
            //Assert.isTrue(CollectionUtils.isNotEmpty(changeInfo.getBankInfoChanges()), "请至少变更一条银行信息！");
            //Assert.isTrue(CollectionUtils.isNotEmpty(changeInfo.getSiteInfoChanges()), "请至少变更一条地点信息！");
        }

     // 保存变更信息到变更表
        this.saveOrUpdateChanges(changeInfo, companyId, changeId, userType);
        //保存bussinessId在file upload表
        Long bussinessId =companyId;
        List<Long> fileuploadIds =changeInfo.getFileuploadChanges().stream().collect(Collectors.mapping(FileuploadChange::getFileuploadId,Collectors.toList()));
        if(fileuploadIds.size() != 0) {
            fileCenterClient.binding(fileuploadIds,bussinessId);
        }
        formResultDTO.setFormId(changeId);
        // 是否启动流程
        formResultDTO.setEnableWorkFlow(YesOrNo.YES.getValue());

        // 信息变更审批时才更新基础表
        if (InfoChangeStatus.APPROVED.getValue().equals(orderStatus)) {
            this.updateCompanyInfo(changeInfo);

        }

        //2020-12-24 隆基回迁 bugfix
        /* Begin by chenwt24@meicloud.com   2020-10-29 */
//        if (InfoChangeStatus.SUBMITTED.getValue().equals(orderStatus)) {
//            //审批流暂时关闭
//            String formId = null;
//            try {
//                formId = vendorInfoChangeFlow.submitVendorInfoChangeDTOFlow(changeInfo);
//            } catch (Exception e) {
//                throw new BaseException(e.getMessage());
//            }
//            if (StringUtils.isEmpty(formId)) {
//                throw new BaseException(LocaleHandler.getLocaleMsg("提交OA审批失败"));
//            }
//        }
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                if (InfoChangeStatus.SUBMITTED.getValue().equals(orderStatus)) {
                    //审批流暂时关闭
                    String formId = null;
                    try {
                        formId = vendorInfoChangeFlow.submitVendorInfoChangeDTOFlow(changeInfo);
                    } catch (Exception e) {
                        throw new BaseException(e.getMessage());
                    }
                    if (StringUtils.isEmpty(formId)) {
                        throw new BaseException(LocaleHandler.getLocaleMsg("提交OA审批失败"));
                    }
                }
            }
        });

        /* End by chenwt24@meicloud.com     2020-10-16 */
        return formResultDTO;
    }

    /**
     * Description 供应商变更审批通过，更新供应商相关信息
     * (包括：基本信息、银行信息、联系人信息、地点信息）
     *
     * @Param companyInfoChange 供应商变更
     * @Author wuwl18@meicloud.com
     * @Date 2020.09.20
     **/
    public void updateCompanyInfo(ChangeInfoDTO changeInfo) {
        CompanyInfoChange companyInfoChange = changeInfo.getCompanyInfoChange();
        Assert.notNull(companyInfoChange, LocaleHandler.getLocaleMsg("变更供应商信息不能为空"));
        Assert.notNull(companyInfoChange.getCompanyId(), LocaleHandler.getLocaleMsg("变更供应商ID不能为空"));

        Long companyId = companyInfoChange.getCompanyId();
        /**更新供应商基本信息*/
        if (null != companyInfoChange.getCompanyId()) {
            CompanyInfo companyInfo = new CompanyInfo();
            companyInfo.setOverseasRelation(companyInfoChange.getOverseasRelation());
            BeanUtils.copyProperties(companyInfoChange, companyInfo);
            String oldCompanyName = iCompanyInfoService.getById(companyId).getCompanyName(); //变更前的供应商名称
            String newCompanyName = companyInfoChange.getCompanyName(); //变更后的供应商名称

            iCompanyInfoService.updateById(companyInfo);
            /** 开始 更新回写供应商银行信息到银行基础表 **/
            /** 开始 删除旧的银行信息 **/
            List<BankInfo> oldBankInfos = iBankInfoService.list(new QueryWrapper<>(new BankInfo().setCompanyId(companyId)));
            List<BankInfoChange> bankInfoChangeList = changeInfo.getBankInfoChanges();

            //需要删除的银行信息主键Id List
            List<Long> deleteBankInfoIds = new ArrayList<>();

            for (BankInfo oldBankInfo : oldBankInfos) {
                boolean shouldDelete = true;
                for (BankInfoChange bankInfoChange : bankInfoChangeList) {
                    if (Objects.equals(bankInfoChange.getBankInfoId(), oldBankInfo.getBankInfoId())) {
                        shouldDelete = false;
                        break;
                    }
                }
                if (shouldDelete) {
                    deleteBankInfoIds.add(oldBankInfo.getBankInfoId());
                }
            }

            //批处理删除（旧的银行信息）
            if (CollectionUtils.isNotEmpty(deleteBankInfoIds)) {
                iBankInfoService.removeByIds(deleteBankInfoIds);
            }
            //保存或更新变更的银行信息
            List<BankInfo> updateBankInfoList = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(bankInfoChangeList)) {
                bankInfoChangeList.forEach(bankInfoChange -> {
                    if (null != bankInfoChange) {
                        BankInfo bankInfo = new BankInfo();
                        BeanUtils.copyProperties(bankInfoChange, bankInfo);
                        updateBankInfoList.add(bankInfo);
                    }
                });
                iBankInfoService.saveOrUpdateBatch(updateBankInfoList);
            }
            /** 结束 更新回写供应商银行信息 **/


            /** 开始 更新回写供应商地点信息到地点基础表 **/
            /** 开始 删除旧的地点信息 **/
            List<SiteInfo> oldSiteInfos = iSiteInfoService.list(new QueryWrapper<>(new SiteInfo().setCompanyId(companyId)));
            List<SiteInfoChange> siteInfoChangeList = changeInfo.getSiteInfoChanges();

            //需要删除的地点信息主键Id List
            List<Long> deleteSiteInfoIds = new ArrayList<>();
            for (SiteInfo oldSiteInfo : oldSiteInfos) {
                boolean shouldDelete = true;
                for (SiteInfoChange siteInfoChange : siteInfoChangeList) {
                    if (Objects.equals(siteInfoChange.getSiteInfoId(), oldSiteInfo.getSiteInfoId())) {
                        shouldDelete = false;
                        break;
                    }
                }
                if (shouldDelete) {
                    deleteSiteInfoIds.add(oldSiteInfo.getSiteInfoId());
                }
            }
            //批处理删除（旧的地点信息）
            if (CollectionUtils.isNotEmpty(deleteSiteInfoIds)) {
                iSiteInfoService.removeByIds(deleteSiteInfoIds);
            }

            /** 更新供应商地点信息 **/
            List<SiteInfo> siteInfoList = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(siteInfoChangeList)) {
                siteInfoChangeList.forEach(siteInfoChange -> {
                    SiteInfo siteInfo = new SiteInfo();
                    BeanUtils.copyProperties(siteInfoChange, siteInfo);
                    siteInfoList.add(siteInfo);
                });
                iSiteInfoService.saveOrUpdateBatch(siteInfoList);
            }
            /** 结束 更新回写供应商地点信息到地点基础表 **/


            /** 开始 更新回写供应商联系人信息到联系人基础表 **/
            /** 开始 删除旧的联系人信息 **/
            List<ContactInfo> oldContactInfos = iContactInfoService.list(new QueryWrapper<>(new ContactInfo().setCompanyId(companyId)));
            Map<Long, ContactInfo> contactInfoMap = oldContactInfos.stream().collect(Collectors.toMap(ContactInfo::getContactInfoId, Function.identity()));
            List<ContactInfoChange> contactInfoChangeList = changeInfo.getContactInfoChanges();

            //需要删除的联系人信息主键Id List
            List<Long> deleteContactInfoIds = new ArrayList<>();
            for (ContactInfo oldContactInfo : oldContactInfos) {
                boolean shouldDelete = true;
                for (ContactInfoChange contactInfoChange : contactInfoChangeList) {
                    if (Objects.equals(contactInfoChange.getContactInfoId(), oldContactInfo.getContactInfoId())) {
                        shouldDelete = false;
                        break;
                    }
                }
                if (shouldDelete) {
                    deleteContactInfoIds.add(oldContactInfo.getContactInfoId());
                }
            }
            if (CollectionUtils.isNotEmpty(deleteContactInfoIds)) {
                iContactInfoService.removeByIds(deleteContactInfoIds);
            }
            /** 结束 删除旧的联系人信息 **/

            List<ContactInfo> contactInfoList = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(contactInfoChangeList)) {
                contactInfoChangeList.stream().collect(
                        Collectors.collectingAndThen(
                                Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getContactInfoId()))), ArrayList::new)
                ).forEach(contactInfoChange -> {
                    ContactInfo contactInfo = new ContactInfo();
                    BeanUtils.copyProperties(contactInfoChange, contactInfo);
                    contactInfoList.add(contactInfo);
                });
                iContactInfoService.saveOrUpdateBatch(contactInfoList);
            }
            /** 结束 更新回写供应商联系人信息到联系人基础表 **/

            //插入附件 可能差个删除旧有fileupload表，mangement表
//            List<FileuploadChange> fileuploadChanges =iFileuploadChangeService.getByChangeId(changeInfo.getInfoChange().getChangeId());
            List<ManagementAttachChange> managementAttachChanges =managementAttachChangeService.getByChangeId(changeInfo.getInfoChange().getChangeId());
            List<ManagementAttach> managementAttaches=new ArrayList<>();
            for(ManagementAttachChange managementAttachChange : managementAttachChanges) {
                //复制属性
                ManagementAttach managementAttach = new ManagementAttach();
                copyPropertiesByHand(managementAttach, managementAttachChange, companyId);
//                BeanUtils.copyProperties(managementAttachChange,managementAttach);
                //获取管理体系信息
                ManagementInfo managementInfo =iManagementInfoService.getOne(new QueryWrapper<>(new ManagementInfo().setCompanyId(companyId)));
                if(managementInfo == null) {
                    Long id = IdGenrator.generate();
                    managementInfo = new ManagementInfo();
                    managementInfo.setCompanyId(companyId);
                    managementInfo.setManagementInfoId(id);
                    iManagementInfoService.save(managementInfo);
                }
                managementAttach.setManagementInfoId(managementInfo.getManagementInfoId());
                //添加
                managementAttaches.add(managementAttach);
            }
            //保存
            iManagementAttachService.saveBatch(managementAttaches);
            //刷新供应商相关信息的推送状态
            // refreshCompanyInfos(companyId);
            // 修改单据状态
            CompletableFuture.runAsync(() -> {
                try {
                    /** 推送供应商基础信息到erp 如果供应商名称改了再推送基础信息 **/
                    if (!oldCompanyName.equals(newCompanyName)) {
                        //推送供应商基础信息 同步推送，如果推送失败，直接抛出异常
                        iCompanyInfoService.sendVendorToErp(companyId);
                    }
                    Long erpVendorId = iCompanyInfoService.getById(companyId).getErpVendorId();
                    //同步供应商其他信息
                    CompletableFuture.runAsync(() -> {
                        try {
                            sendVendorOtherDatasToErp(companyId, erpVendorId);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                    /** 结束 推送供应商数据到erp **/
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

    }
    /**
     * 手动复制属性
     */
    private void copyPropertiesByHand(ManagementAttach managementAttach, ManagementAttachChange managementAttachChange, Long companyId) {
        managementAttach.setManagementAttachId(IdGenrator.generate());
        managementAttach.setCompanyId(companyId);
        managementAttach.setFileuploadId(managementAttachChange.getFileuploadId());
        if(managementAttachChange.getAuthType() != null) {
            managementAttach.setAuthType(managementAttachChange.getAuthType());
        }
        if(managementAttachChange.getAuthDescription() != null) {
            managementAttach.setAuthDescription(managementAttachChange.getAuthDescription());
        }
        if(managementAttachChange.getAuthNum() != null) {
            managementAttach.setAuthNum(managementAttachChange.getAuthNum());
        }
        if(managementAttachChange.getAuthDate() != null) {
            managementAttach.setAuthDate(managementAttachChange.getAuthDate());
        }
        if(managementAttachChange.getAuthOrg() != null) {
            managementAttach.setAuthOrg(managementAttachChange.getAuthOrg());
        }
        if(managementAttachChange.getStartDate() != null) {
            managementAttach.setStartDate(managementAttachChange.getStartDate());
        }
        if(managementAttachChange.getEndDate() != null) {
            managementAttach.setEndDate(managementAttachChange.getEndDate());
        }
    }
    /**
     * 保存信息到变更表
     * @param changeInfo
     * @param companyId
     * @param changeId
     * @param userType
     */
    @Transactional(rollbackFor = Exception.class)
    private void saveOrUpdateChanges(ChangeInfoDTO changeInfo, Long companyId, Long changeId, String userType) {
        //Boolean result = false;
        ChangeFlowResultDTO changeFlowResultDTO = new ChangeFlowResultDTO();
        if (changeId != null) {
            //供应商基本信息 companyInfoChange
            if (null != changeInfo.getCompanyInfoChange()) {
                iCompanyInfoChangeService.saveOrUpdateCompany(changeInfo.getCompanyInfoChange(), companyId, changeId);
            }

            //联系人信息 contactInfoChanges
            if (CollectionUtils.isNotEmpty(changeInfo.getContactInfoChanges())) {
                for (ContactInfoChange contactInfoChange : changeInfo.getContactInfoChanges()) {
                    iContactInfoChangeService.saveOrUpdateContact(contactInfoChange, companyId, changeId);
                }
            }
            if (changeInfo.getOtherInfoChange() != null) {
                iOtherInfoChangeService.saveOrUpdateOther(changeInfo.getOtherInfoChange(), companyId, changeId);
            }
            if (CollectionUtils.isNotEmpty(changeInfo.getOrgInfoChanges())) {
                for (OrgInfoChange orgInfoChange : changeInfo.getOrgInfoChanges()) {
                    iOrgInfoChangeService.saveOrUpdateOrg(orgInfoChange, companyId, changeId);
                }
            }

            //银行信息 bankInfoChanges
            if (CollectionUtils.isNotEmpty(changeInfo.getBankInfoChanges())) {
                for (BankInfoChange bankInfoChange : changeInfo.getBankInfoChanges()) {
                    iBankInfoChangeService.saveOrUpdateBank(bankInfoChange, companyId, changeId);
                }
            }

            //地点信息 siteInfoChanges
            if (!CollectionUtils.isEmpty(changeInfo.getSiteInfoChanges())) {
                for (SiteInfoChange siteInfoChange : changeInfo.getSiteInfoChanges()) {
                	//kuangzm 供应商地点增加删除操作 
                	if (null != siteInfoChange.getOpType() && !siteInfoChange.getOpType().isEmpty() && "Y".equals(siteInfoChange.getOpType())) {
                		iSiteInfoChangeService.removeById(siteInfoChange.getSiteChangeId());
                	} else {
                		iSiteInfoChangeService.saveOrUpdateSite(siteInfoChange, companyId, changeId);
                	}
                }
            }

            if (!CollectionUtils.isEmpty(changeInfo.getFinanceInfoChanges())) {
                for (FinanceInfoChange financeInfoChange : changeInfo.getFinanceInfoChanges()) {
                    iFinanceInfoChangeService.saveOrUpdateFinance(financeInfoChange, companyId, changeId);
                }
            }
            if (changeInfo.getBusinessInfoChange() != null) {
                iBusinessInfoChangeService.saveOrUpdateBusiness(changeInfo.getBusinessInfoChange(), companyId, changeId);
            }
            if (changeInfo.getHolderInfoChange() != null) {
                iHolderInfoChangeService.saveOrUpdateHolder(changeInfo.getHolderInfoChange(), companyId, changeId);
            }
            if (changeInfo.getOperationInfoChange() != null) {
                iOperationInfoChangeService.saveOrUpdateOp(changeInfo.getOperationInfoChange(), companyId, changeId);
            }
            if (changeInfo.getHonorInfoChange() != null) {
                iHonorInfoChangeService.saveOrUpdateHonor(changeInfo.getHonorInfoChange(), companyId, changeId);
            }
            if (!CollectionUtils.isEmpty(changeInfo.getOrgCategoryChanges())) {
                for (OrgCategoryChange orgCategoryChange : changeInfo.getOrgCategoryChanges()) {
                    iOrgCategoryChangeService.saveOrUpdateOrgCategory(orgCategoryChange, companyId, changeId);

                }
            }
            if (!CollectionUtils.isEmpty(changeInfo.getFileuploadChanges())) {
                iFileuploadChangeService.saveOrUpdateAttachs(changeInfo.getFileuploadChanges(), companyId, changeId);
            }
            if(!CollectionUtils.isEmpty(changeInfo.getManagementAttachChanges())) {
                managementAttachChangeService.saveOrUpdateAttachs(changeInfo.getManagementAttachChanges(), companyId, changeId);
            }
        }
    }


    private InfoChange saveOrUpdateInfoChange(InfoChange infoInfoChange) {
        InfoChange resultInfoChange = new InfoChange();
        if (infoInfoChange.getChangeId() != null) {
            resultInfoChange = this.updateInfoChange(infoInfoChange);
        } else {
            resultInfoChange = this.addInfoChange(infoInfoChange);
        }
        return resultInfoChange;
    }

    /**
     * 校验供应商地点
     * 相同的业务实体和地点不能重复
     *
     * @param siteInfoChanges
     * @modifiedBy xiexh12@meicloud.com 2020-10-07 20:33
     */
    public void orgSiteDuplicateCheck(List<SiteInfoChange> siteInfoChanges) {
        boolean allowSiteSave = false;
        Set<String> set = new HashSet<>();
        if (CollectionUtils.isNotEmpty(siteInfoChanges)) {
            for (SiteInfoChange siteInfoChange : siteInfoChanges) {
                String orgId = String.valueOf(siteInfoChange.getBelongOprId());
                String vendorSiteCode = siteInfoChange.getVendorSiteCode();
                String orgSite = orgId + "-" + vendorSiteCode;
                if (set.contains(orgSite)) {
                    StringBuffer sb = new StringBuffer();
                    sb.append("相同的业务实体下地点名称不能重复！业务实体:[").append(siteInfoChange.getOrgName()).append("], 地点名称:[").append(siteInfoChange.getVendorSiteCode()).append("]");
                    throw new BaseException(sb.toString());
                } else {
                    set.add(orgSite);
                }
            }
        }
    }

    /**
     * 供应商银行信息校验
     * 有且仅有一个主账户，主账户必须启用
     * @param bankInfoChanges
     */
    public void bankInfoCheck(List<BankInfoChange> bankInfoChanges) {

        if (CollectionUtils.isNotEmpty(bankInfoChanges)) {
            Map<String, List<BankInfoChange>> collect = bankInfoChanges.stream().collect(Collectors.groupingBy(BankInfoChange::getCeeaMainAccount));
            if (!collect.containsKey("Y")) {
                throw new BaseException(LocaleHandler.getLocaleMsg("银行信息中没有主账户, 请选择1条银行信息为主账户"));
            }
            if (collect.get("Y").size()>1) {
                throw new BaseException(LocaleHandler.getLocaleMsg("银行信息只能有1个主账户"));
            }
            if (Objects.equals(collect.get("Y").get(0).getCeeaEnabled(), "N")) {
                throw new BaseException(LocaleHandler.getLocaleMsg("银行信息主账户必须启用"));
            }
        }

    }


    /**
     * 供应商联系人信息校验
     * 姓名不能为空
     * @param contactInfoChanges
     */
    public void contactInfoCheck(List<ContactInfoChange> contactInfoChanges) {
        if (CollectionUtils.isNotEmpty(contactInfoChanges)) {
            for (ContactInfoChange contactInfoChange : contactInfoChanges) {
                if (StringUtils.isEmpty(contactInfoChange.getContactName())) {
                    throw new BaseException(LocaleHandler.getLocaleMsg("联系人姓名不能为空, 请维护联系人姓名后重试"));
                }
            }
        }
    }

    /**
     * 刷新供应商相关数据的推送erp状态
     * 银行账户信息、地点信息、联系人信息
     */
    public void refreshCompanyInfos(Long companyId) {
        if (Objects.nonNull(companyId)) {
            //银行账号信息
            List<BankInfo> bankInfos = iBankInfoService.list(
                    new QueryWrapper<>(new BankInfo().setCompanyId(companyId))
            );
            if (CollectionUtils.isNotEmpty(bankInfos)) {
                bankInfos.forEach(bankInfo -> {
                    bankInfo.setIfPushErp("N");
                });
                iBankInfoService.updateBatchById(bankInfos);
            }
            //地点信息
            List<SiteInfo> siteInfos = iSiteInfoService.list(
                    new QueryWrapper<>(new SiteInfo().setCompanyId(companyId))
            );
            if (CollectionUtils.isNotEmpty(siteInfos)) {
                siteInfos.forEach(siteInfo -> {
                    siteInfo.setIfPushErp("N");
                });
                iSiteInfoService.updateBatchById(siteInfos);
            }
            //联系人信息
            List<ContactInfo> contactInfos = iContactInfoService.list(
                    new QueryWrapper<>(new ContactInfo().setCompanyId(companyId))
            );
            if (CollectionUtils.isNotEmpty(contactInfos)) {
                contactInfos.forEach(contactInfo -> {
                    contactInfo.setIfPushErp("N");
                });
                iContactInfoService.updateBatchById(contactInfos);
            }
        }
    }

    @Override
    public void commonCheck(ChangeInfoDTO changeInfo, String orderStatus) {
        InfoChange existChange = new InfoChange();
        Assert.notNull(changeInfo, LocaleHandler.getLocaleMsg("变更单据不能为空"));
        Assert.notNull(changeInfo.getInfoChange(), LocaleHandler.getLocaleMsg("变更信息不能为空"));
        Assert.notNull(changeInfo.getCompanyInfoChange(), LocaleHandler.getLocaleMsg("请选择进行信息变更的供应商，或您选择的供应商存在正在进行变更的单据，请重新选择。"));
        Assert.notNull(changeInfo.getCompanyInfoChange().getCompanyId(), LocaleHandler.getLocaleMsg("请选择进行信息变更的供应商。"));
//        Assert.isTrue( StringUtils.isNotBlank(changeInfo.getInfoChange().getEnable4MChange()) ,
//                LocaleHandler.getLocaleMsg("是否是4M变更不能为空"));
        InfoChange infoInfoChange = changeInfo.getInfoChange();
        if (infoInfoChange.getChangeId() != null) {
            existChange = this.getById(infoInfoChange.getChangeId());
            Assert.notNull(existChange, LocaleHandler.getLocaleMsg("当前单据不存在"));
            if (InfoChangeStatus.APPROVED.getValue().equals(existChange.getChangeStatus())) {
                throw new BaseException(LocaleHandler.getLocaleMsg("已审批单据，不允许修改"));
            }
            switch (orderStatus) {
                case "DRAFT":
                    checkBeforesaveTemporary(existChange);
                    break;
                case "VENDOR_SUBMITTED":
                    checkBeforeVendorSubmitted(existChange, infoInfoChange);
                    break;
                case "SUBMITTED":
                    checkBeforeSubmitted(existChange, infoInfoChange);
                    break;
                case "REJECTED":
                    checkBeforeApprove(existChange);
                    break;
                case "APPROVED":
                    checkBeforeApprove(existChange);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public ChangeInfoDTO getInfoByChangeId(Long changeId) {
        ChangeInfoDTO changeInfoDto = new ChangeInfoDTO();
        InfoChange infoChange = this.getById(changeId);
        CompanyInfoChange companyInfoChange = iCompanyInfoChangeService.getByChangeId(changeId);
        List<ContactInfoChange> contactInfoChanges = iContactInfoChangeService.getByChangeId(changeId);
        OtherInfoChange otherInfoChange = iOtherInfoChangeService.getByChangeId(changeId);
        List<BankInfoChange> bankInfoChanges = iBankInfoChangeService.getByChangeId(changeId);
        List<SiteInfoChange> siteInfoChanges = iSiteInfoChangeService.getByChangeId(changeId);
        List<FinanceInfoChange> financeInfoChanges = iFinanceInfoChangeService.getByChangeId(changeId);
        List<OrgCategoryChange> orgCategoryChanges = iOrgCategoryChangeService.getByChangeId(changeId);
        List<OrgInfoChange> orgInfoChanges = iOrgInfoChangeService.getByChangeId(changeId);
        BusinessInfoChange businessInfoChange = iBusinessInfoChangeService.getByChangeId(changeId);
        HonorInfoChange honorInfoChange = iHonorInfoChangeService.getByChangeId(changeId);
        HolderInfoChange holderInfoChange = iHolderInfoChangeService.getByChangeId(changeId);
        OperationInfoChange operationInfoChange = iOperationInfoChangeService.getByChangeId(changeId);
        List<FileuploadChange> attachFileChanges = iFileuploadChangeService.getByChangeId(changeId);
        List<ManagementAttachChange> managementAttachChanges = managementAttachChangeService.getByChangeId(changeId);
        changeInfoDto.setInfoChange(infoChange);
        changeInfoDto.setCompanyInfoChange(companyInfoChange);
        changeInfoDto.setContactInfoChanges(contactInfoChanges);
        changeInfoDto.setOtherInfoChange(otherInfoChange);
        changeInfoDto.setBusinessInfoChange(businessInfoChange);
        changeInfoDto.setBankInfoChanges(bankInfoChanges);
        changeInfoDto.setSiteInfoChanges(siteInfoChanges);
        changeInfoDto.setFinanceInfoChanges(financeInfoChanges);
        changeInfoDto.setHolderInfoChange(holderInfoChange);
        changeInfoDto.setHonorInfoChange(honorInfoChange);
        changeInfoDto.setOperationInfoChange(operationInfoChange);
        changeInfoDto.setOrgCategoryChanges(orgCategoryChanges);
        changeInfoDto.setOrgInfoChanges(orgInfoChanges);
        changeInfoDto.setFileuploadChanges(attachFileChanges);
        changeInfoDto.setManagementAttachChanges(managementAttachChanges);
        return changeInfoDto;
    }

    @Override
    @Transactional
    public void deleteChangeInfo(Long changeId) {
        InfoChange infoChange = this.getById(changeId);
        Assert.notNull(infoChange, LocaleHandler.getLocaleMsg("当前单据不存在"));
        if (infoChange.getChangeStatus().equals(InfoChangeStatus.DRAFT.getValue())) {
            this.removeById(changeId);
            iCompanyInfoChangeService.removeByChangeId(changeId);
            iContactInfoChangeService.removeByChangeId(changeId);
            iOtherInfoChangeService.removeByChangeId(changeId);
            iBankInfoChangeService.removeByChangeId(changeId);
            iFinanceInfoChangeService.removeByChangeId(changeId);
            iOrgCategoryChangeService.removeByChangeId(changeId);
            iOrgInfoChangeService.removeByChangeId(changeId);
            iBusinessInfoChangeService.removeByChangeId(changeId);
            iHonorInfoChangeService.removeByChangeId(changeId);
            iHolderInfoChangeService.removeByChangeId(changeId);
            iOperationInfoChangeService.removeByChangeId(changeId);
            iDimFieldContextChangeService.deleteByChangeId(changeId);
            iFileuploadChangeService.deleteByChangeId(changeId);
            managementAttachChangeService.deleteByChangeId(changeId);

        } else {
            throw new BaseException(LocaleHandler.getLocaleMsg("当前单据不可删除"));
        }
    }

    /**
     * 废弃订单
     *
     * @param changeId
     */
    @Transactional
    @Override
    public void abandon(Long changeId) {
        ChangeInfoDTO changeInfoDTO = this.getInfoByChangeId(changeId);
        InfoChange infoChange = changeInfoDTO.getInfoChange();
        Assert.notNull(ObjectUtils.isEmpty(infoChange), "找不到需要废弃的订单");
        String changeStatus = infoChange.getChangeStatus();
        Assert.isTrue(InfoChangeStatus.WITHDRAW.getValue().equals(changeStatus) || InfoChangeStatus.REJECTED.getValue().equals(changeStatus), "只有已驳回，已撤回的订单才可以撤回。");
        infoChange.setChangeStatus(InfoChangeStatus.ABANDONED.getValue());
        this.updateById(infoChange);
        SrmFlowBusWorkflow srmworkflowForm = baseClient.getSrmFlowBusWorkflow(changeId);
        if (srmworkflowForm != null) {
            try {
                changeInfoDTO.setProcessType("N");
                vendorInfoChangeFlow.submitVendorInfoChangeDTOFlow(changeInfoDTO);
            } catch (Exception e) {
                Assert.isTrue(false, "废弃同步审批流失败。");
            }
        }
    }

    private void checkBeforeVendorSubmitted(InfoChange existChange, InfoChange infoInfoChange) {
        String approveStatus = existChange.getChangeStatus();
        if (!InfoChangeStatus.DRAFT.getValue().equals(approveStatus) &&
                !InfoChangeStatus.REJECTED.getValue().equals(approveStatus) &&
                !InfoChangeStatus.WITHDRAW.getValue().equals(approveStatus)) {
            throw new BaseException(LocaleHandler.getLocaleMsg("当前单据状态不允许修改"));
        }
        if (StringUtils.isBlank(infoInfoChange.getChangeExplain())) {
            throw new BaseException(LocaleHandler.getLocaleMsg("变更说明必填"));
        }
        InfoChange query = new InfoChange();
        query.setCompanyId(existChange.getCompanyId());
        query.setChangeStatus(InfoChangeStatus.VENDOR_SUBMITTED.getValue());
        List<InfoChange> companyInfoChanges = this.list(new QueryWrapper<InfoChange>(query));
        if (!CollectionUtils.isEmpty(companyInfoChanges)) {
            throw new BaseException(LocaleHandler.getLocaleMsg("当前供应商信息变更中，请确认!"));
        }
    }

    private void checkBeforeSubmitted(InfoChange existChange, InfoChange infoInfoChange) {
        String approveStatus = existChange.getChangeStatus();
        if (!InfoChangeStatus.DRAFT.getValue().equals(approveStatus) &&
                !InfoChangeStatus.REJECTED.getValue().equals(approveStatus) &&
                !InfoChangeStatus.WITHDRAW.getValue().equals(approveStatus) &&
                !InfoChangeStatus.VENDOR_SUBMITTED.getValue().equals(approveStatus)) {
            throw new BaseException(LocaleHandler.getLocaleMsg("当前单据状态不允许修改"));
        }
        if (StringUtils.isBlank(infoInfoChange.getChangeExplain())) {
            throw new BaseException(LocaleHandler.getLocaleMsg("变更说明必填"));
        }
        InfoChange query = new InfoChange();
        query.setCompanyId(existChange.getCompanyId());
        query.setChangeStatus(InfoChangeStatus.SUBMITTED.getValue());
        List<InfoChange> companyInfoChanges = this.list(new QueryWrapper<InfoChange>(query));
        if (!CollectionUtils.isEmpty(companyInfoChanges)) {
            throw new BaseException(LocaleHandler.getLocaleMsg("当前供应商信息变更中，请确认!"));
        }
    }

    private void checkBeforeApprove(InfoChange existChange) {
        String approveStatus = existChange.getChangeStatus();
        if (!InfoChangeStatus.SUBMITTED.getValue().equals(approveStatus)) {
            throw new BaseException(LocaleHandler.getLocaleMsg("当前单据状态不允许修改"));
        }
    }

    private void checkBeforesaveTemporary(InfoChange existChange) {
        String approveStatus = StringUtils.isNotBlank(existChange.getChangeStatus()) ? existChange.getChangeStatus() : "DRAFT";
        if (!InfoChangeStatus.DRAFT.getValue().equals(approveStatus) &&
                !InfoChangeStatus.REJECTED.getValue().equals(approveStatus)) {
            throw new BaseException(LocaleHandler.getLocaleMsg("当前单据状态不允许修改"));
        }
    }

    @Override
    @Transactional
    public void updateChange(ChangeInfoDTO changeInfo, String orderStatus) {
        LoginAppUser user = AppUserUtil.getLoginAppUser();
        if (InfoChangeStatus.APPROVED.getValue().equals(orderStatus)) {
            user = rbacClient.findByUsername(defaultUsername);
            Assert.notNull(user, LocaleHandler.getLocaleMsg("账号不能为空"));
        }

        InfoChange changeData = new InfoChange();
        changeData = changeInfo.getInfoChange();
        changeData.setChangeStatus(orderStatus);
        if (InfoChangeStatus.APPROVED.getValue().equals(orderStatus)) {
            changeData.setChangeApprovedDate(new Date());
            changeData.setChangeApprovedBy(user.getUsername());
            changeData.setChangeApprovedById(user.getUserId());
        }
        this.updateById(changeData);
        //批准之后进行信息变更
        if (InfoChangeStatus.APPROVED.getValue().equals(orderStatus)) {
            this.changeInfoData(changeInfo);

            iCompanyStatusLogService.saveStatusLog(changeData.getCompanyId(),
                    changeData.getChangeApprovedById(),
                    changeData.getChangeApprovedBy(),
                    user.getUserType(),
                    InfoChangeStatus.APPROVED.getValue(),
                    changeData.getChangeExplain(),
                    new Date(),
                    "供应商信息变更"
            );
        }
    }

    private void changeInfoData(ChangeInfoDTO changeInfo) {

        iCompanyInfoService.saveOrUpdateInfoChange(changeInfo);
    }

    /**
     * 主要是针对品类更改进行变更
     *
     * @param dto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> saveChangeWithFlow(ChangeInfoDTO dto) {
        saveOrUpdateChange(dto, SampleStatusType.SUBMITTED.getValue());
        InfoChange infoChange = dto.getInfoChange();
        String companyName = dto.getCompanyInfoChange().getCompanyName();
        Long changeId = infoChange.getChangeId();
        Long menuId = dto.getInfoChange().getMenuId();
        //Boolean enableWorkFlow = enableWorkFlow(menuId);
        //TODO
        Boolean enableWorkFlow = false;
        Map<String, Object> map = new HashMap();
        map.put("businessId", infoChange.getChangeId());
        map.put("subject", companyName);
        if (enableWorkFlow) {
            infoChange.setApproveStatus(SampleStatusType.SUBMITTED.getValue());
            infoChange.setChangeStatus(SampleStatusType.SUBMITTED.getValue());
            this.updateById(infoChange);
            if (StringUtil.isEmpty(infoChange.getCbpmInstaceId())) {
                CbpmRquestParamDTO request = buildCbpmRquest(infoChange, companyName);
                map = workFlowFeign.initProcess(request);
                redisTemplate.opsForValue().set("sup-info" + changeId, dto);
            } else {
                map.put("fdId", infoChange.getCbpmInstaceId());
            }
        } else {
            //不启用工作流，直接通过审批
            this.updateChange(dto, InfoChangeStatus.APPROVED.getValue());
        }

        return map;
    }

    private CbpmRquestParamDTO buildCbpmRquest(InfoChange infoChange, String companyName) {
        CbpmRquestParamDTO cbpmRquestParamDTO = new CbpmRquestParamDTO();
        cbpmRquestParamDTO.setBusinessId(String.valueOf(infoChange.getChangeId()));
        cbpmRquestParamDTO.setTemplateCode(CbpmFormTemplateIdEnum.CHANGE_SUP_INFO.getKey());
        cbpmRquestParamDTO.setSubject(companyName);
        cbpmRquestParamDTO.setFdId(infoChange.getCbpmInstaceId());
        return cbpmRquestParamDTO;
    }

    private Boolean enableWorkFlow(Long menuId) {
        Boolean flowEnable;
        Permission menu = rbacClient.getMenu(menuId);
        try {
            flowEnable = workFlowFeign.getFlowEnable(menuId, menu.getFunctionId(), CbpmFormTemplateIdEnum.CHANGE_SUP_INFO.getKey());
        } catch (FeignException e) {
            log.error("提交样品确认时报错,判断工作流是否启动时,参数 menuId：" + menuId + ",functionId" + menu.getFunctionId()
                    + ",templateCode" + CbpmFormTemplateIdEnum.CHANGE_SUP_INFO.getKey() + "报错：", e);
            throw new BaseException("提交物料试用时判断工作流是否启动时报错");
        }
        return flowEnable;
    }

    @Override
    @Transactional
    public void updateChangeWithFlow(ChangeInfoDTO changeInfo, String orderStatus) {
        InfoChange changeData = new InfoChange();
        changeData = changeInfo.getInfoChange();
        changeData.setChangeStatus(orderStatus);
        if (InfoChangeStatus.APPROVED.getValue().equals(orderStatus)) {

            changeData.setChangeApprovedDate(new Date());
            changeData.setChangeApprovedBy("工作流内部feign调用");
            changeData.setChangeApprovedById(-1L);
        }
        this.updateById(changeData);
        //批准之后进行信息变更
        if (InfoChangeStatus.APPROVED.getValue().equals(orderStatus)) {
            this.changeInfoData(changeInfo);

            iCompanyStatusLogService.saveStatusLog(changeData.getCompanyId(),
                    changeData.getChangeApprovedById(),
                    changeData.getChangeApprovedBy(),
                    "BUYER",
                    InfoChangeStatus.APPROVED.getValue(),
                    changeData.getChangeExplain(),
                    new Date(),
                    "共供应商信息变更"
            );
        }
    }

    /**
     * 查询供应商列表
     * 变更状态处于拟定状态或者已提交状态的不作为查询对象
     *
     * @return
     */
    @Override
    public List<CompanyInfo> getVendors() {
        /** 查询得到目前变更状态处于拟定和已提交的供应商 **/
        List<String> statusList = new ArrayList<>();
        statusList.add(InfoChangeStatus.DRAFT.getValue());
        statusList.add(InfoChangeStatus.SUBMITTED.getValue());
        List<InfoChange> infoChanges = this.list(
                new QueryWrapper<InfoChange>().in("CHANGE_STATUS", statusList)
        );
        List<Long> companyIds = new ArrayList<>();
        infoChanges.forEach(infoChange -> {
            Long companyId = infoChange.getCompanyId();
            companyIds.add(companyId);
        });

        /** 查询得到所需要的供应商列表 **/
        List<CompanyInfo> companyInfos = iCompanyInfoService.list(new QueryWrapper<>(new CompanyInfo().setStatus(InfoChangeStatus.APPROVED.getValue())));
        List<CompanyInfo> returnCompanys = new ArrayList<>();
        for (CompanyInfo companyInfo : companyInfos) {
            if (!companyIds.contains(companyInfo.getCompanyId()))
                returnCompanys.add(companyInfo);
        }
        return returnCompanys;
    }

    /**
     * 删除供应商信息变更单据
     *
     * @param id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeChangeById(Long id) {

        InfoChange existInfoChange = this.getById(id);
        String changeStatus = existInfoChange.getChangeStatus();
        Assert.isTrue(!changeStatus.equals(InfoChangeStatus.APPROVED.getValue()), "已审批状态不能删除！");

        /** 删除银行信息变更 **/
        QueryWrapper<BankInfoChange> bankInfoChangeQueryWrapper = new QueryWrapper<>(
                new BankInfoChange().setChangeId(id)
        );
        iBankInfoChangeService.remove(bankInfoChangeQueryWrapper);
        /** 删除银行信息信息变更 **/

        /** 删除地点信息变更 **/
        QueryWrapper<SiteInfoChange> siteInfoChangeQueryWrapper = new QueryWrapper<>(
                new SiteInfoChange().setChangeId(id)
        );
        iSiteInfoChangeService.remove(siteInfoChangeQueryWrapper);
        /** 删除地点信息信息变更 **/

        /** 删除联系人信息变更 **/
        QueryWrapper<ContactInfoChange> contactInfoChangeQueryWrapper = new QueryWrapper<>(
                new ContactInfoChange().setChangeId(id)
        );
        iContactInfoChangeService.remove(contactInfoChangeQueryWrapper);
        /** 删除联系人信息信息变更 **/

        /** 最后删除变更头 **/
        this.removeById(id);
        /** 最后删除变更头 **/
    }

    /**
     * 根据供应商id判断是否能够进行信息变更
     * 有拟定 已提交 撤回 驳回的信息变更单不能进行信息变更
     * @param companyId
     * @return
     */
    @Override
    public InfoChangeDTO ifAddInfoChange(Long companyId) {
        String notChangeList[] = {"DRAFT", "SUBMITTED", "WITHDRAW", "REJECTED"};

        InfoChangeDTO dto = new InfoChangeDTO();
        dto.setChangeStatus("Y");
        if (Objects.isNull(iCompanyInfoService.getById(companyId))) {
            throw new BaseException(LocaleHandler.getLocaleMsg("找不到对应的供应商,srm供应商id:" + companyId));
        }
        CompanyInfo companyInfo = iCompanyInfoService.getById(companyId);
        // 黑名单不能进行信息变更
        if ("Y".equals(companyInfo.getIsBacklist())) {
            dto.setChangeStatus("N");
        }
        // 有拟定 已提交 撤回 驳回的信息, 则变更单不能进行信息变更
        List<InfoChange> infoChangeList = this.list(Wrappers.lambdaQuery(InfoChange.class)
                .eq(InfoChange::getCompanyId, companyId)
                .in(InfoChange::getChangeStatus, notChangeList));
        if (CollectionUtils.isNotEmpty(infoChangeList)){
            dto.setChangeStatus("N");
        }
        return dto;
    }

    /**
     * 采购商驳回供方已提交的单据
     * @param changeId
     */
    @Override
    public void buyerReject(Long changeId) {
        InfoChange infoChange = this.getById(changeId);
        Optional.ofNullable(infoChange.getChangeStatus()).orElseThrow(() -> new BaseException(LocaleHandler.getLocaleMsg("当前单据无变更状态, 不能进行驳回.")));
        String changeStatus = infoChange.getChangeStatus();
        Assert.isTrue(InfoChangeStatus.VENDOR_SUBMITTED.getValue().equals(changeStatus), "当前变更状态不是供方已提交状态, 不能驳回..");
        infoChange.setChangeStatus(InfoChangeStatus.DRAFT.getValue());
        this.updateById(infoChange);
    }

    /**
     * 推送erp主信息外信息
     * @param companyId
     * @param erpVendorId
     */
    public void sendVendorOtherDatasToErp(Long companyId, Long erpVendorId){
        /** 开始 推送供应商银行、地点、联系人数据到erp **/
            //推送供应商银行信息
            submitExector.execute(() -> {
                iCompanyInfoService.sendVendorBank(companyId, erpVendorId);
            });
            //推送供应商地点信息
            submitExector.execute(() -> {
                iCompanyInfoService.sendVendorSite(companyId, erpVendorId);
            });
            //推送供应商联系人信息
            submitExector.execute(() -> {
                iCompanyInfoService.sendVendorContact(companyId, erpVendorId);
            });
    }
}
