package com.midea.cloud.srm.${codeVo.moduleName}.${packageName}.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.${codeVo.moduleName}.${packageName}.entity.${classFileName};

import java.io.IOException;
import java.util.List;

/**
* <pre>
 *  测试 服务类
 * </pre>
*
* @author linsb@meicloud.com
* @version 1.00.00
*
* <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Jan 29, 2021 10:18:45 AM
 *  修改内容:
 * </pre>
*/
public interface ${classFileName}Service extends IService<${classFileName}>{
    /**
     * 保存或更新
     * @param ${targetName}
     * @return
     */
    Long addOrUpdate(${classFileName} ${targetName});

    /**
     * 获取详情
     * @param id
     * @return
     */
    ${classFileName} getDetailById(Long id);


    /*
     批量删除
     */
    void batchDeleted(List<Long> ids) throws IOException;

    /*
   分页查询
    */
    PageInfo<${classFileName}> listPage(${classFileName} ${targetName});


}
