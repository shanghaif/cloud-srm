package com.midea.cloud.srm.supauth.orgcategory.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.constants.SequenceCodeConstant;
import com.midea.cloud.common.enums.*;
import com.midea.cloud.common.enums.flow.CbpmFormTemplateIdEnum;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.JsonUtil;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.common.utils.StringUtil;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.feign.flow.WorkFlowFeign;
import com.midea.cloud.srm.feign.rbac.RbacClient;
import com.midea.cloud.srm.feign.supplier.SupplierClient;
import com.midea.cloud.srm.model.common.FormResultDTO;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.flow.process.dto.CbpmRquestParamDTO;
import com.midea.cloud.srm.model.rbac.permission.entity.Permission;
import com.midea.cloud.srm.model.supplier.info.dto.OrgCateServiceStatusDTO;
import com.midea.cloud.srm.model.supplier.info.entity.OrgCategory;
import com.midea.cloud.srm.model.supplier.info.entity.OrgInfo;
import com.midea.cloud.srm.model.supplierauth.orgcategory.dto.OrgCatFormDTO;
import com.midea.cloud.srm.model.supplierauth.orgcategory.entity.OrgCatForm;
import com.midea.cloud.srm.model.supplierauth.quasample.entity.QuaSample;
import com.midea.cloud.srm.model.supplierauth.review.entity.OrgCateJournal;
import com.midea.cloud.srm.model.workflow.service.IFlowBusinessCallbackService;
import com.midea.cloud.srm.supauth.orgcategory.mapper.OrgCatFormMapper;
import com.midea.cloud.srm.supauth.orgcategory.service.IOrgCatFormService;
import com.midea.cloud.srm.supauth.review.service.IOrgCateJournalService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.*;

/**
 * <pre>
 *  组织品类控制单据(合作终止) 服务实现类
 * </pre>
 *
 * @author chensl26@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-24 15:04:22
 *  修改内容:
 * </pre>
 */
@Service
public class OrgCatFormServiceImpl extends ServiceImpl<OrgCatFormMapper, OrgCatForm> implements IOrgCatFormService, IFlowBusinessCallbackService {

    @Autowired
    OrgCatFormMapper orgCatFormMapper;

    @Autowired
    BaseClient baseClient;

    @Autowired
    IOrgCateJournalService iOrgCateJournalService;

    @Autowired
    SupplierClient supplierClient;

    @Autowired
    FileCenterClient fileCenterClient;

    @Autowired
    RbacClient rbacClient;

    @Autowired
    private WorkFlowFeign workFlowFeign;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    @Transactional
    public FormResultDTO saveTemporary(OrgCatFormDTO orgCatFormDTO, String approveStatusType) {
        OrgCatForm orgCatForm = orgCatFormDTO.getOrgCatForm();
        Assert.notNull(orgCatForm, "合作终止控制单据为空");
        FormResultDTO formResultDTO = saveOrUpdateOrgCatFormDTO(orgCatFormDTO, orgCatForm, approveStatusType);
        return formResultDTO;
    }

    @Override
    @Transactional
    public FormResultDTO submitted(OrgCatFormDTO orgCatFormDTO, String approveStatusType) {
        OrgCatForm orgCatForm = orgCatFormDTO.getOrgCatForm();
        Assert.notNull(orgCatForm, "合作终止控制单据为空");
        checkAllBeforeSaveOrUpdate(orgCatFormDTO, orgCatForm);
        FormResultDTO formResultDTO = saveOrUpdateOrgCatFormDTO(orgCatFormDTO, orgCatForm, approveStatusType);
        return formResultDTO;
    }

