package com.midea.cloud.srm.bid.purchaser.bidprocessconfig.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.enums.ProcessNodeName;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.enums.bid.projectmanagement.projectpublish.BiddingProjectStatus;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.bid.purchaser.bidprocessconfig.mapper.ProcessNodeMapper;
import com.midea.cloud.srm.bid.purchaser.bidprocessconfig.service.IBidProcessConfigService;
import com.midea.cloud.srm.bid.purchaser.bidprocessconfig.service.IProcessNodeService;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service.IBidingService;
import com.midea.cloud.srm.model.bid.purchaser.bidprocessconfig.entity.BidProcessConfig;
import com.midea.cloud.srm.model.bid.purchaser.bidprocessconfig.entity.ProcessNode;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.entity.Biding;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
*  <pre>
 *  招标流程节点标记表 服务实现类
 * </pre>
*
* @author zhuomb1@midea.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-04-27 09:03:45
 *  修改内容:
 * </pre>
*/
@Service
@Slf4j
public class ProcessNodeServiceImpl extends ServiceImpl<ProcessNodeMapper, ProcessNode> implements IProcessNodeService {
    @Autowired
    private IBidProcessConfigService iBidProcessConfigService;
    @Autowired
    private IBidingService iBidingService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveBatchNode(List<ProcessNode> processNodes) {
        processNodes.forEach(processNode->{
            Long id = IdGenrator.generate();
            processNode.setNodeId(id);
        });
        return this.saveBatch(processNodes);
    }

    @Override
    public List<ProcessNode> saveNodes(Long bidingId, Long processConfigId) {
        Assert.notNull(bidingId,"招标单ID不能为空");
        Assert.notNull(processConfigId,"招标流程配置ID不能为空");
        List list = this.list(new QueryWrapper<>(new ProcessNode().setBidingId(bidingId)));
        if(list.size()>0){
            return null;
        }
        BidProcessConfig bidProcessConfig = iBidProcessConfigService.getById(processConfigId);
        Assert.notNull(bidProcessConfig,"未找到招标流程配置信息");
        List<ProcessNode> processNodes = new ArrayList<>();

        /** 项目信息 */
        if(YesOrNo.YES.getValue().equals(bidProcessConfig.getProjectInformation())){
            ProcessNode processNode = new ProcessNode();
            processNode.setNodeCode(ProcessNodeName.projectInformation.getValue());
            processNode.setDataFlag(YesOrNo.YES.getValue());
            processNodes.add(processNode);
        }
        /** 技术交流 */
        if(YesOrNo.YES.getValue().equals(bidProcessConfig.getTechnologyExchange())){
            ProcessNode processNode = new ProcessNode();
            processNode.setNodeCode(ProcessNodeName.technologyExchange.getValue());
            processNodes.add(processNode);
        }
        /** 项目需求 */
        if(YesOrNo.YES.getValue().equals(bidProcessConfig.getProjectRequirement())){
            ProcessNode processNode = new ProcessNode();
            processNode.setNodeCode(ProcessNodeName.projectRequirement.getValue());
            processNodes.add(processNode);
        }
        /** 邀请供应商 */
        if(YesOrNo.YES.getValue().equals(bidProcessConfig.getInviteSupplier())){
            ProcessNode processNode = new ProcessNode();
            processNode.setNodeCode(ProcessNodeName.inviteSupplier.getValue());
            processNodes.add(processNode);
        }
        /** 评分规则 */
        if(YesOrNo.YES.getValue().equals(bidProcessConfig.getScoringRule())){
            ProcessNode processNode = new ProcessNode();
            processNode.setNodeCode(ProcessNodeName.scoringRule.getValue());
            processNodes.add(processNode);
        }
        /** 流程审批 */
        if(YesOrNo.YES.getValue().equals(bidProcessConfig.getProcessApproval())){
            ProcessNode processNode = new ProcessNode();
            processNode.setNodeCode(ProcessNodeName.processApproval.getValue());
            processNodes.add(processNode);
        }
        /** 供应商绩效 */
        if(YesOrNo.YES.getValue().equals(bidProcessConfig.getSupplierPerformance())){
            ProcessNode processNode = new ProcessNode();
            processNode.setNodeCode(ProcessNodeName.supplierPerformance.getValue());
            processNodes.add(processNode);
        }
        /** 拦标价 */
        if(YesOrNo.YES.getValue().equals(bidProcessConfig.getTargetPrice())){
            ProcessNode processNode = new ProcessNode();
            processNode.setNodeCode(ProcessNodeName.targetPrice.getValue());
            processNodes.add(processNode);
        }
        /** 项目发布 */
        if(YesOrNo.YES.getValue().equals(bidProcessConfig.getProjectPublish())){
            ProcessNode processNode = new ProcessNode();
            processNode.setNodeCode(ProcessNodeName.projectPublish.getValue());
            processNodes.add(processNode);
        }
        /** 报名管理 */
        if(YesOrNo.YES.getValue().equals(bidProcessConfig.getEntryManagement())){
            ProcessNode processNode = new ProcessNode();
            processNode.setNodeCode(ProcessNodeName.entryManagement.getValue());
            processNodes.add(processNode);
        }
        /** 质疑澄清 */
        if(YesOrNo.YES.getValue().equals(bidProcessConfig.getQuestionClarification())){
            ProcessNode processNode = new ProcessNode();
            processNode.setNodeCode(ProcessNodeName.questionClarification.getValue());
            processNodes.add(processNode);
        }
        /** 投标控制 */
        if(YesOrNo.YES.getValue().equals(bidProcessConfig.getBidingControl())){
            ProcessNode processNode = new ProcessNode();
            processNode.setNodeCode(ProcessNodeName.bidingControl.getValue());
            processNodes.add(processNode);
        }
        /** 技术评分 */
        if(YesOrNo.YES.getValue().equals(bidProcessConfig.getTechnicalScore())){
            ProcessNode processNode = new ProcessNode();
            processNode.setNodeCode(ProcessNodeName.technicalScore.getValue());
            processNodes.add(processNode);
        }
        /** 技术标管理 */
        if(YesOrNo.YES.getValue().equals(bidProcessConfig.getTechnicalManagement())){
            ProcessNode processNode = new ProcessNode();
            processNode.setNodeCode(ProcessNodeName.technicalManagement.getValue());
            processNodes.add(processNode);
        }
        /** 商务标管理 */
        if(YesOrNo.YES.getValue().equals(bidProcessConfig.getCommercialManagement())){
            ProcessNode processNode = new ProcessNode();
            processNode.setNodeCode(ProcessNodeName.commercialManagement.getValue());
            processNodes.add(processNode);
        }
        /** 评选 */
        if(YesOrNo.YES.getValue().equals(bidProcessConfig.getBidEvaluation())){
            ProcessNode processNode = new ProcessNode();
            processNode.setNodeCode(ProcessNodeName.bidEvaluation.getValue());
            processNodes.add(processNode);
        }
        /** 招标结果 */
        if(YesOrNo.YES.getValue().equals(bidProcessConfig.getBidingResult())){
            ProcessNode processNode = new ProcessNode();
            processNode.setNodeCode(ProcessNodeName.bidingResult.getValue());
            processNodes.add(processNode);
        }
        /** 结项报告 */
        if(YesOrNo.YES.getValue().equals(bidProcessConfig.getProjectReport())){
            ProcessNode processNode = new ProcessNode();
            processNode.setNodeCode(ProcessNodeName.projectReport.getValue());
            processNodes.add(processNode);
        }
        /** 结项审批 */
        if(YesOrNo.YES.getValue().equals(bidProcessConfig.getProjectApproval())){
            ProcessNode processNode = new ProcessNode();
            processNode.setNodeCode(ProcessNodeName.projectApproval.getValue());
            processNodes.add(processNode);
        }
        processNodes.forEach(node->{
            Long id = IdGenrator.generate();
            node.setNodeId(id);
            node.setBidingId(bidingId);
            node.setProcessConfigId(processConfigId);
        });
        this.saveBatch(processNodes);
        return this.list(new QueryWrapper<>(new ProcessNode().setBidingId(bidingId)));
    }

