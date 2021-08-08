package com.midea.cloud.srm.bid.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.srm.bid.mapper.LgtFileConfigMapper;
import com.midea.cloud.srm.bid.service.ILgtFileConfigService;
import com.midea.cloud.srm.model.logistics.bid.entity.LgtFileConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
*  <pre>
 *  供方必须上传附件配置表 服务实现类
 * </pre>
*
* @author wangpr@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021-01-06 09:54:38
 *  修改内容:
 * </pre>
*/
@Service
public class LgtFileConfigServiceImpl extends ServiceImpl<LgtFileConfigMapper, LgtFileConfig> implements ILgtFileConfigService {

    @Autowired
    private LgtFileConfigMapper fileConfigMapper;

}
