package com.midea.cloud.srm.base.dict.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.base.dict.entity.Dict;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 字典表 Mapper 接口
 * </p>
 *
 * @author zhuwl7@meicloud.com
 * @since 2020-02-19
 */
public interface DictMapper extends BaseMapper<Dict> {

     int findByLanguageAndCode(@Param("dictCode") String dictCode, @Param("language") String language
     ,@Param("id")Long id);

     int findByNameAndId(@Param("dictName")String dictName,@Param("id")Long id);

     List<Dict> queryPageByConditions(Dict dict);
}
