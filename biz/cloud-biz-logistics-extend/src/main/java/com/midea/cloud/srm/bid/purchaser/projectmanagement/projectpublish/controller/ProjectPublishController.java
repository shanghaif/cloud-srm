package com.midea.cloud.srm.bid.purchaser.projectmanagement.projectpublish.controller;

import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service.IBidRequirementService;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service.IBidingService;
import com.midea.cloud.srm.feign.rbac.RbacClient;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidinitiating.entity.Biding;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.projectpublish.vo.ProjectPublishVO;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.rbac.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <pre>
 * 项目管理-项目发布 前端控制器
 * </pre>
 *
 * @author fengdc3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-04-03 14:00:03
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/projectManagement/projectPublish")
public class ProjectPublishController extends BaseController {

    @Autowired
    private IBidingService iBidingService;
    @Autowired
    private IBidRequirementService iBidRequirementService;
    @Autowired
    private RbacClient rbacClient;

    /**
     * 查询公告信息
     *
     * @return
     */
    @GetMapping("/getInformation")
    public ProjectPublishVO getInformation(Long bidingId) {
        Assert.notNull(bidingId, "招标id不能为空");
        Biding biding = iBidingService.getById(bidingId);
        ProjectPublishVO projectPublishVO = new ProjectPublishVO();
        projectPublishVO.setBiding(biding);
        projectPublishVO.setBidRequirement(iBidRequirementService.getBidRequirement(bidingId));
//        User preparedByUser = rbacClient.getUserByParmForAnon(new User().setUsername(biding.getPreparedBy()));
//        projectPublishVO.setCreateBy(preparedByUser == null ? null : preparedByUser.getNickname());
//        User approvedByUser = rbacClient.getUserByParmForAnon(new User().setUsername(biding.getApprovedBy()));
//        projectPublishVO.setApprovalBy(approvedByUser == null ? null : approvedByUser.getNickname());
        return projectPublishVO;
    }

    /**
     * 发布
     * @param bidingId
     */
    @GetMapping("/publish")
    public void publish(Long bidingId){
        iBidingService.publish(bidingId);
    }

}
