package com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidingresult.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidingresult.entity.ProjectReportFile;

import java.util.List;

/**
*  <pre>
 *  结项报告附件表 服务类
 * </pre>
*
* @author fengdc3@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-04-11 15:01:54
 *  修改内容:
 * </pre>
*/
public interface IProjectReportFileService extends IService<ProjectReportFile> {

    void saveFileBatch(List<ProjectReportFile> projectReportFileList, Long projectReportId, Long bidingId);
}
