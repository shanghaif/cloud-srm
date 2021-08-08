package com.midea.cloud.srm.pr.requirement.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.supplier.SupplierClient;
import com.midea.cloud.srm.model.base.dict.entity.DictItem;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.pm.pr.division.entity.DivisionCategory;
import com.midea.cloud.srm.model.pm.pr.requirement.dto.RequirementManageDTO;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.RequirementLine;
import com.midea.cloud.srm.model.pm.pr.requirement.vo.RequirementLineVO;
import com.midea.cloud.srm.pr.requirement.service.IRequirementHeadService;
import com.midea.cloud.srm.pr.requirement.service.IRequirementLineService;
import com.midea.cloud.srm.pr.requirement.service.IRequirementTransferOrderService;
import com.midea.cloud.srm.ps.http.fssc.service.IFSSCReqService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <pre>
 * 需求池管理Controller
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/5/14 15:23
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/pr/requirementManage")
@Slf4j
public class RequirementManageController extends BaseController {

    /**
     * 采购需求行Service接口
     */
    @Autowired
    private IRequirementLineService iRequirementLineService;

    @Autowired
    private IRequirementHeadService iRequirementHeadService;

    @Autowired
    private IFSSCReqService ifsscReqService;

    @Autowired
    private IRequirementTransferOrderService iRequirementTransferOrderService;
    @Autowired
    private BaseClient baseClient;
    @Autowired
    private SupplierClient supplierClient;

    /**
     * Description 批量驳回采购需求
     *
     * @param rejectReason 驳回原因
     * @return
     * @Param requirementHeaderIds 多个采购需求头id
     * @Author wuwl18@meicloud.com
     * @Date 2020.05.14
     **/
    @PostMapping("/bachRejectRequirement")
//    @PreAuthorize("hasAuthority('pr:requirementManage:reject')")
    public BaseResult<String> bachRejectRequirement(Long[] requirementLineIds, String rejectReason) {
        BaseResult<String> result = new BaseResult();
        try {
            result = iRequirementLineService.bachRejectRequirement(requirementLineIds, rejectReason);
        } catch (BaseException e) {
            result.setCode(ResultCode.OPERATION_FAILED.getCode());
            result.setMessage(ResultCode.OPERATION_FAILED.getMessage());
        }
        return result;
    }

    /**
     * 批量退回(ceea)
     *
     * @param requirementManageDTO
     */
    @PostMapping("/batchReturn")
    public void batchReturn(@RequestBody RequirementManageDTO requirementManageDTO) {
        iRequirementLineService.batchReturn(requirementManageDTO);
    }

    /**
     * 批量分配采购申请
     *
     * @param requirementManageDTO
     */
    @PostMapping("/bachAssigned")
    public void bachAssigned(@RequestBody RequirementManageDTO requirementManageDTO) {
        iRequirementLineService.bachAssigned(requirementManageDTO);
    }

    /**
     * 批量接受采购申请
     *
     * @param requirementManageDTO
     */
    @PostMapping("/batchReceive")
    public void batchReceive(@RequestBody RequirementManageDTO requirementManageDTO) {
        iRequirementLineService.batchReceive(requirementManageDTO);
    }

//    /**
//     * Description 批量分配/未分配采购需求
//     * @Param requirementLineIds 多个采购需求行id
//     * @Param applyStatus 申请状态
//     * @Param buyerId 采购员ID
//     * @Param buyerName 采购员名称
//     * @return
//     * @Author wuwl18@meicloud.com
//     * @Date 2020.05.15
//     **/
//    @PostMapping("/bachAssigned")
//    @PreAuthorize("hasAnyAuthority('pr:requirementManage:allot','pr:requirementManage:cancelAllot')")
//    public BaseResult<String> bachAssigned(Long[] requirementLineIds,String applyStatus, String buyerId, String buyer, String buyerName){
//        AppUserUtil.getLoginAppUser().getPermissions();
//        BaseResult<String> result = new BaseResult<>();
//        try{
//            result = iRequirementLineService.bachAssigned(requirementLineIds, applyStatus, buyerId, buyer, buyerName);
//        }catch (BaseException e){
//            result.setCode(ResultCode.OPERATION_FAILED.getCode());
//            result.setMessage(ResultCode.OPERATION_FAILED.getMessage());
//        }
//        return result;
//    }

//    /**
//     * Description 检查采购需求能否合并(能合并，返回true，不能合并抛出返回提示信息)
//     * @Param
//     * @return
//     * @Author wuwl18@meicloud.com
//     * @Date 2020.05.17
//     * @throws
//     **/
//    @GetMapping("/checkMergeRequirement")
//    @PreAuthorize("hasAuthority('pr:requirementManage:merge')")
//    public BaseResult<String> isMergeRequirement(Long[] requirementLineIds){
//        BaseResult<String> result = new BaseResult<>();
//        try{
//            result = iRequirementLineService.isMergeRequirement(requirementLineIds);
//        }catch (BaseException e){
//            result.setCode(ResultCode.OPERATION_FAILED.getCode());
//            result.setMessage(ResultCode.OPERATION_FAILED.getMessage());
//        }
//        return result;
//    }

