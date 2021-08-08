package com.midea.cloud.srm.logistics.expense.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.enums.logistics.LogisticsStatus;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.logistics.expense.mapper.RegionMapper;
import com.midea.cloud.srm.logistics.expense.service.IRegionService;
import com.midea.cloud.srm.logistics.job.RegionCache;
import com.midea.cloud.srm.model.logistics.expense.entity.Region;
import com.midea.cloud.srm.model.logistics.soap.tms.entity.TmsRegion;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.*;

/**
 * <pre>
 *  行政区域维护表 服务实现类
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-11-26 12:03:51
 *  修改内容:
 * </pre>
 */
@Service
public class RegionServiceImpl extends ServiceImpl<RegionMapper, Region> implements IRegionService {



    @Override
    public List<Region> queryRegionBy(String regionLevelCode, String parentRegionCode) {
        Assert.notEmpty(regionLevelCode,"缺少参数: regionLevelCode");
        return this.list(Wrappers.lambdaQuery(Region.class).
                in(Region::getRegionLevelCode,regionLevelCode).
                eq(!ObjectUtils.isEmpty(parentRegionCode),Region::getParentRegionCode,parentRegionCode));
    }

    /* 行政区域状态为这几种时置为有效 Y, 其他置为无效 N */
    public static final HashSet<String> StatusSet;

    static {
        StatusSet = new HashSet<String>();

        StatusSet.add("Y");
    }

    //未导入
    static final Integer IMPORT_DEFAULT_STATUS = 0;
    //导入成功
    static final Integer IMPORT_SUCCESS_STATUS = 1;
    //导入失败
    static final Integer IMPORT_FAIL_STATUS = -1;

    /**
     * 条件查询
     *
     * @param region
     * @return
     */
    @Override
    public List<Region> listPageByParam(Region region) {
        List<Region> regionList = this.list(Wrappers.lambdaQuery(Region.class)
                .like(StringUtils.isNotEmpty(region.getRegionCode()), Region::getRegionCode, region.getRegionCode())
                .like(StringUtils.isNotEmpty(region.getRegionName()), Region::getRegionName, region.getRegionName())
                .eq(StringUtils.isNotEmpty(region.getRegionLevelCode()), Region::getRegionLevelCode, region.getRegionLevelCode())
                .like(StringUtils.isNotEmpty(region.getParentRegionName()), Region::getParentRegionName, region.getParentRegionName())
                .like(StringUtils.isNotEmpty(region.getRegionFullName()), Region::getRegionFullName, region.getRegionFullName())
                .eq(StringUtils.isNotEmpty(region.getStatus()), Region::getStatus, region.getStatus())
                .eq(StringUtils.isNotEmpty(region.getIfBase()), Region::getIfBase, region.getIfBase())
                .orderByDesc(Region::getLastUpdateDate)
        );
        return regionList;
    }

    /**
     * 保存行政区域数据
     *
     * @param region
     */
    @Override
    public void saveRegion(Region region) {
        if (null == region.getRegionId()) {
            region.setRegionId(IdGenrator.generate()).setStatus(LogisticsStatus.DRAFT.getValue());
            this.save(region);
        } else {
            this.updateById(region);
        }
    }

    /**
     * 保存前非空校验
     *
     * @param region
     */
    @Override
    public void checkNotEmptyBeforeSave(Region region) {
        if (StringUtils.isEmpty(region.getRegionName()))
            throw new BaseException(LocaleHandler.getLocaleMsg("行政区域名称不能为空, 请选择/输入后重试."));
        if (StringUtils.isEmpty(region.getRegionCode()))
            throw new BaseException(LocaleHandler.getLocaleMsg("行政区域代码不能为空, 请选择/输入后重试."));
        if (StringUtils.isEmpty(region.getRegionLevelCode()))
            throw new BaseException(LocaleHandler.getLocaleMsg("行政区域层级不能为空, 请选择/输入后重试."));
    }

    /**
     * 更新行政区域状态(单条)
     *
     * @param regionId
     * @param status
     */
    @Override
    public void updateRegionStatus(Long regionId, String status) {
        Region region = this.getById(regionId);
        region.setStatus(status);
        this.updateById(region);
    }

