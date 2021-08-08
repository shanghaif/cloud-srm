package com.midea.cloud.srm.pr.logisticsRequirement.service;

import com.midea.cloud.srm.model.logistics.pr.requirement.entity.LogisticsRequirementHead;
import com.midea.cloud.srm.model.logistics.pr.requirement.entity.LogisticsRequirementLine;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
*  <pre>
 *  物流采购需求行表 服务类
 * </pre>
*
* @author chenwt24@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-11-27 10:59:47
 *  修改内容:
 * </pre>
*/
public interface IRequirementLineService extends IService<LogisticsRequirementLine> {

    /**
     * 批量新增附件
     * */
    void addRequirementLineBatch(LogisticsRequirementHead requirementHead, List<LogisticsRequirementLine> requirementLineList);

    void updateBatch(LogisticsRequirementHead requirementHead, List<LogisticsRequirementLine> requirementLineList);

    void importRequirementLineModelDownload(HttpServletResponse response,Long templateHeadId) throws Exception;

    /**
     * 物流采购申请-路线-导入文件模板下载
     * @param response
     */
    void importModelDownload2(HttpServletResponse response) throws IOException;

    /**
     * 导出文件下载
     * @param response
     * @param id
     */
    void export(HttpServletResponse response,Long id) throws IOException;

    /**
     *
     * @param file
     * @return
     */
    List<LogisticsRequirementLine> importExcel(MultipartFile file) throws IOException;
}
