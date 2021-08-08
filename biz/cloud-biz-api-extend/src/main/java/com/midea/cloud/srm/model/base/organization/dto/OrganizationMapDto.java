package com.midea.cloud.srm.model.base.organization.dto;

import com.midea.cloud.srm.model.base.organization.entity.Organization;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * <pre>
 *
 * </pre>
 *
 * @author wangpr@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/2
 *  修改内容:
 * </pre>
 */
@Data
public class OrganizationMapDto implements Serializable {

    private static final long serialVersionUID = -3175077715254964672L;
    private List<Organization> BuList;
    private HashMap<String,Long> pcMap;
}
