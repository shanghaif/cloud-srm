package com.midea.cloud.srm.base.quicksearch.service;

import com.midea.cloud.srm.model.base.quicksearch.entity.QuicksearchAttrConfig;

import java.util.List;

/**
 * <pre>
 *  快速查询属性配置 接口
 * </pre>
 *
 * @author fengdc3@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-3-5 17:04
 *  修改内容:
 * </pre>
 */
public interface IQuicksearchAttrConfigService {

    /**
     * 查询属性配置列表
     * @param param
     * @return
     */
    List<QuicksearchAttrConfig> listQuicksearchAttrConfig(QuicksearchAttrConfig param);

    /**
     * 根据快速查询id删除属性
     * @param id
     */
    void removeByQuicksearchId(Long id);
}