    @Override
    public List<ProcessNode> updateNodeStatus(ProcessNode processNode) {
        ProcessNode processNodeEntity = this.getOne(new QueryWrapper<>(new ProcessNode().setBidingId(processNode.getBidingId()).setNodeCode(processNode.getNodeCode())));
        Assert.notNull(processNodeEntity,"未找到招标流程标记信息");
        if(null == processNode.getDataFlag()){
            processNodeEntity.setDataFlag(YesOrNo.YES.getValue());
        }else{
            processNodeEntity.setDataFlag(processNode.getDataFlag());
        }
        this.updateById(processNodeEntity);

        Biding biding = iBidingService.getById(processNode.getBidingId());
        /** 流程审批-->流程审批节点点亮的逻辑是判断项目状态为"未发布"时 */
        if(Objects.nonNull(biding)&&Objects.equals(biding.getBidingStatus(),BiddingProjectStatus.UNPUBLISHED.getValue())){
            processNodeEntity = this.getOne(new QueryWrapper<>(new ProcessNode().setBidingId(processNode.getBidingId()).setNodeCode(ProcessNodeName.processApproval.getValue())));
            processNodeEntity.setDataFlag(YesOrNo.YES.getValue());
            this.updateById(processNodeEntity);
        }
        /** 结项审批-->流程审批节点点亮的逻辑是判断项目状态为"已结项"时 */
        if(Objects.nonNull(biding)&&Objects.equals(biding.getBidingStatus(), BiddingProjectStatus.PROJECT_END.getValue())){
            processNodeEntity = this.getOne(new QueryWrapper<>(new ProcessNode().setBidingId(processNode.getBidingId()).setNodeCode(ProcessNodeName.projectApproval.getValue())));
            processNodeEntity.setDataFlag(YesOrNo.YES.getValue());
            this.updateById(processNodeEntity);
        }
        return this.list(new QueryWrapper<>(new ProcessNode().setBidingId(processNode.getBidingId())));
    }
}
