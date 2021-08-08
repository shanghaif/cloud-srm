package com.midea.cloud.srm.inq.price.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.feign.bargaining.BidClient;
import com.midea.cloud.srm.inq.price.mapper.ApprovalBiddingItemMapper;
import com.midea.cloud.srm.inq.price.mapper.InquiryHeaderReportMapper;
import com.midea.cloud.srm.inq.price.service.IApprovalBiddingItemService;
import com.midea.cloud.srm.inq.price.service.IApprovalHeaderService;
import com.midea.cloud.srm.inq.price.service.IApprovalItemService;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.inq.price.dto.*;
import com.midea.cloud.srm.model.inq.price.entity.ApprovalBiddingItem;
import com.midea.cloud.srm.model.inq.price.entity.ApprovalHeader;
import com.midea.cloud.srm.model.inq.price.entity.InquiryHeaderReport;
import com.midea.cloud.srm.model.inq.price.enums.SourcingType;
import com.midea.cloud.srm.model.inq.price.vo.ApprovalAllVo;
import com.midea.cloud.srm.model.inq.price.vo.ApprovalBiddingItemVO;
import com.midea.cloud.srm.model.inq.price.vo.ApprovalVo;
import com.midea.cloud.srm.model.pm.po.dto.NetPriceQueryDTO;
import io.swagger.models.auth.In;
import jdk.nashorn.internal.objects.annotations.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * <pre>
 *  价格审批单头表 前端控制器
 * </pre>
 *
 * @author linxc6@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-04-08 15:28:50
 *  修改内容:
 *          </pre>
 */
@RestController
@RequestMapping("/price/approval")
@Slf4j
public class ApprovalController extends BaseController {

    private final IApprovalHeaderService iApprovalHeaderService;
    @Resource
    private ApprovalBiddingItemMapper biddingItemMapper;
    @Resource
    private BidClient brgClient;
    @Resource
    private com.midea.cloud.srm.feign.bid.BidClient bidClient;
    @Resource
    private InquiryHeaderReportMapper reportMapper;

    @Autowired
    public ApprovalController(IApprovalHeaderService iApprovalHeaderService) {
        this.iApprovalHeaderService = iApprovalHeaderService;
    }

    /**
     * 导入文件模板下载
     *
     * @return
     */
    @RequestMapping("/importModelDownload")
    public void importModelDownload(HttpServletResponse response) throws IOException {
        iApprovalHeaderService.importModelDownload(response);
    }

    /**
     * 导入价格审批中标行信息
     *
     * @param file
     */
    @RequestMapping("/importExcel")
    public Map<String, Object> importExcel(@RequestParam("file") MultipartFile file, Long approvalHeaderId, Fileupload fileupload) throws Exception {
        return iApprovalHeaderService.importExcel(file, approvalHeaderId, fileupload);
    }

    /**
     * 手工新增价格审批单
     *
     * @param insertPriceApprovalDTO
     */
    @PostMapping("/savePriceApproval")
    public Long savePriceApproval(@RequestBody InsertPriceApprovalDTO insertPriceApprovalDTO) {
        return iApprovalHeaderService.savePriceApproval(insertPriceApprovalDTO);
    }

    /**
     * 提交审批
     */
    @PostMapping("/submit")
    public void ceeaSubmit(@RequestBody InsertPriceApprovalDTO insertPriceApprovalDTO) {
        iApprovalHeaderService.ceeaSubmit(insertPriceApprovalDTO);
    }

    /**
     * 生成价格审批单
     *
     * @param insertPriceApprovalDTO
     */
    @PostMapping("/insertPriceApproval")
    public void insertPriceApproval(@RequestBody InsertPriceApprovalDTO insertPriceApprovalDTO) {
        iApprovalHeaderService.insertPriceApproval(insertPriceApprovalDTO);
    }

    /**
     * 查询净价
     *
     * @param netPriceQueryDTO
     * @return
     */
    @PostMapping("/getApprovalNetPrice")
    public BigDecimal getApprovalNetPrice(@RequestBody NetPriceQueryDTO netPriceQueryDTO) {
        return iApprovalHeaderService.getApprovalNetPrice(netPriceQueryDTO);
    }

    /**
     * 生成价格审批单
     */
    @PutMapping
    public void generatePriceApproval(Long inquiryId) {
        Assert.notNull(inquiryId, "询价单id不能为空");
        iApprovalHeaderService.generatePriceApproval(inquiryId);
    }


