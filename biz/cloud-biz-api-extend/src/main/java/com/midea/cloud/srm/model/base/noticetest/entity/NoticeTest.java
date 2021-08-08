package com.midea.cloud.srm.model.base.noticetest.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
* <pre>
 *  功能代码生成测试 模型
 * </pre>
*
* @author linsb@meicloud.com
* @version 1.00.00
*
* <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021-1-17 20:39:54
 *  修改内容:
 * </pre>
*/

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_base_notice_test")
public class NoticeTest extends BaseEntity {
private static final long serialVersionUID = 819258L;
 /**
 * 主键ID
 */
 @TableId("NOTICE_ID")
 private Long noticeId;
 /**
 * 公告分类
 */
 @TableField("NOTICE_TYPE")
 private String noticeType;
 /**
 * 状态
 */
 @TableField("NOTICE_STATUS")
 private String noticeStatus;
 /**
 * 是否通知所有：Y:是
 */
 @TableField("NOTICE_ALL")
 private String noticeAll;
 /**
 * 标题
 */
 @TableField("TITLE")
 private String title;
 /**
 * 正文
 */
 @TableField("DETAIL")
 private String detail;
 /**
 * 发布人ID
 */
 @TableField("PUBLISHER_ID")
 private Long publisherId;
 /**
 * 发布时间
 */
 @TableField("PUBLISH_TIME")
 private Date publishTime;
 /**
 * 发布人名称
 */
 @TableField("PUBLISH_BY")
 private String publishBy;
 /**
 * 附件ID
 */
 @TableField("FILE_RELATION_ID")
 private Long fileRelationId;
 /**
 * 附件名称
 */
 @TableField("FILE_NAME")
 private String fileName;
 /**
 * 置顶公告,Y：是,N：否
 */
 @TableField("IS_TOP")
 private String isTop;
 /**
 * 创建人ID
 */
 @TableField("CREATED_ID")
 private Long createdId;
 /**
 * 创建人
 */
 @TableField("CREATED_BY")
 private String createdBy;
 /**
 * 创建时间
 */
 @TableField("CREATION_DATE")
 private Date creationDate;
 /**
 * 创建人IP
 */
 @TableField("CREATED_BY_IP")
 private String createdByIp;
 /**
 * 最后更新时间
 */
 @TableField("LAST_UPDATE_DATE")
 private Date lastUpdateDate;
 /**
 * 最后更新人ID
 */
 @TableField("LAST_UPDATED_ID")
 private Long lastUpdatedId;
 /**
 * 最后更新人
 */
 @TableField("LAST_UPDATED_BY")
 private String lastUpdatedBy;
 /**
 * 最后更新人IP
 */
 @TableField("LAST_UPDATED_BY_IP")
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
  * 开始时间
  */
 @TableField(exist = false)
 private Date startDate;

 /**
  * 结束时间
  */
 @TableField(exist = false)
 private Date endDate;
}