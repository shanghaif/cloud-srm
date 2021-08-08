package com.midea.cloud.srm.model.flow.process.dto;

import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;
import lombok.Setter;
import lombok.Getter;

import java.io.Serializable;

/**
 * <pre>
 *  更新模板权限（全量更新）实体类
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/14 14:03
 *  修改内容:
 * </pre>
 */
@Data
public class ChangeTemplateEditorDTO extends BaseDTO {

    private static final long serialVersionUID = 1670750998176843645L;
    /** 模板ID*/
    @Setter @Getter
    private String fdTemplateIds;

    /**用户登录名 */
    @Setter @Getter
    private String loginName;

    /**模板可编辑者账户(fdTemplateReaderLoginNames和本参数选填一个,用 ;隔开,部门用部门id) */
    @Setter @Getter
    private String fdTemplateEditorLoginNames;

    /**模板可阅读者账户(fdTemplateEditorLoginNames和本参数选填一个,用 ;隔开,部门用部门id) */
    @Setter @Getter
    private String fdTemplateReaderLoginNames;
}
