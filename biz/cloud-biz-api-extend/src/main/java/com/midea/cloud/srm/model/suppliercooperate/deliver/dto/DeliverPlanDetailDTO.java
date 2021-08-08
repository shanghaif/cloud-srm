package com.midea.cloud.srm.model.suppliercooperate.deliver.dto;

import com.midea.cloud.srm.model.suppliercooperate.deliver.entity.DeliverPlan;
import com.midea.cloud.srm.model.suppliercooperate.deliver.entity.DeliverPlanDetail;
import com.midea.cloud.srm.model.suppliercooperate.deliver.entity.OrderAppoint;
import lombok.Data;
import com.github.pagehelper.PageInfo;
import lombok.experimental.Accessors;

import java.util.List;

/**
 *
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
@Data
@Accessors(chain = true)
public class DeliverPlanDetailDTO extends DeliverPlanDetail {
    private DeliverPlan deliverPlan;
    private List<OrderAppoint> orderAppointList;
}
