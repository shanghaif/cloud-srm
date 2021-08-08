package com.midea.cloud.srm.model.bid.purchaser.projectmanagement.projectpublish.vo;

import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.entity.BidRequirement;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.entity.Biding;
import lombok.Data;

import java.io.Serializable;

/**
 * <pre>
 *  项目管理-项目发布 视图对象
 * </pre>
 *
 * @author fengdc3@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-4-3 14:14:03
 *  修改内容:
 * </pre>
 */
@Data
public class ProjectPublishVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private BidRequirement bidRequirement;
    private Biding biding;
    private String approvalBy;
    private String createBy;
}
