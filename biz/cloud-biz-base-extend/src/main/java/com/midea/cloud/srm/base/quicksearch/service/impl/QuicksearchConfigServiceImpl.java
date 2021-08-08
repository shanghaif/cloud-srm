package com.midea.cloud.srm.base.quicksearch.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.constants.QuicksearchConfigFields;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.*;
import com.midea.cloud.dynamicds.bind.CheckModuleHolder;
import com.midea.cloud.dynamicds.enums.Module;
import com.midea.cloud.srm.base.quicksearch.mapper.QuicksearchConfigMapper;
import com.midea.cloud.srm.base.quicksearch.service.IQuicksearchConfigService;
import com.midea.cloud.srm.model.base.quicksearch.entity.QuicksearchAttrConfig;
import com.midea.cloud.srm.model.base.quicksearch.entity.QuicksearchConfig;
import com.midea.cloud.srm.model.base.quicksearch.param.JsonParam;
import com.midea.cloud.srm.model.base.quicksearch.vo.QuicksearchConfigVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <pre>
 *  快速查询 实现类
 * </pre>
 *
 * @author fengdc3@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-3-5 17:04
 *  修改内容:
 * </pre>
 */
@Service
@Slf4j
public class QuicksearchConfigServiceImpl extends ServiceImpl<QuicksearchConfigMapper, QuicksearchConfig> implements IQuicksearchConfigService {

    @Autowired
    protected SqlSessionTemplate stJrsm;
    @Autowired
    private QuicksearchAttrConfigServiceImpl quicksearchAttrConfigServiceImpl;
    @Autowired
    private QuicksearchConfigMapper quicksearchConfigMapper;

