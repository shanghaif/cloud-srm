package com.midea.cloud.srm.base.workbench.service.impl;

import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.DateUtil;
import com.midea.cloud.dynamicds.bind.CheckModuleHolder;
import com.midea.cloud.dynamicds.enums.Module;
import com.midea.cloud.srm.base.workbench.mapper.CeeaWorkbenchMapper;
import com.midea.cloud.srm.base.workbench.service.CeeaIWorkbenchService;
import com.midea.cloud.srm.model.flow.query.dto.WorkbenchMyTaskDTO;
import com.midea.cloud.srm.model.flow.vo.WorkbenchMyTaskVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <pre>
 * 工作台我的任务
 * </pre>
 *
 * @author yourname@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 * 修改记录
 * 修改后版本:
 * 修改人:
 * 修改日期: 2020-9-22 15:37
 * 修改内容:
 * </pre>
 */
@Service
public class CeeaIWorkbenchServiceImpl implements CeeaIWorkbenchService {

    @Autowired
    CeeaWorkbenchMapper ceeaWorkbenchMapper;

    /*** 获取待办信息 ***/
    @Override
    public List<WorkbenchMyTaskVo> ceeaFindMyRunningProcess(WorkbenchMyTaskDTO workbenchMyTaskDTO) throws Exception {
        try {
            List<WorkbenchMyTaskVo> list = new ArrayList<>();
            Long userId = AppUserUtil.getLoginAppUser().getUserId();
            String userName = AppUserUtil.getLoginAppUser().getUsername();
            workbenchMyTaskDTO.setQueryType("process");
            if ("Y".equals(workbenchMyTaskDTO.getIfMove())) { //移动端代办
            		
				if ("BUYER".equals(AppUserUtil.getLoginAppUser().getUserType())) {
					workbenchMyTaskDTO.setUserId(userId);
					CheckModuleHolder.checkout(Module.BARGAIN);
					{
						// 询比价-审批状态已驳回状态
						workbenchMyTaskDTO.setFormStatus("REJECTED");
						list.addAll(ceeaWorkbenchMapper.findInqProcess(workbenchMyTaskDTO));
					}
					// 供应商协同模块
					CheckModuleHolder.checkout(Module.SUPCOOPERATE);
					{
						// 采购订单-已驳回和已拒绝
						list.addAll(ceeaWorkbenchMapper.findPurchaseOrderProcess(workbenchMyTaskDTO));
					}
				} else if ("VENDOR".equals(AppUserUtil.getLoginAppUser().getUserType())) {
					workbenchMyTaskDTO.setVendorId(AppUserUtil.getLoginAppUser().getCompanyId());
					workbenchMyTaskDTO.setQueryType("wait");
					CheckModuleHolder.checkout(Module.SUPCOOPERATE);
					{
						// 采购订单-已驳回和已拒绝
						list.addAll(ceeaWorkbenchMapper.findPurchaseOrderProcess(workbenchMyTaskDTO));
					}
					CheckModuleHolder.checkout(Module.BARGAIN);
					{
						// 询比价-审批状态已驳回状态
						list.addAll(ceeaWorkbenchMapper.findInqProcess(workbenchMyTaskDTO));
					}
				}
            } else {
            	// 供应商准入模块
                CheckModuleHolder.checkout(Module.SUP);
                {
                    // 资质审查-驳回状态
                    workbenchMyTaskDTO.setUserId(userId);
                    workbenchMyTaskDTO.setFormStatus("REJECTED");
                    list.addAll(ceeaWorkbenchMapper.findQuaReviewProcess(workbenchMyTaskDTO));
                    // 供应商认证-驳回状态
                    list.addAll(ceeaWorkbenchMapper.findSiteReviewProcess(workbenchMyTaskDTO));
                }
                // 供应商模块
                CheckModuleHolder.checkout(Module.SUP);
                {
                    // 跨组织引入-驳回状态
                    list.addAll(ceeaWorkbenchMapper.findVendorImportProcess(workbenchMyTaskDTO));
                    // 供应商绿色通道-驳回状态
                    list.addAll(ceeaWorkbenchMapper.findCompanyInfoProcess(workbenchMyTaskDTO));
                    // 供应商信息变更-驳回状态
                    workbenchMyTaskDTO.setFormStatus("REJECTED");
                    list.addAll(ceeaWorkbenchMapper.findVendorInfoChangeProcess(workbenchMyTaskDTO));
                }
                // 绩效模块
                CheckModuleHolder.checkout(Module.PERF);
                {
                    // 绩效评分项目-驳回状态或当前评分人未评分
                    workbenchMyTaskDTO.setUserName(userName);// todo 待确认驳回状态取值
                    list.addAll(ceeaWorkbenchMapper.findPerfScoreManProcess(workbenchMyTaskDTO));
                }
                // 招投标模块
                CheckModuleHolder.checkout(Module.BID);
                {
                    // 招投标-项目管理-审批状态已驳回状态
                    workbenchMyTaskDTO.setFormStatus("REJECTED");
                    list.addAll(ceeaWorkbenchMapper.findBidingProcess(workbenchMyTaskDTO));
                }

                // TODO 临时紧急处理，还需确认20201016
                // 询比价模块
                CheckModuleHolder.checkout(Module.BARGAIN);
                {
                    // 询比价-审批状态已驳回状态
                    workbenchMyTaskDTO.setFormStatus("REJECTED");
                    list.addAll(ceeaWorkbenchMapper.findInqProcess(workbenchMyTaskDTO));
                }
                // 询比价模块
                CheckModuleHolder.checkout(Module.INQ);
                {
                    // 价格审批单-审批状态已驳回状态
                    workbenchMyTaskDTO.setFormStatus("RESULT_REJECTED");
                    list.addAll(ceeaWorkbenchMapper.findPriceApproveProcess(workbenchMyTaskDTO));
                    // 配额调整-审批状态已驳回状态
                    workbenchMyTaskDTO.setFormStatus("REJECTED");
                    list.addAll(ceeaWorkbenchMapper.findQuotaAdjustProcess(workbenchMyTaskDTO));
                }
                // 采购管理模块
                CheckModuleHolder.checkout(Module.SUPCOOPERATE);
                {
                    // 采购申请管理-审批状态已驳回状态或者行项目有待退回
                    workbenchMyTaskDTO.setFormStatus("REJECTED");
                    list.addAll(ceeaWorkbenchMapper.findRequirementProcess(workbenchMyTaskDTO));
                    // 需求池管理-策略负责或采购履行为当前账号的申请行状态为已分配且可下单数量大于0
                    list.addAll(ceeaWorkbenchMapper.findRequirementManageProcess(workbenchMyTaskDTO));
                    // 预付款管理-状态为已驳回
                    workbenchMyTaskDTO.setFormStatus("REJECT");
                    list.addAll(ceeaWorkbenchMapper.findAdvancePaymentProcess(workbenchMyTaskDTO));
                    // 付款申请-状态为已驳回
                    workbenchMyTaskDTO.setFormStatus("REJECT");
                    list.addAll(ceeaWorkbenchMapper.findPurPaymentApplyProcess(workbenchMyTaskDTO));
                    // 购物车-状态为已提交且汇总人或通知人为当前账号
                    workbenchMyTaskDTO.setFormStatus("SUBMITTED");
                    list.addAll(ceeaWorkbenchMapper.findShoppingCartProcess(workbenchMyTaskDTO));
                }
                // 供应商协同模块
                CheckModuleHolder.checkout(Module.SUPCOOPERATE);
                {
                    // 采购订单-已驳回和已拒绝
                    list.addAll(ceeaWorkbenchMapper.findPurchaseOrderProcess(workbenchMyTaskDTO));
                    // 采购订单变更-已驳回和已拒绝或新建的同单据号版本最大的
                    list.addAll(ceeaWorkbenchMapper.findPurchaseOrderChangeProcess(workbenchMyTaskDTO));
                    // 开票通知-已驳回
                    workbenchMyTaskDTO.setFormStatus("REJECTED");
                    list.addAll(ceeaWorkbenchMapper.findPurInvoiceProcess(workbenchMyTaskDTO));
                    // 网上发票-已驳回
                    workbenchMyTaskDTO.setFormStatus("REJECTED");
                    list.addAll(ceeaWorkbenchMapper.findOnlineInvoiceProcess(workbenchMyTaskDTO));
                    // 代理网上开票-已驳回
                    workbenchMyTaskDTO.setFormStatus("REJECTED");
                    list.addAll(ceeaWorkbenchMapper.findAgentOnlineInvoiceProcess(workbenchMyTaskDTO));
                }
                // 合同模块
                CheckModuleHolder.checkout(Module.CONTRACT);
                {
                    // 合同列表管理-已驳回和供应商已确认或有效期七天内
                    list.addAll(ceeaWorkbenchMapper.findContractProcess(workbenchMyTaskDTO));
                    // 验收申请列表-已提交
                    workbenchMyTaskDTO.setFormStatus("SUBMIT");
                    list.addAll(ceeaWorkbenchMapper.findInspectionApplyProcess(workbenchMyTaskDTO));
                    // 验收单列表-已驳回
                    workbenchMyTaskDTO.setFormStatus("REJECTED");
                    list.addAll(ceeaWorkbenchMapper.findInspectionProcess(workbenchMyTaskDTO));
                }
            }
            for (WorkbenchMyTaskVo workbenchMyTaskVo : list) {
                // 设置停留时间
                workbenchMyTaskVo.setResidenceTime(DateUtil.getTimeAndNowInterval(workbenchMyTaskVo.getCreationDate().getTime()));
            }
            return list.stream().sorted(Comparator.comparing(WorkbenchMyTaskVo::getCreationDate).reversed()).collect(Collectors.toList());//根据创建时间;
        } finally {
            CheckModuleHolder.release();
        }
    }

