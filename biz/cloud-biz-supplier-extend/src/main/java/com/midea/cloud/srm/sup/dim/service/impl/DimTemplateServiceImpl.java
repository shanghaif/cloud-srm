package com.midea.cloud.srm.sup.dim.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.supplier.dim.dto.DimConfigDTO;
import com.midea.cloud.srm.model.supplier.dim.dto.DimTemplateDTO;
import com.midea.cloud.srm.model.supplier.dim.entity.DimTemplate;
import com.midea.cloud.srm.sup.dim.mapper.DimTemplateMapper;
import com.midea.cloud.srm.sup.dim.service.IDimConfigService;
import com.midea.cloud.srm.sup.dim.service.IDimTemplateService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
*  <pre>
 *  供应商模板配置表 服务实现类
 * </pre>
*
* @author zhuwl7@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-02-29 14:35:31
 *  修改内容:
 * </pre>
*/
@Service
public class DimTemplateServiceImpl extends ServiceImpl<DimTemplateMapper, DimTemplate> implements IDimTemplateService {

    @Autowired
    private  DimTemplateMapper dimTemplateMapper;

    @Autowired
    private IDimConfigService dimConfigService;

    @Override
    public PageInfo<DimTemplate> listPage(DimTemplate dimTemplate) {
        PageUtil.startPage(dimTemplate.getPageNum(), dimTemplate.getPageSize());
        QueryWrapper<DimTemplate> wrapper = new QueryWrapper<DimTemplate>(dimTemplate);
        if(dimTemplate.getTemplateId() != null){
            wrapper.eq("TEMPLATE_ID",dimTemplate.getTemplateId());
        }
        if(StringUtils.isNoneBlank(dimTemplate.getTemplateVersion())){
            wrapper.like("TEMPLATE_VERSION",dimTemplate.getTemplateVersion());
        }
        if(StringUtils.isNoneBlank(dimTemplate.getOverseasRelation())){
            wrapper.eq("OVERSEAS_RELATION",dimTemplate.getOverseasRelation());
        }
        if(StringUtils.isNoneBlank(dimTemplate.getCompanyType())){
            wrapper.eq("COMPANY_TYPE",dimTemplate.getCompanyType());
        }
        return new PageInfo<>(dimTemplateMapper.selectList(wrapper));
    }

    @Override
    @Transactional
    public DimTemplate saveOrUpdateTemplate(DimTemplate dimTemplate) {
        if(dimTemplate.getTemplateId()!= null){
            dimTemplate.setLastUpdateDate(new Date());
        }else{
            dimTemplate.setCreationDate(new Date());
            Long id = IdGenrator.generate();
            dimTemplate.setTemplateId(id);
            dimTemplate.setTemplateVersion(this.templateVersionGenrator());
        }

        this.saveOrUpdate(dimTemplate);
        //删除对应模板的配置表已经字段配置以及维度配置
       // dimConfigService.deleteDimByTemplateId(dimTemplate.getTemplateId());

        if(dimTemplate.getDimConfigs() != null
                && !dimTemplate.getDimConfigs().isEmpty()){

            dimConfigService.saveBatchDimConfig(dimTemplate.getDimConfigs(),dimTemplate.getTemplateId());
        }

        return  dimTemplate;
    }

    @Override
    public DimTemplateDTO getByTemplateId(Long templateId) {
        DimTemplateDTO dataTemplate = dimTemplateMapper.getByTemplateId(templateId);
        if(dataTemplate != null){
         List<DimConfigDTO> dimConfigDTOS = dimConfigService.getByTemplateId(templateId);
            if(dimConfigDTOS != null &&
                    !dimConfigDTOS.isEmpty()){
                dataTemplate.setDimConfigS(dimConfigDTOS);
            }
        }
        return dataTemplate;
    }

    @Override
    public List<DimConfigDTO> getConfigByTemplate(DimTemplate dimTemplate) {
        List<DimConfigDTO> dimConfigDTOS = new ArrayList<>();
        QueryWrapper<DimTemplate> wrapper = new QueryWrapper<DimTemplate>(dimTemplate);
        List<DimTemplate> dimTemplates = dimTemplateMapper.selectList(wrapper);
        //同一个公司性质以及境外关系只能定义一条
        if(!CollectionUtils.isEmpty(dimTemplates)){
            dimConfigDTOS = dimConfigService.getByTemplateId(dimTemplates.get(0).getTemplateId());
        }
        return dimConfigDTOS;
    }

    private String templateVersionGenrator(){

        String max_code = dimTemplateMapper.templateVersionGenrator();
        String comment_code = "";
            SimpleDateFormat format = new SimpleDateFormat("yyyy"); // 时间字符串产生方式
            String uid_pfix = format.format(new Date()); // 组合流水号前一部分，时间字符串，如：1601
            if (max_code != null && max_code.contains(uid_pfix)) {
                String uid_end = max_code.substring(4, 8); // 截取字符串最后四位，结果:0001
                // System.out.println("uid_end=" + uid_end);
                int endNum = Integer.parseInt(uid_end); // 把String类型的0001转化为int类型的1
                // System.out.println("endNum=" + endNum);
                int tmpNum = 10000 + endNum + 1; // 结果10002
                // System.out.println("tmpNum=" + tmpNum);
                comment_code = uid_pfix + this.subStrTemplateNumber("" + tmpNum, 1);// 把10002首位的1去掉，再拼成1601260002字符串

            } else {
                comment_code = uid_pfix + "0001";
            }
            // System.out.println(comment_code);

            return comment_code;
    }

    @Override
    public void checkTemplate(DimTemplate dimTemplate) {
        Assert.notNull(dimTemplate,"不能传空值");
        Assert.notNull(dimTemplate.getOverseasRelation(),"境外关系企业性质必填");
        Assert.notNull(dimTemplate.getCompanyType(),"境外关系企业性质必填");
        DimTemplate query = new DimTemplate();
        query.setCompanyType(dimTemplate.getCompanyType());
        query.setOverseasRelation(dimTemplate.getOverseasRelation());
        QueryWrapper<DimTemplate> wrapper = new QueryWrapper<DimTemplate>(query);
        wrapper.ne(dimTemplate.getTemplateId() != null,"TEMPLATE_ID",dimTemplate.getTemplateId());
        List<DimTemplate> dimTemplates = this.list(wrapper);
        if(!CollectionUtils.isEmpty(dimTemplates)){
            throw  new BaseException("模板已存在，不可重复配置!");
        }

    }

    /*
     * 把10002首位的1去掉的实现方法：
     * @param str
     * @param start
     * @return
     */
    private  String subStrTemplateNumber(String str, int start) {
        if (str == null || str.equals("") || str.length() == 0)
            return "";
        if (start < str.length()) {
            return str.substring(start);
        } else {
            return "";
        }

    }
}
