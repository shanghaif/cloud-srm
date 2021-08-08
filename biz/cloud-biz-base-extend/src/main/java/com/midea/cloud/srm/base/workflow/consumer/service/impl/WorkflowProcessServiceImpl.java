/**
 *
 */
package com.midea.cloud.srm.base.workflow.consumer.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.landray.kmss.WebServiceClient;
import com.landray.kmss.WebServiceConfig;
import com.landray.kmss.km.review.webservice.AttachmentForm;
import com.landray.kmss.km.review.webservice.IKmReviewWebserviceService;
import com.landray.kmss.km.review.webservice.KmReviewParamterForm;
import com.midea.cloud.common.utils.Byte2InputStream;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.base.workflow.consumer.service.IWorkflowProcessService;
import com.midea.cloud.srm.base.workflow.service.ISrmFlowBusWorkflowService;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.workflow.dto.AttachFileDto;
import com.midea.cloud.srm.model.workflow.dto.WorkflowCreateResponseDto;
import com.midea.cloud.srm.model.workflow.dto.WorkflowFormDto;
import com.midea.cloud.srm.model.workflow.entity.SrmFlowBusWorkflow;
import feign.Response;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import javax.xml.bind.DatatypeConverter;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * <pre>
 *  流程接口调用实现类
 * </pre>
 *
 * @author nianhuanh@meicloud.com
 * @version 1.00.00
 *
 * <pre>
  *  修改记录
  *  修改后版本:
  *  修改人:
  *  修改日期: 2020年8月10日 上午11:06:51
  *  修改内容:
 * </pre>
 */
@Service
public class WorkflowProcessServiceImpl implements IWorkflowProcessService {

	private static final Logger _logger = LoggerFactory.getLogger(WorkflowProcessServiceImpl.class);

	@Autowired
	private ISrmFlowBusWorkflowService workflowService;

	@Resource
	private FileCenterClient fileCenterClient;

	@Transactional
	@Override
	public WorkflowCreateResponseDto createFlow(WorkflowFormDto formDto) throws Exception{

		Assert.notNull(formDto, "参数不能为空");
		Assert.notNull(formDto.getFormInstanceId(), "业务实例ID不能为空");

		List<AttachmentForm> allAtts = createAllAtts(formDto.getFileDtos());
		formDto.setAttachmentForms(allAtts);

		//驳回后提交，流程优化
		JSONObject flowParam = null;
		if(ObjectUtils.isEmpty(formDto.getFlowParam())){
			flowParam = new JSONObject();
		}else {
			flowParam = formDto.getFlowParam();
		}
		if(StringUtils.isEmpty(flowParam.getString("auditNote"))){
			flowParam.put("auditNote","起草人节点意见");
		}


		formDto.setFlowParam(flowParam);


		// 调用接口
		String processId = getClientService().addReview(from(formDto));

		// 保存
		SrmFlowBusWorkflow workflow = new SrmFlowBusWorkflow();
		workflow.setId(IdGenrator.generate());
		workflow.setFormInstanceId(formDto.getFormInstanceId());
		workflow.setFlowInstanceId(processId);
		workflow.setFormTemplateId(formDto.getFdTemplateId());
		workflow.setFormRemark(formDto.getFormValues() == null ? null : formDto.getFormValues().toJSONString());

		workflowService.save(workflow);

		return WorkflowCreateResponseDto.build().processId(processId);
	}
	@Transactional
	@Override
	public WorkflowCreateResponseDto updateFlow(WorkflowFormDto formDto) throws Exception {
		Assert.notNull(formDto, "参数不能为空");
		Assert.notNull(formDto.getFormInstanceId(), "业务实例ID不能为空");

		SrmFlowBusWorkflow querySrmFlow = new SrmFlowBusWorkflow();
		String formInstanceId = formDto.getFormInstanceId();
		querySrmFlow.setFormInstanceId(formInstanceId);
		List<SrmFlowBusWorkflow> workflowList = workflowService.list(new QueryWrapper<>(querySrmFlow));
		Assert.isTrue((CollectionUtils.isNotEmpty(workflowList) && null != workflowList.get(0)),"修改的流程信息为空");
		SrmFlowBusWorkflow workflow = workflowList.get(0);
		formDto.setFlowInstanceId(workflow.getFlowInstanceId());
		formDto.setFdId(workflow.getFlowInstanceId());
		workflow.setLastUpdateDate(new Date());
		workflow.setFlowInstanceId(workflow.getFlowInstanceId());
		workflow.setFormRemark(formDto.getFormValues() == null ? null : formDto.getFormValues().toJSONString());

		//驳回后提交，流程优化
		JSONObject flowParam = null;
		if(ObjectUtils.isEmpty(formDto.getFlowParam())){
			flowParam = new JSONObject();
		}else {
			flowParam = formDto.getFlowParam();
		}
		if(StringUtils.isEmpty(flowParam.getString("auditNote"))){
			flowParam.put("auditNote","起草人节点意见");
		}
		if(StringUtils.isEmpty(flowParam.getString("operationType"))){
			flowParam.put("operationType","handler_pass");
		}

		formDto.setFlowParam(flowParam);

		// 调用流程更新接口,隆基要求清空附件
		String processId = getClientService().updateReviewInfo(from(formDto));

		List<AttachmentForm> allAtts = createAllAtts(formDto.getFileDtos());
		formDto.setAttachmentForms(allAtts);
		String approveProcessId="";
		try {
			 approveProcessId = getClientService().approveProcess(from(formDto));
		}catch (Exception e){
			//workflowService.removeById(workflow.getId());
			Assert.isTrue(false,"错误信息："+approveProcessId+e.getMessage());
		}
		// 更新流程，业务关联表数据
		workflowService.updateById(workflow);

		return WorkflowCreateResponseDto.build().processId(processId);
	}