    @Override
    public PageInfo<OrgCatForm> listPageByParm(OrgCatForm orgCatForm) {
        PageUtil.startPage(orgCatForm.getPageNum(), orgCatForm.getPageSize());
        OrgCatForm form = new OrgCatForm();
        if (StringUtils.isNotBlank(orgCatForm.getApproveStatus())) {
            form.setApproveStatus(orgCatForm.getApproveStatus());
        }
        if (StringUtils.isNotBlank(orgCatForm.getSupplierControlType())) {
            form.setSupplierControlType(orgCatForm.getSupplierControlType());
        }
        QueryWrapper<OrgCatForm> wrapper = new QueryWrapper<OrgCatForm>(form);
        if (StringUtils.isNoneBlank(orgCatForm.getOrgCatFormNumber())) {
            wrapper.like("ORG_CAT_FORM_NUMBER", orgCatForm.getOrgCatFormNumber());
        }
        if (StringUtils.isNoneBlank(orgCatForm.getVendorName())) {
            wrapper.like("VENDOR_NAME", orgCatForm.getVendorName());
        }
        wrapper.orderByDesc("LAST_UPDATE_DATE");
        List<OrgCatForm> orgCatForms = orgCatFormMapper.selectList(wrapper);
        return new PageInfo<>(orgCatForms);
    }

    @Override
    public PageInfo listOrgCateServiceStatusPageByParm(String supplierControlType, Long companyId) {
        Assert.hasText(supplierControlType, "控制类型为空");
        Assert.notNull(companyId, "供应商为空");
        List list = new ArrayList<>();
        //控制类型属于组织
//        if (supplierControlType.startsWith(OrgCatType.ORG.toString())) {
//            //如果控制类型为组织冻结,则筛选服务状态为有效
//            if (SupplierControlType.ORG_FORZEN.toString().equals(supplierControlType)) {
//                list = supplierClient.listOrgInfoByServiceStatusAndCompanyId(companyId, OrgStatus.EFFECTIVE.toString());
//
//                //如果控制类型为组织解冻,则筛选服务状态为冻结
//            } else if (SupplierControlType.ORG_UNFORZEN.toString().equals(supplierControlType)) {
//                list = supplierClient.listOrgInfoByServiceStatusAndCompanyId(companyId, OrgStatus.FROZEN.toString());
//
//                //如果控制类型为组织失效,则筛选服务状态为有效和冻结
//            } else if (SupplierControlType.ORG_INVALID.toString().equals(supplierControlType)) {
//                list = supplierClient.listOrgInfoByServiceStatusAndCompanyId(companyId, OrgStatus.EFFECTIVE.toString(), OrgStatus.FROZEN.toString());
//            }
//        }
//        //控制类型属于品类
//        if (supplierControlType.startsWith(OrgCatType.CAT.toString())) {
//            //如果控制类型为品类暂停,则筛选服务状态为合格
//            if (CategoryStatus.CAT_SUSPEND.toString().equals(supplierControlType)) {
//                list = supplierClient.listOrgCategoryByServiceStatusAndCompanyId(companyId, CategoryStatus.QUALIFIED.toString());
//
//                //如果控制类型为品类恢复,则筛选服务状态为暂停
//            } else if (SupplierControlType.CAT_RENEW.toString().equals(supplierControlType)) {
//                list = supplierClient.listOrgCategoryByServiceStatusAndCompanyId(companyId, CategoryStatus.SUSPEND.toString());
//
//                //如果控制类型为品类终止,则筛选服务状态为合格和暂停
//            } else if (SupplierControlType.CAT_INVALID.toString().equals(supplierControlType)) {
//                list = supplierClient.listOrgCategoryByServiceStatusAndCompanyId(companyId, CategoryStatus.QUALIFIED.toString(), CategoryStatus.SUSPEND.toString());
//            }
//        }
        
        if (com.midea.cloud.common.enums.review.CategoryStatus.YELLOW.name().equals(supplierControlType)) {
            list = supplierClient.listOrgCategoryByServiceStatusAndCompanyId(companyId, com.midea.cloud.common.enums.review.CategoryStatus.GREEN.name(),com.midea.cloud.common.enums.review.CategoryStatus.ONE_TIME.name());

            //如果控制类型为品类恢复,则筛选服务状态为暂停
        } else if (com.midea.cloud.common.enums.review.CategoryStatus.RED.name().equals(supplierControlType)) {
            list = supplierClient.listOrgCategoryByServiceStatusAndCompanyId(companyId, com.midea.cloud.common.enums.review.CategoryStatus.GREEN.name(),com.midea.cloud.common.enums.review.CategoryStatus.ONE_TIME.name(),com.midea.cloud.common.enums.review.CategoryStatus.YELLOW.name());

            //如果控制类型为品类终止,则筛选服务状态为合格和暂停
        } else if (com.midea.cloud.common.enums.review.CategoryStatus.BLACK.name().equals(supplierControlType)) {
            list = supplierClient.listOrgCategoryByServiceStatusAndCompanyId(companyId, CategoryStatus.QUALIFIED.toString(), com.midea.cloud.common.enums.review.CategoryStatus.GREEN.name(),com.midea.cloud.common.enums.review.CategoryStatus.ONE_TIME.name(),com.midea.cloud.common.enums.review.CategoryStatus.YELLOW.name(),com.midea.cloud.common.enums.review.CategoryStatus.RED.name());
        }
        return new PageInfo<>(list);
    }

