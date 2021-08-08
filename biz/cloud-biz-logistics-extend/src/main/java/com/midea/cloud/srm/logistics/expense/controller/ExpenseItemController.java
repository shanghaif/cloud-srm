package com.midea.cloud.srm.logistics.expense.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.logistics.LogisticsStatus;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.logistics.expense.service.IExpenseItemService;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.logistics.expense.dto.ChargeCodeDto;
import com.midea.cloud.srm.model.logistics.expense.entity.ExpenseItem;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * <pre>
 *  费用项定义表 前端控制器
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-11-26 13:33:44
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/logistics/expense-item")
public class ExpenseItemController extends BaseController {

    @Autowired
    private IExpenseItemService iExpenseItemService;

    /**
     * 获取
     *
     * @param id
     */
    @GetMapping("/get")
    public ExpenseItem get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iExpenseItemService.getById(id);
    }

    /**
     * 新增
     *
     * @param expenseItem
     */
    @PostMapping("/add")
    public void add(@RequestBody ExpenseItem expenseItem) {
        Long id = IdGenrator.generate();
        expenseItem.setExpenseItemId(id);
        iExpenseItemService.save(expenseItem);
    }

    /**
     * 修改
     *
     * @param expenseItem
     */
    @PostMapping("/modify")
    public void modify(@RequestBody ExpenseItem expenseItem) {
        iExpenseItemService.updateById(expenseItem);
    }

    /**
     * 分页条件查询
     *
     * @param expenseItem
     * @return
     */
    @PostMapping("/listPageByParam")
    public PageInfo<ExpenseItem> listPage(@RequestBody ExpenseItem expenseItem) {
        PageUtil.startPage(expenseItem.getPageNum(), expenseItem.getPageSize());
        return new PageInfo<ExpenseItem>(iExpenseItemService.listByParam(expenseItem));
    }

    /**
     * 保存费用项(单条保存)
     *
     * @param expenseItem
     */
    @PostMapping("/saveExpenseItem")
    public void saveExpenseItem(@RequestBody ExpenseItem expenseItem) {
        Optional.ofNullable(expenseItem).orElseThrow(() -> new BaseException(LocaleHandler.getLocaleMsg("要保存的费用项为空.")));
        iExpenseItemService.checkNotEmptyBeforeSave(expenseItem);
        iExpenseItemService.saveExpenseItem(expenseItem);
    }

    /**
     * 保存费用项(批量保存)
     *
     * @param expenseItems
     */
    @PostMapping("/saveExpenseItems")
    public void saveExpenseItems(@RequestBody List<ExpenseItem> expenseItems) {
        Assert.isTrue(CollectionUtils.isNotEmpty(expenseItems), "要保存的费用项为空.");
        expenseItems.forEach(expenseItem -> {
            iExpenseItemService.checkNotEmptyBeforeSave(expenseItem);
        });
        iExpenseItemService.saveExpenseItems(expenseItems);
    }

    /**
     * 通过id删除(单条删除)
     * @param expenseItemId
     */
    @GetMapping("/deleteById")
    public void deleteById(@RequestParam("expenseItemId") Long expenseItemId) {
        checkExpenseItemId(expenseItemId);
        Assert.isTrue(Objects.equals(LogisticsStatus.DRAFT.getValue(), iExpenseItemService.getById(expenseItemId).getStatus()), "只有拟定状态才可以删除.");
        iExpenseItemService.removeById(expenseItemId);
    }

    /**
     * 通过ids删除(批量删除)
     *
     * @param expenseItemIds
     */
    @PostMapping("/deleteByIds")
    public void deleteByIds(@RequestBody List<Long> expenseItemIds) {
        checkExpenseItemIds(expenseItemIds);
        checkExpenseItemsByIdsBeforeDelete(expenseItemIds);
        iExpenseItemService.removeByIds(expenseItemIds);
    }

    /**
     * 费用项定义id判空
     * 以及根据费用项定义id是否在数据库能找到对应的费用项定义
     *
     * @param expenseItemId
     */
    public void checkExpenseItemId(@RequestParam("expenseItemId") Long expenseItemId) {
        Optional.ofNullable(expenseItemId).orElseThrow(()->new BaseException(LocaleHandler.getLocaleMsg("费用项定义id为空.")));
        Optional.ofNullable(iExpenseItemService.getById(expenseItemId)).orElseThrow(()->new BaseException(LocaleHandler.getLocaleMsg("无效的费用项定义id: "+expenseItemId+", 找不到相应的费用项定义.")));
    }

    /**
     * 费用项定义ids判空
     * 以及根据费用项定义id是否在数据库能找到对应的费用项定义
     *
     * @param expenseItemIds
     */
    public void checkExpenseItemIds(List<Long> expenseItemIds) {
        Assert.isTrue(CollectionUtils.isNotEmpty(expenseItemIds), "要批量操作的的费用项定义为空, 请选择要操作的费用项定义.");
        for (Long expenseItemId : expenseItemIds) {
            Optional.ofNullable(expenseItemId).orElseThrow(()->new BaseException(LocaleHandler.getLocaleMsg("批量操作的数据存在费用项定义id为空的数据.")));
            Optional.ofNullable(iExpenseItemService.getById(expenseItemId))
                    .orElseThrow(()->new BaseException(LocaleHandler.getLocaleMsg("无效的费用项定义id: "+expenseItemId+", 找不到相应的费用项定义.")));
        }
    }

    /**
     * 费用项定义批量删除前校验
     *
     * @param expenseItemIds
     */
    public void checkExpenseItemsByIdsBeforeDelete(List<Long> expenseItemIds) {
        for (Long expenseItemId : expenseItemIds) {
            iExpenseItemService.checkExpenseItemsByIdBeforeDelete(expenseItemId);
        }
    }

    /**
     * 费用项定义批量生效前校验
     *
     * @param expenseItemIds
     */
    public void checkExpenseItemsByIdsBeforeEffective(List<Long> expenseItemIds) {
        for (Long expenseItemId : expenseItemIds) {
            iExpenseItemService.checkExpenseItemsByIdsBeforeEffective(expenseItemId);
        }
    }

    /**
     * 费用项定义批量失效前校验
     *
     * @param expenseItemIds
     */
    public void checkExpenseItemsByIdsBeforeInEffective(List<Long> expenseItemIds) {
        for (Long expenseItemId : expenseItemIds) {
            iExpenseItemService.checkExpenseItemsByIdsBeforeInEffective(expenseItemId);
        }
    }

    /**
     * 费用项定义生效
     *
     * @param expenseItemId
     */
    @GetMapping("/effectiveExpenseItem")
    public void effectiveExpenseItem(@RequestParam("expenseItemId") Long expenseItemId) {
        checkExpenseItemId(expenseItemId);
        iExpenseItemService.updateExpenseItemStatus(expenseItemId, LogisticsStatus.EFFECTIVE.getValue());
    }

    /**
     * 费用项定义生效(批量)
     *
     * @param expenseItemIds
     */
    @PostMapping("/effectiveExpenseItems")
    public void effectiveExpenseItems(@RequestBody List<Long> expenseItemIds) {
        checkExpenseItemIds(expenseItemIds);
        // checkExpenseItemsByIdsBeforeEffective(expenseItemIds);
        iExpenseItemService.updateExpenseItemsStatus(expenseItemIds, LogisticsStatus.EFFECTIVE.getValue());
    }

    /**
     * 费用项定义失效
     *
     * @param expenseItemId
     */
    @GetMapping("/inEffectiveExpenseItem")
    public void inEffectiveExpenseItem(@RequestParam("expenseItemId") Long expenseItemId) {
        checkExpenseItemId(expenseItemId);
        Assert.isTrue(Objects.equals(LogisticsStatus.EFFECTIVE.getValue(), iExpenseItemService.getById(expenseItemId).getStatus()), "只有生效状态才可以失效.");
        iExpenseItemService.updateExpenseItemStatus(expenseItemId, LogisticsStatus.INEFFECTIVE.getValue());
    }

    /**
     * 费用项定义失效(批量)
     *
     * @param expenseItemIds
     */
    @PostMapping("/inEffectiveExpenseItems")
    public void InEffectiveExpenseItems(@RequestBody List<Long> expenseItemIds) {
        checkExpenseItemIds(expenseItemIds);
        checkExpenseItemsByIdsBeforeInEffective(expenseItemIds);
        iExpenseItemService.updateExpenseItemsStatus(expenseItemIds, LogisticsStatus.INEFFECTIVE.getValue());
    }

    /**
     * 查询所有
     *
     * @return
     */
    @PostMapping("/listAll")
    public List<ExpenseItem> listAll() {
        return iExpenseItemService.list();
    }

    /**
     * 下载费用项定义导入模板
     * @return
     */
    @PostMapping("/importExpenseItemModelDownload")
    public void importExpenseItemModelDownload(HttpServletResponse response) throws Exception {
        iExpenseItemService.importSupplierLeaderModelDownload(response);
    }

    /**
     * 导入数据
     * @param file
     */
    @PostMapping("/importExcel")
    public Map<String, Object> importExcel(@RequestParam("file") MultipartFile file, Fileupload fileupload) throws Exception {
        return iExpenseItemService.importExcel(file, fileupload);
    }

    /**
     * 通过业务模式，运输方式，LEG获取 前端下拉框项
     * @param expenseItem
     * @return
     */
    @PostMapping("/getSelectOptions")
    public List<ExpenseItem> getSelectOptions(@RequestBody ExpenseItem expenseItem){
        String businessModeCode = expenseItem.getBusinessModeCode();
        String transportModeCode = expenseItem.getTransportModeCode();
        String legCode = expenseItem.getLegCode();
        if(StringUtils.isBlank(businessModeCode)){
            throw new BaseException(LocaleHandler.getLocaleMsg("请输入业务模式编码"));
        }
        if(StringUtils.isBlank(transportModeCode)){
            throw new BaseException(LocaleHandler.getLocaleMsg("请输入运输方式编码"));
        }
        if(StringUtils.isBlank(legCode)){
            throw new BaseException(LocaleHandler.getLocaleMsg("请输入leg编码"));
        }
        return iExpenseItemService.list(new QueryWrapper<ExpenseItem>(
                new ExpenseItem().setBusinessModeCode(businessModeCode)
                    .setTransportModeCode(transportModeCode)
                    .setLegCode(legCode)
                )
        );

    }

    /**
     * 根据leg查找生效的有对应关系的费项
     * @param legCode
     * @return
     */
    @GetMapping("/queryChargeCodeDtoByLeg")
    public List<ChargeCodeDto> queryChargeCodeDtoByLeg(String legCode,Long bidingId,Long requirementHeadId){
        return iExpenseItemService.queryChargeCodeDtoByLeg(legCode,bidingId,requirementHeadId);
    }
    /**
     * 查找费项
     */
    @GetMapping("/queryChargeCodeDtoBy")
    public List<ChargeCodeDto> queryChargeCodeDtoBy(String legCode,String transportModeCode,String businessModeCode){
        return iExpenseItemService.queryChargeCodeDtoBy(legCode,transportModeCode,businessModeCode);
    }

}
