package com.midea.cloud.srm.logistics.expense.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.midea.cloud.common.enums.logistics.LogisticsStatus;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.logistics.expense.mapper.PortMapper;
import com.midea.cloud.srm.logistics.expense.service.IPortService;
import com.midea.cloud.srm.model.base.dict.dto.DictItemDTO;
import com.midea.cloud.srm.model.logistics.expense.entity.ExpenseItem;
import com.midea.cloud.srm.model.logistics.expense.entity.Port;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <pre>
 *  港口信息维护表 服务实现类
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-11-26 11:31:42
 *  修改内容:
 * </pre>
 */
@Service
public class PortServiceImpl extends ServiceImpl<PortMapper, Port> implements IPortService {

    @Autowired
    private BaseClient baseClient;

    /**
     * 条件查询
     *
     * @param port
     * @return
     */
    @Override
    public List<Port> listPageByParam(Port port) {
        List<Port> portList = this.list(Wrappers.lambdaQuery(Port.class)
                .like(StringUtils.isNotEmpty(port.getCountryNameZhs()), Port::getCountryNameZhs, port.getCountryNameZhs())
                .like(StringUtils.isNotEmpty(port.getCountryNameEn()), Port::getCountryNameEn, port.getCountryNameEn())
                .eq(StringUtils.isNotEmpty(port.getPortType()), Port::getPortType, port.getPortType())
                .like(StringUtils.isNotEmpty(port.getPortCode()), Port::getPortCode, port.getPortCode())
                .like(StringUtils.isNotEmpty(port.getPortNameZhs()), Port::getPortNameZhs, port.getPortNameZhs())
                .like(StringUtils.isNotEmpty(port.getPortNameEn()), Port::getPortNameEn, port.getPortNameEn())
                .eq(StringUtils.isNotEmpty(port.getStatus()), Port::getStatus, port.getStatus())
                .orderByDesc(Port::getLastUpdateDate));
        return portList;
    }

    /**
     * 保存前非空校验
     *
     * @param port
     */
    @Override
    public void checkNotEmptyBeforeSave(Port port) {
        if (StringUtils.isEmpty(port.getCountryCode()))
            throw new BaseException(LocaleHandler.getLocaleMsg("国家编号不能为空, 请选择/输入后重试."));
        if (StringUtils.isEmpty(port.getCountryNameZhs()))
            throw new BaseException(LocaleHandler.getLocaleMsg("国家中文名不能为空, 请选择/输入后重试."));
        if (StringUtils.isEmpty(port.getCountryNameEn()))
            throw new BaseException(LocaleHandler.getLocaleMsg("国家英文名不能为空, 请选择/输入后重试."));
        if (StringUtils.isEmpty(port.getPortCode()))
            throw new BaseException(LocaleHandler.getLocaleMsg("港口代码不能为空, 请选择/输入后重试."));
        if (StringUtils.isEmpty(port.getPortNameZhs()))
            throw new BaseException(LocaleHandler.getLocaleMsg("港口中文名不能为空, 请选择/输入后重试."));
        if (StringUtils.isEmpty(port.getPortNameZhs()))
            throw new BaseException(LocaleHandler.getLocaleMsg("港口英文名不能为空, 请选择/输入后重试."));
        if (StringUtils.isEmpty(port.getPortType()))
            throw new BaseException(LocaleHandler.getLocaleMsg("港口类型不能为空, 请选择/输入后重试."));

    }

    /**
     * 数据删除前校验 是否都是拟定状态
     *
     * @param portId
     */
    @Override
    public void checkPortsByIdBeforeDelete(Long portId) {
        Port port = this.getById(portId);
        if (!Objects.equals(port.getStatus(), LogisticsStatus.DRAFT.getValue())) {
            // 获取港口代码
            String portCode = port.getPortCode();
            // 获取港口类型字典条目码对应的字典条目名称
            String portTypeDictItemName = getDictItemNameByDictCodeAndDictCode("PORT_TYPE", port.getPortType());
            StringBuffer sb = new StringBuffer();
            sb.append("您选择的港口代码为:[").append(portCode).append("], 港口类型为:[").append(portTypeDictItemName).append("]的数据, 不是拟定状态, 不能删除.");
            throw new BaseException(sb.toString());
        }
    }

    /**
     * 生效前校验
     *
     * @param portId
     */
    @Override
    public void checkPortsByIdsBeforeEffective(Long portId) {
        Port port = this.getById(portId);
        if (!(Objects.equals(port.getStatus(), LogisticsStatus.DRAFT.getValue()) || Objects.equals(port.getStatus(), LogisticsStatus.INEFFECTIVE.getValue()))) {
            // 获取港口代码
            String portCode = port.getPortCode();
            // 获取港口类型字典条目码对应的字典条目名称
            String portTypeDictItemName = getDictItemNameByDictCodeAndDictCode("PORT_TYPE", port.getPortType());
            StringBuffer sb = new StringBuffer();
            sb.append("您选择的港口代码为:[").append(portCode).append("], 港口类型为:[").append(portTypeDictItemName).append("]的数据, 不是拟定或失效状态, 不能进行生效操作.");
            throw new BaseException(sb.toString());
        }
    }

