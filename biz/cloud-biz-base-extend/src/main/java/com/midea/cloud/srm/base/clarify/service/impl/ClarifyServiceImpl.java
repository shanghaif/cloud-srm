package com.midea.cloud.srm.base.clarify.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.constants.SequenceCodeConstant;
import com.midea.cloud.common.enums.UserType;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.BeanCopyUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.base.clarify.mapper.SourcingClarifyFileMapper;
import com.midea.cloud.srm.base.clarify.mapper.SourcingClarifyMapper;
import com.midea.cloud.srm.base.clarify.mapper.SourcingClarifyReplyMapper;
import com.midea.cloud.srm.base.clarify.service.IClarifyService;
import com.midea.cloud.srm.feign.bargaining.BargainSourcingClarifyClient;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.base.IGetSourcingRelateInfo;
import com.midea.cloud.srm.feign.bid.BidSourcingClarifyClient;
import com.midea.cloud.srm.model.base.clarify.dto.*;
import com.midea.cloud.srm.model.base.clarify.entity.SourcingClarify;
import com.midea.cloud.srm.model.base.clarify.entity.SourcingClarifyFile;
import com.midea.cloud.srm.model.base.clarify.entity.SourcingClarifyReply;
import com.midea.cloud.srm.model.base.clarify.enums.ClarifySourcingType;
import com.midea.cloud.srm.model.base.clarify.enums.ClarifyStatus;
import com.midea.cloud.srm.model.base.clarify.enums.ClarifyType;
import com.midea.cloud.srm.model.base.clarify.vo.*;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author tanjl11
 * @date 2020/10/07 21:17
 */
@Service
@AllArgsConstructor
public class ClarifyServiceImpl implements IClarifyService {
    private final SourcingClarifyFileMapper fileMapper;
    private final SourcingClarifyMapper clarifyMapper;
    private final SourcingClarifyReplyMapper replyMapper;
    private final BaseClient baseClient;
    private final BidSourcingClarifyClient bidClarifyClient;
    private final BargainSourcingClarifyClient bargainSourcingClarifyClient;

    /**
     * 发布澄清公告
     *
     * @param dto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SourcingClarifyDetailVO publishSourcingClarify(SourcingClarifyDto dto) {
        //暂存，并更改状态
        SourcingClarifyDetailVO detailVO = saveTemporarySourcingClarify(dto, ClarifyStatus.PUBLISH);
        //生成回复
        generateReplyForSourcing(detailVO, ClarifySourcingType.valueOf(dto.getSourcingType()), dto.getSourcingId());
        return detailVO;
    }

    /**
     * 根据id发布澄清
     *
     * @param clarifyId
     * @return
     */
    @Override
    public Boolean publishSourcingClarifyById(Long clarifyId) {
        String clarifyStatus = getExistsClarifyStatus(clarifyId);
        if (Objects.equals(clarifyStatus, ClarifyStatus.PUBLISH.getCode())) {
            throw new BaseException("澄清公告已发布!");
        }
        return clarifyMapper.update(null, Wrappers.lambdaUpdate(SourcingClarify.class)
                .set(SourcingClarify::getClarifyStatus, ClarifyStatus.PUBLISH.getCode())
                .eq(SourcingClarify::getClarifyId, clarifyId)
        ) == 1;
    }


    /**
     * 创建寻源公告
     *
     * @param dto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SourcingClarifyDetailVO saveTemporarySourcingClarify(SourcingClarifyDto dto, ClarifyStatus status) {
        SourcingClarify sourcingClarify = BeanCopyUtil.copyProperties(dto, SourcingClarify::new);
        sourcingClarify.setClarifyStatus(status.getCode());
        boolean isNew = Objects.isNull(dto.getClarifyId());
        if (isNew) {
            //生成clarify信息
            String code = baseClient.seqGen(SequenceCodeConstant.SEQ_SOURCING_CLARIFY);
            long clarifyId = IdGenrator.generate();
            sourcingClarify.setClarifyId(clarifyId);
            sourcingClarify.setClarifyNumber(code);
            clarifyMapper.insert(sourcingClarify);
        } else {
            clarifyMapper.updateById(sourcingClarify);
        }
        SourcingClarifyDetailVO detailVO = new SourcingClarifyDetailVO();
        SourcingClarifyVO clarifyVO = BeanCopyUtil.copyProperties(sourcingClarify, SourcingClarifyVO::new);
        detailVO.setClarify(clarifyVO);
        //生成文件详情列表，并赋值
        List<SourcingClarifyFileDto> files = dto.getFiles();
        List<SourcingClarifyFileVO> fileVos = getSourcingClarifyFileVOS(sourcingClarify.getClarifyId(), isNew, files, ClarifyType.NOTICE);
        detailVO.setFiles(fileVos);
        return detailVO;
    }

    /**
     * 删除寻源澄清
     *
     * @param sourcingClarifyId
     * @return
     */
    @Override
    public Boolean deleteSourcingClarifyById(Long sourcingClarifyId) {
        String existsClarifyStatus = getExistsClarifyStatus(sourcingClarifyId);
        if (Objects.equals(existsClarifyStatus, ClarifyStatus.PUBLISH)) {
            throw new BaseException("项目已发布，不允许删除");
        }
        //删除公告
        int count = clarifyMapper.deleteById(sourcingClarifyId);
        //删除附件
        fileMapper.delete(Wrappers.lambdaQuery(SourcingClarifyFile.class)
                .eq(SourcingClarifyFile::getClarifyId, sourcingClarifyId));
        return count == 1;
    }

