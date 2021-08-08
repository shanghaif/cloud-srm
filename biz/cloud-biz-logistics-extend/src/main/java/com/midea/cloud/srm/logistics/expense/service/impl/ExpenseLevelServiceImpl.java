package com.midea.cloud.srm.logistics.expense.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.enums.logistics.LogisticsStatus;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.logistics.expense.mapper.ExpenseLevelMapper;
import com.midea.cloud.srm.logistics.expense.service.IExpenseLevelService;
import com.midea.cloud.srm.model.logistics.expense.entity.ExpenseItem;
import com.midea.cloud.srm.model.logistics.expense.entity.ExpenseLevel;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <pre>
 *  费用级别定义表 服务实现类
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-12-02 20:54:24
 *  修改内容:
 * </pre>
 */
@Service
public class ExpenseLevelServiceImpl extends ServiceImpl<ExpenseLevelMapper, ExpenseLevel> implements IExpenseLevelService {

    /**
     * 条件查询
     *
     * @return
     */
    @Override
    public List<ExpenseLevel> listPageByParam(ExpenseLevel expenseLevel) {
        List<ExpenseLevel> expenseLevelList = this.list(Wrappers.lambdaQuery(ExpenseLevel.class)
                .like(StringUtils.isNotEmpty(expenseLevel.getSubLevelCode()), ExpenseLevel::getSubLevelCode, expenseLevel.getSubLevelCode())
                .like(StringUtils.isNotEmpty(expenseLevel.getComments()), ExpenseLevel::getComments, expenseLevel.getComments())
                .eq(StringUtils.isNotEmpty(expenseLevel.getStatus()), ExpenseLevel::getStatus, expenseLevel.getStatus())
                .orderByDesc(ExpenseLevel::getLastUpdateDate)
        );
        return expenseLevelList;
    }

    /**
     * 保存前非空校验
     *
     * @param expenseLevel
     */
    @Override
    public void checkNotEmptyBeforeSave(ExpenseLevel expenseLevel) {
        if (StringUtils.isEmpty(expenseLevel.getSubLevelCode()))
            throw new BaseException(LocaleHandler.getLocaleMsg("费用级别编码不能为空, 请选择/输入后重试."));
        // 范围止 > 范围起
        if (Objects.nonNull(expenseLevel.getRangeFrom()) && Objects.nonNull(expenseLevel.getRangeTo())
                && expenseLevel.getRangeFrom().compareTo(expenseLevel.getRangeTo()) > 0) {
            StringBuffer sb = new StringBuffer();
            sb.append("范围起[").append(expenseLevel.getRangeFrom()).append("]不能大于范围止[").append(expenseLevel.getRangeTo()).append("]");
            throw new BaseException(LocaleHandler.getLocaleMsg(sb.toString()));
        }
    }

    /**
     * 保存数据
     * @param expenseLevels
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveExpenseLevels(List<ExpenseLevel> expenseLevels) {
        // 批量保存前校验, 校验前端传入的列表是否有重复的数据
        checkRepeatExpenseLevels(expenseLevels);
        // 批量保存前校验, 校验要保存的数据和数据库中数据是否重复
        checkDbRepeatExpenseLevels(expenseLevels);

        // 区分出哪些是要新增的, 哪些是要更新的
        List<ExpenseLevel> saveList = new ArrayList<>();
        List<ExpenseLevel> updateList = new ArrayList<>();
        expenseLevels.forEach(expenseLevel -> {
            if (null == expenseLevel.getExpenseLevelId()) {
                expenseLevel.setExpenseLevelId(IdGenrator.generate()).setStatus(LogisticsStatus.DRAFT.getValue());
                saveList.add(expenseLevel);
            } else
                updateList.add(expenseLevel);
        });
        if (CollectionUtils.isNotEmpty(updateList))
            this.updateBatchById(updateList);
        if (CollectionUtils.isNotEmpty(saveList))
            this.saveBatch(saveList);
    }

    /**
     * 删除前校验
     * @param expenseLevelId
     */
    @Override
    public void checkExpenseLevelsByIdBeforeDelete(Long expenseLevelId) {
        ExpenseLevel expenseLevel = this.getById(expenseLevelId);
        if (!Objects.equals(expenseLevel.getStatus(), LogisticsStatus.DRAFT.getValue())) {
            // 获取业务模式字典条目码对应的字典条目名称
            String subLevelCode = expenseLevel.getSubLevelCode();
            StringBuffer sb = new StringBuffer();
            sb.append("您选择的费用级别编码为:[").append(subLevelCode).append("]的数据, 不是拟定状态, 不能删除.");
            throw new BaseException(sb.toString());
        }
    }

