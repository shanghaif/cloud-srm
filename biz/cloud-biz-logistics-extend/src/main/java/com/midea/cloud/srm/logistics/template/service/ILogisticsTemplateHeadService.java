package com.midea.cloud.srm.logistics.template.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.logistics.template.dto.LogisticsTemplateDTO;
import com.midea.cloud.srm.model.logistics.template.entity.LogisticsTemplateHead;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
*  <pre>
 *  物流采购申请模板头表 服务类
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-11-27 16:25:07
 *  修改内容:
 * </pre>
*/
public interface ILogisticsTemplateHeadService extends IService<LogisticsTemplateHead> {

    /**
     * 条件查询
     * @param logisticsTemplateHead
     */
    List<LogisticsTemplateHead> listByParam(@RequestBody LogisticsTemplateHead logisticsTemplateHead);

    /**
     * 保存或更新物流模板头行
     * @param logisticsTemplateDTO
     * @return
     */
    Long saveOrUpdateTemplateDTO(LogisticsTemplateDTO logisticsTemplateDTO, String status);

    /**
     * 根据模板头id删除
     * @param headId
     */
    void deleteByHeadId(Long headId);

    /**
     * 更新模模板头状态
     * @param headId
     * @param status
     */
    void updateTemplateHeadStatus(Long headId, String status);

    /**
     * 根据头id获取模板头行
     * @param headId
     * @return
     */
    LogisticsTemplateDTO listTemplateDTOByHeadId(Long headId);

    /**
     * 批量删除采购申请模板
     * @param headIds
     */
    void batchDelete(List<Long> headIds);
}