    /*** 获取我的已办信息 ***/
    @Override
    public List<WorkbenchMyTaskVo> ceeaFindMyWorkedProcess(WorkbenchMyTaskDTO workbenchMyTaskDTO) throws Exception {
        try {
            List<WorkbenchMyTaskVo> list = new ArrayList<>();
            Long userId = AppUserUtil.getLoginAppUser().getUserId();
            String userName = AppUserUtil.getLoginAppUser().getUsername();
            if ("Y".equals(workbenchMyTaskDTO.getIfMove())) { //移动端代办
                if ("BUYER".equals(AppUserUtil.getLoginAppUser().getUserType())) {
					workbenchMyTaskDTO.setUserId(userId);
					workbenchMyTaskDTO.setFormStatus("APPROVED");
					CheckModuleHolder.checkout(Module.BARGAIN);
	                {
	                    // 询比价-审批状态已驳回状态
	                    list.addAll(ceeaWorkbenchMapper.findInqProcess(workbenchMyTaskDTO));
	                }
	             // 供应商协同模块
	                CheckModuleHolder.checkout(Module.SUPCOOPERATE);
	                {
	                	
	                    // 采购订单-已驳回和已拒绝
	                    list.addAll(ceeaWorkbenchMapper.findPurchaseOrderProcess(workbenchMyTaskDTO));
	                }
				} else if ("VENDOR".equals(AppUserUtil.getLoginAppUser().getUserType())) {
					workbenchMyTaskDTO.setVendorId(AppUserUtil.getLoginAppUser().getCompanyId());
					workbenchMyTaskDTO.setQueryType("done");
					CheckModuleHolder.checkout(Module.SUPCOOPERATE);
					{
						// 采购订单-已驳回和已拒绝
						list.addAll(ceeaWorkbenchMapper.findPurchaseOrderProcess(workbenchMyTaskDTO));
					}
					CheckModuleHolder.checkout(Module.BARGAIN);
					{
						// 询比价-审批状态已驳回状态
						list.addAll(ceeaWorkbenchMapper.findInqProcess(workbenchMyTaskDTO));
					}
				}
                
            } else {
	            // 绩效模块
	            CheckModuleHolder.checkout(Module.PERF);
	            {
	                // 当前评分人已评分
	                workbenchMyTaskDTO.setUserName(userName);
	                list.addAll(ceeaWorkbenchMapper.findPerfScoreManWorked(workbenchMyTaskDTO));
	            }
	            // 采购管理模块
	            CheckModuleHolder.checkout(Module.SUPCOOPERATE);
	            {
	                // 需求池管理-策略负责或采购履行为当前账号的申请行状态为已分配且可下单数量为0
	                list.addAll(ceeaWorkbenchMapper.findRequirementManageWorked(workbenchMyTaskDTO));
	                // 购物车-状态为已创建申请且汇总人或通知人为当前账号
	                workbenchMyTaskDTO.setFormStatus("APPLIED");
	                list.addAll(ceeaWorkbenchMapper.findShoppingCartProcess(workbenchMyTaskDTO));
	            }
            }

            return list.stream().sorted(Comparator.comparing(WorkbenchMyTaskVo::getCreationDate).reversed()).collect(Collectors.toList());//根据创建时间;
        } finally {
            CheckModuleHolder.release();
        }
    }

