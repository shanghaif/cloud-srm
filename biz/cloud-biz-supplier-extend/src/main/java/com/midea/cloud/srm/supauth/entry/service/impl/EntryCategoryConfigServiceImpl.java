package com.midea.cloud.srm.supauth.entry.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseCategory;
import com.midea.cloud.srm.model.supplierauth.entry.dto.EntryCategoryConfigSaveResultDTO;
import com.midea.cloud.srm.model.supplierauth.entry.entity.EntryCategoryConfig;
import com.midea.cloud.srm.model.supplierauth.entry.entity.EntryConfig;
import com.midea.cloud.srm.supauth.entry.mapper.EntryCategoryConfigMapper;
import com.midea.cloud.srm.supauth.entry.service.IEntryCategoryConfigService;
import com.midea.cloud.srm.supauth.entry.service.IEntryConfigService;

import lombok.extern.slf4j.Slf4j;

/**
*  <pre>
 *  供应商准入流程行表（品类配置） 服务实现类
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
@Service
@Slf4j
public class EntryCategoryConfigServiceImpl extends ServiceImpl<EntryCategoryConfigMapper, EntryCategoryConfig> implements IEntryCategoryConfigService {

    @Resource
    private IEntryConfigService iEntryConfigService;

    @Autowired
    private BaseClient baseClient;

    /**
     * 为选取的品类项生成主键ID
     * @param entryCategoryList
     * @return
     */
    @Override
    public List saveEntryCategoryConfig(List<EntryCategoryConfig> entryCategoryList) {
        List<EntryCategoryConfig> entryCategoryConfigList = new ArrayList<>();
        for (EntryCategoryConfig entryCategoryConfig : entryCategoryList){
            if (entryCategoryConfig != null){
                if (entryCategoryConfig.getEntryCategoryConfigId() == null) {
                    Long id = IdGenrator.generate();
                    entryCategoryConfig.setEntryCategoryConfigId(id);
                    entryCategoryConfigList.add(entryCategoryConfig);
                }
                else {
                    entryCategoryConfigList.add(entryCategoryConfig);
                }
            }
        }
        return entryCategoryConfigList;
    }

    /**
     * 根据头表Id查询维护品类列表
     * @param entryConfigId
     * @return
     */
    @Override
    public PageInfo<EntryCategoryConfig> getCategoryListByEntryId(Long entryConfigId) {
        QueryWrapper<EntryCategoryConfig> categoryWrapper = new QueryWrapper<>();
        categoryWrapper.eq("ENTRY_CONFIG_ID", entryConfigId);
        return new PageInfo<>(this.list(categoryWrapper));
    }

    /**
     * 根据传入的quaReviewType，entryId，categoryConfigList
     * 将这个准入类型维护的品类保存到行表
     * 默认，前端传回的categoryConfigList已经按照categoryId实现了去重
     * @param categoryConfigList
     * @param entryId
     * @param quaReviewType
     */
    @Override
    public EntryCategoryConfigSaveResultDTO saveEntryCategoryList(List<EntryCategoryConfig> categoryConfigList, Long entryId, String quaReviewType) {
        //entryId为空，即之前没有这个供方准入类型的品类维护
        boolean existRepeatCategory = false;
        if (entryId == null) {
            //首先生成一个头表Id
            Long generatedEntryId = IdGenrator.generate();
            EntryConfig saveEntry = new EntryConfig();
            saveEntry.setEntryConfigId(generatedEntryId);

            List<EntryCategoryConfig> saveCategoryList = new ArrayList<>();
            for (EntryCategoryConfig categoryConfig : categoryConfigList) {
                //生成行表的Id
                Long generatedCategoryId = IdGenrator.generate();
                categoryConfig.setEntryCategoryConfigId(generatedCategoryId);
                //设置头表Id
                categoryConfig.setEntryConfigId(generatedEntryId);
                saveCategoryList.add(categoryConfig);
            }
            if (quaReviewType != null){
                saveEntry.setQuaReviewType(quaReviewType);
            }
            saveEntry.setEntryCategoryConfigList(categoryConfigList);
            iEntryConfigService.save(saveEntry);
            this.saveBatch(saveCategoryList);
        }

        //否则，说明已经存在这个供方准入类型的部分品类维护
        //需要进行重叠校验
        else{
            QueryWrapper<EntryCategoryConfig> categoryWrapper = new QueryWrapper<>();
            //先删除当前头行对应的品类 集合
            categoryWrapper.eq("ENTRY_CONFIG_ID", entryId);
            this.remove(categoryWrapper);

            //查询到当前的供方准入类型所维护的所有的品类集合
            Set<Long> categorySet = getCategorySetByType(quaReviewType);

            List<EntryCategoryConfig> saveOrUpdateCategoryList = new ArrayList<>();
            //前端传回的 品类列表 categoryConfigList，已经实现了去重
            //选中的传回的品类，只有之前未维护的，才进行保存

            for (EntryCategoryConfig entryCategoryConfig : categoryConfigList) {
                if (entryCategoryConfig != null && entryCategoryConfig.getCategoryId() != null) {
                    if (!categorySet.contains(entryCategoryConfig.getCategoryId())) {
                        Long generatedCategoryId = IdGenrator.generate();
                        entryCategoryConfig.setEntryCategoryConfigId(generatedCategoryId);
                        entryCategoryConfig.setEntryConfigId(entryId);
                        saveOrUpdateCategoryList.add(entryCategoryConfig);
                    }else {
                        existRepeatCategory = true;
                    }
                }
            }
            this.saveBatch(saveOrUpdateCategoryList);
        }
        EntryCategoryConfigSaveResultDTO resultDTO = new EntryCategoryConfigSaveResultDTO();
        resultDTO.setCategoryListSaveStatus(existRepeatCategory);
        return resultDTO;
    }

    @Override
    public List<Long> getCategoryIdListByEntryConfigId(Long entryConfigId) {
        Assert.notNull(entryConfigId, "entryConfigId为空！");
        return this.getBaseMapper().getCategoryIdListByEntryConfigId(entryConfigId);
    }

    /**
     * 导数据用（请忽略）
     */
    @Override
    public void importEntryCategoryConfig() {
        //查询所有小类
        List<PurchaseCategory> minLevelCategoryList = baseClient.listParamCategoryMin();
        Map<String, PurchaseCategory> map = minLevelCategoryList.stream().filter(x -> !StringUtils.isEmpty(x.getCategoryName()))
                .collect(Collectors.toMap(k -> k.getCategoryName(), part -> part, (k1, k2)->k2));
        //查询所有配置行
        List<EntryCategoryConfig> entryCategoryConfigList = this.list();
        entryCategoryConfigList.forEach(entryCategoryConfig -> {
            if (!StringUtils.isEmpty(entryCategoryConfig.getCategoryName())){
                if (map.containsKey(entryCategoryConfig.getCategoryName()) && Objects.nonNull(map.get(entryCategoryConfig.getCategoryName()))){
                    PurchaseCategory purchaseCategory = map.get(entryCategoryConfig.getCategoryName());
                    entryCategoryConfig.setCategoryId(purchaseCategory.getCategoryId())
                            .setCategoryCode(purchaseCategory.getCategoryCode());
                }
            }
        });
        log.info("更新准入配置行表数据："+entryCategoryConfigList.size()+"条");
        this.updateBatchById(entryCategoryConfigList);
    }


    /**
     * 根据传入的quaReviewType查询所维护的品类集合
     * @param quaReviewType
     * @return Set 当前所选的供方准入类型下 所维护的所有品类集合
     */
    public Set<Long> getCategorySetByType(String quaReviewType) {
        Assert.notNull(quaReviewType, "准入类型quaReviewType不能为空！");
        //查询在这个供方准入类型下 当前已经存在的EntryConfig List
        QueryWrapper<EntryConfig> entryWrapper = new QueryWrapper<>();
        entryWrapper.eq("QUA_REVIEW_TYPE", quaReviewType);
        List<EntryConfig> existEntryConfigList = iEntryConfigService.list(entryWrapper);

        //用于判断维护的品类是否有重复
        Set<Long> categoryIdSet = new HashSet<>();

        //获取当前已经维护的所有品类的 List
        if (!existEntryConfigList.isEmpty()){
            /** 遍历头表元素 开始 */
            for (EntryConfig config : existEntryConfigList) {
                QueryWrapper<EntryCategoryConfig> categoryWrapper = new QueryWrapper<>();
                categoryWrapper.eq("ENTRY_CONFIG_ID", config.getEntryConfigId());
                List<EntryCategoryConfig> categoryList = this.list(categoryWrapper);
                /** 将每个头表对应的品类List的每个元素加入到Set集合 开始 */
                if (!categoryList.isEmpty()) {
                    for (EntryCategoryConfig entryCategoryConfig : categoryList) {
                        categoryIdSet.add(entryCategoryConfig.getCategoryId());
                    }
                }
                /** 将每个头表对应的品类List的每个元素加入到Set集合 结束 */
            }
            /** 遍历头表元素 结束 */
        }
        return categoryIdSet;
    }

}
