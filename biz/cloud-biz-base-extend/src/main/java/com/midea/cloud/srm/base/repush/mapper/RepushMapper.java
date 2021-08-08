package com.midea.cloud.srm.base.repush.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.base.repush.entity.Repush;

import java.util.List;

/**
 * <pre>
 *  重推 Mapper 接口
 * </pre>
 *
 * @author huanghb14@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-10-6 14:50
 *  修改内容:
 * </pre>
 */
public interface RepushMapper extends BaseMapper<Repush> {

    List<Repush> listEffective();

}