    /**
     * 回复澄清
     *
     * @param dto
     * @return
     */
    @Override
    public SourcingClarifyReplyDetailVO replySourcingClarify(SourcingClarifyReplyDto dto) {
        SourcingClarifyReply reply = replyMapper.selectOne(Wrappers.lambdaQuery(SourcingClarifyReply.class)
                .select(SourcingClarifyReply::getClarifyReplyId, SourcingClarifyReply::getReplyStatus)
                .eq(SourcingClarifyReply::getClarifyReplyId, dto.getClarifyReplyId()));
        if (Objects.isNull(reply)) {
            throw new BaseException(String.format("id为%s的澄清回复不存在", dto.getClarifyReplyId()));
        }
        if (Objects.equals(reply.getReplyStatus(), ClarifyStatus.ALREADYRESPONSE.getCode())) {
            throw new BaseException(String.format("该澄清已回复，不可更改"));
        }
        return saveTemporarySourcingClarifyReply(dto, ClarifyStatus.ALREADYRESPONSE);
    }

    /**
     * 正常情况下不会走到新建状态
     * 因为发布时候会把对应的供应商去生成初始回复
     *
     * @param dto
     * @param status
     * @return
     */
    @Override
    public SourcingClarifyReplyDetailVO saveTemporarySourcingClarifyReply(SourcingClarifyReplyDto dto, ClarifyStatus status) {
        SourcingClarifyReplyDetailVO detailVO = new SourcingClarifyReplyDetailVO();
        boolean isNew = Objects.isNull(dto.getClarifyId());
        //获取公告信息并赋值
        SourcingClarify clarify = clarifyMapper.selectById(dto.getClarifyId());
        SourcingClarifyReply reply = BeanCopyUtil.copyProperties(dto, SourcingClarifyReply::new);
        reply.setReplyStatus(status.getCode());
        BeanUtils.copyProperties(clarify, reply, BeanCopyUtil.getNullPropertyNames(reply));
        if (isNew) {
            LoginAppUser user = AppUserUtil.getLoginAppUser();
            reply.setCompanyCode(user.getCompanyCode());
            reply.setCompanyId(user.getCompanyId());
            reply.setCompanyName(user.getCompanyName());
            reply.setVendorId(user.getUserId());
            reply.setVendorName(user.getUsername());
            long replyId = IdGenrator.generate();
            reply.setClarifyReplyId(replyId);
            replyMapper.insert(reply);
        } else {
            replyMapper.updateById(reply);
        }
        detailVO.setReply(BeanCopyUtil.copyProperties(reply, SourcingClarifyReplyVO::new));
        //设置文件
        List<SourcingClarifyFileDto> files = dto.getFiles();
        List<SourcingClarifyFileVO> filesVO = getSourcingClarifyFileVOS(reply.getClarifyId(), isNew, files, ClarifyType.REPLY);
        detailVO.setFiles(filesVO);
        return detailVO;
    }

    /**
     * 查看供应商回复
     *
     * @param sourcingClarifyId
     * @return
     */
    public List<SourcingClarifyReplyVO> listSourcingReplyBySourcingClarifyId(Long sourcingClarifyId) {
        return replyMapper.selectList(Wrappers.lambdaQuery(SourcingClarifyReply.class)
                .eq(SourcingClarifyReply::getClarifyId, sourcingClarifyId)).stream()
                .map(e -> BeanCopyUtil.copyProperties(e, SourcingClarifyReplyVO::new))
                .collect(Collectors.toList());
    }

