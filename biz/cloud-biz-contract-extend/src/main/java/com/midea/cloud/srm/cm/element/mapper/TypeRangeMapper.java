package com.midea.cloud.srm.cm.element.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.cm.element.entity.ElemMaintain;
import com.midea.cloud.srm.model.cm.element.entity.TypeRange;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 合同要素维护表 Mapper 接口
 * </p>
 *
 * @author wangpr@meicloud.com
 * @since 2020-08-12
 */
public interface TypeRangeMapper extends BaseMapper<TypeRange> {
    List<ElemMaintain> queryByValid(@Param("contractType") String contractType);
}
