package com.midea.cloud.srm.logistics.expense.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.logistics.LogisticsStatus;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.logistics.expense.service.IExpenseLevelService;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.logistics.expense.entity.ExpenseLevel;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
*  <pre>
 *  费用级别定义表 前端控制器
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-12-02 20:54:24
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/logistics/expense-level")
public class ExpenseLevelController extends BaseController {

    @Autowired
    private IExpenseLevelService iExpenseLevelService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public ExpenseLevel get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iExpenseLevelService.getById(id);
    }

    /**
    * 新增
    * @param expenseLevel
    */
    @PostMapping("/add")
    public void add(@RequestBody ExpenseLevel expenseLevel) {
        Long id = IdGenrator.generate();
        expenseLevel.setExpenseLevelId(id);
        iExpenseLevelService.save(expenseLevel);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iExpenseLevelService.removeById(id);
    }

    /**
    * 修改
    * @param expenseLevel
    */
    @PostMapping("/modify")
    public void modify(@RequestBody ExpenseLevel expenseLevel) {
        iExpenseLevelService.updateById(expenseLevel);
    }

    /**
    * 分页条件查询
    * @param expenseLevel
    * @return
    */
    @PostMapping("/listPageByParam")
    public PageInfo<ExpenseLevel> listPageByParam(@RequestBody ExpenseLevel expenseLevel) {
        PageUtil.startPage(expenseLevel.getPageNum(), expenseLevel.getPageSize());
        return new PageInfo<ExpenseLevel>(iExpenseLevelService.listPageByParam(expenseLevel));
    }

    /**
     * 保存费用级别定义
     * @param expenseLevels
     */
    @PostMapping("/saveExpenseLevels")
    public void saveExpenseLevel(@RequestBody List<ExpenseLevel> expenseLevels) {
        Assert.isTrue(CollectionUtils.isNotEmpty(expenseLevels), "要保存的费用项级别为空.");
        expenseLevels.forEach(expenseLevel -> {
            iExpenseLevelService.checkNotEmptyBeforeSave(expenseLevel);
        });
        iExpenseLevelService.saveExpenseLevels(expenseLevels);
    }

    /**
     * 通过ids删除
     *
     * @param expenseLevelIds
     */
    @PostMapping("/deleteByIds")
    public void deleteByIds(@RequestBody List<Long> expenseLevelIds) {
        checkExpenseLevelIds(expenseLevelIds);
        checkExpenseLevelsByIdsBeforeDelete(expenseLevelIds);
        iExpenseLevelService.removeByIds(expenseLevelIds);
    }

    /**
     * 费用级别生效
     *
     * @param expenseLevelIds
     */
    @PostMapping("/effectiveExpenseLevels")
    public void effectiveExpenseLevels(@RequestBody List<Long> expenseLevelIds) {
        checkExpenseLevelIds(expenseLevelIds);
        // checkExpenseLevelsByIdsBeforeEffective(expenseLevelIds);
        iExpenseLevelService.updateExpenseLevelsStatus(expenseLevelIds, LogisticsStatus.EFFECTIVE.getValue());
    }

    /**
     * 费用级别失效
     *
     * @param expenseLevelIds
     */
    @PostMapping("/inEffectiveExpenseLevels")
    public void inEffectiveExpenseLevels(@RequestBody List<Long> expenseLevelIds) {
        checkExpenseLevelIds(expenseLevelIds);
        checkExpenseLevelsByIdsBeforeInEffective(expenseLevelIds);
        iExpenseLevelService.updateExpenseLevelsStatus(expenseLevelIds, LogisticsStatus.INEFFECTIVE.getValue());
    }

    /**
     * 费用项级别ids判空
     * 以及根据费用级别id是否在数据库能找到对应的费用级别
     *
     * @param expenseLevels
     */
    public void checkExpenseLevelIds(List<Long> expenseLevels) {
        Assert.isTrue(CollectionUtils.isNotEmpty(expenseLevels), "要批量操作的的费用级别为空, 请选择要操作的费用级别.");
        for (Long expenseLevelId : expenseLevels) {
            Optional.ofNullable(expenseLevelId).orElseThrow(()->new BaseException(LocaleHandler.getLocaleMsg("批量操作的数据存在费用级别id为空的数据.")));
            Optional.ofNullable(iExpenseLevelService.getById(expenseLevelId))
                    .orElseThrow(()->new BaseException(LocaleHandler.getLocaleMsg("无效的费用级别id: "+expenseLevelId+", 找不到相应的费用级别.")));
        }
    }

    /**
     * 批量删除前校验
     *
     * @param expenseLevelIds
     */
    public void checkExpenseLevelsByIdsBeforeDelete(List<Long> expenseLevelIds) {
        for (Long expenseLevelId : expenseLevelIds) {
            iExpenseLevelService.checkExpenseLevelsByIdBeforeDelete(expenseLevelId);
        }
    }

    /**
     * 失效前校验
     *
     * @param expenseLevelIds
     */
    public void checkExpenseLevelsByIdsBeforeInEffective(List<Long> expenseLevelIds) {
        for (Long expenseLevelId : expenseLevelIds) {
            iExpenseLevelService.checkExpenseLevelsByIdsBeforeInEffective(expenseLevelId);
        }
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<ExpenseLevel> listAll() { 
        return iExpenseLevelService.list();
    }
 
}
