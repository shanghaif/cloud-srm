package com.midea.cloud.srm.perf.scoring.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.srm.model.common.ExportExcelParam;
import com.midea.cloud.srm.model.perf.scoring.PerfOverallScore;
import com.midea.cloud.srm.model.supplier.info.dto.PerfOverallScoreDto;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 *  <pre>
 *  综合绩效得分主表 服务类
 * </pre>
 *
 * @author wuwl18@meiCloud.com
 * @version 1.00.00
 *
 *  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-06-16 11:50:39
 *  修改内容:
 * </pre>
 */
public interface IPerfOverallScoreService extends IService<PerfOverallScore> {

    /**
     * Description 生成指标维度绩效得分明细、标维度绩效得分和标维度绩效得分主表信息
     * @Param scoreItemsId 绩效评分项目表ID
     * @return true-生成成功，false-生成失败
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.16
     * @throws
     **/
    boolean generateScoreInfo(Long scoreItemsId);

    /**
     * Description 根据条件获绩效指标绩效得分主表和子表集合(用于连表查询)
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.19
     * @throws BaseException
     **/
    List<PerfOverallScore> findOverallScoreAndSonList(PerfOverallScore overallScore) throws BaseException;

    /**
     * Description 绩效评分项目时修改指标维度综合得分主表状态为已发布
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.21
     * @throws BaseException
     **/
    void publishScoreItemsUpdateStatus(Long scoreItemId) throws BaseException;

    /**
     * Description 根据条件获取综合绩效得分主表集合
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.23
     * @throws BaseException
     **/
    List<PerfOverallScore> findOverallScoreList(PerfOverallScore overallScore) throws BaseException;

    /**
     * 供应商提交反馈
     * @param perfOverallScoreList
     */
    void vendorSubmitFeedback(List<PerfOverallScore> perfOverallScoreList);

    /**
     * Description 绩效综合查询列表导出
     * @Param overallScoreDto 导出Excel类
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.23
     * @throws
     **/
    void exportPerfOverallScore(ExportExcelParam<PerfOverallScore> overallScoreDto, HttpServletResponse response) throws IOException;

    /**
     * 获取指定供应商的
     * @param vendorId
     * @return
     */
    List<PerfOverallScoreDto> getPerfOverallScoreVendorId(Long vendorId);

    /**
     * 将多人评分的数据转换成另一张表的数据（测试用）
     */
    void calculateAverageScore(Long scoreItemsId);

    /**
     * 供应商确认
     */
    void vendorConfirm(List<PerfOverallScore> perfOverallScores);

    /**
     * 附件上传
     * @param perfOverallScore
     */
    void uploadFile(PerfOverallScore perfOverallScore);

    /**
     * 附件删除
     * @param perfOverallScore
     */
    void deleteFile(PerfOverallScore perfOverallScore);

}
