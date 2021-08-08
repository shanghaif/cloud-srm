package com.midea.cloud.srm.rbac.function.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.common.utils.StringUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.rbac.function.entity.Function;
import com.midea.cloud.srm.rbac.function.service.IFunctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <pre>
 *  功能维护 前端控制器
 * </pre>
 *
 * @author chensl26@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-12 16:54:24
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/function")
public class FunctionController extends BaseController {

    @Autowired
    private IFunctionService iFunctionService;

    /**
     * 获取
     *
     * @param id
     */
    @GetMapping("/get")
    public Function get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iFunctionService.getById(id);
    }

    /**
     * 新增
     *
     * @param function
     */
    @PostMapping("/add")
    public void add(@RequestBody Function function) {
        Long id = IdGenrator.generate();
        function.setFunctionId(id);
        validFunction(function);
        Function checkFunction = iFunctionService.getOne(new QueryWrapper<Function>(new Function().setFunctionCode(function.getFunctionCode())));
        if (checkFunction != null) {
            throw new BaseException("功能编码不能重复");
        }
        iFunctionService.save(function);
    }

    /**
     * 功能校验
     *
     * @param function
     */
    public void validFunction(Function function) {
        if (function.getFunctionCode() == null || function.getFunctionName() == null || function.getFunctionAddress() == null || function.getStartDate() == null) {
            throw new BaseException(ResultCode.MISSING_SERVLET_REQUEST_PART);
        }
    }

    /**
     * 删除
     *
     * @param id
     */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iFunctionService.removeById(id);
    }

    /**
     * 修改
     *
     * @param function
     */
    @PostMapping("/modify")
    public void modify(@RequestBody Function function) {
        validFunction(function);
        Function checkFunction = iFunctionService.getOne(new QueryWrapper<Function>(new Function().setFunctionCode(function.getFunctionCode())));
        if (checkFunction != null && checkFunction.getFunctionId().compareTo(function.getFunctionId()) != 0) {
            throw new BaseException("功能编码不能重复");
        }
        iFunctionService.updateById(function);
    }

    /**
     * 分页查询
     *
     * @param function
     * @return
     */
    @PostMapping("/listPage")
    public PageInfo<Function> listPage(@RequestBody Function function) {
        PageUtil.startPage(function.getPageNum(), function.getPageSize());
        String functionName = function.getFunctionName();
        function.setFunctionName(null);
        QueryWrapper<Function> wrapper = new QueryWrapper<Function>(function);
        if (!StringUtil.isEmpty(functionName)) {
        	wrapper.like("FUNCTION_NAME", functionName);
        }
        return new PageInfo<Function>(iFunctionService.list(wrapper));
    }

    /**
     * 查询所有
     *
     * @return
     */
    @PostMapping("/listAll")
    public List<Function> listAll() {
        return iFunctionService.list();
    }

}
