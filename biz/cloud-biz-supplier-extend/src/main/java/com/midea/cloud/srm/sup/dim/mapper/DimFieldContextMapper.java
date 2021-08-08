package com.midea.cloud.srm.sup.dim.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.supplier.dim.entity.DimFieldContext;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 维度字段内容表 Mapper 接口
 * </p>
 *
 * @author zhuwl7@meicloud.com
 * @since 2020-03-02
 */
public interface DimFieldContextMapper extends BaseMapper<DimFieldContext> {

    List<String> findKey(Long orderId);

    Map<String,Object> findByKey(@Param("keys") List<String> keys, @Param("orderId") Long orderId);
}
