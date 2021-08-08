package com.midea.cloud.srm.inq.price.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.inq.price.domain.ApprovalHeaderParam;
import com.midea.cloud.srm.model.inq.price.domain.ApprovalHeaderResult;
import com.midea.cloud.srm.model.inq.price.domain.QuoteSelectedResult;
import com.midea.cloud.srm.model.inq.price.dto.ApprovalBiddingItemDto;
import com.midea.cloud.srm.model.inq.price.dto.ApprovalHeaderQueryRequestDTO;
import com.midea.cloud.srm.model.inq.price.entity.ApprovalBiddingItem;
import com.midea.cloud.srm.model.inq.price.entity.ApprovalHeader;
import com.midea.cloud.srm.model.inq.price.vo.ApprovalVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

/**
 * <p>
 * 价格审批单头表 Mapper 接口
 * </p>
 *
 * @author chensl26@meiCloud.com
 * @since 2020-04-08
 */
public interface ApprovalHeaderMapper extends BaseMapper<ApprovalHeader> {

    /**
     * 查询被选定的报价行
     */
    List<QuoteSelectedResult> queryQuoteSelected(@Param("inquiryId") Long inquiryId);

    /**
     * 结果审批单查询
     */
    List<ApprovalHeaderResult> queryByParam(@Param("param") ApprovalHeaderParam param);

    /**
     * 价格审批单查询
     * @param request
     * @return
     */
    List<ApprovalHeader> ceeaFindList(ApprovalHeaderQueryRequestDTO request);

    /**
     * 合同查找寻缘物料
     * @param approvalBiddingItemDto
     * @return
     */
    List<ApprovalBiddingItem> ceeaQueryByCm(ApprovalBiddingItemDto approvalBiddingItemDto);

}
