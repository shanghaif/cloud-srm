package com.midea.cloud.srm.logistics.template.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.constants.SequenceCodeConstant;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.enums.logistics.LogisticsStatus;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.logistics.template.mapper.LogisticsTemplateHeadMapper;
import com.midea.cloud.srm.logistics.template.service.ILogisticsTemplateHeadService;
import com.midea.cloud.srm.logistics.template.service.ILogisticsTemplateLineService;
import com.midea.cloud.srm.model.logistics.template.dto.LogisticsTemplateDTO;
import com.midea.cloud.srm.model.logistics.template.entity.LogisticsTemplateHead;
import com.midea.cloud.srm.model.logistics.template.entity.LogisticsTemplateLine;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <pre>
 *  物流采购申请模板头表 服务实现类
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-11-27 16:25:07
 *  修改内容:
 * </pre>
 */
@Service
public class LogisticsTemplateHeadServiceImpl extends ServiceImpl<LogisticsTemplateHeadMapper, LogisticsTemplateHead> implements ILogisticsTemplateHeadService {

    @Autowired
    private ILogisticsTemplateLineService iLogisticsTemplateLineService;

    @Autowired
    private BaseClient baseClient;

    /**
     * 条件查询
     *
     * @param logisticsTemplateHead
     */
    @Override
    public List<LogisticsTemplateHead> listByParam(LogisticsTemplateHead logisticsTemplateHead) {
        List<LogisticsTemplateHead> logisticsTemplateHeadList = this.list(Wrappers.lambdaQuery(LogisticsTemplateHead.class)
                .like(StringUtils.isNotEmpty(logisticsTemplateHead.getTemplateCode()), LogisticsTemplateHead::getTemplateCode, logisticsTemplateHead.getTemplateCode())
                .like(StringUtils.isNotEmpty(logisticsTemplateHead.getTemplateName()), LogisticsTemplateHead::getTemplateName, logisticsTemplateHead.getTemplateName())
                .eq(StringUtils.isNotEmpty(logisticsTemplateHead.getBusinessModeCode()), LogisticsTemplateHead::getBusinessModeCode, logisticsTemplateHead.getBusinessModeCode())
                .eq(StringUtils.isNotEmpty(logisticsTemplateHead.getTransportModeCode()), LogisticsTemplateHead::getTransportModeCode, logisticsTemplateHead.getTransportModeCode())
                .eq(StringUtils.isNotEmpty(logisticsTemplateHead.getStatus()), LogisticsTemplateHead::getStatus, logisticsTemplateHead.getStatus())
                .like(StringUtils.isNotEmpty(logisticsTemplateHead.getCreatedBy()), LogisticsTemplateHead::getCreatedBy, logisticsTemplateHead.getCreatedBy())
                .orderByDesc(LogisticsTemplateHead::getLastUpdateDate)
        );
        return logisticsTemplateHeadList;
    }

