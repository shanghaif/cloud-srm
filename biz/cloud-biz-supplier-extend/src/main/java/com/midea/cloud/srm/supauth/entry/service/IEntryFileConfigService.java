package com.midea.cloud.srm.supauth.entry.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.supplierauth.entry.entity.EntryFileConfig;

/**
*  <pre>
 *  准入附件配置 服务类
 * </pre>
*
* kuangzm
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
public interface IEntryFileConfigService extends IService<EntryFileConfig> {

	public void batchSave(Long entryConfigId ,List<EntryFileConfig> list);
}
