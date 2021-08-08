package com.midea.cloud.srm.model.price.costelement.dto;

import lombok.Data;

import java.io.Serializable;

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
 *  修改日期: 2020/7/31
 *  修改内容:
 * </pre>
 */
@Data
public class AttrKeyValue implements Serializable {
    private String key;
    private Integer value;
}
