package com.midea.cloud.srm.cm.template.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.contract.TemplateStatus;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.DateUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.cm.template.mapper.TemplHeadMapper;
import com.midea.cloud.srm.cm.template.service.ITemplHeadService;
import com.midea.cloud.srm.cm.template.service.ITemplLineService;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.model.cm.template.dto.ContractTemplDTO;
import com.midea.cloud.srm.model.cm.template.dto.TemplHeadQueryDTO;
import com.midea.cloud.srm.model.cm.template.entity.TemplHead;
import com.midea.cloud.srm.model.cm.template.entity.TemplLine;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
*  <pre>
 *  合同模板头表 服务实现类
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-19 08:58:08
 *  修改内容:
 * </pre>
*/
@Service
@Slf4j
public class TemplHeadServiceImpl extends ServiceImpl<TemplHeadMapper, TemplHead> implements ITemplHeadService {

    @Autowired
    ITemplLineService iTemplLineService;

    @Autowired
    FileCenterClient fileCenterClient;

    @Override
    @Transactional
    public void saveContractTemplDTO(ContractTemplDTO contractTemplDTO) {
        TemplHead templHead = contractTemplDTO.getTemplHead();
        List<TemplLine> templLines = contractTemplDTO.getTemplLines();
        List<Fileupload> fileuploads = contractTemplDTO.getFileuploads();

        log.info("合同模板头信息:{}", templHead);
        log.info("合同模板行信息:{}", templLines);
        log.info("附件上传:{}", fileuploads);
        if (templHead != null) {
            long id = IdGenrator.generate();
            templHead.setTemplHeadId(id);
            templHead.setTemplStatus(TemplateStatus.DRAFT.name());
            this.save(templHead);

            if (!CollectionUtils.isEmpty(templLines)) {
                templLines.forEach(templLine -> {
                    templLine.setTemplLineId(IdGenrator.generate());
                    templLine.setTemplHeadId(id);
                    iTemplLineService.save(templLine);
                });
            }

            if (!CollectionUtils.isEmpty(fileuploads)) {
                fileCenterClient.bindingFileupload(fileuploads, id);
            }
        }
    }

    @Override
    @Transactional
    public void updateContractTemplDTO(ContractTemplDTO contractTemplDTO) {
        TemplHead templHead = contractTemplDTO.getTemplHead();
        List<TemplLine> templLines = contractTemplDTO.getTemplLines();
        List<Fileupload> fileuploads = contractTemplDTO.getFileuploads();

        log.info("合同模板头信息:{}", templHead);
        log.info("合同模板行信息:{}", templLines);
        log.info("附件上传:{}", fileuploads);
        if (templHead != null) {
            this.updateById(templHead);

            if (!CollectionUtils.isEmpty(templLines)) {
                templLines.forEach(templLine -> {
                    iTemplLineService.updateById(templLine);
                });
            }

            if (!CollectionUtils.isEmpty(fileuploads)) {
                fileCenterClient.bindingFileupload(fileuploads, templHead.getTemplHeadId());
            }
        }
    }

    @Override
    public ContractTemplDTO getContractTemplDTO(Long templHeadId) {
        TemplHead templHead = this.getById(templHeadId);
        List<TemplLine> templLines = iTemplLineService.list(
                new QueryWrapper<TemplLine>(new TemplLine().setTemplHeadId(templHeadId)));
        List<Fileupload> fileuploads = fileCenterClient.listPage(new Fileupload().setBusinessId(templHeadId), null).getList();
        ContractTemplDTO contractTemplDTO = new ContractTemplDTO();
        contractTemplDTO.setTemplHead(templHead);
        contractTemplDTO.setTemplLines(templLines);
        contractTemplDTO.setFileuploads(fileuploads);
        return contractTemplDTO;
    }

    @Override
    public PageInfo<TemplHead> listPageByParm(TemplHeadQueryDTO templHeadQueryDTO) {
        PageUtil.startPage(templHeadQueryDTO.getPageNum(), templHeadQueryDTO.getPageSize());
        List<TemplHead> templHeads =this.baseMapper.listPageByParm(templHeadQueryDTO);
        return new PageInfo<>(templHeads);

    }

