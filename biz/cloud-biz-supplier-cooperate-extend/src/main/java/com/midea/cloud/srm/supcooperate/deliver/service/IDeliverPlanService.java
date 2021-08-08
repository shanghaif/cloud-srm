package com.midea.cloud.srm.supcooperate.deliver.service;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.suppliercooperate.deliver.dto.DeliverPlanDTO;
import com.midea.cloud.srm.model.suppliercooperate.deliver.entity.DeliverPlan;
import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.suppliercooperate.deliver.entity.DeliverPlanDetail;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
*  <pre>
 *  到货计划维护表 服务类
 * </pre>
*
* @author zhi1772778785@163.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-27 14:42:31
 *  修改内容:
 * </pre>
*/
public interface IDeliverPlanService extends IService<DeliverPlan> {
    PageInfo<DeliverPlan> getdeliverPlanListPage(DeliverPlanDTO deliverPlanDTO);
    DeliverPlanDTO getDeliverPlan(Long id);
    void modifyDeliverPlan(DeliverPlanDTO deliverPlanDTO);

    /**
     * 到货计划导入模板下载
     * @param monthlySchDate
     * @param response
     * @throws IOException
     * @throws ParseException
     */
    void importModelDownload(String monthlySchDate, HttpServletResponse response) throws IOException, ParseException;

    /**
     * 到货计划导入
     * @param file
     * @param fileupload
     * @return
     * @throws Exception
     */
    Map<String,Object> importExcel(MultipartFile file, Fileupload fileupload) throws Exception;

    /**
     * 到货计划详情页导入模板下载
     * @param deliverPlanId
     * @param response
     * @throws Exception
     */
    void importLineModelDownload(Long deliverPlanId, HttpServletResponse response) throws Exception;

    /**
     * 到货计划详情页导入
     * @param file
     * @param deliverPlanId
     * @param fileupload
     * @return
     * @throws Exception
     */
    Map<String,Object> importLineExcel(@RequestParam("file") MultipartFile file,Long deliverPlanId, Fileupload fileupload) throws Exception;

    /**
     * 导出到货计划
     * @param deliverPlanDTO
     * @param response
     * @throws Exception
     */
    void export(DeliverPlanDTO deliverPlanDTO, HttpServletResponse response) throws Exception;

    /**
     * 导出到货计划详情
     * @param deliverPlanId
     * @param response
     * @throws Exception
     */
    void exportLine(@RequestParam("deliverPlanId") Long deliverPlanId, HttpServletResponse response) throws Exception;
    void exportLineCopy(@RequestParam("deliverPlanId") Long deliverPlanId, HttpServletResponse response) throws Exception;

     List<DeliverPlanDetail> getDeliverPlanList(DeliverPlanDTO deliverPlanDTO);
    DeliverPlanDTO getDeliverPlanMRPList(DeliverPlanDTO deliverPlanDTO) throws Exception;

    DeliverPlanDTO getDeliverPlanMessageMRP(DeliverPlanDTO deliverPlanDTO)throws Exception;

     void getAffirmByMrp(Boolean falg);
}
