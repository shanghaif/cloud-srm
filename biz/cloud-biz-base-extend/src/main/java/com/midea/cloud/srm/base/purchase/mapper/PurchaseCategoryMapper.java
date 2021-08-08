package com.midea.cloud.srm.base.purchase.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 采购分类 Mapper 接口
 * </p>
 *
 * @author chensl26@meicloud.com
 * @since 2020-03-06
 */
public interface PurchaseCategoryMapper extends BaseMapper<PurchaseCategory> {

    List<PurchaseCategory> listPageByParm(PurchaseCategory purchaseCategory);

    List<PurchaseCategory> queryByParam(@Param("param") String param,@Param("enabled")String enabled);

    List<PurchaseCategory> queryCategoryByType(@Param("param")String param, @Param("level") Integer level,@Param("enabled") String enabled);

    List<PurchaseCategory> listByParam(PurchaseCategory purchaseCategory);
}
