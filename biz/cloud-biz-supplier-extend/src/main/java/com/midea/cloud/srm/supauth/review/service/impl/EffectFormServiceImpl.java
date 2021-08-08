package com.midea.cloud.srm.supauth.review.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.constants.SequenceCodeConstant;
import com.midea.cloud.common.enums.*;
import com.midea.cloud.common.enums.review.CategoryStatus;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.feign.rbac.RbacClient;
import com.midea.cloud.srm.feign.supplier.SupplierClient;
import com.midea.cloud.srm.model.common.FormResultDTO;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.rbac.permission.entity.Permission;
import com.midea.cloud.srm.model.supplier.info.entity.BankInfo;
import com.midea.cloud.srm.model.supplier.info.entity.FinanceInfo;
import com.midea.cloud.srm.model.supplier.info.entity.OrgCategory;
import com.midea.cloud.srm.model.supplier.info.entity.OrgInfo;
import com.midea.cloud.srm.model.supplierauth.review.dto.EffectFormDTO;
import com.midea.cloud.srm.model.supplierauth.review.entity.*;
import com.midea.cloud.srm.supauth.review.mapper.EffectFormMapper;
import com.midea.cloud.srm.supauth.review.service.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
*  <pre>
 *  供方生效单据 服务实现类
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-19 21:09:09
 *  修改内容:
 * </pre>
*/
@Service
public class EffectFormServiceImpl extends ServiceImpl<EffectFormMapper, EffectForm> implements IEffectFormService {

    @Autowired
    EffectFormMapper effectFormMapper;

    @Autowired
    BaseClient baseClient;

    @Autowired
    RbacClient rbacClient;

    @Autowired
    SupplierClient supplierClient;

    @Autowired
    IOrgCateJournalService iOrgCateJournalService;

    @Autowired
    IFinanceJournalService iFinanceJournalService;

    @Autowired
    IBankJournalService iBankJournalService;

    @Autowired
    IReviewFormService iReviewFormService;

    @Autowired
    FileCenterClient fileCenterClient;

    //新增或编辑
    private FormResultDTO saveOrUpdateEffectFormDTO(EffectFormDTO effectFormDTO, EffectForm effectForm, String approveStatusType) {
        List<FinanceJournal> financeJournals = effectFormDTO.getFinanceJournals();
        List<BankJournal> bankJournals = effectFormDTO.getBankJournals();
        List<OrgCateJournal> orgCateJournals = effectFormDTO.getOrgCateJournals();
        if (effectForm.getEffectFormId() == null) {
            saveEffectForm(approveStatusType, effectForm);
        } else {
            updateEffectForm(approveStatusType, effectForm);
        }
        batchSaveOrUpdateOrgCateJournal(orgCateJournals, effectForm);
        batchSaveOrUpdateFinanceJournal(financeJournals, effectForm);
        batchSaveOrUpdateBankJournal(bankJournals, effectForm);
        //绑定附件
        if (!CollectionUtils.isEmpty(effectForm.getFileUploads())) {
            fileCenterClient.bindingFileupload(effectForm.getFileUploads(), effectForm.getEffectFormId());
        }

        //判断是否启用工作流.
        FormResultDTO formResultDTO = new FormResultDTO();
        formResultDTO.setFormId(effectForm.getEffectFormId());
        UpdateWrapper<EffectForm> updateWrapper = new UpdateWrapper<>(
                new EffectForm().setEffectFormId(effectForm.getEffectFormId()));
        //拟定状态不进入工作流
        if (!ApproveStatusType.DRAFT.getValue().equals(effectForm.getApproveStatus())) {
            Long menuId = effectFormDTO.getMenuId();
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
            updateVendorMainData(orgCateJournals, financeJournals, bankJournals);
            //改变状态为已审批
            updateApproveStatus(updateWrapper, ApproveStatusType.APPROVED.getValue());
        }
        return formResultDTO;
    }

