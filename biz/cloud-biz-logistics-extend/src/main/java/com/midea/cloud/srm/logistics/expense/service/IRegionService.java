package com.midea.cloud.srm.logistics.expense.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.logistics.job.RegionCache;
import com.midea.cloud.srm.model.logistics.expense.entity.Region;
import com.midea.cloud.srm.model.logistics.soap.tms.entity.TmsRegion;

import java.util.List;
import java.util.Map;

/**
*  <pre>
 *  行政区域维护表 服务类
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-11-26 12:03:51
 *  修改内容:
 * </pre>
*/
public interface IRegionService extends IService<Region> {

    /**
     * 条件查询
     * @param region
     * @return
     */
    List<Region> listPageByParam(Region region);

    /**
     * 保存行政区域数据(单条)
     * @param region
     */
    void saveRegion(Region region);

    /**
     * 保存前非空校验
     * @param region
     */
    void checkNotEmptyBeforeSave(Region region);

    /**
     * 更新行政区域状态(单条)
     * @param regionId
     * @param status
     */
    void updateRegionStatus(Long regionId, String status);

    /**
     * 保存或更新数据到srm数据库
     * @param tmsRegion
     * @param region
     * @param dbRegionParentMap
     * @param regionCache
     * @return
     */
    Region saveOrUpdateSrmRegion(TmsRegion tmsRegion, Region region, Map<String, Region> dbRegionParentMap, RegionCache regionCache);

    /**
     * 根据父编码和类型查找行政区域
     * @return
     */
    List<Region> queryRegionBy(String regionLevelCode,String parentRegionCode);
}
