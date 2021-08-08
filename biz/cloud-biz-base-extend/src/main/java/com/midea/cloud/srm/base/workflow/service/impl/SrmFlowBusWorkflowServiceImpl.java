/**
 * 
 */
package com.midea.cloud.srm.base.workflow.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.srm.base.workflow.mapper.SrmFlowBusWorkflowMapper;
import com.midea.cloud.srm.base.workflow.service.ISrmFlowBusWorkflowService;
import com.midea.cloud.srm.model.workflow.entity.SrmFlowBusWorkflow;

import java.util.List;
import java.util.Objects;

/**
 * <pre>
 *  
 * </pre>
 *
 * @author nianhuanh@meicloud.com
 * @version 1.00.00
 *
 * <pre>
  *  修改记录
  *  修改后版本:
  *  修改人:
  *  修改日期: 2020年8月11日 上午10:31:02
  *  修改内容:
 * </pre>
 */

@Service
public class SrmFlowBusWorkflowServiceImpl extends ServiceImpl<SrmFlowBusWorkflowMapper, SrmFlowBusWorkflow> implements ISrmFlowBusWorkflowService {

	@Autowired
	private SrmFlowBusWorkflowMapper busWorkflowMapper;
	
	@Override
	public SrmFlowBusWorkflow findByFormId(String formId) {
		QueryWrapper<SrmFlowBusWorkflow> busWorkflowWrapper = new QueryWrapper<SrmFlowBusWorkflow>();
		busWorkflowWrapper.eq("FORM_INSTANCE_ID", formId);
		return busWorkflowMapper.selectOne(busWorkflowWrapper);
	}

	/**
	 * 根据单据id获取OA流程id
	 * @param formId
	 * @return
	 */
    @Override
    public String getFlowIdByFormId(Long formId) {
		String flowId = "";

		String formInstanceId = String.valueOf(formId);
		List<SrmFlowBusWorkflow> list = this.list(Wrappers.lambdaQuery(SrmFlowBusWorkflow.class)
				.eq(SrmFlowBusWorkflow::getFormInstanceId, formInstanceId));
		if (CollectionUtils.isNotEmpty(list) && Objects.nonNull(list.get(0))) {
			flowId = list.get(0).getFlowInstanceId();
		}

		return flowId;
	}

}