    private void updateVendorMainData(List<OrgCateJournal> orgCateJournals, List<FinanceJournal> financeJournals,
                                      List<BankJournal> bankJournals) {
        if (!CollectionUtils.isEmpty(orgCateJournals)) {
            for (OrgCateJournal orgCateJournal : orgCateJournals) {
                if (orgCateJournal == null) continue;
                Long categoryId = orgCateJournal.getCategoryId();
                Long orgId = orgCateJournal.getOrgId();
                if (categoryId != null) {
                    updateOrgCategoryData(orgCateJournal);
                    if (orgId != null) {
                        updateOrgInfoData(orgCateJournal);
                    }
                } else {
                    updateOrgInfoData(orgCateJournal);
                }
                //更新供应商银行信息数据
                createBankInfoData(bankJournals, orgCateJournal);

                //更新供应商财务信息数据
                createFinanceInfoData(financeJournals, orgCateJournal);
            }
        }
    }


    private void createFinanceInfoData(List<FinanceJournal> financeJournals, OrgCateJournal orgCateJournal) {
        if (!CollectionUtils.isEmpty(financeJournals)) {
            for (FinanceJournal financeJournal : financeJournals) {
                if (financeJournal == null) continue;
                FinanceInfo repeatFinanceInfo = supplierClient.getFinanceInfoByCompanyIdAndOrgId(orgCateJournal.getVendorId(), orgCateJournal.getOrgId());
                if (repeatFinanceInfo != null) continue;
                FinanceInfo financeInfo = new FinanceInfo();
                BeanUtils.copyProperties(financeJournal, financeInfo);
                financeInfo.setCompanyId(orgCateJournal.getVendorId());
                if (orgCateJournal.getOrgId() != null) {
                    financeInfo.setOrgId(orgCateJournal.getOrgId());
                    financeInfo.setOrgCode(orgCateJournal.getOrgCode());
                    financeInfo.setOrgName(orgCateJournal.getOrgName());
                }
                supplierClient.addFinanceInfo(financeInfo);
            }
        }
    }


    private void createBankInfoData(List<BankJournal> bankJournals, OrgCateJournal orgCateJournal) {
        if (!CollectionUtils.isEmpty(bankJournals)) {
            for (BankJournal bankJournal : bankJournals) {
                if (bankJournal == null) continue;
                BankInfo bankInfo = new BankInfo();
                bankInfo.setCompanyId(bankJournal.getVendorId())
                        .setBankAccount(bankJournal.getBankAccount())
                        .setUnionCode(bankJournal.getUnionCode()).setOrgId(orgCateJournal.getOrgId());
                BankInfo repeatBankInfo = supplierClient.getBankInfoByParm(bankInfo);
                if (repeatBankInfo == null) {
                    BeanUtils.copyProperties(bankJournal, bankInfo);
                    if (orgCateJournal.getOrgId() != null) {
                        bankInfo.setOrgId(orgCateJournal.getOrgId());
                        bankInfo.setOrgCode(orgCateJournal.getOrgCode());
                        bankInfo.setOrgName(orgCateJournal.getOrgName());
                    }
                    supplierClient.addBankInfo(bankInfo);
                }
            }
        }
    }

    private void updateOrgCategoryData(OrgCateJournal orgCateJournal) {
        OrgCategory orgCategory = new OrgCategory();
        orgCategory.setServiceStatus(CategoryStatus.QUALIFIED.toString())
                .setCategoryId(orgCateJournal.getCategoryId())
                .setOrgId(orgCateJournal.getOrgId())
                .setCompanyId(orgCateJournal.getVendorId())
                .setStartDate(LocalDate.now());
        supplierClient.updateOrgCategoryServiceStatus(orgCategory);
    }

    private void updateOrgInfoData(OrgCateJournal orgCateJournal) {
        OrgInfo orgInfo = new OrgInfo();
        orgInfo.setServiceStatus(com.midea.cloud.common.enums.review.OrgStatus.EFFECTIVE.toString())
                .setCompanyId(orgCateJournal.getVendorId())
                .setOrgId(orgCateJournal.getOrgId())
                .setStartDate(LocalDate.now());
        supplierClient.updateOrgInfoServiceStatus(orgInfo);
    }

    private void updateApproveStatus(UpdateWrapper<EffectForm> updateWrapper, String approveStatusType) {
        updateWrapper.set("APPROVE_STATUS", approveStatusType);
        this.update(updateWrapper);
    }

