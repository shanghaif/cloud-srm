package com.midea.cloud.srm.model.supplier.info.dto;

import com.midea.cloud.srm.model.supplier.info.entity.SiteInfo;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author yourname@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/21 8:50
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
public class SiteInfoQueryDTO extends SiteInfo {
    private List<Long> materialIds;
}
