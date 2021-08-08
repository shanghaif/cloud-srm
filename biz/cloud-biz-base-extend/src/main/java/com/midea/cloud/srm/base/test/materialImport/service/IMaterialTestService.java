package com.midea.cloud.srm.base.test.materialImport.service;

import com.midea.cloud.srm.model.base.test.MaterialTest;
import com.baomidou.mybatisplus.extension.service.IService;

/**
*  <pre>
 *  物料长宽高属性配置表（导测试数据用） 服务类
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-26 13:57:30
 *  修改内容:
 * </pre>
*/
public interface IMaterialTestService extends IService<MaterialTest> {

    void importData();

    void createMaterialAttribute();

}
