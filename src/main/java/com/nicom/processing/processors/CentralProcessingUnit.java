package com.nicom.processing.processors;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.nicom.processing.dtos.InstructionRequest;
import com.nicom.processing.dtos.InstructionResponse;
import com.nicom.processing.processors.forth.Forth;

public class CentralProcessingUnit {
	private static final String OPERATION_SUCEEDED_CODE ="200";
	private static final String OPERATION_SUCEEDED_MESSAGE = "Operation suceeded";

	public static InstructionResponse processInstructions(InstructionRequest instructionRequest)  {
		List<String> instructionScriptsResults =new ArrayList<String>();
		List<String> instructionScripts = instructionRequest.getInstructionScripts();

		long processingTimeInMs;
		long initialTimeInMs=getCurrentTimeInMs();
		Forth forth= new Forth();
		
		for (String script : instructionScripts) {
			try {
				String instructionScriptsResult=forth.processInput(script);
				instructionScriptsResults.add(instructionScriptsResult);
			} catch (Exception e) {
				e.printStackTrace();
				instructionScriptsResults.add("PROCESS_FAILED");
			}
		}

		processingTimeInMs=initialTimeInMs-getCurrentTimeInMs();		
		
		InstructionResponse response=new InstructionResponse();
		response.setResponseCode(OPERATION_SUCEEDED_CODE);
		response.setResponseMessage(OPERATION_SUCEEDED_MESSAGE);
		response.setOutPutLines(instructionScriptsResults);
		response.setResponseTime(new Date());
		response.setProcessingTimeInMs(processingTimeInMs);
		
		return response ;
	}

	
	private static long getCurrentTimeInMs() {
		return System.currentTimeMillis();
	}

}
