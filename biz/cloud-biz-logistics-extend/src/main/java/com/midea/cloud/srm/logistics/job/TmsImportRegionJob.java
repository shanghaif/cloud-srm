package com.midea.cloud.srm.logistics.job;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.quartz.bind.Job;
import com.midea.cloud.quartz.handler.ExecuteableJob;
import com.midea.cloud.srm.logistics.expense.service.IRegionService;
import com.midea.cloud.srm.logistics.soap.tms.service.ITmsRegionService;
import com.midea.cloud.srm.model.logistics.expense.entity.Region;
import com.midea.cloud.srm.model.logistics.soap.tms.entity.TmsRegion;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * <pre>
 *  定时任务导tms象征区域数据
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/12/3 11:18
 *  修改内容:
 * </pre>
 */
@Job("TmsImportRegionJob")
@Slf4j
public class TmsImportRegionJob implements ExecuteableJob {

    // 区域层级代码
    private final static String REGION_CODE = "regionLevelCode";

    // 省 层级代码
    private final static String REGION_PROVINCE_LEVEL_CODE = "2.0";

    // 市 层级代码
    private final static String REGION_CITY_LEVEL_CODE = "3.0";

    // 县/区 层级代码
    private final static String REGION_COUNTY_LEVEL_CODE = "4.0";

    private final int subAmount = 1000;

    //未导入
    static final Integer IMPORT_DEFAULT_STATUS = 0;
    //导入成功
    static final Integer IMPORT_SUCCESS_STATUS = 1;
    //导入失败
    static final Integer IMPORT_FAIL_STATUS = -1;

    @Autowired
    private ITmsRegionService iTmsRegionService;

    @Autowired
    private IRegionService iRegionService;

    /**
     * 执行任务
     *
     * @param params key:2.0 value:2.0
     * @return
     */
    @Override
    public BaseResult executeJob(Map<String, String> params) {

        // 1.查询当前层级的接口表数据
        List<TmsRegion> tmsRegionList = iTmsRegionService.list(Wrappers.lambdaQuery(TmsRegion.class)
                .eq(TmsRegion::getRegionLevelCode, params.get(REGION_CODE))
                .eq(TmsRegion::getImportStatus, IMPORT_DEFAULT_STATUS));
        // 2.根据查询到的数据, 分批批量处理
        // 查询待处理的有多少条
        int count = tmsRegionList.size();
        // 要处理多少轮
        int round = count / subAmount;
        round = round == 0 ? 1 : round;
        if (count % subAmount > 0) {
            round += 1;
        }

        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger errorCount = new AtomicInteger(0);

        log.info("tms推送行政区域数据导入到基础表, 开始...");
        log.info("查询到需要导入的数据有{}条", count);
        Long startTime = System.currentTimeMillis();

        log.info("===============开始导入行政区域信息=================");
        for (int i = 0; i < round; i++) {
            // 获取子列表
            List<TmsRegion> subRegionList = iTmsRegionService.list(Wrappers.lambdaQuery(TmsRegion.class)
                    .eq(TmsRegion::getRegionLevelCode, params.get(REGION_CODE))
                    .eq(TmsRegion::getImportStatus, IMPORT_DEFAULT_STATUS))
                    .stream().limit(subAmount).collect(Collectors.toList());

            if (CollectionUtils.isNotEmpty(subRegionList)) {
                // 导入数据的行政区域代码
                List<String> regionCodeList = subRegionList.stream().map(x -> x.getAdminRegionCode()).collect(Collectors.toList());

                // 正式表中的数据
                Map<String, Region> dbRegionMap = iRegionService.list(Wrappers.lambdaQuery(Region.class).in(Region::getRegionCode, regionCodeList))
                        .stream().collect(Collectors.toMap(k -> k.getRegionCode(), v -> v, (o1, o2) -> o2));

                // 导入数据的父区域
                List<String> regionParentCodeList = new ArrayList<>();
                // 正式表中的数据
                Map<String, Region> dbRegionParentMap = new HashMap<>();
                // 2.0 省
                if (params.get(REGION_CODE).equals(REGION_PROVINCE_LEVEL_CODE)) {
                    regionParentCodeList = subRegionList.stream().map(x -> x.getBelongCountryCode()).collect(Collectors.toList());
                    dbRegionParentMap = iRegionService.list(Wrappers.lambdaQuery(Region.class).in(Region::getRegionCode, regionParentCodeList))
                            .stream().collect(Collectors.toMap(k -> k.getRegionCode(), v -> v, (o1, o2) -> o2));
                }
                // 3.0 市
                if (params.get(REGION_CODE).equals(REGION_CITY_LEVEL_CODE)) {
                    regionParentCodeList = subRegionList.stream().map(x -> x.getBelongProvinceCode()).collect(Collectors.toList());
                    dbRegionParentMap = iRegionService.list(Wrappers.lambdaQuery(Region.class).in(Region::getRegionCode, regionParentCodeList))
                            .stream().collect(Collectors.toMap(k -> k.getRegionCode(), v -> v, (o1, o2) -> o2));
                }
                // 4.0 区/县
                if (params.get(REGION_CODE).equals(REGION_COUNTY_LEVEL_CODE)) {
                    regionParentCodeList = subRegionList.stream().map(x -> x.getBelongCityCode()).collect(Collectors.toList());
                    dbRegionParentMap = iRegionService.list(Wrappers.lambdaQuery(Region.class).in(Region::getRegionCode, regionParentCodeList))
                            .stream().collect(Collectors.toMap(k -> k.getRegionCode(), v -> v, (o1, o2) -> o2));
                }

                RegionCache regionCache = new RegionCache();

                // 开始循环处理数据
                for (TmsRegion tmsRegion : subRegionList) {
                    regionCache.getTmsRegionList().add(tmsRegion);

                    try {
                        regionCache = loadRegionDataToCache(tmsRegion, dbRegionMap, dbRegionParentMap, regionCache);
                    } catch (Exception e) {
                        errorHandle(e, tmsRegion, errorCount);
                    }

                }
                // 保存数据到数据库
                try {
                    saveCacheDateToDB(regionCache);
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("导入行政区域信息失败", e.getMessage());
                }
                successCount.addAndGet(regionCache.getTmsSuccessRegionList().size());
                errorCount.addAndGet(regionCache.getTmsFailRegionList().size());
            }
        }
        StringBuffer sb = new StringBuffer();
        Long totalTime = (System.currentTimeMillis() - startTime) / 1000;
        sb.append("=====================更新行政区域数据结束，成功").append(successCount.get()).append(",失败").append(errorCount.get()).append("条,用时").append(totalTime).append("秒;=================================");
        log.info("更新行政区域结束，成功{}条，失败{}条，用时{}秒", successCount.get(), errorCount.get(), totalTime);
        return BaseResult.build(ResultCode.SUCCESS, sb.toString());
    }


