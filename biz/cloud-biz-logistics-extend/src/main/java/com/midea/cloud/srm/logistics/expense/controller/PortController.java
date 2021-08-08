package com.midea.cloud.srm.logistics.expense.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.logistics.LogisticsStatus;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.logistics.expense.service.IPortService;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.logistics.expense.entity.Port;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
*  <pre>
 *  港口信息维护表 前端控制器
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-11-26 11:31:42
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/logistics/port")
public class PortController extends BaseController {

    @Autowired
    private IPortService iPortService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public Port get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iPortService.getById(id);
    }

    /**
    * 新增
    * @param port
    */
    @PostMapping("/add")
    public void add(@RequestBody Port port) {
        Long id = IdGenrator.generate();
        port.setPortId(id);
        iPortService.save(port);
    }

    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iPortService.removeById(id);
    }

    /**
    * 修改
    * @param port
    */
    @PostMapping("/modify")
    public void modify(@RequestBody Port port) {
        iPortService.updateById(port);
    }

    /**
    * 分页条件查询
    * @param port
    * @return
    */
    @PostMapping("/listPageByParam")
    public PageInfo<Port> listPageByParam(@RequestBody Port port) {
        PageUtil.startPage(port.getPageNum(), port.getPageSize());
        return new PageInfo<Port>(iPortService.listPageByParam(port));
    }

    /**
     * 保存港口数据(单条)
     * @param port
     */
    @PostMapping("/savePort")
    public void savePort(@RequestBody Port port) {
        Optional.ofNullable(port).orElseThrow(() -> new BaseException(LocaleHandler.getLocaleMsg("要保存的港口数据为空.")));
        iPortService.checkNotEmptyBeforeSave(port);
        iPortService.savePort(port);
    }

    /**
     * 保存港口数据(批量)
     * @param ports
     */
    @PostMapping("/savePorts")
    public void savePorts(@RequestBody List<Port> ports) {
        Assert.isTrue(CollectionUtils.isNotEmpty(ports), "要保存的港口数据为空.");
        ports.forEach(port -> {
            iPortService.checkNotEmptyBeforeSave(port);
        });
        iPortService.savePorts(ports);
    }

    /**
     * 通过id删除港口(单条删除)
     * @param portId
     */
    @GetMapping("/deleteById")
    public void deleteById(@RequestParam("portId") Long portId) {
        checkPortId(portId);
        Assert.isTrue(Objects.equals(LogisticsStatus.DRAFT.getValue(), iPortService.getById(portId).getStatus()), "只有拟定状态才可以删除.");
        iPortService.removeById(portId);
    }

    /**
     * 通过ids删除(批量删除)
     *
     * @param portIds
     */
    @PostMapping("/deleteByIds")
    public void deleteByIds(@RequestBody List<Long> portIds) {
        checkPortIds(portIds);
        checkPortsByIdsBeforeDelete(portIds);
        iPortService.removeByIds(portIds);
    }

    /**
     * 港口生效(单条)
     *
     * @param portId
     */
    @GetMapping("/effectivePort")
    public void effectivePort(@RequestParam("portId") Long portId) {
        checkPortId(portId);
        iPortService.updatePortStatus(portId, LogisticsStatus.EFFECTIVE.getValue());
    }

    /**
     * 港口生效(批量)
     *
     * @param portIds
     */
    @PostMapping("/effectivePorts")
    public void effectivePorts(@RequestBody List<Long> portIds) {
        checkPortIds(portIds);
        checkPortsByIdsBeforeEffective(portIds);
        iPortService.updatePortsStatus(portIds, LogisticsStatus.EFFECTIVE.getValue());
    }

    /**
     * 港口失效(单条)
     *
     * @param portId
     */
    @GetMapping("/inEffectivePort")
    public void inEffectivePort(@RequestParam("portId") Long portId) {
        checkPortId(portId);
        iPortService.updatePortStatus(portId, LogisticsStatus.INEFFECTIVE.getValue());
    }

    /**
     * 港口失效(批量)
     *
     * @param portIds
     */
    @PostMapping("/inEffectivePorts")
    public void InEffectivePorts(@RequestBody List<Long> portIds) {
        checkPortIds(portIds);
        checkPortsByIdsBeforeInEffective(portIds);
        iPortService.updatePortsStatus(portIds, LogisticsStatus.INEFFECTIVE.getValue());
    }

    /**
     * 港口id判空
     * 以及根据港口id是否在数据库能找到对应的港口数据
     *
     * @param portId
     */
    public void checkPortId(@RequestParam("portId") Long portId) {
        Optional.ofNullable(portId).orElseThrow(()->new BaseException(LocaleHandler.getLocaleMsg("港口id为空.")));
        Optional.ofNullable(iPortService.getById(portId)).orElseThrow(()->new BaseException(LocaleHandler.getLocaleMsg("无效的港口id: "+portId+", 找不到相应的港口.")));
    }

    /**
     * 港口ids判空(批量)
     * 以及港口id是否在数据库能找到相应的港口数据
     *
     * @param portIds
     */
    public void checkPortIds(List<Long> portIds) {
        Assert.isTrue(CollectionUtils.isNotEmpty(portIds), "要批量操作的港口为空, 请选择要操作的港口.");
        for (Long portId : portIds) {
            Optional.ofNullable(portId).orElseThrow(()->new BaseException(LocaleHandler.getLocaleMsg("批量操作的数据存在港口id为空的数据.")));
            Optional.ofNullable(iPortService.getById(portId))
                    .orElseThrow(()->new BaseException(LocaleHandler.getLocaleMsg("无效的港口id: "+portId+", 找不到相应的港口.")));
        }
    }

    /**
     * 港口批量删除前校验
     *
     * @param portIds
     */
    public void checkPortsByIdsBeforeDelete(List<Long> portIds) {
        for (Long portId : portIds) {
            iPortService.checkPortsByIdBeforeDelete(portId);
        }
    }

    /**
     * 批量生效前校验
     *
     * @param portIds
     */
    public void checkPortsByIdsBeforeEffective(List<Long> portIds) {
        for (Long portId : portIds) {
            iPortService.checkPortsByIdsBeforeEffective(portId);
        }
    }

    /**
     * 批量失效前校验
     *
     * @param portIds
     */
    public void checkPortsByIdsBeforeInEffective(List<Long> portIds) {
        for (Long portId : portIds) {
            iPortService.checkPortsByIdsBeforeInEffective(portId);
        }
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<Port> listAll() {
        return iPortService.list();
    }

    /**
     * 获取港口数据
     * @param portNameList
     * @return
     */
    @PostMapping("/listPortByNameBatch")
    List<Port> listPortByNameBatch(@RequestBody List<String> portNameList){
        return iPortService.listPortByNameBatch(portNameList);
    }

}