    @Override
    public OrgCatFormDTO getOrgCatFormDTO(Long orgCatFormId) {
        OrgCatFormDTO orgCatFormDTO = new OrgCatFormDTO();
        OrgCatForm orgCatForm = orgCatFormMapper.selectById(orgCatFormId);
        List<OrgCateJournal> orgCateJournals = iOrgCateJournalService.list(new QueryWrapper<>(
                new OrgCateJournal().setOrgCateBillType(OrgCateBillType.ORG_CAT_FORM.getValue())
                        .setOrgCateBillId(orgCatFormId)));
        orgCatFormDTO.setOrgCatForm(orgCatForm);
        orgCatFormDTO.setOrgCateJournals(orgCateJournals);
        return orgCatFormDTO;
    }

    @Override
    @Transactional
    public void deleteOrgCatFormById(Long orgCatFormId) {
        OrgCatForm orgCatForm = this.getById(orgCatFormId);
        if (orgCatForm != null) {
            if (ApproveStatusType.DRAFT.getValue().equals(orgCatForm.getApproveStatus())
                    || ApproveStatusType.REJECTED.getValue().equals(orgCatForm.getApproveStatus())) {
                this.removeById(orgCatFormId);
                iOrgCateJournalService.remove(new QueryWrapper<>(new OrgCateJournal().setOrgCateBillId(orgCatFormId)
                        .setOrgCateBillType(OrgCateBillType.ORG_CAT_FORM.getValue())));
                fileCenterClient.deleteByParam(new Fileupload().setBusinessId(orgCatFormId));
            } else {
                throw new BaseException("合作终止单据状态不是拟定或驳回,不允许删除");
            }
        }
    }

    private void checkAllBeforeSaveOrUpdate(OrgCatFormDTO orgCatFormDTO, OrgCatForm orgCatForm) {
        if (orgCatForm.getOrgCatFormId() != null) {
            OrgCatForm selectById = this.getById(orgCatForm.getOrgCatFormId());
            if (selectById != null && (ApproveStatusType.APPROVED.getValue().equals(selectById.getApproveStatus())
                    || ApproveStatusType.SUBMITTED.getValue().equals(selectById.getApproveStatus()) || ApproveStatusType.ABANDONED.getValue().equals(selectById.getApproveStatus()))) {
                throw new BaseException("合作终止单据不允许再修改");
            }
        }
        Assert.notNull(orgCatForm.getVendorId(), "供应商为空");
        Assert.hasText(orgCatForm.getSupplierControlType(), "控制类型为空");
    }

