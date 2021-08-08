package com.midea.cloud.srm.model.inq.inquiry.vo;

import com.midea.cloud.srm.model.common.BaseEntity;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Accessors(chain = true)
public class QuotaParamVO extends BaseEntity {
    /**
     * 配额数值
     */
    Double priceDouble;
    /**
     * 剩余总配额数值
     */
    Double sum;
    /**
     *没有收到上限的供应商
     */
    Map<Integer, CompanyInfo> companyInfoMap;
}
