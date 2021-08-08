package com.midea.cloud.srm.sup.responsibility.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.base.material.MaterialItemAttribute;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.supplier.responsibility.entity.SupplierLeader;
import com.midea.cloud.srm.sup.responsibility.service.ISupplierLeaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * supplier leader维护表 前端控制器
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-19 14:45:21
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/supplier-leader")
public class SupplierLeaderController extends BaseController {

    @Autowired
    private ISupplierLeaderService iSupplierLeaderService;

    /**
     * 获取
     *
     * @param id
     */
    @GetMapping("/get")
    public SupplierLeader get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iSupplierLeaderService.getById(id);
    }

    /**
     * 新增一行
     *
     * @param supplierLeader
     */
    @PostMapping("/add")
    public void add(@RequestBody SupplierLeader supplierLeader) {
        Long id = IdGenrator.generate();
        supplierLeader.setSupplierLeaderId(id);
        iSupplierLeaderService.save(supplierLeader);
    }

    /**
     * 批量保存
     *
     * @param supplierLeaderList
     */
    @PostMapping("/addsupplierLeader")
    public void addsupplierLeader(@RequestBody List<SupplierLeader> supplierLeaderList) {
        if (!supplierLeaderList.isEmpty()) {
            iSupplierLeaderService.addSupplierLeader(supplierLeaderList);
        }
    }

    /**
     * 删除
     *
     * @param id
     */
    @GetMapping("/delete")
    public void delete(@RequestParam("id") Long id) {
        Assert.notNull(id, "id不能为空！");
        iSupplierLeaderService.removeById(id);
    }

    /**
     * 修改
     *
     * @param supplierLeader
     */
    @PostMapping("/modify")
    public void modify(@RequestBody SupplierLeader supplierLeader) {
        iSupplierLeaderService.updateById(supplierLeader);
    }

    /**
     * 分页条件查询
     *
     * @param supplierLeader
     * @return
     */
    @PostMapping("/listPageByParam")
    public PageInfo<SupplierLeader> listPage(@RequestBody SupplierLeader supplierLeader) {
        PageUtil.startPage(supplierLeader.getPageNum(), supplierLeader.getPageSize());
        return new PageInfo<SupplierLeader>(iSupplierLeaderService.listPageByParam(supplierLeader));
    }

    /**
     * 查询所有
     *
     * @return
     */
    @PostMapping("/listAll")
    public List<SupplierLeader> listAll() {
        return iSupplierLeaderService.list();
    }

    /**
     * 下载supplier leader导入模板
     * @return
     */
    @PostMapping("/importSupplierLeaderModelDownload")
    public void importSupplierLeaderModelDownload(HttpServletResponse response) throws Exception {
        iSupplierLeaderService.importSupplierLeaderModelDownload(response);
    }

    /**
     * 导入文件
     * @param file
     */
    @PostMapping("/importExcel")
    public Map<String,Object> importExcel(@RequestParam("file") MultipartFile file, Fileupload fileupload) throws Exception {
        return iSupplierLeaderService.importExcel(file, fileupload);
    }

}
