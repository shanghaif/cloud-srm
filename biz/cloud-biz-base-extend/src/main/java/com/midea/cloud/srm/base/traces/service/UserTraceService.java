package com.midea.cloud.srm.base.traces.service;

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
 *  修改日期: 2020/6/8
 *  修改内容:
 * </pre>
 */
public interface UserTraceService {
    /**
     * 记录当前会话开始
     */
    void startRecordingSession();

    /**
     * 会话结束控制
     */
    void sessionEnd();
}