    private FormResultDTO saveOrUpdateOrgCatFormDTO(OrgCatFormDTO orgCatFormDTO, OrgCatForm orgCatForm, String approveStatusType) {
        if (orgCatForm.getOrgCatFormId() == null) {
            saveOrgCatForm(orgCatForm, approveStatusType);
        } else {
            updateOrgCatForm(orgCatForm, approveStatusType);
        }
        List<OrgCateJournal> orgCateJournals = orgCatFormDTO.getOrgCateJournals();
        batchSaveOrUpdateOrgCateJournal(orgCateJournals, orgCatForm);
        //绑定附件
        if (!CollectionUtils.isEmpty(orgCatForm.getFileUploads())) {
            fileCenterClient.bindingFileupload(orgCatForm.getFileUploads(), orgCatForm.getOrgCatFormId());
        }

        //判断是否启用工作流.
        FormResultDTO formResultDTO = new FormResultDTO();
        formResultDTO.setFormId(orgCatForm.getOrgCatFormId());
        UpdateWrapper<OrgCatForm> updateWrapper = new UpdateWrapper<>(
                new OrgCatForm().setOrgCatFormId(orgCatForm.getOrgCatFormId()));
        //拟定状态不进入工作流
        if (!ApproveStatusType.DRAFT.getValue().equals(orgCatForm.getApproveStatus())) {
            Long menuId = orgCatFormDTO.getMenuId();
            Permission menu = null;
            if (menuId != null) {
                menu = rbacClient.getMenu(menuId);
            }
            if (menu != null) {
                //1.启用:进入工作流,
                formResultDTO.setEnableWorkFlow(menu.getEnableWorkFlow());
                formResultDTO.setFunctionId(menu.getFunctionId());
                if (YesOrNo.YES.getValue().equals(menu.getEnableWorkFlow())) {
                    updateApproveStatus(updateWrapper, ApproveStatusType.DRAFT.getValue());
                    return formResultDTO;
                }
            }
            //2.禁用:更新供应商档案主数据
            updateVendorMainData(orgCateJournals, orgCatForm);
            //改变状态为已审批
            updateApproveStatus(updateWrapper, ApproveStatusType.APPROVED.getValue());
        }
        return formResultDTO;
    }

    private void updateVendorMainData(List<OrgCateJournal> orgCateJournals, OrgCatForm orgCatForm) {
        String supplierControlType = orgCatForm.getSupplierControlType();
        if (StringUtils.isNotBlank(supplierControlType)) {
//            //改变品类状态
//            if (SupplierControlType.CAT_INVALID.toString().equals(supplierControlType)) {
//                updateOrgCategoryData(orgCateJournals, CategoryStatus.TERMINATION.toString());
//            } else if (SupplierControlType.CAT_RENEW.toString().equals(supplierControlType)) {
//                updateOrgCategoryData(orgCateJournals, CategoryStatus.QUALIFIED.toString());
//            } else if (SupplierControlType.CAT_SUSPEND.toString().equals(supplierControlType)) {
//                updateOrgCategoryData(orgCateJournals, CategoryStatus.SUSPEND.toString());
//            }
//            //改变组织状态
//            if (SupplierControlType.ORG_INVALID.toString().equals(supplierControlType)) {
//                updateOrgInfoData(orgCateJournals, OrgStatus.INVALID.toString());
//            } else if (SupplierControlType.ORG_FORZEN.toString().equals(supplierControlType)) {
//                updateOrgInfoData(orgCateJournals, OrgStatus.FROZEN.toString());
//            } else if (SupplierControlType.ORG_UNFORZEN.toString().equals(supplierControlType)) {
//                updateOrgInfoData(orgCateJournals, OrgStatus.EFFECTIVE.toString());
//            }
        	
        	if (com.midea.cloud.common.enums.review.CategoryStatus.YELLOW.name().equals(supplierControlType)) {
        		updateOrgCategoryData(orgCateJournals, com.midea.cloud.common.enums.review.CategoryStatus.YELLOW.name());
            } else if (com.midea.cloud.common.enums.review.CategoryStatus.RED.name().equals(supplierControlType)) {
            	updateOrgCategoryData(orgCateJournals, com.midea.cloud.common.enums.review.CategoryStatus.RED.name());
            } else if (com.midea.cloud.common.enums.review.CategoryStatus.BLACK.name().equals(supplierControlType)) {
            	updateOrgCategoryData(orgCateJournals, com.midea.cloud.common.enums.review.CategoryStatus.BLACK.name());
            }
        }
    }

