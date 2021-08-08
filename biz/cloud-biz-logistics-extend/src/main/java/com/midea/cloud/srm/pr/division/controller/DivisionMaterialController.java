package com.midea.cloud.srm.pr.division.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.pm.pr.division.dto.DivisionMaterialQueryDTO;
import com.midea.cloud.srm.model.pm.pr.division.entity.DivisionMaterial;
import com.midea.cloud.srm.pr.division.service.IDivisionMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
*  <pre>
 *  物料分工规则表 前端控制器(已弃用)
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-07-22 08:45:14
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/division/divisionMaterial")
public class DivisionMaterialController extends BaseController {

    @Autowired
    private IDivisionMaterialService iDivisionMaterialService;


    /**
     * 新增物料分工规则
     * @param divisionMaterials
     */
    @PostMapping("/saveDivisionMaterial")
    public void saveDivisionMaterial(@RequestBody List<DivisionMaterial> divisionMaterials) {
        iDivisionMaterialService.saveOrUpdateDivisionMaterial(divisionMaterials);
    }

    /**
     * 编辑物料分工规则
     * @param divisionMaterials
     */
    @PostMapping("/updateDivisionMaterial")
    public void updateDivisionMaterial(@RequestBody List<DivisionMaterial> divisionMaterials) {
        iDivisionMaterialService.saveOrUpdateDivisionMaterial(divisionMaterials);
    }

    /**
     * 删除物料分工规则
     * @param divisionMaterialId
     */
    @GetMapping("/deleteDivisionMaterial")
    public void deleteDivisionMaterial(@RequestParam Long divisionMaterialId) {
        iDivisionMaterialService.removeById(divisionMaterialId);
    }

    /**
     * 分页条件查询
     * @param divisionMaterialQueryDTO
     * @return
     */
    @PostMapping("/listPageByParam")
    public PageInfo<DivisionMaterial> listPageByParam (@RequestBody DivisionMaterialQueryDTO divisionMaterialQueryDTO) {
        return iDivisionMaterialService.listPageByParam(divisionMaterialQueryDTO);
    }

    /**
     * 导入文件模板下载
     * @param response
     * @throws IOException
     */
    @RequestMapping("/importModelDownload")
    public void importModelDownload(HttpServletResponse response) throws Exception {
        iDivisionMaterialService.importModelDownload(response);
    }

    /**
     * 导入文件
     * @param file
     */
    @RequestMapping("/importExcel")
    public void importExcel(@RequestParam("file") MultipartFile file) throws Exception {
        iDivisionMaterialService.importExcel(file);
    }
}
