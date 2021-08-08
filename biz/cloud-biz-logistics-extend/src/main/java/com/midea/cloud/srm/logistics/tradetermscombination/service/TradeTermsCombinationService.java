package com.midea.cloud.srm.logistics.tradetermscombination.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.logistics.tradetermscombination.dto.TradeTermsCombinationDto;
import com.midea.cloud.srm.model.logistics.tradetermscombination.entity.TradeTermsCombination;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
* <pre>
 *  贸易术语组合 服务类
 * </pre>
*
* @author yancj9@meicloud.com
* @version 1.00.00
*
* <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Mar 3, 2021 11:52:27 AM
 *  修改内容:
 * </pre>
*/
public interface TradeTermsCombinationService extends IService<TradeTermsCombination>{
    /*
    批量更新或者批量新增
    */
    void batchSaveOrUpdate(List<TradeTermsCombination> tradeTermsCombinationList) throws IOException;

    /**
     * 批量更新状态
     * @param tradeTermsCombinationId
     * @param status
     */
    void updateStatus(List<Long> tradeTermsCombinationId, String status);
    /*
     批量删除
     */
    void batchDeleted(List<Long> ids) throws IOException;
    /*
    导出excel模板文件
    */
    public void exportExcelTemplate(HttpServletResponse response) throws IOException;
    /*
    导入excel 内容
     */
    Map<String,Object> importExcel(MultipartFile file, Fileupload fileupload) throws Exception;
    /*
   分页查询
    */
    PageInfo<TradeTermsCombination> listPage(TradeTermsCombination tradeTermsCombination);

    /**
     * 查找生效的贸易术语组合
     * @return
     */
    List<TradeTermsCombinationDto> queryTradeTermsCombinationDto();

}
