package com.midea.cloud.srm.pr.requirement.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.pm.pr.requirement.dto.RequirementManageDTO;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.RequirementLine;
import com.midea.cloud.srm.model.pm.pr.requirement.vo.RequirementLineVO;
import com.midea.cloud.srm.pr.requirement.service.IRequirementHeadService;
import com.midea.cloud.srm.pr.requirement.service.IRequirementLineService;
import com.midea.cloud.srm.ps.http.fssc.service.IFSSCReqService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

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
     * 创建采购订单 (转换采购订单)
     *
     * @param requirementManageDTOS
     * @return
     */
    @PostMapping("/createPurchaseOrder")
    public Collection<RequirementManageDTO> createPurchaseOrder(@RequestBody List<RequirementManageDTO> requirementManageDTOS) {

            return iRequirementHeadService.createPurchaseOrderNew(requirementManageDTOS);

//        return iRequirementHeadService.createPurchaseOrder(requirementManageDTOS);
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
}
