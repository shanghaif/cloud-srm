package com.midea.cloud.srm.${codeVo.moduleName}.${packageName}.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.*;
import com.midea.cloud.srm.${codeVo.moduleName}.${packageName}.mapper.${classFileName}Mapper;
import com.midea.cloud.srm.${codeVo.moduleName}.${packageName}.service.${classFileName}Service;
import com.midea.cloud.srm.${codeVo.moduleName}.${packageName}.service.${lineClassFileName}Service;
import com.midea.cloud.srm.model.${codeVo.moduleName}.${packageName}.entity.${classFileName};
import com.midea.cloud.srm.model.${codeVo.moduleName}.${packageName}.entity.${lineClassFileName};
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;


/**
* <pre>
 *  测试 服务实现类
 * </pre>
*
* @author linsb@meicloud.com
* @version 1.00.00
*
* <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Jan 29, 2021 10:18:45 AM
 *  修改内容:
 * </pre>
*/
@Service
public class ${classFileName}ServiceImpl extends ServiceImpl<${classFileName}Mapper, ${classFileName}> implements ${classFileName}Service {
    @Resource
    private ${lineClassFileName}Service ${lineTargetName}Service;

    @Transactional
    public void batchUpdate(List<${classFileName}> ${targetName}List) {
        this.saveOrUpdateBatch(${targetName}List);
    }

    @Override
    public Long addOrUpdate(${classFileName} ${targetName}) {
        // 校验数据
        check${classFileName}(${targetName});
        Long ${targetName}Id = ${targetName}.get${pk?cap_first}();
        if(StringUtil.isEmpty(${targetName}Id)){
            ${targetName}.set${pk?cap_first}(IdGenrator.generate());
            this.save(${targetName});
        }else {
            this.updateById(${targetName});
        }
        // 保存行
        ${lineTargetName}Service.remove(new QueryWrapper<>(new ${lineClassFileName}().setQuotaHeadId(${targetName}Id)));
        List<${lineClassFileName}> ${lineTargetName}List = ${targetName}.get${lineClassFileName}List();
        if(CollectionUtils.isNotEmpty(${lineTargetName}List)){
            ${lineTargetName}List.forEach(${lineTargetName} -> {
                ${lineTargetName}.set${pk?cap_first}(${targetName}.get${pk?cap_first}());
                ${lineTargetName}.set${linePk?cap_first}(IdGenrator.generate());
            });
            ${lineTargetName}Service.saveBatch(${lineTargetName}List);
        }
        return ${targetName}.get${pk?cap_first}();
    }

    @Override
    public ${classFileName} getDetailById(Long ${targetName}Id) {
        Assert.notNull(${targetName}Id, "参数:${targetName}Id,不能为空");
        ${classFileName} ${targetName} = this.getById(${targetName}Id);
        Assert.notNull(${targetName},"找不到详情,${targetName}Id="+${targetName}Id);
        List<${lineClassFileName}> ${lineTargetName}s = ${lineTargetName}Service.list(new QueryWrapper<>(new ${lineClassFileName}().setQuotaHeadId(${targetName}Id)));
        ${targetName}.set${lineClassFileName}List(${lineTargetName}s);
        return ${targetName};
    }

    /**
     * 1、
     */
    public void check${classFileName}(${classFileName} ${targetName}){
        // 检查头信息 demo 麻烦修改
       // Assert.notNull(${targetName}.getCategoryName(),"xxxx不能为空");

    }

    @Override
    @Transactional
    public void batchDeleted(List<Long> ids) {
        this.removeByIds(ids);
    }

    @Override
    public PageInfo<${classFileName}> listPage(${classFileName} ${targetName}) {
        PageUtil.startPage(${targetName}.getPageNum(), ${targetName}.getPageSize());
        List<${classFileName}> ${targetName}s = get${classFileName}s(${targetName});
        return new PageInfo<>(${targetName}s);
    }

    public List<${classFileName}> get${classFileName}s(${classFileName} ${targetName}) {
        QueryWrapper<${classFileName}> wrapper = new QueryWrapper<>();
  //      wrapper.eq("NOTICE_ID",${targetName}.getNoticeId()); // 精准匹配
//        wrapper.like("TITLE",${targetName}.getTitle());      // 模糊匹配
//        wrapper.and(queryWrapper ->
//                queryWrapper.ge("CREATION_DATE",${targetName}.getStartDate()).
//                        le("CREATION_DATE",${targetName}.getEndDate())); // 时间范围查询
        return this.list(wrapper);
    }
}