    /**
     * 保存或更新数据到srm数据库
     *
     * @param tmsRegion
     * @param region
     * @param regionCache
     * @return
     */
    @Override
    public Region saveOrUpdateSrmRegion(TmsRegion tmsRegion, Region saveRegion, Map<String, Region> dbRegionParentMap, RegionCache regionCache) {
        if (Objects.isNull(saveRegion)) {
            saveRegion = new Region();
            saveRegion.setRegionId(IdGenrator.generate());
            saveRegion = setRegionField(tmsRegion, saveRegion, dbRegionParentMap, regionCache);
            if (null != saveRegion.getParentRegionId()) {
                regionCache.getSaveRegionList().add(saveRegion);
                tmsRegion.setLastUpdateDate(new Date()).setImportStatus(IMPORT_SUCCESS_STATUS).setImportDate(new Date());
                regionCache.getTmsSuccessRegionList().add(tmsRegion);
            } else {
                StringBuffer sb = new StringBuffer();
                sb.append("根据父区域代码:[").append(saveRegion.getParentRegionCode()).append("]找不到对应的父区域");
                tmsRegion.setLastUpdateDate(new Date()).setImportStatus(IMPORT_FAIL_STATUS).setErrorMsg(sb.toString());
                regionCache.getTmsFailRegionList().add(tmsRegion);
            }
        } else {
            saveRegion = setRegionField(tmsRegion, saveRegion, dbRegionParentMap, regionCache);
            if (null != saveRegion.getParentRegionId()) {
                regionCache.getUpdateRegionList().add(saveRegion);
                tmsRegion.setLastUpdateDate(new Date()).setImportStatus(IMPORT_SUCCESS_STATUS).setImportDate(new Date());
                regionCache.getTmsSuccessRegionList().add(tmsRegion);
            } else {
                StringBuffer sb = new StringBuffer();
                sb.append("根据父区域代码:[").append(saveRegion.getParentRegionCode()).append("]找不到对应的父区域");
                tmsRegion.setLastUpdateDate(new Date()).setImportStatus(IMPORT_FAIL_STATUS).setErrorMsg(sb.toString());
                regionCache.getTmsFailRegionList().add(tmsRegion);
            }
        }
        return saveRegion;
    }

    /**
     * @param tmsRegion
     * @description 设置行政区域的字段（除了regionId之外的）
     * @modifiedBy xiexh12@meicloud.com
     */
    private Region setRegionField(TmsRegion tmsRegion,
                                  Region saveRegion,
                                  Map<String, Region> dbRegionParentMap,
                                  RegionCache regionCache) {
        // 行政区域代码
        saveRegion.setRegionCode(tmsRegion.getAdminRegionCode());
        // 行政区域中文名称
        saveRegion.setRegionName(tmsRegion.getAdminRegionName());
        // 行政区域英文名称
        saveRegion.setRegionNameEn(tmsRegion.getAttribute1());

        // 行政区域层级代码
        String regionLevelCode = tmsRegion.getRegionLevelCode();
        saveRegion.setRegionLevelCode(regionLevelCode);
        // 上级代码
        String parentCode = "";
        switch (regionLevelCode) {
            // 省
            case "2.0":
                parentCode = tmsRegion.getBelongCountryCode();
                saveRegion.setParentRegionCode(parentCode);
                break;
            // 市
            case "3.0":
                parentCode = tmsRegion.getBelongProvinceCode();
                saveRegion.setParentRegionCode(parentCode);
                break;
            // 区/县
            case "4.0":
                parentCode = tmsRegion.getBelongCityCode();
                saveRegion.setParentRegionCode(parentCode);
                break;
            default:
                break;
        }

        if (StringUtils.isNotEmpty(parentCode) && dbRegionParentMap.containsKey(parentCode)) {
            Region parentRegion = dbRegionParentMap.get(parentCode);
            saveRegion.setParentRegionId(parentRegion.getRegionId());
            saveRegion.setParentRegionName(parentRegion.getRegionName());
            saveRegion.setRegionFullId(generateRegionFullId(saveRegion));
            saveRegion.setRegionFullName(generateRegionFullName(saveRegion));
        }
        // 状态
        if (StatusSet.contains(tmsRegion.getEnableFlag())) {
            saveRegion.setStatus(LogisticsStatus.EFFECTIVE.getValue());
        } else {
            saveRegion.setStatus(LogisticsStatus.INEFFECTIVE.getValue());
        }
        // 来源系统
        saveRegion.setSourceSystem(tmsRegion.getSourceSystem());
        return saveRegion;
    }

    /**
     * 根据区域生成全路径id
     *
     * @param region
     * @return
     */
    private String generateRegionFullId(Region region) {
        String fullId = "";
        Long parentRegionId = region.getParentRegionId();
        if (region.getParentRegionId() != null && region.getParentRegionId() > 0) {
            Region parentRegion = this.getById(parentRegionId);
            fullId = parentRegion.getRegionFullId() + "-" + region.getRegionId();
        } else {
            fullId = "" + region.getRegionId();
        }
        return fullId;
    }

    /**
     * 根据区域生成全路径名称
     *
     * @param region
     * @return
     */
    private String generateRegionFullName(Region region) {
        String fullName = "";
        Long parentRegionId = region.getParentRegionId();
        if (region.getParentRegionId() != null && region.getParentRegionId() > 0) {
            Region parentRegion = this.getById(parentRegionId);
            fullName = parentRegion.getRegionFullName() + "-" + region.getRegionName();
        } else {
            fullName = "" + region.getRegionName();
        }
        return fullName;
    }

}
