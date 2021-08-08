package com.midea.cloud.srm.perf.processexceptionhandle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.perf.processexceptionhandle.dto.QuaProcessExceptionDTO;
import com.midea.cloud.srm.model.perf.processexceptionhandle.entity.QuaProcessException;
import com.midea.cloud.srm.model.perf.report8d.entity.Qua8dReport;

/**
* <pre>
 *  制程异常处理单 服务类
 * </pre>
*
* @author chenjw90@meicloud.com
* @version 1.00.00
*
* <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Jan 31, 2021 5:45:00 PM
 *  修改内容:
 * </pre>
*/
public interface QuaProcessExceptionService extends IService<QuaProcessException>{

    /*
   分页查询
    */
    PageInfo<QuaProcessExceptionDTO> listPage(QuaProcessException quaProcessException);

    /*
   添加8D报告
    */
    Long add8D(Qua8dReport qua8dReport);

}
