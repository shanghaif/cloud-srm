package com.midea.cloud.srm.perf.scoring.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.srm.model.common.ExportExcelParam;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.perf.scoreproject.entity.PerfScoreItems;
import com.midea.cloud.srm.model.perf.scoring.PerfScoreManScoring;
import com.midea.cloud.srm.model.perf.scoring.ScoreManScoringV1;
import com.midea.cloud.srm.model.perf.scoring.dto.ScoreManScoringV1SubmitConfirmDTO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
*  <pre>
 *  评分人绩效评分表 服务类
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021-01-21 17:26:15
 *  修改内容:
 * </pre>
*/
public interface IScoreManScoringV1Service extends IService<ScoreManScoringV1> {

    /**
     * Description 根据绩效评分项目表ID保存评分人绩效评分信息
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.11
     * @throws BaseException
     **/
    String saveScoreManScoringByScoreItemsId(Long scoreItemsId) throws BaseException;

    /**
     * Description 分页查询评分人绩效评分信息
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.12
     **/
    List<ScoreManScoringV1> listScoreManScoringPage(ScoreManScoringV1 scoreManScoringV1) throws BaseException;

    /**
     * Description 保存绩效模型指标集合
     * @Param scoreManScoringsList 绩效模型指标集合
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.13
     * @throws List<PerfScoreManScoring>
     **/
    String saveScoreManScoring(List<ScoreManScoringV1> scoreManScoringV1List);

    /**
     * 放弃评分
     * @param scoreManScoringV1List
     */
    void abstention(List<ScoreManScoringV1> scoreManScoringV1List);

    /**
     * 附件上传
     * @param scoreManScoringV1
     */
    void uploadFile(ScoreManScoringV1 scoreManScoringV1);

    /**
     * 附件删除
     * @param scoreManScoringV1
     */
    void deleteFile(ScoreManScoringV1 scoreManScoringV1);

    /**
     * 锁定绩效项目评分页面的 是否已截止评分标志位
     * @param scoreItemsId
     */
    void lockIfEndScored(Long scoreItemsId);

    /**
     * 评分人绩效评分提交前获取哪些数据没有评分
     * @param scoreManScoringV1List
     * @return
     */
    BaseResult<ScoreManScoringV1SubmitConfirmDTO> confirmBeforeScoreManScoringSubmit(List<ScoreManScoringV1> scoreManScoringV1List);

    /**
     * 评分人绩效评分获取项目列表
     * @param scoreManScoringV1List
     * @return
     */
    List<PerfScoreItems> getScoreItemsAfterSubmit(List<ScoreManScoringV1> scoreManScoringV1List);

    /**
     * 评分人绩效评分自定义导出
     * @param scoreManScoringV1ExportParam
     * @param response
     * @return
     * @throws IOException
     */
    void exportScoreManScoringV1Excel(@RequestBody ExportExcelParam<ScoreManScoringV1> scoreManScoringV1ExportParam, HttpServletResponse response) throws IOException;

    /**
     * 评分人绩效评分-导入文件模板下载
     * @param response
     */
    void importScoreManScoringV1Download(HttpServletResponse response) throws Exception;

    /**
     * 评分人绩效评分自定义导入
     */
    Map<String, Object> importScoreManScoringV1Excel(@RequestParam("file") MultipartFile file, Fileupload fileupload) throws Exception;

    /**
     * 查询通过excel导入的数据
     * @return
     */
    List<ScoreManScoringV1> listExcelImportData();

    /**
     * 刷新通过excel导入的数据
     */
    void freshExcelImportData();

}
