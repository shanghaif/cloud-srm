package com.midea.cloud.srm.model.base.idm;

import lombok.Data;

/**
 * <pre>
 *  隆基IDm接口用户实体类
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/24 15:03
 *  修改内容:
 * </pre>
 *
 */
@Data
public class IdmEmployee {
    private String USER_TYPE;  //用户类型
    private String USERID;	//用户id
    private String USER_NAME_C;	 //姓名
    private String USER_TEL;	 //	电话
    private String USER_EMAIL;	 //邮箱
    private String COMPANY_NAME;	 //公司名
    private String PASSWORD;	 //密码
    private String EDI_BPM_FLAG;	 //传递BPM标记(E:新增，N：关闭N)
    private String EDI_BPM_TIME;	 //传递BPM时间(账号截至日期)
    private String Attr1;	 //	备用字段1
    private String Attr2;	 //	备用字段2
    private String Attr3;	 //	备用字段3
    private String Attr4;	 //	备用字段4
    private String STATUS;  //状态

    /**以下字段是修改Idm密码*/
    private String EMPLID;  //用户名
    private String NewPassword;  //新的密码
    private String OldPassword;  //旧密码
    private String Attr5;  //备用字段5

}
