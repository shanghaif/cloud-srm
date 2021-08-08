package com.midea.cloud.srm.model.base.ou.dto.create;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author tanjl11@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/4 20:12
 *  修改内容:
 * </pre>
 */
@Data
public class BaseOuDetailCreateDTO {
    @NotNull(message = "业务实体id不能为空")
    private Long ouId;

    /**
     * 业务实体编码
     */

    private String ouCode;

    /**
     * 业务实体名称
     */

    private String ouName;

    /**
     * 库存组织ID
     */
    @NotNull(message = "库存组织ID不能为空")
    private Long invId;

    /**
     * 库存组织编码
     */
    @NotEmpty(message = "库存组织编码不能为空")
    private String invCode;

    /**
     * 库存组织名称
     */
    @NotEmpty(message = "库存组织名称不能为空")
    private String invName;

    /**
     * 事业部名称
     */
    @NotEmpty(message = "事业部名不能为空")
    private String buName;

    /**
     * 事业部id
     */
    @NotNull(message = "事业部id不能为空")
    private String buId;
}
