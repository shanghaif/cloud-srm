package com.midea.cloud.srm.base.job;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.midea.cloud.common.constants.BaseConst;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.common.utils.redis.RedisUtil;
import com.midea.cloud.quartz.bind.Job;
import com.midea.cloud.quartz.handler.ExecuteableJob;
import com.midea.cloud.srm.base.busiunit.service.IErpService;
import com.midea.cloud.srm.base.material.service.IMaterialItemService;
import com.midea.cloud.srm.base.material.service.IMaterialOrgService;
import com.midea.cloud.srm.base.organization.mapper.ErpMaterialItemMapper;
import com.midea.cloud.srm.base.organization.service.ICategoryService;
import com.midea.cloud.srm.base.organization.service.IErpMaterialItemService;
import com.midea.cloud.srm.base.organization.service.IOrganizationService;
import com.midea.cloud.srm.model.base.material.MaterialItem;
import com.midea.cloud.srm.model.base.material.MaterialOrg;
import com.midea.cloud.srm.model.base.organization.entity.Category;
import com.midea.cloud.srm.model.base.organization.entity.ErpMaterialItem;
import com.midea.cloud.srm.model.base.organization.entity.Organization;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * <pre>
 *  ERP推送物料 定时程序
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/10/11 16:04
 *  修改内容:
 * </pre>
 */
@Job("ERPImportMaterialItemJob")
@Slf4j
public class ERPImportMaterialItemJob implements ExecuteableJob {

    static final int subAmount = 50;

    @Autowired
    private IMaterialItemService iMaterialItemService;
    @Autowired
    private IMaterialOrgService iMaterialOrgService;

    @Autowired
    private IErpMaterialItemService iErpMaterialItemService;

    @Autowired
    private ICategoryService iCategoryService;

    @Resource
    private ErpMaterialItemMapper erpMaterialItemMapper;

    @Resource
    private IOrganizationService iOrganizationService;

    @Resource
    private RedisUtil redisUtil;

    public static final String MATERIAL_SAVE_IMPORT_LIST =   "IMPORT_MATERIAL_SAVE_";       //物料保存集合
    public static final String MATERIAL_UPDATE_IMPORT_LIST =   "IMPORT_MATERIAL_UPDATE_";    //物料更新集合
    public static final String MATERIAL_ORG_SAVE_LIST = "IMPORT_MATERIAL_ORG_SAVE_";      //物料组织关系保存集合
    public static final String MATERIAL_ORG_UPDATE_LIST = "IMPORT_MATERIAL_ORG_UPDATE_";   //物料组织关系保存集合
    public static final String ERP_MATERIAL_UPDATE_LIST = "ERP_MATERIAL_UPDATE_";           //需要更新的erp推送数据记录集合

    public static final int       MATERIAL_CACHE_LIFE  =   86400;//一天




