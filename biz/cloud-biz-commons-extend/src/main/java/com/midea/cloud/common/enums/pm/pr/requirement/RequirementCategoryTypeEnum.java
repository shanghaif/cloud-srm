package com.midea.cloud.common.enums.pm.pr.requirement;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RequirementCategoryTypeEnum {
    //物资类别的，分类
    SERVICE_IT("SERVICE_IT","服务类-IT信息化服务"),
    SERVICE_OTHER("SERVICE_OTHER","服务类-其他"),
    BUSINESS_THINGS("BUSINESS_THINGS","招待用品"),
    LOGISTICS("LOGISTICS","物流"),
    OTHERS("OTHERS","其他");

    private String name;
    private String value;

}
