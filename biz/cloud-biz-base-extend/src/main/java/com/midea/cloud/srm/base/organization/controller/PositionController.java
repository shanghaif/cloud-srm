package com.midea.cloud.srm.base.organization.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.common.utils.StringUtil;
import com.midea.cloud.srm.base.organization.service.IPositionService;
import com.midea.cloud.srm.model.base.organization.entity.Position;
import com.midea.cloud.srm.model.common.BaseController;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
*  <pre>
 *  职位表（隆基职位同步） 前端控制器
 * </pre>
*
* @author xiexh12@midea.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-17 16:05:50
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/organization/position")
public class PositionController extends BaseController {

    @Autowired
    private IPositionService iPositionService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public Position get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iPositionService.getById(id);
    }

    /**
    * 新增
    * @param position
    */
    @PostMapping("/add")
    public void add(@RequestBody Position position) {
        Long id = IdGenrator.generate();
        position.setId(id);
        iPositionService.save(position);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iPositionService.removeById(id);
    }

    /**
    * 修改
    * @param position
    */
    @PostMapping("/modify")
    public void modify(@RequestBody Position position) {
        iPositionService.updateById(position);
    }

    /**
    * 分页查询
    * @param position
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<Position> listPage(@RequestBody Position position) {
        PageUtil.startPage(position.getPageNum(), position.getPageSize());
        QueryWrapper<Position> wrapper = new QueryWrapper<Position>(position);
        return new PageInfo<Position>(iPositionService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<Position> listAll() { 
        return iPositionService.list();
    }

    /**
     * 根据岗位名称查找岗位信息
     * @param descrList
     * @return
     */
    @PostMapping("/queryPositionBydescrshortList")
    public Map<String,Position> queryPositionBydescrshortList(@RequestBody List<String> descrList){
        Map<String, Position> positionMap = new HashMap<>();
        if(CollectionUtils.isNotEmpty(descrList)){
            List<Position> positionList = iPositionService.list(new QueryWrapper<Position>().in("DESCR", descrList));
            if(CollectionUtils.isNotEmpty(positionList)){
                positionMap = positionList.stream().filter(position -> StringUtil.notEmpty(position.getDescr())).
                        collect(Collectors.toMap(Position::getDescr, v -> v, (k1, k2) -> k1));
            }
        }
        return positionMap;
    }

 
}
