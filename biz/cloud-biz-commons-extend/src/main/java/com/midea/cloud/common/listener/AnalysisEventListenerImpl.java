package com.midea.cloud.common.listener;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 *
 * <pre>
 *  EasyExcel Listener实现
 * </pre>
 *
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020年4月17日 上午10:09:03
 *  修改内容:
 * </pre>
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AnalysisEventListenerImpl<E> extends AnalysisEventListener<E> {

	private List<E> datas = new ArrayList<E>();

	@Override
	public void invoke(E data, AnalysisContext context) {
		datas.add(data);
	}

	@Override
	public void doAfterAllAnalysed(AnalysisContext context) {

	}

}
