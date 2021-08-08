package com.midea.cloud.srm.base.ou.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.controller.BaseCheckController;
import com.midea.cloud.srm.base.ou.service.IBaseOuGroupService;
import com.midea.cloud.srm.model.base.formula.enums.StuffStatus;
import com.midea.cloud.srm.model.base.ou.dto.create.BaseOuGroupCreateDTO;
import com.midea.cloud.srm.model.base.ou.dto.query.BaseOuGroupQueryDTO;
import com.midea.cloud.srm.model.base.ou.dto.update.BaseOuGroupUpdateDTO;
import com.midea.cloud.srm.model.base.ou.entity.BaseOuDetail;
import com.midea.cloud.srm.model.base.ou.entity.BaseOuGroup;
import com.midea.cloud.srm.model.base.ou.vo.BaseOuBuInfoVO;
import com.midea.cloud.srm.model.base.ou.vo.BaseOuGroupDetailVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Objects;


/**
 * <pre>
 *  ou组信息表 前端控制器
 * </pre>
 *
 * @author tanjl11@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-04 19:53:34
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/base/base-ou-group")
@Slf4j
public class BaseOuGroupController extends BaseCheckController {

    @Autowired
    private IBaseOuGroupService iBaseOuGroupService;


    /**
     * 根据UO组编码查找OU组
     *
     * @param ouCodes
     * @return
     */
    @PostMapping("/queryOuByOuCodeList")
    public List<BaseOuGroup> queryOuByOuCodeList(@RequestBody List<String> ouCodes) {
        if (CollectionUtils.isNotEmpty(ouCodes)) {
            List<BaseOuGroup> ouGroups = iBaseOuGroupService.list(new QueryWrapper<BaseOuGroup>().in("OU_GROUP_CODE", ouCodes));
            return ouGroups;
        } else {
            return null;
        }
    }

    @PostMapping("/create")
    public BaseOuGroupDetailVO create(@Valid @RequestBody BaseOuGroupCreateDTO dto, BindingResult result) {
        checkParamBeforeHandle(log, result, dto);
        return iBaseOuGroupService.createOuGroup(dto);
    }

    @PostMapping("/updateById")
    public BaseOuGroupDetailVO update(@Valid @RequestBody BaseOuGroupUpdateDTO dto, BindingResult result) {
        checkParamBeforeHandle(log, result, dto);
        return iBaseOuGroupService.updateOuGroup(dto);
    }

    @GetMapping("/queryById")
    public BaseOuGroupDetailVO queryById(@RequestParam Long id) {
        return iBaseOuGroupService.queryOuGroupById(id);
    }

    @PostMapping("/queryByPage")
    public PageInfo<BaseOuGroupDetailVO> queryByPage(@RequestBody BaseOuGroupQueryDTO queryDTO) {
        return iBaseOuGroupService.queryOuDetailByPage(queryDTO);
    }

    @PostMapping("/queryOuDetailByDto")
    public List<BaseOuGroupDetailVO> queryOuDetailByDto(@RequestBody BaseOuGroupQueryDTO queryDTO) {
        return iBaseOuGroupService.queryOuDetailByDto(queryDTO);
    }

    @GetMapping("/deleteById")
    public Boolean deleteById(@RequestParam Long id) {
        return iBaseOuGroupService.deleteOuGroupById(id);
    }

    @GetMapping("/queryGroupInfoById")
    public BaseOuGroupDetailVO queryId(@RequestParam Long id) {
        return iBaseOuGroupService.queryGroupInfoById(id);
    }

    @PostMapping("/queryGroupInfoByIds")
    public List<BaseOuGroupDetailVO> queryGroupInfoByIds(@RequestBody Collection<Long> ids) {
        return iBaseOuGroupService.queryGroupInfoByIds(ids);
    }

    @PostMapping("/queryGroupInfoDetailByIds")
    public List<BaseOuGroupDetailVO> queryGroupInfoDetailByIds(@RequestBody Collection<Long> ids) {
        return iBaseOuGroupService.queryGroupInfoDetailByIds(ids);
    }

    @GetMapping("/queryBuInfoByOrgId")
    public BaseOuBuInfoVO queryBuInfoByOrgId(@RequestParam Long orgId) {
        if (Objects.isNull(orgId)) {
            return BaseOuBuInfoVO.builder().build();
        }
        return iBaseOuGroupService.queryBuInfoByOrgId(orgId);
    }

    /**
     * 根据OU组编码查询详情
     *
     * @param ouGroupCode
     * @return
     */
    @GetMapping("/queryBaseOuDetailByCode")
    BaseOuDetail queryBaseOuDetailByCode(@RequestParam("ouGroupCode") String ouGroupCode) {
        return iBaseOuGroupService.queryBaseOuDetailByCode(ouGroupCode);
    }


    /**
     * 生效ou组
     */
    @GetMapping("/activeGroup")
    public void activeGroup(@RequestParam("groupId") Long groupId) {
       updateStatus(groupId,StuffStatus.ACTIVE);
    }

    /**
     * 失效ou组
     */
    @GetMapping("/invalidGroup")
    public void invalidGroup(@RequestParam("groupId") Long groupId) {
        updateStatus(groupId,StuffStatus.INVAILD);
    }

    /**
     * 废弃ou组
     */
    @GetMapping("/abandonGroup")
    public void abandonGroup(@RequestParam("groupId") Long groupId) {
        updateStatus(groupId,StuffStatus.ABANDON);
    }

    private void updateStatus(Long groupId, StuffStatus status) {
        iBaseOuGroupService.update(Wrappers.lambdaUpdate(BaseOuGroup.class)
                .set(BaseOuGroup::getGroupStatus, status.getStatus())
                .eq(BaseOuGroup::getOuGroupId, groupId)
        );
    }

}
