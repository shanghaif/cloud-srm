package com.midea.cloud.srm.cm.contract.mapper;

import com.midea.cloud.srm.model.cm.contract.dto.ContractMaterialDTO;
import com.midea.cloud.srm.model.cm.contract.entity.ContractMaterial;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.cm.contract.vo.ContractVo;

import java.util.List;

/**
 * <p>
 * 合同物料 Mapper 接口
 * </p>
 *
 * @author chensl26@meiCloud.com
 * @since 2020-05-27
 */
public interface ContractMaterialMapper extends BaseMapper<ContractMaterial> {

    List<ContractMaterialDTO> listPageMaterialDTOByParm(ContractMaterialDTO contractMaterialDTO);

}
