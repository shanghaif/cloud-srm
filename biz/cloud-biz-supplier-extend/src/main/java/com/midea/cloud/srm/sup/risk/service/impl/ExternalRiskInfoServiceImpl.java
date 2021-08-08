package com.midea.cloud.srm.sup.risk.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.srm.model.supplier.risk.dto.ExternalRiskInfoDto;
import com.midea.cloud.srm.model.supplier.risk.entity.ExternalRiskInfo;
import com.midea.cloud.srm.sup.risk.mapper.ExternalRiskInfoMapper;
import com.midea.cloud.srm.sup.risk.service.IExternalRiskInfoService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
*  <pre>
 *  外部风险信息 服务实现类
 * </pre>
*
* @author chensl26@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-03 19:41:36
 *  修改内容:
 * </pre>
*/
@Service
public class ExternalRiskInfoServiceImpl extends ServiceImpl<ExternalRiskInfoMapper, ExternalRiskInfo> implements IExternalRiskInfoService {
    @Override
    public List<ExternalRiskInfoDto> getExternalRiskInfoDto(Long vendorId) {
        List<ExternalRiskInfo> externalRiskInfos = this.list();
        List<ExternalRiskInfoDto> externalRiskInfoDtos = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(externalRiskInfos)){
            Map<String, List<ExternalRiskInfo>> collect = externalRiskInfos.stream().collect(Collectors.groupingBy(ExternalRiskInfo::getTitleMain));
            Set<String> titles = collect.keySet();
            titles.forEach(title->{
                ExternalRiskInfoDto externalRiskInfoDto = new ExternalRiskInfoDto();
                externalRiskInfoDto.setTitle(title);
                List<ExternalRiskInfo> riskInfos = collect.get(title);
                if(CollectionUtils.isNotEmpty(riskInfos)){
                    externalRiskInfoDto.setNum(riskInfos.size());
                    StringBuffer info = new StringBuffer();
                    int size = riskInfos.size();
                    for (int i =0;i < size;i++){
                        ExternalRiskInfo externalRiskInfo = riskInfos.get(i);
                        info.append(externalRiskInfo.getTitleLine());
                        if((size-1) != i){
                            info.append("\n");
                        }
                    }
                    externalRiskInfoDto.setContent(info.toString());
                }else {
                    externalRiskInfoDto.setNum(0);
                }
                externalRiskInfoDtos.add(externalRiskInfoDto);
            });
        }
        return externalRiskInfoDtos;
    }
}
