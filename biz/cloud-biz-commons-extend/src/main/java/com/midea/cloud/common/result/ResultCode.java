package com.midea.cloud.common.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author tanjl11@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/27 8:33
 *  修改内容:
 * </pre>
 */
@AllArgsConstructor
@Getter
public enum ResultCode {
    SUCCESS("R000", "操作成功"),
    UNKNOWN_ERROR("R001", "系统异常，请联系系统管理员"),
    SESSION_VALID_ERROR("R002", "会话失效，请重新登录"),
    PARAM_VALID_ERROR("R003", "输入参数有误，请重试"),
    RESOURCE_NOT_FOUND("R004", "请求资源未找到"),
    METHOD_NOT_SUPPORTED("R005", "不支持的请求方法格式"),
    MEDIA_TYPE_NOT_SUPPORTED("R006", "不支持媒体类型"),
    MEDIA_TYPE_NOT_ACCEPTABLE("R007", "不接受媒体类型"),
    MISSING_PATH_VARIABLE("R008", "缺失必要的路径变量"),
    MISSING_SERVLET_REQUEST_PARAMETER("R009", "缺失必要的请求参数"),
    SERVLET_REQUEST_BINDING("R010", "请求绑定异常"),
    CONVERSION_NOT_SUPPORTED("R011", "不支持的参数转换"),
    TYPE_MISMATCH("R012", "参数类型匹配有误"),
    MESSAGE_NOT_READABLE("R013", "不可读的请求参数类型"),
    MESSAGE_NOT_WRITABLE("R014", "不可写的请求参数类型"),
    METHOD_ARGUMENT_NOT_VALID("R015", "参数校验失败"),
    MISSING_SERVLET_REQUEST_PART("R016", "必要参数未传递"),
    BIND_ERROR("R017", "数据绑定失败"),
    NO_HANDLER_FOUND("R018", "未找到相关处理器"),
    ASYNC_REQUEST_TIMEOUT("R019", "调用请求超时"),
    OPERATION_FAILED("R020", "操作失败"),
    UPLOAD_EXCEPTIONS("R021", "上传失败"),
    DOWNLOAD_EXCEPTIONS("R022", "下载失败"),
    IMPORT_EXCEPTIONS("R023", "导入失败"),
    EXPORT_EXCEPTIONS("R024", "导出失败"),
    NEED_PERMISSION("R025", "没有权限"),
    IMPORT_TEMPLATE_EXCEPTIONS("R026", "导入模板错误"),
    RPC_ERROR("R027", "远程调用失败"),
    LOGIN_ERROR("R028", "账号或密码错误"),
    AUTH_ERROR("R029", "认证失败"),
    BASE_MATERIAL_PRICE_EXISTS("R030", "基材价格重复"),
    BASE_MATERIAL_NOT_EXISTS("R031", "基材编码不存在"),
    FORMULA_LINE_FACTOR_EMPTY("R032", "字段类型的公式元素缺失要素id"),
    MATERIAL_ATTRIBUTE_EMPTY("R033", "物料主属性不能为空"),
    MATERIAL_ATTRIBUTE_NAME_EXISTS("R034","物料主属性名已存在"),
    ESSENTIAL_FACTOR_NAME_EXISTS("R035","要素名称已存在"),
    STATUS_ERROR("R036","状态错误，无法操作"),
    MATERIAL_ITEM_NOT_EXISTS("R037","物料不存在"),
    FORMULA_STATUS_ERROR_OR_NOT_EXISTS("R038","公式不存在或未生效"),
    FIRST_LOGIN_MSG("R080", "第一次登陆,请重置密码");

    private String code;
    private String message;

    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}