    @Override
    public void effective(Long templHeadId) {
        Assert.notNull(templHeadId, "templHeadId不能为空");
        ContractTemplDTO contractTemplDTO = this.getContractTemplDTO(templHeadId);
        if (contractTemplDTO != null) {
            List<TemplLine> templLines = contractTemplDTO.getTemplLines();
            TemplHead templHead = contractTemplDTO.getTemplHead();
            //生效前需要校验
            checkBeforeEffective(templHead, templLines);
            if (!TemplateStatus.EFFECTIVE.name().equals(templHead.getTemplStatus())) {
                templHead.setStartDate(LocalDate.now())
                        .setTemplStatus(TemplateStatus.EFFECTIVE.name());
                this.updateById(templHead);
            }
        }
    }

    @Override
    @Transactional
    public void deleteContractTemplDTO(Long templHeadId) {
        Assert.notNull(templHeadId, "templHeadId不能为空");
        this.removeById(templHeadId);
        iTemplLineService.remove(new QueryWrapper<>(new TemplLine().setTemplHeadId(templHeadId)));
        fileCenterClient.deleteByParam(new Fileupload().setBusinessId(templHeadId));
    }

    @Override
    public void invalid(TemplHead templHead) {
        Long templHeadId = templHead.getTemplHeadId();
        Assert.notNull(templHeadId, "templHeadId不能为空");
        TemplHead existTemplHead = this.getById(templHeadId);
        if (existTemplHead != null
                && !TemplateStatus.INVALID.name().equals(existTemplHead.getTemplStatus())) {
            templHead.setTemplStatus(TemplateStatus.INVALID.name());
            templHead.setEndDate(LocalDate.now());
            this.updateById(templHead);
        }
    }

    @Override
    public List<TemplHead> listEffectiveTempl() {
        return this.list(new QueryWrapper<>(new TemplHead().setTemplStatus(TemplateStatus.EFFECTIVE.name())));
    }

    @Override
    @Transactional
    public void copy(Long templHeadId) {
        TemplHead copyTemplHead = this.getById(templHeadId);
        List<TemplLine> templLines = iTemplLineService.list(new QueryWrapper<>(new TemplLine().setTemplHeadId(templHeadId)));
        if (copyTemplHead != null) {
            TemplHead templHead = new TemplHead();
            BeanUtils.copyProperties(copyTemplHead, templHead);
            long headId = IdGenrator.generate();
            templHead.setTemplStatus(TemplateStatus.DRAFT.name())
                    .setTemplHeadId(headId)
                    .setStartDate(null)
                    .setEndDate(null)
                    .setInvalidReason(null);
            this.save(templHead);

            if (!CollectionUtils.isEmpty(templLines)) {
                templLines.forEach(templLine -> {
                    templLine.setTemplLineId(IdGenrator.generate())
                            .setTemplHeadId(headId)
                            .setStartDate(null)
                            .setEndDate(null);
                    iTemplLineService.save(templLine);
                });
            }
        }
    }

    private void checkBeforeEffective(TemplHead templHead, List<TemplLine> templLines) {
        Assert.notNull(templHead, "templHead不能为空");
        Assert.notEmpty(templLines, "templLines不能为空");
        Assert.hasText(templHead.getTemplType(), "模板类型为空");
        Assert.hasText(templHead.getTemplType(), "模板名称为空");
        Assert.hasText(templHead.getOwner(), "甲方为空");
        Assert.hasText(templHead.getPhone(), "电话为空");
        Assert.hasText(templHead.getPostcode(), "邮编为空");
        Assert.hasText(templHead.getOpeningBank(), "开户行为空");
        Assert.hasText(templHead.getBankAccount(), "账号为空");
        Assert.hasText(templHead.getLegalPerson(), "法定代表人为空");
        Assert.hasText(templHead.getEntrustedAgent(), "委托代理人为空");
        templLines.forEach(templLine -> {
            if (StringUtils.isBlank(templLine.getContractItem())
                    && StringUtils.isBlank(templLine.getTemplContent())) {
                throw new BaseException("合同条款项与合同内容同时为空");
            }
        });
    }
}