    @Override
    public BaseResult executeJob(Map<String, String> params) {
        /**
         * 每次处理500条
         */
        //1.根据记录表的importStatus =0 order by last_update_date 查询需要插入的数据集合
        QueryWrapper<ErpMaterialItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("IMPORT_STATUS", IErpService.IMPORT_DEFAULT_STATUS);
        // 查询待处理的有多少条
        int count = iErpMaterialItemService.count(queryWrapper);
        int round = count / 1000;
        round = round == 0 ? 1 : round;
        if (count % 1000 > 0) {
            round += 1;
        }

        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger errorCount = new AtomicInteger(0);
        log.info("ERP推送物料数据导入到基础表");
        log.info("查询到需要导入的数据有{}条", count);
        Long startTime = System.currentTimeMillis();
        log.info("======获取库存组织信息============");
        //查询组织（库存组织和业务实体）
        List<Organization> invs = iOrganizationService.list(Wrappers.lambdaQuery(Organization.class)
                .eq(Organization::getOrganizationTypeCode, BaseConst.ORG_INV));
        Map<String, Organization> invOUMap = new HashMap<>();
        invs.forEach(inv -> {
            Long parentId = Long.valueOf(inv.getParentOrganizationIds());
            List<Organization> ous = iOrganizationService.list(Wrappers.lambdaQuery(Organization.class)
                    .eq(Organization::getOrganizationId, parentId));
            if (CollectionUtils.isNotEmpty(ous) && Objects.nonNull(ous.get(0))) {
                invOUMap.put(inv.getOrganizationCode(), ous.get(0));
            }
        });
        log.info("===============开始导入物料信息=================");
        for (int i = 0; i < round; i++) {
            List<ErpMaterialItem> erpMaterialItemList = erpMaterialItemMapper.queryErpMaterialItem500();
            if (CollectionUtils.isNotEmpty(erpMaterialItemList)) {
                //导入的物料id
                Set<Long> itemIdSet = erpMaterialItemList.stream().map(e -> e.getItemId()).collect(Collectors.toSet());
                //导入的物料code
                List<String> ItemNumberList = erpMaterialItemList.stream().map(e -> e.getItemNumber()).collect(Collectors.toList());
                //导入的类别集合
                List<Category> categories = iCategoryService.list(new QueryWrapper<Category>().in("ITEM_ID", itemIdSet));
                //查询数据库中的物料信息
                Map<String, MaterialItem> dbMaterialItemMap = iMaterialItemService.listMaterialByCodeBatch(ItemNumberList)
                        .stream().collect(Collectors.toMap(m -> m.getMaterialCode(), m -> m , (o, o2) -> o));

                //清空上一轮的信息
                MaterialCache materialCache = new MaterialCache();
                materialCache.setInvs(invs);

                //对物料数据信息进行初次处理
                for(int j = 0 ; j < erpMaterialItemList.size() ;j++ ){
                    ErpMaterialItem erpMaterialItem = erpMaterialItemList.get(j);
                    //查询并设置设置这条物料的类别集 categoryList
                    List<Category> itCategoryList = categories.stream().filter(e ->
                            erpMaterialItem.getItemId().equals(e.getItemId())).collect(Collectors.toList());
                    erpMaterialItem.setCategoryList(itCategoryList);
                    materialCache.getErpMaterialItemList().add(erpMaterialItem);
                    try {
                        loadItemDataToCache(erpMaterialItem, dbMaterialItemMap, materialCache);
                        successCount.addAndGet(1);
                    } catch (Exception e) {
                        errorHandle(e , erpMaterialItem , errorCount);
                    }
                }

                try {
                    //拿到本次导入的erp物料记录中的库存组织
                    List<Organization> inputOrgList = new ArrayList<>();
                    for(ErpMaterialItem erpMaterialItem : materialCache.getErpMaterialItemList()){
                        for(Organization organization : materialCache.getInvs()){
                            if(erpMaterialItem.getOrgCode().equals(organization.getOrganizationCode())){
                                inputOrgList.add(organization);
                                break;
                            }
                        }
                    }
                    //获取数据库中库存组织关系
                    List<MaterialItem> updateMaterialItemList = materialCache.getUpdateMaterialItemList();
                    Set<Long> materialIds = updateMaterialItemList.stream().map(m -> m.getMaterialId()).collect(Collectors.toSet());
                    List<MaterialOrg> inDBMaterialOrgList = iMaterialOrgService.list(Wrappers.lambdaQuery(MaterialOrg.class)
                            .in(!materialIds.isEmpty() , MaterialOrg::getMaterialId , materialIds)
                            .in(MaterialOrg::getOrgId , inputOrgList.stream().map(m -> m.getParentOrganizationIds()).collect(Collectors.toList()))
                            .in(MaterialOrg::getOrganizationId , inputOrgList.stream().map(m -> m.getOrganizationId()).collect(Collectors.toList())));
                    materialCache.setInDBMaterialOrgList(inDBMaterialOrgList);
                }catch (Exception e){
                    log.error("获取物料库存组织失败",e);
                }

                //处理物料组织关系
                for(int j = 0 ; j < materialCache.getErpMaterialItemList().size() ;j++ ){
                    ErpMaterialItem erpMaterialItem = materialCache.getErpMaterialItemList().get(j);
                    try {
                        MaterialItem materialItem = dbMaterialItemMap.get(erpMaterialItem.getItemNumber());
                        iMaterialItemService.saveOrUpdateSrmMaterialOrg(erpMaterialItem, materialItem, invs, invOUMap ,materialCache);
                    }catch (Exception e){
                        errorHandle(e , erpMaterialItem , errorCount);
                    }
                }

                //固化到数据库中
                try {
                    saveCacheDateToDB(materialCache);
                }catch (Exception e){
                    int size = materialCache.getUpdateMaterialItemList().size() +
                                materialCache.getSaveMaterialItemList().size();
                    errorCount.addAndGet(size);
                    String stackTrace = Arrays.toString(e.getStackTrace());
                    Map<String, String> errorMsg = new LinkedHashMap<>();
                    errorMsg.put("message", e.getMessage());
                    errorMsg.put("stackTrace", stackTrace);
                    log.error("导入物料信息失败", e);
                    for(int k = 0 ; k < materialCache.getUpdateErpMaterialList().size() ; i++){
                        materialCache.getUpdateErpMaterialList().get(k).setImportStatus(IErpService.IMPORT_FAIL_STATUS);
                    }
                    iErpMaterialItemService.updateBatchById(materialCache.getUpdateErpMaterialList());
                }
                //指向其他内存
                materialCache = null;

            }
        }
        StringBuffer sb = new StringBuffer();
        Long totalTime = (System.currentTimeMillis() - startTime) / 1000;
        sb.append("=====================更新物料结束，成功").append(successCount.get()).append(",失败").append(errorCount.get()).append("条,用时").append(totalTime).append("秒;=================================");
        log.info("更新物料结束，成功{}条，失败{}条，用时{}秒", successCount.get(), errorCount.get(), totalTime);
        return BaseResult.build(ResultCode.SUCCESS, sb.toString());
    }


