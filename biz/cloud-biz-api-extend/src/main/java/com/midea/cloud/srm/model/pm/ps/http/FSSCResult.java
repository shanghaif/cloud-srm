package com.midea.cloud.srm.model.pm.ps.http;

import lombok.Data;

/**
 * <pre>
 *    对接财务共享中心返回值接收类
 * </pre>
 *
 * @author chensl26@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/27 18:22
 *  修改内容:
 * </pre>
 */
@Data
public class FSSCResult {

    private String msg;//返回的结果信息

    private String code;//返回的结果码

    private String expire;//失效时间

    private String type;//类型

    private String token;//令牌

    private String boeNo;

    private String sourceBoeNo;

    private String printUrl;

//    private String fstatus;//接收状态 成功Y;失败是空，表示：是否接收到信息。
//
//    private String message;//错误信息描述 失败将错误信息描述返回
}
