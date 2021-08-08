package com.midea.cloud.srm.model.api.interfaceconfig.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
*  <pre>
 *  接口配置 模型
 * </pre>
*
* @author kuangzm@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-06-08 18:02:16
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_api_interface_config")
public class InterfaceConfig extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 接口ID
     */
    @TableId("INTERFACE_ID")
    private Long interfaceId;

    /**
     * 接口编码
     */
    @TableField("INTERFACE_CODE")
    private String interfaceCode;

    /**
     * 接口名称
     */
    @TableField("INTERFACE_NAME")
    private String interfaceName;

    /**
     * 接口地址
     */
    @TableField("INTERFACE_URL")
    private String interfaceUrl;

    /**
     * 系统ID
     */
    @TableField("SYSTEM_ID")
    private Long systemId;

    /**
     * 获取数据方式
     */
    @TableField("SOURCE")
    private String source;
    
    /**
     * 数据源配置
     */
    @TableField("DATA_CONFIG")
    private String dataConfig;

    /**
     * 数据源
     */
    @TableField("DATA_SOURCE")
    private String dataSource;

    /**
     * 是否转换
     */
    @TableField("IF_CHANGE")
    private String ifChange;

    /**
     * 数据格式
     */
    @TableField("DATA_FORMAT")
    private String dataFormat;

    /**
     * 数据体
     */
    @TableField("DATA_STRUCTURE")
    private String dataStructure;

    /**
     * 返回实现类
     */
    @TableField("RETURN_CLASS")
    private String returnClass;

    /**
     * 数据实现类
     */
    @TableField("BEAN_SOURCE_CLASS")
    private String beanSourceClass;

    /**
     * data类型
     */
    @TableField("DATA_TYPE")
    private String dataType;
    
    /**
     * 额外日志接口
     */
    @TableField("LOG_HTTP")
    private String logHttp;
    
    /**
     * 调用方式
     */
    @TableField("METHOD")
    private String method;
    
    /**
     * 参数构造方式
     */
    @TableField("PARAM_STRUCT")
    private String paramStruct;

    /**
     * 日志模式：SYNC同步写入/ASYNC异步写入；默认为SYNC
     */
    @TableField("LOG_MODEL")
    private String logModel;

    /**
     * 创建人ID
     */
    @TableField(value = "CREATED_ID", fill = FieldFill.INSERT)
    private Long createdId;

    /**
     * 创建人
     */
    @TableField(value = "CREATED_BY", fill = FieldFill.INSERT)
    private String createdBy;

    /**
     * 创建时间
     */
    @TableField(value = "CREATION_DATE", fill = FieldFill.INSERT)
    private Date creationDate;

    /**
     * 创建人IP
     */
    @TableField(value = "CREATED_BY_IP", fill = FieldFill.INSERT)
    private String createdByIp;

    /**
     * 最后更新时间
     */
    @TableField(value = "LAST_UPDATE_DATE", fill = FieldFill.UPDATE)
    private Date lastUpdateDate;

    /**
     * 最后更新人ID
     */
    @TableField(value = "LAST_UPDATED_ID", fill = FieldFill.UPDATE)
    private Long lastUpdatedId;

    /**
     * 最后更新人
     */
    @TableField(value = "LAST_UPDATED_BY", fill = FieldFill.UPDATE)
    private String lastUpdatedBy;

    /**
     * 最后更新人IP
     */
    @TableField(value = "LAST_UPDATED_BY_IP", fill = FieldFill.UPDATE)
    private String lastUpdatedByIp;

    /**
     * 租户
     */
    @TableField("TENANT_ID")
    private Long tenantId;

    /**
     * 版本号
     */
    @TableField("VERSION")
    private Long version;
    
    /**
     * 报文参考
     */
    @TableField("DEMO_TEXT")
    private String demoText;
    
    /**
     * 出参类型
     */
    @TableField("OUT_TYPE")
    private String outType;
    
    /**
     * 是否异步
     */
    @TableField("IF_SYN")
    private String ifSyn;
    
    /**
     * 是否需要参数字段配置
     */
    @TableField("IF_NEED_PARAM")
    private String ifNeedParam;
    
    /**
     * 是否需要返回字段配置
     */
    @TableField("IF_NEED_RESULT")
    private String ifNeedResult;
}
