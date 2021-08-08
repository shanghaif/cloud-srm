package com.midea.cloud.srm.bid.purchaser.projectmanagement.techscore.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.techscore.entity.TechScoreLine;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.techscore.vo.TechScoreLineVO;

import java.util.List;

/**
 * 
 * 
 * <pre>
 * 技术评分行
 * </pre>
 * 
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 * 
 *          <pre>
 *  修改记录
 *  修改后版本: 
 *  修改人: 
 *  修改日期: 2020年3月19日 下午7:12:21  
 *  修改内容:
 *          </pre>
 */
public interface ITechScoreLineService extends IService<TechScoreLine> {

	List<TechScoreLineVO> queryTechScoreLineList(Long bidVendorId);

}
