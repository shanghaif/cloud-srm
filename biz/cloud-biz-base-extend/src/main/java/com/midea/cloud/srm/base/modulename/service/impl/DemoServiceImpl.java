package com.midea.cloud.srm.base.modulename.service.impl;

import com.midea.cloud.srm.model.base.modulename.entity.Demo;
import com.midea.cloud.srm.base.modulename.mapper.DemoMapper;
import com.midea.cloud.srm.base.modulename.service.IDemoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
*  <pre>
 *  演示 服务实现类
 * </pre>
*
* @author huanghb14@example.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-02-12 13:25:26
 *  修改内容:
 * </pre>
*/
@Service
public class DemoServiceImpl extends ServiceImpl<DemoMapper, Demo> implements IDemoService {


    @Override
    public int countRecord() {
        return getBaseMapper().countRecord();
    }

}
