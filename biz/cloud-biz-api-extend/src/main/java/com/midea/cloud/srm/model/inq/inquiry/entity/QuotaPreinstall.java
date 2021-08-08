package com.midea.cloud.srm.model.inq.inquiry.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
*  <pre>
 *  配额-预设比例表 模型
 * </pre>
*
* @author yourname@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-05 16:40:36
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ceea_inquiry_quota_preinstall")
public class QuotaPreinstall extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 预设比例ID
     */
    @TableId("QUOTA_PREINSTALL_ID")
    private Long quotaPreinstallId;

    /**
     * 配额ID
     */
    @TableField("QUOTA_ID")
    private Long quotaId;

    /**
     * 供应商数量
     */
    @TableField("SUPPLIER_NUMBER")
    private Integer supplierNumber;

    /**
     * 预设比例数值
     */
    @TableField("QUOTA_PREINSTALL_NUMBER")
    private String quotaPreinstallNumber;


}
