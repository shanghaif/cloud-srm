package com.midea.cloud.repush.config.dto;

import com.midea.cloud.repush.service.RepushCallbackService;
import com.midea.cloud.srm.model.base.repush.entity.RepushStatus;
import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

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
 *  修改日期: 2020-05-27 09:24:20
 *  修改内容:
 * </pre>
 */
/**
 * <pre>
 * 接口重推参数
 * </pre>
 *
 * @author wangpr@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-27 09:24:20
 *  修改内容:
 * </pre>
 */
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class RepushDto extends BaseDTO {
    /**
     * 标题
     */
    private String title;
    /**
     * 单据ID
     */
    private String sourceNumber;
    /**
     * 服务名
     */
    private String callServiceName;
    /**
     * 方法名
     */
    private String callMethodName;
    /**
     * 最大重推次数
     */
    private Integer maxRetryCount;
    /**
     * 推送状态 SUCCESS 成功 FAIL 失败
     */
    private RepushStatus repushStatus;
    /**
     * 是否需要重推,0:不需重推,1:需要重推,默认0
     */
    private int ifRepus;
    /**
     * 重推回调接口逻辑
     */
    private RepushCallbackService repushCallbackService;
    /**
     * 重推回调接口参数
     */
    private Object[] repushCallbackParam;
    /**
     * 重推参数
     */
    private Object[] args;
}
