package com.midea.cloud.srm.model.inq.price.dto;

import com.midea.cloud.srm.model.inq.price.entity.ApprovalFile;
import com.midea.cloud.srm.model.inq.price.entity.ApprovalHeader;
import com.midea.cloud.srm.model.inq.price.entity.ApprovalItem;
import com.midea.cloud.srm.model.inq.price.vo.ApprovalBiddingItemVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 *
 *
 * <pre>
 * 插入价格审批单
 * </pre>
 *
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 *
 *          <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020年4月15日 上午9:02:54
 *  修改内容:
 *          </pre>
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class InsertPriceApprovalDTO implements Serializable {

	private static final long serialVersionUID = 4734794493711321570L;

	private ApprovalHeader approvalHeader;// 价格审批单头表
	private List<ApprovalItem> approvalItemList;// 价格审批单行表
	private List<ApprovalFile> approvalFiles;//附件
	private List<ApprovalBiddingItemVO> approvalBiddingItemList;//中标行信息
	private String processType;//提交审批Y，废弃N
}
