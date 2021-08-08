package com.midea.cloud.srm.model.supplier.demotion.dto;

import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.supplier.demotion.entity.CompanyDemotion;
import com.midea.cloud.srm.model.supplier.demotion.entity.CompanyDemotionCategory;
import com.midea.cloud.srm.model.supplier.demotion.entity.CompanyDemotionOrg;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021/1/7 14:56
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
public class CompanyDemotionDTO {

    /**
     * 单据头
     */
    private CompanyDemotion companyDemotion;

    /**
     * 升降级品类行
     */
    private List<CompanyDemotionCategory> companyDemotionCategories;

    /**
     * 升降级OU行
     */
    private List<CompanyDemotionOrg> companyDemotionOrgs;

    /**
     * 升降级附件行
     */
    private List<Fileupload> fileUploads;

    //提交审批Y，废弃N
    private String processType;

    /**
     * 流程id
     */
    private String flowInstanceId;

}
