package com.midea.cloud.srm.bid.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.logistics.bid.entity.LgtRound;

/**
 * <pre>
 * 招标轮次
 * </pre>
 *
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020年3月25日 下午5:15:01
 *  修改内容:
 *          </pre>
 */
public interface ILgtRoundService extends IService<LgtRound> {

    /**
     * 公示结果
     * @param bidingId
     */
    void publicResult(Long bidingId);

}
