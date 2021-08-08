package com.midea.cloud.srm.pr.logisticsRequirement.service.impl;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.constants.SequenceCodeConstant;
import com.midea.cloud.common.enums.logistics.BusinessMode;
import com.midea.cloud.common.enums.logistics.LogisticsBusinessType;
import com.midea.cloud.common.enums.logistics.pr.requirement.LogisticsApplyAssignStyle;
import com.midea.cloud.common.enums.logistics.pr.requirement.LogisticsApplyProcessStatus;
import com.midea.cloud.common.enums.logistics.pr.requirement.LogisticsApproveStatus;
import com.midea.cloud.common.enums.pm.pr.requirement.RequirementApplyStatus;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.logistics.template.service.ILogisticsTemplateHeadService;
import com.midea.cloud.srm.logistics.template.service.ILogisticsTemplateLineService;
import com.midea.cloud.srm.model.logistics.pr.requirement.dto.LogisticsPurchaseRequirementDTO;
import com.midea.cloud.srm.model.logistics.pr.requirement.dto.LogisticsRequirementHeadQueryDTO;
import com.midea.cloud.srm.model.logistics.pr.requirement.dto.LogisticsRequirementManageDTO;
import com.midea.cloud.srm.model.logistics.pr.requirement.entity.LogisticsRequirementFile;
import com.midea.cloud.srm.model.logistics.pr.requirement.entity.LogisticsRequirementHead;
import com.midea.cloud.srm.model.logistics.pr.requirement.entity.LogisticsRequirementLine;
import com.midea.cloud.srm.model.logistics.template.entity.LogisticsTemplateLine;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.pr.logisticsRequirement.mapper.LogisticsRequirementHeadMapper;
import com.midea.cloud.srm.pr.logisticsRequirement.service.IRequirementFileService;
import com.midea.cloud.srm.pr.logisticsRequirement.service.IRequirementHeadService;
import com.midea.cloud.srm.pr.logisticsRequirement.service.IRequirementLineService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <pre>
 *  物流采购需求头表 服务实现类
 *  物流寻源模块，物流采购申请(发布dev测试)
 * </pre>
 *
 * @author chenwt24@meiCloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-11-27 10:59:47
 *  修改内容:
 * </pre>
 */
@Service(value = "LogisticsRequirementHeadServiceImpl")
@Slf4j
public class RequirementHeadServiceImpl extends ServiceImpl<LogisticsRequirementHeadMapper, LogisticsRequirementHead> implements IRequirementHeadService {

    @Resource
    private BaseClient baseClient;
    @Autowired
    private IRequirementLineService iRequirementLineService;
    @Autowired
    private IRequirementFileService iRequirementFileService;

