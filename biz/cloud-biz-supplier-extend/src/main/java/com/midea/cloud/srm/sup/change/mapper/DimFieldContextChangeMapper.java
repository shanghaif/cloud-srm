package com.midea.cloud.srm.sup.change.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.supplier.change.entity.DimFieldContextChange;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 维度字段内容变更表 Mapper 接口
 * </p>
 *
 * @author chensl26@meiCloud.com
 * @since 2020-03-26
 */
public interface DimFieldContextChangeMapper extends BaseMapper<DimFieldContextChange> {

    Map<String, Object> findByKey(@Param("keys") List<String> keys,@Param("changeOrderId") Long changeOrderId);

    List<String> findKey(Long changeOrderId);
}
