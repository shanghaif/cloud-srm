package com.midea.cloud.srm.base.dynamicsql.service.impl;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.common.utils.Assert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.util.StringUtil;
import com.midea.cloud.common.constants.QuicksearchConfigFields;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.dynamicds.bind.CheckModuleHolder;
import com.midea.cloud.dynamicds.enums.Module;
import com.midea.cloud.srm.base.dynamicsql.mapper.DynamicSqlConfigMapper;
import com.midea.cloud.srm.base.dynamicsql.service.DynamicSqlAttrService;
import com.midea.cloud.srm.base.dynamicsql.service.DynamicSqlConfigService;
import com.midea.cloud.srm.model.base.dynamicsql.dto.DynamicSqlDTO;
import com.midea.cloud.srm.model.base.dynamicsql.dto.DynamicSqlParamDTO;
import com.midea.cloud.srm.model.base.dynamicsql.entity.DynamicSqlAttr;
import com.midea.cloud.srm.model.base.dynamicsql.entity.DynamicSqlConfig;

/**
*  <pre>
 *  动态sql 服务实现类
 * </pre>
*
* @author kuangzm
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Jan 18, 2021 8:24:06 PM
 *  修改内容:
 * </pre>
*/
@Service
public class DynamicSqlConfigServiceImpl extends ServiceImpl<DynamicSqlConfigMapper, DynamicSqlConfig> implements DynamicSqlConfigService {
	
	@Autowired
	private DynamicSqlConfigMapper dynamicSqlConfigMapper;
	
	@Autowired
	private DynamicSqlAttrService dynamicSqlAttrService;
	
	@Autowired
	protected SqlSessionTemplate stJrsm;
	
	private static String EXACT = "EXACT";//精确匹配
	
	private static String FUZZY = "FUZZY";//模糊匹配
	
