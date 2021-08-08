package com.midea.cloud.srm.logistics;

import com.midea.cloud.log.bind.EnableOpLog;
import com.midea.cloud.quartz.bind.EnableJobFeignSupport;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.io.Resource;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * <pre>
 *  物流寻源模块
 * </pre>
 *
 * @author huanghb14@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/11/17 14:04
 *  修改内容:
 * </pre>
 */
@EnableFeignClients("com.midea.cloud.srm.feign")
@EnableEurekaClient
@SpringBootApplication
@ComponentScan(
        value = {
                "com.midea.cloud.component",
                "com.midea.cloud.common",
                "com.midea.cloud.srm.logistics",
                "com.midea.cloud.srm.bid",
                "com.midea.cloud.srm.po",
                "com.midea.cloud.srm.pr",
                "com.midea.cloud.srm.ps",
				"com.midea.cloud.log"
        },
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com\\.midea\\.cloud\\.srm\\.(bid|inq|pm)\\.config.*"),
                @ComponentScan.Filter(type = FilterType.CUSTOM, classes = {LogisticsApplication.ExcludeFilter.class})
        })
@MapperScan(basePackages = {
        "com.midea.cloud.srm.logistics.**.mapper", "com.midea.cloud.srm.bid.**.mapper",
        "com.midea.cloud.srm.inq.**.mapper", "com.midea.cloud.srm.pm.**.mapper",
        "com.midea.cloud.srm.po.**.mapper", "com.midea.cloud.srm.pr.**.mapper", "com.midea.cloud.srm.ps.**.mapper"})
@EnableOpLog
@EnableJobFeignSupport
@Slf4j
public class LogisticsApplication {

    public static void main(String[] args) {
        args = Arrays.copyOf(args, args.length + 1);
        args[args.length - 1] = "--server.port=8842";
        SpringApplication.run(LogisticsApplication.class, args);
    }

    static class ExcludeFilter implements TypeFilter {

        private static final Set<String> excludeClassPath = new HashSet<>();

        static {
            excludeClassPath.add("com.midea.cloud.srm.pm.flow.controller.CbpmPmRequireMentFeignController");
            excludeClassPath.add("com.midea.cloud.srm.inq.flow.controller.CbpmInqFeignController");
            excludeClassPath.add("com.midea.cloud.srm.bid.flow.controller.CbpmBidFeignController");
        }

        @Override
        public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory)
                throws IOException {
            AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
            ClassMetadata classMetadata = metadataReader.getClassMetadata();
            Resource resource = metadataReader.getResource();
            String className = classMetadata.getClassName();
            return excludeClassPath.contains(className);
        }
    }

}
