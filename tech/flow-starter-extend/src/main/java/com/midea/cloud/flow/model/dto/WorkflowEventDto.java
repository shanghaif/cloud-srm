/**
 * 
 */
package com.midea.cloud.flow.model.dto;

import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @author lizl7
 *
 */
@Data
public class WorkflowEventDto implements Serializable {

	private String token;

	private String iframeUrl;

	private String taskId;

	private String flowinstanceId;

}
