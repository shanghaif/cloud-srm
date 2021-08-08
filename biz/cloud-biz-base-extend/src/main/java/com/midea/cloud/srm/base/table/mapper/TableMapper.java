package com.midea.cloud.srm.base.table.mapper;

import com.midea.cloud.srm.model.common.TableField;
import com.midea.cloud.srm.model.common.TableInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TableMapper {

    @Select("select * FROM information_schema.TABLES  WHERE table_type = 'BASE TABLE'")
    List<TableInfo> listTable();

    @Select({"<script>",
            "select * from information_schema.COLUMNS where  TABLE_NAME in  ",
            "<foreach item='item' index='index' collection='tableName' open='(' separator=', ' close=')'>" ,
            "#{item}" ,
            "</foreach>",
            "</script>"})
    List<TableField> listTableColumn(@Param("tableName") List<String> tableName);
}
