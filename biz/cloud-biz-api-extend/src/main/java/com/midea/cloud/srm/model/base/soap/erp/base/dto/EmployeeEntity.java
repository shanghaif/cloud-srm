package com.midea.cloud.srm.model.base.soap.erp.base.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <pre>
 *  员工接口表实体类
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/18 11:27
 *  修改内容:
 * </pre>
 */

@XmlAccessorType(XmlAccessType.FIELD) //@XmlAccessorType(XmlAccessType.FIELD) 映射这个类中的所有字段到XML
@XmlType(name = "", propOrder = {
        "id","emplid","emplRcd","effdt","name","nameAc","birthdate","country","countryDescr","sex","marStatus",
        "nationalIdType","nationalId","address1","address2","mobilePhone","emailAddr","emailAddr2",
        "ethnicGrpCd","startDtChn","politicalStaChn","nativePlaceChn","lgiCoachId","lastHireDt","terminationDt",
        "lgiProbationFlag","hrStatus","positionNbr","positionNbrDescr","company","companyDescr",
        "deptid","deptDescr","location","locationDescr","jobcode","jobcodeDescr","supervisorId",
        "emplClass","lgiJobGrade","lgiJobLevel","oprid","lgiWorkingHour","lgiWkdOtMod","lgiHolOtMod",
        "lastupddttm","lgiIntDt","lgiIntFlag","finishedFlag","errorFlag","messageText","Attr1","Attr2","Attr3","Attr4","Attr5"

}) //propOrder 指定映射XML时的节点顺序，使用该属性时，必须列出JavaBean对象中的所有字段，否则会报错
@Data
@Accessors(chain = true)
public class EmployeeEntity {

    /**
     * 主键ID
     */
    @XmlElement(name = "ID", required = false)
    private Long id;

    /**
     * 员工ID
     */
    @XmlElement(name = "EMPLID", required = false)
    private String emplid;

    /**
     * 雇佣记录（0:主岗；1-99:兼岗）
     */
    @XmlElement(name = "EMPL_RCD", required = false)
    private String emplRcd;

    /**
     * 生效日期（日期格式：YYYY-MM-DD）
     */
    @XmlElement(name = "EFFDT", required = false)
    private String effdt;

    /**
     * 姓名
     */
    @XmlElement(name = "NAME", required = false)
    private String name;

    /**
     * 英文名
     */
    @XmlElement(name = "NAME_AC", required = false)
    private String nameAc;

    /**
     * 出生日期（日期格式：YYYY-MM-DD）
     */
    @XmlElement(name = "BIRTHDATE", required = false)
    private String birthdate;

    /**
     * 国籍代码
     */
    @XmlElement(name = "COUNTRY", required = false)
    private String country;

    /**
     * 国籍描述
     */
    @XmlElement(name = "COUNTRY_DESCR", required = false)
    private String countryDescr;

    /**
     * 性别（M-男；F-女；U-未知）
     */
    @XmlElement(name = "SEX", required = false)
    private String sex;

    /**
     * 婚姻状况
     */
    @XmlElement(name = "MAR_STATUS", required = false)
    private String marStatus;

    /**
     * 证件类型
     */
    @XmlElement(name = "NATIONAL_ID_TYPE", required = false)
    private String nationalIdType;

    /**
     * 证件号码
     */
    @XmlElement(name = "NATIONAL_ID", required = false)
    private String nationalId;

    /**
     * 居住地址
     */
    @XmlElement(name = "ADDRESS1", required = false)
    private String address1;

    /**
     * 法律文书地址
     */
    @XmlElement(name = "ADDRESS2", required = false)
    private String address2;

    /**
     * 移动电话
     */
    @XmlElement(name = "MOBILE_PHONE", required = false)
    private String mobilePhone;

    /**
     * 公司邮箱
     */
    @XmlElement(name = "EMAIL_ADDR", required = false)
    private String emailAddr;

    /**
     * 个人邮箱
     */
    @XmlElement(name = "EMAIL_ADDR2", required = false)
    private String emailAddr2;

    /**
     * 民族
     */
    @XmlElement(name = "ETHNIC_GRP_CD", required = false)
    private String ethnicGrpCd;

    /**
     * 职业生涯开始日期（日期格式：YYYY-MM-DD）
     */
    @XmlElement(name = "START_DT_CHN", required = false)
    private String startDtChn;

    /**
     * 政治面貌（2 中共预备党员；3 共青团员；4 民革会员；5 民盟盟员；6 民建会员；7 民进会员；8 农工党党员；9 致公党党员；10 九三学社社员；11 台盟盟员；12 无党派人士；13 群众；99 其他）
     */
    @XmlElement(name = "POLITICAL_STA_CHN", required = false)
    private String politicalStaChn;

    /**
     * 籍贯
     */
    @XmlElement(name = "NATIVE_PLACE_CHN", required = false)
    private String nativePlaceChn;

    /**
     * 入职导师
     */
    @XmlElement(name = "LGI_COACH_ID", required = false)
    private String lgiCoachId;

