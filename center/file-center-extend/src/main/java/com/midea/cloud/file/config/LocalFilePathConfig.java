package com.midea.cloud.file.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 使系统加载jar包外的文件
 *
 * @author artifact
 */
@Configuration
public class LocalFilePathConfig {

    /**
     * 上传文件存储在本地的根路径
     */
    @Value("${file.local.path}")
    private String localFilePath;

    /**
     * url前缀
     */
    @Value("${file.local.prefix}")
    public String localFilePrefix;

}
