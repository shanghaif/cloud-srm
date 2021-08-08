package com.midea.cloud.srm.base.seq.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.base.seq.entity.SeqDefinition;

import java.util.List;
import java.util.Map;

/**
 * <pre>
 * 序列号服务 Mapper 接口
 * </pre>
 *
 * @author huanghb14@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-3-11 17:44
 *  修改内容:
 * </pre>
 */
public interface SeqDefinitionMapper extends BaseMapper<SeqDefinition>  {

    List<SeqDefinition> getDefinition(Map<String, Object> paramMap);

    List<SeqDefinition> selectForUpdate(Map<String, Object> params);

}
