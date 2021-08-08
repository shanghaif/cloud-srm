package com.midea.cloud.srm.model.supplierauth.orgcategory.dto;

import com.midea.cloud.srm.model.common.BaseDTO;
import com.midea.cloud.srm.model.supplierauth.orgcategory.entity.OrgCatForm;
import com.midea.cloud.srm.model.supplierauth.review.entity.OrgCateJournal;
import lombok.Data;

import java.util.List;

/**
 * <pre>
 *  功能名称描述:  合作终止单据DTO
 * </pre>
 *
 * @author chensl26@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-3-25 16:21
 *  修改内容:
 * </pre>
 */
@Data
public class OrgCatFormDTO extends BaseDTO {

    private OrgCatForm orgCatForm;

    private List<OrgCateJournal> orgCateJournals;

    private Long menuId;
}
