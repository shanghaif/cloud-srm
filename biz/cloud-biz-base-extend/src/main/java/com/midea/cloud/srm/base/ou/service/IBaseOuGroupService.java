package com.midea.cloud.srm.base.ou.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.base.ou.dto.create.BaseOuGroupCreateDTO;
import com.midea.cloud.srm.model.base.ou.dto.query.BaseOuGroupQueryDTO;
import com.midea.cloud.srm.model.base.ou.dto.update.BaseOuGroupUpdateDTO;
import com.midea.cloud.srm.model.base.ou.entity.BaseOuDetail;
import com.midea.cloud.srm.model.base.ou.entity.BaseOuGroup;
import com.midea.cloud.srm.model.base.ou.vo.BaseOuBuInfoVO;
import com.midea.cloud.srm.model.base.ou.vo.BaseOuGroupDetailVO;

import java.util.Collection;
import java.util.List;

/**
*  <pre>
 *  ou组信息表 服务类
 * </pre>
*
* @author tanjl11@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-04 19:53:34
 *  修改内容:
 * </pre>
*/
public interface IBaseOuGroupService extends IService<BaseOuGroup> {
    /**
     * 新增ou组
     * @param dto
     * @return
     */
    BaseOuGroupDetailVO createOuGroup(BaseOuGroupCreateDTO dto);

    /**
     * 删除ou组
     */
    Boolean deleteOuGroupById(Long id);
    /**
     * 查询
     */
    PageInfo<BaseOuGroupDetailVO> queryOuDetailByPage(BaseOuGroupQueryDTO dto);
    /**
     * 查询
     */
    List<BaseOuGroupDetailVO> queryOuDetailByDto(BaseOuGroupQueryDTO dto);
    /**
     * 修改
     */
    BaseOuGroupDetailVO updateOuGroup(BaseOuGroupUpdateDTO dto);
    /**
     * 根据id查询
     */
    BaseOuGroupDetailVO queryOuGroupById(Long id);

    /**
     * 根据id查询组信息
     * @param id
     * @return
     */
    BaseOuGroupDetailVO queryGroupInfoById(Long id);

    /**
     * 根据idList返回组对象
     */
    List<BaseOuGroupDetailVO> queryGroupInfoByIds(Collection<Long> idsList);

    List<BaseOuGroupDetailVO> queryGroupInfoDetailByIds(Collection<Long> idList);

    BaseOuBuInfoVO queryBuInfoByOrgId(Long orgId);

    /**
     * 根据ou组编码查询详情
     * @param ouGroupCode
     * @return
     */
    BaseOuDetail queryBaseOuDetailByCode(String ouGroupCode);
}
