package com.midea.cloud.api.interfaceconfig.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.srm.model.api.interfaceconfig.dto.InterfaceAllDTO;
import com.midea.cloud.srm.model.api.interfaceconfig.dto.InterfaceConfigDTO;
import com.midea.cloud.srm.model.api.interfaceconfig.entity.InterfaceColumn;
import com.midea.cloud.srm.model.api.interfaceconfig.entity.InterfaceConfig;
import com.midea.cloud.srm.model.api.interfaceconfig.entity.InterfaceResult;

/**
*  <pre>
 *  接口配置 服务类
 * </pre>
*
* @author kuangzm@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-06-08 18:02:16
 *  修改内容:
 * </pre>
*/
public interface IInterfaceConfigService extends IService<InterfaceConfig> {

	public BaseResult saveOrUpdate(InterfaceAllDTO allDTO) throws Exception;
	
	public List<InterfaceConfigDTO> findList(InterfaceConfigDTO queryDto);
	
}
