package com.midea.cloud.srm.model.base.formula.vo;

import lombok.*;

import java.io.Serializable;

/**
 * 要素值对象
 *
 * @author zixuan.yan@meicloud.com
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EssentialFactorValue implements Serializable {

    protected Long      essentialFactorId;
    protected String    essentialFactorValue;
}
