package com.nicom.processing.services.impl;


import org.springframework.stereotype.Service;

import com.nicom.processing.dtos.InstructionRequest;
import com.nicom.processing.dtos.InstructionResponse;
import com.nicom.processing.processors.ForthProcessor;
import com.nicom.processing.services.InstructionService;

import lombok.extern.java.Log;

@Log
@Service
public class InstructionServiceImpl  implements InstructionService{@Override
	public InstructionResponse processInstruction(InstructionRequest instructionRequest) {
		return ForthProcessor.processInstructions(instructionRequest);
	}

}
