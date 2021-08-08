package com.midea.cloud.srm.supcooperate.invoice.utils;

import com.midea.cloud.component.context.i18n.LocaleHandler;

import java.util.LinkedHashMap;

/**
 * <pre>
 *  代理网上开票表导出工具
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/11/15 14:05
 *  修改内容:
 * </pre>
 */
public class OnlineInvoiceExportUtils {

    /**
     * 中文导出标题
     */
    public static final LinkedHashMap<String, String> OnlineInvoiceTitles_CH;
    /**
     * 日文导出标题
     */
    public static final LinkedHashMap<String, String> OnlineInvoiceTitles_JP;
    /**
     * 英文
     */
    public static final LinkedHashMap<String, String> OnlineInvoiceTitles_EN;



    static {

        OnlineInvoiceTitles_CH = new LinkedHashMap<>();
        OnlineInvoiceTitles_JP = new LinkedHashMap<>();
        OnlineInvoiceTitles_EN = new LinkedHashMap<>();

        OnlineInvoiceTitles_CH.put("onlineInvoiceNum", "网上发票号");
        OnlineInvoiceTitles_CH.put("accountPayableDealine", "应付账款到期日");
        OnlineInvoiceTitles_CH.put("invoiceStatus", "发票状态");
        OnlineInvoiceTitles_CH.put("importStatus", "导入状态");
        OnlineInvoiceTitles_CH.put("orgName", "业务实体");
        OnlineInvoiceTitles_CH.put("vendorCode", "供应商编号");
        OnlineInvoiceTitles_CH.put("vendorName", "供应商名称");
        OnlineInvoiceTitles_CH.put("costTypeName", "供应商地点");
        OnlineInvoiceTitles_CH.put("taxInvoiceNum", "税务发票号");
        OnlineInvoiceTitles_CH.put("fsscNo", "费控发票号");
        OnlineInvoiceTitles_CH.put("businessType", "业务类型");
        OnlineInvoiceTitles_CH.put("comment", "备注");

        OnlineInvoiceTitles_EN.put("onlineInvoiceNum", "onlineInvoiceNum");
        OnlineInvoiceTitles_EN.put("accountPayableDealine", "accountPayableDealine");
        OnlineInvoiceTitles_EN.put("invoiceStatus", "invoiceStatus");
        OnlineInvoiceTitles_EN.put("importStatus", "importStatus");
        OnlineInvoiceTitles_EN.put("orgName", "orgName");
        OnlineInvoiceTitles_EN.put("vendorCode", "vendorCode");
        OnlineInvoiceTitles_EN.put("vendorName", "vendorName");
        OnlineInvoiceTitles_EN.put("costTypeName", "costTypeName");
        OnlineInvoiceTitles_EN.put("taxInvoiceNum", "taxInvoiceNum");
        OnlineInvoiceTitles_EN.put("fsscNo", "fsscNo");
        OnlineInvoiceTitles_EN.put("businessType", "businessType");
        OnlineInvoiceTitles_EN.put("comment", "comment");

        OnlineInvoiceTitles_JP.put("onlineInvoiceNum", "オンラインインボイス番号");
        OnlineInvoiceTitles_JP.put("accountPayableDealine", "買掛金満期日");
        OnlineInvoiceTitles_JP.put("invoiceStatus", "インボイス状態");
        OnlineInvoiceTitles_JP.put("importStatus", "インポート状態");
        OnlineInvoiceTitles_JP.put("orgName", "業務エンティティ");
        OnlineInvoiceTitles_JP.put("vendorCode", "ベンダー番号");
        OnlineInvoiceTitles_JP.put("vendorName", "サプライヤー名");
        OnlineInvoiceTitles_JP.put("costTypeName", "サプライヤーの場所");
        OnlineInvoiceTitles_JP.put("taxInvoiceNum", "税務インボイス番号");
        OnlineInvoiceTitles_JP.put("fsscNo", "インボイス番号");
        OnlineInvoiceTitles_JP.put("businessType", "ビジネスタイプ");
        OnlineInvoiceTitles_JP.put("comment", "コメント");

    }


    /**
     * 根据当前环境语言返回标题
     * @return
     */
    public static LinkedHashMap<String,String> getOnlineInvoiceTitles(){
        String localeKey = LocaleHandler.getLocaleKey();
        switch (localeKey){
            case "en_US":
                return OnlineInvoiceTitles_EN;
            case "ja_JP":
                return OnlineInvoiceTitles_JP;
            default:
                return OnlineInvoiceTitles_CH;
        }
    }

}
