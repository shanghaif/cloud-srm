package com.midea.cloud.srm.model.base.monitor.enums;

import lombok.Getter;

/**
 * 操作符号
 * @author euler
 */
public enum OperationSymbol {

    /**
     * 等于
     */
    EQUAL_TO(1, "=", "Equal", OperationAmount.SINGLE),

    /**
     * 不等于
     */
    NOT_EQUAL_TO(2, "<>", "NotEqual", OperationAmount.SINGLE),

    /**
     * 大于
     */
    GREATER_THAN(3, ">", "Greater", OperationAmount.SINGLE),

    /**
     * 大于等于
     */
    GREATER_THAN_OR_EQUAL(4, ">=", "GreaterEqual", OperationAmount.SINGLE),

    /**
     * 小于
     */
    LESS_THAN(5, "<", "Less", OperationAmount.SINGLE),

    /**
     * 小于等于
     */
    LESS_THAN_OR_EQUAL(6, "<=", "LessEqual", OperationAmount.SINGLE),

    /**
     * 模糊匹配
     */
    LIKE(7, "like", "Like", OperationAmount.SINGLE),

    /**
     * 模糊不匹配
     */
    NOT_LIKE(8, "not like", "NotLike", OperationAmount.SINGLE),

    /**
     * 包含
     */
    IN(9, "in", "Set", OperationAmount.LIST),

    /**
     * 不包含
     */
    NOT_IN(10, "not in", "NotSet", OperationAmount.LIST),

    /**
     * 区间
     */
    BETWEEN(11, "between", "Between", OperationAmount.TWO),

    /**
     * 不在区间
     */
    NOT_BETWEEN(12, "not between", "NotBetween", OperationAmount.TWO);

    /**
     * 符号
     */
    @Getter
    String symbol;

    @Getter
    String fieldSuffix;

    @Getter
    OperationAmount amount;

    @Getter
    final Short value;

    OperationSymbol(int value, String symbol, String fieldSuffix, OperationAmount amount) {
        this.value = (short) value;
        this.symbol = symbol;
        this.fieldSuffix = fieldSuffix;
        this.amount = amount;
    }

}