    @Override
    @Transactional
    public Long add(EffectFormDTO effectFormDTO, String approveStatusType) {
        EffectForm effectForm = effectFormDTO.getEffectForm();
        Assert.notNull(effectForm, "供方生效单为空");
        checkAllBeforeSaveOrUpdate(effectFormDTO, effectForm);
        saveEffectForm(approveStatusType, effectForm);

        batchSaveOrgCateJournal(effectFormDTO, effectForm);
        batchSaveFinanceJournal(effectFormDTO, effectForm);
        batchSaveBankJournal(effectFormDTO, effectForm);

        return effectForm.getEffectFormId();
    }

    @Override
    public PageInfo<EffectForm> listPageByParm(EffectForm effectForm) {
        PageUtil.startPage(effectForm.getPageNum(), effectForm.getPageSize());
        EffectForm form = new EffectForm();
        if (!StringUtils.isBlank(effectForm.getQuaReviewType())) {
            form.setQuaReviewType(effectForm.getQuaReviewType());
        }
        if (!StringUtils.isBlank(effectForm.getApproveStatus())) {
            form.setApproveStatus(effectForm.getApproveStatus());
        }
        QueryWrapper<EffectForm> wrapper = new QueryWrapper<EffectForm>(form);
        if (!StringUtils.isBlank(effectForm.getEffectFormNumber())) {
            wrapper.like("EFFECT_FORM_NUMBER", effectForm.getEffectFormNumber());
        }
        if (!StringUtils.isBlank(effectForm.getVendorName())) {
            wrapper.like("VENDOR_NAME", effectForm.getVendorName());
        }
        if (!StringUtils.isBlank(effectForm.getReviewFormNumber())) {
            wrapper.like("REVIEW_FORM_NUMBER", effectForm.getReviewFormNumber());
        }
        if (!StringUtils.isBlank(effectForm.getSiteFormNumber())) {
            wrapper.like("SITE_FORM_NUMBER", effectForm.getSiteFormNumber());
        }
        wrapper.orderByDesc("LAST_UPDATE_DATE");
        return new PageInfo<>(effectFormMapper.selectList(wrapper));
    }

    @Override
    public EffectFormDTO getEffectFormDTOById(Long effectFormId) {
        Assert.notNull(effectFormId, "effectFormId不能为空");
        EffectFormDTO effectFormDTO = new EffectFormDTO();
        //组合effectFormDTO
        assembleEffectFormDTO(effectFormId, effectFormDTO);
        return effectFormDTO;
    }

    @Override
    @Transactional
    public FormResultDTO submitted(EffectFormDTO effectFormDTO, String approveStatusType) {
        EffectForm effectForm = effectFormDTO.getEffectForm();
        Assert.notNull(effectForm, "供方生效单为空");
        checkAllBeforeSaveOrUpdate(effectFormDTO, effectForm);
        FormResultDTO formResultDTO = saveOrUpdateEffectFormDTO(effectFormDTO, effectForm, approveStatusType);
        return formResultDTO;
    }

    @Override
    @Transactional
    public FormResultDTO saveTemporary(EffectFormDTO effectFormDTO, String approveStatusType) {
        EffectForm effectForm = effectFormDTO.getEffectForm();
        Assert.notNull(effectForm, "供方生效单为空");
        FormResultDTO formResultDTO = saveOrUpdateEffectFormDTO(effectFormDTO, effectForm, approveStatusType);
        return formResultDTO;
    }

    @Override
    @Transactional
    public void bachDeleteByList(List<Long> effectFormIds) {
        for (Long effectFormId : effectFormIds) {
            EffectForm effectForm = effectFormMapper.selectById(effectFormId);
            if (effectForm != null) {
                if (ApproveStatusType.DRAFT.getValue().equals(effectForm.getApproveStatus())
                        || ApproveStatusType.REJECTED.getValue().equals(effectForm.getApproveStatus())) {
                    effectFormMapper.deleteById(effectFormId);
                    iOrgCateJournalService.remove(new QueryWrapper<>(
                            new OrgCateJournal().setOrgCateBillType(OrgCateBillType.EFFECT_FORM.getValue())
                                    .setOrgCateBillId(effectFormId)));
                    iBankJournalService.remove(new QueryWrapper<>(
                            new BankJournal().setFormType(FormType.EFFECT_FORM.toString())
                                    .setFormId(effectFormId)));
                    iFinanceJournalService.remove(new QueryWrapper<>(
                            new FinanceJournal().setFormType(FormType.EFFECT_FORM.toString())
                                    .setFormId(effectFormId)));
                    fileCenterClient.deleteByParam(new Fileupload().setBusinessId(effectFormId));
                } else {
                    throw new BaseException("供方生效单已提交或已审批,不能删除");
                }
            }
        }
    }

