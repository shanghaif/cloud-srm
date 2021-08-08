package com.midea.cloud.srm.model.supplierauth.purchasecatalog.entity;

import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;

import java.util.ArrayList;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author yourname@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/5/21 13:41
 *  修改内容:
 * </pre>
 */
@Data
public class PurchaseCatalogVo extends BaseDTO {
    /**
     * 采购目录参数
     */
    private PurchaseCatalog queryParam;
    /**
     * 采购目录导出标题
     */
    private ArrayList<String> titleList;
    /**
     * 导出文件名字
     */
    private String fileName;
}
