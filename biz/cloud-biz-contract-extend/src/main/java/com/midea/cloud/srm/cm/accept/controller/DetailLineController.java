package com.midea.cloud.srm.cm.accept.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.cm.accept.service.IDetailLineService;
import com.midea.cloud.srm.feign.supcooperate.SupcooperateClient;
import com.midea.cloud.srm.model.cm.accept.dto.DetailLineDTO;
import com.midea.cloud.srm.model.cm.accept.entity.DetailLine;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.OrderDetailDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.OrderRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <pre>
 *  合同验收行 前端控制器
 * </pre>
 *
 * @author chensl26@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-11 19:45:28
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/accept/detailLine")
public class DetailLineController extends BaseController {

    @Autowired
    private IDetailLineService iDetailLineService;
    @Autowired
    private SupcooperateClient supcooperateClient;

    /**
     * 获取
     *
     * @param id
     */
    @GetMapping("/get")
    public DetailLine get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iDetailLineService.getById(id);
    }

    /**
     * 新增
     *
     * @param detailLine
     */
    @PostMapping("/add")
    public void add(@RequestBody DetailLine detailLine) {
        Long id = IdGenrator.generate();
        detailLine.setAcceptDetailLineId(id);
        iDetailLineService.save(detailLine);
    }

    /**
     * 删除
     *
     * @param id
     */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iDetailLineService.removeById(id);
    }

    /**
     * 修改
     *
     * @param detailLine
     */
    @PostMapping("/modify")
    public void modify(@RequestBody DetailLine detailLine) {
        iDetailLineService.updateById(detailLine);
    }

    /**
     * 分页查询
     *
     * @param detailLine
     * @return
     */
    @PostMapping("/listPageByParm")
    public PageInfo<DetailLine> listPage(@RequestBody DetailLineDTO detailLine) {
        PageUtil.startPage(detailLine.getPageNum(), detailLine.getPageSize());
        QueryWrapper<DetailLine> wrapper = new QueryWrapper<DetailLine>(detailLine);
        return new PageInfo<DetailLine>(iDetailLineService.list(wrapper));
    }

    /**
     * 查询所有
     *
     * @return
     */
    @PostMapping("/listAll")
    public List<DetailLine> listAll() {
        return iDetailLineService.list();
    }

}