    //组合effectFormDTO
    private void assembleEffectFormDTO(Long effectFormId, EffectFormDTO effectFormDTO) {
        EffectForm effectForm = effectFormMapper.selectById(effectFormId);

        QueryWrapper<OrgCateJournal> orgCateJournalQueryWrapper = new QueryWrapper<>(new OrgCateJournal().setOrgCateBillType(OrgCateBillType.EFFECT_FORM.getValue())
                .setOrgCateBillId(effectFormId));
        List<OrgCateJournal> orgCateJournals = iOrgCateJournalService.list(orgCateJournalQueryWrapper);

        QueryWrapper<FinanceJournal> financeJournalQueryWrapper = new QueryWrapper<>(new FinanceJournal().setFormType(FormType.EFFECT_FORM.toString())
                .setFormId(effectFormId));
        List<FinanceJournal> financeJournals = iFinanceJournalService.list(financeJournalQueryWrapper);

        QueryWrapper<BankJournal> bankJournalQueryWrapper = new QueryWrapper<>(new BankJournal().setFormType(FormType.EFFECT_FORM.toString())
                .setFormId(effectFormId));
        List<BankJournal> bankJournals = iBankJournalService.list(bankJournalQueryWrapper);

        effectFormDTO.setEffectForm(effectForm);
        effectFormDTO.setBankJournals(bankJournals);
        effectFormDTO.setFinanceJournals(financeJournals);
        effectFormDTO.setOrgCateJournals(orgCateJournals);
    }

    //批量保存银行信息日志
    private void batchSaveBankJournal(EffectFormDTO effectFormDTO, EffectForm effectForm) {
        List<BankJournal> bankJournals = effectFormDTO.getBankJournals();
        if (!CollectionUtils.isEmpty(bankJournals)) {
            for (BankJournal bankJournal : bankJournals) {
                if (bankJournal == null) continue;
                saveBankJournal(effectForm, bankJournal);
            }
        }
    }

    //批量保存财务信息日志
    private void batchSaveFinanceJournal(EffectFormDTO effectFormDTO, EffectForm effectForm) {
        List<FinanceJournal> financeJournals = effectFormDTO.getFinanceJournals();
        if (!CollectionUtils.isEmpty(financeJournals)) {
            for (FinanceJournal financeJournal : financeJournals) {
                if (financeJournal == null) continue;
                saveFinanceJournal(effectForm, financeJournal);
            }
        }
    }

    //批量保存组织与品类日志
    private void batchSaveOrgCateJournal(EffectFormDTO effectFormDTO, EffectForm effectForm) {
        List<OrgCateJournal> orgCateJournals = effectFormDTO.getOrgCateJournals();
        if (!CollectionUtils.isEmpty(orgCateJournals)) {
            for (OrgCateJournal orgCateJournal : orgCateJournals) {
                if (orgCateJournal == null) continue;
                saveOrgCateJournal(effectForm, orgCateJournal);
            }
        }
    }

    //批量编辑银行信息日志
    private void batchUpdateBankJournal(EffectFormDTO effectFormDTO, EffectForm effectForm) {
        List<BankJournal> bankJournals = effectFormDTO.getBankJournals();
        if (!CollectionUtils.isEmpty(bankJournals)) {
            for (BankJournal bankJournal : bankJournals) {
                if (bankJournal == null) continue;
                updateBankJournal(bankJournal);
            }
        }
    }

    //批量编辑财务信息日志
    private void batchUpdateFinanceJournal(EffectFormDTO effectFormDTO, EffectForm effectForm) {
        List<FinanceJournal> financeJournals = effectFormDTO.getFinanceJournals();
        if (!CollectionUtils.isEmpty(financeJournals)) {
            for (FinanceJournal financeJournal : financeJournals) {
                if (financeJournal == null) continue;
                updateFinanceJournal(financeJournal);
            }
        }
    }

