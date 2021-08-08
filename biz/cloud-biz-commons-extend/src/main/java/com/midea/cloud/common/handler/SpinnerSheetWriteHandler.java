package com.midea.cloud.common.handler;

import java.util.Map;

import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;

import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;

import lombok.Data;

/**
 * 
 * 
 * <pre>
 *  EasyExcel 下拉框
 * </pre>
 * 
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 * 
 *          <pre>
 *  修改记录
 *  修改后版本: 
 *  修改人: 
 *  修改日期: 2020年4月25日 下午12:30:54  
 *  修改内容:
 *          </pre>
 */
@Data
public class SpinnerSheetWriteHandler implements SheetWriteHandler {

	private Map<Integer, String[]> mapDropDown;

	public SpinnerSheetWriteHandler(Map<Integer, String[]> mapDropDown) {
		this.mapDropDown = mapDropDown;
	}

	@Override
	public void beforeSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {

	}

	@Override
	public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
		Sheet sheet = writeSheetHolder.getSheet();
		/// 开始设置下拉框
		DataValidationHelper helper = sheet.getDataValidationHelper();// 设置下拉框
		if (mapDropDown != null && mapDropDown.keySet().size() > 0) {
			for (Integer colIndex : mapDropDown.keySet()) {
				/*** 起始行、终止行、起始列、终止列 **/
				CellRangeAddressList addressList = new CellRangeAddressList(1, 1000, colIndex, colIndex);
				/*** 设置下拉框数据 **/
				DataValidationConstraint constraint = helper.createExplicitListConstraint(mapDropDown.get(colIndex));
				DataValidation dataValidation = helper.createValidation(constraint, addressList);
				/*** 处理Excel兼容性问题 **/
				if (dataValidation instanceof XSSFDataValidation) {
					dataValidation.setSuppressDropDownArrow(true);
					dataValidation.setShowErrorBox(true);
				} else {
					dataValidation.setSuppressDropDownArrow(false);
				}
				sheet.addValidationData(dataValidation);
			}
		}
	}

}
