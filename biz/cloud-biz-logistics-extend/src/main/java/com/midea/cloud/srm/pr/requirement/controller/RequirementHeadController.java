package com.midea.cloud.srm.pr.requirement.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.constants.BaseConst;
import com.midea.cloud.common.enums.pm.pr.requirement.RequirementApproveStatus;
import com.midea.cloud.common.enums.pm.ps.FSSCResponseCode;
import com.midea.cloud.common.enums.pm.ps.FsscResponseType;
import com.midea.cloud.common.enums.rbac.RoleFuncSetTypeEnum;
import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.common.utils.StringUtil;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.rbac.RbacClient;
import com.midea.cloud.srm.model.base.organization.entity.Organization;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.pm.ps.http.FSSCResult;
import com.midea.cloud.srm.model.pm.pr.requirement.dto.PurchaseRequirementDTO;
import com.midea.cloud.srm.model.pm.pr.requirement.dto.RequirementHeadQueryDTO;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.RequirementHead;
import com.midea.cloud.srm.model.pm.pr.requirement.vo.RequirementApplyRejectVO;
import com.midea.cloud.srm.model.rbac.role.entity.Role;
import com.midea.cloud.srm.model.rbac.role.entity.RoleFuncSet;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.pr.requirement.service.IRequirementHeadService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

/**
 * <pre>
 *  采购需求头表 前端控制器
 * </pre>
 *
 * @author fengdc3@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-12 18:46:40
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/pr/requirementHead")
public class RequirementHeadController extends BaseController {

    @Autowired
    private IRequirementHeadService iRequirementHeadService;

    @Resource
    private RbacClient rbacClient;

    @Resource
    private BaseClient baseClient;


    /**
     * 获取详情
     *
     * @param requirementHeadId
     */
    @GetMapping("/getByHeadId")
    public PurchaseRequirementDTO getByHeadId(Long requirementHeadId) {
        return iRequirementHeadService.getByHeadId(requirementHeadId);
    }

    /**
     * 新增
     *
     * @param purchaseRequirementDTO
     */
//    @PreAuthorize("hasAuthority('pr:requirementApply:add')")
    @PostMapping("/addPurchaseRequirement")
    public Long addPurchaseRequirement(@RequestBody PurchaseRequirementDTO purchaseRequirementDTO) {
        return iRequirementHeadService.addPurchaseRequirement(purchaseRequirementDTO, RequirementApproveStatus.DRAFT.getValue());
    }

    /**
     * 删除
     *
     * @param requirementHeadId
     */
//    @PreAuthorize("hasAuthority('pr:requirementApply:delete')")
    @GetMapping("/deleteByHeadId")
    public void deleteByHeadId(Long requirementHeadId) {
        iRequirementHeadService.deleteByHeadId(requirementHeadId);
    }

    /**
     * 修改
     *
     * @param purchaseRequirementDTO
     */
