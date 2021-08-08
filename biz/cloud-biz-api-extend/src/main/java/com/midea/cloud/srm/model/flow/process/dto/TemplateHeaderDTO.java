package com.midea.cloud.srm.model.flow.process.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.midea.cloud.srm.model.common.BaseDTO;
import com.midea.cloud.srm.model.flow.process.entity.TemplateLines;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * <pre>
 *  流程模板查询类
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/21 14::20
 *  修改内容:
 * </pre>
 * */
@Data
public class TemplateHeaderDTO extends BaseDTO {

    private static final long serialVersionUID = -3294748001599469761L;

    /**流程行集合*/
    private List<TemplateLines> templateLinesList;

    private Long templateHeadId;

    /*** cdpm流程fdTemplateId*/
    private String modelId;

    /*** 模板Id对应菜单表functionId*/
    private Long templateId;

    /*** 模板名称*/
    private String templateName;

    /*** 模板编码(功能维护表functionCode)*/
    private String templateCode;

    /*** 描述说明*/
    private String description;

    private String pendingApproveUrl;
    /*** 分流服务(对应数据字典编码MODULE_DIVISION_FLOW)*/
    private String feignClient;

    private String bussinessClass;

    /*** 开始日期*/
    private Date startDateActive;

    /*** 结束日期*/
    private Date endDateActive;

    /*** 语言*/
    private String language;

    /**创建人ID*/
    private Long createdId;

    /**创建人IP*/
    private String createdByIp;

    /**最后修改人ID*/
    private Long lastUpdatedId;

    /**最后修改人IP*/
    private String lastUpdatedByIp;

    /**租户ID*/
    private Long tenantId;

    /*** 创建人账号*/
    private String createdBy;

    /*** 创建时间*/
    private Date creationDate;

    /*** 最后更新时间*/
    private String lastUpdatedBy;
    /*** 最后更新时间*/
    private Date lastUpdateDate;
    /*** 创建人姓名*/
    private String createdFullName;

    /*** 最后更新人姓名*/
    private String lastUpdatedFullName;

    /*** 是否删除 0不删除 1删除*/
    private Integer deleteFlag;
    /*** 版本号*/
    private Long version;
    /**是否有效(是-Y，否-N)*/
    private String enableFlag;
    /**属性类别*/
    private String attributeCategory;
    /**预留字段1*/
    private String attribute1;

    /**以下字段用于查询*/
    /**业务类型*/
    private String bussinessType;
    /**事件处理过程（业务方法）*/
    private String bussinessFunction;
    
    /**
     * 集成模式
     */
    private String integrationMode;
    
    /**
     * 业务名称
     */
    private String businessName;
    
}