    /**
     * 保存或更新物流模板头行
     *
     * @param logisticsTemplateDTO
     * @param status
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long saveOrUpdateTemplateDTO(LogisticsTemplateDTO logisticsTemplateDTO, String status) {
        LogisticsTemplateHead head = logisticsTemplateDTO.getTemplateHead();
        List<LogisticsTemplateLine> lines = logisticsTemplateDTO.getTemplateLines();
        // 模板行校验
        checkTemplateLines(lines);

        // 处理模板头
        head.setStatus(status);
        if (null == head.getTemplateHeadId()) {
            head.setTemplateHeadId(IdGenrator.generate()).setTemplateCode(baseClient.seqGen(SequenceCodeConstant.SEQ_LOGISTICS_TEMPLATE));
            this.save(head);
        } else {
            this.updateById(head);
        }

        // 处理模板行
        if (CollectionUtils.isNotEmpty(lines)) {
            List<LogisticsTemplateLine> updateLines = new ArrayList<>();
            List<LogisticsTemplateLine> saveLines = new ArrayList<>();
            lines.forEach(line -> {
                // 设置头id
                if (null == line.getHeadId()) {
                    line.setHeadId(head.getTemplateHeadId());
                }
                // 行id处理
                if (null == line.getTemplateLineId()) {
                    line.setTemplateLineId(IdGenrator.generate());
                    saveLines.add(line);
                } else {
                    updateLines.add(line);
                }
            });
            if (CollectionUtils.isNotEmpty(updateLines)) {
                iLogisticsTemplateLineService.updateBatchById(updateLines);
            }
            if (CollectionUtils.isNotEmpty(saveLines)) {
                iLogisticsTemplateLineService.saveBatch(saveLines);
            }
        }

        return head.getTemplateHeadId();
    }

    /**
     * 模板行校验
     * 只有选了可操作, 才能勾选必填
     * @param lines
     */
    private void checkTemplateLines(List<LogisticsTemplateLine> lines) {
        if (CollectionUtils.isNotEmpty(lines)) {
            for (LogisticsTemplateLine line : lines) {
                if (YesOrNo.NO.getValue().equals(line.getApplyOperateFlag()) && YesOrNo.YES.getValue().equals(line.getApplyNotEmptyFlag())) {
                    StringBuffer sb = new StringBuffer();
                    sb.append("模板字段[").append(line.getFieldName()).append("]申请可操作未勾选, 请先勾选申请可操作, 再勾选申请必填.");
                    throw new BaseException(LocaleHandler.getLocaleMsg(sb.toString()));
                }
                if (YesOrNo.NO.getValue().equals(line.getPurchaseOperateFlag()) && YesOrNo.YES.getValue().equals(line.getPurchaseNotEmptyFlag())) {
                    StringBuffer sb = new StringBuffer();
                    sb.append("模板字段[").append(line.getFieldName()).append("]采购可操作未勾选, 请先勾选采购可操作, 再勾选采购方必填.");
                    throw new BaseException(LocaleHandler.getLocaleMsg(sb.toString()));
                }
                if (YesOrNo.NO.getValue().equals(line.getVendorOperateFlag()) && YesOrNo.YES.getValue().equals(line.getVendorNotEmptyFlag())) {
                    StringBuffer sb = new StringBuffer();
                    sb.append("模板字段[").append(line.getFieldName()).append("]供应商可操作未勾选, 请先勾选供应商可操作, 再勾选供应商必填.");
                    throw new BaseException(LocaleHandler.getLocaleMsg(sb.toString()));
                }
            }
        }
    }

    /**
     * 根据模板头id删除
     * 先删除模板行, 再删除模板头
     *
     * @param headId
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteByHeadId(Long headId) {
        iLogisticsTemplateLineService.remove(Wrappers.lambdaQuery(LogisticsTemplateLine.class)
                .eq(LogisticsTemplateLine::getHeadId, headId));
        this.removeById(headId);
    }

    /**
     * 更新模板头状态
     *
     * @param headId
     * @param status
     */
    @Override
    public void updateTemplateHeadStatus(Long headId, String status) {
        LogisticsTemplateHead head = this.getById(headId);
        head.setStatus(status);
        this.updateById(head);
    }

    /**
     * 根据头id获取模板头行
     *
     * @param headId
     * @return
     */
    @Override
    public LogisticsTemplateDTO listTemplateDTOByHeadId(Long headId) {
        LogisticsTemplateHead head = this.getById(headId);
        LogisticsTemplateDTO dto = new LogisticsTemplateDTO();
        dto.setTemplateHead(head);

        List<LogisticsTemplateLine> lines = iLogisticsTemplateLineService.list(Wrappers.lambdaQuery(LogisticsTemplateLine.class)
                .eq(LogisticsTemplateLine::getHeadId, headId));
        dto.setTemplateLines(lines);
        return dto;
    }

    /**
     * 批量删除采购申请模板
     * @param headIds
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(List<Long> headIds) {
        //校验
        if(CollectionUtils.isEmpty(headIds)){
            throw new BaseException(LocaleHandler.getLocaleMsg("请传递必要参数"));
        }
        List<LogisticsTemplateHead> templateHeadList = this.listByIds(headIds);
        for(LogisticsTemplateHead logisticsTemplateHead : templateHeadList){
            if(!LogisticsStatus.DRAFT.getValue().equals(logisticsTemplateHead.getStatus())){
                throw new BaseException(LocaleHandler.getLocaleMsg("只有拟定状态才可以删除"));
            }
        }

        //批量删除模板头
        this.removeByIds(headIds);
        //批量删除模板行
        QueryWrapper<LogisticsTemplateLine> wrapper = new QueryWrapper<>();
        wrapper.in("HEAD_ID",headIds);
        List<LogisticsTemplateLine> templateLineList = iLogisticsTemplateLineService.list(wrapper);
        List<Long> templateLineIds = templateLineList.stream().map(item -> item.getTemplateLineId()).collect(Collectors.toList());
        iLogisticsTemplateLineService.removeByIds(templateLineIds);
    }
}