    private void updateOrgInfoData(List<OrgCateJournal> orgCateJournals, String orgStatus) {
        if (!CollectionUtils.isEmpty(orgCateJournals)) {
            for (OrgCateJournal orgCateJournal : orgCateJournals) {
                OrgInfo orgInfo = new OrgInfo();
                orgInfo.setServiceStatus(orgStatus)
                        .setOrgId(orgCateJournal.getOrgId())
                        .setCompanyId(orgCateJournal.getVendorId());
                if (OrgStatus.INVALID.toString().equals(orgStatus)) {
                    orgInfo.setEndDate(LocalDate.now());
                }
                supplierClient.updateOrgInfoServiceStatus(orgInfo);
            }
        }
    }

    private void updateOrgCategoryData(List<OrgCateJournal> orgCateJournals, String categoryStatus) {
        if (!CollectionUtils.isEmpty(orgCateJournals)) {
            for (OrgCateJournal orgCateJournal : orgCateJournals) {
                OrgCategory orgCategory = new OrgCategory();
                orgCategory.setServiceStatus(categoryStatus)
                        .setCategoryId(orgCateJournal.getCategoryId())
                        .setOrgId(orgCateJournal.getOrgId())
                        .setCompanyId(orgCateJournal.getVendorId());
                if (CategoryStatus.TERMINATION.toString().equals(categoryStatus)) {
                    orgCategory.setEndDate(LocalDate.now());
                }
                supplierClient.updateOrgCategoryServiceStatus(orgCategory);
            }
        }
    }

    private void updateApproveStatus(UpdateWrapper<OrgCatForm> updateWrapper, String approveStatusType) {
        updateWrapper.set("APPROVE_STATUS", approveStatusType);
        this.update(updateWrapper);
    }

    private void batchSaveOrUpdateOrgCateJournal(List<OrgCateJournal> orgCateJournals, OrgCatForm orgCatForm) {
    	//先删除
    	iOrgCateJournalService.remove(new QueryWrapper<>(new OrgCateJournal().setOrgCateBillId(orgCatForm.getOrgCatFormId())
                .setOrgCateBillType(OrgCateBillType.ORG_CAT_FORM.getValue())));
    	
    	if (!CollectionUtils.isEmpty(orgCateJournals)) {
            for (OrgCateJournal orgCateJournal : orgCateJournals) {
                if (orgCateJournal == null) continue;
                if (orgCateJournal.getOrgCateJournalId() == null) {
                    saveOrgCateJournal(orgCatForm, orgCateJournal);
                } else {
                    updateOrgCateJournal(orgCateJournal);
                }
            }
        }
    }

    private void updateOrgCateJournal(OrgCateJournal orgCateJournal) {
        iOrgCateJournalService.updateById(orgCateJournal);
    }

    private void saveOrgCateJournal(OrgCatForm orgCatForm, OrgCateJournal orgCateJournal) {
        long id = IdGenrator.generate();
        orgCateJournal.setOrgCateJournalId(id)
                .setOrgCateBillType(OrgCateBillType.ORG_CAT_FORM.getValue())
                .setOrgCateBillId(orgCatForm.getOrgCatFormId())
                .setVendorId(orgCatForm.getVendorId());
        iOrgCateJournalService.save(orgCateJournal);
    }

    private void updateOrgCatForm(OrgCatForm orgCatForm, String approveStatusType) {
        orgCatForm.setApproveStatus(approveStatusType)
                .setLastUpdateDate(new Date());
        orgCatFormMapper.updateById(orgCatForm);
    }

