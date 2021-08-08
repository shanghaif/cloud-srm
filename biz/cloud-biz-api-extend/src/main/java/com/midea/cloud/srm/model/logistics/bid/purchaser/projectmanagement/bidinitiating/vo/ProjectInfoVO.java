package com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidinitiating.vo;

import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidinitiating.entity.BidFile;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidinitiating.entity.BidFileConfig;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidinitiating.entity.Biding;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidinitiating.entity.Group;
import lombok.Data;

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
public class ProjectInfoVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Biding biding;
    private List<BidFile> fileList;
    private List<BidFileConfig> bidFileConfigList;
    private List<Group> groupList;
}
