package com.midea.cloud.srm.model.supplier.info.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;

/**
 * <pre>
 * 待改进项
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
public class ImproveFormDto extends BaseDTO {
    /**
     * 供应商改善单ID
     */
    private Long vendorImproveId;

    /**
     * 业务实体Id
     */
    private Long organizationId;

    /**
     * 业务实体编码
     */
    private String organizationCode;

    /**
     * 业务实体名称
     */
    private String organizationName;

    /**
     * 品类ID
     */
    private Long categoryId;

    /**
     * 品类编码
     */
    private String categoryCode;

    /**
     * 品类名称
     */
    private String categoryName;

    /**
     * 改善主题
     */
    private String improveTitle;

    /**
     * 改善项目
     */
    private String improveProject;

    /**
     * 改善说明
     */
    private String explanation;

    /**
     * 责任跟进人账号
     */
    private String respUserName;

    /**
     * 责任跟进人名字
     */
    private String respFullName;

    /**
     * 状态(DRAFT-拟定,IMPROVING-改善中,UNDER_EVALUATION-评价中,EVALUATED-已评价)
     */
    private String status;
}
