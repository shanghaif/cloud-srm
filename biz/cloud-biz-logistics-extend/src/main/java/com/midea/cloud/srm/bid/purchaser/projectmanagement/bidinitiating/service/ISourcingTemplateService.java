package com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidinitiating.entity.SourcingTemplate;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidinitiating.vo.SourceForm;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidinitiating.vo.SourcingTemplateQueryParameter;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;

import java.util.List;

/**
 * 寻源模板服务
 *
 * @author zixuan.yan@meicloud.com
 */
public interface ISourcingTemplateService {

    /**
     * 获取 寻源模板集（不包含模板数据）
     *
     * @param queryParameter 查询参数
     * @param pageNo         当前页码
     * @param pageSize       分页大小
     * @return 寻源模板集
     */
    PageInfo<SourcingTemplate> findSourcingTemplatesWithoutTemplateData(SourcingTemplateQueryParameter queryParameter,
                                                                        int pageNo, int pageSize);

    /**
     * 获取 寻源模板
     *
     * @param id 寻源模板ID
     * @return 寻源模板
     */
    SourcingTemplate findSourcingTemplate(Long id);

    /**
     * 获取 寻源模板数据
     *
     * @param id 寻源模板ID
     * @return 寻源模板数据
     */
    SourceForm findSourcingTemplateData(Long id);

    /**
     * 起草 寻源模板
     *
     * @param sourcingTemplate 寻源模板
     * @return 寻源模板
     */
    SourcingTemplate draftSourcingTemplate(SourcingTemplate sourcingTemplate);

    /**
     * 生效 寻源模板集
     *
     * @param ids 寻源模板ID集
     */
    void validSourcingTemplates(Long[] ids);

    /**
     * 失效 寻源模板集
     *
     * @param ids 寻源模板ID集
     */
    void inValidSourcingTemplates(Long[] ids);

    /**
     * 删除 寻源模板集
     *
     * @param ids 寻源模板ID集
     */
    void deleteSourcingTemplates(Long[] ids);

    /**
     * 生成寻源单
     *
     * @param id 寻源模板ID
     * @return 寻源单
     */
    Object generateSourceForm(Long id);

    /**
     * 获取推荐的供应商
     */
    List<CompanyInfo> findVendors(Long templateId);


    List findQuoteAuthorizes(Long vendorId, Long templateId);
}
