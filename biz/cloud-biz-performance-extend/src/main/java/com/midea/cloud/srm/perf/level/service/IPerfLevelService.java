package com.midea.cloud.srm.perf.level.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.perf.level.entity.PerfLevel;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 *  <pre>
 *  绩效等级表 服务类
 * </pre>
 *
 * @author wuwl18@meiCloud.com
 * @version 1.00.00
 *
 *  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-06-03 09:26:13
 *  修改内容:
 * </pre>
 */
public interface IPerfLevelService extends IService<PerfLevel> {

    /**
     * Description 保存或修改绩效等级信息
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.03
     * @throws BaseException
     **/
    String saveOrUpdatePerfLevel(PerfLevel perfLevel) throws BaseException;

    /**
     * Description 根据ID删除绩效等级信息
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.03
     * @throws BaseException
     **/
    String deletePerfLevel(Long levelId);

    /**
     * Description 导出‘绩效等级数据导入模板’
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.03
     * @throws IOException
     **/
    void exportPerfLevelModel(HttpServletResponse response) throws IOException;

    /**
     * Description 通过导入Excel数据保存绩效等级信息
     * @Param file Excel文件
     * @Param fileupload
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.03
     * @throws Exception
     **/
    String importExcelInsertLevel(MultipartFile file, Fileupload fileupload) throws Exception;

    /**
     * Description 获取有效的去重等级名称集合
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.19
     * @throws BaseException
     **/
    List<Map<String, Object>> findDistinctLevelNameList() throws BaseException;

    /**
     * Description 判断有效的绩效名称、采购组织不能重复
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.24
     **/
    void checkRepeatLevelNameAndOrgId(PerfLevel perfLevel, boolean isUpdate);

    /**
     * 当状态为启用时，校验得分区间不能重叠
     * @param perfLevel
     */
    void checkScoreOverlap(PerfLevel perfLevel);

}
