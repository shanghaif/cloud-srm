package com.midea.cloud.srm.model.logistics.bid.dto;

import com.midea.cloud.srm.model.logistics.bid.entity.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 *  招标立项-项目信息页 视图对象
 * </pre>
 *
 * @author fengdc3@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-3-20 16:44
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LgtBidInfoDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private LgtBiding biding;
    private List<LgtBidFile> fileList;
    private List<LgtFileConfig> bidFileConfigList;
    private List<LgtGroup> groupList;
    private List<LgtBidTemplate> lgtBidTemplates;
    private List<LgtProcessNode> lgtProcessNodes;
}