    /**
     * 组装data结构
     */
    public static JSONObject getDataJSONObject(List<Map<String, String>> list,
                                               String xid, String idColumn) {
        String dataFormatJson = String.format(QuicksearchConfigFields.DATA_FORMAT, xid, idColumn);
        JSONObject jsonObject = JSONObject.parseObject(dataFormatJson);
        JSONObject jsonFieldObjects = new JSONObject();
        for (int i = 0; i < list.size(); i++) {
            Map<String, String> fieldMap = list.get(i);
            String name = fieldMap.get("name");
            String label = fieldMap.get(QuicksearchConfigFields.Fields.LABEL);
            String dataField = String.format(QuicksearchConfigFields.DATA_FIELD_FORMAT, name, name, name,
                    label, "String");
            JSONObject dataFieldJsonObject = JSONObject.parseObject(dataField);
            jsonFieldObjects.put(name, dataFieldJsonObject);
        }
        if (jsonObject != null) {
            jsonObject.put("defCols", jsonFieldObjects);
        } else {
            jsonObject = new JSONObject();
            jsonObject.put("defCols", jsonFieldObjects);
        }
        return jsonObject;
    }

//    /**
//     * 拼接 计算弹窗查询记录总数 sql
//     *
//     * @param str
//     * @return
//     */
//    private static String getQuickDataCountStr(String str) {
//        String regex = "(?<=select).*(?=from)";
//        Pattern pattern = Pattern.compile(regex);
//        Matcher matcher = pattern.matcher(str);
//        String resultStr = "";
//        while (matcher.find()) {
//            resultStr = matcher.replaceAll("   count(1)   ");
//        }
//        return resultStr;
//    }

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
                if (keywordIndex == 0) { // 获取到的关键字是 SELECT 时, 初始化一个记录位置数组（可覆盖位置值）
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

    /**
     * 分页查询 快速查询配置列表
     *
     * @param param
     * @return
     */
    @Override
    public PageInfo<QuicksearchConfig> listPage(QuicksearchConfig param) {
        PageUtil.startPage(param.getPageNum(), param.getPageSize());
        QueryWrapper<QuicksearchConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(!StringUtil.isEmpty(param.getName()), "NAME", param.getName());
        queryWrapper.like(!StringUtil.isEmpty(param.getDescription()), "DESCRIPTION", param.getDescription());
        queryWrapper.like(!StringUtil.isEmpty(param.getQueryTable()), "QUERY_TABLE", param.getQueryTable());
        return new PageInfo<>(this.list(queryWrapper));
    }


    /**
     * 获取 快速查询配置页总记录数
     *
     * @param param
     * @return
     */
    @Override
    public Integer countConfig(QuicksearchConfig param) {
        QueryWrapper<QuicksearchConfig> queryWrapper = new QueryWrapper<>(param);
        return quicksearchConfigMapper.selectCount(queryWrapper);
    }


    /**
     * 获取快速查询配置详情
     *
     * @param param
     * @return
     */
    @Override
    public QuicksearchConfigVO getDetail(QuicksearchConfig param) {
        QueryWrapper<QuicksearchConfig> queryWrapper = new QueryWrapper<>(param);
        QuicksearchConfig quicksearchConfig = quicksearchConfigMapper.selectOne(queryWrapper);
        if (quicksearchConfig == null)
            return null;
        QuicksearchAttrConfig quicksearchAttrConfig = new QuicksearchAttrConfig();
        quicksearchAttrConfig.setQuicksearchId(quicksearchConfig.getQuicksearchConfigId());
        QuicksearchConfigVO quicksearchConfigVo = new QuicksearchConfigVO();
        quicksearchConfigVo.setAttrConfigs(quicksearchAttrConfigServiceImpl.listQuicksearchAttrConfig(quicksearchAttrConfig));
        return quicksearchConfigVo;
    }

    /**
     * 查询表信息
     *
     * @param param
     * @return
     */
    @Override
    public List<Map<String, String>> listSchemaTables(QuicksearchConfig param) {
        List<Map<String, String>> tableMaps = new ArrayList<>();

        SqlSession sqlSession = stJrsm.getSqlSessionFactory().openSession();
        Connection connection = sqlSession.getConnection();
        ResultSet rs = null;
        try {
            DatabaseMetaData dm = connection.getMetaData();
            ResultSet tables = dm.getTables(param.getQueryDatasource(), null, null, new String[]{"TABLE"});
            System.out.println(tables);
            while (tables.next()) {
                Map<String, String> tableMap = new HashMap();
                String tableNameStr = tables.getString("TABLE_NAME");
                tableMap.put("tableName",tableNameStr);
                tableMaps.add(tableMap);
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
                log.info("数据错误！！！", e1);
            }
            log.info("数据错误！！！", e);
        } finally {
            sqlSession.close();
        }
        return tableMaps;
    }

    /**
     * 查询表信息
     *
     * @param param
     * @return
     */
    @Override
    public List<Map<String, String>> listTables(QuicksearchConfig param) {
        List<Map<String, String>> list = new ArrayList<>();

        SqlSession sqlSession = stJrsm.getSqlSessionFactory().openSession();
        Connection connection = sqlSession.getConnection();
        ResultSet rs = null;
        try {
            String tableName = param.getQueryTable();
            DatabaseMetaData dm = connection.getMetaData();
            rs = dm.getColumns(null, null, tableName, "%");
            ResultSet pks = dm.getPrimaryKeys(null, null, tableName);
            List<String> primaryKeys = new ArrayList<>();
            //获取主键
            while (pks.next()) {
                primaryKeys.add(pks.getString(4));
            }
            while (rs.next()) {
                String columnName = rs.getString("COLUMN_NAME");// 列名
                String label = rs.getString("REMARKS");
                Map<String, String> map = new HashMap<>();
                map.put("title",
                        (label != null && label.trim().length() > 0) ? label
                                : columnName);
                map.put("attr", columnName.toUpperCase());
                map.put("alias", "t");
                map.put("isMain", "N");
                map.put("typeName", rs.getString("TYPE_NAME"));
                map.put("nullAble", "0".equals(rs.getString("NULLABLE")) ? "N":"Y");
                map.put("mainName", rs.getString(6));
                list.add(map);
            }
            //判断是否主键
            for (Map<String, String> stringStringMap : list) {
                for (String primaryKey : primaryKeys) {
                    if (stringStringMap.get("attr").equals(primaryKey)) {
                        stringStringMap.put("isMain","Y");
                    }
                }
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
                log.info("数据错误！！！", e1);
            }
            log.info("数据错误！！！", e);
        } finally {
            sqlSession.close();
        }
        return list;
    }


    /**
     * 查询快速查询配置
     *
     * @param param
     * @return
     */
    @Override
    public Map<String, Object> getConfig(QuicksearchConfig param) {
        QueryWrapper<QuicksearchConfig> queryWrapper = new QueryWrapper<>(param);
        QuicksearchConfig config = quicksearchConfigMapper.selectOne(queryWrapper);
        if (config == null) {
            return null;
        }
        QuicksearchAttrConfig attrParam = new QuicksearchAttrConfig();
        attrParam.setQuicksearchId(config.getQuicksearchConfigId());
        List<QuicksearchAttrConfig> configs = quicksearchAttrConfigServiceImpl.listQuicksearchAttrConfig(attrParam);
        List<QuicksearchAttrConfig> queryConfigs = new ArrayList<>();
        List<QuicksearchAttrConfig> showConfigs = new ArrayList<>();
        List<String> selectCols = new ArrayList<>();
        List<Map<String, Object>> selectDatas = new ArrayList<>();
        //设置查询数据和查询的列
        this.setSelectDataList(selectCols, selectDatas, configs, queryConfigs, showConfigs);
        Map<String, Object> configDatas = new HashMap<>();
        List<Object> queryFields = new ArrayList<>();
        List<Map<String, String>> queryDatas = new ArrayList<>();

        for (int i = 0; i < queryConfigs.size(); i++) {
            QuicksearchAttrConfig attconfig = queryConfigs.get(i);
            Map<String, String> query = new HashMap<>();
            query.put("refData",
                    attconfig.getAlias() + "." + attconfig.getAttr());
            query.put(QuicksearchConfigFields.Fields.LABEL, attconfig.getTitle());
            query.put(QuicksearchConfigFields.Fields.WIDTH, attconfig.getColumnWidth());
            query.put("sqlColumn", attconfig.getAttr());
            query.put("girdColumn", underlineToCamel(attconfig.getAttr()));
            query.put("filedType", attconfig.getFiledtype());
            query.put("filedOption", attconfig.getFiledoption());
            queryFields.add(query);

            Map<String, String> fieldMap = new HashMap<>();
            fieldMap.put("name",
                    attconfig.getAlias() + "." + attconfig.getAttr());
            fieldMap.put(QuicksearchConfigFields.Fields.LABEL, attconfig.getTitle());
            queryDatas.add(fieldMap);
        }
        // 增加主键列
        Map<String, String> idFieldObject = JSONObject.parseObject(
                "{\"name\":\"_id_column\"}", Map.class);
        queryDatas.add(idFieldObject);
        configDatas.put("queryFields", queryFields);

        JSONObject queryDatasObj = getDataJSONObject(queryDatas,
                param.getName() + "_xid_quickQuery_query_data", "_id_column");
        configDatas.put("queryData", queryDatasObj);

        configDatas.put("valueAttr", param.getValueAttr());
        configDatas.put("codeAttr", param.getCodeAttr());
        configDatas.put("selectDatas", selectDatas);
        //下拉的列
        configDatas.put("selectCols", selectCols);
        JSONArray colModelArray = new JSONArray();
        List<Map<String, String>> gridDatas = new ArrayList<>();
        List<Object> showFields = new ArrayList<>();
        for (int i = 0; i < showConfigs.size(); i++) {
            QuicksearchAttrConfig attconfig = showConfigs.get(i);
            Map<String, String> query = new HashMap<>();
            query.put("refData",
                    attconfig.getAlias() + "." + attconfig.getAttr());
            query.put(QuicksearchConfigFields.Fields.LABEL, attconfig.getTitle());
            query.put(QuicksearchConfigFields.Fields.WIDTH, attconfig.getColumnWidth());
            query.put("sqlColumn", attconfig.getAttr());
            showFields.add(query);

            JSONObject gridColumn = new JSONObject();
            gridColumn.put(QuicksearchConfigFields.Fields.LABEL, attconfig.getTitle());
            gridColumn.put(QuicksearchConfigFields.Fields.WIDTH, attconfig.getColumnWidth());
            gridColumn.put("name",
                    underlineToCamel(attconfig.getAttr()));
            colModelArray.add(gridColumn);

            Map<String, String> fieldMap = new HashMap<>();
            fieldMap.put("name",
                    underlineToCamel(attconfig.getAttr()));
            fieldMap.put(QuicksearchConfigFields.Fields.LABEL, attconfig.getTitle());
            gridDatas.add(fieldMap);
        }
        configDatas.put("showFields", showFields);
        configDatas.put("colModel", colModelArray);
        JSONObject gridDatasObj = getDataJSONObject(gridDatas, param.getName()
                        + "_xid_quickQuery_grid_data",
                underlineToCamel(config.getValueAttr().toLowerCase()));
        configDatas.put("gridData", gridDatasObj);
        return configDatas;
    }

    /**
     * @Author: yehui
     * @modified fengdc3
     * @Description //设置查询数据和查询的列
     * @Date: 2019/11/16  11:16
     * @Param:
     * @return:
     **/
    private void setSelectDataList(List<String> selectCols,
                                   List<Map<String, Object>> selectDatas,
                                   List<QuicksearchAttrConfig> configs,
                                   List<QuicksearchAttrConfig> queryConfigs,
                                   List<QuicksearchAttrConfig> showConfigs) {
        for (int i = 0; i < configs.size(); i++) {
            QuicksearchAttrConfig attconfig = configs.get(i);
            if ("Y".equals(attconfig.getQueryItemEnabled())) {
                queryConfigs.add(attconfig);
            }
            if ("Y".equals(attconfig.getDisplayItemEnabled())) {
                showConfigs.add(attconfig);
            }
            if ("select".equals(attconfig.getFiledtype()) && attconfig.getFiledoption() != null)//设置下拉的data数据
            {
                List<Map<String, String>> selectqueryDatas = new ArrayList<>();

                Map<String, String> selectnamefieldMap = new HashMap<>();
                selectnamefieldMap.put("name", "name");
                selectqueryDatas.add(selectnamefieldMap);
                Map<String, String> selectcodefieldMap = new HashMap<>();
                selectcodefieldMap.put("name", "code");
                selectqueryDatas.add(selectcodefieldMap);
                JSONObject queryDatasObj = getDataJSONObject(selectqueryDatas,
                        underlineToCamel(attconfig.getAttr()) + "_xid_quickQuery_select_data", "code");
                Map<String, Object> selectMap = new HashMap<>();
                selectMap.put("key", attconfig.getFiledoption());
                selectMap.put("value", queryDatasObj);
                selectDatas.add(selectMap);

                selectCols.add(underlineToCamel(attconfig.getAttr()));
            }
        }

    }

    /**
     * 方法用途:解析条件
     *
     * @param param
     * @return
     * @author yehui
     * @modified fengdc3
     * @date 2019/9/18
     */
    public Map<String, Object> getParamConfig(JsonParam param) {
        String params = param.getParams();
        Map<String, String> paramsMap = JsonUtil.parseJsonStrToMap(params);
        String queryCondition = paramsMap.get("query");
        String extendCondition = paramsMap.get("extendQuery");
        Map<String, String> extendConditions = JsonUtil.parseJsonStrToMap(extendCondition);
        String createBy = "admin";
        String entityId = param.getEntityId();
        if (StringUtil.isEmpty(entityId)) {
            extendConditions.put("entityId", "10");
        }
        if (StringUtil.isEmpty(createBy)) {
            extendConditions.put("createdBy", createBy);
        }
        String id = extendConditions.get(QuicksearchConfigFields.Fields.QUICK_KEY);
        if (sql_inj(queryCondition) || (sql_inj(extendCondition))) {
            return new HashMap<>();
        }
        QuicksearchConfig config = new QuicksearchConfig();
        config.setName(id);
        QueryWrapper<QuicksearchConfig> queryWrapper = new QueryWrapper<>(config);
        QuicksearchConfig quicksearchConfig = quicksearchConfigMapper.selectOne(queryWrapper);
        if (quicksearchConfig == null) {
            return new HashMap<>();
        }
        //设置sql
        this.setQuerySql(quicksearchConfig, extendConditions);
        Map<String, Object> map = new HashMap<>();
        map.put(QuicksearchConfigFields.Fields.CONFIG, quicksearchConfig);
        map.put(QuicksearchConfigFields.Fields.QUERYCONDITION, queryCondition);
        return map;
    }

    /**
     * @Author: yehui
     * @modified fengdc3
     * @Description 设置sql
     * @Date: 2019/11/16  14:30
     * @Param:
     * @return:
     **/
    private void setQuerySql(QuicksearchConfig config, Map<String, String> extendConditions) {
        String querySql = config.getDialogQueryLanguage();
        for (Map.Entry<String, String> stringStringEntry : extendConditions.entrySet()) {
            String key = stringStringEntry.getKey();
            if ((key != null) && (!"".equals(key)) && !QuicksearchConfigFields.Fields.QUICK_KEY.equals(key)) {
                String paramValue = extendConditions.get(key);
                if (paramValue != null) {
                    querySql = querySql.replace(":" + key, "'" + paramValue
                            + "'");
                }
            }
        }

        config.setDialogQueryLanguage(querySql);
    }

    /**
     * 方法用途:获得sql
     *
     * @param param
     * @return
     * @author yehui
     * @modified fengdc3
     * @date 2019/9/18
     */
    @Override
    public Map<String, Object> listByFormCondition(JsonParam param) {
        Map<String, Object> paramConfig = getParamConfig(param);
        if (MapUtils.isEmpty(paramConfig)) {
            return paramConfig;
        }
        QuicksearchConfig config = (QuicksearchConfig) paramConfig.get(QuicksearchConfigFields.Fields.CONFIG);
        String queryCondition = paramConfig.get(QuicksearchConfigFields.Fields.QUERYCONDITION).toString();
        String language = this.queryByFormCondition(config,
                JsonUtil.parseJsonStrToMap(queryCondition));
        //language += " limit " + (page - 1) * pageSize + " ," + pageSize;
        //切换数据源
        List<Map<String, Object>> list = null;
        try {
            PageUtil.startPage(param.getPageNum(), param.getPageSize());
            CheckModuleHolder.checkout(Module.get(config.getQueryModule()));
            list = quicksearchConfigMapper.getQuickData(language);
        } finally {
            CheckModuleHolder.release();
        }
        Map<String, Object> result = new HashMap<>();
        if (!CollectionUtils.isEmpty(convertList(list))) {
            result.put("data", convertList(list));
        } else {
            result.put("data", new ArrayList());
        }
        //查询总条数
        Integer totalCount = this.countPopupWindowInfo(param);
        if (totalCount != null) {
            result.put("totalCount", totalCount);
        }
        if (null != config) {
        	result.put("title", config.getDescription());
        }
        return result;
    }

    /**
     * 方法用途:获得总条数
     *
     * @param param
     * @return
     * @author yehui
     * @modified fengdc3
     * @date 2019/9/18
     */
    @Override
    public Integer countPopupWindowInfo(JsonParam param) {
        Map<String, Object> paramConfig = getParamConfig(param);
        QuicksearchConfig config = (QuicksearchConfig) paramConfig.get(QuicksearchConfigFields.Fields.CONFIG);
        String queryCondition = paramConfig.get(QuicksearchConfigFields.Fields.QUERYCONDITION).toString();
        String language = this.queryByFormCondition(config,
                JsonUtil.parseJsonStrToMap(queryCondition));
        String countStr = getQuickDataCountStr(language);
        Integer count = null;
        try {
            CheckModuleHolder.checkout(Module.get(config.getQueryModule()));
            count = quicksearchConfigMapper.getQuickDatacount(countStr);
        } finally {
            CheckModuleHolder.release();
        }
        return count > 0 ? count : 0;
    }

    public String queryByFormCondition(QuicksearchConfig config, Map map) {
        String sql = config.getDialogQueryLanguage();
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
        if (map != null) {
            if (language.toLowerCase().contains(QuicksearchConfigFields.Fields.WHERE)) {
                language = language + " and";
            } else {
                language = language + " where";
            }
            Iterator<Map.Entry> iter = map.entrySet().iterator();
            String match = config.getQueryMatchOperator();
            language = this.getNewLanguage(iter, language, match);
            language += languageOrderBy;
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
     * @Author: yehui
     * @Description sql进行拼接
     * @Date: 2019/11/16  11:35
     * @Param:
     * @return:
     **/
    private String getNewLanguage(Iterator<Map.Entry> iter, String language, String match) {
        while (iter.hasNext()) {
            Map.Entry entry = iter.next();
            Object obj = entry.getValue();
            if ((obj != null) && (obj.toString().trim().length() != 0)) {
                language = language + " ((" + getReplaceStr(entry.getKey().toString());
                String value = "";
                if (QuicksearchConfigFields.Fields.ALLMATCH.equals(match)) {
                    value = "%" + obj + "%";
                } else if (QuicksearchConfigFields.Fields.EQUALMATCH.equals(match)) {
                    value = String.valueOf(obj);
                } else if (QuicksearchConfigFields.Fields.LEFTMATCH.equals(match)) {
                    value = obj + "%";
                } else {
                    value = "%" + obj;
                }
                language = language + " like  '" + value.trim() + "')or(" + "  " + entry.getKey() + " like  '" + value.trim() + "'))";
                language = language + " and";
            }
        }
        if (language.toLowerCase().endsWith("and")) {
            language = language.substring(0, language.length() - 3);
        } else if (language.toLowerCase().endsWith(QuicksearchConfigFields.Fields.WHERE)) {
            language = language.substring(0, language.length() - 5);
        }
        return language;
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

    @Override
    public List<Map<String, Object>> listInputInfo(JsonParam param) {
        String params = param.getParams();
        Map<String, String> paramsMap = JsonUtil.parseJsonStrToMap(params);
        String id = paramsMap.get("key");
        String value = paramsMap.get("value");
        String extendQuery = paramsMap.get("extendQuery");
        Map<String, String> extendConditions = JsonUtil.parseJsonStrToMap(extendQuery);
        if (sql_inj(value) || (sql_inj(extendQuery))) {
            return new ArrayList();
        }
        String createBy = "admin";
        extendConditions.put("createdBy", createBy);
        extendConditions.put("entityId", "10");

        QuicksearchConfig config = new QuicksearchConfig();
        config.setName(id);
        QueryWrapper<QuicksearchConfig> queryWrapper = new QueryWrapper<>(config);
        QuicksearchConfig quicksearchConfig = quicksearchConfigMapper.selectOne(queryWrapper);
        List list = null;
        if (quicksearchConfig == null) {
            return list;
        }
        String querySql = quicksearchConfig.getQueryLanguage();
        for (Map.Entry<String, String> stringStringEntry : extendConditions.entrySet()) {
            String key = stringStringEntry.getKey();
            if ((key != null) && (!"".equals(key)) && !QuicksearchConfigFields.Fields.QUICK_KEY.equals(key)) {
                String paramValue = extendConditions.get(key);
                if (paramValue != null) {
                    querySql = querySql.replace(":" + key, "'" + paramValue
                            + "'");
                }
            }
        }
        QuicksearchAttrConfig attrParam = new QuicksearchAttrConfig();
        attrParam.setQuicksearchId(quicksearchConfig.getQuicksearchConfigId());
        List<QuicksearchAttrConfig> configs = quicksearchAttrConfigServiceImpl
                .listQuicksearchAttrConfig(attrParam);
        String alias = configs.get(0).getAlias();
        return this.getResultList(paramsMap, alias, querySql, quicksearchConfig, value);
    }

    /**
     * @Author: yehui
     * @Description 得到结果集
     * @Date: 2019/11/16  11:48
     * @Param:
     * @return:
     **/
    private List<Map<String, Object>> getResultList(Map<String, String> paramsMap, String alias, String querySql, QuicksearchConfig config, String value) {
        String isSetValue = paramsMap.get("isSetValue") + "";
        if ("true".equals(isSetValue)) {
            querySql = this.getTrueQuerySql(alias, config, value, querySql);
            List<Map<String, Object>> datalist = null;
            try {
                CheckModuleHolder.checkout(Module.get(config.getQueryModule()));
                datalist = quicksearchConfigMapper.getQuickData(querySql);
            } finally {
                CheckModuleHolder.release();
            }
            List list = null;
            if (CollectionUtils.isEmpty(datalist)) {
                return list;
            }
            List resultList = new ArrayList();
            resultList.add(datalist.get(0));
            return convertList(resultList);
        } else {
            String match = config.getQueryMatchOperator();
            if (QuicksearchConfigFields.Fields.ALLMATCH.equals(match)) {
                value = "%" + value + "%";
            } else if (QuicksearchConfigFields.Fields.EQUALMATCH.equals(match)) {
                value = String.valueOf(value);
            } else if (QuicksearchConfigFields.Fields.LEFTMATCH.equals(match)) {
                value = value + "%";
            } else {
                value = "%" + value;
            }
            querySql = querySql.replace(":query", "'" + value + "'");
            int page = config.getQueryMaxSize();
            querySql += " limit 0," + page;
            List<Map<String, Object>> datalist = null;
            try {
                //切换数据源
                CheckModuleHolder.checkout(Module.get(config.getQueryModule()));
                datalist = quicksearchConfigMapper.getQuickData(querySql);
            } finally {
                CheckModuleHolder.release();
            }
            return convertList(datalist);
        }
    }

    /**
     * @Author: yehui
     * @Description 当是true的时候拼接的sql
     * @Date: 2019/11/16  13:44
     * @Param:
     * @return:
     **/
    private String getTrueQuerySql(String alias, QuicksearchConfig config, String value, String querySql) {
        String condition = alias + "." + config.getValueAttr();
        boolean typeMisMatch = false;
        try {
            Long.parseLong(value);
        } catch (NumberFormatException e) {
            typeMisMatch = true;
        }
        String regex = "(?i)ORDER\\s+BY";
        String querySqlOrderBy = "";
        String[] sqlSplit = querySql.split(regex);

        if (sqlSplit.length > 1) {
            querySql = sqlSplit[0];
            querySqlOrderBy = " ORDER BY " + sqlSplit[1];
        }

        if (typeMisMatch) {
            if (querySql.toLowerCase().contains(QuicksearchConfigFields.Fields.WHERE)) {
                querySql =
                        querySql.substring(0, querySql.lastIndexOf(QuicksearchConfigFields.Fields.WHERE)) + "  " + QuicksearchConfigFields.Fields.WHERE + "  " + condition + " = '" + value + "'";
            } else {
                querySql = querySql + "  " + QuicksearchConfigFields.Fields.WHERE + "  " + condition + " = '" + value + "'";
            }
        } else {
            if (querySql.toLowerCase().contains(QuicksearchConfigFields.Fields.WHERE)) {
                querySql =
                        querySql.substring(0, querySql.lastIndexOf(QuicksearchConfigFields.Fields.WHERE)) + "  " + QuicksearchConfigFields.Fields.WHERE + "  " + condition + " = " + value;
            } else {
                querySql = querySql + "  " + QuicksearchConfigFields.Fields.WHERE + "  " + condition + " = " + value;
            }
        }
        querySql += querySqlOrderBy;
        String match = config.getQueryMatchOperator();
        if (QuicksearchConfigFields.Fields.ALLMATCH.equals(match)) {
            value = "%" + value + "%";
        } else if (QuicksearchConfigFields.Fields.EQUALMATCH.equals(match)) {
            value = String.valueOf(value);
        } else if (QuicksearchConfigFields.Fields.LEFTMATCH.equals(match)) {
            value = value + "%";
        } else {
            value = "%" + value;
        }
        querySql = querySql.replace(":query", "'" + value + "'");
        return querySql;
    }

    /**
     * 保存配置信息
     *
     * @param param
     * @return
     */
    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void save(QuicksearchConfigVO param) {
        if (param.getQuicksearchConfigId() != null) {
            QuicksearchConfig config = new QuicksearchConfig();
            BeanCopyUtil.copyProperties(config, param);
            quicksearchConfigMapper.updateById(config);
        } else {
            Long id = IdGenrator.generate();
            param.setQuicksearchConfigId(id);
            quicksearchConfigMapper.insert(param);
        }

        Long id = param.getQuicksearchConfigId();
        quicksearchAttrConfigServiceImpl.removeByQuicksearchId(id);

        List<QuicksearchAttrConfig> list = new ArrayList<>();

        for (QuicksearchAttrConfig configParam : param
                .getAttrConfigs()) {
            if (configParam.getQueryItemEnabled() == null) {
                configParam.setQueryItemEnabled("N");
            }
            if (configParam.getDisplayItemEnabled() == null) {
                configParam.setDisplayItemEnabled("N");
            }
            Long attrId = IdGenrator.generate();
            configParam.setQuicksearchAttrId(attrId);
            configParam.setQuicksearchId(id);
            list.add(configParam);
        }
        if (CollectionUtils.isNotEmpty(list)) {
            quicksearchAttrConfigServiceImpl.saveBatch(list);
        }
    }

    /**
     * 删除配置信息
     *
     * @param param
     * @return
     */
    @Override
    @Transactional
    public Integer removeConfig(JsonParam param) {
        String params = param.getParams();
        List<Map<String, Object>> listMap = JsonUtil.jsonStrToListMap(params);
        for (int i = 0; i < listMap.size(); i++) {
            Map<String, Object> map = listMap.get(i);
            Long id = (Long) map.get("id");
            quicksearchAttrConfigServiceImpl.removeByQuicksearchId(id);
            QuicksearchConfig config = new QuicksearchConfig();
            config.setQuicksearchConfigId(id);
            QueryWrapper<QuicksearchConfig> queryWrapper = new QueryWrapper<>(config);
            quicksearchConfigMapper.delete(queryWrapper);
        }
        return listMap.size();
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

    /**
     * sql注入检验
     *
     * @param str
     * @return
     */
    public static boolean sql_inj(String str) {
//		String inj_str = "'|and|exec|insert|select|delete|update|count|*|%|chr|mid|master|truncate|char|declare|;|or|-|+|,";
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

    /*public static String getFileContent(String rootPath, String fileName) {
        File file = new File(rootPath + fileName);
        StringBuilder result = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            //构造一个BufferedReader类来读取文件
            String s = null;
            while ((s = br.readLine()) != null) {//使用readLine方法，一次读一行
                result.append(s.trim());
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException("快速查询操作文件异常");
        }
        return result.toString();
    }*/

}