    /**
     * 入职日期（日期格式：YYYY-MM-DD）
     */
    @XmlElement(name = "LAST_HIRE_DT", required = false)
    private String lastHireDt;

    /**
     * 离职日期（日期格式：YYYY-MM-DD）
     */
    @XmlElement(name = "TERMINATION_DT", required = false)
    private String terminationDt;

    /**
     * 转正（Y-已转正；N-未转正）
     */
    @XmlElement(name = "LGI_PROBATION_FLAG", required = false)
    private String lgiProbationFlag;

    /**
     * HR状态（A-在职；I-离职）
     */
    @XmlElement(name = "HR_STATUS", required = false)
    private String hrStatus;

    /**
     * 职位编号
     */
    @XmlElement(name = "POSITION_NBR", required = false)
    private String positionNbr;

    /**
     * 职位描述
     */
    @XmlElement(name = "POSITION_NBR_DESCR", required = false)
    private String positionNbrDescr;

    /**
     * 公司
     */
    @XmlElement(name = "COMPANY", required = false)
    private String company;

    /**
     * 公司描述
     */
    @XmlElement(name = "COMPANY_DESCR", required = false)
    private String companyDescr;

    /**
     * 部门编码
     */
    @XmlElement(name = "DEPTID", required = false)
    private String deptid;

    /**
     * 部门描述
     */
    @XmlElement(name = "DEPT_DESCR", required = false)
    private String deptDescr;

    /**
     * 地点编码
     */
    @XmlElement(name = "LOCATION", required = false)
    private String location;

    /**
     * 地点描述
     */
    @XmlElement(name = "LOCATION_DESCR", required = false)
    private String locationDescr;

    /**
     * 岗位代码
     */
    @XmlElement(name = "JOBCODE", required = false)
    private String jobcode;

    /**
     * 岗位描述
     */
    @XmlElement(name = "JOBCODE_DESCR", required = false)
    private String jobcodeDescr;

    /**
     * 直接上级ID（直接上级员工）
     */
    @XmlElement(name = "SUPERVISOR_ID", required = false)
    private String supervisorId;

    /**
     * 员工类别（1 正式员工；2 实习生；3 劳务派遣；4 顾问；5 退休返聘）
     */
    @XmlElement(name = "EMPL_CLASS", required = false)
    private String emplClass;

    /**
     * 职等
     */
    @XmlElement(name = "LGI_JOB_GRADE", required = false)
    private String lgiJobGrade;

    /**
     * 职级
     */
    @XmlElement(name = "LGI_JOB_LEVEL", required = false)
    private String lgiJobLevel;

    /**
     * 用户账户（移动端根据此字段生成账号）
     */
    @XmlElement(name = "OPRID", required = false)
    private String oprid;

    /**
     * 工时制（T1：综合工时制；T2：不定时工时制；T3：标准工时制）
     */
    @XmlElement(name = "LGI_WORKING_HOUR", required = false)
    private String lgiWorkingHour;

    /**
     * 超时激励方式（A:超时奖金或换休；B:超时奖金；C:换休）
     */
    @XmlElement(name = "LGI_WKD_OT_MOD", required = false)
    private String lgiWkdOtMod;

    /**
     * 节假日超时激励方式（A:超时奖金或换休；B:超时奖金；C:换休）
     */
    @XmlElement(name = "LGI_HOL_OT_MOD", required = false)
    private String lgiHolOtMod;

    /**
     * 上次更新日期时间（日期格式：YYYY-MM-DD HH24:MI:SS）
     */
    @XmlElement(name = "LASTUPDDTTM", required = false)
    private String lastupddttm;

    /**
     * 时间戳
     */
    @XmlElement(name = "LGI_INT_DT", required = false)
    private String lgiIntDt;

    /**
     * 全量增量标记（A:新增,U:更新,D:删除,N:无变化）
     */
    @XmlElement(name = "LGI_INT_FLAG", required = false)
    private String lgiIntFlag;

    /**
     * 完成标识
     */
    @XmlElement(name = "FINISHED_FLAG", required = false)
    private String finishedFlag;

    /**
     * 出错标识
     */
    @XmlElement(name = "ERROR_FLAG", required = false)
    private String errorFlag;

    /**
     * 消息文本
     */
    @XmlElement(name = "MESSAGE_TEXT", required = false)
    private String messageText;

    /**
     * 弹性字段1
     */
    @XmlElement(name = "Attr1", required = false)
    private String Attr1;

    /**
     * 弹性字段2
     */
    @XmlElement(name = "Attr2", required = false)
    private String Attr2;

    /**
     * 弹性字段3
     */
    @XmlElement(name = "Attr3", required = false)
    private String Attr3;

    /**
     * 弹性字段4
     */
    @XmlElement(name = "Attr4", required = false)
    private String Attr4;

    /**
     * 弹性字段5
     */
    @XmlElement(name = "Attr5", required = false)
    private String Attr5;
    
}
