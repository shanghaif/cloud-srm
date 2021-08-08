package com.midea.cloud.srm.bargaining.purchaser.projectmanagement.techscore.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.techscore.entity.TechScoreLine;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.techscore.vo.TechScoreLineVO;

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
