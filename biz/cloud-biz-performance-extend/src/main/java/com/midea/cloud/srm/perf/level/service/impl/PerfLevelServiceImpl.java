package com.midea.cloud.srm.perf.level.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.enums.Enable;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.EasyExcelUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.model.base.organization.entity.OrganizationUser;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.perf.level.dto.PerfLevelExcelDTO;
import com.midea.cloud.srm.model.perf.level.entity.PerfLevel;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.perf.common.PerfLevelConst;
import com.midea.cloud.srm.perf.level.mapper.PerfLevelMapper;
import com.midea.cloud.srm.perf.level.service.IPerfLevelService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 *  <pre>
 *  绩效等级表 服务实现类
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
@Service
public class PerfLevelServiceImpl extends ServiceImpl<PerfLevelMapper, PerfLevel> implements IPerfLevelService {

    /**
     * 保存或修改绩效等级信息
     * @param perfLevel
     * @return
     * @throws BaseException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String saveOrUpdatePerfLevel(PerfLevel perfLevel) throws BaseException {
        Assert.notNull(perfLevel, ResultCode.MISSING_SERVLET_REQUEST_PARAMETER.getMessage());
        Long levelId= perfLevel.getLevelId();
        boolean isUpdate = false;   //是否修改默认为不是
        if(null == levelId) { //新增
            levelId = IdGenrator.generate();
            perfLevel.setLevelId(levelId);
            perfLevel.setDeleteFlag(Enable.N.toString());
            perfLevel.setVersion(1L);
        }else{
            isUpdate = true;
        }
        this.checkSaveOrUpdatePerfLevel(perfLevel, isUpdate);    //检查绩效等级信息能否保存

        boolean isUpdateSuccess = false;
        try {
            isUpdateSuccess = super.saveOrUpdate(perfLevel);
        }catch (Exception e){
            log.error("保存/修改绩效等级信息时报错：",e);
            throw new BaseException(ResultCode.OPERATION_FAILED.getMessage());
        }
        if(!isUpdateSuccess){
            return ResultCode.OPERATION_FAILED.getMessage();
        }
        return ResultCode.SUCCESS.getMessage();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String deletePerfLevel(Long levelId) {
        Assert.notNull(levelId, "id不能为空");
        int deleteCount = 0;
        try{
            deleteCount = getBaseMapper().deleteById(levelId);
        }catch (Exception e){
            log.error("根据ID: "+levelId+"删除绩效等级时报错：",e);
            throw new BaseException(ResultCode.OPERATION_FAILED.getMessage());
        }
        if(0 > deleteCount){
            return ResultCode.OPERATION_FAILED.getMessage();
        }
        return ResultCode.SUCCESS.getMessage();
    }

    @Override
    public void exportPerfLevelModel(HttpServletResponse response) throws IOException {
        String fileName = "绩效等级数据导入模板";
        ArrayList<PerfLevelExcelDTO> perfLevelExcelDTOS = new ArrayList<>();
        ServletOutputStream outputStream = EasyExcelUtil.getServletOutputStream(response, fileName);
        EasyExcelUtil.writeExcelWithModel(outputStream,fileName,perfLevelExcelDTOS, PerfLevelExcelDTO.class);
    }

    @Override
    public String importExcelInsertLevel(MultipartFile file, Fileupload fileupload) throws Exception {
        // 校验传参
        EasyExcelUtil.checkParam(file, fileupload);

        InputStream inputStream = file.getInputStream();
        List<PerfLevel> perfLevelList = new ArrayList<>();

        //获取当前用户所属组织，并检查是否为空，为空则抛出采购组织ID不能为空异常
        LoginAppUser user = AppUserUtil.getLoginAppUser();
        Assert.notNull(user, PerfLevelConst.ORGANIZATION_ID_NOT_NULL);
        List<OrganizationUser> organizationUserList = null;
        if(null != user){
            organizationUserList = user.getOrganizationUsers();
        }
        if(CollectionUtils.isEmpty(organizationUserList)){
            Assert.isTrue(false, PerfLevelConst.CURRENT_USER_NOT_ORG);
        }

        // 获取导入数据转为绩效等级实体类，并检查保存数据，如有不符合则提示前端
        List<Object> objects = EasyExcelUtil.readExcelWithModel(inputStream, PerfLevelExcelDTO.class);
        if(CollectionUtils.isNotEmpty(objects)){
            for(Object object : objects){
                if(null != object){
                    PerfLevelExcelDTO perfLevelExcelDTO = (PerfLevelExcelDTO)object;
                    PerfLevel perfLevel = new PerfLevel();
                    BeanUtils.copyProperties(perfLevelExcelDTO, perfLevel);
                    if(null != perfLevel){
                        //根据组织名获取对应的组织信息，并渲染组织ID和组织Code
                        OrganizationUser organizationUser = this.getOrganization(organizationUserList, perfLevel.getOrganizationName());
                        Assert.notNull(organizationUser, PerfLevelConst.ORG_NOT_BELONG_USER);
                        perfLevel.setOrganizationId(organizationUser.getOrganizationId());
                        perfLevel.setOrganizationName(organizationUser.getOrganizationName());

                        perfLevel.setLevelId(IdGenrator.generate());
                        perfLevel.setDeleteFlag(Enable.N.toString());
                        perfLevel.setVersion(1L);
                        this.checkSaveOrUpdatePerfLevel(perfLevel, false);
                        String status = perfLevel.getStatus();
                        if(!(Enable.Y.toString().equals(status) || Enable.N.toString().equals(status))){
                            Assert.isTrue(false, PerfLevelConst.STATUS_IS_Y_N);
                        }
                        perfLevelList.add(perfLevel);
                    }
                }
            }

            if(CollectionUtils.isEmpty(perfLevelList)){
                Assert.isTrue(false, ResultCode.MISSING_SERVLET_REQUEST_PARAMETER.getMessage());
            }
            boolean isSave = super.saveBatch(perfLevelList);
            if(!isSave){
                return ResultCode.OPERATION_FAILED.getMessage();
            }
        }
        return ResultCode.SUCCESS.getMessage();
    }

    @Override
    public List<Map<String, Object>> findDistinctLevelNameList() throws BaseException{
        try{
            return getBaseMapper().findDistinctLevelNameList();
        }catch (Exception e){
            log.error("获取有效的去重等级名称集合时报错：",e);
            throw new BaseException(ResultCode.UNKNOWN_ERROR.getMessage());
        }
    }

    @Override
    public void checkRepeatLevelNameAndOrgId(PerfLevel perfLevel, boolean isUpdate) {
        Assert.notNull(perfLevel, ResultCode.MISSING_SERVLET_REQUEST_PARAMETER.getMessage());
        String status = perfLevel.getStatus();
        if(Enable.Y.toString().equals(status)) {
            /**判断有效的绩效名称、采购组织不能重复*/
            Long perfLevelId = perfLevel.getLevelId();
            String levelName = perfLevel.getLevelName();
            Long organizationId = perfLevel.getOrganizationId();
            PerfLevel queryPerfLevel = new PerfLevel();
            queryPerfLevel.setLevelName(levelName);
            queryPerfLevel.setOrganizationId(organizationId);
            queryPerfLevel.setStatus(Enable.Y.toString());
            List<PerfLevel> oldPerfLevelList = getBaseMapper().selectList(new QueryWrapper<>(queryPerfLevel));
            if (isUpdate) { //修改
                if (1 == oldPerfLevelList.size() && null != oldPerfLevelList.get(0) && perfLevelId.compareTo(oldPerfLevelList.get(0).getLevelId()) != 0) {
                    Assert.isTrue(false, PerfLevelConst.LEVEL_NAME_AND_ORG_ID_SAME);
                }else if(1 < oldPerfLevelList.size()){
                    Assert.isTrue(false, PerfLevelConst.LEVEL_NAME_AND_ORG_ID_SAME);
                }
            } else {
                if (1 <= oldPerfLevelList.size()) {
                    Assert.isTrue(false, PerfLevelConst.LEVEL_NAME_AND_ORG_ID_SAME);
                }
            }
        }
    }

    /**
     * 当状态为启用时，校验得分区间不能重叠
     * @param perfLevel
     */
    @Override
    public void checkScoreOverlap(PerfLevel perfLevel) {
        if (YesOrNo.YES.getValue().equals(perfLevel.getStatus())) {
            List<PerfLevel> dbPerfLevels = new ArrayList<>();
            // 未经暂存
            if (Objects.isNull(perfLevel.getLevelId())) {
                dbPerfLevels = this.list(Wrappers.lambdaQuery(PerfLevel.class)
                        .eq(PerfLevel::getStatus, YesOrNo.YES.getValue())
                );
            }
            // 更新操作
            else {
                dbPerfLevels = this.list(Wrappers.lambdaQuery(PerfLevel.class)
                        .eq(PerfLevel::getStatus, YesOrNo.YES.getValue())
                        .ne(PerfLevel::getLevelId, perfLevel.getLevelId())
                );
            }
            BigDecimal scoreStart = perfLevel.getScoreStart();
            BigDecimal scoreEnd = perfLevel.getScoreEnd();
            if (CollectionUtils.isNotEmpty(dbPerfLevels)) {
                dbPerfLevels.forEach(dbPerfLevel -> {
                    BigDecimal dbScoreStart = dbPerfLevel.getScoreStart();
                    BigDecimal dbScoreEnd = dbPerfLevel.getScoreEnd();
                    // 左边开始节点在右边区间
                    boolean case1 = (scoreStart.compareTo(dbScoreStart) > 0) && (scoreStart.compareTo(dbScoreEnd) < 0);
                    // 右边开始节点在左边区间
                    boolean case2 = (dbScoreStart.compareTo(scoreStart) > 0) && (dbScoreStart.compareTo(scoreEnd) < 0);
                    // 开始节点相等
                    boolean case3 = (scoreStart.compareTo(dbScoreStart) == 0);
                    // 结束节点相等
                    boolean case4 = (scoreEnd.compareTo(dbScoreEnd) == 0);
                    if (case1 || case2 || case3 || case4) {
                        String levelName = dbPerfLevel.getLevelName();
                        StringBuffer sb = new StringBuffer();
                        sb.append("绩效等级得分重叠，已存在等级名称：[").append(levelName).append("]，已有得分区间：[")
                                .append(dbScoreStart).append("至").append(dbScoreEnd).append("]，请调整后重试。");
                        throw new BaseException(LocaleHandler.getLocaleMsg(sb.toString()));
                    }
                });
            }
        }
    }

    /**
     * Description 检查绩效等级信息能否保存
     * @Param perfLevel 绩效等级实体类
     * @Param isUpdate 是否修改，是则为true
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.03
     * @throws Assert
     **/
    private void checkSaveOrUpdatePerfLevel(PerfLevel perfLevel, boolean isUpdate){
        Assert.notNull(perfLevel, ResultCode.MISSING_SERVLET_REQUEST_PARAMETER.getMessage());
        this.checkRepeatLevelNameAndOrgId(perfLevel, isUpdate);
        Assert.notNull(perfLevel.getLevelId(), "id不能为空");
        Assert.notNull(perfLevel.getLevelName(), PerfLevelConst.LEVEL_NAME_NOT_NULL);
        Assert.notNull(perfLevel.getLevelDescription(), PerfLevelConst.LEVEL_DESCRIPTION_NOT_NULL);
        Assert.notNull(perfLevel.getScoreStart(), PerfLevelConst.SCORE_START_NOT_NULL);
        Assert.notNull(perfLevel.getScoreEnd(), PerfLevelConst.SCORE_END_NOT_NULL);
        Assert.notNull(perfLevel.getStatus(), PerfLevelConst.STATUS_NOT_NULL);
        // 当状态为启用时，校验得分区间不能重叠
        this.checkScoreOverlap(perfLevel);
        Assert.notNull(perfLevel.getOrganizationId(), PerfLevelConst.ORGANIZATION_ID_NOT_NULL);
        Assert.notNull(perfLevel.getOrganizationName(), PerfLevelConst.ORGANIZATION_NAME_NOT_NULL);
    }

    /**
     * Description 通过组织名称获取组织信息
     * @param organizations 当前用户所属的组织
     * @param organizationName 组织名称
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.03
     * @throws
     **/
    private OrganizationUser getOrganization(List<OrganizationUser> organizations, String organizationName) {
        for (OrganizationUser organizationUser : organizations) {
            if (null != organizationUser && StringUtils.equals(organizationName, organizationUser.getOrganizationName())) {
                return organizationUser;
            }
        }
        return null;
    }

}
