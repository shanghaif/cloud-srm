package com.midea.cloud.srm.base.quotaorder.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.base.quotaorder.QuotaHead;
import com.midea.cloud.srm.model.base.quotaorder.dto.QuotaHeadDto;
import com.midea.cloud.srm.model.base.quotaorder.dto.QuotaLineDto;
import com.midea.cloud.srm.model.base.quotaorder.dto.QuotaParamDto;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
*  <pre>
 *  配额比例头表 服务类
 * </pre>
*
* @author wangpr@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-12-07 17:00:12
 *  修改内容:
 * </pre>
*/
public interface IQuotaHeadService extends IService<QuotaHead> {
    /**
     * 配额分页查询
     * @param quotaHeadDto
     * @return
     */
    PageInfo<QuotaHeadDto> listPage(QuotaHeadDto quotaHeadDto);

    /**
     * 获取详情
     * @param quotaHeadId
     * @return
     */
    QuotaHead get(Long quotaHeadId);

    /**
     * 保存或更新
     * @param quotaHead
     * @return
     */
    Long addOrUpdate(QuotaHead quotaHead);

    /**
     * 根据物料ID和组织ID查找有效的配额
     * @param quotaParamDto
     * @return
     */
    Map<String,QuotaHead> queryQuotaHeadByOrgIdItemId(QuotaParamDto quotaParamDto);

    /**
     * 检查配额数据中的供应商必须在价格库中具备有效价格。
     * @param quotaHeadId
     * @return
     */
    String checkVendorIfValidPrice(Long quotaHeadId, Date requirementDate);

    /**
     * 根据物料ID和组织ID查找有效的配额
     * 配额里的供应商都有有效价格
     * @param quotaParamDto
     * @return
     */
    Map<String,QuotaHead> queryQuotaHeadByOrgIdItemIdAndPriceValid(QuotaParamDto quotaParamDto);

    /**
     * 根据组织+物料+时间 查找配额
     * @param quotaParamDto
     * @return
     */
    QuotaHead getQuotaHeadByOrgIdItemIdDate(QuotaParamDto quotaParamDto);

    /**
     * 创建采购订单回写数据
     * @param quotaLineDtos
     */
    void updateQuotaLineByQuotaLineDtos(List<QuotaLineDto> quotaLineDtos);

    /**
     * 采购订单删除回写数据
     * @param quotaLineDtos
     */
    void rollbackQuotaLineByQuotaLineDtos(List<QuotaLineDto> quotaLineDtos);
}