    /**
     * Description 获取采购需求合并信息
     *
     * @return
     * @throws
     * @Param
     * @Author wuwl18@meicloud.com
     * @Date 2020.05.17
     **/
    @GetMapping("/findRequirementMergeList")
//    @PreAuthorize("hasAuthority('pr:requirementManage:merge')")
    public BaseResult<List<RequirementLine>> findRequirementMergeList(Long[] requirementLineIds) {
        BaseResult<List<RequirementLine>> result = BaseResult.build(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage());
        try {
            List<RequirementLine> requirementLineList = iRequirementLineService.findRequirementMergeList(requirementLineIds);
            result.setData(requirementLineList);
        } catch (BaseException e) {
            result.setCode(ResultCode.OPERATION_FAILED.getCode());
            result.setMessage(ResultCode.OPERATION_FAILED.getMessage());
        }
        return result;
    }

    /**
     * 需求池管理列表条件分页查询(ceea)
     *
     * @param requirementManageDTO
     * @return
     */
    @PostMapping("/listPageByParam")
    public PageInfo<RequirementManageDTO> listPageByParam(@RequestBody RequirementManageDTO requirementManageDTO) {
//        return iRequirementHeadService.listPageByParam(requirementManageDTO);
        return iRequirementHeadService.listPageByParamNew(requirementManageDTO);
    }

    /**
     * 需求池导出
     *
     * @param requirementManageDTO
     * @return
     */
    @PostMapping("/export")
    public void export(@RequestBody RequirementManageDTO requirementManageDTO, HttpServletResponse response) throws Exception {
        iRequirementHeadService.export(requirementManageDTO, response);
    }

    /**
     * 创建采购订单 (转换采购订单)  查找供应商
     *
     * @param requirementManageDTOS
     * @return
     */
    @PostMapping("/createPurchaseOrder")
    public Collection<RequirementManageDTO> createPurchaseOrder(@RequestBody Map<String, Object> paramMap) {
        List<RequirementManageDTO> requirementManageDTOS = JSON.parseArray(paramMap.get("list").toString(),RequirementManageDTO.class);
        LocalDate from=Optional.ofNullable(paramMap.get("from")).map(e->LocalDate.parse(e.toString(),DateTimeFormatter.ofPattern("yyyy-MM-dd"))).orElse(LocalDate.now());
        LocalDate to=Optional.ofNullable(paramMap.get("to")).map(e->LocalDate.parse(e.toString(),DateTimeFormatter.ofPattern("yyyy-MM-dd"))).orElse(LocalDate.now());
        ////2020-12-01 需求转订单调整:新增海鲜价业务，并代码迁移到新的类中，避免RequirementHeadService越来越长。
        return iRequirementTransferOrderService.createPurchaseOrderNew(requirementManageDTOS,from,to);
//        return iRequirementHeadService.createPurchaseOrderNew(requirementManageDTOS);
    }

    @PostMapping("/customPricingTime")
    public boolean customPricingTime(@RequestBody List<RequirementManageDTO> requirementManageDTOS) {
        List<DictItem> category = baseClient.listDictItemByDictCode("CUSTOM_PRICING_TIME_CATEGORY");
        Set<String> set = category.stream().map(DictItem::getDictItemCode).collect(Collectors.toSet());
        return requirementManageDTOS.stream().anyMatch(e -> set.contains(e.getCategoryCode()));
    }

    /**
     * 提交需求行，生成采购订单
     *
     * @param requirementManageDTOS
     * @return
     */
    @PostMapping("/submitPurchaseOrder")
    public void submitPurchaseOrder(@RequestBody List<RequirementManageDTO> requirementManageDTOS) {
//        iRequirementHeadService.submitPurchaseOrder(requirementManageDTOS);
        iRequirementHeadService.submitPurchaseOrderNew(requirementManageDTOS);
    }

