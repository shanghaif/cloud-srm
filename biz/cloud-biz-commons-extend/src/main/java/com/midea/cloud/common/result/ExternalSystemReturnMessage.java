package com.midea.cloud.common.result;

/**
 * <pre>
 *  外部系统的返回错误提示
 * </pre>
 *
 * @author chenjj120@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/12/17 上午 10:39
 *  修改内容:
 * </pre>
 */
public class ExternalSystemReturnMessage {

    /**
     * 同步订单到ERP中，erp提示“XXXX已存在此数据”，就应该让流程往下走；避免重复手动给用户改数据
     */
    public static final String ERP_HAS_DATA = "Ifacecode(PUR_PO_EXP)已经存在此数据.";
    /**
     * "PoNumberPO20201214000050与PoLineNumber5组合已取消."
     * 现象：订单变更推送erp 系统提示不友好，其实已经变更推送过的，srm只需要改状态即可
     */
    public static final String EPR_CHANGE_PUSH_ALREADY  = "组合已取消";
    /**
     * "PoNumberPO20201214000050与PoLineNumber5组合不存在."
     * 现象：订单变更推送erp erp中不存在数据
     */
    public static final String EPR_CHANGE_NON_DATA  = "组合不存在";

}
