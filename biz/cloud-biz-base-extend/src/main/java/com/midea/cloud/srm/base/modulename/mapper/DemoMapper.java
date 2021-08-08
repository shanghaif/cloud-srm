package com.midea.cloud.srm.base.modulename.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.base.modulename.entity.Demo;

import java.util.List;

/**
 * <p>
 * 演示 Mapper 接口
 * </p>
 *
 * @author huanghb14@example.com
 * @since 2020-02-12
 */
public interface DemoMapper extends BaseMapper<Demo> {

    /**
     * 写一条特殊语句
     */
    int countRecord();

    List<Demo> selectAll();

    void insertByDemo(Demo demo);

}
