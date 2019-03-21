package com.nicom.processing.services.impl;


import org.springframework.stereotype.Service;

import com.nicom.processing.dtos.InstructionRequest;
import com.nicom.processing.dtos.InstructionResponse;
import com.nicom.processing.processors.CentralProcessingUnit;
import com.nicom.processing.services.InstructionService;

import lombok.extern.java.Log;

@Log
@Service
public class InstructionServiceImpl  implements InstructionService{@Override
	public InstructionResponse processInstruction(InstructionRequest instructionRequest) {
		return CentralProcessingUnit.processInstructions(instructionRequest);
	}

}
