package com.nicom.processing.utils;

import java.util.ArrayList;
import java.util.List;

import com.nicom.processing.dtos.InstructionRequest;

public class RequestResponseMaker {

	public static InstructionRequest makeTestInstructionRequest() {
		InstructionRequest instructionRequest=new InstructionRequest();
		List<String> instructionScripts=makeTestInstructionScripts();
		instructionRequest.setInstructionScripts(instructionScripts);
		return instructionRequest;
	}

	private static List<String> makeTestInstructionScripts() {
		String instructionScript;
		List<String> instructionScripts=new ArrayList<>();
		instructionScript=": +. + . ;";
		instructionScripts.add(instructionScript);			

		instructionScript="1 2 +.";
		instructionScripts.add(instructionScript);		
		
		return instructionScripts;
	}

}
