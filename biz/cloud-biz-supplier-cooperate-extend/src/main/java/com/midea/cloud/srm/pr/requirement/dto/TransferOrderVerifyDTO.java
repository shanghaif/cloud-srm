package com.midea.cloud.srm.pr.requirement.dto;

import com.midea.cloud.srm.model.base.dict.entity.DictItem;
import com.midea.cloud.srm.model.cm.contract.vo.ContractVo;
import com.midea.cloud.srm.model.inq.price.entity.PriceLibrary;
import com.midea.cloud.srm.model.pm.pr.requirement.dto.RequirementManageDTO;
import com.midea.cloud.srm.model.supplier.info.entity.OrgCategory;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <pre>
 *  申请转订单校验数据
 * </pre>
 *
 * @author chenjj120@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/11/23 下午 03:16
 *  修改内容:
 * </pre>
 */
@Data
public class TransferOrderVerifyDTO implements Serializable {

    //组织品类关系
    private Map<String, Map<String, List<OrgCategory>>> orgCatMapT;
    //框架协议合同列表，按供应商、业务实体
    private Map<String, Map<String, List<ContractVo>>> frameContractMapT;
    //价格库，按库存组织和物料编码查询
    Map<String, Map<String, Map<String, List<PriceLibrary>>>> priceLibraryMapT;
    //按业务实体、库存和物料
    private Map<String, Map<String, Map<String, List<ContractVo>>>> notFrameContractMapT;
    //目录化的数据
    private List<RequirementManageDTO> directRequirement;
    //非目录化的数据
    private List<RequirementManageDTO> notDirectRequirement;

    private Set<String> codeSet;

    private List<DictItem> requireEntity;

    private Map<String, Set<String>> buCodeMap;
}
