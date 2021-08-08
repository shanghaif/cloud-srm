package com.midea.cloud.srm.model.pm.ps.http;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author yourname@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/3 10:41
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
public class AttachmentDto {
    /**
     * 文件名
     */
    private String attachFileName;
    /**
     * 附件访问路径
     */
    private String attachFilePath;
    /**
     * 附件类型
     */
    private String attachFileType;
    /**
     * 上传人
     */
    private String attachUploader;
    /**
     * 上传时间
     */
    private String attachUploadTime;

    /**
     * 预留字段1
     */
    private String reservedField1;

    /**
     * 预留字段2
     */
    private String reservedField2;

    /**
     * 预留字段3
     */
    private String reservedField3;

    /**
     * 预留字段4
     */
    private String reservedField4;

    /**
     * 预留字段5
     */
    private String reservedField5;

}
