package com.midea.cloud.srm.base.formula.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.base.formula.dto.create.BaseMaterialPriceCreateDto;
import com.midea.cloud.srm.model.base.formula.dto.query.BaseMaterialPriceQueryDto;
import com.midea.cloud.srm.model.base.formula.dto.update.BaseMaterialPriceUpdateDto;
import com.midea.cloud.srm.model.base.formula.dto.update.BaseMaterialUpdateDto;
import com.midea.cloud.srm.model.base.formula.entity.BaseMaterialPrice;
import com.midea.cloud.srm.model.base.formula.enums.StuffStatus;
import com.midea.cloud.srm.model.base.formula.vo.BaseMaterialPriceVO;

import java.util.List;

/**
 * <pre>
 *  基本材料价格表 服务类
 * </pre>
 *
 * @author tanjl11@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-26 16:27:13
 *  修改内容:
 * </pre>
 */
public interface IBaseMaterialPriceService extends IService<BaseMaterialPrice> {
    /**
     * 根据条件查询基本材料价格
     */
    PageInfo<BaseMaterialPrice> listBaseMaterialPriceByPage(BaseMaterialPriceQueryDto dto);

    /**
     * 创建基价
     * @param dto
     * @return
     */
    BaseMaterialPriceVO createBaseMaterialPrice(BaseMaterialPriceCreateDto dto);

    /**
     * 删除
     * @param id
     * @return
     */
    Boolean deleteBaseMaterialPriceById(Long id);

    /**
     * 修改状态
     * @param status
     * @param id
     * @return
     */
    Boolean updateStatus(StuffStatus status, Long id);


    BaseMaterialPriceVO updateById(BaseMaterialPriceUpdateDto dto);
}
