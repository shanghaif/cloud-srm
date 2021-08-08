package com.midea.cloud.srm.bargaining.suppliercooperate.projectlist.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.entity.Biding;
import com.midea.cloud.srm.model.bargaining.suppliercooperate.vo.SupplierBidingVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 招标基础信息表 Mapper 接口
 * </p>
 *
 * @author zhuomb1@midea.com
 * @since 2020-03-31
 */
public interface SupplierBidingMapper extends BaseMapper<Biding> {
    /**
     * 通过查询参数获取招标项目列表--供应商端
     * @param supplierBidingVO
     * @return
     */
    List<SupplierBidingVO> getSupplierBidingList(@Param("supplierBidingVO") SupplierBidingVO supplierBidingVO);

    Integer countCreate(@Param("supplierBidingVO")SupplierBidingVO supplierBidingVO);

}
