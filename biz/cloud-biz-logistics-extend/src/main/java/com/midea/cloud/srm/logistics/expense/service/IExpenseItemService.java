package com.midea.cloud.srm.logistics.expense.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.logistics.expense.dto.ChargeCodeDto;
import com.midea.cloud.srm.model.logistics.expense.entity.ExpenseItem;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
*  <pre>
 *  费用项定义表 服务类
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-11-26 13:33:44
 *  修改内容:
 * </pre>
*/
public interface IExpenseItemService extends IService<ExpenseItem> {

    /**
     * 条件查询
     * @param expenseItem
     * @return
     */
    List<ExpenseItem> listByParam(ExpenseItem expenseItem);

    /**
     * 保存前非空校验
     * @param expenseItem
     */
    void checkNotEmptyBeforeSave(ExpenseItem expenseItem);

    /**
     * 删除前校验 数据是否都是拟定状态
     * @param expenseItemId
     */
    void checkExpenseItemsByIdBeforeDelete(Long expenseItemId);

    /**
     * 生效前校验 数据是否都是拟定状态
     * @param expenseItemId
     */
    void checkExpenseItemsByIdsBeforeEffective(Long expenseItemId);

    /**
     * 失效前校验 数据是否都是生效状态
     * @param expenseItemId
     */
    void checkExpenseItemsByIdsBeforeInEffective(Long expenseItemId);

    /**
     * 保存费用项定义
     * @param expenseItem
     */
    void saveExpenseItem(ExpenseItem expenseItem);

    /**
     * 保存费用项定义
     * @param expenseItems
     */
    void saveExpenseItems(List<ExpenseItem> expenseItems);

    /**
     * 更新费用项定义状态
     * @param expenseItemId
     * @param status
     */
    void updateExpenseItemStatus(Long expenseItemId, String status);

    /**
     * 批量更新费用项定义状态
     * @param expenseItemIds
     * @param status
     */
    void updateExpenseItemsStatus(List<Long> expenseItemIds, String status);

    /**
     * 下载费用项定义导入模板
     * @param response
     * @throws Exception
     */
    void importSupplierLeaderModelDownload(HttpServletResponse response) throws Exception;

    /**
     * excel文件导入数据到数据库
     * @param file
     * @param fileupload
     * @return
     * @throws Exception
     */
    Map<String, Object> importExcel(MultipartFile file, Fileupload fileupload) throws Exception;

    /**
     * 根据leg查找生效的有对应关系的费项
     * @param legCode
     * @return
     */
    List<ChargeCodeDto> queryChargeCodeDtoByLeg(String legCode,Long bidingId,Long requirementHeadId);
    
    /**
     * 查找费项
     */
    List<ChargeCodeDto> queryChargeCodeDtoBy(String legCode,String transportModeCode,String businessModeCode);
    
    /**
     * 查找leg的层级
     * @Param legName leg的名称组
     * @return leg编码 - (费项名称-费项编码)
     */
    Map<String,Map<String,String>> queryLegChargeMap(List<String> legName,Long bidingId);
}