    /**
     * 获取公告列表
     * 供应商只能查询自己的行
     *
     * @param queryDto
     * @return
     */
    @Override
    public PageInfo<SourcingClarifyVO> listSourcingClarifyByPage(SourcingClarifyQueryDto queryDto) {
        ///看需求要不要根据角色查看数据范围
        /* LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        Long vendorId=null;
        if(Objects.equals(loginAppUser.getUserType(),UserType.VENDOR.name())){
            vendorId=loginAppUser.getUserId();
        }*/
        PageUtil.startPage(queryDto.getPageNum(), queryDto.getPageSize());
        List<SourcingClarifyVO> result = clarifyMapper.selectList(Wrappers.lambdaQuery(SourcingClarify.class)
                .eq(Objects.nonNull(queryDto.getClarifyNumber()), SourcingClarify::getClarifyNumber, queryDto.getClarifyNumber())
                .eq(Objects.nonNull(queryDto.getClarifyStatus()), SourcingClarify::getClarifyStatus, queryDto.getClarifyStatus())
                .eq(Objects.nonNull(queryDto.getClarifyTitle()), SourcingClarify::getClarifyTitle, queryDto.getClarifyTitle())
                .eq(Objects.nonNull(queryDto.getSourcingNumber()), SourcingClarify::getSourcingNumber, queryDto.getClarifyNumber())
                .eq(Objects.nonNull(queryDto.getSourcingProjectName()), SourcingClarify::getSourcingProjectName, queryDto.getSourcingProjectName())
                .eq(Objects.nonNull(queryDto.getSourcingType()), SourcingClarify::getSourcingType, queryDto.getSourcingType()))
                .stream().map(e -> BeanCopyUtil.copyProperties(e, SourcingClarifyVO::new)).collect(Collectors.toList());
        return new PageInfo<>(result);
    }

    /**
     * 供应商端根据公告id获取自己的回复详情
     *
     * @param clarifyId
     * @return
     */
    @Override
    public SourcingClarifyReplyDetailVO queryReplyDetailById(Long clarifyId) {
        LoginAppUser user = AppUserUtil.getLoginAppUser();
        if (!Objects.equals(user.getUserType(), UserType.VENDOR.name())) {
            throw new BaseException("当前用户不为供应商，无法操作");
        }
        Long vendorId = user.getUserId();
        SourcingClarifyReplyDetailVO vo = new SourcingClarifyReplyDetailVO();
        SourcingClarifyReply reply = replyMapper.selectOne(Wrappers.lambdaQuery(SourcingClarifyReply.class)
                .eq(SourcingClarifyReply::getClarifyId, clarifyId)
                .eq(SourcingClarifyReply::getVendorId, vendorId)
        );
        assignValue(vo, reply);
        return vo;
    }

    /**
     * 供应商点详情查看
     *
     * @param replyId
     * @return
     */
    @Override
    public SourcingClarifyReplyDetailVO queryReplyDetailByReplyId(Long replyId) {
        SourcingClarifyReplyDetailVO vo = new SourcingClarifyReplyDetailVO();
        SourcingClarifyReply reply = replyMapper.selectById(replyId);
        assignValue(vo, reply);
        return vo;
    }


    /**
     * 根据澄清id获取详情
     *
     * @param clarifyId
     * @return
     */
    @Override
    public SourcingClarifyVO queryClarifyDetailById(Long clarifyId) {
        SourcingClarifyVO clarifyVO = BeanCopyUtil.copyProperties(clarifyMapper.selectById(clarifyId), SourcingClarifyVO::new);
        List<SourcingClarifyReplyVO> replyVOS = replyMapper.selectList(Wrappers.lambdaQuery(SourcingClarifyReply.class)
                .eq(SourcingClarifyReply::getClarifyId, clarifyId)).stream().map(e -> BeanCopyUtil.copyProperties(e, SourcingClarifyReplyVO::new)).collect(Collectors.toList());
        clarifyVO.setReplys(replyVOS);
        return clarifyVO;
    }


    /**
     * 获取已存在的澄清公告的状态
     *
     * @param clarifyId
     * @return
     */
    private String getExistsClarifyStatus(Long clarifyId) {
        SourcingClarify clarify = clarifyMapper.selectOne(
                Wrappers.lambdaQuery(SourcingClarify.class)
                        .select(SourcingClarify::getClarifyStatus)
                        .eq(SourcingClarify::getClarifyId, clarifyId));
        String clarifyStatus = Optional.of(clarify).orElseThrow(() -> new BaseException("该澄清公告不存在"))
                .getClarifyStatus();
        return clarifyStatus;
    }


