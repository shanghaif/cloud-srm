package com.midea.cloud.log.useroperation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.log.biz.dto.BizOperateLogInfoDto;
import com.midea.cloud.srm.model.log.biz.entity.BizOperateLogInfo;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author tanjl11@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/5 14:44
 *  修改内容:
 * </pre>
 */
public interface IBusinessOperationService  extends IService<BizOperateLogInfo> {
    PageInfo<BizOperateLogInfo> listPage(BizOperateLogInfoDto dto);
}
