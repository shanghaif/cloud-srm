package com.midea.cloud.srm.bargaining.purchaser.projectmanagement.techscore.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.constants.BidConstant;
import com.midea.cloud.common.enums.bargaining.projectmanagement.evaluation.OrderStatusEnum;
import com.midea.cloud.common.enums.bargaining.projectmanagement.techscore.ScoreStatusEnum;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidinitiating.service.IBidVendorService;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidinitiating.service.IBidingService;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidinitiating.service.IGroupService;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.techproposal.service.IOrderHeadService;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.techscore.mapper.TechScoreHeadMapper;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.techscore.mapper.TechScoreLineMapper;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.techscore.service.ITechScoreHeadService;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.entity.BidVendor;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.entity.Biding;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.entity.Group;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.techproposal.entity.OrderHead;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.techscore.entity.TechScoreHead;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.techscore.entity.TechScoreLine;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.techscore.param.TechScoreQueryParam;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.techscore.param.TechScoreSaveHeadParam;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.techscore.param.TechScoreSaveLineParam;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.techscore.vo.TechScoreHeadVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <pre>
 * 技术评分头表
 * </pre>
 *
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020年3月19日 下午7:15:45
 *  修改内容:
 *          </pre>
 */
@Service
public class TechScoreHeadServiceImpl extends ServiceImpl<TechScoreHeadMapper, TechScoreHead> implements ITechScoreHeadService {

    @Autowired
    private TechScoreLineMapper techScoreLineMapper;

    @Autowired
    private IOrderHeadService iOrderHeadService;

    @Autowired
    private IBidVendorService iBidVendorService;

    @Autowired
    private IGroupService iGroupService;

    @Autowired
    private IBidingService iBidingService;

    @Override
    public PageInfo<TechScoreHeadVO> listPage(TechScoreQueryParam queryParam) {
        if (Objects.isNull(queryParam.getOperateUserId())) {
            queryParam.setOperateUserId(AppUserUtil.getLoginAppUser().getUserId());
        }
        Assert.notNull(queryParam.getBidingId(), "招投标ID不能为空");
        List<TechScoreHeadVO> techScoreHeadVoList = new ArrayList<TechScoreHeadVO>();
        // 第一轮投标的供应商
        List<OrderHead> orderHeadList = iOrderHeadService.list(new QueryWrapper<OrderHead>(new OrderHead().setBidingId(queryParam.getBidingId()).setRound(BidConstant.FIRST_ROUND).setOrderStatus(OrderStatusEnum.SUBMISSION.getValue())));
        if (orderHeadList.size() > 0) {
            List<Long> bidVendorIdList = orderHeadList.stream().map(OrderHead::getBidVendorId).collect(Collectors.toList());
            Assert.notEmpty(bidVendorIdList, "投标头表供应商ID不能为空");
            List<BidVendor> vendorList = iBidVendorService.listByIds(bidVendorIdList);
            // 当前登录专家，已经评审的记录
            List<TechScoreHead> techScoreHeadList = this.list(new QueryWrapper<>(new TechScoreHead().setBidingId(queryParam.getBidingId()).setCreatedId(queryParam.getOperateUserId()).setScoreStatus(ScoreStatusEnum.FINISHED.getValue())));
            //kuangzm 获取评分组设置
            List<Group> groups =  iGroupService.list(new QueryWrapper<>(new Group().setBidingId(queryParam.getBidingId()).setUserId(queryParam.getOperateUserId())));
            
            Map<Long, TechScoreHead> techScoreHeadMap = techScoreHeadList.stream().collect(Collectors.toMap(TechScoreHead::getBidVendorId, Function.identity(), (k1, k2) -> k1));
            for (BidVendor vendor : vendorList) {
                TechScoreHeadVO techScoreHeadVO = new TechScoreHeadVO();
                techScoreHeadVO.setBidVendorId(vendor.getBidVendorId()).setVendorId(vendor.getVendorId()).setVendorCode(vendor.getVendorCode()).setVendorName(vendor.getVendorName()).setPhone(vendor.getPhone()).setEmail(vendor.getEmail()).setLinkManName(vendor.getLinkManName());
                techScoreHeadVO.setScoreStatus(techScoreHeadMap.containsKey(vendor.getBidVendorId()) ? ScoreStatusEnum.FINISHED.getValue() : ScoreStatusEnum.UNFINISHED.getValue());
                if (null != groups && groups.size() > 0 && null != groups.get(0).getMaxEvaluateScore()) {
                	techScoreHeadVO.setMaxEvaluateScore(groups.get(0).getMaxEvaluateScore());
                }
                
                techScoreHeadVoList.add(techScoreHeadVO);
            }
        }
        return PageUtil.pagingByFullData(queryParam.getPageNum(), queryParam.getPageSize(), techScoreHeadVoList);
    }

