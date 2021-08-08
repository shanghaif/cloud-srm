package com.midea.cloud.srm.sup.change.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.UserType;
import com.midea.cloud.common.enums.sup.InfoChangeStatus;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.common.FormResultDTO;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.supplier.change.dto.ChangeInfoDTO;
import com.midea.cloud.srm.model.supplier.change.dto.ChangeRequestDTO;
import com.midea.cloud.srm.model.supplier.change.dto.InfoChangeDTO;
import com.midea.cloud.srm.model.supplier.change.entity.InfoChange;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import com.midea.cloud.srm.sup.change.service.IBankInfoChangeService;
import com.midea.cloud.srm.sup.change.service.IContactInfoChangeService;
import com.midea.cloud.srm.sup.change.service.IInfoChangeService;
import com.midea.cloud.srm.sup.change.service.ISiteInfoChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * <pre>
 *  公司信息变更表 前端控制器
 * </pre>
 *
 * @author chensl26@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-30 19:57:36
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/change/infoChange")
public class InfoChangeController extends BaseController {

    @Autowired
    private IInfoChangeService iInfoChangeService;

    @Autowired
    private IBankInfoChangeService iBankInfoChangeService;

    @Autowired
    private IContactInfoChangeService iContactInfoChangeService;

    @Autowired
    private ISiteInfoChangeService iSiteInfoChangeService;

    /**
     * 获取
     *
     * @param id
     */
    @GetMapping("/get")
    public InfoChange get(@RequestParam("id") Long id) {
        Assert.notNull(id, "id不能为空");
        return iInfoChangeService.getById(id);
    }

    /**
     * 新增
     *
     * @param infoChange
     */
    @PostMapping("/add")
    public InfoChange add(@RequestBody InfoChange infoChange) {

        return iInfoChangeService.addInfoChange(infoChange);
    }

    /**
     * 删除
     *
     * @param id
     */
    @GetMapping("/delete")
    public void delete(@RequestParam("id") Long id) {
        Assert.notNull(id, "id不能为空");
        iInfoChangeService.removeChangeById(id);
    }

    /**
     * 修改
     *
     * @param infoChange
     */
    @PostMapping("/modify")
    public InfoChange modify(@RequestBody InfoChange infoChange) {
        return iInfoChangeService.updateInfoChange(infoChange);
    }

    /**
     * 分页查询
     *
     * @param infoChange
     * @return
     */
    @PostMapping("/listPage")
    public PageInfo<InfoChange> listPage(@RequestBody InfoChange infoChange) {
        PageUtil.startPage(infoChange.getPageNum(), infoChange.getPageSize());
        QueryWrapper<InfoChange> wrapper = new QueryWrapper<InfoChange>(infoChange);
        return new PageInfo<InfoChange>(iInfoChangeService.list(wrapper));
    }

    /**
     * 查询所有
     *
     * @return
     */
    @PostMapping("/listAll")
    public List<InfoChange> listAll() {
        return iInfoChangeService.list();
    }

    /**
     * 分页条件查询
     *
     * @param changeRequestDTO
     * @return
     * @modifiedBy xiexh12@meicloud.com
     */
    @PostMapping("/listPageByParam")
    public PageInfo<InfoChangeDTO> listPageByParam(@RequestBody ChangeRequestDTO changeRequestDTO) {
        return iInfoChangeService.listPageByParamPage(changeRequestDTO);
    }

    /**
     * 暂存/保存
     * @param changeInfo
     * @return
     */
    @PostMapping("/saveTemporary")
    public FormResultDTO saveOrUpdateChange(@RequestBody ChangeInfoDTO changeInfo) {
        iInfoChangeService.commonCheck(changeInfo, InfoChangeStatus.DRAFT.getValue());
        return iInfoChangeService.saveOrUpdateChange(changeInfo, InfoChangeStatus.DRAFT.getValue());
    }

