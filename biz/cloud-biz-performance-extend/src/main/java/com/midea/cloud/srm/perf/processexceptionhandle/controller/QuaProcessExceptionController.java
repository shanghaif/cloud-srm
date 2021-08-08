package com.midea.cloud.srm.perf.processexceptionhandle.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.perf.processexceptionhandle.dto.QuaProcessExceptionDTO;
import com.midea.cloud.srm.model.perf.processexceptionhandle.entity.QuaProcessException;
import com.midea.cloud.srm.model.perf.report8d.entity.Qua8dReport;
import com.midea.cloud.srm.perf.processexceptionhandle.service.QuaProcessExceptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;


/**
* <pre>
 *  制程异常处理单 前端控制器
 * </pre>
*
* @author chenjw90@meicloud.com
* @version 1.00.00
*
* <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Jan 31, 2021 5:45:00 PM
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/quaProcessException")
public class QuaProcessExceptionController extends BaseController {

    @Autowired
    private QuaProcessExceptionService quaProcessExceptionService;

    /**
    * 获取
     * @param billCode
     */
    @GetMapping("/getById")
    public QuaProcessException getById(Long billCode) {
        Assert.notNull(billCode, "billCode不能为空");
        return quaProcessExceptionService.getById(billCode);
    }

    /**
    * 新增
    * @param quaProcessException
    */
    @PostMapping("/add")
    public void add(@RequestBody QuaProcessException quaProcessException) {
        Long id = IdGenrator.generate();
        quaProcessException.setBillCode(id);
        quaProcessExceptionService.save(quaProcessException);
    }
    
    /**
    * 删除
    * @param billCode
    */
    @GetMapping("/delete")
    public void delete(Long billCode) {
        Assert.notNull(billCode, "billCode不能为空");
        quaProcessExceptionService.removeById(billCode);
    }

    /**
    * 修改
    * @param quaProcessException
    */
    @PostMapping("/modify")
    public void modify(@RequestBody QuaProcessException quaProcessException) {
        quaProcessExceptionService.updateById(quaProcessException);
    }

    /**
    * 分页查询+条件查询
    * @param quaProcessException
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<QuaProcessExceptionDTO> listPage(@RequestBody QuaProcessException quaProcessException) {
        return quaProcessExceptionService.listPage(quaProcessException);
    }

    /**
     * 新增
     * @param qua8dReport
     */
    @PostMapping("/add8D")
    public Long add8D(@RequestBody Qua8dReport qua8dReport) {
        return quaProcessExceptionService.add8D(qua8dReport);
    }

}
