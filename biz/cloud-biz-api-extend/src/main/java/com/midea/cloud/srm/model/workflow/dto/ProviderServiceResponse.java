/**
 * 
 */
package com.midea.cloud.srm.model.workflow.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <pre>
 *  
 * </pre>
 *
 * @author nianhuanh@meicloud.com
 * @version 1.00.00
 *
 * <pre>
  *  修改记录
  *  修改后版本:
  *  修改人:
  *  修改日期: 2020年8月7日 下午6:37:19
  *  修改内容:
 * </pre>
 */
@XmlRootElement
public class ProviderServiceResponse implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 829310900748885206L;

	private String resultCode;
	
	private String resultMsg;

	/**
	 * @return the resultCode
	 */
	@XmlElement(name = "resultcode")
	public String getResultCode() {
		return resultCode;
	}

	/**
	 * @param resultCode the resultCode to set
	 */
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	/**
	 * @return the resultMsg
	 */
	@XmlElement(name = "resultmsg")
	public String getResultMsg() {
		return resultMsg;
	}

	/**
	 * @param resultMsg the resultMsg to set
	 */
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
	
}
