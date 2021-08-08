package com.midea.cloud.srm.model.inq.price.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.midea.cloud.srm.model.cm.contract.entity.ContractHead;
import com.midea.cloud.srm.model.inq.price.entity.PriceLibrary;
import lombok.Data;

import java.util.List;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author dengyl23@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/10/4 19:12
 *  修改内容:
 * </pre>
 */
@Data
public class PriceLibraryContractRequestDTO {

    List<PriceLibrary> priceLibraryList;
    /**
     * 库存组织名称
     */
    private String ceeaOrganizationName;
    /**
     * 业务实体名称
     */
    private String ceeaOrgName;

    /**
     * 供应商名称
     */
    private String vendorName;

    /**
     * 合同序号
     */
    private String contractNo;

    /**
     * 合同名称
     */
    private String contractName;
}
