package com.midea.cloud.api.interfaceconfig.service.impl;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.util.StringUtil;
import com.midea.cloud.api.inter.common.CommonUtil;
import com.midea.cloud.api.inter.service.impl.WebserviceGenerator;
import com.midea.cloud.api.interfaceconfig.mapper.InterfaceConfigMapper;
import com.midea.cloud.api.interfaceconfig.service.IInterfaceColumnService;
import com.midea.cloud.api.interfaceconfig.service.IInterfaceConfigService;
import com.midea.cloud.api.systemconfig.service.ISystemConfigService;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.dynamicds.bind.CheckModuleHolder;
import com.midea.cloud.dynamicds.enums.Module;
import com.midea.cloud.srm.model.api.interfaceconfig.dto.InterfaceAllDTO;
import com.midea.cloud.srm.model.api.interfaceconfig.dto.InterfaceConfigDTO;
import com.midea.cloud.srm.model.api.interfaceconfig.entity.InterfaceColumn;
import com.midea.cloud.srm.model.api.interfaceconfig.entity.InterfaceConfig;
import com.midea.cloud.srm.model.api.systemconfig.entity.SystemConfig;

/**
 * <pre>
 *  接口配置 服务实现类
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
@Service
public class InterfaceConfigServiceImpl extends ServiceImpl<InterfaceConfigMapper, InterfaceConfig>
		implements IInterfaceConfigService {

	@Autowired
	protected SqlSessionTemplate stJrsm;

	@Autowired
	private IInterfaceColumnService iInterfaceColumnService;

	@Resource
	private IInterfaceConfigService iInterfaceConfigService;
	
	@Autowired
	private ISystemConfigService iSystemConfigService;
	
	@Autowired
	private WebserviceGenerator generator;
	
	@Transactional
	@Override
	public BaseResult saveOrUpdate(InterfaceAllDTO allDTO) throws Exception {
		InterfaceConfig con = null;
		Assert.notNull(allDTO, "参数异常");

		// 验证编码是否重复
		QueryWrapper wrapper = new QueryWrapper();
		wrapper.eq("INTERFACE_CODE", allDTO.getForm().getInterfaceCode());
		if (null != allDTO.getForm().getInterfaceId()) {
			wrapper.ne("INTERFACE_ID", allDTO.getForm().getInterfaceId());
		}
		if (null != allDTO.getForm().getSystemId()) {
			wrapper.eq("SYSTEM_ID", allDTO.getForm().getSystemId());
		}
		List<InterfaceConfig> list = this.list(wrapper);
		if (list.size() > 0) {
			throw new BaseException(ResultCode.PARAM_VALID_ERROR, "存在重复的接口编码");
		}
		if (null != allDTO.getForm().getInterfaceId()) {
			con = this.getById(allDTO.getForm().getInterfaceId());
			con.setInterfaceName(allDTO.getForm().getInterfaceName());
			con.setInterfaceCode(allDTO.getForm().getInterfaceCode());
			con.setSystemId(allDTO.getForm().getSystemId());
			con.setInterfaceUrl(allDTO.getForm().getInterfaceUrl());
			con.setSource(allDTO.getForm().getSource());
			con.setDataFormat(allDTO.getForm().getDataFormat());
			con.setDataType(allDTO.getForm().getDataType());
			con.setDataStructure(allDTO.getForm().getDataStructure());
			con.setIfChange(allDTO.getForm().getIfChange());
			con.setReturnClass(allDTO.getForm().getReturnClass());
			con.setDataSource(allDTO.getForm().getDataSource());
			con.setDataConfig(allDTO.getForm().getDataConfig());
			con.setLogHttp(allDTO.getForm().getLogHttp());
			con.setParamStruct(allDTO.getForm().getParamStruct());
			con.setMethod(allDTO.getForm().getMethod());
			con.setDemoText(allDTO.getForm().getDemoText());
			con.setOutType(allDTO.getForm().getOutType());
			con.setIfSyn(allDTO.getForm().getIfSyn());
			con.setIfNeedParam(allDTO.getForm().getIfNeedParam());
			con.setIfNeedResult(allDTO.getForm().getIfNeedResult());
			this.updateById(con);
		} else {
			Long id = IdGenrator.generate();
			con = new InterfaceConfig();
			BeanUtils.copyProperties(allDTO.getForm(), con);
			con.setInterfaceId(id);
			this.save(con);
		}
		
		//SQL参数信息
		this.updateColumn(con.getInterfaceId(), allDTO.getSqlParams(), null,"SQL");
		
		// 参数信息
		this.updateColumn(con.getInterfaceId(), allDTO.getParams(), allDTO.getChildParams(),"IN");
		
		// 字段信息
		this.updateColumn(con.getInterfaceId(), allDTO.getColumns(), allDTO.getChildColumns(),"OUT");
		
		//加载webservice接口
		this.initWebservice(con);

		return BaseResult.build(ResultCode.SUCCESS);
	}
	
	
	private void initWebservice(InterfaceConfig config) throws Exception {
    	QueryWrapper qw = new QueryWrapper();
    	qw.eq("INTERFACE_ID", config.getInterfaceId());
    	qw.eq("TYPE", "OUT");
    	List<InterfaceColumn> columns = iInterfaceColumnService.list(qw);
    	qw = new QueryWrapper();
    	qw.eq("INTERFACE_ID", config.getInterfaceId());
    	qw.eq("TYPE", "IN");
    	List<InterfaceColumn> params = iInterfaceColumnService.list(qw);
    	SystemConfig system = iSystemConfigService.getById(config.getSystemId());
    	if ("WEBSERVICE".equals(system.getProtocol()) && "RECEIVE".equals(system.getType())) {
    		generator.webservicePush(config,columns,params);
    	}
    }


	private void updateColumn(Long interfaceId, List<InterfaceColumn> columns, List<InterfaceColumn> childColumns,String type) {
		// 先删除
		Map<String, Object> columnMap = new HashMap<String, Object>();
		columnMap.put("INTERFACE_ID", interfaceId);
		columnMap.put("TYPE", type);
		this.iInterfaceColumnService.removeByMap(columnMap);
		// 添加
		Map<Long, Long> columnIdMap = new HashMap<Long, Long>();
		if (null != columns) {
			int i=1;
			for (InterfaceColumn column : columns) {
				Long parentId = column.getParentId();
				if (columnIdMap.containsKey(parentId)) {
					column.setParentId(columnIdMap.get(parentId));
				}
				Long columnsId = column.getColumnId();
				Long id = IdGenrator.generate();
				columnIdMap.put(columnsId, id);
				column.setColumnId(id);
				column.setLineNum(i);
				i++;
				column.setInterfaceId(interfaceId);
				column.setType(type);
				this.iInterfaceColumnService.save(column);
			}
			
			this.updateChildColumn(childColumns, columnIdMap, interfaceId,type);
		}
	}
	
	private void updateChildColumn(List<InterfaceColumn> list,Map<Long, Long> columnIdMap,Long interfaceId,String type) {
		List<InterfaceColumn> templist = new ArrayList<InterfaceColumn>();
		if (null != list && list.size() > 0) {
			int i=1;
			boolean flag = false; //判断是否存在子节点
			for (InterfaceColumn column : list) {
				Long parentId = column.getParentId();
				if (columnIdMap.containsKey(parentId)) {
					column.setParentId(columnIdMap.get(parentId));
				} else {
					templist.add(column);
					continue;
				}
				flag = true;
				Long columnsId = column.getColumnId();
				Long id = IdGenrator.generate();
				columnIdMap.put(columnsId, id);
				column.setColumnId(id);
				column.setLineNum(i);
				i++;
				column.setInterfaceId(interfaceId);
				column.setType(type);
				this.iInterfaceColumnService.save(column);
			}
			if (flag && templist.size() > 0) {
				this.updateChildColumn(templist, columnIdMap, interfaceId,type);
			}
		}
	}
	
	@Override
	public List<InterfaceConfigDTO> findList(InterfaceConfigDTO queryDto) {
		return this.getBaseMapper().interfaceConfigFindList(queryDto);
	}

	
	//-----------------------------------接收转换 end-------------------------------------------------------------//
	
	
}
