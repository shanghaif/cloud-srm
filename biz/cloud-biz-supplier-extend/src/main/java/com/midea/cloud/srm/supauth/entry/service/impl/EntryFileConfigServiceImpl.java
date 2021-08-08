package com.midea.cloud.srm.supauth.entry.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.model.supplierauth.entry.entity.EntryFileConfig;
import com.midea.cloud.srm.supauth.entry.mapper.EntryFileConfigMapper;
import com.midea.cloud.srm.supauth.entry.service.IEntryFileConfigService;

/**
*  <pre>
 *  准入附件配置 服务实现类
 * </pre>
*
* @author kuangzm
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Jan 21, 2021 2:15:47 PM
 *  修改内容:
 * </pre>
*/
@Service
public class EntryFileConfigServiceImpl extends ServiceImpl<EntryFileConfigMapper, EntryFileConfig> implements IEntryFileConfigService {
	
	@Override
	@Transactional
	public void batchSave(Long entryConfigId ,List<EntryFileConfig> list) {
		//先删除
		QueryWrapper wrapper = new QueryWrapper();
		wrapper.eq("ENTRY_CONFIG_ID", entryConfigId);
		this.remove(wrapper);
		//再批量添加
		if (null != list && list.size() > 0) {
			for (EntryFileConfig fileConfig : list) {
				 Long id = IdGenrator.generate();
				fileConfig.setFileConfigId(id);
				fileConfig.setEntryConfigId(entryConfigId);
			}
		}
		this.saveBatch(list);
	}
}
