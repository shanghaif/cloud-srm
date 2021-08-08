package com.midea.cloud.srm.supauth.entry.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.constants.SequenceCodeConstant;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.model.base.configguide.entity.ConfigGuide;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseCategory;
import com.midea.cloud.srm.model.supplierauth.entry.entity.EntryCategoryConfig;
import com.midea.cloud.srm.model.supplierauth.entry.entity.EntryConfig;
import com.midea.cloud.srm.supauth.entry.mapper.EntryConfigMapper;
import com.midea.cloud.srm.supauth.entry.service.IEntryCategoryConfigService;
import com.midea.cloud.srm.supauth.entry.service.IEntryConfigService;

/**
*  <pre>
 *  供应商准入流程 服务实现类
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
@Service
public class EntryConfigServiceImpl extends ServiceImpl<EntryConfigMapper, EntryConfig> implements IEntryConfigService {

    @Autowired
    private EntryConfigMapper entryConfigMapper;

    @Autowired
    private BaseClient baseClient;

    @Resource
    private IEntryCategoryConfigService iEntryCategoryConfigService;

    @Override
    public List<EntryConfig> listPageByParam(EntryConfig entryConfig) {
        PageUtil.startPage(entryConfig.getPageNum(), entryConfig.getPageSize());
        QueryWrapper<EntryConfig> queryWrapper = new QueryWrapper<>();
        //供方准入类型条件查询
        queryWrapper.eq(entryConfig.getQuaReviewType() != null, "ssec.QUA_REVIEW_TYPE", entryConfig.getQuaReviewType());
        queryWrapper.eq(entryConfig.getCategoryId() != null, "ssecc.CATEGORY_ID", entryConfig.getCategoryId());
        queryWrapper.groupBy("ssec.ENTRY_CONFIG_ID");
        queryWrapper.orderByDesc("LAST_UPDATE_DATE");
        return (entryConfigMapper.listPageByParam(queryWrapper));
    }

    @Override
    public void saveOrUpdateEntryConfig(EntryConfig entryConfig) {
        Assert.notNull(entryConfig, "entryConfig不能为空");
        Assert.hasText(entryConfig.getQuaReviewType(), "供方准入类型不能为空");
        Assert.hasText(entryConfig.getAccessProcess(), "准入流程不能为空");
        Assert.hasText(entryConfig.getTrialProcess(), "样品试用流程不能为空");
        if (entryConfig.getEntryConfigId() == null) {
            Long id = IdGenrator.generate();
            entryConfig.setEntryConfigId(id);
        }
        try {
            this.saveOrUpdate(entryConfig);
        } catch (Exception e) {
            log.error("操作失败",e);
            throw new BaseException("供方准入类型不能重复");
        }
        baseClient.saveOrUpdateConfigGuide(new ConfigGuide().setFlowConfig(YesOrNo.YES.getValue()));
    }

    @Override
    public EntryConfig getEntryConfigByQuaReviewType(String quaReviewType) {
        Assert.hasText(quaReviewType, "供方准入类型不能为空");
        QueryWrapper<EntryConfig> queryWrapper = new QueryWrapper<>(
                new EntryConfig().setQuaReviewType(quaReviewType));
        EntryConfig entryConfig = entryConfigMapper.selectOne(queryWrapper);
        if (entryConfig == null) {
            throw new BaseException("根据供方准入类型未找到有效的引入流程配置");
        }
        return entryConfig;
    }

    @Override
    public List<EntryConfig> listEntryConfigByQuaReviewType(String quaReviewType) {
        List<EntryConfig> entryConfigs = this.list(new QueryWrapper<>(new EntryConfig().setQuaReviewType(quaReviewType)));
        return entryConfigs;
    }

    @Override
    public List<EntryCategoryConfig> listEntryCategoryConfig(Long entryConfigId) {
        QueryWrapper categoryConfigWrapper = new QueryWrapper<>();
        categoryConfigWrapper.eq("ENTRY_CONFIG_ID", entryConfigId);
        List<EntryCategoryConfig> entryCategoryConfigList = iEntryCategoryConfigService.list(categoryConfigWrapper);
        return entryCategoryConfigList;
    }

    @Override
    @Transactional
    public void saveOrUpdateEntryCategoryConfig(EntryConfig entryConfig) {
        Set<Long> entrySet = getCategorySetByType(entryConfig);

        Long entryId = entryConfig.getEntryConfigId();

        //1.1 如果头的Id为空（即为新增），先生成头Id 开始=====
        if (entryId == null) {
            Long generatedEntryId = IdGenrator.generate();
            entryConfig.setEntryConfigId(generatedEntryId);
            //1.2 获取维护的品类列表
            List<EntryCategoryConfig> categoryList = entryConfig.getEntryCategoryConfigList();
            Assert.isTrue(!ifCategoryMaintained(entrySet, categoryList), "选中的品类存在已被维护的品类！");
            //用于保存或更新的CategoryList
            List<EntryCategoryConfig> saveOrUpdateCategoryList = new ArrayList<>();
            for (EntryCategoryConfig categoryConfig : categoryList) {
                if (categoryConfig != null) {
                    //1.3 判断行表的每一项的Id是否为空 如果为空，就生成
                    if (categoryConfig.getEntryCategoryConfigId() == null) {
                        Long generatedCategoryId = IdGenrator.generate();
                        categoryConfig.setEntryCategoryConfigId(generatedCategoryId);
                    }
                    categoryConfig.setEntryConfigId(generatedEntryId);
                    saveOrUpdateCategoryList.add(categoryConfig);
                }
            }
            entryConfig.setEntryCategoryConfigList(saveOrUpdateCategoryList);
            this.save(entryConfig);
            iEntryCategoryConfigService.saveOrUpdateBatch(saveOrUpdateCategoryList);
        }
        //1.1 如果头的Id为空，先生成头Id 结束=====


        //2.1 如果头表的Id不为空（即为更新） 开始=====
        else {
            //获取维护的品类列表
            List<EntryCategoryConfig> categoryList = entryConfig.getEntryCategoryConfigList();
            Assert.isTrue(!ifCategoryMaintained(entrySet, categoryList), "选中的品类存在已被维护的品类！");
            //先将原来这个头表维护的品类删除
            QueryWrapper<EntryCategoryConfig> deleteWrapper = new QueryWrapper<>();
            deleteWrapper.eq("ENTRY_CONFIG_ID", entryId);
            iEntryCategoryConfigService.remove(deleteWrapper);
            /** 判断每一个行表的主键Id是否为空，如果为空就生成 */
            List<EntryCategoryConfig> saveOrUpdateCategoryList = new ArrayList<>();
            //循环遍历维护的品类 开始 =====
            for (EntryCategoryConfig categoryConfig : categoryList) {
                if (categoryConfig != null) {
                    //如果行表项的Id为空 设置行表Id
                    if (categoryConfig.getEntryCategoryConfigId() == null) {
                        Long generatedCategoryId = IdGenrator.generate();
                        categoryConfig.setEntryCategoryConfigId(generatedCategoryId);
                    }
                    categoryConfig.setEntryConfigId(entryId);
                    saveOrUpdateCategoryList.add(categoryConfig);
                }
            }
            //循环遍历维护的品类 结束 =====
            entryConfig.setEntryCategoryConfigList(saveOrUpdateCategoryList);
            this.updateById(entryConfig);
            iEntryCategoryConfigService.saveOrUpdateBatch(saveOrUpdateCategoryList);
        }
        //2.1 如果头表的Id不为空 结束=====
    }

    /**
     * 根据传入的entryConfig实体查询所有已经被维护的品类的Set
     * @param entryConfig
     * @return HashSet
     */
    public Set<Long> getCategorySetByType(EntryConfig entryConfig) {
        String quaReviewType = entryConfig.getQuaReviewType();
        //查询当前已经存在的EntryConfig List
        QueryWrapper<EntryConfig> entryWrapper = new QueryWrapper<>();
        entryWrapper.eq("QUA_REVIEW_TYPE", quaReviewType);
        List<EntryConfig> existEntryConfigList = this.list(entryWrapper);
        //用于判断维护的品类是否有重复
        Set<Long> categoryIdSet = new HashSet<>();

        //获取当前已经维护的所有品类的 List
        if (!existEntryConfigList.isEmpty()){
            /** 遍历头表元素 开始 */
            for (EntryConfig config : existEntryConfigList) {
                QueryWrapper<EntryCategoryConfig> categoryWrapper = new QueryWrapper<>();
                categoryWrapper.eq("ENTRY_CONFIG_ID", config.getEntryConfigId());
                List<EntryCategoryConfig> categoryList = iEntryCategoryConfigService.list(categoryWrapper);
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

    public boolean ifCategoryMaintained(Set<Long> categorySet, List<EntryCategoryConfig> categoryList) {
        boolean isMaintained = false;
        for (EntryCategoryConfig categoryConfig : categoryList) {
            if (categorySet.contains(categoryConfig.getCategoryId())){
                isMaintained = true;
                break;
            }
        }
        return isMaintained;
    }

    @Override
    public void deleteEntryConfig(Long entryConfigId) {
        QueryWrapper<EntryCategoryConfig> categoryConfigQueryWrapper = new QueryWrapper<>();
        categoryConfigQueryWrapper.eq("ENTRY_CONFIG_ID", entryConfigId);
        iEntryCategoryConfigService.remove(categoryConfigQueryWrapper);
        this.removeById(entryConfigId);
    }

    @Override
    public void updateByParam(EntryConfig entryConfig) {
        Long entryId = entryConfig.getEntryConfigId();
        Assert.notNull(entryId, "entryId为空！");
        this.updateById(entryConfig);
    }

    /**
     * 根据供方准入类型和品类Id查询对应的准入配置EntryConfig
     *
     */
    @Override
    public EntryConfig getEntryConfigByTypeAndCategoryId(String type, Long categoryId) {
        EntryConfig entryConfig = new EntryConfig();
        Assert.notNull(type, "传入的供方准入类型为空！");

        /** 查询传入的type对应的所有entryConfig列表 */
        QueryWrapper<EntryConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("QUA_REVIEW_TYPE", type);
        List<EntryConfig> entryConfigList = this.list(queryWrapper);
        /** 查询传入的type对应的所有entryConfig列表 */

        for (EntryConfig entry : entryConfigList) {
            /** 查询当前entryConfig查询维护的对应的品类列表 */
            if (entry != null && entry.getEntryConfigId() != null){
                List<Long> categoryIdList = iEntryCategoryConfigService.getCategoryIdListByEntryConfigId(entry.getEntryConfigId());
                if (!categoryIdList.isEmpty() && categoryIdList.contains(categoryId)) {
                    entryConfig = entry;
                    break;
                }
            }
        }
        return entryConfig;
    }

    @Override
    public void importEntryConfigs() {
        PurchaseCategory purchaseCategory = new PurchaseCategory();
        purchaseCategory.setLevel(3);
        List<PurchaseCategory> categoryList = baseClient.listByLevel(purchaseCategory);

        List<String> types = new ArrayList<>();
        types.add("NEW_VENDOR");
        types.add("NEW_CATEGORY");
        types.add("RED_VERIFY");
        //System.out.println(categoryList.size());
        for (String type : types) {
            EntryConfig enableEntryConfig = new EntryConfig();
            Long entryId = IdGenrator.generate();
            enableEntryConfig.setEntryConfigId(entryId);

            String entryConfigNum = baseClient.seqGen(SequenceCodeConstant.SEQ_SUP_ENTRY_CONFIG_NUM);
            enableEntryConfig.setEntryConfigNum(entryConfigNum);

            enableEntryConfig.setQuaReviewType(type);

            enableEntryConfig.setIfAuth("Y");
            enableEntryConfig.setIfQpaQsa("Y");
            enableEntryConfig.setIfAuthOnSite("Y");
            enableEntryConfig.setIfAuthSample("Y");

            this.save(enableEntryConfig);
            //==========================
            EntryConfig disableEntryConfig = new EntryConfig();
            Long disEntryId = IdGenrator.generate();
            disableEntryConfig.setEntryConfigId(disEntryId);

            String disEntryConfigNum = baseClient.seqGen(SequenceCodeConstant.SEQ_SUP_ENTRY_CONFIG_NUM);
            disableEntryConfig.setEntryConfigNum(disEntryConfigNum);

            disableEntryConfig.setQuaReviewType(type);

            disableEntryConfig.setIfAuth("N");
            disableEntryConfig.setIfQpaQsa("N");
            disableEntryConfig.setIfAuthOnSite("N");
            disableEntryConfig.setIfAuthSample("N");

            this.save(disableEntryConfig);

            List<EntryCategoryConfig> saveCategoryList = new ArrayList<>();
            for (int i = 0; i < categoryList.size(); i++){
                if (i < 45) {
                    PurchaseCategory enablePurchaseCategory = categoryList.get(i);
                    EntryCategoryConfig entryCategoryConfig = new EntryCategoryConfig();

                    Long categoryConfigId = IdGenrator.generate();
                    entryCategoryConfig.setEntryCategoryConfigId(categoryConfigId);
                    entryCategoryConfig.setEntryConfigId(entryId);

                    Long categoryId = enablePurchaseCategory.getCategoryId();
                    entryCategoryConfig.setCategoryId(categoryId);
                    String categoryCode = enablePurchaseCategory.getCategoryCode();
                    entryCategoryConfig.setCategoryCode(categoryCode);
                    String categoryName = enablePurchaseCategory.getCategoryName();
                    entryCategoryConfig.setCategoryName(categoryName);
                    saveCategoryList.add(entryCategoryConfig);
                }else {
                    PurchaseCategory disablePurchaseCategory = categoryList.get(i);
                    EntryCategoryConfig entryCategoryConfig = new EntryCategoryConfig();

                    Long categoryConfigId = IdGenrator.generate();
                    entryCategoryConfig.setEntryCategoryConfigId(categoryConfigId);
                    entryCategoryConfig.setEntryConfigId(disEntryId);

                    Long categoryId = disablePurchaseCategory.getCategoryId();
                    entryCategoryConfig.setCategoryId(categoryId);
                    String categoryCode = disablePurchaseCategory.getCategoryCode();
                    entryCategoryConfig.setCategoryCode(categoryCode);
                    String categoryName = disablePurchaseCategory.getCategoryName();
                    entryCategoryConfig.setCategoryName(categoryName);
                    saveCategoryList.add(entryCategoryConfig);
                }
            }
            iEntryCategoryConfigService.saveBatch(saveCategoryList);
        }

    }

    /**
     * 批量导入测试数据（忽略）
     */
    @Override
    public void importEntryConfigNewVendor() {
        PurchaseCategory purchaseCategory = new PurchaseCategory();
        purchaseCategory.setLevel(3);
        List<PurchaseCategory> categoryList = baseClient.listByLevel(purchaseCategory);

        Long entryId = new Long("7894008503205888");

        List<EntryCategoryConfig> saveCategoryList = new ArrayList<>();
        for (PurchaseCategory category : categoryList) {
            EntryCategoryConfig entryCategoryConfig = new EntryCategoryConfig();

            Long categoryConfigId = IdGenrator.generate();
            entryCategoryConfig.setEntryCategoryConfigId(categoryConfigId);
            entryCategoryConfig.setEntryConfigId(entryId);

            Long categoryId = category.getCategoryId();
            entryCategoryConfig.setCategoryId(categoryId);
            String categoryCode = category.getCategoryCode();
            entryCategoryConfig.setCategoryCode(categoryCode);
            String categoryName = category.getCategoryName();
            entryCategoryConfig.setCategoryName(categoryName);
            saveCategoryList.add(entryCategoryConfig);

        }

        iEntryCategoryConfigService.saveBatch(saveCategoryList);

    }

}
