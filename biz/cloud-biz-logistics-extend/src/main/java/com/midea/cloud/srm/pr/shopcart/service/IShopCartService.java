package com.midea.cloud.srm.pr.shopcart.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.base.material.dto.MaterialQueryDTO;
import com.midea.cloud.srm.model.common.ExportExcelParam;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.pm.pr.shopcart.entity.ShopCart;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
*  <pre>
 *  购物车 服务类
 * </pre>
*
* @author haiping2.li@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-12 16:11:56
 *  修改内容:
 * </pre>
*/
public interface IShopCartService extends IService<ShopCart> {
	/**
	 * @Description 加入购物车
	 * @Param [material]
	 * @return
	 * @Author haiping2.li@meicloud.com
	 * @Date 2020.09.18 15:30
	 **/
	void ceeaAddToShopCart(MaterialQueryDTO material);

	/**
	 * @Description 选择汇总人
	 * @Param [summaryUserId, summaryEmpNo, summaryNickname, noticeUserId, noticeEmpNo, noticeNickname, ids]
	 * @return java.lang.String
	 * @Author haiping2.li@meicloud.com
	 * @Date 2020.09.19 09:09
	 **/
	String ceeaSetSummaryAndNoticeUser(Long summaryUserId, String summaryEmpNo, String summaryNickname,
									   Long noticeUserId, String noticeEmpNo, String noticeNickname, List<Long> ids);

	/**
	 * @Description 提交/退回需求
	 * @Param [status , returnReason, ids]
	 * @return
	 * @Author haiping2.li@meicloud.com
	 * @Date 2020.09.19 13:24
	 **/
	String ceeaChangeShopCartStatus(String status, String returnReason, List<Long> ids);

	/**
	 * @Description 勾选购物车行点击保存
	 * @Param [shopCarts]
	 * @return
	 * @Author haiping2.li@meicloud.com
	 * @Date 2020.09.19 16:59
	 **/
	String ceeaUpdateShopCarts(List<ShopCart> shopCarts);

	/**
	 * @Description 批量维护采购类型和需求时间
	 * @Param [shopCarts, purchaseType, requirementDate]
	 * @return
	 * @Author wuhx29@meicloud.com
	 * @Date 2020.10.16 13:50
	 **/
	String ceeaUpdateShopCarts(List<Long> ids, String purchaseType, LocalDate requirementDate);

	/**
	 * @Description 创建申请单前校验“采购类型”、“需求时间”、“数量”是否已维护
	 * @Param [ids]
	 * @return
	 * @Author haiping2.li@meicloud.com
	 * @Date 2020.09.21 10:07
	 **/
	String ceeaValidRequiredInfo(List<Long> ids);

	/**
	 * @Description 创建申请单
	 * @Param [ids]
	 * @return
	 * @Author haiping2.li@meicloud.com
	 * @Date 2020.09.21 10:59
	 **/
	List<Long> ceeaCreateRequirements(List<Long> ids);

	/**
	 * @Description 根据主键id删除（未提交的数据）
	 * @Param [ids]
	 * @return
	 * @Author haiping2.li@meicloud.com
	 * @Date 2020.09.23 08:48
	 **/
	void ceeaDeleteByIds(List<Long> ids);

	/**
	 * @Description 采购需求导入下载模板
	 * @Param: [httpServletResponse]
	 * @Return: void
	 * @Author: dengyl23@meicloud.com
	 * @Date: 2020/9/26 13:57
	 */
	void importModelDownload(HttpServletResponse httpServletResponse);

	/**
	 * @Description 购物车导入需求行
	 * @Param: [file]
	 * @Return: void
	 * @Author: dengyl23@meicloud.com
	 * @Date: 2020/9/26 14:33
	 */
	void importExcel(MultipartFile file);

	Map<String,Object> importExcelNew(MultipartFile file, Fileupload fileupload);

	/**
	 * @Description 购物车Excel导出
	 * @Param [shopCartExportParam, response]
	 * @return void
	 * @Author haiping2.li@meicloud.com
	 * @Date 2020.09.29 17:31
	 **/
	void exportMaterialItemExcel(ExportExcelParam<ShopCart> shopCartExportParam, HttpServletResponse response) throws Exception;

	/**
	 * @Description: 选择汇总人之前要校验是否为未提交
	 * @param: [ids]
	 * @return: java.lang.String
	 * @auther: wuhx29@meicloud.com
	 * @date: 2020/10/17 15:28
	 */
	int ceeaCheckSummaryAndSubmitRequest(List<Long> shopCartIds);

	/**
	 * @Description: 勾选购物车行点击保存之前校验是否为未提交或已提交
	 * @param: [shopCartIds]
	 * @return: java.lang.String
	 * @auther: wuhx29@meicloud.com
	 * @date: 2020/10/17 15:53
	 */
	int ceeaCheckUpdateShopCarts(List<Long> shopCartIds);

	/**
	 * @Description: 调用创建申请单方法之前先检查行数据状态是否为已提交
	 * @param: [shopCartIds]
	 * @return: int  1为可操作申请单方法，0为只有状态为已提交的行才可操作
	 * @auther: wuhx29@meicloud.com
	 * @date: 2020/10/17 16:50
	 */
	int ceeaCheckCreateRequirements(List<Long> shopCartIds);

}