    @Override
    public void saveOrUpdateTechScore(TechScoreSaveHeadParam param) {

        // 校验 - 是否有权限操作技术评分
        validateOperateTechScore(param);

        TechScoreHead techScoreHead = this.getOne(new QueryWrapper<>(new TechScoreHead().setBidingId(param.getBidingId()).setCreatedId(param.getOperateUserId()).setBidVendorId(param.getBidVendorId())));
        if (techScoreHead == null) {
            techScoreHead = new TechScoreHead().setTechScoreHeadId(IdGenrator.generate());
            techScoreHead.setCreatedId(param.getOperateUserId());
            techScoreHead.setCreatedBy(param.getOperateUsername());
            techScoreHead.setCreationDate(new Date());
            techScoreHead.setCreatedByIp("");
            techScoreHead.setLastUpdateDate(new Date());
        }
//        Assert.isTrue(ScoreStatusEnum.get(techScoreHead.getScoreStatus()) != ScoreStatusEnum.FINISHED, "已提交评审，无需重复提交");
        if ("SAVE".equals(param.getType())) {
            techScoreHead.setScoreStatus(ScoreStatusEnum.UNFINISHED.getValue());
        } else if ("SUBMIT".equals(param.getType())) {
            techScoreHead.setScoreStatus(ScoreStatusEnum.FINISHED.getValue());
        }
        techScoreHead.setBidingId(param.getBidingId());
        techScoreHead.setBidVendorId(param.getBidVendorId());
        techScoreHead.setTechComments(param.getTechComments());
        this.saveOrUpdate(techScoreHead);
        techScoreLineMapper.delete(new QueryWrapper<>(new TechScoreLine().setTechScoreHeadId(techScoreHead.getTechScoreHeadId())));// 删除旧的评分记录
        for (TechScoreSaveLineParam line : param.getLineList()) {
            TechScoreLine techScoreLine = new TechScoreLine();
            techScoreLine.setTechScoreLineId(IdGenrator.generate()).setBidingId(param.getBidingId()).setTechScoreHeadId(techScoreHead.getTechScoreHeadId()).setRuleLineId(line.getRuleLineId()).setScore(line.getScore());
            techScoreLineMapper.insert(techScoreLine);
        }
    }

    /**
     * 校验 - 是否有权限操作技术评分
     *
     * @param parameter 校验参数
     */
    protected void validateOperateTechScore(TechScoreSaveHeadParam parameter) {
        if (Objects.isNull(parameter.getOperateUserId())) {
            parameter.setOperateUserId(AppUserUtil.getLoginAppUser().getUserId());
        }
        Assert.notNull(parameter.getBidingId(), "招投标ID不能为空");
        Assert.notNull(parameter.getBidVendorId(), "供应商ID不存在");

        Biding biding = iBidingService.getById(parameter.getBidingId());
        Assert.notNull(biding, "招投标ID不存在");

        Assert.isTrue("Y".equals(biding.getTechOpenBid()), "未技术开标，不能评分");

        // 若代理人与单据创建人一致，则校验通过
        if (parameter.getProxyOperateUserId() != null && parameter.getProxyOperateUserId().equals(biding.getCreatedId()))
            return;

        // 获取 当前操作人的工作小组信息
        Group group = iGroupService.getOne(
                Wrappers.lambdaQuery(Group.class)
                        .eq(Group::getBidingId, parameter.getBidingId())
                        .eq(Group::getUserId, parameter.getOperateUserId())
        );
        if (group == null)
            throw new BaseException("不属于工作小组，无法评审");

        // 若是第一负责人，则校验通过
        if ("Y".equals(group.getIsFirstResponse()))
            return;

        if (!"Y".equals(group.getJudgeFlag()))
            throw new BaseException("不属于第一负责人或技术评委，无法评审");
    }
}
