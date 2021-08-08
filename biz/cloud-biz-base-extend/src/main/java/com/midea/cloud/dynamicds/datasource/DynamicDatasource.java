package com.midea.cloud.dynamicds.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.midea.cloud.dynamicds.bind.CheckModuleHolder;
import com.midea.cloud.dynamicds.config.DynamicDatasourceConfigProperties;
import com.midea.cloud.dynamicds.enums.DatasourceConfigKey;
import com.midea.cloud.dynamicds.enums.Module;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.jdbc.datasource.AbstractDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

/**
 * <pre>
 *  自定义动态数据源
 * </pre>
 *
 * @author huanghb14@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-3-22 15:35
 *  修改内容:
 * </pre>
 */
public class DynamicDatasource extends AbstractDataSource {

    private static Logger logger = Logger.getLogger(DynamicDatasource.class.getName());

    private Map<Module, DataSource> moduleDataSourceMap = new ConcurrentHashMap<Module, DataSource>();

    public void setDynamicDatasourceConfigProperties(DynamicDatasourceConfigProperties dynamicDatasourceConfigProperties) {
        //默认用druid
    	parseAndAssembleDruid(dynamicDatasourceConfigProperties);
    }

    /**
     * druid数据源
     * @param dynamicDatasourceConfigProperties
     */
    protected void parseAndAssembleDruid(DynamicDatasourceConfigProperties dynamicDatasourceConfigProperties) {
        try {
            dynamicDatasourceConfigProperties.getDatasouces().forEach((k, v) -> {
            	try {
    	  	    	DruidDataSource dataSource = new DruidDataSource();
    	  	    	dataSource.setName(k.getName());
    	            dataSource.setDriverClassName(v.get(DatasourceConfigKey.dirverpath.name()).toString());
    	            dataSource.setUrl(v.get(DatasourceConfigKey.url.name()).toString());
    	            dataSource.setUsername(v.get(DatasourceConfigKey.username.name()).toString());
    	            
    	            Object publickey = v.get(DatasourceConfigKey.publickey.name());
                    if (publickey != null) {
                    	dataSource.setConnectionProperties("config.decrypt=true;config.decrypt.key="+publickey.toString());
                    	dataSource.setFilters("stat,config");
                    }else {
                    	dataSource.setFilters("stat");
                    }

    	            dataSource.setPassword(v.get(DatasourceConfigKey.password.name()).toString());
    	      
                    Object maximumPoolSize = v.get(DatasourceConfigKey.maximumPoolSize.name());
                    if (maximumPoolSize != null) {
                    	dataSource.setMaxActive(Integer.valueOf(maximumPoolSize.toString()));
                    }
                    Object minimumIdle = v.get(DatasourceConfigKey.minimumIdle.name());
                    if (minimumIdle != null) {
                    	dataSource.setMinIdle(Integer.valueOf(minimumIdle.toString()));
                    	dataSource.setInitialSize(Integer.valueOf(minimumIdle.toString()));
                    }

    	            dataSource.setMaxWait( 10000 );
    	            //注意集团的数据库的数据库连接保持时间是300s，所以要比这个少
    	            dataSource.setMinEvictableIdleTimeMillis( 200000 );
    	            
    	            dataSource.setTimeBetweenEvictionRunsMillis( 60000 );
    	            
    	            dataSource.setTestWhileIdle(true);
    	            Object validationQuery = v.get(DatasourceConfigKey.validationQuery.name());
                    if (validationQuery != null) {
        	            dataSource.setValidationQuery(validationQuery.toString());
                    }else {
                    	//默认是mysql，如果是oracle，可以不设置或者在配置中指定
                    	dataSource.setValidationQuery("select 1");
                    }

    	            dataSource.setPoolPreparedStatements(false);
    	            dataSource.setMaxOpenPreparedStatements(-1);

    	            dataSource.setRemoveAbandoned(true);
    	            dataSource.setRemoveAbandonedTimeout(7200);

                    moduleDataSourceMap.put(k, dataSource);
                    logger.info("数据源[" + k.name() + "]创建成功");
				} catch (Exception e) {
					 throw new IllegalArgumentException("动态数据源创建失败", e);
				}
	  		
            });
            Module defaultModule = dynamicDatasourceConfigProperties.getDefaultModule();
            if (defaultModule != null) {
                moduleDataSourceMap.put(Module.DEF, moduleDataSourceMap.get(defaultModule)); // 设置默认数据源
            } else {
                throw new IllegalArgumentException("请设置默认数据源");
            }
        } catch (Exception ex) {
            throw new IllegalArgumentException("动态数据源创建失败", ex);
        }
    }
    /**
     * Hikari数据源
     * @param dynamicDatasourceConfigProperties
     */
    protected void parseAndAssembleHikari(DynamicDatasourceConfigProperties dynamicDatasourceConfigProperties) {
        try {
            dynamicDatasourceConfigProperties.getDatasouces().forEach((k, v) -> {
                HikariConfig config = new HikariConfig();
                config.setDriverClassName(v.get(DatasourceConfigKey.dirverpath.name()).toString());
                config.setJdbcUrl(v.get(DatasourceConfigKey.url.name()).toString());
                config.setUsername(v.get(DatasourceConfigKey.username.name()).toString());
                config.setPassword(v.get(DatasourceConfigKey.password.name()).toString());
                Object maximumPoolSize = v.get(DatasourceConfigKey.maximumPoolSize.name());
                if (maximumPoolSize != null) {
                    config.setMaximumPoolSize(Integer.valueOf(maximumPoolSize.toString()));
                }
                Object minimumIdle = v.get(DatasourceConfigKey.minimumIdle.name());
                if (minimumIdle != null) {
                    config.setMinimumIdle(Integer.valueOf(minimumIdle.toString()));
                }
                HikariDataSource hikariDataSource = new HikariDataSource(config);
                Object otherProperties = v.get(DatasourceConfigKey.properties.name());
                if (otherProperties != null) {
                    Properties properties = new Properties();
                    properties.putAll((Map) otherProperties);
                    config.setDataSourceProperties(properties);
                }
                moduleDataSourceMap.put(k, hikariDataSource);
                logger.info("数据源[" + k.name() + "]创建成功");
            });
            Module defaultModule = dynamicDatasourceConfigProperties.getDefaultModule();
            if (defaultModule != null) {
                moduleDataSourceMap.put(Module.DEF, moduleDataSourceMap.get(defaultModule)); // 设置默认数据源
            } else {
                throw new IllegalArgumentException("请设置默认数据源");
            }
        } catch (Exception ex) {
            throw new IllegalArgumentException("动态数据源创建失败", ex);
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        Module module = CheckModuleHolder.get();
        if (module == null) {
            // 选择默认数据源
            module = Module.DEF;
        }
        DataSource dataSource = moduleDataSourceMap.get(module);
        if (dataSource == null) {
            throw new IllegalArgumentException("获取动态数据源失败");
        }
        return dataSource.getConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
}
