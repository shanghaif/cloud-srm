package com.midea.cloud.srm.sup.info.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.supplier.change.entity.CategoryRelChange;
import com.midea.cloud.srm.model.supplier.info.dto.CategoryRelQueryDTO;
import com.midea.cloud.srm.model.supplier.info.entity.CategoryRel;
import com.midea.cloud.srm.sup.change.service.ICategoryRelChangeService;
import com.midea.cloud.srm.sup.info.service.ICategoryRelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <pre>
 *  功能名称 组织与品类关系
 * </pre>
 *
 * @author chensl26@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/9 16:44
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/info/categoryRel")
public class CategoryRelController extends BaseController {

    @Autowired
    ICategoryRelService iCategoryRelService;

    @Autowired
    ICategoryRelChangeService iCategoryRelChangeService;

    /**
     * 分页条件查询
     * @param categoryRelQueryDTO
     * @return
     */
    @PostMapping("/listPageCategoryRelByParam")
    public PageInfo<CategoryRel> listPageByParam(@RequestBody CategoryRelQueryDTO categoryRelQueryDTO) {
        return iCategoryRelService.listPageByParam(categoryRelQueryDTO);
    }

    /**
     * 分页查询状态变更记录
     * @param categoryRelQueryDTO
     * @return
     */
    @PostMapping("/listPageChangeByParam")
    public PageInfo<CategoryRelChange> listPageChangeByParam(@RequestBody CategoryRelQueryDTO categoryRelQueryDTO) {
        return iCategoryRelChangeService.listPageChangeByParam(categoryRelQueryDTO);
    }

}