	/**
	 * 转换
	 * @param formDto
	 * @return
	 */
	private static KmReviewParamterForm from(WorkflowFormDto formDto) {
		KmReviewParamterForm form = new KmReviewParamterForm();
		BeanUtils.copyProperties(formDto, form);
//		form.setFdTemplateId(formDto.getFdTemplateId());
//		form.setDocSubject(formDto.getDocSubject());
		form.setDocCreator( (null != formDto.getDocCreator() ? formDto.getDocCreator().toJSONString() : null));
		form.setFormValues( null != formDto.getFormValues() ? formDto.getFormValues().toJSONString() : null);
		form.setDocProperty(null != formDto.getDocProperty() ? formDto.getDocProperty().toJSONString() : null);
		form.setFdKeyword(formDto.getFdKeyword() == null ? null : formDto.getFdKeyword().toJSONString());
		form.setFormValues(formDto.getFormValues() == null ? null : formDto.getFormValues().toJSONString());
		// add by chenwt24@meicloud.com    2020-10-20
		form.setFlowParam(formDto.getFlowParam() == null ? null : formDto.getFlowParam().toJSONString());

		if(!ObjectUtils.isEmpty(formDto.getAttachmentForms())){
			form.getAttachmentForms().addAll(formDto.getAttachmentForms());
		}
//		form.setFlowParam("{\"auditNote\" : \"请审核\"}");
		form.setFdId(formDto.getFlowInstanceId());
		return form;
	}

	private IKmReviewWebserviceService getClientService() {
		try {
			WebServiceConfig cfg = WebServiceConfig.getInstance();
			return (IKmReviewWebserviceService) WebServiceClient.callService(cfg.getAddress(), cfg.getServiceClass());
		} catch (Exception e) {
			_logger.error("error in obtainning webservice client ", e);
			throw new RuntimeException("error in obtainning webservice client");
		}

	}

	@Override
	public WorkflowCreateResponseDto submitFlow(WorkflowFormDto formDto) throws Exception {
		String formId = formDto.getFormInstanceId();
		SrmFlowBusWorkflow busWorkflow = workflowService.findByFormId(formId);
		// 新增
		if (Objects.isNull(busWorkflow)) {
			return createFlow(formDto);
		// 更新
		} else {
			return updateFlow(formDto);
		}
	}

	public List<AttachmentForm>createAllAtts(List<AttachFileDto> fileDtos) throws Exception {
		if(ObjectUtils.isEmpty(fileDtos)){
			return null;
		}
		ArrayList<AttachmentForm> attachmentForms = new ArrayList<>();
		for(AttachFileDto r:fileDtos){
			Fileupload fileupload = new Fileupload();
			fileupload.setFileuploadId(r.getFileId());
			List<Fileupload> fileuploadList = fileCenterClient.listPage(fileupload,null).getList();
			if(fileuploadList.size()>0){
				Fileupload file = fileuploadList.get(0);
				AttachmentForm att = createAtt(file.getFileSourceName(),file,r.getFdKey());

				attachmentForms.add(att);
			}
		}

		return attachmentForms;
	}

	/**
	 * 创建附件对象
	 */
	public AttachmentForm createAtt(String fileName,Fileupload fileUpload,String fdKey) throws Exception {
		AttachmentForm attForm = new AttachmentForm();
		attForm.setFdFileName(fileName);
		// 设置附件关键字，表单模式下为附件控件的id
		attForm.setFdKey(fdKey);

		Response response = fileCenterClient.downloadFileByParam(new Fileupload().setFileuploadId(fileUpload.getFileuploadId()));

		InputStream inputStream = response.body().asInputStream();
		byte[] bytes = Byte2InputStream.inputStream2byte(inputStream);
		//String dataHandler = DatatypeConverter.printBase64Binary(bytes);
        //byte[] bytes = response.request().requestBody().asBytes();

		String dataHandler = DatatypeConverter.printBase64Binary(bytes);
		//ByteDataSource fds = new ByteDataSource(bytes);
		//DataHandler dataHandler = new DataHandler(fds);
		//Assert.isTrue(false,"");
		attForm.setFdAttachment(dataHandler);

		return attForm;
	}

}
