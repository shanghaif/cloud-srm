package com.midea.cloud.srm.inq.price.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.PriceApprovalStatus;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.inq.price.dto.*;
import com.midea.cloud.srm.model.inq.price.entity.ApprovalBiddingItem;
import com.midea.cloud.srm.model.inq.price.entity.ApprovalHeader;
import com.midea.cloud.srm.model.inq.price.vo.ApprovalAllVo;
import com.midea.cloud.srm.model.inq.price.vo.ApprovalBiddingItemVO;
import com.midea.cloud.srm.model.inq.price.vo.ApprovalVo;
import com.midea.cloud.srm.model.pm.po.dto.NetPriceQueryDTO;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *  价格审批单头表 服务类
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
 * </pre>
 */
public interface IApprovalHeaderService extends IService<ApprovalHeader> {

    /**
     * 创建价格审批单
     *
     * @param insertPriceApprovalDTO
     */
    Long savePriceApproval(InsertPriceApprovalDTO insertPriceApprovalDTO);

    /**
     * 生成价格审批单
     *
     * @param insertPriceApprovalDTO
     */
    void insertPriceApproval(InsertPriceApprovalDTO insertPriceApprovalDTO);

    /**
     * 查询净价
     *
     * @param netPriceQueryDTO
     * @return
     */
    BigDecimal getApprovalNetPrice(NetPriceQueryDTO netPriceQueryDTO);

    /**
     * 生成价格审批单
     */
    void generatePriceApproval(Long inquiryId);

    /**
     * 结果审批分页查询
     */
    PageInfo<ApprovalHeaderQueryResponseDTO> pageQuery(ApprovalHeaderQueryRequestDTO request);

    /**
     * 价格审批单分页查询
     */
    PageInfo<ApprovalHeader> ceeaListPage(ApprovalHeaderQueryRequestDTO request);

    /**
     * 结果审批单详情
     */
    ApprovalHeaderDetailResponseDTO getApprovalDetail(Long approvalHeaderId, Long menuId);


    /**
     * 审核通过
     */
    void auditPass(ApprovalHeader header);

    /**
     * 审核驳回
     */
    void reject(Long approvalHeaderId,String ceeaDrafterOpinion);

    /**
     * 废弃订单
     * @param approvalHeaderId
     */
    void abandon(Long approvalHeaderId);
    /**
     * 撤回
     * @param approvalHeaderId
     */
     void withdraw(Long approvalHeaderId);

    /**
     * 提交审批
     *
     * @param insertPriceApprovalDTO
     */
    void ceeaSubmit(InsertPriceApprovalDTO insertPriceApprovalDTO);

    /**
     * 查询采购审批单详情
     *
     * @param approvalHeadId
     * @return
     */
    ApprovalAllVo ceeaGetApprovalDetail(Long approvalHeadId);


    ApprovalVo getApprovalDetails(String ceeaSourceNo);

    /**
     * 配额修改中标行至价格库
     */
    void ceeaQuotaToPriceLibrary(List<ApprovalBiddingItem> approvalBiddingItemList);

    /**
     * 合同查找寻缘物料
     *
     * @param approvalBiddingItemDto
     * @return
     */
    List<ApprovalBiddingItem> ceeaQueryByCm(ApprovalBiddingItemDto approvalBiddingItemDto);

    void ceeaApprovalToContract(Long approvalHeadId);

    /**
     * 根据价格审批单id获取类型
     * @author tanjl11
     * @param approvalHeaderId
     * @return
     */
    List<Map<String,Object>> getContractTypeByApprovalHeaderId(Long approvalHeaderId);

    /**
     * 获取当前审批单的组织信息
     * @param approvalHeaderId
     * @return
     */
    List<Map<String, Object>> getOrgInfoByApprovalHeaderId(Long approvalHeaderId);

    /**
     * 根据条件查询行信息
     * @author tanjl11
     * @param queryDto
     * @return
     */
    List<ApprovalBiddingItemVO> getItemVoByParam(ApprovalToContractQueryDto queryDto);

    /**
     * 返回生成的合同id
     * @author tanjl11
     * @param itemVOS
     * @return
     */
    List<Long> genContractByApprovalBidingItemVOs(List<ApprovalBiddingItemVO> itemVOS);

    /**
     * 提供回写接口
     * @author tanjl11
     * @param itemVOS
     */
   void updateItemsBelongNumber(Collection<ApprovalBiddingItemVO> itemVOS );

    /**
     *  把合同的价格审批单清空
     * @param contractIds
     */
    void resetItemsBelongNumber(Collection<Long> contractIds);

    /**
     * 中标行导入模板下载
     * @param response
     * @throws IOException
     */
    void importModelDownload(HttpServletResponse response) throws IOException;

    /**
     * 导入价格审批中标行信息
     * @param file
     */
    Map<String,Object> importExcel(MultipartFile file, Long approvalHeaderId , Fileupload fileupload) throws Exception;
}