package com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.EasyExcelUtil;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service.IBidRequirementLineService;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service.IBidRequirementService;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.entity.BidRequirement;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.entity.BidRequirementLine;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.vo.BidRequirementLineImportVO;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.vo.BidRequirementVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * <pre>
 *  招标需求表 前端控制器
 * </pre>
 *
 * @author fengdc3@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人: tanjl11@meicloud.com
 *  修改日期: 2020-09-04 11:43:00
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/bidInitiating/bidRequirement")
public class BidRequirementController {

    @Autowired
    private IBidRequirementService iBidRequirementService;
    @Autowired
    private IBidRequirementLineService iBidRequirementLineService;


    /**
     * 获取
     *
     * @param id
     */
    @GetMapping("/get")
    public BidRequirement get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iBidRequirementService.getById(id);
    }

    /**
     * 根据招标ID获取需求简述
     *
     * @param bidingId
     */
    @GetMapping("/getByBidingId")
    public BidRequirement getByBidingId(Long bidingId) {
        return iBidRequirementService.getBidRequirement(bidingId);
    }

    /**
     * 新增
     *
     * @param bidRequirementVO
     */
    @PostMapping("/add")
    public BidRequirementVO add(@RequestBody BidRequirementVO bidRequirementVO) {
        iBidRequirementService.saveBidRequirement(bidRequirementVO.getBidRequirement(),
                bidRequirementVO.getBidRequirementLineList());
        List<BidRequirement> bidList = iBidRequirementService.list(Wrappers.lambdaQuery(
                BidRequirement.class
        ).eq(BidRequirement::getBidingId, bidRequirementVO.getBidRequirement().getBidingId()));
        if (bidList.size() > 1) {
            throw new BaseException("招标需求头数据有误，请联系管理员");
        }
        BidRequirement byId = bidList.get(0);
        List<BidRequirementLine> list = iBidRequirementLineService.listPage(new BidRequirementLine().setBidingId(bidRequirementVO.getBidRequirement().getBidingId())).getList();
        bidRequirementVO.setBidRequirement(byId);
        bidRequirementVO.setBidRequirementLineList(list);
        return bidRequirementVO;
    }

    /**
     * 删除
     *
     * @param id
     */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iBidRequirementService.removeById(id);
    }

    /**
     * 修改
     *
     * @param bidRequirementVO
     */
    @PostMapping("/modify")
    public BidRequirementVO modify(@RequestBody BidRequirementVO bidRequirementVO) {
        iBidRequirementService.updateBidRequirement(bidRequirementVO.getBidRequirement(),
                bidRequirementVO.getBidRequirementLineList());
        BidRequirement byId = iBidRequirementService.getById(bidRequirementVO.getBidRequirement().getBidingId());

        List<BidRequirementLine> list = iBidRequirementLineService.listPage(new BidRequirementLine().setBidingId(bidRequirementVO.getBidRequirement().getBidingId())).getList();
        bidRequirementVO.setBidRequirement(byId);
        bidRequirementVO.setBidRequirementLineList(list);
        return bidRequirementVO;
    }

    /**
     * 分页查询
     *
     * @param bidRequirement
     * @return
     */
    @PostMapping("/listPage")
    public PageInfo<BidRequirement> listPage(@RequestBody BidRequirement bidRequirement) {
        PageUtil.startPage(bidRequirement.getPageNum(), bidRequirement.getPageSize());
        QueryWrapper<BidRequirement> wrapper = new QueryWrapper<BidRequirement>(bidRequirement);
        return new PageInfo<BidRequirement>(iBidRequirementService.list(wrapper));
    }

    /**
     * 查询所有
     *
     * @return
     */
    @PostMapping("/listAll")
    public List<BidRequirement> listAll() {
        return iBidRequirementService.list();
    }

    /**
     * excel导出
     */
    @GetMapping("/excelExportBidRequirementLine")
    public void excelEportBidRequirementLine(Long bidingId, HttpServletResponse response) throws IOException {
        iBidRequirementLineService.excelResponse(response, bidingId);
    }

    /**
     * excel导入项目需求行
     */
    @PostMapping("/excelImportLine")
    public List<BidRequirementLine> excelImport(@RequestParam("file") MultipartFile file) throws IOException {
        Assert.notNull(file, "文件上传失败");
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        Assert.isTrue((org.apache.commons.lang3.StringUtils.equals("xls", suffix.toLowerCase()) || org.apache.commons.lang3.StringUtils.equals("xlsx", suffix.toLowerCase())),
                "请上传excel文件");
        List<Object> voList = EasyExcelUtil.readExcelWithModel(file.getInputStream(),
                BidRequirementLineImportVO.class);
        return iBidRequirementLineService.importExcelInfo(voList);
    }


}
