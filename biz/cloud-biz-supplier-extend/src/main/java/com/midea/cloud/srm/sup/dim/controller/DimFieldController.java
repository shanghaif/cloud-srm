package com.midea.cloud.srm.sup.dim.controller;

import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.supplier.dim.dto.DimFieldDTO;
import com.midea.cloud.srm.model.supplier.dim.entity.DimField;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.sup.dim.service.IDimFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import java.util.List;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

/**
*  <pre>
 *  维度字段表 前端控制器
 * </pre>
*
* @author zhuwl7@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-02-29 15:28:51
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/dim/dimField")
public class DimFieldController extends BaseController {

    @Autowired
    private IDimFieldService iDimFieldService;

    /**
    * 获取
    * @param fieldId
    */
    @GetMapping("/get")
    public DimFieldDTO get(Long fieldId) {
        Assert.notNull(fieldId, "fieldId不能为空");
        return iDimFieldService.get(fieldId);
    }

    /**
    * 新增
    * @param dimField
    */
    @PostMapping("/add")
    public void add(DimField dimField) {
        Long id = IdGenrator.generate();
        dimField.setFieldId(id);
        iDimFieldService.save(dimField);
    }

    /**
    * 删除
    * @param id
    */
    @PostMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iDimFieldService.removeById(id);
    }

    /**
    * 修改
    * @param dimField
    */
    @PostMapping("/modify")
    public void modify(DimField dimField) {
        iDimFieldService.updateById(dimField);
    }

    /**
    * 分页查询
    * @param dimField
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<DimField> listPage(DimField dimField) {
        PageUtil.startPage(dimField.getPageNum(), dimField.getPageSize());
        QueryWrapper<DimField> wrapper = new QueryWrapper<DimField>(dimField);
        return new PageInfo<DimField>(iDimFieldService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<DimField> listAll() {
        return iDimFieldService.list();
    }


    /**
     * 条件分页查询
     * @param requestDto
     * @return
     */
    @PostMapping("/listPageByParam")
    public PageInfo<DimFieldDTO> listPage(@RequestBody DimFieldDTO requestDto) {
        return iDimFieldService.listPageByParam(requestDto);
    }

    /**
     * 保存或更新
     * @param dimField
     * @return
     */
    @PostMapping("/saveOrUpdateField")
    public void saveOrUpdateField(@RequestBody DimField dimField) {
        iDimFieldService.saveOrUpdateField(dimField);
    }

}
