package com.appdirect.event.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.appdirect.enums.ResponseErrorCode;

@XmlRootElement(name = "result")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class NotificationResponse {

	private boolean success=true;
	private String message;
	private ResponseErrorCode errorCode;
	private String accountIdentifier;

	public NotificationResponse(String companyUuid, boolean success, String accountIdentifier, ResponseErrorCode errorCode, String message) {

		this.accountIdentifier = companyUuid;
		this.success = success;
		this.message = message;
		this.errorCode = errorCode;
	}

	public NotificationResponse(String companyUuid) {
		// TODO Auto-generated constructor stub
	}

	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public ResponseErrorCode getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(ResponseErrorCode userAlreadyExists) {
		this.errorCode = userAlreadyExists;
	}
	public String getAccountIdentifier() {
		return accountIdentifier;
	}
	public void setAccountIdentifier(String accountIdentifier) {
		this.accountIdentifier = accountIdentifier;
	}

}