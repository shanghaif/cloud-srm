package com.midea.cloud.api.interfacelog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.api.interfacelog.mapper.InterfaceLogLineMapper;
import com.midea.cloud.api.interfacelog.mapper.InterfaceLogMapper;
import com.midea.cloud.api.interfacelog.service.IInterfaceLogService;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.NamedThreadFactory;
import com.midea.cloud.srm.model.api.interfacelog.dto.InterfaceLogDTO;
import com.midea.cloud.srm.model.api.interfacelog.entity.InterfaceLog;
import com.midea.cloud.srm.model.api.interfacelog.entity.InterfaceLogLine;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * <pre>
 *  接口日志表 服务实现类
 * </pre>
 *
 * @author kuangzm@meicloud.com
 * @version 1.00.00
 *
 *          <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-28 10:58:43
 *  修改内容:
 *          </pre>
 */
@Service
public class InterfaceLogServiceImpl extends ServiceImpl<InterfaceLogMapper, InterfaceLog>
		implements IInterfaceLogService {

	private static final ThreadPoolExecutor CONSUMER_EXECUTOR = new ThreadPoolExecutor(10, 300,
			60L, TimeUnit.SECONDS,
			new SynchronousQueue<>(),
			new NamedThreadFactory("api-log-consumer", true));

	private static final BlockingQueue<InterfaceLog> logQueue = new LinkedBlockingQueue<>(1000);

	@Autowired
	private InterfaceLogLineMapper interfaceLogLineMapper;

	@PostConstruct
	public void init() {
		CONSUMER_EXECUTOR.execute(() -> {
			while (true) {
				try {
					InterfaceLog logDTO = logQueue.take();

					this.save(logDTO);

					List<InterfaceLog> logList = new ArrayList<>();
					logQueue.drainTo(logList, 500);
					while (CollectionUtils.isNotEmpty(logList)) {
						this.saveBatch(logList);

						logList.clear();
						logQueue.drainTo(logList, 500);
					}
				} catch (Throwable e) {
					log.error("", e);
				}
			}
		});
	}

	@Override
	@Transactional
	public InterfaceLogDTO createInterfaceLog(InterfaceLogDTO interfaceLog) {
		//保存头信息
		InterfaceLog log = null;
		Long logLineId = IdGenrator.generate();
		if (null != interfaceLog.getLogId()) {
			log = this.getById(interfaceLog.getLogId());
			log.setDealTime(log.getDealTime()+1);
			log.setStatus(interfaceLog.getStatus());
			log.setLineLogId(logLineId);
			this.updateById(log);
		} else {
			log = new InterfaceLog();
			BeanUtils.copyProperties(interfaceLog, log);
			Long id = IdGenrator.generate();
			log.setLineLogId(logLineId);
			log.setDealTime(1L);
			log.setLogId(id);
			this.save(log);
			interfaceLog.setLogId(id);
		}
		//保存行信息
		InterfaceLogLine line = new InterfaceLogLine();
		line.setLogId(log.getLogId());
		line.setLogLineId(logLineId);
		line.setServiceInfo(interfaceLog.getServiceInfo());
		line.setReturnInfo(interfaceLog.getReturnInfo());
		line.setErrorInfo(interfaceLog.getErrorInfo());
		interfaceLogLineMapper.insert(line);
		return interfaceLog;
	}

	@Override
	public void asyncAddLog(InterfaceLog interfaceLog) {
		logQueue.add(interfaceLog);
	}

	@Override
	@Transactional
	public void updateInterfaceLog(InterfaceLogDTO interfaceLog) {
		//更新头信息
		InterfaceLog log = this.getById(interfaceLog.getLogId());
		log.setStatus(interfaceLog.getStatus());
		log.setFinishDate(interfaceLog.getFinishDate());
		this.updateById(log);
		//更新行信息
		InterfaceLogLine line = interfaceLogLineMapper.selectById(log.getLineLogId());
		line.setReturnInfo(interfaceLog.getReturnInfo());
		line.setErrorInfo(interfaceLog.getErrorInfo());
		interfaceLogLineMapper.updateById(line);
	}
}
