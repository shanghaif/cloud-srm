package com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.EasyExcelUtil;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service.IBidRequirementLineService;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.entity.BidRequirement;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.entity.BidRequirementLine;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.vo.BidRequirementVO;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *  招标需求明细表 前端控制器
 * </pre>
 *
 * @author fengdc3@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-25 17:04:28
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/bidInitiating/bidRequirementLine")
public class BidRequirementLineController extends BaseController {

    @Autowired
    private IBidRequirementLineService iBidRequirementLineService;

    /**
     * 获取
     *
     * @param id
     */
    @GetMapping("/get")
    public BidRequirementLine get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iBidRequirementLineService.getById(id);
    }

    /**
     * 批量新增
     *
     * @param bidRequirementVO
     */
    @PostMapping("/addBatch")
    public void addBatch(@RequestBody BidRequirementVO bidRequirementVO) {
        iBidRequirementLineService.saveBidRequirementLineList(bidRequirementVO.getBidRequirementLineList(),
                bidRequirementVO.getBidRequirement());
    }

    /**
     * 删除
     *
     * @param id
     */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iBidRequirementLineService.removeById(id);
    }

    /**
     * 修改
     *
     * @param bidRequirementLine
     */
    @PostMapping("/modify")
    public void modify(@RequestBody BidRequirementLine bidRequirementLine) {
        iBidRequirementLineService.updateById(bidRequirementLine);
    }

    /**
     * 批量 根据标的编号更新拦标价
     *
     * @param bidRequirementLineList
     */
    @PostMapping("/updateBatch")
    public void updateBatch(@RequestBody List<BidRequirementLine> bidRequirementLineList) {
        iBidRequirementLineService.updateTargetPriceBatch(bidRequirementLineList);
    }

    /**
     * 分页查询
     *
     * @param bidRequirementLine
     * @return
     */
    @PostMapping("/listPage")
    public PageInfo<BidRequirementLine> listPage(@RequestBody BidRequirementLine bidRequirementLine) {
        bidRequirementLine.setPageSize(9999);
        return iBidRequirementLineService.listPage(bidRequirementLine);
    }

    /**
     * 导入文件模板下载
     *
     * @return
     */
    @RequestMapping("/importModelDownload")
    public void importModelDownload(HttpServletResponse response) throws Exception {
//        iBidRequirementLineService.importModelDownload(response);
        InputStream inputStream = this.getClass().getResourceAsStream("/excel-model/需求行导入模板.xlsx");
        OutputStream outputStream = null;
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            outputStream = EasyExcelUtil.getServletOutputStream(response, "需求行导入模板");
            workbook.write(outputStream);
        } finally {
            if (null != outputStream) {
                outputStream.flush();
                outputStream.close();
            }
        }
    }

    /**
     * 导入文件返回数据
     *
     * @param file
     */
    @RequestMapping("/importExcel")
    public Map<String, Object> importExcel(@RequestParam("file") MultipartFile file, BidRequirement bidRequirement, Fileupload fileupload) throws Exception {
        return iBidRequirementLineService.importExcel(bidRequirement, file, fileupload);
    }

    /**
     * 查询所有
     *
     * @return
     */
    @PostMapping("/listAll")
    public List<BidRequirementLine> listAll() {
        return iBidRequirementLineService.list();
    }

    /**
     * 根据投标单ID查找项目需求明细
     *
     * @param bidingId
     * @return
     */
    @GetMapping("/getRequirementLinesByBidingId")
    public List<BidRequirementLine> getRequirementLinesByBidingId(@RequestParam Long bidingId) {
        return iBidRequirementLineService.list(Wrappers.lambdaQuery(BidRequirementLine.class)
                .eq(BidRequirementLine::getBidingId, bidingId)
        );
    }
}
