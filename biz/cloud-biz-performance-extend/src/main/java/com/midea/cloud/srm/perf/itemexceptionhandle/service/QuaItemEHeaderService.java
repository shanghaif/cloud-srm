package com.midea.cloud.srm.perf.itemexceptionhandle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.perf.itemexceptionhandle.dto.QuaItemEHeaderQueryDTO;
import com.midea.cloud.srm.model.perf.itemexceptionhandle.entity.QuaItemEHeader;
import com.midea.cloud.srm.model.perf.report8d.entity.Qua8dReport;

import java.util.List;

/**
 * <pre>
 *  来料异常处理单 服务类
 * </pre>
 *
 * @author chenjw90@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Jan 27, 2021 7:40:23 PM
 *  修改内容:
 * </pre>
 */
public interface QuaItemEHeaderService extends IService<QuaItemEHeader> {
      /**
       * 分页查询来料异常处理单
       * @param quaItemEHeader
       * @return PageInfo<QuaItemEHeaderQueryDTO>
       */
      PageInfo<QuaItemEHeaderQueryDTO> listPage(QuaItemEHeader quaItemEHeader);

      /**
       * 点击单号获取信息
       * @param itemExceptionHeadId
       */
      QuaItemEHeader get(Long itemExceptionHeadId);

      /**
       * 查询全部来料异常处理单
       * @return List<QuaItemEHeaderQueryDTO>
       */
      List<QuaItemEHeaderQueryDTO> listAll();

      /**
       * 新增
       * @param quaItemEHeader
       */
      Long addOrUpdate(QuaItemEHeader quaItemEHeader);

      /**
       * 添加8D报告
       * @param qua8dReport
       * @return
       */
      Long add8D(Qua8dReport qua8dReport);
}
