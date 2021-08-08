package com.midea.cloud.srm.pr.logisticsRequirement.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.logistics.pr.requirement.entity.LogisticsRequirementFile;
import com.midea.cloud.srm.model.logistics.pr.requirement.entity.LogisticsRequirementHead;

import java.util.List;

/**
*  <pre>
 *  物流采购申请附件 服务类
 * </pre>
*
* @author chenwt24@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-11-27 10:59:47
 *  修改内容:
 * </pre>
*/
public interface IRequirementFileService extends IService<LogisticsRequirementFile> {
    /**
     * 批量新增附件
     * */
    void addRequirementFileBatch(LogisticsRequirementHead requirementHead, List<LogisticsRequirementFile> requirementAttaches);

    void updateRequirementAttachBatch(LogisticsRequirementHead requirementHead, List<LogisticsRequirementFile> requirementAttaches);


}
