package com.midea.cloud.api.interfaceconfig.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.api.inter.service.IInterService;
import com.midea.cloud.api.interfaceconfig.service.IInterfaceColumnService;
import com.midea.cloud.api.interfaceconfig.service.IInterfaceConfigService;
import com.midea.cloud.api.systemconfig.service.ISystemConfigService;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.api.interfaceconfig.dto.InterfaceAllDTO;
import com.midea.cloud.srm.model.api.interfaceconfig.dto.InterfaceConfigDTO;
import com.midea.cloud.srm.model.api.interfaceconfig.entity.InterfaceColumn;
import com.midea.cloud.srm.model.api.interfaceconfig.entity.InterfaceConfig;
import com.midea.cloud.srm.model.api.systemconfig.entity.SystemConfig;
import com.midea.cloud.srm.model.common.BaseController;

/**
 * <pre>
 *  接口配置 前端控制器
 * </pre>
 *
 * @author kuangzm@meicloud.com
 * @version 1.00.00
 *
 *          <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-06-08 18:02:16
 *  修改内容:
 *          </pre>
 */
@RestController
@RequestMapping("/interfaceconfig")
public class InterfaceConfigController extends BaseController {

	@Autowired
	private IInterfaceConfigService iInterfaceConfigService;

	@Autowired
	private ISystemConfigService iSystemConfigService;

	@Autowired
	private IInterfaceColumnService iInterfaceColumnService;

	@Autowired
	private IInterService iInterService;

	/**
	 * 获取
	 * 
	 * @param interfaceId
	 */
	@GetMapping("/get")
	public InterfaceAllDTO get(Long interfaceId) {
		Assert.notNull(interfaceId, "interfaceId不能为空");
		InterfaceAllDTO allDTO = new InterfaceAllDTO();
		InterfaceConfig config = iInterfaceConfigService.getById(interfaceId);
		InterfaceConfigDTO dto = new InterfaceConfigDTO();
		BeanUtils.copyProperties(config, dto);
		SystemConfig system = this.iSystemConfigService.getById(config.getSystemId());
		dto.setSystemName(system.getSystemName());
		dto.setType(system.getType());
		dto.setProtocol(system.getProtocol());
		allDTO.setForm(dto);

		QueryWrapper wrapper = new QueryWrapper();
		wrapper.eq("interface_id", config.getInterfaceId());
		wrapper.eq("type", "IN");
		wrapper.orderByAsc("parent_id,line_num");
		List<InterfaceColumn> allParams = iInterfaceColumnService.list(wrapper);
		List<InterfaceColumn> params = new ArrayList<InterfaceColumn>();
		List<InterfaceColumn> childParams = new ArrayList<InterfaceColumn>();
		if (null != allParams && allParams.size() > 0) {
			for (InterfaceColumn column : allParams) {
				if (null == column.getParentId()) {
					params.add(column);
				} else {
					childParams.add(column);
				}
			}
		}
		allDTO.setParams(params);
		allDTO.setChildParams(childParams);

		wrapper = new QueryWrapper();
		wrapper.eq("interface_id", config.getInterfaceId());
		wrapper.eq("type", "OUT");
		wrapper.orderByAsc("parent_id,line_num");
		List<InterfaceColumn> allColumns = iInterfaceColumnService.list(wrapper);
		List<InterfaceColumn> columns = new ArrayList<InterfaceColumn>();
		List<InterfaceColumn> childColumns = new ArrayList<InterfaceColumn>();
		if (null != allColumns && allColumns.size() > 0) {
			for (InterfaceColumn column : allColumns) {
				if (null == column.getParentId()) {
					columns.add(column);
				} else {
					childColumns.add(column);
				}
			}
		}
		allDTO.setColumns(columns);
		allDTO.setChildColumns(childColumns);

		wrapper = new QueryWrapper();
		wrapper.eq("interface_id", config.getInterfaceId());
		wrapper.eq("type", "SQL");
		wrapper.orderByAsc("parent_id,line_num");
		List<InterfaceColumn> sqlParams = iInterfaceColumnService.list(wrapper);
		allDTO.setSqlParams(sqlParams);

		return allDTO;
	}

