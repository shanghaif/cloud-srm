package com.midea.cloud.srm.perf.template.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.srm.model.perf.template.dto.PerfTemplateDTO;
import com.midea.cloud.srm.model.perf.template.dto.PerfTemplateHeaderDTO;
import com.midea.cloud.srm.model.perf.template.dto.PerfTemplateHeaderQueryDTO;
import com.midea.cloud.srm.model.perf.template.entity.PerfTemplateHeader;
import com.midea.cloud.srm.model.perf.template.entity.PerfTemplateLine;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;

import java.util.List;

/**
 *  <pre>
 *  绩效模型头表 服务类
 * </pre>
 *
 * @author wuwl18@meiCloud.com
 * @version 1.00.00
 *
 *  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-28 16:41:07
 *  修改内容:
 * </pre>
 */
public interface IPerfTemplateHeaderService extends IService<PerfTemplateHeader> {

    /**
     * Description 根据条件(绩效模型头和采购分类表)获取绩效模型头
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.05.29
     * @throws BaseException
     **/
    List<PerfTemplateHeaderDTO> findPerTemplateHeadList(PerfTemplateHeaderQueryDTO pefTemplateHeader) throws BaseException;

    /**
     * Description 根据ID获取绩效模型头、采购分类表、绩效指标维度和绩效指标行表信息
     * @Param perfTemplateHeaderId 绩效模型头Id
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.05.30
     * @throws BaseException
     **/
    PerfTemplateDTO findPerTemplateByTemplateHeadId(Long perfTemplateHeaderId) throws BaseException;

    /**
     * Description 根据条件获取绩效模型头和采购分类表
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.05.28
     * @throws BaseException
     **/
    List<PerfTemplateDTO> findPerTemplateHeadAndOrgCateGory(PerfTemplateHeaderQueryDTO pefTemplateHeader) throws BaseException;

    /**
     * Description  绩效模型启动/禁用
     * @Param pefTempateHeadId 绩效模型头表Id
     * @Param templateStatus 模版状态
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.05.28
     * @throws BaseException
     **/
    String enablePefTemplateHeader(Long pefTempateHeadId, String templateStatus) throws BaseException;

    /**
     * Description 根据ID删除绩效模型记录
     * @Param pefTempateHeadId 绩效模型头表Id
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.05.28
     * @throws BaseException
     **/
    void delPefTemplateHeader(Long pefTempateHeadId) throws BaseException;

    /**
     * Description 保存/修改绩效模型信息
     * @Param perfTemplateDTO 保存/修改绩效模型DTO
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.01
     * @throws BaseException
     **/
    String saveOrUpdatePerfTemplate(PerfTemplateDTO perfTemplateDTO) throws BaseException;

    /**
     * 根据绩效模型查询供应商
     * 根据绩效模型行上的品类去组织品类表查询供应商
     * @param perfTemplateHeader
     * @return
     */
    List<CompanyInfo> listCompanysByPerfTemplateHeader(PerfTemplateHeader perfTemplateHeader);

    /**
     * 根据模板头id查询维度和指标信息
     * @param templateHeaderId
     * @return
     */
    List<PerfTemplateLine> listTemplateLinesByTemplateHeaderId(Long templateHeaderId);

    /**
     * 列表条件查询
     * @param queryDTO
     * @return
     */
    List<PerfTemplateHeader> listPefTemplateHeaderPage(PerfTemplateHeaderQueryDTO queryDTO);

    /**
     * 绩效模板复制
     * @param perfTemplateHeaderId
     */
    void copyPerfTemplateHeader(Long perfTemplateHeaderId);

    /**
     * 校验模型头
     * @param perfTemplateHeader
     */
    void checkTemplateHeader(PerfTemplateHeader perfTemplateHeader);

}
