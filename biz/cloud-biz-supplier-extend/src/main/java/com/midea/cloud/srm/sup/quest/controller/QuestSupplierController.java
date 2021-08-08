package com.midea.cloud.srm.sup.quest.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.UserType;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.component.context.container.SpringContextHolder;
import com.midea.cloud.srm.feign.rbac.RbacClient;
import com.midea.cloud.srm.feign.workflow.FlowBusinessCallbackClient;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.rbac.permission.entity.Permission;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.supplier.quest.dto.QuestSupplierDto;
import com.midea.cloud.srm.model.supplier.quest.entity.QuestSupplier;
import com.midea.cloud.srm.model.supplier.quest.enums.QuestSupplierApproveStatusEnum;
import com.midea.cloud.srm.model.supplier.quest.vo.QuestSupplierVo;
import com.midea.cloud.srm.model.workflow.service.IFlowBusinessCallbackService;
import com.midea.cloud.srm.sup.quest.service.IQuestSupplierService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;


/**
* <pre>
 *  问卷调查 前端控制器
 * </pre>
*
* @author bing5.wang@midea.com
* @version 1.00.00
*
* <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Apr 16, 2021 5:33:46 PM
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/quest/questSupplier")
@Slf4j
public class QuestSupplierController extends BaseController implements FlowBusinessCallbackClient {

    @Autowired
    private IQuestSupplierService questSupplierService;
    @Autowired
    private RbacClient rbacClient;
    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public QuestSupplier get(Long id) {
        Assert.notNull(id, "id不能为空");
        return questSupplierService.getById(id);
    }

    /**
    * 新增或修改
    * @param
    */
    @PostMapping("/saveOrUpdateQuestSupplierForm")
    public void saveOrUpdateQuestSupplierForm(@RequestBody QuestSupplierDto questSupplierDto) {
        questSupplierService.saveOrUpdateQuestSupplierForm(questSupplierDto);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/deleteById")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        QuestSupplier questSupplier = questSupplierService.getById(id);
        if (!QuestSupplierApproveStatusEnum.DRAFT.getValue().equals(questSupplier.getApprovalStatus())) {
            throw new BaseException("只能删除拟定状态单据！");

        } else {
            questSupplierService.removeById(id);
        }
    }

    /**
    * 分页查询
    * @param
    * @return
    */
    @PostMapping("/listPageByParm")
    public PageInfo<QuestSupplierVo> listPageByParm(@RequestBody QuestSupplierDto questSupplierDto) {
        return questSupplierService.listPageByParm(questSupplierDto);
    }

    @GetMapping("/flow/getOpenWorkFlow")
    public boolean getOpenWorkFlow(Long menuId) {
        Boolean flag = false;
        if (menuId != null) {
            //判断是否启用工作流
            Permission menu = rbacClient.getMenu(menuId);
            if (menu != null) {
                flag = menu.getEnableWorkFlow().equals("Y");
            }
        }
        return flag;
    }
    /**
     * 供应商问卷调查审批通过
     *
     * @param questSupplier
     */
    @PostMapping("/flow/questSupplierPass")
    public void questSupplierPass(@RequestBody QuestSupplier questSupplier) {
        questSupplierService.pass(questSupplier);
    }
    /**
     * 供应商问卷调查驳回
     *
     * @param questSupplier
     */
    @PostMapping("/flow/updateQuestSupplierApprovalStatus")
    public void updateQuestSupplierApprovalStatus(@RequestBody QuestSupplier questSupplier) {
        QuestSupplier questSupplierTemp = questSupplierService.getById(questSupplier.getQuestSupId());
        questSupplierService.updateById(questSupplierTemp.setApprovalStatus(questSupplier.getApprovalStatus()));
    }
    /**
     * 根据ID获取供应商问卷调查单
     *
     * @param questSupIdForQuery
     * @return
     */
    @GetMapping("/flow/getQuestSupplierVo")
    public QuestSupplierVo getQuestSupplierVo(Long questSupIdForQuery) {
        Assert.notNull(questSupIdForQuery, "questSupIdForQuery不能为空");
        return questSupplierService.getQuestSupplierVo(questSupIdForQuery);
    }

    @Override
    public String callbackFlow(String serviceBean, String callbackMethod, Long businessId, String param) throws Exception {
        IFlowBusinessCallbackService iFlowBusinessCallbackService =null;

        Class clazz=Class.forName(serviceBean);
        Object bean = SpringContextHolder.getApplicationContext().getBean(clazz);
        iFlowBusinessCallbackService =(IFlowBusinessCallbackService)bean;
        log.error("method: {}, businessId:{}, param:{}", callbackMethod, businessId, param);
        //SpringContextHolder.getBean(name);
        if( "submitFlow".equals(callbackMethod) ) {
            iFlowBusinessCallbackService.submitFlow(businessId, param);
            return null;
        }
        if( "passFlow".equals(callbackMethod) ) {
            iFlowBusinessCallbackService.passFlow(businessId, param);
            return null;
        }
        if( "rejectFlow".equals(callbackMethod) ) {
            iFlowBusinessCallbackService.rejectFlow(businessId, param);
            return null;
        }
        if( "withdrawFlow".equals(callbackMethod) ) {
            iFlowBusinessCallbackService.withdrawFlow(businessId, param);
            return null;
        }
        if( "destoryFlow".equals(callbackMethod) ) {
            iFlowBusinessCallbackService.destoryFlow(businessId, param);
            return null;
        }
        if( "getVariableFlow".equals(callbackMethod) ) {
            return iFlowBusinessCallbackService.getVariableFlow(businessId, param);
        }
        if( "getDataPushFlow".equals(callbackMethod) ) {
            return iFlowBusinessCallbackService.getDataPushFlow(businessId, param);
        }
        return null;
    }
}
