package com.midea.cloud.srm.supcooperate.deliver.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.supcooperate.DeliverPlanStatus;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.suppliercooperate.deliver.dto.DeliverPlanDTO;
import com.midea.cloud.srm.model.suppliercooperate.deliver.entity.DeliverPlan;
import com.midea.cloud.srm.supcooperate.deliver.service.IDeliverPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *  到货计划维护表 前端控制器
 * </pre>
 *
 * @author zhi1772778785@163.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-27 14:42:31
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/deliver/deliverPlan")
public class DeliverPlanController extends BaseController {

    @Autowired
    private IDeliverPlanService iDeliverPlanService;

    /**
     * 获取
     *
     * @param id
     */
    @GetMapping("/getDeliverPlan")
    public DeliverPlanDTO getDeliverPlan(Long id) {
        Assert.notNull(id, "id不能为空");
        return iDeliverPlanService.getDeliverPlan(id);
    }

    /**
     * 新增
     *
     * @param deliverPlan
     */
    @PostMapping("/adddDliverPlan")
    public void add(@RequestBody DeliverPlan deliverPlan) {
        Long id = IdGenrator.generate();
        deliverPlan.setDeliverPlanId(id);
        iDeliverPlanService.save(deliverPlan);
    }

    /**
     * 删除
     *
     * @param id
     */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iDeliverPlanService.removeById(id);
    }

    /**
     * 保存
     *
     * @param deliverPlanDTO
     */
    @PostMapping("/modifyDeliverPlan")
    public void modifyDeliverPlan(@RequestBody DeliverPlanDTO deliverPlanDTO) {
        Assert.notNull(deliverPlanDTO, "到货计划详情不能为空");
        iDeliverPlanService.modifyDeliverPlan(deliverPlanDTO);
    }

    /**
     * 分页查询
     *
     * @param deliverPlanDTO
     * @return
     */
    @PostMapping("/deliverPlanListPage")
    public PageInfo<DeliverPlan> deliverPlanListPage(@RequestBody DeliverPlanDTO deliverPlanDTO) {
        return iDeliverPlanService.getdeliverPlanListPage(deliverPlanDTO);
    }

    /**
     * 查询所有
     *
     * @return
     */
    @PostMapping("/listAll")
    public List<DeliverPlan> listAll() {
        return iDeliverPlanService.list();
    }

    /**
     * 改变到货计划状态为发布
     *
     * @param id
     */
    @GetMapping("/getDeliverPlanStatus")
    public void getDeliverPlanStatus(Long id) {
        Assert.notNull(id, "id不能为空");
        DeliverPlan deliverPlan = iDeliverPlanService.getById(id);
        if (deliverPlan != null
                && DeliverPlanStatus.APPROVAL.toString().equals(deliverPlan.getDeliverPlanStatus())) {
            throw new BaseException("R001", "只有已创建的到货计划才能发布");
        }
        deliverPlan.setDeliverPlanStatus(DeliverPlanStatus.APPROVAL.toString());
        iDeliverPlanService.updateById(deliverPlan);
    }

    /**
     * 下载到货计划导入模板
     * @return
     */
    @RequestMapping("/importModelDownload")
    public void importModelDownload(@RequestParam("monthlySchDate") String monthlySchDate, HttpServletResponse response) throws IOException, ParseException {
        iDeliverPlanService.importModelDownload(monthlySchDate, response);
    }

    /**
     * 导入文件
     * @param file
     */
    @RequestMapping("/importExcel")
    public Map<String,Object> importExcel(@RequestParam("file") MultipartFile file, Fileupload fileupload) throws Exception {
        return iDeliverPlanService.importExcel(file, fileupload);
    }

    /**
     * 下载到货计划详情导入模板
     * @return
     */
    @RequestMapping("/importLineModelDownload")
    public void importLineModelDownload(@RequestParam("deliverPlanId") Long deliverPlanId, HttpServletResponse response) throws Exception{
        iDeliverPlanService.importLineModelDownload(deliverPlanId,response);
    }

    /**
     * 导入到货计划详情文件
     * @param file
     */
    @RequestMapping("/importLineExcel")
    public Map<String,Object> importLineExcel(@RequestParam("file") MultipartFile file,Long deliverPlanId, Fileupload fileupload) throws Exception {
        return iDeliverPlanService.importLineExcel(file, deliverPlanId, fileupload);
    }

    /**
     * 导出到货计划
     * @return
     */
    @PostMapping("/export")
    public void export(@RequestBody DeliverPlanDTO deliverPlanDTO, HttpServletResponse response) throws Exception{
        iDeliverPlanService.export(deliverPlanDTO, response);
    }

    /**
     * 导出到货计划详情
     * @return
     */
    @GetMapping("/exportLine")
    public void exportLine(@RequestParam("deliverPlanId") Long deliverPlanId, HttpServletResponse response) throws Exception{
        iDeliverPlanService.exportLine(deliverPlanId, response);
    }


    /**
     * 导出到货计划详情
     * @return
     */
    @GetMapping("/exportLineCopy")
    public void exportLineCopy(@RequestParam("deliverPlanId") Long deliverPlanId, HttpServletResponse response) throws Exception{
        iDeliverPlanService.exportLineCopy(deliverPlanId, response);
    }
}
