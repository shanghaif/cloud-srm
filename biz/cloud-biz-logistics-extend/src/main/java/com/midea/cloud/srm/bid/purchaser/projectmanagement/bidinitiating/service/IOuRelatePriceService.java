package com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidinitiating.dto.OuRelatePriceCreateBatchDto;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidinitiating.entity.OuRelatePrice;

import java.math.BigDecimal;
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
 *  修改日期: 2020/9/4 15:32
 *  修改内容:时间赶，写的烂，请见谅
 * </pre>
 */
public interface IOuRelatePriceService extends IService<OuRelatePrice> {
    /**
     * 创建ou组价格关系，每个项目需求以物料进行分组，每个组下面都一个基准ou组
     * 对于基准ou组
     *
     * @param dto
     * @return
     */
    Boolean createOuRelatePrice(OuRelatePriceCreateBatchDto dto);

    /**
     * 根据需求头和基准ou返回关联的价格关系
     * @param headerId
     * @param baseOuId
     * @return
     */
    List<OuRelatePrice> listOuRelatePrices(Long headerId, Long baseOuId);

    /**
     * 更新价格差
     * @param id
     * @param price
     * @return
     */
    Boolean updatePrice(Long id, BigDecimal price);

    /**
     * 删除关联id
     * @param id
     * @return
     */
    Boolean deleteById(Long id);

    /**
     * 删除报价行关系
     * @param requirementLineId
     * @return
     */
     Boolean deleteByRequirementLineId(Long requirementLineId);
    /**
     * 根据报价行获取其他行的价格信息
     * @return
     */
    List<OuRelatePrice> listOuRelatePriceByOrderLine(Long baseOuRequireLineId, BigDecimal currentPrice);
}
