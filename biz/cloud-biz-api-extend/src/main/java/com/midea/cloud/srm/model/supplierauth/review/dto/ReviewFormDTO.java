package com.midea.cloud.srm.model.supplierauth.review.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.midea.cloud.srm.model.common.BaseDTO;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.supplierauth.entry.entity.FileRecord;
import com.midea.cloud.srm.model.supplierauth.review.entity.*;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * <pre>
 *  功能名称描述:    资质审查单据DTO
 * </pre>
 *
 * @author chensl26@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-3-11 11:43
 *  修改内容:
 * </pre>
 */
@Data
public class ReviewFormDTO extends BaseDTO {

    ReviewForm reviewForm;
    @NotNull(message = "供应商的银行信息不能为空!")
    @Size(min = 1, message = "至少维护一条银行信息!")
    @Valid
    List<BankJournal> bankJournals;
    @NotNull(message = "供应商的地点信息不能为空!")
    @Size(min = 1, message = "至少维护一条地点信息!")
    @Valid
    List<SiteJournal> siteJournals;
    List<OrgJournal> orgJournals;
    List<CateJournal> cateJournals;
    List<ReviewFormExp> reviewFormExps;
    
    List<FileRecord> fileRecords;//附件
//    List<OrgCateJournal> orgCateJournals;
//    List<FinanceJournal> financeJournals;

    /**
     * 操作类型:暂存,提交
     */
    String opType;

    /**
     * 功能菜单ID
     */
    Long menuId;

    private String processType;//提交审批Y，废弃N

}
