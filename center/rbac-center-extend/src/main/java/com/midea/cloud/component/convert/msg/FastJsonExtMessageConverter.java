package com.midea.cloud.component.convert.msg;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.midea.cloud.common.constants.SysConstant;
import com.midea.cloud.common.enums.InvokeType;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.component.filter.HttpServletHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;

public class FastJsonExtMessageConverter extends FastJsonHttpMessageConverter {

    protected void writeInternal(Object objIn, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        String invokeType = HttpServletHolder.getRequest().getParameter(SysConstant.INVOKE_TYPE);
        invokeType = invokeType == null ? HttpServletHolder.getRequest().getHeader(SysConstant.INVOKE_TYPE) : invokeType;
        if (invokeType != null && invokeType.equals(InvokeType.INTERAL_INVOKE.getCode())) {
            super.writeInternal(objIn, outputMessage);
            return;
        }
        BaseResult baseResult = null;
        try {
            if (objIn != null && objIn instanceof BaseResult) {
                ((BaseResult)objIn).setMessage(LocaleHandler.getLocaleMsg(((BaseResult)objIn).getMessage())); // 添加国际化支持
                super.writeInternal(objIn, outputMessage);
                return;
            } else {
            	if( this.checkIsOriginalResponse(objIn, outputMessage) ) {
            		//特殊的不做处理，主要第三方框架需要原生返回,header有拦截器根据url前缀录入
            		super.writeInternal(objIn, outputMessage);
            		return;
            	}else {
                    baseResult = BaseResult.buildSuccess(objIn);
            	}
            }
        } catch (BaseException baseException) {
            baseResult = BaseResult.build(baseException.getResultCode(), baseException.getMessage());
        } catch (Exception e) {
            baseResult = BaseResult.build(ResultCode.CONVERSION_NOT_SUPPORTED, null);
        }
        baseResult.setMessage(LocaleHandler.getLocaleMsg(baseResult.getMessage())); // 添加国际化支持
        super.writeInternal(baseResult, outputMessage);
    }

    protected void addDefaultHeaders(HttpHeaders headers, Object obj, MediaType contentType) throws IOException {
        headers.setContentType(new MediaType(MediaType.APPLICATION_JSON, this.getDefaultCharset()));
    }

    /**
     * 判断是否原始数据返回，主要第三方框架需要原生返回,header有拦截器根据url前缀录入
     * @param objIn
     * @param outputMessage
     * @return
     */
    public Boolean checkIsOriginalResponse(Object objIn, HttpOutputMessage outputMessage) {
    	if( outputMessage!=null && outputMessage.getHeaders()!=null 
    			&& outputMessage.getHeaders().get("IS-ORIGINAL-RESPONSE")!=null 
    			&& "true".equals(outputMessage.getHeaders().get("IS-ORIGINAL-RESPONSE").get(0)) ) {
    		//特殊的不做处理，主要第三方框架需要原生返回,header有拦截器根据url前缀录入
    		return true;
    	}
    	return false;
    }
}
