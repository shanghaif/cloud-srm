package com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.entity.BidRequirement;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.entity.BidRequirementLine;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
*  <pre>
 *  招标需求明细表 服务类
 * </pre>
*
* @author fengdc3@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:  tanjl11@meicloud.com
 *  修改日期: 2020-09-03 17:04:28
 *  修改内容:
 * </pre>
*/
public interface IBidRequirementLineService extends IService<BidRequirementLine> {

    void saveBidRequirementLineList(List<BidRequirementLine> bidRequirementLineList, BidRequirement bidRequirement);

    void updateBatch(List<BidRequirementLine> bidRequirementLineList, BidRequirement bidRequirement);

    void updateTargetPriceBatch(List<BidRequirementLine> bidRequirementLineList);

    PageInfo<BidRequirementLine> listPage(BidRequirementLine bidRequirementLine);

    List<BidRequirementLine> importExcelInfo(List<Object> list);

    void excelResponse(HttpServletResponse response, Long bidingId);

    /**
     * 导入文件返回项目需求数据
     * @param file
     * @param fileupload
     * @return
     * @throws Exception
     */
    Map<String,Object> importExcel(BidRequirement bidRequirement, MultipartFile file, Fileupload fileupload) throws Exception;

    /**
     * 项目需求导入模板下载
     * @param response
     * @throws Exception
     */
    void importModelDownload(HttpServletResponse response) throws Exception;
}
