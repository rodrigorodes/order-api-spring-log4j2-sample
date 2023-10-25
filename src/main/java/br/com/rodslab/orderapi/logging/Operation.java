package br.com.rodslab.orderapi.logging;

import org.apache.commons.lang3.StringUtils;

public enum Operation {

	NEW_ORDER(2),
	REPLACED(3),
	CANCEL(4);
	
	private int code;

	private Operation(int code) {
		this.code = code;
	}
	
	public String code() {
		return StringUtils.leftPad(String.valueOf(code), 00, '0');
	}
}
