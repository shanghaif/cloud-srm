package com.midea.cloud.srm.po.order.service;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.OrderDetailDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.OrderDetail;
import com.midea.cloud.srm.model.suppliercooperate.order.vo.OrderDetailVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 *
 *
 *
 * <pre>
 * 订单明细
 * </pre>
 *
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 *
 *          <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020年4月27日 下午2:48:38
 *  修改内容:
 *          </pre>
 */
public interface IOrderDetailService {

	void closeOrderDetail(List<Long> ids);

	Map<String, Object> downloadTemplate() throws Exception;

	List<OrderDetail> importExcel(Long vendorId, Long organizationId, MultipartFile file) throws Exception;

	/**
	 * 送货通知查询订单明细
	 * @param orderDetailDTO
	 * @return
	 */
    PageInfo<OrderDetailDTO> listInDeliveryNotice(OrderDetailDTO orderDetailDTO);
}
