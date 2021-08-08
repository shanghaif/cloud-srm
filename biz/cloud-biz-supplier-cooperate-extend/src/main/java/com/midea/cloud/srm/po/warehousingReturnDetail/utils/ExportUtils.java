package com.midea.cloud.srm.po.warehousingReturnDetail.utils;

import com.midea.cloud.component.context.i18n.LocaleHandler;

import java.util.LinkedHashMap;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author chenwj92@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人: chenwj92
 *  修改日期: 2020/8/20 20:44
 *  修改内容:
 * </pre>
 */
public class ExportUtils {
    public static final LinkedHashMap<String, String> warehousingReturnDetail_CH = new LinkedHashMap();
    public static final LinkedHashMap<String, String> warehousingReturnDetail_JP = new LinkedHashMap();
    public static final LinkedHashMap<String, String> warehousingReturnDetail_EN = new LinkedHashMap();

    public ExportUtils() {
    }

    public static LinkedHashMap<String, String> getWareHousingReturnDetailTitles() {
        String localeKey = LocaleHandler.getLocaleKey();
        byte var2 = -1;
        switch(localeKey.hashCode()) {
            case 96646644:
                if (localeKey.equals("en_US")) {
                    var2 = 0;
                }
                break;
            case 100876622:
                if (localeKey.equals("ja_JP")) {
                    var2 = 1;
                }
        }

        switch(var2) {
            case 0:
                return warehousingReturnDetail_EN;
            case 1:
                return warehousingReturnDetail_JP;
            default:
                return warehousingReturnDetail_CH;
        }
    }

    static {
        warehousingReturnDetail_CH.put("index","序号");
        warehousingReturnDetail_CH.put("type","事务处理类型");
        warehousingReturnDetail_CH.put("receiveOrderNo","接收单号");
        warehousingReturnDetail_CH.put("receiveOrderLineNo","接收行号");
        warehousingReturnDetail_CH.put("dealDate","事务处理日期");
        warehousingReturnDetail_CH.put("orgName","业务实体");
        warehousingReturnDetail_CH.put("organizationName","库存组织");
        warehousingReturnDetail_CH.put("vendorCode","供应商编码");
        warehousingReturnDetail_CH.put("vendorName","供应商名称");
        warehousingReturnDetail_CH.put("categoryName","物料小类");
        warehousingReturnDetail_CH.put("itemCode","物料编码");
        warehousingReturnDetail_CH.put("itemName","物料名称");
        warehousingReturnDetail_CH.put("unit","单位");
        warehousingReturnDetail_CH.put("receiveNum","接收数量");
        warehousingReturnDetail_CH.put("requirementHeadNum","采购申请单号");
        warehousingReturnDetail_CH.put("rowNum","申请行号");
        warehousingReturnDetail_CH.put("orderNumber","采购订单号");
        warehousingReturnDetail_CH.put("lineNum","订单行号");
        warehousingReturnDetail_CH.put("contractNo","合同编号");
        warehousingReturnDetail_CH.put("createdBy","创建人");
        warehousingReturnDetail_CH.put("creationDate","创建日期");

        warehousingReturnDetail_EN.put("index","序号");
        warehousingReturnDetail_EN.put("type","事务处理类型");
        warehousingReturnDetail_EN.put("receiveOrderNo","接收单号");
        warehousingReturnDetail_EN.put("receiveOrderLineNo","接收行号");
        warehousingReturnDetail_EN.put("dealDate","事务处理日期");
        warehousingReturnDetail_EN.put("orgName","业务实体");
        warehousingReturnDetail_EN.put("organizationName","库存组织");
        warehousingReturnDetail_EN.put("vendorCode","供应商编码");
        warehousingReturnDetail_EN.put("vendorName","供应商名称");
        warehousingReturnDetail_EN.put("categoryName","物料小类");
        warehousingReturnDetail_EN.put("itemCode","物料编码");
        warehousingReturnDetail_EN.put("itemName","物料名称");
        warehousingReturnDetail_EN.put("unit","单位");
        warehousingReturnDetail_EN.put("receiveNum","接收数量");
        warehousingReturnDetail_EN.put("requirementHeadNum","采购申请单号");
        warehousingReturnDetail_EN.put("rowNum","申请行号");
        warehousingReturnDetail_EN.put("orderNumber","采购订单号");
        warehousingReturnDetail_EN.put("lineNum","订单行号");
        warehousingReturnDetail_EN.put("contractNo","合同编号");
        warehousingReturnDetail_EN.put("createdBy","创建人");
        warehousingReturnDetail_EN.put("creationDate","创建日期");

        warehousingReturnDetail_JP.put("index","序号");
        warehousingReturnDetail_JP.put("type","事务处理类型");
        warehousingReturnDetail_JP.put("receiveOrderNo","接收单号");
        warehousingReturnDetail_JP.put("receiveOrderLineNo","接收行号");
        warehousingReturnDetail_JP.put("dealDate","事务处理日期");
        warehousingReturnDetail_JP.put("orgName","业务实体");
        warehousingReturnDetail_JP.put("organizationName","库存组织");
        warehousingReturnDetail_JP.put("vendorCode","供应商编码");
        warehousingReturnDetail_JP.put("vendorName","供应商名称");
        warehousingReturnDetail_JP.put("categoryName","物料小类");
        warehousingReturnDetail_JP.put("itemCode","物料编码");
        warehousingReturnDetail_JP.put("itemName","物料名称");
        warehousingReturnDetail_JP.put("unit","单位");
        warehousingReturnDetail_JP.put("receiveNum","接收数量");
        warehousingReturnDetail_JP.put("requirementHeadNum","采购申请单号");
        warehousingReturnDetail_JP.put("rowNum","申请行号");
        warehousingReturnDetail_JP.put("orderNumber","采购订单号");
        warehousingReturnDetail_JP.put("lineNum","订单行号");
        warehousingReturnDetail_JP.put("contractNo","合同编号");
        warehousingReturnDetail_JP.put("createdBy","创建人");
        warehousingReturnDetail_JP.put("creationDate","创建日期");



    }
}
