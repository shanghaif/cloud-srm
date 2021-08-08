package com.midea.cloud.srm.model.suppliercooperate.deliver.dto;
/**
 *
 * @author yourname@meiCloud.com
 * @version 1.00.00
 *
 *  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-27 14:42:31
 *  修改内容:
 * </pre>
 */
import com.baomidou.mybatisplus.annotation.TableField;
import com.midea.cloud.srm.model.suppliercooperate.deliver.entity.DeliverPlan;
import com.midea.cloud.srm.model.suppliercooperate.deliver.entity.DeliverPlanDetail;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class DeliverPlanDTO extends DeliverPlan {
    /**
     * 计划行状态
     */
    private String deliverPlanLineStatus;
    /**
     * 业务实体集合
     */
    private List<Long> orgIds;
    /**
     * 库存组织集合
     */
    private List<Long> organizationIds;
    /**
     * 物料小类集合
     */
    private List<Long> categoryIds;
    /**
     * 到货计划详情集合
     */
    private List<DeliverPlanDetail> deliverPlanDetailList;
    /**
     * 到货计划对象
     */
    private DeliverPlan deliverPlan;
    /**
     * 采购订单号
     */
    @TableField("ORDER_NUMBER")
    private String orderNumber;

    private List<DeliverPlanVO> stringList;
    /**
     * 送货通知类型(DN送货通知，DP送货预示)
     */
    private  String notifyType;
}