    private void saveOrgCatForm(OrgCatForm orgCatForm, String approveStatusType) {
        long id = IdGenrator.generate();
        orgCatForm.setOrgCatFormId(id)
                .setOrgCatFormNumber(baseClient.seqGen(SequenceCodeConstant.SEQ_ORG_CATE_FORM_NUM_CODE))
                .setApproveStatus(approveStatusType)
                .setCreationDate(new Date())
                .setLastUpdateDate(new Date());
        orgCatFormMapper.insert(orgCatForm);
    }

    /**
     * 由于只能增量写代码，造成冗余，请见谅
     *
     * @param orgCatFormDTO
     * @return
     */


    @Override
    public Map<String, Object> submitWithFlow(OrgCatFormDTO orgCatFormDTO,String value) {
        OrgCatForm orgCatForm = orgCatFormDTO.getOrgCatForm();
        Assert.notNull(orgCatForm, "合作终止控制单据为空");
        checkAllBeforeSaveOrUpdate(orgCatFormDTO, orgCatForm);
        return saveOrUpdateOrgCatFormDTOWithFlow(orgCatFormDTO, orgCatForm, value);
    }

    private Map<String, Object> saveOrUpdateOrgCatFormDTOWithFlow(OrgCatFormDTO orgCatFormDTO, OrgCatForm orgCatForm, String approveStatusType) {
        if (orgCatForm.getOrgCatFormId() == null) {
            saveOrgCatForm(orgCatForm, approveStatusType);
        } else {
            updateOrgCatForm(orgCatForm, approveStatusType);
        }
        List<OrgCateJournal> orgCateJournals = orgCatFormDTO.getOrgCateJournals();
        batchSaveOrUpdateOrgCateJournal(orgCateJournals, orgCatForm);
        //绑定附件
        if (!CollectionUtils.isEmpty(orgCatForm.getFileUploads())) {
            fileCenterClient.bindingFileupload(orgCatForm.getFileUploads(), orgCatForm.getOrgCatFormId());
        }
        Map<String, Object> map = new HashMap();
        //判断是否启用工作流.
        map.put("businessId", orgCatForm.getOrgCatFormId());
        map.put("fdId", orgCatForm.getCbpmInstaceId());
        map.put("subject", orgCatForm.getVendorName());
        UpdateWrapper<OrgCatForm> updateWrapper = new UpdateWrapper<>(
                new OrgCatForm().setOrgCatFormId(orgCatForm.getOrgCatFormId()));
        //拟定状态不进入工作流
        if (!ApproveStatusType.DRAFT.getValue().equals(orgCatForm.getApproveStatus())) {
            Long menuId = orgCatFormDTO.getMenuId();
            Permission menu = null;
            if (menuId != null) {
                menu = rbacClient.getMenu(menuId);
            }
            boolean flowEnable = workFlowFeign.getFlowEnable(menuId, menu.getFunctionId(), CbpmFormTemplateIdEnum.END_COOPERATE.getKey());
            if (flowEnable) {
                //变为拟定，如果提交以后，会变为已提交
                updateApproveStatus(updateWrapper, ApproveStatusType.DRAFT.getValue());
                //初始化工作流
                if(StringUtil.isEmpty(orgCatForm.getCbpmInstaceId())){
                    CbpmRquestParamDTO cbpmRquestParamDTO = new CbpmRquestParamDTO();
                    cbpmRquestParamDTO.setBusinessId(String.valueOf(orgCatForm.getOrgCatFormId()));
                    cbpmRquestParamDTO.setTemplateCode(CbpmFormTemplateIdEnum.END_COOPERATE.getKey());
                    cbpmRquestParamDTO.setSubject(orgCatForm.getVendorName());
                    cbpmRquestParamDTO.setFdId(orgCatForm.getCbpmInstaceId());
                    map = workFlowFeign.initProcess(cbpmRquestParamDTO);
                    String key="orgCat"+orgCatForm.getOrgCatFormId();
                    orgCatFormDTO.setOrgCatForm(orgCatForm);
                    redisTemplate.opsForValue().set(key,orgCatFormDTO);
                }

                return map;

            }
            //2.禁用:更新供应商档案主数据
            updateVendorMainData(orgCateJournals, orgCatForm);
            //改变状态为已审批
            updateApproveStatus(updateWrapper, ApproveStatusType.APPROVED.getValue());
        }
        return map;
    }
    @Override
    public void updateVendorMainDataAfterFLow(List<OrgCateJournal> orgCateJournals, OrgCatForm orgCatForm) {
        updateVendorMainData(orgCateJournals, orgCatForm);
        UpdateWrapper<OrgCatForm> updateWrapper = new UpdateWrapper<>(
                new OrgCatForm().setOrgCatFormId(orgCatForm.getOrgCatFormId()));
        updateApproveStatus(updateWrapper, ApproveStatusType.APPROVED.getValue());
    }
    //合同终止-工作流改造
    @Transactional
    public void pass(OrgCatForm orgCatForm) {
        OrgCatForm byId = this.getById(orgCatForm.getOrgCatFormId());
        log.error("cooperationEnded-pass:"+byId);
        if (byId != null) {
            byId.setApproveStatus(ApproveStatusType.APPROVED.getValue());
            this.updateById(new OrgCatForm().setOrgCatFormId(orgCatForm.getOrgCatFormId()).setApproveStatus(ApproveStatusType.APPROVED.getValue()));
        }
    }
    @Override
    public void submitFlow(Long businessId, String param) throws Exception {
        log.error("cooperationEnded-submitFlow:"+SampleStatusType.PUBLISHED.getValue());
        OrgCatForm orgCatForm = new OrgCatForm();
        orgCatForm.setOrgCatFormId(businessId);
        this.updateById(orgCatForm.setApproveStatus(SampleStatusType.PUBLISHED.getValue()));
    }

