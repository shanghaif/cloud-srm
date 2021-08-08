package com.midea.cloud.srm.cm.accept.service;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.cm.accept.dto.DetailLineDTO;
import com.midea.cloud.srm.model.cm.accept.entity.DetailLine;
import com.baomidou.mybatisplus.extension.service.IService;

/**
*  <pre>
 *  合同验收行 服务类
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-11 19:45:28
 *  修改内容:
 * </pre>
*/
public interface IDetailLineService extends IService<DetailLine> {
    public PageInfo<DetailLine> listPageByParm(DetailLineDTO detailLine);
}
