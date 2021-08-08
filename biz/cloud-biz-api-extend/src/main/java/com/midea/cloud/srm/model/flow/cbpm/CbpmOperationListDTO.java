package com.midea.cloud.srm.model.flow.cbpm;

import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;

import java.util.List;

/**
 * <pre>
 * cbpm操作人对象(根据角色分组后的流程可用操作接口返回值)
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/28 15:02
 *  修改内容:
 * </pre>
 */
@Data
public class CbpmOperationListDTO extends BaseDTO {

    private static final long serialVersionUID = 2180223314748172051L;
    /**操作角色类型(审批人：approve,起草人：draft)*/
    private String roleType;
    /**操作人信息列表*/
    private List<CbpmOperationDTO> operationList;
}
