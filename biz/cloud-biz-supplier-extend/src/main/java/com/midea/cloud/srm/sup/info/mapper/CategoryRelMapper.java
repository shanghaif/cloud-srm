package com.midea.cloud.srm.sup.info.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.supplier.info.dto.CategoryRelQueryDTO;
import com.midea.cloud.srm.model.supplier.info.entity.CategoryRel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 可供品类 Mapper 接口
 * </p>
 *
 * @author chensl26@meiCloud.com
 * @since 2020-04-05
 */
public interface CategoryRelMapper extends BaseMapper<CategoryRel> {

    List<CategoryRel> listPageByParam(@Param("categoryRelQueryDTO") CategoryRelQueryDTO categoryRelQueryDTO, @Param("longs") List<Long> longs);
}
