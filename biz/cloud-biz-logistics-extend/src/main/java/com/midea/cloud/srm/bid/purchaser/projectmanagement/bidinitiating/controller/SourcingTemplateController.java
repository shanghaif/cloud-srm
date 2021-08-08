package com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service.ISourcingTemplateService;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidinitiating.entity.SourcingTemplate;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidinitiating.vo.SourceForm;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidinitiating.vo.SourcingTemplateQueryParameter;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Http接口 - 寻源模板
 *
 * @author zixuan.yan@meicloud.com
 */
@RestController
@RequestMapping("/bidInitiating/sourcingTemplate")
public class SourcingTemplateController {

    @Resource
    private ISourcingTemplateService    sourcingTemplateService;


    @PostMapping("/findSourcingTemplatesWithoutTemplateData")
    public PageInfo<SourcingTemplate> findSourcingTemplatesWithoutTemplateData(@RequestBody SourcingTemplateQueryParameter queryParameter) {
        return sourcingTemplateService.findSourcingTemplatesWithoutTemplateData(queryParameter, queryParameter.getPageNum(), queryParameter.getPageSize());
    }

    @GetMapping("/findSourcingTemplate")
    public SourcingTemplate findSourcingTemplate(@RequestParam("id") Long id) {
        return sourcingTemplateService.findSourcingTemplate(id);
    }

    @GetMapping("/findSourcingTemplateData")
    public SourceForm findSourcingTemplateData(Long id) {
        return sourcingTemplateService.findSourcingTemplateData(id);
    }

    @PostMapping("/draftSourcingTemplate")
    public SourcingTemplate draftSourcingTemplate(@RequestBody SourcingTemplate sourcingTemplate) {
        return sourcingTemplateService.draftSourcingTemplate(sourcingTemplate);
    }

    @PostMapping("/validSourcingTemplates")
    public void validSourcingTemplate(Long[] ids) {
        sourcingTemplateService.validSourcingTemplates(ids);
    }

    @PostMapping("/inValidSourcingTemplates")
    public void inValidSourcingTemplate(Long[] ids) {
        sourcingTemplateService.inValidSourcingTemplates(ids);
    }

    @DeleteMapping("/deleteSourcingTemplates")
    public void deleteSourcingTemplates(Long[] ids) {
        sourcingTemplateService.deleteSourcingTemplates(ids);
    }

    @PostMapping("/generateSourceForm")
    public Object generateSourceForm(Long id) {
        return sourcingTemplateService.generateSourceForm(id);
    }

    @GetMapping("/findVendorsByTemplateId")
    public List<CompanyInfo> findVendorsByTempalteId(@RequestParam("templateId")Long templateId){
        return sourcingTemplateService.findVendors(templateId);
    }
    @GetMapping("/findQuoteAuthorizes")
    public List<Object> findQuoteAuthorizes(@RequestParam("vendorId")Long vendorId,@RequestParam("templateId")Long templateId){
        return sourcingTemplateService.findQuoteAuthorizes(vendorId, templateId);
    }
}
