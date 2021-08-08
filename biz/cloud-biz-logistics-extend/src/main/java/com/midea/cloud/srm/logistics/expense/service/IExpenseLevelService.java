package com.midea.cloud.srm.logistics.expense.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.logistics.expense.entity.ExpenseLevel;

import java.util.List;

/**
*  <pre>
 *  费用级别定义表 服务类
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
public interface IExpenseLevelService extends IService<ExpenseLevel> {

    /**
     * 条件查询
     * @return
     */
    List<ExpenseLevel> listPageByParam(ExpenseLevel expenseLevel);

    /**
     * 保存前非空校验
     * @param expenseLevel
     */
    void checkNotEmptyBeforeSave(ExpenseLevel expenseLevel);

    /**
     * 保存数据
     * @param expenseLevels
     */
    void saveExpenseLevels(List<ExpenseLevel> expenseLevels);

    /**
     * 删除前校验
     */
    void checkExpenseLevelsByIdBeforeDelete(Long expenseLevelId);

    /**
     * 批量更新费用级别状态
     * @param expenseLevelIds
     * @param status
     */
    void updateExpenseLevelsStatus(List<Long> expenseLevelIds, String status);

    /**
     * 失效前校验
     */
    void checkExpenseLevelsByIdsBeforeInEffective(Long expenseLevelId);

}
