package com.midea.cloud.srm.sup.quest.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.srm.model.supplier.quest.dto.QuestTemplateDto;
import com.midea.cloud.srm.model.supplier.quest.entity.QuestTemplate;
import com.midea.cloud.srm.model.supplier.quest.vo.QuestTemplateVo;

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
 *  修改日期: Apr 16, 2021 5:17:12 PM
 *  修改内容:
 * </pre>
*/
public interface IQuestTemplateService extends IService<QuestTemplate>{
    /*
    批量更新或者批量新增
    */
     void batchSaveOrUpdate(List<QuestTemplate> questTemplateList) throws IOException;

    /*
     批量删除
     */
    void batchDeleted(List<Long> ids) throws IOException;
    /*
   分页查询
    */
    PageInfo<QuestTemplate> listPageByParm(QuestTemplate questTemplate);


    /**
     *
     * <pre>
     *  功能名称
     * </pre>
     *
     * @author yourname@meicloud.com
     * @version 1.00.00
     *
     * <pre>
     *  修改记录
     *  修改后版本:
     *  修改人:
     *  修改日期: ${DATE} ${TIME}
     *  修改内容:
     * </pre>
     */
    QuestTemplateVo getQuestTemplateById(Long templateId);


    /**
     * 保存模板数据
     * @param questTemplateDto
     * @return
     */
    Long saveQuestTemplateData(QuestTemplateDto questTemplateDto);

    /**
     * 校验模板是否已经分配给了供应商
     * @param questTemplateId
     * @return
     */
    Integer checkUseBySupplier(Long questTemplateId);
}
