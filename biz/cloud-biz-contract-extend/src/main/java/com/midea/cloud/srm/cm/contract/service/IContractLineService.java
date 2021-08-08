package com.midea.cloud.srm.cm.contract.service;

import com.midea.cloud.srm.model.cm.contract.entity.ContractLine;
import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.cm.contract.entity.ContractMaterial;

import java.util.List;

/**
*  <pre>
 *  合同行表 服务类
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-27 10:12:07
 *  修改内容:
 * </pre>
*/
public interface IContractLineService extends IService<ContractLine> {
    /**
     * 查询寻缘物料
     * @param contractMaterial
     * @return
     */
    List<ContractMaterial> getMaterialsByContractMaterial(ContractMaterial contractMaterial);
}
