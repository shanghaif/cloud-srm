package com.midea.cloud.srm.model.pm.ps.advance.vo;

import com.midea.cloud.srm.model.base.dept.dto.DeptDto;
import com.midea.cloud.srm.model.pm.ps.advance.entity.AdvanceApplyHead;
import lombok.Data;
import lombok.experimental.Accessors;

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
 *  修改日期: 2020/10/9 10:11
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
public class AdvanceApplyHeadVo extends AdvanceApplyHead {

    private List<DeptDto> deptDtos;//全部部门集

    private List<String> paymentStages;
}
