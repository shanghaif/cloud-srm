package com.midea.cloud.srm.model.log.biz.holder;
import com.midea.cloud.srm.model.log.biz.entity.BizOperateLogInfo;

/**
 * <pre>
 *  存储操作信息的threadlocal
 * </pre>
 *
 * @author tanjl11@meicloud.com
 * @version 1.00.00
 */
public class BizDocumentInfoHolder {
    public static ThreadLocal<BizOperateLogInfo> holder = ThreadLocal.withInitial(BizOperateLogInfo::new);
    public static void clear() {
        holder.remove();
    }

    public static BizOperateLogInfo get() {
        return holder.get();
    }
}
