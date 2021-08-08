package com.midea.cloud.srm.model.common.assist.handler;

import com.midea.cloud.srm.model.common.assist.util.AESUtil;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <pre>
 *  对需要加解密处理的数据库字段进行处理
 * </pre>
 *
 * @author huanghb14@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-3-23 19:19
 *  修改内容:
 * </pre>
 */
public abstract class AbstractAESEncryptHandler<T> extends BaseTypeHandler<T> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
        // 参数加密，数据库默认都是用varchar保存，长度尽可能设置长一点
        ps.setString(i, AESUtil.encrypt(parameter.toString()));
    }

    @Override
    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        // 参数解密
        String columnValue = rs.getString(columnName);
        if (columnValue == null) {
            return null;
        }
        // 自定义实现类型转换
        return formatResultFiled(columnValue);
    }

    @Override
    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        // 参数解密
        String columnValue = rs.getString(columnIndex);
        if (columnValue == null) {
            return null;
        }
        // 自定义实现类型转换
        return formatResultFiled(columnValue);
    }

    @Override
    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        // 参数解密
        String columnValue = cs.getString(columnIndex);
        if (columnValue == null) {
            return null;
        }
        // 自定义实现类型转换
        return formatResultFiled(columnValue);
    }

    /**
     * 自定义实现类型转换
     *
     * @param columnValue
     * @return
     */
    public abstract T formatResultFiled(String columnValue);

}
