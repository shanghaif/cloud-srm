package com.midea.cloud.srm.model.supplierauth.review.dto;

import com.midea.cloud.srm.model.common.BaseDTO;
import com.midea.cloud.srm.model.supplierauth.entry.entity.FileRecord;
import com.midea.cloud.srm.model.supplierauth.review.entity.*;
import lombok.Data;

import java.util.List;

/**
 * <pre>
 *  功能名称描述:  现场评审DTO
 * </pre>
 *
 * @author chensl26@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-3-15 18:42
 *  修改内容:
 * </pre>
 */
@Data
public class SiteFormDTO extends BaseDTO {

    /**
     * 现场评审单基本信息
     */
    private SiteForm siteForm;

    /**
     * 现场评审单记录明细(ceea:认证结果)
     */
    private List<FileRecord> fileRecords;

    /**
     * 合作ou日志集
     */
    private List<OrgJournal> orgJournals;

    /**
     * 合作品类日志集
     */
    private List<CateJournal> cateJournals;

//    /**
//     * 现场评审单附件
//     */
//    private List<SiteAttach> siteAttaches;
//
//    /**
//     * 组织与品类关系日志
//     */
//    private List<OrgCateJournal> orgCateJournals;

    /**
     * 暂存or提交
     */
    private String opType;

    /**
     * 功能菜单ID
     */
    private Long menuId;
    private String processType;//提交审批Y，废弃N

}
