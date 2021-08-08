package com.midea.cloud.srm.model.base.idm;

import com.midea.cloud.srm.model.base.soap.erp.base.dto.EsbInfoParam;
import com.midea.cloud.srm.model.base.soap.erp.base.dto.EsbInfoRequest;
import com.midea.cloud.srm.model.base.soap.erp.base.dto.EsbInfoResponse;
import lombok.Data;

/**
 * <pre>
 * Idm接口用户最外层
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/24 18:56
 *  修改内容:
 * </pre>
 */
@Data
public class IdmEmployeeAll {
    /**隆基Idm入参标注格式*/
    private EsbInfoParam esbInfo;
    /**Idm接口RequestInfo(包括用户表集合)*/
    private IdmRequestInfo requestInfo;
}
