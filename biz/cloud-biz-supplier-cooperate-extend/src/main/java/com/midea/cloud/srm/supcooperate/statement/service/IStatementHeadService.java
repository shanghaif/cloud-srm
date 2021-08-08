package com.midea.cloud.srm.supcooperate.statement.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.pm.ps.statement.dto.StatementDTO;
import com.midea.cloud.srm.model.pm.ps.statement.entity.StatementHead;
import com.midea.cloud.srm.supcooperate.statement.controller.StatementHeadController.PassStatementDTO;

/**
 * <pre>
 *  对账单-头表 服务类
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 *          <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/5/29 12:00
 *  修改内容:
 *          </pre>
 */
public interface IStatementHeadService extends IService<StatementHead> {

	void saveOrUpdateStatement(StatementDTO statementDTO);

	void deleteStatement(List<Long> statementHeadIds);

	void passStatement(PassStatementDTO passStatementDTO);

	void cancelStatement(Long statementHeadId);

	StatementDTO getStatementById(Long statementHeadId);
}
