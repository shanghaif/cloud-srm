package com.midea.cloud.srm.model.base.noticetest.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;

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
@ColumnWidth(15) //列宽
public class NoticeTestDto extends BaseEntity {
private static final long serialVersionUID = 819258L;
 /**
 * 主键ID
 */
 @ExcelProperty( value = "主键ID",index = 0)
 private Long noticeId;
 /**
 * 公告分类
 */
 @ExcelProperty( value = "公告分类",index = 1)
 private String noticeType;
 /**
 * 状态
 */
 @ExcelProperty( value = "状态",index = 2)
 private String noticeStatus;
 /**
 * 是否通知所有：Y:是
 */
 @ExcelProperty( value = "是否通知所有：Y:是",index = 3)
 private String noticeAll;
 /**
 * 标题
 */
 @ExcelProperty( value = "标题",index = 4)
 private String title;
 /**
 * 正文
 */
 @ExcelProperty( value = "正文",index = 5)
 private String detail;
 /**
 * 发布人ID
 */
 @ExcelProperty( value = "发布人ID",index = 6)
 private Long publisherId;
 /**
 * 发布时间
 */
 @ExcelProperty( value = "发布时间",index = 7)
 private Date publishTime;
 /**
 * 发布人名称
 */
 @ExcelProperty( value = "发布人名称",index = 8)
 private String publishBy;
 /**
 * 附件ID
 */
 @ExcelProperty( value = "附件ID",index = 9)
 private Long fileRelationId;
 /**
 * 附件名称
 */
 @ExcelProperty( value = "附件名称",index = 10)
 private String fileName;
 /**
 * 置顶公告,Y：是,N：否
 */
 @ExcelProperty( value = "置顶公告,Y：是,N：否",index = 11)
 private String isTop;
 /**
 * 创建人ID
 */
 @ExcelProperty( value = "创建人ID",index = 12)
 private Long createdId;
 /**
 * 创建人
 */
 @ExcelProperty( value = "创建人",index = 13)
 private String createdBy;
 /**
 * 创建时间
 */
 @ExcelProperty( value = "创建时间",index = 14)
 private Date creationDate;
 /**
 * 创建人IP
 */
 @ExcelProperty( value = "创建人IP",index = 15)
 private String createdByIp;
 /**
 * 最后更新时间
 */
 @ExcelProperty( value = "最后更新时间",index = 16)
 private Date lastUpdateDate;
 /**
 * 最后更新人ID
 */
 @ExcelProperty( value = "最后更新人ID",index = 17)
 private Long lastUpdatedId;
 /**
 * 最后更新人
 */
 @ExcelProperty( value = "最后更新人",index = 18)
 private String lastUpdatedBy;
 /**
 * 最后更新人IP
 */
 @ExcelProperty( value = "最后更新人IP",index = 19)
 private String lastUpdatedByIp;
 /**
 * 租户
 */
 @ExcelProperty( value = "租户",index = 20)
 private Long tenantId;
 /**
 * 版本号
 */
 @ExcelProperty( value = "版本号",index = 21)
 private Long version;
 /**
  * 错误信息提示
  */
 @ExcelProperty( value = "错误信息提示",index = 22)
 private String errorMsg;
}