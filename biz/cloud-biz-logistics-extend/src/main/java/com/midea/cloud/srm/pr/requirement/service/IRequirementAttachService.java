package com.midea.cloud.srm.pr.requirement.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.RequirementAttach;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.RequirementHead;

import java.util.List;

/**
*  <pre>
 *  采购需求附件表 服务类
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-07-28 11:18:34
 *  修改内容:
 * </pre>
*/
public interface IRequirementAttachService extends IService<RequirementAttach> {

    void addRequirementAttachBatch(RequirementHead requirementHead, List<RequirementAttach> requirementAttaches);

    void updateRequirementAttachBatch(RequirementHead requirementHead, List<RequirementAttach> requirementAttaches);
}
