package com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidinitiating.vo;

import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidinitiating.entity.SourcingTemplate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 查询参数 - {@link SourcingTemplate}.
 *
 * @author zixuan.yan@meicloud.com
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SourcingTemplateQueryParameter implements Serializable {

    private String sn;                     // 模板编号
    private String name;                   // 模板名称
    private String status;                 // 模板状态
    private String sourcingType;           // 寻源类型
    private Integer pageNum;
    private Integer pageSize;
}
