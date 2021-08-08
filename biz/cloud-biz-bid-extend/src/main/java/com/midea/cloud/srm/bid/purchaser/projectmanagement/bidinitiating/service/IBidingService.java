package com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.entity.Biding;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.vo.ProjectInfoVO;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.RequirementLine;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

/**
*  <pre>
 *  招标基础信息表 服务类
 * </pre>
*
* @author fengdc3
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:tanjl11@meicloud.com
 *  修改日期: 2020-09-09 14:59:11
 *  修改内容:
 * </pre>
*/
public interface IBidingService extends IService<Biding> {

    PageInfo<Biding> listPage(Biding biding);

    Long saveProjectInfo(ProjectInfoVO projectInfoVO);

    void updateProjectInfo(ProjectInfoVO projectInfoVO);

    void removeByBidingId(Long id);

    /**
     * 废弃订单
     * @param bidingId
     */
    void abandon(Long bidingId);

    void publish(Long bidingId);

    void updatePermission(Biding biding);

    Map<String, Object> initProjectWorkFlow(Long bidingId, Long menuId, String templateCode, String title);

    //备用接口
    void initProjectApproval(Long bidingId);

    //备用接口
    void endProjectApproval(Long bidingId);

    String requirementGenBiding(List<RequirementLine> requirementLineList);

    void callBackForWorkFlow(Biding biding);
}
