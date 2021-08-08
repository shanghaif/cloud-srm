package com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidingresult.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidingresult.mapper.ProjectReportFileMapper;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidingresult.service.IProjectReportFileService;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidingresult.entity.ProjectReportFile;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <pre>
 *  结项报告附件表 服务实现类
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
public class ProjectReportFileServiceImpl extends ServiceImpl<ProjectReportFileMapper, ProjectReportFile> implements IProjectReportFileService {

    @Override
    public void saveFileBatch(List<ProjectReportFile> projectReportFileList, Long projectReportId, Long bidingId) {
        this.remove(new QueryWrapper<>(new ProjectReportFile().setBidingId(bidingId).setProjectReportId(projectReportId)));
        for (ProjectReportFile file : projectReportFileList) {
            Long id = IdGenrator.generate();
            file.setProjectReportFileId(id).setProjectReportId(projectReportId).
                    setBidingId(bidingId);
        }
        this.saveBatch(projectReportFileList);
    }
}
