package com.nicom.processing.modules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nicom.processing.dtos.InstructionRequest;
import com.nicom.processing.dtos.InstructionResponse;
import com.nicom.processing.services.InstructionService;

import lombok.extern.java.Log;

@Log
@Component
public class InstructionModule {
	
	@Autowired
	InstructionService instructionService;

	public InstructionResponse processInstruction(InstructionRequest instructionRequest) {
		return instructionService.processInstruction(instructionRequest);
	}

}
