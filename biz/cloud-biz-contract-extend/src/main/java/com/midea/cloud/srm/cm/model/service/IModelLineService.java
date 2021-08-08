package com.midea.cloud.srm.cm.model.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.cm.model.dto.ModelLineDto;
import com.midea.cloud.srm.model.cm.model.entity.ModelLine;

import java.util.List;

/**
*  <pre>
 *  合同行表 服务类
 * </pre>
*
* @author wangpr@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-06-24 09:24:06
 *  修改内容:
 * </pre>
*/
public interface IModelLineService extends IService<ModelLine> {
    /**
     * 批量新增
     * @param modelLineList
     */
    void add(List<ModelLine> modelLineList);

    /**
     * 更新
     * @param modelLineList
     */
    void update(List<ModelLine> modelLineList);

    /**
     * 根据合同模板id带出占位符值
     * @param modelHeadId
     * @return
     */
    List<ModelLine> getModelLine(Long modelHeadId);

    /**
     * 查找合同
     * @param orderId
     * @return
     */
    ModelLineDto queryContract(Long orderId);

    /**
     * 查找合同列表
     * @param modelLine
     * @return
     */
    List<ModelLine> queryContractList(ModelLine modelLine);
}