    @PostMapping("/submitPurchaseOrderWithType")
    public void submitPurchaseOrder(@RequestBody Map<String, Object> paramMap) {
        List<RequirementManageDTO> requirementManageDTOS = JSON.parseArray(paramMap.get("list").toString(), RequirementManageDTO.class);
        String isElectric = paramMap.get("electric").toString();
//        iRequirementHeadService.submitPurchaseOrder(requirementManageDTOS);
        //设置回原来的业务实体和库存组织
        Set<Long> lineIds = requirementManageDTOS.stream().map(RequirementManageDTO::getRequirementLineId).collect(Collectors.toSet());
        Map<Long, RequirementLine> temp = iRequirementLineService.listByIds(lineIds).stream().collect(Collectors.toMap(RequirementLine::getRequirementLineId, Function.identity()));
        for (RequirementManageDTO e : requirementManageDTOS) {
            RequirementLine requirementLine = temp.get(e.getRequirementLineId());
            //库存组织不相等且非电站业务报错
            if (!Objects.equals(e.getOrganizationCode(), requirementLine.getOrganizationCode()) && !Objects.equals(isElectric, "Y")) {
                throw new BaseException("非电站业务要求库存组织一致");
            }
            e.setOrgName(requirementLine.getOrgName())
                    .setOrgId(requirementLine.getOrgId())
                    .setOrgCode(requirementLine.getOrgCode())
                    .setOrganizationId(requirementLine.getOrganizationId())
                    .setOrganizationName(requirementLine.getOrganizationName())
                    .setOrganizationCode(requirementLine.getOrganizationCode());
        }
        //2020-12-26 隆基回迁产品 添加校验：如果供应商不为审批通过状态，则提示错误。
        //通过供应商编码，校验供应商是否为【审批通过状态】
//        List<String> vendorCodeParams = requirementManageDTOS.stream().map(item -> item.getVendorCode()).collect(Collectors.toList());
//        List<CompanyInfo> companyInfoList = supplierClient.listCompanyByCodes(vendorCodeParams);
//        List<CompanyInfo> unApprovedCompanyInfoList = companyInfoList.stream().filter(item -> !ApproveStatusType.APPROVED.getValue().equals(item.getStatus())).collect(Collectors.toList());
//        if(CollectionUtils.isNotEmpty(unApprovedCompanyInfoList) && unApprovedCompanyInfoList.size() > 0){
//            String unApprovedCompanyNames = unApprovedCompanyInfoList.stream().map(item -> item.getCompanyName()).collect(Collectors.joining(","));
//            throw new BaseException("存在供应商[" + unApprovedCompanyNames + "]未审批");
//        }

        iRequirementHeadService.submitPurchaseOrderNew(requirementManageDTOS);
    }

//    public static void main(String[] args) {
//        CompanyInfo c1 = new CompanyInfo();
//        c1.setCompanyId(123L);
//        c1.setCompanyName("A公司");
//        CompanyInfo c2 = new CompanyInfo();
//        c2.setCompanyId(124L);
//        c2.setCompanyName("B公司");
//        List<CompanyInfo> l = new ArrayList<>();
//        l.add(c1);
//        l.add(c2);
//        System.out.println(l.stream().map(item -> item.getCompanyName()).collect(Collectors.joining(",")));
//    }

    /**
     * 创建寻源单据
     *
     * @param requirementManageDTOS
     */
    @PostMapping("/createSourceForm")
    public RequirementLineVO createSourceForm(@RequestBody List<RequirementManageDTO> requirementManageDTOS, @RequestParam int sourcingType) {
        return iRequirementHeadService.createSourceForm(requirementManageDTOS, sourcingType);
    }

//    /**
//     * Description 采购需求合并
//     * @Param
//     * @return
//     * @Author wuwl18@meicloud.com
//     * @Date 2020.05.17
//     * @throws
//     **/
//    @PostMapping("/bachRequirementMerge")
//    @PreAuthorize("hasAuthority('pr:requirementManage:merge')")
//    public BaseResult<String> bachRequirementMerge(Long[] requirementLineIds){
//        BaseResult<String> result = new BaseResult<>();
//        try{
//            result = iRequirementLineService.bachRequirementMerge(requirementLineIds);
//        }catch (BaseException e){
//            result.setCode(ResultCode.OPERATION_FAILED.getCode());
//            result.setMessage(ResultCode.OPERATION_FAILED.getMessage());
//        }
//        return result;
//    }

    /**
     * 查询需求池导出总条数
     *
     * @param requirementManageDTO
     * @return
     */
    @PostMapping("/getExportNum")
    public Long getExportNum(@RequestBody RequirementManageDTO requirementManageDTO) {
        return iRequirementHeadService.getExportNum(requirementManageDTO);
    }

    /**
     * 需求池分配转办
     *
     * @param requirementManageDTOS
     * @return
     */
    @PostMapping("/findCategorys")
    public PageInfo<DivisionCategory> findCategorys(@RequestBody List<RequirementManageDTO> requirementManageDTOS, @RequestParam("duty") String duty) {

        return iRequirementHeadService.findCategorys(requirementManageDTOS, duty);
    }
}