    /**
     * 失效前校验
     *
     * @param portId
     */
    @Override
    public void checkPortsByIdsBeforeInEffective(Long portId) {
        Port port = this.getById(portId);
        if (!Objects.equals(port.getStatus(), LogisticsStatus.EFFECTIVE.getValue())) {
            // 获取港口代码
            String portCode = port.getPortCode();
            // 获取港口类型字典条目码对应的字典条目名称
            String portTypeDictItemName = getDictItemNameByDictCodeAndDictCode("PORT_TYPE", port.getPortType());
            StringBuffer sb = new StringBuffer();
            sb.append("您选择的港口代码为:[").append(portCode).append("], 港口类型为:[").append(portTypeDictItemName).append("]的数据, 不是生效状态, 不能进行失效操作.");
            throw new BaseException(sb.toString());
        }
    }

    /**
     * 保存港口数据(批量)
     *
     * @param ports
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void savePorts(List<Port> ports) {
        // 批量保存前校验, 校验前端传入的列表是否有重复的数据
        checkRepeatPorts(ports);
        // 批量保存前校验, 校验要保存的数据和数据库中数据是否重复
        checkDbRepeatPorts(ports);

        // 区分出哪些是要新增的, 哪些是要更新的
        List<Port> saveList = new ArrayList<>();
        List<Port> updateList = new ArrayList<>();

        ports.forEach(port -> {
            if (null == port.getPortId()) {
                port.setPortId(IdGenrator.generate()).setStatus(LogisticsStatus.DRAFT.getValue());
                saveList.add(port);
            } else
                updateList.add(port);
        });

        if (CollectionUtils.isNotEmpty(updateList))
            this.updateBatchById(updateList);
        if (CollectionUtils.isNotEmpty(saveList))
            this.saveBatch(saveList);

    }

    /**
     * 保存港口数据
     *
     * @param port
     */
    @Override
    public void savePort(Port port) {
        if (null == port.getPortId()) {
            port.setPortId(IdGenrator.generate()).setStatus(LogisticsStatus.DRAFT.getValue());
            this.save(port);
        } else {
            this.updateById(port);
        }
    }

    /**
     * 校验前端传入的数据是否存在重复
     *
     * @param ports
     */
    public void checkRepeatPorts(List<Port> ports) {
        Map<String, List<Port>> collect = ports.stream().collect(Collectors.groupingBy(x -> getPortKey(x)));
        ports.forEach(port -> {
            String key = getPortKey(port);
            if (collect.containsKey(key) && collect.get(key).size() > 1) {
                throw new BaseException("需要保存的数据存在重复, 请检查后重试");
            }
        });
    }

    /**
     * 校验要保存的数据和数据库中的数据是否存在重复
     *
     * @param ports
     */
    public void checkDbRepeatPorts(List<Port> ports) {
        ports = ports.stream().filter(x->null==x.getPortId()).collect(Collectors.toList());
        for (Port port : ports) {
            List<Port> portList = this.list(Wrappers.lambdaQuery(Port.class)
                    .eq(Port::getPortCode, port.getPortCode())
                    .eq(Port::getPortType, port.getPortType()));
            if (CollectionUtils.isNotEmpty(portList)) {
                // 获取港口代码
                String portCode = port.getPortCode();
                // 获取港口类型字典条目码对应的字典条目名称
                String portTypeDictItemName = getDictItemNameByDictCodeAndDictCode("PORT_TYPE", port.getPortType());
                StringBuffer sb = new StringBuffer();
                sb.append("您选择的港口代码为:[").append(portCode).append("], 港口类型为:[").append(portTypeDictItemName).append("]的数据, 在数据库已存在.");
                throw new BaseException(LocaleHandler.getLocaleMsg(sb.toString()));
            }
        }
    }

    /**
     * 更新港口数据状态
     *
     * @param portId
     * @param status
     */
    @Override
    public void updatePortStatus(Long portId, String status) {
        Port port = this.getById(portId);
        port.setStatus(status);
        this.updateById(port);
    }

    /**
     * 批量更新港口状态
     *
     * @param portIds
     * @param status
     */
    @Override
    public void updatePortsStatus(List<Long> portIds, String status) {
        List<Port> portList = new ArrayList<>();
        portIds.forEach(portId -> {
            Port port = this.getById(portId);
            port.setStatus(status);
            portList.add(port);
        });
        this.updateBatchById(portList);
    }

    @Override
    public List<Port> listPortByNameBatch(List<String> portNameList) {
        if(CollectionUtils.isEmpty(portNameList)){
            return Collections.EMPTY_LIST;
        }
        QueryWrapper<Port> wrapper = new QueryWrapper<>();
        //港口中文名
        wrapper.in("PORT_NAME_ZHS", portNameList);
        return this.list(wrapper);
    }

    /**
     * 根据字典编码和字典条目编码获取字典条目名称
     */
    public String getDictItemNameByDictCodeAndDictCode(String dictCode, String dictItemCode) {
        List<DictItemDTO> dictItems = baseClient.listAllByParam(
                new DictItemDTO().setDictCode(dictCode).setDictItemCode(dictItemCode)
        );
        DictItemDTO dictItemDTO = CollectionUtils.isNotEmpty(dictItems) ? dictItems.get(0) : null;
        String dictItemName = StringUtils.isNotEmpty(dictItemDTO.getDictItemName()) ? dictItemDTO.getDictItemName() : "";
        return dictItemName;
    }

    /**
     * 根据几个字段 唯一标识一条港口数据
     */
    public String getPortKey(Port port) {
        StringBuffer sb = new StringBuffer();
        sb.append(port.getPortCode()).append("-")
                .append(port.getPortType());
        return sb.toString();
    }
}
