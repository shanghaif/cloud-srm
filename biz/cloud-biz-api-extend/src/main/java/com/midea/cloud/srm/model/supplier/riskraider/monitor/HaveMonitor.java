package com.midea.cloud.srm.model.supplier.riskraider.monitor;

import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
*  <pre>
 *  风险雷达以监控企业列表 模型
 * </pre>
*
* @author chenwt24@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021-01-20 18:39:44
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_sup_have_monitor")
public class HaveMonitor extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableField("COMPANY_ID")
    private Long companyId;

    @TableField("COMPANY_CODE")
    private String companyCode;

    @TableField("COMPANY_NAME")
    private String companyName;


}
