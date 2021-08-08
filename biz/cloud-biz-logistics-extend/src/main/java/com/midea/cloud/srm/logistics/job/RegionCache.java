package com.midea.cloud.srm.logistics.job;

import com.midea.cloud.srm.model.logistics.expense.entity.Region;
import com.midea.cloud.srm.model.logistics.soap.tms.entity.TmsRegion;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *  行政区域缓存类
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/12/3 11:53
 *  修改内容:
 * </pre>
 */
@Data
public class RegionCache implements Serializable {

    /**
     * 要保存的数据集合
     */
    List<Region> saveRegionList = new ArrayList<>();

    /**
     * 要更新的数据集合
     */
    List<Region> updateRegionList = new ArrayList<>();

    /**
     * tms数据集合
     */
    List<TmsRegion> tmsRegionList = new ArrayList<>();

    /**
     * tms同步成功数据集
     */
    List<TmsRegion> tmsSuccessRegionList = new ArrayList<>();

    /**
     * tms同步失败数据集
     */
    List<TmsRegion> tmsFailRegionList = new ArrayList<>();

}
