package com.midea.cloud.srm.model.inq.inquiry.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.inq.inquiry.entity.QuotaAdjust;
import lombok.Data;

import java.util.List;

@Data
public class QuotaAdjustDTO extends QuotaAdjust {
    /**
     * 业务实体ID
     */
    private Long orgId;
    /**
     * 物料小类ID
     */
    private Long categoryId;
    /**
     * 物料编码
     */
    private String itemCode;
    /**
     * 供应商id
     */
    private Long vendorId;

    /**
     * 供应商编码
     */
    private String vendorCode;

    /**
     * 供应商名称
     */
    private String vendorName;
    /**
     * 配额调整实体
     */
    private QuotaAdjust quotaAdjust;
    /**
     * 寻源单号对应的价格审批单列表
     */
    private List<QuotaSourceDTO> quotaSourceDTOList;
    /**
     * 配额调整附件列表
     */
    private  List<Fileupload> quotaAdjustFile;
}