    @Transactional(rollbackFor = Exception.class)
    public RegionCache loadRegionDataToCache(TmsRegion tmsRegion,
                                             Map<String, Region> dbRegionMap,
                                             Map<String, Region> dbRegionParentMap,
                                             RegionCache regionCache) {
        Region region = dbRegionMap.get(tmsRegion.getAdminRegionCode());
        boolean dbNotRegion = Objects.isNull(region);
        // 区域代码判空, 不会出现, 接口表区域代码字段已经做了判空并且是非空
        if (StringUtils.isEmpty(tmsRegion.getAdminRegionCode())) {
            throw new BaseException("传入的行政区域代码为空！");
        }
        region = iRegionService.saveOrUpdateSrmRegion(tmsRegion, region, dbRegionParentMap, regionCache);
        if (dbNotRegion) {
            dbRegionMap.put(tmsRegion.getAdminRegionCode(), region);
        }

        return regionCache;
    }

    /**
     * 出错处理
     *
     * @param e
     * @param tmsRegion
     * @param errorCount
     */
    private void errorHandle(Exception e, TmsRegion tmsRegion, AtomicInteger errorCount) {
        String stackTrace = Arrays.toString(e.getStackTrace());
        Map<String, String> errorMsg = new LinkedHashMap<>();
        errorMsg.put("message", e.getMessage());
        errorMsg.put("stackTrace", stackTrace);
        iTmsRegionService.updateById(tmsRegion.
                setImportStatus(IMPORT_FAIL_STATUS).
                setErrorMsg(JSON.toJSONString(errorMsg)).
                setLastUpdateDate(new Date())
                .setImportDate(new Date()));
        log.error("导入行政区域数据失败", e);
        log.error("行政区域信息{}", tmsRegion.toString());
        errorCount.addAndGet(1);
    }

    /**
     * 把缓存中的数据保存到数据库
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveCacheDateToDB(RegionCache regionCache) {
        List<Region> saveRegionList = regionCache.getSaveRegionList();
        List<Region> updateRegionList = regionCache.getUpdateRegionList();
        List<TmsRegion> tmsSuccessRegionList = regionCache.getTmsSuccessRegionList();
        List<TmsRegion> tmsFailRegionList = regionCache.getTmsFailRegionList();

        log.info("======================开始同步行政区域信息========================");
        iRegionService.saveBatch(saveRegionList);
        iRegionService.updateBatchById(updateRegionList);
        log.info("======================同步行政区域信息结束========================");


        log.info("======================开始更新tms行政区域接口表记录信息========================");
        iTmsRegionService.updateBatchById(tmsSuccessRegionList);
        iTmsRegionService.updateBatchById(tmsFailRegionList);
        log.info("======================更新tms行政区域记录接口表信息结束========================");
    }

}
