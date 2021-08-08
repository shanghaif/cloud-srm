package com.midea.cloud.srm.bargaining.purchaser.techdiscuss.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.bargaining.purchaser.techdiscuss.entity.TechDiscussReply;
import com.midea.cloud.srm.model.bargaining.purchaser.techdiscuss.vo.TechDiscussReplyVO;

import java.util.List;

/**
*  <pre>
 *  技术交流项目供应商回复表 服务类
 * </pre>
*
* @author fengdc3@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-12 11:09:20
 *  修改内容:
 * </pre>
*/
public interface ITechDiscussReplyService extends IService<TechDiscussReply> {
    TechDiscussReplyVO saveTechDiscussReplyInfo(TechDiscussReplyVO techDiscussReplyVO);

    List<TechDiscussReplyVO> listTechDiscussReplyInfo(TechDiscussReplyVO techDiscussReplyVO);

}
