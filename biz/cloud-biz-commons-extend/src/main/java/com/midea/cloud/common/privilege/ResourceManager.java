/**
 * 
 */
package com.midea.cloud.common.privilege;

/**
 * <pre>
 *  资源管理
 * </pre>
 *
 * @author nianhuanh@meicloud.com
 * @version 1.00.00
 *
 * <pre>
  *  修改记录
  *  修改后版本:
  *  修改人:
  *  修改日期: 2020年9月28日 下午5:04:59
  *  修改内容:
 * </pre>
 */
public interface ResourceManager {
	
	/**
	 * 获取允许的资源
	 * @return
	 */
	public String[] acquirePermitResource();
	
	/**
	 * 获取禁止的资源
	 * @return
	 */
	public String[] acquireForbidenResource();

}
