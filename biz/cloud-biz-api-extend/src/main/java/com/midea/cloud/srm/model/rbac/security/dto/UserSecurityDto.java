package com.midea.cloud.srm.model.rbac.security.dto;

import com.midea.cloud.srm.model.common.BaseDTO;
import com.midea.cloud.srm.model.rbac.security.entity.UserSecurity;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

/**
 * 用户安全 模型
 * @author lizl7
 *
 */
@Data
public class UserSecurityDto extends UserSecurity{

    /**
     * 前端base64数据流
     */
    private String faceFileBase64;

}
