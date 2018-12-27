/**
 * ProjectManagerException.java
 *
 * Modification History
 *
 * Date        Version   Developer      Description
 * ---------   -------   ------------   --------------------------------------
 * 11/23/2018   1.0	 	 Cognizant		Initial version
 */
package com.project.mgr.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PMException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4657743076067403540L;

	private String errorCode;
	
	private String errorMessage;
	
	private int status;

	public PMException(String errorCode, String errorMessage, int status) {
		// TODO Auto-generated constructor stub
		super(errorMessage);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.status = status;
	}

	public PMException() {
		// TODO Auto-generated constructor stub
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public PMException transformException()
	{
		PMException restError = new PMException();
		restError.setErrorCode(this.errorCode);
		restError.setErrorMessage(this.errorMessage);
		return restError;
	}
	
}
