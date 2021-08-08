package com.midea.cloud.srm.base.material.utils;

import com.midea.cloud.component.context.i18n.LocaleHandler;

import java.util.LinkedHashMap;

/**
 * <pre>
 * 物料维护表导出工具
 * </pre>
 *
 * @author dengyl23@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/23
 *  修改内容:
 * </pre>
 */
public class MaterialItemExportUtils {
    /**
     * 中文导出标题
     */
    public static final LinkedHashMap<String,String> MaterialItemTitles_CH;
    /**
     * 日文导出标题
     */
    public static final LinkedHashMap<String,String> MaterialItemTitles_JP;
    /**
     * 英文
     */
    public static final LinkedHashMap<String,String> MaterialItemTitles_EN;

    static {
        MaterialItemTitles_CH = new LinkedHashMap<>();
        MaterialItemTitles_JP = new LinkedHashMap<>();
        MaterialItemTitles_EN = new LinkedHashMap<>();

        MaterialItemTitles_CH.put("materialName", "物料名称");
        MaterialItemTitles_CH.put("materialCode", "物料编码");
        MaterialItemTitles_CH.put("specification", "规格型号");
        MaterialItemTitles_CH.put("unit", "单位");
        MaterialItemTitles_CH.put("categoryFullName", "品类");
        MaterialItemTitles_CH.put("ceeaMaterialStatus", "状态");
        MaterialItemTitles_CH.put("ceeaSupplierCode", "供应商编码");
        MaterialItemTitles_CH.put("ceeaSupplierName", "供应商名称");
        MaterialItemTitles_CH.put("ceeaIfCatalogMaterial", "是否目录化物料");
        MaterialItemTitles_CH.put("ceeaContractNo", "合同编号");

        MaterialItemTitles_EN.put("materialName", "materialName");
        MaterialItemTitles_EN.put("materialCode", "materialCode");
        MaterialItemTitles_EN.put("specification", "specification");
        MaterialItemTitles_EN.put("unit", "unit");
        MaterialItemTitles_EN.put("categoryFullName", "category");
        MaterialItemTitles_EN.put("ceeaMaterialStatus", "status");
        MaterialItemTitles_EN.put("ceeaSupplierCode", "supplierCode");
        MaterialItemTitles_EN.put("ceeaSupplierName", "supplierName");
        MaterialItemTitles_EN.put("ceeaIfCatalogMaterial", "ceeaIfCatalogMaterial");
        MaterialItemTitles_EN.put("ceeaContractNo", "contractNo");

        MaterialItemTitles_JP.put("materialName", "素材名");
        MaterialItemTitles_JP.put("materialCode", "材料コーディング");
        MaterialItemTitles_JP.put("specification", "仕様モデル");
        MaterialItemTitles_JP.put("unit", "単位");
        MaterialItemTitles_JP.put("categoryFullName", "カテゴリー");
        MaterialItemTitles_JP.put("ceeaMaterialStatus", "状態");
        MaterialItemTitles_JP.put("ceeaSupplierCode", "サプライヤーコード");
        MaterialItemTitles_JP.put("ceeaSupplierName", "サプライヤ名");
        MaterialItemTitles_JP.put("ceeaIfCatalogMaterial", "資料をカタログ化するかどうか");
        MaterialItemTitles_JP.put("ceeaContractNo", "契約番号");

    }

    /**
     * 根据当前环境语言返回标题
     * @return
     */
    public static LinkedHashMap<String,String> getMaterItemTitles(){
        String localeKey = LocaleHandler.getLocaleKey();
        switch (localeKey){
            case "en_US":
                return MaterialItemTitles_EN;
            case "ja_JP":
                return MaterialItemTitles_JP;
            default:
                return MaterialItemTitles_CH;
        }
    }

}
