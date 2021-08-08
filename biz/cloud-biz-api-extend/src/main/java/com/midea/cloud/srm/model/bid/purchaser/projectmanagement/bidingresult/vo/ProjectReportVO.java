package com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidingresult.vo;

import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidingresult.entity.ProjectReport;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidingresult.entity.ProjectReportFile;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 * 结项报告页 视图对象
 * </pre>
 *
 * @author fengdc3@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-4-11 15:20
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
public class ProjectReportVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private ProjectReport projectReport;
    private List<ProjectReportFile> projectReportFileList;

}
