package com.midea.cloud.gernator.autocode.mapper;

import com.midea.cloud.gernator.autocode.entity.TableDefDTO;
import com.midea.cloud.gernator.autocode.entity.TableNameDTO;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *  代码生成器 Dao
 * </pre>
 *
 * @author yixinyx@meicloud.com
 * @version 1.00.00
 *
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */

@Repository
public interface GeneratorMapper {

    /**
     *  查看所有表 Oracle
     * @param tableNameDTO
     * @return
     */
    List<TableNameDTO> listTableForOracle(TableNameDTO tableNameDTO);

    /**
     *  查看所有表 MySQL
     * @param tableNameDTO
     * @return
     */
    List<TableNameDTO> listTableForMySQL(TableNameDTO tableNameDTO);

    /**
     *  根据加表名查询主键 Oracle
     * @param tableName
     * @return
     */
   String getPkColumnNameByTableNameForOracle(String tableName);

    /**
     *  根据加表名查询主键 MySQL
     * @param params
     * @return
     */
    String getPkColumnNameByTableNameForMySQL(Map<String, Object> params);

    /**
     * 根据名字查看表结构信息 Oracle
     * @param tableName
     * @return
     * @throws Exception
     */
    List<TableDefDTO> getTableDefInfoByNameForOracle(String tableName);

    /**
     * 根据名字查看表结构信息 MySQL
     * @param params
     * @return
     * @throws Exception
     */
    List<TableDefDTO> getTableDefInfoByNameForMySQL(Map<String, Object> params);
}
