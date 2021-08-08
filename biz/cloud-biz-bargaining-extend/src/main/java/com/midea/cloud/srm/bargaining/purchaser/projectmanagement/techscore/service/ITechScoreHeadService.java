package com.midea.cloud.srm.bargaining.purchaser.projectmanagement.techscore.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.techscore.entity.TechScoreHead;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.techscore.param.TechScoreSaveHeadParam;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.techscore.param.TechScoreQueryParam;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.techscore.vo.TechScoreHeadVO;

/**
 * 
 * 
 * <pre>
 * 技术评分头表
 * </pre>
 * 
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 * 
 *          <pre>
 *  修改记录
 *  修改后版本: 
 *  修改人: 
 *  修改日期: 2020年3月19日 下午7:11:35  
 *  修改内容:
 *          </pre>
 */
public interface ITechScoreHeadService extends IService<TechScoreHead> {

	PageInfo<TechScoreHeadVO> listPage(TechScoreQueryParam queryParam);

	void saveOrUpdateTechScore(TechScoreSaveHeadParam param);

}