    private void errorHandle(Exception e , ErpMaterialItem erpMaterialItem , AtomicInteger errorCount ){
        String stackTrace = Arrays.toString(e.getStackTrace());
        Map<String, String> errorMsg = new LinkedHashMap<>();
        errorMsg.put("message", e.getMessage());
        errorMsg.put("stackTrace", stackTrace);
        iErpMaterialItemService.updateById(erpMaterialItem.
                setImportStatus(IErpService.IMPORT_FAIL_STATUS).
                setErrorMsg(JSON.toJSONString(errorMsg)).
                setLastUpdateDate(new Date()));
        log.error("导入物料信息失败", e);
        log.error("物料信息{}", erpMaterialItem.toString());
        errorCount.addAndGet(1);
    }

    @Transactional(rollbackFor = Exception.class)
    public void loadItemDataToCache(ErpMaterialItem erpMaterialItem,
                                    Map<String, MaterialItem> dbMaterialItemMap,
                                    MaterialCache materialCache) {
        MaterialItem materialItem = dbMaterialItemMap.get(erpMaterialItem.getItemNumber());
        boolean dbNotMaterialItem = Objects.isNull(materialItem);
        if (StringUtils.isBlank(erpMaterialItem.getItemNumber())) {
            throw new BaseException("传入的物料编码为空！");
        }
        materialItem = iMaterialItemService.saveOrUpdateSrmMaterialItem(erpMaterialItem, materialItem, materialCache);
        if (dbNotMaterialItem) {
            dbMaterialItemMap.put(erpMaterialItem.getItemNumber(), materialItem);
        }

//        //3.写入物料库存组织关系表
//        iMaterialItemService.saveOrUpdateSrmMaterialOrg(erpMaterialItem, materialItem, invs, invOUMap ,materialCache);

        erpMaterialItem.setImportStatus(IErpService.IMPORT_SUCCESS_STATUS).setLastUpdateDate(new Date());
        materialCache.getUpdateErpMaterialList().add(erpMaterialItem);
    }

    /**
     * 把缓存中的数据固化到数据库中
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveCacheDateToDB(MaterialCache materialCache){
        List<MaterialItem> saveMaterialItemList = materialCache.getSaveMaterialItemList();
        List<MaterialItem> updateMaterialItemList = materialCache.getUpdateMaterialItemList();
        List<MaterialOrg> saveMaterialOrgList = materialCache.getSaveMaterialOrgList();
        List<MaterialOrg> updateMaterialOrgList = materialCache.getUpdateMaterialOrgList();
        List<ErpMaterialItem> updateErpMaterialList = materialCache.getUpdateErpMaterialList();

        log.info("======================开始更新物料信息========================");
        for(int i = 0 ; i < updateErpMaterialList.size() ; i++){
            log.info(updateErpMaterialList.get(i).toString());
        }
        iMaterialItemService.saveBatch(saveMaterialItemList);
        iMaterialItemService.updateBatchById(updateMaterialItemList);
        log.info("======================更新物料信息结束========================");

        log.info("======================开始更新物料组织信息========================");
        iMaterialOrgService.saveBatch(saveMaterialOrgList);
        iMaterialOrgService.updateBatchById(updateMaterialOrgList);
        log.info("======================更新物料组织信息结束========================");

        log.info("======================开始更新ERP物料记录信息========================");
        iErpMaterialItemService.updateBatchById(updateErpMaterialList);
        log.info("======================更新ERP物料记录信息结束========================");
    }
}