    private List<SourcingClarifyFileVO> getSourcingClarifyFileVOS(Long clarifyId, boolean isNew,
                                                                  List<SourcingClarifyFileDto> files, ClarifyType type) {
        List<SourcingClarifyFileVO> fileVos = null;
        if (isNew) {
            if (!CollectionUtils.isEmpty(files)) {
                fileVos = files.stream().map(e -> {
                    e.setClarifyFileId(IdGenrator.generate());
                    SourcingClarifyFile sourcingClarifyFile = BeanCopyUtil.copyProperties(e, SourcingClarifyFile::new);
                    sourcingClarifyFile.setClarifyType(type.getCode());
                    sourcingClarifyFile.setClarifyId(clarifyId);
                    fileMapper.insert(sourcingClarifyFile);
                    SourcingClarifyFileVO fileDetail = BeanCopyUtil.copyProperties(sourcingClarifyFile, SourcingClarifyFileVO::new);
                    return fileDetail;
                }).collect(Collectors.toList());
            }
            //更新操作
        } else {
            List<Long> waitForDeleteFileIds = fileMapper.selectList(Wrappers.lambdaQuery(SourcingClarifyFile.class)
                    .eq(SourcingClarifyFile::getClarifyId, clarifyId))
                    .stream().map(SourcingClarifyFile::getClarifyFileId).collect(Collectors.toList());
            //剩下的就是待删除的列表
            if (waitForDeleteFileIds.size() > 0) {
                for (int i = waitForDeleteFileIds.size() - 1; i >= 0; i--) {
                    boolean find = false;
                    Long judgeShouldDeleteId = waitForDeleteFileIds.get(i);
                    for (SourcingClarifyFileDto file : files) {
                        if (Objects.equals(file.getClarifyFileId(), judgeShouldDeleteId)) {
                            find = true;
                            break;
                        }
                    }
                    if (!find) {
                        waitForDeleteFileIds.remove(i);
                    }
                }
            }
            if (!CollectionUtils.isEmpty(waitForDeleteFileIds)) {
                fileMapper.deleteBatchIds(waitForDeleteFileIds);
            }
            //删除完以后，执行新增/修改的操作
            if (!CollectionUtils.isEmpty(files)) {
                fileVos = files.stream().map(file -> {
                            if (Objects.isNull(file.getClarifyFileId())) {
                                file.setClarifyFileId(IdGenrator.generate());
                                SourcingClarifyFile sourcingClarifyFile = BeanCopyUtil.copyProperties(file, SourcingClarifyFile::new);
                                sourcingClarifyFile.setClarifyType(ClarifyType.NOTICE.getCode());
                                sourcingClarifyFile.setClarifyId(clarifyId);
                                fileMapper.insert(sourcingClarifyFile);
                            } else {
                                SourcingClarifyFile sourcingClarifyFile = BeanCopyUtil.copyProperties(file, SourcingClarifyFile::new);
                                fileMapper.updateById(sourcingClarifyFile);
                            }
                            return BeanCopyUtil.copyProperties(file, SourcingClarifyFileVO::new);
                        }
                ).collect(Collectors.toList());
            }

        }
        return fileVos;
    }

    /**
     * 为寻源的供应商生成回复
     *
     * @param type
     * @param sourcingId
     */
    @Transactional(rollbackFor = Exception.class)
    protected void generateReplyForSourcing(SourcingClarifyDetailVO detailVO, ClarifySourcingType type, Long sourcingId) {
        IGetSourcingRelateInfo getSourcingVendorInfo = null;
        switch (type) {
            case RFQ:
                getSourcingVendorInfo = bargainSourcingClarifyClient;
                break;
            case TENDER:
                getSourcingVendorInfo = bidClarifyClient;
                break;
            default:
                break;
        }
        if (Objects.isNull(getSourcingVendorInfo)) {
            throw new BaseException("没有找到对应的寻源方式");
        }
        Collection<SourcingVendorInfo> sourcingVendorInfo = getSourcingVendorInfo
                .getSourcingVendorInfo(type.getItemName(), sourcingId);
        if (CollectionUtils.isEmpty(sourcingVendorInfo)) {
            return;
        }
        //生成回复
        sourcingVendorInfo.stream()
                .forEach(e -> {
                    SourcingClarifyReply reply = BeanCopyUtil.copyProperties(e, SourcingClarifyReply::new);
                    reply.setClarifyReplyId(IdGenrator.generate());
                    BeanUtils.copyProperties(detailVO.getClarify(), reply, BeanCopyUtil.getNullPropertyNames(reply));
                    replyMapper.insert(reply);
                });
    }

    /**
     * 设置回复信息和回复文件
     *
     * @param vo
     * @param reply
     */
    private void assignValue(SourcingClarifyReplyDetailVO vo, SourcingClarifyReply reply) {
        vo.setReply(BeanCopyUtil.copyProperties(reply, SourcingClarifyReplyVO::new));
        List<SourcingClarifyFileVO> clarifyFiles = fileMapper.selectList(Wrappers.lambdaQuery(SourcingClarifyFile.class)
                .eq(SourcingClarifyFile::getClarifyId, reply.getClarifyReplyId())
                .eq(SourcingClarifyFile::getClarifyType, ClarifyType.REPLY.getCode())
        ).stream().map(e -> BeanCopyUtil.copyProperties(e, SourcingClarifyFileVO::new)).collect(Collectors.toList());
        vo.setFiles(clarifyFiles);
    }

}