    /**
     * 询价结果审批分页查询
     */
    @PostMapping("/header")
    public PageInfo<ApprovalHeaderQueryResponseDTO> queryHeader(@RequestBody ApprovalHeaderQueryRequestDTO request) {
        return iApprovalHeaderService.pageQuery(request);
    }

    /**
     * 价格审批单分页查询
     *
     * @return
     */
    @PostMapping("/listPage")
    public PageInfo<ApprovalHeader> ceeaListPage(@RequestBody ApprovalHeaderQueryRequestDTO request) {
        return iApprovalHeaderService.ceeaListPage(request);
    }

    /**
     * 合同查找价格审批物料
     *
     * @return
     */
    @PostMapping("/ceeaQueryByCm")
    public List<ApprovalBiddingItem> ceeaQueryByCm(@RequestBody ApprovalBiddingItemDto approvalBiddingItemDto) {
        return iApprovalHeaderService.ceeaQueryByCm(approvalBiddingItemDto);
    }

    /**
     * 价格审批详情（有传menuId则进入审批流程流程）
     */
    @RequestMapping("/detail")
    public ApprovalHeaderDetailResponseDTO getApprovalDetail(Long approvalHeaderId, Long menuId) {
        return iApprovalHeaderService.getApprovalDetail(approvalHeaderId, menuId);
    }

    /**
     * 获取价格审批详情
     */
    @GetMapping("/approvalDetail")
    public ApprovalAllVo ceeaGetApprovalDetail(Long approvalHeaderId) {
        return iApprovalHeaderService.ceeaGetApprovalDetail(approvalHeaderId);
    }

    /**
     * 获取价格审批详情
     */
    @GetMapping("/getApprovalDetails")
    public ApprovalVo getApprovalDetails(@RequestParam("ceeaSourceNo") String ceeaSourceNo) {
        return iApprovalHeaderService.getApprovalDetails(ceeaSourceNo);
    }

    /**
     * 审批通过
     */
    @PostMapping("/auditPass")
    public void auditPass(@RequestParam("approvalHeaderId") Long approvalHeaderId) {
        Assert.notNull(approvalHeaderId, LocaleHandler.getLocaleMsg("审批单id不能为空"));
        ApprovalHeader header = iApprovalHeaderService.getById(approvalHeaderId);
        Assert.notNull(header, LocaleHandler.getLocaleMsg("价格审批单不存在"));
        iApprovalHeaderService.auditPass(header);
    }

    /**
     * 审批驳回
     */
    @RequestMapping("/reject")
    public void reject(Long approvalHeaderId) {
        Assert.notNull(approvalHeaderId, LocaleHandler.getLocaleMsg("审批单id不能为空"));
        iApprovalHeaderService.reject(approvalHeaderId, null);
    }

    /**
     * 废弃订单
     *
     * @param approvalHeaderId
     */
    @GetMapping("/abandon")
    public void abandon(Long approvalHeaderId) {
        Assert.notNull(approvalHeaderId, "废弃订单id不能为空");
        iApprovalHeaderService.abandon(approvalHeaderId);
    }


    /**
     * 价格审批单转合同
     */
    @PostMapping("/approvalToContract")
    public void ceeaApprovalToContract(@RequestParam("approvalHeaderId") Long approvalHeadId) {
        iApprovalHeaderService.ceeaApprovalToContract(approvalHeadId);
    }


    /**
     * 根据选中的行生成合同
     *
     * @param itemVOS
     * @return
     */
    @PostMapping("/genContractByApprovalBidingItemVOs")
    List<Long> genContractByApprovalBidingItemVOs(@RequestBody List<ApprovalBiddingItemVO> itemVOS) {
        return iApprovalHeaderService.genContractByApprovalBidingItemVOs(itemVOS);
    }

    /**
     * 根据查询条件查询行信息
     *
     * @param queryDto
     * @return
     */
    @PostMapping("/getItemVoByParam")
    List<ApprovalBiddingItemVO> getItemVoByParam(@RequestBody ApprovalToContractQueryDto queryDto) {
        return iApprovalHeaderService.getItemVoByParam(queryDto);
    }

    /**
     * 根据价格审批头id返回类型
     *
     * @param approvalHeaderId
     * @return
     */
    @GetMapping("/getContractTypeByApprovalHeaderId")
    List<Map<String, Object>> getContractTypeByApprovalHeaderId(@RequestParam("approvalHeaderId") Long approvalHeaderId) {
        return iApprovalHeaderService.getContractTypeByApprovalHeaderId(approvalHeaderId);
    }

