package com.midea.cloud.srm.cm.element.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.cm.element.service.IElemMaintainsService;
import com.midea.cloud.srm.cm.element.service.ITypeRangesService;
import com.midea.cloud.srm.model.cm.element.entity.ElemMaintain;
import com.midea.cloud.srm.model.cm.element.entity.TypeRange;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.perf.itemexceptionhandle.dto.QuaItemEHeaderQueryDTO;
import com.midea.cloud.srm.model.perf.itemexceptionhandle.entity.QuaItemEHeader;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
*  <pre>
 *  合同要素维护表 前端控制器
 * </pre>
*
* @author wangpr@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-12 16:29:03
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/type-range")
public class TypeRangeController extends BaseController {

    @Autowired
    private ITypeRangesService iTypeRangesService;
    @Autowired
    private IElemMaintainsService iElemMaintainsService;

    /**
    * 新增
    * @param typeRange
    */
    @PostMapping("/add")
    public void add(@RequestBody TypeRange typeRange) {
        Long id = IdGenrator.generate();
        typeRange.setTypeRangeId(id);
        iTypeRangesService.save(typeRange);
    }
    
    /**
    * 删除
    * @param typeRangeId
    */
    @GetMapping("/delete")
    public void delete(Long typeRangeId) {
        Assert.notNull(typeRangeId, "typeRangeId不能为空");
        iTypeRangesService.removeById(typeRangeId);
    }

    /**
    * 修改
    * @param typeRange
    */
    @PostMapping("/modify")
    public void modify(@RequestBody TypeRange typeRange) {
        Assert.notNull(typeRange.getTypeRangeId(), "typeRangeId不能为空");
        iTypeRangesService.updateById(typeRange);
    }

    /**
     *批量修改
     * @param typeRanges
     */
     @PostMapping("/batchSaveOrUpdate")
     public void batchSaveOrUpdate(@RequestBody List<TypeRange> typeRanges) {
         Assert.notNull(typeRanges, "保存数据不能为空");
         if (null != typeRanges && typeRanges.size() > 0) {
        	 for (TypeRange typeRange : typeRanges) {
        		 if (null == typeRange.getTypeRangeId()) {
        			 	Long id = IdGenrator.generate();
        		        typeRange.setTypeRangeId(id);

                     String typeRangeElemName=typeRange.getElemName();
                     ElemMaintain elemMaintain = null;
                     QueryWrapper<ElemMaintain> qw = new QueryWrapper<>();
                     qw.ge("ELEM_NAME",typeRangeElemName);
                     List<ElemMaintain> elemMaintainList = iElemMaintainsService.list(qw);
                     elemMaintain = elemMaintainList.get(elemMaintainList.size()-1);
                     typeRange.setElemCode(elemMaintain.getElemCode());
                     typeRange.setElemMaintainId(elemMaintain.getElemMaintainId());

                     iTypeRangesService.save(typeRange);
        		 } else {
        			 iTypeRangesService.updateById(typeRange);
        		 }
        	 }
         }
     }
    
    
    /**
    * 分页查询
    * @param typeRange
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<TypeRange> listPage(@RequestBody TypeRange typeRange) {
        return iTypeRangesService.listPage(typeRange);
    }

    /**
    * 查询指定合同类型有效的合同
    * @return
    */
    @GetMapping("/queryByValid")
    public List<ElemMaintain> queryByValid(String contractType) {
        return iTypeRangesService.queryByValid(contractType);
    }
 
}
