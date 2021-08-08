package com.midea.cloud.srm.model.supplier.quest.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.midea.cloud.srm.model.supplier.quest.entity.QuestTemplate;
import com.midea.cloud.srm.model.supplier.quest.entity.QuestTemplateOrg;
import lombok.Data;

import java.util.List;

/**
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
 *  修改日期: 2021/4/19 20:09
 *  修改内容:
 * </pre>
 */
@Data
public class QuestTemplateDto extends QuestTemplate {

    /**
     * 页签组
     */
    @TableField(exist = false)
    private List<QuestTemplatePropGroupDto> questTemplateTabArr;
    @TableField(exist = false)
    private List<QuestTemplateOrg> questTemplateOrgArr;

}
