package com.midea.cloud.srm.supauth.entry.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.supplierauth.entry.dto.EntryCategoryConfigSaveResultDTO;
import com.midea.cloud.srm.model.supplierauth.entry.entity.EntryCategoryConfig;

/**
*  <pre>
 *  供应商准入流程行表（品类配置） 服务类
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-15 11:28:48
 *  修改内容:
 * </pre>
*/
public interface IEntryCategoryConfigService extends IService<EntryCategoryConfig> {

    List<EntryCategoryConfig> saveEntryCategoryConfig(List<EntryCategoryConfig> entryCategoryList);

    PageInfo<EntryCategoryConfig> getCategoryListByEntryId(Long entryConfigId);

    /**
     * 根据传入的quaReviewType，entryId，categoryConfigList
     * 将这个准入类型维护的品类保存到行表
     * @param categoryConfigList
     * @param entryId
     * @param quaReviewType
     */
    EntryCategoryConfigSaveResultDTO saveEntryCategoryList(List<EntryCategoryConfig> categoryConfigList, Long entryId, String quaReviewType);

    /**
     * 根据entryConfigId查询对应的categoryId列表
     * @param entryConfigId
     * @return List<Long> categoryIdList
     */
    List<Long> getCategoryIdListByEntryConfigId(Long entryConfigId);

    /**
     * 导数据用（请忽略）
     */
    void importEntryCategoryConfig();
}
