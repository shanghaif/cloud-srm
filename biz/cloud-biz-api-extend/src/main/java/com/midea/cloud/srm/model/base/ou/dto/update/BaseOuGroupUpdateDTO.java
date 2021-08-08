package com.midea.cloud.srm.model.base.ou.dto.update;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

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
 *  修改日期: 2020/9/4 20:28
 *  修改内容:
 * </pre>
 */
@Data
public class BaseOuGroupUpdateDTO {
    @NotNull(message = "ou组id不能为空")
    private Long ouGroupId;

    /**
     * OU组名称
     */
    private String ouGroupName;

    private List<BaseOuDetailUpdateDTO> details;
}
