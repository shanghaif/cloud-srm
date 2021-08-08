package com.midea.cloud.srm.base.customtable.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.midea.cloud.srm.base.customtable.service.ICustomTableService;
import com.midea.cloud.srm.model.base.customtable.vo.CustomTableVO;

/**
 * 
 * 
 * <pre>
 * 自定义表格
 * </pre>
 * 
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 * 
 *          <pre>
 *  修改记录
 *  修改后版本: 
 *  修改人: 
 *  修改日期: 2020年4月22日 上午11:38:58  
 *  修改内容:
 *          </pre>
 */
@RestController
@RequestMapping("/customTable")
public class CustomTableController {

	@Autowired
	private ICustomTableService iCustomTableService;

	/**
	 * 自定义表格列表
	 * 
	 * @param bidingId
	 * @return
	 */
	@GetMapping("/queryCustomTableList")
	public List<CustomTableVO> queryCustomTableList(@RequestParam(name = "businessId", required = true) Long businessId, @RequestParam(name = "businessType", required = true) String businessType) {
		return iCustomTableService.queryCustomTableList(businessId, businessType);
	}

	/**
	 * 保存自定义表格列表
	 * 
	 * @param list
	 */
	@PostMapping("/saveOrUpdateCustomTable")
	public void saveOrUpdateCustomTable(@RequestParam(name = "businessId", required = true) Long businessId, @RequestParam(name = "businessType", required = true) String businessType, @RequestBody List<CustomTableVO> list) {
		iCustomTableService.saveOrUpdateCustomTable(businessId, businessType, list);
	}

}
