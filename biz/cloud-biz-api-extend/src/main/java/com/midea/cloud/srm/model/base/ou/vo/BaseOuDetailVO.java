package com.midea.cloud.srm.model.base.ou.vo;
import lombok.Data;

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
 *  修改日期: 2020/9/4 20:00
 *  修改内容:
 * </pre>
 */
@Data
public class BaseOuDetailVO {
    private Long ouDetailId;

    /**
     * OU组ID
     */
    private Long ouGroupId;

    /**
     * 业务实体ID
     */
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
    private Long invId;

    /**
     * 库存组织编码
     */
    private String invCode;

    /**
     * 库存组织名称
     */
    private String invName;

    /**
     * 事业部变成了字典值
     * 事业部名称
     */
    private String buName;

    /**
     * 事业部id
     */
    private String buId;

}
