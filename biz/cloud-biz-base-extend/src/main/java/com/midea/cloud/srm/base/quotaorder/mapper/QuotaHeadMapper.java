package com.midea.cloud.srm.base.quotaorder.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.base.quotaorder.QuotaHead;
import com.midea.cloud.srm.model.base.quotaorder.dto.QuotaHeadDto;

import java.util.List;

/**
*  <pre>
 *  配额比例头表 Mapper 接口
 * </pre>
*
* @author wangpr@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-12-07 17:00:12
 *  修改内容:
 * </pre>
*/
public interface QuotaHeadMapper extends BaseMapper<QuotaHead> {
    List<QuotaHeadDto> listPage(QuotaHeadDto quotaHeadDto);
}
