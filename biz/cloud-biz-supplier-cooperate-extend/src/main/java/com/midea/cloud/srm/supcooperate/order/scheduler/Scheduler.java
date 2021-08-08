package com.midea.cloud.srm.supcooperate.order.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * 定时器
 * </pre>
 * 
 * saveItemsByExcel
 *
 * @author huangbf3
 * @version 1.00.00
 *
 *          <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/4/20 19:32
 *  修改内容:
 *          </pre>
 */
@Component
public class Scheduler {
	// 每隔2小时执行一次，更新超时未确认退货单，自动设置为已确认
	@Scheduled(fixedRate = 7200000)
	public void updateSureReturnStatus() {
	}
}
