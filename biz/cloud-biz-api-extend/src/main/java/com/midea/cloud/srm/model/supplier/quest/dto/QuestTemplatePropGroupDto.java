package com.midea.cloud.srm.model.supplier.quest.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.midea.cloud.srm.model.supplier.quest.entity.QuestTemplatePropGroup;
import com.midea.cloud.srm.model.supplier.quest.vo.QuestTemplatePropVo;
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
 *  修改日期: 2021/4/19 20:10
 *  修改内容:
 * </pre>
 */
@Data
public class QuestTemplatePropGroupDto extends QuestTemplatePropGroup {

    /**
     * 页签字段
     */
    @TableField(exist = false)
    private List<QuestTemplatePropDto> questTemplatePropArr;
}
