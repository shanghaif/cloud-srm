package com.midea.cloud.srm.base.formula.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.base.formula.dto.create.EssentialFactorCreateDto;
import com.midea.cloud.srm.model.base.formula.dto.query.EssentialFactorQueryDto;
import com.midea.cloud.srm.model.base.formula.dto.update.EssentialFactorUpdateDto;
import com.midea.cloud.srm.model.base.formula.entity.EssentialFactor;
import com.midea.cloud.srm.model.base.formula.enums.StuffStatus;
import com.midea.cloud.srm.model.base.formula.vo.EssentialFactorVO;

import java.util.List;

/**
 * <pre>
 *  公式要素表 服务类
 * </pre>
 *
 * @author tanjl11@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-26 16:27:13
 *  修改内容:
 * </pre>
 */
public interface IEssentialFactorService extends IService<EssentialFactor> {
    /**
     * 新建要素
     * @param dto
     * @return
     */
    EssentialFactorVO createEssentialFactor(EssentialFactorCreateDto dto);

    /**
     * 修改要素
     * @param dto
     * @return
     */
    EssentialFactorVO updateEssentialFactor(EssentialFactorUpdateDto dto);

    Boolean deleteEssentialFactorById(Long essentialId);

    PageInfo<EssentialFactorVO> queryEssentialFactorByPage(EssentialFactorQueryDto dto);

    /**
     *
     * @return
     */
    Boolean updateStatus(StuffStatus status,Long id);

    List<EssentialFactor> listByEssentialFactorName(List<String> essentialFactorNameList);

    EssentialFactorVO queryEssentialFactorById(Long essentialFactorId);
}