    /*** 获取我启动信息 ***/
    @Override
    public List<WorkbenchMyTaskVo> ceeaFindMyStartProcess(WorkbenchMyTaskDTO workbenchMyTaskDTO) throws Exception {
        try {
            List<WorkbenchMyTaskVo> list = new ArrayList<>();
            Long userId = AppUserUtil.getLoginAppUser().getUserId();
            String userName = AppUserUtil.getLoginAppUser().getUsername();
            workbenchMyTaskDTO.setQueryType("start");
            if("Y".equals(workbenchMyTaskDTO.getIfMove())) { //移动端功能
            	workbenchMyTaskDTO.setUserId(userId);
            	 CheckModuleHolder.checkout(Module.SUPCOOPERATE);
                 {
                     // 采购订单
                     list.addAll(ceeaWorkbenchMapper.findPurchaseOrderProcess(workbenchMyTaskDTO));
                 }
            	// 询比价模块
                CheckModuleHolder.checkout(Module.BARGAIN);
                {
                    // 询比价
                    list.addAll(ceeaWorkbenchMapper.findInqProcess(workbenchMyTaskDTO));
                }
            } else {
            	// 供应商准入模块
                CheckModuleHolder.checkout(Module.SUP);
                {
                    // 资质审查
                    workbenchMyTaskDTO.setUserId(userId);
                    list.addAll(ceeaWorkbenchMapper.findQuaReviewProcess(workbenchMyTaskDTO));
                    // 供应商认证
                    list.addAll(ceeaWorkbenchMapper.findSiteReviewProcess(workbenchMyTaskDTO));
                }
                // 供应商模块
                CheckModuleHolder.checkout(Module.SUP);
                {
                    // 跨组织引入
                    list.addAll(ceeaWorkbenchMapper.findVendorImportProcess(workbenchMyTaskDTO));
                    // 供应商绿色通道
                    list.addAll(ceeaWorkbenchMapper.findCompanyInfoProcess(workbenchMyTaskDTO));
                    // 供应商信息变更
                    list.addAll(ceeaWorkbenchMapper.findVendorInfoChangeProcess(workbenchMyTaskDTO));
                }
                // 绩效模块
                CheckModuleHolder.checkout(Module.PERF);
                {
                    // 绩效评分项目
                    workbenchMyTaskDTO.setUserName(userName);
                    list.addAll(ceeaWorkbenchMapper.findPerfScoreManProcess(workbenchMyTaskDTO));
                }
                // 招投标模块
                CheckModuleHolder.checkout(Module.BID);
                {
                    // 招投标-项目管理
                    list.addAll(ceeaWorkbenchMapper.findBidingProcess(workbenchMyTaskDTO));
                }
                // 询比价模块
                CheckModuleHolder.checkout(Module.BARGAIN);
                {
                    // 询比价
                    list.addAll(ceeaWorkbenchMapper.findInqProcess(workbenchMyTaskDTO));
                }
                // 询比价模块
                CheckModuleHolder.checkout(Module.INQ);
                {
                    // 价格审批单
                    list.addAll(ceeaWorkbenchMapper.findPriceApproveProcess(workbenchMyTaskDTO));
                    // 配额调整
                    list.addAll(ceeaWorkbenchMapper.findQuotaAdjustProcess(workbenchMyTaskDTO));
                }
                // 采购管理模块
                CheckModuleHolder.checkout(Module.SUPCOOPERATE);
                {
                    // 采购申请管理
                    list.addAll(ceeaWorkbenchMapper.findRequirementProcess(workbenchMyTaskDTO));
                    // 预付款管理
                    list.addAll(ceeaWorkbenchMapper.findAdvancePaymentProcess(workbenchMyTaskDTO));
                    // 付款申请
                    list.addAll(ceeaWorkbenchMapper.findPurPaymentApplyProcess(workbenchMyTaskDTO));
                }
                // 供应商协同模块
                CheckModuleHolder.checkout(Module.SUPCOOPERATE);
                {
                    // 采购订单
                    list.addAll(ceeaWorkbenchMapper.findPurchaseOrderProcess(workbenchMyTaskDTO));
                    // 采购订单变更
                    list.addAll(ceeaWorkbenchMapper.findPurchaseOrderChangeProcess(workbenchMyTaskDTO));
                    // 开票通知
                    list.addAll(ceeaWorkbenchMapper.findPurInvoiceProcess(workbenchMyTaskDTO));
                    // 代理网上开票
                    list.addAll(ceeaWorkbenchMapper.findAgentOnlineInvoiceProcess(workbenchMyTaskDTO));
                }
                // 合同模块
                CheckModuleHolder.checkout(Module.CONTRACT);
                {
                    // 合同列表管理
                    list.addAll(ceeaWorkbenchMapper.findContractProcess(workbenchMyTaskDTO));
                    // 验收单列表
                    list.addAll(ceeaWorkbenchMapper.findInspectionProcess(workbenchMyTaskDTO));
                }
            }
            

            return list.stream().sorted(Comparator.comparing(WorkbenchMyTaskVo::getCreationDate).reversed()).collect(Collectors.toList());//根据创建时间;
        } finally {
            CheckModuleHolder.release();
        }
    }
}
