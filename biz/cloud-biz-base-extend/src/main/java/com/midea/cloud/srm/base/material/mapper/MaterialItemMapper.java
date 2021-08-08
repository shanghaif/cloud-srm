package com.midea.cloud.srm.base.material.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.midea.cloud.srm.model.base.material.MaterialItem;
import com.midea.cloud.srm.model.base.material.dto.ItemCodeUserPurchaseDto;
import com.midea.cloud.srm.model.base.material.dto.MaterialQueryDTO;
import com.midea.cloud.srm.model.base.material.dto.PurchaseCatalogQueryDto;
import com.midea.cloud.srm.model.base.material.vo.MaterialItemVo;
import com.midea.cloud.srm.model.cm.accept.dto.AcceptOrderDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 物料维护 Mapper 接口
 * </p>
 *
 * @author zhuwl7@meicloud.com
 * @since 2020-03-06
 */
public interface MaterialItemMapper extends BaseMapper<MaterialItem> {

    List<ItemCodeUserPurchaseDto> queryItemIdUserPurchase(ItemCodeUserPurchaseDto itemCodeUserPurchaseDto);

    List<MaterialItem> queryMaterialItemByCodes(@Param("materialCodeList") List<String> materialCodeList);

    List<MaterialQueryDTO> listPageByParam(MaterialQueryDTO materialQueryDTO);

    List<MaterialQueryDTO> listPageByWrapper(@Param("materialQueryDTO") MaterialQueryDTO materialQueryDTO);

	List<MaterialItemVo> listByParam(@Param("materialQueryDTO") MaterialQueryDTO materialQueryDTO,@Param("longs") List<Long> longs);

    /**
     * @Description 采购采购目录
     * @Param [materialQueryDTO]
     * @return
     * @Author haiping2.li@meicloud.com
     * @Date 2020.09.18 10:30
     **/
    List<MaterialQueryDTO> ceeaListPurchaseCatalogPage(MaterialQueryDTO materialQueryDTO);


    /**
     * @Description 物料目录化批量查询采购目录
     * @Param: [materialQueryDTO]
     * @Return: java.util.List<com.midea.cloud.srm.model.base.material.dto.MaterialQueryDTO>
     * @Author: dengyl23@meicloud.com
     * @Date: 2020/9/27 10:31
     */
    List<PurchaseCatalogQueryDto> PurchaseCatalogQueryBatch(List<PurchaseCatalogQueryDto> purchaseCatalogQueryDtoList);

    List<MaterialItem> listAllForTranferOrder(MaterialQueryDTO materialQueryDTO);

    List<MaterialItem> listAllForImportPriceLibrary();

    List<MaterialQueryDTO> ceeaListPurchaseCatalogPage2(MaterialQueryDTO materialQueryDTO);

    /**
     * 非目录优化查询采购目录数据
     * @param materialQueryDTO
     * @return
     */
    List<MaterialQueryDTO> queryMaterialQueryDTOByCatalogingN (MaterialQueryDTO materialQueryDTO);

    /**
     * 非目录优化查询采购目录数据
     * @param materialQueryDTO
     * @return
     */
    List<MaterialQueryDTO> queryMaterialQueryDTOByCatalogingY (MaterialQueryDTO materialQueryDTO);

    /**
     * 非目录优化查询采购目录数据
     * @param materialQueryDTO
     * @return
     */
    List<MaterialQueryDTO> queryMaterialQueryDTOByCatalogingY1 (MaterialQueryDTO materialQueryDTO);


    List<MaterialItem> ListMaterialItemByCategoryCode(@Param(Constants.WRAPPER) QueryWrapper<MaterialItem> wrapper);
}

