package com.midea.cloud.srm.base.repush.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.base.repush.entity.Repush;

import java.util.List;

/**
 * <pre>
 *  接口重推接口
 * </pre>
 *
 * @author huanghb14@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-10-6 16:37
 *  修改内容:
 * </pre>
 */
public interface RepushService extends IService<Repush> {

    List<Repush> listEffective();

    /**
     * 分页条件查询
     * @param repush
     * @return
     */
    List<Repush> listPageByParam(Repush repush);

    /**
     * 进行重推动作
     * @param repush
     * @return
     */
    public void doRePush(Repush repush);

}
