package com.nicom.processing.validators;

import com.nicom.processing.dtos.InstructionRequest;
import com.nicom.processing.dtos.InstructionResponse;
import com.nicom.processing.enums.ResultStatus;

public class InstructionRequestValidator {
	public static InstructionResponse validate(InstructionRequest instructionRequest) {
		boolean errorsFound=false;
		InstructionResponse instructionResponse=new InstructionResponse();
		String responseCode;
		String responseMessage;
		
		
		if(instructionRequest.getInstructionScripts()==null || instructionRequest.getInstructionScripts().size()<1) {
			errorsFound=true;
			responseCode=ResultStatus.INVALID_REQUEST.getStatusCode();
			responseMessage=ResultStatus.INVALID_REQUEST.getStatusMessage();

			instructionResponse.setResponseCode(responseCode);
			instructionResponse.setResponseMessage(responseMessage);
		} else if(instructionRequest.getIoMap()!=null && instructionRequest.getIoMap().size()>0) {
			if (instructionRequest.getIoVariables()!=null ||  instructionRequest.getIoVariables().size()>0) {
				errorsFound=true;
				responseCode=ResultStatus.INVALID_REQUEST.getStatusCode();
				responseMessage=ResultStatus.INVALID_REQUEST.getStatusMessage();
	
				instructionResponse.setResponseCode(responseCode);
				instructionResponse.setResponseMessage(responseMessage);
			}
		}
		
		if(!errorsFound) {
			responseCode=ResultStatus.SUCCESS.getStatusCode();
			responseMessage=ResultStatus.SUCCESS.getStatusMessage();

			instructionResponse.setResponseCode(responseCode);
			instructionResponse.setResponseMessage(responseMessage);
			
		}
		
		return instructionResponse;
		
	}

	public static boolean hasErrors(InstructionRequest instructionRequest) {
		return false;
	}
	
	
}