    @Override
    public void passFlow(Long businessId, String param) throws Exception {
        log.error("cooperationEnded-pass");
        OrgCatForm orgCatForm = new OrgCatForm();
        orgCatForm.setOrgCatFormId(businessId);
        this.pass(orgCatForm);
    }

    @Override
    public void rejectFlow(Long businessId, String param) throws Exception {
        log.error("cooperationEnded-rejectFlow:"+SampleStatusType.REJECTED.getValue());
        OrgCatForm orgCatForm = new OrgCatForm();
        orgCatForm.setOrgCatFormId(businessId);
        this.updateById(orgCatForm.setApproveStatus(SampleStatusType.REJECTED.getValue()));
    }

    @Override
    public void withdrawFlow(Long businessId, String param) throws Exception {
        log.error("cooperationEnded-withdrawFlow:"+SampleStatusType.WITHDRAW.getValue());
        OrgCatForm orgCatForm = new OrgCatForm();
        orgCatForm.setOrgCatFormId(businessId);
        this.updateById(orgCatForm.setApproveStatus(SampleStatusType.WITHDRAW.getValue()));

    }

    @Override
    public void destoryFlow(Long businessId, String param) throws Exception {
        log.error("cooperationEnded-destoryFlow:"+SampleStatusType.WITHDRAW.getValue());
        OrgCatForm orgCatForm = new OrgCatForm();
        orgCatForm.setOrgCatFormId(businessId);
        //用撤回代替
        this.updateById(orgCatForm.setApproveStatus(SampleStatusType.WITHDRAW.getValue()));
    }

    @Override
    public String getVariableFlow(Long businessId, String param) throws Exception {
        log.error("cooperationEnded-getVariableFlow");
        OrgCatForm orgCatForm = this.getById(businessId);
        return JsonUtil.entityToJsonStr(orgCatForm);
    }

    @Override
    public String getDataPushFlow(Long businessId, String param) throws Exception {
        log.error("cooperationEnded-getDataPushFlow");
        OrgCatForm orgCatForm = this.getById(businessId);
        return JsonUtil.entityToJsonStr(orgCatForm);
    }
}
