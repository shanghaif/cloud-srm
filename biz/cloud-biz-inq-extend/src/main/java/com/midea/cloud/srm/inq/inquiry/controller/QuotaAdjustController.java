package com.midea.cloud.srm.inq.inquiry.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.inq.AdjustStatus;
import com.midea.cloud.common.enums.inq.QuotaStatus;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.inq.inquiry.service.IQuotaAdjustService;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.inq.inquiry.dto.QuotaAdjustDTO;
import com.midea.cloud.srm.model.inq.inquiry.entity.Quota;
import com.midea.cloud.srm.model.inq.inquiry.entity.QuotaAdjust;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <pre>
 *  配额调整 前端控制器
 * </pre>
 *
 * @author zhi1772778785@163.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-08 14:01:49
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/inquiry/quotaAdjust")
public class QuotaAdjustController extends BaseController {

    @Autowired
    private IQuotaAdjustService iQuotaAdjustService;

    /**
     * 获取
     *
     * @param id
     */
    @GetMapping("/getQuotaAdjust")
    public QuotaAdjustDTO getQuotaAdjust(Long id) {
        Assert.notNull(id, "id不能为空");
        return iQuotaAdjustService.getQuotaAdjust(id);
    }

    /**
     * 新增或暂存
     *
     * @param quotaAdjust
     */
    @PostMapping("/quotaAdjustAdd")
    public void add(@RequestBody QuotaAdjustDTO quotaAdjust) {
        iQuotaAdjustService.quotaAdjustAdd(quotaAdjust);
    }

    /**
     * 删除
     *
     * @param id
     */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iQuotaAdjustService.deleteQuotaAdjust(id);
    }

    /**
     * 修改
     *
     * @param quotaAdjust
     */
    @PostMapping("/modify")
    public void modify(@RequestBody QuotaAdjust quotaAdjust) {
        iQuotaAdjustService.updateById(quotaAdjust);
    }

    /**
     * 分页查询
     *
     * @param quotaAdjustDTO
     * @return
     */
    @PostMapping("/quotaAdjustListPage")
    public PageInfo<QuotaAdjust> quotaAdjustListPage(@RequestBody QuotaAdjustDTO quotaAdjustDTO) {
        PageUtil.startPage(quotaAdjustDTO.getPageNum(), quotaAdjustDTO.getPageSize());
        return new PageInfo<QuotaAdjust>(iQuotaAdjustService.quotaAdjustList(quotaAdjustDTO));
    }

    /**
     * 查询所有
     *
     * @return
     */
    @PostMapping("/listAll")
    public List<QuotaAdjust> listAll() {
        return iQuotaAdjustService.list();
    }
    /**
     * 提交审批
     *
     * @param id
     */
    @GetMapping("/submitQuotaAdjust")
    public void submitQuotaAdjust(Long id) {
        Assert.notNull(id, "id不能为空");
        UpdateWrapper<QuotaAdjust> wrapper = new UpdateWrapper<>();
        wrapper.set("STATUS", AdjustStatus.get("SUBMITTED"));
        wrapper.eq("QUOTA_ADJUST_ID",id);
        iQuotaAdjustService.update(wrapper);
    }
    /**
     * 审批通过
     *
     * @param id
     */
    @GetMapping("/getApprove")
    public void getApprove(Long id) {
        Assert.notNull(id, "id不能为空");
        iQuotaAdjustService.getApprove(id);
    }
    /**
     * 审批驳回
     *
     * @param id
     */
    @GetMapping("/getReject")
    public void getReject(Long id) {
        Assert.notNull(id, "id不能为空");
        UpdateWrapper<QuotaAdjust> wrapper = new UpdateWrapper<>();
        wrapper.set("STATUS", AdjustStatus.get("REJECTED"));
        wrapper.eq("QUOTA_ADJUST_ID",id);
        iQuotaAdjustService.update(wrapper);
    }
}
