package com.midea.cloud.srm.base.quicksearch.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.base.quicksearch.entity.QuicksearchConfig;
import com.midea.cloud.srm.model.base.quicksearch.param.JsonParam;
import com.midea.cloud.srm.model.base.quicksearch.vo.QuicksearchConfigVO;

import java.util.List;
import java.util.Map;


/**
 * <pre>
 *  快速查询组件 接口
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
public interface IQuicksearchConfigService extends IService<QuicksearchConfig> {

    /**
     * 分页查询 快速查询配置列表
     *
     * @param param
     * @return
     */
    PageInfo<QuicksearchConfig> listPage(QuicksearchConfig param);

    /**
     * 获取 快速查询配置页总记录数
     *
     * @param param
     * @return
     */
    Integer countConfig(QuicksearchConfig param);

    /**
     * 获取快速查询配置详情
     *
     * @param param
     * @return
     */
    QuicksearchConfigVO getDetail(QuicksearchConfig param);

    /**
     * 查询表信息
     *
     * @param param
     * @return
     */
    List<Map<String, String>> listTables(QuicksearchConfig param);

    /**
     * 查询表信息
     *
     * @param param
     * @return
     */
    List<Map<String, String>> listSchemaTables(QuicksearchConfig param);

    /**
     * 查询快速查询配置
     *
     * @param param
     * @return
     */
    Map<String, Object> getConfig(QuicksearchConfig param);

    /**
     * 根据数据库配置查询信息
     *
     * @param param
     * @return
     */
    Map<String, Object> listByFormCondition(JsonParam param);

    /**
     * 根据配置模糊查询输入框信息
     *
     * @param param
     * @return
     */
    List<Map<String, Object>> listInputInfo(JsonParam param);

    /**
     * 删除配置信息
     *
     * @param param
     * @return
     */
    Integer removeConfig(JsonParam param);

    /**
     * 检索结果弹窗查询总数
     *
     * @param param
     * @return
     */
    Integer countPopupWindowInfo(JsonParam param);

    /**
     * 保存配置信息
     *
     * @param param
     * @return
     */
    void save(QuicksearchConfigVO param);
}
