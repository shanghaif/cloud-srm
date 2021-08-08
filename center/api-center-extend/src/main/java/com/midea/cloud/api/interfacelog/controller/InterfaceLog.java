package com.midea.cloud.api.interfacelog.controller;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.midea.cloud.common.enums.api.InterfaceType;
import com.midea.cloud.common.enums.api.ServiceType;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface InterfaceLog {
	ServiceType serviceType();
	InterfaceType type();
	String serviceName();
}