    //提交前校验
    private void checkAllBeforeSaveOrUpdate(EffectFormDTO effectFormDTO, EffectForm effectForm) {

        if (effectForm.getEffectFormId() != null) {
            EffectForm selectById = effectFormMapper.selectById(effectForm.getEffectFormId());
            if (selectById != null && (ApproveStatusType.APPROVED.getValue().equals(selectById.getApproveStatus())
                    || ApproveStatusType.SUBMITTED.getValue().equals(selectById.getApproveStatus()) || ApproveStatusType.ABANDONED.getValue().equals(selectById.getApproveStatus()))) {
                throw new BaseException("供方生效单状态为已提交或已审批不允许再修改");
            }
        }

        if (effectForm.getVendorId() == null) {
            throw new BaseException("供应商为空");
        }

        if (StringUtils.isBlank(effectForm.getQuaReviewType())) {
            throw new BaseException("资质审查类型为空");
        }

        if (StringUtils.isBlank(effectForm.getReviewFormNumber())) {
            throw new BaseException("资质审查单号为空");
        }

        if (effectForm.getReviewFormId() == null) {
            throw new BaseException("资质审查单ID为空");
        } else {
            ReviewForm byId = iReviewFormService.getById(effectForm.getReviewFormId());
            if ((byId != null && YesOrNo.YES.getValue().equals(byId.getIfSiteForm()))
                    && StringUtils.isBlank(effectForm.getSiteFormNumber())) {
                throw new BaseException("现场评审单据号为空");
            }
        }

        if (CollectionUtils.isEmpty(effectFormDTO.getOrgCateJournals())) {
            throw new BaseException("请选择引入组织与品类");
        }

        //TODO 暂时注释,后续需加维度控制进行检验
       /* if (CollectionUtils.isEmpty(effectFormDTO.getFinanceJournals())) {
            throw new BaseException("财务信息为空");
        } else {
            List<FinanceJournal> financeJournals = effectFormDTO.getFinanceJournals();
            for (FinanceJournal financeJournal : financeJournals) {
                if (StringUtils.isBlank(financeJournal.getClearCurrency())) {
                    throw new BaseException("结算币种为空");
                } else if (StringUtils.isBlank(financeJournal.getPaymentMethod())) {
                    throw new BaseException("付款方式为空");
                } else if (StringUtils.isBlank(financeJournal.getPaymentTerms())) {
                    throw new BaseException("付款条件为空");
                } else if (financeJournal.getTaxRate() == null) {
                    throw new BaseException("税率为空");
                }
            }
        }

        if (CollectionUtils.isEmpty(effectFormDTO.getBankJournals())) {
            throw new BaseException("银行信息为空");
        } else {
            List<BankJournal> bankJournals = effectFormDTO.getBankJournals();
            for (BankJournal bankJournal : bankJournals) {
                if (StringUtils.isBlank(bankJournal.getOpeningBank())) {
                    throw new BaseException("开户行为空");
                } else if (StringUtils.isBlank(bankJournal.getUnionCode())) {
                    throw new BaseException("联行编码为空");
                } else if (StringUtils.isBlank(bankJournal.getBankAccountName())) {
                    throw new BaseException("账户名称为空");
                } else if (StringUtils.isBlank(bankJournal.getBankAccount())) {
                    throw new BaseException("银行账号为空");
                } else if (StringUtils.isBlank(bankJournal.getCurrencyCode())) {
                    throw new BaseException("币种为空");
                } else if (StringUtils.isBlank(bankJournal.getAccountType())) {
                    throw new BaseException("账号类型为空");
                }
            }
        }*/
    }

    //批量编辑组织与品类日志
    private void batchUpdateOrgCateJournal(EffectFormDTO effectFormDTO, EffectForm effectForm) {
        List<OrgCateJournal> orgCateJournals = effectFormDTO.getOrgCateJournals();
        if (!CollectionUtils.isEmpty(orgCateJournals)) {
            for (OrgCateJournal orgCateJournal : orgCateJournals) {
                if (orgCateJournal == null) continue;
                updateOrgCateJournal(orgCateJournal);
            }
        }
    }

    private void updateEffectForm(String approveStatusType, EffectForm effectForm) {
        effectForm.setApproveStatus(approveStatusType);
        effectFormMapper.updateById(effectForm);
    }

    private void saveEffectForm(String approveStatusType, EffectForm effectForm) {
        long id = IdGenrator.generate();
        effectForm.setEffectFormId(id)
                  .setEffectFormNumber(baseClient.seqGen(SequenceCodeConstant.SEQ_VENDOR_EFFECT_FORM_NUM_CODE))
                  .setApproveStatus(approveStatusType);
        effectFormMapper.insert(effectForm);
    }