    /**
     * 根据审批头获取组织id和组织名
     *
     * @param approvalHeaderId
     * @return
     */
    @GetMapping("/getOrgInfoByApprovalHeaderId")
    List<Map<String, Object>> getOrgInfoByApprovalHeaderId(@RequestParam("approvalHeaderId") Long approvalHeaderId) {
        return iApprovalHeaderService.getOrgInfoByApprovalHeaderId(approvalHeaderId);
    }

    /**
     * 提供回写接口
     *
     * @param itemVOS
     */
    @PostMapping("/updateItemsBelongNumber")
    public void updateApprovalBidingInfo(@RequestBody Collection<ApprovalBiddingItemVO> itemVOS) {
        iApprovalHeaderService.updateItemsBelongNumber(itemVOS);
    }

    @PostMapping("/resetItemsBelongNumber")
    public void resetItemsBelongNumber(@RequestBody Collection<Long> contractIds) {
        if (CollectionUtils.isEmpty(contractIds)) {
            return;
        }
        iApprovalHeaderService.resetItemsBelongNumber(contractIds);
    }

    /**
     * 根据审批头id查看是否有剩余可创建合同行
     */
    @GetMapping("/checkWhetherCreateContract")
    public boolean checkWhetherCreateContract(@RequestParam("approvalHeaderId") Long approvalHeaderId) {
        Integer count = biddingItemMapper.selectCount(Wrappers.lambdaQuery(ApprovalBiddingItem.class)
                .eq(ApprovalBiddingItem::getApprovalHeaderId, approvalHeaderId)
                .isNull(ApprovalBiddingItem::getFromContractId)
        );
        return count > 0;
    }

    @GetMapping("/dropApprovalHeader")
    @Transactional(rollbackFor = Exception.class)
    public void dropApprovalHeader(@RequestParam Long approvalHeaderId) {
        ApprovalHeader one = iApprovalHeaderService.getOne(Wrappers.lambdaQuery(ApprovalHeader.class)
                .select(ApprovalHeader::getSourceType, ApprovalHeader::getCeeaSourceNo, ApprovalHeader::getApprovalHeaderId)
                .eq(ApprovalHeader::getApprovalHeaderId, approvalHeaderId)
        );
        if (Objects.isNull(one)) {
            throw new BaseException(String.format("价格审批单不存在,id:%s", approvalHeaderId));
        }
        if (Objects.isNull(one.getSourceType()) || Objects.isNull(one.getCeeaSourceNo())) {
            return;
        }
        boolean bid = one.getSourceType().equals(SourcingType.TENDER.getItemValue());
        try {
            if (bid) {
                bidClient.changeBidingApprovalStatus("N", one.getCeeaSourceNo());
            } else {
                brgClient.changeBidingApprovalStatus("N", one.getCeeaSourceNo());
            }
        } catch (Exception e) {
            if (bid) {
                bidClient.changeBidingApprovalStatus("Y", one.getCeeaSourceNo());
            } else {
                brgClient.changeBidingApprovalStatus("Y", one.getCeeaSourceNo());
            }
            return;
        }
        iApprovalHeaderService.removeById(approvalHeaderId);
        biddingItemMapper.delete(Wrappers.lambdaQuery(ApprovalBiddingItem.class)
                .eq(ApprovalBiddingItem::getApprovalHeaderId, approvalHeaderId)
        );
        reportMapper.delete(Wrappers.lambdaQuery(InquiryHeaderReport.class)
                .eq(InquiryHeaderReport::getBiddingNo, one.getCeeaSourceNo())
        );


    }

    /**
     * 根据寻源单号获取价格审批单（采购申请报表使用）
     * @param subsequentDocumentsNo
     * @return
     */
    @PostMapping("/listApprovalHeaderBysourceNo")
    public List<ApprovalHeader> listApprovalHeaderBysourceNo(@RequestBody List<String> subsequentDocumentsNo){
        if(CollectionUtils.isEmpty(subsequentDocumentsNo)){
            return Collections.EMPTY_LIST;
        }
        QueryWrapper<ApprovalHeader> wrapper = new QueryWrapper<>();
        wrapper.in("CEEA_SOURCE_NO",subsequentDocumentsNo);
        return iApprovalHeaderService.list(wrapper);
    }


}
