package com.midea.cloud.srm.model.bid.purchaser.projectmanagement.clearquestion.vo;

import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.clearquestion.entity.BidingAnswer;
import com.midea.cloud.srm.model.bid.suppliercooperate.vo.BidVendorFileVO;
import lombok.Data;

import java.util.List;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author zhuomb1@midea.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/4/21 8:47
 *  修改内容:
 * </pre>
 */
@Data
public class BidingAnswerVO extends BidingAnswer {
    /** 质疑单号 */
    private String questionNum;
    /** 质疑标题 */
    private String questionTitle;
    /** 质疑状态 */
    private String questionStatus;

    private List<Long> questionIds;

    List<BidVendorFileVO> files;
}