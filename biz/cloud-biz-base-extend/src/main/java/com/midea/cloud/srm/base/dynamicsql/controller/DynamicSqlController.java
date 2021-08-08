package com.midea.cloud.srm.base.dynamicsql.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.base.dynamicsql.service.DynamicSqlAttrService;
import com.midea.cloud.srm.base.dynamicsql.service.DynamicSqlConfigService;
import com.midea.cloud.srm.model.base.dynamicsql.dto.DynamicSqlDTO;
import com.midea.cloud.srm.model.base.dynamicsql.dto.DynamicSqlParamDTO;
import com.midea.cloud.srm.model.base.dynamicsql.entity.DynamicSqlAttr;
import com.midea.cloud.srm.model.base.dynamicsql.entity.DynamicSqlConfig;
import com.midea.cloud.srm.model.base.quicksearch.param.JsonParam;
import com.midea.cloud.srm.model.common.BaseController;

/**
 * <pre>
 *  动态sql 前端控制器
 * </pre>
 *
 * @author kuangzm
 * @version 1.00.00
 *
 *          <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Jan 18, 2021 8:24:06 PM
 *  修改内容:
 *          </pre>
 */
@RestController
@RequestMapping("/dynamicsql")
public class DynamicSqlController extends BaseController {

	@Autowired
	private DynamicSqlConfigService dynamicSqlConfigService;
	@Autowired
	private DynamicSqlAttrService dynamicSqlAttrService;

	/**
	 * 获取
	 * 
	 * @param id
	 */
	@GetMapping("/get")
	public DynamicSqlDTO get(Long id) {
		Assert.notNull(id, "id不能为空");
		DynamicSqlDTO dto = new DynamicSqlDTO();
		DynamicSqlConfig config = dynamicSqlConfigService.getById(id);
		if (null != config) {
			BeanUtils.copyProperties(config, dto);
			QueryWrapper wrapper = new QueryWrapper();
			wrapper.eq("SQL_ID", id);
			List<DynamicSqlAttr> list = dynamicSqlAttrService.list(wrapper);
			if (null != list && list.size() > 0) {
				dto.setAttrs(list);
			}
		}
		return dto;
	}

	/**
	 * 删除
	 * 
	 * @param id
	 */
	@GetMapping("/delete")
	public void delete(Long id) {
		Assert.notNull(id, "id不能为空");
		dynamicSqlConfigService.removeById(id);
		QueryWrapper wrapper = new QueryWrapper();
		wrapper.eq("SQL_ID", id);
		dynamicSqlAttrService.remove(wrapper);

	}

	/**
	 * 分页查询
	 * 
	 * @param dynamicSqlConfig
	 * @return
	 */
	@PostMapping("/listPage")
	public PageInfo<DynamicSqlConfig> listPage(@RequestBody DynamicSqlConfig dynamicSqlConfig) {
		PageUtil.startPage(dynamicSqlConfig.getPageNum(), dynamicSqlConfig.getPageSize());
		QueryWrapper<DynamicSqlConfig> wrapper = new QueryWrapper<DynamicSqlConfig>(dynamicSqlConfig);
		return new PageInfo<DynamicSqlConfig>(dynamicSqlConfigService.list(wrapper));
	}

	/**
	 * 查询所有
	 * 
	 * @return
	 */
	@PostMapping("/listAll")
	public List<DynamicSqlConfig> listAll() {
		return dynamicSqlConfigService.list();
	}

	/**
	 * 查询所有
	 * 
	 * @return
	 */
	@PostMapping("/saveOrUpdate")
	public DynamicSqlDTO saveOrUpdate(@RequestBody DynamicSqlDTO dynamicSqlDTO) {
		return dynamicSqlConfigService.saveOrUpdate(dynamicSqlDTO);
	}

	/**
	 * 根据SQL查询所有字段信息
	 * 
	 * @param dynamicSqlDTO
	 * @return
	 * @throws SQLException
	 */
	@PostMapping("/getAttrs")
	public List<Map<String, Object>> getAttrs(@RequestBody DynamicSqlDTO dynamicSqlDTO) throws SQLException {
		return dynamicSqlConfigService.listSql(dynamicSqlDTO.getQuerySql(), dynamicSqlDTO.getQueryModule());
	}

	/**
	 * 根据数据库配置查询信息
	 * 
	 * @param param
	 * @return
	 */
	@PostMapping("/listByFormCondition")
	public Map<String, Object> listByFormCondition(@RequestBody DynamicSqlParamDTO param) {
		String sqlKey = null;
		Assert.notNull(param, "参数不能为空！");
		Assert.notNull(param.getSqlKey(), "查询便阿门不能为空！");
		return dynamicSqlConfigService.listByFormCondition(param);
	}

	/**
	 * 获取
	 * 
	 * @param id
	 */
	@GetMapping("/getByName")
	public DynamicSqlConfig getByName(String name) {
		Assert.notNull(name, "编码不能为空");
		QueryWrapper wrapper = new QueryWrapper();
		DynamicSqlDTO dto = new DynamicSqlDTO();
		wrapper.eq("NAME", name);
		DynamicSqlConfig config = dynamicSqlConfigService.getOne(wrapper);
		if (null != config) {
			BeanUtils.copyProperties(config, dto);
			wrapper = new QueryWrapper();
			wrapper.eq("SQL_ID", dto.getSqlId());
			List<DynamicSqlAttr> list = dynamicSqlAttrService.list(wrapper);
			if (null != list && list.size() > 0) {
				dto.setAttrs(list);
			}
		}
		return dto;
	}

}
