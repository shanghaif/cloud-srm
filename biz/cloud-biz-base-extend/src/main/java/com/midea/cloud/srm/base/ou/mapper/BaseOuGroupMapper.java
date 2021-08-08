package com.midea.cloud.srm.base.ou.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.base.ou.dto.query.BaseOuGroupQueryDTO;
import com.midea.cloud.srm.model.base.ou.entity.BaseOuDetail;
import com.midea.cloud.srm.model.base.ou.entity.BaseOuGroup;
import com.midea.cloud.srm.model.base.ou.vo.BaseOuGroupDetailVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * ou组信息表 Mapper 接口
 * </p>
 *
 * @author tanjl11@meiCloud.com
 * @since 2020-09-04
 */
public interface BaseOuGroupMapper extends BaseMapper<BaseOuGroup> {


    List<BaseOuGroupDetailVO> queryBaseOuGroupDetailByPage(@Param("dto")BaseOuGroupQueryDTO dto,@Param("userName")String userName);

    BaseOuDetail queryBaseOuDetailByCode(@Param("ouGroupCode") String ouGroupCode);

}
