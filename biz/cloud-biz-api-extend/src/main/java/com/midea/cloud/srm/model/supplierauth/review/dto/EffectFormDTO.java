package com.midea.cloud.srm.model.supplierauth.review.dto;

import com.midea.cloud.srm.model.common.BaseDTO;
import com.midea.cloud.srm.model.supplierauth.review.entity.BankJournal;
import com.midea.cloud.srm.model.supplierauth.review.entity.EffectForm;
import com.midea.cloud.srm.model.supplierauth.review.entity.FinanceJournal;
import com.midea.cloud.srm.model.supplierauth.review.entity.OrgCateJournal;
import lombok.Data;

import java.util.List;

/**
 * <pre>
 *  功能名称描述:   供方生效DTO
 * </pre>
 *
 * @author chensl26@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-3-20 10:59
 *  修改内容:
 * </pre>
 */
@Data
public class EffectFormDTO extends BaseDTO{

    private EffectForm effectForm;

    private List<OrgCateJournal> orgCateJournals;

    private List<BankJournal> bankJournals;

    private List<FinanceJournal> financeJournals;

    private Long menuId;
}
