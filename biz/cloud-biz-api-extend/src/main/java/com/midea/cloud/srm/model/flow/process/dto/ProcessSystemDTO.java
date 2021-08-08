package com.midea.cloud.srm.model.flow.process.dto;

import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;

import java.util.List;

/**
 * <pre>
 *  cbpm工作流新增系统DTO
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/4/26 19:48
 *  修改内容:
 * </pre>
 */
@Data
public class ProcessSystemDTO extends BaseDTO {
    private static final long serialVersionUID = 105798289936531291L;

    /**系统主键id */
    private String fdId;
    /**创建人id */
    private String docCreatorId;
    /**域名*/
    private String fdAddress;
    /**模块名称*/
    private String fdModelName;
    /**系统名称 */
    private String fdName;
    /**状态 */
    private String fdStatus;
    /**系统code */
    private String fdCode;
    /**可编辑者 */
    private String authAeditorIds;
    /**可阅读者 */
    private String authAreaderIds;
    private List<ProcessSystemDetailDTO> detailList;
}
