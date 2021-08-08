package com.midea.cloud.srm.model.inq.price.dto;

import com.midea.cloud.srm.model.cm.contract.entity.ContractHead;
import com.midea.cloud.srm.model.inq.price.entity.PriceLibrary;
import lombok.Data;

import java.util.List;

/**
 * <pre>
 *  功能名称
 * </pre>
 * 上架物料入参
 * @author dengyl23@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/21 13:44
 *  修改内容:
 * </pre>
 */
@Data
public class PriceLibraryPutOnShelvesDTO {

    /**
     * 上架物料列表
     */
    List<PriceLibrary> priceLibraryList;
    /**
     * 查询所选的合同
     */
    Long contractHeadId;
}
