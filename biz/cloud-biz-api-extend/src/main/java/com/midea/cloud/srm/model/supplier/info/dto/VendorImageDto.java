package com.midea.cloud.srm.model.supplier.info.dto;

import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * <pre>
 * 供应商画像
 * </pre>
 *
 * @author wangpr@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/1
 *  修改内容:
 * </pre>
 */
@Data
public class VendorImageDto extends BaseDTO {

    /**
     * 供应商Id
     */
    private Long vendorId;

    /**
     * 供应商级别(战略级与非战略级)
     */
    private String vendorLevel;

    /**
     * 注册资本
     */
    private String registeredCapital;

    /**
     * 法定代表人
     */
    private String legalPerson;

    /**
     * 企业性质(字典:COMPANY_NATURE)
     */
    private String companyType;

    /**
     * 企业成立日期
     */
    private LocalDate companyCreationDate;

    /**
     * 业务类型
     */
    private String businessType;

    /**
     * 服务年限(供应商第一次在某个品类商开始供货算起，到现在的年份即为服务年限。)
     */
    private int serviceLength;

    /**
     * 商业模式(由供应商注册时带出)
     */
    private String businessModel;

    /**
     * 经营范围
     */
    private String businessScope;

    /**
     * 异常总数
     */
    private int exceptionSum;

    /**
     * 待改善总数
     */
    private int improveSum;

    /**
     * 已关闭总数
     */
    private int closeSum;

    /**
     * 单一来源
     */
    private List<SingleSourceDto> singleSourceDtos;

    /**
     * 可供品类
     */
    private List<CategoryRelDto> categoryRelDtos;

    /**
     * 近三年采购金额
     */
    private List<PurchaseAmountDto> purchaseAmountDtos;

    /**
     * 中标次数
     */
    private List<BidFrequency> bidFrequencies;

    /**
     * 绩效信息
     */
    private List<PerfOverallScoreDto> perfOverallScoreDtos;

    /**
     * 异常跟踪
     */
    private List<AssesFormDto> assesFormDtos;

    /**
     * 待改进项
     */
    private List<ImproveFormDto> improveFormDtos;

}
