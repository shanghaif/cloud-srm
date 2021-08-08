package com.midea.cloud.srm.model.supplier.dim.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author zhuwl7@midea.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-3-3 16:37
 *  修改内容:
 * </pre>
 */
@Data
public class DimConfigDTO {
    /**
     * 维度配置ID
     */
    private Long dimConfigId;

    /**
     * 租户ID
     */
    private Long tenantId;

    /**
     * ID
     */
    private Long templateId;

    /**
     * 维度ID
     */
    private Long dimId;

    /**
     * 维度Code
     */
    private String dimCode;

    /**
     * 维度名字
     */
    private String dimName;

    /**
     * 别名
     */
    private String dimNickName;

    /**
     * 顺序
     */
    private Integer orderNum;

    /**
     * 版本号
     */
    private Long version;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 创建日期
     */
    private Date creationDate;

    /**
     * 最后更新人
     */
    private String lastUpdatedBy;

    /**
     * 最后更新时间
     */
    private Date lastUpdateDate;

    List<DimFieldConfigDTO> dimFieldConfigS;
}
