package com.midea.cloud.flow.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import com.midea.cloud.flow.service.IWorkflowSelfService;

/**
 * 工作流操作，只用于自带界面模式，对于自带页面的情况，所有界面操作都独立的类中实现，因为这是只有这个模式特有的，在这里实现适配第三方工作流
 * @author lizl7
 *
 */
@Service
public class WorkflowSelfServiceImpl implements IWorkflowSelfService {

	
	/**
	 * 提交流程
	 */
	@Override
	public String submit(Long businessId, String businessType) throws Exception {
		return null;
	}
	
	
	/**
	 * 审批
	 * @param id
	 * @param type
	 * @return
	 * @throws Exception
	 */
	@Override
	public String audit(Long businessId, String businessType)  throws Exception {
		return null;
	}


	/**
	 * 查询节点，用于提交
	 * @param id
	 * @param type
	 * @return
	 * @throws Exception
	 */
	@Override
	public String queryFlowNodeForSubmit(Long businessId, String businessType) throws Exception {
		return null;
	}
	
	/**
	 * 根据业务id，业务类型获取工作流实例入口
	 * @param id
	 * @param type
	 * @return
	 * @throws Exception
	 */
	@Override
	public String queryFlowInstanceByBusiness(Long businessId, String businessType) throws Exception {
		return null;
	}

}
