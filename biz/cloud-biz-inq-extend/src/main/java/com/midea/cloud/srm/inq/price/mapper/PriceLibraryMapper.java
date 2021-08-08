package com.midea.cloud.srm.inq.price.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.midea.cloud.srm.model.inq.price.dto.PriceLibraryRequestDTO;
import com.midea.cloud.srm.model.inq.price.entity.PriceLibrary;
import com.midea.cloud.srm.model.inq.price.entity.PriceLibraryOld;
import com.midea.cloud.srm.model.inq.price.vo.PriceLibraryVO;
import com.midea.cloud.srm.model.inq.sourcingresult.vo.FindPriceTrendParameter;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 报价-价格目录表 Mapper 接口
 * </p>
 *
 * @author linxc6@meiCloud.com
 * @since 2020-03-25
 */
public interface PriceLibraryMapper extends BaseMapper<PriceLibrary> {

    /**
     * 根据 物料编号+物料描述+供应商编号+业务实体+库存组织+到货地点+价格类型+价格有效期自+价格有效期至 查询数据
     *
     * @param item
     * @return
     */
    List<PriceLibrary> ceeaFindListByParams(PriceLibrary item);

    /**
     * 根据 物料编号+物料描述+供应商编号+业务实体+库存组织+到货地点+价格类型 查询数据
     *
     * @param item
     * @return
     */
    List<PriceLibrary> ceeaFindListByParams2(PriceLibrary item);

    /**
     * 查询某业务实体下的物料是否有有效期
     *
     * @param priceLibrary
     * @return
     */
    Integer findEffectivePriceCount(PriceLibrary priceLibrary);

    /**
     * 查询某业务实体下 拥有有效价格 的物料
     *
     * @param priceLibrary
     * @return
     */
    List<PriceLibrary> findEffectivePrice(PriceLibrary priceLibrary);

    Integer getThreeYearsBidFrequency(@Param("param") Map<String, Object> param);

    List<PriceLibrary> listAllEffective(PriceLibrary priceLibrary);

    List<PriceLibraryVO> listPageCopy(@Param(Constants.WRAPPER) QueryWrapper<PriceLibrary> wrapper);

    List<PriceLibrary> listAllPriceForImport();

    PriceLibrary getLatest(PriceLibrary priceLibrary);

    List<PriceLibrary> getLastestBatch(@Param("orgIds") Collection<Long> orgIds, @Param("itemIds") Collection<Long> itemsIds);

    List<PriceLibrary> getLatestFive(PriceLibraryRequestDTO priceLibraryRequestDTOList);

    /**
     * 获取 价格趋势集
     *
     * @param parameter 查询参数
     * @return 价格趋势集
     */
    List<PriceLibrary> findPriceTrends(@Param("parameter") FindPriceTrendParameter parameter);

    /**
     * 与当前行物料编码相同，"是否已上架"为是，且在价格有效期内的物料，
     * 并按物料编码+物料描述+供应商去重
     *
     * @param priceLibrary
     * @return
     */
    List<PriceLibrary> listForMaterialSecByBuyer(PriceLibrary priceLibrary);

    /**
     * 物料维护-供应商端 根据供应商 + 上架物料 + 价格有效期 查询
     * 并按物料编码+物料描述+供应商去重
     *
     * @param priceLibrary
     * @return
     */
    List<PriceLibrary> listForMaterialSecByVendor(PriceLibrary priceLibrary);

    List<Long> queryItemIdByOuAndInv(@Param("ouId") Long ouId, @Param("invId") Long invId);

    List<PriceLibrary> listEffectiveForCreateOrder(@Param("itemCodes") Collection<String> itemCodes,
                                                   @Param("itemDescs") Collection<String> itemDesc,
                                                   @Param("orgCodes") Collection<String> orgCodes,
                                                   @Param("invCodes") Collection<String> invCodes
    );

    List<PriceLibrary> listPriceLibraryForTransferOrders(@Param("materialIds") Collection<Long> materialIds,
                                                         @Param("materialNames") Collection<String> materialNames,
                                                         @Param("organizationIds") Collection<Long> organizationIds,
                                                         @Param("vendorIds") Collection<Long> vendorIds);


    List<PriceLibraryOld> listPriceLibraryOld(@Param("orgCode") String orgCode, @Param("itemCode") String itemCode,@Param("itemDesc")String itemDesc);
}