    /**
     * 批量更新费用级别状态
     * @param expenseLevelIds
     * @param status
     */
    @Override
    public void updateExpenseLevelsStatus(List<Long> expenseLevelIds, String status) {
        List<ExpenseLevel> expenseLevelList = new ArrayList<>();
        expenseLevelIds.forEach(expenseLevelId -> {
            ExpenseLevel expenseLevel = this.getById(expenseLevelId);
            expenseLevel.setStatus(status);
            expenseLevelList.add(expenseLevel);
        });
        this.updateBatchById(expenseLevelList);
    }

    /**
     * 失效前校验
     * @param expenseLevelId
     */
    @Override
    public void checkExpenseLevelsByIdsBeforeInEffective(Long expenseLevelId) {
        ExpenseLevel expenseLevel = this.getById(expenseLevelId);
        if (!Objects.equals(expenseLevel.getStatus(), LogisticsStatus.EFFECTIVE.getValue())) {
            // 获取费用级别编码
            String subLevelCode = expenseLevel.getSubLevelCode();
            StringBuffer sb = new StringBuffer();
            sb.append("您选择的费用级别编码为:[").append(subLevelCode).append("]的数据, 不是生效状态, 不能进行失效操作.");
            throw new BaseException(sb.toString());
        }
    }

    /**
     * 校验前端传入的数据是否存在重复
     *
     * @param expenseLevels
     */
    public void checkRepeatExpenseLevels(List<ExpenseLevel> expenseLevels) {
        Map<String, List<ExpenseLevel>> collect = expenseLevels.stream().collect(Collectors.groupingBy(x -> getExpenseLevelKey(x)));
        expenseLevels.forEach(expenseLevel -> {
            String key = getExpenseLevelKey(expenseLevel);
            if (collect.containsKey(key) && collect.get(key).size() > 1) {
                throw new BaseException(LocaleHandler.getLocaleMsg("需要保存的数据存在重复, 请检查后重试"));
            }
        });
    }

    /**
     * 校验要保存的数据和数据库中的数据是否存在重复
     *
     * @param expenseLevels
     */
    public void checkDbRepeatExpenseLevels(List<ExpenseLevel> expenseLevels) {
        expenseLevels = expenseLevels.stream().filter(x->null == x.getExpenseLevelId()).collect(Collectors.toList());
        for (ExpenseLevel expenseLevel : expenseLevels) {
            List<ExpenseLevel> expenseLevelList = this.list(Wrappers.lambdaQuery(ExpenseLevel.class)
                    .eq(ExpenseLevel::getSubLevelCode, expenseLevel.getSubLevelCode()));
            if (CollectionUtils.isNotEmpty(expenseLevelList)) {
                // 获取费用级别编码
                String subLevelCode = expenseLevel.getSubLevelCode();
                StringBuffer sb = new StringBuffer();
                sb.append("您选择的费用级别编码为:[").append(subLevelCode).append("]的数据, 在数据库已存在.");
                throw new BaseException(LocaleHandler.getLocaleMsg(sb.toString()));
            }
        }
    }

    /**
     * 获取 唯一标识一条费用级别数据的key
     */
    public String getExpenseLevelKey(ExpenseLevel expenseLevel) {
        StringBuffer sb = new StringBuffer();
        sb.append(expenseLevel.getSubLevelCode());
        return sb.toString();
    }


}
