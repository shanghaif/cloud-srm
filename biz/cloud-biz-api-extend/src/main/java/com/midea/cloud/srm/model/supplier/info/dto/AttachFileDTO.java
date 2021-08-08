package com.midea.cloud.srm.model.supplier.info.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

/**
*  <pre>
 *  企业注册附件表 模型
 * </pre>
*
* @author zhuwl7@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-09 15:44:17
 *  修改内容:
 * </pre>
*/
@Data
public class AttachFileDTO  {


    /**
     * 附件ID
     */
    private Long attachFileId;

    /**
     * 企业编号
     */
    private Long companyId;

    /**
     * 附件名称
     */
    private String attachmentName;

    /**
     * 附件描述
     */
    private String attachmentDiscription;

    /**
     * 附件名称（上传到fastdf的文件名）
     */
    private String attachmentPic;

    /**
     * 附件FileId
     */
    private Long attachmentPicFileId;

    /**
     * 附件有效期
     */
    private LocalDate attachmentValidDate;

    private Long version;

    private String createdByIp;

    private BigDecimal createdBy;

    private Date creationDate;

    private String lastUpdatedByIp;

    private String lastUpdatedBy;

    private Date lastUpdateDate;


}
