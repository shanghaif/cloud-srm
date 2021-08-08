package com.midea.cloud.srm.supauth.entry.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.supplierauth.entry.entity.EntryCategoryConfig;
import com.midea.cloud.srm.model.supplierauth.entry.entity.EntryConfig;

/**
*  <pre>
 *  供应商准入流程 服务类
 * </pre>
*
* @author chensl26@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-05 13:54:48
 *  修改内容:
 * </pre>
*/
public interface IEntryConfigService extends IService<EntryConfig> {

    /**
     * 分页条件查询
     * @param entryConfig
     * @return
     */
    List<EntryConfig> listPageByParam(EntryConfig entryConfig);

    /**
     * 编辑或保存
     * @param entryConfig
     */
    void saveOrUpdateEntryConfig(EntryConfig entryConfig);

    /**
     * 根据供方准入类型获取引入流程配置对象数据
     * @param quaReviewType
     * @return EntryConfig
     */
    EntryConfig getEntryConfigByQuaReviewType(String quaReviewType);

    /**
     * 根据供方准入类型获取引入流程配置对象数据(ceea)
     * @param quaReviewType
     * @return
     */
    List<EntryConfig> listEntryConfigByQuaReviewType(String quaReviewType);

    /**
     * 根据供方准入Id查询维护的品类列表
     * @param entryConfigId
     * @return
     */
    List<EntryCategoryConfig> listEntryCategoryConfig(Long entryConfigId);

    /**
     * 保存或更新
     * @param entryConfig
     * @return
     */
    void saveOrUpdateEntryCategoryConfig(EntryConfig entryConfig);

    /**
     * 根据头表Id删除
     * @param entryConfigId
     * @return
     */
    void deleteEntryConfig(Long entryConfigId);

    /**
     * 根据勾选是否更新
     * @param entryConfig
     * @return
     */
    void updateByParam(EntryConfig entryConfig);

    /**
     * 根据供方准入类型和品类Id查询对应的准入配置EntryConfig
     *
     */
    EntryConfig getEntryConfigByTypeAndCategoryId(String type, Long categoryId);

    /**
     * 批量配置（导入用，请忽略）
     * @modified xiexh12@meicloud.com
     */
    void importEntryConfigs();

    /**
     * 批量配置（导入数据用，请忽略）
     * @modifiedBy xiexh12@meicloud.com 10-07 16:38
     */
    void importEntryConfigNewVendor();

}
