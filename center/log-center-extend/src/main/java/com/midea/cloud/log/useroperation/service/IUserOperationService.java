package com.midea.cloud.log.useroperation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.log.useroperation.dto.UserOperationDto;
import com.midea.cloud.srm.model.log.useroperation.entity.UserOperation;
import org.springframework.web.bind.annotation.RequestBody;

/**
*  <pre>
 *  用户操作日志表 服务类
 * </pre>
*
* @author wangpr@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-06-11 13:53:23
 *  修改内容:
 * </pre>
*/
public interface IUserOperationService extends IService<UserOperation> {
    /**
     * 分页查询
     * @param userOperation
     * @return
     */
    PageInfo<UserOperation> listPage(UserOperationDto userOperation);
}
