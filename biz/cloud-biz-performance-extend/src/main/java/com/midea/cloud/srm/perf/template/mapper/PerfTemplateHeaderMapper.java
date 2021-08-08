package com.midea.cloud.srm.perf.template.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.srm.model.perf.template.dto.PerfTemplateDTO;
import com.midea.cloud.srm.model.perf.template.dto.PerfTemplateHeaderDTO;
import com.midea.cloud.srm.model.perf.template.dto.PerfTemplateHeaderQueryDTO;
import com.midea.cloud.srm.model.perf.template.entity.PerfTemplateHeader;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 绩效模型头表 Mapper 接口
 * </p>
 *
 * @author wuwl18@meiCloud.com
 * @since 2020-05-28
 */
public interface PerfTemplateHeaderMapper extends BaseMapper<PerfTemplateHeader> {

    /**
     * Description 根据条件(绩效模型头和采购分类表)获取绩效模型头
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.05.29
     * @throws
     **/
    List<PerfTemplateHeaderDTO> findPerTemplateHeadList(@Param("pefTemplateHeader") PerfTemplateHeaderQueryDTO pefTemplateHeader) throws BaseException;

    /**
     * Description 根据条件获取绩效模型头和采购分类表
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.05.28
     * @throws
     **/
    List<PerfTemplateDTO> findPerTemplateHeadAndOrgCateGory(@Param("pefTemplateHeader") PerfTemplateHeaderQueryDTO pefTemplateHeader) throws BaseException;


}
