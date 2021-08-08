package com.midea.cloud.srm.model.base.organization.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.midea.cloud.srm.model.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
*  <pre>
 *  员工表（隆基员工同步） 模型
 * </pre>
*
* @author xiexh12@midea.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-18 11:18:28
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ceea_base_employee")
public class Employee extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId("ID")
    private Long id;

    /**
     * 员工ID
     */
    @TableField("EMPLID")
    private Long emplid;

    /**
     * 雇佣记录（0:主岗；1-99:兼岗）
     */
    @TableField("EMPL_RCD")
    private String emplRcd;

    /**
     * 生效日期（日期格式：YYYY-MM-DD）
     */
    @TableField("EFFDT")
    private String effdt;

    /**
     * 姓名
     */
    @TableField("NAME")
    private String name;

    /**
     * 英文名
     */
    @TableField("NAME_AC")
    private String nameAc;

    /**
     * 出生日期（日期格式：YYYY-MM-DD）
     */
    @TableField("BIRTHDATE")
    private String birthdate;

    /**
     * 国籍代码
     */
    @TableField("COUNTRY")
    private String country;

    /**
     * 国籍描述
     */
    @TableField("COUNTRY_DESCR")
    private String countryDescr;

    /**
     * 性别（M-男；F-女；U-未知）
     */
    @TableField("SEX")
    private String sex;

    /**
     * 婚姻状况
     */
    @TableField("MAR_STATUS")
    private String marStatus;

    /**
     * 证件类型
     */
    @TableField("NATIONAL_ID_TYPE")
    private String nationalIdType;

    /**
     * 证件号码
     */
    @TableField("NATIONAL_ID")
    private String nationalId;

    /**
     * 居住地址
     */
    @TableField("ADDRESS1")
    private String address1;

    /**
     * 法律文书地址
     */
    @TableField("ADDRESS2")
    private String address2;

    /**
     * 移动电话
     */
    @TableField("MOBILE_PHONE")
    private String mobilePhone;

    /**
     * 公司邮箱
     */
    @TableField("EMAIL_ADDR")
    private String emailAddr;

    /**
     * 个人邮箱
     */
    @TableField("EMAIL_ADDR2")
    private String emailAddr2;

    /**
     * 民族
     */
    @TableField("ETHNIC_GRP_CD")
    private String ethnicGrpCd;

    /**
     * 职业生涯开始日期（日期格式：YYYY-MM-DD）
     */
    @TableField("START_DT_CHN")
    private String startDtChn;

    /**
     * 政治面貌（2 中共预备党员；3 共青团员；4 民革会员；5 民盟盟员；6 民建会员；7 民进会员；8 农工党党员；9 致公党党员；10 九三学社社员；11 台盟盟员；12 无党派人士；13 群众；99 其他）
     */
    @TableField("POLITICAL_STA_CHN")
    private String politicalStaChn;

    /**
     * 籍贯
     */
    @TableField("NATIVE_PLACE_CHN")
    private String nativePlaceChn;

    /**
     * 入职导师
     */
    @TableField("LGI_COACH_ID")
    private String lgiCoachId;

    /**
     * 入职日期（日期格式：YYYY-MM-DD）
     */
    @TableField("LAST_HIRE_DT")
    private String lastHireDt;

    /**
     * 离职日期（日期格式：YYYY-MM-DD）
     */
    @TableField("TERMINATION_DT")
    private String terminationDt;

    /**
     * 转正（Y-已转正；N-未转正）
     */
    @TableField("LGI_PROBATION_FLAG")
    private String lgiProbationFlag;

    /**
     * HR状态（A-在职；I-离职）
     */
    @TableField("HR_STATUS")
    private String hrStatus;

    /**
     * 职位编号
     */
    @TableField("POSITION_NBR")
    private String positionNbr;

    /**
     * 职位描述
     */
    @TableField("POSITION_NBR_DESCR")
    private String positionNbrDescr;

    /**
     * 公司
     */
    @TableField("COMPANY")
    private String company;

    /**
     * 公司描述
     */
    @TableField("COMPANY_DESCR")
    private String companyDescr;

    /**
     * 部门编码
     */
    @TableField("DEPTID")
    private String deptid;

    /**
     * 部门描述
     */
    @TableField("DEPT_DESCR")
    private String deptDescr;

    /**
     * 地点编码
     */
    @TableField("LOCATION")
    private String location;

    /**
     * 地点描述
     */
    @TableField("LOCATION_DESCR")
    private String locationDescr;

    /**
     * 岗位代码
     */
    @TableField("JOBCODE")
    private String jobcode;

    /**
     * 岗位描述
     */
    @TableField("JOBCODE_DESCR")
    private String jobcodeDescr;

    /**
     * 直接上级ID（直接上级员工）
     */
    @TableField("SUPERVISOR_ID")
    private String supervisorId;

    /**
     * 员工类别（1 正式员工；2 实习生；3 劳务派遣；4 顾问；5 退休返聘）
     */
    @TableField("EMPL_CLASS")
    private String emplClass;

    /**
     * 职等
     */
    @TableField("LGI_JOB_GRADE")
    private String lgiJobGrade;

    /**
     * 职级
     */
    @TableField("LGI_JOB_LEVEL")
    private String lgiJobLevel;

    /**
     * 用户账户（移动端根据此字段生成账号）
     */
    @TableField("OPRID")
    private String oprid;

    /**
     * 工时制（T1：综合工时制；T2：不定时工时制；T3：标准工时制）
     */
    @TableField("LGI_WORKING_HOUR")
    private String lgiWorkingHour;

    /**
     * 超时激励方式（A:超时奖金或换休；B:超时奖金；C:换休）
     */
    @TableField("LGI_WKD_OT_MOD")
    private String lgiWkdOtMod;

    /**
     * 节假日超时激励方式（A:超时奖金或换休；B:超时奖金；C:换休）
     */
    @TableField("LGI_HOL_OT_MOD")
    private String lgiHolOtMod;

    /**
     * 上次更新日期时间（日期格式：YYYY-MM-DD HH24:MI:SS）
     */
    @TableField("LASTUPDDTTM")
    private String lastupddttm;

    /**
     * 时间戳
     */
    @TableField("LGI_INT_DT")
    private String lgiIntDt;

    /**
     * 全量增量标记（A:新增,U:更新,D:删除,N:无变化）
     */
    @TableField("LGI_INT_FLAG")
    private String lgiIntFlag;

    /**
     * 接口状态(NEW-数据在接口表里,没同步到业务表,UPDATE-数据更新到接口表里,SUCCESS-同步到业务表,ERROR-数据同步到业务表出错)
     */
    @TableField("ITF_STATUS")
    private String itfStatus;

    /**
     * 完成标识
     */
    @TableField("FINISHED_FLAG")
    private String finishedFlag;

    /**
     * 出错标识
     */
    @TableField("ERROR_FLAG")
    private String errorFlag;

    /**
     * 消息文本
     */
    @TableField("MESSAGE_TEXT")
    private String messageText;

    /**
     * 弹性字段1
     */
    @TableField("Attr1")
    private String Attr1;

    /**
     * 弹性字段2
     */
    @TableField("Attr2")
    private String Attr2;

    /**
     * 弹性字段3
     */
    @TableField("Attr3")
    private String Attr3;

    /**
     * 弹性字段4
     */
    @TableField("Attr4")
    private String Attr4;

    /**
     * 弹性字段5
     */
    @TableField("Attr5")
    private String Attr5;

    /**
     * 创建人ID
     */
    @TableField(value = "CREATED_ID", fill = FieldFill.INSERT)
    private Long createdId;

    /**
     * 创建人
     */
    @TableField(value = "CREATED_BY", fill = FieldFill.INSERT)
    private String createdBy;

    /**
     * 创建日期
     */
    @TableField(value = "CREATION_DATE", fill = FieldFill.INSERT)
    private Date creationDate;

    /**
     * 创建人IP
     */
    @TableField(value = "CREATED_BY_IP", fill = FieldFill.INSERT)
    private String createdByIp;

    /**
     * 最后更新人ID
     */
    @TableField(value = "LAST_UPDATED_ID", fill = FieldFill.INSERT_UPDATE)
    private Long lastUpdatedId;

    /**
     * 最后更新人
     */
    @TableField(value = "LAST_UPDATED_BY", fill = FieldFill.INSERT_UPDATE)
    private String lastUpdatedBy;

    /**
     * 最后更新时间
     */
    @TableField(value = "LAST_UPDATE_DATE", fill = FieldFill.INSERT_UPDATE)
    private Date lastUpdateDate;

    /**
     * 最后更新人IP
     */
    @TableField(value = "LAST_UPDATED_BY_IP", fill = FieldFill.INSERT_UPDATE)
    private String lastUpdatedByIp;

    /**
     * 租户ID
     */
    @TableField("TENANT_ID")
    private Long tenantId;

    /**
     * 版本号
     */
    @TableField("VERSION")
    private Long version;


}
