package com.midea.cloud.srm.base.dict.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.base.dict.dto.DictItemDTO;
import com.midea.cloud.srm.model.base.dict.dto.ExcelDictItemDTO;
import com.midea.cloud.srm.model.base.dict.entity.DictItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 字典条目 Mapper 接口
 * </p>
 *
 * @author zhuwl7@meicloud.com
 * @since 2020-02-19
 */
public interface DictItemMapper extends BaseMapper<DictItem> {

    int findByNameOrCode(@Param("dictItemName")String dictItemName,
                         @Param("dictItemCode")String dictItemCode,
                         @Param("id")Long id,@Param("dictId")Long dictId);

    List<DictItem> queryPageByConditions(DictItem dictItem);

    DictItemDTO queryById(Long id);

    List<DictItemDTO> listAllByDictCode(@Param("dictCode")String dictCode,@Param("language") String language);

    List<DictItemDTO> listByDictCode(List<String> dictCodes,@Param("language") String language);

    List<DictItemDTO> listAllByParam(DictItemDTO requestDto);

    List<DictItemDTO> getDictItemsByDictCodeAndDictItemNames(@Param("dictCode") String dictCode, @Param("dictItemNames") List<String> dictItemNames);
    
    List<DictItemDTO> queryProductType();

    List<DictItem> queryDictItemBy(@Param("itemLanguage") String itemLanguage,
                                   @Param("dictId") Long dictId,
                                   @Param("itemDescription") String itemDescription);

    List<ExcelDictItemDTO> getExportData(@Param("ids")List<Long> ids);

    List<ExcelDictItemDTO> getRecordsByDictProperties(@Param("dictCode")String dictCode,@Param("language")String language,@Param("dictName")String dictName);
}
