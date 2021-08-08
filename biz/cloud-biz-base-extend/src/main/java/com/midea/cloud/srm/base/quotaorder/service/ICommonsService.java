package com.midea.cloud.srm.base.quotaorder.service;

import com.midea.cloud.srm.model.base.material.MaterialItem;

/**
 * <pre>
 *
 * </pre>
 *
 * @author wangpr@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-27 09:24:20
 *  修改内容:
 * </pre>
 */
public interface ICommonsService {
    /**
     * 1、	若【配额管理类型】为“固定比例”或“综合比例”，则【最小拆单量】必填，否则【最小拆单量】不可填写；
     * 2、	若【配额管理类型】为“配额达成率”，则【最大分配量】必填，否则【最大分配量】不可填写；
     * 以上若校验成功则保存成功，若校验失败，则提示对应的错误。
     */
    void checkMaterialItem(MaterialItem materialItem);
}
