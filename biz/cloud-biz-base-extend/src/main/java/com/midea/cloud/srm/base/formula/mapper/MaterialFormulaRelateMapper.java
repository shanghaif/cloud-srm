package com.midea.cloud.srm.base.formula.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.base.formula.dto.query.MaterialFormulaRelateQueryDto;
import com.midea.cloud.srm.model.base.formula.entity.MaterialFormulaRelate;
import com.midea.cloud.srm.model.base.formula.vo.MaterialFormulaRelateVO;
import org.apache.ibatis.annotations.Param;


import java.util.List;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author tanjl11@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/2 9:46
 *  修改内容:
 * </pre>
 */
public interface MaterialFormulaRelateMapper extends BaseMapper<MaterialFormulaRelate> {
    List<MaterialFormulaRelateVO> getMaterialFormulaRelateMapper(@Param("query") MaterialFormulaRelateQueryDto dto);
}