	/**
     * 保存配置信息
     *
     * @param param
     * @return
     */
    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public DynamicSqlDTO saveOrUpdate(DynamicSqlDTO dynamicSqlDTO) {
    	Assert.notNull(dynamicSqlDTO.getName(), "查询编码必填");
    	Assert.notNull(dynamicSqlDTO.getQueryModule(), "查询模块必填");
    	Assert.notNull(dynamicSqlDTO.getQuerySql(), "查询SQL必填");
        if (dynamicSqlDTO.getSqlId() != null) {
            this.updateById(dynamicSqlDTO);
        } else {
            Long id = IdGenrator.generate();
            dynamicSqlDTO.setSqlId(id);
            this.save(dynamicSqlDTO);
        }

        Long id = dynamicSqlDTO.getSqlId();
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("SQL_ID", id);
        dynamicSqlAttrService.remove(wrapper);
        List<DynamicSqlAttr> list = new ArrayList<>();
        for (DynamicSqlAttr attr : dynamicSqlDTO.getAttrs()) {
            
            Long attrId = IdGenrator.generate();
            attr.setSqlAttrId(attrId);
            attr.setSqlId(id);
            list.add(attr);
        }
        if (CollectionUtils.isNotEmpty(list)) {
        	dynamicSqlAttrService.saveBatch(list);
        }
        return dynamicSqlDTO;
    }
    
    
    /**
	 * 查询表信息
	 * @param sql
	 * @param dataConfig
	 * @return
	 */
	@Override
	public List<Map<String, Object>> listSql(String sql, String dataConfig) throws SQLException {

		List<Map<String, Object>> list = new ArrayList<>();
		Statement pStemt = null;
		ResultSet rs = null;
		Connection connection = null;
		SqlSession sqlSession = null;
		ResultSetMetaData metaData = null;
		try {
			if (StringUtil.isNotEmpty(dataConfig)) {
				// 切换数据源
				CheckModuleHolder.checkout(Module.get(dataConfig));
			}
			sqlSession = stJrsm.getSqlSessionFactory().openSession();
			connection = sqlSession.getConnection();
			pStemt = (Statement) connection.createStatement();
			try {
				if (sql.endsWith(";")) {
					sql = sql.replace(";", "");
				}
				if (null != sql && sql.toLowerCase().indexOf(" limit") == -1) {
					sql = sql + " limit 0,1 ";
				}
				rs = pStemt.executeQuery(sql);
				metaData = rs.getMetaData();
			} catch (Exception e) {
				e.printStackTrace();
				throw new BaseException(sql + "--> sql无法解析, 刷新时请填写可直接解析的sql, 等刷新后再在sql语句的where后加带参数的条件");
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (null != rs ) {
				rs.close();
			}
			if (null != connection) {
				connection.close();
			}
			if (null != pStemt) {
				pStemt.close();
			}
			if (StringUtil.isNotEmpty(dataConfig)) {
				CheckModuleHolder.release();
			}
			sqlSession.close();
		}

		// 获取字段名集合
		this.getSqlAttrList(list, metaData);

		return list;
	}
    
	
	private void getSqlAttrList(List<Map<String, Object>> list, ResultSetMetaData metaData) throws SQLException {
		if (null != metaData) {
			int count = metaData.getColumnCount();
			Map<String, List<Map<String, Object>>> columnListMap = new HashMap<String, List<Map<String, Object>>>();
			List<Map<String, Object>> columnList = null;
			Map<String, Object> columnMap = null;
			for (int i = 1; i <= count; i++) {
				String name = metaData.getColumnName(i);// 获取字段名称
				String tableName = metaData.getTableName(i);
				if (columnListMap.containsKey(tableName)) {
					columnList = columnListMap.get(tableName);
				} else {
					columnList = new ArrayList<Map<String, Object>>();
				}
				columnMap = new HashMap<String, Object>();
				columnMap.put("attr", name);
				columnMap.put("dataType", metaData.getColumnTypeName(i));
				getAlias(metaData.toString(), i,columnMap);
				columnMap.put("javaType", metaData.getColumnClassName(i));
				
				columnList.add(columnMap);
				columnListMap.put(tableName, columnList);
			}
			// 获取数据库备注
			Iterator it = columnListMap.keySet().iterator();
			Map<String, String> remarkMap = null;
			while (it.hasNext()) {
				String tableName = (String) it.next();
				remarkMap = this.listTables(tableName);
				columnList = columnListMap.get(tableName);
				for (Map<String, Object> map : columnList) {
					String columnName = (String) map.get("attr");
					if (remarkMap.containsKey(columnName)) {
						map.put("title", remarkMap.get(columnName));
						map.put("tableName", tableName);
					}
					list.add(map);
				}
			}
		}
	}
	
	private void getAlias(String str,int i,Map<String, Object> columnMap) {
		if(str.contains("\n") || str.contains("\r\n")){  
			String [] s  = str.split("\\n");
			System.out.println(s[i]);
			columnMap.put("alias", getColumn(s[i], "tableName="));
		}
	}
	
	private String getColumn(String str ,String name) {
		return str.substring(str.indexOf(name)+name.length(),str.indexOf(name)+str.substring(str.indexOf(name)).indexOf(","));
	}
	
	private Map<String, String> listTables(String tableName) {
		Map<String, String> resultMap = new HashMap<String, String>();
		SqlSession sqlSession = stJrsm.getSqlSessionFactory().openSession();
		Connection connection = sqlSession.getConnection();
		ResultSet rs = null;
		try {
			DatabaseMetaData dm = connection.getMetaData();
			rs = dm.getColumns(null, null, tableName, "%");
			while (rs.next()) {
				String columnName = rs.getString("COLUMN_NAME");// 列名
				String label = rs.getString("REMARKS");
				resultMap.put(columnName, label);
			}
		} catch (Exception e) {
			try {
				if (rs != null) {
					rs.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e1) {
			}
		} finally {
			sqlSession.close();
		}
		return resultMap;
	}
	
	
	//=======================查询开始===========================================//
	
	 private static Map<String, Integer> sqlRuleIndex = new HashMap<String, Integer>();
	 
	 static {
        sqlRuleIndex.put("SELECT", 0);
        sqlRuleIndex.put("FROM", 1);
        sqlRuleIndex.put("JOIN", 2);
        sqlRuleIndex.put("ON", 3);
        sqlRuleIndex.put("WHERE", 4);
        sqlRuleIndex.put("GROUP", 5);
        sqlRuleIndex.put("HAVING", 6);
        sqlRuleIndex.put("ORDER", 7);
        sqlRuleIndex.put("LIMIT", 8);
    }
		/**
	     * 方法用途:获得sql
	     *
	     * @param param
	     * @return
	     * @author kuangzm
	     * @modified 
	     * @date 2019/9/18
	     */
	    @Override
	    public Map<String, Object> listByFormCondition(DynamicSqlParamDTO param) {
        	if (null == param.getPageNum()) {
        		param.setPageNum(1);
        	}
        	if (null == param.getPageSize()) {
        		param.setPageSize(15);
        	}
	    	DynamicSqlConfig config = new DynamicSqlConfig();
	        config.setName(param.getSqlKey());
	        QueryWrapper<DynamicSqlConfig> queryWrapper = new QueryWrapper<>(config);
	        DynamicSqlConfig sqlConfig = dynamicSqlConfigMapper.selectOne(queryWrapper);
	        
	        //查询字段
	        QueryWrapper wrapper = new QueryWrapper();
	        wrapper.eq("SQL_ID", sqlConfig.getSqlId());
	        wrapper.eq("QUERY_ITEM_ENABLED", "Y");
	        List<DynamicSqlAttr> qp = this.dynamicSqlAttrService.list(wrapper);
	        List<DynamicSqlAttr> queryParam = new ArrayList<DynamicSqlAttr>();
	        if (null != qp && qp.size() > 0 && null != param.getQueryParam()) {
	        	for (DynamicSqlAttr attr : qp) {
	        		if (param.getQueryParam().containsKey(underlineToCamel(attr.getAttr()))) {
	        			attr.setValue(param.getQueryParam().get(underlineToCamel(attr.getAttr())));
	        			queryParam.add(attr);
	        		}
	        	}
	        }
	        
	        String language = this.queryByFormCondition(sqlConfig,queryParam);
	        //language += " limit " + (page - 1) * pageSize + " ," + pageSize;
	        //切换数据源
	        List<Map<String, Object>> list = null;
	        try {
	        	
	            PageUtil.startPage(param.getPageNum(), param.getPageSize());
	            CheckModuleHolder.checkout(Module.get(config.getQueryModule()));
	            list = dynamicSqlConfigMapper.getSqlData(language);
	        } finally {
	            CheckModuleHolder.release();
	        }
	        Map<String, Object> result = new HashMap<>();
	        if (!CollectionUtils.isEmpty(convertList(list))) {
	            result.put("list", convertList(list));
	        } else {
	            result.put("list", new ArrayList());
	        }
	        //查询总条数
	        Integer totalCount = this.countPopupWindowInfo(sqlConfig,queryParam);
	        if (totalCount != null) {
	            result.put("total", totalCount);
	        }
	        if (null != config) {
	        	result.put("title", sqlConfig.getDescription());
	        }
	        return result;
	    }
	    
	    public List<Map<String, Object>> convertList(List<Map<String, Object>> list) {
	        List<Map<String, Object>> retlist = new ArrayList<>();
	        for (int i = 0; i < list.size(); i++) {
	            Map<String, Object> data = list.get(i);
	            Map<String, Object> newdata = new HashMap<>();
	            for (Map.Entry<String, Object> entry : data.entrySet()) {
	                newdata.put(underlineToCamel(entry.getKey()),
	                        entry.getValue());
	            }
	            retlist.add(newdata);
	        }
	        return retlist;
	    }
	    
	    /**
	     * 方法用途:获得总条数
	     *
	     * @param param
	     * @return
	     * @author kuangzm
	     * @modified 
	     * @date 2019/9/18
	     */
	    @Override
	    public Integer countPopupWindowInfo(DynamicSqlConfig config,List<DynamicSqlAttr> queryParam) {
	        String language = this.queryByFormCondition(config,queryParam);
	        String countStr = getQuickDataCountStr(language);
	        Integer count = null;
	        try {
	            CheckModuleHolder.checkout(Module.get(config.getQueryModule()));
	            count = dynamicSqlConfigMapper.getSqlDataCount(countStr);
	        } finally {
	            CheckModuleHolder.release();
	        }
	        return count > 0 ? count : 0;
	    }
	    
	    /**
	     * sql注入检验
	     *
	     * @param str
	     * @return
	     */
	    public static boolean sql_inj(String str) {
//			String inj_str = "'|and|exec|insert|select|delete|update|count|*|%|chr|mid|master|truncate|char|declare|;|or|-|+|,";
	        if (StringUtils.isBlank(str)) {
	            return false;
	        }
	        String inj_str = "'| or ";
	        String inj_stra[] = inj_str.split("\\|");
	        for (int i = 0; i < inj_stra.length; i++) {
	            if (str.indexOf(inj_stra[i]) >= 0) {
	                return true;
	            }
	        }
	        return false;
	    }
	    
	    public String queryByFormCondition(DynamicSqlConfig config, List<DynamicSqlAttr> queryParam) {
	        String sql = config.getQuerySql();
	        String regex = "(?i)ORDER\\s+BY";
	        String language;
	        String languageOrderBy = "";
	        String[] sqlSplit = sql.split(regex);
	        if (sqlSplit.length == 1) {
	            language = sql;
	        } else {
	            language = sqlSplit[0];
	            languageOrderBy = " ORDER BY " + sqlSplit[1];
	        }
	        if (queryParam != null) {
	            if (language.toLowerCase().contains(QuicksearchConfigFields.Fields.WHERE)) {
	                language = language + " and";
	            } else {
	                language = language + " where";
	            }
	            language = this.getNewLanguage(queryParam, language);
	            language += languageOrderBy;
	        }
	        return language;
	    }
	    
	    /**
	     * @Author: kuangzm
	     * @Description sql进行拼接
	     * @Date: 2021/1/19
	     * @Param:
	     * @return:
	     **/
	    private String getNewLanguage(List<DynamicSqlAttr> queryParam, String language) {
	    	
	    	for (DynamicSqlAttr param : queryParam) {
	    		 String key = param.getAttr();
	    		 if (null != param.getAlias() && !param.getAlias().isEmpty()) {
	    			 key = param.getAlias()+"."+param.getAttr();
	    		 }
	            Object obj = param.getValue();
	            String match = param.getQueryMatchOperator();
	            if ((obj != null) && (obj.toString().trim().length() != 0)) {
	                
	                String value = "";
	                if (null != param.getDataType() && ( param.getDataType().equals("DATETIME") || param.getDataType().equals("DATE")))  { //先判断是否 时间类型
	                	String [] dr = obj.toString().split(",");
	                	if (null != dr[0] && !dr[0].isEmpty()) {
	                		language = language +" "+ key +" >= '" +dr[0]+"'";
	                		 language = language + " and";
	                	}
	                	if (null != dr[1] && !dr[1].isEmpty()) {
	                		language = language +" "+ key +" <= '" +dr[1]+"'";
	                		 language = language + " and";
	                	}
	                } else { //如果不是时间类型则，查询匹配方式
	                	language = language + " ((" + getReplaceStr(key);
	                	if (EXACT.equals(match)) {
		                    value = "%" + obj + "%";
		                    language = language + " like  '" + value.trim() + "')or(" + "  " + key+ " like  '" + value.trim() + "'))";
			                language = language + " and";
		                } else if (FUZZY.equals(match)) {
		                    value = String.valueOf(obj);
		                    language = language + " like  '" + value.trim() + "')or(" + "  " + key+ " like  '" + value.trim() + "'))";
			                language = language + " and";
		                } else {
		                	value = String.valueOf(obj);
		                    language = language + " like  '" + value.trim() + "')or(" + "  " + key+ " like  '" + value.trim() + "'))";
			                language = language + " and";
		                }
	                }
	            }
	    	}
	        if (language.toLowerCase().endsWith("and")) {
	            language = language.substring(0, language.length() - 3);
	        } else if (language.toLowerCase().endsWith(QuicksearchConfigFields.Fields.WHERE)) {
	            language = language.substring(0, language.length() - 5);
	        }
	        return language;
	    }
	    
	    private static String getReplaceStr(String replaceStr) {
	        String[] replaceArr = {"(", ")", "/", "_", "-"};
	        for (String r : replaceArr) {
	            StringBuffer replaceResult = new StringBuffer();
	            replaceResult.append(" replace (").append(replaceStr).append(",").append("'" + r + "'").append(",").append("''").append(") ");
	            replaceStr = replaceResult.toString();
	        }
	        return replaceStr;
	    }
	    
	    /**
	     * 拼接 计算弹窗查询记录总数 sql
	     *
	     * @param originSql
	     * @return
	     */
	    private static String getQuickDataCountStr(String originSql) {
	        // 目标SQL拼接
	        StringBuffer targetSqlBuf = new StringBuffer();
	        // 先将()语句位置贮藏起来
	        Map<String, String> bracketSqlMap = new HashMap<String, String>(); // key : uuid , value: 最外层的括号内所有SQL
	        Position bracketSqlPosition; // 最外层的()括号开始结束位置,保持平衡
	        while ((bracketSqlPosition = getBalancePosition("\\(|\\)", "(", ")", originSql)) != null) {
	            String uuid = " " + UUID.randomUUID().toString() + " "; // 生成UUID
	            String bracketSql = originSql.substring(bracketSqlPosition.getStart(), bracketSqlPosition.getEnd()); // 获取原始SQL中的括号字符串内的所有字符
	            bracketSqlMap.put(uuid, bracketSql); // 记录括号sql
	            originSql = originSql.substring(0, bracketSqlPosition.getStart())
	                    + uuid + originSql.substring(bracketSqlPosition.getEnd(), originSql.length()); // 将原始字符的括号SQL替换成uuid, 后续匹配完对应sql规则后在还原
	        }
	        // SQL关键字顺序解析位置匹配表达式
	        String sqlParserReg = "((?<=^|\\(|\\s)select(?=\\s))|((?<=^|\\s)from(?=\\s|\\())|((?<=\\s)join(?=\\s))|((?<=\\s)on(?=\\s))|((?<=\\s)where(?=\\s))|((?<=\\s)group\\s+by(?=\\s))|((?<=\\s)having(?=\\s))|((?<=\\s)order\\s+by(?=\\s))|((?<=\\s)limit(?=\\s))";
	        LinkedList<Position[]> matchKeywordList = new LinkedList<Position[]>(); // 匹配的关键字位置记录列表
	        Pattern pattern = Pattern.compile(sqlParserReg, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE); // 忽略大小写, 换行匹配
	        Matcher matcher = pattern.matcher(originSql);
	        try {
	            while (matcher.find()) {
	                String upperCaseKeyword = matcher.group().toUpperCase(); // 匹配中的关键字
	                upperCaseKeyword = upperCaseKeyword.replaceAll("\\s+", "XmarkX"); // 替换空白符并标记
	                Integer blankIndex = upperCaseKeyword.indexOf("XmarkX"); // 寻找标记开始位置
	                Integer keywordIndex = sqlRuleIndex.get(upperCaseKeyword.substring(0, blankIndex == -1 ? upperCaseKeyword.length() : blankIndex)); // 获取sql顺序规则下标
	                // SELECT, FROM, JOIN, ON, WHERE, GROUP, HAVING, ORDER, LIMIT
	                //    0      1     2    3    4      5       6      7      8
	                if (null == keywordIndex || keywordIndex == 0) { // 获取到的关键字是 SELECT 时, 初始化一个记录位置数组（可覆盖位置值）
	                    matchKeywordList.add(new Position[sqlRuleIndex.size()]);
	                }
	                Position[] lastMatchKeyword = matchKeywordList.getLast(); // 获取最后一个匹配关键字的完整SQL
	                lastMatchKeyword[keywordIndex] = new Position(matcher.start(), matcher.end());
	            }
	        } catch (Exception e) {
	            throw new BaseException("解析sql异常");
	        }

	        if (matchKeywordList.isEmpty()) {
	            return originSql;
	        }

	        Position[] lastMatchKeyword = matchKeywordList.getLast();
	        // SELECT, FROM, JOIN, ON, WHERE, GROUP, HAVING, ORDER, LIMIT
	        //    0      1     2    3    4      5       6      7      8
	        Position selectKwPosition = lastMatchKeyword[0]; // SELECT关键字匹配位置
	        Position fromKwPosition = lastMatchKeyword[1]; // FROM关键字匹配位置
	        targetSqlBuf.append(originSql.substring(0, selectKwPosition.getEnd()))
	                .append(" count(1) ") // 替换查询的字段成统计字符
	                .append(originSql.substring(fromKwPosition.getStart()));

	        // 还原之前将原始字符的括号SQL替换成uuid的SQL
	        Iterator<Map.Entry<String, String>> it = bracketSqlMap.entrySet().iterator();
	        String targetSql = targetSqlBuf.toString();
	        while (it.hasNext()) {
	            Map.Entry<String, String> entry = it.next();
	            targetSql = targetSql.replace(entry.getKey(), entry.getValue());
	        }
	        return targetSql;
	    }
	    
	    /**
	     * 通过正则表达式获取一对标签开头结尾的位置信息
	     *
	     * @param reg
	     * @param startIdent
	     * @param endIdent
	     * @param originSql
	     * @return
	     */
	    public static Position getBalancePosition(String reg, String startIdent, String endIdent, String originSql) {
	        Pattern pattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
	        Matcher matcher = pattern.matcher(originSql);
	        int balanceCount = 0;
	        LinkedList<Position> allPosition = new LinkedList<Position>();
	        while (matcher.find()) {
	            allPosition.add(new Position(matcher.start(), matcher.end()));
	            if (matcher.group().toUpperCase().equals(startIdent.toUpperCase())) {
	                balanceCount++;
	            } else if (matcher.group().toUpperCase().equals(endIdent.toUpperCase())) {
	                balanceCount--;
	            }
	            if (balanceCount == 0) {
	                return new Position(allPosition.getFirst().getStart(), allPosition.getLast().getEnd());
	            }
	        }
	        return null;
	    }
	    
	    /**
	     * 拼写格式转换：下划线分割转换成驼峰命名法
	     *
	     * @param param
	     * @return
	     */
	    private String underlineToCamel(String param) {
	        param = param.toLowerCase();
	        if (param == null || "".equals(param.trim())) {
	            return "";
	        }
	        int len = param.length();
	        StringBuilder sb = new StringBuilder(len);
	        for (int i = 0; i < len; i++) {
	            char c = param.charAt(i);
	            if (c == '_') {
	                if (++i < len) {
	                    sb.append(Character.toUpperCase(param.charAt(i)));
	                }
	            } else {
	                sb.append(c);
	            }
	        }
	        return sb.toString();
	    }
	    
		//=======================查询结束===========================================//
		
	    /**
	     * 匹配位置信息
	     */
	    static class Position {
	        private int start; // 匹配开始位置
	        private int end; // 匹配结束位置

	        public Position(int start, int end) {
	            this.start = start;
	            this.end = end;
	        }

	        public int getStart() {
	            return start;
	        }

	        public void setStart(int start) {
	            this.start = start;
	        }

	        public int getEnd() {
	            return end;
	        }

	        public void setEnd(int end) {
	            this.end = end;
	        }

	    }
	
	
}

