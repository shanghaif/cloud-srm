package com.midea.cloud.srm.model.supplier.riskraider.r8.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseDTO;
import com.midea.cloud.srm.model.common.BaseEntity;
import com.midea.cloud.srm.model.supplier.riskraider.r8.entity.R8DiscreditRelationDetail;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
*  <pre>
 *  关联企业失信信息表 模型
 * </pre>
*
* @author chenwt24@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021-01-18 10:46:05
 *  修改内容:
 * </pre>
*/
@Data
@Accessors(chain = true)
public class R8DiscreditRelationDto extends BaseDTO {


    /**
     * 关联企业失信ID
     */
    private Long discreditRelationId;

    /**
     * 企业失信ID
     */
    private Long discreditId;

    /**
     * 关联企业失信标志（0：非失信，1：失信）
     */
    private String relationDiscreditFlag;

    /**
     * 关联企业失信提示
     */
    private String relationDiscreditPrompt;

    /**
     * 创建人ID
     */
    private Long createdId;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 创建时间
     */
    private Date creationDate;

    /**
     * 创建人IP
     */
    private String createdByIp;

    /**
     * 最后更新人ID
     */
    private Long lastUpdatedId;

    /**
     * 更新人
     */
    private String lastUpdatedBy;

    /**
     * 最后更新时间
     */
    private Date lastUpdateDate;

    /**
     * 最后更新人IP
     */
    private String lastUpdatedByIp;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 版本号
     */
    private Long version;

    private List<R8DiscreditRelationDetail> relationDiscreditList;


}
