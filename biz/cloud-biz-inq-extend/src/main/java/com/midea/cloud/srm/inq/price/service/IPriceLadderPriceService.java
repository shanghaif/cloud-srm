package com.midea.cloud.srm.inq.price.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.inq.price.entity.PriceLadderPrice;

import java.util.List;

/**
*  <pre>
 *  价格目录-阶梯价 服务类
 * </pre>
*
* @author linxc6@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-25 14:53:38
 *  修改内容:
 * </pre>
*/
public interface IPriceLadderPriceService extends IService<PriceLadderPrice> {

    /**
     * 根据价格目录id获取阶梯价
     */
    List<PriceLadderPrice> getLadderPrice(Long priceLibraryId);

    /**
     * 根据价格目录删除
     */
    void deleteByPriceLibraryId(Long priceLibraryId);
}
