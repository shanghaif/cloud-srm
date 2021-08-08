package com.midea.cloud.srm.perf.itemexceptionhandle.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.perf.itemexceptionhandle.dto.QuaItemEHeaderQueryDTO;
import com.midea.cloud.srm.model.perf.itemexceptionhandle.entity.QuaItemEHeader;
import com.midea.cloud.srm.model.perf.report8d.entity.Qua8dReport;
import com.midea.cloud.srm.perf.itemexceptionhandle.service.QuaItemEHeaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
* <pre>
 *  来料异常处理单 前端控制器
 * </pre>
*
* @author chenjw90@meicloud.com
* @version 1.00.00
*
* <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Jan 27, 2021 7:33:19 PM
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/itemExceptionHandle")
public class QuaItemEHeaderController extends BaseController {

    @Autowired
    private QuaItemEHeaderService quaItemEHeaderService;

    /**
     * 点击单号获取信息
     * @param itemExceptionHeadId
     */
    @GetMapping("/get")
    public QuaItemEHeader get(Long itemExceptionHeadId) {
        Assert.notNull(itemExceptionHeadId, "异常单号不能为空");
        return quaItemEHeaderService.get(itemExceptionHeadId);
    }

    /**
     * 查询全部来料异常处理单
     * @return List<QuaItemEHeaderQueryDTO>
     */
    @PostMapping("/listAll")
    public List<QuaItemEHeaderQueryDTO> listAll() {
        return quaItemEHeaderService.listAll();
    }

    /**
     * 分页查询来料异常处理单
     * @param quaItemEHeader
     * @return PageInfo<QuaItemEHeaderQueryDTO>
     */
    @PostMapping("/listPage")
    public PageInfo<QuaItemEHeaderQueryDTO> listPage(@RequestBody QuaItemEHeader quaItemEHeader) {
        return quaItemEHeaderService.listPage(quaItemEHeader);
    }

    /**
     * 新增
     * @param quaItemEHeader
     */
    @PostMapping("/add")
    public Long addOrUpdate(@RequestBody QuaItemEHeader quaItemEHeader) {
        return quaItemEHeaderService.addOrUpdate(quaItemEHeader);
    }

    /**
     * 删除
     * @param itemExceptionHeadId
     */
    @GetMapping("/delete")
    public void delete(Long itemExceptionHeadId) {
        Assert.notNull(itemExceptionHeadId, "itemExceptionHeadId不能为空");
        quaItemEHeaderService.removeById(itemExceptionHeadId);
    }

    /**
     * 修改
     * @param quaItemEHeader
     */
    @PostMapping("/modify")
    public Long modify(@RequestBody QuaItemEHeader quaItemEHeader) {
        return quaItemEHeaderService.addOrUpdate(quaItemEHeader);
    }

    /**
     * 新增
     * @param qua8dReport
     */
    @PostMapping("/add8D")
    public Long add8D(@RequestBody Qua8dReport qua8dReport) {
        return quaItemEHeaderService.add8D(qua8dReport);
    }

}
