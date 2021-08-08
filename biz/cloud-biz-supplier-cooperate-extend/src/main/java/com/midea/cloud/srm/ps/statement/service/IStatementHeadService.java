package com.midea.cloud.srm.ps.statement.service;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;

import com.midea.cloud.srm.model.pm.ps.statement.dto.StatementDTO;

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
public interface IStatementHeadService {

	void saveStatement(StatementDTO statementDTO);

	void updateStatement(StatementDTO statementDTO);

	void submitStatement(StatementDTO statementDTO);

	void deleteStatement(@RequestBody List<Long> statementHeadIds);

	void recallStatement(@RequestBody List<Long> statementHeadIds);

}
