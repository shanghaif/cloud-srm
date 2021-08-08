package com.midea.cloud.srm.base.formula.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.base.formula.dto.create.BaseMaterialCreateDto;
import com.midea.cloud.srm.model.base.formula.dto.query.BaseMaterialQueryDto;
import com.midea.cloud.srm.model.base.formula.dto.update.BaseMaterialUpdateDto;
import com.midea.cloud.srm.model.base.formula.entity.BaseMaterial;
import com.midea.cloud.srm.model.base.formula.enums.StuffStatus;
import com.midea.cloud.srm.model.base.formula.vo.BaseMaterialVO;

/**
 * <pre>
 *  基本材料表 crud
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
public interface IBaseMaterialService extends IService<BaseMaterial> {
    /**
     * 新增基材
     *
     * @param dto
     * @return
     */
    BaseMaterialVO createBaseMaterial(BaseMaterialCreateDto dto);

    /**
     * 修改基材
     *
     * @param dto
     * @return
     */
    BaseMaterialVO updateBaseMaterial(BaseMaterialUpdateDto dto);

    /**
     * 根据id删除基材
     */
    Boolean deleteBaseMaterialById(Long materialId);

    /**
     * 查询基材
     */
    PageInfo<BaseMaterial> queryBaseMaterialByPage(BaseMaterialQueryDto dto);

    Boolean updateStatus(StuffStatus status, Long id);
}
