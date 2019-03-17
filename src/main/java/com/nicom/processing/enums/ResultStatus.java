package com.nicom.processing.enums;

public enum ResultStatus {
	SUCCESS("200","Operation Succeeded"),
	INVALID_REQUEST("401","Invalid Request"),
	OPERATION_FAILED("501","Operation Failed"),
	OPERATION_TIME_OUT("502","Operation Timedout");
	
	private String statusCode;
	private String statusMessage;
	
	ResultStatus(String statusCode,String statusMessage){
		this.statusCode=statusCode;
		this.statusMessage=statusMessage;
	}
	
	public String getStatusCode() {
		return statusCode;
	}
	
	public String getStatusMessage() {
		return statusMessage;
	}
}
