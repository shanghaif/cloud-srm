package com.midea.cloud.srm.base.quicksearch.controller;


import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.base.quicksearch.service.IQuicksearchConfigService;
import com.midea.cloud.srm.model.base.quicksearch.entity.QuicksearchConfig;
import com.midea.cloud.srm.model.base.quicksearch.param.JsonParam;
import com.midea.cloud.srm.model.base.quicksearch.vo.QuicksearchConfigVO;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * <pre>
 *  快速查询组件 前端控制器
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
@RestController
@RequestMapping(value="/quicksearch/quicksearchConfig")
public class QuicksearchConfigController extends BaseController {

    @Autowired
    private IQuicksearchConfigService quicksearchConfigService;

    /**
     * 查询快速查询配置列表
     * @param param
     * @return
     */
    @PostMapping("/listPage")
    public PageInfo<QuicksearchConfig> listPage(@RequestBody QuicksearchConfig param){
       return quicksearchConfigService.listPage(param);
    }

    /**
     * 快速查询配置页查询总数
     * @param param
     * @return
     */
    @PostMapping("/countConfig")
    public Integer countConfig(@RequestBody QuicksearchConfig param){
        return quicksearchConfigService.countConfig(param);
    }

    /**
     * 查询详情
     * @param param
     * @return
     */
    @PostMapping("/getDetail")
    public QuicksearchConfigVO getDetail(@RequestBody QuicksearchConfig param){
        return quicksearchConfigService.getDetail(param);
    }

    /**
     * 查询表信息
     * @param param
     * @return
     */
    @PostMapping("/listSchemaTables")
    public List<Map<String,String>> listSchemaTables(@RequestBody QuicksearchConfig param){
        return quicksearchConfigService.listSchemaTables(param);
    }

    /**
     * 查询表信息
     * @param param
     * @return
     */
    @PostMapping("/listTables")
    public List<Map<String,String>> listTables(@RequestBody QuicksearchConfig param){
        return quicksearchConfigService.listTables(param);
    }

    /**
     * 查询快速查询配置
     * @param param
     * @return
     */
    @PostMapping("/getConfig")
    public Map<String,Object> getConfig(@RequestBody QuicksearchConfig param){
        return  quicksearchConfigService.getConfig(param);
    }

    /**
     * 根据数据库配置查询信息
     * @param param
     * @return
     */
    @PostMapping("/listByFormCondition")
    public Map<String, Object> listByFormCondition(@RequestBody JsonParam param){
        if(param.getPageNum() == null){
            param.setPageNum(1);
        }
        if(param.getPageSize() == null){
            param.setPageSize(10);
        }
        return quicksearchConfigService.listByFormCondition(param);
    }

    /**
     * 根据配置模糊查询输入框信息
     * @param param
     * @return
     */
    @PostMapping("/listInputInfo")
    public List<Map<String,Object>> listInputInfo(@RequestBody JsonParam param){
        return quicksearchConfigService.listInputInfo(param);
    }

    /**
     * 保存配置信息
     * @param param
     * @return
     */
    @PostMapping("/save")
    public void save(@RequestBody QuicksearchConfigVO param){
        quicksearchConfigService.save(param);
    }

    /**
     * 删除配置信息
     * @param param
     * @return
     */
    @PostMapping("/removeConfig")
    public Integer removeConfig(@RequestBody JsonParam param){
       return quicksearchConfigService.removeConfig(param);
    }

    /**
     * 检索结果弹窗查询总数
     * @param param
     * @return
     */
    @PostMapping("/countPopupWindowInfo")
    public Integer countPopupWindowInfo(@RequestBody JsonParam param){
        return quicksearchConfigService.countPopupWindowInfo(param);
    }

}