package com.midea.cloud.srm.model.suppliercooperate.orderreceive.dto;

import com.midea.cloud.srm.model.suppliercooperate.orderreceive.entity.OrderReceive;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class OrderReceiveDTO extends OrderReceive {
  /**
  * 业务实体列表
  */
  private List<Long> orgIds;
}
