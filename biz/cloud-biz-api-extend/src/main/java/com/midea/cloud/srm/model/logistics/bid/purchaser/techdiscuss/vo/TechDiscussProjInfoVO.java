package com.midea.cloud.srm.model.logistics.bid.purchaser.techdiscuss.vo;

import com.midea.cloud.srm.model.logistics.bid.purchaser.techdiscuss.entity.TechDiscussProjInfo;
import com.midea.cloud.srm.model.logistics.bid.purchaser.techdiscuss.entity.TechDiscussSupplier;
import lombok.Data;

import java.util.List;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author fengdc3@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-3-12 11:21
 *  修改内容:
 * </pre>
 */
@Data
public class TechDiscussProjInfoVO extends TechDiscussProjInfo {

    private static final long serialVersionUID = 1L;

    private List<TechDiscussSupplier> techDiscussSupplierList;
}
