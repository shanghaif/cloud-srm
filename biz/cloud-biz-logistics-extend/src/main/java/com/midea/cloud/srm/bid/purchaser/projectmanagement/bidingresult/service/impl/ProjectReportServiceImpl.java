package com.midea.cloud.srm.bid.purchaser.projectmanagement.bidingresult.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidingresult.mapper.ProjectReportMapper;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidingresult.service.IProjectReportFileService;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidingresult.service.IProjectReportService;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service.IBidingService;
import com.midea.cloud.srm.feign.rbac.RbacClient;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidingresult.entity.ProjectReport;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidingresult.vo.ProjectReportVO;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidinitiating.entity.Biding;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * <pre>
 *  结项报告基础信息表 服务实现类
 * </pre>
 *
 * @author fengdc3@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-04-11 15:01:54
 *  修改内容:
 * </pre>
 */
@Service
public class ProjectReportServiceImpl extends ServiceImpl<ProjectReportMapper, ProjectReport> implements IProjectReportService {

    @Autowired
    private IProjectReportFileService iProjectReportFileService;
    @Autowired
    private IBidingService iBidingService;
    @Autowired
    private RbacClient rbacClient;

    @Override
    public void saveProjectReportAndFile(ProjectReportVO projectReportVO) {
        Assert.notNull(projectReportVO.getProjectReport(), "结项报告信息不能为空");
        Assert.notNull(projectReportVO.getProjectReport().getProjectReportId(), "结项报告id不能为空");
        Long bidingId = projectReportVO.getProjectReport().getBidingId();
        Assert.notNull(bidingId, "结项报告信息不能为空");
        Assert.isTrue(CollectionUtils.isNotEmpty(projectReportVO.getProjectReportFileList()), "结项报告附件不能为空");

        this.updateById(projectReportVO.getProjectReport());

        iProjectReportFileService.saveFileBatch(projectReportVO.getProjectReportFileList(),
                projectReportVO.getProjectReport().getProjectReportId(), bidingId);
    }

    @Override
    public ProjectReport getProjectReport(Long bidingId) {
        Assert.notNull(bidingId, "招标id不能为空");
        ProjectReport projectReport = new ProjectReport();

        //若结项报告表有记录则直接返回
        ProjectReport currentReport = this.getOne(new QueryWrapper<>(new ProjectReport().setBidingId(bidingId)));
        if (currentReport != null) {
            return currentReport;
        }

        //若结项报告表无记录则查询招标基础信息表
        Biding biding = iBidingService.getById(bidingId);
        Assert.notNull(biding, "查询招标信息为空");
        LoginAppUser realName = rbacClient.findByUsername(biding.getCreatedBy());
        Long id = IdGenrator.generate();
        projectReport.setProjectReportId(id).setBidingNum(biding.getBidingNum()).setProjectIdentificationTime(biding.getCreationDate()).
                setProjectLeader(realName.getNickname()).setBidingName(biding.getBidingName()).setBidingId(bidingId);

        //第一次查询保存信息
        this.save(projectReport);
        return projectReport;
    }
}
