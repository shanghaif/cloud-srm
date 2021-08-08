package com.midea.cloud.srm.model.perf.supplierenotice.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;

import java.util.Date;

/**
 * <pre>
 *  21 excel导出模型
 * </pre>
 *
 * @author wengzc@media.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Feb 1, 2021 5:12:42 PM
 *  修改内容:
 * </pre>
 */
@Data
public class QuaSupplierEnoticeDTO extends BaseDTO {
    /**
     * 通知编号ID
     */
    private Long noticeId;
    /**
     * 供应商编码
     */
    private String vendorCode;
    /**
     * 供应商名称
     */
    private String vendorName;
    /**
     * 问题类别
     */
    private String problemType;
    /**
     * 处罚金额
     */
    private Long fineAmount;
    /**
     * 发布日期
     */
    private Date releaseDate;
    /**
     * 业务实体
     */
    private String orgName;
    /**
     * 单据状态
     */
    private String orderStatus;
    /**
     * 供方查阅时间
     */
    private Date supplierReadTime;

}
