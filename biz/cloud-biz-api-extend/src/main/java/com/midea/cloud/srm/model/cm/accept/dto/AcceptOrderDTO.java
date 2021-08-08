package com.midea.cloud.srm.model.cm.accept.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.midea.cloud.srm.model.cm.accept.entity.AcceptOrder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * <pre>
 *  合同验收单头信息DTO
 * </pre>
 *
 * @author chensl26@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-6-3 19:55
 *  修改内容:
 * </pre>
 */
@Data
public class AcceptOrderDTO extends AcceptOrder {
    /**
     * 起始验收日期
     */
    private LocalDate startAcceptDate;

    /**
     * 终止验收日期
     */
    private LocalDate endAcceptDate;

    /**
     * 起始实际完成日期
     */
    private LocalDate startOverDate;

    /**
     * 终止实际完成日期
     */
    private LocalDate endOverDate;
    /**
     * 业务实体集合
     */
    private List<Long> orgIdList;

    /**
     * 采购订单号
     */
    private String orderNumber;
    /**
     * 区别是验收申请还是验收管理
     */
    private String roofScheme;
}
