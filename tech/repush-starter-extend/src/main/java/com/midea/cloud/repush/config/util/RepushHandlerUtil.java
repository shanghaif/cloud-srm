package com.midea.cloud.repush.config.util;

import com.midea.cloud.component.context.container.SpringContextHolder;
import com.midea.cloud.repush.config.dto.RepushDto;
import com.midea.cloud.repush.service.RepushHandlerService;

/**
 * <pre>
 * 接口重推工具类
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
public class RepushHandlerUtil {
    // 重推处理类
    private static RepushHandlerService repushHandlerService;

    /**
     * 保存重推接口数据
     * @param repushDto
     */
    public static void save(RepushDto repushDto){
        repushHandlerService.save(
                repushDto.getTitle(),
                repushDto.getSourceNumber(),
                repushDto.getCallServiceName(),
                repushDto.getCallMethodName(),
                repushDto.getMaxRetryCount(),
                repushDto.getRepushStatus(),
                repushDto.getIfRepus(),
                repushDto.getRepushCallbackService(),
                repushDto.getRepushCallbackParam(),
                repushDto.getArgs());
    }


    /**
     * 获取重推实现类
     * @return
     */
    private static synchronized RepushHandlerService getRepushHandlerService(){
        if(null == repushHandlerService){
            repushHandlerService = SpringContextHolder.getBean(RepushHandlerService.class);
        }
        return repushHandlerService;
    }
}
