package com.midea.cloud.srm.sup.quest.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.supplier.quest.entity.QuestTemplateOrg;

import java.io.IOException;
import java.util.List;

/**
* <pre>
 *  问卷调查 服务类
 * </pre>
*
* @author bing5.wang@midea.com
* @version 1.00.00
*
* <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Apr 16, 2021 5:32:42 PM
 *  修改内容:
 * </pre>
*/
public interface IQuestTemplateOrgService extends IService<QuestTemplateOrg>{
    /*
    批量更新或者批量新增
    */
     void batchSaveOrUpdate(List<QuestTemplateOrg> questTemplateOrgList) throws IOException;

    /*
     批量删除
     */
    void batchDeleted(List<Long> ids) throws IOException;
    /*
   分页查询
    */
    PageInfo<QuestTemplateOrg> listPage(QuestTemplateOrg questTemplateOrg);


}