    /**
     * 提交
     *
     * @param changeInfo
     */
    @PostMapping("/submitted")
    public FormResultDTO submitted(@RequestBody ChangeInfoDTO changeInfo) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        String userType = loginAppUser.getUserType();
        // 供应商提交
        if (UserType.VENDOR.name().equals(userType)) {
            iInfoChangeService.commonCheck(changeInfo, InfoChangeStatus.VENDOR_SUBMITTED.getValue());
            return iInfoChangeService.saveOrUpdateChange(changeInfo, InfoChangeStatus.VENDOR_SUBMITTED.getValue());
        }
        // 采购商提交
        else {
            iInfoChangeService.commonCheck(changeInfo, InfoChangeStatus.SUBMITTED.getValue());
            return iInfoChangeService.saveOrUpdateChange(changeInfo, InfoChangeStatus.SUBMITTED.getValue());
        }
    }

    /**
     * 已审批
     *
     * @param changeInfo
     */
    @PostMapping("/approved")
    public void approved(@RequestBody ChangeInfoDTO changeInfo) {
        iInfoChangeService.commonCheck(changeInfo, InfoChangeStatus.APPROVED.getValue());
        iInfoChangeService.updateChange(changeInfo, InfoChangeStatus.APPROVED.getValue());
    }

    /**
     * 审批（测试用，目前在用）
     * @param changeId
     */
    @GetMapping("/approve")
    public void approve(@RequestParam("changeId") Long changeId) {
        ChangeInfoDTO changeInfo = iInfoChangeService.getInfoByChangeId(changeId);
        iInfoChangeService.commonCheck(changeInfo, InfoChangeStatus.APPROVED.getValue());
        iInfoChangeService.saveOrUpdateChange(changeInfo, InfoChangeStatus.APPROVED.getValue());
    }

    /**
     * 已驳回
     *
     * @param changeInfo
     */
    @PostMapping("/rejected")
    public void rejected(@RequestBody ChangeInfoDTO changeInfo) {
        iInfoChangeService.commonCheck(changeInfo, InfoChangeStatus.APPROVED.getValue());
        iInfoChangeService.updateChange(changeInfo, InfoChangeStatus.REJECTED.getValue());
    }

    /**
     * 查询变更详情
     * @param changeId
     * @return
     */
    @GetMapping("/getInfoByChangeId")
    public ChangeInfoDTO getInfoByChangeId(@RequestParam("changeId") Long changeId) {
        return iInfoChangeService.getInfoByChangeId(changeId);
    }

    /**
     * 删除
     *
     * @param changeId
     */
    @GetMapping("/deleteChangeInfo")
    public void deleteChangeInfo(@RequestParam("changeId") Long changeId) {
        Assert.notNull(changeId, "changeId不能为空");
        iInfoChangeService.deleteChangeInfo(changeId);
    }


    /**
     * 废弃订单
     * @param changeId
     */
    @GetMapping("/abandon")
    public void abandon(Long changeId) {
        Assert.notNull(changeId,"废弃订单id不能为空");
        iInfoChangeService.abandon(changeId);
    }

    /**
     * 提交
     * @param changeInfo
     * @return
     */
    @PostMapping("/submitWithFlow")
    public Map<String, Object> submitWithFlow(@RequestBody ChangeInfoDTO changeInfo) {
        iInfoChangeService.commonCheck(changeInfo, InfoChangeStatus.SUBMITTED.getValue());
        return iInfoChangeService.saveChangeWithFlow(changeInfo);
    }

    /**
     * 查询目前处于已审批的供应商
     */
    @PostMapping("/getVendors")
    public List<CompanyInfo> getVendors(){
        return iInfoChangeService.getVendors();
    }

    /**
     * 删除供应商银行信息
     */
    @GetMapping("/deleteBankChange")
    public void deleteBankChange(@RequestParam("bankChangeId") Long bankChangeId){
        iBankInfoChangeService.removeByBankChangeId(bankChangeId);
    }

    /**
     * 删除供应商联系人信息
     */
    @GetMapping("/deleteContactChange")
    public void deleteContactChange(@RequestParam("contactChangeId") Long contactChangeId){
        iContactInfoChangeService.removeByContactChangeId(contactChangeId);
    }

    /**
     * 删除供应商地点信息
     */
    @GetMapping("/deleteSiteChange")
    public void deleteSiteChange(@RequestParam("siteChangeId") Long siteChangeId){
        iSiteInfoChangeService.removeBySiteChangeId(siteChangeId);
    }

    /**
     * 供应商登录,判断该供应商能否进行信息变更
     * 黑名单不能进行信息变更,有拟定 已提交 撤回 驳回的不能进行信息变更
     */
    @GetMapping("/ifAddInfoChange")
    public InfoChangeDTO ifAddInfoChange(@RequestParam("companyId") Long companyId){
        Assert.notNull(companyId, "当前登录供应商为空.");
        return iInfoChangeService.ifAddInfoChange(companyId);
    }

    /**
     * 采购商驳回供应商已提交的单据
     * 供方已提交 -> 拟定 双方可继续编辑
     */
    @GetMapping("/buyerReject")
    public void buyerReject(@RequestParam("changeId") Long changeId) {
        Optional.ofNullable(changeId).orElseThrow( () -> new BaseException(LocaleHandler.getLocaleMsg("当前变更单据为空!")));
        iInfoChangeService.buyerReject(changeId);
    }

}