    //批量添加或编辑银行信息日志
    private void batchSaveOrUpdateBankJournal(List<BankJournal> bankJournals, EffectForm effectForm) {
        if (!CollectionUtils.isEmpty(bankJournals)) {
            for (BankJournal bankJournal : bankJournals) {
                if (bankJournal == null) continue;
                //当前银行信息日志属于资质审查单,主键ID需要置空.
                if (bankJournal.getFormId() != effectForm.getEffectFormId()
                        && !FormType.EFFECT_FORM.toString().equals(bankJournal.getFormType())) {
                    bankJournal.setBankJournalId(null).setFormId(null).setFormType(null);
                }
                if (bankJournal.getBankJournalId() == null) {
                    saveBankJournal(effectForm, bankJournal);
                } else {
                    updateBankJournal(bankJournal);
                }
            }
        }
    }

    private void updateBankJournal(BankJournal bankJournal) {
        iBankJournalService.updateById(bankJournal);
    }

    private void saveBankJournal(EffectForm effectForm, BankJournal bankJournal) {
        long id = IdGenrator.generate();
        bankJournal.setBankJournalId(id)
                   .setFormType(FormType.EFFECT_FORM.toString())
                   .setFormId(effectForm.getEffectFormId())
                   .setVendorId(effectForm.getVendorId());
        iBankJournalService.save(bankJournal);
    }

    //批量添加财务信息日志
    private void batchSaveOrUpdateFinanceJournal(List<FinanceJournal> financeJournals, EffectForm effectForm) {
        if (!CollectionUtils.isEmpty(financeJournals)) {
            for (FinanceJournal financeJournal : financeJournals) {
                if (financeJournal == null) continue;
                //当前财务信息日志属于资质审查单,主键ID需要置空.
                if (financeJournal.getFormId() != effectForm.getEffectFormId()
                        && !FormType.EFFECT_FORM.toString().equals(financeJournal.getFormType())) {
                    financeJournal.setFinanceJournalId(null).setFormId(null).setFormType(null);
                }
                if (financeJournal.getFinanceJournalId() == null) {
                    saveFinanceJournal(effectForm, financeJournal);
                } else {
                    updateFinanceJournal(financeJournal);
                }
            }
        }
    }

    private void updateFinanceJournal(FinanceJournal financeJournal) {
        iFinanceJournalService.updateById(financeJournal);
    }

    private void saveFinanceJournal(EffectForm effectForm, FinanceJournal financeJournal) {
        long id = IdGenrator.generate();
        financeJournal.setFormType(FormType.EFFECT_FORM.toString())
                      .setFormId(effectForm.getEffectFormId())
                      .setVendorId(effectForm.getVendorId())
                      .setFinanceJournalId(id);
        iFinanceJournalService.save(financeJournal);
    }

    //批量添加组织与品类日志
    private void batchSaveOrUpdateOrgCateJournal(List<OrgCateJournal> orgCateJournals, EffectForm effectForm) {
        if (!CollectionUtils.isEmpty(orgCateJournals)) {
            for (OrgCateJournal orgCateJournal : orgCateJournals) {
                if (orgCateJournal == null) continue;
                if (orgCateJournal.getOrgCateJournalId() == null) {
                    saveOrgCateJournal(effectForm, orgCateJournal);
                } else {
                    updateOrgCateJournal(orgCateJournal);
                }
            }
        }
    }

    private void updateOrgCateJournal(OrgCateJournal orgCateJournal) {
        orgCateJournal.setLastUpdateDate(new Date());
        iOrgCateJournalService.updateById(orgCateJournal);
    }

    private void saveOrgCateJournal(EffectForm effectForm, OrgCateJournal orgCateJournal) {
        long id = IdGenrator.generate();
        orgCateJournal.setOrgCateBillType(OrgCateBillType.EFFECT_FORM.getValue())
                      .setOrgCateBillId(effectForm.getEffectFormId())
                      .setVendorId(effectForm.getVendorId())
                      .setOrgCateJournalId(id)
                      .setCreationDate(new Date())
                      .setLastUpdateDate(new Date());
        iOrgCateJournalService.save(orgCateJournal);
    }
}
