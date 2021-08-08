package com.midea.cloud.srm.base.quotaorder.service.impl;

import com.midea.cloud.common.enums.base.QuotaManagementType;
import com.midea.cloud.srm.base.quotaorder.service.ICommonsService;
import com.midea.cloud.srm.model.base.material.MaterialItem;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

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
@Service
public class CommonsServiceImpl implements ICommonsService {
    /**
     * 1、	若【配额管理类型】为“固定比例”或“综合比例”，则【最小拆单量】必填，否则【最小拆单量】不可填写；
     * 2、	若【配额管理类型】为“配额达成率”，则【最大分配量】必填，否则【最大分配量】不可填写；
     * 以上若校验成功则保存成功，若校验失败，则提示对应的错误。
     */
    @Override
    public void checkMaterialItem(MaterialItem materialItem) {
        if(!QuotaManagementType.QUOTA_ACHIEVEMENT_RATE.name().equals(materialItem.getQuotaManagementType())){
            //【最小拆单量】必填，否则【最小拆单量】不可填写；
            Assert.notNull(materialItem.getMiniSplit(),"物料编码:"+materialItem.getMaterialCode()+",最小拆单量不能为空");
            Assert.isNull(materialItem.getMaxAllocation(),"物料编码:"+materialItem.getMaterialCode()+",最大分配量不可填");
        }else {
            // 【最大分配量】必填，否则【最大分配量】不可填写；
            Assert.notNull(materialItem.getMaxAllocation(),"物料编码:"+materialItem.getMaterialCode()+",最大分配量不能为空");
            Assert.isNull(materialItem.getMiniSplit(),"物料编码:"+materialItem.getMaterialCode()+",最小拆单量不可填");
        }
    }
}