//    @PreAuthorize("hasAuthority('pr:requirementApply:edit')")
    @PostMapping("/modifyPurchaseRequirement")
    public Long modifyPurchaseRequirement(@RequestBody PurchaseRequirementDTO purchaseRequirementDTO) {
        return iRequirementHeadService.modifyPurchaseRequirement(purchaseRequirementDTO);
    }

    /**
     * Description 跟进当前用户和菜单code获取对应的权限Map(key为allQuery表示所有询条件，buQuery-事业部ID查询,ouQuery-业务实体ID查询
     *              invQuery-库存组织ID)
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.10.25
     * @throws
     **/
    public Map<String, Object> getRoleFunction(LoginAppUser user, String functionCode){
        Map<String, Object> queryMap = new HashMap<>();
        if(null != user){
            List<Role> roleList = user.getRolePermissions();
            StringBuilder roleStr = new StringBuilder("");
            roleList.forEach(role -> {roleStr.append(role.getRoleCode()).append(",");});
            //根据菜单Code获取角色-菜单配置集合
            List<RoleFuncSet> roleFuncSetList= rbacClient.findRoleFuncSetByParam(new RoleFuncSet()
                    .setFunctionCode(functionCode)
                    .setRoleCode(roleStr.toString()));

            //获取所有的组织关系-人员表
            List<Organization> queryOrgList = baseClient.listAllOrganization();
            //OU-按业务实体,BU-按事业部,INVENTORY-按库存组织,CATEGORY-按品类分工,OU_CATEGORY-按OU+品类,ALL-全部
            StringBuilder allQuery = new StringBuilder(); //根据所有询条件
            StringBuilder buQuery = new StringBuilder(); //事业部ID查询条件
            StringBuilder ouQuery = new StringBuilder(); //业务实体ID查询条件
            StringBuilder invQuery = new StringBuilder(); //库存组织ID查询条件
            roleFuncSetList.forEach(roleFuncSet -> {
                if(null != roleFuncSet) {
                    String roleFuncSetType = roleFuncSet.getRoleFuncSetType();
                    if (RoleFuncSetTypeEnum.ALL.getKey().equals(roleFuncSetType)) { //所有
                        allQuery.append("");
                    } else if (RoleFuncSetTypeEnum.OU.getKey().equals(roleFuncSetType) && CollectionUtils.isNotEmpty(user.getOrganizationUsers())) { //事业部
                        user.getOrganizationUsers().forEach(orgUser ->{
                            if(null != orgUser ){
                                queryOrgList.forEach(queryOrg ->{
                                    if(orgUser.getOrganizationId().compareTo(queryOrg.getOrganizationId()) == 0
                                            && BaseConst.ORG_BU.equals(queryOrg.getOrganizationTypeCode())){
                                        buQuery.append(queryOrg.getDivisionId());
                                        return;
                                    }
                                });
                            }
                        });
                    } else if (RoleFuncSetTypeEnum.OU.getKey().equals(roleFuncSetType) && CollectionUtils.isNotEmpty(user.getOrganizationUsers())) { //业务实体
                        user.getOrganizationUsers().forEach(orgUser ->{
                            if(null != orgUser ){
                                queryOrgList.forEach(queryOrg ->{
                                    if(orgUser.getOrganizationId().compareTo(queryOrg.getOrganizationId()) == 0
                                        && BaseConst.ORG_OU.equals(queryOrg.getOrganizationTypeCode())){
                                        ouQuery.append(queryOrg.getOrganizationId());
                                        return;
                                    }
                                });
                            }
                        });
                    }else if (RoleFuncSetTypeEnum.INVENTORY.getKey().equals(roleFuncSetType) && CollectionUtils.isNotEmpty(user.getOrganizationUsers())) { //库存组织
                        user.getOrganizationUsers().forEach(orgUser ->{
                            if(null != orgUser ){
                                queryOrgList.forEach(queryOrg ->{
                                    if(orgUser.getOrganizationId().compareTo(queryOrg.getOrganizationId()) == 0
                                            && BaseConst.ORG_INV.equals(queryOrg.getOrganizationTypeCode())){
                                        invQuery.append(queryOrg.getOrganizationId());
                                        return;
                                    }
                                });
                            }
                        });
                    }
                }
            });
            queryMap.put("allQuery", allQuery);
            queryMap.put("buQuery", buQuery);
            queryMap.put("invQuery", invQuery);
            queryMap.put("ouQuery", ouQuery);
        }
        return queryMap;
    }

    /**
     * 申请/审批页-分页查询
     *
     * @param requirementHeadQueryDTO
     * @return
     */
    @PostMapping("/listPage")
    public PageInfo<RequirementHead> listPage(@RequestBody RequirementHeadQueryDTO requirementHeadQueryDTO) {
        return iRequirementHeadService.listPage(requirementHeadQueryDTO);
    }

    /**
     * 提交审批(longi)
     *
     * @param purchaseRequirementDTO
     */