    @Autowired
    private ILogisticsTemplateLineService iLogisticsTemplateLineService;
    @Autowired
    private ILogisticsTemplateHeadService logisticsTemplateHeadService;
    @Autowired
    private LogisticsRequirementHeadMapper requirementHeadMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long addPurchaseRequirement(LogisticsPurchaseRequirementDTO purchaseRequirementDTO) {
        log.info("保存物流采购申请addPurchaseRequirement,参数：{}", purchaseRequirementDTO);
        LogisticsRequirementHead requirementHead = purchaseRequirementDTO.getRequirementHead();
        List<LogisticsRequirementLine> requirementLineList = purchaseRequirementDTO.getRequirementLineList();
        List<LogisticsRequirementFile> requirementFiles = purchaseRequirementDTO.getRequirementAttaches();

        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        //创建人中文名
        String createdByName = loginAppUser.getNickname();

        long id = IdGenrator.generate();
        String status = requirementHead.getRequirementStatus();
        if(Objects.isNull(status)){
            status = LogisticsApproveStatus.DRAFT.getValue();
        }

        requirementHead.setRequirementHeadId(id)
                .setRequirementHeadNum(getRequirementNum(requirementHead))
                .setCreatedByName(createdByName)
                .setRequirementStatus(status);
        this.save(requirementHead);
        //保存采购需求行列表
        iRequirementLineService.addRequirementLineBatch(requirementHead, requirementLineList);
        //保存采购需求附件表
        iRequirementFileService.addRequirementFileBatch(requirementHead, requirementFiles);
        return id;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByHeadId(Long requirementHeadId) {
        log.info("删除物流采购申请单，参数：{}",requirementHeadId);
        LogisticsRequirementHead requirementHead = this.getById(requirementHeadId);
        if(Objects.isNull(requirementHead)){
            throw new BaseException(String.format("未找到物流采购申请单，单据id：%s",requirementHead));
        }
        //拟定状态下才可删除
        if (LogisticsApproveStatus.DRAFT.getValue().equals(requirementHead.getRequirementStatus())) {
            this.removeById(requirementHeadId);
            List<LogisticsRequirementLine> rls = iRequirementLineService.list(new QueryWrapper<>(new LogisticsRequirementLine().setRequirementHeadId(requirementHeadId)));
            iRequirementLineService.remove(new QueryWrapper<>(new LogisticsRequirementLine().setRequirementHeadId(requirementHeadId)));
            iRequirementFileService.remove(new QueryWrapper<>(new LogisticsRequirementFile().setRequirementHeaderId(requirementHeadId)));
        } else {
            throw new BaseException(LocaleHandler.getLocaleMsg("只有拟定状态下才可以删除"));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long modifyPurchaseRequirement(LogisticsPurchaseRequirementDTO purchaseRequirementDTO) {
        LogisticsRequirementHead requirementHead = purchaseRequirementDTO.getRequirementHead();
        List<LogisticsRequirementLine> requirementLineList = purchaseRequirementDTO.getRequirementLineList();
        List<LogisticsRequirementFile> requirementAttaches = purchaseRequirementDTO.getRequirementAttaches();


        LogisticsRequirementHead head = this.getById(requirementHead.getRequirementHeadId());
        if (LogisticsApproveStatus.APPROVED.getValue().equals(head.getRequirementStatus())
                && LogisticsApproveStatus.APPROVING.getValue().equals(head.getRequirementStatus())) {
            throw new BaseException(LocaleHandler.getLocaleMsg("只有拟定或已驳回状态才可以编辑"));
        }
        this.updateById(requirementHead);
        iRequirementLineService.updateBatch(requirementHead, requirementLineList);
        //编辑采购需求附件表 (ceea)
        iRequirementFileService.updateRequirementAttachBatch(requirementHead, requirementAttaches);
        return head.getRequirementHeadId();
    }

    /**
     * <pre>
     *  //业务模式为国内时，字母为D，业务模式为国际时，字母为F，业务模式为海外时，字母为S；
     * </pre>
     *
     * @author chenwt24@meicloud.com
     * @version 1.00.00
     * <p>
     * <pre>
     *  修改记录
     *  修改后版本:
     *  修改人:
     *  修改日期: 2020-11-27
     *  修改内容:
     * </pre>
     */
    public String getRequirementNum(LogisticsRequirementHead requirementHead) {
        Objects.requireNonNull(requirementHead.getBusinessModeCode());
        String code = requirementHead.getBusinessModeCode();
        String reequirementNum = null;
        if (Objects.equals(BusinessMode.INSIDE.getValue(), code)) {
            reequirementNum = baseClient.seqGen(SequenceCodeConstant.SEQ_PMP_PR_APPLY_NUM_D);
        } else if (Objects.equals(BusinessMode.OUTSIDE.getValue(), code)) {
            reequirementNum = baseClient.seqGen(SequenceCodeConstant.SEQ_PMP_PR_APPLY_NUM_F);
        } else if (Objects.equals(BusinessMode.OVERSEA.getValue(), code)) {
            reequirementNum = baseClient.seqGen(SequenceCodeConstant.SEQ_PMP_PR_APPLY_NUM_S);
        }else {
            throw new BaseException(String.format("业务模式字典码[%s]错误，请联系管理员",code));
        }
        return reequirementNum;
    }

    @Override
    public PageInfo<LogisticsRequirementHead> listPage(LogisticsRequirementHeadQueryDTO requirementHeadQueryDTO) {
        PageUtil.startPage(requirementHeadQueryDTO.getPageNum(), requirementHeadQueryDTO.getPageSize());
        List<LogisticsRequirementHead> requirementHeadList = requirementHeadMapper.list(requirementHeadQueryDTO);
        return new PageInfo<>(requirementHeadList);
    }

    @Override
    public LogisticsPurchaseRequirementDTO getByHeadId(Long requirementHeadId) {
        //校验
        Assert.notNull(requirementHeadId, LocaleHandler.getLocaleMsg("采购需求头ID不能为空"));
        LogisticsRequirementHead requirementHead = this.getById(requirementHeadId);
        Assert.notNull(requirementHead,LocaleHandler.getLocaleMsg(String.format("找不到物流采购申请,requirementHeadId=[%s]",requirementHeadId)));

        LogisticsPurchaseRequirementDTO vo = new LogisticsPurchaseRequirementDTO();
        vo.setRequirementHead(requirementHead);
        List<LogisticsRequirementLine> requirementLineList = iRequirementLineService.list(new QueryWrapper<>(new LogisticsRequirementLine().
                setRequirementHeadId(requirementHeadId)));
        vo.setRequirementLineList(requirementLineList);
        List<LogisticsRequirementFile> requirementAttaches = iRequirementFileService.list(new QueryWrapper<>(new LogisticsRequirementFile()
                .setRequirementHeaderId(requirementHeadId)));
        vo.setRequirementAttaches(requirementAttaches);
        //采购申请模板头
        vo.setLogisticsTemplateHead(logisticsTemplateHeadService.getById(vo.getRequirementHead().getTemplateHeadId()));
        //采购申请模板行
        List<LogisticsTemplateLine> logisticsTemplateLineList = iLogisticsTemplateLineService.list(new QueryWrapper<>(new LogisticsTemplateLine().setHeadId(vo.getRequirementHead().getTemplateHeadId())));
        vo.setLogisticsTemplateLineList(logisticsTemplateLineList);
        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void submitApproval(LogisticsPurchaseRequirementDTO purchaseRequirementDTO) {
        log.info("提交物流采购申请，参数：{}",purchaseRequirementDTO.toString());
        LogisticsRequirementHead requirementHead = purchaseRequirementDTO.getRequirementHead();
        List<LogisticsRequirementLine> requirementLineList = purchaseRequirementDTO.getRequirementLineList();
        List<LogisticsRequirementFile> requirementAttaches = purchaseRequirementDTO.getRequirementAttaches();
        requirementHead.setRequirementStatus(LogisticsApproveStatus.APPROVING.getValue())
                .setApplyProcessStatus(LogisticsApplyProcessStatus.UNPROCESSED.getValue())
                .setApplyAssignStatus(LogisticsApplyAssignStyle.UNASSIGNED.getValue());

        checkForSubmit(requirementHead,requirementLineList);

        Long requirementHeadId = null;
        if (requirementHead.getRequirementHeadId() == null) {
            requirementHeadId = this.addPurchaseRequirement(purchaseRequirementDTO);
        } else {
            requirementHeadId = this.modifyPurchaseRequirement(purchaseRequirementDTO);
            this.updateById(requirementHead);
        }

        //模拟对接审批流
        approval(requirementHeadId);
    }

    /**
     * 物流采购申请单审批
     * @param requirementHeadId
     */
    @Transactional
    public void approval(Long requirementHeadId) {
        LogisticsRequirementHead requirementHead = this.getById(requirementHeadId);
        if(Objects.isNull(requirementHead)){
            throw new BaseException(LocaleHandler.getLocaleMsg("找不到该物流采购申请：requirementHeadId = [" + requirementHeadId + "]"));
        }
        this.updateById(requirementHead.setRequirementStatus(LogisticsApproveStatus.APPROVED.getValue()));
    }

    public void checkForSubmit(LogisticsRequirementHead requirementHead,List<LogisticsRequirementLine> requirementLineList){
        Assert.notNull(requirementHead, LocaleHandler.getLocaleMsg("采购需求头不能为空"));
        Assert.notEmpty(requirementLineList, LocaleHandler.getLocaleMsg("请至少添加一行行明细信息"));
        String errorMsg = "%s为带*的字段，要求必填，请维护字段值";
        //检查物流采购申请头，必填字段
        Assert.notNull(requirementHead.getTemplateHeadId(), LocaleHandler.getLocaleMsg(String.format(errorMsg,"申请模板")));
        Assert.hasText(requirementHead.getBusinessModeCode(), LocaleHandler.getLocaleMsg(String.format(errorMsg,"业务模式")));
        Assert.hasText(requirementHead.getTransportModeCode(), LocaleHandler.getLocaleMsg(String.format(errorMsg,"运输方式")));
        Assert.hasText(requirementHead.getBusinessType(), LocaleHandler.getLocaleMsg(String.format(errorMsg,"业务类型不能为空")));
//        Assert.hasText(requirementHead.getUnit(), LocaleHandler.getLocaleMsg(String.format(errorMsg,"单位")));
//        Assert.notNull(requirementHead.getProjectTotal(), LocaleHandler.getLocaleMsg(String.format(errorMsg,"项目总量")));
        Assert.notNull(requirementHead.getDemandDate(), LocaleHandler.getLocaleMsg(String.format(errorMsg,"需求日期")));
        Assert.notNull(requirementHead.getBudgetAmount(), LocaleHandler.getLocaleMsg(String.format(errorMsg,"预算金额")));
        Assert.notNull(requirementHead.getCurrencyId(), LocaleHandler.getLocaleMsg(String.format(errorMsg,"币种")));
        Assert.notNull(requirementHead.getPriceStartDate(), LocaleHandler.getLocaleMsg(String.format(errorMsg,"价格有效开始日期")));
        Assert.notNull(requirementHead.getPriceEndDate(), LocaleHandler.getLocaleMsg(String.format(errorMsg,"价格有效结束日期")));


        //ii.	若业务类型为项目类时，校验服务项目名称必填
        if(Objects.equals(requirementHead.getBusinessType(), LogisticsBusinessType.PROJECT.getValue())
                && StringUtils.isEmpty(requirementHead.getServiceProjectCode())) {
            throw new BaseException(LocaleHandler.getLocaleMsg("若业务类型为项目类时，校验服务项目名称必填"));
        }

        //检查行，必填
        checkLineByTemplate(requirementHead,requirementLineList);

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
    public void checkLineByTemplate(LogisticsRequirementHead requirementHead,List<LogisticsRequirementLine> requirementLineList){
        String templateName = requirementHead.getTemplateName();
        Objects.requireNonNull(templateName);

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

    public Object doCheck(String fieldCode,LogisticsRequirementLine line) throws IllegalAccessException {

        Field[] declaredFields = LogisticsRequirementLine.class.getDeclaredFields();
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
    public void updateApproved(Long requirementHeadId, String auditStatus) {
        LogisticsRequirementHead byId = this.getById(requirementHeadId);
        //List<LogisticsRequirementLine> requirementLineList = iRequirementLineService.list(new QueryWrapper<>(new LogisticsRequirementLine().setRequirementHeadId(requirementHeadId)));
        if (byId != null && !LogisticsApproveStatus.APPROVING.getValue().equals(byId.getRequirementStatus())) {
            throw new BaseException("单据非已提交状态,不可审批,请检查!");
        }
        // 更新头表 将该数据写入需求池(未分配)
        this.updateById(byId.setRequirementStatus(auditStatus).setApplyAssignStatus(LogisticsApplyAssignStyle.UNASSIGNED.getValue()));

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void withdraw(Long requirementHeadId, String rejectReason) {
        this.updateById(new LogisticsRequirementHead().setRequirementHeadId(requirementHeadId).
                setRequirementStatus(LogisticsApproveStatus.CANCEL.getValue()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void reject(Long requirementHeadId, String rejectReason) {
        this.updateById(new LogisticsRequirementHead().setRequirementHeadId(requirementHeadId).
                setRequirementStatus(LogisticsApproveStatus.REJECTED.getValue()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void abandon(Long requirementHeadId) {
        //已驳回、已审核通过的可以作废。作废时判断物流申请单是否已经被分配。未被分配则可以作废。被分配后需要联系分配的采购员进行取消分配
        LogisticsRequirementHead head = this.getById(requirementHeadId);
        if(!Objects.equals(head.getRequirementStatus(),LogisticsApproveStatus.REJECTED.getValue())
                && !Objects.equals(head.getRequirementStatus(),LogisticsApproveStatus.APPROVED.getValue())){
            throw new BaseException("已驳回、已审核通过的可以作废。");
        }
        if(Objects.equals(head.getRequirementStatus(),RequirementApplyStatus.ASSIGNED.getValue())){
            throw new BaseException("物流申请单已经被分配,请联系联系分配的采购员进行取消分配");
        }
        this.updateById(new LogisticsRequirementHead().setRequirementHeadId(requirementHeadId).
                setRequirementStatus(LogisticsApproveStatus.CANCEL.getValue()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void bachAssigned(LogisticsRequirementManageDTO requirementManageDTO) {
        List<Long> requirementHeadIds = requirementManageDTO.getRequirementHeadIds();
        Assert.notEmpty(requirementHeadIds, ResultCode.MISSING_SERVLET_REQUEST_PARAMETER.getMessage());
        List<LogisticsRequirementHead> requirementHeads = this.listByIds(requirementHeadIds);
        for (LogisticsRequirementHead requirementHead : requirementHeads) {
            if (requirementHead == null) continue;
            if(StringUtils.isNotEmpty(requirementHead.getBidingCode())){
                throw new BaseException("已创建招标单据，不可分配或转办");
            }
            requirementHead.setCeeaApplyUserId(requirementManageDTO.getCeeaApplyUserId());
            requirementHead.setCeeaApplyUserName(requirementManageDTO.getCeeaApplyUserName());
            requirementHead.setCeeaApplyUserNickname(requirementManageDTO.getCeeaApplyUserNickname());

            requirementHead.setApplyAssignStatus(LogisticsApplyAssignStyle.ASSIGNED.getValue());
            this.updateById(requirementHead);
        }
    }

    @Override
    public void bachUnAssigned(LogisticsRequirementManageDTO requirementManageDTO) {
        List<Long> requirementHeadIds = requirementManageDTO.getRequirementHeadIds();
        Assert.notEmpty(requirementHeadIds, ResultCode.MISSING_SERVLET_REQUEST_PARAMETER.getMessage());
        List<LogisticsRequirementHead> requirementHeads = this.listByIds(requirementHeadIds);
        for (LogisticsRequirementHead requirementHead : requirementHeads) {
            if (requirementHead == null) continue;
            if(StringUtils.isNotEmpty(requirementHead.getBidingCode())){
                throw new BaseException("已创建招标单据，不可取消分配");
            }
            requirementHead.setCeeaApplyUserId(null);
            requirementHead.setCeeaApplyUserName(null);
            requirementHead.setCeeaApplyUserNickname(null);

            requirementHead.setApplyAssignStatus(LogisticsApplyAssignStyle.UNASSIGNED.getValue());
            this.updateById(requirementHead);
        }
    }

    /**
     * 需求池分页条件查询
     * @param requirementHeadQueryDTO
     * @return
     */
    @Override
    public List<LogisticsRequirementHead> listPageNew(LogisticsRequirementHeadQueryDTO requirementHeadQueryDTO) {

        LogisticsRequirementHead headQuery = new LogisticsRequirementHead();
        QueryWrapper<LogisticsRequirementHead> wrapper = new QueryWrapper<>(headQuery);
        // 需求池查询 只查 已审批的采购申请
        wrapper.eq("REQUIREMENT_STATUS", LogisticsApproveStatus.APPROVED.getValue());
        wrapper.like(StringUtils.isNotEmpty(requirementHeadQueryDTO.getRequirementHeadNum()),
                "REQUIREMENT_HEAD_NUM", requirementHeadQueryDTO.getRequirementHeadNum());

        wrapper.like(StringUtils.isNotEmpty(requirementHeadQueryDTO.getRequirementTitle()),
                "REQUIREMENT_TITLE", requirementHeadQueryDTO.getRequirementTitle());

        wrapper.like(StringUtils.isNotEmpty(requirementHeadQueryDTO.getBusinessModeCode()),
                "BUSINESS_MODE_CODE", requirementHeadQueryDTO.getBusinessModeCode());

        wrapper.like(StringUtils.isNotEmpty(requirementHeadQueryDTO.getTransportModeCode()),
                "TRANSPORT_MODE_CODE", requirementHeadQueryDTO.getTransportModeCode());

        wrapper.eq(StringUtils.isNotEmpty(requirementHeadQueryDTO.getBusinessType()),
                "BUSINESS_TYPE", requirementHeadQueryDTO.getBusinessType());

        wrapper.like(StringUtils.isNotEmpty(requirementHeadQueryDTO.getApplyBy()),
                "APPLY_BY", requirementHeadQueryDTO.getApplyBy());

        wrapper.like(StringUtils.isNotEmpty(requirementHeadQueryDTO.getApplyDepartmentName()),
                "APPLY_DEPARTMENT_NAME", requirementHeadQueryDTO.getApplyDepartmentName());

        wrapper.like(StringUtils.isNotEmpty(requirementHeadQueryDTO.getServiceProjectName()),
                "SERVICE_PROJECT_NAME", requirementHeadQueryDTO.getServiceProjectName());


        wrapper.like(StringUtils.isNotEmpty(requirementHeadQueryDTO.getCeeaApplyUserNickname()),
                "CEEA_APPLY_USER_NICKNAME", requirementHeadQueryDTO.getCeeaApplyUserNickname());
        wrapper.like(StringUtils.isNotEmpty(requirementHeadQueryDTO.getTemplateName()),
                "TEMPLATE_NAME", requirementHeadQueryDTO.getTemplateName());

        wrapper.eq(StringUtils.isNotEmpty(requirementHeadQueryDTO.getRequirementStatus()),
                "REQUIREMENT_STATUS", requirementHeadQueryDTO.getRequirementStatus());
        //需求池处理状态
        wrapper.eq(StringUtils.isNotEmpty(requirementHeadQueryDTO.getApplyProcessStatus()),
                "APPLY_PROCESS_STATUS", requirementHeadQueryDTO.getApplyProcessStatus());
        //需求池分配状态
        wrapper.eq(StringUtils.isNotEmpty(requirementHeadQueryDTO.getApplyAssignStatus()),
                "APPLY_ASSIGN_STATUS",requirementHeadQueryDTO.getApplyAssignStatus());

        wrapper.orderByDesc("LAST_UPDATE_DATE");
        return this.list(wrapper);
    }

    /**
     * 批量删除采购申请
     * @param requirementHeadIds
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(List<Long> requirementHeadIds) {
        //校验
        if(CollectionUtils.isEmpty(requirementHeadIds)){
            throw new BaseException(LocaleHandler.getLocaleMsg("请传递必填参数"));
        }
        List<LogisticsRequirementHead> logisticsRequirementHeads = this.listByIds(requirementHeadIds);
        for(LogisticsRequirementHead requirementHead : logisticsRequirementHeads){
            if(!LogisticsApproveStatus.DRAFT.getValue().equals(requirementHead.getRequirementStatus())){
                throw new BaseException(LocaleHandler.getLocaleMsg("拟定状态的申请才允许删除"));
            }
        }
        //删除
        this.removeByIds(requirementHeadIds);
    }

    /**
     * 批量复制采购申请
     * @param requirementHeadIds
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Long> batchCopy(List<Long> requirementHeadIds) {
        //校验
        if(CollectionUtils.isEmpty(requirementHeadIds)){
            throw new BaseException(LocaleHandler.getLocaleMsg("请传递必填参数"));
        }
        List<LogisticsRequirementHead> logisticsRequirementHeads = this.listByIds(requirementHeadIds);

        //获取数据
        QueryWrapper<LogisticsRequirementLine> wrapper = new QueryWrapper<>();
        wrapper.in("REQUIREMENT_HEAD_ID",requirementHeadIds);
        List<LogisticsRequirementLine> logisticsRequirementLines = iRequirementLineService.list(wrapper);
        List<LogisticsPurchaseRequirementDTO> requirementDTOList = buildRequirement(logisticsRequirementHeads,logisticsRequirementLines);

        //批量插入
        List<LogisticsRequirementHead> requirementHeadAdds = new LinkedList<>();
        List<LogisticsRequirementLine> requirementLineAdds = new LinkedList<>();
        for(LogisticsPurchaseRequirementDTO requirementDTO : requirementDTOList){
            LogisticsRequirementHead requirementHead = requirementDTO.getRequirementHead();
            List<LogisticsRequirementLine> requirementLineList = requirementDTO.getRequirementLineList();

            Long id = IdGenrator.generate();
            String status = LogisticsApproveStatus.DRAFT.getValue();
            String requirementHeadNum = getRequirementNum(requirementHead);
            requirementHead.setRequirementHeadId(id)
                    .setRequirementStatus(status)
                    .setRequirementHeadNum(requirementHeadNum);

            if(!CollectionUtils.isEmpty(requirementLineList)){
                for(LogisticsRequirementLine requirementLine : requirementLineList){
                    requirementLine.setRequirementLineId(IdGenrator.generate())
                            .setRequirementHeadId(id)
                            .setRequirementHeadNum(requirementHeadNum);
                }
            }

            //将订单单号置空
//            requirementHead.setOrderHeadNum(null);
            //将招标信息置空
            requirementHead.setBidingId(null);
            requirementHead.setBidingCode(null);
            requirementHead.setBidingName(null);
            //将采购员信息置空
            requirementHead.setCeeaApplyUserId(null);
            requirementHead.setCeeaApplyUserName(null);
            requirementHead.setCeeaApplyUserNickname(null);
            //初始化需求池处理状态
            requirementHead.setApplyProcessStatus(LogisticsApplyProcessStatus.UNPROCESSED.getValue());
            //初始化需求池分配状态
            requirementHead.setApplyAssignStatus(LogisticsApplyAssignStyle.UNASSIGNED.getValue());

            requirementHeadAdds.add(requirementHead);
            if(!CollectionUtils.isEmpty(requirementLineList)){
                requirementLineAdds.addAll(requirementLineList);
            }
        }
        if(!CollectionUtils.isEmpty(requirementHeadAdds)){
            this.saveBatch(requirementHeadAdds);
        }
        if(!CollectionUtils.isEmpty(requirementLineAdds)){
            iRequirementLineService.saveBatch(requirementLineAdds);
        }

        return requirementHeadAdds.stream().map(item -> item.getRequirementHeadId()).collect(Collectors.toList());
    }

    /**
     * 招标释放采购申请
     * @param id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void release(Long id) {
        LogisticsRequirementHead requirementHead = this.getById(id);
        requirementHead.setBidingId(null).setBidingName(null).setBidingCode(null);
        if(!LogisticsApplyProcessStatus.FINISHED.getValue().equals(requirementHead.getApplyProcessStatus())){
            requirementHead.setApplyProcessStatus(LogisticsApplyProcessStatus.UNPROCESSED.getValue());
        }
        this.updateById(requirementHead);
    }

    /**
     * 根据单号释放申请接口
     * @param requirementHeadNum
     */
    @Override
    @Transactional
    public void release(List<String> requirementHeadNum) {
        if(!CollectionUtils.isEmpty(requirementHeadNum)){
            List<LogisticsRequirementHead> requirementHeads = this.list(Wrappers.lambdaQuery(LogisticsRequirementHead.class).
                    in(LogisticsRequirementHead::getRequirementHeadNum, requirementHeadNum));
            if(!CollectionUtils.isEmpty(requirementHeads)){
                requirementHeads.forEach(item -> {
                    item.setBidingId(null).setBidingName(null).setBidingCode(null);
                    if(!LogisticsApplyProcessStatus.FINISHED.getValue().equals(item.getApplyProcessStatus())){
                        item.setApplyProcessStatus(LogisticsApplyProcessStatus.UNPROCESSED.getValue());
                    }
                    this.updateBatchById(requirementHeads);
                });
            }
        }
    }

    /**
     * 构建采购申请DTO
     * @param requirementHeadList
     * @param requirementLineList
     * @return
     */
    List<LogisticsPurchaseRequirementDTO> buildRequirement(List<LogisticsRequirementHead> requirementHeadList,List<LogisticsRequirementLine> requirementLineList){
        List<LogisticsPurchaseRequirementDTO> result = new LinkedList<>();
        Map<Long,LogisticsRequirementHead> requirementHeadMap = requirementHeadList.stream().collect(Collectors.toMap(item -> item.getRequirementHeadId(),item -> item));
        Map<Long,List<LogisticsRequirementLine>> requirementLineMap = requirementLineList.stream().collect(Collectors.groupingBy(LogisticsRequirementLine::getRequirementHeadId));
        for(Map.Entry<Long,LogisticsRequirementHead> entry : requirementHeadMap.entrySet()){
            LogisticsPurchaseRequirementDTO requirementDTO = new LogisticsPurchaseRequirementDTO();
            Long requirementHeadId = entry.getKey();
            requirementDTO.setRequirementHead(entry.getValue()).setRequirementLineList(requirementLineMap.get(entry.getKey()));
            result.add(requirementDTO);
        }
        return result;
    }

}
