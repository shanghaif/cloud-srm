package com.midea.cloud.srm.base.workflow.consumer.service;

import com.midea.cloud.srm.model.workflow.dto.WorkflowCreateResponseDto;

public interface IFlowService {
	public WorkflowCreateResponseDto approved(String valueByCode, Long formInstanceId) throws Exception;
}
