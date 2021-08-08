package com.midea.cloud.srm.cm.contract.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.cm.contract.dto.ContractDescExportDto;
import com.midea.cloud.srm.model.cm.accept.dto.AcceptOrderDTO;
import com.midea.cloud.srm.model.cm.contract.dto.ContractHeadDTO;
import com.midea.cloud.srm.model.cm.contract.dto.ContractItemDto;
import com.midea.cloud.srm.model.cm.contract.dto.ContractMaterialDto2;
import com.midea.cloud.srm.model.cm.contract.entity.ContractHead;
import com.midea.cloud.srm.model.cm.contract.entity.ContractMaterial;
import com.midea.cloud.srm.model.cm.contract.vo.ContractHeadVO;
import com.midea.cloud.srm.model.cm.contract.vo.ContractVo;
import com.midea.cloud.srm.model.inq.price.dto.PriceLibraryContractResDTO;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 合同头表 Mapper 接口
 * </p>
 *
 * @author chensl26@meiCloud.com
 * @since 2020-05-27
 */
public interface ContractHeadMapper extends BaseMapper<ContractHead> {

    List<ContractHead> listPageByParam(ContractHeadDTO contractHeadDTO);

    List<ContractHead> listPageByParamExport(ContractHeadDTO contractHeadDTO);

    Long queryCount (ContractHeadDTO contractHeadDTO);

    List<ContractDescExportDto> queryContractDescExportDate(ContractHeadDTO contractHeadDTO);

    Long queryContractDescExportcount (ContractHeadDTO contractHeadDTO);



    /**
     * @Description 获取上架关联合同列表
     * @Param: [contractHeadDTO]
     * @Return: java.util.List<com.midea.cloud.srm.model.cm.contract.entity.ContractHead>
     * @Author: dengyl23@meicloud.com
     * @Date: 2020/10/4 11:35
     */
    List<PriceLibraryContractResDTO> getOnShelvesContractList(Map<String, Object> param);

    List<ContractMaterialDto2> queryContractItem(ContractItemDto contractItemDto);

    /**
     * chenwj 查询所有有效合同物料
     *
     * @return
     */
    List<ContractVo> listAllEffectiveCM(@Param("contractItemDto") ContractItemDto contractItemDto);

    /**
     * 更新合同总金额
     *
     * @param contractHeadId
     */
    void updateContractAmount(@Param("contractHeadId") Long contractHeadId);

    /**
     * 根据条件查询有效合同
     *
     * @param contractMaterial add by chensl26
     * @return
     */
    List<ContractVo> listEffectiveContractByParam(ContractMaterial contractMaterial);

    List<ContractVo> listEffectiveContractByInvCodeAndMaterialCode(@Param("invCodes") Collection<String> invCodes,
                                                                   @Param("materialCodes") Collection<String> materialCodes,
                                                                   @Param("from") LocalDate from,
                                                                   @Param("to") LocalDate to,
                                                                   @Param("frame") String frame,
                                                                   @Param("orgCodes") Collection<String> orgCodes
    );

    //获取业务实体合同
    List<ContractVo> listEffectiveContractByOrgId(@Param("orgCodes") Collection<String> orgCodes, @Param("now") LocalDate now);


    List<ContractHead> listPageEffectiveByParam(ContractHeadDTO contractHeadDTO);

    List<ContractHead> listContractHeadByVendorIdAndIsFrameworkAgreement(@Param("vendorId") Long vendorId, @Param("isFrameworkAgreement") String isFrameworkAgreement);


    List<ContractHeadVO> getContractHeadVOList(@Param(Constants.WRAPPER) QueryWrapper<ContractHead> wrapper);
}