//    @PreAuthorize("hasAuthority('pr:requirementApply:submitAudit')")
    @PostMapping("/submitApproval")
    public BaseResult submitApproval(@RequestBody PurchaseRequirementDTO purchaseRequirementDTO) {
        FSSCResult fsscResult = iRequirementHeadService.submitApproval(purchaseRequirementDTO, RequirementApproveStatus.SUBMITTED.getValue());
        BaseResult baseResult = BaseResult.buildSuccess();
        if (FSSCResponseCode.WARN.getCode().equals(fsscResult.getCode())) {
            baseResult.setCode(fsscResult.getCode());
            baseResult.setMessage(fsscResult.getMsg());
            baseResult.setData(fsscResult.getType());
        }
        return baseResult;
    }

    /**
     * 通过审批(备用)
     *
     * @param requirementHeadId
     */
//    @PreAuthorize("hasAuthority('pr:requirementApply:confirm')")
    @GetMapping("/approval")
    public void approval(Long requirementHeadId) {
        iRequirementHeadService.updateApproved(requirementHeadId, RequirementApproveStatus.APPROVED.getValue());
    }

    /**
     * 废弃(备用)
     *
     * @param requirementHeadId
     */
//    @PreAuthorize("hasAuthority('pr:requirementApply:cancel')")
    @GetMapping("/abandon")
    public void abandon(Long requirementHeadId) {
        iRequirementHeadService.abandon(requirementHeadId);
    }

    /**
     * 获取采购申请头
     *
     * @param requirementHeadQueryDTO
     * @return
     */
    @GetMapping("/getRequirementHeadByParam")
    public RequirementHead getRequirementHeadByParam(@RequestBody RequirementHeadQueryDTO requirementHeadQueryDTO) {
        return iRequirementHeadService.getRequirementHeadByParam(requirementHeadQueryDTO);
    }
    /**
     * 驳回(备用)
     * @param requirementApplyRejectVO
     */
//    @PreAuthorize("hasAuthority('pr:requirementApply:refuse')")
    @PostMapping("/reject")
    public void reject(@RequestBody RequirementApplyRejectVO requirementApplyRejectVO) {
        iRequirementHeadService.reject(requirementApplyRejectVO.getRequirementHeadId(),
                requirementApplyRejectVO.getRejectReason());
    }

    /**
     * <pre>
     *  审批撤回
     * </pre>
     *
     * @author chenwt24@meicloud.com
     * @version 1.00.00
     *
     * <pre>
     *  修改记录
     *  修改后版本:
     *  修改人:
     *  修改日期: 2020-10-10
     *  修改内容:
     * </pre>
     */
    @PostMapping("/withdraw")
    public void withdraw(@RequestBody RequirementApplyRejectVO requirementApplyRejectVO) {
        iRequirementHeadService.withdraw(requirementApplyRejectVO.getRequirementHeadId(),
                requirementApplyRejectVO.getRejectReason());
    }

    /**
     * 根据采购申请筛选采购申请
     *
     * @param requirementHead
     * @return
     */
    @PostMapping("/getRequirementHeadByParam")
    public RequirementHead queryByRequirementHead(@RequestBody RequirementHead requirementHead) {
        RequirementHead requirementHead1 = null;
        if (StringUtil.notEmpty(requirementHead.getRequirementHeadNum())) {
            List<RequirementHead> requirementHeads = iRequirementHeadService.list(new QueryWrapper<>(requirementHead));
            if (CollectionUtils.isNotEmpty(requirementHeads)) {
                requirementHead1 = requirementHeads.get(0);
            }
        }
        return requirementHead1;
    }

    @PostMapping("/getRequirementHeadByNumber")
    public List<RequirementHead> getRequirementHeadByNumber(@RequestBody Collection<String> number) {
        if(CollectionUtils.isEmpty(number)){
            return Collections.emptyList();
        }
        return iRequirementHeadService.list(Wrappers.lambdaQuery(RequirementHead.class)
                .select(RequirementHead::getCeeaProjectNum, RequirementHead::getCeeaProjectName)
                .in(RequirementHead::getRequirementHeadNum, number)
        );
    }

}
