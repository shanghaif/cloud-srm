package com.midea.cloud.file.utils;

import lombok.Data;

import java.io.Serializable;

/**
 * 美的云OSS返回类
 * 
 *
 */
@Data
public class MideaOssResponse implements Serializable {

	private static final long serialVersionUID = 3710082742512686469L;

	private int code;

	private String msg;

	private Object data;
}
