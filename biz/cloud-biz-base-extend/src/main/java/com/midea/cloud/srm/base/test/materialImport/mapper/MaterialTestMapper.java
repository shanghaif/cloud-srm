package com.midea.cloud.srm.base.test.materialImport.mapper;

import com.midea.cloud.srm.model.base.test.MaterialTest;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 物料长宽高属性配置表（导测试数据用） Mapper 接口
 * </p>
 *
 * @author xiexh12@meicloud.com
 * @since 2020-09-26
 */
public interface MaterialTestMapper extends BaseMapper<MaterialTest> {
    void updateMaterialItem(@Param("mCode") String mCode, @Param("cCode") String cCode);
}