	/**
	 * 新增
	 * 
	 * @param interfaceConfig
	 */
	@PostMapping("/add")
	public void add(@RequestBody InterfaceConfig interfaceConfig) {
		Long id = IdGenrator.generate();
		interfaceConfig.setInterfaceId(id);
		iInterfaceConfigService.save(interfaceConfig);
	}

	/**
	 * 删除
	 * 
	 * @param id
	 */
	@GetMapping("/delete")
	public void delete(Long id) {
		Assert.notNull(id, "id不能为空");
		iInterfaceConfigService.removeById(id);
	}

	/**
	 * 修改
	 * 
	 * @param interfaceConfig
	 */
	@PostMapping("/modify")
	public void modify(@RequestBody InterfaceConfig interfaceConfig) {
		iInterfaceConfigService.updateById(interfaceConfig);
	}

	/**
	 * 分页查询
	 * 
	 * @param interfaceConfig
	 * @return
	 */
	@PostMapping("/listPage")
	public PageInfo<InterfaceConfig> listPage(@RequestBody InterfaceConfigDTO interfaceConfig) {
		PageUtil.startPage(interfaceConfig.getPageNum(), interfaceConfig.getPageSize());
		return new PageInfo(iInterfaceConfigService.findList(interfaceConfig));
	}

	/**
	 * 查询所有
	 * 
	 * @return
	 */
	@PostMapping("/listAll")
	public List<InterfaceConfig> listAll() {
		return iInterfaceConfigService.list();
	}

	/**
	 * 新增或修改保存
	 * 
	 * @param interfaceConfig
	 * @throws Exception
	 */
	@PostMapping("/save")
	public void save(@RequestBody InterfaceAllDTO allDTO) throws Exception {
		this.iInterfaceConfigService.saveOrUpdate(allDTO);
	}

	/**
	 * 刷新
	 * 
	 * @param sql
	 * @param dataConfig
	 * @return
	 * @throws SQLException
	 */
	@PostMapping("/getColumnBySql")
	public List<InterfaceColumn> getColumnBySql(String sql, String dataConfig) throws SQLException {
		return iInterService.listSql(sql, dataConfig);
	}

	/**
	 * 获取HTTP参数
	 * 
	 * @param sql
	 * @param dataConfig
	 * @return
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 * @throws SQLException
	 */
	@PostMapping("/getHttpColumns")
	public List<InterfaceColumn> getHttpColumns(@RequestBody InterfaceAllDTO allDTO)
			throws Exception {
		return iInterService.getHttpColumns(allDTO);
	}

	/**
	 * 获取HTTP参数
	 * 
	 * @param sql
	 * @param dataConfig
	 * @return
	 * @throws SQLException
	 */
	@PostMapping("/test")
	public List<InterfaceColumn> test(@RequestBody InterfaceAllDTO allDTO) {
		return iInterService.getSqlColumns(allDTO);
	}

	/**
	 * showDoc
	 * @param allDTO
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/showDoc")
	public String showDoc(@RequestBody InterfaceAllDTO allDTO) throws Exception {
		return iInterService.showDoc(allDTO);
	}
	
	/**
	 * 测试接口
	 * @param allDTO
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/testInterface")
	public String testInterface(@RequestBody InterfaceAllDTO allDTO) throws Exception {
		return iInterService.testInterface(allDTO);
	}
	
	/**
	 * 测试接口
	 * @param allDTO
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/getHttpParam")
	public List<InterfaceColumn> getHttpParam(@RequestBody InterfaceAllDTO allDTO) throws Exception {
		return iInterService.getHttpParam(allDTO);
	}
}
