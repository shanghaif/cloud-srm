package com.midea.cloud.srm.model.cm.contract.dto;

import com.midea.cloud.srm.model.cm.annex.Annex;
import com.midea.cloud.srm.model.cm.contract.entity.*;
import com.midea.cloud.srm.model.cm.model.entity.ModelLine;
import com.midea.cloud.srm.model.common.BaseDTO;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <pre>
 *  合同DTO
 * </pre>
 *
 * @author chensl26@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-5-27 10:46
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
public class ContractDTO extends BaseDTO{

    /**
     * 合同头信息
     */
    private ContractHead contractHead;

    /**
     *合同行表
     */
    private List<ModelLine> modelLines;

    /**
     * 合同行信息
     */
    private List<ContractLine> contractLines;

    /**
     * 合同附件
     */
    private List<Fileupload> fileuploads;

    /**
     * 合同附件
     */
    private List<Annex> annexes;

    /**
     * 付款计划
     */
    private List<PayPlan> payPlans;

    /**
     * 合同物料
     */
    private List<ContractMaterial> contractMaterials;

    /**
     * 合作伙伴
     */
    private List<ContractPartner> contractPartners;

    /**
     * 关闭合同附件
     */
    private List<CloseAnnex> closeAnnexes;

    private String processType;//提交审批Y，废弃N
}
